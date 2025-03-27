namespace Doordeck.Headless.Sdk.Model;

public class RefreshTokenData(string refreshToken)
{
    public string RefreshToken { get; set; } = refreshToken;
}

public class RegisterEphemeralKeyData(string publicKey)
{
    public string PublicKey { get; set; } = publicKey;
}

public class RegisterEphemeralKeyWithSecondaryAuthenticationData(
    string? publicKey = null,
    TwoFactorMethod? method = null)
{
    public string? PublicKey { get; set; } = publicKey;
    public TwoFactorMethod? Method { get; set; } = method;
}

public class VerifyEphemeralKeyRegistrationData(string code, string? privateKey = null)
{
    public string Code { get; set; } = code;
    public string? PrivateKey { get; set; } = privateKey;
}

public class ChangePasswordData(string oldPassword, string newPassword)
{
    public string OldPassword { get; set; } = oldPassword;
    public string NewPassword { get; set; } = newPassword;
}

public class UpdateUserDetailsData(string displayName)
{
    public string DisplayName { get; set; } = displayName;
}