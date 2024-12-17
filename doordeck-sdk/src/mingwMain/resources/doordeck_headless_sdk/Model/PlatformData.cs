namespace Doordeck.Headless.Sdk.Model
{
    public class CreateApplicationData
    {
        public string name { get; set; }
        public string companyName { get; set; }
        public string mailingAddress { get; set; }
        public string? privacyPolicy { get; set; } = null;
        public string? supportContact { get; set; } = null;
        public string? appLink { get; set; } = null;
        public EmailPreferencesData? emailPreferences { get; set; } = null;
        public string? logoUrl { get; set; } = null;

        public CreateApplicationData(string name, string companyName, string mailingAddress,
                                      string? privacyPolicy = null, string? supportContact = null,
                                      string? appLink = null, EmailPreferencesData? emailPreferences = null,
                                      string? logoUrl = null)
        {
            this.name = name;
            this.companyName = companyName;
            this.mailingAddress = mailingAddress;
            this.privacyPolicy = privacyPolicy;
            this.supportContact = supportContact;
            this.appLink = appLink;
            this.emailPreferences = emailPreferences;
            this.logoUrl = logoUrl;
        }
    }

    public class EmailPreferencesData
    {
        public string? senderEmail { get; set; } = null;
        public string? senderName { get; set; } = null;
        public string? primaryColour { get; set; } = null;
        public string? secondaryColour { get; set; } = null;
        public bool? onlySendEssentialEmails { get; set; } = null;
        public EmailCallToActionData? callToAction { get; set; } = null;

        public EmailPreferencesData(string? senderEmail = null, string? senderName = null,
                                    string? primaryColour = null, string? secondaryColour = null,
                                    bool? onlySendEssentialEmails = null, EmailCallToActionData? callToAction = null)
        {
            this.senderEmail = senderEmail;
            this.senderName = senderName;
            this.primaryColour = primaryColour;
            this.secondaryColour = secondaryColour;
            this.onlySendEssentialEmails = onlySendEssentialEmails;
            this.callToAction = callToAction;
        }
    }

    public class EmailCallToActionData
    {
        public string actionTarget { get; set; }
        public string headline { get; set; }
        public string actionText { get; set; }

        public EmailCallToActionData(string actionTarget, string headline, string actionText)
        {
            this.actionTarget = actionTarget;
            this.headline = headline;
            this.actionText = actionText;
        }
    }

    public class GetApplicationData
    {
        public string applicationId { get; set; }

        public GetApplicationData(string applicationId)
        {
            this.applicationId = applicationId;
        }
    }

    public class UpdateApplicationNameData
    {
        public string applicationId { get; set; }
        public string name { get; set; }

        public UpdateApplicationNameData(string applicationId, string name)
        {
            this.applicationId = applicationId;
            this.name = name;
        }
    }

    public class UpdateApplicationCompanyNameData
    {
        public string applicationId { get; set; }
        public string companyName { get; set; }

        public UpdateApplicationCompanyNameData(string applicationId, string companyName)
        {
            this.applicationId = applicationId;
            this.companyName = companyName;
        }
    }

    public class UpdateApplicationMailingAddressData
    {
        public string applicationId { get; set; }
        public string mailingAddress { get; set; }

        public UpdateApplicationMailingAddressData(string applicationId, string mailingAddress)
        {
            this.applicationId = applicationId;
            this.mailingAddress = mailingAddress;
        }
    }

    public class UpdateApplicationPrivacyPolicyData
    {
        public string applicationId { get; set; }
        public string privacyPolicy { get; set; }

        public UpdateApplicationPrivacyPolicyData(string applicationId, string privacyPolicy)
        {
            this.applicationId = applicationId;
            this.privacyPolicy = privacyPolicy;
        }
    }

    public class UpdateApplicationSupportContactData
    {
        public string applicationId { get; set; }
        public string supportContact { get; set; }

        public UpdateApplicationSupportContactData(string applicationId, string supportContact)
        {
            this.applicationId = applicationId;
            this.supportContact = supportContact;
        }
    }

    public class UpdateApplicationAppLinkData
    {
        public string applicationId { get; set; }
        public string appLink { get; set; }

        public UpdateApplicationAppLinkData(string applicationId, string appLink)
        {
            this.applicationId = applicationId;
            this.appLink = appLink;
        }
    }

    public class UpdateApplicationEmailPreferencesData
    {
        public string applicationId { get; set; }
        public EmailPreferencesData emailPreferences { get; set; }

        public UpdateApplicationEmailPreferencesData(string applicationId, EmailPreferencesData emailPreferences)
        {
            this.applicationId = applicationId;
            this.emailPreferences = emailPreferences;
        }
    }

    public class UpdateApplicationLogoUrlData
    {
        public string applicationId { get; set; }
        public string logoUrl { get; set; }

        public UpdateApplicationLogoUrlData(string applicationId, string logoUrl)
        {
            this.applicationId = applicationId;
            this.logoUrl = logoUrl;
        }
    }

    public class DeleteApplicationData
    {
        public string applicationId { get; set; }

        public DeleteApplicationData(string applicationId)
        {
            this.applicationId = applicationId;
        }
    }

    public class GetLogoUploadUrlData
    {
        public string applicationId { get; set; }
        public string contentType { get; set; }

        public GetLogoUploadUrlData(string applicationId, string contentType)
        {
            this.applicationId = applicationId;
            this.contentType = contentType;
        }
    }

    public class AddAuthKeyData
    {
        public string applicationId { get; set; }
        public AuthKeyData key { get; set; }

        public AddAuthKeyData(string applicationId, AuthKeyData key)
        {
            this.applicationId = applicationId;
            this.key = key;
        }
    }

    public interface AuthKeyData
    {
        string kid { get; }
        string kty { get; }
        string use { get; }
        string? alg { get; }
    }

    public class RsaKeyData : AuthKeyData
    {
        public string kty { get; private set; } = "RSA";
        public string use { get; set; }
        public string kid { get; set; }
        public string? alg { get; set; } = null;
        public string p { get; set; }
        public string q { get; set; }
        public string d { get; set; }
        public string e { get; set; }
        public string qi { get; set; }
        public string dp { get; set; }
        public string dq { get; set; }
        public string n { get; set; }

        public RsaKeyData(string use, string kid, string p, string q, string d, string e, string qi, string dp, string dq, string n, string? alg = null)
        {
            this.use = use;
            this.kid = kid;
            this.p = p;
            this.q = q;
            this.d = d;
            this.e = e;
            this.qi = qi;
            this.dp = dp;
            this.dq = dq;
            this.n = n;
            this.alg = alg;
        }
    }

    public class EcKeyData : AuthKeyData
    {
        public string kty { get; private set; } = "EC";
        public string use { get; set; }
        public string kid { get; set; }
        public string? alg { get; set; } = null;
        public string d { get; set; }
        public string crv { get; set; }
        public string x { get; set; }
        public string y { get; set; }

        public EcKeyData(string use, string kid, string d, string crv, string x, string y, string? alg = null)
        {
            this.use = use;
            this.kid = kid;
            this.d = d;
            this.crv = crv;
            this.x = x;
            this.y = y;
            this.alg = alg;
        }
    }

    public class Ed25519KeyData : AuthKeyData
    {
        public string kty { get; private set; } = "OKP";
        public string use { get; set; }
        public string kid { get; set; }
        public string? alg { get; set; } = null;
        public string d { get; set; }
        public string crv { get; set; }
        public string x { get; set; }

        public Ed25519KeyData(string use, string kid, string d, string crv, string x, string? alg = null)
        {
            this.use = use;
            this.kid = kid;
            this.d = d;
            this.crv = crv;
            this.x = x;
            this.alg = alg;
        }
    }

    public class AddAuthIssuerData
    {
        public string applicationId { get; set; }
        public string url { get; set; }

        public AddAuthIssuerData(string applicationId, string url)
        {
            this.applicationId = applicationId;
            this.url = url;
        }
    }

    public class DeleteAuthIssuerData
    {
        public string applicationId { get; set; }
        public string url { get; set; }

        public DeleteAuthIssuerData(string applicationId, string url)
        {
            this.applicationId = applicationId;
            this.url = url;
        }
    }

    public class AddCorsDomainData
    {
        public string applicationId { get; set; }
        public string url { get; set; }

        public AddCorsDomainData(string applicationId, string url)
        {
            this.applicationId = applicationId;
            this.url = url;
        }
    }

    public class RemoveCorsDomainData
    {
        public string applicationId { get; set; }
        public string url { get; set; }

        public RemoveCorsDomainData(string applicationId, string url)
        {
            this.applicationId = applicationId;
            this.url = url;
        }
    }

    public class AddApplicationOwnerData
    {
        public string applicationId { get; set; }
        public string userId { get; set; }

        public AddApplicationOwnerData(string applicationId, string userId)
        {
            this.applicationId = applicationId;
            this.userId = userId;
        }
    }

    public class RemoveApplicationOwnerData
    {
        public string applicationId { get; set; }
        public string userId { get; set; }

        public RemoveApplicationOwnerData(string applicationId, string userId)
        {
            this.applicationId = applicationId;
            this.userId = userId;
        }
    }

    public class GetApplicationOwnersDetailsData
    {
        public string applicationId { get; set; }

        public GetApplicationOwnersDetailsData(string applicationId)
        {
            this.applicationId = applicationId;
        }
    }
}
