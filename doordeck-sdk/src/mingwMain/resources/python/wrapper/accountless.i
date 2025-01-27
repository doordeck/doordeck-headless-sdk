%pythoncode %{

def login(thiz, data):
    response = json.loads(_doordeck_headless_sdk.loginJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return token_response(**get_success_result(response))

def registration(thiz, data):
    response = json.loads(_doordeck_headless_sdk.registrationJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return token_response(**get_success_result(response))

def verify_email(thiz, data):
    response = json.loads(_doordeck_headless_sdk.verifyEmailJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def password_reset(thiz, data):
    response = json.loads(_doordeck_headless_sdk.passwordResetJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def password_reset_verify(thiz, data):
    response = json.loads(_doordeck_headless_sdk.passwordResetVerifyJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

%}