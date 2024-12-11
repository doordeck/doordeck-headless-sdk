namespace DoordeckHeadlessSDK.model
{
    public class GetSingleLockData
    {
        public string lockId { get; set; }

        public GetSingleLockData(string lockId)
        {
            this.lockId = lockId;
        }
    }

    public class GetLockAuditTrailData
    {
        public string lockId { get; set; }
        public int start { get; set; }
        public int end { get; set; }

        public GetLockAuditTrailData(string lockId, int start, int end)
        {
            this.lockId = lockId;
            this.start = start;
            this.end = end;
        }
    }

    public class GetAuditForUserData
    {
        public string userId { get; set; }
        public int start { get; set; }
        public int end { get; set; }

        public GetAuditForUserData(string userId, int start, int end)
        {
            this.userId = userId;
            this.start = start;
            this.end = end;
        }
    }

    public class GetUsersForLockData
    {
        public string lockId { get; set; }

        public GetUsersForLockData(string lockId)
        {
            this.lockId = lockId;
        }
    }

    public class GetLocksForUserData
    {
        public string userId { get; set; }

        public GetLocksForUserData(string userId)
        {
            this.userId = userId;
        }
    }

    public class UpdateLockNameData
    {
        public string lockId { get; set; }
        public string? name { get; set; }

        public UpdateLockNameData(string lockId, string? name = null)
        {
            this.lockId = lockId;
            this.name = name;
        }
    }

    public class UpdateLockFavouriteData
    {
        public string lockId { get; set; }
        public bool? favourite { get; set; }

        public UpdateLockFavouriteData(string lockId, bool? favourite = null)
        {
            this.lockId = lockId;
            this.favourite = favourite;
        }
    }

    public class UpdateLockColourData
    {
        public string lockId { get; set; }
        public string? colour { get; set; }

        public UpdateLockColourData(string lockId, string? colour = null)
        {
            this.lockId = lockId;
            this.colour = colour;
        }
    }

    public class UpdateLockSettingDefaultNameData
    {
        public string lockId { get; set; }
        public string? name { get; set; }

        public UpdateLockSettingDefaultNameData(string lockId, string? name = null)
        {
            this.lockId = lockId;
            this.name = name;
        }
    }

    public class SetLockSettingPermittedAddressesData
    {
        public string lockId { get; set; }
        public List<string> permittedAddresses { get; set; }

        public SetLockSettingPermittedAddressesData(string lockId, List<string> permittedAddresses)
        {
            this.lockId = lockId;
            this.permittedAddresses = permittedAddresses;
        }
    }

    public class UpdateLockSettingHiddenData
    {
        public string lockId { get; set; }
        public bool hidden { get; set; }

        public UpdateLockSettingHiddenData(string lockId, bool hidden)
        {
            this.lockId = lockId;
            this.hidden = hidden;
        }
    }

    public class SetLockSettingTimeRestrictionsData
    {
        public string lockId { get; set; }
        public List<TimeRequirementData> times { get; set; }

        public SetLockSettingTimeRestrictionsData(string lockId, List<TimeRequirementData> times)
        {
            this.lockId = lockId;
            this.times = times;
        }
    }

    public class TimeRequirementData
    {
        public string start { get; set; }
        public string end { get; set; }
        public string timezone { get; set; }
        public List<string> days { get; set; }

        public TimeRequirementData(string start, string end, string timezone, List<string> days)
        {
            this.start = start;
            this.end = end;
            this.timezone = timezone;
            this.days = days;
        }
    }

    public class UpdateLockSettingLocationRestrictionsData
    {
        public string lockId { get; set; }
        public LocationRequirementData? location { get; set; }

        public UpdateLockSettingLocationRestrictionsData(string lockId, LocationRequirementData? location = null)
        {
            this.lockId = lockId;
            this.location = location;
        }
    }

    public class LocationRequirementData
    {
        public double latitude { get; set; }
        public double longitude { get; set; }
        public bool? enabled { get; set; }
        public int? radius { get; set; }
        public int? accuracy { get; set; }

        public LocationRequirementData(double latitude, double longitude, bool? enabled = null, int? radius = null, int? accuracy = null)
        {
            this.latitude = latitude;
            this.longitude = longitude;
            this.enabled = enabled;
            this.radius = radius;
            this.accuracy = accuracy;
        }
    }

    public class GetUserPublicKeyData
    {
        public string userEmail { get; set; }
        public bool visitor { get; set; }

        public GetUserPublicKeyData(string userEmail, bool visitor = false)
        {
            this.userEmail = userEmail;
            this.visitor = visitor;
        }
    }

    public class GetUserPublicKeyByEmailData
    {
        public string email { get; set; }

        public GetUserPublicKeyByEmailData(string email)
        {
            this.email = email;
        }
    }

    public class GetUserPublicKeyByTelephoneData
    {
        public string telephone { get; set; }

        public GetUserPublicKeyByTelephoneData(string telephone)
        {
            this.telephone = telephone;
        }
    }

    public class GetUserPublicKeyByLocalKeyData
    {
        public string localKey { get; set; }

        public GetUserPublicKeyByLocalKeyData(string localKey)
        {
            this.localKey = localKey;
        }
    }

    public class GetUserPublicKeyByForeignKeyData
    {
        public string foreignKey { get; set; }

        public GetUserPublicKeyByForeignKeyData(string foreignKey)
        {
            this.foreignKey = foreignKey;
        }
    }

    public class GetUserPublicKeyByIdentityData
    {
        public string identity { get; set; }

        public GetUserPublicKeyByIdentityData(string identity)
        {
            this.identity = identity;
        }
    }

    public class GetUserPublicKeyByEmailsData
    {
        public List<string> emails { get; set; }

        public GetUserPublicKeyByEmailsData(List<string> emails)
        {
            this.emails = emails;
        }
    }

    public class UnlockOperationData
    {
        public BaseOperationData baseOperation { get; set; }
        public List<string>? directAccessEndpoints { get; set; }

        public UnlockOperationData(BaseOperationData baseOperation, List<string>? directAccessEndpoints = null)
        {
            this.baseOperation = baseOperation;
            this.directAccessEndpoints = directAccessEndpoints;
        }
    }

    public class BaseOperationData
    {
        public string? userId { get; set; }
        public List<string>? userCertificateChain { get; set; }
        public string? userPrivateKey { get; set; }
        public string lockId { get; set; }
        public int? notBefore { get; set; }
        public int? issuedAt { get; set; }
        public int? expiresAt { get; set; }
        public string? jti { get; set; }

        public BaseOperationData(string lockId, string? userId = null, List<string>? userCertificateChain = null, string? userPrivateKey = null, int? notBefore = null, int? issuedAt = null, int? expiresAt = null, string? jti = null)
        {
            this.userId = userId;
            this.userCertificateChain = userCertificateChain;
            this.userPrivateKey = userPrivateKey;
            this.lockId = lockId;
            this.notBefore = notBefore;
            this.issuedAt = issuedAt;
            this.expiresAt = expiresAt;
            this.jti = jti;
        }
    }

    public class ShareLockData
    {
        public string targetUserId { get; set; }
        public UserRole targetUserRole { get; set; }
        public string targetUserPublicKey { get; set; }
        public int? start { get; set; }
        public int? end { get; set; }

        public ShareLockData(string targetUserId, UserRole targetUserRole, string targetUserPublicKey, int? start = null, int? end = null)
        {
            this.targetUserId = targetUserId;
            this.targetUserRole = targetUserRole;
            this.targetUserPublicKey = targetUserPublicKey;
            this.start = start;
            this.end = end;
        }
    }

    public class ShareLockOperationData
    {
        public BaseOperationData baseOperation { get; set; }
        public ShareLockData shareLock { get; set; }

        public ShareLockOperationData(BaseOperationData baseOperation, ShareLockData shareLock)
        {
            this.baseOperation = baseOperation;
            this.shareLock = shareLock;
        }
    }

    public class RevokeAccessToLockOperationData
    {
        public BaseOperationData baseOperation { get; set; }
        public List<string> users { get; set; }

        public RevokeAccessToLockOperationData(BaseOperationData baseOperation, List<string> users)
        {
            this.baseOperation = baseOperation;
            this.users = users;
        }
    }

    public class UpdateSecureSettingUnlockDurationData
    {
        public BaseOperationData baseOperation { get; set; }
        public int unlockDuration { get; set; }

        public UpdateSecureSettingUnlockDurationData(BaseOperationData baseOperation, int unlockDuration)
        {
            this.baseOperation = baseOperation;
            this.unlockDuration = unlockDuration;
        }
    }

    public class UnlockBetweenData
    {
        public string start { get; set; }
        public string end { get; set; }
        public string timezone { get; set; }
        public List<string> days { get; set; }
        public List<string>? exceptions { get; set; }

        public UnlockBetweenData(string start, string end, string timezone, List<string> days, List<string>? exceptions = null)
        {
            this.start = start;
            this.end = end;
            this.timezone = timezone;
            this.days = days;
            this.exceptions = exceptions;
        }
    }

    public class UpdateSecureSettingUnlockBetweenData
    {
        public BaseOperationData baseOperation { get; set; }
        public UnlockBetweenData? unlockBetween { get; set; }

        public UpdateSecureSettingUnlockBetweenData(BaseOperationData baseOperation, UnlockBetweenData? unlockBetween = null)
        {
            this.baseOperation = baseOperation;
            this.unlockBetween = unlockBetween;
        }
    }
}
