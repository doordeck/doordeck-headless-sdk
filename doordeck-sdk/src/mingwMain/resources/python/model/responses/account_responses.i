%pythoncode %{

@dataclass
class TokenResponse:
    def __init__(self, auth_token: str, refresh_token: str):
        self.auth_token = auth_token
        self.refresh_token = refresh_token

@dataclass
class UserDetailsResponse:
    def __init__(self, email: str, email_verified: bool, public_key: str, display_name: str = None):
        self.email = email
        self.display_name = display_name
        self.email_verified = email_verified
        self.public_key = public_key

@dataclass
class RegisterEphemeralKeyResponse:
    def __init__(self, certificate_chain: list, user_id: str):
        self.certificate_chain = certificate_chain
        self.user_id = user_id

@dataclass
class RegisterEphemeralKeyWithSecondaryAuthenticationResponse:
    def __init__(self, method):
        self.method = method

%}