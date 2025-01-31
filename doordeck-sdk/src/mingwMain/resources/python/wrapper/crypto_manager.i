%pythoncode %{

class CryptoManager(object):

    def __init__(self, resource):
        self.resource = resource

    def generate_key_pair(self):
        response = json.loads(_doordeck_headless_sdk.generateEncodedKeyPair(self.resource))
        return EncodedKeyPair(**response)

%}