using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Wrapper;

namespace Doordeck.Headless.Sdk;

public unsafe class DoordeckSdk(ApiEnvironment apiEnvironment, string? authToken)
{
    private readonly Doordeck_Headless_Sdk_ExportedSymbols* _symbols = Methods.Doordeck_Headless_Sdk_symbols();

    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_model_ApiEnvironment _apiEnvironment;
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

        if (authToken != null)
        {
            var token = Utils.Utils.ToSByte(authToken);
            try
            {
                _sdk = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.KDoordeckFactory.initializeWithAuthToken_(_factory, _apiEnvironment, token);
            }
            finally
            {
                Marshal.FreeHGlobal((IntPtr)token);
            }
        }
        else
        {
            _sdk = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.KDoordeckFactory.initialize_(_factory, _apiEnvironment);
        }

        _account.Initialize(_symbols, _sdk);
        _accountless.Initialize(_symbols, _sdk);
        _fusion.Initialize(_symbols, _sdk);
        _helper.Initialize(_symbols, _sdk);
        _lockOperations.Initialize(_symbols, _sdk);
        _platform.Initialize(_symbols, _sdk);
        _sites.Initialize(_symbols, _sdk);
        _tiles.Initialize(_symbols, _sdk);
        _contextManager.Initialize(_symbols, _sdk);
        _cryptoManager.Initialize(_symbols, _sdk);
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

    public void Release()
    {
        _symbols->DisposeStablePointer(_apiEnvironment.pinned);
        _symbols->DisposeStablePointer(_factory.pinned);
        _symbols->DisposeStablePointer(_sdk.pinned);

        _account.Release();
        _accountless.Release();
        _fusion.Release();
        _helper.Release();
        _lockOperations.Release();
        _platform.Release();
        _sites.Release();
        _tiles.Release();
        _contextManager.Release();
        _cryptoManager.Release();
    }
}