using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;

public class Helper(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_HelperApi helper,
    Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._HelperApi_e__Struct helperApi) : AbstractWrapper
{
    public unsafe Task<object> UploadPlatformLogo(string applicationId, string contentType, string image) =>
        Process<object>(helperApi.uploadPlatformLogo_, null, new { applicationId, contentType, image });

    public unsafe Task<AssistedLoginResponse> AssistedLogin(string email, string password) =>
        Process<AssistedLoginResponse>(helperApi.assistedLogin_, null, new { email, password });

    public unsafe Task<AssistedRegisterEphemeralKeyResponse> AssistedRegisterEphemeralKey(string? publicKey = null) =>
        Process<AssistedRegisterEphemeralKeyResponse>(helperApi.assistedRegisterEphemeralKey_, null, new { publicKey });

    public unsafe Task<object> AssistedRegister(string email, string password, string? displayName = null, bool force = false) =>
        Process<object>(helperApi.assistedRegister_, null, new { email, password, displayName, force });

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