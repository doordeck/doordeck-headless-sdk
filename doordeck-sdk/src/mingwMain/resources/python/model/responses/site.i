%pythoncode %{

@dataclass
class SiteResponse:
    id: str
    name: str
    colour: str
    longitude: float
    latitude: float
    radius: int
    passBackground: str
    created: str
    updated: str

@dataclass
class SiteStateResponse:
    connected: bool

@dataclass
class UserForSiteResponse:
    userId: str
    email: str
    orphan: bool
    displayName: typing.Optional[str] = None

@dataclass
class SiteLockSettingsResponse:
    unlockTime: float
    permittedAddresses: typing.List[str]
    defaultName: str
    tiles: typing.List[str]
    state: typing.Optional[SiteStateResponse] = None
    favourite: typing.Optional[bool] = None

@dataclass
class SiteLocksResponse:
    id: str
    name: str
    role: UserRole
    settings: SiteLockSettingsResponse
    colour: typing.Optional[str] = None

@dataclass
class TileLocksResponse:
    siteId: str
    tileId: str
    deviceIds: typing.List[str]

%}