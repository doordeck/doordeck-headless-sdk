%pythoncode %{

@dataclass
class assisted_login_response:
    requiresVerification: bool

@dataclass
class assisted_register_ephemeral_key_response:
    requiresVerification: bool

%}