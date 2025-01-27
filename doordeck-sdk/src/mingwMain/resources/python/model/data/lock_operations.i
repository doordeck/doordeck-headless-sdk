%pythoncode %{

@dataclass
class base_operation_data:
    lockId: str
    userId: Optional[str] = None
    userCertificateChain: Optional[List[str]] = None
    userPrivateKey: Optional[str] = None
    notBefore: int = field(default_factory=current_epoch_seconds)
    issuedAt: int = field(default_factory=current_epoch_seconds)
    expiresAt: int = field(default_factory=lambda: current_epoch_seconds() + 60)  # 1 minute from now
    jti: str = field(default_factory=lambda: str(uuid.uuid4()))

@dataclass
class unlock_operation_data:
    baseOperation: base_operation_data
    directAccessEndpoints: Optional[List[str]] = None

@dataclass
class operation_context_data:
    userId: str
    userCertificateChain: str
    userPublicKey: str
    userPrivateKey: str

%}