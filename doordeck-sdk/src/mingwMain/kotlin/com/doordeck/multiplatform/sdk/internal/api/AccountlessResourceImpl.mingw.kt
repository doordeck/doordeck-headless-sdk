package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.AccountlessResource
import com.doordeck.multiplatform.sdk.api.model.LoginData
import com.doordeck.multiplatform.sdk.api.model.PasswordResetData
import com.doordeck.multiplatform.sdk.api.model.PasswordResetVerifyData
import com.doordeck.multiplatform.sdk.api.model.RegistrationData
import com.doordeck.multiplatform.sdk.api.model.VerifyEmailData
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.resultData
import kotlinx.coroutines.runBlocking

internal object AccountlessResourceImpl : AccountlessResource {

    override fun login(email: String, password: String): TokenResponse {
        return runBlocking { AccountlessClient.loginRequest(email, password) }
    }

    override fun loginJson(data: String): String {
        return resultData {
            val loginData = data.fromJson<LoginData>()
            login(loginData.email, loginData.password)
        }
    }

    override fun registration(email: String, password: String, displayName: String?, force: Boolean, publicKey: ByteArray?): TokenResponse {
        return runBlocking { AccountlessClient.registrationRequest(email, password, displayName, force, publicKey) }
    }

    override fun registrationJson(data: String): String {
        return resultData {
            val registrationData = data.fromJson<RegistrationData>()
            registration(registrationData.email, registrationData.password, registrationData.displayName, registrationData.force, registrationData.publicKey?.decodeBase64ToByteArray())
        }
    }

    override fun verifyEmail(code: String) {
        return runBlocking { AccountlessClient.verifyEmailRequest(code) }
    }

    override fun verifyEmailJson(data: String): String {
        return resultData {
            val verifyEmailData = data.fromJson<VerifyEmailData>()
            verifyEmail(verifyEmailData.code)
        }
    }

    override fun passwordReset(email: String) {
        return runBlocking { AccountlessClient.passwordResetRequest(email) }
    }

    override fun passwordResetJson(data: String): String {
        return resultData {
            val passwordResetData = data.fromJson<PasswordResetData>()
            passwordReset(passwordResetData.email)
        }
    }

    override fun passwordResetVerify(userId: String, token: String, password: String) {
        return runBlocking { AccountlessClient.passwordResetVerifyRequest(userId, token, password) }
    }

    override fun passwordResetVerifyJson(data: String): String {
        return resultData {
            val passwordResetVerifyData = data.fromJson<PasswordResetVerifyData>()
            passwordResetVerify(passwordResetVerifyData.userId, passwordResetVerifyData.token, passwordResetVerifyData.password)
        }
    }
}