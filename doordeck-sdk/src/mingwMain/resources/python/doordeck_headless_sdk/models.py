"""Payload types accepted by the generated wrappers."""

from __future__ import annotations

import typing
from dataclasses import dataclass, field

from .utils import current_epoch_millis


@dataclass
class EmailCallToAction:
    actionTarget: str
    headline: str
    actionText: str

@dataclass
class EmailPreferences:
    senderEmail: typing.Optional[str] = None
    senderName: typing.Optional[str] = None
    primaryColour: typing.Optional[str] = None
    secondaryColour: typing.Optional[str] = None
    onlySendEssentialEmails: typing.Optional[bool] = None
    callToAction: typing.Optional[EmailCallToAction] = None

@dataclass
class CreateApplication:
    name: str
    companyName: str
    mailingAddress: str
    privacyPolicy: typing.Optional[str] = None
    supportContact: typing.Optional[str] = None
    appLink: typing.Optional[str] = None
    emailPreferences: typing.Optional[EmailPreferences] = None
    logoUrl: typing.Optional[str] = None

@dataclass
class AuthKey:
    use: str
    kid: str
    alg: typing.Optional[str]
    kty: str = field(init=False)

@dataclass
class RsaKey(AuthKey):
    kty: str
    p: str
    q: str
    d: str
    e: str
    qi: str
    dp: str
    dq: str
    n: str

    def __post_init__(self):
        self.kty = "RSA"

@dataclass
class EcKey(AuthKey):
    kty: str
    d: str
    crv: str
    x: str
    y: str

    def __post_init__(self):
        self.kty = "EC"

@dataclass
class Ed25519Key(AuthKey):
    kty: str
    d: str
    crv: str
    x: str

    def __post_init__(self):
        self.kty = "OKP"

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
    enabled: bool = False
    radius: int = 100
    accuracy: int = 200

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
    notBefore: int = field(default_factory=current_epoch_millis)
    issuedAt: int = field(default_factory=current_epoch_millis)
    expiresAt: int = field(default_factory=lambda: current_epoch_millis() + 60000)  # 1 minute from now
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

@dataclass
class LockController:
    type: str = field(init=False)

@dataclass
class AlpetaController(LockController):
    type: str = field(init=False)
    username: str
    password: str
    doorId: int
    baseUrl: typing.Optional[str] = None

    def __post_init__(self):
        self.type = "alpeta"

@dataclass
class AmagController(LockController):
    type: str = field(init=False)
    username: str
    password: str
    doorId: int
    baseUrl: typing.Optional[str] = None

    def __post_init__(self):
        self.type = "amag"

@dataclass
class AssaAbloyController(LockController):
    type: str = field(init=False)
    baseUrl: str
    doorId: str

    def __post_init__(self):
        self.type = "assa-abloy"

@dataclass
class AvigilonController(LockController):
    type: str = field(init=False)
    baseUrl: str
    username: str
    password: str
    doorId: str

    def __post_init__(self):
        self.type = "avigilon"

@dataclass
class AxisController(LockController):
    type: str = field(init=False)
    baseUrl: str
    doorIdentifier: str

    def __post_init__(self):
        self.type = "axis"

@dataclass
class AzureTlsConfig:
    certificate: str
    trustedCertificate: str
    privateKey: str
    privateKeyPassword: str

@dataclass
class AzureController(LockController):
    type: str = field(init=False)
    host: str
    port: int
    tlsConfig: AzureTlsConfig
    accessPointId: int

    def __post_init__(self):
        self.type = "azure"

@dataclass
class CCureController(LockController):
    type: str = field(init=False)
    baseUrl: str
    username: str
    password: str
    doorType: str
    doorId: int

    def __post_init__(self):
        self.type = "ccure"

@dataclass
class DemoController(LockController):
    type: str = field(init=False)
    port: int = 8080

    def __post_init__(self):
        self.type = "demo"

@dataclass
class GallagherController(LockController):
    type: str = field(init=False)
    apiKey: str
    doorId: str
    baseUrl: typing.Optional[str] = None

    def __post_init__(self):
        self.type = "gallagher"

@dataclass
class GenetecController(LockController):
    type: str = field(init=False)
    baseUrl: str
    username: str
    password: str
    doorId: str

    def __post_init__(self):
        self.type = "genetec"

@dataclass
class LenelController(LockController):
    type: str = field(init=False)
    baseUrl: str
    username: str
    password: str
    directoryId: str
    panelId: str
    readerId: str

    def __post_init__(self):
        self.type = "lenel"

@dataclass
class MitrefinchController(LockController):
    type: str = field(init=False)
    host: str
    output: int

    def __post_init__(self):
        self.type = "mitrefinch"

@dataclass
class PaxtonNet2Controller(LockController):
    type: str = field(init=False)
    host: str
    address: str
    output: int
    username: typing.Optional[str] = None
    password: typing.Optional[str] = None

    def __post_init__(self):
        self.type = "net2"

@dataclass
class Paxton10Controller(LockController):
    type: str = field(init=False)
    baseUrl: str
    username: str
    password: str
    applianceId: int

    def __post_init__(self):
        self.type = "paxton10"

@dataclass
class IntegraV1Controller(LockController):
    type: str = field(init=False)
    username: str
    password: str
    controllerId: int

    def __post_init__(self):
        self.type = "integra"

@dataclass
class IntegraV2Controller(LockController):
    type: str = field(init=False)
    baseUrl: str
    sessionId: str
    controllerId: int
    cardholderId: int
    pinCode: typing.Optional[int] = None

    def __post_init__(self):
        self.type = "integra-v2"

@dataclass
class DataSource:
    driverClass: str
    url: str
    user: str
    password: str

@dataclass
class PacController(LockController):
    type: str = field(init=False)
    dataSource: DataSource
    outputChannel: int
    controllerSerial: int

    def __post_init__(self):
        self.type = "pac512"

@dataclass
class TdsiExgardeController(LockController):
    type: str = field(init=False)
    username: str
    password: str
    doorId: int
    dbUrl: typing.Optional[str] = None

    def __post_init__(self):
        self.type = "tdsi-exgarde"

@dataclass
class TdsiGardisController(LockController):
    type: str = field(init=False)
    host: str
    username: str
    password: str
    doorId: int

    def __post_init__(self):
        self.type = "tdsi-gardis"

@dataclass
class ZktecoController(LockController):
    type: str = field(init=False)
    clientSecret: str
    doorId: str
    entityType: typing.Literal["DOOR", "FLOOR"]
    baseUrl: typing.Optional[str] = None

    def __post_init__(self):
        self.type = "zkteco-zkbio-cvsecurity"
