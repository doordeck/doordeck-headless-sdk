using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;

public class Account(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountApi account,
    Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._AccountApi_e__Struct accountApi) : AbstractWrapper
{
    public unsafe Task<TokenResponse> RefreshToken(string? refreshToken = null) =>
        Process<TokenResponse>(accountApi.refreshToken_, null, new { refreshToken });

    public unsafe Task<object> Logout() =>
        Process<object>(null, accountApi.logout_, null);

    public unsafe Task<RegisterEphemeralKeyResponse> RegisterEphemeralKey(string? publicKey = null) =>
        Process<RegisterEphemeralKeyResponse>(accountApi.registerEphemeralKey_, null, new { publicKey });

    public unsafe Task<RegisterEphemeralKeyWithSecondaryAuthenticationResponse> RegisterEphemeralKeyWithSecondaryAuthentication(string? publicKey = null, TwoFactorMethod? method = null) =>
        Process<RegisterEphemeralKeyWithSecondaryAuthenticationResponse>(accountApi.registerEphemeralKeyWithSecondaryAuthentication_, null, new { publicKey, method });

    public unsafe Task<RegisterEphemeralKeyResponse> VerifyEphemeralKeyRegistration(string code, string? privateKey = null) =>
        Process<RegisterEphemeralKeyResponse>(accountApi.verifyEphemeralKeyRegistration_, null, new { code, privateKey });

    public unsafe Task<object> ReverifyEmail() =>
        Process<object>(null, accountApi.reverifyEmail_, null);

    public unsafe Task<object> ChangePassword(string oldPassword, string newPassword) =>
        Process<object>(accountApi.changePassword_, null, new { oldPassword, newPassword });

    public unsafe Task<UserDetailsResponse> GetUserDetails() =>
        Process<UserDetailsResponse>(null, accountApi.getUserDetails_, null);

    public unsafe Task<object> UpdateUserDetails(string displayName) =>
        Process<object>(accountApi.updateUserDetails_, null, new { displayName });

    public unsafe Task<object> DeleteAccount() =>
        Process<object>(null, accountApi.deleteAccount_, null);

    private unsafe Task<TResponse> Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountApi,
            sbyte*, void*, void> processWithData,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountApi,
            void*, void> processWithoutData,
        object? data) =>
        ProcessCommon<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountApi, TResponse>(
            account,
            data,
            processWithData,
            processWithoutData);
}