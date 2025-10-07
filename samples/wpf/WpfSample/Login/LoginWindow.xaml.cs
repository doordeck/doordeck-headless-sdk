using System.Windows;
using Doordeck.Headless.Sdk.Model;

namespace WpfSample.Login;

/// <summary>
///     Interaction logic for LoginWindow.xaml
/// </summary>
public partial class LoginWindow
{
    public LoginWindow()
    {
        InitializeComponent();
    }

    private async void SubmitLogin_Click(object sender, RoutedEventArgs e)
    {
        // Attempt to log-in
        try
        {
            await App.Sdk
                .GetAccountless()
                .Login(EmailTextBox.Text, PasswordBox.Password);
            
            // Generate a new key pair
            var keyPair = App.Sdk
                .GetCryptoManager()
                .GenerateKeyPair();
            
            // Register the key pair
            await App.Sdk
                .GetAccount()
                .RegisterEphemeralKeyWithSecondaryAuthentication(keyPair.PublicKey);

            var twoFactorWindow = new TwoFactorVerify.TwoFactorVerify();
            if (twoFactorWindow.ShowDialog() == false) return;
            if (twoFactorWindow.TwoFactorCode == null) return;
            
            try
            {
                // Attempt to verify the key pair
                await App.Sdk
                    .GetAccount()
                    .VerifyEphemeralKeyRegistration(twoFactorWindow.TwoFactorCode, keyPair.PublicKey, keyPair.PrivateKey);
            
                // Display the dashboard
                var dashboard = new Dashboard.Dashboard();
                dashboard.Show();

                // Close the login window
                Close();
            }
            catch (Exception exception)
            {
                switch (exception)
                {
                    case ForbiddenException:
                        MessageBox.Show("Code is invalid", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                        break;
                    case TooManyRequestsException:
                        MessageBox.Show("Too many pending verifications", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                        break;
                    default:
                        MessageBox.Show("Unhandled error, please try it again later.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                        break;
                }
            }
        }
        catch (Exception exception)
        {
            switch (exception)
            {
                case UnauthorizedException:
                case BadRequestException:
                    MessageBox.Show("Login failed, wrong email or password.", "Error", MessageBoxButton.OK,
                        MessageBoxImage.Error);
                    break;
                default:
                    MessageBox.Show("Unhandled error, please try it again later.", "Error", MessageBoxButton.OK,
                        MessageBoxImage.Error);
                    break;
            }
        }
    }
}