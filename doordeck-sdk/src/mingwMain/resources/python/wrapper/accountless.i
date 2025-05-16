%pythoncode %{
class Accountless(object):

    def __init__(self, resource):
        self.resource = resource

    async def login(self, email: str, password: str):
        data = {
            "email": email,
            "password": password
        }
        return await execute_async(
            _doordeck_headless_sdk.login,
            [self.resource, json.dumps(data)]
        )

    async def registration(self, email: str, password: str, force: bool = False, displayName: typing.Optional[str] = None, publicKey: typing.Optional[str] = None):
        data = {
            "email": email,
            "password": password,
            "force": force,
            "publicKey": publicKey,
            "displayName": displayName
        }
        return await execute_async(
            _doordeck_headless_sdk.registration,
            [self.resource, json.dumps(data)]
        )

    async def verify_email(self, code: str):
        data = { "code": code }
        return await execute_async(
            _doordeck_headless_sdk.verifyEmail,
            [self.resource, json.dumps(data)]
        )

    async def password_reset(self, email: str):
        data = { "email": email }
        return await execute_async(
            _doordeck_headless_sdk.passwordReset,
            [self.resource, json.dumps(data)]
        )

    async def password_reset_verify(self, userId: str, token: str, password: str):
        data = {
            "userId": userId,
            "token": token,
            "password": password
        }
        return await execute_async(
            _doordeck_headless_sdk.passwordResetVerify,
            [self.resource, json.dumps(data)]
        )
%}