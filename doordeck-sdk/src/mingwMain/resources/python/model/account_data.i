%pythoncode %{

@dataclass
class RefreshTokenData:
    def __init__(self, refresh_token: str):
        self.refresh_token = refresh_token

@dataclass
class RegisterEphemeralKeyData:
    def __init__(self, public_key: str):
        self.public_key = public_key

@dataclass
class RegisterEphemeralKeyWithSecondaryAuthenticationData:
    def __init__(self, public_key: str = None, method = None):
        self.public_key = public_key
        self.method = method

@dataclass
class VerifyEphemeralKeyRegistrationData:
    def __init__(self, code: str, private_key: str = None):
        self.code = code
        self.private_key = private_key

@dataclass
class ChangePasswordData:
    def __init__(self, old_password: str, new_password: str):
        self.old_password = old_password
        self.new_password = new_password

@dataclass
class UpdateUserDetailsData:
    def __init__(self, display_name: str):
        self.display_name = display_name

%}