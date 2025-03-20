using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

public class LockOperations : IResource
{
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_LockOperationsApi _lockOperations;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._LockOperationsApi_e__Struct
        _lockOperationsApi;

    private unsafe Doordeck_Headless_Sdk_ExportedSymbols* _symbols;

    unsafe void IResource.Initialize(Doordeck_Headless_Sdk_ExportedSymbols* symbols,
        Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck sdk)
    {
        _symbols = symbols;
        _lockOperations = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations_(sdk);
        _lockOperationsApi = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsApi;
    }

    unsafe void IResource.Release()
    {
        _symbols->DisposeStablePointer(_lockOperations.pinned);
    }

    public unsafe Task<LockResponse> GetSingleLock(GetSingleLockData data)
    {
        return Process<LockResponse>(_lockOperationsApi.getSingleLock_, null, data);
    }

    public unsafe Task<List<AuditResponse>> GetLockAuditTrail(GetLockAuditTrailData data)
    {
        return Process<List<AuditResponse>>(_lockOperationsApi.getLockAuditTrail_, null, data);
    }

    public unsafe Task<List<AuditResponse>> GetAuditForUser(GetAuditForUserData data)
    {
        return Process<List<AuditResponse>>(_lockOperationsApi.getAuditForUser_, null, data);
    }

    public unsafe Task<List<UserLockResponse>> GetUsersForLock(GetUsersForLockData data)
    {
        return Process<List<UserLockResponse>>(_lockOperationsApi.getUsersForLock_, null, data);
    }

    public unsafe Task<LockUserResponse> GetLocksForUser(GetLocksForUserData data)
    {
        return Process<LockUserResponse>(_lockOperationsApi.getLocksForUser_, null, data);
    }

    public unsafe Task<object> UpdateLockName(UpdateLockNameData data)
    {
        return Process<object>(_lockOperationsApi.updateLockName_, null, data);
    }

    public unsafe Task<object> UpdateLockFavourite(UpdateLockFavouriteData data)
    {
        return Process<object>(_lockOperationsApi.updateLockFavourite_, null, data);
    }

    public unsafe Task<object> UpdateLockColour(UpdateLockColourData data)
    {
        return Process<object>(_lockOperationsApi.updateLockColour_, null, data);
    }

    public unsafe Task<object> UpdateLockSettingDefaultName(UpdateLockSettingDefaultNameData data)
    {
        return Process<object>(_lockOperationsApi.updateLockSettingDefaultName_, null, data);
    }

    public unsafe Task<object> SetLockSettingPermittedAddresses(SetLockSettingPermittedAddressesData data)
    {
        return Process<object>(_lockOperationsApi.setLockSettingPermittedAddresses_, null, data);
    }

    public unsafe Task<object> UpdateLockSettingHidden(UpdateLockSettingHiddenData data)
    {
        return Process<object>(_lockOperationsApi.updateLockSettingHidden_, null, data);
    }

    public unsafe Task<object> SetLockSettingTimeRestrictions(SetLockSettingTimeRestrictionsData data)
    {
        return Process<object>(_lockOperationsApi.setLockSettingTimeRestrictions_, null, data);
    }

    public unsafe Task<object> UpdateLockSettingLocationRestrictions(UpdateLockSettingLocationRestrictionsData data)
    {
        return Process<object>(_lockOperationsApi.updateLockSettingLocationRestrictions_, null, data);
    }

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKey(GetUserPublicKeyData data)
    {
        return Process<UserPublicKeyResponse>(_lockOperationsApi.getUserPublicKey_, null, data);
    }

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKeyByEmail(GetUserPublicKeyByEmailData data)
    {
        return Process<UserPublicKeyResponse>(_lockOperationsApi.getUserPublicKeyByEmail_, null, data);
    }

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKeyByTelephone(GetUserPublicKeyByTelephoneData data)
    {
        return Process<UserPublicKeyResponse>(_lockOperationsApi.getUserPublicKeyByTelephone_, null, data);
    }

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKeyByLocalKey(GetUserPublicKeyByLocalKeyData data)
    {
        return Process<UserPublicKeyResponse>(_lockOperationsApi.getUserPublicKeyByLocalKey_, null, data);
    }

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKeyByForeignKey(GetUserPublicKeyByForeignKeyData data)
    {
        return Process<UserPublicKeyResponse>(_lockOperationsApi.getUserPublicKeyByForeignKey_, null, data);
    }

    public unsafe Task<UserPublicKeyResponse> GetUserPublicKeyByIdentity(GetUserPublicKeyByIdentityData data)
    {
        return Process<UserPublicKeyResponse>(_lockOperationsApi.getUserPublicKeyByIdentity_, null, data);
    }

    public unsafe Task<List<BatchUserPublicKeyResponse>> GetUserPublicKeyByEmails(GetUserPublicKeyByEmailsData data)
    {
        return Process<List<BatchUserPublicKeyResponse>>(_lockOperationsApi.getUserPublicKeyByEmails_, null, data);
    }

    public unsafe Task<List<BatchUserPublicKeyResponse>> GetUserPublicKeyByTelephones(GetUserPublicKeyByTelephonesData data)
    {
        return Process<List<BatchUserPublicKeyResponse>>(_lockOperationsApi.getUserPublicKeyByTelephones_, null, data);
    }

    public unsafe Task<List<BatchUserPublicKeyResponse>> GetUserPublicKeyByLocalKeys(GetUserPublicKeyByLocalKeysData data)
    {
        return Process<List<BatchUserPublicKeyResponse>>(_lockOperationsApi.getUserPublicKeyByLocalKeys_, null, data);
    }

    public unsafe Task<List<BatchUserPublicKeyResponse>> GetUserPublicKeyByForeignKeys(GetUserPublicKeyByForeignKeysData data)
    {
        return Process<List<BatchUserPublicKeyResponse>>(_lockOperationsApi.getUserPublicKeyByForeignKeys_, null, data);
    }

    public unsafe Task<object> Unlock(UnlockOperationData data)
    {
        return Process<object>(_lockOperationsApi.unlock_, null, data);
    }

    public unsafe Task<object> ShareLock(ShareLockOperationData data)
    {
        return Process<object>(_lockOperationsApi.shareLock_, null, data);
    }

    public unsafe Task<object> BatchShareLock(BatchShareLockOperationData data)
    {
        return Process<object>(_lockOperationsApi.batchShareLock_, null, data);
    }

    public unsafe Task<object> RevokeAccessToLock(RevokeAccessToLockOperationData data)
    {
        return Process<object>(_lockOperationsApi.revokeAccessToLock_, null, data);
    }

    public unsafe Task<object> UpdateSecureSettingUnlockDuration(UpdateSecureSettingUnlockDurationData data)
    {
        return Process<object>(_lockOperationsApi.updateSecureSettingUnlockDuration_, null, data);
    }

    public unsafe Task<object> UpdateSecureSettingUnlockBetween(UpdateSecureSettingUnlockBetweenData data)
    {
        return Process<object>(_lockOperationsApi.updateSecureSettingUnlockBetween_, null, data);
    }

    public unsafe Task<List<LockResponse>> GetPinnedLocks()
    {
        return Process<List<LockResponse>>(null, _lockOperationsApi.getPinnedLocks_, null);
    }

    public unsafe Task<List<ShareableLockResponse>> GetShareableLocks()
    {
        return Process<List<ShareableLockResponse>>(null, _lockOperationsApi.getShareableLocks_, null);
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
            var holder = new CallbackHolder<TResponse>(null, tcs);
            IResource.CallbackDelegate callbackDelegate = holder.Callback;
            var callbackPointer = Marshal.GetFunctionPointerForDelegate(callbackDelegate);
            if (data != null)
            {
                processWithData(_lockOperations, sData, callbackPointer.ToPointer());
            }
            else
            {
                processWithoutData(_lockOperations, callbackPointer.ToPointer());
            }
        }
        finally
        {
            if (data != null) Marshal.FreeHGlobal((IntPtr)sData);
        }

        return tcs.Task;
    }
}