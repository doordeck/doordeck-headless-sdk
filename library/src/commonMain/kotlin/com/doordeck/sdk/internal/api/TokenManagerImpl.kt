package com.doordeck.sdk.internal.api

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

    override fun setTokens(token: String, refreshToken: String) {
        currentToken = token
        currentRefreshToken = refreshToken
    }

    override fun resetTokens() {
        currentToken = null
        currentRefreshToken = null
    }
}