package com.doordeck.multiplatform.sdk.context

import com.doordeck.multiplatform.sdk.cache.CapabilityCache
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.signWithPrivateKey
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.verifySignature
import com.doordeck.multiplatform.sdk.model.data.Context
import com.doordeck.multiplatform.sdk.model.data.Crypto
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.storage.DefaultSecureStorage
import com.doordeck.multiplatform.sdk.storage.MemorySettings
import com.doordeck.multiplatform.sdk.storage.SecureStorage
import com.doordeck.multiplatform.sdk.util.JwtUtils.isJwtTokenAboutToExpire
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Utils.stringToCertificateChain
import com.doordeck.multiplatform.sdk.util.fromJson
import kotlin.uuid.Uuid

internal object ContextManagerImpl : ContextManager {

    private var secureStorage: SecureStorage = DefaultSecureStorage(MemorySettings())

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

    override fun isCloudAuthTokenAboutToExpire(): Boolean {
        return getCloudAuthToken()?.isJwtTokenAboutToExpire() ?: true
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
            ?: "localhost:27700"
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

    override fun isCertificateChainAboutToExpire(): Boolean {
        return getCertificateChain()?.firstOrNull()?.let {
            CryptoManager.isCertificateAboutToExpire(it)
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

    internal fun getPublicKey(): ByteArray? {
        return secureStorage.getPublicKey()
    }

    internal fun getPrivateKey(): ByteArray? {
        return secureStorage.getPrivateKey()
    }

    override fun isKeyPairValid(): Boolean {
        val actualUserPublicKey = getPublicKey()
        val actualUserPrivateKey = getPrivateKey()
        if (actualUserPublicKey == null || actualUserPrivateKey == null) {
            return false
        }
        val text = Uuid.random().toString()
        val signature = try {
            text.signWithPrivateKey(actualUserPrivateKey)
        } catch (exception: Exception) {
            return false
        }
        return signature.verifySignature(actualUserPublicKey, text)
    }

    internal fun setTokens(token: String, refreshToken: String) {
        setCloudAuthToken(token)
        setCloudRefreshToken(refreshToken)
    }

    internal fun reset() {
        CapabilityCache.reset()
        clearContext()
    }

    override fun setOperationContext(userId: String, certificateChain: List<String>, publicKey: ByteArray, privateKey: ByteArray) {
        setUserId(userId)
        setCertificateChain(certificateChain)
        setKeyPair(publicKey, privateKey)
    }

    override fun setOperationContextJson(data: String) {
        val operationContextData = data.fromJson<Context.OperationContextData>()
        setUserId(operationContextData.userId)
        setCertificateChain(operationContextData.userCertificateChain.stringToCertificateChain())
        setKeyPair(operationContextData.userPublicKey.decodeBase64ToByteArray(), operationContextData.userPrivateKey.decodeBase64ToByteArray())
    }

    internal fun setSecureStorageImpl(secureStorage: SecureStorage) {
        this.secureStorage = secureStorage
    }

    override fun clearContext() {
        secureStorage.clear()
    }
}