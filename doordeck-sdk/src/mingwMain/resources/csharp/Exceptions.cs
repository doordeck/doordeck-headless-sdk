namespace Doordeck.Headless.Sdk.Model;

using System;
using System.Collections.Generic;

// Base exception class
public class SdkException : Exception
{
    public SdkException(string message, Exception innerException = null)
        : base(message, innerException) { }
}

// SDK Exceptions
public class MissingContextFieldException : SdkException
{
    public MissingContextFieldException(string message)
        : base(message) { }
}

public class BatchShareFailedException : SdkException
{
    public List<string> UserIds { get; }

    public BatchShareFailedException(string message, List<string> userIds)
        : base(message)
    {
        UserIds = userIds;
    }
}

// API Exceptions
public class BadRequestException : SdkException
{
    public BadRequestException(string message)
        : base(message) { }
}

public class UnauthorizedException : SdkException
{
    public UnauthorizedException(string message)
        : base(message) { }
}

public class ForbiddenException : SdkException
{
    public ForbiddenException(string message)
        : base(message) { }
}

public class NotFoundException : SdkException
{
    public NotFoundException(string message)
        : base(message) { }
}

public class MethodNotAllowedException : SdkException
{
    public MethodNotAllowedException(string message)
        : base(message) { }
}

public class NotAcceptableException : SdkException
{
    public NotAcceptableException(string message)
        : base(message) { }
}

public class ConflictException : SdkException
{
    public ConflictException(string message)
        : base(message) { }
}

public class GoneException : SdkException
{
    public GoneException(string message)
        : base(message) { }
}

public class LockedException : SdkException
{
    public LockedException(string message)
        : base(message) { }
}

public class TooEarlyException : SdkException
{
    public TooEarlyException(string message)
        : base(message) { }
}

public class TooManyRequestsException : SdkException
{
    public TooManyRequestsException(string message)
        : base(message) { }
}

public class InternalServerErrorException : SdkException
{
    public InternalServerErrorException(string message)
        : base(message) { }
}

public class ServiceUnavailableException : SdkException
{
    public ServiceUnavailableException(string message)
        : base(message) { }
}

public class GatewayTimeoutException : SdkException
{
    public GatewayTimeoutException(string message)
        : base(message) { }
}
