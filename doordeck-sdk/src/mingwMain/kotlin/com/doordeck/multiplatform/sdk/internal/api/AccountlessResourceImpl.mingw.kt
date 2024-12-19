package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.AccountlessResource
import com.doordeck.multiplatform.sdk.api.model.LoginData
import com.doordeck.multiplatform.sdk.api.model.RegistrationData
import com.doordeck.multiplatform.sdk.api.model.VerifyEmailData
import com.doordeck.multiplatform.sdk.api.responses.TokenResponse
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.runBlocking

internal class AccountlessResourceImpl(
    private val accountlessClient: AccountlessClient
) : AccountlessResource {

    override fun login(email: String, password: String): TokenResponse {
        return runBlocking { accountlessClient.loginRequest(email, password) }
    }

    override fun loginJson(data: String): String {
        val loginData = data.fromJson<LoginData>()
        return login(loginData.email, loginData.password).toJson()
    }

    override fun registration(email: String, password: String, displayName: String?, force: Boolean, publicKey: ByteArray?): TokenResponse {
        return runBlocking { accountlessClient.registrationRequest(email, password, displayName, force, publicKey) }
    }

    override fun registrationJson(data: String): String {
        val registrationData = data.fromJson<RegistrationData>()
        return registration(registrationData.email, registrationData.password, registrationData.displayName, registrationData.force, registrationData.publicKey?.decodeBase64ToByteArray()).toJson()
    }

    override fun verifyEmail(code: String) {
        return runBlocking { accountlessClient.verifyEmailRequest(code) }
    }

    override fun verifyEmailJson(data: String) {
        val verifyEmailData = data.fromJson<VerifyEmailData>()
        return verifyEmail(verifyEmailData.code)
    }
}