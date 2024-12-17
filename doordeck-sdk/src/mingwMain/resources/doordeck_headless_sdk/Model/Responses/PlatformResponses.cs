using System.Text.Json.Serialization;

namespace Doordeck.Headless.Sdk.Model.Responses
{
    public class ApplicationResponse
    {
        public string applicationId { get; set; }
        public string name { get; set; }
        public double? lastUpdated { get; set; } = null;
        public List<string>? owners { get; set; } = null;
        public List<string>? corsDomains { get; set; } = null;
        public List<string>? authDomains { get; set; } = null;
        public string? logoUrl { get; set; } = null;
        public string? privacyPolicy { get; set; } = null;
        public string? mailingAddress { get; set; } = null;
        public string? companyName { get; set; } = null;
        public string? supportContact { get; set; } = null;
        public string? appLink { get; set; } = null;
        public string? slug { get; set; } = null;
        public EmailPreferencesResponse emailPreferences { get; set; }
        public Dictionary<string, AuthKeyResponse> authKeys { get; set; }
        public OauthResponse? oauth { get; set; } = null;
        public bool? isDoordeckApplication { get; set; } = null;
    }

    public interface AuthKeyResponse
    {
        string kid { get; set; }
        string use { get; set; }
        string? alg { get; set; }
        List<string>? ops { get; set; }
        string? x5u { get; set; }
        string? x5t { get; set; }
        string? x5t256 { get; set; }
        List<string>? x5c { get; set; }
        int? exp { get; set; }
        int? nbf { get; set; }
        int? iat { get; set; }
    }

    public class RsaKeyResponse : AuthKeyResponse
    {
        public string use { get; set; }
        public string kid { get; set; }
        public string? alg { get; set; } = null;
        [JsonPropertyName("key_ops")]
        public List<string>? ops { get; set; } = null;
        public string? x5u { get; set; } = null;
        public string? x5t { get; set; } = null;
        public string? x5t256 { get; set; } = null;
        [JsonPropertyName("x5t#S256")]
        public List<string>? x5c { get; set; } = null;
        public int? exp { get; set; } = null;
        public int? nbf { get; set; } = null;
        public int? iat { get; set; } = null;
        public string e { get; set; }
        public string n { get; set; }
    }

    public class EcKeyResponse : AuthKeyResponse
    {
        public string use { get; set; }
        public string kid { get; set; }
        public string? alg { get; set; } = null;
        [JsonPropertyName("key_ops")]
        public List<string>? ops { get; set; } = null;
        public string? x5u { get; set; } = null;
        public string? x5t { get; set; } = null;
        [JsonPropertyName("x5t#S256")]
        public string? x5t256 { get; set; } = null;
        public List<string>? x5c { get; set; } = null;
        public int? exp { get; set; } = null;
        public int? nbf { get; set; } = null;
        public int? iat { get; set; } = null;
        public string crv { get; set; }
        public string x { get; set; }
        public string y { get; set; }
    }

    public class Ed25519KeyResponse : AuthKeyResponse
    {
        public string use { get; set; }
        public string kid { get; set; }
        public string? alg { get; set; } = null;
        [JsonPropertyName("key_ops")]
        public List<string>? ops { get; set; } = null;
        public string? x5u { get; set; } = null;
        public string? x5t { get; set; } = null;
        [JsonPropertyName("x5t#S256")]
        public string? x5t256 { get; set; } = null;
        public List<string>? x5c { get; set; } = null;
        public int? exp { get; set; } = null;
        public int? nbf { get; set; } = null;
        public int? iat { get; set; } = null;
        public string? d { get; set; } = null;
        public string crv { get; set; }
        public string x { get; set; }
    }

    public class EmailPreferencesResponse
    {
        public string? senderEmail { get; set; } = null;
        public string? senderName { get; set; } = null;
        public string primaryColour { get; set; }
        public string secondaryColour { get; set; }
        public bool? onlySendEssentialEmails { get; set; } = null;
        public EmailCallToActionResponse? callToAction { get; set; } = null;
    }

    public class EmailCallToActionResponse
    {
        public string actionTarget { get; set; }
        public string headline { get; set; }
        public string actionText { get; set; }
    }

    public class OauthResponse
    {
        public string authorizationEndpoint { get; set; }
        public string clientId { get; set; }
        public string grantType { get; set; }
    }

    public class ApplicationOwnerDetailsResponse
    {
        public string userId { get; set; }
        public string email { get; set; }
        public string? displayName { get; set; } = null;
        public bool orphan { get; set; }
        public bool foreign { get; set; }
    }

    public class GetLogoUploadUrlResponse
    {
        public string uploadUrl { get; set; }
    }
}
