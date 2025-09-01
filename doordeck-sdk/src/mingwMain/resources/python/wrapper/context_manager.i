%pythoncode %{
class ContextManager(object):

    def __init__(self, resource):
        self.resource = resource

    def get_api_environment(self):
        env = _doordeck_headless_sdk.getApiEnvironment(self.resource)
        name = _doordeck_headless_sdk.getApiEnvironmentName(env)
        return name

    def set_cloud_auth_token(self, token: str):
        _doordeck_headless_sdk.setCloudAuthToken(self.resource, token)

    def get_cloud_auth_token(self):
        return _doordeck_headless_sdk.getCloudAuthToken(self.resource)

    async def is_cloud_auth_token_invalid_or_expired():
        return await execute_async(
            _doordeck_headless_sdk.isCloudAuthTokenInvalidOrExpired,
            [self.resource]
        )

    def set_cloud_refresh_token(self, token: str):
        _doordeck_headless_sdk.setCloudRefreshToken(self.resource, token)

    def get_cloud_refresh_token(self):
        return _doordeck_headless_sdk.getCloudRefreshToken(self.resource)

    def set_fusion_host(self, host: str):
        _doordeck_headless_sdk.setFusionHost(self.resource, host)

    def get_fusion_host(self):
        return _doordeck_headless_sdk.getFusionHost(self.resource)

    def set_fusion_auth_token(self, token: str):
        _doordeck_headless_sdk.setFusionAuthToken(self.resource, token)

    def get_fusion_auth_token(self):
        return _doordeck_headless_sdk.getFusionAuthToken(self.resource)

    def set_user_id(self, userId: str):
        _doordeck_headless_sdk.setUserId(self.resource, userId)

    def get_user_id(self):
        return _doordeck_headless_sdk.getUserId(self.resource)

    def set_user_email(self, email: str):
        _doordeck_headless_sdk.setUserEmail(self.resource, email)

    def get_user_email(self):
        return _doordeck_headless_sdk.getUserEmail(self.resource)

    def is_certificate_chain_invalid_or_expired(self):
        return _doordeck_headless_sdk.isCertificateChainInvalidOrExpired(self.resource)

    def is_key_pair_valid(self):
        return _doordeck_headless_sdk.isKeyPairValid(self.resource)

    def set_operation_context(self, userId: str, userCertificateChain: str, userPublicKey: str, userPrivateKey: str):
        data = {
            "userId": userId,
            "userCertificateChain": userCertificateChain,
            "userPublicKey": userPublicKey,
            "userPrivateKey": userPrivateKey
        }
        _doordeck_headless_sdk.setOperationContextJson(self.resource, json.dumps(data))

    async def get_context_state():
        return await execute_async(
            _doordeck_headless_sdk.getContextState,
            [self.resource]
        )

    def clear_context(self):
        _doordeck_headless_sdk.clearContext(self.resource)
%}