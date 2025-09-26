using System.Text.Json;
using System.Text.Json.Serialization;

namespace Doordeck.Headless.Sdk.Converter;

public class TimeSpanJsonConverter : JsonConverter<TimeSpan>
{
    public override TimeSpan Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
    {
        return reader.TokenType == JsonTokenType.Number ?
            TimeSpan.FromSeconds(reader.GetDouble()) :
            throw new JsonException($"Invalid time span: {reader.TokenType}");
    }

    public override void Write(Utf8JsonWriter writer, TimeSpan value, JsonSerializerOptions options)
    {
        writer.WriteNumberValue(value.TotalSeconds);
    }
}