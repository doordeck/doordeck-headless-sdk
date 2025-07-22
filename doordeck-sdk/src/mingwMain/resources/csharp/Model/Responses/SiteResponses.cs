namespace Doordeck.Headless.Sdk.Model.Responses;

public class SiteResponse
{
    public string Id { get; set; } = string.Empty;
    public string Name { get; set; } = string.Empty;
    public string Colour { get; set; } = string.Empty;
    public double Longitude { get; set; }
    public double Latitude { get; set; }
    public int Radius { get; set; }
    public string PassBackground { get; set; } = string.Empty;
    public string Created { get; set; } = string.Empty;
    public string Updated { get; set; } = string.Empty;
}

public class SiteLocksResponse
{
    public string Id { get; set; } = string.Empty;
    public string Name { get; set; } = string.Empty;
    public string? Colour { get; set; } = null;
    public UserRole Role { get; set; }
    public SiteLockSettingsResponse Settings { get; set; } = new SiteLockSettingsResponse();
    public SiteStateResponse State { get; set; } = new SiteStateResponse();
    public bool Favourite { get; set; }
}

public class SiteLockSettingsResponse
{
    public double UnlockTime { get; set; }
    public List<string> PermittedAddresses { get; set; } = [];
    public string DefaultName { get; set; } = string.Empty;
    public List<string> Tiles { get; set; } = [];
    public bool Hidden { get; set; }
}

public class SiteStateResponse
{
    public bool Connected { get; set; }
}

public class UserForSiteResponse
{
    public string UserId { get; set; } = string.Empty;
    public string Email { get; set; } = string.Empty;
    public string? DisplayName { get; set; } = null;
    public bool Orphan { get; set; }
}