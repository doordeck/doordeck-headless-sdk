package com.doordeck.multiplatform.sdk.exceptions

import kotlin.js.JsExport

/**
 * API Exceptions
 */
@JsExport
class BadRequestException(override val message: String, statusCode: Int) : HttpException(message, statusCode)

@JsExport
class UnauthorizedException(override val message: String, statusCode: Int) : HttpException(message, statusCode)

@JsExport
class ForbiddenException(override val message: String, statusCode: Int) : HttpException(message, statusCode)

@JsExport
class NotFoundException(override val message: String, statusCode: Int) : HttpException(message, statusCode)

@JsExport
class MethodNotAllowedException(override val message: String, statusCode: Int) : HttpException(message, statusCode)

@JsExport
class NotAcceptableException(override val message: String, statusCode: Int) : HttpException(message, statusCode)

@JsExport
class ConflictException(override val message: String, statusCode: Int) : HttpException(message, statusCode)

@JsExport
class GoneException(override val message: String, statusCode: Int) : HttpException(message, statusCode)

@JsExport
class UnprocessableEntityException(override val message: String, statusCode: Int) : HttpException(message, statusCode)

@JsExport
class LockedException(override val message: String, statusCode: Int) : HttpException(message, statusCode)

@JsExport
class TooEarlyException(override val message: String, statusCode: Int) : HttpException(message, statusCode)

@JsExport
class TooManyRequestsException(override val message: String, statusCode: Int) : HttpException(message, statusCode)

@JsExport
class InternalServerErrorException(override val message: String, statusCode: Int) : HttpException(message, statusCode)

@JsExport
class ServiceUnavailableException(override val message: String, statusCode: Int) : HttpException(message, statusCode)

@JsExport
class GatewayTimeoutException(override val message: String, statusCode: Int) : HttpException(message, statusCode)
