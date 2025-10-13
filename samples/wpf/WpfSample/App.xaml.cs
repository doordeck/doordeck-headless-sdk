using System.Windows;
using Doordeck.Headless.Sdk;
using Doordeck.Headless.Sdk.Model;

namespace WpfSample;

/// <summary>
///     Interaction logic for App.xaml
/// </summary>
public partial class App
{
    public static readonly DoordeckSdk Sdk = new(ApiEnvironment.DEV);

    protected override void OnStartup(StartupEventArgs e)
    {
        base.OnStartup(e);

        ShutdownMode = ShutdownMode.OnLastWindowClose;
    }

    protected override void OnExit(ExitEventArgs e)
    {
        Sdk.Release();
    }
}