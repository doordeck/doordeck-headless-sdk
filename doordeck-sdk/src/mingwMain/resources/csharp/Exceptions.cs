namespace Doordeck.Headless.Sdk.Model;

using System;
using System.Collections.Generic;

// Base exception class
public class SdkException(string message, Exception? innerException = null) : Exception(message, innerException);

// SDK Exceptions
public class MissingContextFieldException(string message) : SdkException(message);

public class BatchShareFailedException(string message, List<string> userIds) : SdkException(message)
{
    public List<string> UserIds { get; } = userIds;
}

// API Exceptions
public class BadRequestException(string message) : SdkException(message);

public class UnauthorizedException(string message) : SdkException(message);

public class ForbiddenException(string message) : SdkException(message);

public class NotFoundException(string message) : SdkException(message);

public class MethodNotAllowedException(string message) : SdkException(message);

public class NotAcceptableException(string message) : SdkException(message);

public class ConflictException(string message) : SdkException(message);

public class GoneException(string message) : SdkException(message);

public class LockedException(string message) : SdkException(message);

public class TooEarlyException(string message) : SdkException(message);

public class TooManyRequestsException(string message) : SdkException(message);

public class InternalServerErrorException(string message) : SdkException(message);

public class ServiceUnavailableException(string message) : SdkException(message);

public class GatewayTimeoutException(string message) : SdkException(message);
