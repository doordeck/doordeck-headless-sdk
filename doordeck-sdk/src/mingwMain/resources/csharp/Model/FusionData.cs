namespace Doordeck.Headless.Sdk.Model;

public class FusionLoginData(string email, string password)
{
    public string Email { get; set; } = email;
    public string Password { get; set; } = password;
}
    
public class GetIntegrationConfigurationData(string type)
{
    public string Type { get; set; } = type;
}
    
public class EnableDoorData(string name, string siteId, LockController controller)
{
    public string Name { get; set; } = name;
    public string SiteId { get; set; } = siteId;
    public LockController Controller { get; set; } = controller;
}

public class DeviceIdData(string deviceId)
{
    public string DeviceId { get; set; } = deviceId;
}