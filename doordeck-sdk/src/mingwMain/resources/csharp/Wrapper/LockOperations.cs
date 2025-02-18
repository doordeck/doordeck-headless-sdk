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

    public void Initialize(Doordeck_Headless_Sdk_ExportedSymbols* symbols,
        Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck sdk)
    {
        _symbols = symbols;
        _lockOperations = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations_(sdk);
        _lockOperationsApi = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsApi;
    }

    public void Release()
    {
        _symbols->DisposeStablePointer(_lockOperations.pinned);
    }

    public LockResponse GetSingleLock(GetSingleLockData data)
    {
        return Process<LockResponse>(
            _lockOperationsApi.getSingleLockJson_,
            null,
            data
        );
    }

    public List<AuditResponse> GetLockAuditTrail(GetLockAuditTrailData data)
    {
        return Process<List<AuditResponse>>(
            _lockOperationsApi.getLockAuditTrailJson_,
            null,
            data
        );
    }

    public List<AuditResponse> GetAuditForUser(GetAuditForUserData data)
    {
        return Process<List<AuditResponse>>(
            _lockOperationsApi.getAuditForUserJson_,
            null,
            data
        );
    }

    public List<UserLockResponse> GetUsersForLock(GetUsersForLockData data)
    {
        return Process<List<UserLockResponse>>(
            _lockOperationsApi.getUsersForLockJson_,
            null,
            data
        );
    }

    public LockUserResponse GetLocksForUser(GetLocksForUserData data)
    {
        return Process<LockUserResponse>(
            _lockOperationsApi.getLocksForUserJson_,
            null,
            data
        );
    }

    public void UpdateLockName(UpdateLockNameData data)
    {
        Process<object>(
            _lockOperationsApi.updateLockNameJson_,
            null,
            data
        );
    }

    public void UpdateLockFavourite(UpdateLockFavouriteData data)
    {
        Process<object>(
            _lockOperationsApi.updateLockFavouriteJson_,
            null,
            data
        );
    }

    public void UpdateLockColour(UpdateLockColourData data)
    {
        Process<object>(
            _lockOperationsApi.updateLockColourJson_,
            null,
            data
        );
    }

    public void UpdateLockSettingDefaultName(UpdateLockSettingDefaultNameData data)
    {
        Process<object>(
            _lockOperationsApi.updateLockSettingDefaultNameJson_,
            null,
            data
        );
    }

    public void SetLockSettingPermittedAddresses(SetLockSettingPermittedAddressesData data)
    {
        Process<object>(
            _lockOperationsApi.setLockSettingPermittedAddressesJson_,
            null,
            data
        );
    }

    public void UpdateLockSettingHidden(UpdateLockSettingHiddenData data)
    {
        Process<object>(
            _lockOperationsApi.updateLockSettingHiddenJson_,
            null,
            data
        );
    }

    public void SetLockSettingTimeRestrictions(SetLockSettingTimeRestrictionsData data)
    {
        Process<object>(
            _lockOperationsApi.setLockSettingTimeRestrictionsJson_,
            null,
            data
        );
    }

    public void UpdateLockSettingLocationRestrictions(UpdateLockSettingLocationRestrictionsData data)
    {
        Process<object>(
            _lockOperationsApi.updateLockSettingLocationRestrictionsJson_,
            null,
            data
        );
    }

    public UserPublicKeyResponse GetUserPublicKey(GetUserPublicKeyData data)
    {
        return Process<UserPublicKeyResponse>(
            _lockOperationsApi.getUserPublicKeyJson_,
            null,
            data
        );
    }

    public UserPublicKeyResponse GetUserPublicKeyByEmail(GetUserPublicKeyByEmailData data)
    {
        return Process<UserPublicKeyResponse>(
            _lockOperationsApi.getUserPublicKeyByEmailJson_,
            null,
            data
        );
    }

    public UserPublicKeyResponse GetUserPublicKeyByTelephone(GetUserPublicKeyByTelephoneData data)
    {
        return Process<UserPublicKeyResponse>(
            _lockOperationsApi.getUserPublicKeyByTelephoneJson_,
            null,
            data
        );
    }

    public UserPublicKeyResponse GetUserPublicKeyByLocalKey(GetUserPublicKeyByLocalKeyData data)
    {
        return Process<UserPublicKeyResponse>(
            _lockOperationsApi.getUserPublicKeyByLocalKeyJson_,
            null,
            data
        );
    }

    public UserPublicKeyResponse GetUserPublicKeyByForeignKey(GetUserPublicKeyByForeignKeyData data)
    {
        return Process<UserPublicKeyResponse>(
            _lockOperationsApi.getUserPublicKeyByForeignKeyJson_,
            null,
            data
        );
    }

    public UserPublicKeyResponse GetUserPublicKeyByIdentity(GetUserPublicKeyByIdentityData data)
    {
        return Process<UserPublicKeyResponse>(
            _lockOperationsApi.getUserPublicKeyByIdentityJson_,
            null,
            data
        );
    }

    public List<BatchUserPublicKeyResponse> GetUserPublicKeyByEmails(GetUserPublicKeyByEmailsData data)
    {
        return Process<List<BatchUserPublicKeyResponse>>(
            _lockOperationsApi.getUserPublicKeyByEmailsJson_,
            null,
            data
        );
    }

    public List<BatchUserPublicKeyResponse> GetUserPublicKeyByTelephones(GetUserPublicKeyByTelephonesData data)
    {
        return Process<List<BatchUserPublicKeyResponse>>(
            _lockOperationsApi.getUserPublicKeyByTelephonesJson_,
            null,
            data
        );
    }

    public List<BatchUserPublicKeyResponse> GetUserPublicKeyByLocalKeys(GetUserPublicKeyByLocalKeysData data)
    {
        return Process<List<BatchUserPublicKeyResponse>>(
            _lockOperationsApi.getUserPublicKeyByLocalKeysJson_,
            null,
            data
        );
    }

    public List<BatchUserPublicKeyResponse> GetUserPublicKeyByForeignKeys(GetUserPublicKeyByForeignKeysData data)
    {
        return Process<List<BatchUserPublicKeyResponse>>(
            _lockOperationsApi.getUserPublicKeyByForeignKeysJson_,
            null,
            data
        );
    }

    public void Unlock(UnlockOperationData data)
    {
        Process<object>(
            _lockOperationsApi.unlockJson_,
            null,
            data
        );
    }

    public void ShareLock(ShareLockOperationData data)
    {
        Process<object>(
            _lockOperationsApi.shareLockJson_,
            null,
            data
        );
    }

    public void BatchShareLock(BatchShareLockOperationData data)
    {
        Process<object>(
            _lockOperationsApi.batchShareLockJson_,
            null,
            data
        );
    }

    public void RevokeAccessToLock(RevokeAccessToLockOperationData data)
    {
        Process<object>(
            _lockOperationsApi.revokeAccessToLockJson_,
            null,
            data
        );
    }

    public void UpdateSecureSettingUnlockDuration(UpdateSecureSettingUnlockDurationData data)
    {
        Process<object>(
            _lockOperationsApi.updateSecureSettingUnlockDurationJson_,
            null,
            data
        );
    }

    public void UpdateSecureSettingUnlockBetween(UpdateSecureSettingUnlockBetweenData data)
    {
        Process<object>(
            _lockOperationsApi.updateSecureSettingUnlockBetweenJson_,
            null,
            data
        );
    }

    public List<LockResponse> GetPinnedLocks()
    {
        return Process<List<LockResponse>>(
            null,
            _lockOperationsApi.getPinnedLocksJson_,
            null
        );
    }

    public List<ShareableLockResponse> GetShareableLocks()
    {
        return Process<List<ShareableLockResponse>>(
            null,
            _lockOperationsApi.getShareableLocksJson_,
            null
        );
    }

    private TResponse Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_LockOperationsApi,
            sbyte*, sbyte*> processDataWithResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_LockOperationsApi,
            sbyte*> processWithoutDataWithResponse,
        object? data
    )
    {
        var sData = data != null ? data.ToData() : null;
        sbyte* result = null;
        try
        {
            var hasData = data != null;
            result = hasData ? processDataWithResponse(_lockOperations, sData) :
                processWithoutDataWithResponse(_lockOperations);

            var resultData = result != null
                ? Utils.Utils.FromData<ResultData<TResponse>>(result)
                : default!;

            resultData.HandleException();

            return resultData.Success!.Result ?? default!;
        }
        finally
        {
            if (data != null) Marshal.FreeHGlobal((IntPtr)sData);

            if (result != null) _symbols->DisposeString(result);
        }
    }
}