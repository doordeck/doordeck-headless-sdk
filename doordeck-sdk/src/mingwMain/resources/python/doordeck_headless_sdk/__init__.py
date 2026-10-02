"""The Doordeck SDK.

    from doordeck_headless_sdk import Doordeck

    sdk = Doordeck(api_environment="PROD")
    locks = await sdk.lock_operations.get_pinned_locks()
    sdk.release()
"""

from __future__ import annotations

import typing

from . import _secure_storage, _transport
from .account import Account
from .accountless import Accountless
from .context_manager import ContextManager
from .crypto_manager import CryptoManager
from .errors import (
    BadRequestException,
    BatchShareFailedException,
    ConflictException,
    ForbiddenException,
    GatewayTimeoutException,
    GoneException,
    InternalServerErrorException,
    LockedException,
    MethodNotAllowedException,
    MissingContextFieldException,
    NotAcceptableException,
    NotFoundException,
    SdkException,
    ServiceUnavailableException,
    TooEarlyException,
    TooManyRequestsException,
    UnauthorizedException,
    UnprocessableEntityException,
)
from .fusion import Fusion
from .helper import Helper
from .lock_operations import LockOperations
from .platform import Platform
from .secure_storage import ISecureStorage
from .sites import Sites
from .tiles import Tiles


class Doordeck:
    """There is a single SDK instance behind the boundary, so constructing this initializes it and
    :meth:`release` tears it down."""

    def __init__(
        self,
        api_environment: typing.Literal["DEV", "STAGING", "PROD"] = "PROD",
        cloud_auth_token: str | None = None,
        cloud_refresh_token: str | None = None,
        fusion_host: str | None = None,
        secure_storage_impl: ISecureStorage | None = None,
        debug_logging: bool = False,
    ) -> None:
        # Registered before initializing: initialization reads the secure storage to restore any
        # previously stored context.
        if secure_storage_impl is not None:
            _secure_storage.register(secure_storage_impl)

        _transport.initialize(
            {
                "apiEnvironment": api_environment,
                "cloudAuthToken": cloud_auth_token,
                "cloudRefreshToken": cloud_refresh_token,
                "fusionHost": fusion_host,
                "debugLogging": debug_logging,
            }
        )

        self.account = Account()
        self.accountless = Accountless()
        self.context_manager = ContextManager()
        self.crypto_manager = CryptoManager()
        self.fusion = Fusion()
        self.helper = Helper()
        self.lock_operations = LockOperations()
        self.platform = Platform()
        self.sites = Sites()
        self.tiles = Tiles()

    def release(self) -> None:
        _transport.release()


__all__ = [
    "Doordeck",
    "ISecureStorage",
    "SdkException",
    "MissingContextFieldException",
    "BatchShareFailedException",
    "BadRequestException",
    "UnauthorizedException",
    "ForbiddenException",
    "NotFoundException",
    "MethodNotAllowedException",
    "NotAcceptableException",
    "ConflictException",
    "GoneException",
    "UnprocessableEntityException",
    "LockedException",
    "TooEarlyException",
    "TooManyRequestsException",
    "InternalServerErrorException",
    "ServiceUnavailableException",
    "GatewayTimeoutException",
]
