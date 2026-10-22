"""Generated from the SDK's API surface: each method names the operation it dispatches to."""

from __future__ import annotations

import json

from ._transport import call_async, call_sync
from .utils import object_hook


class Sites:

    async def list_sites(self):
        return await call_async("sites.listSites")

    async def get_locks_for_site(self, siteId: str):
        data = { "siteId": siteId }
        return await call_async("sites.getLocksForSite", data)

    async def get_users_for_site(self, siteId: str):
        data = { "siteId": siteId }
        return await call_async("sites.getUsersForSite", data)
