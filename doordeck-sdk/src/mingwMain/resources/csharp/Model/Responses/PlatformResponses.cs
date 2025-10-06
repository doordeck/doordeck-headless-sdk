using System.Text.Json.Serialization;

namespace Doordeck.Headless.Sdk.Model.Responses;

public class ApplicationResponse
{
    public required Guid ApplicationId { get; set; }
    public required string Name { get; set; }
    public DateTime? LastUpdated { get; set; }
    public List<Guid>? Owners { get; set; }
    public List<Uri>? CorsDomains { get; set; }
    public List<Uri>? AuthDomains { get; set; }
    public Uri? LogoUrl { get; set; }
    public Uri? PrivacyPolicy { get; set; }
    public string? MailingAddress { get; set; }
    public string? CompanyName { get; set; }
    public Uri? SupportContact { get; set; }
    public Uri? AppLink { get; set; }
    public string? Slug { get; set; }
    public required EmailPreferencesResponse EmailPreferences { get; set; }
    public required Dictionary<string, AuthKeyResponse> AuthKeys { get; set; }
    public OauthResponse? Oauth { get; set; }
    public bool? IsDoordeckApplication { get; set; }
}

public interface IAuthKeyResponse;

[JsonPolymorphic(TypeDiscriminatorPropertyName = "kty")]
[JsonDerivedType(typeof(RsaKeyResponse), "RSA")]
[JsonDerivedType(typeof(EcKeyResponse), "EC")]
[JsonDerivedType(typeof(Ed25519KeyResponse), "OKP")]
public abstract class AuthKeyResponse : IAuthKeyResponse;

public class RsaKeyResponse : AuthKeyResponse
{
    public required string Kty { get; set; }
    public required string Kid { get; set; }
    public required string Use { get; set; }
    public string? Alg { get; set; }
    [JsonPropertyName("key_ops")]
    public List<string>? Ops { get; set; }
    public string? X5U { get; set; }
    public string? X5T { get; set; }
    [JsonPropertyName("x5t#S256")]
    public string? X5T256 { get; set; }
    public List<string>? X5C { get; set; }
    public int? Exp { get; set; }
    public int? Nbf { get; set; }
    public int? Iat { get; set; }
    public required string E { get; set; }
    public required string N { get; set; }
}

public class EcKeyResponse : AuthKeyResponse
{
    public required string Kty { get; set; }
    public required string Kid { get; set; }
    public required string Use { get; set; }
    public string? Alg { get; set; }
    [JsonPropertyName("key_ops")]
    public List<string>? Ops { get; set; }
    public string? X5U { get; set; }
    public string? X5T { get; set; }
    [JsonPropertyName("x5t#S256")]
    public string? X5T256 { get; set; }
    public List<string>? X5C { get; set; }
    public int? Exp { get; set; }
    public int? Nbf { get; set; }
    public int? Iat { get; set; }
    public required string Crv { get; set; }
    public required string X { get; set; }
    public required string Y { get; set; }
}

public class Ed25519KeyResponse : AuthKeyResponse
{
    public required string Kty { get; set; }
    public required string Kid { get; set; }
    public required string Use { get; set; }
    public string? Alg { get; set; }
    [JsonPropertyName("key_ops")]
    public List<string>? Ops { get; set; }
    public string? X5U { get; set; }
    public string? X5T { get; set; }
    [JsonPropertyName("x5t#S256")]
    public string? X5T256 { get; set; }
    public List<string>? X5C { get; set; }
    public int? Exp { get; set; }
    public int? Nbf { get; set; }
    public int? Iat { get; set; }
    public required string Crv { get; set; }
    public required string X { get; set; }
}

public class EmailPreferencesResponse
{
    public string? SenderEmail { get; set; }
    public string? SenderName { get; set; }
    public required string PrimaryColour { get; set; }
    public required string SecondaryColour { get; set; }
    public bool? OnlySendEssentialEmails { get; set; }
    public required EmailCallToActionResponse? CallToAction { get; set; }
}

public class EmailCallToActionResponse
{
    public required Uri ActionTarget { get; set; }
    public required string Headline { get; set; }
    public required string ActionText { get; set; }
}

public class OauthResponse
{
    public required string AuthorizationEndpoint { get; set; }
    public required string ClientId { get; set; }
    public required GrantType GrantType { get; set; }
}

public class ApplicationOwnerDetailsResponse
{
    public Guid UserId { get; set; }
    public required string Email { get; set; }
    public string? DisplayName { get; set; }
    public required bool Orphan { get; set; }
    public required bool Foreign { get; set; }
}

public class GetLogoUploadUrlResponse
{
    public required Uri UploadUrl { get; set; }
}