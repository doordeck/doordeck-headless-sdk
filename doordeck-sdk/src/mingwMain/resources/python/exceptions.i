%pythoncode %{
class SdkException(Exception):
    def __init__(self, message: str, exception: typing.Optional[Exception] = None):
        super().__init__(message)
        self.exception = exception


class MissingContextFieldException(SdkException):
    def __init__(self, message: str):
        super().__init__(message)


class MissingAndroidContextException(SdkException):
    def __init__(self, message: str):
        super().__init__(message)


class BatchShareFailedException(SdkException):
    def __init__(self, message: str, user_ids: typing.List[str]):
        super().__init__(message, user_ids)
        self.user_ids = user_ids


class BadRequestException(SdkException):
    def __init__(self, message: str):
        super().__init__(message)


class UnauthorizedException(SdkException):
    def __init__(self, message: str):
        super().__init__(message)


class ForbiddenException(SdkException):
    def __init__(self, message: str):
        super().__init__(message)


class NotFoundException(SdkException):
    def __init__(self, message: str):
        super().__init__(message)


class MethodNotAllowedException(SdkException):
    def __init__(self, message: str):
        super().__init__(message)


class NotAcceptableException(SdkException):
    def __init__(self, message: str):
        super().__init__(message)


class ConflictException(SdkException):
    def __init__(self, message: str):
        super().__init__(message)


class GoneException(SdkException):
    def __init__(self, message: str):
        super().__init__(message)


class LockedException(SdkException):
    def __init__(self, message: str):
        super().__init__(message)


class TooEarlyException(SdkException):
    def __init__(self, message: str):
        super().__init__(message)


class TooManyRequestsException(SdkException):
    def __init__(self, message: str):
        super().__init__(message)


class InternalServerErrorException(SdkException):
    def __init__(self, message: str):
        super().__init__(message)


class ServiceUnavailableException(SdkException):
    def __init__(self, message: str):
        super().__init__(message)


class GatewayTimeoutException(SdkException):
    def __init__(self, message: str):
        super().__init__(message)
%}