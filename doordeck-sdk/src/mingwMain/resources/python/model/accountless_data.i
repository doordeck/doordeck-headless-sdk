%pythoncode %{

@dataclass
class LoginData:
     email: str
     password: str

@dataclass
class RegistrationData:
     email: str
     password: str
     display_name: str
     force: str
     public_key: str

@dataclass
class VerifyEmailData:
     code: str

@dataclass
class PasswordResetData:
     email: str

@dataclass
class PasswordResetVerifyData:
     user_id: str
     token: str
     password: str

%}