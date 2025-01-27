%pythoncode %{

@dataclass
class tile_locks_response:
    siteId: str
    tileId: str
    deviceIds: List[str]

%}