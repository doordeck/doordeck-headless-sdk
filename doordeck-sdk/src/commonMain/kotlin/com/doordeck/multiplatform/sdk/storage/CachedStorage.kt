package com.doordeck.multiplatform.sdk.storage

import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import io.ktor.utils.io.locks.SynchronizedObject
import io.ktor.utils.io.locks.synchronized

/**
 * [SecureStorage] decorator that keeps an in-memory copy of every value, so that only the first read
 * of each value hits the (potentially slow) [delegate] storage.
 *
 * Writes go to the [delegate] first and then update the in-memory copy (write-through), so the
 * [delegate] must not be modified by anything other than this instance.
 */
internal class CachedStorage(
    private val delegate: SecureStorage
) : SecureStorage {

    private val lock = SynchronizedObject()

    /**
     * Cached values, a key being present (even with a null value) means it has already been loaded.
     */
    private val cache = mutableMapOf<StorageKey, Any?>()

    override fun setApiEnvironment(apiEnvironment: ApiEnvironment) =
        store(StorageKey.API_ENVIRONMENT_KEY, apiEnvironment) { delegate.setApiEnvironment(apiEnvironment) }

    override fun getApiEnvironment(): ApiEnvironment? =
        retrieve(StorageKey.API_ENVIRONMENT_KEY) { delegate.getApiEnvironment() }

    override fun addCloudAuthToken(token: String) =
        store(StorageKey.CLOUD_AUTH_TOKEN_KEY, token) { delegate.addCloudAuthToken(token) }

    override fun getCloudAuthToken(): String? =
        retrieve(StorageKey.CLOUD_AUTH_TOKEN_KEY) { delegate.getCloudAuthToken() }

    override fun addCloudRefreshToken(token: String) =
        store(StorageKey.CLOUD_REFRESH_TOKEN_KEY, token) { delegate.addCloudRefreshToken(token) }

    override fun getCloudRefreshToken(): String? =
        retrieve(StorageKey.CLOUD_REFRESH_TOKEN_KEY) { delegate.getCloudRefreshToken() }

    override fun setFusionHost(host: String) =
        store(StorageKey.FUSION_HOST_KEY, host) { delegate.setFusionHost(host) }

    override fun getFusionHost(): String? =
        retrieve(StorageKey.FUSION_HOST_KEY) { delegate.getFusionHost() }

    override fun addFusionAuthToken(token: String) =
        store(StorageKey.FUSION_AUTH_TOKEN_KEY, token) { delegate.addFusionAuthToken(token) }

    override fun getFusionAuthToken(): String? =
        retrieve(StorageKey.FUSION_AUTH_TOKEN_KEY) { delegate.getFusionAuthToken() }

    override fun addPublicKey(publicKey: ByteArray) =
        store(StorageKey.PUBLIC_KEY_KEY, publicKey.copyOf()) { delegate.addPublicKey(publicKey) }

    override fun getPublicKey(): ByteArray? =
        retrieve(StorageKey.PUBLIC_KEY_KEY) { delegate.getPublicKey() }?.copyOf()

    override fun addPrivateKey(privateKey: ByteArray) =
        store(StorageKey.PRIVATE_KEY_KEY, privateKey.copyOf()) { delegate.addPrivateKey(privateKey) }

    override fun getPrivateKey(): ByteArray? =
        retrieve(StorageKey.PRIVATE_KEY_KEY) { delegate.getPrivateKey() }?.copyOf()

    override fun setKeyPairVerified(publicKey: ByteArray?) =
        store(StorageKey.VERIFIED_KEY_PAIR_KEY, publicKey?.copyOf()) { delegate.setKeyPairVerified(publicKey) }

    override fun getKeyPairVerified(): ByteArray? =
        retrieve(StorageKey.VERIFIED_KEY_PAIR_KEY) { delegate.getKeyPairVerified() }?.copyOf()

    override fun addUserId(userId: String) =
        store(StorageKey.USER_ID_KEY, userId) { delegate.addUserId(userId) }

    override fun getUserId(): String? =
        retrieve(StorageKey.USER_ID_KEY) { delegate.getUserId() }

    override fun addUserEmail(email: String) =
        store(StorageKey.USER_EMAIL_KEY, email) { delegate.addUserEmail(email) }

    override fun getUserEmail(): String? =
        retrieve(StorageKey.USER_EMAIL_KEY) { delegate.getUserEmail() }

    override fun addCertificateChain(certificateChain: List<String>) =
        store(StorageKey.CERTIFICATE_CHAIN_KEY, certificateChain.toList()) { delegate.addCertificateChain(certificateChain) }

    override fun getCertificateChain(): List<String>? =
        retrieve(StorageKey.CERTIFICATE_CHAIN_KEY) { delegate.getCertificateChain() }

    override fun clear() = synchronized(lock) {
        try {
            delegate.clear()
        } finally {
            cache.clear()
        }
    }

    private inline fun <reified T> retrieve(key: StorageKey, load: () -> T): T = synchronized(lock) {
        if (key in cache) cache[key] as T
        else load().also { cache[key] = it }
    }

    /**
     * Invalidates the cached value before writing, so a failed write leaves the
     * key to be reloaded from the [delegate] on the next read.
     */
    private fun store(key: StorageKey, value: Any?, write: () -> Unit) = synchronized(lock) {
        cache.remove(key)
        write()
        cache[key] = value
    }
}