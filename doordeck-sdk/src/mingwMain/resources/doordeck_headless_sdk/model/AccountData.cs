namespace Doordeck.Headless.Sdk.model
{
    public class RefreshTokenData
    {
        public string refreshToken { get; set; }

        public RefreshTokenData(string refreshToken)
        {
            this.refreshToken = refreshToken;
        }
    }

    public class RegisterEphemeralKeyData
    {
        public string publicKey { get; set; }

        public RegisterEphemeralKeyData(string publicKey)
        {
            this.publicKey = publicKey;
        }
    }

    public class RegisterEphemeralKeyWithSecondaryAuthenticationData
    {
        public string? publicKey { get; set; }
        public TwoFactorMethod? method { get; set; }

        public RegisterEphemeralKeyWithSecondaryAuthenticationData(string? publicKey = null, TwoFactorMethod? method = null)
        {
            this.publicKey = publicKey;
            this.method = method;
        }
    }

    public class VerifyEphemeralKeyRegistrationData
    {
        public string code { get; set; }
        public string? privateKey { get; set; }

        public VerifyEphemeralKeyRegistrationData(string code, string? privateKey = null)
        {
            this.code = code;
            this.privateKey = privateKey;
        }
    }

    public class ChangePasswordData
    {
        public string oldPassword { get; set; }
        public string newPassword { get; set; }

        public ChangePasswordData(string oldPassword, string newPassword)
        {
            this.oldPassword = oldPassword;
            this.newPassword = newPassword;
        }
    }

    public class UpdateUserDetailsData
    {
        public string displayName { get; set; }

        public UpdateUserDetailsData(string displayName)
        {
            this.displayName = displayName;
        }
    }
}
