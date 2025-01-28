%pythoncode %{

def set_auth_token(thiz, data):
    _doordeck_headless_sdk.setAuthToken(thiz, data)

def get_auth_token(thiz):
    _doordeck_headless_sdk.getAuthToken(thiz)

def is_auth_token_about_to_expire(thiz):
    return _doordeck_headless_sdk.isAuthTokenAboutToExpire(thiz)

def set_refresh_token(thiz, data):
    _doordeck_headless_sdk.setRefreshToken(thiz, data)

def get_refresh_token(thiz):
    _doordeck_headless_sdk.getRefreshToken(thiz)

def set_fusion_auth_token(thiz, data):
    _doordeck_headless_sdk.setFusionAuthToken(thiz, data)

def get_fusion_auth_token(thiz):
    _doordeck_headless_sdk.getFusionAuthToken(thiz)

def set_user_id(thiz, data):
    _doordeck_headless_sdk.setUserId(thiz, data)

def get_user_id(thiz):
    return _doordeck_headless_sdk.getUserId(thiz)

def set_user_email(thiz, data):
    _doordeck_headless_sdk.setUserEmail(thiz, data)

def get_user_email(thiz):
    return _doordeck_headless_sdk.getUserEmail(thiz)

def is_certificate_chain_about_to_expire(thiz):
    return _doordeck_headless_sdk.isCertificateChainAboutToExpire(thiz)

def is_key_pair_valid(thiz):
    return _doordeck_headless_sdk.isKeyPairValid(thiz)

def set_operation_context(thiz, data):
    _doordeck_headless_sdk.setOperationContextJson(thiz, json.dumps(dataclasses.asdict(data)))

def load_context(thiz):
    _doordeck_headless_sdk.loadContext(thiz)

def store_context(thiz):
    _doordeck_headless_sdk.storeContext(thiz)

def clear_context(thiz):
    _doordeck_headless_sdk.clearContext(thiz)

%}