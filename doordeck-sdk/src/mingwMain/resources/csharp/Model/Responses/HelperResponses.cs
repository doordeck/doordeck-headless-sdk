namespace Doordeck.Headless.Sdk.Model.Responses;

public class AssistedLoginResponse
{
    public required bool RequiresVerification { get; set; }
    public required bool RequiresRetry { get; set; }
}

public class AssistedRegisterEphemeralKeyResponse
{
    public required bool RequiresVerification { get; set; }
    public required bool RequiresRetry { get; set; }
}