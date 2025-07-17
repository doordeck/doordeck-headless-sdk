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

internal object Context {

    private var secureStorage: SecureStorage = DefaultSecureStorage(MemorySettings())

    internal fun setDebugLogging(enabled: Boolean) {
        SdkLogger.enableDebugLogging(enabled)
    }

    fun setApiEnvironment(apiEnvironment: ApiEnvironment) {
        secureStorage.setApiEnvironment(apiEnvironment)
    }

    fun getApiEnvironment(): ApiEnvironment {
        return secureStorage.getApiEnvironment()
            ?: ApiEnvironment.PROD
    }

    fun setCloudAuthToken(token: String) {
        secureStorage.addCloudAuthToken(token)
    }

    fun getCloudAuthToken(): String? {
        return secureStorage.getCloudAuthToken()
    }

    fun isCloudAuthTokenInvalidOrExpired(): Boolean {
        return getCloudAuthToken()?.isJwtTokenInvalidOrExpired() ?: true
    }

    fun setCloudRefreshToken(token: String) {
        secureStorage.addCloudRefreshToken(token)
    }

    fun getCloudRefreshToken(): String? {
        return secureStorage.getCloudRefreshToken()
    }

    fun setFusionHost(host: String) {
        secureStorage.setFusionHost(host)
    }

    fun getFusionHost(): String {
        return secureStorage.getFusionHost()
            ?: DEFAULT_FUSION_HOST
    }

    fun setFusionAuthToken(token: String) {
        secureStorage.addFusionAuthToken(token)
    }

    fun getFusionAuthToken(): String? {
        return secureStorage.getFusionAuthToken()
    }

    fun setUserId(userId: String) {
        secureStorage.addUserId(userId)
    }

    fun getUserId(): String? {
        return secureStorage.getUserId()
    }

    fun setUserEmail(email: String) {
        secureStorage.addUserEmail(email)
    }

    fun getUserEmail(): String? {
        return secureStorage.getUserEmail()
    }

    fun setCertificateChain(certificateChain: List<String>) {
        secureStorage.addCertificateChain(certificateChain)
    }

    fun getCertificateChain(): List<String>? {
        return secureStorage.getCertificateChain()
    }

    fun isCertificateChainInvalidOrExpired(): Boolean {
        return getCertificateChain()?.firstOrNull()?.let {
            CryptoManager.isCertificateInvalidOrExpired(it)
        } ?: true
    }

    fun setKeyPair(publicKey: ByteArray, privateKey: ByteArray) {
        secureStorage.addPublicKey(publicKey)
        secureStorage.addPrivateKey(privateKey)
    }

    fun getKeyPair(): Crypto.KeyPair? {
        val actualUserPublicKey = getPublicKey()
        val actualUserPrivateKey = getPrivateKey()
        return if (actualUserPublicKey != null && actualUserPrivateKey != null) {
            Crypto.KeyPair(actualUserPrivateKey, actualUserPublicKey)
        } else null
    }

    fun setKeyPairVerified(publicKey: ByteArray?) {
        secureStorage.setKeyPairVerified(publicKey)
    }

    fun isKeyPairVerified(): Boolean {
        val verified = secureStorage.getKeyPairVerified()
        val publicKey = secureStorage.getPublicKey()
        if (verified == null || publicKey == null) {
            return false
        }
        return verified.contentEquals(publicKey)
    }

    internal fun getPublicKey(): ByteArray? {
        return secureStorage.getPublicKey()
    }

    internal fun getPrivateKey(): ByteArray? {
        return secureStorage.getPrivateKey()
    }

    fun isKeyPairValid(): Boolean {
        val publicKey = getPublicKey()
        val privateKey = getPrivateKey()
        if (publicKey == null || privateKey == null) {
            return false
        }
        return KeyPairUtils.isKeyPairValid(publicKey, privateKey)
    }

    internal fun reset() {
        CapabilityCache.reset()
        clearContext()
    }

    fun setOperationContext(userId: String, certificateChain: List<String>, publicKey: ByteArray,
                                     privateKey: ByteArray, isKeyPairVerified: Boolean) {
        setUserId(userId)
        setCertificateChain(certificateChain)
        setKeyPair(publicKey = publicKey, privateKey = privateKey)
        setKeyPairVerified(if (isKeyPairVerified) publicKey else null)
    }

    fun getContextState(): ContextState {
        if (isCloudAuthTokenInvalidOrExpired()) { return ContextState.CLOUD_TOKEN_IS_INVALID_OR_EXPIRED }
        if (!isKeyPairValid()) { return ContextState.KEY_PAIR_IS_INVALID }
        if (!isKeyPairVerified()) { return ContextState.KEY_PAIR_IS_NOT_VERIFIED }
        if (isCertificateChainInvalidOrExpired()) { return ContextState.CERTIFICATE_CHAIN_IS_INVALID_OR_EXPIRED }
        return ContextState.READY
    }

    internal fun setSecureStorageImpl(secureStorage: SecureStorage) {
        this.secureStorage = secureStorage
    }

    fun clearContext() {
        secureStorage.clear()
    }
}