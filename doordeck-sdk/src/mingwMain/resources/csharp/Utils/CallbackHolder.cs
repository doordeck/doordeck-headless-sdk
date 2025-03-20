using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;

namespace Doordeck.Headless.Sdk.Utils;

internal class CallbackHolder<TResponse>
{
    private readonly Action<TResponse> _userCallback;
    private GCHandle _handle;

    public CallbackHolder(Action<TResponse>? userCallback)
    {
        _userCallback = userCallback;
        _handle = GCHandle.Alloc(this);
    }

    public void Callback(IntPtr ptr)
    {
        if (ptr == IntPtr.Zero)
        {
            Release();
            return;
        }

        var result = Marshal.PtrToStringAnsi(ptr);
        if (result == null)
        {
            Release();
            return;
        }

        try
        {
            var resultData = Utils.FromData<ResultData<TResponse>>(result);
            resultData.HandleException();
            _userCallback.Invoke(resultData.Success!.Result ?? default!);
        }
        finally
        {
            Release();
        }
    }

    private void Release()
    {
        if (_handle.IsAllocated)
        {
            _handle.Free();
        }
    }
}