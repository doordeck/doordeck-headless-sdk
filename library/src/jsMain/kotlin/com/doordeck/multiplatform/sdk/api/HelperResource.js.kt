package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.internal.api.HelperResourceImpl
import io.ktor.client.HttpClient
import org.koin.core.qualifier.named
import org.koin.mp.KoinPlatform.getKoin
import kotlin.js.Promise

@JsExport
actual interface HelperResource {
    fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray): Promise<dynamic>
}

private val helper = HelperResourceImpl(
    httpClient = getKoin().get<HttpClient>(named("httpClient")),
    cloudHttpClient = getKoin().get<HttpClient>(named("cloudHttpClient"))
)

@JsExport
actual fun helper(): HelperResource = helper