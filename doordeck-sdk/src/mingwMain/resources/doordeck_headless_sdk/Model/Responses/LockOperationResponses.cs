namespace Doordeck.Headless.Sdk.Model.Responses
{
    public class LockResponse
    {
        public string Id { get; set; } = string.Empty;
        public string Name { get; set; } = string.Empty;
        public string? Colour { get; set; } = null;
        public string? Start { get; set; } = null;
        public string? End { get; set; } = null;
        public UserRole Role { get; set; }
        public LockSettingsResponse Settings { get; set; } = new LockSettingsResponse();
        public LockStateResponse State { get; set; } = new LockStateResponse();
        public bool Favourite { get; set; }
        public double? UnlockTime { get; set; } = null;
    }

    public class LockSettingsResponse
    {
        public double UnlockTime { get; set; }
        public List<string> PermittedAddresses { get; set; } = [];
        public string DefaultName { get; set; } = string.Empty;
        public UsageRequirementsResponse? UsageRequirements { get; set; } = null;
        public UnlockBetweenSettingResponse? UnlockBetweenWindow { get; set; } = null;
        public List<string> Tiles { get; set; } = [];
        public bool Hidden { get; set; }
        public List<string> DirectAccessEndpoints { get; set; } = [];
        public Dictionary<CapabilityType, CapabilityStatus> Capabilities { get; set; } = [];
    }

    public class UsageRequirementsResponse
    {
        public List<TimeRequirementResponse>? Time { get; set; } = null;
        public LocationRequirementResponse? Location { get; set; } = null;
    }

    public class TimeRequirementResponse
    {
        public string Start { get; set; } = string.Empty;
        public string End { get; set; } = string.Empty;
        public string Timezone { get; set; } = string.Empty;
        public List<string> Days { get; set; } = [];
    }

    public class LocationRequirementResponse
    {
        public double Latitude { get; set; }
        public double Longitude { get; set; }
        public bool? Enabled { get; set; } = null;
        public int? Radius { get; set; } = null;
        public int? Accuracy { get; set; } = null;
    }

    public class UnlockBetweenSettingResponse
    {
        public string Start { get; set; } = string.Empty;
        public string End { get; set; } = string.Empty;
        public string Timezone { get; set; } = string.Empty;
        public List<string> Days { get; set; } = [];
        public List<string>? Exceptions { get; set; } = null;
    }

    public class LockStateResponse
    {
        public bool Locked { get; set; }
        public bool Connected { get; set; }
    }

    public class UserPublicKeyResponse
    {
        public string Id { get; set; } = string.Empty;
        public string PublicKey { get; set; } = string.Empty;
    }

    public class BatchUserPublicKeyResponse
    {
        public string Id { get; set; } = string.Empty;
        public string? Email { get; set; } = null;
        public string? ForeignKey { get; set; } = null;
        public string? Phone { get; set; } = null;
        public string PublicKey { get; set; } = string.Empty;
    }

    public class ShareableLockResponse
    {
        public string Id { get; set; } = string.Empty;
        public string Name { get; set; } = string.Empty;
    }

    public class UserLockResponse
    {
        public string UserId { get; set; } = string.Empty;
        public string Email { get; set; } = string.Empty;
        public string PublicKey { get; set; } = string.Empty;
        public string? DisplayName { get; set; } = null;
        public bool Orphan { get; set; }
        public bool Foreign { get; set; }
        public UserRole Role { get; set; }
        public double? Start { get; set; } = null;
        public double? End { get; set; } = null;
    }

    public class LockUserResponse
    {
        public string UserId { get; set; } = string.Empty;
        public string Email { get; set; } = string.Empty;
        public string PublicKey { get; set; } = string.Empty;
        public string? DisplayName { get; set; } = null;
        public bool Orphan { get; set; }
        public bool Foreign { get; set; }
        public double? Start { get; set; } = null;
        public double? End { get; set; } = null;
        public List<LockUserDetailsResponse> Devices { get; set; } = [];
    }

    public class LockUserDetailsResponse
    {
        public string DeviceId { get; set; } = string.Empty;
        public UserRole Role { get; set; }
        public double? Start { get; set; } = null;
        public double? End { get; set; } = null;
    }

    public class AuditResponse
    {
        public string DeviceId { get; set; } = string.Empty;
        public double Timestamp { get; set; }
        public AuditEvent Type { get; set; }
        public UserAuditIssuerResponse Issuer { get; set; } = new UserAuditIssuerResponse();
        public UserAuditSubjectResponse? Subject { get; set; } = null;
        public bool Rejected { get; set; }
    }

    public class UserAuditIssuerResponse
    {
        public string UserId { get; set; } = string.Empty;
    }

    public class UserAuditSubjectResponse
    {
        public string UserId { get; set; } = string.Empty;
        public string Email { get; set; } = string.Empty;
    }

    public class LockAuditTrailResponse
    {
        public double Timestamp { get; set; }
        public AuditEvent Type { get; set; }
        public string? User { get; set; } = null;
        public string? Email { get; set; } = null;
        public string? DisplayName { get; set; } = null;
        public string? Message { get; set; } = null;
    }
}
