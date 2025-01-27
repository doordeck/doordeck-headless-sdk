%pythoncode %{

def current_epoch_seconds() -> int:
    return int(time.time())

def get_success_result(json_response):
    return json_response["success"]["result"]

def handle_exception(response):
    if response["failure"] is None:
        return
    exception_type = response["failure"]["exceptionType"]
    exception_message = response["failure"]["exceptionMessage"]
    if "SdkException" in exception_type:
        raise SdkException(exception_message)
    if "MissingContextFieldException" in exception_type:
        raise MissingContextFieldException(exception_message)
    if "BatchShareFailedException" in exception_type:
        raise BatchShareFailedException(exception_message)
    if "BadRequestException" in exception_type:
        raise BadRequestException(exception_message)
    if "UnauthorizedException" in exception_type:
        raise UnauthorizedException(exception_message)
    if "ForbiddenException" in exception_type:
        raise ForbiddenException(exception_message)
    if "NotFoundException" in exception_type:
        raise NotFoundException(exception_message)
    if "MethodNotAllowedException" in exception_type:
        raise MethodNotAllowedException(exception_message)
    if "NotAcceptableException" in exception_type:
        raise NotAcceptableException(exception_message)
    if "ConflictException" in exception_type:
        raise ConflictException(exception_message)
    if "GoneException" in exception_type:
        raise GoneException(exception_message)
    if "LockedException" in exception_type:
        raise LockedException(exception_message)
    if "TooEarlyException" in exception_type:
        raise TooEarlyException(exception_message)
    if "TooManyRequestsException" in exception_type:
        raise TooManyRequestsException(exception_message)
    if "InternalServerErrorException" in exception_type:
        raise InternalServerErrorException(exception_message)
    if "ServiceUnavailableException" in exception_type:
        raise ServiceUnavailableException(exception_message)
    if "GatewayTimeoutException" in exception_type:
        raise GatewayTimeoutException(exception_message)
    raise SdkException("Unhandled exception type: " + exception_type)

%}