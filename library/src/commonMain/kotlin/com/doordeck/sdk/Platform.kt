package com.doordeck.sdk

import com.doordeck.sdk.api.model.ApiEnvironment
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json

val JSON = Json {
    ignoreUnknownKeys = true
    isLenient = true
}

fun createHttpClient(
    apiEnvironment: ApiEnvironment,
    token: String
): HttpClient {
    return HttpClient {
        install(ContentNegotiation) {
            json(JSON)
        }
        defaultRequest {
            url {
                host = apiEnvironment.host
                protocol = URLProtocol.HTTPS
            }
            // TODO Build the token-refresh around ktor?
            headers.append(HttpHeaders.Authorization, "Bearer $token")
        }
    }
}

expect fun <T> runBlocking(block: suspend CoroutineScope.() -> T): T