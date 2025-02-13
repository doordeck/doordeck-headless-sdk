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

    public class AssistedLoginData
    {
        public string Email { get; set; }
        public string Password { get; set; }

        public AssistedLoginData(string email, string password)
        {
            Email = email;
            Password = password;
        }
    }

    public class AssistedRegisterEphemeralKeyData
    {
        public string PublicKey { get; set; }

        public AssistedRegisterEphemeralKeyData(string publicKey)
        {
            PublicKey = publicKey;
        }
    }

    public class AssistedRegisterData
    {
        public string Email { get; set; }
        public string Password { get; set; }
        public string? DisplayName { get; set; }
        public bool Force { get; set; }

        public AssistedRegisterData(string email, string password, string? displayName = null, bool force = false)
        {
            Email = email;
            Password = password;
            DisplayName = displayName;
            Force = force;
        }
    }
}
