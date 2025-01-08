using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk;

public unsafe class DoordeckSdk(ApiEnvironment apiEnvironment)
{
    private readonly Doordeck_Headless_Sdk_ExportedSymbols* _symbols = Methods.Doordeck_Headless_Sdk_symbols();
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountResource _account;
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountlessResource _accountless;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._AccountlessResource_e__Struct _accountlessResource;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._AccountResource_e__Struct _accountResource;

    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_model_ApiEnvironment _apiEnvironment;
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_ContextManager _context;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._ContextManager_e__Struct _contextManager;

    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_crypto_CryptoManager _crypto;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._crypto_e__Struct._CryptoManager_e__Struct _cryptoManager;

    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_KDoordeckFactory _factory;
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_FusionResource _fusion;
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_HelperResource _helper;
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_LockOperationsResource _lockOperations;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._LockOperationsResource_e__Struct
        _lockOperationsResource;

    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_PlatformResource _platform;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._PlatformResource_e__Struct _platformResource;

    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck _sdk;
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_SitesResource _sites;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._SitesResource_e__Struct _sitesResource;

    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_TilesResource _tiles;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._TilesResource_e__Struct _tilesResource;

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
        _sdk = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.KDoordeckFactory.initialize(_factory,
            _apiEnvironment);
        _account = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.account(_sdk);
        _accountless = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.accountless(_sdk);
        _fusion = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.fusion(_sdk);
        _helper = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.helper(_sdk);
        _lockOperations = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(_sdk);
        _platform = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.platform(_sdk);
        _sites = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.sites(_sdk);
        _tiles = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.tiles(_sdk);
        _context = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.contextManager(_sdk);
        _crypto = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.crypto(_sdk);
        _utils = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.util.Utils._instance();

        _accountlessResource = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountlessResource;
        _accountResource = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource;
        _lockOperationsResource = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource;
        _platformResource = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource;
        _sitesResource = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.SitesResource;
        _tilesResource = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.TilesResource;
        _contextManager = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager;
        _cryptoManager = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.crypto.CryptoManager;
    }

    /**
     * Accountless
     */
    public TokenResponse Login(LoginData data)
    {
        return ProcessAccountlessResource<TokenResponse>(
            _accountlessResource.loginJson,
            null,
            null,
            data
        );
    }

    public TokenResponse Registration(RegistrationData data)
    {
        return ProcessAccountlessResource<TokenResponse>(
            _accountlessResource.registrationJson,
            null,
            null,
            data
        );
    }

    public void VerifyEmail(VerifyEmailData data)
    {
        ProcessAccountlessResource<object>(
            null,
            _accountlessResource.verifyEmailJson,
            null,
            data
        );
    }

    public void PasswordReset(PasswordResetData data)
    {
        ProcessAccountlessResource<object>(
            null,
            _accountlessResource.passwordResetJson,
            null,
            data
        );
    }

    public void PasswordResetVerify(PasswordResetVerifyData data)
    {
        ProcessAccountlessResource<object>(
            null,
            _accountlessResource.passwordResetVerifyJson,
            null,
            data
        );
    }

    /**
     * Account
     */
    public TokenResponse RefreshToken(RefreshTokenData? data)
    {
        return ProcessAccountResource<TokenResponse>(
            _accountResource.refreshTokenJson,
            null,
            null,
            data
        );
    }

    public void Logout()
    {
        _accountResource.logout(_account);
    }

    public RegisterEphemeralKeyWithSecondaryAuthenticationResponse RegisterEphemeralKey(RegisterEphemeralKeyData? data)
    {
        return ProcessAccountResource<RegisterEphemeralKeyWithSecondaryAuthenticationResponse>(
            _accountResource.registerEphemeralKeyJson,
            null,
            null,
            data
        );
    }

    public RegisterEphemeralKeyResponse RegisterEphemeralKeyWithSecondaryAuthentication(
        RegisterEphemeralKeyWithSecondaryAuthenticationData? data)
    {
        return ProcessAccountResource<RegisterEphemeralKeyResponse>(
            _accountResource.registerEphemeralKeyWithSecondaryAuthenticationJson,
            null,
            null,
            data
        );
    }

    public RegisterEphemeralKeyResponse VerifyEphemeralKeyRegistration(VerifyEphemeralKeyRegistrationData data)
    {
        return ProcessAccountResource<RegisterEphemeralKeyResponse>(
            _accountResource.verifyEphemeralKeyRegistrationJson,
            null,
            null,
            data
        );
    }

    public void ReverifyEmail()
    {
        _accountResource.reverifyEmail(_account);
    }

    public void ChangePassword(ChangePasswordData data)
    {
        ProcessAccountResource<object>(
            null,
            _accountResource.changePasswordJson,
            null,
            data
        );
    }

    public UserDetailsResponse GetUserDetails()
    {
        return ProcessAccountResource<UserDetailsResponse>(
            null,
            null,
            _accountResource.getUserDetailsJson,
            null
        );
    }

    public void UpdateUserDetails(UpdateUserDetailsData data)
    {
        ProcessAccountResource<object>(
            null,
            _accountResource.updateUserDetailsJson,
            null,
            data
        );
    }

    public void DeleteAccount()
    {
        _accountResource.deleteAccount(_account);
    }

    /**
     * Helper
     */
    public void UploadPlatformLogo(UploadPlatformLogoData uploadPlatformLogoData)
    {
        var data = uploadPlatformLogoData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.HelperResource.uploadPlatformLogoJson(_helper,
                data);
        }
        finally
        {
            ReleaseMemory(data, null);
        }
    }

    public AssistedLoginResponse AssistedLogin(AssistedLoginData assistedLoginData)
    {
        var data = assistedLoginData.ToData();
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.HelperResource.assistedLoginJson(_helper,
                data);
            return Utils.FromData<AssistedLoginResponse>(result);
        }
        finally
        {
            ReleaseMemory(data, result);
        }
    }

    public AssistedRegisterEphemeralKeyResponse AssistedRegisterEphemeralKey(
        AssistedRegisterEphemeralKeyData? assistedRegisterEphemeralKeyData)
    {
        var data = assistedRegisterEphemeralKeyData != null ? assistedRegisterEphemeralKeyData.ToData() : null;
        sbyte* result = null;
        try
        {
            result = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.HelperResource
                .assistedRegisterEphemeralKeyJson(_helper, data);
            return Utils.FromData<AssistedRegisterEphemeralKeyResponse>(result);
        }
        finally
        {
            ReleaseMemory(data, result);
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
            ReleaseMemory(data, null);
        }
    }

    /**
     * Lock Operations
     */
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

    /**
     * Platform
     */
    public void CreateApplication(CreateApplicationData data)
    {
        ProcessPlatformResource<object>(
            null,
            _platformResource.createApplicationJson,
            null,
            data
        );
    }

    public List<ApplicationResponse> ListApplications()
    {
        return ProcessPlatformResource<List<ApplicationResponse>>(
            null,
            null,
            _platformResource.listApplicationsJson,
            null
        );
    }

    public ApplicationResponse GetApplication(GetApplicationData data)
    {
        return ProcessPlatformResource<ApplicationResponse>(
            _platformResource.getApplicationJson,
            null,
            null,
            data
        );
    }

    public void UpdateApplicationName(UpdateApplicationNameData data)
    {
        ProcessPlatformResource<object>(
            null,
            _platformResource.updateApplicationNameJson,
            null,
            data
        );
    }

    public void UpdateApplicationCompanyName(UpdateApplicationCompanyNameData data)
    {
        ProcessPlatformResource<object>(
            null,
            _platformResource.updateApplicationCompanyNameJson,
            null,
            data
        );
    }

    public void UpdateApplicationMailingAddress(UpdateApplicationMailingAddressData data)
    {
        ProcessPlatformResource<object>(
            null,
            _platformResource.updateApplicationMailingAddressJson,
            null,
            data
        );
    }

    public void UpdateApplicationPrivacyPolicy(UpdateApplicationPrivacyPolicyData data)
    {
        ProcessPlatformResource<object>(
            null,
            _platformResource.updateApplicationPrivacyPolicyJson,
            null,
            data
        );
    }

    public void UpdateApplicationSupportContact(UpdateApplicationSupportContactData data)
    {
        ProcessPlatformResource<object>(
            null,
            _platformResource.updateApplicationSupportContactJson,
            null,
            data
        );
    }

    public void UpdateApplicationAppLink(UpdateApplicationAppLinkData data)
    {
        ProcessPlatformResource<object>(
            null,
            _platformResource.updateApplicationAppLinkJson,
            null,
            data
        );
    }

    public void UpdateApplicationEmailPreferences(UpdateApplicationEmailPreferencesData data)
    {
        ProcessPlatformResource<object>(
            null,
            _platformResource.updateApplicationEmailPreferencesJson,
            null,
            data
        );
    }

    public void UpdateApplicationLogoUrl(UpdateApplicationLogoUrlData data)
    {
        ProcessPlatformResource<object>(
            null,
            _platformResource.updateApplicationLogoUrlJson,
            null,
            data
        );
    }

    public void DeleteApplication(DeleteApplicationData data)
    {
        ProcessPlatformResource<object>(
            null,
            _platformResource.deleteApplicationJson,
            null,
            data
        );
    }

    public GetLogoUploadUrlResponse GetLogoUploadUrl(GetLogoUploadUrlData data)
    {
        return ProcessPlatformResource<GetLogoUploadUrlResponse>(
            _platformResource.getLogoUploadUrlJson,
            null,
            null,
            data
        );
    }

    public void AddAuthKey(AddAuthKeyData data)
    {
        ProcessPlatformResource<object>(
            null,
            _platformResource.addAuthKeyJson,
            null,
            data
        );
    }

    public void AddAuthIssuer(AddAuthIssuerData data)
    {
        ProcessPlatformResource<object>(
            null,
            _platformResource.addAuthIssuerJson,
            null,
            data
        );
    }

    public void DeleteAuthIssuer(DeleteAuthIssuerData data)
    {
        ProcessPlatformResource<object>(
            null,
            _platformResource.deleteAuthIssuerJson,
            null,
            data
        );
    }

    public void AddCorsDomain(AddCorsDomainData data)
    {
        ProcessPlatformResource<object>(
            null,
            _platformResource.addCorsDomainJson,
            null,
            data
        );
    }

    public void RemoveCorsDomain(RemoveCorsDomainData data)
    {
        ProcessPlatformResource<object>(
            null,
            _platformResource.removeCorsDomainJson,
            null,
            data
        );
    }

    public void AddApplicationOwner(AddApplicationOwnerData data)
    {
        ProcessPlatformResource<object>(
            null,
            _platformResource.addApplicationOwnerJson,
            null,
            data
        );
    }

    public void RemoveApplicationOwner(RemoveApplicationOwnerData data)
    {
        ProcessPlatformResource<object>(
            null,
            _platformResource.removeApplicationOwnerJson,
            null,
            data
        );
    }

    public List<ApplicationOwnerDetailsResponse> GetApplicationOwnersDetails(GetApplicationOwnersDetailsData data)
    {
        return ProcessPlatformResource<List<ApplicationOwnerDetailsResponse>>(
            _platformResource.getApplicationOwnersDetailsJson,
            null,
            null,
            data
        );
    }

    /**
     * Sites
     */
    public List<SiteResponse> ListSites()
    {
        return ProcessSitesResource<List<SiteResponse>>(
            null,
            null,
            _sitesResource.listSitesJson,
            null
        );
    }

    public List<SiteLocksResponse> GetLocksForSite(GetLocksForSiteData data)
    {
        return ProcessSitesResource<List<SiteLocksResponse>>(
            _sitesResource.getLocksForSiteJson,
            null,
            null,
            data
        );
    }

    public List<UserForSiteResponse> GetUsersForSite(GetUsersForSiteData data)
    {
        return ProcessSitesResource<List<UserForSiteResponse>>(
            _sitesResource.getUsersForSiteJson,
            null,
            null,
            data
        );
    }

    /**
     * Tiles
     */
    public TileLocksResponse GetLocksBelongingToTile(GetLocksBelongingToTileData data)
    {
        return ProcessTilesResource<TileLocksResponse>(
            _tilesResource.getLocksBelongingToTileJson,
            null,
            null,
            data
        );
    }

    public void AssociateMultipleLocks(AssociateMultipleLocksData data)
    {
        ProcessTilesResource<object>(
            null,
            _tilesResource.associateMultipleLocksJson,
            null,
            data
        );
    }

    /**
     * Context
     */
    public void SetAuthToken(string token)
    {
        var data = token.ToSByte();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager.setAuthToken(_context, data);
        }
        finally
        {
            ReleaseMemory(data, null);
        }
    }

    public string GetAuthToken()
    {
        sbyte* result = null;
        try
        {
            result =
                _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager.getAuthToken(_context);
            return Utils.ConvertSByteToString(result);
        }
        finally
        {
            ReleaseMemory(null, result);
        }
    }

    public bool IsAuthTokenAboutToExpire()
    {
        return _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager
            .isAuthTokenAboutToExpire(_context).ToBoolean();
    }

    public void SetRefreshToken(string token)
    {
        var data = token.ToSByte();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager.setRefreshToken(_context,
                data);
        }
        finally
        {
            ReleaseMemory(data, null);
        }
    }

    public string GetRefreshToken()
    {
        sbyte* result = null;
        try
        {
            result =
                _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager
                    .getRefreshToken(_context);
            return Utils.ConvertSByteToString(result);
        }
        finally
        {
            ReleaseMemory(null, result);
        }
    }

    public void SetFusionAuthToken(string token)
    {
        var data = token.ToSByte();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager.setFusionAuthToken(_context,
                data);
        }
        finally
        {
            ReleaseMemory(data, null);
        }
    }

    public string GetFusionAuthToken()
    {
        sbyte* result = null;
        try
        {
            result =
                _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager.getFusionAuthToken(
                    _context);
            return Utils.ConvertSByteToString(result);
        }
        finally
        {
            ReleaseMemory(null, result);
        }
    }

    public void SetUserId(string userId)
    {
        var data = userId.ToSByte();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager.setUserId(_context, data);
        }
        finally
        {
            ReleaseMemory(data, null);
        }
    }

    public string GetUserId()
    {
        sbyte* result = null;
        try
        {
            result =
                _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager.getUserId(_context);
            return Utils.ConvertSByteToString(result);
        }
        finally
        {
            ReleaseMemory(null, result);
        }
    }

    public void SetUserEmail(string email)
    {
        var data = email.ToSByte();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager.setUserEmail(_context, data);
        }
        finally
        {
            ReleaseMemory(data, null);
        }
    }

    public string GetUserEmail()
    {
        sbyte* result = null;
        try
        {
            result =
                _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager.getUserEmail(_context);
            return Utils.ConvertSByteToString(result);
        }
        finally
        {
            ReleaseMemory(null, result);
        }
    }

    // SetCertificateChain

    // GetCertificateChain

    public bool IsCertificateChainAboutToExpire()
    {
        return _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager
            .isCertificateChainAboutToExpire(_context).ToBoolean();
    }

    // SetKeyPair

    // GetKeyPair

    public bool IsKeyPairValid()
    {
        return _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager.isKeyPairValid(_context)
            .ToBoolean();
    }

    public void SetOperationContextJson(OperationContextData operationContextData)
    {
        var data = operationContextData.ToData();
        try
        {
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager.setOperationContextJson(
                _context, data);
        }
        finally
        {
            ReleaseMemory(data, null);
        }
    }

    public void LoadContext()
    {
        _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager.loadContext(_context);
    }

    public void StoreContext()
    {
        _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager.storeContext(_context);
    }

    public void ClearContext()
    {
        _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager.clearContext(_context);
    }

    /**
     * Crypto manager
     */
    public EncodedKeyPair GenerateEncodedKeyPair()
    {
        sbyte* result = null;
        try
        {
            result = _cryptoManager.generateEncodedKeyPair(_crypto);
            return Utils.FromData<EncodedKeyPair>(result);
        }
        finally
        {
            ReleaseMemory(null, result);
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
        _symbols->DisposeStablePointer(_context.pinned);
        _symbols->DisposeStablePointer(_crypto.pinned);
        _symbols->DisposeStablePointer(_utils.pinned);
    }

    private TResponse ProcessAccountlessResource<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountlessResource,
            sbyte*, sbyte*> withDataAndWithResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountlessResource,
            sbyte*, void> withDataAndWithoutResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountlessResource,
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
                result = withDataAndWithResponse(_accountless, sData);
            else if (withData && !withResponse)
                withDataAndWithoutResponse(_accountless, sData);
            else if (!withData && withResponse)
                result = withoutDataAndWithResponse(_accountless);
            return result != null ? Utils.FromData<TResponse>(result)! : default!;
        }
        finally
        {
            if (data != null) Marshal.FreeHGlobal((IntPtr)sData);

            if (result != null) _symbols->DisposeString(result);
        }
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
            return result != null ? Utils.FromData<TResponse>(result)! : default!;
        }
        finally
        {
            if (data != null) Marshal.FreeHGlobal((IntPtr)sData);

            if (result != null) _symbols->DisposeString(result);
        }
    }

    private TResponse ProcessPlatformResource<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_PlatformResource,
            sbyte*, sbyte*> withDataAndWithResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_PlatformResource,
            sbyte*, void> withDataAndWithoutResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_PlatformResource,
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
                result = withDataAndWithResponse(_platform, sData);
            else if (withData && !withResponse)
                withDataAndWithoutResponse(_platform, sData);
            else if (!withData && withResponse)
                result = withoutDataAndWithResponse(_platform);
            return result != null ? Utils.FromData<TResponse>(result)! : default!;
        }
        finally
        {
            if (data != null) Marshal.FreeHGlobal((IntPtr)sData);

            if (result != null) _symbols->DisposeString(result);
        }
    }

    private TResponse ProcessAccountResource<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountResource,
            sbyte*, sbyte*> withDataAndWithResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountResource,
            sbyte*, void> withDataAndWithoutResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountResource,
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
                result = withDataAndWithResponse(_account, sData);
            else if (withData && !withResponse)
                withDataAndWithoutResponse(_account, sData);
            else if (!withData && withResponse)
                result = withoutDataAndWithResponse(_account);
            return result != null ? Utils.FromData<TResponse>(result)! : default!;
        }
        finally
        {
            if (data != null) Marshal.FreeHGlobal((IntPtr)sData);

            if (result != null) _symbols->DisposeString(result);
        }
    }

    private TResponse ProcessSitesResource<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_SitesResource,
            sbyte*, sbyte*> withDataAndWithResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_SitesResource,
            sbyte*, void> withDataAndWithoutResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_SitesResource,
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
                result = withDataAndWithResponse(_sites, sData);
            else if (withData && !withResponse)
                withDataAndWithoutResponse(_sites, sData);
            else if (!withData && withResponse)
                result = withoutDataAndWithResponse(_sites);
            return result != null ? Utils.FromData<TResponse>(result)! : default!;
        }
        finally
        {
            if (data != null) Marshal.FreeHGlobal((IntPtr)sData);

            if (result != null) _symbols->DisposeString(result);
        }
    }


    private TResponse ProcessTilesResource<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_TilesResource,
            sbyte*, sbyte*> withDataAndWithResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_TilesResource,
            sbyte*, void> withDataAndWithoutResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_TilesResource,
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
                result = withDataAndWithResponse(_tiles, sData);
            else if (withData && !withResponse)
                withDataAndWithoutResponse(_tiles, sData);
            else if (!withData && withResponse)
                result = withoutDataAndWithResponse(_tiles);
            return result != null ? Utils.FromData<TResponse>(result)! : default!;
        }
        finally
        {
            if (data != null) Marshal.FreeHGlobal((IntPtr)sData);

            if (result != null) _symbols->DisposeString(result);
        }
    }

    private void ReleaseMemory(sbyte* data, sbyte* result)
    {
        if (data != null) Marshal.FreeHGlobal((IntPtr)data);

        if (result != null) _symbols->DisposeString(result);
    }
}