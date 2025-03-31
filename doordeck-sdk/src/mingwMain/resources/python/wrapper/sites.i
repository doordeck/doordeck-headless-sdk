%pythoncode %{
class Sites(object):

    def __init__(self, resource):
        self.resource = resource

    async def list_sites(self):
        return await execute_async(
            _doordeck_headless_sdk.listSites,
            [self.resource]
        )

    async def get_locks_for_site(self, siteId: str):
        data = { "siteId": siteId }
        return await execute_async(
            _doordeck_headless_sdk.getLocksForSite,
            [self.resource, json.dumps(data)]
        )

    async def get_users_for_site(self, data: str):
        data = { "siteId": siteId }
        return await execute_async(
            _doordeck_headless_sdk.getUsersForSite,
            [self.resource, json.dumps(data)]
        )
%}