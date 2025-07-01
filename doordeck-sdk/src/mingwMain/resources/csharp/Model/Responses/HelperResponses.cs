namespace Doordeck.Headless.Sdk.Model.Responses;

public class AssistedLoginResponse
{
    public bool RequiresVerification { get; set; }
    public bool RequiresRetry { get; set; }
}

public class AssistedRegisterEphemeralKeyResponse
{
    public bool RequiresVerification { get; set; }
    public bool RequiresRetry { get; set; }
}