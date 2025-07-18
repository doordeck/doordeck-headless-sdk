using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

public abstract class AbstractWrapper
{
    private static class Native
    {
        [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
        public delegate void CallbackDelegate(IntPtr ptr);
    }

    private class CallbackHolder<TResponse> : IDisposable
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
    
        private void Callback(IntPtr ptr)
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
                HandleException(resultData);
                var response = resultData.Success!.Result ?? default!;
                _tcs.SetResult(response);
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
    
    internal static unsafe Task<TResponse> ProcessCommon<TApi, TResponse>(
        TApi api,
        object? data,
        delegate* unmanaged[Cdecl]<TApi, sbyte*, void*, void> processWithData,
        delegate* unmanaged[Cdecl]<TApi, void*, void> processWithoutData) where TApi : unmanaged
    {
        var tcs = new TaskCompletionSource<TResponse>();
        var sData = data != null ? data.ToData() : null;
        try
        {
            var holder = new CallbackHolder<TResponse>(tcs);
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
    
    private static void HandleException<T>(ResultData<T> input)
    {
        if (input.IsSuccess) return;
        var exceptionType = input.Failure!.ExceptionType;
        if (exceptionType.Contains("SdkException")) throw new SdkException(input.Failure.ExceptionMessage);
        if (exceptionType.Contains("MissingContextFieldException")) throw new MissingContextFieldException(input.Failure.ExceptionMessage);
        if (exceptionType.Contains("BatchShareFailedException")) throw new BatchShareFailedException(input.Failure.ExceptionMessage, []);
        if (exceptionType.Contains("BadRequestException")) throw new BadRequestException(input.Failure.ExceptionMessage);
        if (exceptionType.Contains("UnauthorizedException")) throw new UnauthorizedException(input.Failure.ExceptionMessage);
        if (exceptionType.Contains("ForbiddenException")) throw new ForbiddenException(input.Failure.ExceptionMessage);
        if (exceptionType.Contains("NotFoundException")) throw new NotFoundException(input.Failure.ExceptionMessage);
        if (exceptionType.Contains("MethodNotAllowedException")) throw new MethodNotAllowedException(input.Failure.ExceptionMessage);
        if (exceptionType.Contains("NotAcceptableException")) throw new NotAcceptableException(input.Failure.ExceptionMessage);
        if (exceptionType.Contains("ConflictException")) throw new ConflictException(input.Failure.ExceptionMessage);
        if (exceptionType.Contains("GoneException")) throw new GoneException(input.Failure.ExceptionMessage);
        if (exceptionType.Contains("UnprocessableEntityException")) throw new UnprocessableEntityException(input.Failure.ExceptionMessage);
        if (exceptionType.Contains("LockedException")) throw new LockedException(input.Failure.ExceptionMessage);
        if (exceptionType.Contains("TooEarlyException")) throw new TooEarlyException(input.Failure.ExceptionMessage);
        if (exceptionType.Contains("TooManyRequestsException")) throw new TooManyRequestsException(input.Failure.ExceptionMessage);
        if (exceptionType.Contains("InternalServerErrorException")) throw new InternalServerErrorException(input.Failure.ExceptionMessage);
        if (exceptionType.Contains("ServiceUnavailableException")) throw new ServiceUnavailableException(input.Failure.ExceptionMessage);
        if (exceptionType.Contains("GatewayTimeoutException")) throw new GatewayTimeoutException(input.Failure.ExceptionMessage);
        throw new SdkException("Unhandled exception type: " + exceptionType);
    }
}