"""Adapts a host provided secure storage onto the three entry points the SDK asks for.

The SDK owns every buffer that crosses the boundary - it passes one in to be filled - so no allocation
is ever handed over for the other side to free, and there is no shared scratch buffer to overflow.
"""

from __future__ import annotations

import base64
import ctypes
from typing import Any

from . import _transport
from .errors import SdkException

# Entry names agreed with SecureStorageKeys on the Kotlin side.
API_ENVIRONMENT = "apiEnvironment"
CLOUD_AUTH_TOKEN = "cloudAuthToken"
CLOUD_REFRESH_TOKEN = "cloudRefreshToken"
FUSION_HOST = "fusionHost"
FUSION_AUTH_TOKEN = "fusionAuthToken"
PUBLIC_KEY = "publicKey"
PRIVATE_KEY = "privateKey"
KEY_PAIR_VERIFIED = "keyPairVerified"
USER_ID = "userId"
USER_EMAIL = "userEmail"
CERTIFICATE_CHAIN = "certificateChain"

_SET_ENTRY = ctypes.CFUNCTYPE(None, ctypes.c_char_p, ctypes.c_char_p)
_GET_ENTRY = ctypes.CFUNCTYPE(ctypes.c_int, ctypes.c_char_p, ctypes.c_void_p, ctypes.c_int)
_CLEAR_ENTRIES = ctypes.CFUNCTYPE(None)

_implementation: Any = None


def _set_entry(key: bytes, value: bytes | None) -> None:
    if _implementation is None or key is None:
        return
    name = key.decode()
    entry = None if value is None else value.decode()

    if name == KEY_PAIR_VERIFIED:
        # The only entry the SDK legitimately clears by storing a null value.
        _implementation.set_key_pair_verified(None if entry is None else base64.b64decode(entry))
    elif entry is None:
        return
    elif name == API_ENVIRONMENT:
        _implementation.set_api_environment(entry)
    elif name == CLOUD_AUTH_TOKEN:
        _implementation.add_cloud_auth_token(entry)
    elif name == CLOUD_REFRESH_TOKEN:
        _implementation.add_cloud_refresh_auth_token(entry)
    elif name == FUSION_HOST:
        _implementation.set_fusion_host(entry)
    elif name == FUSION_AUTH_TOKEN:
        _implementation.add_fusion_auth_token(entry)
    elif name == PUBLIC_KEY:
        _implementation.add_public_key(base64.b64decode(entry))
    elif name == PRIVATE_KEY:
        _implementation.add_private_key(base64.b64decode(entry))
    elif name == USER_ID:
        _implementation.add_user_id(entry)
    elif name == USER_EMAIL:
        _implementation.set_user_email(entry)
    elif name == CERTIFICATE_CHAIN:
        _implementation.add_certificate_chain(entry.split("|"))


def _read(name: str) -> str | None:
    if name == API_ENVIRONMENT:
        return _implementation.get_api_environment()
    if name == CLOUD_AUTH_TOKEN:
        return _implementation.get_cloud_auth_token()
    if name == CLOUD_REFRESH_TOKEN:
        return _implementation.get_cloud_refresh_token()
    if name == FUSION_HOST:
        return _implementation.get_fusion_host()
    if name == FUSION_AUTH_TOKEN:
        return _implementation.get_fusion_auth_token()
    if name == PUBLIC_KEY:
        return _encode(_implementation.get_public_key())
    if name == PRIVATE_KEY:
        return _encode(_implementation.get_private_key())
    if name == KEY_PAIR_VERIFIED:
        return _encode(_implementation.get_key_pair_verified())
    if name == USER_ID:
        return _implementation.get_user_id()
    if name == USER_EMAIL:
        return _implementation.get_user_email()
    if name == CERTIFICATE_CHAIN:
        chain = _implementation.get_certificate_chain()
        return None if chain is None else "|".join(chain)
    return None


def _encode(value: bytes | None) -> str | None:
    return None if value is None else base64.b64encode(value).decode()


def _get_entry(key: bytes, buffer: int | None, capacity: int) -> int:
    if _implementation is None or key is None:
        return -1
    try:
        entry = _read(key.decode())
    except Exception:  # noqa: BLE001 - a host failure must not cross back as an exception
        return -1
    if entry is None:
        return -1

    # UTF-8 to match Kotlin/Native's toKString on the other side.
    encoded = entry.encode()

    # A null buffer, or one too small, is how the SDK asks for the length before allocating.
    if buffer and capacity > len(encoded):
        ctypes.memmove(buffer, encoded + b"\x00", len(encoded) + 1)

    return len(encoded)


def _clear_entries() -> None:
    if _implementation is not None:
        _implementation.clear()


# Held at module level for the lifetime of the process: the SDK keeps these pointers, so the objects
# they came from must stay referenced. A temporary would be freed as soon as the call registering it
# returned.
_set_entry_callback = _SET_ENTRY(_set_entry)
_get_entry_callback = _GET_ENTRY(_get_entry)
_clear_entries_callback = _CLEAR_ENTRIES(_clear_entries)


def _address_of(callback: Any) -> int:
    address = ctypes.cast(callback, ctypes.c_void_p).value
    if address is None:
        raise SdkException("Could not take the address of a secure storage callback")
    return address


def register(implementation: Any) -> None:
    """Hands `implementation` to the SDK. Must be called before initialising it, because
    initialisation reads the storage to restore any previously stored context."""
    global _implementation
    _implementation = implementation
    _transport.register_secure_storage(
        _address_of(_set_entry_callback),
        _address_of(_get_entry_callback),
        _address_of(_clear_entries_callback),
    )
