%pythoncode %{
@dataclass
class UploadPlatformLogoData:
    applicationId: str
    contentType: str
    image: str

@dataclass
class AssistedLoginData:
    email: str
    password: str

@dataclass
class AssistedRegisterEphemeralKeyData:
    publicKey: str

@dataclass
class AssistedRegisterData:
    email: str
    password: str
    displayName: typing.Optional[str] = None
    force: bool = False
%}