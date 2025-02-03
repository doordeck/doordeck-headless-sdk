%pythoncode %{
@dataclass
class TileLocksResponse:
    siteId: str
    tileId: str
    deviceIds: typing.List[str]
%}