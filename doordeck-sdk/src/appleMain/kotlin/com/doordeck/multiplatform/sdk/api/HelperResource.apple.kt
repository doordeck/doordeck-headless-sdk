package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.internal.api.HelperResourceImpl
import io.ktor.client.HttpClient
import org.koin.core.qualifier.named
import org.koin.mp.KoinPlatform.getKoin

actual interface HelperResource {
    @Throws(Exception::class)
    suspend fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray)
}

actual fun helper(): HelperResource = HelperResourceImpl(
    httpClient = getKoin().get<HttpClient>(named("httpClient")),
    cloudHttpClient = getKoin().get<HttpClient>(named("cloudHttpClient"))
)