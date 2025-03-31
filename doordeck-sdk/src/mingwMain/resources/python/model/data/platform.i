%pythoncode %{
@dataclass
class EmailCallToAction:
    actionTarget: str
    headline: str
    actionText: str

@dataclass
class EmailPreferences:
    senderEmail: typing.Optional[str] = None
    senderName: typing.Optional[str] = None
    primaryColour: typing.Optional[str] = None
    secondaryColour: typing.Optional[str] = None
    onlySendEssentialEmails: typing.Optional[bool] = None
    callToAction: typing.Optional[EmailCallToAction] = None

@dataclass
class CreateApplication:
    name: str
    companyName: str
    mailingAddress: str
    privacyPolicy: typing.Optional[str] = None
    supportContact: typing.Optional[str] = None
    appLink: typing.Optional[str] = None
    emailPreferences: typing.Optional[EmailPreferences] = None
    logoUrl: typing.Optional[str] = None

@dataclass
class AuthKey:
    use: str
    kid: str
    alg: typing.Optional[str]
    kty: str = field(init=False)

@dataclass
class RsaKey(AuthKey):
    kty: str
    p: str
    q: str
    d: str
    e: str
    qi: str
    dp: str
    dq: str
    n: str

    def __post_init__(self):
        self.kty = "RSA"

@dataclass
class EcKey(AuthKey):
    kty: str
    d: str
    crv: str
    x: str
    y: str

    def __post_init__(self):
        self.kty = "EC"

@dataclass
class Ed25519Key(AuthKey):
    kty: str
    d: str
    crv: str
    x: str

    def __post_init__(self):
        self.kty = "OKP"
%}