using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;

public class Accountless(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountlessApi accountless,
    Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._AccountlessApi_e__Struct accountlessApi) : AbstractWrapper
{
    public unsafe Task<TokenResponse> Login(string email, string password) =>
        Process<TokenResponse>(accountlessApi.login_, null, new { email, password });

    public unsafe Task<TokenResponse> Registration(string email, string password, string? displayName = null,
        bool force = false, string? publicKey = null) =>
        Process<TokenResponse>(accountlessApi.registration_, null, new { email, password, displayName, force, publicKey });
    
    public unsafe Task<object> VerifyEmail(string code) =>
        Process<object>(accountlessApi.verifyEmail_, null, new { code });

    public unsafe Task<object> PasswordReset(string email) =>
        Process<object>(accountlessApi.passwordReset_, null, new { email });

    public unsafe Task<object> PasswordResetVerify(string userId, string token, string password) =>
        Process<object>(accountlessApi.passwordResetVerify_, null, new { userId, token, password });

    private unsafe Task<TResponse> Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountlessApi, sbyte*, void*, void> processWithData,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountlessApi, void*, void> processWithoutData,
        object? data = null) => 
        ProcessCommon<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountlessApi, TResponse>(
            accountless,
            data,
            processWithData,
            processWithoutData);
}