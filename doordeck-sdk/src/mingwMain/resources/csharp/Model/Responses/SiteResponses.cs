namespace Doordeck.Headless.Sdk.Model.Responses;

public class SiteResponse
{
    public string Id { get; set; } = string.Empty;
    public string Name { get; set; } = string.Empty;
    public string Colour { get; set; } = string.Empty;
    public double Longitude { get; set; }
    public double Latitude { get; set; }
    public int Radius { get; set; }
    public string Created { get; set; } = string.Empty;
    public string Updated { get; set; } = string.Empty;
}

public class UserForSiteResponse
{
    public string UserId { get; set; } = string.Empty;
    public string Email { get; set; } = string.Empty;
    public string? DisplayName { get; set; } = null;
    public bool Orphan { get; set; }
}