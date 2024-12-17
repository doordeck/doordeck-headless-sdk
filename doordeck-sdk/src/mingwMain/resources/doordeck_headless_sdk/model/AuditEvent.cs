using System.Text.Json.Serialization;

namespace Doordeck.Headless.Sdk.model
{
    [JsonConverter(typeof(JsonStringEnumConverter))]
    public enum AuditEvent
    {
        DOOR_OPEN,
        DOOR_CLOSE,
        DOOR_UNLOCK,
        DOOR_LOCK,
        OWNER_ASSIGNED,
        DEVICE_CONNECTED,
        DEVICE_DISCONNECTED,
        LOCK_SHARED,
        LOCK_REVOKED,
        USER_PROMOTED,
        USER_DEMOTED,
        SETTING_CHANGED,
        TILE_ASSOCIATED,
        TILE_DISASSOCIATED,
        DEVICE_DECOMMISSIONED
    }
}
