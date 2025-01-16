using System.Windows;
using System.Windows.Controls;
using Doordeck.Headless.Sdk.Model;

namespace WpfSample.ChangePassword;

public partial class ChangePassword : Window
{
    public ChangePassword()
    {
        InitializeComponent();
    }
    
    private void ChangePasswordButton_Click(object sender, RoutedEventArgs e)
    {
        if (sender is Button button)
        {
            var currentPassword = CurrentPasswordBox.Password; 
            var newPassword = NewPasswordBox.Password;

            try
            {
                App.Sdk
                    .GetAccount()
                    .ChangePassword(new ChangePasswordData(currentPassword, newPassword));
                
                MessageBox.Show("Password successfully changed!", "Information", MessageBoxButton.OK, MessageBoxImage.Information);
            }
            catch (Exception exception)
            {
                MessageBox.Show("Failed to change password.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }
            
            Close();
        }
    }
}