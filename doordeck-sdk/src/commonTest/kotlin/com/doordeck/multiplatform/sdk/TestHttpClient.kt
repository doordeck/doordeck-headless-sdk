package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.util.toJson
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.request.HttpResponseData
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.charsets.Charsets
import io.ktor.utils.io.core.toByteArray

internal val TEST_FUSION_CLIENT = createFusionHttpClient()
internal val TEST_CLOUD_CLIENT = createCloudHttpClient()
internal val TEST_HTTP_CLIENT = createHttpClient()

internal inline fun <reified T> MockRequestHandleScope.respondContent(content: T): HttpResponseData = respond(
    content = ByteReadChannel(content.toJson().toByteArray(Charsets.UTF_8)),
    status = HttpStatusCode.OK,
    headers = headersOf(HttpHeaders.ContentType, "application/json")
)