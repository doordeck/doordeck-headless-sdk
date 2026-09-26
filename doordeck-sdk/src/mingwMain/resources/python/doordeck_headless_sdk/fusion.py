"""Generated from the SDK's API surface: each method names the operation it dispatches to."""

from __future__ import annotations

from typing import List, Optional
import dataclasses
import json
import typing

from ._transport import call_async, call_sync
from .utils import object_hook
from .models import LockController


class Fusion:

    async def login(self, email: str, password: str):
        data = {
            "email": email,
            "password": password
        }
        return await call_async("fusion.login", data)

    async def get_integration_type(self):
        return await call_async("fusion.getIntegrationType")

    async def get_integration_configuration(self, type: str, controller: typing.Optional[LockController] = None):
        data = { "type": type, "controller": controller }
        return await call_async("fusion.getIntegrationConfiguration", data)

    async def enable_door(self, name: str, siteId: str, controller: LockController):
        data = {
            "name": name,
            "siteId": siteId,
            "controller": dataclasses.asdict(controller)
        }
        return await call_async("fusion.enableDoor", data)

    async def delete_door(self, deviceId: str):
        data = { "deviceId": deviceId }
        return await call_async("fusion.deleteDoor", data)

    async def get_door_status(self, deviceId: str):
        data = { "deviceId": deviceId }
        return await call_async("fusion.getDoorStatus", data)

    async def start_door(self, deviceId: str):
        data = { "deviceId": deviceId }
        return await call_async("fusion.startDoor", data)

    async def stop_door(self, deviceId: str):
        data = { "deviceId": deviceId }
        return await call_async("fusion.stopDoor", data)
