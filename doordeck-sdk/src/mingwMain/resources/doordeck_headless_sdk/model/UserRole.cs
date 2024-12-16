using System.Text.Json.Serialization;

namespace DoordeckHeadlessSDK.model
{
    [JsonConverter(typeof(JsonStringEnumConverter))]
    public enum UserRole
    {
        ADMIN,
        USER
    }
}
