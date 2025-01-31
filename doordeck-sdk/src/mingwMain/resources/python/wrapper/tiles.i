%pythoncode %{

def get_locks_belonging_to_tile(thiz, data):
    response = json.loads(_doordeck_headless_sdk.getLocksBelongingToTileJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)
    return TileLocksResponse(**get_success_result(response))

def associate_multiple_locks(thiz, data):
    response = json.loads(_doordeck_headless_sdk.associateMultipleLocksJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

%}