using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;

public class Platform(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_PlatformApi platform,
    Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._PlatformApi_e__Struct platformApi) : AbstractWrapper
{
    public unsafe Task<object> CreateApplication(CreateApplicationData data) =>
        Process<object>(platformApi.createApplication_, null, data);

    public unsafe Task<List<ApplicationResponse>> ListApplications() =>
        Process<List<ApplicationResponse>>(null, platformApi.listApplications_, null);

    public unsafe Task<ApplicationResponse> GetApplication(ApplicationIdData data) =>
        Process<ApplicationResponse>(platformApi.getApplication_, null, data);

    public unsafe Task<object> UpdateApplicationName(UpdateApplicationNameData data) =>
        Process<object>(platformApi.updateApplicationName_, null, data);
    
    public unsafe Task<object> UpdateApplicationCompanyName(UpdateApplicationCompanyNameData data) =>
        Process<object>(platformApi.updateApplicationCompanyName_, null, data);

    public unsafe Task<object> UpdateApplicationMailingAddress(UpdateApplicationMailingAddressData data) =>
        Process<object>(platformApi.updateApplicationMailingAddress_, null, data);

    public unsafe Task<object> UpdateApplicationPrivacyPolicy(UpdateApplicationPrivacyPolicyData data) =>
        Process<object>(platformApi.updateApplicationPrivacyPolicy_, null, data);

    public unsafe Task<object> UpdateApplicationSupportContact(UpdateApplicationSupportContactData data) =>
        Process<object>(platformApi.updateApplicationSupportContact_, null, data);

    public unsafe Task<object> UpdateApplicationAppLink(UpdateApplicationAppLinkData data) =>
        Process<object>(platformApi.updateApplicationAppLink_, null, data);

    public unsafe Task<object> UpdateApplicationEmailPreferences(UpdateApplicationEmailPreferencesData data) =>
        Process<object>(platformApi.updateApplicationEmailPreferences_, null, data);

    public unsafe Task<object> UpdateApplicationLogoUrl(UpdateApplicationLogoUrlData data) =>
        Process<object>(platformApi.updateApplicationLogoUrl_, null, data);

    public unsafe Task<object> DeleteApplication(ApplicationIdData data) =>
        Process<object>(platformApi.deleteApplication_, null, data);

    public unsafe Task<GetLogoUploadUrlResponse> GetLogoUploadUrl(GetLogoUploadUrlData data) =>
        Process<GetLogoUploadUrlResponse>(platformApi.getLogoUploadUrl_, null, data);

    public unsafe Task<object> AddAuthKey(AddAuthKeyData data) =>
        Process<object>(platformApi.addAuthKey_, null, data);

    public unsafe Task<object> AddAuthIssuer(AuthIssuerData data) =>
        Process<object>(platformApi.addAuthIssuer_, null, data);

    public unsafe Task<object> DeleteAuthIssuer(AuthIssuerData data) =>
        Process<object>(platformApi.deleteAuthIssuer_, null, data);

    public unsafe Task<object> AddCorsDomain(CorsDomainData data) =>
        Process<object>(platformApi.addCorsDomain_, null, data);

    public unsafe Task<object> RemoveCorsDomain(CorsDomainData data) =>
        Process<object>(platformApi.removeCorsDomain_, null, data);

    public unsafe Task<object> AddApplicationOwner(ApplicationOwnerData data) =>
        Process<object>(platformApi.addApplicationOwner_, null, data);

    public unsafe Task<object> RemoveApplicationOwner(ApplicationOwnerData data) =>
        Process<object>(platformApi.removeApplicationOwner_, null, data);

    public unsafe Task<List<ApplicationOwnerDetailsResponse>> GetApplicationOwnersDetails(ApplicationOwnerData data) =>
        Process<List<ApplicationOwnerDetailsResponse>>(platformApi.getApplicationOwnersDetails_, null, data);

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