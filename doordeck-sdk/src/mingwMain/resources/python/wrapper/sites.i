%pythoncode %{

def list_sites(thiz):
    response = json.loads(_doordeck_headless_sdk.listSitesJson(thiz))
    handle_exception(response)
    return [SiteResponse(**item) for item in get_success_result(response)]

def get_locks_for_site(thiz, data):
    response = json.loads(_doordeck_headless_sdk.getLocksForSiteJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return [SiteLocksResponse(**item) for item in get_success_result(response)]

def get_users_for_site(thiz, data):
    response = json.loads(_doordeck_headless_sdk.getUsersForSiteJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return [UserForSiteResponse(**item) for item in get_success_result(response)]

%}