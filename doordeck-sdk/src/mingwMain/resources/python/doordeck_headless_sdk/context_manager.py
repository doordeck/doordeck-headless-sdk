"""Generated from the SDK's API surface: each method names the operation it dispatches to."""

from __future__ import annotations

import json
import typing

from ._transport import call_async, call_sync
from .utils import object_hook


class ContextManager:

    def get_api_environment(self):
        return call_sync("context.getApiEnvironment")

    def set_cloud_auth_token(self, token: str):
        call_sync("context.setCloudAuthToken", token)

    def get_cloud_auth_token(self):
        return call_sync("context.getCloudAuthToken")

    async def is_cloud_auth_token_invalid_or_expired(self, checkServerInvalidation: bool):
        return await call_async("context.isCloudAuthTokenInvalidOrExpired", checkServerInvalidation)

    def set_cloud_refresh_token(self, token: str):
        call_sync("context.setCloudRefreshToken", token)

    def get_cloud_refresh_token(self):
        return call_sync("context.getCloudRefreshToken")

    def set_fusion_host(self, host: str):
        call_sync("context.setFusionHost", host)

    def get_fusion_host(self):
        return call_sync("context.getFusionHost")

    def set_fusion_auth_token(self, token: str):
        call_sync("context.setFusionAuthToken", token)

    def get_fusion_auth_token(self):
        return call_sync("context.getFusionAuthToken")

    def set_user_id(self, userId: str):
        call_sync("context.setUserId", userId)

    def get_user_id(self):
        return call_sync("context.getUserId")

    def set_user_email(self, email: str):
        call_sync("context.setUserEmail", email)

    def get_user_email(self):
        return call_sync("context.getUserEmail")

    def is_certificate_chain_invalid_or_expired(self):
        return call_sync("context.isCertificateChainInvalidOrExpired")

    def is_key_pair_valid(self):
        return call_sync("context.isKeyPairValid")

    def set_operation_context(self, userId: str, userCertificateChain: str, userPublicKey: str, userPrivateKey: str):
        data = {
            "userId": userId,
            "certificateChain": userCertificateChain,
            "publicKey": userPublicKey,
            "privateKey": userPrivateKey
        }
        call_sync("context.setOperationContext", json.dumps(data))

    async def get_context_state(self, checkServerInvalidation: bool):
        return await call_async("context.getContextState", checkServerInvalidation)

    def clear_context(self):
        call_sync("context.clearContext")
