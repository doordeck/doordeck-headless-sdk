package com.doordeck.sdk.internal.api

import com.doordeck.sdk.BadRequestException
import com.doordeck.sdk.ConflictException
import com.doordeck.sdk.ForbiddenException
import com.doordeck.sdk.GatewayTimeoutException
import com.doordeck.sdk.GoneException
import com.doordeck.sdk.InternalServerErrorException
import com.doordeck.sdk.LockedException
import com.doordeck.sdk.MethodNotAllowedException
import com.doordeck.sdk.NotAcceptableException
import com.doordeck.sdk.NotFoundException
import com.doordeck.sdk.SdkException
import com.doordeck.sdk.ServiceUnavailableException
import com.doordeck.sdk.TooManyRequestsException
import com.doordeck.sdk.TooEarlyException
import com.doordeck.sdk.UnauthorizedException
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
                response.handleResponseFailure()
            }
            return if (T::class == Unit::class) {
                Unit as T
            } else {
                response.body<T>()
            }
        } catch (exception: ContentConvertException) {
            throw SdkException("Failed to deserialize API response", exception)
        } catch (exception: SdkException) {
            throw exception
        } catch (exception: Exception) {
            throw SdkException("Failed to perform API call", exception)
        }
    }

    /**
     * Handle the error codes
     *
     * @see <a href="https://developer.doordeck.com/docs/#errors">API Doc</a>
     */
    protected suspend fun HttpResponse.handleResponseFailure() {
        val message = "API call failed with: ${status.value} (${status.description}): ${bodyAsText()}"
        throw when(status) {
            HttpStatusCode.BadRequest -> BadRequestException(message)
            HttpStatusCode.Unauthorized -> UnauthorizedException(message)
            HttpStatusCode.Forbidden -> ForbiddenException(message)
            HttpStatusCode.NotFound -> NotFoundException(message)
            HttpStatusCode.MethodNotAllowed -> MethodNotAllowedException(message)
            HttpStatusCode.NotAcceptable -> NotAcceptableException(message)
            HttpStatusCode.Conflict -> ConflictException(message)
            HttpStatusCode.Gone -> GoneException(message)
            HttpStatusCode.Locked -> LockedException(message)
            HttpStatusCode.TooEarly -> TooEarlyException(message)
            HttpStatusCode.TooManyRequests -> TooManyRequestsException(message)
            HttpStatusCode.InternalServerError -> InternalServerErrorException(message)
            HttpStatusCode.ServiceUnavailable -> ServiceUnavailableException(message)
            HttpStatusCode.GatewayTimeout -> GatewayTimeoutException(message)
            else -> SdkException(message)
        }
    }
}