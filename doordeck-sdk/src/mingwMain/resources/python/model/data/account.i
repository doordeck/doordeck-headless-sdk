%pythoncode %{

@dataclass
class RefreshTokenData:
    refreshToken: str

@dataclass
class RegisterEphemeralKeyData:
    publicKey: str

@dataclass
class RegisterEphemeralKeyWithSecondaryAuthenticationData:
    publicKey: str
    method: typing.Optional[TwoFactorMethod] = None

@dataclass
class VerifyEphemeralKeyRegistrationData:
    code: str
    privateKey: typing.Optional[str] = None

@dataclass
class ChangePasswordData:
    oldPassword: str
    newPassword: str

@dataclass
class UpdateUserDetailsData:
    displayName: str

%}