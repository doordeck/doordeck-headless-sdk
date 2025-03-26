using System.Runtime.InteropServices;
using System.Text.Json;

namespace Doordeck.Headless.Sdk.Utils;

public static class Utils
{
    private static readonly JsonSerializerOptions JsonSerializerOptions = new()
    {
        Converters = {
            new AuthKeyResponseConverter()
        },
        PropertyNamingPolicy = JsonNamingPolicy.CamelCase
    };

    public static unsafe sbyte* ToData<T>(this T input) =>
        JsonSerializer.Serialize(input, JsonSerializerOptions).ToSByte();

    public static unsafe T FromData<T>(sbyte* input) =>
        JsonSerializer.Deserialize<T>(ConvertSByteToString(input), JsonSerializerOptions);

    public static T FromData<T>(string input) =>
        JsonSerializer.Deserialize<T>(input, JsonSerializerOptions);

    public static unsafe sbyte* ToSByte(this string input) =>
        (sbyte*)Marshal.StringToHGlobalAnsi(input);

    public static unsafe string ConvertSByteToString(sbyte* input) =>
        Marshal.PtrToStringAnsi((IntPtr)input)!;

    public static bool ToBoolean(this byte input) =>
        Convert.ToBoolean(input);

    public static byte[] DecodeBase64ToByteArray(this string input) =>
        Convert.FromBase64String(input);

    public static string EncodeByteArrayToBase64(this byte[] input) =>
        Convert.ToBase64String(input);

    public static string CertificateChainToString(this List<string> input) =>
        string.Join("|", input);

    public static List<string> StringToCertificateChain(this string input) =>
        input.Split("|").ToList();
}