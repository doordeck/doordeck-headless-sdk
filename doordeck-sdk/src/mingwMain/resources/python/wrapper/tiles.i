%pythoncode %{
class Tiles(object):

    def __init__(self, resource):
        self.resource = resource

    def get_locks_belonging_to_tile(self, data):
        response = json.loads(_doordeck_headless_sdk.getLocksBelongingToTileJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)
        return TileLocksResponse(**get_success_result(response))

    def associate_multiple_locks(self, data):
        response = json.loads(_doordeck_headless_sdk.associateMultipleLocksJson(self.resource, json.dumps(dataclasses.asdict(data))))
        handle_exception(response)
%}