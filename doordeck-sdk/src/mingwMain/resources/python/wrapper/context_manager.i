%pythoncode %{
class ContextManager(object):

    def __init__(self, resource):
        self.resource = resource

    def set_auth_token(self, data):
        _doordeck_headless_sdk.setAuthToken(self.resource, data)

    def get_auth_token(self):
        _doordeck_headless_sdk.getAuthToken(self.resource)

    def is_auth_token_about_to_expire(self):
        return _doordeck_headless_sdk.isAuthTokenAboutToExpire(self.resource)

    def set_refresh_token(self, data):
        _doordeck_headless_sdk.setRefreshToken(self.resource, data)

    def get_refresh_token(self):
        _doordeck_headless_sdk.getRefreshToken(self.resource)

    def set_fusion_auth_token(self, data):
        _doordeck_headless_sdk.setFusionAuthToken(self.resource, data)

    def get_fusion_auth_token(self):
        _doordeck_headless_sdk.getFusionAuthToken(self.resource)

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

    def load_context(self):
        _doordeck_headless_sdk.loadContext(self.resource)

    def store_context(self):
        _doordeck_headless_sdk.storeContext(self.resource)

    def clear_context(self):
        _doordeck_headless_sdk.clearContext(self.resource)
%}