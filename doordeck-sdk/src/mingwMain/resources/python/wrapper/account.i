%pythoncode %{

def refresh_token(thiz, data):
    response = json.loads(_doordeck_headless_sdk.refreshTokenJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return TokenResponse(**get_success_result(response))

def logout(thiz):
    response = json.loads(_doordeck_headless_sdk.logoutJson(thiz))
    handle_exception(response)

def register_ephemeral_key(thiz, data):
    response = json.loads(_doordeck_headless_sdk.registerEphemeralKeyJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return RegisterEphemeralKeyResponse(**get_success_result(response))

def register_ephemeral_key_with_secondary_authentication(thiz, data):
    response = json.loads(_doordeck_headless_sdk.registerEphemeralKeyWithSecondaryAuthenticationJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return RegisterEphemeralKeyWithSecondaryAuthenticationResponse(**get_success_result(response))

def verify_ephemeral_key_registration(thiz, data):
    response = json.loads(_doordeck_headless_sdk.verifyEphemeralKeyRegistrationJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return RegisterEphemeralKeyResponse(**get_success_result(response))

def reverify_email(thiz):
    response = json.loads(_doordeck_headless_sdk.reverifyEmailJson(thiz))
    handle_exception(response)

def change_password(thiz, data):
    response = json.loads(_doordeck_headless_sdk.changePasswordJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def get_user_details(thiz):
    response = json.loads(_doordeck_headless_sdk.getUserDetailsJson(thiz))
    handle_exception(response)
    return UserDetailsResponse(**get_success_result(response))

def update_user_details(thiz, data):
    response = json.loads(_doordeck_headless_sdk.updateUserDetailsJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def delete_account(thiz):
    response = json.loads(_doordeck_headless_sdk.deleteAccountJson(thiz))
    handle_exception(response)

%}