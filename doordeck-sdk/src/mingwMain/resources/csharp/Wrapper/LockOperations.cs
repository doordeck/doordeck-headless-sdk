using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

public unsafe class LockOperations : IResource
{
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_LockOperationsResource _lockOperations;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._LockOperationsResource_e__Struct
        _lockOperationsResource;

    private Doordeck_Headless_Sdk_ExportedSymbols* _symbols;

    public void Initialize(Doordeck_Headless_Sdk_ExportedSymbols* symbols,
        Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck sdk)
    {
        _symbols = symbols;
        _lockOperations = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
        _lockOperationsResource = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource;
    }

    public void Release()
    {
        _symbols->DisposeStablePointer(_lockOperations.pinned);
    }

    public LockResponse GetSingleLock(GetSingleLockData data)
    {
        return Process<LockResponse>(
            _lockOperationsResource.getSingleLockJson,
            null,
            data
        );
    }

    public List<AuditResponse> GetLockAuditTrail(GetLockAuditTrailData data)
    {
        return Process<List<AuditResponse>>(
            _lockOperationsResource.getLockAuditTrailJson,
            null,
            data
        );
    }

    public List<AuditResponse> GetAuditForUser(GetAuditForUserData data)
    {
        return Process<List<AuditResponse>>(
            _lockOperationsResource.getAuditForUserJson,
            null,
            data
        );
    }

    public List<UserLockResponse> GetUsersForLock(GetUsersForLockData data)
    {
        return Process<List<UserLockResponse>>(
            _lockOperationsResource.getUsersForLockJson,
            null,
            data
        );
    }

    public LockUserResponse GetLocksForUser(GetLocksForUserData data)
    {
        return Process<LockUserResponse>(
            _lockOperationsResource.getLocksForUserJson,
            null,
            data
        );
    }

    public void UpdateLockName(UpdateLockNameData data)
    {
        Process<object>(
            _lockOperationsResource.updateLockNameJson,
            null,
            data
        );
    }

    public void UpdateLockFavourite(UpdateLockFavouriteData data)
    {
        Process<object>(
            _lockOperationsResource.updateLockFavouriteJson,
            null,
            data
        );
    }

    public void UpdateLockColour(UpdateLockColourData data)
    {
        Process<object>(
            _lockOperationsResource.updateLockColourJson,
            null,
            data
        );
    }

    public void UpdateLockSettingDefaultName(UpdateLockSettingDefaultNameData data)
    {
        Process<object>(
            _lockOperationsResource.updateLockSettingDefaultNameJson,
            null,
            data
        );
    }

    public void SetLockSettingPermittedAddresses(SetLockSettingPermittedAddressesData data)
    {
        Process<object>(
            _lockOperationsResource.setLockSettingPermittedAddressesJson,
            null,
            data
        );
    }

    public void UpdateLockSettingHidden(UpdateLockSettingHiddenData data)
    {
        Process<object>(
            _lockOperationsResource.updateLockSettingHiddenJson,
            null,
            data
        );
    }

    public void SetLockSettingTimeRestrictions(SetLockSettingTimeRestrictionsData data)
    {
        Process<object>(
            _lockOperationsResource.setLockSettingTimeRestrictionsJson,
            null,
            data
        );
    }

    public void UpdateLockSettingLocationRestrictions(UpdateLockSettingLocationRestrictionsData data)
    {
        Process<object>(
            _lockOperationsResource.updateLockSettingLocationRestrictionsJson,
            null,
            data
        );
    }

    public UserPublicKeyResponse GetUserPublicKey(GetUserPublicKeyData data)
    {
        return Process<UserPublicKeyResponse>(
            _lockOperationsResource.getUserPublicKeyJson,
            null,
            data
        );
    }

    public UserPublicKeyResponse GetUserPublicKeyByEmail(GetUserPublicKeyByEmailData data)
    {
        return Process<UserPublicKeyResponse>(
            _lockOperationsResource.getUserPublicKeyByEmailJson,
            null,
            data
        );
    }

    public UserPublicKeyResponse GetUserPublicKeyByTelephone(GetUserPublicKeyByTelephoneData data)
    {
        return Process<UserPublicKeyResponse>(
            _lockOperationsResource.getUserPublicKeyByTelephoneJson,
            null,
            data
        );
    }

    public UserPublicKeyResponse GetUserPublicKeyByLocalKey(GetUserPublicKeyByLocalKeyData data)
    {
        return Process<UserPublicKeyResponse>(
            _lockOperationsResource.getUserPublicKeyByLocalKeyJson,
            null,
            data
        );
    }

    public UserPublicKeyResponse GetUserPublicKeyByForeignKey(GetUserPublicKeyByForeignKeyData data)
    {
        return Process<UserPublicKeyResponse>(
            _lockOperationsResource.getUserPublicKeyByForeignKeyJson,
            null,
            data
        );
    }

    public UserPublicKeyResponse GetUserPublicKeyByIdentity(GetUserPublicKeyByIdentityData data)
    {
        return Process<UserPublicKeyResponse>(
            _lockOperationsResource.getUserPublicKeyByIdentityJson,
            null,
            data
        );
    }

    public List<BatchUserPublicKeyResponse> GetUserPublicKeyByEmails(GetUserPublicKeyByEmailsData data)
    {
        return Process<List<BatchUserPublicKeyResponse>>(
            _lockOperationsResource.getUserPublicKeyByEmailsJson,
            null,
            data
        );
    }

    public List<BatchUserPublicKeyResponse> GetUserPublicKeyByTelephones(GetUserPublicKeyByTelephonesData data)
    {
        return Process<List<BatchUserPublicKeyResponse>>(
            _lockOperationsResource.getUserPublicKeyByTelephonesJson,
            null,
            data
        );
    }

    public List<BatchUserPublicKeyResponse> GetUserPublicKeyByLocalKeys(GetUserPublicKeyByLocalKeysData data)
    {
        return Process<List<BatchUserPublicKeyResponse>>(
            _lockOperationsResource.getUserPublicKeyByLocalKeysJson,
            null,
            data
        );
    }

    public List<BatchUserPublicKeyResponse> GetUserPublicKeyByForeignKeys(GetUserPublicKeyByForeignKeysData data)
    {
        return Process<List<BatchUserPublicKeyResponse>>(
            _lockOperationsResource.getUserPublicKeyByForeignKeysJson,
            null,
            data
        );
    }

    public void Unlock(UnlockOperationData data)
    {
        Process<object>(
            _lockOperationsResource.unlockJson,
            null,
            data
        );
    }

    public void ShareLock(ShareLockOperationData data)
    {
        Process<object>(
            _lockOperationsResource.shareLockJson,
            null,
            data
        );
    }

    public void BatchShareLock(BatchShareLockOperationData data)
    {
        Process<object>(
            _lockOperationsResource.batchShareLockJson,
            null,
            data
        );
    }

    public void RevokeAccessToLock(RevokeAccessToLockOperationData data)
    {
        Process<object>(
            _lockOperationsResource.revokeAccessToLockJson,
            null,
            data
        );
    }

    public void UpdateSecureSettingUnlockDuration(UpdateSecureSettingUnlockDurationData data)
    {
        Process<object>(
            _lockOperationsResource.updateSecureSettingUnlockDurationJson,
            null,
            data
        );
    }

    public void UpdateSecureSettingUnlockBetween(UpdateSecureSettingUnlockBetweenData data)
    {
        Process<object>(
            _lockOperationsResource.updateSecureSettingUnlockBetweenJson,
            null,
            data
        );
    }

    public List<LockResponse> GetPinnedLocks()
    {
        return Process<List<LockResponse>>(
            null,
            _lockOperationsResource.getPinnedLocksJson,
            null
        );
    }

    public List<ShareableLockResponse> GetShareableLocks()
    {
        return Process<List<ShareableLockResponse>>(
            null,
            _lockOperationsResource.getShareableLocksJson,
            null
        );
    }

    private TResponse Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_LockOperationsResource,
            sbyte*, sbyte*> processDataWithResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_LockOperationsResource,
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