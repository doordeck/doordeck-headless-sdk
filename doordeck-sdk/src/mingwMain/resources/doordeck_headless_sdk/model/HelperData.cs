namespace DoordeckHeadlessSDK.model
{
    public class UploadPlatformLogoData
    {
        public string applicationId { get; set; }
        public string contentType { get; set; }
        public string image { get; set; }

        public UploadPlatformLogoData(string applicationId, string contentType, string image)
        {
            this.applicationId = applicationId;
            this.contentType = contentType;
            this.image = image;
        }
    }
}
