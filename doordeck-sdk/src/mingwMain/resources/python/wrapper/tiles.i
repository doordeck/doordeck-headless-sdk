%pythoncode %{
class Tiles(object):

    def __init__(self, resource):
        self.resource = resource

    async def get_locks_belonging_to_tile(self, data):
        return await execute_async(
            _doordeck_headless_sdk.getLocksBelongingToTile,
            [self.resource, json.dumps(dataclasses.asdict(data))],
            lambda r: TileLocksResponse(**get_success_result(r))
        )

    async def associate_multiple_locks(self, data):
        return await execute_async(
            _doordeck_headless_sdk.associateMultipleLocks,
            [self.resource, json.dumps(dataclasses.asdict(data))]
        )
%}