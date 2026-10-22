using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;

namespace Doordeck.Headless.Sdk;

/// <summary>
/// The complete native surface of the SDK. Every operation is addressed by name through
/// <see cref="call"/>, so these four declarations do not change as the API grows - which is why
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
    internal static partial void initialize(string? configJson, long requestId, nint callback);

    [LibraryImport(Library, StringMarshalling = StringMarshalling.Utf8)]
    [UnmanagedCallConv(CallConvs = [typeof(CallConvCdecl)])]
    internal static partial void call(string method, string? args, long requestId, nint callback);

    [LibraryImport(Library)]
    [UnmanagedCallConv(CallConvs = [typeof(CallConvCdecl)])]
    internal static partial void release(long requestId, nint callback);

    /// <summary>
    /// Registers a host provided secure storage. Function pointers cannot travel as JSON, so this is
    /// the one operation that is not expressed through <see cref="call"/>.
    /// </summary>
    [LibraryImport(Library)]
    [UnmanagedCallConv(CallConvs = [typeof(CallConvCdecl)])]
    internal static partial void set_secure_storage(nint setEntry, nint getEntry, nint clear);
}
