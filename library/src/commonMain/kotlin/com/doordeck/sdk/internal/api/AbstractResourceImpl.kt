package com.doordeck.sdk.internal.api

import com.doordeck.sdk.api.responses.EmptyResponse
import com.doordeck.sdk.runBlocking
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

abstract class AbstractResourceImpl {

    protected inline fun <reified T>HttpClient.post(
        urlString: String,
        crossinline block: HttpRequestBuilder.() -> Unit = {}
    ): T = runBlocking {
        post { url(urlString); block() }.body()
    }

    protected inline fun HttpClient.postEmpty(
        urlString: String,
        crossinline block: HttpRequestBuilder.() -> Unit = {}
    ): EmptyResponse = runBlocking {
        post { url(urlString); block() }
        EmptyResponse()
    }

    protected inline fun <reified T>HttpClient.get(
        urlString: String,
        crossinline block: HttpRequestBuilder.() -> Unit = {}
    ): T = runBlocking {
        get { url(urlString); block() }.body()
    }

    protected inline fun <reified T>HttpClient.put(
        urlString: String,
        crossinline block: HttpRequestBuilder.() -> Unit = {}
    ): T = runBlocking {
        put { url(urlString); block() }.body()
    }

    protected inline fun HttpClient.putEmpty(
        urlString: String,
        crossinline block: HttpRequestBuilder.() -> Unit = {}
    ): EmptyResponse = runBlocking {
        put { url(urlString); block() }
        EmptyResponse()
    }

    protected inline fun <reified T>HttpClient.delete(
        urlString: String,
        crossinline block: HttpRequestBuilder.() -> Unit = {}
    ): T = runBlocking {
        delete { url(urlString); block() }.body()
    }

    protected inline fun HttpClient.deleteEmpty(
        urlString: String,
        crossinline block: HttpRequestBuilder.() -> Unit = {}
    ): EmptyResponse = runBlocking {
        delete { url(urlString); block() }
        EmptyResponse()
    }
}