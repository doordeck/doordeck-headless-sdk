%pythoncode %{
class ContextManager(object):

    def __init__(self, resource):
        self.resource = resource

    def get_api_environment(self):
        env = _doordeck_headless_sdk.getApiEnvironment(self.resource)
        name = _doordeck_headless_sdk.getApiEnvironmentName(env)
        return ApiEnvironment[name]

    def set_cloud_auth_token(self, data):
        _doordeck_headless_sdk.setCloudAuthToken(self.resource, data)

    def get_cloud_auth_token(self):
        return _doordeck_headless_sdk.getCloudAuthToken(self.resource)

    def is_cloud_auth_token_about_to_expire(self):
        return _doordeck_headless_sdk.isCloudAuthTokenAboutToExpire(self.resource)

    def set_cloud_refresh_token(self, data):
        _doordeck_headless_sdk.setCloudRefreshToken(self.resource, data)

    def get_cloud_refresh_token(self):
        return _doordeck_headless_sdk.getCloudRefreshToken(self.resource)

    def set_fusion_host(self, data):
        _doordeck_headless_sdk.setFusionHost(self.resource, data)

    def get_fusion_host(self):
        return _doordeck_headless_sdk.getFusionHost(self.resource)

    def set_fusion_auth_token(self, data):
        _doordeck_headless_sdk.setFusionAuthToken(self.resource, data)

    def get_fusion_auth_token(self):
        return _doordeck_headless_sdk.getFusionAuthToken(self.resource)

    def set_user_id(self, data):
        _doordeck_headless_sdk.setUserId(self.resource, data)

    def get_user_id(self):
        return _doordeck_headless_sdk.getUserId(self.resource)

    def set_user_email(self, data):
        _doordeck_headless_sdk.setUserEmail(self.resource, data)

    def get_user_email(self):
        return _doordeck_headless_sdk.getUserEmail(self.resource)

    def is_certificate_chain_about_to_expire(self):
        return _doordeck_headless_sdk.isCertificateChainAboutToExpire(self.resource)

    def is_key_pair_valid(self):
        return _doordeck_headless_sdk.isKeyPairValid(self.resource)

    def set_operation_context(self, data):
        _doordeck_headless_sdk.setOperationContextJson(self.resource, json.dumps(dataclasses.asdict(data)))

    def clear_context(self):
        _doordeck_headless_sdk.clearContext(self.resource)
%}