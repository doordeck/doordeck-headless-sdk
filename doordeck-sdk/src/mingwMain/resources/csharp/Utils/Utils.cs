using System.Security.Cryptography.X509Certificates;
using System.Text.Json;
using Doordeck.Headless.Sdk.Converter;

namespace Doordeck.Headless.Sdk.Utilities;

public static class Utils
{
    private static readonly JsonSerializerOptions JsonSerializerOptions = new()
    {
        PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
        Converters =
        {
            new IpAddressJsonConverter(),
            new DateTimeJsonConverter(),
            new TimeOnlyJsonConverter(),
            new TimeZoneInfoJsonConverter(),
            new DateOnlyJsonConverter(),
            new X509CertificateJsonConverter(),
            new TimeSpanJsonConverter()
        }
    };

    public static string ToJson<T>(this T input) => JsonSerializer.Serialize(input, JsonSerializerOptions);

    public static T FromJson<T>(string input) => JsonSerializer.Deserialize<T>(input, JsonSerializerOptions)!;

    public static byte[] DecodeBase64ToByteArray(this string input) => Convert.FromBase64String(input);

    public static string EncodeByteArrayToBase64(this byte[] input) => Convert.ToBase64String(input);

    public static string CertificateChainToString(this List<X509Certificate> input) =>
        string.Join("|", input.Select(cert => cert.Export(X509ContentType.Cert).EncodeByteArrayToBase64()));

    public static List<X509Certificate> StringToCertificateChain(this string input) =>
    [
        ..input.Split("|")
            .ToList()
            .Select(cert => X509CertificateLoader.LoadCertificate(cert.DecodeBase64ToByteArray()))
            .ToList()
    ];
}