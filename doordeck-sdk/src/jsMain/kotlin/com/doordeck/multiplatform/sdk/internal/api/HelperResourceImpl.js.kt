package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.HelperResource
import com.doordeck.multiplatform.sdk.api.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.AssistedRegisterEphemeralKeyResponse
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

    override fun assistedRegisterEphemeralKey(publicKey: ByteArray?): Promise<AssistedRegisterEphemeralKeyResponse> {
        return GlobalScope.promise { HelperClient.assistedRegisterEphemeralKeyRequest(publicKey) }
    }

    override fun assistedRegister(email: String, password: String, displayName: String?, force: Boolean): Promise<dynamic> {
        return GlobalScope.promise { HelperClient.assistedRegisterRequest(email, password, displayName, force) }
    }
}