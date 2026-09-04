using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;

using AccountApi = Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountApi;
    
public class Account(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountApi account) : AbstractWrapper
{
    public unsafe Task<TokenResponse> RefreshToken(string? refreshToken = null) =>
        Process<AccountApi, TokenResponse>(account, &Methods.refreshToken, new { refreshToken });

    public unsafe Task<object> Logout() =>
        Process<AccountApi, object>(account, &Methods.logout); 

    public unsafe Task<RegisterEphemeralKeyResponse> RegisterEphemeralKey(byte[]? publicKey = null, byte[]? privateKey = null) =>
        Process<AccountApi, RegisterEphemeralKeyResponse>(account, &Methods.registerEphemeralKey, new { publicKey, privateKey });

    public unsafe Task<RegisterEphemeralKeyWithSecondaryAuthenticationResponse> RegisterEphemeralKeyWithSecondaryAuthentication(byte[]? publicKey = null, TwoFactorMethod? method = null) =>
        Process<AccountApi, RegisterEphemeralKeyWithSecondaryAuthenticationResponse>(account, &Methods.registerEphemeralKeyWithSecondaryAuthentication, new { publicKey, method });

    public unsafe Task<RegisterEphemeralKeyResponse> VerifyEphemeralKeyRegistration(string code, byte[]? publicKey = null, byte[]? privateKey = null) =>
        Process<AccountApi, RegisterEphemeralKeyResponse>(account, &Methods.verifyEphemeralKeyRegistration, new { code, publicKey, privateKey });

    public unsafe Task<object> ReverifyEmail() =>
        Process<AccountApi, object>(account, &Methods.reverifyEmail);

    public unsafe Task<object> ChangePassword(string oldPassword, string newPassword) =>
        Process<AccountApi, object>(account, &Methods.changePassword, new { oldPassword, newPassword });

    public unsafe Task<UserDetailsResponse> GetUserDetails() =>
        Process<AccountApi, UserDetailsResponse>(account, &Methods.getUserDetails);

    public unsafe Task<object> UpdateUserDetails(string displayName) =>
        Process<AccountApi, object>(account, &Methods.updateUserDetails, new { displayName });

    public unsafe Task<object> DeleteAccount() =>
        Process<AccountApi, object>(account, &Methods.deleteAccount);
}