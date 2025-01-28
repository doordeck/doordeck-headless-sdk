%pythoncode %{

@dataclass
class get_single_lock_data:
    lockId: str

@dataclass
class get_lock_audit_trail_data:
    lockId: str
    start: int
    end: int

@dataclass
class get_audit_for_user_data:
    userId: str
    start: int
    end: int

@dataclass
class get_users_for_lock_data:
    lockId: str

@dataclass
class get_locks_for_user_data:
    userId: str

@dataclass
class update_lock_name_dData:
    lockId: str
    name: typing.Optional[str] = None

@dataclass
class update_lock_favourite_data:
    lockId: str
    favourite: typing.Optional[bool] = None

@dataclass
class update_lock_colour_data:
    lockId: str
    colour: typing.Optional[str] = None

@dataclass
class update_lock_setting_default_name_data:
    lockId: str
    name: typing.Optional[str] = None

@dataclass
class set_lock_setting_permitted_addresses_data:
    lockId: str
    permittedAddresses: typing.List[str]

@dataclass
class update_lock_setting_hidden_data:
    lockId: str
    hidden: bool

@dataclass
class time_requirement_data:
    start: str
    end: str
    timezone: str
    days: typing.List[str]

@dataclass
class set_lock_setting_time_restrictions_data:
    lockId: str
    times: typing.List[time_requirement_data]

@dataclass
class location_requirement_data:
    latitude: float
    longitude: float
    enabled: typing.Optional[bool] = None
    radius: typing.Optional[int] = None
    accuracy: typing.Optional[int] = None

@dataclass
class update_lock_setting_location_restrictions_data:
    lockId: str
    location: typing.Optional[location_requirement_data] = None

@dataclass
class get_user_public_key_data:
    userEmail: str
    visitor: bool = False

@dataclass
class get_user_public_key_by_email_data:
    email: str

@dataclass
class get_user_public_key_by_telephone_data:
    telephone: str

@dataclass
class get_user_public_key_by_local_key_data:
    localKey: str

@dataclass
class get_user_public_key_by_foreign_key_data:
    foreignKey: str

@dataclass
class get_user_public_key_by_identity_data:
    identity: str

@dataclass
class get_user_public_key_by_emails_data:
    emails: typing.List[str]

@dataclass
class get_user_public_key_by_telephones_data:
    telephones: typing.List[str]

@dataclass
class get_user_public_key_by_local_keys_data:
    localKeys: typing.List[str]

@dataclass
class get_user_public_key_by_foreign_keys_data:
    foreignKeys: typing.List[str]

@dataclass
class share_lock_data:
    targetUserId: str
    targetUserRole: user_role
    targetUserPublicKey: str
    start: typing.Optional[int] = None
    end: typing.Optional[int] = None

@dataclass
class base_operation_data:
    lockId: str
    userId: typing.Optional[str] = None
    userCertificateChain: typing.Optional[typing.List[str]] = None
    userPrivateKey: typing.Optional[str] = None
    notBefore: int = field(default_factory=current_epoch_seconds)
    issuedAt: int = field(default_factory=current_epoch_seconds)
    expiresAt: int = field(default_factory=lambda: current_epoch_seconds() + 60)  # 1 minute from now
    jti: str = field(default_factory=lambda: str(uuid.uuid4()))

@dataclass
class share_lock_operation_data:
    baseOperation: base_operation_data
    shareLock: share_lock_data

@dataclass
class batch_share_lock_operation_data:
    baseOperation: base_operation_data
    users: typing.List[share_lock_data]

@dataclass
class revoke_access_to_lock_operation_data:
    baseOperation: base_operation_data
    users: typing.List[str]

@dataclass
class update_secure_setting_unlock_duration_data:
    baseOperation: base_operation_data
    unlockDuration: int

@dataclass
class unlock_between_data:
    start: str
    end: str
    timezone: str
    days: typing.List[str]
    exceptions: typing.Optional[typing.List[str]] = None

@dataclass
class unlock_operation_data:
    baseOperation: base_operation_data
    directAccessEndpoints: typing.Optional[typing.List[str]] = None

@dataclass
class update_secure_setting_unlock_between_data:
    baseOperation: base_operation_data
    unlockBetween: typing.Optional[unlock_between_data] = None

%}