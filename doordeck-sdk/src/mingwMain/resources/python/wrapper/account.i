%pythoncode %{

def refreshToken(thiz, data):
    response = json.loads(_doordeck_headless_sdk.refreshTokenJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return TokenResponse(**get_success_result(response))

def logout(thiz, data):
    response = json.loads(_doordeck_headless_sdk.logoutJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def registerEphemeralKey(thiz, data):
    response = json.loads(_doordeck_headless_sdk.registerEphemeralKeyJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return RegisterEphemeralKeyResponse(**get_success_result(response))

def registerEphemeralKeyWithSecondaryAuthentication(thiz, data):
    response = json.loads(_doordeck_headless_sdk.registerEphemeralKeyWithSecondaryAuthenticationJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return RegisterEphemeralKeyWithSecondaryAuthenticationResponse(**get_success_result(response))

def verifyEphemeralKeyRegistration(thiz, data):
    response = json.loads(_doordeck_headless_sdk.verifyEphemeralKeyRegistrationJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return RegisterEphemeralKeyResponse(**get_success_result(response))

def reverifyEmail(thiz, data):
    response = json.loads(_doordeck_headless_sdk.reverifyEmailJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def changePassword(thiz, data):
    response = json.loads(_doordeck_headless_sdk.changePasswordJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def getUserDetails(thiz, data):
    response = json.loads(_doordeck_headless_sdk.getUserDetailsJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return UserDetailsResponse(**get_success_result(response))

def updateUserDetails(thiz, data):
    response = json.loads(_doordeck_headless_sdk.updateUserDetailsJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def deleteAccount(thiz, data):
    response = json.loads(_doordeck_headless_sdk.deleteAccountJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

%}