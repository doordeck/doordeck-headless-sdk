namespace Doordeck.Headless.Sdk.Model
{
    public class UploadPlatformLogoData
    {
        public string ApplicationId { get; set; }
        public string ContentType { get; set; }
        public string Image { get; set; }

        public UploadPlatformLogoData(string applicationId, string contentType, string image)
        {
            ApplicationId = applicationId;
            ContentType = contentType;
            Image = image;
        }
    }
}
