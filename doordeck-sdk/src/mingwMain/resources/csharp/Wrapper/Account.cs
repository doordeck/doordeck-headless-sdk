using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;

using AccountApi = Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountApi;
    
public class Account(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountApi account,
    Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._AccountApi_e__Struct accountApi) : AbstractWrapper
{
    public unsafe Task<TokenResponse> RefreshToken(string? refreshToken = null) =>
        Process<AccountApi, TokenResponse>(account, accountApi.refreshToken_, new { refreshToken });

    public unsafe Task<object> Logout() =>
        Process<AccountApi, object>(account, accountApi.logout_); 

    public unsafe Task<RegisterEphemeralKeyResponse> RegisterEphemeralKey(string? publicKey = null, string? privateKey = null) =>
        Process<AccountApi, RegisterEphemeralKeyResponse>(account, accountApi.registerEphemeralKey_, new { publicKey, privateKey });

    public unsafe Task<RegisterEphemeralKeyWithSecondaryAuthenticationResponse> RegisterEphemeralKeyWithSecondaryAuthentication(string? publicKey = null, TwoFactorMethod? method = null) =>
        Process<AccountApi, RegisterEphemeralKeyWithSecondaryAuthenticationResponse>(account, accountApi.registerEphemeralKeyWithSecondaryAuthentication_, new { publicKey, method });

    public unsafe Task<RegisterEphemeralKeyResponse> VerifyEphemeralKeyRegistration(string code, string? publicKey = null, string? privateKey = null) =>
        Process<AccountApi, RegisterEphemeralKeyResponse>(account, accountApi.verifyEphemeralKeyRegistration_, new { code, publicKey, privateKey });

    public unsafe Task<object> ReverifyEmail() =>
        Process<AccountApi, object>(account, accountApi.reverifyEmail_);

    public unsafe Task<object> ChangePassword(string oldPassword, string newPassword) =>
        Process<AccountApi, object>(account, accountApi.changePassword_, new { oldPassword, newPassword });

    public unsafe Task<UserDetailsResponse> GetUserDetails() =>
        Process<AccountApi, UserDetailsResponse>(account, accountApi.getUserDetails_);

    public unsafe Task<object> UpdateUserDetails(string displayName) =>
        Process<AccountApi, object>(account, accountApi.updateUserDetails_, new { displayName });

    public unsafe Task<object> DeleteAccount() =>
        Process<AccountApi, object>(account, accountApi.deleteAccount_);
}