%pythoncode %{

@dataclass
class EmailCallToActionData:
    actionTarget: str
    headline: str
    actionText: str

@dataclass
class EmailPreferencesData:
    senderEmail: typing.Optional[str] = None
    senderName: typing.Optional[str] = None
    primaryColour: typing.Optional[str] = None
    secondaryColour: typing.Optional[str] = None
    onlySendEssentialEmails: typing.Optional[bool] = None
    callToAction: typing.Optional[EmailCallToActionData] = None

@dataclass
class CreateApplicationData:
    name: str
    companyName: str
    mailingAddress: str
    privacyPolicy: typing.Optional[str] = None
    supportContact: typing.Optional[str] = None
    appLink: typing.Optional[str] = None
    emailPreferences: typing.Optional[EmailPreferencesData] = None
    logoUrl: typing.Optional[str] = None

@dataclass
class GetApplicationData:
    applicationId: str

@dataclass
class UpdateApplicationNameData:
    applicationId: str
    name: str

@dataclass
class UpdateApplicationCompanyNameData:
    applicationId: str
    companyName: str

@dataclass
class UpdateApplicationMailingAddressData:
    applicationId: str
    mailingAddress: str

@dataclass
class UpdateApplicationPrivacyPolicyData:
    applicationId: str
    privacyPolicy: str

@dataclass
class UpdateApplicationSupportContactData:
    applicationId: str
    supportContact: str

@dataclass
class UpdateApplicationAppLinkData:
    applicationId: str
    appLink: str

@dataclass
class UpdateApplicationEmailPreferencesData:
    applicationId: str
    emailPreferences: EmailPreferencesData

@dataclass
class UpdateApplicationLogoUrlData:
    applicationId: str
    logoUrl: str

@dataclass
class DeleteApplicationData:
    applicationId: str

@dataclass
class GetLogoUploadUrlData:
    applicationId: str
    contentType: str

@dataclass
class AuthKeyData(ABC):
    kid: str
    kty: str
    use: str
    alg: Optional[str] = None

    @abstractmethod
    def __init__(self, kid: str, kty: str, use: str, alg: Optional[str] = None):
        self.kid = kid
        self.kty = kty
        self.use = use
        self.alg = alg

@dataclass
class RsaKeyData(AuthKeyData):
    kty: str = "RSA"

    def __init__(
        self,
        kid: str,
        use: str,
        p: str,
        q: str,
        d: str,
        e: str,
        qi: str,
        dp: str,
        dq: str,
        n: str,
        alg: Optional[str] = None,
    ):
        super().__init__(kid, self.kty, use, alg)
        self.p = p
        self.q = q
        self.d = d
        self.e = e
        self.qi = qi
        self.dp = dp
        self.dq = dq
        self.n = n

@dataclass
class EcKeyData(AuthKeyData):
    kty: str = "EC"

    def __init__(
        self,
        kid: str,
        use: str,
        d: str,
        crv: str,
        x: str,
        y: str,
        alg: Optional[str] = None,
    ):
        super().__init__(kid, self.kty, use, alg)
        self.d = d
        self.crv = crv
        self.x = x
        self.y = y

@dataclass
class Ed25519KeyData(AuthKeyData):
    kty: str = "OKP"

    def __init__(
        self,
        kid: str,
        use: str,
        d: str,
        crv: str,
        x: str,
        alg: Optional[str] = None,
    ):
        super().__init__(kid, self.kty, use, alg)
        self.d = d
        self.crv = crv
        self.x = x

@dataclass
class AddAuthKeyData:
    applicationId: str
    key: AuthKeyData

@dataclass
class AddAuthIssuerData:
    applicationId: str
    url: str

@dataclass
class DeleteAuthIssuerData:
    applicationId: str
    url: str

@dataclass
class AddCorsDomainData:
    applicationId: str
    url: str

@dataclass
class RemoveCorsDomainData:
    applicationId: str
    url: str

@dataclass
class AddApplicationOwnerData:
    applicationId: str
    userId: str

@dataclass
class RemoveApplicationOwnerData:
    applicationId: str
    userId: str

@dataclass
class GetApplicationOwnersDetailsData:
    applicationId: str

%}