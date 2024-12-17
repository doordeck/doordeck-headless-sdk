namespace Doordeck.Headless.Sdk.model.responses
{
    public class LockResponse
    {
        public string id { get; set; }
        public string name { get; set; }
        public string? colour { get; set; } = null;
        public string? start { get; set; } = null;
        public string? end { get; set; } = null;
        public UserRole role { get; set; }
        public LockSettingsResponse settings { get; set; }
        public LockStateResponse state { get; set; }
        public bool favourite { get; set; }
        public double? unlockTime { get; set; } = null;
    }

    public class LockSettingsResponse
    {
        public double unlockTime { get; set; }
        public List<string> permittedAddresses { get; set; }
        public string defaultName { get; set; }
        public UsageRequirementsResponse? usageRequirements { get; set; } = null;
        public UnlockBetweenSettingResponse? unlockBetweenWindow { get; set; } = null;
        public List<string> tiles { get; set; }
        public bool hidden { get; set; }
        public List<string> directAccessEndpoints { get; set; }
        public Dictionary<CapabilityType, CapabilityStatus> capabilities { get; set; }
    }

    public class UsageRequirementsResponse
    {
        public List<TimeRequirementResponse>? time { get; set; } = null;
        public LocationRequirementResponse? location { get; set; } = null;
    }

    public class TimeRequirementResponse
    {
        public string start { get; set; }
        public string end { get; set; }
        public string timezone { get; set; }
        public List<string> days { get; set; }
    }

    public class LocationRequirementResponse
    {
        public double latitude { get; set; }
        public double longitude { get; set; }
        public bool? enabled { get; set; } = null;
        public int? radius { get; set; } = null;
        public int? accuracy { get; set; } = null;
    }

    public class UnlockBetweenSettingResponse
    {
        public string start { get; set; }
        public string end { get; set; }
        public string timezone { get; set; }
        public List<string> days { get; set; }
        public List<string>? exceptions { get; set; } = null;
    }

    public class LockStateResponse
    {
        public bool locked { get; set; }
        public bool connected { get; set; }
    }

    public class UserPublicKeyResponse
    {
        public string id { get; set; }
        public string publicKey { get; set; }
    }

    public class BatchUserPublicKeyResponse
    {
        public string id { get; set; }
        public string? email { get; set; } = null;
        public string? foreignKey { get; set; } = null;
        public string? phone { get; set; } = null;
        public string publicKey { get; set; }
    }

    public class ShareableLockResponse
    {
        public string id { get; set; }
        public string name { get; set; }
    }

    public class UserLockResponse
    {
        public string userId { get; set; }
        public string email { get; set; }
        public string publicKey { get; set; }
        public string? displayName { get; set; } = null;
        public bool orphan { get; set; }
        public bool foreign { get; set; }
        public UserRole role { get; set; }
        public double? start { get; set; } = null;
        public double? end { get; set; } = null;
    }

    public class LockUserResponse
    {
        public string userId { get; set; }
        public string email { get; set; }
        public string publicKey { get; set; }
        public string? displayName { get; set; } = null;
        public bool orphan { get; set; }
        public bool foreign { get; set; }
        public double? start { get; set; } = null;
        public double? end { get; set; } = null;
        public List<LockUserDetailsResponse> devices { get; set; }
    }

    public class LockUserDetailsResponse
    {
        public string deviceId { get; set; }
        public UserRole role { get; set; }
        public double? start { get; set; } = null;
        public double? end { get; set; } = null;
    }

    public class UserAuditResponse
    {
        public string deviceId { get; set; }
        public double timestamp { get; set; }
        public AuditEvent type { get; set; }
        public UserAuditIssuerResponse issuer { get; set; }
        public UserAuditSubjectResponse? subject { get; set; } = null;
        public bool rejected { get; set; }
    }

    public class UserAuditIssuerResponse
    {
        public string userId { get; set; }
    }

    public class UserAuditSubjectResponse
    {
        public string userId { get; set; }
        public string email { get; set; }
    }

    public class LockAuditTrailResponse
    {
        public double timestamp { get; set; }
        public AuditEvent type { get; set; }
        public string? user { get; set; } = null;
        public string? email { get; set; } = null;
        public string? displayName { get; set; } = null;
        public string? message { get; set; } = null;
    }
}
