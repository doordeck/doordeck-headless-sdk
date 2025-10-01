namespace Doordeck.Headless.Sdk.Model;

public class CreateApplication(
    string name,
    string companyName,
    string mailingAddress,
    Uri? privacyPolicy = null,
    Uri? supportContact = null,
    Uri? appLink = null,
    EmailPreferences? emailPreferences = null,
    Uri? logoUrl = null)
{
    public string Name { get; set; } = name;
    public string CompanyName { get; set; } = companyName;
    public string MailingAddress { get; set; } = mailingAddress;
    public Uri? PrivacyPolicy { get; set; } = privacyPolicy;
    public Uri? SupportContact { get; set; } = supportContact;
    public Uri? AppLink { get; set; } = appLink;
    public EmailPreferences? EmailPreferences { get; set; } = emailPreferences;
    public Uri? LogoUrl { get; set; } = logoUrl;
}

public class EmailPreferences(
    string? senderEmail = null,
    string? senderName = null,
    string? primaryColour = null,
    string? secondaryColour = null,
    bool? onlySendEssentialEmails = null,
    EmailCallToAction? callToAction = null)
{
    public string? SenderEmail { get; set; } = senderEmail;
    public string? SenderName { get; set; } = senderName;
    public string? PrimaryColour { get; set; } = primaryColour;
    public string? SecondaryColour { get; set; } = secondaryColour;
    public bool? OnlySendEssentialEmails { get; set; } = onlySendEssentialEmails;
    public EmailCallToAction? CallToAction { get; set; } = callToAction;
}

public class EmailCallToAction(Uri actionTarget, string headline, string actionText)
{
    public Uri ActionTarget { get; set; } = actionTarget;
    public string Headline { get; set; } = headline;
    public string ActionText { get; set; } = actionText;
}

public interface IAuthKey
{
    string Kid { get; }
    string Kty { get; }
    string Use { get; }
    string? Alg { get; }
}

public class RsaKey(string use, string kid, string e, string n, string? alg = null)
    : IAuthKey
{
    public string Kty { get; } = "RSA";
    public string Use { get; set; } = use;
    public string Kid { get; set; } = kid;
    public string? Alg { get; set; } = alg;
    public string E { get; set; } = e;
    public string N { get; set; } = n;
}

public class EcKey(string use, string kid, string crv, string x, string y, string? alg = null)
    : IAuthKey
{
    public string Kty { get; } = "EC";
    public string Use { get; set; } = use;
    public string Kid { get; set; } = kid;
    public string? Alg { get; set; } = alg;
    public string Crv { get; set; } = crv;
    public string X { get; set; } = x;
    public string Y { get; set; } = y;
}

public class Ed25519Key(string use, string kid, string crv, string x, string? alg = null)
    : IAuthKey
{
    public string Kty { get; } = "OKP";
    public string Use { get; set; } = use;
    public string Kid { get; set; } = kid;
    public string? Alg { get; set; } = alg;
    public string Crv { get; set; } = crv;
    public string X { get; set; } = x;
}