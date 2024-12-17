namespace Doordeck.Headless.Sdk.Model.Responses
{
    public class TokenResponse
    {
        public string AuthToken { get; set; } = string.Empty;
        public string RefreshToken { get; set; } = string.Empty;
    }

    public class UserDetailsResponse
    {
        public string Email { get; set; } = string.Empty;
        public string? DisplayName { get; set; } = null;
        public bool EmailVerified { get; set; }
        public string PublicKey { get; set; } = string.Empty;
    }

    public class RegisterEphemeralKeyResponse
    {
        public List<string> CertificateChain { get; set; } = [];
        public string UserId { get; set; } = string.Empty;
    }

    public class RegisterEphemeralKeyWithSecondaryAuthenticationResponse
    {
        public TwoFactorMethod Method { get; set; }
    }
}
