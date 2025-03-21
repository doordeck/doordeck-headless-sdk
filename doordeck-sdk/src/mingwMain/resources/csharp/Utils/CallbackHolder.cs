using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;

namespace Doordeck.Headless.Sdk.Utils;

internal class CallbackHolder<TResponse>
{
    private TaskCompletionSource<TResponse> _tcs;
    private GCHandle _handle;

    public CallbackHolder(TaskCompletionSource<TResponse> tcs)
    {
        _tcs = tcs;
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
            var r = resultData.Success!.Result ?? default!;
            _tcs.SetResult(r);
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