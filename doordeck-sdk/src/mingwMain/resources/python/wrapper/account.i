%pythoncode %{
class Account(object):

    def __init__(self, resource):
        self.resource = resource

    async def refresh_token(self, refreshToken: str):
        data = { "refreshToken": refreshToken }
        return await execute_async(
            _doordeck_headless_sdk.refreshToken,
            [self.resource, json.dumps(data)]
        )

    async def logout(self):
        return await execute_async(
            _doordeck_headless_sdk.logout,
            [self.resource]
        )

    async def register_ephemeral_key(self, publicKey: str):
        data = { "publicKey": publicKey }
        return await execute_async(
            _doordeck_headless_sdk.registerEphemeralKey,
            [self.resource, json.dumps(data)]
        )

    async def register_ephemeral_key_with_secondary_authentication(self, publicKey: str, method: TwoFactorMethod = None):
        data = {
            "publicKey": publicKey,
            "method": method
        }
        return await execute_async(
            _doordeck_headless_sdk.registerEphemeralKeyWithSecondaryAuthentication,
            [self.resource, json.dumps(data)]
        )

    async def verify_ephemeral_key_registration(self, code: str, privateKey: str = None):
        data = {
            "code": code,
            "privateKey": privateKey
        }
        return await execute_async(
            _doordeck_headless_sdk.verifyEphemeralKeyRegistration,
            [self.resource, json.dumps(data)]
        )

    async def reverify_email(self):
        return await execute_async(
            _doordeck_headless_sdk.reverifyEmail,
            [self.resource]
        )

    async def change_password(self, oldPassword: str, newPassword: str):
        data = {
           "oldPassword": oldPassword,
            "oldPassword": oldPassword
        }
        return await execute_async(
            _doordeck_headless_sdk.changePassword,
            [self.resource, json.dumps(data)]
        )

    async def get_user_details(self):
        return await execute_async(
            _doordeck_headless_sdk.getUserDetails,
            [self.resource]
        )

    async def update_user_details(self, displayName: str):
        data = { "displayName": displayName }
        return await execute_async(
            _doordeck_headless_sdk.updateUserDetails,
            [self.resource, json.dumps(data)]
        )

    async def delete_account(self):
        return await execute_async(
            _doordeck_headless_sdk.deleteAccount,
            [self.resource]
        )
%}