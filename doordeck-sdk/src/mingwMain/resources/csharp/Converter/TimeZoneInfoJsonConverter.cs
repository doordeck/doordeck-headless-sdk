using System.Text.Json;
using System.Text.Json.Serialization;

namespace Doordeck.Headless.Sdk.Converter;

public class TimeZoneInfoJsonConverter : JsonConverter<TimeZoneInfo>
{
    public override TimeZoneInfo Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
    {
        var value = reader.GetString();
        return !string.IsNullOrWhiteSpace(value) ?
            TimeZoneInfo.FindSystemTimeZoneById(value) :
            throw new JsonException($"Invalid time zone: {value}");
    }

    public override void Write(Utf8JsonWriter writer, TimeZoneInfo value, JsonSerializerOptions options)
    {
        writer.WriteStringValue(value.Id);
    }
}