using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;

using SitesApi = Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_SitesApi;

public class Sites(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_SitesApi sites) : AbstractWrapper
{
    public unsafe Task<List<SiteResponse>> ListSites() =>
        Process<SitesApi, List<SiteResponse>>(sites, &Methods.listSites);

    public unsafe Task<List<LockResponse>> GetLocksForSite(Guid siteId) =>
        Process<SitesApi, List<LockResponse>>(sites, &Methods.getLocksForSite, new { siteId });

    public unsafe Task<List<UserForSiteResponse>> GetUsersForSite(Guid siteId) =>
        Process<SitesApi, List<UserForSiteResponse>>(sites, &Methods.getUsersForSite, new { siteId });
}