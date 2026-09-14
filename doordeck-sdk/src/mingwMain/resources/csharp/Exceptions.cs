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

public class UnprocessableEntityException(string message) : SdkException(message);

public class LockedException(string message) : SdkException(message);

public class TooEarlyException(string message) : SdkException(message);

public class TooManyRequestsException(string message) : SdkException(message);

public class InternalServerErrorException(string message) : SdkException(message);

public class ServiceUnavailableException(string message) : SdkException(message);

public class GatewayTimeoutException(string message) : SdkException(message);

/// <summary>
/// Maps a failure reported across the native boundary back onto the SDK exception hierarchy. The
/// boundary can only carry the exception's type name and message, so the type is matched by name.
/// </summary>
public static class FailedResultDataExtensions
{
    public static Exception ToException(this FailedResultData failure)
    {
        var exceptionType = failure.ExceptionType;
        if (exceptionType.Contains("SdkException")) return new SdkException(failure.ExceptionMessage);
        if (exceptionType.Contains("MissingContextFieldException")) return new MissingContextFieldException(failure.ExceptionMessage);
        if (exceptionType.Contains("BatchShareFailedException")) return new BatchShareFailedException(failure.ExceptionMessage, []);
        if (exceptionType.Contains("BadRequestException")) return new BadRequestException(failure.ExceptionMessage);
        if (exceptionType.Contains("UnauthorizedException")) return new UnauthorizedException(failure.ExceptionMessage);
        if (exceptionType.Contains("ForbiddenException")) return new ForbiddenException(failure.ExceptionMessage);
        if (exceptionType.Contains("NotFoundException")) return new NotFoundException(failure.ExceptionMessage);
        if (exceptionType.Contains("MethodNotAllowedException")) return new MethodNotAllowedException(failure.ExceptionMessage);
        if (exceptionType.Contains("NotAcceptableException")) return new NotAcceptableException(failure.ExceptionMessage);
        if (exceptionType.Contains("ConflictException")) return new ConflictException(failure.ExceptionMessage);
        if (exceptionType.Contains("GoneException")) return new GoneException(failure.ExceptionMessage);
        if (exceptionType.Contains("UnprocessableEntityException")) return new UnprocessableEntityException(failure.ExceptionMessage);
        if (exceptionType.Contains("LockedException")) return new LockedException(failure.ExceptionMessage);
        if (exceptionType.Contains("TooEarlyException")) return new TooEarlyException(failure.ExceptionMessage);
        if (exceptionType.Contains("TooManyRequestsException")) return new TooManyRequestsException(failure.ExceptionMessage);
        if (exceptionType.Contains("InternalServerErrorException")) return new InternalServerErrorException(failure.ExceptionMessage);
        if (exceptionType.Contains("ServiceUnavailableException")) return new ServiceUnavailableException(failure.ExceptionMessage);
        if (exceptionType.Contains("GatewayTimeoutException")) return new GatewayTimeoutException(failure.ExceptionMessage);
        return new SdkException("Unhandled exception type: " + exceptionType);
    }
}
