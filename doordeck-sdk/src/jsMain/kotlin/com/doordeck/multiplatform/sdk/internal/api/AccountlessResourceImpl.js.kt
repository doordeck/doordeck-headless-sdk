package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.AccountlessResource
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.js.Promise

internal object AccountlessResourceImpl : AccountlessResource {

    override fun login(email: String, password: String): Promise<TokenResponse> {
        return GlobalScope.promise { AccountlessClient.loginRequest(email, password) }
    }

    override fun registration(email: String, password: String, displayName: String?, force: Boolean, publicKey: ByteArray?): Promise<TokenResponse> {
        return GlobalScope.promise { AccountlessClient.registrationRequest(email, password, displayName, force, publicKey) }
    }

    override fun verifyEmail(code: String): Promise<Unit> {
        return GlobalScope.promise { AccountlessClient.verifyEmailRequest(code) }
    }

    override fun passwordReset(email: String): Promise<dynamic> {
        return GlobalScope.promise { AccountlessClient.passwordResetRequest(email) }
    }

    override fun passwordResetVerify(userId: String, token: String, password: String): Promise<dynamic> {
        return GlobalScope.promise { AccountlessClient.passwordResetVerifyRequest(userId, token, password) }
    }
}