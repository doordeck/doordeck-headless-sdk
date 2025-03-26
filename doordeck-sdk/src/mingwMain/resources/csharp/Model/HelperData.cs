namespace Doordeck.Headless.Sdk.Model;

public class UploadPlatformLogoData(string applicationId, string contentType, string image)
{
    public string ApplicationId { get; set; } = applicationId;
    public string ContentType { get; set; } = contentType;
    public string Image { get; set; } = image;
}

public class AssistedLoginData(string email, string password)
{
    public string Email { get; set; } = email;
    public string Password { get; set; } = password;
}

public class AssistedRegisterEphemeralKeyData(string publicKey)
{
    public string PublicKey { get; set; } = publicKey;
}

public class AssistedRegisterData(string email, string password, string? displayName = null, bool force = false)
{
    public string Email { get; set; } = email;
    public string Password { get; set; } = password;
    public string? DisplayName { get; set; } = displayName;
    public bool Force { get; set; } = force;
}