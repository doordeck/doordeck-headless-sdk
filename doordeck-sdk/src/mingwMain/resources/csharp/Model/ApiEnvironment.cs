using System.Text.Json.Serialization;

namespace Doordeck.Headless.Sdk.Model
{
    [JsonConverter(typeof(JsonStringEnumConverter))]
    public enum ApiEnvironment
    {
        DEV,
        STAGING,
        PROD
    }
}