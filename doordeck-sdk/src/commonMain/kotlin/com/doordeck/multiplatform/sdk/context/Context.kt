package com.doordeck.multiplatform.sdk.context

import com.doordeck.multiplatform.sdk.Constants.DEFAULT_FUSION_HOST
import com.doordeck.multiplatform.sdk.cache.CapabilityCache
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.logger.SdkLogger
import com.doordeck.multiplatform.sdk.model.common.ContextState
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.model.data.Crypto
import com.doordeck.multiplatform.sdk.storage.DefaultSecureStorage
import com.doordeck.multiplatform.sdk.storage.MemorySettings
import com.doordeck.multiplatform.sdk.storage.SecureStorage
import com.doordeck.multiplatform.sdk.util.JwtUtils.isJwtTokenInvalidOrExpired
import com.doordeck.multiplatform.sdk.util.KeyPairUtils
import kotlin.jvm.JvmSynthetic

internal object Context {

    private var secureStorage: SecureStorage = DefaultSecureStorage(MemorySettings())

    @JvmSynthetic
    fun setDebugLogging(enabled: Boolean) {
        SdkLogger.enableDebugLogging(enabled)
    }

    /**
     * Sets the API environment on which the SDK will operate, the provided value will be automatically stored in secure storage.
     *
     * It can be changed anytime, although it is recommended to set it up through the `SdkConfig` builder.
     */
    @JvmSynthetic
    fun setApiEnvironment(apiEnvironment: ApiEnvironment) {
        secureStorage.setApiEnvironment(apiEnvironment)
    }

    /**
     * Retrieves the current API environment.
     */
    @JvmSynthetic
    fun getApiEnvironment(): ApiEnvironment {
        return secureStorage.getApiEnvironment()
            ?: ApiEnvironment.PROD
    }

    /**
     * Sets the cloud authentication token, the provided value will be automatically stored in secure storage.
     *
     * It can be changed anytime, although it is recommended to set it up through the `SdkConfig` builder.
     */
    @JvmSynthetic
    fun setCloudAuthToken(token: String) {
        secureStorage.addCloudAuthToken(token)
    }

    /**
     * Retrieves the cloud authentication token.
     */
    @JvmSynthetic
    fun getCloudAuthToken(): String? {
        return secureStorage.getCloudAuthToken()
    }

    /**
     * Checks whether the cloud authentication token is invalid (e.g., null, malformed) or expired.
     * (we consider it expired if it will expire within the next [com.doordeck.multiplatform.sdk.util.MIN_TOKEN_LIFETIME_DAYS] days).
     */
    @JvmSynthetic
    fun isCloudAuthTokenInvalidOrExpired(): Boolean {
        return getCloudAuthToken()?.isJwtTokenInvalidOrExpired() ?: true
    }

    /**
     * Sets the cloud refresh token, the provided value will be automatically stored in secure storage.
     *
     * It can be changed anytime, although it is recommended to set it up through the `SdkConfig` builder.
     */
    @JvmSynthetic
    fun setCloudRefreshToken(token: String) {
        secureStorage.addCloudRefreshToken(token)
    }

    /**
     * Retrieves the cloud refresh token.
     */
    @JvmSynthetic
    fun getCloudRefreshToken(): String? {
        return secureStorage.getCloudRefreshToken()
    }

    /**
     * Sets the fusion host, the provided value will be automatically stored in secure storage.
     *
     * It can be changed anytime, although it is recommended to set it up through the SdkConfig builder.
     */
    @JvmSynthetic
    fun setFusionHost(host: String) {
        secureStorage.setFusionHost(host)
    }

    /**
     * Retrieves the fusion host.
     */
    @JvmSynthetic
    fun getFusionHost(): String {
        return secureStorage.getFusionHost()
            ?: DEFAULT_FUSION_HOST
    }

    /**
     * Sets the Fusion authentication token, the provided value will be automatically stored in secure storage.
     */
    @JvmSynthetic
    fun setFusionAuthToken(token: String) {
        secureStorage.addFusionAuthToken(token)
    }

    /**
     * Retrieves the Fusion authentication token.
     */
    @JvmSynthetic
    fun getFusionAuthToken(): String? {
        return secureStorage.getFusionAuthToken()
    }

    /**
     * Sets the user ID associated with the context, the provided value will be automatically stored in secure storage.
     */
    @JvmSynthetic
    fun setUserId(userId: String) {
        secureStorage.addUserId(userId)
    }

    /**
     * Retrieves the user identifier.
     */
    @JvmSynthetic
    fun getUserId(): String? {
        return secureStorage.getUserId()
    }

    /**
     * Sets the user email associated with the context, the provided value will be automatically stored in secure storage.
     */
    @JvmSynthetic
    fun setUserEmail(email: String) {
        secureStorage.addUserEmail(email)
    }

    /**
     * Retrieves the user email.
     */
    @JvmSynthetic
    fun getUserEmail(): String? {
        return secureStorage.getUserEmail()
    }

    /**
     * Sets the certificate chain for the context, the provided value will be automatically stored in secure storage.
     */
    @JvmSynthetic
    fun setCertificateChain(certificateChain: List<String>) {
        secureStorage.addCertificateChain(certificateChain)
    }

    /**
     * Retrieves the certificate chain.
     */
    @JvmSynthetic
    fun getCertificateChain(): List<String>? {
        return secureStorage.getCertificateChain()
    }

    /**
     * Checks whether the certificate chain is invalid (e.g., null, malformed) or expired.
     * (we consider it expired if it will expire within the next [com.doordeck.multiplatform.sdk.crypto.MIN_CERTIFICATE_LIFETIME_DAYS] days).
     */
    @JvmSynthetic
    fun isCertificateChainInvalidOrExpired(): Boolean {
        return getCertificateChain()?.firstOrNull()?.let {
            CryptoManager.isCertificateInvalidOrExpired(it)
        } ?: true
    }

    /**
     * Sets the key pair for the context, the provided values will be automatically stored in secure storage.
     */
    @JvmSynthetic
    fun setKeyPair(publicKey: ByteArray, privateKey: ByteArray) {
        secureStorage.addPublicKey(publicKey)
        secureStorage.addPrivateKey(privateKey)
    }

    /**
     * Retrieves the key pair.
     */
    @JvmSynthetic
    fun getKeyPair(): Crypto.KeyPair? {
        val actualUserPublicKey = getPublicKey()
        val actualUserPrivateKey = getPrivateKey()
        return if (actualUserPublicKey != null && actualUserPrivateKey != null) {
            Crypto.KeyPair(actualUserPrivateKey, actualUserPublicKey)
        } else null
    }

    /**
     * Sets the public key that has been verified via two-factor authentication. The provided value will be automatically stored in secure storage.
     */
    @JvmSynthetic
    fun setKeyPairVerified(publicKey: ByteArray?) {
        secureStorage.setKeyPairVerified(publicKey)
    }

    /**
     * Retrieves the key pair verification status.
     */
    @JvmSynthetic
    fun isKeyPairVerified(): Boolean {
        val verified = secureStorage.getKeyPairVerified()
        val publicKey = secureStorage.getPublicKey()
        if (verified == null || publicKey == null) {
            return false
        }
        return verified.contentEquals(publicKey)
    }

    @JvmSynthetic
    fun getPublicKey(): ByteArray? {
        return secureStorage.getPublicKey()
    }

    @JvmSynthetic
    fun getPrivateKey(): ByteArray? {
        return secureStorage.getPrivateKey()
    }

    /**
     * Checks whether the current key pair is not null and valid by signing a small piece of text and verifying it.
     */
    @JvmSynthetic
    fun isKeyPairValid(): Boolean {
        val publicKey = getPublicKey()
        val privateKey = getPrivateKey()
        if (publicKey == null || privateKey == null) {
            return false
        }
        return KeyPairUtils.isKeyPairValid(publicKey, privateKey)
    }

    @JvmSynthetic
    fun reset() {
        CapabilityCache.reset()
        clearContext()
    }

    /**
     * Sets all necessary fields to perform secure operations, the provided values will be automatically stored in secure storage.
     */
    @JvmSynthetic
    fun setOperationContext(userId: String, certificateChain: List<String>, publicKey: ByteArray,
                                     privateKey: ByteArray, isKeyPairVerified: Boolean) {
        setUserId(userId)
        setCertificateChain(certificateChain)
        setKeyPair(publicKey = publicKey, privateKey = privateKey)
        setKeyPairVerified(if (isKeyPairVerified) publicKey else null)
    }

    /**
     * Checks the context and returns a [ContextState] representing its state.
     */
    @JvmSynthetic
    fun getContextState(): ContextState {
        if (isCloudAuthTokenInvalidOrExpired()) { return ContextState.CLOUD_TOKEN_IS_INVALID_OR_EXPIRED }
        if (!isKeyPairValid()) { return ContextState.KEY_PAIR_IS_INVALID }
        if (!isKeyPairVerified()) { return ContextState.KEY_PAIR_IS_NOT_VERIFIED }
        if (isCertificateChainInvalidOrExpired()) { return ContextState.CERTIFICATE_CHAIN_IS_INVALID_OR_EXPIRED }
        return ContextState.READY
    }

    @JvmSynthetic
    fun setSecureStorageImpl(secureStorage: SecureStorage) {
        this.secureStorage = secureStorage
    }

    /**
     * Clears all the values stored in secure storage.
     */
    @JvmSynthetic
    fun clearContext() {
        secureStorage.clear()
    }
}