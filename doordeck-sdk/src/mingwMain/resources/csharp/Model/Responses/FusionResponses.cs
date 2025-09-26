using System.Text.Json.Serialization;

namespace Doordeck.Headless.Sdk.Model.Responses;

public class FusionLoginResponse
{
    public required string AuthToken { get; set; }
}
    
public class IntegrationTypeResponse
{
    public string? Status { get; set; }
}
    
public class DoorStateResponse
{
    public required ServiceStateType State { get; set; }
}
    
public class IntegrationConfigurationResponse
{
    public ControllerResponse? Doordeck { get; set; }
    public ServiceStateResponse? Service { get; set; }
    public DiscoveredDeviceResponse? Integration { get; set; }
}
    
public class ControllerResponse
{
    public required Guid Id { get; set; }
    public string? Name { get; set; }
    public UserRole? Role { get; set; }
}
    
public class ServiceStateResponse
{
    public required ServiceStateType State { get; set; }
}
    
public class DiscoveredDeviceResponse
{
    public required LockController Key { get; set; }
    public required Dictionary<string, string> Metadata { get; set; }
}

[JsonConverter(typeof(JsonStringEnumConverter))]
public enum ServiceStateType
{
    RUNNING,
    STOPPED,
    UNDEFINED
}