package com.doordeck.sdk.internal

import com.doordeck.sdk.MissingOperationContextException
import com.doordeck.sdk.api.ContextManager
import com.doordeck.sdk.api.model.Context

class ContextManagerImpl(
    token: String? = null,
    refreshToken: String? = null
): ContextManager {

    var currentToken: String? = token
    var currentRefreshToken: String? = refreshToken

    private var currentUserId: String? = null
    private var currentUserCertificateChain: Array<String>? = null
    private var currentUserPrivateKey: ByteArray? = null

    override fun setAuthToken(token: String) {
        currentToken = token
    }

    internal fun setTokens(token: String, refreshToken: String) {
        currentToken = token
        currentRefreshToken = refreshToken
    }

    internal fun reset() {
        currentToken = null
        currentRefreshToken = null
        currentUserId = null
        currentUserCertificateChain = null
        currentUserPrivateKey = null
    }

    override fun setOperationContext(userId: String, certificateChain: Array<String>, privateKey: ByteArray) {
        currentUserId = userId
        currentUserCertificateChain = certificateChain
        currentUserPrivateKey = privateKey
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