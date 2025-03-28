%pythoncode %{
@dataclass
class LockController:
    str: type

@dataclass
class DemoController(LockController):
    str: str
    port: int = 8080

    def __post_init__(self):
        self.str = "demo"

@dataclass
class FusionLoginData:
    email: str
    password: str

@dataclass
class GetIntegrationConfigurationData:
    type: str

@dataclass
class EnableDoorData:
    name: str
    siteId: str
    controller: LockController

@dataclass
class DeviceIdData:
    deviceId: str
%}