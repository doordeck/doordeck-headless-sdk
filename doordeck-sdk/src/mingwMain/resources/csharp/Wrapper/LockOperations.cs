using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;

public class LockOperations(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_LockOperationsApi lockOperations,
    Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._LockOperationsApi_e__Struct lockOperationsApi) : AbstractWrapper
{
    public unsafe Task<LockResponse> GetSingleLock(string lockId) =>
        Process<LockResponse>(lockOperationsApi.getSingleLock_, null, new { lockId });

    public unsafe Task<List<AuditResponse>> GetLockAuditTrail(string lockId, long? start = null, long? end = null) =>
        Process<List<AuditResponse>>(lockOperationsApi.getLockAuditTrail_, null, new { lockId, start, end });

    public unsafe Task<List<AuditResponse>> GetAuditForUser(string userId, long? start = null, long? end = null) =>
        Process<List<AuditResponse>>(lockOperationsApi.getAuditForUser_, null, new { userId, start, end });

    public unsafe Task<List<UserLockResponse>> GetUsersForLock(string lockId) =>
        Process<List<UserLockResponse>>(lockOperationsApi.getUsersForLock_, null, new { lockId });

    public unsafe Task<LockUserResponse> GetLocksForUser(string userId) =>
        Process<LockUserResponse>(lockOperationsApi.getLocksForUser_, null, new { userId });

    public unsafe Task<object> UpdateLockName(string lockId, string? name = null) =>
        Process<object>(lockOperationsApi.updateLockName_, null, new { lockId, name });

    public unsafe Task<object> UpdateLockFavourite(string lockId, bool favourite) =>
        Process<object>(lockOperationsApi.updateLockFavourite_, null, new { lockId, favourite });

    public unsafe Task<object> UpdateLockSettingDefaultName(string lockId, string name) =>
        Process<object>(lockOperationsApi.updateLockSettingDefaultName_, null, new { lockId, name });

    public unsafe Task<object> SetLockSettingPermittedAddresses(string lockId, List<string> permittedAddresses) =>
        Process<object>(lockOperationsApi.setLockSettingPermittedAddresses_, null, new { lockId,  permittedAddresses });

    public unsafe Task<object> UpdateLockSettingHidden(string lockId, bool hidden) =>
        Process<object>(lockOperationsApi.updateLockSettingHidden_, null, new { lockId, hidden });

    public unsafe Task<object> SetLockSettingTimeRestrictions(string lockId, List<TimeRequirement> times) =>
        Process<object>(lockOperationsApi.setLockSettingTimeRestrictions_, null, new { lockId, times });

    public unsafe Task<object> UpdateLockSettingLocationRestrictions(string lockId, LocationRequirement? location = null) =>
        Process<object>(lockOperationsApi.updateLockSettingLocationRestrictions_, null, new { lockId, location });

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKey(string userEmail, bool visitor = false) =>
        Process<UserPublicKeyResponse>(lockOperationsApi.getUserPublicKey_, null, new { userEmail, visitor });

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKeyByEmail(string email) =>
        Process<UserPublicKeyResponse>(lockOperationsApi.getUserPublicKeyByEmail_, null, new { email });

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKeyByTelephone(string telephone) =>
        Process<UserPublicKeyResponse>(lockOperationsApi.getUserPublicKeyByTelephone_, null, new { telephone });

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKeyByLocalKey(string localKey) =>
        Process<UserPublicKeyResponse>(lockOperationsApi.getUserPublicKeyByLocalKey_, null, new { localKey });

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKeyByForeignKey(string foreignKey) =>
        Process<UserPublicKeyResponse>(lockOperationsApi.getUserPublicKeyByForeignKey_, null, new { foreignKey });

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKeyByIdentity(string identity) =>
        Process<UserPublicKeyResponse>(lockOperationsApi.getUserPublicKeyByIdentity_, null, new { identity });

    public unsafe Task<List<BatchUserPublicKeyResponse>> GetUserPublicKeyByEmails(List<string> emails) =>
        Process<List<BatchUserPublicKeyResponse>>(lockOperationsApi.getUserPublicKeyByEmails_, null, new { emails });

    public unsafe Task<List<BatchUserPublicKeyResponse>> GetUserPublicKeyByTelephones(List<string> telephones) =>
        Process<List<BatchUserPublicKeyResponse>>(lockOperationsApi.getUserPublicKeyByTelephones_, null, new { telephones });

    public unsafe Task<List<BatchUserPublicKeyResponse>> GetUserPublicKeyByLocalKeys(List<string> localKeys) =>
        Process<List<BatchUserPublicKeyResponse>>(lockOperationsApi.getUserPublicKeyByLocalKeys_, null, new { localKeys });

    public unsafe Task<List<BatchUserPublicKeyResponse>> GetUserPublicKeyByForeignKeys(List<string> foreignKeys) =>
        Process<List<BatchUserPublicKeyResponse>>(lockOperationsApi.getUserPublicKeyByForeignKeys_, null, new { foreignKeys });
    
    public unsafe Task<object> Unlock(UnlockOperation data) =>
        Process<object>(lockOperationsApi.unlock_, null, data);

    public unsafe Task<object> ShareLock(ShareLockOperation data) =>
        Process<object>(lockOperationsApi.shareLock_, null, data);

    public unsafe Task<object> BatchShareLock(BatchShareLockOperation data) =>
        Process<object>(lockOperationsApi.batchShareLock_, null, data);

    public unsafe Task<object> RevokeAccessToLock(RevokeAccessToLockOperation data) =>
        Process<object>(lockOperationsApi.revokeAccessToLock_, null, data);

    public unsafe Task<object> UpdateSecureSettingUnlockDuration(UpdateSecureSettingUnlockDuration data) =>
        Process<object>(lockOperationsApi.updateSecureSettingUnlockDuration_, null, data);

    public unsafe Task<object> UpdateSecureSettingUnlockBetween(UpdateSecureSettingUnlockBetween data) =>
        Process<object>(lockOperationsApi.updateSecureSettingUnlockBetween_, null, data);

    public unsafe Task<List<LockResponse>> GetPinnedLocks() =>
        Process<List<LockResponse>>(null, lockOperationsApi.getPinnedLocks_, null);

    public unsafe Task<List<ShareableLockResponse>> GetShareableLocks() =>
        Process<List<ShareableLockResponse>>(null, lockOperationsApi.getShareableLocks_, null);

    private unsafe Task<TResponse> Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_LockOperationsApi,
            sbyte*, void*, void> processWithData,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_LockOperationsApi,
            void*, void> processWithoutData,
        object? data) =>
        ProcessCommon<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_LockOperationsApi, TResponse>(
            lockOperations,
            data,
            processWithData,
            processWithoutData);
}