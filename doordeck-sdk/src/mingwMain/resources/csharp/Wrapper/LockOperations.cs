using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Callback;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

public class LockOperations(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_LockOperationsApi lockOperations,
    Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._LockOperationsApi_e__Struct lockOperationsApi)
{
    public unsafe Task<LockResponse> GetSingleLock(LockIdData data)
    {
        return Process<LockResponse>(lockOperationsApi.getSingleLock_, null, data);
    }

    public unsafe Task<List<AuditResponse>> GetLockAuditTrail(GetLockAuditTrailData data)
    {
        return Process<List<AuditResponse>>(lockOperationsApi.getLockAuditTrail_, null, data);
    }

    public unsafe Task<List<AuditResponse>> GetAuditForUser(GetAuditForUserData data)
    {
        return Process<List<AuditResponse>>(lockOperationsApi.getAuditForUser_, null, data);
    }

    public unsafe Task<List<UserLockResponse>> GetUsersForLock(LockIdData data)
    {
        return Process<List<UserLockResponse>>(lockOperationsApi.getUsersForLock_, null, data);
    }

    public unsafe Task<LockUserResponse> GetLocksForUser(GetLocksForUserData data)
    {
        return Process<LockUserResponse>(lockOperationsApi.getLocksForUser_, null, data);
    }

    public unsafe Task<object> UpdateLockName(UpdateLockNameData data)
    {
        return Process<object>(lockOperationsApi.updateLockName_, null, data);
    }

    public unsafe Task<object> UpdateLockFavourite(UpdateLockFavouriteData data)
    {
        return Process<object>(lockOperationsApi.updateLockFavourite_, null, data);
    }

    public unsafe Task<object> UpdateLockColour(UpdateLockColourData data)
    {
        return Process<object>(lockOperationsApi.updateLockColour_, null, data);
    }

    public unsafe Task<object> UpdateLockSettingDefaultName(UpdateLockSettingDefaultNameData data)
    {
        return Process<object>(lockOperationsApi.updateLockSettingDefaultName_, null, data);
    }

    public unsafe Task<object> SetLockSettingPermittedAddresses(SetLockSettingPermittedAddressesData data)
    {
        return Process<object>(lockOperationsApi.setLockSettingPermittedAddresses_, null, data);
    }

    public unsafe Task<object> UpdateLockSettingHidden(UpdateLockSettingHiddenData data)
    {
        return Process<object>(lockOperationsApi.updateLockSettingHidden_, null, data);
    }

    public unsafe Task<object> SetLockSettingTimeRestrictions(SetLockSettingTimeRestrictionsData data)
    {
        return Process<object>(lockOperationsApi.setLockSettingTimeRestrictions_, null, data);
    }

    public unsafe Task<object> UpdateLockSettingLocationRestrictions(UpdateLockSettingLocationRestrictionsData data)
    {
        return Process<object>(lockOperationsApi.updateLockSettingLocationRestrictions_, null, data);
    }

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKey(GetUserPublicKeyData data)
    {
        return Process<UserPublicKeyResponse>(lockOperationsApi.getUserPublicKey_, null, data);
    }

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKeyByEmail(GetUserPublicKeyByEmailData data)
    {
        return Process<UserPublicKeyResponse>(lockOperationsApi.getUserPublicKeyByEmail_, null, data);
    }

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKeyByTelephone(GetUserPublicKeyByTelephoneData data)
    {
        return Process<UserPublicKeyResponse>(lockOperationsApi.getUserPublicKeyByTelephone_, null, data);
    }

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKeyByLocalKey(GetUserPublicKeyByLocalKeyData data)
    {
        return Process<UserPublicKeyResponse>(lockOperationsApi.getUserPublicKeyByLocalKey_, null, data);
    }

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKeyByForeignKey(GetUserPublicKeyByForeignKeyData data)
    {
        return Process<UserPublicKeyResponse>(lockOperationsApi.getUserPublicKeyByForeignKey_, null, data);
    }

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKeyByIdentity(GetUserPublicKeyByIdentityData data)
    {
        return Process<UserPublicKeyResponse>(lockOperationsApi.getUserPublicKeyByIdentity_, null, data);
    }

    public unsafe Task<List<BatchUserPublicKeyResponse>> GetUserPublicKeyByEmails(GetUserPublicKeyByEmailsData data)
    {
        return Process<List<BatchUserPublicKeyResponse>>(lockOperationsApi.getUserPublicKeyByEmails_, null, data);
    }

    public unsafe Task<List<BatchUserPublicKeyResponse>> GetUserPublicKeyByTelephones(GetUserPublicKeyByTelephonesData data)
    {
        return Process<List<BatchUserPublicKeyResponse>>(lockOperationsApi.getUserPublicKeyByTelephones_, null, data);
    }

    public unsafe Task<List<BatchUserPublicKeyResponse>> GetUserPublicKeyByLocalKeys(GetUserPublicKeyByLocalKeysData data)
    {
        return Process<List<BatchUserPublicKeyResponse>>(lockOperationsApi.getUserPublicKeyByLocalKeys_, null, data);
    }

    public unsafe Task<List<BatchUserPublicKeyResponse>> GetUserPublicKeyByForeignKeys(GetUserPublicKeyByForeignKeysData data)
    {
        return Process<List<BatchUserPublicKeyResponse>>(lockOperationsApi.getUserPublicKeyByForeignKeys_, null, data);
    }

    public unsafe Task<object> Unlock(UnlockOperationData data)
    {
        return Process<object>(lockOperationsApi.unlock_, null, data);
    }

    public unsafe Task<object> ShareLock(ShareLockOperationData data)
    {
        return Process<object>(lockOperationsApi.shareLock_, null, data);
    }

    public unsafe Task<object> BatchShareLock(BatchShareLockOperationData data)
    {
        return Process<object>(lockOperationsApi.batchShareLock_, null, data);
    }

    public unsafe Task<object> RevokeAccessToLock(RevokeAccessToLockOperationData data)
    {
        return Process<object>(lockOperationsApi.revokeAccessToLock_, null, data);
    }

    public unsafe Task<object> UpdateSecureSettingUnlockDuration(UpdateSecureSettingUnlockDurationData data)
    {
        return Process<object>(lockOperationsApi.updateSecureSettingUnlockDuration_, null, data);
    }

    public unsafe Task<object> UpdateSecureSettingUnlockBetween(UpdateSecureSettingUnlockBetweenData data)
    {
        return Process<object>(lockOperationsApi.updateSecureSettingUnlockBetween_, null, data);
    }

    public unsafe Task<List<LockResponse>> GetPinnedLocks()
    {
        return Process<List<LockResponse>>(null, lockOperationsApi.getPinnedLocks_, null);
    }

    public unsafe Task<List<ShareableLockResponse>> GetShareableLocks()
    {
        return Process<List<ShareableLockResponse>>(null, lockOperationsApi.getShareableLocks_, null);
    }

    private unsafe Task<TResponse> Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_LockOperationsApi,
            sbyte*, void*, void> processWithData,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_LockOperationsApi,
            void*, void> processWithoutData,
        object? data
    )
    {
        var tcs = new TaskCompletionSource<TResponse>();
        var sData = data != null ? data.ToData() : null;
        try
        {
            var holder = new CallbackHolder<TResponse>(tcs);
            var callbackPointer = Marshal.GetFunctionPointerForDelegate(holder.CallbackDelegate);
            if (data != null)
            {
                processWithData(lockOperations, sData, callbackPointer.ToPointer());
            }
            else
            {
                processWithoutData(lockOperations, callbackPointer.ToPointer());
            }
        }
        finally
        {
            if (data != null) Marshal.FreeHGlobal((IntPtr)sData);
        }

        return tcs.Task;
    }
}