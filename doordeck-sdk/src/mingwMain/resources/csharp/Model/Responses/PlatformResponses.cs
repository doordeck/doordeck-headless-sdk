using System.Text.Json.Serialization;

namespace Doordeck.Headless.Sdk.Model.Responses;

public class ApplicationResponse
{
    public string ApplicationId { get; set; } = string.Empty;
    public string Name { get; set; } = string.Empty;
    public double? LastUpdated { get; set; } = null;
    public List<string>? Owners { get; set; } = null;
    public List<string>? CorsDomains { get; set; } = null;
    public List<string>? AuthDomains { get; set; } = null;
    public string? LogoUrl { get; set; } = null;
    public string? PrivacyPolicy { get; set; } = null;
    public string? MailingAddress { get; set; } = null;
    public string? CompanyName { get; set; } = null;
    public string? SupportContact { get; set; } = null;
    public string? AppLink { get; set; } = null;
    public string? Slug { get; set; } = null;
    public EmailPreferencesResponse EmailPreferences { get; set; } = new EmailPreferencesResponse();
    public Dictionary<string, IAuthKeyResponse> AuthKeys { get; set; } = [];
    public OauthResponse? Oauth { get; set; } = null;
    public bool? IsDoordeckApplication { get; set; } = null;
}

public interface IAuthKeyResponse
{
    string Kid { get; set; }
    string Use { get; set; }
    string? Alg { get; set; }
    List<string>? Ops { get; set; }
    string? X5u { get; set; }
    string? X5t { get; set; }
    string? X5t256 { get; set; }
    List<string>? X5c { get; set; }
    int? Exp { get; set; }
    int? Nbf { get; set; }
    int? Iat { get; set; }
}

public class RsaKeyResponse : IAuthKeyResponse
{
    public string Use { get; set; } = string.Empty;
    public string Kid { get; set; } = string.Empty;
    public string? Alg { get; set; } = null;
    [JsonPropertyName("key_ops")]
    public List<string>? Ops { get; set; } = null;
    public string? X5u { get; set; } = null;
    public string? X5t { get; set; } = null;
    public string? X5t256 { get; set; } = null;
    [JsonPropertyName("x5t#S256")]
    public List<string>? X5c { get; set; } = null;
    public int? Exp { get; set; } = null;
    public int? Nbf { get; set; } = null;
    public int? Iat { get; set; } = null;
    public string E { get; set; } = string.Empty;
    public string N { get; set; } = string.Empty;
}

public class EcKeyResponse : IAuthKeyResponse
{
    public string Use { get; set; }
    public string Kid { get; set; }
    public string? Alg { get; set; } = null;
    [JsonPropertyName("key_ops")]
    public List<string>? Ops { get; set; } = null;
    public string? X5u { get; set; } = null;
    public string? X5t { get; set; } = null;
    [JsonPropertyName("x5t#S256")]
    public string? X5t256 { get; set; } = null;
    public List<string>? X5c { get; set; } = null;
    public int? Exp { get; set; } = null;
    public int? Nbf { get; set; } = null;
    public int? Iat { get; set; } = null;
    public string Crv { get; set; } = string.Empty;
    public string X { get; set; } = string.Empty;
    public string Y { get; set; } = string.Empty;
}

public class Ed25519KeyResponse : IAuthKeyResponse
{
    public string Use { get; set; } = string.Empty;
    public string Kid { get; set; } = string.Empty;
    public string? Alg { get; set; } = null;
    [JsonPropertyName("key_ops")]
    public List<string>? Ops { get; set; } = null;
    public string? X5u { get; set; } = null;
    public string? X5t { get; set; } = null;
    [JsonPropertyName("x5t#S256")]
    public string? X5t256 { get; set; } = null;
    public List<string>? X5c { get; set; } = null;
    public int? Exp { get; set; } = null;
    public int? Nbf { get; set; } = null;
    public int? Iat { get; set; } = null;
    public string? D { get; set; } = null;
    public string Crv { get; set; } = string.Empty;
    public string X { get; set; } = string.Empty;
}

public class EmailPreferencesResponse
{
    public string? SenderEmail { get; set; } = null;
    public string? SenderName { get; set; } = null;
    public string PrimaryColour { get; set; } = string.Empty;
    public string SecondaryColour { get; set; } = string.Empty;
    public bool? OnlySendEssentialEmails { get; set; } = null;
    public EmailCallToActionResponse? CallToAction { get; set; } = null;
}

public class EmailCallToActionResponse
{
    public string ActionTarget { get; set; } = string.Empty;
    public string Headline { get; set; } = string.Empty;
    public string ActionText { get; set; } = string.Empty;
}

public class OauthResponse
{
    public string AuthorizationEndpoint { get; set; } = string.Empty;
    public string ClientId { get; set; } = string.Empty;
    public string GrantType { get; set; } = string.Empty;
}

public class ApplicationOwnerDetailsResponse
{
    public string UserId { get; set; } = string.Empty;
    public string Email { get; set; } = string.Empty;
    public string? DisplayName { get; set; } = null;
    public bool Orphan { get; set; }
    public bool Foreign { get; set; }
}

public class GetLogoUploadUrlResponse
{
    public string UploadUrl { get; set; } = string.Empty;
}