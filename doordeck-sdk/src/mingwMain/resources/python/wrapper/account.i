%pythoncode %{
class Account(object):

    def __init__(self, resource):
        self.resource = resource

    async def refresh_token(self, data):
        return await execute_async(
            _doordeck_headless_sdk.refreshToken,
            [self.resource, json.dumps(dataclasses.asdict(data))],
            lambda r: TokenResponse(**get_success_result(r))
        )

    async def logout(self):
        return await execute_async(
            _doordeck_headless_sdk.logout,
            [self.resource]
        )

    async def register_ephemeral_key(self, data):
        return await execute_async(
            _doordeck_headless_sdk.registerEphemeralKey,
            [self.resource, json.dumps(dataclasses.asdict(data))],
            lambda r: RegisterEphemeralKeyResponse(**get_success_result(r))
        )

    async def register_ephemeral_key_with_secondary_authentication(self, data):
        return await execute_async(
            _doordeck_headless_sdk.registerEphemeralKeyWithSecondaryAuthentication,
            [self.resource, json.dumps(dataclasses.asdict(data))],
            lambda r: RegisterEphemeralKeyWithSecondaryAuthenticationResponse(**get_success_result(r))
        )

    async def verify_ephemeral_key_registration(self, data):
        return await execute_async(
            _doordeck_headless_sdk.verifyEphemeralKeyRegistration,
            [self.resource, json.dumps(dataclasses.asdict(data))],
            lambda r: RegisterEphemeralKeyResponse(**get_success_result(r))
        )

    async def reverify_email(self):
        return await execute_async(
            _doordeck_headless_sdk.reverifyEmail,
            [self.resource]
        )

    async def change_password(self, data):
        return await execute_async(
            _doordeck_headless_sdk.changePassword,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def get_user_details(self):
        return await execute_async(
            _doordeck_headless_sdk.getUserDetails,
            [self.resource],
            lambda r: UserDetailsResponse(**get_success_result(r))
        )

    async def update_user_details(self, data):
        return await execute_async(
            _doordeck_headless_sdk.updateUserDetails,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def delete_account(self):
        return await execute_async(
            _doordeck_headless_sdk.deleteAccount,
            [self.resource]
        )
%}