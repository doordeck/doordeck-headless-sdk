using System.Globalization;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace Doordeck.Headless.Sdk.Converter;

public class DateTimeJsonConverter : JsonConverter<DateTime>
{
    public override DateTime Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
    {
        var value = reader.TokenType == JsonTokenType.String ?
            reader.GetString() :
            reader.TokenType == JsonTokenType.Number ?
            reader.GetDouble().ToString("R", CultureInfo.InvariantCulture) :
            throw new JsonException($"Invalid date time: {reader.TokenType}");

        if (string.IsNullOrWhiteSpace(value) ||
            !double.TryParse(value, NumberStyles.Float | NumberStyles.AllowLeadingSign, CultureInfo.InvariantCulture,
                out var totalSeconds))
        {
            throw new JsonException($"Invalid epoch-second.nano format: {value}");
        }

        var epoch = DateTime.UnixEpoch;
        try
        {
            return epoch.AddSeconds(totalSeconds);
        }
        catch (ArgumentOutOfRangeException ex)
        {
            throw new JsonException("Resulting DateTime is out of range.", ex);
        }
    }

    public override void Write(Utf8JsonWriter writer, DateTime value, JsonSerializerOptions options)
    {
        writer.WriteNumberValue((long)(value.ToUniversalTime() - DateTime.UnixEpoch).TotalSeconds);
    }
}