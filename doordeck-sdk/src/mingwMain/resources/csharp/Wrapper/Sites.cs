using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;

using SitesApi = Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_SitesApi;

public class Sites(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_SitesApi sites,
    Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._SitesApi_e__Struct sitesApi) : AbstractWrapper
{
    public unsafe Task<List<SiteResponse>> ListSites() =>
        Process<SitesApi, List<SiteResponse>>(sites, sitesApi.listSites_);

    public unsafe Task<List<LockResponse>> GetLocksForSite(string siteId) =>
        Process<SitesApi, List<LockResponse>>(sites, sitesApi.getLocksForSite_, new { siteId });

    public unsafe Task<List<UserForSiteResponse>> GetUsersForSite(string siteId) =>
        Process<SitesApi, List<UserForSiteResponse>>(sites, sitesApi.getUsersForSite_, new { siteId });
}