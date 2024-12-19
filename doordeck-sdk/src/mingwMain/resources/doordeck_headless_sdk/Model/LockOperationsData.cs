namespace Doordeck.Headless.Sdk.Model
{
    public class GetSingleLockData
    {
        public string LockId { get; set; }

        public GetSingleLockData(string lockId)
        {
            LockId = lockId;
        }
    }

    public class GetLockAuditTrailData
    {
        public string LockId { get; set; }
        public int Start { get; set; }
        public int End { get; set; }

        public GetLockAuditTrailData(string lockId, int start, int end)
        {
            LockId = lockId;
            Start = start;
            End = end;
        }
    }

    public class GetAuditForUserData
    {
        public string UserId { get; set; }
        public int Start { get; set; }
        public int End { get; set; }

        public GetAuditForUserData(string userId, int start, int end)
        {
            UserId = userId;
            Start = start;
            End = end;
        }
    }

    public class GetUsersForLockData
    {
        public string LockId { get; set; }

        public GetUsersForLockData(string lockId)
        {
            LockId = lockId;
        }
    }

    public class GetLocksForUserData
    {
        public string UserId { get; set; }

        public GetLocksForUserData(string userId)
        {
            UserId = userId;
        }
    }

    public class UpdateLockNameData
    {
        public string LockId { get; set; }
        public string? Name { get; set; }

        public UpdateLockNameData(string lockId, string? name = null)
        {
            LockId = lockId;
            Name = name;
        }
    }

    public class UpdateLockFavouriteData
    {
        public string LockId { get; set; }
        public bool? Favourite { get; set; }

        public UpdateLockFavouriteData(string lockId, bool? favourite = null)
        {
            LockId = lockId;
            Favourite = favourite;
        }
    }

    public class UpdateLockColourData
    {
        public string LockId { get; set; }
        public string? Colour { get; set; }

        public UpdateLockColourData(string lockId, string? colour = null)
        {
            LockId = lockId;
            Colour = colour;
        }
    }

    public class UpdateLockSettingDefaultNameData
    {
        public string LockId { get; set; }
        public string? Name { get; set; }

        public UpdateLockSettingDefaultNameData(string lockId, string? name = null)
        {
            LockId = lockId;
            Name = name;
        }
    }

    public class SetLockSettingPermittedAddressesData
    {
        public string LockId { get; set; }
        public List<string> PermittedAddresses { get; set; }

        public SetLockSettingPermittedAddressesData(string lockId, List<string> permittedAddresses)
        {
            LockId = lockId;
            PermittedAddresses = permittedAddresses;
        }
    }

    public class UpdateLockSettingHiddenData
    {
        public string LockId { get; set; }
        public bool Hidden { get; set; }

        public UpdateLockSettingHiddenData(string lockId, bool hidden)
        {
            LockId = lockId;
            Hidden = hidden;
        }
    }

    public class SetLockSettingTimeRestrictionsData
    {
        public string LockId { get; set; }
        public List<TimeRequirementData> Times { get; set; }

        public SetLockSettingTimeRestrictionsData(string lockId, List<TimeRequirementData> times)
        {
            LockId = lockId;
            Times = times;
        }
    }

    public class TimeRequirementData
    {
        public string Start { get; set; }
        public string End { get; set; }
        public string Timezone { get; set; }
        public List<string> Days { get; set; }

        public TimeRequirementData(string start, string end, string timezone, List<string> days)
        {
            Start = start;
            End = end;
            Timezone = timezone;
            Days = days;
        }
    }

    public class UpdateLockSettingLocationRestrictionsData
    {
        public string LockId { get; set; }
        public LocationRequirementData? Location { get; set; }

        public UpdateLockSettingLocationRestrictionsData(string lockId, LocationRequirementData? location = null)
        {
            LockId = lockId;
            Location = location;
        }
    }

    public class LocationRequirementData
    {
        public double Latitude { get; set; }
        public double Longitude { get; set; }
        public bool? Enabled { get; set; }
        public int? Radius { get; set; }
        public int? Accuracy { get; set; }

        public LocationRequirementData(double latitude, double longitude, bool? enabled = null, int? radius = null, int? accuracy = null)
        {
            Latitude = latitude;
            Longitude = longitude;
            Enabled = enabled;
            Radius = radius;
            Accuracy = accuracy;
        }
    }

    public class GetUserPublicKeyData
    {
        public string UserEmail { get; set; }
        public bool Visitor { get; set; }

        public GetUserPublicKeyData(string userEmail, bool visitor = false)
        {
            UserEmail = userEmail;
            Visitor = visitor;
        }
    }

    public class GetUserPublicKeyByEmailData
    {
        public string Email { get; set; }

        public GetUserPublicKeyByEmailData(string email)
        {
            Email = email;
        }
    }

    public class GetUserPublicKeyByTelephoneData
    {
        public string Telephone { get; set; }

        public GetUserPublicKeyByTelephoneData(string telephone)
        {
            Telephone = telephone;
        }
    }

    public class GetUserPublicKeyByLocalKeyData
    {
        public string LocalKey { get; set; }

        public GetUserPublicKeyByLocalKeyData(string localKey)
        {
            LocalKey = localKey;
        }
    }

    public class GetUserPublicKeyByForeignKeyData
    {
        public string ForeignKey { get; set; }

        public GetUserPublicKeyByForeignKeyData(string foreignKey)
        {
            ForeignKey = foreignKey;
        }
    }

    public class GetUserPublicKeyByIdentityData
    {
        public string Identity { get; set; }

        public GetUserPublicKeyByIdentityData(string identity)
        {
            Identity = identity;
        }
    }

    public class GetUserPublicKeyByEmailsData
    {
        public List<string> Emails { get; set; }

        public GetUserPublicKeyByEmailsData(List<string> emails)
        {
            Emails = emails;
        }
    }

    public class UnlockOperationData
    {
        public BaseOperationData BaseOperation { get; set; }
        public List<string>? DirectAccessEndpoints { get; set; }

        public UnlockOperationData(BaseOperationData baseOperation, List<string>? directAccessEndpoints = null)
        {
            BaseOperation = baseOperation;
            DirectAccessEndpoints = directAccessEndpoints;
        }
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

    public class ShareLockData
    {
        public string TargetUserId { get; set; }
        public UserRole TargetUserRole { get; set; }
        public string TargetUserPublicKey { get; set; }
        public int? Start { get; set; }
        public int? End { get; set; }

        public ShareLockData(string targetUserId, UserRole targetUserRole, string targetUserPublicKey, int? start = null, int? end = null)
        {
            TargetUserId = targetUserId;
            TargetUserRole = targetUserRole;
            TargetUserPublicKey = targetUserPublicKey;
            Start = start;
            End = end;
        }
    }

    public class ShareLockOperationData
    {
        public BaseOperationData BaseOperation { get; set; }
        public ShareLockData ShareLock { get; set; }

        public ShareLockOperationData(BaseOperationData baseOperation, ShareLockData shareLock)
        {
            BaseOperation = baseOperation;
            ShareLock = shareLock;
        }
    }

    public class RevokeAccessToLockOperationData
    {
        public BaseOperationData BaseOperation { get; set; }
        public List<string> Users { get; set; }

        public RevokeAccessToLockOperationData(BaseOperationData baseOperation, List<string> users)
        {
            BaseOperation = baseOperation;
            Users = users;
        }
    }

    public class UpdateSecureSettingUnlockDurationData
    {
        public BaseOperationData BaseOperation { get; set; }
        public int UnlockDuration { get; set; }

        public UpdateSecureSettingUnlockDurationData(BaseOperationData baseOperation, int unlockDuration)
        {
            BaseOperation = baseOperation;
            UnlockDuration = unlockDuration;
        }
    }

    public class UnlockBetweenData
    {
        public string Start { get; set; }
        public string End { get; set; }
        public string Timezone { get; set; }
        public List<string> Days { get; set; }
        public List<string>? Exceptions { get; set; }

        public UnlockBetweenData(string start, string end, string timezone, List<string> days, List<string>? exceptions = null)
        {
            Start = start;
            End = end;
            Timezone = timezone;
            Days = days;
            Exceptions = exceptions;
        }
    }

    public class UpdateSecureSettingUnlockBetweenData
    {
        public BaseOperationData BaseOperation { get; set; }
        public UnlockBetweenData? UnlockBetween { get; set; }

        public UpdateSecureSettingUnlockBetweenData(BaseOperationData baseOperation, UnlockBetweenData? unlockBetween = null)
        {
            BaseOperation = baseOperation;
            UnlockBetween = unlockBetween;
        }
    }
}
