using System.Net;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;


public class LockOperations
{
    public Task<LockResponse> GetSingleLock(Guid lockId) =>
        Dispatcher.Call<LockResponse>("lockOperations.getSingleLock", new { lockId });

    public Task<List<AuditResponse>> GetLockAuditTrail(Guid lockId, DateTime? start = null, DateTime? end = null) =>
        Dispatcher.Call<List<AuditResponse>>("lockOperations.getLockAuditTrail", new { lockId, start, end });

    public Task<List<AuditResponse>> GetAuditForUser(Guid userId, DateTime? start = null, DateTime? end = null) =>
        Dispatcher.Call<List<AuditResponse>>("lockOperations.getAuditForUser", new { userId, start, end });

    public Task<List<UserLockResponse>> GetUsersForLock(Guid lockId) =>
        Dispatcher.Call<List<UserLockResponse>>("lockOperations.getUsersForLock", new { lockId });

    public Task<LockUserResponse> GetLocksForUser(Guid userId) =>
        Dispatcher.Call<LockUserResponse>("lockOperations.getLocksForUser", new { userId });

    public Task<object> UpdateLockName(Guid lockId, string? name = null) =>
        Dispatcher.Call<object>("lockOperations.updateLockName", new { lockId, name });

    public Task<object> UpdateLockFavourite(Guid lockId, bool favourite) =>
        Dispatcher.Call<object>("lockOperations.updateLockFavourite", new { lockId, favourite });

    public Task<object> UpdateLockSettingDefaultName(Guid lockId, string name) =>
        Dispatcher.Call<object>("lockOperations.updateLockSettingDefaultName", new { lockId, name });

    public Task<object> SetLockSettingPermittedAddresses(Guid lockId, List<IPAddress> permittedAddresses) =>
        Dispatcher.Call<object>("lockOperations.setLockSettingPermittedAddresses", new { lockId,  permittedAddresses });

    public Task<object> UpdateLockSettingHidden(Guid lockId, bool hidden) =>
        Dispatcher.Call<object>("lockOperations.updateLockSettingHidden", new { lockId, hidden });

    public Task<object> SetLockSettingTimeRestrictions(Guid lockId, List<TimeRequirement> times) =>
        Dispatcher.Call<object>("lockOperations.setLockSettingTimeRestrictions", new { lockId, times });

    public Task<object> UpdateLockSettingLocationRestrictions(Guid lockId, LocationRequirement? location = null) =>
        Dispatcher.Call<object>("lockOperations.updateLockSettingLocationRestrictions", new { lockId, location });

    public Task<UserPublicKeyResponse> GetUserPublicKey(string userEmail, bool visitor = false) =>
        Dispatcher.Call<UserPublicKeyResponse>("lockOperations.getUserPublicKey", new { userEmail, visitor });

    public Task<UserPublicKeyResponse> GetUserPublicKeyByEmail(string email) =>
        Dispatcher.Call<UserPublicKeyResponse>("lockOperations.getUserPublicKeyByEmail", new { email });

    public Task<UserPublicKeyResponse> GetUserPublicKeyByTelephone(string telephone) =>
        Dispatcher.Call<UserPublicKeyResponse>("lockOperations.getUserPublicKeyByTelephone", new { telephone });

    public Task<UserPublicKeyResponse> GetUserPublicKeyByLocalKey(string localKey) =>
        Dispatcher.Call<UserPublicKeyResponse>("lockOperations.getUserPublicKeyByLocalKey", new { localKey });

    public Task<UserPublicKeyResponse> GetUserPublicKeyByForeignKey(string foreignKey) =>
        Dispatcher.Call<UserPublicKeyResponse>("lockOperations.getUserPublicKeyByForeignKey", new { foreignKey });

    public Task<UserPublicKeyResponse> GetUserPublicKeyByIdentity(string identity) =>
        Dispatcher.Call<UserPublicKeyResponse>("lockOperations.getUserPublicKeyByIdentity", new { identity });

    public Task<List<BatchUserPublicKeyResponse>> GetUserPublicKeyByEmails(List<string> emails) =>
        Dispatcher.Call<List<BatchUserPublicKeyResponse>>("lockOperations.getUserPublicKeyByEmails", new { emails });

    public Task<List<BatchUserPublicKeyResponse>> GetUserPublicKeyByTelephones(List<string> telephones) =>
        Dispatcher.Call<List<BatchUserPublicKeyResponse>>("lockOperations.getUserPublicKeyByTelephones", new { telephones });

    public Task<List<BatchUserPublicKeyResponse>> GetUserPublicKeyByLocalKeys(List<string> localKeys) =>
        Dispatcher.Call<List<BatchUserPublicKeyResponse>>("lockOperations.getUserPublicKeyByLocalKeys", new { localKeys });

    public Task<List<BatchUserPublicKeyResponse>> GetUserPublicKeyByForeignKeys(List<string> foreignKeys) =>
        Dispatcher.Call<List<BatchUserPublicKeyResponse>>("lockOperations.getUserPublicKeyByForeignKeys", new { foreignKeys });
    
    public Task<object> Unlock(UnlockOperation data) =>
        Dispatcher.Call<object>("lockOperations.unlock", data);

    public Task<object> ShareLock(ShareLockOperation data) =>
        Dispatcher.Call<object>("lockOperations.shareLock", data);

    public Task<object> BatchShareLock(BatchShareLockOperation data) =>
        Dispatcher.Call<object>("lockOperations.batchShareLock", data);

    public Task<object> RevokeAccessToLock(RevokeAccessToLockOperation data) =>
        Dispatcher.Call<object>("lockOperations.revokeAccessToLock", data);

    public Task<object> UpdateSecureSettingUnlockDuration(UpdateSecureSettingUnlockDuration data) =>
        Dispatcher.Call<object>("lockOperations.updateSecureSettingUnlockDuration", data);

    public Task<object> UpdateSecureSettingUnlockBetween(UpdateSecureSettingUnlockBetween data) =>
        Dispatcher.Call<object>("lockOperations.updateSecureSettingUnlockBetween", data);

    public Task<List<LockResponse>> GetPinnedLocks() =>
        Dispatcher.Call<List<LockResponse>>("lockOperations.getPinnedLocks");

    public Task<List<ShareableLockResponse>> GetShareableLocks() =>
        Dispatcher.Call<List<ShareableLockResponse>>("lockOperations.getShareableLocks");
}