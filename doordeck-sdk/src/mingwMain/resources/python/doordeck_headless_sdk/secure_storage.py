"""The interface a host implements to persist the SDK's context."""

from __future__ import annotations

import typing


class ISecureStorage:
    def set_api_environment(self, api_environment: str) -> None:
        raise NotImplementedError

    def get_api_environment(self) -> str | None:
        raise NotImplementedError

    def add_cloud_auth_token(self, token: str) -> None:
        raise NotImplementedError

    def get_cloud_auth_token(self) -> str | None:
        raise NotImplementedError

    def add_cloud_refresh_auth_token(self, token: str) -> None:
        raise NotImplementedError

    def get_cloud_refresh_token(self) -> str | None:
        raise NotImplementedError

    def set_fusion_host(self, host: str) -> None:
        raise NotImplementedError

    def get_fusion_host(self) -> str | None:
        raise NotImplementedError

    def add_fusion_auth_token(self, token: str) -> None:
        raise NotImplementedError

    def get_fusion_auth_token(self) -> str | None:
        raise NotImplementedError

    def add_public_key(self, public_key: bytes) -> None:
        raise NotImplementedError

    def get_public_key(self) -> bytes | None:
        raise NotImplementedError

    def add_private_key(self, private_key: bytes) -> None:
        raise NotImplementedError

    def get_private_key(self) -> bytes | None:
        raise NotImplementedError

    def set_key_pair_verified(self, verified: bool) -> None:
        raise NotImplementedError

    def get_key_pair_verified(self) -> bool | None:
        raise NotImplementedError

    def add_user_id(self, user_id: str) -> None:
        raise NotImplementedError

    def get_user_id(self) -> str | None:
        raise NotImplementedError

    def set_user_email(self, user_email: str) -> None:
        raise NotImplementedError

    def get_user_email(self) -> str | None:
        raise NotImplementedError

    def add_certificate_chain(self, certificate_chain: typing.List[str]) -> None:
        raise NotImplementedError

    def get_certificate_chain(self) -> typing.List[str]:
        raise NotImplementedError

    def clear(self) -> None:
        raise NotImplementedError
