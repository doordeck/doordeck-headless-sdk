package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.clients.AccountlessClient
import com.doordeck.multiplatform.sdk.model.responses.TokenResponse
import com.doordeck.multiplatform.sdk.model.responses.toTokenResponse
import com.doordeck.multiplatform.sdk.util.promise
import kotlin.js.Promise

/**
 * Platform-specific implementations of accountless-related API calls.
 */
@JsExport
actual object AccountlessApi {
    /**
     * @see AccountlessClient.loginRequest
     */
    fun login(email: String, password: String): Promise<TokenResponse> = promise {
        AccountlessClient
            .loginRequest(
                email = email,
                password = password
            )
            .toTokenResponse()
    }

    /**
     * @see AccountlessClient.registrationRequest
     */
    fun registration(
        email: String,
        password: String,
        displayName: String? = null,
        force: Boolean = false,
        publicKey: ByteArray? = null
    ): Promise<TokenResponse> = promise {
        AccountlessClient
            .registrationRequest(
                email = email,
                password = password,
                displayName = displayName,
                force = force,
                publicKey = publicKey
            )
            .toTokenResponse()
    }

    /**
     * @see AccountlessClient.verifyEmailRequest
     */
    fun verifyEmail(code: String): Promise<dynamic> = promise {
        AccountlessClient.verifyEmailRequest(code)
    }

    /**
     * @see AccountlessClient.passwordResetRequest
     */
    fun passwordReset(email: String): Promise<dynamic> = promise {
        AccountlessClient.passwordResetRequest(email)
    }

    /**
     * @see AccountlessClient.passwordResetRequest
     */
    fun passwordResetVerify(userId: String, token: String, password: String): Promise<dynamic> = promise {
        AccountlessClient.passwordResetVerifyRequest(
            userId = userId,
            token = token,
            password = password
        )
    }
}

private val accountless = AccountlessApi

/**
 * Defines the platform-specific implementation of [AccountlessApi]
 */
@JsExport
actual fun accountless(): AccountlessApi = accountless