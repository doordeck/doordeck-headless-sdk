%pythoncode %{
@dataclass
class TokenResponse:
    authToken: str
    refreshToken: str

@dataclass
class UserDetailsResponse:
    email: str
    emailVerified: bool
    publicKey: str
    displayName: typing.Optional[str] = None

@dataclass
class RegisterEphemeralKeyResponse:
    certificateChain: [typing.List[str]]
    userId: str

@dataclass
class RegisterEphemeralKeyWithSecondaryAuthenticationResponse:
    method: TwoFactorMethod
%}