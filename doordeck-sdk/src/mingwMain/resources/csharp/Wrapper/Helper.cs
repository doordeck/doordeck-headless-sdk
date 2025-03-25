using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

public class Helper(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_HelperApi helper,
    Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._HelperApi_e__Struct helperApi) : IResource
{
    public unsafe Task<object> UploadPlatformLogo(UploadPlatformLogoData data)
    {
        return Process<object>(helperApi.uploadPlatformLogo_, null, data);
    }

    public unsafe Task<AssistedLoginResponse> AssistedLogin(AssistedLoginData data)
    {
        return Process<AssistedLoginResponse>(helperApi.assistedLogin_, null, data);
    }

    public unsafe Task<AssistedRegisterEphemeralKeyResponse> AssistedRegisterEphemeralKey(AssistedRegisterEphemeralKeyData? data)
    {
        return Process<AssistedRegisterEphemeralKeyResponse>(helperApi.assistedRegisterEphemeralKey_, null, data);
    }

    public unsafe Task<object> AssistedRegister(AssistedRegisterData data)
    {
        return Process<object>(helperApi.assistedRegister_, null, data);
    }

    private unsafe Task<TResponse> Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_HelperApi,
            sbyte*, void*, void> processWithData,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_HelperApi,
            void*, void> processWithoutData,
        object? data
    )
    {
        var tcs = new TaskCompletionSource<TResponse>();
        var sData = data != null ? data.ToData() : null;
        try
        {
            var holder = new CallbackHolder<TResponse>(tcs);
            IResource.CallbackDelegate callbackDelegate = holder.Callback;
            var callbackPointer = Marshal.GetFunctionPointerForDelegate(callbackDelegate);
            if (data != null)
            {
                processWithData(helper, sData, callbackPointer.ToPointer());
            }
            else
            {
                processWithoutData(helper, callbackPointer.ToPointer());
            }
        }
        finally
        {
            if (data != null) Marshal.FreeHGlobal((IntPtr)sData);
        }

        return tcs.Task;
    }
}