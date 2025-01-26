namespace Doordeck.Headless.Sdk.Model
{
    public class RefreshTokenData
    {
        public string RefreshToken { get; set; }

        public RefreshTokenData(string refreshToken)
        {
            RefreshToken = refreshToken;
        }
    }

    public class RegisterEphemeralKeyData
    {
        public string PublicKey { get; set; }

        public RegisterEphemeralKeyData(string publicKey)
        {
            PublicKey = publicKey;
        }
    }

    public class RegisterEphemeralKeyWithSecondaryAuthenticationData
    {
        public string? PublicKey { get; set; }
        public TwoFactorMethod? Method { get; set; }

        public RegisterEphemeralKeyWithSecondaryAuthenticationData(string? publicKey = null, TwoFactorMethod? method = null)
        {
            PublicKey = publicKey;
            Method = method;
        }
    }

    public class VerifyEphemeralKeyRegistrationData
    {
        public string Code { get; set; }
        public string? PrivateKey { get; set; }

        public VerifyEphemeralKeyRegistrationData(string code, string? privateKey = null)
        {
            Code = code;
            PrivateKey = privateKey;
        }
    }

    public class ChangePasswordData
    {
        public string OldPassword { get; set; }
        public string NewPassword { get; set; }

        public ChangePasswordData(string oldPassword, string newPassword)
        {
            OldPassword = oldPassword;
            NewPassword = newPassword;
        }
    }

    public class UpdateUserDetailsData
    {
        public string DisplayName { get; set; }

        public UpdateUserDetailsData(string displayName)
        {
            DisplayName = displayName;
        }
    }
}
