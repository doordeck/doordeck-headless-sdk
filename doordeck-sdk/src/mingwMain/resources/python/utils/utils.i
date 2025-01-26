%pythoncode %{

def current_epoch_seconds() -> int:
    return int(time.time())

def handle_exception(type, message):
    if type in "SdkException":
        raise SdkException(message)
    if type in "MissingContextFieldException":
        raise MissingContextFieldException(message)
    if type in "BatchShareFailedException":
        raise BatchShareFailedException(message)
    if type in "BadRequestException":
        raise BadRequestException(message)
    if type in "UnauthorizedException":
        raise UnauthorizedException(message)
    if type in "ForbiddenException":
        raise ForbiddenException(message)
    if type in "NotFoundException":
        raise NotFoundException(message)
    if type in "MethodNotAllowedException":
        raise MethodNotAllowedException(message)
    if type in "NotAcceptableException":
        raise NotAcceptableException(message)
    if type in "ConflictException":
        raise ConflictException(message)
    if type in "GoneException":
        raise GoneException(message)
    if type in "LockedException":
        raise LockedException(message)
    if type in "TooEarlyException":
        raise TooEarlyException(message)
    if type in "TooManyRequestsException":
        raise TooManyRequestsException(message)
    if type in "InternalServerErrorException":
        raise InternalServerErrorException(message)
    if type in "ServiceUnavailableException":
        raise ServiceUnavailableException(message)
    if type in "GatewayTimeoutException":
        raise GatewayTimeoutException(message)
    raise SdkException("Unhandled exception type: " + type)

%}