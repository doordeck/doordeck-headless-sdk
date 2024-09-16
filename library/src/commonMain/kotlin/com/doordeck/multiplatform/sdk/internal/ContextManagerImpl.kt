package com.doordeck.multiplatform.sdk.internal

import com.doordeck.multiplatform.sdk.MissingOperationContextException
import com.doordeck.multiplatform.sdk.api.ContextManager
import com.doordeck.multiplatform.sdk.api.model.Context

class ContextManagerImpl(
    token: String? = null,
    refreshToken: String? = null
): ContextManager {

    var currentToken: String? = token
    var currentRefreshToken: String? = refreshToken
    var currentFusionToken: String? = null

    private var currentUserId: String? = null
    private var currentUserCertificateChain: List<String>? = null
    private var currentUserPrivateKey: ByteArray? = null

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

    internal fun resetTokens() {
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

    override fun setFusionAuthToken(token: String) {
        currentFusionToken = token
    }

    fun getOperationContext(): Context.OperationContext {
        val actualUserId = currentUserId
        val actualUserCertificateChain = currentUserCertificateChain
        val actualUserPrivateKey = currentUserPrivateKey
        if (actualUserId == null || actualUserCertificateChain == null || actualUserPrivateKey == null) {
            throw MissingOperationContextException("The operation context is missing")
        }
        return Context.OperationContext(
            userId = actualUserId,
            userCertificateChain = actualUserCertificateChain,
            userPrivateKey = actualUserPrivateKey
        )
    }
}