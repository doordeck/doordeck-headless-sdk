namespace Doordeck.Headless.Sdk.Model;

public class CreateApplication
{
    public string Name { get; set; }
    public string CompanyName { get; set; }
    public string MailingAddress { get; set; }
    public string? PrivacyPolicy { get; set; } = null;
    public string? SupportContact { get; set; } = null;
    public string? AppLink { get; set; } = null;
    public EmailPreferences? EmailPreferences { get; set; } = null;
    public string? LogoUrl { get; set; } = null;

    public CreateApplication(string name, string companyName, string mailingAddress,
        string? privacyPolicy = null, string? supportContact = null,
        string? appLink = null, EmailPreferences? emailPreferences = null,
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

public class EmailPreferences
{
    public string? SenderEmail { get; set; } = null;
    public string? SenderName { get; set; } = null;
    public string? PrimaryColour { get; set; } = null;
    public string? SecondaryColour { get; set; } = null;
    public bool? OnlySendEssentialEmails { get; set; } = null;
    public EmailCallToAction? CallToAction { get; set; } = null;

    public EmailPreferences(string? senderEmail = null, string? senderName = null,
        string? primaryColour = null, string? secondaryColour = null,
        bool? onlySendEssentialEmails = null, EmailCallToAction? callToAction = null)
    {
        SenderEmail = senderEmail;
        SenderName = senderName;
        PrimaryColour = primaryColour;
        SecondaryColour = secondaryColour;
        OnlySendEssentialEmails = onlySendEssentialEmails;
        CallToAction = callToAction;
    }
}

public class EmailCallToAction(string actionTarget, string headline, string actionText)
{
    public string ActionTarget { get; set; } = actionTarget;
    public string Headline { get; set; } = headline;
    public string ActionText { get; set; } = actionText;
}

public interface AuthKey
{
    string Kid { get; }
    string Kty { get; }
    string Use { get; }
    string? Alg { get; }
}

public class RsaKey : AuthKey
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

    public RsaKey(string use, string kid, string p, string q, string d, string e, string qi, string dp, string dq, string n, string? alg = null)
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

public class EcKey : AuthKey
{
    public string Kty { get; private set; } = "EC";
    public string Use { get; set; }
    public string Kid { get; set; }
    public string? Alg { get; set; } = null;
    public string D { get; set; }
    public string Crv { get; set; }
    public string X { get; set; }
    public string Y { get; set; }

    public EcKey(string use, string kid, string d, string crv, string x, string y, string? alg = null)
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

public class Ed25519Key : AuthKey
{
    public string Kty { get; private set; } = "OKP";
    public string Use { get; set; }
    public string Kid { get; set; }
    public string? Alg { get; set; } = null;
    public string D { get; set; }
    public string Crv { get; set; }
    public string X { get; set; }

    public Ed25519Key(string use, string kid, string d, string crv, string x, string? alg = null)
    {
        Use = use;
        Kid = kid;
        D = d;
        Crv = crv;
        X = x;
        Alg = alg;
    }
}