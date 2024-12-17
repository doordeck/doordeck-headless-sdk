namespace Doordeck.Headless.Sdk.Model
{
    public class GetLocksBelongingToTileData
    {
        public string tileId {  get; set; }

        public GetLocksBelongingToTileData(string tileId)
        {
            this.tileId = tileId;
        }
    }

    public class AssociateMultipleLocksData
    {
        public string tileId { get; set; }
        public string siteId { get; set; }
        public List<string> lockIds { get; set; }

        public AssociateMultipleLocksData(string tileId, string siteId, List<string> lockIds)
        {
            this.tileId = tileId;
            this.siteId = siteId;
            this.lockIds = lockIds;
        }
    }
}
