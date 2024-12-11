using System.Text;
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

        public static unsafe sbyte* ToSByte(this string input)
        {
            byte[] bytes = Encoding.ASCII.GetBytes(input);
            fixed (byte* po = bytes)
            {
                return (sbyte*)po;
            }
        }

        public static unsafe string ConvertSByteToString(sbyte* input)
        {
            // Find the length of the C-style string (null-terminated)
            int length = 0;
            while (input[length] != 0)
            {
                length++;
            }
            // Create a byte array from the sbyte*
            byte[] byteArray = new byte[length];
            for (int i = 0; i < length; i++)
            {
                byteArray[i] = (byte)input[i];
            }
            // Convert the byte array to a string (assuming ASCII encoding)
            return Encoding.ASCII.GetString(byteArray);
        }
    }
}
