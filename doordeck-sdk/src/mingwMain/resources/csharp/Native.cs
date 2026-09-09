using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;

namespace Doordeck.Headless.Sdk;

/// <summary>
/// The complete native surface of the SDK. Every operation is addressed by name through
/// <see cref="dd_call"/>, so these four declarations do not change as the API grows - which is why
/// they are written by hand rather than generated from the C header.
///
/// String parameters are marshalled by the LibraryImport source generator, so nothing here allocates
/// or frees unmanaged memory. Results arrive on a callback and are only borrowed for its duration.
/// </summary>
internal static partial class Native
{
    private const string Library = "Doordeck.Headless.Sdk.dll";

    [LibraryImport(Library, StringMarshalling = StringMarshalling.Utf8)]
    [UnmanagedCallConv(CallConvs = [typeof(CallConvCdecl)])]
    internal static partial void dd_create(string? configJson, long requestId, nint callback);

    [LibraryImport(Library, StringMarshalling = StringMarshalling.Utf8)]
    [UnmanagedCallConv(CallConvs = [typeof(CallConvCdecl)])]
    internal static partial void dd_call(string method, string? args, long requestId, nint callback);

    [LibraryImport(Library)]
    [UnmanagedCallConv(CallConvs = [typeof(CallConvCdecl)])]
    internal static partial void dd_release(long requestId, nint callback);

    /// <summary>
    /// Registers a host provided secure storage. Function pointers cannot travel as JSON, so this is
    /// the one operation that is not expressed through <see cref="dd_call"/>.
    /// </summary>
    [LibraryImport(Library)]
    [UnmanagedCallConv(CallConvs = [typeof(CallConvCdecl)])]
    internal static partial void dd_set_secure_storage(
        nint setApiEnvironment, nint getApiEnvironment,
        nint addCloudAuthToken, nint getCloudAuthToken,
        nint addCloudRefreshToken, nint getCloudRefreshToken,
        nint setFusionHost, nint getFusionHost,
        nint addFusionAuthToken, nint getFusionAuthToken,
        nint addPublicKey, nint getPublicKey,
        nint addPrivateKey, nint getPrivateKey,
        nint setKeyPairVerified, nint getKeyPairVerified,
        nint addUserId, nint getUserId,
        nint addUserEmail, nint getUserEmail,
        nint addCertificateChain, nint getCertificateChain,
        nint clear);
}
