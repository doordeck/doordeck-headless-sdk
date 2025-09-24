using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;

using PlatformApi = Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_PlatformApi;

public class Platform(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_PlatformApi platform,
    Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._PlatformApi_e__Struct platformApi) : AbstractWrapper
{
    public unsafe Task<Guid> CreateApplication(CreateApplication data) =>
        Process<PlatformApi, Guid>(platform, platformApi.createApplication_, data);

    public unsafe Task<List<ApplicationResponse>> ListApplications() =>
        Process<PlatformApi, List<ApplicationResponse>>(platform, platformApi.listApplications_);

    public unsafe Task<ApplicationResponse> GetApplication(Guid applicationId) =>
        Process<PlatformApi, ApplicationResponse>(platform, platformApi.getApplication_, new { applicationId });

    public unsafe Task<object> UpdateApplicationName(Guid applicationId, string name) =>
        Process<PlatformApi, object>(platform, platformApi.updateApplicationName_, new { applicationId, name });

    public unsafe Task<object> UpdateApplicationCompanyName(Guid applicationId, string companyName) =>
        Process<PlatformApi, object>(platform, platformApi.updateApplicationCompanyName_, new { applicationId, companyName });

    public unsafe Task<object> UpdateApplicationMailingAddress(Guid applicationId, string mailingAddress) =>
        Process<PlatformApi, object>(platform, platformApi.updateApplicationMailingAddress_, new { applicationId, mailingAddress });

    public unsafe Task<object> UpdateApplicationPrivacyPolicy(Guid applicationId, Uri privacyPolicy) =>
        Process<PlatformApi, object>(platform, platformApi.updateApplicationPrivacyPolicy_, new { applicationId, privacyPolicy });

    public unsafe Task<object> UpdateApplicationSupportContact(Guid applicationId, Uri supportContact) =>
        Process<PlatformApi, object>(platform, platformApi.updateApplicationSupportContact_, new { applicationId, supportContact });

    public unsafe Task<object> UpdateApplicationAppLink(Guid applicationId, Uri appLink) =>
        Process<PlatformApi, object>(platform, platformApi.updateApplicationAppLink_, new { applicationId, appLink });

    public unsafe Task<object> UpdateApplicationEmailPreferences(Guid applicationId, EmailPreferences emailPreferences) =>
        Process<PlatformApi, object>(platform, platformApi.updateApplicationEmailPreferences_, new { applicationId, emailPreferences });

    public unsafe Task<object> UpdateApplicationLogoUrl(Guid applicationId, Uri logoUrl) =>
        Process<PlatformApi, object>(platform, platformApi.updateApplicationLogoUrl_, new { applicationId, logoUrl });

    public unsafe Task<object> DeleteApplication(Guid applicationId) =>
        Process<PlatformApi, object>(platform, platformApi.deleteApplication_, new { applicationId });

    public unsafe Task<GetLogoUploadUrlResponse> GetLogoUploadUrl(Guid applicationId, string contentType) =>
        Process<PlatformApi, GetLogoUploadUrlResponse>(platform, platformApi.getLogoUploadUrl_, new { applicationId, contentType });

    public unsafe Task<object> AddAuthKey(Guid applicationId, IAuthKey key) =>
        Process<PlatformApi, object>(platform, platformApi.addAuthKey_, new { applicationId, key });

    public unsafe Task<object> AddAuthIssuer(Guid applicationId, Uri url) =>
        Process<PlatformApi, object>(platform, platformApi.addAuthIssuer_, new { applicationId, url });

    public unsafe Task<object> DeleteAuthIssuer(Guid applicationId, Uri url) =>
        Process<PlatformApi, object>(platform, platformApi.deleteAuthIssuer_, new { applicationId, url });

    public unsafe Task<object> AddCorsDomain(Guid applicationId, Uri url) =>
        Process<PlatformApi, object>(platform, platformApi.addCorsDomain_, new { applicationId, url });

    public unsafe Task<object> RemoveCorsDomain(Guid applicationId, Uri url) =>
        Process<PlatformApi, object>(platform, platformApi.removeCorsDomain_, new { applicationId, url });

    public unsafe Task<object> AddApplicationOwner(Guid applicationId, Guid userId) =>
        Process<PlatformApi, object>(platform, platformApi.addApplicationOwner_, new { applicationId, userId });

    public unsafe Task<object> RemoveApplicationOwner(Guid applicationId, Guid userId) =>
        Process<PlatformApi, object>(platform, platformApi.removeApplicationOwner_, new { applicationId, userId });

    public unsafe Task<List<ApplicationOwnerDetailsResponse>> GetApplicationOwnersDetails(Guid applicationId, Guid userId) =>
        Process<PlatformApi, List<ApplicationOwnerDetailsResponse>>(platform, platformApi.getApplicationOwnersDetails_, new { applicationId, userId });
}