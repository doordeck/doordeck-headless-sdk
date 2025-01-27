%pythoncode %{

def login(thiz, data):
    response = json.loads(_doordeck_headless_sdk.loginJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return TokenResponse(**get_success_result(response))

def registration(thiz, data):
    response = json.loads(_doordeck_headless_sdk.registrationJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return TokenResponse(**get_success_result(response))

def verifyEmail(thiz, data):
    response = json.loads(_doordeck_headless_sdk.verifyEmailJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def passwordReset(thiz, data):
    response = json.loads(_doordeck_headless_sdk.passwordResetJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def passwordResetVerify(thiz, data):
    response = json.loads(_doordeck_headless_sdk.passwordResetVerifyJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

%}