%pythoncode %{
class Accountless(object):

    def __init__(self, resource):
        self.resource = resource

    def login(self, data):
        response = json.loads(_doordeck_headless_sdk.loginJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)
        return TokenResponse(**get_success_result(response))

    def registration(self, data):
        response = json.loads(_doordeck_headless_sdk.registrationJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)
        return TokenResponse(**get_success_result(response))

    def verify_email(self, data):
        response = json.loads(_doordeck_headless_sdk.verifyEmailJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def password_reset(self, data):
        response = json.loads(_doordeck_headless_sdk.passwordResetJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def password_reset_verify(self, data):
        response = json.loads(_doordeck_headless_sdk.passwordResetVerifyJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)
%}