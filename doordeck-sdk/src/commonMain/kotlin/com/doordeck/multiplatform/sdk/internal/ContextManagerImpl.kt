package com.doordeck.multiplatform.sdk.internal

import com.doordeck.multiplatform.sdk.ApplicationContext
import com.doordeck.multiplatform.sdk.api.ContextManager
import com.doordeck.multiplatform.sdk.api.model.Context
import com.doordeck.multiplatform.sdk.api.model.Crypto
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.storage.SecureStorage
import com.doordeck.multiplatform.sdk.storage.createSecureStorage
import com.doordeck.multiplatform.sdk.util.JwtUtils.isJwtTokenAboutToExpire
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.fromJson

internal class ContextManagerImpl(
    private val applicationContext: ApplicationContext? = null,
    token: String? = null,
    refreshToken: String? = null
): ContextManager {

    private var currentToken: String? = token
    private var currentRefreshToken: String? = refreshToken
    private var currentFusionToken: String? = null
    private var currentUserId: String? = null
    private var currentUserCertificateChain: List<String>? = null
    private var currentUserPublicKey: ByteArray? = null
    private var currentUserPrivateKey: ByteArray? = null
    private var secureStorage: SecureStorage? = null

    override fun setAuthToken(token: String) {
        currentToken = token
    }

    override fun isAuthTokenAboutToExpire(): Boolean {
        return currentToken?.isJwtTokenAboutToExpire() ?: true
    }

    override fun setRefreshToken(token: String) {
        currentRefreshToken = token
    }

    override fun setFusionAuthToken(token: String) {
        currentFusionToken = token
    }

    override fun setUserId(userId: String) {
        currentUserId = userId
    }

    override fun setCertificateChain(certificateChain: List<String>) {
        currentUserCertificateChain = certificateChain
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

    internal fun setTokens(token: String, refreshToken: String) {
        currentToken = token
        currentRefreshToken = refreshToken
    }

    internal fun reset() {
        resetTokens()
        resetOperationContext()
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
        currentUserCertificateChain = operationContextData.userCertificateChain
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
        currentUserCertificateChain?.let { secureStorage?.addCertificateChain(it) }
        currentUserPublicKey?.let { secureStorage?.addPublicKey(it) }
        currentUserPrivateKey?.let { secureStorage?.addPrivateKey(it) }
    }

    override fun clearContext() {
        initializeSecureStorage()

        secureStorage?.clear()
    }

    internal fun getCertificateChain(): List<String>? {
        return currentUserCertificateChain
    }

    internal fun getUserId(): String? {
        return currentUserId
    }

    internal fun getAuthToken(): String? {
        return currentToken
    }

    internal fun getRefreshToken(): String? {
        return currentRefreshToken
    }

    internal fun getFusionAuthToken(): String? {
        return currentFusionToken
    }

    internal fun getPublicKey(): ByteArray? {
        return currentUserPublicKey
    }

    internal fun getPrivateKey(): ByteArray? {
        return currentUserPrivateKey
    }

    internal fun getKeyPair(): Crypto.KeyPair? {
        val actualUserPublicKey = currentUserPublicKey
        val actualUserPrivateKey = currentUserPrivateKey
        return if (actualUserPublicKey != null && actualUserPrivateKey != null) {
            Crypto.KeyPair(actualUserPrivateKey, actualUserPublicKey)
        } else null
    }

    private fun initializeSecureStorage() {
        secureStorage = secureStorage ?: createSecureStorage(applicationContext)
    }
}