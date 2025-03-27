namespace Doordeck.Headless.Sdk.Model;

public class GetLocksBelongingToTileData(string tileId)
{
    public string TileId {  get; set; } = tileId;
}

public class AssociateMultipleLocksData(string tileId, string siteId, List<string> lockIds)
{
    public string TileId { get; set; } = tileId;
    public string SiteId { get; set; } = siteId;
    public List<string> LockIds { get; set; } = lockIds;
}