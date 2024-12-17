using System.Text.Json.Serialization;

namespace Doordeck.Headless.Sdk.model
{
    [JsonConverter(typeof(JsonStringEnumConverter))]
    public enum CapabilityStatus
    {
        SUPPORTED,
        UNSUPPORTED
    }

    [JsonConverter(typeof(JsonStringEnumConverter))]
    public enum CapabilityType
    {
        CONFIGURABLE_UNLOCK_DURATION,
        OPEN_HOURS,
        BATCH_SHARING_25
    }
}
