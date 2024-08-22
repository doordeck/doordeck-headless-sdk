package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.internal.api.HelperResourceImpl
import io.ktor.client.HttpClient
import org.koin.core.qualifier.named
import org.koin.mp.KoinPlatform.getKoin
import java.util.concurrent.CompletableFuture

actual interface HelperResource {
    suspend fun uploadPlatformLogo(applicationId: String, contentType: String, image: ByteArray)
    fun uploadPlatformLogoFuture(applicationId: String, contentType: String, image: ByteArray): CompletableFuture<Unit>
}

actual fun helper(): HelperResource = HelperResourceImpl(
    httpClient = getKoin().get<HttpClient>(named("httpClient")),
    cloudHttpClient = getKoin().get<HttpClient>(named("cloudHttpClient"))
)