%pythoncode %{

@dataclass
class EmailCallToActionResponse:
    actionTarget: str
    headline: str
    actionText: str

@dataclass
class EmailPreferencesResponse:
    primaryColour: str
    secondaryColour: str
    senderEmail: typing.Optional[str] = None
    senderName: typing.Optional[str] = None
    onlySendEssentialEmails: typing.Optional[bool] = None
    callToAction: typing.Optional[EmailCallToActionResponse] = None

@dataclass
class OauthResponse:
    authorizationEndpoint: str
    clientId: str
    grantType: str

@dataclass
class AuthKeyResponse:
    kid: str
    use: str
    alg: Optional[str]
    ops: Optional[List[str]]
    x5u: Optional[str]
    x5t: Optional[str]
    x5t256: Optional[str]
    x5c: Optional[List[str]]
    exp: Optional[int]
    nbf: Optional[int]
    iat: Optional[int]

@dataclass
class RsaKeyResponse(AuthKeyResponse):
    e: str
    n: str

@dataclass
class EcKeyResponse(AuthKeyResponse):
    crv: str
    x: str
    y: str

@dataclass
class Ed25519KeyResponse(AuthKeyResponse):
    crv: str
    x: str
    d: Optional[str] = None

@dataclass
class ApplicationResponse:
   applicationId: str
   name: str
   emailPreferences: EmailPreferencesResponse
   oauth: typing.Optional[OauthResponse]
   lastUpdated: typing.Optional[float] = None
   owners: typing.Optional[typing.List[str]] = None
   corsDomains: typing.Optional[typing.List[str]] = None
   authDomains: typing.Optional[typing.List[str]] = None
   logoUrl: typing.Optional[str] = None
   privacyPolicy: typing.Optional[str] = None
   mailingAddress: typing.Optional[str] = None
   companyName: typing.Optional[str] = None
   supportContact: typing.Optional[str] = None
   appLink: typing.Optional[str] = None
   slug: typing.Optional[str] = None
   authKeys: typing.Dict[str, AuthKeyResponse] = field(default_factory=dict)
   isDoordeckApplication: typing.Optional[bool] = None

@dataclass
class ApplicationOwnerDetailsResponse:
    userId: str
    email: str
    orphan: bool
    foreign: bool
    displayName: typing.Optional[str] = None

@dataclass
class GetLogoUploadUrlResponse:
    uploadUrl: str

%}