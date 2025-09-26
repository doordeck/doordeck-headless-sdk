using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Utilities;

namespace Doordeck.Headless.Sdk.Wrapper;

public abstract class AbstractWrapper
{
    [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
    public delegate void CallbackDelegate(IntPtr ptr);

    private class NativeCallback<TResponse> : IDisposable
    {
        private readonly TaskCompletionSource<TResponse> _tcs;
        private GCHandle _handle;
        public CallbackDelegate CallbackDelegate { get; }

        public NativeCallback(TaskCompletionSource<TResponse> tcs)
        {
            _tcs = tcs;
            CallbackDelegate = OnCallback;
            _handle = GCHandle.Alloc(this);
        }

        private void OnCallback(IntPtr ptr)
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
                var resultData = Utils.FromJson<ResultData<TResponse>>(result);
                if (resultData.Failure != null)
                {
                    _tcs.SetException(HandleException(resultData));
                }
                else
                {
                    var response = resultData.Success!.Result ?? default!;
                    _tcs.SetResult(response);
                }
            }
            catch (Exception exception)
            {
                _tcs.SetException(exception);
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

    internal static unsafe Task<TResponse> Process<TApi, TResponse>(
        TApi api,
        delegate* unmanaged[Cdecl]<TApi, sbyte*, void*, void> processWithData,
        object data) where TApi : unmanaged =>
        Process<TApi, TResponse>(api, processWithData, null, data);
    
    internal static unsafe Task<TResponse> Process<TApi, TResponse>(
        TApi api,
        delegate* unmanaged[Cdecl]<TApi, void*, void> processWithoutData) where TApi : unmanaged =>
        Process<TApi, TResponse>(api, null, processWithoutData, null);

    private static unsafe Task<TResponse> Process<TApi, TResponse>(
        TApi api,
        delegate* unmanaged[Cdecl]<TApi, sbyte*, void*, void> processWithData,
        delegate* unmanaged[Cdecl]<TApi, void*, void> processWithoutData,
        object? data) where TApi : unmanaged
    {
        var tcs = new TaskCompletionSource<TResponse>();
        var sData = data != null ? data.ToJsonSByte() : null;
        try
        {
            var holder = new NativeCallback<TResponse>(tcs);
            var callbackPointer = Marshal.GetFunctionPointerForDelegate(holder.CallbackDelegate);
            if (data != null)
            {
                processWithData(api, sData, callbackPointer.ToPointer());
            }
            else
            {
                processWithoutData(api, callbackPointer.ToPointer());
            }
        }
        finally
        {
            if (data != null) Marshal.FreeHGlobal((IntPtr)sData);
        }

        return tcs.Task;
    }

    private static Exception HandleException<T>(ResultData<T> input)
    {
        var exceptionType = input.Failure!.ExceptionType;
        if (exceptionType.Contains("SdkException")) return new SdkException(input.Failure.ExceptionMessage);
        if (exceptionType.Contains("MissingContextFieldException")) return new MissingContextFieldException(input.Failure.ExceptionMessage);
        if (exceptionType.Contains("BatchShareFailedException")) return new BatchShareFailedException(input.Failure.ExceptionMessage, []);
        if (exceptionType.Contains("BadRequestException")) return new BadRequestException(input.Failure.ExceptionMessage);
        if (exceptionType.Contains("UnauthorizedException")) return new UnauthorizedException(input.Failure.ExceptionMessage);
        if (exceptionType.Contains("ForbiddenException")) return new ForbiddenException(input.Failure.ExceptionMessage);
        if (exceptionType.Contains("NotFoundException")) return new NotFoundException(input.Failure.ExceptionMessage);
        if (exceptionType.Contains("MethodNotAllowedException")) return new MethodNotAllowedException(input.Failure.ExceptionMessage);
        if (exceptionType.Contains("NotAcceptableException")) return new NotAcceptableException(input.Failure.ExceptionMessage);
        if (exceptionType.Contains("ConflictException")) return new ConflictException(input.Failure.ExceptionMessage);
        if (exceptionType.Contains("GoneException")) return new GoneException(input.Failure.ExceptionMessage);
        if (exceptionType.Contains("UnprocessableEntityException")) return new UnprocessableEntityException(input.Failure.ExceptionMessage);
        if (exceptionType.Contains("LockedException")) return new LockedException(input.Failure.ExceptionMessage);
        if (exceptionType.Contains("TooEarlyException")) return new TooEarlyException(input.Failure.ExceptionMessage);
        if (exceptionType.Contains("TooManyRequestsException")) return new TooManyRequestsException(input.Failure.ExceptionMessage);
        if (exceptionType.Contains("InternalServerErrorException")) return new InternalServerErrorException(input.Failure.ExceptionMessage);
        if (exceptionType.Contains("ServiceUnavailableException")) return new ServiceUnavailableException(input.Failure.ExceptionMessage);
        if (exceptionType.Contains("GatewayTimeoutException")) return new GatewayTimeoutException(input.Failure.ExceptionMessage);
        return new SdkException("Unhandled exception type: " + exceptionType);
    }
}