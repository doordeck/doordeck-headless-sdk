%pythoncode %{

@dataclass
class operation_context_data:
    userId: str
    userCertificateChain: str
    userPublicKey: str
    userPrivateKey: str

%}