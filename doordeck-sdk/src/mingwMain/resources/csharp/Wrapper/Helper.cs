using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;

public class Helper(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_HelperApi helper,
    Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._HelperApi_e__Struct helperApi) : AbstractWrapper
{
    public unsafe Task<object> UploadPlatformLogo(UploadPlatformLogoData data) =>
        Process<object>(helperApi.uploadPlatformLogo_, null, data);

    public unsafe Task<AssistedLoginResponse> AssistedLogin(AssistedLoginData data) =>
        Process<AssistedLoginResponse>(helperApi.assistedLogin_, null, data);

    public unsafe Task<AssistedRegisterEphemeralKeyResponse> AssistedRegisterEphemeralKey(AssistedRegisterEphemeralKeyData? data) =>
        Process<AssistedRegisterEphemeralKeyResponse>(helperApi.assistedRegisterEphemeralKey_, null, data);

    public unsafe Task<object> AssistedRegister(AssistedRegisterData data) =>
        Process<object>(helperApi.assistedRegister_, null, data);

    private unsafe Task<TResponse> Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_HelperApi,
            sbyte*, void*, void> processWithData,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_HelperApi,
            void*, void> processWithoutData,
        object? data) => 
        ProcessCommon<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_HelperApi, TResponse>(
            helper,
            data,
            processWithData,
            processWithoutData);
}