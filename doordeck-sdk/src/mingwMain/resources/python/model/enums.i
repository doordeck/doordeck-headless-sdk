%pythoncode %{
class ApiEnvironment(Enum):
    DEV = "DEV"
    STAGING = "STAGING"
    PROD = "PROD"

class TwoFactorMethod(Enum):
    EMAIL = "EMAIL"
    TELEPHONE = "TELEPHONE"
    SMS = "SMS"

class UserRole(Enum):
    ADMIN = "ADMIN"
    USER = "USER"
%}