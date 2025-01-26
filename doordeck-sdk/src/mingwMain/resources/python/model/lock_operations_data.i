%pythoncode %{

@dataclass
class BaseOperationData:
    lockId: str
    userId: Optional[str] = None
    userCertificateChain: Optional[List[str]] = None
    userPrivateKey: Optional[str] = None
    notBefore: int = field(default_factory=current_epoch_seconds)
    issuedAt: int = field(default_factory=current_epoch_seconds)
    expiresAt: int = field(default_factory=lambda: current_epoch_seconds() + 60)  # 1 minute from now
    jti: str = field(default_factory=lambda: str(uuid.uuid4()))

@dataclass
class UnlockOperationData:
    baseOperation: BaseOperationData
    directAccessEndpoints: Optional[List[str]] = None

@dataclass
class OperationContextData:
    userId: str
    userCertificateChain: str
    userPublicKey: str
    userPrivateKey: str

%}