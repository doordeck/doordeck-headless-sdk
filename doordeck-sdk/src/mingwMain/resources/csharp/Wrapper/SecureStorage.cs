using System.Runtime.InteropServices;
using System.Text;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Utilities;

namespace Doordeck.Headless.Sdk.Wrapper;

internal static class SecureStorage
{
    public static ISecureStorage? Implementation { get; set; }

    /// <summary>Entry names agreed with SecureStorageKeys on the Kotlin side.</summary>
    private const string ApiEnvironmentKey = "apiEnvironment";
    private const string CloudAuthTokenKey = "cloudAuthToken";
    private const string CloudRefreshTokenKey = "cloudRefreshToken";
    private const string FusionHostKey = "fusionHost";
    private const string FusionAuthTokenKey = "fusionAuthToken";
    private const string PublicKeyKey = "publicKey";
    private const string PrivateKeyKey = "privateKey";
    private const string KeyPairVerifiedKey = "keyPairVerified";
    private const string UserIdKey = "userId";
    private const string UserEmailKey = "userEmail";
    private const string CertificateChainKey = "certificateChain";

    public static class Delegates
    {
        [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
        public delegate void SetEntryDelegate(IntPtr key, IntPtr value);

        [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
        public delegate int GetEntryDelegate(IntPtr key, IntPtr buffer, int capacity);

        [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
        public delegate void ClearDelegate();
    }

    // Held in static fields for the lifetime of the process: the SDK keeps these pointers, so the
    // delegates they came from must stay reachable.
    private static readonly Delegates.SetEntryDelegate SetEntryDelegate = SetEntry;
    private static readonly Delegates.GetEntryDelegate GetEntryDelegate = GetEntry;
    private static readonly Delegates.ClearDelegate ClearDelegate = Clear;

    internal static void Register() =>
        Native.dd_set_secure_storage(
            Marshal.GetFunctionPointerForDelegate(SetEntryDelegate),
            Marshal.GetFunctionPointerForDelegate(GetEntryDelegate),
            Marshal.GetFunctionPointerForDelegate(ClearDelegate));

    private static void SetEntry(IntPtr key, IntPtr value)
    {
        if (Implementation is not {} implementation) return;

        var name = Marshal.PtrToStringUTF8(key);
        if (name == null) return;

        var entry = value == IntPtr.Zero ? null : Marshal.PtrToStringUTF8(value);

        switch (name)
        {
            case ApiEnvironmentKey when entry != null:
                implementation.SetApiEnvironment(Enum.Parse<ApiEnvironment>(entry));
                break;
            case CloudAuthTokenKey when entry != null:
                implementation.AddCloudAuthToken(entry);
                break;
            case CloudRefreshTokenKey when entry != null:
                implementation.AddCloudRefreshToken(entry);
                break;
            case FusionHostKey when entry != null:
                implementation.SetFusionHost(entry);
                break;
            case FusionAuthTokenKey when entry != null:
                implementation.AddFusionAuthToken(entry);
                break;
            case PublicKeyKey when entry != null:
                implementation.AddPublicKey(entry.DecodeBase64ToByteArray());
                break;
            case PrivateKeyKey when entry != null:
                implementation.AddPrivateKey(entry.DecodeBase64ToByteArray());
                break;
            // The only entry the SDK legitimately clears by storing a null value.
            case KeyPairVerifiedKey:
                implementation.SetKeyPairVerified(entry?.DecodeBase64ToByteArray());
                break;
            case UserIdKey when entry != null:
                implementation.AddUserId(new Guid(entry));
                break;
            case UserEmailKey when entry != null:
                implementation.AddUserEmail(entry);
                break;
            case CertificateChainKey when entry != null:
                implementation.AddCertificateChain(entry.StringToCertificateChain());
                break;
        }
    }

    private static int GetEntry(IntPtr key, IntPtr buffer, int capacity)
    {
        if (Implementation is not {} implementation) return -1;

        var name = Marshal.PtrToStringUTF8(key);
        if (name == null) return -1;

        var entry = name switch
        {
            ApiEnvironmentKey => implementation.GetApiEnvironment()?.ToString(),
            CloudAuthTokenKey => implementation.GetCloudAuthToken(),
            CloudRefreshTokenKey => implementation.GetCloudRefreshToken(),
            FusionHostKey => implementation.GetFusionHost(),
            FusionAuthTokenKey => implementation.GetFusionAuthToken(),
            PublicKeyKey => implementation.GetPublicKey()?.EncodeByteArrayToBase64(),
            PrivateKeyKey => implementation.GetPrivateKey()?.EncodeByteArrayToBase64(),
            KeyPairVerifiedKey => implementation.GetKeyPairVerified()?.EncodeByteArrayToBase64(),
            UserIdKey => implementation.GetUserId()?.ToString(),
            UserEmailKey => implementation.GetUserEmail(),
            CertificateChainKey => implementation.GetCertificateChain()?.CertificateChainToString(),
            _ => null
        };

        if (entry == null) return -1;

        // UTF-8 to match Kotlin/Native's toKString on the other side.
        var bytes = Encoding.UTF8.GetBytes(entry);

        // A null buffer, or one too small, is how the caller asks for the length before allocating.
        if (buffer != IntPtr.Zero && capacity > bytes.Length)
        {
            Marshal.Copy(bytes, 0, buffer, bytes.Length);
            Marshal.WriteByte(buffer, bytes.Length, 0);
        }

        return bytes.Length;
    }

    private static void Clear() => Implementation?.Clear();
}