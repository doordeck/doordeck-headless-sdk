namespace DoordeckHeadlessSDK.model.responses
{
    public class SiteResponse
    {
        public string id { get; set; }
        public string name { get; set; }
        public string colour { get; set; }
        public double longitude { get; set; }
        public double latitude { get; set; }
        public int radius { get; set; }
        public string passBackground { get; set; }
        public string created { get; set; }
        public string updated { get; set; }
    }

    public class SiteLocksResponse
    {
        public string id { get; set; }
        public string name { get; set; }
        public string? colour { get; set; } = null;
        public UserRole role { get; set; }
        public SiteLockSettingsResponse settings { get; set; }
    }

    public class SiteLockSettingsResponse
    {
        public double unlockTime { get; set; }
        public List<string> permittedAddresses { get; set; }
        public string defaultName { get; set; }
        public List<string> tiles { get; set; }
        public SiteStateResponse? state { get; set; } = null;
        public bool? favourite { get; set; } = null;
    }

    public class SiteStateResponse
    {
        public bool connected { get; set; }
    }

    public class UserForSiteResponse
    {
        public string userId { get; set; }
        public string email { get; set; }
        public string? displayName { get; set; } = null;
        public bool orphan { get; set; }
    }
}
