using System.Runtime.InteropServices;

namespace Doordeck.Headless.Sdk.Wrapper;

public interface IResource
{
    [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
    delegate void CallbackDelegate(IntPtr r);
}