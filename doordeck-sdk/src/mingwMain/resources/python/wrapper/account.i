%pythoncode %{
class Account(object):

    def __init__(self, resource):
        self.resource = resource

    def refresh_token(self, data):
        response = json.loads(_doordeck_headless_sdk.refreshTokenJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)
        return TokenResponse(**get_success_result(response))

    def logout(self):
        response = json.loads(_doordeck_headless_sdk.logoutJson(self.resource))
        handle_exception(response)

    def register_ephemeral_key(self, data):
        response = json.loads(_doordeck_headless_sdk.registerEphemeralKeyJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)
        return RegisterEphemeralKeyResponse(**get_success_result(response))

    def register_ephemeral_key_with_secondary_authentication(self, data):
        response = json.loads(_doordeck_headless_sdk.registerEphemeralKeyWithSecondaryAuthenticationJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)
        return RegisterEphemeralKeyWithSecondaryAuthenticationResponse(**get_success_result(response))

    def verify_ephemeral_key_registration(self, data):
        response = json.loads(_doordeck_headless_sdk.verifyEphemeralKeyRegistrationJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)
        return RegisterEphemeralKeyResponse(**get_success_result(response))

    def reverify_email(self):
        response = json.loads(_doordeck_headless_sdk.reverifyEmailJson(self.resource))
        handle_exception(response)

    def change_password(self, data):
        response = json.loads(_doordeck_headless_sdk.changePasswordJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def get_user_details(self):
        response = json.loads(_doordeck_headless_sdk.getUserDetailsJson(self.resource))
        handle_exception(response)
        return UserDetailsResponse(**get_success_result(response))

    def update_user_details(self, data):
        response = json.loads(_doordeck_headless_sdk.updateUserDetailsJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def delete_account(self):
        response = json.loads(_doordeck_headless_sdk.deleteAccountJson(self.resource))
        handle_exception(response)
%}