using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Wrapper;

namespace Doordeck.Headless.Sdk;

public class DoordeckSdk
{
    private readonly unsafe Doordeck_Headless_Sdk_ExportedSymbols* _symbols = Methods.Doordeck_Headless_Sdk_symbols();

    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_model_data_ApiEnvironment _apiEnvironment;
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_KDoordeckFactory _factory;
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck _sdk;

    private readonly Account _account = new();
    private readonly Accountless _accountless = new();
    private readonly Fusion _fusion = new();
    private readonly Helper _helper = new();
    private readonly LockOperations _lockOperations = new();
    private readonly Platform _platform = new();
    private readonly Sites _sites = new();
    private readonly Tiles _tiles = new();
    private readonly ContextManager _contextManager = new();
    private readonly CryptoManager _cryptoManager = new();

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

        if (token != null)
        {
            sdkConfig.Builder.setCloudAuthToken(builder, token);
        }

        if (refreshToken != null)
        {
            sdkConfig.Builder.setCloudRefreshToken(builder, refreshToken);
        }

        if (fHost != null)
        {
            sdkConfig.Builder.setFusionHost(builder, fHost);
        }

        try
        {
            _sdk = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.KDoordeckFactory.initialize_(_factory, sdkConfig.Builder.build(builder));
        }
        finally
        {
            if (token != null)
            {
               Marshal.FreeHGlobal((IntPtr)token);
            }
            if (refreshToken != null)
            {
                Marshal.FreeHGlobal((IntPtr)refreshToken);
            }
            if (fHost != null)
            {
                Marshal.FreeHGlobal((IntPtr)fHost);
            }
        }

        ((IResource)_account).Initialize(_symbols, _sdk);
        ((IResource)_accountless).Initialize(_symbols, _sdk);
        ((IResource)_fusion).Initialize(_symbols, _sdk);
        ((IResource)_helper).Initialize(_symbols, _sdk);
        ((IResource)_lockOperations).Initialize(_symbols, _sdk);
        ((IResource)_platform).Initialize(_symbols, _sdk);
        ((IResource)_sites).Initialize(_symbols, _sdk);
        ((IResource)_tiles).Initialize(_symbols, _sdk);
        ((IResource)_contextManager).Initialize(_symbols, _sdk);
        ((IResource)_cryptoManager).Initialize(_symbols, _sdk);
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

        ((IResource)_account).Release();
        ((IResource)_accountless).Release();
        ((IResource)_fusion).Release();
        ((IResource)_helper).Release();
        ((IResource)_lockOperations).Release();
        ((IResource)_platform).Release();
        ((IResource)_sites).Release();
        ((IResource)_tiles).Release();
        ((IResource)_contextManager).Release();
        ((IResource)_cryptoManager).Release();
    }
}