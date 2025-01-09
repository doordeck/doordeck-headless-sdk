using System.Text.Json.Serialization;

namespace Doordeck.Headless.Sdk.Model.Responses
{
    public class FusionLoginResponse
    {
        public string AuthToken { get; set; } = string.Empty;
    }
    
    public class IntegrationTypeResponse
    {
        public string Status { get; set; } = string.Empty;
    }
    
    public class DoorStateResponse
    {
        public ServiceStateType State { get; set; } = ServiceStateType.UNDEFINED;
    }
    
    public class IntegrationConfigurationResponse
    {
        public ControllerResponse? Doordeck { get; set; } = null;
        public ServiceStateResponse? Service { get; set; } = null;
        public DiscoveredDeviceResponse? Integration { get; set; } = null;
    }
    
    public class ControllerResponse
    {
        public string Id { get; set; } = string.Empty;
        public string? Name { get; set; } = null;
        public string? Role { get; set; } = null;
    }
    
    public class ServiceStateResponse
    {
        public ServiceStateType State { get; set; } = ServiceStateType.UNDEFINED;
    }
    
    public class DiscoveredDeviceResponse
    {
        public ILockController Key { get; set; } = new DemoController();
        public Dictionary<string, string> Metadata { get; set; } = new();
    }

    [JsonConverter(typeof(JsonStringEnumConverter))]
    public enum ServiceStateType
    {
        RUNNING,
        STOPPED,
        UNDEFINED
    }
}