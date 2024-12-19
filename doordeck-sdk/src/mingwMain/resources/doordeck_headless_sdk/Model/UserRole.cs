using System.Text.Json.Serialization;

namespace Doordeck.Headless.Sdk.Model
{
    [JsonConverter(typeof(JsonStringEnumConverter))]
    public enum UserRole
    {
        ADMIN,
        USER
    }
}
