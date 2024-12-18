namespace Doordeck.Headless.Sdk.Model
{
    public class GetLocksForSiteData
    {
        public string SiteId { get; set; }

        public GetLocksForSiteData(string siteId)
        {
            SiteId = siteId;
        }
    }

    public class GetUsersForSiteData
    {
        public string SiteId { get; set; }

        public GetUsersForSiteData(string siteId)
        {
            SiteId = siteId;
        }
    }
}
