using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Utilities;
using Doordeck.Headless.Sdk.Wrapper;

namespace Doordeck.Headless.Sdk;

public class DoordeckSdk
{
    // The ExportedSymbols struct is retained only for the three runtime helpers that Kotlin/Native
    // does not expose as flat symbols: KDoordeckFactory._instance, DisposeString and
    // DisposeStablePointer. Every SDK call goes through the flat Methods.* P/Invokes instead.
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

        var secureStorage = default(Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_storage_SecureStorage);
        if (secureStorageImpl != null)
        {
            SecureStorage.Implementation = secureStorageImpl;
            secureStorage = CreateSecureStorage();
        }

        try
        {
            var sdkConfig = Methods.buildSdkConfig(
                apiEnvironmentPtr,
                cloudAuthTokenPtr,
                cloudRefreshTokenPtr,
                fusionHostPtr,
                secureStorage,
                debugLoggingPtr
            );

            _sdk = Methods.initialize(_factory, sdkConfig);
        }
        finally
        {
            Marshal.FreeHGlobal((IntPtr)apiEnvironmentPtr);
            Marshal.FreeHGlobal((IntPtr)debugLoggingPtr);
            if (cloudAuthTokenPtr != null) Marshal.FreeHGlobal((IntPtr)cloudAuthTokenPtr);
            if (cloudRefreshTokenPtr != null) Marshal.FreeHGlobal((IntPtr)cloudRefreshTokenPtr);
            if (fusionHostPtr != null) Marshal.FreeHGlobal((IntPtr)fusionHostPtr);
        }

        _accountApi = Methods.account(_sdk);
        _accountlessApi = Methods.accountless(_sdk);
        _fusionApi = Methods.fusion(_sdk);
        _helperApi = Methods.helper(_sdk);
        _lockOperationsApi = Methods.lockOperations(_sdk);
        _platformApi = Methods.platform(_sdk);
        _sitesApi = Methods.sites(_sdk);
        _tilesApi = Methods.tiles(_sdk);
        _contextApi = Methods.contextManager(_sdk);
        _cryptoApi = Methods.crypto(_sdk);

        _account = new Account(_accountApi);
        _accountless = new Accountless(_accountlessApi);
        _fusion = new Fusion(_fusionApi);
        _helper = new Helper(_helperApi);
        _lockOperations = new LockOperations(_lockOperationsApi);
        _platform = new Platform(_platformApi);
        _sites = new Sites(_sitesApi);
        _tiles = new Tiles(_tilesApi);
        _contextManager = new ContextManager(_contextApi, _symbols);
        _cryptoManager = new CryptoManager(_cryptoApi, _symbols);
    }

    private static unsafe Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_storage_SecureStorage
        CreateSecureStorage()
    {
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

        return Methods.createMingwSecureStorage(
            Marshal.GetFunctionPointerForDelegate(setApiEnvironmentDelegate).ToPointer(),
            Marshal.GetFunctionPointerForDelegate(getApiEnvironmentDelegate).ToPointer(),
            Marshal.GetFunctionPointerForDelegate(addCloudAuthTokenDelegate).ToPointer(),
            Marshal.GetFunctionPointerForDelegate(getCloudAuthTokenDelegate).ToPointer(),
            Marshal.GetFunctionPointerForDelegate(addCloudRefreshTokenDelegate).ToPointer(),
            Marshal.GetFunctionPointerForDelegate(getCloudRefreshTokenDelegate).ToPointer(),
            Marshal.GetFunctionPointerForDelegate(setFusionHostDelegate).ToPointer(),
            Marshal.GetFunctionPointerForDelegate(getFusionHostDelegate).ToPointer(),
            Marshal.GetFunctionPointerForDelegate(addFusionAuthTokenDelegate).ToPointer(),
            Marshal.GetFunctionPointerForDelegate(getFusionAuthTokenDelegate).ToPointer(),
            Marshal.GetFunctionPointerForDelegate(addPublicKeyDelegate).ToPointer(),
            Marshal.GetFunctionPointerForDelegate(getPublicKeyDelegate).ToPointer(),
            Marshal.GetFunctionPointerForDelegate(addPrivateKeyDelegate).ToPointer(),
            Marshal.GetFunctionPointerForDelegate(getPrivateKeyDelegate).ToPointer(),
            Marshal.GetFunctionPointerForDelegate(setKeyPairVerifiedDelegate).ToPointer(),
            Marshal.GetFunctionPointerForDelegate(getKeyPairVerifiedDelegate).ToPointer(),
            Marshal.GetFunctionPointerForDelegate(addUserIdDelegate).ToPointer(),
            Marshal.GetFunctionPointerForDelegate(getUserIdDelegate).ToPointer(),
            Marshal.GetFunctionPointerForDelegate(addUserEmailDelegate).ToPointer(),
            Marshal.GetFunctionPointerForDelegate(getUserEmailDelegate).ToPointer(),
            Marshal.GetFunctionPointerForDelegate(addCertificateChainDelegate).ToPointer(),
            Marshal.GetFunctionPointerForDelegate(getCertificateChainDelegate).ToPointer(),
            Marshal.GetFunctionPointerForDelegate(clearDelegate).ToPointer()
        );
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
        Methods.release(_sdk);
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
