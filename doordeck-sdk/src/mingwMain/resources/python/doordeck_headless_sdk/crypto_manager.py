"""Generated from the SDK's API surface: each method names the operation it dispatches to."""

from __future__ import annotations

import json

from ._transport import call_async, call_sync
from .utils import object_hook


class CryptoManager:

    def generate_key_pair(self):
        return json.loads(call_sync("crypto.generateEncodedKeyPair"), object_hook=object_hook)
