using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;

using HelperApi = Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_HelperApi;

public class Helper(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_HelperApi helper) : AbstractWrapper
{
    public unsafe Task<object> UploadPlatformLogo(Guid applicationId, string contentType, string image) =>
        Process<HelperApi, object>(helper, &Methods.uploadPlatformLogo, new { applicationId, contentType, image });

    public unsafe Task<AssistedLoginResponse> AssistedLogin(string email, string password) =>
        Process<HelperApi, AssistedLoginResponse>(helper, &Methods.assistedLogin, new { email, password });

    public unsafe Task<AssistedRegisterEphemeralKeyResponse> AssistedRegisterEphemeralKey(byte[]? publicKey = null, byte[]? privateKey = null) =>
        Process<HelperApi, AssistedRegisterEphemeralKeyResponse>(helper, &Methods.assistedRegisterEphemeralKey, new { publicKey, privateKey });

    public unsafe Task<object> AssistedRegister(string email, string password, string? displayName = null, bool force = false) =>
        Process<HelperApi, object>(helper, &Methods.assistedRegister, new { email, password, displayName, force });

    public unsafe Task<ServerTimeResponse> ServerTime() =>
            Process<HelperApi, ServerTimeResponse>(helper, &Methods.serverTime);
}