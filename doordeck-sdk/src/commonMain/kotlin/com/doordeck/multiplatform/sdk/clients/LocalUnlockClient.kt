package com.doordeck.multiplatform.sdk.clients

import com.doordeck.multiplatform.sdk.HttpClient
import com.doordeck.multiplatform.sdk.util.addRequestHeaders
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.selects.select

internal object LocalUnlockClient {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    fun unlock(directAccessEndpoints: List<String>, request: String) {
        // Launch the request at the direct access endpoints
        val requests = directAccessEndpoints.map {
            coroutineScope.async {
                try {
                    HttpClient.client.post(it) {
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