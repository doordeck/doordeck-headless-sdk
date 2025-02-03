%pythoncode %{
class Helper(object):

    def __init__(self, resource):
        self.resource = resource

    def upload_platform_logo(self, data):
        response = json.loads(_doordeck_headless_sdk.uploadPlatformLogoJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def assisted_login(self, data):
        response = json.loads(_doordeck_headless_sdk.assistedLoginJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)
        return AssistedLoginResponse(**get_success_result(response))

    def assisted_register_ephemeral_key(self, data):
        response = json.loads(_doordeck_headless_sdk.assistedRegisterEphemeralKeyJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)
        return AssistedRegisterEphemeralKeyResponse(**get_success_result(response))

    def assisted_register(self, data):
        response = json.loads(_doordeck_headless_sdk.assistedRegisterJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)
%}