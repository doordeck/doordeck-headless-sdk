namespace Doordeck.Headless.Sdk.Model.Responses;

public class SiteResponse
{
    public required Guid Id { get; set; }
    public required string Name { get; set; }
    public required string Colour { get; set; }
    public required double Longitude { get; set; }
    public required double Latitude { get; set; }
    public required int Radius { get; set; }
    public required DateTime Created { get; set; }
    public required DateTime Updated { get; set; }
}

public class UserForSiteResponse
{
    public required Guid UserId { get; set; }
    public required string Email { get; set; }
    public string? DisplayName { get; set; }
    public required bool Orphan { get; set; }
}