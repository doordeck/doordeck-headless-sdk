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

    public void Initialize(Doordeck_Headless_Sdk_ExportedSymbols* symbols,
        Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck sdk)
    {
        _symbols = symbols;
        _helper = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.helper_(sdk);
        _helperApi = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.HelperApi;
    }

    public void Release()
    {
        _symbols->DisposeStablePointer(_helper.pinned);
    }

    public void UploadPlatformLogo(UploadPlatformLogoData data)
    {
        Process<object>(
            _helperApi.uploadPlatformLogoJson_,
            null,
            data
        );
    }

    public AssistedLoginResponse AssistedLogin(AssistedLoginData data)
    {
        return Process<AssistedLoginResponse>(
            _helperApi.assistedLoginJson_,
            null,
            data
        );
    }

    public AssistedRegisterEphemeralKeyResponse AssistedRegisterEphemeralKey(AssistedRegisterEphemeralKeyData? data)
    {
        return Process<AssistedRegisterEphemeralKeyResponse>(
            _helperApi.assistedRegisterEphemeralKeyJson_,
            null,
            data
        );
    }

    public void AssistedRegister(AssistedRegisterData data)
    {
        Process<object>(
            _helperApi.assistedRegisterJson_,
            null,
            data
        );
    }

    private TResponse Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_HelperApi,
            sbyte*, sbyte*> processDataWithResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_HelperApi,
            sbyte*> processWithoutDataWithResponse,
        object? data
    )
    {
        var sData = data != null ? data.ToData() : null;
        sbyte* result = null;
        try
        {
            var hasData = data != null;
            result = hasData ? processDataWithResponse(_helper, sData) :
                processWithoutDataWithResponse(_helper);

            var resultData = result != null
                ? Utils.Utils.FromData<ResultData<TResponse>>(result)
                : default!;

            resultData.HandleException();

            return resultData.Success!.Result ?? default!;
        }
        finally
        {
            if (data != null) Marshal.FreeHGlobal((IntPtr)sData);

            if (result != null) _symbols->DisposeString(result);
        }
    }
}