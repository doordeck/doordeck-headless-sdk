using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;

using AccountlessApi = Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountlessApi;

public class Accountless(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountlessApi accountless) : AbstractWrapper
{
    public unsafe Task<TokenResponse> Login(string email, string password) =>
        Process<AccountlessApi, TokenResponse>(accountless, &Methods.login, new { email, password });

    public unsafe Task<TokenResponse> Registration(string email, string password, string? displayName = null, bool force = false, byte[]? publicKey = null) =>
        Process<AccountlessApi, TokenResponse>(accountless, &Methods.registration, new { email, password, displayName, force, publicKey });
    
    public unsafe Task<object> VerifyEmail(string code) =>
        Process<AccountlessApi, object>(accountless, &Methods.verifyEmail, new { code });

    public unsafe Task<object> PasswordReset(string email) =>
        Process<AccountlessApi, object>(accountless, &Methods.passwordReset, new { email });

    public unsafe Task<object> PasswordResetVerify(Guid userId, string token, string password) =>
        Process<AccountlessApi, object>(accountless, &Methods.passwordResetVerify, new { userId, token, password });
}