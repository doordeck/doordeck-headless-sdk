using System.Runtime.InteropServices;
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

    public static unsafe sbyte* ToJsonSByte<T>(this T input) => ToJson(input).StringToSByte();

    public static unsafe T FromJsonSByte<T>(sbyte* input) => FromJson<T>(SByteToString(input));

    private static string ToJson<T>(this T input) => JsonSerializer.Serialize(input, JsonSerializerOptions);

    public static T FromJson<T>(string input) => JsonSerializer.Deserialize<T>(input, JsonSerializerOptions)!;

    public static unsafe sbyte* StringToSByte(this string input) => (sbyte*)Marshal.StringToHGlobalAnsi(input);

    public static unsafe string SByteToString(sbyte* input) => Marshal.PtrToStringAnsi((IntPtr)input)!;

    public static bool ByteToBoolean(this byte input) => Convert.ToBoolean(input);

    public static byte BooleanToByte(this bool input) => Convert.ToByte(input);

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