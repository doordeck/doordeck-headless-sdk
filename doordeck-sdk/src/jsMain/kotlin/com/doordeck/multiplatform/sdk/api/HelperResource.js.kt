package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.RegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.internal.api.HelperClient
import com.doordeck.multiplatform.sdk.internal.api.HelperResourceImpl
import org.koin.mp.KoinPlatform.getKoin
import kotlin.js.Promise

@JsExport
actual interface HelperResource {
    fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray): Promise<dynamic>

    fun assistedLogin(email: String, password: String): Promise<AssistedLoginResponse>

    fun completeAssistedLogin(code: String): Promise<RegisterEphemeralKeyResponse>
}

private val helper = HelperResourceImpl(getKoin().get<HelperClient>())

@JsExport
actual fun helper(): HelperResource = helper