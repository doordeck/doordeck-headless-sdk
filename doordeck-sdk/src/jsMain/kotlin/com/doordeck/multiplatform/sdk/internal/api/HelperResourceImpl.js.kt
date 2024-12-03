package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.HelperResource
import com.doordeck.multiplatform.sdk.api.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.js.Promise

internal class HelperResourceImpl(
    private val helperClient: HelperClient
) : HelperResource {

    override fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray): Promise<Unit> {
        return GlobalScope.promise { helperClient.uploadPlatformLogoRequest(applicationId, contentType, image) }
    }

    override fun assistedLogin(email: String, password: String): Promise<AssistedLoginResponse> {
        return GlobalScope.promise { helperClient.assistedLoginRequest(email, password) }
    }

    override fun completeAssistedLogin(code: String): Promise<RegisterEphemeralKeyResponse> {
        return GlobalScope.promise { helperClient.completeAssistedLoginRequest(code) }
    }
}