using System.Net;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;

using LockOperationsApi = Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_LockOperationsApi;

public class LockOperations(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_LockOperationsApi lockOperations) : AbstractWrapper
{
    public unsafe Task<LockResponse> GetSingleLock(Guid lockId) =>
        Process<LockOperationsApi, LockResponse>(lockOperations, &Methods.getSingleLock, new { lockId });

    public unsafe Task<List<AuditResponse>> GetLockAuditTrail(Guid lockId, DateTime? start = null, DateTime? end = null) =>
        Process<LockOperationsApi, List<AuditResponse>>(lockOperations, &Methods.getLockAuditTrail, new { lockId, start, end });

    public unsafe Task<List<AuditResponse>> GetAuditForUser(Guid userId, DateTime? start = null, DateTime? end = null) =>
        Process<LockOperationsApi, List<AuditResponse>>(lockOperations, &Methods.getAuditForUser, new { userId, start, end });

    public unsafe Task<List<UserLockResponse>> GetUsersForLock(Guid lockId) =>
        Process<LockOperationsApi, List<UserLockResponse>>(lockOperations, &Methods.getUsersForLock, new { lockId });

    public unsafe Task<LockUserResponse> GetLocksForUser(Guid userId) =>
        Process<LockOperationsApi, LockUserResponse>(lockOperations, &Methods.getLocksForUser, new { userId });

    public unsafe Task<object> UpdateLockName(Guid lockId, string? name = null) =>
        Process<LockOperationsApi, object>(lockOperations, &Methods.updateLockName, new { lockId, name });

    public unsafe Task<object> UpdateLockFavourite(Guid lockId, bool favourite) =>
        Process<LockOperationsApi, object>(lockOperations, &Methods.updateLockFavourite, new { lockId, favourite });

    public unsafe Task<object> UpdateLockSettingDefaultName(Guid lockId, string name) =>
        Process<LockOperationsApi, object>(lockOperations, &Methods.updateLockSettingDefaultName, new { lockId, name });

    public unsafe Task<object> SetLockSettingPermittedAddresses(Guid lockId, List<IPAddress> permittedAddresses) =>
        Process<LockOperationsApi, object>(lockOperations, &Methods.setLockSettingPermittedAddresses, new { lockId,  permittedAddresses });

    public unsafe Task<object> UpdateLockSettingHidden(Guid lockId, bool hidden) =>
        Process<LockOperationsApi, object>(lockOperations, &Methods.updateLockSettingHidden, new { lockId, hidden });

    public unsafe Task<object> SetLockSettingTimeRestrictions(Guid lockId, List<TimeRequirement> times) =>
        Process<LockOperationsApi, object>(lockOperations, &Methods.setLockSettingTimeRestrictions, new { lockId, times });

    public unsafe Task<object> UpdateLockSettingLocationRestrictions(Guid lockId, LocationRequirement? location = null) =>
        Process<LockOperationsApi, object>(lockOperations, &Methods.updateLockSettingLocationRestrictions, new { lockId, location });

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKey(string userEmail, bool visitor = false) =>
        Process<LockOperationsApi, UserPublicKeyResponse>(lockOperations, &Methods.getUserPublicKey, new { userEmail, visitor });

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKeyByEmail(string email) =>
        Process<LockOperationsApi, UserPublicKeyResponse>(lockOperations, &Methods.getUserPublicKeyByEmail, new { email });

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKeyByTelephone(string telephone) =>
        Process<LockOperationsApi, UserPublicKeyResponse>(lockOperations, &Methods.getUserPublicKeyByTelephone, new { telephone });

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKeyByLocalKey(string localKey) =>
        Process<LockOperationsApi, UserPublicKeyResponse>(lockOperations, &Methods.getUserPublicKeyByLocalKey, new { localKey });

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKeyByForeignKey(string foreignKey) =>
        Process<LockOperationsApi, UserPublicKeyResponse>(lockOperations, &Methods.getUserPublicKeyByForeignKey, new { foreignKey });

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKeyByIdentity(string identity) =>
        Process<LockOperationsApi, UserPublicKeyResponse>(lockOperations, &Methods.getUserPublicKeyByIdentity, new { identity });

    public unsafe Task<List<BatchUserPublicKeyResponse>> GetUserPublicKeyByEmails(List<string> emails) =>
        Process<LockOperationsApi, List<BatchUserPublicKeyResponse>>(lockOperations, &Methods.getUserPublicKeyByEmails, new { emails });

    public unsafe Task<List<BatchUserPublicKeyResponse>> GetUserPublicKeyByTelephones(List<string> telephones) =>
        Process<LockOperationsApi, List<BatchUserPublicKeyResponse>>(lockOperations, &Methods.getUserPublicKeyByTelephones, new { telephones });

    public unsafe Task<List<BatchUserPublicKeyResponse>> GetUserPublicKeyByLocalKeys(List<string> localKeys) =>
        Process<LockOperationsApi, List<BatchUserPublicKeyResponse>>(lockOperations, &Methods.getUserPublicKeyByLocalKeys, new { localKeys });

    public unsafe Task<List<BatchUserPublicKeyResponse>> GetUserPublicKeyByForeignKeys(List<string> foreignKeys) =>
        Process<LockOperationsApi, List<BatchUserPublicKeyResponse>>(lockOperations, &Methods.getUserPublicKeyByForeignKeys, new { foreignKeys });
    
    public unsafe Task<object> Unlock(UnlockOperation data) =>
        Process<LockOperationsApi, object>(lockOperations, &Methods.unlock, data);

    public unsafe Task<object> ShareLock(ShareLockOperation data) =>
        Process<LockOperationsApi, object>(lockOperations, &Methods.shareLock, data);

    public unsafe Task<object> BatchShareLock(BatchShareLockOperation data) =>
        Process<LockOperationsApi, object>(lockOperations, &Methods.batchShareLock, data);

    public unsafe Task<object> RevokeAccessToLock(RevokeAccessToLockOperation data) =>
        Process<LockOperationsApi, object>(lockOperations, &Methods.revokeAccessToLock, data);

    public unsafe Task<object> UpdateSecureSettingUnlockDuration(UpdateSecureSettingUnlockDuration data) =>
        Process<LockOperationsApi, object>(lockOperations, &Methods.updateSecureSettingUnlockDuration, data);

    public unsafe Task<object> UpdateSecureSettingUnlockBetween(UpdateSecureSettingUnlockBetween data) =>
        Process<LockOperationsApi, object>(lockOperations, &Methods.updateSecureSettingUnlockBetween, data);

    public unsafe Task<List<LockResponse>> GetPinnedLocks() =>
        Process<LockOperationsApi, List<LockResponse>>(lockOperations, &Methods.getPinnedLocks);

    public unsafe Task<List<ShareableLockResponse>> GetShareableLocks() =>
        Process<LockOperationsApi, List<ShareableLockResponse>>(lockOperations, &Methods.getShareableLocks);
}