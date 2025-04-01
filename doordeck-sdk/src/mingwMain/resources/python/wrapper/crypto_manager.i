%pythoncode %{
class CryptoManager(object):

    def __init__(self, resource):
        self.resource = resource

    def generate_key_pair(self):
        return json.loads(_doordeck_headless_sdk.generateEncodedKeyPair(self.resource), object_hook=object_hook)
%}