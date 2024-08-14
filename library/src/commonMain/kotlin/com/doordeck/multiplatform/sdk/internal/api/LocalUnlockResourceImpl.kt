package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.LocalUnlockResource
import com.doordeck.multiplatform.sdk.util.addRequestHeaders
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.*
import kotlinx.coroutines.selects.select

class LocalUnlockResourceImpl(
    private val httpClient: HttpClient
) : AbstractResourceImpl(), LocalUnlockResource {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    override fun unlock(directAccessEndpoints: Array<String>, request: String) {
        // Launch the request at the direct access endpoints
        val requests = directAccessEndpoints.map {
            coroutineScope.async {
                try {
                    httpClient.post<Unit>(it) {
                        addRequestHeaders(true)
                        setBody(request)
                    }
                } catch (exception: Exception) {
                    null
                }
            }
        }
        coroutineScope.launch {
            // Select the first successful response
            select {
                requests.forEach { request ->
                    request.onAwait { it }
                }
            }
            // Cancel the remaining requests
            requests.forEach { it.cancel() }
        }
    }
}