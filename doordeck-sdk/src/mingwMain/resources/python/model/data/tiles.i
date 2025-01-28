%pythoncode %{

@dataclass
class get_locks_belonging_to_tile_data:
    tileId: str

@dataclass
class associate_multiple_locks_data:
    tileId: str
    siteId: str
    lockIds: typing.List[str]

%}