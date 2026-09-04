using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;

using PlatformApi = Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_PlatformApi;

public class Platform(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_PlatformApi platform) : AbstractWrapper
{
    public unsafe Task<Guid> CreateApplication(CreateApplication data) =>
        Process<PlatformApi, Guid>(platform, &Methods.createApplication, data);

    public unsafe Task<List<ApplicationResponse>> ListApplications() =>
        Process<PlatformApi, List<ApplicationResponse>>(platform, &Methods.listApplications);

    public unsafe Task<ApplicationResponse> GetApplication(Guid applicationId) =>
        Process<PlatformApi, ApplicationResponse>(platform, &Methods.getApplication, new { applicationId });

    public unsafe Task<object> UpdateApplicationName(Guid applicationId, string name) =>
        Process<PlatformApi, object>(platform, &Methods.updateApplicationName, new { applicationId, name });

    public unsafe Task<object> UpdateApplicationCompanyName(Guid applicationId, string companyName) =>
        Process<PlatformApi, object>(platform, &Methods.updateApplicationCompanyName, new { applicationId, companyName });

    public unsafe Task<object> UpdateApplicationMailingAddress(Guid applicationId, string mailingAddress) =>
        Process<PlatformApi, object>(platform, &Methods.updateApplicationMailingAddress, new { applicationId, mailingAddress });

    public unsafe Task<object> UpdateApplicationPrivacyPolicy(Guid applicationId, Uri privacyPolicy) =>
        Process<PlatformApi, object>(platform, &Methods.updateApplicationPrivacyPolicy, new { applicationId, privacyPolicy });

    public unsafe Task<object> UpdateApplicationSupportContact(Guid applicationId, Uri supportContact) =>
        Process<PlatformApi, object>(platform, &Methods.updateApplicationSupportContact, new { applicationId, supportContact });

    public unsafe Task<object> UpdateApplicationAppLink(Guid applicationId, Uri appLink) =>
        Process<PlatformApi, object>(platform, &Methods.updateApplicationAppLink, new { applicationId, appLink });

    public unsafe Task<object> UpdateApplicationEmailPreferences(Guid applicationId, EmailPreferences emailPreferences) =>
        Process<PlatformApi, object>(platform, &Methods.updateApplicationEmailPreferences, new { applicationId, emailPreferences });

    public unsafe Task<object> UpdateApplicationLogoUrl(Guid applicationId, Uri logoUrl) =>
        Process<PlatformApi, object>(platform, &Methods.updateApplicationLogoUrl, new { applicationId, logoUrl });

    public unsafe Task<object> DeleteApplication(Guid applicationId) =>
        Process<PlatformApi, object>(platform, &Methods.deleteApplication, new { applicationId });

    public unsafe Task<GetLogoUploadUrlResponse> GetLogoUploadUrl(Guid applicationId, string contentType) =>
        Process<PlatformApi, GetLogoUploadUrlResponse>(platform, &Methods.getLogoUploadUrl, new { applicationId, contentType });

    public unsafe Task<object> AddAuthKey(Guid applicationId, IAuthKey key) =>
        Process<PlatformApi, object>(platform, &Methods.addAuthKey, new { applicationId, key });

    public unsafe Task<object> AddAuthIssuer(Guid applicationId, Uri url) =>
        Process<PlatformApi, object>(platform, &Methods.addAuthIssuer, new { applicationId, url });

    public unsafe Task<object> DeleteAuthIssuer(Guid applicationId, Uri url) =>
        Process<PlatformApi, object>(platform, &Methods.deleteAuthIssuer, new { applicationId, url });

    public unsafe Task<object> AddCorsDomain(Guid applicationId, Uri url) =>
        Process<PlatformApi, object>(platform, &Methods.addCorsDomain, new { applicationId, url });

    public unsafe Task<object> RemoveCorsDomain(Guid applicationId, Uri url) =>
        Process<PlatformApi, object>(platform, &Methods.removeCorsDomain, new { applicationId, url });

    public unsafe Task<object> AddApplicationOwner(Guid applicationId, Guid userId) =>
        Process<PlatformApi, object>(platform, &Methods.addApplicationOwner, new { applicationId, userId });

    public unsafe Task<object> RemoveApplicationOwner(Guid applicationId, Guid userId) =>
        Process<PlatformApi, object>(platform, &Methods.removeApplicationOwner, new { applicationId, userId });

    public unsafe Task<List<ApplicationOwnerDetailsResponse>> GetApplicationOwnersDetails(Guid applicationId, Guid userId) =>
        Process<PlatformApi, List<ApplicationOwnerDetailsResponse>>(platform, &Methods.getApplicationOwnersDetails, new { applicationId, userId });

    public unsafe Task<List<ApplicationUserResponse>> GetApplicationUsers(Guid applicationId, int pageSize = 100, Guid? lastUserRetrieved = null) =>
        Process<PlatformApi, List<ApplicationUserResponse>>(platform, &Methods.getApplicationUsers, new { applicationId, pageSize, lastUserRetrieved });
}