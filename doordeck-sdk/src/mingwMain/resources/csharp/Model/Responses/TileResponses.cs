namespace Doordeck.Headless.Sdk.Model.Responses;

public class TileLocksResponse
{
    public required Guid SiteId { get; set; }
    public required Guid TileId { get; set; }
    public required List<Guid> DeviceIds { get; set; }
}