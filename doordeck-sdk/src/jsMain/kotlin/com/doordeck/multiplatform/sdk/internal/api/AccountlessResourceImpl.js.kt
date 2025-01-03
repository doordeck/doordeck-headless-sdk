package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.AccountlessResource
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.util.promise
import kotlin.js.Promise

internal object AccountlessResourceImpl : AccountlessResource {

    override fun login(email: String, password: String): Promise<TokenResponse> {
        return promise { AccountlessClient.loginRequest(email, password) }
    }

    override fun registration(email: String, password: String, displayName: String?, force: Boolean, publicKey: ByteArray?): Promise<TokenResponse> {
        return promise { AccountlessClient.registrationRequest(email, password, displayName, force, publicKey) }
    }

    override fun verifyEmail(code: String): Promise<Unit> {
        return promise { AccountlessClient.verifyEmailRequest(code) }
    }

    override fun passwordReset(email: String): Promise<dynamic> {
        return promise { AccountlessClient.passwordResetRequest(email) }
    }

    override fun passwordResetVerify(userId: String, token: String, password: String): Promise<dynamic> {
        return promise { AccountlessClient.passwordResetVerifyRequest(userId, token, password) }
    }
}