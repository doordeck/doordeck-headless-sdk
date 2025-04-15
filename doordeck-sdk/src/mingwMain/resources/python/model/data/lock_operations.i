%pythoncode %{

@dataclass
class TimeRequirement:
    start: str
    end: str
    timezone: str
    days: typing.List[str]

@dataclass
class LocationRequirement:
    latitude: float
    longitude: float
    enabled: typing.Optional[bool] = None
    radius: int = 20
    accuracy: int = 20

@dataclass
class ShareLock:
    targetUserId: str
    targetUserRole: typing.Literal["ADMIN", "USER"]
    targetUserPublicKey: str
    start: typing.Optional[int] = None
    end: typing.Optional[int] = None

@dataclass
class BaseOperation:
    lockId: str
    userId: typing.Optional[str] = None
    userCertificateChain: typing.Optional[typing.List[str]] = None
    userPrivateKey: typing.Optional[str] = None
    notBefore: int = field(default_factory=current_epoch_seconds)
    issuedAt: int = field(default_factory=current_epoch_seconds)
    expiresAt: int = field(default_factory=lambda: current_epoch_seconds() + 60)  # 1 minute from now
    jti: str = field(default_factory=lambda: str(uuid.uuid4()))

@dataclass
class ShareLockOperation:
    baseOperation: BaseOperation
    shareLock: ShareLock

@dataclass
class BatchShareLockOperation:
    baseOperation: BaseOperation
    users: typing.List[ShareLock]

@dataclass
class RevokeAccessToLockOperation:
    baseOperation: BaseOperation
    users: typing.List[str]

@dataclass
class UpdateSecureSettingUnlockDuration:
    baseOperation: BaseOperation
    unlockDuration: int

@dataclass
class UnlockBetween:
    start: str
    end: str
    timezone: str
    days: typing.List[str]
    exceptions: typing.Optional[typing.List[str]] = None

@dataclass
class UnlockOperation:
    baseOperation: BaseOperation
    directAccessEndpoints: typing.Optional[typing.List[str]] = None

@dataclass
class UpdateSecureSettingUnlockBetween:
    baseOperation: BaseOperation
    unlockBetween: typing.Optional[UnlockBetween] = None
%}