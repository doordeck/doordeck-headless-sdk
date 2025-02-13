using System.Runtime.InteropServices;
using System.Text.Json;
using Doordeck.Headless.Sdk.Model;

namespace Doordeck.Headless.Sdk.Utils
{
    public static unsafe class Utils
    {
        private static readonly JsonSerializerOptions JsonSerializerOptions = new()
        {
            Converters = {
                new AuthKeyResponseConverter()
            },
            PropertyNamingPolicy = JsonNamingPolicy.CamelCase
        };

        public static sbyte* ToData<T>(this T input) =>
            JsonSerializer.Serialize(input, JsonSerializerOptions).ToSByte();

        public static T FromData<T>(sbyte* input) =>
            JsonSerializer.Deserialize<T>(ConvertSByteToString(input), JsonSerializerOptions);

        public static sbyte* ToSByte(this string input) =>
            (sbyte*)Marshal.StringToHGlobalAnsi(input);

        public static string ConvertSByteToString(sbyte* input) =>
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

        public static ResultData<T> HandleException<T>(this ResultData<T> input)
        {
            if (input.IsSuccess) return input;
            var exceptionType = input.Failure!.ExceptionType;
            if (exceptionType.Contains("SdkException"))
            {
                throw new SdkException(input.Failure.ExceptionMessage);
            }
            else if (exceptionType.Contains("MissingContextFieldException"))
            {
                throw new MissingContextFieldException(input.Failure.ExceptionMessage);
            }
            else if (exceptionType.Contains("BatchShareFailedException"))
            {
                throw new BatchShareFailedException(input.Failure.ExceptionMessage, new List<string>());
            }
            else if (exceptionType.Contains("BadRequestException"))
            {
                throw new BadRequestException(input.Failure.ExceptionMessage);
            }
            else if (exceptionType.Contains("UnauthorizedException"))
            {
                throw new UnauthorizedException(input.Failure.ExceptionMessage);
            }
            else if (exceptionType.Contains("ForbiddenException"))
            {
                throw new ForbiddenException(input.Failure.ExceptionMessage);
            }
            else if (exceptionType.Contains("NotFoundException"))
            {
                throw new NotFoundException(input.Failure.ExceptionMessage);
            }
            else if (exceptionType.Contains("MethodNotAllowedException"))
            {
                throw new MethodNotAllowedException(input.Failure.ExceptionMessage);
            }
            else if (exceptionType.Contains("NotAcceptableException"))
            {
                throw new NotAcceptableException(input.Failure.ExceptionMessage);
            }
            else if (exceptionType.Contains("ConflictException"))
            {
                throw new ConflictException(input.Failure.ExceptionMessage);
            }
            else if (exceptionType.Contains("GoneException"))
            {
                throw new GoneException(input.Failure.ExceptionMessage);
            }
            else if (exceptionType.Contains("LockedException"))
            {
                throw new LockedException(input.Failure.ExceptionMessage);
            }
            else if (exceptionType.Contains("TooEarlyException"))
            {
                throw new TooEarlyException(input.Failure.ExceptionMessage);
            }
            else if (exceptionType.Contains("TooManyRequestsException"))
            {
                throw new TooManyRequestsException(input.Failure.ExceptionMessage);
            }
            else if (exceptionType.Contains("InternalServerErrorException"))
            {
                throw new InternalServerErrorException(input.Failure.ExceptionMessage);
            }
            else if (exceptionType.Contains("ServiceUnavailableException"))
            {
                throw new ServiceUnavailableException(input.Failure.ExceptionMessage);
            }
            else if (exceptionType.Contains("GatewayTimeoutException"))
            {
                throw new GatewayTimeoutException(input.Failure.ExceptionMessage);
            }
            else
            {
                throw new SdkException("Unhandled exception type: " + exceptionType);
            }
        }
    }
}
