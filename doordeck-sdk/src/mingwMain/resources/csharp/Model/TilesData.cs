namespace Doordeck.Headless.Sdk.Model
{
    public class GetLocksBelongingToTileData
    {
        public string TileId {  get; set; }

        public GetLocksBelongingToTileData(string tileId)
        {
            TileId = tileId;
        }
    }

    public class AssociateMultipleLocksData
    {
        public string TileId { get; set; }
        public string SiteId { get; set; }
        public List<string> LockIds { get; set; }

        public AssociateMultipleLocksData(string tileId, string siteId, List<string> lockIds)
        {
            TileId = tileId;
            SiteId = siteId;
            LockIds = lockIds;
        }
    }
}
