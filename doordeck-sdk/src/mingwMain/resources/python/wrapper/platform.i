%pythoncode %{

class Platform(object):

    def __init__(self, resource):
        self.resource = resource

    def create_application(self, data):
        response = json.loads(_doordeck_headless_sdk.createApplicationJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def list_applications(self):
        response = json.loads(_doordeck_headless_sdk.listApplicationsJson(self.resource))
        handle_exception(response)
        return [ApplicationResponse(**item) for item in get_success_result(response)]

    def get_application(self, data):
        response = json.loads(_doordeck_headless_sdk.getApplicationJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)
        return ApplicationResponse(**get_success_result(response))

    def update_application_name(self, data):
        response = json.loads(_doordeck_headless_sdk.updateApplicationNameJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def update_application_company_name(self, data):
        response = json.loads(_doordeck_headless_sdk.updateApplicationCompanyNameJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def update_application_mailing_address(self, data):
        response = json.loads(_doordeck_headless_sdk.updateApplicationMailingAddressJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def update_application_privacy_policy(self, data):
        response = json.loads(_doordeck_headless_sdk.updateApplicationPrivacyPolicyJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def update_application_support_contact(self, data):
        response = json.loads(_doordeck_headless_sdk.updateApplicationSupportContactJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def update_application_app_link(self, data):
        response = json.loads(_doordeck_headless_sdk.updateApplicationAppLinkJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def update_application_email_preferences(self, data):
        response = json.loads(_doordeck_headless_sdk.updateApplicationEmailPreferencesJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def update_application_logo_url(self, data):
        response = json.loads(_doordeck_headless_sdk.updateApplicationLogoUrlJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def delete_application(self, data):
        response = json.loads(_doordeck_headless_sdk.deleteApplicationJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def get_logo_upload_url(self, data):
        response = json.loads(_doordeck_headless_sdk.getLogoUploadUrlJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)
        return GetLogoUploadUrlResponse(**get_success_result(response))

    def add_auth_key(self, data):
        response = json.loads(_doordeck_headless_sdk.addAuthKeyJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def add_auth_issuer(self, data):
        response = json.loads(_doordeck_headless_sdk.addAuthIssuerJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def delete_auth_issuer(self, data):
        response = json.loads(_doordeck_headless_sdk.deleteAuthIssuerJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def add_cors_domain(self, data):
        response = json.loads(_doordeck_headless_sdk.addCorsDomainJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def remove_cors_domain(self, data):
        response = json.loads(_doordeck_headless_sdk.removeCorsDomainJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def add_application_owner(self, data):
        response = json.loads(_doordeck_headless_sdk.addApplicationOwnerJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def remove_application_owner(self, data):
        response = json.loads(_doordeck_headless_sdk.removeApplicationOwnerJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)

    def get_application_owners_details(self, data):
        response = json.loads(_doordeck_headless_sdk.getApplicationOwnersDetailsJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)
        return [ApplicationOwnerDetailsResponse(**item) for item in get_success_result(response)]

%}