package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.HelperResource
import com.doordeck.multiplatform.sdk.api.responses.AssistedLoginResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.js.Promise

internal object HelperResourceImpl : HelperResource {

    override fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray): Promise<Unit> {
        return GlobalScope.promise { HelperClient.uploadPlatformLogoRequest(applicationId, contentType, image) }
    }

    override fun assistedLogin(email: String, password: String): Promise<AssistedLoginResponse> {
        return GlobalScope.promise { HelperClient.assistedLoginRequest(email, password) }
    }
}