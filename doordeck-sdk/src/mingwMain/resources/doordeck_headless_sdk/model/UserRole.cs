using System.Text.Json.Serialization;

namespace Doordeck.Headless.Sdk.model
{
    [JsonConverter(typeof(JsonStringEnumConverter))]
    public enum UserRole
    {
        ADMIN,
        USER
    }
}
