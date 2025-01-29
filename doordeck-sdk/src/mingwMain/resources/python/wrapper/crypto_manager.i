%pythoncode %{

def generate_key_pair(thiz):
    response = json.loads(_doordeck_headless_sdk.generateEncodedKeyPair(thiz))
    return EncodedKeyPair(**response)

%}