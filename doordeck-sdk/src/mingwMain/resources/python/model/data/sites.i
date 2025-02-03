%pythoncode %{
@dataclass
class GetLocksForSiteData:
    siteId: str

@dataclass
class GetUsersForSiteData:
    siteId: str
%}