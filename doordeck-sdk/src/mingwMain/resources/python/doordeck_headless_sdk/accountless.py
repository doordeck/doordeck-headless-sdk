"""Generated from the SDK's API surface: each method names the operation it dispatches to."""

from __future__ import annotations

import json
import typing

from ._transport import call_async, call_sync
from .utils import object_hook


class Accountless:

    async def login(self, email: str, password: str):
        data = {
            "email": email,
            "password": password
        }
        return await call_async("accountless.login", data)

    async def registration(self, email: str, password: str, force: bool = False, displayName: typing.Optional[str] = None, publicKey: typing.Optional[str] = None):
        data = {
            "email": email,
            "password": password,
            "force": force,
            "publicKey": publicKey,
            "displayName": displayName
        }
        return await call_async("accountless.registration", data)

    async def verify_email(self, code: str):
        data = { "code": code }
        return await call_async("accountless.verifyEmail", data)

    async def password_reset(self, email: str):
        data = { "email": email }
        return await call_async("accountless.passwordReset", data)

    async def password_reset_verify(self, userId: str, token: str, password: str):
        data = {
            "userId": userId,
            "token": token,
            "password": password
        }
        return await call_async("accountless.passwordResetVerify", data)
