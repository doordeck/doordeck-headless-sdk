%pythoncode %{

def set_auth_token(thiz, data):
    _doordeck_headless_sdk.setAuthToken(thiz, data)

def get_auth_token(thiz):
    _doordeck_headless_sdk.getAuthToken(thiz)

def set_operation_context(thiz, data):
    _doordeck_headless_sdk.setOperationContextJson(thiz, json.dumps(dataclasses.asdict(data)))

%}