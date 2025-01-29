%pythoncode %{

def create_application(thiz, data):
    response = json.loads(_doordeck_headless_sdk.createApplicationJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def list_applications(thiz):
    response = json.loads(_doordeck_headless_sdk.listApplicationsJson(thiz))
    handle_exception(response)
    return [ApplicationResponse(**item) for item in get_success_result(response)]

def get_application(thiz, data):
    response = json.loads(_doordeck_headless_sdk.getApplicationJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return ApplicationResponse(**get_success_result(response))

def update_application_name(thiz, data):
    response = json.loads(_doordeck_headless_sdk.updateApplicationNameJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def update_application_company_name(thiz, data):
    response = json.loads(_doordeck_headless_sdk.updateApplicationCompanyNameJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def update_application_mailing_address(thiz, data):
    response = json.loads(_doordeck_headless_sdk.updateApplicationMailingAddressJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def update_application_privacy_policy(thiz, data):
    response = json.loads(_doordeck_headless_sdk.updateApplicationPrivacyPolicyJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def update_application_support_contact(thiz, data):
    response = json.loads(_doordeck_headless_sdk.updateApplicationSupportContactJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def update_application_app_link(thiz, data):
    response = json.loads(_doordeck_headless_sdk.updateApplicationAppLinkJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def update_application_email_preferences(thiz, data):
    response = json.loads(_doordeck_headless_sdk.updateApplicationEmailPreferencesJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def update_application_logo_url(thiz, data):
    response = json.loads(_doordeck_headless_sdk.updateApplicationLogoUrlJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def delete_application(thiz, data):
    response = json.loads(_doordeck_headless_sdk.deleteApplicationJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def get_logo_upload_url(thiz, data):
    response = json.loads(_doordeck_headless_sdk.getLogoUploadUrlJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return GetLogoUploadUrlResponse(**get_success_result(response))

def add_auth_key(thiz, data):
    response = json.loads(_doordeck_headless_sdk.addAuthKeyJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def add_auth_issuer(thiz, data):
    response = json.loads(_doordeck_headless_sdk.addAuthIssuerJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def delete_auth_issuer(thiz, data):
    response = json.loads(_doordeck_headless_sdk.deleteAuthIssuerJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def add_cors_domain(thiz, data):
    response = json.loads(_doordeck_headless_sdk.addCorsDomainJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def remove_cors_domain(thiz, data):
    response = json.loads(_doordeck_headless_sdk.removeCorsDomainJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def add_application_owner(thiz, data):
    response = json.loads(_doordeck_headless_sdk.addApplicationOwnerJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def remove_application_owner(thiz, data):
    response = json.loads(_doordeck_headless_sdk.removeApplicationOwnerJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

def get_application_owners_details(thiz, data):
    response = json.loads(_doordeck_headless_sdk.getApplicationOwnersDetailsJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return [ApplicationOwnerDetailsResponse(**item) for item in get_success_result(response)]

%}