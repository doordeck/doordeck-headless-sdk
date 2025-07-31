using System.Text.Json.Serialization;

namespace Doordeck.Headless.Sdk.Model;

[JsonConverter(typeof(JsonStringEnumConverter))]
public enum GrantType
{
    PASSWORD,
    AUTHORIZATION_CODE,
    CLIENT_CREDENTIALS,
    REFRESH_TOKEN
}