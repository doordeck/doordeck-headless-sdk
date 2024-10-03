package com.doordeck.multiplatform.sdk

open class SdkException(override val message: String, exception: Throwable? = null) : Exception(message, exception)

/**
 * SDK Exceptions
 */
class MissingOperationContextException(override val message: String): SdkException(message)
class MissingAndroidContextException(override val message: String): SdkException(message)

/**
 * API Exceptions
 */
class BadRequestException(override val message: String): SdkException(message)
class UnauthorizedException(override val message: String): SdkException(message)
class ForbiddenException(override val message: String): SdkException(message)
class NotFoundException(override val message: String): SdkException(message)
class MethodNotAllowedException(override val message: String): SdkException(message)
class NotAcceptableException(override val message: String): SdkException(message)
class ConflictException(override val message: String): SdkException(message)
class GoneException(override val message: String): SdkException(message)
class LockedException(override val message: String): SdkException(message)
class TooEarlyException(override val message: String): SdkException(message)
class TooManyRequestsException(override val message: String): SdkException(message)
class InternalServerErrorException(override val message: String): SdkException(message)
class ServiceUnavailableException(override val message: String): SdkException(message)
class GatewayTimeoutException(override val message: String): SdkException(message)
