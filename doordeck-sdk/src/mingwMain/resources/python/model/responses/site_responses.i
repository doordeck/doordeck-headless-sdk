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
    displayName: Optional[str] = None

@dataclass
class site_lock_settings_response:
    unlockTime: float
    permittedAddresses: List[str]
    defaultName: str
    tiles: List[str]
    state: Optional[site_state_response] = None
    favourite: Optional[bool] = None

@dataclass
class site_locks_response:
    id: str
    name: str
    role: user_role
    settings: site_lock_settings_response
    colour: Optional[str] = None

@dataclass
class tile_locks_response:
    siteId: str
    tileId: str
    deviceIds: List[str]

%}