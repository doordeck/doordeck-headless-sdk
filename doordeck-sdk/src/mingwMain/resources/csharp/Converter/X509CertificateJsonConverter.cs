using System.Security.Cryptography.X509Certificates;
using System.Text.Json;
using System.Text.Json.Serialization;

namespace Doordeck.Headless.Sdk.Converter;

public class X509CertificateJsonConverter : JsonConverter<X509Certificate>
{
    public override X509Certificate Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
    {
        try
        {
            var value = reader.GetString();
            return !string.IsNullOrWhiteSpace(value) ?
                X509CertificateLoader.LoadCertificate(Convert.FromBase64String(value)) :
                throw new JsonException($"Invalid certificate value: {value}");
        }
        catch (Exception exception)
        {
            throw new JsonException("Invalid X509Certificate", exception);
        }
    }

    public override void Write(Utf8JsonWriter writer, X509Certificate value, JsonSerializerOptions options)
    {
        writer.WriteStringValue(Convert.ToBase64String(value.Export(X509ContentType.Cert)));
    }
}