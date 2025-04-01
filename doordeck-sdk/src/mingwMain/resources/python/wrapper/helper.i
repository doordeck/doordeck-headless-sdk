%pythoncode %{
class Helper(object):

    def __init__(self, resource):
        self.resource = resource

    async def upload_platform_logo(self, applicationId: str, contentType: str, image: str):
        data = {
            "applicationId": applicationId,
            "contentType": contentType,
            "image": image
        }
        return await execute_async(
            _doordeck_headless_sdk.uploadPlatformLogo,
            [self.resource, json.dumps(data)]
        )

    async def assisted_login(self, email: str, password: str):
        data = {
            "email": email,
            "password": password
        }
        return await execute_async(
            _doordeck_headless_sdk.assistedLogin,
            [self.resource, json.dumps(data)]
        )

    async def assisted_register_ephemeral_key(self, publicKey: str):
        data = { "publicKey": publicKey }
        return await execute_async(
            _doordeck_headless_sdk.assistedRegisterEphemeralKey,
            [self.resource, json.dumps(data)]
        )

    async def assisted_register(self, email: str, password: str, force: bool = False, displayName: str = None):
        data = {
            "email": email,
            "password": password,
            "displayName": displayName,
            "force": force
        }
        return await execute_async(
            _doordeck_headless_sdk.assistedRegister,
            [self.resource, json.dumps(data)]
        )
%}