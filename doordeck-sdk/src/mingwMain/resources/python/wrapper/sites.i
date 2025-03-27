%pythoncode %{
class Sites(object):

    def __init__(self, resource):
        self.resource = resource

    async def list_sites(self):
        return await execute_async(
            _doordeck_headless_sdk.listSites,
            [self.resource],
            lambda r: [SiteResponse(**item) for item in get_success_result(r)]
        )

    async def get_locks_for_site(self, data):
        return await execute_async(
            _doordeck_headless_sdk.getLocksForSite,
            [self.resource, json.dumps(dataclasses.asdict(data))],
            lambda r: [SiteLocksResponse(**item) for item in get_success_result(r)]
        )

    async def get_users_for_site(self, data):
        return await execute_async(
            _doordeck_headless_sdk.getUsersForSite,
            [self.resource, json.dumps(dataclasses.asdict(data))],
            lambda r: [UserForSiteResponse(**item) for item in get_success_result(r)]
        )
%}