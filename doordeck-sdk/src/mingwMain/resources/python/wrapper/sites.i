%pythoncode %{

def list_sites(thiz):
    response = json.loads(_doordeck_headless_sdk.listSitesJson(thiz))
    handle_exception(response)
    return [site_response(**item) for item in get_success_result(response)]

def get_locks_for_site(thiz, data):
    response = json.loads(_doordeck_headless_sdk.getLocksForSiteJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return [site_locks_response(**item) for item in get_success_result(response)]

def get_users_for_site(thiz, data):
    response = json.loads(_doordeck_headless_sdk.getUsersForSiteJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return [user_for_site_response(**item) for item in get_success_result(response)]

%}