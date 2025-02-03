%pythoncode %{
@dataclass
class OperationContextData:
    userId: str
    userCertificateChain: str
    userPublicKey: str
    userPrivateKey: str
%}