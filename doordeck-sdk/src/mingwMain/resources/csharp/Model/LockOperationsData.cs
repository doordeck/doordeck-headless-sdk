namespace Doordeck.Headless.Sdk.Model;

public class LockIdData(string lockId)
{
    public string LockId { get; set; } = lockId;
}

public class GetLockAuditTrailData(string lockId, int start, int end)
{
    public string LockId { get; set; } = lockId;
    public int Start { get; set; } = start;
    public int End { get; set; } = end;
}

public class GetAuditForUserData(string userId, int start, int end)
{
    public string UserId { get; set; } = userId;
    public int Start { get; set; } = start;
    public int End { get; set; } = end;
}

public class GetLocksForUserData(string userId)
{
    public string UserId { get; set; } = userId;
}

public class UpdateLockNameData(string lockId, string? name = null)
{
    public string LockId { get; set; } = lockId;
    public string? Name { get; set; } = name;
}

public class UpdateLockFavouriteData(string lockId, bool? favourite = null)
{
    public string LockId { get; set; } = lockId;
    public bool? Favourite { get; set; } = favourite;
}

public class UpdateLockColourData(string lockId, string? colour = null)
{
    public string LockId { get; set; } = lockId;
    public string? Colour { get; set; } = colour;
}

public class UpdateLockSettingDefaultNameData(string lockId, string? name = null)
{
    public string LockId { get; set; } = lockId;
    public string? Name { get; set; } = name;
}

public class SetLockSettingPermittedAddressesData(string lockId, List<string> permittedAddresses)
{
    public string LockId { get; set; } = lockId;
    public List<string> PermittedAddresses { get; set; } = permittedAddresses;
}

public class UpdateLockSettingHiddenData(string lockId, bool hidden)
{
    public string LockId { get; set; } = lockId;
    public bool Hidden { get; set; } = hidden;
}

public class SetLockSettingTimeRestrictionsData(string lockId, List<TimeRequirementData> times)
{
    public string LockId { get; set; } = lockId;
    public List<TimeRequirementData> Times { get; set; } = times;
}

public class TimeRequirementData(string start, string end, string timezone, List<string> days)
{
    public string Start { get; set; } = start;
    public string End { get; set; } = end;
    public string Timezone { get; set; } = timezone;
    public List<string> Days { get; set; } = days;
}

public class UpdateLockSettingLocationRestrictionsData(string lockId, LocationRequirementData? location = null)
{
    public string LockId { get; set; } = lockId;
    public LocationRequirementData? Location { get; set; } = location;
}

public class LocationRequirementData(
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

public class GetUserPublicKeyData(string userEmail, bool visitor = false)
{
    public string UserEmail { get; set; } = userEmail;
    public bool Visitor { get; set; } = visitor;
}

public class GetUserPublicKeyByEmailData(string email)
{
    public string Email { get; set; } = email;
}

public class GetUserPublicKeyByTelephoneData(string telephone)
{
    public string Telephone { get; set; } = telephone;
}

public class GetUserPublicKeyByLocalKeyData(string localKey)
{
    public string LocalKey { get; set; } = localKey;
}

public class GetUserPublicKeyByForeignKeyData(string foreignKey)
{
    public string ForeignKey { get; set; } = foreignKey;
}

public class GetUserPublicKeyByIdentityData(string identity)
{
    public string Identity { get; set; } = identity;
}

public class GetUserPublicKeyByEmailsData(List<string> emails)
{
    public List<string> Emails { get; set; } = emails;
}

public class GetUserPublicKeyByTelephonesData(List<string> telephones)
{
    public List<string> Telephones { get; set; } = telephones;
}

public class GetUserPublicKeyByLocalKeysData(List<string> localKeys)
{
    public List<string> LocalKeys { get; set; } = localKeys;
}

public class GetUserPublicKeyByForeignKeysData(List<string> foreignKeys)
{
    public List<string> ForeignKeys { get; set; } = foreignKeys;
}

public class UnlockOperationData(BaseOperationData baseOperation, List<string>? directAccessEndpoints = null)
{
    public BaseOperationData BaseOperation { get; set; } = baseOperation;
    public List<string>? DirectAccessEndpoints { get; set; } = directAccessEndpoints;
}

public class BaseOperationData
{
    public string? UserId { get; set; } = null;
    public List<string>? UserCertificateChain { get; set; } = null;
    public string? UserPrivateKey { get; set; } = null;
    public string LockId { get; set; }
    public int NotBefore { get; set; }
    public int IssuedAt { get; set; }
    public int ExpiresAt { get; set; }
    public string Jti { get; set; }

    public BaseOperationData(string userId, List<string> userCertificateChain, string userPrivateKey, string lockId, int notBefore, int issuedAt, int expiresAt, string jti)
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

    public BaseOperationData(string lockId)
    {
        LockId = lockId;
        NotBefore = (int)DateTimeOffset.Now.ToUnixTimeSeconds();
        IssuedAt = (int)DateTimeOffset.Now.ToUnixTimeSeconds();
        ExpiresAt = (int)DateTimeOffset.Now.AddMinutes(1).ToUnixTimeSeconds();
        Jti = Guid.NewGuid().ToString();
    }
}

public class ShareLockData(
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

public class ShareLockOperationData(BaseOperationData baseOperation, ShareLockData shareLock)
{
    public BaseOperationData BaseOperation { get; set; } = baseOperation;
    public ShareLockData ShareLock { get; set; } = shareLock;
}

public class BatchShareLockOperationData(BaseOperationData baseOperation, List<ShareLockData> users)
{
    public BaseOperationData BaseOperation { get; set; } = baseOperation;
    public List<ShareLockData> Users { get; set; } = users;
}

public class RevokeAccessToLockOperationData(BaseOperationData baseOperation, List<string> users)
{
    public BaseOperationData BaseOperation { get; set; } = baseOperation;
    public List<string> Users { get; set; } = users;
}

public class UpdateSecureSettingUnlockDurationData(BaseOperationData baseOperation, int unlockDuration)
{
    public BaseOperationData BaseOperation { get; set; } = baseOperation;
    public int UnlockDuration { get; set; } = unlockDuration;
}

public class UnlockBetweenData(
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

public class UpdateSecureSettingUnlockBetweenData(
    BaseOperationData baseOperation,
    UnlockBetweenData? unlockBetween = null)
{
    public BaseOperationData BaseOperation { get; set; } = baseOperation;
    public UnlockBetweenData? UnlockBetween { get; set; } = unlockBetween;
}