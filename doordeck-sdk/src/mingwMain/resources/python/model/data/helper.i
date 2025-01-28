%pythoncode %{

@dataclass
class upload_platform_logo_data:
    applicationId: str
    contentType: str
    image: str

@dataclass
class assisted_login_data:
    email: str
    password: str

@dataclass
class assisted_register_ephemeral_key_data:
    publicKey: str

@dataclass
class assisted_register_data:
    email: str
    password: str
    displayName: typing.Optional[str] = None
    force: bool = False

%}