using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;

public class LockOperations(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_LockOperationsApi lockOperations,
    Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._LockOperationsApi_e__Struct lockOperationsApi) : AbstractWrapper
{
    public unsafe Task<LockResponse> GetSingleLock(LockIdData data) =>
        Process<LockResponse>(lockOperationsApi.getSingleLock_, null, data);

    public unsafe Task<List<AuditResponse>> GetLockAuditTrail(GetLockAuditTrailData data) =>
        Process<List<AuditResponse>>(lockOperationsApi.getLockAuditTrail_, null, data);

    public unsafe Task<List<AuditResponse>> GetAuditForUser(GetAuditForUserData data) =>
        Process<List<AuditResponse>>(lockOperationsApi.getAuditForUser_, null, data);

    public unsafe Task<List<UserLockResponse>> GetUsersForLock(LockIdData data) =>
        Process<List<UserLockResponse>>(lockOperationsApi.getUsersForLock_, null, data);

    public unsafe Task<LockUserResponse> GetLocksForUser(GetLocksForUserData data) =>
        Process<LockUserResponse>(lockOperationsApi.getLocksForUser_, null, data);

    public unsafe Task<object> UpdateLockName(UpdateLockNameData data) =>
        Process<object>(lockOperationsApi.updateLockName_, null, data);

    public unsafe Task<object> UpdateLockFavourite(UpdateLockFavouriteData data) =>
        Process<object>(lockOperationsApi.updateLockFavourite_, null, data);

    public unsafe Task<object> UpdateLockColour(UpdateLockColourData data) =>
        Process<object>(lockOperationsApi.updateLockColour_, null, data);

    public unsafe Task<object> UpdateLockSettingDefaultName(UpdateLockSettingDefaultNameData data) =>
        Process<object>(lockOperationsApi.updateLockSettingDefaultName_, null, data);

    public unsafe Task<object> SetLockSettingPermittedAddresses(SetLockSettingPermittedAddressesData data) =>
        Process<object>(lockOperationsApi.setLockSettingPermittedAddresses_, null, data);

    public unsafe Task<object> UpdateLockSettingHidden(UpdateLockSettingHiddenData data) =>
        Process<object>(lockOperationsApi.updateLockSettingHidden_, null, data);

    public unsafe Task<object> SetLockSettingTimeRestrictions(SetLockSettingTimeRestrictionsData data) =>
        Process<object>(lockOperationsApi.setLockSettingTimeRestrictions_, null, data);

    public unsafe Task<object> UpdateLockSettingLocationRestrictions(UpdateLockSettingLocationRestrictionsData data) =>
        Process<object>(lockOperationsApi.updateLockSettingLocationRestrictions_, null, data);

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKey(GetUserPublicKeyData data) =>
        Process<UserPublicKeyResponse>(lockOperationsApi.getUserPublicKey_, null, data);

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKeyByEmail(GetUserPublicKeyByEmailData data) =>
        Process<UserPublicKeyResponse>(lockOperationsApi.getUserPublicKeyByEmail_, null, data);

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKeyByTelephone(GetUserPublicKeyByTelephoneData data) =>
        Process<UserPublicKeyResponse>(lockOperationsApi.getUserPublicKeyByTelephone_, null, data);

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKeyByLocalKey(GetUserPublicKeyByLocalKeyData data) =>
        Process<UserPublicKeyResponse>(lockOperationsApi.getUserPublicKeyByLocalKey_, null, data);

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKeyByForeignKey(GetUserPublicKeyByForeignKeyData data) =>
        Process<UserPublicKeyResponse>(lockOperationsApi.getUserPublicKeyByForeignKey_, null, data);

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKeyByIdentity(GetUserPublicKeyByIdentityData data) =>
        Process<UserPublicKeyResponse>(lockOperationsApi.getUserPublicKeyByIdentity_, null, data);

    public unsafe Task<List<BatchUserPublicKeyResponse>> GetUserPublicKeyByEmails(GetUserPublicKeyByEmailsData data) =>
        Process<List<BatchUserPublicKeyResponse>>(lockOperationsApi.getUserPublicKeyByEmails_, null, data);

    public unsafe Task<List<BatchUserPublicKeyResponse>> GetUserPublicKeyByTelephones(GetUserPublicKeyByTelephonesData data) =>
        Process<List<BatchUserPublicKeyResponse>>(lockOperationsApi.getUserPublicKeyByTelephones_, null, data);

    public unsafe Task<List<BatchUserPublicKeyResponse>> GetUserPublicKeyByLocalKeys(GetUserPublicKeyByLocalKeysData data) =>
        Process<List<BatchUserPublicKeyResponse>>(lockOperationsApi.getUserPublicKeyByLocalKeys_, null, data);

    public unsafe Task<List<BatchUserPublicKeyResponse>> GetUserPublicKeyByForeignKeys(GetUserPublicKeyByForeignKeysData data) =>
        Process<List<BatchUserPublicKeyResponse>>(lockOperationsApi.getUserPublicKeyByForeignKeys_, null, data);
    
    public unsafe Task<object> Unlock(UnlockOperationData data) =>
        Process<object>(lockOperationsApi.unlock_, null, data);

    public unsafe Task<object> ShareLock(ShareLockOperationData data) =>
        Process<object>(lockOperationsApi.shareLock_, null, data);

    public unsafe Task<object> BatchShareLock(BatchShareLockOperationData data) =>
        Process<object>(lockOperationsApi.batchShareLock_, null, data);

    public unsafe Task<object> RevokeAccessToLock(RevokeAccessToLockOperationData data) =>
        Process<object>(lockOperationsApi.revokeAccessToLock_, null, data);

    public unsafe Task<object> UpdateSecureSettingUnlockDuration(UpdateSecureSettingUnlockDurationData data) =>
        Process<object>(lockOperationsApi.updateSecureSettingUnlockDuration_, null, data);

    public unsafe Task<object> UpdateSecureSettingUnlockBetween(UpdateSecureSettingUnlockBetweenData data) =>
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