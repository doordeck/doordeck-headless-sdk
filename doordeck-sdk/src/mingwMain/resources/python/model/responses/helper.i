%pythoncode %{

@dataclass
class AssistedLoginResponse:
    requiresVerification: bool

@dataclass
class AssistedRegisterEphemeralKeyResponse:
    requiresVerification: bool

%}