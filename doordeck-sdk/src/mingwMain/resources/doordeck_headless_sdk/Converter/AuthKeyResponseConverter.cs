using System.Text.Json;
using System.Text.Json.Serialization;
using Doordeck.Headless.Sdk.Model.Responses;

public class AuthKeyResponseConverter : JsonConverter<AuthKeyResponse>
{
    public override AuthKeyResponse Read(ref Utf8JsonReader reader, Type typeToConvert, JsonSerializerOptions options)
    {
        using (var jsonDocument = JsonDocument.ParseValue(ref reader))
        {
            var jsonObject = jsonDocument.RootElement;
            switch(jsonObject.GetProperty("kty").GetString())
            {
                case "RSA":
                    return JsonSerializer.Deserialize<RsaKeyResponse>(jsonObject.GetRawText(), options);
                case "EC":
                    return JsonSerializer.Deserialize<EcKeyResponse>(jsonObject.GetRawText(), options); ;
                case "OKP":
                    return JsonSerializer.Deserialize<Ed25519KeyResponse>(jsonObject.GetRawText(), options);
                default: 
                    throw new JsonException("Unknown kty value");

            }
        }
    }

    public override void Write(Utf8JsonWriter writer, AuthKeyResponse value, JsonSerializerOptions options)
    {
        JsonSerializer.Serialize(writer, (object)value, value.GetType(), options);
    }
}

