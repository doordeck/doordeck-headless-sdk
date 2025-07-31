using System.Text.Json.Serialization;

namespace Doordeck.Headless.Sdk.Model;

[JsonConverter(typeof(JsonStringEnumConverter))]
public enum DayOfWeek
{
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY
}