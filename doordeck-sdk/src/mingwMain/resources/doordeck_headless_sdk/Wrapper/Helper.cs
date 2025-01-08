using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

public unsafe class Helper : IResource
{
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_HelperResource _helper;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._HelperResource_e__Struct _helperResource;

    private Doordeck_Headless_Sdk_ExportedSymbols* _symbols;

    public void Initialize(Doordeck_Headless_Sdk_ExportedSymbols* symbols,
        Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck sdk)
    {
        _symbols = symbols;
        _helper = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.helper(sdk);
        _helperResource = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.HelperResource;
    }

    public void Release()
    {
        _symbols->DisposeStablePointer(_helper.pinned);
    }

    public void UploadPlatformLogo(UploadPlatformLogoData data)
    {
        ProcessHelperResource<object>(
            null,
            _helperResource.uploadPlatformLogoJson,
            null,
            data
        );
    }

    public AssistedLoginResponse AssistedLogin(AssistedLoginData data)
    {
        return ProcessHelperResource<AssistedLoginResponse>(
            _helperResource.assistedLoginJson,
            null,
            null,
            data
        );
    }

    public AssistedRegisterEphemeralKeyResponse AssistedRegisterEphemeralKey(AssistedRegisterEphemeralKeyData? data)
    {
        return ProcessHelperResource<AssistedRegisterEphemeralKeyResponse>(
            _helperResource.assistedRegisterEphemeralKeyJson,
            null,
            null,
            data
        );
    }

    public void AssistedRegister(AssistedRegisterData data)
    {
        ProcessHelperResource<object>(
            null,
            _helperResource.assistedRegisterJson,
            null,
            data
        );
    }

    private TResponse ProcessHelperResource<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_HelperResource,
            sbyte*, sbyte*> withDataAndWithResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_HelperResource,
            sbyte*, void> withDataAndWithoutResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_HelperResource,
            sbyte*> withoutDataAndWithResponse,
        object? data
    )
    {
        var sData = data != null ? data.ToData() : null;
        sbyte* result = null;
        try
        {
            var withResponse = typeof(TResponse) != typeof(object);
            var withData = data != null;

            if (withData && withResponse)
                result = withDataAndWithResponse(_helper, sData);
            else if (withData && !withResponse)
                withDataAndWithoutResponse(_helper, sData);
            else if (!withData && withResponse)
                result = withoutDataAndWithResponse(_helper);
            return result != null ? Utils.Utils.FromData<TResponse>(result)! : default!;
        }
        finally
        {
            if (data != null) Marshal.FreeHGlobal((IntPtr)sData);

            if (result != null) _symbols->DisposeString(result);
        }
    }
}