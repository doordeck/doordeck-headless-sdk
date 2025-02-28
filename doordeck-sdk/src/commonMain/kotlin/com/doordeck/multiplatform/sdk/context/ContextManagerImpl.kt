package com.doordeck.multiplatform.sdk.context

import com.doordeck.multiplatform.sdk.cache.CapabilityCache
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.signWithPrivateKey
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.verifySignature
import com.doordeck.multiplatform.sdk.model.data.Context
import com.doordeck.multiplatform.sdk.model.data.Crypto
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.storage.SecureStorage
import com.doordeck.multiplatform.sdk.util.JwtUtils.isJwtTokenAboutToExpire
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Utils.stringToCertificateChain
import com.doordeck.multiplatform.sdk.util.fromJson
import kotlin.uuid.Uuid

internal object ContextManagerImpl : ContextManager {

    private lateinit var apiEnvironment: ApiEnvironment
    private var currentToken: String? = null
    private var currentRefreshToken: String? = null
    private var currentFusionToken: String? = null
    private var currentUserId: String? = null
    private var currentEmail: String? = null
    private var currentUserCertificateChain: List<String>? = null
    private var currentUserPublicKey: ByteArray? = null
    private var currentUserPrivateKey: ByteArray? = null
    private lateinit var secureStorage: SecureStorage

    override fun setApiEnvironment(apiEnvironment: ApiEnvironment) {
        this.apiEnvironment = apiEnvironment
    }

    override fun getApiEnvironment(): ApiEnvironment {
        return apiEnvironment
    }

    override fun setAuthToken(token: String) {
        currentToken = token
        secureStorage.addCloudAuthToken(token)
    }

    override fun getAuthToken(): String? {
        return currentToken
    }

    override fun isAuthTokenAboutToExpire(): Boolean {
        return currentToken?.isJwtTokenAboutToExpire() ?: true
    }

    override fun setRefreshToken(token: String) {
        currentRefreshToken = token
        secureStorage.addCloudRefreshToken(token)
    }

    override fun getRefreshToken(): String? {
        return currentRefreshToken
    }

    override fun setFusionAuthToken(token: String) {
        currentFusionToken = token
        secureStorage.addFusionAuthToken(token)
    }

    override fun getFusionAuthToken(): String? {
        return currentFusionToken
    }

    override fun setUserId(userId: String) {
        currentUserId = userId
        secureStorage.addUserId(userId)
    }

    override fun getUserId(): String? {
        return currentUserId
    }

    override fun setUserEmail(email: String) {
        currentEmail = email
        secureStorage.addUserEmail(email)
    }

    override fun getUserEmail(): String? {
        return currentEmail
    }

    override fun setCertificateChain(certificateChain: List<String>) {
        currentUserCertificateChain = certificateChain
        secureStorage.addCertificateChain(certificateChain)
    }

    override fun getCertificateChain(): List<String>? {
        return currentUserCertificateChain
    }

    override fun isCertificateChainAboutToExpire(): Boolean {
        return currentUserCertificateChain?.firstOrNull()?.let {
            CryptoManager.isCertificateAboutToExpire(it)
        } ?: true
    }

    override fun setKeyPair(publicKey: ByteArray, privateKey: ByteArray) {
        currentUserPublicKey = publicKey
        currentUserPrivateKey = privateKey
        secureStorage.addPublicKey(publicKey)
        secureStorage.addPrivateKey(privateKey)
    }

    override fun getKeyPair(): Crypto.KeyPair? {
        val actualUserPublicKey = currentUserPublicKey
        val actualUserPrivateKey = currentUserPrivateKey
        return if (actualUserPublicKey != null && actualUserPrivateKey != null) {
            Crypto.KeyPair(actualUserPrivateKey, actualUserPublicKey)
        } else null
    }

    internal fun getPublicKey(): ByteArray? {
        return currentUserPublicKey
    }

    internal fun getPrivateKey(): ByteArray? {
        return currentUserPrivateKey
    }

    override fun isKeyPairValid(): Boolean {
        val actualUserPublicKey = currentUserPublicKey
        val actualUserPrivateKey = currentUserPrivateKey
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
        setAuthToken(token)
        setRefreshToken(refreshToken)
    }

    internal fun reset() {
        currentToken = null
        currentRefreshToken = null
        currentFusionToken = null
        currentUserId = null
        currentUserCertificateChain = null
        currentUserPublicKey = null
        currentUserPrivateKey = null
        currentEmail = null
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

    internal fun loadContext() {
        currentToken =  secureStorage.getCloudAuthToken()
        currentRefreshToken = secureStorage.getCloudRefreshToken()
        currentFusionToken = secureStorage.getFusionAuthToken()
        currentUserId = secureStorage.getUserId()
        currentEmail = secureStorage.getUserEmail()
        currentUserCertificateChain = secureStorage.getCertificateChain()
        currentUserPublicKey = secureStorage.getPublicKey()
        currentUserPrivateKey = secureStorage.getPrivateKey()
    }

    override fun clearContext() {
        secureStorage.clear()
    }
}