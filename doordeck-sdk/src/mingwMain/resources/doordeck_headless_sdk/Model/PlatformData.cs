namespace Doordeck.Headless.Sdk.Model
{
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

    public class EmailCallToActionData
    {
        public string ActionTarget { get; set; }
        public string Headline { get; set; }
        public string ActionText { get; set; }

        public EmailCallToActionData(string actionTarget, string headline, string actionText)
        {
            ActionTarget = actionTarget;
            Headline = headline;
            ActionText = actionText;
        }
    }

    public class GetApplicationData
    {
        public string ApplicationId { get; set; }

        public GetApplicationData(string applicationId)
        {
            ApplicationId = applicationId;
        }
    }

    public class UpdateApplicationNameData
    {
        public string ApplicationId { get; set; }
        public string Name { get; set; }

        public UpdateApplicationNameData(string applicationId, string name)
        {
            ApplicationId = applicationId;
            Name = name;
        }
    }

    public class UpdateApplicationCompanyNameData
    {
        public string ApplicationId { get; set; }
        public string CompanyName { get; set; }

        public UpdateApplicationCompanyNameData(string applicationId, string companyName)
        {
            ApplicationId = applicationId;
            CompanyName = companyName;
        }
    }

    public class UpdateApplicationMailingAddressData
    {
        public string ApplicationId { get; set; }
        public string MailingAddress { get; set; }

        public UpdateApplicationMailingAddressData(string applicationId, string mailingAddress)
        {
            ApplicationId = applicationId;
            MailingAddress = mailingAddress;
        }
    }

    public class UpdateApplicationPrivacyPolicyData
    {
        public string ApplicationId { get; set; }
        public string PrivacyPolicy { get; set; }

        public UpdateApplicationPrivacyPolicyData(string applicationId, string privacyPolicy)
        {
            ApplicationId = applicationId;
            PrivacyPolicy = privacyPolicy;
        }
    }

    public class UpdateApplicationSupportContactData
    {
        public string ApplicationId { get; set; }
        public string SupportContact { get; set; }

        public UpdateApplicationSupportContactData(string applicationId, string supportContact)
        {
            ApplicationId = applicationId;
            SupportContact = supportContact;
        }
    }

    public class UpdateApplicationAppLinkData
    {
        public string ApplicationId { get; set; }
        public string AppLink { get; set; }

        public UpdateApplicationAppLinkData(string applicationId, string appLink)
        {
            ApplicationId = applicationId;
            AppLink = appLink;
        }
    }

    public class UpdateApplicationEmailPreferencesData
    {
        public string ApplicationId { get; set; }
        public EmailPreferencesData EmailPreferences { get; set; }

        public UpdateApplicationEmailPreferencesData(string applicationId, EmailPreferencesData emailPreferences)
        {
            ApplicationId = applicationId;
            EmailPreferences = emailPreferences;
        }
    }

    public class UpdateApplicationLogoUrlData
    {
        public string ApplicationId { get; set; }
        public string LogoUrl { get; set; }

        public UpdateApplicationLogoUrlData(string applicationId, string logoUrl)
        {
            ApplicationId = applicationId;
            LogoUrl = logoUrl;
        }
    }

    public class DeleteApplicationData
    {
        public string ApplicationId { get; set; }

        public DeleteApplicationData(string applicationId)
        {
            ApplicationId = applicationId;
        }
    }

    public class GetLogoUploadUrlData
    {
        public string ApplicationId { get; set; }
        public string ContentType { get; set; }

        public GetLogoUploadUrlData(string applicationId, string contentType)
        {
            ApplicationId = applicationId;
            ContentType = contentType;
        }
    }

    public class AddAuthKeyData
    {
        public string ApplicationId { get; set; }
        public AuthKeyData Key { get; set; }

        public AddAuthKeyData(string applicationId, AuthKeyData key)
        {
            ApplicationId = applicationId;
            Key = key;
        }
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

    public class AddAuthIssuerData
    {
        public string ApplicationId { get; set; }
        public string Url { get; set; }

        public AddAuthIssuerData(string applicationId, string url)
        {
            ApplicationId = applicationId;
            Url = url;
        }
    }

    public class DeleteAuthIssuerData
    {
        public string ApplicationId { get; set; }
        public string Url { get; set; }

        public DeleteAuthIssuerData(string applicationId, string url)
        {
            ApplicationId = applicationId;
            Url = url;
        }
    }

    public class AddCorsDomainData
    {
        public string ApplicationId { get; set; }
        public string Url { get; set; }

        public AddCorsDomainData(string applicationId, string url)
        {
            ApplicationId = applicationId;
            Url = url;
        }
    }

    public class RemoveCorsDomainData
    {
        public string ApplicationId { get; set; }
        public string Url { get; set; }

        public RemoveCorsDomainData(string applicationId, string url)
        {
            ApplicationId = applicationId;
            Url = url;
        }
    }

    public class AddApplicationOwnerData
    {
        public string ApplicationId { get; set; }
        public string UserId { get; set; }

        public AddApplicationOwnerData(string applicationId, string userId)
        {
            ApplicationId = applicationId;
            UserId = userId;
        }
    }

    public class RemoveApplicationOwnerData
    {
        public string ApplicationId { get; set; }
        public string UserId { get; set; }

        public RemoveApplicationOwnerData(string applicationId, string userId)
        {
            ApplicationId = applicationId;
            UserId = userId;
        }
    }

    public class GetApplicationOwnersDetailsData
    {
        public string ApplicationId { get; set; }

        public GetApplicationOwnersDetailsData(string applicationId)
        {
            ApplicationId = applicationId;
        }
    }
}
