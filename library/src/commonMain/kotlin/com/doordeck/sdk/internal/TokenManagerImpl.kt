package com.doordeck.sdk.internal

import com.doordeck.sdk.api.TokenManager

class TokenManagerImpl(
    token: String? = null,
    refreshToken: String? = null
): TokenManager {

    var currentToken: String? = token
    var currentRefreshToken: String? = refreshToken

    override fun setAuthToken(token: String) {
        currentToken = token
    }

    fun setTokens(token: String, refreshToken: String) {
        currentToken = token
        currentRefreshToken = refreshToken
    }

    fun resetTokens() {
        currentToken = null
        currentRefreshToken = null
    }
}