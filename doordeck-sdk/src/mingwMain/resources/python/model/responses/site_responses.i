%pythoncode %{

@dataclass
class site_response:
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
class site_state_response:
    connected: bool

@dataclass
class user_for_site_response:
    userId: str
    email: str
    orphan: bool
    displayName: typing.Optional[str] = None

@dataclass
class site_lock_settings_response:
    unlockTime: float
    permittedAddresses: typing.List[str]
    defaultName: str
    tiles: typing.List[str]
    state: typing.Optional[site_state_response] = None
    favourite: typing.Optional[bool] = None

@dataclass
class site_locks_response:
    id: str
    name: str
    role: user_role
    settings: site_lock_settings_response
    colour: typing.Optional[str] = None

@dataclass
class tile_locks_response:
    siteId: str
    tileId: str
    deviceIds: typing.List[str]

%}