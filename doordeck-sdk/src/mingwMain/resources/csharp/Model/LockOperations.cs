namespace Doordeck.Headless.Sdk.Model;

public class TimeRequirement(TimeOnly start, TimeOnly end, TimeZoneInfo timezone, List<DayOfWeek> days)
{
    public TimeOnly Start { get; set; } = start;
    public TimeOnly End { get; set; } = end;
    public TimeZoneInfo Timezone { get; set; } = timezone;
    public List<DayOfWeek> Days { get; set; } = days;
}

public class LocationRequirement(
    double latitude,
    double longitude,
    bool enabled = false,
    int radius = 100,
    int accuracy = 200)
{
    public double Latitude { get; set; } = latitude;
    public double Longitude { get; set; } = longitude;
    public bool Enabled { get; set; } = enabled;
    public int Radius { get; set; } = radius;
    public int Accuracy { get; set; } = accuracy;
}

public class UnlockOperation(BaseOperation baseOperation, List<Uri>? directAccessEndpoints = null)
{
    public BaseOperation BaseOperation { get; set; } = baseOperation;
    public List<Uri>? DirectAccessEndpoints { get; set; } = directAccessEndpoints;
}

public class BaseOperation
{
    public Guid? UserId { get; set; }
    public List<string>? UserCertificateChain { get; set; }
    public string? UserPrivateKey { get; set; }
    public Guid LockId { get; set; }
    public DateTime NotBefore { get; set; }
    public DateTime IssuedAt { get; set; }
    public DateTime ExpiresAt { get; set; }
    public Guid Jti { get; set; }

    public BaseOperation(Guid userId, List<string> userCertificateChain, string userPrivateKey, Guid lockId, DateTime notBefore, DateTime issuedAt, DateTime expiresAt, Guid jti)
    {
        UserId = userId;
        UserCertificateChain = userCertificateChain;
        UserPrivateKey = userPrivateKey;
        LockId = lockId;
        NotBefore = notBefore;
        IssuedAt = issuedAt;
        ExpiresAt = expiresAt;
        Jti = jti;
    }

    public BaseOperation(Guid lockId)
    {
        LockId = lockId;
        NotBefore = DateTime.Now;
        IssuedAt = DateTime.Now;
        ExpiresAt = DateTime.Now.AddMinutes(1);
        Jti = Guid.NewGuid();
    }
}

public class ShareLock(
    Guid targetUserId,
    UserRole targetUserRole,
    byte[] targetUserPublicKey,
    DateTime? start = null,
    DateTime? end = null)
{
    public Guid TargetUserId { get; set; } = targetUserId;
    public UserRole TargetUserRole { get; set; } = targetUserRole;
    public byte[] TargetUserPublicKey { get; set; } = targetUserPublicKey;
    public DateTime? Start { get; set; } = start;
    public DateTime? End { get; set; } = end;
}

public class ShareLockOperation(BaseOperation baseOperation, ShareLock shareLock)
{
    public BaseOperation BaseOperation { get; set; } = baseOperation;
    public ShareLock ShareLock { get; set; } = shareLock;
}

public class BatchShareLockOperation(BaseOperation baseOperation, List<ShareLock> users)
{
    public BaseOperation BaseOperation { get; set; } = baseOperation;
    public List<ShareLock> Users { get; set; } = users;
}

public class RevokeAccessToLockOperation(BaseOperation baseOperation, List<Guid> users)
{
    public BaseOperation BaseOperation { get; set; } = baseOperation;
    public List<Guid> Users { get; set; } = users;
}

public class UpdateSecureSettingUnlockDuration(BaseOperation baseOperation, TimeSpan unlockDuration)
{
    public BaseOperation BaseOperation { get; set; } = baseOperation;
    public TimeSpan UnlockDuration { get; set; } = unlockDuration;
}

public class UnlockBetween(
    TimeOnly start,
    TimeOnly end,
    TimeZoneInfo timezone,
    List<DayOfWeek> days,
    List<DateOnly>? exceptions = null)
{
    public TimeOnly Start { get; set; } = start;
    public TimeOnly End { get; set; } = end;
    public TimeZoneInfo Timezone { get; set; } = timezone;
    public List<DayOfWeek> Days { get; set; } = days;
    public List<DateOnly>? Exceptions { get; set; } = exceptions;
}

public class UpdateSecureSettingUnlockBetween(
    BaseOperation baseOperation,
    UnlockBetween? unlockBetween = null)
{
    public BaseOperation BaseOperation { get; set; } = baseOperation;
    public UnlockBetween? UnlockBetween { get; set; } = unlockBetween;
}