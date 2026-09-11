using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;

    
public class Account
{
    public Task<TokenResponse> RefreshToken(string? refreshToken = null) =>
        Dispatcher.Call<TokenResponse>("account.refreshToken", new { refreshToken });

    public Task<object> Logout() =>
        Dispatcher.Call<object>("account.logout"); 

    public Task<RegisterEphemeralKeyResponse> RegisterEphemeralKey(byte[]? publicKey = null, byte[]? privateKey = null) =>
        Dispatcher.Call<RegisterEphemeralKeyResponse>("account.registerEphemeralKey", new { publicKey, privateKey });

    public Task<RegisterEphemeralKeyWithSecondaryAuthenticationResponse> RegisterEphemeralKeyWithSecondaryAuthentication(byte[]? publicKey = null, TwoFactorMethod? method = null) =>
        Dispatcher.Call<RegisterEphemeralKeyWithSecondaryAuthenticationResponse>("account.registerEphemeralKeyWithSecondaryAuthentication", new { publicKey, method });

    public Task<RegisterEphemeralKeyResponse> VerifyEphemeralKeyRegistration(string code, byte[]? publicKey = null, byte[]? privateKey = null) =>
        Dispatcher.Call<RegisterEphemeralKeyResponse>("account.verifyEphemeralKeyRegistration", new { code, publicKey, privateKey });

    public Task<object> ReverifyEmail() =>
        Dispatcher.Call<object>("account.reverifyEmail");

    public Task<object> ChangePassword(string oldPassword, string newPassword) =>
        Dispatcher.Call<object>("account.changePassword", new { oldPassword, newPassword });

    public Task<UserDetailsResponse> GetUserDetails() =>
        Dispatcher.Call<UserDetailsResponse>("account.getUserDetails");

    public Task<object> UpdateUserDetails(string displayName) =>
        Dispatcher.Call<object>("account.updateUserDetails", new { displayName });

    public Task<object> DeleteAccount() =>
        Dispatcher.Call<object>("account.deleteAccount");
}