namespace DoordeckHeadlessSDK.model.responses
{
    public class TileLocksResponse
    {
        public string siteId { get; set; }
        public string tileId { get; set; }
        public List<string> deviceIds { get; set; }
    }
}
