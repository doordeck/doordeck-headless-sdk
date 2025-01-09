namespace Doordeck.Headless.Sdk.Model
{
    public class FusionLoginData
    {
        public string Email { get; set; }
        public string Password { get; set; }

        public FusionLoginData(string email, string password)
        {
            Email = email;
            Password = password;
        }
    }
    
    public class GetIntegrationConfigurationData
    {
        public string Type { get; set; }

        public GetIntegrationConfigurationData(string type)
        {
            Type = type;
        }
    }
    
    public class EnableDoorData
    {
        public string Name { get; set; }
        public string SiteId { get; set; }
        public ILockController Controller { get; set; }

        public EnableDoorData(string name, string siteId, ILockController controller)
        {
            Name = name;
            SiteId = siteId;
            Controller = controller;
        }
    }
    
    public class DeleteDoorData
    {
        public string DeviceId { get; set; }

        public DeleteDoorData(string deviceId)
        {
            DeviceId = deviceId;
        }
    }
    
    public class GetDoorStatusData
    {
        public string DeviceId { get; set; }

        public GetDoorStatusData(string deviceId)
        {
            DeviceId = deviceId;
        }
    }
    
    public class StartDoorData
    {
        public string DeviceId { get; set; }

        public StartDoorData(string deviceId)
        {
            DeviceId = deviceId;
        }
    }
    
    public class StopDoorData
    {
        public string DeviceId { get; set; }

        public StopDoorData(string deviceId)
        {
            DeviceId = deviceId;
        }
    }
}