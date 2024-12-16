using System.Runtime.InteropServices;
using System.Text.Json;

namespace DoordeckHeadlessSDK.utils
{
    public static unsafe class Utils
    {
        private static readonly JsonSerializerOptions JsonSerializerOptions = new()
        {
            Converters = {
                new AuthKeyResponseConverter()
            }
        };

        public static sbyte* ToData<T>(this T input) =>
            JsonSerializer.Serialize(input, JsonSerializerOptions).ToSByte();

        public static T? FromData<T>(sbyte* input) =>
            JsonSerializer.Deserialize<T>(ConvertSByteToString(input), JsonSerializerOptions);

        public static unsafe sbyte* ToSByte(this string input) =>
            (sbyte*)Marshal.StringToHGlobalAnsi(input);

        public static unsafe string ConvertSByteToString(sbyte* input) =>
            Marshal.PtrToStringAnsi((IntPtr)input)!;
    }
}
