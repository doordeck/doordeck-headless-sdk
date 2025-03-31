%pythoncode %{
class Platform(object):

    def __init__(self, resource):
        self.resource = resource

    async def create_application(self, data: CreateApplicationData):
        return await execute_async(
            _doordeck_headless_sdk.createApplication,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def list_applications(self):
        return await execute_async(
            _doordeck_headless_sdk.listApplications,
            [self.resource]
        )

    async def get_application(self, applicationId: str):
        data = { "applicationId": applicationId }
        return await execute_async(
            _doordeck_headless_sdk.getApplication,
            [self.resource, json.dumps(data)]
        )

    async def update_application_name(self, applicationId: str, name: str):
        data = {
            "applicationId": applicationId,
            "name": name
        }
        return await execute_async(
            _doordeck_headless_sdk.updateApplicationName,
            [self.resource, json.dumps(data)]
        )

    async def update_application_company_name(self, applicationId: str, companyName: str):
        data = {
            "applicationId": applicationId,
            "companyName": companyName
        }
        return await execute_async(
            _doordeck_headless_sdk.updateApplicationCompanyName,
            [self.resource, json.dumps(data)]
        )

    async def update_application_mailing_address(self, applicationId: str, mailingAddress: str):
        data = {
            "applicationId": applicationId,
            "mailingAddress": mailingAddress
        }
        return await execute_async(
            _doordeck_headless_sdk.updateApplicationMailingAddress,
            [self.resource, json.dumps(data)]
        )

    async def update_application_privacy_policy(self, applicationId: str, privacyPolicy: str):
        data = {
            "applicationId": applicationId,
            "privacyPolicy": privacyPolicy
        }
        return await execute_async(
            _doordeck_headless_sdk.updateApplicationPrivacyPolicy,
            [self.resource, json.dumps(data)]
        )

    async def update_application_support_contact(self, applicationId: str, supportContact: str):
        data = {
            "applicationId": applicationId,
            "supportContact": supportContact
        }
        return await execute_async(
            _doordeck_headless_sdk.updateApplicationSupportContact,
            [self.resource, json.dumps(data)]
        )

    async def update_application_app_link(self, applicationId: str, appLink: str):
        data = {
            "applicationId": applicationId,
            "appLink": appLink
        }
        return await execute_async(
            _doordeck_headless_sdk.updateApplicationAppLink,
            [self.resource, json.dumps(data)]
        )

    async def update_application_email_preferences(self, applicationId: str, emailPreferences: EmailPreferencesData):
        data = {
            "applicationId": applicationId,
            "emailPreferences": dataclasses.asdict(emailPreferences)
        }
        return await execute_async(
            _doordeck_headless_sdk.updateApplicationEmailPreferences,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def update_application_logo_url(self, applicationId: str, logoUrl: str):
        data = {
            "applicationId": applicationId,
            "logoUrl": logoUrl
        }
        return await execute_async(
            _doordeck_headless_sdk.updateApplicationLogoUrl,
            [self.resource, json.dumps(data)]
        )

    async def delete_application(self, applicationId: str):
        data = { "applicationId": applicationId }
        return await execute_async(
            _doordeck_headless_sdk.deleteApplication,
            [self.resource, json.dumps(data)]
        )

    async def get_logo_upload_url(self, applicationId: str, contentType: str):
        data = {
            "applicationId": applicationId,
            "contentType": contentType
        }
        return await execute_async(
            _doordeck_headless_sdk.getLogoUploadUrl,
            [self.resource, json.dumps(data)]
        )

    async def add_auth_key(self, applicationId: str, key: AuthKeyData):
        data = {
            "applicationId": applicationId,
            "key": dataclasses.asdict(key)
        }
        return await execute_async(
            _doordeck_headless_sdk.addAuthKey,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )

    async def add_auth_issuer(self, applicationId: str, url: str):
        data = {
            "applicationId": applicationId,
            "url": url
        }
        return await execute_async(
            _doordeck_headless_sdk.addAuthIssuer,
            [self.resource, json.dumps(data)]
        )

    async def delete_auth_issuer(self, applicationId: str, url: str):
        data = {
            "applicationId": applicationId,
            "url": url
        }
        return await execute_async(
            _doordeck_headless_sdk.deleteAuthIssuer,
            [self.resource, json.dumps(data)]
        )

    async def add_cors_domain(self, applicationId: str, url: str):
        data = {
            "applicationId": applicationId,
            "url": url
        }
        return await execute_async(
            _doordeck_headless_sdk.addCorsDomain,
            [self.resource, json.dumps(data)]
        )

    async def remove_cors_domain(self, applicationId: str, url: str):
        data = {
            "applicationId": applicationId,
            "url": url
        }
        return await execute_async(
            _doordeck_headless_sdk.removeCorsDomain,
            [self.resource, json.dumps(data)]
        )

    async def add_application_owner(self, applicationId: str, userId: str):
        data = {
            "applicationId": applicationId,
            "userId": userId
        }
        return await execute_async(
            _doordeck_headless_sdk.addApplicationOwner,
            [self.resource, json.dumps(data)]
        )

    async def remove_application_owner(self, applicationId: str, userId: str):
        data = {
            "applicationId": applicationId,
            "userId": userId
        }
        return await execute_async(
            _doordeck_headless_sdk.removeApplicationOwner,
            [self.resource, json.dumps(data)]
        )

    async def get_application_owners_details(self, applicationId: str):
        data = { "applicationId": applicationId }
        return await execute_async(
            _doordeck_headless_sdk.getApplicationOwnersDetails,
            [self.resource, json.dumps(data)]
        )
%}