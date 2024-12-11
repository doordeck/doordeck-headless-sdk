using System.Text.Json.Serialization;

namespace DoordeckHeadlessSDK.model
{
    [JsonConverter(typeof(JsonStringEnumConverter))]
    public enum TwoFactorMethod
    {
        EMAIL,
        TELEPHONE,
        SMS
    }
}
