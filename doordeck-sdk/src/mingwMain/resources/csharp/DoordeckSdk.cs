using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Utilities;
using Doordeck.Headless.Sdk.Wrapper;

namespace Doordeck.Headless.Sdk;

public class DoordeckSdk
{
    private readonly unsafe Doordeck_Headless_Sdk_ExportedSymbols* _symbols = Methods.Doordeck_Headless_Sdk_symbols();

    private readonly Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_KDoordeckFactory _factory;
    private readonly Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck _sdk;

    private readonly Account _account;
    private readonly Accountless _accountless;
    private readonly Fusion _fusion;
    private readonly Helper _helper;
    private readonly LockOperations _lockOperations;
    private readonly Platform _platform;
    private readonly Sites _sites;
    private readonly Tiles _tiles;
    private readonly ContextManager _contextManager;
    private readonly CryptoManager _cryptoManager;

    private readonly Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountApi _accountApi;
    private readonly Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_AccountlessApi _accountlessApi;
    private readonly Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_FusionApi _fusionApi;
    private readonly Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_HelperApi _helperApi;
    private readonly Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_LockOperationsApi _lockOperationsApi;
    private readonly Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_PlatformApi _platformApi;
    private readonly Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_TilesApi _tilesApi;
    private readonly Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_SitesApi _sitesApi;
    private readonly Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_context_ContextManager _contextApi;
    private readonly Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_crypto_CryptoManager _cryptoApi;

    public unsafe DoordeckSdk(ApiEnvironment apiEnvironment = ApiEnvironment.PROD, string? cloudAuthToken = null,
        string? cloudRefreshToken = null, string? fusionHost = null, ISecureStorage? secureStorageImpl = null,
        bool? debugLogging = null)
    {
        _factory = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.KDoordeckFactory._instance();

        var apiEnvironmentPtr = apiEnvironment.ToString().StringToSByte();
        var cloudAuthTokenPtr = cloudAuthToken != null ? cloudAuthToken.StringToSByte() : null;
        var cloudRefreshTokenPtr = cloudRefreshToken != null ? cloudRefreshToken.StringToSByte() : null;
        var fusionHostPtr = fusionHost != null ? fusionHost.StringToSByte() : null;
        var debugLoggingPtr = (debugLogging ?? false).ToString().StringToSByte();

        var sdkConfig = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.config.SdkConfig;
        var sdkConfigBuilder = sdkConfig.Builder.Builder();
        sdkConfig.Builder.setApiEnvironment(sdkConfigBuilder, apiEnvironmentPtr);

        if (cloudAuthTokenPtr != null) sdkConfig.Builder.setCloudAuthToken(sdkConfigBuilder, cloudAuthTokenPtr);
        if (cloudRefreshTokenPtr != null) sdkConfig.Builder.setCloudRefreshToken(sdkConfigBuilder, cloudRefreshTokenPtr);
        if (fusionHostPtr != null) sdkConfig.Builder.setFusionHost(sdkConfigBuilder, fusionHostPtr);
        sdkConfig.Builder.setDebugLogging(sdkConfigBuilder, debugLoggingPtr);

        if (secureStorageImpl != null)
        {
            SecureStorage.Implementation = secureStorageImpl;

            SecureStorage.Delegates.SetApiEnvironmentDelegate setApiEnvironmentDelegate =
                SecureStorage.SetApiEnvironment;
            SecureStorage.Delegates.GetApiEnvironmentDelegate getApiEnvironmentDelegate =
                SecureStorage.GetApiEnvironment;
            SecureStorage.Delegates.AddCloudAuthTokenDelegate addCloudAuthTokenDelegate =
                SecureStorage.AddCloudAuthToken;
            SecureStorage.Delegates.GetCloudAuthTokenDelegate getCloudAuthTokenDelegate =
                SecureStorage.GetCloudAuthToken;
            SecureStorage.Delegates.AddCloudRefreshTokenDelegate addCloudRefreshTokenDelegate =
                SecureStorage.AddCloudRefreshToken;
            SecureStorage.Delegates.GetCloudRefreshTokenDelegate getCloudRefreshTokenDelegate =
                SecureStorage.GetCloudRefreshToken;
            SecureStorage.Delegates.SetFusionHostDelegate setFusionHostDelegate =
                SecureStorage.SetFusionHost;
            SecureStorage.Delegates.GetFusionHostDelegate getFusionHostDelegate =
                SecureStorage.GetFusionHost;
            SecureStorage.Delegates.AddFusionAuthTokenDelegate addFusionAuthTokenDelegate =
                SecureStorage.AddFusionAuthToken;
            SecureStorage.Delegates.GetFusionAuthTokenDelegate getFusionAuthTokenDelegate =
                SecureStorage.GetFusionAuthToken;
            SecureStorage.Delegates.AddPublicKeyDelegate addPublicKeyDelegate =
                SecureStorage.AddPublicKey;
            SecureStorage.Delegates.GetPublicKeyDelegate getPublicKeyDelegate =
                SecureStorage.GetPublicKey;
            SecureStorage.Delegates.AddPrivateKeyDelegate addPrivateKeyDelegate =
                SecureStorage.AddPrivateKey;
            SecureStorage.Delegates.GetPrivateKeyDelegate getPrivateKeyDelegate =
                SecureStorage.GetPrivateKey;
            SecureStorage.Delegates.SetKeyPairVerifiedDelegate setKeyPairVerifiedDelegate =
                SecureStorage.SetKeyPairVerified;
            SecureStorage.Delegates.GetKeyPairVerifiedDelegate getKeyPairVerifiedDelegate =
                SecureStorage.GetKeyPairVerified;
            SecureStorage.Delegates.AddUserIdDelegate addUserIdDelegate =
                SecureStorage.AddUserId;
            SecureStorage.Delegates.GetUserIdDelegate getUserIdDelegate =
                SecureStorage.GetUserId;
            SecureStorage.Delegates.AddUserEmailDelegate addUserEmailDelegate =
                SecureStorage.AddUserEmail;
            SecureStorage.Delegates.GetUserEmailDelegate getUserEmailDelegate =
                SecureStorage.GetUserEmail;
            SecureStorage.Delegates.AddCertificateChainDelegate addCertificateChainDelegate =
                SecureStorage.AddCertificateChain;
            SecureStorage.Delegates.GetCertificateChainDelegate getCertificateChainDelegate =
                SecureStorage.GetCertificateChain;
            SecureStorage.Delegates.ClearDelegate clearDelegate =
                SecureStorage.Clear;

            var setApiEnvironment = Marshal.GetFunctionPointerForDelegate(setApiEnvironmentDelegate);
            var getApiEnvironment = Marshal.GetFunctionPointerForDelegate(getApiEnvironmentDelegate);
            var addCloudAuthToken = Marshal.GetFunctionPointerForDelegate(addCloudAuthTokenDelegate);
            var getCloudAuthToken = Marshal.GetFunctionPointerForDelegate(getCloudAuthTokenDelegate);
            var addCloudRefreshToken = Marshal.GetFunctionPointerForDelegate(addCloudRefreshTokenDelegate);
            var getCloudRefreshToken = Marshal.GetFunctionPointerForDelegate(getCloudRefreshTokenDelegate);
            var setFusionHost = Marshal.GetFunctionPointerForDelegate(setFusionHostDelegate);
            var getFusionHost = Marshal.GetFunctionPointerForDelegate(getFusionHostDelegate);
            var addFusionAuthToken = Marshal.GetFunctionPointerForDelegate(addFusionAuthTokenDelegate);
            var getFusionAuthToken = Marshal.GetFunctionPointerForDelegate(getFusionAuthTokenDelegate);
            var addPublicKey = Marshal.GetFunctionPointerForDelegate(addPublicKeyDelegate);
            var getPublicKey = Marshal.GetFunctionPointerForDelegate(getPublicKeyDelegate);
            var addPrivateKey = Marshal.GetFunctionPointerForDelegate(addPrivateKeyDelegate);
            var getPrivateKey = Marshal.GetFunctionPointerForDelegate(getPrivateKeyDelegate);
            var setKeyPairVerified = Marshal.GetFunctionPointerForDelegate(setKeyPairVerifiedDelegate);
            var getKeyPairVerified = Marshal.GetFunctionPointerForDelegate(getKeyPairVerifiedDelegate);
            var addUserId = Marshal.GetFunctionPointerForDelegate(addUserIdDelegate);
            var getUserId = Marshal.GetFunctionPointerForDelegate(getUserIdDelegate);
            var addUserEmail = Marshal.GetFunctionPointerForDelegate(addUserEmailDelegate);
            var getUserEmail = Marshal.GetFunctionPointerForDelegate(getUserEmailDelegate);
            var addCertificateChain = Marshal.GetFunctionPointerForDelegate(addCertificateChainDelegate);
            var getCertificateChain = Marshal.GetFunctionPointerForDelegate(getCertificateChainDelegate);
            var clear = Marshal.GetFunctionPointerForDelegate(clearDelegate);

            var secureStorage = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.storage.createMingwSecureStorage_(
                setApiEnvironment.ToPointer(),
                getApiEnvironment.ToPointer(),
                addCloudAuthToken.ToPointer(),
                getCloudAuthToken.ToPointer(),
                addCloudRefreshToken.ToPointer(),
                getCloudRefreshToken.ToPointer(),
                setFusionHost.ToPointer(),
                getFusionHost.ToPointer(),
                addFusionAuthToken.ToPointer(),
                getFusionAuthToken.ToPointer(),
                addPublicKey.ToPointer(),
                getPublicKey.ToPointer(),
                addPrivateKey.ToPointer(),
                getPrivateKey.ToPointer(),
                setKeyPairVerified.ToPointer(),
                getKeyPairVerified.ToPointer(),
                addUserId.ToPointer(),
                getUserId.ToPointer(),
                addUserEmail.ToPointer(),
                getUserEmail.ToPointer(),
                addCertificateChain.ToPointer(),
                getCertificateChain.ToPointer(),
                clear.ToPointer()
            );

            var secureStorageP = new Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_storage_SecureStorage
            {
                pinned = secureStorage.pinned
            };

            sdkConfig.Builder.setSecureStorageOverride(sdkConfigBuilder, secureStorageP);
        }

        try
        {
            _sdk = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.KDoordeckFactory.initialize_(_factory, sdkConfig.Builder.build(sdkConfigBuilder));
        }
        finally
        {
            Marshal.FreeHGlobal((IntPtr)apiEnvironmentPtr);
            Marshal.FreeHGlobal((IntPtr)debugLoggingPtr);
            if (cloudAuthTokenPtr != null) Marshal.FreeHGlobal((IntPtr)cloudAuthTokenPtr);
            if (cloudRefreshTokenPtr != null) Marshal.FreeHGlobal((IntPtr)cloudRefreshTokenPtr);
            if (fusionHostPtr != null) Marshal.FreeHGlobal((IntPtr)fusionHostPtr);
        }

        _accountApi = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.account_(_sdk);
        _accountlessApi = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.accountless_(_sdk);
        _fusionApi = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.fusion_(_sdk);
        _helperApi = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.helper_(_sdk);
        _lockOperationsApi = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations_(_sdk);
        _platformApi = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.platform_(_sdk);
        _sitesApi = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.sites_(_sdk);
        _tilesApi = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.tiles_(_sdk);
        _contextApi = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.contextManager_(_sdk);
        _cryptoApi = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.crypto_(_sdk);

        _account = new Account(_accountApi,
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountApi);
        _accountless = new Accountless(_accountlessApi,
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountlessApi);
        _fusion = new Fusion(_fusionApi,
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.FusionApi);
        _helper = new Helper(_helperApi,
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.HelperApi);
        _lockOperations = new LockOperations(_lockOperationsApi,
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsApi);
        _platform = new Platform(_platformApi,
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformApi);
        _sites = new Sites(_sitesApi,
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.SitesApi);
        _tiles = new Tiles(_tilesApi,
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.TilesApi);
        _contextManager = new ContextManager(_contextApi,
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.context.ContextManager, _symbols);
        _cryptoManager = new CryptoManager(_cryptoApi,
            _symbols->kotlin.root.com.doordeck.multiplatform.sdk.crypto.CryptoManager, _symbols);
    }

    public Account GetAccount() => _account;

    public Accountless GetAccountless() => _accountless;

    public Fusion GetFusion() => _fusion;

    public Helper GetHelper() => _helper;

    public LockOperations GetLockOperations() => _lockOperations;

    public Platform GetPlatform() => _platform;

    public Sites GetSites() => _sites;

    public Tiles GetTiles() => _tiles;

    public ContextManager GetContextManager() => _contextManager;

    public CryptoManager GetCryptoManager() => _cryptoManager;

    public unsafe void Release()
    {
        _symbols->DisposeStablePointer(_factory.pinned);
        _symbols->DisposeStablePointer(_sdk.pinned);
        _symbols->DisposeStablePointer(_accountApi.pinned);
        _symbols->DisposeStablePointer(_accountlessApi.pinned);
        _symbols->DisposeStablePointer(_fusionApi.pinned);
        _symbols->DisposeStablePointer(_helperApi.pinned);
        _symbols->DisposeStablePointer(_lockOperationsApi.pinned);
        _symbols->DisposeStablePointer(_platformApi.pinned);
        _symbols->DisposeStablePointer(_sitesApi.pinned);
        _symbols->DisposeStablePointer(_tilesApi.pinned);
        _symbols->DisposeStablePointer(_cryptoApi.pinned);
        _symbols->DisposeStablePointer(_contextApi.pinned);
    }
}