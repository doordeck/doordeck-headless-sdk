using System.Runtime.InteropServices;

namespace Doordeck.Headless.Sdk.Callback;

public static class Native
{
    [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
    public delegate void CallbackDelegate(IntPtr ptr);
}