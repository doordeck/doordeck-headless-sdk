package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.BadRequestException
import com.doordeck.multiplatform.sdk.ConflictException
import com.doordeck.multiplatform.sdk.ForbiddenException
import com.doordeck.multiplatform.sdk.GatewayTimeoutException
import com.doordeck.multiplatform.sdk.GoneException
import com.doordeck.multiplatform.sdk.InternalServerErrorException
import com.doordeck.multiplatform.sdk.LockedException
import com.doordeck.multiplatform.sdk.MethodNotAllowedException
import com.doordeck.multiplatform.sdk.NotAcceptableException
import com.doordeck.multiplatform.sdk.NotFoundException
import com.doordeck.multiplatform.sdk.SdkException
import com.doordeck.multiplatform.sdk.ServiceUnavailableException
import com.doordeck.multiplatform.sdk.TooEarlyException
import com.doordeck.multiplatform.sdk.TooManyRequestsException
import com.doordeck.multiplatform.sdk.UnauthorizedException
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import io.ktor.serialization.ContentConvertException
import kotlinx.serialization.Serializable

internal abstract class AbstractResourceImpl {

    protected suspend inline fun <reified T>HttpClient.post(
        urlString: String,
        crossinline block: HttpRequestBuilder.() -> Unit = {}
    ): T = handleRequest {
        post { url(urlString); block() }
    }

    protected suspend inline fun <reified T>HttpClient.get(
        urlString: String,
        crossinline block: HttpRequestBuilder.() -> Unit = {}
    ): T = handleRequest {
        get { url(urlString); block() }
    }

    protected suspend inline fun <reified T>HttpClient.put(
        urlString: String,
        crossinline block: HttpRequestBuilder.() -> Unit = {}
    ): T = handleRequest {
        put { url(urlString); block() }
    }

    protected suspend inline fun <reified T>HttpClient.delete(
        urlString: String,
        crossinline block: HttpRequestBuilder.() -> Unit = {}
    ): T = handleRequest {
        delete { url(urlString); block() }
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
        val responseError = try {
            body<ResponseError>()
        } catch (exception: Exception) {
            null
        }
        val message = "API call failed with: ${if (responseError?.message != null) responseError.message else "${status.value} (${status.description}) - ${bodyAsText()}"}"
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

    @Serializable
    private class ResponseError(
        val code: Int? = null,
        val message: String? = null
    )
}