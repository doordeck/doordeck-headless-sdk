using System.Net;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace ConsoleApp2.Doordeck.Headless.Sdk.Converter;

public class IpAddressJsonConverter : JsonConverter<IPAddress>
{
    public override IPAddress? Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
    {
        var value = reader.GetString();
        if (string.IsNullOrWhiteSpace(value)) return null;
        if (IPAddress.TryParse(value, out var address)) return address;
        throw new JsonException($"Invalid IP address: {value}");
    }

    public override void Write(Utf8JsonWriter writer, IPAddress value, JsonSerializerOptions options)
    {
        writer.WriteStringValue(value.ToString());
    }
}