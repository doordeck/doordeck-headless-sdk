using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;

public class Sites(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_SitesApi sites,
    Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._SitesApi_e__Struct sitesApi) : AbstractWrapper
{
    public unsafe Task<List<SiteResponse>> ListSites() =>
        Process<List<SiteResponse>>(null, sitesApi.listSites_, null);

    public unsafe Task<List<SiteLocksResponse>> GetLocksForSite(string siteId) =>
        Process<List<SiteLocksResponse>>(sitesApi.getLocksForSite_, null, new { siteId });

    public unsafe Task<List<UserForSiteResponse>> GetUsersForSite(string siteId) =>
        Process<List<UserForSiteResponse>>(sitesApi.getUsersForSite_, null, new { siteId });

    private unsafe Task<TResponse> Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_SitesApi,
            sbyte*, void*, void> processWithData,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_SitesApi,
            void*, void> processWithoutData,
        object? data) =>
        ProcessCommon<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_SitesApi, TResponse>(
            sites,
            data,
            processWithData,
            processWithoutData);
}