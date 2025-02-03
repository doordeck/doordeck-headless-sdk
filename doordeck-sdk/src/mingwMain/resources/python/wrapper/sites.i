%pythoncode %{
class Sites(object):

    def __init__(self, resource):
        self.resource = resource

    def list_sites(self):
        response = json.loads(_doordeck_headless_sdk.listSitesJson(self.resource))
        handle_exception(response)
        return [SiteResponse(**item) for item in get_success_result(response)]

    def get_locks_for_site(self, data):
        response = json.loads(_doordeck_headless_sdk.getLocksForSiteJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)
        return [SiteLocksResponse(**item) for item in get_success_result(response)]

    def get_users_for_site(self, data):
        response = json.loads(_doordeck_headless_sdk.getUsersForSiteJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)
        return [UserForSiteResponse(**item) for item in get_success_result(response)]
%}