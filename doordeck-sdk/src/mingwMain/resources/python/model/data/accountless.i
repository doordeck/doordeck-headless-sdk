%pythoncode %{

@dataclass
class login_data:
     email: str
     password: str

@dataclass
class registration_data:
     email: str
     password: str
     force: str
     publicKey: str
     displayName: typing.Optional[str] = None

@dataclass
class verify_email_data:
     code: str

@dataclass
class password_reset_data:
     email: str

@dataclass
class password_reset_verify_data:
     user_id: str
     token: str
     password: str

%}