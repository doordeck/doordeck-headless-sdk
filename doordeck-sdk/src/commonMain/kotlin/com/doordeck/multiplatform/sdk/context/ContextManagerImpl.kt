package com.doordeck.multiplatform.sdk.context

import com.doordeck.multiplatform.sdk.Constants.DEFAULT_FUSION_HOST
import com.doordeck.multiplatform.sdk.cache.CapabilityCache
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.logger.SdkLogger
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.model.data.Context
import com.doordeck.multiplatform.sdk.model.data.Crypto
import com.doordeck.multiplatform.sdk.storage.DefaultSecureStorage
import com.doordeck.multiplatform.sdk.storage.MemorySettings
import com.doordeck.multiplatform.sdk.storage.SecureStorage
import com.doordeck.multiplatform.sdk.util.JwtUtils.isJwtTokenInvalidOrExpired
import com.doordeck.multiplatform.sdk.util.KeyPairUtils
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Utils.stringToCertificateChain
import com.doordeck.multiplatform.sdk.util.fromJson

internal object ContextManagerImpl : ContextManager {

    private var secureStorage: SecureStorage = DefaultSecureStorage(MemorySettings())

    internal fun setDebugLogging(enabled: Boolean) {
        SdkLogger.enableDebugLogging(enabled)
    }

    override fun setApiEnvironment(apiEnvironment: ApiEnvironment) {
        secureStorage.setApiEnvironment(apiEnvironment)
    }

    override fun getApiEnvironment(): ApiEnvironment {
        return secureStorage.getApiEnvironment()
            ?: ApiEnvironment.PROD
    }

    override fun setCloudAuthToken(token: String) {
        secureStorage.addCloudAuthToken(token)
    }

    override fun getCloudAuthToken(): String? {
        return secureStorage.getCloudAuthToken()
    }

    override fun isCloudAuthTokenInvalidOrExpired(): Boolean {
        return getCloudAuthToken()?.isJwtTokenInvalidOrExpired() ?: true
    }

    override fun setCloudRefreshToken(token: String) {
        secureStorage.addCloudRefreshToken(token)
    }

    override fun getCloudRefreshToken(): String? {
        return secureStorage.getCloudRefreshToken()
    }

    override fun setFusionHost(host: String) {
        secureStorage.setFusionHost(host)
    }

    override fun getFusionHost(): String {
        return secureStorage.getFusionHost()
            ?: DEFAULT_FUSION_HOST
    }

    override fun setFusionAuthToken(token: String) {
        secureStorage.addFusionAuthToken(token)
    }

    override fun getFusionAuthToken(): String? {
        return secureStorage.getFusionAuthToken()
    }

    override fun setUserId(userId: String) {
        secureStorage.addUserId(userId)
    }

    override fun getUserId(): String? {
        return secureStorage.getUserId()
    }

    override fun setUserEmail(email: String) {
        secureStorage.addUserEmail(email)
    }

    override fun getUserEmail(): String? {
        return secureStorage.getUserEmail()
    }

    override fun setCertificateChain(certificateChain: List<String>) {
        secureStorage.addCertificateChain(certificateChain)
    }

    override fun getCertificateChain(): List<String>? {
        return secureStorage.getCertificateChain()
    }

    override fun isCertificateChainInvalidOrExpired(): Boolean {
        return getCertificateChain()?.firstOrNull()?.let {
            CryptoManager.isCertificateInvalidOrExpired(it)
        } ?: true
    }

    override fun setKeyPair(publicKey: ByteArray, privateKey: ByteArray) {
        secureStorage.addPublicKey(publicKey)
        secureStorage.addPrivateKey(privateKey)
    }

    override fun getKeyPair(): Crypto.KeyPair? {
        val actualUserPublicKey = getPublicKey()
        val actualUserPrivateKey = getPrivateKey()
        return if (actualUserPublicKey != null && actualUserPrivateKey != null) {
            Crypto.KeyPair(actualUserPrivateKey, actualUserPublicKey)
        } else null
    }

    override fun setKeyPairVerified(publicKey: ByteArray?) {
        secureStorage.setKeyPairVerified(publicKey)
    }

    override fun isKeyPairVerified(): Boolean {
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

    override fun isKeyPairValid(): Boolean {
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

    override fun setOperationContext(userId: String, certificateChain: List<String>, publicKey: ByteArray,
                                     privateKey: ByteArray, isKeyPairVerified: Boolean) {
        setUserId(userId)
        setCertificateChain(certificateChain)
        setKeyPair(publicKey = publicKey, privateKey = privateKey)
        setKeyPairVerified(if (isKeyPairVerified) publicKey else null)
    }

    override fun setOperationContextJson(data: String) {
        val operationContextData = data.fromJson<Context.OperationContextData>()
        setOperationContext(
            userId = operationContextData.userId,
            certificateChain = operationContextData.userCertificateChain.stringToCertificateChain(),
            publicKey = operationContextData.userPublicKey.decodeBase64ToByteArray(),
            privateKey = operationContextData.userPrivateKey.decodeBase64ToByteArray(),
            isKeyPairVerified = operationContextData.isKeyPairVerified
        )
    }

    internal fun setSecureStorageImpl(secureStorage: SecureStorage) {
        this.secureStorage = secureStorage
    }

    override fun clearContext() {
        secureStorage.clear()
    }
}