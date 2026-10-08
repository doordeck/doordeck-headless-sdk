namespace Doordeck.Headless.Sdk.Model;

public class ResultData<T>
{
    public SuccessResultData<T>? Success { get; set; } = null;
    public FailedResultData? Failure { get; set; } = null;
}

public class SuccessResultData<T>
{
    public T? Result { get; set; } = default;
}

public class FailedResultData
{
    public string ExceptionType { get; set; } = string.Empty;
    public string ExceptionMessage { get; set; } = string.Empty;
}