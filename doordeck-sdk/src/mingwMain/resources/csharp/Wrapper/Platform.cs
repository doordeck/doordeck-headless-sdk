using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;

public class Platform(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_PlatformApi platform,
    Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._PlatformApi_e__Struct platformApi) : AbstractWrapper
{
    public unsafe Task<object> CreateApplication(CreateApplication data) =>
        Process<object>(platformApi.createApplication_, null, data);

    public unsafe Task<List<ApplicationResponse>> ListApplications() =>
        Process<List<ApplicationResponse>>(null, platformApi.listApplications_, null);

    public unsafe Task<ApplicationResponse> GetApplication(string applicationId) =>
        Process<ApplicationResponse>(platformApi.getApplication_, null, new { applicationId });

    public unsafe Task<object> UpdateApplicationName(string applicationId, string name) =>
        Process<object>(platformApi.updateApplicationName_, null, new { applicationId, name });
    
    public unsafe Task<object> UpdateApplicationCompanyName(string applicationId, string companyName) =>
        Process<object>(platformApi.updateApplicationCompanyName_, null, new { applicationId, companyName });

    public unsafe Task<object> UpdateApplicationMailingAddress(string applicationId, string mailingAddress) =>
        Process<object>(platformApi.updateApplicationMailingAddress_, null, new { applicationId, mailingAddress });

    public unsafe Task<object> UpdateApplicationPrivacyPolicy(string applicationId, string privacyPolicy) =>
        Process<object>(platformApi.updateApplicationPrivacyPolicy_, null, new { applicationId, privacyPolicy });

    public unsafe Task<object> UpdateApplicationSupportContact(string applicationId, string supportContact) =>
        Process<object>(platformApi.updateApplicationSupportContact_, null, new { applicationId, supportContact });

    public unsafe Task<object> UpdateApplicationAppLink(string applicationId, string appLink) =>
        Process<object>(platformApi.updateApplicationAppLink_, null, new { applicationId, appLink });

    public unsafe Task<object> UpdateApplicationEmailPreferences(string applicationId, EmailPreferences emailPreferences) =>
        Process<object>(platformApi.updateApplicationEmailPreferences_, null, new { applicationId, emailPreferences });

    public unsafe Task<object> UpdateApplicationLogoUrl(string applicationId, string logoUrl) =>
        Process<object>(platformApi.updateApplicationLogoUrl_, null, new { applicationId, logoUrl });

    public unsafe Task<object> DeleteApplication(string applicationId) =>
        Process<object>(platformApi.deleteApplication_, null, new { applicationId });

    public unsafe Task<GetLogoUploadUrlResponse> GetLogoUploadUrl(string applicationId, string contentType) =>
        Process<GetLogoUploadUrlResponse>(platformApi.getLogoUploadUrl_, null, new { applicationId, contentType });

    public unsafe Task<object> AddAuthKey(string applicationId, AuthKey key) =>
        Process<object>(platformApi.addAuthKey_, null, new { applicationId, key });

    public unsafe Task<object> AddAuthIssuer(string applicationId, string url) =>
        Process<object>(platformApi.addAuthIssuer_, null, new { applicationId, url });

    public unsafe Task<object> DeleteAuthIssuer(string applicationId, string url) =>
        Process<object>(platformApi.deleteAuthIssuer_, null, new { applicationId, url });

    public unsafe Task<object> AddCorsDomain(string applicationId, string url) =>
        Process<object>(platformApi.addCorsDomain_, null, new { applicationId, url });

    public unsafe Task<object> RemoveCorsDomain(string applicationId, string url) =>
        Process<object>(platformApi.removeCorsDomain_, null, new { applicationId, url });

    public unsafe Task<object> AddApplicationOwner(string applicationId, string userId) =>
        Process<object>(platformApi.addApplicationOwner_, null, new { applicationId, userId });

    public unsafe Task<object> RemoveApplicationOwner(string applicationId, string userId) =>
        Process<object>(platformApi.removeApplicationOwner_, null, new { applicationId, userId });

    public unsafe Task<List<ApplicationOwnerDetailsResponse>> GetApplicationOwnersDetails(string applicationId, string userId) =>
        Process<List<ApplicationOwnerDetailsResponse>>(platformApi.getApplicationOwnersDetails_, null, new { applicationId, userId });

    private unsafe Task<TResponse> Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_PlatformApi,
            sbyte*, void*, void> processWithData,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_PlatformApi,
            void*, void> processWithoutData,
        object? data) => 
        ProcessCommon<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_PlatformApi, TResponse>(
            platform,
            data,
            processWithData,
            processWithoutData);
}