%pythoncode %{
class ServiceStateType(Enum):
    RUNNING = "RUNNING"
    STOPPED = "STOPPED"
    UNDEFINED = "UNDEFINED"

@dataclass
class FusionLoginResponse:
    authToken: str

@dataclass
class IntegrationTypeResponse:
    status: str

@dataclass
class DoorStateResponse:
    state: str

@dataclass
class ControllerResponse:
    id: str
    name: typing.Optional[str] = None
    role: typing.Optional[UserRole] = None

@dataclass
class ServiceStateResponse:
    state: ServiceStateType

@dataclass
class DiscoveredDeviceResponse:
    key: LockController
    metadata: typing.Dict[str, str] = field(default_factory=dict)

@dataclass
class IntegrationConfigurationResponse:
    doordeck: typing.Optional[ControllerResponse] = None
    service: typing.Optional[ServiceStateResponse] = None
    integration: typing.Optional[DiscoveredDeviceResponse] = None
%}