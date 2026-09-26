"""Generated from the SDK's API surface: each method names the operation it dispatches to."""

from __future__ import annotations

import json
import typing

from ._transport import call_async, call_sync
from .utils import object_hook


class Tiles:

    async def get_locks_belonging_to_tile(self, tileId: str):
        data = { "tileId": tileId }
        return await call_async("tiles.getLocksBelongingToTile", data)

    async def associate_multiple_locks(self, tileId: str, siteId: str, lockIds: List[str]):
        data = {
            "tileId": tileId,
            "siteId": siteId,
            "lockIds": lockIds
        }
        return await call_async("tiles.associateMultipleLocks", data)
