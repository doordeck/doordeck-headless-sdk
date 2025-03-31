%pythoncode %{
@dataclass
class LockController:
    type: str = field(init=False)

@dataclass
class DemoController(LockController):
    type: str = field(init=False)
    port: int = 8080

    def __post_init__(self):
        self.type = "demo"
%}