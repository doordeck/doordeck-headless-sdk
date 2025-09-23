using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;

using LockOperationsApi = Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_LockOperationsApi;

public class LockOperations(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_LockOperationsApi lockOperations,
    Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._LockOperationsApi_e__Struct lockOperationsApi) : AbstractWrapper
{
    public unsafe Task<LockResponse> GetSingleLock(string lockId) =>
        Process<LockOperationsApi, LockResponse>(lockOperations, lockOperationsApi.getSingleLock_, new { lockId });

    public unsafe Task<List<AuditResponse>> GetLockAuditTrail(string lockId, long? start = null, long? end = null) =>
        Process<LockOperationsApi, List<AuditResponse>>(lockOperations, lockOperationsApi.getLockAuditTrail_, new { lockId, start, end });

    public unsafe Task<List<AuditResponse>> GetAuditForUser(string userId, long? start = null, long? end = null) =>
        Process<LockOperationsApi, List<AuditResponse>>(lockOperations, lockOperationsApi.getAuditForUser_, new { userId, start, end });

    public unsafe Task<List<UserLockResponse>> GetUsersForLock(string lockId) =>
        Process<LockOperationsApi, List<UserLockResponse>>(lockOperations, lockOperationsApi.getUsersForLock_, new { lockId });

    public unsafe Task<LockUserResponse> GetLocksForUser(string userId) =>
        Process<LockOperationsApi, LockUserResponse>(lockOperations, lockOperationsApi.getLocksForUser_, new { userId });

    public unsafe Task<object> UpdateLockName(string lockId, string? name = null) =>
        Process<LockOperationsApi, object>(lockOperations, lockOperationsApi.updateLockName_, new { lockId, name });

    public unsafe Task<object> UpdateLockFavourite(string lockId, bool favourite) =>
        Process<LockOperationsApi, object>(lockOperations, lockOperationsApi.updateLockFavourite_, new { lockId, favourite });

    public unsafe Task<object> UpdateLockSettingDefaultName(string lockId, string name) =>
        Process<LockOperationsApi, object>(lockOperations, lockOperationsApi.updateLockSettingDefaultName_, new { lockId, name });

    public unsafe Task<object> SetLockSettingPermittedAddresses(string lockId, List<string> permittedAddresses) =>
        Process<LockOperationsApi, object>(lockOperations, lockOperationsApi.setLockSettingPermittedAddresses_, new { lockId,  permittedAddresses });

    public unsafe Task<object> UpdateLockSettingHidden(string lockId, bool hidden) =>
        Process<LockOperationsApi, object>(lockOperations, lockOperationsApi.updateLockSettingHidden_, new { lockId, hidden });

    public unsafe Task<object> SetLockSettingTimeRestrictions(string lockId, List<TimeRequirement> times) =>
        Process<LockOperationsApi, object>(lockOperations, lockOperationsApi.setLockSettingTimeRestrictions_, new { lockId, times });

    public unsafe Task<object> UpdateLockSettingLocationRestrictions(string lockId, LocationRequirement? location = null) =>
        Process<LockOperationsApi, object>(lockOperations, lockOperationsApi.updateLockSettingLocationRestrictions_, new { lockId, location });

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKey(string userEmail, bool visitor = false) =>
        Process<LockOperationsApi, UserPublicKeyResponse>(lockOperations, lockOperationsApi.getUserPublicKey_, new { userEmail, visitor });

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKeyByEmail(string email) =>
        Process<LockOperationsApi, UserPublicKeyResponse>(lockOperations, lockOperationsApi.getUserPublicKeyByEmail_, new { email });

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKeyByTelephone(string telephone) =>
        Process<LockOperationsApi, UserPublicKeyResponse>(lockOperations, lockOperationsApi.getUserPublicKeyByTelephone_, new { telephone });

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKeyByLocalKey(string localKey) =>
        Process<LockOperationsApi, UserPublicKeyResponse>(lockOperations, lockOperationsApi.getUserPublicKeyByLocalKey_, new { localKey });

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKeyByForeignKey(string foreignKey) =>
        Process<LockOperationsApi, UserPublicKeyResponse>(lockOperations, lockOperationsApi.getUserPublicKeyByForeignKey_, new { foreignKey });

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKeyByIdentity(string identity) =>
        Process<LockOperationsApi, UserPublicKeyResponse>(lockOperations, lockOperationsApi.getUserPublicKeyByIdentity_, new { identity });

    public unsafe Task<List<BatchUserPublicKeyResponse>> GetUserPublicKeyByEmails(List<string> emails) =>
        Process<LockOperationsApi, List<BatchUserPublicKeyResponse>>(lockOperations, lockOperationsApi.getUserPublicKeyByEmails_, new { emails });

    public unsafe Task<List<BatchUserPublicKeyResponse>> GetUserPublicKeyByTelephones(List<string> telephones) =>
        Process<LockOperationsApi, List<BatchUserPublicKeyResponse>>(lockOperations, lockOperationsApi.getUserPublicKeyByTelephones_, new { telephones });

    public unsafe Task<List<BatchUserPublicKeyResponse>> GetUserPublicKeyByLocalKeys(List<string> localKeys) =>
        Process<LockOperationsApi, List<BatchUserPublicKeyResponse>>(lockOperations, lockOperationsApi.getUserPublicKeyByLocalKeys_, new { localKeys });

    public unsafe Task<List<BatchUserPublicKeyResponse>> GetUserPublicKeyByForeignKeys(List<string> foreignKeys) =>
        Process<LockOperationsApi, List<BatchUserPublicKeyResponse>>(lockOperations, lockOperationsApi.getUserPublicKeyByForeignKeys_, new { foreignKeys });
    
    public unsafe Task<object> Unlock(UnlockOperation data) =>
        Process<LockOperationsApi, object>(lockOperations, lockOperationsApi.unlock_, data);

    public unsafe Task<object> ShareLock(ShareLockOperation data) =>
        Process<LockOperationsApi, object>(lockOperations, lockOperationsApi.shareLock_, data);

    public unsafe Task<object> BatchShareLock(BatchShareLockOperation data) =>
        Process<LockOperationsApi, object>(lockOperations, lockOperationsApi.batchShareLock_, data);

    public unsafe Task<object> RevokeAccessToLock(RevokeAccessToLockOperation data) =>
        Process<LockOperationsApi, object>(lockOperations, lockOperationsApi.revokeAccessToLock_, data);

    public unsafe Task<object> UpdateSecureSettingUnlockDuration(UpdateSecureSettingUnlockDuration data) =>
        Process<LockOperationsApi, object>(lockOperations, lockOperationsApi.updateSecureSettingUnlockDuration_, data);

    public unsafe Task<object> UpdateSecureSettingUnlockBetween(UpdateSecureSettingUnlockBetween data) =>
        Process<LockOperationsApi, object>(lockOperations, lockOperationsApi.updateSecureSettingUnlockBetween_, data);

    public unsafe Task<List<LockResponse>> GetPinnedLocks() =>
        Process<LockOperationsApi, List<LockResponse>>(lockOperations, lockOperationsApi.getPinnedLocks_);

    public unsafe Task<List<ShareableLockResponse>> GetShareableLocks() =>
        Process<LockOperationsApi, List<ShareableLockResponse>>(lockOperations, lockOperationsApi.getShareableLocks_);
}