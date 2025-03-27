using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Wrapper;

namespace Doordeck.Headless.Sdk;

public class DoordeckSdk
{
    private readonly unsafe Doordeck_Headless_Sdk_ExportedSymbols* _symbols = Methods.Doordeck_Headless_Sdk_symbols();

    private readonly Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_model_data_ApiEnvironment _apiEnvironment;
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

    public unsafe DoordeckSdk(ApiEnvironment apiEnvironment, string? cloudAuthToken = null, string? cloudRefreshToken = null, string? fusionHost = null)
    {
        _apiEnvironment = apiEnvironment switch
        {
            ApiEnvironment.DEV => _symbols->kotlin.root.com.doordeck.multiplatform.sdk.model.data.ApiEnvironment.DEV
                .get(),
            ApiEnvironment.STAGING => _symbols->kotlin.root.com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
                .STAGING.get(),
            ApiEnvironment.PROD => _symbols->kotlin.root.com.doordeck.multiplatform.sdk.model.data.ApiEnvironment.PROD
                .get(),
            _ => _apiEnvironment
        };

        _factory = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.KDoordeckFactory._instance();

        var token = cloudAuthToken != null ? Utils.Utils.ToSByte(cloudAuthToken) : null;
        var refreshToken = cloudRefreshToken != null ? Utils.Utils.ToSByte(cloudRefreshToken) : null;
        var fHost = fusionHost != null ? Utils.Utils.ToSByte(fusionHost) : null;
        var sdkConfig = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.config.SdkConfig;
        var builder = sdkConfig.Builder.Builder();
        sdkConfig.Builder.setApiEnvironment(builder, _apiEnvironment);

        if (token != null) sdkConfig.Builder.setCloudAuthToken(builder, token);
        if (refreshToken != null) sdkConfig.Builder.setCloudRefreshToken(builder, refreshToken);
        if (fHost != null) sdkConfig.Builder.setFusionHost(builder, fHost);

        try
        {
            _sdk = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.KDoordeckFactory.initialize_(_factory, sdkConfig.Builder.build(builder));
        }
        finally
        {
            if (token != null) Marshal.FreeHGlobal((IntPtr)token);
            if (refreshToken != null) Marshal.FreeHGlobal((IntPtr)refreshToken);
            if (fHost != null) Marshal.FreeHGlobal((IntPtr)fHost);
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

    public Account GetAccount()
    {
        return _account;
    }

    public Accountless GetAccountless()
    {
        return _accountless;
    }

    public Fusion GetFusion()
    {
        return _fusion;
    }

    public Helper GetHelper()
    {
        return _helper;
    }

    public LockOperations GetLockOperations()
    {
        return _lockOperations;
    }

    public Platform GetPlatform()
    {
        return _platform;
    }

    public Sites GetSites()
    {
        return _sites;
    }

    public Tiles GetTiles()
    {
        return _tiles;
    }

    public ContextManager GetContextManager()
    {
        return _contextManager;
    }

    public CryptoManager GetCryptoManager()
    {
        return _cryptoManager;
    }

    public unsafe void Release()
    {
        _symbols->DisposeStablePointer(_apiEnvironment.pinned);
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