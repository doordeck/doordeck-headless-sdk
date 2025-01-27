%pythoncode %{

@dataclass
class success_result_data:
    result: typing.Any

@dataclass
class failed_result_data:
    exceptionType: str
    exceptionMessage: str

@dataclass
class result_data:
    success: typing.Optional[success_result_data] = None
    failure: typing.Optional[failed_result_data] = None

%}