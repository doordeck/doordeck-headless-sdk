using System.Windows;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Utils;

namespace WpfSample.Login;

/// <summary>
///     Interaction logic for LoginWindow.xaml
/// </summary>
public partial class LoginWindow : Window
{
    public LoginWindow()
    {
        InitializeComponent();
    }

    private async void SubmitLogin_Click(object sender, RoutedEventArgs e)
    {
        try
        {
            // Attempt to log-in
            await App.Sdk
                .GetAccountless()
                .Login(new LoginData(EmailTextBox.Text, PasswordBox.Password));
        }
        catch (Exception exception)
        {
            if (exception is UnauthorizedException)
                MessageBox.Show("Login failed, wrong email or password.", "Error", MessageBoxButton.OK,
                    MessageBoxImage.Error);
            if (exception is BadRequestException)
                MessageBox.Show("Login failed, wrong email or password.", "Error", MessageBoxButton.OK,
                    MessageBoxImage.Error);
            return;
        }

        // Generate a new key pair
        var keyPair = App.Sdk
            .GetCryptoManager()
            .GenerateEncodedKeyPair();

        // Register the key pair
        await App.Sdk
            .GetAccount()
            .RegisterEphemeralKeyWithSecondaryAuthentication(
                new RegisterEphemeralKeyWithSecondaryAuthenticationData(keyPair.Public));

        // Two factor dialog
        var twoFactorWindow = new TwoFactorVerify.TwoFactorVerify();
        if (twoFactorWindow.ShowDialog() == false) return;
        try
        {
            // Attempt to verify the key pair
            var verifyResponse = await App.Sdk
                .GetAccount()
                .VerifyEphemeralKeyRegistration(
                    new VerifyEphemeralKeyRegistrationData(twoFactorWindow.TwoFactorCode, keyPair.Private));

            // Set the operation context
            App.Sdk
                .GetContextManager()
                .SetOperationContext(new OperationContextData(verifyResponse.UserId,
                    verifyResponse.CertificateChain.CertificateChainToString(), keyPair.Public, keyPair.Private));

            // Display the dashboard
            var dashboard = new Dashboard.Dashboard();
            dashboard.Show();

            // Close the login window
            Close();
        }
        catch (Exception exception)
        {
            if (exception is ForbiddenException)
                MessageBox.Show("Code is invalid", "Error", MessageBoxButton.OK, MessageBoxImage.Error);

            if (exception is TooManyRequestsException)
                MessageBox.Show("Too many pending verifications", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
        }
    }
}