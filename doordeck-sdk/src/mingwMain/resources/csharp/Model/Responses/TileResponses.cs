namespace Doordeck.Headless.Sdk.Model.Responses;

public class TileLocksResponse
{
    public string SiteId { get; set; } = string.Empty;
    public string TileId { get; set; } = string.Empty;
    public List<string> DeviceIds { get; set; } = [];
}