package com.doordeck.multiplatform.sdk.storage

import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.storage.StorageKey.API_ENVIRONMENT_KEY
import com.doordeck.multiplatform.sdk.storage.StorageKey.CERTIFICATE_CHAIN_KEY
import com.doordeck.multiplatform.sdk.storage.StorageKey.CLOUD_AUTH_TOKEN_KEY
import com.doordeck.multiplatform.sdk.storage.StorageKey.CLOUD_REFRESH_TOKEN_KEY
import com.doordeck.multiplatform.sdk.storage.StorageKey.FUSION_AUTH_TOKEN_KEY
import com.doordeck.multiplatform.sdk.storage.StorageKey.FUSION_HOST_KEY
import com.doordeck.multiplatform.sdk.storage.StorageKey.PRIVATE_KEY_KEY
import com.doordeck.multiplatform.sdk.storage.StorageKey.PUBLIC_KEY_KEY
import com.doordeck.multiplatform.sdk.storage.StorageKey.USER_EMAIL_KEY
import com.doordeck.multiplatform.sdk.storage.StorageKey.USER_ID_KEY
import com.doordeck.multiplatform.sdk.storage.StorageKey.VERIFIED_KEY_PAIR_KEY
import io.ktor.utils.io.locks.SynchronizedObject
import io.ktor.utils.io.locks.synchronized

/**
 * [SecureStorage] decorator that keeps a thread-safe in-memory copy of every value, so that only the first read
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

    override fun setApiEnvironment(apiEnvironment: ApiEnvironment) = store(API_ENVIRONMENT_KEY, apiEnvironment) {
        delegate.setApiEnvironment(apiEnvironment)
    }

    override fun getApiEnvironment(): ApiEnvironment? = retrieve(API_ENVIRONMENT_KEY) {
        delegate.getApiEnvironment()
    }

    override fun addCloudAuthToken(token: String) = store(CLOUD_AUTH_TOKEN_KEY, token) {
        delegate.addCloudAuthToken(token)
    }

    override fun getCloudAuthToken(): String? = retrieve(CLOUD_AUTH_TOKEN_KEY) {
        delegate.getCloudAuthToken()
    }

    override fun addCloudRefreshToken(token: String) = store(CLOUD_REFRESH_TOKEN_KEY, token) {
        delegate.addCloudRefreshToken(token)
    }

    override fun getCloudRefreshToken(): String? = retrieve(CLOUD_REFRESH_TOKEN_KEY) {
        delegate.getCloudRefreshToken()
    }

    override fun setFusionHost(host: String) = store(FUSION_HOST_KEY, host) {
        delegate.setFusionHost(host)
    }

    override fun getFusionHost(): String? = retrieve(FUSION_HOST_KEY) {
        delegate.getFusionHost()
    }

    override fun addFusionAuthToken(token: String) = store(FUSION_AUTH_TOKEN_KEY, token) {
        delegate.addFusionAuthToken(token)
    }

    override fun getFusionAuthToken(): String? = retrieve(FUSION_AUTH_TOKEN_KEY) {
        delegate.getFusionAuthToken()
    }

    override fun addPublicKey(publicKey: ByteArray) = store(PUBLIC_KEY_KEY, publicKey.copyOf()) {
        delegate.addPublicKey(publicKey)
    }

    override fun getPublicKey(): ByteArray? = retrieve(PUBLIC_KEY_KEY) {
        delegate.getPublicKey()
    }?.copyOf()

    override fun addPrivateKey(privateKey: ByteArray) = store(PRIVATE_KEY_KEY, privateKey.copyOf()) {
        delegate.addPrivateKey(privateKey)
    }

    override fun getPrivateKey(): ByteArray? = retrieve(PRIVATE_KEY_KEY) {
        delegate.getPrivateKey()
    }?.copyOf()

    override fun setKeyPairVerified(publicKey: ByteArray?) = store(VERIFIED_KEY_PAIR_KEY, publicKey?.copyOf()) {
        delegate.setKeyPairVerified(publicKey)
    }

    override fun getKeyPairVerified(): ByteArray? = retrieve(VERIFIED_KEY_PAIR_KEY) {
        delegate.getKeyPairVerified()
    }?.copyOf()

    override fun addUserId(userId: String) = store(USER_ID_KEY, userId) {
        delegate.addUserId(userId)
    }

    override fun getUserId(): String? = retrieve(USER_ID_KEY) {
        delegate.getUserId()
    }

    override fun addUserEmail(email: String) = store(USER_EMAIL_KEY, email) {
        delegate.addUserEmail(email)
    }

    override fun getUserEmail(): String? = retrieve(USER_EMAIL_KEY) {
        delegate.getUserEmail()
    }

    override fun addCertificateChain(certificateChain: List<String>) = store(CERTIFICATE_CHAIN_KEY, certificateChain.toList()) {
        delegate.addCertificateChain(certificateChain)
    }

    override fun getCertificateChain(): List<String>? = retrieve(CERTIFICATE_CHAIN_KEY) {
        delegate.getCertificateChain()
    }

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