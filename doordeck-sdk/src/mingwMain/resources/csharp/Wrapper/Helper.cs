using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

public unsafe class Helper : IResource
{
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_HelperApi _helper;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._HelperApi_e__Struct _helperApi;

    private Doordeck_Headless_Sdk_ExportedSymbols* _symbols;

    void IResource.Initialize(Doordeck_Headless_Sdk_ExportedSymbols* symbols,
        Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck sdk)
    {
        _symbols = symbols;
        _helper = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.helper_(sdk);
        _helperApi = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.HelperApi;
    }

    void IResource.Release()
    {
        _symbols->DisposeStablePointer(_helper.pinned);
    }

    public void UploadPlatformLogo(UploadPlatformLogoData data, Action<object> action)
    {
        Process(_helperApi.uploadPlatformLogo_, null, action, data);
    }

    public void AssistedLogin(AssistedLoginData data, Action<AssistedLoginResponse> action)
    {
        Process(_helperApi.assistedLogin_, null, action, data);
    }

    public void AssistedRegisterEphemeralKey(AssistedRegisterEphemeralKeyData? data,
        Action<AssistedRegisterEphemeralKeyResponse> action)
    {
        Process(_helperApi.assistedRegisterEphemeralKey_, null, action, data);
    }

    public void AssistedRegister(AssistedRegisterData data, Action<object> action)
    {
        Process(_helperApi.assistedRegister_, null, action, data);
    }

    private void Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_HelperApi,
            sbyte*, void*, void> processWithData,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_HelperApi,
            void*, void> processWithoutData,
        Action<TResponse> userCallback,
        object? data
    )
    {
        var sData = data != null ? data.ToData() : null;
        try
        {
            var holder = new CallbackHolder<TResponse>(userCallback);
            IResource.CallbackDelegate callbackDelegate = holder.Callback;
            var callbackPointer = Marshal.GetFunctionPointerForDelegate(callbackDelegate);
            if (data != null)
            {
                processWithData(_helper, sData, callbackPointer.ToPointer());
            }
            else
            {
                processWithoutData(_helper, callbackPointer.ToPointer());
            }
        }
        finally
        {
            if (data != null) Marshal.FreeHGlobal((IntPtr)sData);
        }
    }
}