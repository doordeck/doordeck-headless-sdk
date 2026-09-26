"""Generated from the SDK's API surface: each method names the operation it dispatches to."""

from __future__ import annotations

from typing import List, Optional
import json
import typing

from ._transport import call_async, call_sync
from .utils import object_hook


class Account:

    async def refresh_token(self, refreshToken: str):
        data = { "refreshToken": refreshToken }
        return await call_async("account.refreshToken", data)

    async def logout(self):
        return await call_async("account.logout")

    async def register_ephemeral_key(self, publicKey: str, privateKey: str):
        data = { "publicKey": publicKey, "privateKey": privateKey }
        return await call_async("account.registerEphemeralKey", data)

    async def register_ephemeral_key_with_secondary_authentication(self, publicKey: str, method: typing.Optional[typing.Literal["EMAIL", "TELEPHONE", "SMS"]] = None):
        data = {
            "publicKey": publicKey,
            "method": method
        }
        return await call_async("account.registerEphemeralKeyWithSecondaryAuthentication", data)

    async def verify_ephemeral_key_registration(self, code: str, publicKey: typing.Optional[str] = None, privateKey: typing.Optional[str] = None):
        data = {
            "code": code,
            "publicKey": publicKey,
            "privateKey": privateKey
        }
        return await call_async("account.verifyEphemeralKeyRegistration", data)

    async def reverify_email(self):
        return await call_async("account.reverifyEmail")

    async def change_password(self, oldPassword: str, newPassword: str):
        data = {
           "oldPassword": oldPassword,
           "newPassword": newPassword
        }
        return await call_async("account.changePassword", data)

    async def get_user_details(self):
        return await call_async("account.getUserDetails")

    async def update_user_details(self, displayName: str):
        data = { "displayName": displayName }
        return await call_async("account.updateUserDetails", data)

    async def delete_account(self):
        return await call_async("account.deleteAccount")
