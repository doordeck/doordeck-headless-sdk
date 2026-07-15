using System.Text.Json.Serialization;

namespace Doordeck.Headless.Sdk.Model;

[JsonConverter(typeof(JsonStringEnumConverter))]
public enum AuditUserAgentType
{
    KMP_SDK_JVM,
    KMP_SDK_ANDROID,
    KMP_SDK_APPLE_MAC,
    KMP_SDK_APPLE_IOS,
    KMP_SDK_APPLE_WATCH,
    KMP_SDK_WINDOWS,
    KMP_SDK_JS_BROWSER,
    FUSION,
    PKOC_CONNECTOR
}