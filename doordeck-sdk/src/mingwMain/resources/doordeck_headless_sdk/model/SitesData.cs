namespace DoordeckHeadlessSDK.model
{
    public class GetLocksForSiteData
    {
        public string siteId { get; set; }
        public GetLocksForSiteData(string siteId)
        {
            this.siteId = siteId;
        }
    }

    public class GetUsersForSiteData
    {
        public string siteId { get; set; }
        public GetUsersForSiteData(string siteId)
        {
            this.siteId = siteId;
        }
    }
}
