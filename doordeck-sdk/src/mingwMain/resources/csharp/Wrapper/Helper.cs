using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;


public class Helper
{
    public Task<object> UploadPlatformLogo(Guid applicationId, string contentType, string image) =>
        Dispatcher.Call<object>("helper.uploadPlatformLogo", new { applicationId, contentType, image });

    public Task<AssistedLoginResponse> AssistedLogin(string email, string password) =>
        Dispatcher.Call<AssistedLoginResponse>("helper.assistedLogin", new { email, password });

    public Task<AssistedRegisterEphemeralKeyResponse> AssistedRegisterEphemeralKey(byte[]? publicKey = null, byte[]? privateKey = null) =>
        Dispatcher.Call<AssistedRegisterEphemeralKeyResponse>("helper.assistedRegisterEphemeralKey", new { publicKey, privateKey });

    public Task<object> AssistedRegister(string email, string password, string? displayName = null, bool force = false) =>
        Dispatcher.Call<object>("helper.assistedRegister", new { email, password, displayName, force });

    public Task<ServerTimeResponse> ServerTime() =>
            Dispatcher.Call<ServerTimeResponse>("helper.serverTime");
}