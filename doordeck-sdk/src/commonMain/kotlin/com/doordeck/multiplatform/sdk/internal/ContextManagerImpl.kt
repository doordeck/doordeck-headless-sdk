package com.doordeck.multiplatform.sdk.internal

import com.doordeck.multiplatform.sdk.ApplicationContext
import com.doordeck.multiplatform.sdk.api.ContextManager
import com.doordeck.multiplatform.sdk.cache.CapabilityCache
import com.doordeck.multiplatform.sdk.api.model.ApiEnvironment
import com.doordeck.multiplatform.sdk.api.model.Context
import com.doordeck.multiplatform.sdk.api.model.Crypto
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.signWithPrivateKey
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.verifySignature
import com.doordeck.multiplatform.sdk.storage.SecureStorage
import com.doordeck.multiplatform.sdk.storage.createSecureStorage
import com.doordeck.multiplatform.sdk.util.JwtUtils.isJwtTokenAboutToExpire
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Utils.stringToCertificateChain
import com.doordeck.multiplatform.sdk.util.fromJson
import kotlin.uuid.Uuid

internal object ContextManagerImpl : ContextManager {

    private var apiEnvironment: ApiEnvironment = ApiEnvironment.PROD
    private var applicationContext: ApplicationContext? = null
    private var currentToken: String? = null
    private var currentRefreshToken: String? = null
    private var currentFusionToken: String? = null
    private var currentUserId: String? = null
    private var currentEmail: String? = null
    private var currentUserCertificateChain: List<String>? = null
    private var currentUserPublicKey: ByteArray? = null
    private var currentUserPrivateKey: ByteArray? = null
    private var secureStorage: SecureStorage? = null

    override fun setApiEnvironment(apiEnvironment: ApiEnvironment) {
        this.apiEnvironment = apiEnvironment
    }

    override fun getApiEnvironment(): ApiEnvironment {
        return apiEnvironment
    }

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        this.applicationContext = applicationContext
    }

    override fun setAuthToken(token: String) {
        currentToken = token
    }

    override fun getAuthToken(): String? {
        return currentToken
    }

    override fun isAuthTokenAboutToExpire(): Boolean {
        return currentToken?.isJwtTokenAboutToExpire() ?: true
    }

    override fun setRefreshToken(token: String) {
        currentRefreshToken = token
    }

    override fun getRefreshToken(): String? {
        return currentRefreshToken
    }

    override fun setFusionAuthToken(token: String) {
        currentFusionToken = token
    }

    override fun getFusionAuthToken(): String? {
        return currentFusionToken
    }

    override fun setUserId(userId: String) {
        currentUserId = userId
    }

    override fun getUserId(): String? {
        return currentUserId
    }

    override fun setUserEmail(email: String) {
        currentEmail = email
    }

    override fun getUserEmail(): String? {
        return currentEmail
    }

    override fun setCertificateChain(certificateChain: List<String>) {
        currentUserCertificateChain = certificateChain
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
        currentToken = token
        currentRefreshToken = refreshToken
    }

    internal fun reset() {
        resetTokens()
        resetOperationContext()
        currentEmail = null
        CapabilityCache.reset()
    }

    private fun resetTokens() {
        currentToken = null
        currentRefreshToken = null
        currentFusionToken = null
    }

    internal fun resetOperationContext() {
        currentUserId = null
        currentUserCertificateChain = null
        currentUserPublicKey = null
        currentUserPrivateKey = null
    }

    override fun setOperationContext(userId: String, certificateChain: List<String>, publicKey: ByteArray, privateKey: ByteArray) {
        currentUserId = userId
        currentUserCertificateChain = certificateChain
        currentUserPublicKey = publicKey
        currentUserPrivateKey = privateKey
    }

    override fun setOperationContextJson(data: String) {
        val operationContextData = data.fromJson<Context.OperationContextData>()
        currentUserId = operationContextData.userId
        currentUserCertificateChain = operationContextData.userCertificateChain.stringToCertificateChain()
        currentUserPublicKey = operationContextData.userPublicKey.decodeBase64ToByteArray()
        currentUserPrivateKey = operationContextData.userPrivateKey.decodeBase64ToByteArray()
    }

    override fun setSecureStorageImpl(secureStorage: SecureStorage) {
        this.secureStorage = secureStorage
    }

    override fun loadContext() {
        initializeSecureStorage()

        currentToken =  currentToken ?: secureStorage?.getCloudAuthToken()
        currentRefreshToken = currentRefreshToken ?: secureStorage?.getCloudRefreshToken()
        currentFusionToken = currentFusionToken ?: secureStorage?.getFusionAuthToken()
        currentUserId = currentUserId ?: secureStorage?.getUserId()
        currentEmail = currentEmail ?: secureStorage?.getUserEmail()
        currentUserCertificateChain = currentUserCertificateChain ?: secureStorage?.getCertificateChain()
        currentUserPublicKey = currentUserPublicKey ?: secureStorage?.getPublicKey()
        currentUserPrivateKey = currentUserPrivateKey ?: secureStorage?.getPrivateKey()
    }

    override fun storeContext() {
        initializeSecureStorage()

        currentToken?.let { secureStorage?.addCloudAuthToken(it) }
        currentRefreshToken?.let { secureStorage?.addCloudRefreshToken(it) }
        currentFusionToken?.let { secureStorage?.addFusionAuthToken(it) }
        currentUserId?.let { secureStorage?.addUserId(it) }
        currentEmail?.let { secureStorage?.addUserEmail(it) }
        currentUserCertificateChain?.let { secureStorage?.addCertificateChain(it) }
        currentUserPublicKey?.let { secureStorage?.addPublicKey(it) }
        currentUserPrivateKey?.let { secureStorage?.addPrivateKey(it) }
    }

    override fun clearContext() {
        initializeSecureStorage()

        secureStorage?.clear()
    }

    private fun initializeSecureStorage() {
        secureStorage = secureStorage ?: createSecureStorage(applicationContext)
    }
}