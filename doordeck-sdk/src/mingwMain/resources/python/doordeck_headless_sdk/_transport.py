"""The native boundary.

Every SDK operation is addressed by name through ``call``, so the four declarations below do not change
as the API grows. That is why they are written by hand with ctypes rather than generated: there is
nothing left for a binding generator to do, and nothing to build - no C compiler, no extension module,
and therefore no dependency on a particular CPython version.

Results arrive on a callback and the string handed to it is only borrowed for the duration of the call,
so it is decoded immediately.
"""

from __future__ import annotations

import asyncio
import ctypes
import json
import os
import threading
from typing import Any, Callable

from .errors import SdkException, to_exception

_LIBRARY_NAME = "Doordeck.Headless.Sdk.dll"
_LIBRARY_PATH_VARIABLE = "DOORDECK_SDK_LIBRARY"


def _load_library() -> ctypes.CDLL:
    override = os.environ.get(_LIBRARY_PATH_VARIABLE)
    if override:
        return ctypes.CDLL(os.path.abspath(override))
    alongside = os.path.join(os.path.dirname(os.path.abspath(__file__)), _LIBRARY_NAME)
    return ctypes.CDLL(alongside)


_library = _load_library()

_library.initialize.argtypes = [ctypes.c_char_p, ctypes.c_longlong, ctypes.c_void_p]
_library.initialize.restype = None
_library.call.argtypes = [ctypes.c_char_p, ctypes.c_char_p, ctypes.c_longlong, ctypes.c_void_p]
_library.call.restype = None
_library.release.argtypes = [ctypes.c_longlong, ctypes.c_void_p]
_library.release.restype = None
_library.set_secure_storage.argtypes = [ctypes.c_void_p, ctypes.c_void_p, ctypes.c_void_p]
_library.set_secure_storage.restype = None

_RESULT_CALLBACK = ctypes.CFUNCTYPE(None, ctypes.c_longlong, ctypes.c_char_p)

_pending: dict[int, Callable[[str], None]] = {}
_lock = threading.Lock()
_last_request_id = 0


def _next_request_id() -> int:
    global _last_request_id
    with _lock:
        _last_request_id += 1
        return _last_request_id


def _on_result(request_id: int, payload: bytes) -> None:
    """Runs on a native stack, so it must not let anything escape."""
    with _lock:
        complete = _pending.pop(request_id, None)
    if complete is None:
        return
    try:
        complete(payload.decode() if payload else "")
    except Exception:  # noqa: BLE001 - nothing useful can be done with it here
        pass


# One callback serves every call, correlated by request id. Building one per call would be wrong as
# well as wasteful: a CFUNCTYPE that is not referenced anywhere is freed as soon as the expression
# creating it ends, leaving the SDK holding a pointer into reclaimed memory.
_result_callback = _RESULT_CALLBACK(_on_result)
_result_callback_pointer = ctypes.cast(_result_callback, ctypes.c_void_p)


def _serialize(args: Any) -> bytes | None:
    """Operations taking a single value receive it verbatim; everything else is serialised.

    Passing a bare string through the serialiser would wrap it in quotes and escape it, and the SDK
    would store those quotes as part of the value.
    """
    if args is None:
        return None
    if isinstance(args, str):
        return args.encode()
    return json.dumps(args).encode()


def _unwrap(payload: str) -> Any:
    result = json.loads(payload)
    failure = result.get("failure")
    if failure is not None:
        raise to_exception(failure.get("exceptionType", ""), failure.get("exceptionMessage", ""))
    success = result.get("success")
    return None if success is None else success.get("result")


def _invoke(native_call: Callable[[int, ctypes.c_void_p], None], complete: Callable[[str], None]) -> None:
    request_id = _next_request_id()
    with _lock:
        _pending[request_id] = complete
    try:
        native_call(request_id, _result_callback_pointer)
    except BaseException:
        with _lock:
            _pending.pop(request_id, None)
        raise


def call_sync(method: str, args: Any = None) -> Any:
    """For operations the SDK completes without leaving the process.

    Those invoke the callback before the native call returns, so the result is already in hand and
    nothing is ever waited on.
    """
    return _invoke_sync(lambda request_id, callback: _library.call(method.encode(), _serialize(args), request_id, callback), method)


async def call_async(method: str, args: Any = None) -> Any:
    return await _invoke_async(lambda request_id, callback: _library.call(method.encode(), _serialize(args), request_id, callback))


def initialize(config: Any) -> Any:
    return _invoke_sync(lambda request_id, callback: _library.initialize(_serialize(config), request_id, callback), "initialize")


def release() -> Any:
    return _invoke_sync(lambda request_id, callback: _library.release(request_id, callback), "release")


def register_secure_storage(set_entry: int, get_entry: int, clear: int) -> None:
    _library.set_secure_storage(set_entry, get_entry, clear)


def _invoke_sync(native_call: Callable[[int, ctypes.c_void_p], None], method: str) -> Any:
    received: list[str] = []
    _invoke(native_call, received.append)
    if not received:
        raise SdkException(f"{method} did not report a result synchronously")
    return _unwrap(received[0])


async def _invoke_async(native_call: Callable[[int, ctypes.c_void_p], None]) -> Any:
    loop = asyncio.get_running_loop()
    future: asyncio.Future[Any] = loop.create_future()

    def complete(payload: str) -> None:
        loop.call_soon_threadsafe(_settle, future, payload)

    _invoke(native_call, complete)
    return await future


def _settle(future: asyncio.Future[Any], payload: str) -> None:
    if future.done():
        return
    try:
        future.set_result(_unwrap(payload))
    except BaseException as error:  # noqa: BLE001 - reported to the awaiting caller
        future.set_exception(error)
