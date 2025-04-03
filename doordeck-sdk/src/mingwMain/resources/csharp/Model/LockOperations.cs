namespace Doordeck.Headless.Sdk.Model;

public class TimeRequirement(string start, string end, string timezone, List<string> days)
{
    public string Start { get; set; } = start;
    public string End { get; set; } = end;
    public string Timezone { get; set; } = timezone;
    public List<string> Days { get; set; } = days;
}

public class LocationRequirement(
    double latitude,
    double longitude,
    bool? enabled = null,
    int? radius = null,
    int? accuracy = null)
{
    public double Latitude { get; set; } = latitude;
    public double Longitude { get; set; } = longitude;
    public bool? Enabled { get; set; } = enabled;
    public int? Radius { get; set; } = radius;
    public int? Accuracy { get; set; } = accuracy;
}

public class UnlockOperation(BaseOperation baseOperation, List<string>? directAccessEndpoints = null)
{
    public BaseOperation BaseOperation { get; set; } = baseOperation;
    public List<string>? DirectAccessEndpoints { get; set; } = directAccessEndpoints;
}

public class BaseOperation
{
    public string? UserId { get; set; } = null;
    public List<string>? UserCertificateChain { get; set; } = null;
    public string? UserPrivateKey { get; set; } = null;
    public string LockId { get; set; }
    public int NotBefore { get; set; }
    public int IssuedAt { get; set; }
    public int ExpiresAt { get; set; }
    public string Jti { get; set; }

    public BaseOperation(string userId, List<string> userCertificateChain, string userPrivateKey, string lockId, int notBefore, int issuedAt, int expiresAt, string jti)
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

    public BaseOperation(string lockId)
    {
        LockId = lockId;
        NotBefore = (int)DateTimeOffset.Now.ToUnixTimeSeconds();
        IssuedAt = (int)DateTimeOffset.Now.ToUnixTimeSeconds();
        ExpiresAt = (int)DateTimeOffset.Now.AddMinutes(1).ToUnixTimeSeconds();
        Jti = Guid.NewGuid().ToString();
    }
}

public class ShareLock(
    string targetUserId,
    UserRole targetUserRole,
    string targetUserPublicKey,
    int? start = null,
    int? end = null)
{
    public string TargetUserId { get; set; } = targetUserId;
    public UserRole TargetUserRole { get; set; } = targetUserRole;
    public string TargetUserPublicKey { get; set; } = targetUserPublicKey;
    public int? Start { get; set; } = start;
    public int? End { get; set; } = end;
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

public class RevokeAccessToLockOperation(BaseOperation baseOperation, List<string> users)
{
    public BaseOperation BaseOperation { get; set; } = baseOperation;
    public List<string> Users { get; set; } = users;
}

public class UpdateSecureSettingUnlockDuration(BaseOperation baseOperation, int unlockDuration)
{
    public BaseOperation BaseOperation { get; set; } = baseOperation;
    public int UnlockDuration { get; set; } = unlockDuration;
}

public class UnlockBetween(
    string start,
    string end,
    string timezone,
    List<string> days,
    List<string>? exceptions = null)
{
    public string Start { get; set; } = start;
    public string End { get; set; } = end;
    public string Timezone { get; set; } = timezone;
    public List<string> Days { get; set; } = days;
    public List<string>? Exceptions { get; set; } = exceptions;
}

public class UpdateSecureSettingUnlockBetween(
    BaseOperation baseOperation,
    UnlockBetween? unlockBetween = null)
{
    public BaseOperation BaseOperation { get; set; } = baseOperation;
    public UnlockBetween? UnlockBetween { get; set; } = unlockBetween;
}