%pythoncode %{
class Tiles(object):

    def __init__(self, resource):
        self.resource = resource

    async def get_locks_belonging_to_tile(self, tileId: str):
        data = { "tileId": tileId }
        return await execute_async(
            _doordeck_headless_sdk.getLocksBelongingToTile,
            [self.resource, json.dumps(data)]
        )

    async def associate_multiple_locks(self, tileId: str, siteId: str, lockIds: List[str]):
        data = {
            "tileId": tileId,
            "siteId": siteId,
            "lockIds": lockIds
        }
        return await execute_async(
            _doordeck_headless_sdk.associateMultipleLocks,
            [self.resource, json.dumps(data)]
        )
%}