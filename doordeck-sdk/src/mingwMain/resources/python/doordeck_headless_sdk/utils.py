"""Helpers shared by the generated wrappers."""

import base64
import time
from types import SimpleNamespace


def current_epoch_millis() -> int:
    return time.time_ns() // 1_000_000

def decode_base64_to_byte_array(input):
    return base64.b64decode(input)

def encode_byte_array_to_base64(input):
    return base64.b64encode(input).decode('utf-8')

def certificate_chain_to_string(input):
    return '|'.join(input)

def string_to_certificate_chain(input):
    return input.split('|')


def object_hook(d):
    return SimpleNamespace(**{k: (object_hook(v) if isinstance(v, dict) else v) for k, v in d.items()})
