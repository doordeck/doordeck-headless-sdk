namespace Doordeck.Headless.Sdk.Model;

public class LoginData(string email, string password)
{
    public string Email { get; set; } = email;
    public string Password { get; set; } = password;
}

public class RegistrationData(
    string email,
    string password,
    string? displayName = null,
    bool force = false,
    string? publicKey = null)
{
    public string Email { get; set; } = email;
    public string Password { get; set; } = password;
    public string? DisplayName { get; set; } = displayName;
    public bool Force { get; set; } = force;
    public string? PublicKey { get; set; } = publicKey;
}

public class VerifyEmailData(string code)
{
    public string Code { get; set; } = code;
}

public class PasswordResetData(string email)
{
    public string Email { get; set; } = email;
}

public class PasswordResetVerifyData(string userId, string token, string password)
{
    public string UserId { get; set; } = userId;
    public string Token { get; set; } = token;
    public string Password { get; set; } = password;
}