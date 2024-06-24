package com.doordeck.sdk.internal.api

import com.doordeck.sdk.SdkException
import com.doordeck.sdk.runBlocking
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.*

abstract class AbstractResourceImpl {

    protected inline fun <reified T>HttpClient.post(
        urlString: String,
        crossinline block: HttpRequestBuilder.() -> Unit = {}
    ): T = runBlocking {
        handleRequest {
            post { url(urlString); block() }
        }
    }

    protected inline fun HttpClient.postEmpty(
        urlString: String,
        crossinline block: HttpRequestBuilder.() -> Unit = {}
    ): Unit = runBlocking {
        handleRequest {
            post { url(urlString); block() }
        }
    }

    protected inline fun <reified T>HttpClient.get(
        urlString: String,
        crossinline block: HttpRequestBuilder.() -> Unit = {}
    ): T = runBlocking {
        handleRequest {
            get { url(urlString); block() }
        }
    }

    protected inline fun <reified T>HttpClient.put(
        urlString: String,
        crossinline block: HttpRequestBuilder.() -> Unit = {}
    ): T = runBlocking {
        handleRequest {
            put { url(urlString); block() }
        }
    }

    protected inline fun HttpClient.putEmpty(
        urlString: String,
        crossinline block: HttpRequestBuilder.() -> Unit = {}
    ): Unit = runBlocking {
        handleRequest {
            put { url(urlString); block() }
        }
    }

    protected inline fun <reified T>HttpClient.delete(
        urlString: String,
        crossinline block: HttpRequestBuilder.() -> Unit = {}
    ): T = runBlocking {
        handleRequest {
            delete { url(urlString); block() }
        }
    }

    protected inline fun HttpClient.deleteEmpty(
        urlString: String,
        crossinline block: HttpRequestBuilder.() -> Unit = {}
    ): Unit = runBlocking {
        handleRequest {
            delete { url(urlString); block() }
        }
    }

    protected suspend inline fun <reified T>handleRequest(function: () -> HttpResponse): T {
        try {
            val response = function()
            if (!response.status.isSuccess()) {
                throw SdkException("API call failed with: ${response.status.value} (${response.status.description}): ${response.bodyAsText()}")
            }
            return if (T::class == Unit::class) {
                Unit as T
            } else {
                response.body<T>()
            }
        } catch(exception: ContentConvertException) {
            throw SdkException("Failed to deserialize API response", exception)
        } catch (exception: Exception) {
            if (exception is SdkException) {
                throw exception
            }
            throw SdkException("Failed to perform API call", exception)
        }
    }
}