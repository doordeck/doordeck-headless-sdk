%pythoncode %{
class Helper(object):

    def __init__(self, resource):
        self.resource = resource

    async def upload_platform_logo(self, data):
        return await execute_async(
            _doordeck_headless_sdk.uploadPlatformLogo,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def assisted_login(self, data):
        return await execute_async(
            _doordeck_headless_sdk.assistedLogin,
            [self.resource, json.dumps(dataclasses.asdict(data))],
            lambda r: AssistedLoginResponse(**get_success_result(r))
        )

    async def assisted_register_ephemeral_key(self, data):
        return await execute_async(
            _doordeck_headless_sdk.assistedRegisterEphemeralKey,
            [self.resource, json.dumps(dataclasses.asdict(data))],
            lambda r: AssistedRegisterEphemeralKeyResponse(**get_success_result(r))
        )

    async def assisted_register(self, data):
        return await execute_async(
            _doordeck_headless_sdk.assistedRegister,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )
%}