using System.Security.Cryptography.X509Certificates;

namespace Doordeck.Headless.Sdk.Model.Responses;

public class TokenResponse
{
    public required string AuthToken { get; set; }
    public required string RefreshToken { get; set; }
}

public class UserDetailsResponse
{
    public required string Email { get; set; }
    public string? DisplayName { get; set; }
    public required bool EmailVerified { get; set; }
    public required string PublicKey { get; set; }
}

public class RegisterEphemeralKeyResponse
{
    public required List<X509Certificate> CertificateChain { get; set; }
    public required Guid UserId { get; set; }
}

public class RegisterEphemeralKeyWithSecondaryAuthenticationResponse
{
    public required TwoFactorMethod Method { get; set; }
}