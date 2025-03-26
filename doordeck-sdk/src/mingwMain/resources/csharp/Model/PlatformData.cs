namespace Doordeck.Headless.Sdk.Model;

public class CreateApplicationData
{
    public string Name { get; set; }
    public string CompanyName { get; set; }
    public string MailingAddress { get; set; }
    public string? PrivacyPolicy { get; set; } = null;
    public string? SupportContact { get; set; } = null;
    public string? AppLink { get; set; } = null;
    public EmailPreferencesData? EmailPreferences { get; set; } = null;
    public string? LogoUrl { get; set; } = null;

    public CreateApplicationData(string name, string companyName, string mailingAddress,
        string? privacyPolicy = null, string? supportContact = null,
        string? appLink = null, EmailPreferencesData? emailPreferences = null,
        string? logoUrl = null)
    {
        Name = name;
        CompanyName = companyName;
        MailingAddress = mailingAddress;
        PrivacyPolicy = privacyPolicy;
        SupportContact = supportContact;
        AppLink = appLink;
        EmailPreferences = emailPreferences;
        LogoUrl = logoUrl;
    }
}

public class EmailPreferencesData
{
    public string? SenderEmail { get; set; } = null;
    public string? SenderName { get; set; } = null;
    public string? PrimaryColour { get; set; } = null;
    public string? SecondaryColour { get; set; } = null;
    public bool? OnlySendEssentialEmails { get; set; } = null;
    public EmailCallToActionData? CallToAction { get; set; } = null;

    public EmailPreferencesData(string? senderEmail = null, string? senderName = null,
        string? primaryColour = null, string? secondaryColour = null,
        bool? onlySendEssentialEmails = null, EmailCallToActionData? callToAction = null)
    {
        SenderEmail = senderEmail;
        SenderName = senderName;
        PrimaryColour = primaryColour;
        SecondaryColour = secondaryColour;
        OnlySendEssentialEmails = onlySendEssentialEmails;
        CallToAction = callToAction;
    }
}

public class EmailCallToActionData(string actionTarget, string headline, string actionText)
{
    public string ActionTarget { get; set; } = actionTarget;
    public string Headline { get; set; } = headline;
    public string ActionText { get; set; } = actionText;
}

public class ApplicationIdData(string applicationId)
{
    public string ApplicationId { get; set; } = applicationId;
}

public class UpdateApplicationNameData(string applicationId, string name)
{
    public string ApplicationId { get; set; } = applicationId;
    public string Name { get; set; } = name;
}

public class UpdateApplicationCompanyNameData(string applicationId, string companyName)
{
    public string ApplicationId { get; set; } = applicationId;
    public string CompanyName { get; set; } = companyName;
}

public class UpdateApplicationMailingAddressData(string applicationId, string mailingAddress)
{
    public string ApplicationId { get; set; } = applicationId;
    public string MailingAddress { get; set; } = mailingAddress;
}

public class UpdateApplicationPrivacyPolicyData(string applicationId, string privacyPolicy)
{
    public string ApplicationId { get; set; } = applicationId;
    public string PrivacyPolicy { get; set; } = privacyPolicy;
}

public class UpdateApplicationSupportContactData(string applicationId, string supportContact)
{
    public string ApplicationId { get; set; } = applicationId;
    public string SupportContact { get; set; } = supportContact;
}

public class UpdateApplicationAppLinkData(string applicationId, string appLink)
{
    public string ApplicationId { get; set; } = applicationId;
    public string AppLink { get; set; } = appLink;
}

public class UpdateApplicationEmailPreferencesData(string applicationId, EmailPreferencesData emailPreferences)
{
    public string ApplicationId { get; set; } = applicationId;
    public EmailPreferencesData EmailPreferences { get; set; } = emailPreferences;
}

public class UpdateApplicationLogoUrlData(string applicationId, string logoUrl)
{
    public string ApplicationId { get; set; } = applicationId;
    public string LogoUrl { get; set; } = logoUrl;
}

public class GetLogoUploadUrlData(string applicationId, string contentType)
{
    public string ApplicationId { get; set; } = applicationId;
    public string ContentType { get; set; } = contentType;
}

public class AddAuthKeyData(string applicationId, AuthKeyData key)
{
    public string ApplicationId { get; set; } = applicationId;
    public AuthKeyData Key { get; set; } = key;
}

public interface AuthKeyData
{
    string Kid { get; }
    string Kty { get; }
    string Use { get; }
    string? Alg { get; }
}

public class RsaKeyData : AuthKeyData
{
    public string Kty { get; private set; } = "RSA";
    public string Use { get; set; }
    public string Kid { get; set; }
    public string? Alg { get; set; } = null;
    public string P { get; set; }
    public string Q { get; set; }
    public string D { get; set; }
    public string E { get; set; }
    public string Qi { get; set; }
    public string Dp { get; set; }
    public string Dq { get; set; }
    public string N { get; set; }

    public RsaKeyData(string use, string kid, string p, string q, string d, string e, string qi, string dp, string dq, string n, string? alg = null)
    {
        Use = use;
        Kid = kid;
        P = p;
        Q = q;
        D = d;
        E = e;
        Qi = qi;
        Dp = dp;
        Dq = dq;
        N = n;
        Alg = alg;
    }
}

public class EcKeyData : AuthKeyData
{
    public string Kty { get; private set; } = "EC";
    public string Use { get; set; }
    public string Kid { get; set; }
    public string? Alg { get; set; } = null;
    public string D { get; set; }
    public string Crv { get; set; }
    public string X { get; set; }
    public string Y { get; set; }

    public EcKeyData(string use, string kid, string d, string crv, string x, string y, string? alg = null)
    {
        Use = use;
        Kid = kid;
        D = d;
        Crv = crv;
        X = x;
        Y = y;
        Alg = alg;
    }
}

public class Ed25519KeyData : AuthKeyData
{
    public string Kty { get; private set; } = "OKP";
    public string Use { get; set; }
    public string Kid { get; set; }
    public string? Alg { get; set; } = null;
    public string D { get; set; }
    public string Crv { get; set; }
    public string X { get; set; }

    public Ed25519KeyData(string use, string kid, string d, string crv, string x, string? alg = null)
    {
        Use = use;
        Kid = kid;
        D = d;
        Crv = crv;
        X = x;
        Alg = alg;
    }
}

public class AuthIssuerData(string applicationId, string url)
{
    public string ApplicationId { get; set; } = applicationId;
    public string Url { get; set; } = url;
}

public class CorsDomainData(string applicationId, string url)
{
    public string ApplicationId { get; set; } = applicationId;
    public string Url { get; set; } = url;
}

public class ApplicationOwnerData(string applicationId, string userId)
{
    public string ApplicationId { get; set; } = applicationId;
    public string UserId { get; set; } = userId;
}