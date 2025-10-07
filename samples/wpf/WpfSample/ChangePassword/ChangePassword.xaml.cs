using System.Windows;

namespace WpfSample.ChangePassword;

public partial class ChangePassword
{
    public ChangePassword()
    {
        InitializeComponent();
    }

    private async void ChangePassword_Click(object sender, RoutedEventArgs e)
    {
        try
        {
            var currentPassword = CurrentPasswordBox.Password;
            var newPassword = NewPasswordBox.Password;
            
            await App.Sdk
                .GetAccount()
                .ChangePassword(currentPassword, newPassword);

            MessageBox.Show("Password successfully changed!", "Information", MessageBoxButton.OK,
                MessageBoxImage.Information);
            
            Close();
        }
        catch
        {
            MessageBox.Show("Failed to change password.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
        }
    }
}