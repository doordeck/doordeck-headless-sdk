package com.doordeck.multiplatform.sdk

import kotlin.js.JsExport

@JsExport
open class SdkException(override val message: String, exception: Throwable? = null) : Exception(message, exception)

/**
 * SDK Exceptions
 */
@JsExport
class MissingOperationContextException(override val message: String): SdkException(message)
@JsExport
class MissingAndroidContextException(override val message: String): SdkException(message)

/**
 * API Exceptions
 */
@JsExport
class BadRequestException(override val message: String): SdkException(message)
@JsExport
class UnauthorizedException(override val message: String): SdkException(message)
@JsExport
class ForbiddenException(override val message: String): SdkException(message)
@JsExport
class NotFoundException(override val message: String): SdkException(message)
@JsExport
class MethodNotAllowedException(override val message: String): SdkException(message)
@JsExport
class NotAcceptableException(override val message: String): SdkException(message)
@JsExport
class ConflictException(override val message: String): SdkException(message)
@JsExport
class GoneException(override val message: String): SdkException(message)
@JsExport
class LockedException(override val message: String): SdkException(message)
@JsExport
class TooEarlyException(override val message: String): SdkException(message)
@JsExport
class TooManyRequestsException(override val message: String): SdkException(message)
@JsExport
class InternalServerErrorException(override val message: String): SdkException(message)
@JsExport
class ServiceUnavailableException(override val message: String): SdkException(message)
@JsExport
class GatewayTimeoutException(override val message: String): SdkException(message)
