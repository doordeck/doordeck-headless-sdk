"""Exceptions raised by the SDK.

The boundary can only carry an exception's type name and message, so a failure reported by the SDK is
matched back onto this hierarchy by name.
"""


class SdkException(Exception):
    pass


class MissingContextFieldException(SdkException):
    pass


class BatchShareFailedException(SdkException):
    def __init__(self, message: str, user_ids: list[str] | None = None) -> None:
        super().__init__(message)
        self.user_ids = user_ids or []


class BadRequestException(SdkException):
    pass


class UnauthorizedException(SdkException):
    pass


class ForbiddenException(SdkException):
    pass


class NotFoundException(SdkException):
    pass


class MethodNotAllowedException(SdkException):
    pass


class NotAcceptableException(SdkException):
    pass


class ConflictException(SdkException):
    pass


class GoneException(SdkException):
    pass


class UnprocessableEntityException(SdkException):
    pass


class LockedException(SdkException):
    pass


class TooEarlyException(SdkException):
    pass


class TooManyRequestsException(SdkException):
    pass


class InternalServerErrorException(SdkException):
    pass


class ServiceUnavailableException(SdkException):
    pass


class GatewayTimeoutException(SdkException):
    pass


# Ordered so that the more specific names are tested before the ones they contain.
_BY_NAME: tuple[tuple[str, type[SdkException]], ...] = (
    ("MissingContextFieldException", MissingContextFieldException),
    ("BatchShareFailedException", BatchShareFailedException),
    ("BadRequestException", BadRequestException),
    ("UnauthorizedException", UnauthorizedException),
    ("ForbiddenException", ForbiddenException),
    ("NotFoundException", NotFoundException),
    ("MethodNotAllowedException", MethodNotAllowedException),
    ("NotAcceptableException", NotAcceptableException),
    ("ConflictException", ConflictException),
    ("GoneException", GoneException),
    ("UnprocessableEntityException", UnprocessableEntityException),
    ("LockedException", LockedException),
    ("TooEarlyException", TooEarlyException),
    ("TooManyRequestsException", TooManyRequestsException),
    ("InternalServerErrorException", InternalServerErrorException),
    ("ServiceUnavailableException", ServiceUnavailableException),
    ("GatewayTimeoutException", GatewayTimeoutException),
    ("SdkException", SdkException),
)


def to_exception(exception_type: str, exception_message: str) -> SdkException:
    for name, cls in _BY_NAME:
        if name in exception_type:
            return cls(exception_message)
    return SdkException(f"Unhandled exception type: {exception_type}")
