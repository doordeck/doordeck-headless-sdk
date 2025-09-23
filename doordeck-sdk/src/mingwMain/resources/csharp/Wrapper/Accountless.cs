using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;

using AccountlessApi = Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountlessApi;

public class Accountless(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountlessApi accountless,
    Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._AccountlessApi_e__Struct accountlessApi) : AbstractWrapper
{
    public unsafe Task<TokenResponse> Login(string email, string password) =>
        Process<AccountlessApi, TokenResponse>(accountless, accountlessApi.login_, new { email, password });

    public unsafe Task<TokenResponse> Registration(string email, string password, string? displayName = null, bool force = false, string? publicKey = null) =>
        Process<AccountlessApi, TokenResponse>(accountless, accountlessApi.registration_, new { email, password, displayName, force, publicKey });
    
    public unsafe Task<object> VerifyEmail(string code) =>
        Process<AccountlessApi, object>(accountless, accountlessApi.verifyEmail_, new { code });

    public unsafe Task<object> PasswordReset(string email) =>
        Process<AccountlessApi, object>(accountless, accountlessApi.passwordReset_, new { email });

    public unsafe Task<object> PasswordResetVerify(string userId, string token, string password) =>
        Process<AccountlessApi, object>(accountless, accountlessApi.passwordResetVerify_, new { userId, token, password });
}