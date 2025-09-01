using System.Text.Json.Serialization;

namespace Doordeck.Headless.Sdk.Model;

[JsonConverter(typeof(JsonStringEnumConverter))]
public enum ContextState
{
    CLOUD_TOKEN_IS_INVALID_OR_EXPIRED,
    KEY_PAIR_IS_INVALID,
    KEY_PAIR_IS_NOT_VERIFIED,
    CERTIFICATE_CHAIN_IS_INVALID_OR_EXPIRED,
    READY
}