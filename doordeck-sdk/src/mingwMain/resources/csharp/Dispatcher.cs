using System.Collections.Concurrent;
using System.Runtime.CompilerServices;
using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Utilities;

namespace Doordeck.Headless.Sdk;

/// <summary>
/// Turns the callback based native boundary into tasks.
///
/// A single static callback serves every call, correlated by request id, so no delegate or GCHandle
/// is allocated per call. The JSON handed to that callback is borrowed for the duration of the call
/// only, so it is copied immediately.
/// </summary>
internal static class Dispatcher
{
    private static long _nextRequestId;
    private static readonly ConcurrentDictionary<long, IPending> Pending = new();

    private interface IPending
    {
        void Complete(string json);
    }

    private sealed class PendingCall<TResponse>(TaskCompletionSource<TResponse> completion) : IPending
    {
        public void Complete(string json)
        {
            try
            {
                var result = Utils.FromJson<ResultData<TResponse>>(json);
                if (result.Failure != null)
                {
                    completion.SetException(result.Failure.ToException());
                }
                else
                {
                    completion.SetResult(result.Success!.Result ?? default!);
                }
            }
            catch (Exception exception)
            {
                completion.SetException(exception);
            }
        }
    }

    // Must never throw: it runs on a native stack, where an escaping exception would terminate the
    // process. Everything that can fail is handled inside PendingCall.Complete.
    [UnmanagedCallersOnly(CallConvs = [typeof(CallConvCdecl)])]
    private static void OnResult(long requestId, nint json)
    {
        if (Pending.TryRemove(requestId, out var pending))
        {
            pending.Complete(Marshal.PtrToStringUTF8(json) ?? "");
        }
    }

    private static readonly unsafe nint Callback = (nint)(delegate* unmanaged[Cdecl]<long, nint, void>)&OnResult;

    internal static Task<TResponse> Call<TResponse>(string method, object? args = null) =>
        Invoke<TResponse>((requestId, callback) => Native.dd_call(method, Serialize(args), requestId, callback));

    /// <summary>
    /// Operations taking a single value receive it verbatim; everything else is serialised. Passing a
    /// bare string through the serialiser would wrap it in quotes and escape it, and the SDK would
    /// store those quotes as part of the value.
    /// </summary>
    private static string? Serialize(object? args) => args switch
    {
        null => null,
        string value => value,
        _ => args.ToJson()
    };

    /// <summary>
    /// For operations the SDK completes without leaving the process: the callback has already run by
    /// the time the native call returns, so the task is complete and this never blocks.
    /// </summary>
    internal static TResponse CallSync<TResponse>(string method, object? args = null) =>
        Call<TResponse>(method, args).GetAwaiter().GetResult();

    internal static Task<TResponse> Create<TResponse>(object config) =>
        Invoke<TResponse>((requestId, callback) => Native.dd_create(config.ToJson(), requestId, callback));

    internal static Task<TResponse> Release<TResponse>() =>
        Invoke<TResponse>(Native.dd_release);

    private static Task<TResponse> Invoke<TResponse>(Action<long, nint> nativeCall)
    {
        var completion = new TaskCompletionSource<TResponse>(TaskCreationOptions.RunContinuationsAsynchronously);
        var requestId = Interlocked.Increment(ref _nextRequestId);
        Pending[requestId] = new PendingCall<TResponse>(completion);
        try
        {
            nativeCall(requestId, Callback);
        }
        catch (Exception exception)
        {
            Pending.TryRemove(requestId, out _);
            completion.SetException(exception);
        }

        return completion.Task;
    }
}
