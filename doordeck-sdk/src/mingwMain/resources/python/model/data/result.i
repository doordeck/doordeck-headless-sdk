%pythoncode %{
@dataclass
class SuccessResultData:
    result: typing.Any

@dataclass
class FailedResultData:
    exceptionType: str
    exceptionMessage: str

@dataclass
class ResultData:
    success: typing.Optional[SuccessResultData] = None
    failure: typing.Optional[FailedResultData] = None
%}