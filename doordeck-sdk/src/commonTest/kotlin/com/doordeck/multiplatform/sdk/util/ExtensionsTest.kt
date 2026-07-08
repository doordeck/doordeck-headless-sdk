package com.doordeck.multiplatform.sdk.util

import com.doordeck.multiplatform.sdk.PlatformType
import com.doordeck.multiplatform.sdk.context.Context
import com.doordeck.multiplatform.sdk.exceptions.BadRequestException
import com.doordeck.multiplatform.sdk.exceptions.ConflictException
import com.doordeck.multiplatform.sdk.exceptions.ForbiddenException
import com.doordeck.multiplatform.sdk.exceptions.GatewayTimeoutException
import com.doordeck.multiplatform.sdk.exceptions.GoneException
import com.doordeck.multiplatform.sdk.exceptions.InternalServerErrorException
import com.doordeck.multiplatform.sdk.exceptions.LockedException
import com.doordeck.multiplatform.sdk.exceptions.MethodNotAllowedException
import com.doordeck.multiplatform.sdk.exceptions.NotAcceptableException
import com.doordeck.multiplatform.sdk.exceptions.NotFoundException
import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.exceptions.ServiceUnavailableException
import com.doordeck.multiplatform.sdk.exceptions.TooEarlyException
import com.doordeck.multiplatform.sdk.exceptions.TooManyRequestsException
import com.doordeck.multiplatform.sdk.exceptions.UnauthorizedException
import com.doordeck.multiplatform.sdk.exceptions.UnprocessableEntityException
import com.doordeck.multiplatform.sdk.model.data.BasicAmagController
import com.doordeck.multiplatform.sdk.model.data.BasicDataSource
import com.doordeck.multiplatform.sdk.model.data.BasicPacController
import com.doordeck.multiplatform.sdk.model.network.ApiVersion
import com.doordeck.multiplatform.sdk.model.network.Paths
import com.doordeck.multiplatform.sdk.model.requests.LoginRequest
import com.doordeck.multiplatform.sdk.platformType
import com.doordeck.multiplatform.sdk.randomDouble
import com.doordeck.multiplatform.sdk.randomInt
import com.doordeck.multiplatform.sdk.randomNullable
import com.doordeck.multiplatform.sdk.randomString
import com.doordeck.multiplatform.sdk.randomUrlString
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.UserAgent
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.pluginOrNull
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.http.HttpStatusCode.Companion.Conflict
import io.ktor.http.HttpStatusCode.Companion.Forbidden
import io.ktor.http.HttpStatusCode.Companion.GatewayTimeout
import io.ktor.http.HttpStatusCode.Companion.Gone
import io.ktor.http.HttpStatusCode.Companion.InternalServerError
import io.ktor.http.HttpStatusCode.Companion.Locked
import io.ktor.http.HttpStatusCode.Companion.MethodNotAllowed
import io.ktor.http.HttpStatusCode.Companion.NotAcceptable
import io.ktor.http.HttpStatusCode.Companion.NotFound
import io.ktor.http.HttpStatusCode.Companion.ServiceUnavailable
import io.ktor.http.HttpStatusCode.Companion.TooEarly
import io.ktor.http.HttpStatusCode.Companion.TooManyRequests
import io.ktor.http.HttpStatusCode.Companion.Unauthorized
import io.ktor.http.HttpStatusCode.Companion.UnprocessableEntity
import io.ktor.http.auth.AuthScheme
import io.ktor.serialization.ContentConvertException
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.uuid.Uuid

class ExtensionsTest {

    @Test
    fun shouldSetSignedContentTypeHeader() = runTest {
        // Given
        val httpRequestBuilder = HttpRequestBuilder()

        // When
        httpRequestBuilder.apply {
            addRequestHeaders(true)
        }

        // Then
        assertEquals("application/jwt", httpRequestBuilder.headers[HttpHeaders.ContentType])
    }

    @Test
    fun shouldSetNotSignedContentTypeHeader() = runTest {
        // Given
        val httpRequestBuilder = HttpRequestBuilder()

        // When
        httpRequestBuilder.apply {
            addRequestHeaders(false)
        }

        // Then
        assertEquals(ContentType.Application.Json.toString(), httpRequestBuilder.headers[HttpHeaders.ContentType])
    }

    @Test
    fun shouldSetApiVersionAcceptHeader() = runTest {
        // Given
        val httpRequestBuilder = HttpRequestBuilder()

        // When
        httpRequestBuilder.apply {
            addRequestHeaders(apiVersion = ApiVersion.VERSION_3)
        }

        // Then
        assertEquals(ApiVersion.VERSION_3.toHeaderValue(), httpRequestBuilder.headers[HttpHeaders.Accept])
    }

    @Test
    fun shouldSetTokenAuthHeader() = runTest {
        // Given
        val httpRequestBuilder = HttpRequestBuilder()
        val token = randomString()

        // When
        httpRequestBuilder.apply {
            addRequestHeaders(token = token)
        }

        // Then
        assertEquals("${AuthScheme.Bearer} $token", httpRequestBuilder.headers[HttpHeaders.Authorization])
    }

    @Test
    fun shouldInstallContentNegotiation() = runTest {
        // Given
        val httpClient = HttpClient {
            installContentNegotiation()
        }

        // Then
        assertNotNull(httpClient.pluginOrNull(ContentNegotiation))
    }

    @Test
    fun shouldInstallTimeout() = runTest {
        // Given
        val httpClient = HttpClient {
            installTimeout()
        }

        // Then
        assertNotNull(httpClient.pluginOrNull(HttpTimeout))
    }

    @Test
    fun shouldInstallDefaultRequest() = runTest {
        // Given
        val httpClient = HttpClient {
            installDefaultRequest(determineHost = {
                ""
            })
        }

        // Then
        assertNotNull(httpClient.pluginOrNull(DefaultRequest))
    }

    @Test
    fun shouldInstallUserAgent() = runTest {
        // Given
        val httpClient = HttpClient {
            installUserAgent()
        }

        // Then
        if (platformType == PlatformType.JS_BROWSER) {
            assertNull(httpClient.pluginOrNull(UserAgent))
        } else {
            assertNotNull(httpClient.pluginOrNull(UserAgent))
        }
    }

    @Test
    fun shouldInstallAuth() = runTest {
        // Given
        val httpClient = HttpClient {
            installAuth()
        }

        // Then
        assertNotNull(httpClient.pluginOrNull(Auth))
    }

    @Test
    fun shouldInstallInterceptor() = runTest {
        // Given
        val httpClient = HttpClient().also {
            it.addAuthInterceptor(
                requiresAuth = Paths::requiresAuth,
                getAuthToken = Context::getCloudAuthToken
            )
        }

        httpClient.use { client ->
            // Then
            assertNotNull(client.pluginOrNull(HttpSend))
        }
    }

    @Test
    fun shouldInstallResponseValidator() = runTest {
        // Given
        val status = listOf(BadRequest, Unauthorized, Forbidden, NotFound, MethodNotAllowed, NotAcceptable, Conflict,
            Gone, UnprocessableEntity, Locked, TooEarly, TooManyRequests, InternalServerError, ServiceUnavailable, GatewayTimeout
        )
        status.forEach { responseStatus ->
            val httpClient = HttpClient(MockEngine {
                respond(
                    content = "",
                    status = responseStatus
                )
            }) {
                installResponseValidator()
            }

            httpClient.use { client ->
                // When
                val type = when (responseStatus) {
                    BadRequest -> BadRequestException::class
                    Unauthorized -> UnauthorizedException::class
                    Forbidden -> ForbiddenException::class
                    NotFound -> NotFoundException::class
                    MethodNotAllowed -> MethodNotAllowedException::class
                    NotAcceptable -> NotAcceptableException::class
                    Conflict -> ConflictException::class
                    Gone -> GoneException::class
                    UnprocessableEntity -> UnprocessableEntityException::class
                    Locked -> LockedException::class
                    TooEarly -> TooEarlyException::class
                    TooManyRequests -> TooManyRequestsException::class
                    InternalServerError -> InternalServerErrorException::class
                    ServiceUnavailable -> ServiceUnavailableException::class
                    GatewayTimeout -> GatewayTimeoutException::class
                    else -> error("Unhandled status: $responseStatus")
                }
                val exception = assertFailsWith(type) {
                    client.get("")
                }

                // Then
                assertNotNull(client.pluginOrNull(HttpSend))
            }
        }

    }

    @Test
    fun shouldInstallExceptionInterceptor() = runTest {
        // Given
        val exceptions = listOf(ContentConvertException(""), SdkException(""), Exception(""))
        exceptions.forEach { exception ->
            val httpClient = HttpClient(
                MockEngine {
                    throw exception
                }
            ).also {
                it.addExceptionInterceptor()
            }

            httpClient.use { client ->
                // When
                val exception = assertFailsWith<SdkException> {
                    client.get("")
                }

                // Then
                assertNotNull(client.pluginOrNull(HttpSend))
            }
        }
    }

    @Test
    fun shouldBuildApiVersionToHeaderValue() = runTest {
        // Given
        val version = ApiVersion.VERSION_3

        // When
        val result = version.toHeaderValue()

        // Then
        assertEquals("application/vnd.doordeck.api-v3+json", result)
    }

    @Test
    fun shouldMaskLoggedValue() = runTest {
        // Given
        val password = "awesomepassword"

        // When
        val result = password.mask()

        // Then
        assertEquals("awe***", result)
    }

    @Test
    fun shouldNotThrowExceptionWithValidLatitude() = runTest {
        // Given
        val latitude = randomDouble(-90.0, 90.0)

        // When
        assertDoesNotThrow {
            latitude.validateLatitude()
        }
    }

    @Test
    fun shouldThrowExceptionWithInvalidLatitude() = runTest {
        // Given
        val latitude = 190.0

        // When
        val exception = assertFailsWith<SdkException> {
            latitude.validateLatitude()
        }

        // Then
        assertEquals("Latitude must be between -90 and 90 degrees", exception.message)
    }

    @Test
    fun shouldNotThrowExceptionWithValidLongitude() = runTest {
        // Given
        val longitude = randomDouble(-180.0, 180.0)

        // When
        assertDoesNotThrow {
            longitude.validateLongitude()
        }
    }

    @Test
    fun shouldThrowExceptionWithInvalidLongitude() = runTest {
        // Given
        val longitude = 190.0

        // When
        val exception = assertFailsWith<SdkException> {
            longitude.validateLongitude()
        }

        // Then
        assertEquals("Longitude must be between -180 and 180 degrees", exception.message)
    }

    @Test
    fun shouldNotThrowExceptionWithValidRadius() = runTest {
        // Given
        val radius = randomInt(1, 1000)

        // When
        assertDoesNotThrow {
            radius.validateRadius()
        }
    }

    @Test
    fun shouldThrowExceptionWithInvalidRadius() = runTest {
        // Given
        val radius = 1001

        // When
        val exception = assertFailsWith<SdkException> {
            radius.validateRadius()
        }

        // Then
        assertEquals("Radius must be between 1m and 1km", exception.message)
    }

    @Test
    fun shouldNotThrowExceptionWithValidAccuracy() = runTest {
        // Given
        val accuracy = randomInt(1, 1000)

        // When
        assertDoesNotThrow {
            accuracy.validateAccuracy()
        }
    }

    @Test
    fun shouldThrowExceptionWithInvalidAccuracy() = runTest {
        // Given
        val accuracy = 1001

        // When
        val exception = assertFailsWith<SdkException> {
            accuracy.validateAccuracy()
        }

        // Then
        assertEquals("Accuracy must be between 1m and 1km", exception.message)
    }

    @Test
    fun shouldSerializeAndDeserializeJson() = runTest {
        // Given
        val request = LoginRequest(Uuid.random().toString(), Uuid.random().toString())

        // When
        val result = request.toJson()

        // Then
        val restored = result.fromJson<LoginRequest>()
        assertEquals(request.email, restored.email)
        assertEquals(request.password, restored.password)
    }

    @Test
    fun shouldSerializeFusionControllerToParameters() = runTest {
        // Given
        val username = randomString()
        val password = randomString()
        val doorId = randomInt()
        val baseUrl = randomNullable { randomUrlString() }
        val controller = BasicAmagController(
            username = username,
            password = password,
            doorId = doorId,
            baseUrl = baseUrl
        )

        // When
        val result = controller.toParameters()

        // Then
        assertEquals(username, result["username"])
        assertEquals(password, result["password"])
        assertEquals(doorId.toString(), result["doorId"])
        assertEquals(baseUrl, result["baseUrl"])
    }

    @Test
    fun shouldSerializeFusionControllerToNestedParameters() = runTest {
        // Given
        val driverClass = randomString()
        val url = randomUrlString()
        val user = randomString()
        val password = randomString()
        val dataSource = BasicDataSource(
            driverClass = driverClass,
            url = url,
            user = user,
            password = password,
        )
        val outputChannel = randomInt()
        val controllerSerial = randomInt()
        val controller = BasicPacController(
            dataSource = dataSource,
            outputChannel = outputChannel,
            controllerSerial = controllerSerial
        )

        // When
        val result = controller.toParameters()

        // Then
        assertEquals(driverClass, result["dataSource.driverClass"])
        assertEquals(url, result["dataSource.url"])
        assertEquals(user, result["dataSource.user"])
        assertEquals(password, result["dataSource.password"])
        assertEquals(outputChannel.toString(), result["outputChannel"])
        assertEquals(controllerSerial.toString(), result["controllerSerial"])
    }
}