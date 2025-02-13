%pythoncode %{
@dataclass
class LoginData:
    email: str
    password: str

@dataclass
class RegistrationData:
    email: str
    password: str
    force: str
    publicKey: str
    displayName: typing.Optional[str] = None

@dataclass
class VerifyEmailData:
    code: str

@dataclass
class PasswordResetData:
    email: str

@dataclass
class PasswordResetVerifyData:
    userId: str
    token: str
    password: str
%}