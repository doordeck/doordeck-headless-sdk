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
        return ProcessLockOperationsResource<LockResponse>(
            _lockOperationsResource.getSingleLockJson,
            null,
            null,
            data
        );
    }

    public List<AuditResponse> GetLockAuditTrail(GetLockAuditTrailData data)
    {
        return ProcessLockOperationsResource<List<AuditResponse>>(
            _lockOperationsResource.getLockAuditTrailJson,
            null,
            null,
            data
        );
    }

    public List<AuditResponse> GetAuditForUser(GetAuditForUserData data)
    {
        return ProcessLockOperationsResource<List<AuditResponse>>(
            _lockOperationsResource.getAuditForUserJson,
            null,
            null,
            data
        );
    }

    public List<UserLockResponse> GetUsersForLock(GetUsersForLockData data)
    {
        return ProcessLockOperationsResource<List<UserLockResponse>>(
            _lockOperationsResource.getUsersForLockJson,
            null,
            null,
            data
        );
    }

    public LockUserResponse GetLocksForUser(GetLocksForUserData data)
    {
        return ProcessLockOperationsResource<LockUserResponse>(
            _lockOperationsResource.getLocksForUserJson,
            null,
            null,
            data
        );
    }

    public void UpdateLockName(UpdateLockNameData data)
    {
        ProcessLockOperationsResource<object>(
            null,
            _lockOperationsResource.updateLockNameJson,
            null,
            data
        );
    }

    public void UpdateLockFavourite(UpdateLockFavouriteData data)
    {
        ProcessLockOperationsResource<object>(
            null,
            _lockOperationsResource.updateLockFavouriteJson,
            null,
            data
        );
    }

    public void UpdateLockColour(UpdateLockColourData data)
    {
        ProcessLockOperationsResource<object>(
            null,
            _lockOperationsResource.updateLockColourJson,
            null,
            data
        );
    }

    public void UpdateLockSettingDefaultName(UpdateLockSettingDefaultNameData data)
    {
        ProcessLockOperationsResource<object>(
            null,
            _lockOperationsResource.updateLockSettingDefaultNameJson,
            null,
            data
        );
    }

    public void SetLockSettingPermittedAddresses(SetLockSettingPermittedAddressesData data)
    {
        ProcessLockOperationsResource<object>(
            null,
            _lockOperationsResource.setLockSettingPermittedAddressesJson,
            null,
            data
        );
    }

    public void UpdateLockSettingHidden(UpdateLockSettingHiddenData data)
    {
        ProcessLockOperationsResource<object>(
            null,
            _lockOperationsResource.updateLockSettingHiddenJson,
            null,
            data
        );
    }

    public void SetLockSettingTimeRestrictions(SetLockSettingTimeRestrictionsData data)
    {
        ProcessLockOperationsResource<object>(
            null,
            _lockOperationsResource.setLockSettingTimeRestrictionsJson,
            null,
            data
        );
    }

    public void UpdateLockSettingLocationRestrictions(UpdateLockSettingLocationRestrictionsData data)
    {
        ProcessLockOperationsResource<object>(
            null,
            _lockOperationsResource.updateLockSettingLocationRestrictionsJson,
            null,
            data
        );
    }

    public UserPublicKeyResponse GetUserPublicKey(GetUserPublicKeyData data)
    {
        return ProcessLockOperationsResource<UserPublicKeyResponse>(
            _lockOperationsResource.getUserPublicKeyJson,
            null,
            null,
            data
        );
    }

    public UserPublicKeyResponse GetUserPublicKeyByEmail(GetUserPublicKeyByEmailData data)
    {
        return ProcessLockOperationsResource<UserPublicKeyResponse>(
            _lockOperationsResource.getUserPublicKeyByEmailJson,
            null,
            null,
            data
        );
    }

    public UserPublicKeyResponse GetUserPublicKeyByTelephone(GetUserPublicKeyByTelephoneData data)
    {
        return ProcessLockOperationsResource<UserPublicKeyResponse>(
            _lockOperationsResource.getUserPublicKeyByTelephoneJson,
            null,
            null,
            data
        );
    }

    public UserPublicKeyResponse GetUserPublicKeyByLocalKey(GetUserPublicKeyByLocalKeyData data)
    {
        return ProcessLockOperationsResource<UserPublicKeyResponse>(
            _lockOperationsResource.getUserPublicKeyByLocalKeyJson,
            null,
            null,
            data
        );
    }

    public UserPublicKeyResponse GetUserPublicKeyByForeignKey(GetUserPublicKeyByForeignKeyData data)
    {
        return ProcessLockOperationsResource<UserPublicKeyResponse>(
            _lockOperationsResource.getUserPublicKeyByForeignKeyJson,
            null,
            null,
            data
        );
    }

    public UserPublicKeyResponse GetUserPublicKeyByIdentity(GetUserPublicKeyByIdentityData data)
    {
        return ProcessLockOperationsResource<UserPublicKeyResponse>(
            _lockOperationsResource.getUserPublicKeyByIdentityJson,
            null,
            null,
            data
        );
    }

    public List<BatchUserPublicKeyResponse> GetUserPublicKeyByEmails(GetUserPublicKeyByEmailsData data)
    {
        return ProcessLockOperationsResource<List<BatchUserPublicKeyResponse>>(
            _lockOperationsResource.getUserPublicKeyByEmailsJson,
            null,
            null,
            data
        );
    }

    public List<BatchUserPublicKeyResponse> GetUserPublicKeyByTelephones(GetUserPublicKeyByTelephonesData data)
    {
        return ProcessLockOperationsResource<List<BatchUserPublicKeyResponse>>(
            _lockOperationsResource.getUserPublicKeyByTelephonesJson,
            null,
            null,
            data
        );
    }

    public List<BatchUserPublicKeyResponse> GetUserPublicKeyByLocalKeys(GetUserPublicKeyByLocalKeysData data)
    {
        return ProcessLockOperationsResource<List<BatchUserPublicKeyResponse>>(
            _lockOperationsResource.getUserPublicKeyByLocalKeysJson,
            null,
            null,
            data
        );
    }

    public List<BatchUserPublicKeyResponse> GetUserPublicKeyByForeignKeys(GetUserPublicKeyByForeignKeysData data)
    {
        return ProcessLockOperationsResource<List<BatchUserPublicKeyResponse>>(
            _lockOperationsResource.getUserPublicKeyByForeignKeysJson,
            null,
            null,
            data
        );
    }

    public void Unlock(UnlockOperationData data)
    {
        ProcessLockOperationsResource<object>(
            null,
            _lockOperationsResource.unlockJson,
            null,
            data
        );
    }

    public void ShareLock(ShareLockOperationData data)
    {
        ProcessLockOperationsResource<object>(
            null,
            _lockOperationsResource.shareLockJson,
            null,
            data
        );
    }

    public void RevokeAccessToLock(RevokeAccessToLockOperationData data)
    {
        ProcessLockOperationsResource<object>(
            null,
            _lockOperationsResource.revokeAccessToLockJson,
            null,
            data
        );
    }

    public void UpdateSecureSettingUnlockDuration(UpdateSecureSettingUnlockDurationData data)
    {
        ProcessLockOperationsResource<object>(
            null,
            _lockOperationsResource.updateSecureSettingUnlockDurationJson,
            null,
            data
        );
    }

    public void UpdateSecureSettingUnlockBetween(UpdateSecureSettingUnlockBetweenData data)
    {
        ProcessLockOperationsResource<object>(
            null,
            _lockOperationsResource.updateSecureSettingUnlockBetweenJson,
            null,
            data
        );
    }

    public List<LockResponse> GetPinnedLocks()
    {
        return ProcessLockOperationsResource<List<LockResponse>>(
            null,
            null,
            _lockOperationsResource.getPinnedLocksJson,
            null
        );
    }

    public List<ShareableLockResponse> GetShareableLocks()
    {
        return ProcessLockOperationsResource<List<ShareableLockResponse>>(
            null,
            null,
            _lockOperationsResource.getShareableLocksJson,
            null
        );
    }

    private TResponse ProcessLockOperationsResource<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_LockOperationsResource,
            sbyte*, sbyte*> withDataAndWithResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_LockOperationsResource,
            sbyte*, void> withDataAndWithoutResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_LockOperationsResource,
            sbyte*> withoutDataAndWithResponse,
        object? data
    )
    {
        var sData = data != null ? data.ToData() : null;
        sbyte* result = null;
        try
        {
            var withResponse = typeof(TResponse) != typeof(object);
            var withData = data != null;

            if (withData && withResponse)
                result = withDataAndWithResponse(_lockOperations, sData);
            else if (withData && !withResponse)
                withDataAndWithoutResponse(_lockOperations, sData);
            else if (!withData && withResponse)
                result = withoutDataAndWithResponse(_lockOperations);
            return result != null ? Utils.Utils.FromData<TResponse>(result)! : default!;
        }
        finally
        {
            if (data != null) Marshal.FreeHGlobal((IntPtr)sData);

            if (result != null) _symbols->DisposeString(result);
        }
    }
}