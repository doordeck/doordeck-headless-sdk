package com.doordeck.headlessreactnativesdk

import com.doordeck.multiplatform.sdk.Doordeck
import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.model.data.LockOperations
import com.doordeck.multiplatform.sdk.model.responses.AssistedRegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.TileLocksResponse
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.annotations.ReactModule
import java.security.KeyFactory
import java.security.Signature
import java.security.spec.PKCS8EncodedKeySpec
import kotlin.time.Duration.Companion.days

@ReactModule(name = HeadlessReactNativeSdkModule.NAME)
class HeadlessReactNativeSdkModule(
  reactContext: ReactApplicationContext,
  private val doordeckSdk: Doordeck,
) : NativeHeadlessReactNativeSdkSpec(reactContext) {

  override fun getName(): String {
    return NAME
  }

  /**
   * Auth operations
   */

  override fun login(
    email: String,
    password: String,
    promise: Promise,
  ) {
    setKeyPairIfNeeded()

    doordeckSdk.accountless().loginAsync(email, password)
      .thenApply {
        respondNeedsVerification(promise)
      }
      .exceptionally { error ->
        promise.reject("LOGIN_ERROR", error)
      }
  }

  override fun setAuthToken(
    authToken: String,
    promise: Promise,
  ) {
    setKeyPairIfNeeded()

    doordeckSdk.contextManager().setAuthToken(authToken)
    respondNeedsVerification(promise)
  }

  internal fun String.signWithPrivateKey(privateKey: ByteArray): ByteArray = try {
    Signature.getInstance("Ed25519").apply {
      initSign(
        KeyFactory.getInstance("Ed25519")
        .generatePrivate(PKCS8EncodedKeySpec(privateKey.toPlatformPrivateKey())))
      update(toByteArray())
    }.sign()
  } catch (exception: Exception) {
    throw SdkException("Failed to sign with private key", exception)
  }


  fun ByteArray.toPlatformPrivateKey(): ByteArray = when(size) {
    CRYPTO_KIT_PRIVATE_KEY_SIZE,
    SODIUM_PRIVATE_KEY_SIZE -> PRIVATE_KEY_ASN1_HEADER + sliceArray(0 until RAW_KEY_SIZE)
    JAVA_PKCS8_PRIVATE_KEY_SIZE -> this
    else -> throw SdkException("Unknown private key size: $size")
  }

  override fun verify(code: String, promise: Promise) {
    doordeckSdk.account().verifyEphemeralKeyRegistrationAsync(code)
      .thenApply {
        promise.resolve(null)
      }
      .exceptionally { error ->
        promise.reject("VERIFY_ERROR", error)
      }
  }

  override fun logout(promise: Promise) {
    doordeckSdk.account().logoutAsync()
      .thenApply {
        doordeckSdk.contextManager().clearContext()
        promise.resolve(null)
      }
      .exceptionally { error ->
        promise.reject("LOGOUT_ERROR", error)
      }
  }

  /**
   * Device and tile operations
   */

  override fun getLocksBelongingToTile(tileId: String, promise: Promise) {
    doordeckSdk.tiles().getLocksBelongingToTileAsync(tileId)
      .thenApply { response ->
        promise.resolve(response.toNativeMap())
      }
      .exceptionally { error ->
        promise.reject("LOCKS_ERROR", error)
      }
  }

  override fun unlockDevice(lockId: String, promise: Promise) {
    doordeckSdk.lockOperations().unlockAsync(
      LockOperations.UnlockOperation(
        LockOperations.BaseOperation(
          lockId = lockId,
        )
      )
    )
      .thenApply {
        promise.resolve(null)
      }
      .exceptionally { error ->
        promise.reject("UNLOCK_ERROR", error)
      }
  }


  companion object {
    const val NAME = "HeadlessReactNativeSdk"
  }

  private fun setKeyPairIfNeeded() {
    if (doordeckSdk.contextManager().getKeyPair() == null) {
      val newKeyPair = doordeckSdk.crypto().generateKeyPair()
      doordeckSdk.contextManager().setKeyPair(
        publicKey = newKeyPair.public,
        privateKey = newKeyPair.private,
      )

      doordeckSdk.contextManager().storeContext()
    }
  }

  private fun respondNeedsVerification(promise: Promise) {
    doordeckSdk.helper().assistedRegisterEphemeralKeyAsync()
      .thenApply { response ->
        promise.resolve(response.toNativeMap())
      }
      .exceptionally { error ->
        promise.reject("NEEDS_VERIFICATION_ERROR", error)
      }
  }

  private fun AssistedRegisterEphemeralKeyResponse.toNativeMap() = Arguments.createMap().apply {
    putBoolean("requiresVerification", requiresVerification)
  }

  private fun TileLocksResponse.toNativeMap() = Arguments.createMap().apply {
    putString("siteId", siteId)
    putString("tileId", tileId)
    putArray("deviceIds", Arguments.createArray().apply {
      deviceIds.forEach { pushString(it) }
    })
  }

}


internal const val SODIUM_PUBLIC_KEY_SIZE = 32
internal const val SODIUM_PRIVATE_KEY_SIZE = 64
internal const val CRYPTO_KIT_PUBLIC_KEY_SIZE = 32
internal const val CRYPTO_KIT_PRIVATE_KEY_SIZE = 32
internal const val JAVA_PKCS8_PUBLIC_KEY_SIZE = 44
internal const val JAVA_PKCS8_PRIVATE_KEY_SIZE = 48
internal const val RAW_KEY_SIZE = 32
internal val MIN_CERTIFICATE_LIFETIME_DAYS = 7.days
internal val PRIVATE_KEY_ASN1_HEADER = byteArrayOf(
  0x30, 0x2e,                     // ASN.1 SEQUENCE, length 46
  0x02, 0x01, 0x00,               // INTEGER 0
  0x30, 0x05,                     // ASN.1 SEQUENCE, length 5
  0x06, 0x03, 0x2b, 0x65, 0x70,   // OBJECT IDENTIFIER 1.3.101.112 (Ed25519)
  0x04, 0x22,                     // OCTET STRING, length 34
  0x04, 0x20                      // OCTET STRING, length 32 (your key here)
)

internal val PUBLIC_KEY_ASN1_HEADER = byteArrayOf(
  0x30, 0x2a,                     // ASN.1 SEQUENCE, length 42
  0x30, 0x05,                     // ASN.1 SEQUENCE, length 5
  0x06, 0x03, 0x2b, 0x65, 0x70,   // OBJECT IDENTIFIER 1.3.101.112 (Ed25519)
  0x03, 0x21, 0x00                // BIT STRING, length 33 (0 padding bit)
)
