using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

public unsafe class LockOperations : IResource
{
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_LockOperationsApi _lockOperations;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._LockOperationsApi_e__Struct
        _lockOperationsApi;

    private Doordeck_Headless_Sdk_ExportedSymbols* _symbols;

    [UnmanagedFunctionPointer(CallingConvention.Cdecl)]
    private delegate void CallbackDelegate(IntPtr r);

    void IResource.Initialize(Doordeck_Headless_Sdk_ExportedSymbols* symbols,
        Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck sdk)
    {
        _symbols = symbols;
        _lockOperations = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations_(sdk);
        _lockOperationsApi = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsApi;
    }

    void IResource.Release()
    {
        _symbols->DisposeStablePointer(_lockOperations.pinned);
    }

    public void GetSingleLock(GetSingleLockData data, Action<LockResponse> action)
    {
        Process(_lockOperationsApi.getSingleLock_, null, action, data);
    }

    public void GetLockAuditTrail(GetLockAuditTrailData data, Action<List<AuditResponse>> action)
    {
        Process(_lockOperationsApi.getLockAuditTrail_, null, action, data);
    }

    public void GetAuditForUser(GetAuditForUserData data, Action<List<AuditResponse>> action)
    {
        Process(_lockOperationsApi.getAuditForUser_, null, action, data);
    }

    public void GetUsersForLock(GetUsersForLockData data, Action<List<UserLockResponse>> action)
    {
        Process(_lockOperationsApi.getUsersForLock_, null, action, data);
    }

    public void GetLocksForUser(GetLocksForUserData data, Action<LockUserResponse> action)
    {
        Process(_lockOperationsApi.getLocksForUser_, null, action, data);
    }

    public void UpdateLockName(UpdateLockNameData data, Action<object> action)
    {
        Process(_lockOperationsApi.updateLockName_, null, action, data);
    }

    public void UpdateLockFavourite(UpdateLockFavouriteData data, Action<object> action)
    {
        Process(_lockOperationsApi.updateLockFavourite_, null, action, data);
    }

    public void UpdateLockColour(UpdateLockColourData data, Action<object> action)
    {
        Process(_lockOperationsApi.updateLockColour_, null, action, data);
    }

    public void UpdateLockSettingDefaultName(UpdateLockSettingDefaultNameData data, Action<object> action)
    {
        Process(_lockOperationsApi.updateLockSettingDefaultName_, null, action, data);
    }

    public void SetLockSettingPermittedAddresses(SetLockSettingPermittedAddressesData data, Action<object> action)
    {
        Process(_lockOperationsApi.setLockSettingPermittedAddresses_, null, action, data);
    }

    public void UpdateLockSettingHidden(UpdateLockSettingHiddenData data, Action<object> action)
    {
        Process(_lockOperationsApi.updateLockSettingHidden_, null, action, data);
    }

    public void SetLockSettingTimeRestrictions(SetLockSettingTimeRestrictionsData data, Action<object> action)
    {
        Process(_lockOperationsApi.setLockSettingTimeRestrictions_, null, action, data);
    }

    public void UpdateLockSettingLocationRestrictions(UpdateLockSettingLocationRestrictionsData data, Action<object> action)
    {
        Process(_lockOperationsApi.updateLockSettingLocationRestrictions_, null, action, data);
    }

    public void GetUserPublicKey(GetUserPublicKeyData data, Action<UserPublicKeyResponse> action)
    {
        Process(_lockOperationsApi.getUserPublicKey_, null, action, data);
    }

    public void GetUserPublicKeyByEmail(GetUserPublicKeyByEmailData data, Action<UserPublicKeyResponse> action)
    {
        Process(_lockOperationsApi.getUserPublicKeyByEmail_, null, action, data);
    }

    public void GetUserPublicKeyByTelephone(GetUserPublicKeyByTelephoneData data, Action<UserPublicKeyResponse> action)
    {
        Process(_lockOperationsApi.getUserPublicKeyByTelephone_, null, action, data);
    }

    public void GetUserPublicKeyByLocalKey(GetUserPublicKeyByLocalKeyData data, Action<UserPublicKeyResponse> action)
    {
        Process(_lockOperationsApi.getUserPublicKeyByLocalKey_, null, action, data);
    }

    public void GetUserPublicKeyByForeignKey(GetUserPublicKeyByForeignKeyData data, Action<UserPublicKeyResponse> action)
    {
        Process(_lockOperationsApi.getUserPublicKeyByForeignKey_, null, action, data);
    }

    public void GetUserPublicKeyByIdentity(GetUserPublicKeyByIdentityData data, Action<UserPublicKeyResponse> action)
    {
        Process(_lockOperationsApi.getUserPublicKeyByIdentity_, null, action, data);
    }

    public void GetUserPublicKeyByEmails(GetUserPublicKeyByEmailsData data, Action<List<BatchUserPublicKeyResponse>> action)
    {
        Process(_lockOperationsApi.getUserPublicKeyByEmails_, null, action, data);
    }

    public void GetUserPublicKeyByTelephones(GetUserPublicKeyByTelephonesData data, Action<List<BatchUserPublicKeyResponse>> action)
    {
        Process(_lockOperationsApi.getUserPublicKeyByTelephones_, null, action, data);
    }

    public void GetUserPublicKeyByLocalKeys(GetUserPublicKeyByLocalKeysData data, Action<List<BatchUserPublicKeyResponse>> action)
    {
        Process(_lockOperationsApi.getUserPublicKeyByLocalKeys_, null, action, data);
    }

    public void GetUserPublicKeyByForeignKeys(GetUserPublicKeyByForeignKeysData data, Action<List<BatchUserPublicKeyResponse>> action)
    {
        Process(_lockOperationsApi.getUserPublicKeyByForeignKeys_, null, action, data);
    }

    public void Unlock(UnlockOperationData data, Action<object> action)
    {
        Process(_lockOperationsApi.unlock_, null, action, data);
    }

    public void ShareLock(ShareLockOperationData data, Action<object> action)
    {
        Process(_lockOperationsApi.shareLock_, null, action, data);
    }

    public void BatchShareLock(BatchShareLockOperationData data, Action<object> action)
    {
        Process(_lockOperationsApi.batchShareLock_, null, action, data);
    }

    public void RevokeAccessToLock(RevokeAccessToLockOperationData data, Action<object> action)
    {
        Process(_lockOperationsApi.revokeAccessToLock_, null, action, data);
    }

    public void UpdateSecureSettingUnlockDuration(UpdateSecureSettingUnlockDurationData data, Action<object> action)
    {
        Process(_lockOperationsApi.updateSecureSettingUnlockDuration_, null, action, data);
    }

    public void UpdateSecureSettingUnlockBetween(UpdateSecureSettingUnlockBetweenData data, Action<object> action)
    {
        Process(_lockOperationsApi.updateSecureSettingUnlockBetween_, null, action, data);
    }

    public void GetPinnedLocks(Action<List<LockResponse>> action)
    {
        Process(null, _lockOperationsApi.getPinnedLocks_, action, null);
    }

    public void GetShareableLocks(Action<List<ShareableLockResponse>> action)
    {
        Process(null, _lockOperationsApi.getShareableLocks_, action, null);
    }

    private void Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_LockOperationsApi,
            sbyte*, void*, void> processWithData,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_LockOperationsApi,
            void*, void> processWithoutData,
        Action<TResponse> userCallback,
        object? data
    )
    {
        var sData = data != null ? data.ToData() : null;
        try
        {
            var holder = new CallbackHolder<TResponse>(userCallback);
            CallbackDelegate callbackDelegate = holder.Callback;
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
    }
}