%pythoncode %{
@dataclass
class LockController:
    type: str = field(init=False)

@dataclass
class AlpetaController(LockController):
    type: str = field(init=False)
    username: str
    password: str
    doorId: int
    baseUrl: str = None

    def __post_init__(self):
        self.type = "alpeta"

@dataclass
class AmagController(LockController):
    type: str = field(init=False)
    username: str
    password: str
    doorId: int
    baseUrl: str = None

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
    baseUrl: Optional[str] = None

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
    username: Optional[str] = None
    password: Optional[str] = None
    address: str
    output: int

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
    pinCode: Optional[int] = None

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
    dbUrl: Optional[str] = None

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
    baseUrl: str = None

    def __post_init__(self):
        self.type = "zkteco-zkbio-cvsecurity"
%}