using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;

public class Accountless(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountlessApi accountless,
    Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._AccountlessApi_e__Struct accountlessApi) : AbstractWrapper
{
    public unsafe Task<TokenResponse> Login(LoginData data) =>
        Process<TokenResponse>(accountlessApi.login_, null, data);

    public unsafe Task<TokenResponse> Registration(RegistrationData data) =>
        Process<TokenResponse>(accountlessApi.registration_, null, data);
    
    public unsafe Task<object> VerifyEmail(VerifyEmailData data) =>
        Process<object>(accountlessApi.verifyEmail_, null, data);

    public unsafe Task<object> PasswordReset(PasswordResetData data) =>
        Process<object>(accountlessApi.passwordReset_, null, data);

    public unsafe Task<object> PasswordResetVerify(PasswordResetVerifyData data) =>
        Process<object>(accountlessApi.passwordResetVerify_, null, data);

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