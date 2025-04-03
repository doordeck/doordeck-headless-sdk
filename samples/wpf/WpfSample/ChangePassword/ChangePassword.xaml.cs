using System.Windows;

namespace WpfSample.ChangePassword;

public partial class ChangePassword : Window
{
    public ChangePassword()
    {
        InitializeComponent();
    }

    private async void ChangePassword_Click(object sender, RoutedEventArgs e)
    {
        // Capture the input values
        var currentPassword = CurrentPasswordBox.Password;
        var newPassword = NewPasswordBox.Password;

        try
        {
            await App.Sdk
                .GetAccount()
                .ChangePassword(currentPassword, newPassword);

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