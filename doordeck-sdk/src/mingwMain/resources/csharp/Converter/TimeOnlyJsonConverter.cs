using System.Text.Json;
using System.Text.Json.Serialization;

namespace Doordeck.Headless.Sdk.Converter;

public class TimeOnlyJsonConverter : JsonConverter<TimeOnly>
{
    private const string Format = "HH:mm";

    public override TimeOnly Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
    {
        var value = reader.GetString();
        return !string.IsNullOrWhiteSpace(value) ?
            TimeOnly.ParseExact(value, Format) :
            throw new JsonException($"Invalid time only: {value}");
    }

    public override void Write(Utf8JsonWriter writer, TimeOnly value, JsonSerializerOptions options)
    {
        writer.WriteStringValue(value.ToString(Format));
    }
}