using System.Windows;
using Doordeck.Headless.Sdk.Model;

namespace WpfSample.ChangePassword;

public partial class ChangePassword : Window
{
    public ChangePassword()
    {
        InitializeComponent();
    }

    private void ChangePassword_Click(object sender, RoutedEventArgs e)
    {
        // Capture the input values
        var currentPassword = CurrentPasswordBox.Password;
        var newPassword = NewPasswordBox.Password;

        try
        {
            App.Sdk
                .GetAccount()
                .ChangePassword(new ChangePasswordData(currentPassword, newPassword));

            MessageBox.Show("Password successfully changed!", "Information", MessageBoxButton.OK,
                MessageBoxImage.Information);
        }
        catch
        {
            MessageBox.Show("Failed to change password.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            return;
        }

        Close();
    }
}