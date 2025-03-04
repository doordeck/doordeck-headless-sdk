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

  override fun verify(code: String, promise: Promise) {
    doordeckSdk.account().verifyEphemeralKeyRegistrationAsync(code)
      .thenApply {
        doordeckSdk.contextManager().storeContext()
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
