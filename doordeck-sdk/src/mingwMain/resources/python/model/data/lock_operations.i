%pythoncode %{

@dataclass
class TimeRequirementData:
    start: str
    end: str
    timezone: str
    days: typing.List[str]

@dataclass
class LocationRequirementData:
    latitude: float
    longitude: float
    enabled: typing.Optional[bool] = None
    radius: typing.Optional[int] = None
    accuracy: typing.Optional[int] = None

@dataclass
class ShareLockData:
    targetUserId: str
    targetUserRole: UserRole
    targetUserPublicKey: str
    start: typing.Optional[int] = None
    end: typing.Optional[int] = None

@dataclass
class BaseOperationData:
    lockId: str
    userId: typing.Optional[str] = None
    userCertificateChain: typing.Optional[typing.List[str]] = None
    userPrivateKey: typing.Optional[str] = None
    notBefore: int = field(default_factory=current_epoch_seconds)
    issuedAt: int = field(default_factory=current_epoch_seconds)
    expiresAt: int = field(default_factory=lambda: current_epoch_seconds() + 60)  # 1 minute from now
    jti: str = field(default_factory=lambda: str(uuid.uuid4()))

@dataclass
class ShareLockOperationData:
    baseOperation: BaseOperationData
    shareLock: ShareLockData

@dataclass
class BatchShareLockOperationData:
    baseOperation: BaseOperationData
    users: typing.List[ShareLockData]

@dataclass
class RevokeAccessToLockOperationData:
    baseOperation: BaseOperationData
    users: typing.List[str]

@dataclass
class UpdateSecureSettingUnlockDurationData:
    baseOperation: BaseOperationData
    unlockDuration: int

@dataclass
class UnlockBetweenData:
    start: str
    end: str
    timezone: str
    days: typing.List[str]
    exceptions: typing.Optional[typing.List[str]] = None

@dataclass
class UnlockOperationData:
    baseOperation: BaseOperationData
    directAccessEndpoints: typing.Optional[typing.List[str]] = None

@dataclass
class UpdateSecureSettingUnlockBetweenData:
    baseOperation: BaseOperationData
    unlockBetween: typing.Optional[UnlockBetweenData] = None
%}