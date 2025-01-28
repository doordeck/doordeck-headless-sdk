%pythoncode %{

@dataclass
class audit_subject_response:
    userId: str
    email: str
    displayName: typing.Optional[str] = None

@dataclass
class audit_issuer_response:
    userId: str
    email: typing.Optional[str] = None
    ip: typing.Optional[str] = None

@dataclass
class audit_response:
    deviceId: str
    timestamp: float
    type: audit_event
    issuer: audit_issuer_response
    rejected: bool
    subject: typing.Optional[audit_subject_response] = None
    rejectionReason: typing.Optional[str] = None

@dataclass
class lock_user_details_response:
    deviceId: str
    role: user_role
    start: typing.Optional[float] = None
    end: typing.Optional[float] = None

@dataclass
class lock_user_response:
    userId: str
    email: str
    publicKey: str
    orphan: bool
    foreign: bool
    devices: typing.List[lock_user_details_response]
    displayName: typing.Optional[str] = None
    start: typing.Optional[float] = None
    end: typing.Optional[float] = None

@dataclass
class user_lock_response:
    userId: str
    email: str
    publicKey: str
    orphan: bool
    foreign: bool
    role: user_role
    displayName: typing.Optional[str] = None
    start: typing.Optional[float] = None
    end: typing.Optional[float] = None

@dataclass
class shareable_lock_response:
    id: str
    name: str

@dataclass
class batch_user_public_key_response:
    id: str
    publicKey: str
    email: typing.Optional[str] = None
    foreignKey: typing.Optional[str] = None
    phone: typing.Optional[str] = None

@dataclass
class user_public_key_response:
    id: str
    publicKey: str

@dataclass
class lock_state_response:
    locked: bool
    connected: bool

@dataclass
class unlock_between_setting_response:
    start: str
    end: str
    timezone: str
    days: typing.List[str]
    exceptions: typing.Optional[typing.List[str]] = None

@dataclass
class location_requirement_response:
    latitude: float
    longitude: float
    enabled: typing.Optional[bool] = None
    radius: typing.Optional[int] = None
    accuracy: typing.Optional[int] = None

@dataclass
class time_requirement_response:
    start: str
    end: str
    timezone: str
    days: typing.List[str]

@dataclass
class usage_requirements_response:
    time: typing.Optional[typing.List[time_requirement_response]] = None
    location: typing.Optional[location_requirement_response] = None

@dataclass
class lock_settings_response:
    unlockTime: float
    permittedAddresses: typing.List[str]
    defaultName: str
    tiles: typing.List[str]
    hidden: bool
    directAccessEndpoints: typing.List[str]
    capabilities: typing.Dict[capability_type, capability_status]
    usageRequirements: typing.Optional[usage_requirements_response] = None
    unlockBetweenWindow: typing.Optional[unlock_between_setting_response] = None

@dataclass
class lock_response:
    id: str
    name: str
    role: user_role
    settings: lock_settings_response
    state: lock_state_response
    favourite: bool
    colour: typing.Optional[str] = None
    start: typing.Optional[str] = None
    end: typing.Optional[str] = None
    unlockTime: typing.Optional[float] = None

%}