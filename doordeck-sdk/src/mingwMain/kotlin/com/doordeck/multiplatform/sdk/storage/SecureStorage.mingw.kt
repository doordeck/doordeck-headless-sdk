package com.doordeck.multiplatform.sdk.storage

import com.doordeck.multiplatform.sdk.ApplicationContext
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.util.Utils.certificateChainToString
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.Utils.stringToCertificateChain
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.allocArray
import kotlinx.cinterop.cstr
import kotlinx.cinterop.invoke
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toKString

internal actual fun createSecureStorage(applicationContext: ApplicationContext?): SecureStorage {
    return DefaultSecureStorage(MemorySettings())
}

/**
 * Names of the entries exchanged with a host provided secure storage. The host stores opaque strings
 * against these names; all typing, encoding and validation stays on this side.
 */
object SecureStorageKeys {
    const val API_ENVIRONMENT = "apiEnvironment"
    const val CLOUD_AUTH_TOKEN = "cloudAuthToken"
    const val CLOUD_REFRESH_TOKEN = "cloudRefreshToken"
    const val FUSION_HOST = "fusionHost"
    const val FUSION_AUTH_TOKEN = "fusionAuthToken"
    const val PUBLIC_KEY = "publicKey"
    const val PRIVATE_KEY = "privateKey"
    const val KEY_PAIR_VERIFIED = "keyPairVerified"
    const val USER_ID = "userId"
    const val USER_EMAIL = "userEmail"
    const val CERTIFICATE_CHAIN = "certificateChain"
}

/**
 * Stores `value` under `key`. A null `value` removes the entry.
 */
internal typealias setEntryCallback = CPointer<CFunction<(CPointer<ByteVar>, CPointer<ByteVar>?) -> Unit>>

/**
 * Reads the entry stored under `key` into `buffer`, which has room for `capacity` bytes including the
 * terminating NUL, and returns the length of the value in bytes excluding that NUL, or -1 when no
 * entry exists. The value is not written when `capacity` is too small for it, so a null buffer with a
 * capacity of 0 is how the required length is asked for.
 *
 * The buffer belongs to this side, which is what keeps ownership out of the boundary. Having the host
 * return a pointer instead cannot be made correct: it has to free that pointer, and its only two
 * opportunities are before this side has read it, or never.
 */
internal typealias getEntryCallback = CPointer<CFunction<(CPointer<ByteVar>, CPointer<ByteVar>?, Int) -> Int>>

/**
 * Removes every entry.
 */
internal typealias clearEntriesCallback = CPointer<CFunction<() -> Unit>>

/**
 * The secure storage handed over by the host, if any. Kept here rather than returned across the
 * boundary so that no Kotlin object reference has to travel to the caller and back.
 */
internal var hostSecureStorage: SecureStorage? = null
    private set

@CName("set_secure_storage")
fun setSecureStorage(
    setEntryCp: setEntryCallback,
    getEntryCp: getEntryCallback,
    clearEntriesCp: clearEntriesCallback
) {
    hostSecureStorage = MingwSecureStorage(setEntryCp, getEntryCp, clearEntriesCp)
}

class MingwSecureStorage(
    private val setEntryCp: setEntryCallback,
    private val getEntryCp: getEntryCallback,
    private val clearEntriesCp: clearEntriesCallback
) : SecureStorage {

    private fun write(key: String, value: String?) = memScoped {
        setEntryCp.invoke(key.cstr.ptr, value?.cstr?.ptr)
    }

    private fun read(key: String): String? = memScoped {
        val keyPtr = key.cstr.ptr
        val length = getEntryCp.invoke(keyPtr, null, 0)
        when {
            length < 0 -> null
            length == 0 -> ""
            else -> {
                val buffer = allocArray<ByteVar>(length + 1)
                if (getEntryCp.invoke(keyPtr, buffer, length + 1) < 0) null else buffer.toKString()
            }
        }
    }

    override fun setApiEnvironment(apiEnvironment: ApiEnvironment) =
        write(SecureStorageKeys.API_ENVIRONMENT, apiEnvironment.name)

    override fun getApiEnvironment(): ApiEnvironment? =
        read(SecureStorageKeys.API_ENVIRONMENT)?.let { ApiEnvironment.valueOf(it) }

    override fun addCloudAuthToken(token: String) =
        write(SecureStorageKeys.CLOUD_AUTH_TOKEN, token)

    override fun getCloudAuthToken(): String? =
        read(SecureStorageKeys.CLOUD_AUTH_TOKEN)

    override fun addCloudRefreshToken(token: String) =
        write(SecureStorageKeys.CLOUD_REFRESH_TOKEN, token)

    override fun getCloudRefreshToken(): String? =
        read(SecureStorageKeys.CLOUD_REFRESH_TOKEN)

    override fun setFusionHost(host: String) =
        write(SecureStorageKeys.FUSION_HOST, host)

    override fun getFusionHost(): String? =
        read(SecureStorageKeys.FUSION_HOST)

    override fun addFusionAuthToken(token: String) =
        write(SecureStorageKeys.FUSION_AUTH_TOKEN, token)

    override fun getFusionAuthToken(): String? =
        read(SecureStorageKeys.FUSION_AUTH_TOKEN)

    override fun addPublicKey(publicKey: ByteArray) =
        write(SecureStorageKeys.PUBLIC_KEY, publicKey.encodeByteArrayToBase64())

    override fun getPublicKey(): ByteArray? =
        read(SecureStorageKeys.PUBLIC_KEY)?.decodeBase64ToByteArray()

    override fun addPrivateKey(privateKey: ByteArray) =
        write(SecureStorageKeys.PRIVATE_KEY, privateKey.encodeByteArrayToBase64())

    override fun getPrivateKey(): ByteArray? =
        read(SecureStorageKeys.PRIVATE_KEY)?.decodeBase64ToByteArray()

    override fun setKeyPairVerified(publicKey: ByteArray?) =
        write(SecureStorageKeys.KEY_PAIR_VERIFIED, publicKey?.encodeByteArrayToBase64())

    override fun getKeyPairVerified(): ByteArray? =
        read(SecureStorageKeys.KEY_PAIR_VERIFIED)?.decodeBase64ToByteArray()

    override fun addUserId(userId: String) =
        write(SecureStorageKeys.USER_ID, userId)

    override fun getUserId(): String? =
        read(SecureStorageKeys.USER_ID)

    override fun addUserEmail(email: String) =
        write(SecureStorageKeys.USER_EMAIL, email)

    override fun getUserEmail(): String? =
        read(SecureStorageKeys.USER_EMAIL)

    override fun addCertificateChain(certificateChain: List<String>) =
        write(SecureStorageKeys.CERTIFICATE_CHAIN, certificateChain.certificateChainToString())

    override fun getCertificateChain(): List<String>? =
        read(SecureStorageKeys.CERTIFICATE_CHAIN)?.stringToCertificateChain()

    override fun clear() = clearEntriesCp.invoke()
}