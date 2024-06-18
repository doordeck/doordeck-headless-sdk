package com.doordeck.sdk.internal.api

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
    ): Unit = runBlocking {
        post { url(urlString); block() }
        Unit
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
    ): Unit = runBlocking {
        put { url(urlString); block() }
        Unit
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
    ): Unit = runBlocking {
        delete { url(urlString); block() }
        Unit
    }
}