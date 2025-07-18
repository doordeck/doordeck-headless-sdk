namespace Doordeck.Headless.Sdk.Model;

using System;
using System.Collections.Generic;

// Base exception class
public class SdkException(string message, Exception? innerException = null) : Exception(message, innerException);

// Http exception class
public class HttpException(string message, int statusCode, Exception? innerException = null) : SdkException(message, innerException);

// SDK Exceptions
public class MissingContextFieldException(string message) : SdkException(message);

public class BatchShareFailedException(string message, List<string> userIds) : SdkException(message)
{
    public List<string> UserIds { get; } = userIds;
}

// API Exceptions
public class BadRequestException(string message, int statusCode) : HttpException(message, statusCode);

public class UnauthorizedException(string message, int statusCode) : HttpException(message, statusCode);

public class ForbiddenException(string message, int statusCode) : HttpException(message, statusCode);

public class NotFoundException(string message, int statusCode) : HttpException(message, statusCode);

public class MethodNotAllowedException(string message, int statusCode) : HttpException(message, statusCode);

public class NotAcceptableException(string message, int statusCode) : HttpException(message, statusCode);

public class ConflictException(string message, int statusCode) : HttpException(message, statusCode);

public class GoneException(string message, int statusCode) : HttpException(message, statusCode);

public class UnprocessableEntityException(string message, int statusCode) : HttpException(message, statusCode);

public class LockedException(string message, int statusCode) : HttpException(message, statusCode);

public class TooEarlyException(string message, int statusCode) : HttpException(message, statusCode);

public class TooManyRequestsException(string message, int statusCode) : HttpException(message, statusCode);

public class InternalServerErrorException(string message, int statusCode) : HttpException(message, statusCode);

public class ServiceUnavailableException(string message, int statusCode) : HttpException(message, statusCode);

public class GatewayTimeoutException(string message, int statusCode) : HttpException(message, statusCode);
