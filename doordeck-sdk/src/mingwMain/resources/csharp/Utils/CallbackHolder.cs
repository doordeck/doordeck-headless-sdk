using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;

namespace Doordeck.Headless.Sdk.Utils;

internal class CallbackHolder<TResponse> : IDisposable
{
    private readonly TaskCompletionSource<TResponse> _tcs;
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
            Dispose();
            return;
        }

        var result = Marshal.PtrToStringAnsi(ptr);
        if (result == null)
        {
            Dispose();
            return;
        }

        try
        {
            var resultData = Utils.FromData<ResultData<TResponse>>(result);
            resultData.HandleException();
            var r = resultData.Success!.Result ?? default!;
            _tcs.SetResult(r);
        }
        catch (Exception ex)
        {
            _tcs.SetException(ex);
        }
        finally
        {
            Dispose();
        }
    }

    public void Dispose()
    {
        if (_handle.IsAllocated)
        {
            _handle.Free();
        }
    }
}