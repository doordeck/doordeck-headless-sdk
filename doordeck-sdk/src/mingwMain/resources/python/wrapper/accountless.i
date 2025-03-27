%pythoncode %{
class Accountless(object):

    def __init__(self, resource):
        self.resource = resource

    async def login(self, data):
        return await execute_async(
            _doordeck_headless_sdk.login,
            [self.resource, json.dumps(dataclasses.asdict(data))],
            lambda r: TokenResponse(**get_success_result(r))
        )

    async def registration(self, data):
        return await execute_async(
            _doordeck_headless_sdk.registration,
            [self.resource, json.dumps(dataclasses.asdict(data))],
            lambda r: TokenResponse(**get_success_result(r))
        )

    async def verify_email(self, data):
        return await execute_async(
            _doordeck_headless_sdk.verifyEmail,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def password_reset(self, data):
        return await execute_async(
            _doordeck_headless_sdk.passwordReset,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def password_reset_verify(self, data):
        return await execute_async(
            _doordeck_headless_sdk.passwordResetVerify,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )
%}