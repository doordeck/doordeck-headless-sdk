using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Callback;

internal class CallbackHolder<TResponse> : IDisposable
{
    private readonly TaskCompletionSource<TResponse> _tcs;
    private GCHandle _handle;
    public Native.CallbackDelegate CallbackDelegate { get; }

    public CallbackHolder(TaskCompletionSource<TResponse> tcs)
    {
        _tcs = tcs;
        CallbackDelegate = Callback;
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
            var resultData = Utils.Utils.FromData<ResultData<TResponse>>(result);
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