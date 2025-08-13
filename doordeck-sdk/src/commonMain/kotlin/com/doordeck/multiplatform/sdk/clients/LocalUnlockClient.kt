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
import kotlin.jvm.JvmSynthetic

/**
 * Internal implementation of the local unlock API client.
 * Handles all  requests related to local unlock.
 */
internal object LocalUnlockClient {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    /**
     * Attempts to send unlock requests to all local endpoints concurrently, completing as soon as any request succeeds.
     *
     * @param directAccessEndpoints The list of local IP addresses to which unlock requests should be sent.
     * @param request The unlock request as base64.
     */
    @JvmSynthetic
    internal fun unlock(directAccessEndpoints: List<String>, request: String) {
        // Launch the request at the direct access endpoints
        val requests = directAccessEndpoints.map {
            coroutineScope.async {
                try {
                    HttpClient.client.post(it) {
                        addRequestHeaders(true)
                        setBody(request)
                    }
                } catch (_: Exception) {
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