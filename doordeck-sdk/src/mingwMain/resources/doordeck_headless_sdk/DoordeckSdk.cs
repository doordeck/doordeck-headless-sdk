using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk;

public unsafe class DoordeckSdk(ApiEnvironment apiEnvironment)
{
    private readonly Doordeck_Headless_Sdk_ExportedSymbols* _symbols = Methods.Doordeck_Headless_Sdk_symbols();
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_KDoordeckFactory _factory;
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_model_ApiEnvironment _apiEnvironment;
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck _sdk;
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountResource _account;
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountlessResource _accountless;
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_FusionResource _fusion;
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_HelperResource _helper;
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_LockOperationsResource _lockOperations;
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_PlatformResource _platform;
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_SitesResource _sites;
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_TilesResource _tiles;
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_ContextManager _contextManager;
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_crypto_CryptoManager _cryptoManager;
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_util_Utils _utils;
    
    public void Initialize()
    {
        _apiEnvironment = apiEnvironment switch
        {
            ApiEnvironment.DEV => _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.model.ApiEnvironment.DEV
                .get(),
            ApiEnvironment.STAGING => _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.model.ApiEnvironment
                .STAGING.get(),
            ApiEnvironment.PROD => _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.model.ApiEnvironment.PROD
                .get(),
            _ => _apiEnvironment
        };

        _factory = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.KDoordeckFactory._instance();
        _sdk = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.KDoordeckFactory.initialize(_factory, _apiEnvironment);
        _account = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.account(_sdk);
        _accountless = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.accountless(_sdk);
        _fusion = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.fusion(_sdk);
        _helper = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.helper(_sdk);
        _lockOperations = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(_sdk);
        _platform = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.platform(_sdk);
        _sites = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.sites(_sdk);
        _tiles = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.tiles(_sdk);
        _contextManager = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.contextManager(_sdk);
        _cryptoManager = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.crypto(_sdk);
        _utils = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.util.Utils._instance();
    }
    
    /**
     * Accountless
     */
    public TokenResponse Login(LoginData loginData)
    {
        var data = loginData.ToData();
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountlessResource.loginJson(_accountless, data);
            return Utils.FromData<TokenResponse>(result);
        }
        finally
        {
            ReleaseCallMemory(data, result);
        }
    }

    public TokenResponse Registration(RegistrationData registrationData)
    {
        var data = registrationData.ToData();
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountlessResource.registrationJson(_accountless, data);
            return Utils.FromData<TokenResponse>(result);
        }
        finally
        {
            ReleaseCallMemory(data, result);
        }
    }

    public void VerifyEmail(VerifyEmailData verifyEmailData)
    {
        var data = verifyEmailData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountlessResource.verifyEmailJson(_accountless, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public void PasswordReset(PasswordResetData passwordResetData)
    {
        var data = passwordResetData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountlessResource.passwordResetJson(_accountless, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public void PasswordResetVerify(PasswordResetVerifyData passwordResetVerifyData)
    {
        var data = passwordResetVerifyData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountlessResource.passwordResetVerifyJson(_accountless, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    /**
     * Account
     */
    public TokenResponse RefreshToken(RefreshTokenData? refreshTokenData)
    {
        var data = refreshTokenData != null ? refreshTokenData.ToData() : null;
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource.refreshTokenJson(_account, null);
            return Utils.FromData<TokenResponse>(result);
        }
        finally
        {
            ReleaseCallMemory(data, result);
        }
    }
    
    public void Logout()
    {
        _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource.logout(_account);
    }
    
    public RegisterEphemeralKeyWithSecondaryAuthenticationResponse RegisterEphemeralKey(RegisterEphemeralKeyData? registerEphemeralKeyData)
    {
        var data = registerEphemeralKeyData != null ? registerEphemeralKeyData.ToData() : null;
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource.registerEphemeralKeyJson(_account, data);
            return Utils.FromData<RegisterEphemeralKeyWithSecondaryAuthenticationResponse>(result);
        }
        finally
        {
            ReleaseCallMemory(data, result);
        }
    }
    
    public RegisterEphemeralKeyResponse RegisterEphemeralKeyWithSecondaryAuthentication(RegisterEphemeralKeyWithSecondaryAuthenticationData? registerEphemeralKeyWithSecondaryAuthenticationData)
    {
        var data = registerEphemeralKeyWithSecondaryAuthenticationData != null ? registerEphemeralKeyWithSecondaryAuthenticationData.ToData() : null;
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource.registerEphemeralKeyWithSecondaryAuthenticationJson(_account, data);
            return Utils.FromData<RegisterEphemeralKeyResponse>(result);
        }
        finally
        {
            ReleaseCallMemory(data, result);
        }
    }

    public void VerifyEphemeralKeyRegistration(VerifyEphemeralKeyRegistrationData verifyEphemeralKeyRegistrationData)
    {
        var data = verifyEphemeralKeyRegistrationData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource.verifyEphemeralKeyRegistrationJson(_account, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public void ReverifyEmail()
    {
        _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource.reverifyEmail(_account);
    }

    public void ChangePassword(ChangePasswordData changePasswordData)
    {
        var data = changePasswordData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource.changePasswordJson(_account, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public UserDetailsResponse GetUserDetails()
    {
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource.getUserDetailsJson(_account);
            return Utils.FromData<UserDetailsResponse>(result);
        }
        finally
        {
            ReleaseCallMemory(null, result);
        }
    }

    public void UpdateUserDetails(UpdateUserDetailsData updateUserDetailsData)
    {
        var data = updateUserDetailsData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource.updateUserDetailsJson(_account, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }
    
    public void DeleteAccount()
    {
        _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource.deleteAccount(_account);
    }

    /**
     * Helper
     */
    public void UploadPlatformLogo(UploadPlatformLogoData uploadPlatformLogoData)
    {
        var data = uploadPlatformLogoData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.HelperResource.uploadPlatformLogoJson(_helper, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public AssistedLoginResponse AssistedLogin(AssistedLoginData assistedLoginData)
    {
        var data = assistedLoginData.ToData();
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.HelperResource.assistedLoginJson(_helper, data);
            return Utils.FromData<AssistedLoginResponse>(result);
        }
        finally
        {
            ReleaseCallMemory(data, result);
        }
    }

    public AssistedRegisterEphemeralKeyResponse AssistedRegisterEphemeralKey(AssistedRegisterEphemeralKeyData? assistedRegisterEphemeralKeyData)
    {
        var data = assistedRegisterEphemeralKeyData != null ? assistedRegisterEphemeralKeyData.ToData() : null;
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.HelperResource.assistedRegisterEphemeralKeyJson(_helper, data);
            return Utils.FromData<AssistedRegisterEphemeralKeyResponse>(result);
        }
        finally
        {
            ReleaseCallMemory(data, result);
        }
    }

    public void AssistedRegister(AssistedRegisterData assistedRegisterData)
    {
        var data = assistedRegisterData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.HelperResource.assistedRegisterJson(_helper, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    /**
     * Lock Operations
     */
    public LockResponse GetSingleLock(GetSingleLockData getSingleLockData)
    {
        var data = getSingleLockData.ToData();
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getSingleLockJson(_lockOperations, data);
            return Utils.FromData<LockResponse>(result);
        }
        finally
        {
            ReleaseCallMemory(data, result);
        }
    }

    public List<AuditResponse> GetLockAuditTrail(GetLockAuditTrailData getLockAuditTrailData)
    {
        var data = getLockAuditTrailData.ToData();
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getLockAuditTrailJson(_lockOperations, data);
            return Utils.FromData<List<AuditResponse>>(result);
        }
        finally
        {
            ReleaseCallMemory(data, result);
        }
    }

    public List<AuditResponse> GetAuditForUser(GetAuditForUserData getAuditForUserData)
    {
        var data = getAuditForUserData.ToData();
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getAuditForUserJson(_lockOperations, data);
            return Utils.FromData<List<AuditResponse>>(result);
        }
        finally
        {
            ReleaseCallMemory(data, result);
        }
    }

    public List<UserLockResponse> GetUsersForLock(GetUsersForLockData getUsersForLockData)
    {
        var data = getUsersForLockData.ToData();
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getUsersForLockJson(_lockOperations, data);
            return Utils.FromData<List<UserLockResponse>>(result);
        }
        finally
        {
            ReleaseCallMemory(data, result);
        }
    }

    public LockUserResponse GetLocksForUser(GetLocksForUserData getLocksForUserData)
    {
        var data = getLocksForUserData.ToData();
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getLocksForUserJson(_lockOperations, data);
            return Utils.FromData<LockUserResponse>(result);
        }
        finally
        {
            ReleaseCallMemory(data, result);
        }
    }

    public void UpdateLockName(UpdateLockNameData updateLockNameData)
    {
        var data = updateLockNameData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.updateLockNameJson(_lockOperations, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public void UpdateLockFavourite(UpdateLockFavouriteData updateLockFavouriteData)
    {
        var data = updateLockFavouriteData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.updateLockFavouriteJson(_lockOperations, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public void UpdateLockColour(UpdateLockColourData updateLockColourData)
    {
        var data = updateLockColourData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.updateLockColourJson(_lockOperations, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public void UpdateLockSettingDefaultName(UpdateLockSettingDefaultNameData updateLockSettingDefaultNameData)
    {
        var data = updateLockSettingDefaultNameData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.updateLockSettingDefaultNameJson(_lockOperations, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public void SetLockSettingPermittedAddresses(SetLockSettingPermittedAddressesData setLockSettingPermittedAddressesData)
    {
        var data = setLockSettingPermittedAddressesData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.setLockSettingPermittedAddressesJson(_lockOperations, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public void UpdateLockSettingHidden(UpdateLockSettingHiddenData updateLockSettingHiddenData)
    {
        var data = updateLockSettingHiddenData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.updateLockSettingHiddenJson(_lockOperations, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public void SetLockSettingTimeRestrictions(SetLockSettingTimeRestrictionsData setLockSettingTimeRestrictionsData)
    {
        var data = setLockSettingTimeRestrictionsData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.setLockSettingTimeRestrictionsJson(_lockOperations, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public void UpdateLockSettingLocationRestrictions(UpdateLockSettingLocationRestrictionsData updateLockSettingLocationRestrictionsData)
    {
        var data = updateLockSettingLocationRestrictionsData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.updateLockSettingLocationRestrictionsJson(_lockOperations, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public UserPublicKeyResponse GetUserPublicKey(GetUserPublicKeyData getUserPublicKeyData)
    {
        var data = getUserPublicKeyData.ToData();
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getUserPublicKeyJson(_lockOperations, data);
            return Utils.FromData<UserPublicKeyResponse>(result);
        }
        finally
        {
            ReleaseCallMemory(data, result);
        }
    }

    public UserPublicKeyResponse GetUserPublicKeyByEmail(GetUserPublicKeyByEmailData getUserPublicKeyByEmailData)
    {
        var data = getUserPublicKeyByEmailData.ToData();
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getUserPublicKeyByEmailJson(_lockOperations, data);
            return Utils.FromData<UserPublicKeyResponse>(result);
        }
        finally
        {
            ReleaseCallMemory(data, result);
        }
    }

    public UserPublicKeyResponse GetUserPublicKeyByTelephone(GetUserPublicKeyByTelephoneData getUserPublicKeyByTelephoneData)
    {
        var data = getUserPublicKeyByTelephoneData.ToData();
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getUserPublicKeyByTelephoneJson(_lockOperations, data);
            return Utils.FromData<UserPublicKeyResponse>(result);
        }
        finally
        {
            ReleaseCallMemory(data, result);
        }
    }

    public UserPublicKeyResponse GetUserPublicKeyByLocalKey(GetUserPublicKeyByLocalKeyData getUserPublicKeyByLocalKeyData)
    {
        var data = getUserPublicKeyByLocalKeyData.ToData();
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getUserPublicKeyByLocalKeyJson(_lockOperations, data);
            return Utils.FromData<UserPublicKeyResponse>(result);
        }
        finally
        {
            ReleaseCallMemory(data, result);
        }
    }

    public UserPublicKeyResponse GetUserPublicKeyByForeignKey(GetUserPublicKeyByForeignKeyData getUserPublicKeyByForeignKeyData)
    {
        var data = getUserPublicKeyByForeignKeyData.ToData();
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getUserPublicKeyByForeignKeyJson(_lockOperations, data);
            return Utils.FromData<UserPublicKeyResponse>(result);
        }
        finally
        {
            ReleaseCallMemory(data, result);
        }
    }

    public UserPublicKeyResponse GetUserPublicKeyByIdentity(GetUserPublicKeyByIdentityData getUserPublicKeyByIdentityData)
    {
        var data = getUserPublicKeyByIdentityData.ToData();
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getUserPublicKeyByIdentityJson(_lockOperations, data);
            return Utils.FromData<UserPublicKeyResponse>(result);
        }
        finally
        {
            ReleaseCallMemory(data, result);
        }
    }

    public List<BatchUserPublicKeyResponse> GetUserPublicKeyByEmails(GetUserPublicKeyByEmailsData getUserPublicKeyByEmailsData)
    {
        var data = getUserPublicKeyByEmailsData.ToData();
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getUserPublicKeyByEmailsJson(_lockOperations, data);
            return Utils.FromData<List<BatchUserPublicKeyResponse>>(result);
        }
        finally
        {
            ReleaseCallMemory(data, result);
        }
    }

    public List<BatchUserPublicKeyResponse> GetUserPublicKeyByTelephones(GetUserPublicKeyByTelephonesData getUserPublicKeyByTelephonesData)
    {
        var data = getUserPublicKeyByTelephonesData.ToData();
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getUserPublicKeyByTelephonesJson(_lockOperations, data);
            return Utils.FromData<List<BatchUserPublicKeyResponse>>(result);
        }
        finally
        {
            ReleaseCallMemory(data, result);
        }
    }

    public List<BatchUserPublicKeyResponse> GetUserPublicKeyByLocalKeys(GetUserPublicKeyByLocalKeysData getUserPublicKeyByLocalKeysData)
    {
        var data = getUserPublicKeyByLocalKeysData.ToData();
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getUserPublicKeyByLocalKeysJson(_lockOperations, data);
            return Utils.FromData<List<BatchUserPublicKeyResponse>>(result);
        }
        finally
        {
            ReleaseCallMemory(data, result);
        }
    }

    public List<BatchUserPublicKeyResponse> GetUserPublicKeyByForeignKeys(GetUserPublicKeyByForeignKeysData getUserPublicKeyByForeignKeysData)
    {
        var data = getUserPublicKeyByForeignKeysData.ToData();
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getUserPublicKeyByForeignKeysJson(_lockOperations, data);
            return Utils.FromData<List<BatchUserPublicKeyResponse>>(result);
        }
        finally
        {
            ReleaseCallMemory(data, result);
        }
    }

    public void Unlock(UnlockOperationData unlockOperationData)
    {
        var data = unlockOperationData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.unlockJson(_lockOperations, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public void ShareLock(ShareLockOperationData shareLockOperationData)
    {
        var data = shareLockOperationData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.shareLockJson(_lockOperations, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public void RevokeAccessToLock(RevokeAccessToLockOperationData revokeAccessToLockOperationData)
    {
        var data = revokeAccessToLockOperationData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.revokeAccessToLockJson(_lockOperations, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public void UpdateSecureSettingUnlockDuration(UpdateSecureSettingUnlockDurationData updateSecureSettingUnlockDurationData)
    {
        var data = updateSecureSettingUnlockDurationData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.updateSecureSettingUnlockDurationJson(_lockOperations, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public void UpdateSecureSettingUnlockBetween(UpdateSecureSettingUnlockBetweenData updateSecureSettingUnlockBetweenData)
    {
        var data = updateSecureSettingUnlockBetweenData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.updateSecureSettingUnlockBetweenJson(_lockOperations, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public List<LockResponse> GetPinnedLocks()
    {
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getPinnedLocksJson(_lockOperations);
            return Utils.FromData<List<LockResponse>>(result);
        }
        finally
        {
            ReleaseCallMemory(null, result);
        }
    }

    public List<ShareableLockResponse> GetShareableLocks()
    {
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getShareableLocksJson(_lockOperations);
            return Utils.FromData<List<ShareableLockResponse>>(result);
        }
        finally
        {
            ReleaseCallMemory(null, result);
        }
    }

    /**
     * Platform
     */
    public void CreateApplication(CreateApplicationData createApplicationData)
    {
        var data = createApplicationData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.createApplicationJson(_platform, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public List<ApplicationResponse> ListApplications()
    {
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.listApplicationsJson(_platform);
            return Utils.FromData<List<ApplicationResponse>>(result);
        }
        finally
        {
            ReleaseCallMemory(null, result);
        }
    }

    public ApplicationResponse GetApplication(GetApplicationData getApplicationData)
    {
        var data = getApplicationData.ToData();
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.getApplicationJson(_platform, data);
            return Utils.FromData<ApplicationResponse>(result);
        }
        finally
        {
            ReleaseCallMemory(data, result);
        }
    }

    public void UpdateApplicationName(UpdateApplicationNameData updateApplicationNameData)
    {
        var data = updateApplicationNameData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.updateApplicationNameJson(_platform, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public void UpdateApplicationCompanyName(UpdateApplicationCompanyNameData updateApplicationCompanyNameData)
    {
        var data = updateApplicationCompanyNameData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.updateApplicationCompanyNameJson(_platform, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public void UpdateApplicationMailingAddress(UpdateApplicationMailingAddressData updateApplicationMailingAddressData)
    {
        var data = updateApplicationMailingAddressData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.updateApplicationMailingAddressJson(_platform, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public void UpdateApplicationPrivacyPolicy(UpdateApplicationPrivacyPolicyData updateApplicationPrivacyPolicyData)
    {
        var data = updateApplicationPrivacyPolicyData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.updateApplicationPrivacyPolicyJson(_platform, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public void UpdateApplicationSupportContact(UpdateApplicationSupportContactData updateApplicationSupportContactData)
    {
        var data = updateApplicationSupportContactData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.updateApplicationSupportContactJson(_platform, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public void UpdateApplicationAppLink(UpdateApplicationAppLinkData updateApplicationAppLinkData)
    {
        var data = updateApplicationAppLinkData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.updateApplicationAppLinkJson(_platform, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public void UpdateApplicationEmailPreferences(UpdateApplicationEmailPreferencesData updateApplicationEmailPreferencesData)
    {
        var data = updateApplicationEmailPreferencesData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.updateApplicationEmailPreferencesJson(_platform, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public void UpdateApplicationLogoUrl(UpdateApplicationLogoUrlData updateApplicationLogoUrlData)
    {
        var data = updateApplicationLogoUrlData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.updateApplicationLogoUrlJson(_platform, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public void DeleteApplication(DeleteApplicationData deleteApplicationData)
    {
        var data = deleteApplicationData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.deleteApplicationJson(_platform, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public GetLogoUploadUrlResponse GetLogoUploadUrl(GetLogoUploadUrlData getLogoUploadUrlData)
    {
        var data = getLogoUploadUrlData.ToData();
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.getLogoUploadUrlJson(_platform, data);
            return Utils.FromData<GetLogoUploadUrlResponse>(result);
        }
        finally
        {
            ReleaseCallMemory(data, result);
        }
    }

    public void AddAuthKey(AddAuthKeyData addAuthKeyData)
    {
        var data = addAuthKeyData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.addAuthKeyJson(_platform, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public void AddAuthIssuer(AddAuthIssuerData addAuthIssuerData)
    {
        var data = addAuthIssuerData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.addAuthIssuerJson(_platform, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public void DeleteAuthIssuer(DeleteAuthIssuerData deleteAuthIssuerData)
    {
        var data = deleteAuthIssuerData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.deleteAuthIssuerJson(_platform, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public void AddCorsDomain(AddCorsDomainData addCorsDomainData)
    {
        var data = addCorsDomainData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.addCorsDomainJson(_platform, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public void RemoveCorsDomain(RemoveCorsDomainData removeCorsDomainData)
    {
        var data = removeCorsDomainData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.removeCorsDomainJson(_platform, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public void AddApplicationOwner(AddApplicationOwnerData addApplicationOwnerData)
    {
        var data = addApplicationOwnerData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.addApplicationOwnerJson(_platform, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public void RemoveApplicationOwner(RemoveApplicationOwnerData removeApplicationOwnerData)
    {
        var data = removeApplicationOwnerData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.removeApplicationOwnerJson(_platform, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    public List<ApplicationOwnerDetailsResponse> GetApplicationOwnersDetails(GetApplicationOwnersDetailsData getApplicationOwnersDetailsData)
    {
        var data = getApplicationOwnersDetailsData.ToData();
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.getApplicationOwnersDetailsJson(_platform, data);
            return Utils.FromData<List<ApplicationOwnerDetailsResponse>>(result);
        }
        finally
        {
            ReleaseCallMemory(data, result);
        }
    }

    /**
     * Sites
     */
    public List<SiteResponse> ListSites()
    {
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.SitesResource.listSitesJson(_sites);
            return Utils.FromData<List<SiteResponse>>(result);
        }
        finally
        {
            ReleaseCallMemory(null, result);
        }
    }

    public List<SiteLocksResponse> GetLocksForSite(GetLocksForSiteData getLocksForSiteData)
    {
        var data = getLocksForSiteData.ToData();
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.SitesResource.getLocksForSiteJson(_sites, data);
            return Utils.FromData<List<SiteLocksResponse>>(result);
        }
        finally
        {
            ReleaseCallMemory(data, result);
        }
    }

    public List<UserForSiteResponse> GetUsersForSite(GetUsersForSiteData getUsersForSiteData)
    {
        var data = getUsersForSiteData.ToData();
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.SitesResource.getUsersForSiteJson(_sites, data);
            return Utils.FromData<List<UserForSiteResponse>>(result);
        }
        finally
        {
            ReleaseCallMemory(data, result);
        }
    }

    /**
     * Tiles
     */
    public TileLocksResponse GetLocksBelongingToTile(GetLocksBelongingToTileData getLocksBelongingToTileData)
    {
        var data = getLocksBelongingToTileData.ToData();
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.TilesResource.getLocksBelongingToTileJson(_tiles, data);
            return Utils.FromData<TileLocksResponse>(result);
        }
        finally
        {
            ReleaseCallMemory(data, result);
        }
    }

    public void AssociateMultipleLocks(AssociateMultipleLocksData associateMultipleLocksData)
    {
        var data = associateMultipleLocksData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.TilesResource.associateMultipleLocksJson(_tiles, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }

    /**
     * Context
     */
    public void SetAuthToken(string token)
    {
        var data = token.ToSByte();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager.setAuthToken(_contextManager, data);
        }
        finally
        {
            ReleaseCallMemory(data, null);
        }
    }
    
    public void Release()
    {
        _symbols->DisposeStablePointer(_apiEnvironment.pinned);
        _symbols->DisposeStablePointer(_factory.pinned);
        _symbols->DisposeStablePointer(_sdk.pinned);
        _symbols->DisposeStablePointer(_account.pinned);
        _symbols->DisposeStablePointer(_accountless.pinned);
        _symbols->DisposeStablePointer(_fusion.pinned);
        _symbols->DisposeStablePointer(_helper.pinned);
        _symbols->DisposeStablePointer(_lockOperations.pinned);
        _symbols->DisposeStablePointer(_platform.pinned);
        _symbols->DisposeStablePointer(_sites.pinned);
        _symbols->DisposeStablePointer(_tiles.pinned);
        _symbols->DisposeStablePointer(_contextManager.pinned);
        _symbols->DisposeStablePointer(_cryptoManager.pinned);
        _symbols->DisposeStablePointer(_utils.pinned);
    }
    
    private void ReleaseCallMemory(sbyte* data, sbyte* result)
    {
        if (data != null)
        {
            Marshal.FreeHGlobal((IntPtr)data);
        }
        
        if (result != null)
        {
            _symbols->DisposeString(result);
        }
    }
}