using System.Net;

namespace Doordeck.Headless.Sdk.Model.Responses;

public class LockResponse
{
    public required Guid Id { get; set; }
    public required string Name { get; set; }
    public DateTime? Start { get; set; }
    public DateTime? End { get; set; }
    public required UserRole Role { get; set; }
    public required LockSettingsResponse Settings { get; set; }
    public required LockStateResponse State { get; set; }
    public required bool Favourite { get; set; }
}

public class LockSettingsResponse
{
    public required TimeSpan UnlockTime { get; set; }
    public required List<IPAddress> PermittedAddresses { get; set; }
    public required string DefaultName { get; set; }
    public UsageRequirementsResponse? UsageRequirements { get; set; }
    public UnlockBetweenSettingResponse? UnlockBetweenWindow { get; set; }
    public required List<Guid> Tiles { get; set; }
    public required bool Hidden { get; set; }
    public required List<Uri> DirectAccessEndpoints { get; set; }
    public required Dictionary<CapabilityType, CapabilityStatus> Capabilities { get; set; }
}

public class UsageRequirementsResponse
{
    public List<TimeRequirementResponse>? Time { get; set; }
    public LocationRequirementResponse? Location { get; set; }
}

public class TimeRequirementResponse
{
    public required TimeOnly Start { get; set; }
    public required TimeOnly End { get; set; }
    public required TimeZoneInfo Timezone { get; set; }
    public required List<DayOfWeek> Days { get; set; }
}

public class LocationRequirementResponse
{
    public required double Latitude { get; set; }
    public required double Longitude { get; set; }
    public required bool Enabled { get; set; }
    public required int Radius { get; set; }
    public required int Accuracy { get; set; }
}

public class UnlockBetweenSettingResponse
{
    public required TimeOnly Start { get; set; }
    public required TimeOnly End { get; set; }
    public required TimeZoneInfo Timezone { get; set; }
    public required List<DayOfWeek> Days { get; set; }
    public List<DateOnly>? Exceptions { get; set; }
}

public class LockStateResponse
{
    public bool? Connected { get; set; }
}

public class UserPublicKeyResponse
{
    public required Guid Id { get; set; }
    public required string PublicKey { get; set; }
}

public class BatchUserPublicKeyResponse
{
    public required Guid Id { get; set; }
    public string? Email { get; set; }
    public string? ForeignKey { get; set; }
    public string? Phone { get; set; }
    public required string PublicKey { get; set; }
}

public class ShareableLockResponse
{
    public required Guid Id { get; set; }
    public required string Name { get; set; }
}

public class UserLockResponse
{
    public required Guid UserId { get; set; }
    public required string Email { get; set; }
    public required string PublicKey { get; set; }
    public string? DisplayName { get; set; }
    public required bool Orphan { get; set; }
    public required bool Foreign { get; set; }
    public required UserRole Role { get; set; }
    public DateTime? Start { get; set; }
    public DateTime? End { get; set; }
}

public class LockUserResponse
{
    public required Guid UserId { get; set; }
    public required string Email { get; set; }
    public required string PublicKey { get; set; }
    public string? DisplayName { get; set; }
    public required bool Orphan { get; set; }
    public required bool Foreign { get; set; }
    public DateTime? Start { get; set; }
    public DateTime? End { get; set; }
    public required List<LockUserDetailsResponse> Devices { get; set; }
}

public class LockUserDetailsResponse
{
    public required Guid DeviceId { get; set; }
    public required UserRole Role { get; set; }
    public DateTime? Start { get; set; }
    public DateTime? End { get; set; }
}

public class AuditResponse
{
    public required Guid DeviceId { get; set; }
    public required DateTime Timestamp { get; set; }
    public required AuditEvent Type { get; set; }
    public required AuditUserResponse Issuer { get; set; }
    public AuditUserResponse? Subject { get; set; }
    public required bool Rejected { get; set; }
}

public class AuditUserResponse
{
    public required Guid UserId { get; set; }
    public string? Email { get; set; }
    public string? DisplayName { get; set; }
    public IPAddress? Ip { get; set; }
}