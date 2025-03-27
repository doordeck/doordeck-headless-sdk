%pythoncode %{
@dataclass
class LockIdData:
    lockId: str

@dataclass
class GetLockAuditTrailData:
    lockId: str
    start: int
    end: int

@dataclass
class GetAuditForUserData:
    userId: str
    start: int
    end: int

@dataclass
class GetLocksForUserData:
    userId: str

@dataclass
class UpdateLockNameData:
    lockId: str
    name: typing.Optional[str] = None

@dataclass
class UpdateLockFavouriteData:
    lockId: str
    favourite: typing.Optional[bool] = None

@dataclass
class UpdateLockColourData:
    lockId: str
    colour: typing.Optional[str] = None

@dataclass
class UpdateLockSettingDefaultNameData:
    lockId: str
    name: typing.Optional[str] = None

@dataclass
class SetLockSettingPermittedAddressesData:
    lockId: str
    permittedAddresses: typing.List[str]

@dataclass
class UpdateLockSettingHiddenData:
    lockId: str
    hidden: bool

@dataclass
class TimeRequirementData:
    start: str
    end: str
    timezone: str
    days: typing.List[str]

@dataclass
class SetLockSettingTimeRestrictionsData:
    lockId: str
    times: typing.List[TimeRequirementData]

@dataclass
class LocationRequirementData:
    latitude: float
    longitude: float
    enabled: typing.Optional[bool] = None
    radius: typing.Optional[int] = None
    accuracy: typing.Optional[int] = None

@dataclass
class UpdateLockSettingLocationRestrictionsData:
    lockId: str
    location: typing.Optional[LocationRequirementData] = None

@dataclass
class GetUserPublicKeyData:
    userEmail: str
    visitor: bool = False

@dataclass
class GetUserPublicKeyByEmailData:
    email: str

@dataclass
class GetUserPublicKeyByTelephoneData:
    telephone: str

@dataclass
class GetUserPublicKeyByLocalKeyData:
    localKey: str

@dataclass
class GetUserPublicKeyByForeignKeyData:
    foreignKey: str

@dataclass
class GetUserPublicKeyByIdentityData:
    identity: str

@dataclass
class GetUserPublicKeyByEmailsData:
    emails: typing.List[str]

@dataclass
class GetUserPublicKeyByTelephonesData:
    telephones: typing.List[str]

@dataclass
class GetUserPublicKeyByLocalKeysData:
    localKeys: typing.List[str]

@dataclass
class GetUserPublicKeyByForeignKeysData:
    foreignKeys: typing.List[str]

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