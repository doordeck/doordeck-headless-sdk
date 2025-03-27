%pythoncode %{
class Platform(object):

    def __init__(self, resource):
        self.resource = resource

    async def create_application(self, data):
        return await execute_async(
            _doordeck_headless_sdk.createApplication,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def list_applications(self):
        return await execute_async(
            _doordeck_headless_sdk.listApplications,
            [self.resource],
            lambda r: [ApplicationResponse(**item) for item in get_success_result(r)]
        )

    async def get_application(self, data):
        return await execute_async(
            _doordeck_headless_sdk.getApplication,
            [self.resource, json.dumps(dataclasses.asdict(data))],
            lambda r: ApplicationResponse(**get_success_result(r))
        )

    async def update_application_name(self, data):
        return await execute_async(
            _doordeck_headless_sdk.updateApplicationName,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def update_application_company_name(self, data):
        return await execute_async(
            _doordeck_headless_sdk.updateApplicationCompanyName,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def update_application_mailing_address(self, data):
        return await execute_async(
            _doordeck_headless_sdk.updateApplicationMailingAddress,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def update_application_privacy_policy(self, data):
        return await execute_async(
            _doordeck_headless_sdk.updateApplicationPrivacyPolicy,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def update_application_support_contact(self, data):
        return await execute_async(
            _doordeck_headless_sdk.updateApplicationSupportContact,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def update_application_app_link(self, data):
        return await execute_async(
            _doordeck_headless_sdk.updateApplicationAppLink,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def update_application_email_preferences(self, data):
        return await execute_async(
            _doordeck_headless_sdk.updateApplicationEmailPreferences,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def update_application_logo_url(self, data):
        return await execute_async(
            _doordeck_headless_sdk.updateApplicationLogoUrl,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def delete_application(self, data):
        return await execute_async(
            _doordeck_headless_sdk.deleteApplication,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def get_logo_upload_url(self, data):
        return await execute_async(
            _doordeck_headless_sdk.getLogoUploadUrl,
            [self.resource, json.dumps(dataclasses.asdict(data))],
            lambda r: GetLogoUploadUrlResponse(**get_success_result(r))
        )

    async def add_auth_key(self, data):
        return await execute_async(
            _doordeck_headless_sdk.addAuthKey,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def add_auth_issuer(self, data):
        return await execute_async(
            _doordeck_headless_sdk.addAuthIssuer,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def delete_auth_issuer(self, data):
        return await execute_async(
            _doordeck_headless_sdk.deleteAuthIssuer,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def add_cors_domain(self, data):
        return await execute_async(
            _doordeck_headless_sdk.addCorsDomain,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def remove_cors_domain(self, data):
        return await execute_async(
            _doordeck_headless_sdk.removeCorsDomain,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def add_application_owner(self, data):
        return await execute_async(
            _doordeck_headless_sdk.addApplicationOwner,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def remove_application_owner(self, data):
        return await execute_async(
            _doordeck_headless_sdk.removeApplicationOwner,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def get_application_owners_details(self, data):
        return await execute_async(
            _doordeck_headless_sdk.getApplicationOwnersDetails,
            [self.resource, json.dumps(dataclasses.asdict(data))],
            lambda r: [ApplicationOwnerDetailsResponse(**item) for item in get_success_result(r)]
        )
%}