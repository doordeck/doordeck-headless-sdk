%pythoncode %{

@dataclass
class GetLocksBelongingToTileData:
    tileId: str

@dataclass
class AssociateMultipleLocksData:
    tileId: str
    siteId: str
    lockIds: typing.List[str]

%}