package com.doordeck.multiplatform.sdk.internal

import com.doordeck.multiplatform.sdk.ApplicationContext
import com.doordeck.multiplatform.sdk.MissingOperationContextException
import com.doordeck.multiplatform.sdk.api.ContextManager
import com.doordeck.multiplatform.sdk.api.model.Context
import com.doordeck.multiplatform.sdk.storage.SecureStorage
import com.doordeck.multiplatform.sdk.storage.createSecureStorage
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.fromJson

internal class ContextManagerImpl(
    private val applicationContext: ApplicationContext? = null,
    token: String? = null,
    refreshToken: String? = null
): ContextManager {

    var currentToken: String? = token
    var currentRefreshToken: String? = refreshToken // Probably to be removed
    var currentFusionToken: String? = null

    private var currentUserId: String? = null
    private var currentUserCertificateChain: List<String>? = null
    private var currentUserPrivateKey: ByteArray? = null

    private var secureStorage: SecureStorage? = null

    override fun setAuthToken(token: String) {
        currentToken = token
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
        currentUserPrivateKey = null
    }

    override fun setOperationContext(userId: String, certificateChain: List<String>, privateKey: ByteArray) {
        currentUserId = userId
        currentUserCertificateChain = certificateChain
        currentUserPrivateKey = privateKey
    }

    override fun setOperationContextJson(data: String) {
        val operationContextData = data.fromJson<Context.OperationContextData>()
        currentUserId = operationContextData.userId
        currentUserCertificateChain = operationContextData.userCertificateChain
        currentUserPrivateKey = operationContextData.userPrivateKey.decodeBase64ToByteArray()
    }

    override fun setFusionAuthToken(token: String) {
        currentFusionToken = token
    }

    override fun setSecureStorageImpl(secureStorage: SecureStorage) {
        this.secureStorage = secureStorage
    }

    override fun loadContext() {
        initializeSecureStorage()

        currentToken =  currentToken ?: secureStorage?.getCloudAuthToken()
        currentFusionToken = currentFusionToken ?: secureStorage?.getFusionAuthToken()
        currentUserId = currentUserId ?: secureStorage?.getUserId()
        currentUserCertificateChain = currentUserCertificateChain ?: secureStorage?.getCertificateChain()
        currentUserPrivateKey = currentUserPrivateKey ?: secureStorage?.getPrivateKey()
    }

    override fun storeContext() {
        initializeSecureStorage()

        currentToken?.let { secureStorage?.addCloudAuthToken(it) }
        currentFusionToken?.let { secureStorage?.addFusionAuthToken(it) }
        currentUserId?.let { secureStorage?.addUserId(it) }
        currentUserCertificateChain?.let { secureStorage?.addCertificateChain(it) }
        currentUserPrivateKey?.let { secureStorage?.addPrivateKey(it) }
    }

    override fun clearContext() {
        initializeSecureStorage()

        secureStorage?.clear()
    }

    private fun initializeSecureStorage() {
        secureStorage = secureStorage ?: createSecureStorage(applicationContext)
    }

    internal fun getOperationContext(): Context.OperationContext {
        val actualUserId = currentUserId
        val actualUserCertificateChain = currentUserCertificateChain
        val actualUserPrivateKey = currentUserPrivateKey
        if (actualUserId == null || actualUserCertificateChain == null || actualUserPrivateKey == null) {
            throw MissingOperationContextException("Operation context is missing")
        }
        return Context.OperationContext(
            userId = actualUserId,
            userCertificateChain = actualUserCertificateChain,
            userPrivateKey = actualUserPrivateKey
        )
    }
}