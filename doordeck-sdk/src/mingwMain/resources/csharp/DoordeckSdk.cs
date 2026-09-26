using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Wrapper;

namespace Doordeck.Headless.Sdk;

public class DoordeckSdk
{
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

    public DoordeckSdk(ApiEnvironment apiEnvironment = ApiEnvironment.PROD, string? cloudAuthToken = null,
        string? cloudRefreshToken = null, string? fusionHost = null, ISecureStorage? secureStorageImpl = null,
        bool? debugLogging = null)
    {
        // Registered before creating the SDK: initialisation reads the secure storage to restore any
        // previously stored context.
        if (secureStorageImpl != null)
        {
            SecureStorage.Implementation = secureStorageImpl;
            SecureStorage.Register();
        }

        Dispatcher.Create<object>(new
        {
            apiEnvironment = apiEnvironment.ToString(),
            cloudAuthToken,
            cloudRefreshToken,
            fusionHost,
            debugLogging = debugLogging ?? false
        }).GetAwaiter().GetResult();
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

    public void Release() => Dispatcher.Release<object>().GetAwaiter().GetResult();
}