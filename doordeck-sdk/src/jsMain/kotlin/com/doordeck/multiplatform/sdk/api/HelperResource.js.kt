package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.responses.AssistedLoginResponse
import com.doordeck.multiplatform.sdk.api.responses.AssistedRegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.internal.api.HelperClient
import com.doordeck.multiplatform.sdk.internal.api.HelperResourceImpl
import org.koin.mp.KoinPlatform.getKoin
import kotlin.js.Promise

@JsExport
actual interface HelperResource {
    fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray): Promise<dynamic>

    fun assistedLogin(email: String, password: String): Promise<AssistedLoginResponse>

    fun assistedRegisterEphemeralKey(publicKey: ByteArray? = null): Promise<AssistedRegisterEphemeralKeyResponse>

    fun assistedRegister(email: String, password: String, displayName: String? = null, force: Boolean = false): Promise<dynamic>
}

private val helper = HelperResourceImpl(getKoin().get<HelperClient>())

@JsExport
actual fun helper(): HelperResource = helper