%pythoncode %{

def login(thiz, data):
    response = json.load(_DoordeckHeadlessSdk.loginJson(thiz, json.dumps(dataclasses.asdict(data))))
    if response["failure"] != None:
        handle_exception(**response["failure"]["exceptionType"], **response["failure"]["exceptionMessage"])
    else:
        return TokenResponse(**l["success"]["result"])
%}