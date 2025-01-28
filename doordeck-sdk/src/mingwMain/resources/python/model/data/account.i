%pythoncode %{

@dataclass
class refresh_token_data:
    refreshToken: str

@dataclass
class register_ephemeral_key_data:
    publicKey: str

@dataclass
class register_ephemeral_key_withSecondary_authentication_data:
    publicKey: str
    method: typing.Optional[two_factor_method] = None

@dataclass
class verify_ephemeral_key_registration_data:
        code: str
        privateKey: typing.Optional[str] = None

@dataclass
class change_password_data:
    oldPassword: str
    newPassword: str

@dataclass
class update_user_details_data:
    displayName: str

%}