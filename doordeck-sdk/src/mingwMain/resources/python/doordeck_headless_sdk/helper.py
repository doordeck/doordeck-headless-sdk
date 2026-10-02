"""Generated from the SDK's API surface: each method names the operation it dispatches to."""

from __future__ import annotations

from typing import List, Optional
import json
import typing

from ._transport import call_async, call_sync
from .utils import object_hook


class Helper:

    async def upload_platform_logo(self, applicationId: str, contentType: str, image: str):
        data = {
            "applicationId": applicationId,
            "contentType": contentType,
            "image": image
        }
        return await call_async("helper.uploadPlatformLogo", data)

    async def assisted_login(self, email: str, password: str):
        data = {
            "email": email,
            "password": password
        }
        return await call_async("helper.assistedLogin", data)

    async def assisted_register_ephemeral_key(self, publicKey: str, privateKey: str):
        data = { "publicKey": publicKey, "privateKey": privateKey }
        return await call_async("helper.assistedRegisterEphemeralKey", data)

    async def assisted_register(self, email: str, password: str, force: bool = False, displayName: typing.Optional[str] = None):
        data = {
            "email": email,
            "password": password,
            "displayName": displayName,
            "force": force
        }
        return await call_async("helper.assistedRegister", data)

    async def server_time(self):
        return await call_async("helper.serverTime")
