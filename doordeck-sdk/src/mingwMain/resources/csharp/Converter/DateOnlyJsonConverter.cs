using System.Globalization;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace Doordeck.Headless.Sdk.Converter;

public class DateOnlyJsonConverter : JsonConverter<DateOnly>
{
    private const string Format = "yyyy-MM-dd";

    public override DateOnly Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
    {
        try
        {
            var value = reader.GetString();
            return !string.IsNullOrWhiteSpace(value) ?
                DateOnly.ParseExact(value, Format, CultureInfo.InvariantCulture) :
                throw new JsonException($"Invalid date time: {value}");
        }
        catch (FormatException ex)
        {
            throw new JsonException($"Invalid date format", ex);
        }
    }

    public override void Write(Utf8JsonWriter writer, DateOnly value, JsonSerializerOptions options)
    {
        writer.WriteStringValue(value.ToString(Format, CultureInfo.InvariantCulture));
    }
}