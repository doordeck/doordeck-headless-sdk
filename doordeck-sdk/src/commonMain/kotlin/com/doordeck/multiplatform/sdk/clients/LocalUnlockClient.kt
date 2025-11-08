package com.doordeck.multiplatform.sdk.clients

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.HttpClient
import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.util.addRequestHeaders
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.utils.io.CancellationException
import kotlinx.coroutines.async
import kotlinx.coroutines.selects.select
import kotlinx.coroutines.supervisorScope
import kotlin.jvm.JvmSynthetic

/**
 * Internal implementation of the local unlock API client.
 * Handles all requests related to local unlock.
 */
internal object LocalUnlockClient {

    /**
     * Sends the unlock requests to the cloud API and all the local endpoints simultaneously,
     * completing as soon as any request succeeds.
     * When all other requests fail, it throws the exception from the cloud endpoint request,
     * which was supposed to succeed under all circumstances.
     *
     * @param cloudEndpoint The cloud endpoint for the unlock request.
     * @param directAccessEndpoints The list of local IP addresses to which unlock requests should be sent.
     * @param body The unlock request as base64.
     */
    @JvmSynthetic
    internal suspend fun unlock(
        cloudEndpoint: String,
        directAccessEndpoints: List<String>,
        body: String
    ) = supervisorScope {
        val endpoints = directAccessEndpoints + cloudEndpoint
        val requests = endpoints.associateWith {
            async {
                try {
                    /**
                     * We will use the [CloudHttpClient] to send requests to the cloud endpoint and the [HttpClient]
                     * for the local endpoint. This separation is necessary because the configuration of the
                     * CloudHttpClient—with features like certificate pinning and auth interceptors—could
                     * cause requests to the local endpoint to fail
                     */
                    val client = if (it == cloudEndpoint) CloudHttpClient.client else HttpClient.client
                    val response = client.post(it) {
                        addRequestHeaders(true)
                        setBody(body)
                    }
                    Result.success(response)
                } catch (exception: CancellationException) {
                    throw exception
                } catch (exception: Throwable) {
                    Result.failure(exception)
                }
            }
        }.toMutableMap()

        var cloudException: Throwable? = null
        try {
            while (requests.isNotEmpty()) {
                val (endpoint, result) = select {
                    requests.forEach { (endpoint, request) ->
                        request.onAwait { endpoint to it }
                    }
                }

                requests.remove(endpoint)

                if (result.isSuccess) {
                    requests.values.forEach { it.cancel() }
                    return@supervisorScope result.getOrThrow()
                } else if (endpoint == cloudEndpoint) {
                    cloudException = result.exceptionOrNull()
                }
            }

            throw (cloudException
                ?: SdkException("All unlock requests failed"))
        } finally {
            requests.values.forEach { if (!it.isCompleted) it.cancel() }
        }
    }
}