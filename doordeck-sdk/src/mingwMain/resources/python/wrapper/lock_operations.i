%pythoncode %{

def unlock(thiz, data):
    response = json.loads(_doordeck_headless_sdk.unlockJson(thiz, json.dumps(dataclasses.asdict(data))))
    handle_exception(response)

%}