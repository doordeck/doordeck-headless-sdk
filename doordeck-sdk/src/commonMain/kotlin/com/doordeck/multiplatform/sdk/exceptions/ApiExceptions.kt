package com.doordeck.multiplatform.sdk.exceptions

import kotlin.js.JsExport

/**
 * API Exceptions
 */
@JsExport
class BadRequestException(override val message: String) : SdkException(message)

@JsExport
class UnauthorizedException(override val message: String) : SdkException(message)

@JsExport
class ForbiddenException(override val message: String) : SdkException(message)

@JsExport
class NotFoundException(override val message: String) : SdkException(message)

@JsExport
class MethodNotAllowedException(override val message: String) : SdkException(message)

@JsExport
class NotAcceptableException(override val message: String) : SdkException(message)

@JsExport
class ConflictException(override val message: String) : SdkException(message)

@JsExport
class GoneException(override val message: String) : SdkException(message)

@JsExport
class LockedException(override val message: String) : SdkException(message)

@JsExport
class UnprocessableEntityException(override val message: String) : SdkException(message)

@JsExport
class TooEarlyException(override val message: String) : SdkException(message)

@JsExport
class TooManyRequestsException(override val message: String) : SdkException(message)

@JsExport
class InternalServerErrorException(override val message: String) : SdkException(message)

@JsExport
class ServiceUnavailableException(override val message: String) : SdkException(message)

@JsExport
class GatewayTimeoutException(override val message: String) : SdkException(message)