using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;


public class Accountless
{
    public Task<TokenResponse> Login(string email, string password) =>
        Dispatcher.Call<TokenResponse>("accountless.login", new { email, password });

    public Task<TokenResponse> Registration(string email, string password, string? displayName = null, bool force = false, byte[]? publicKey = null) =>
        Dispatcher.Call<TokenResponse>("accountless.registration", new { email, password, displayName, force, publicKey });
    
    public Task<object> VerifyEmail(string code) =>
        Dispatcher.Call<object>("accountless.verifyEmail", new { code });

    public Task<object> PasswordReset(string email) =>
        Dispatcher.Call<object>("accountless.passwordReset", new { email });

    public Task<object> PasswordResetVerify(Guid userId, string token, string password) =>
        Dispatcher.Call<object>("accountless.passwordResetVerify", new { userId, token, password });
}