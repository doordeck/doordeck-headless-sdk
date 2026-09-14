using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;


public class Sites
{
    public Task<List<SiteResponse>> ListSites() =>
        Dispatcher.Call<List<SiteResponse>>("sites.listSites");

    public Task<List<LockResponse>> GetLocksForSite(Guid siteId) =>
        Dispatcher.Call<List<LockResponse>>("sites.getLocksForSite", new { siteId });

    public Task<List<UserForSiteResponse>> GetUsersForSite(Guid siteId) =>
        Dispatcher.Call<List<UserForSiteResponse>>("sites.getUsersForSite", new { siteId });
}