using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;

using HelperApi = Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_HelperApi;

public class Helper(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_HelperApi helper,
    Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._HelperApi_e__Struct helperApi) : AbstractWrapper
{
    public unsafe Task<object> UploadPlatformLogo(Guid applicationId, string contentType, string image) =>
        Process<HelperApi, object>(helper, helperApi.uploadPlatformLogo_, new { applicationId, contentType, image });

    public unsafe Task<AssistedLoginResponse> AssistedLogin(string email, string password) =>
        Process<HelperApi, AssistedLoginResponse>(helper, helperApi.assistedLogin_, new { email, password });

    public unsafe Task<AssistedRegisterEphemeralKeyResponse> AssistedRegisterEphemeralKey(string? publicKey = null, string? privateKey = null) =>
        Process<HelperApi, AssistedRegisterEphemeralKeyResponse>(helper, helperApi.assistedRegisterEphemeralKey_, new { publicKey, privateKey });

    public unsafe Task<object> AssistedRegister(string email, string password, string? displayName = null, bool force = false) =>
        Process<HelperApi, object>(helper, helperApi.assistedRegister_, new { email, password, displayName, force });
}