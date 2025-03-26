using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;

public class Account(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountApi account,
    Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._AccountApi_e__Struct accountApi) : AbstractWrapper
{
    public unsafe Task<TokenResponse> RefreshToken(RefreshTokenData? data) =>
        Process<TokenResponse>(accountApi.refreshToken_, null, data);

    public unsafe Task<object> Logout() =>
        Process<object>(null, accountApi.logout_, null);

    public unsafe Task<RegisterEphemeralKeyResponse> RegisterEphemeralKey(RegisterEphemeralKeyData? data) =>
        Process<RegisterEphemeralKeyResponse>(accountApi.registerEphemeralKey_, null, data);

    public unsafe Task<RegisterEphemeralKeyWithSecondaryAuthenticationResponse> RegisterEphemeralKeyWithSecondaryAuthentication(RegisterEphemeralKeyWithSecondaryAuthenticationData? data) =>
        Process<RegisterEphemeralKeyWithSecondaryAuthenticationResponse>(accountApi.registerEphemeralKeyWithSecondaryAuthentication_, null, data);

    public unsafe Task<RegisterEphemeralKeyResponse> VerifyEphemeralKeyRegistration(VerifyEphemeralKeyRegistrationData data) =>
        Process<RegisterEphemeralKeyResponse>(accountApi.verifyEphemeralKeyRegistration_, null, data);

    public unsafe Task<object> ReverifyEmail() =>
        Process<object>(null, accountApi.reverifyEmail_, null);

    public unsafe Task<object> ChangePassword(ChangePasswordData data) =>
        Process<object>(accountApi.changePassword_, null, data);

    public unsafe Task<UserDetailsResponse> GetUserDetails() =>
        Process<UserDetailsResponse>(null, accountApi.getUserDetails_, null);

    public unsafe Task<object> UpdateUserDetails(UpdateUserDetailsData data) =>
        Process<object>(accountApi.updateUserDetails_, null, data);

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