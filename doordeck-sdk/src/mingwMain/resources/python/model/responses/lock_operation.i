%pythoncode %{

@dataclass
class AuditSubjectResponse:
    userId: str
    email: str
    displayName: typing.Optional[str] = None

@dataclass
class AuditIssuerResponse:
    userId: str
    email: typing.Optional[str] = None
    ip: typing.Optional[str] = None

@dataclass
class AuditResponse:
    deviceId: str
    timestamp: float
    type: AuditEvent
    issuer: AuditIssuerResponse
    rejected: bool
    subject: typing.Optional[AuditSubjectResponse] = None
    rejectionReason: typing.Optional[str] = None

@dataclass
class LockUserDetailsResponse:
    deviceId: str
    role: UserRole
    start: typing.Optional[float] = None
    end: typing.Optional[float] = None

@dataclass
class LockUserResponse:
    userId: str
    email: str
    publicKey: str
    orphan: bool
    foreign: bool
    devices: typing.List[LockUserDetailsResponse]
    displayName: typing.Optional[str] = None
    start: typing.Optional[float] = None
    end: typing.Optional[float] = None

@dataclass
class UserLockResponse:
    userId: str
    email: str
    publicKey: str
    orphan: bool
    foreign: bool
    role: UserRole
    displayName: typing.Optional[str] = None
    start: typing.Optional[float] = None
    end: typing.Optional[float] = None

@dataclass
class ShareableLockResponse:
    id: str
    name: str

@dataclass
class BatchUserPublicKeyResponse:
    id: str
    publicKey: str
    email: typing.Optional[str] = None
    foreignKey: typing.Optional[str] = None
    phone: typing.Optional[str] = None

@dataclass
class UserPublicKeyResponse:
    id: str
    publicKey: str

@dataclass
class LockStateResponse:
    locked: bool
    connected: bool

@dataclass
class UnlockBetweenSettingResponse:
    start: str
    end: str
    timezone: str
    days: typing.List[str]
    exceptions: typing.Optional[typing.List[str]] = None

@dataclass
class LocationRequirementResponse:
    latitude: float
    longitude: float
    enabled: typing.Optional[bool] = None
    radius: typing.Optional[int] = None
    accuracy: typing.Optional[int] = None

@dataclass
class TimeRequirementResponse:
    start: str
    end: str
    timezone: str
    days: typing.List[str]

@dataclass
class UsageRequirementsResponse:
    time: typing.Optional[typing.List[TimeRequirementResponse]] = None
    location: typing.Optional[LocationRequirementResponse] = None

@dataclass
class LockSettingsResponse:
    unlockTime: float
    permittedAddresses: typing.List[str]
    defaultName: str
    tiles: typing.List[str]
    hidden: bool
    directAccessEndpoints: typing.List[str]
    capabilities: typing.Dict[CapabilityType, CapabilityStatus]
    usageRequirements: typing.Optional[UsageRequirementsResponse] = None
    unlockBetweenWindow: typing.Optional[UnlockBetweenSettingResponse] = None

@dataclass
class LockResponse:
    id: str
    name: str
    role: UserRole
    settings: LockSettingsResponse
    state: LockStateResponse
    favourite: bool
    colour: typing.Optional[str] = None
    start: typing.Optional[str] = None
    end: typing.Optional[str] = None
    unlockTime: typing.Optional[float] = None

%}