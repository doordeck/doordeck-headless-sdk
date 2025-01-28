%pythoncode %{

@dataclass
class token_response:
    authToken: str
    refreshToken: str

@dataclass
class user_details_response:
    email: str
    emailVerified: bool
    publicKey: str
    displayName: typing.Optional[str] = None

@dataclass
class register_ephemeral_key_response:
    certificateChain: [typing.List[str]]
    userId: str

@dataclass
class register_ephemeral_key_with_secondary_authentication_response:
    method: two_factor_method

%}