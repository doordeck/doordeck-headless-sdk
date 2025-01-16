using System.Windows;
using System.Windows.Controls;
using Doordeck.Headless.Sdk.Model;

namespace WpfSample.ChangeDisplayName;

public partial class ChangeDisplayName : Window
{
    public ChangeDisplayName()
    {
        InitializeComponent();
    }

    private void ChangeDisplayNameButton_Click(object sender, RoutedEventArgs e)
    {
        if (sender is Button button)
        {
            var displayName = NewDisplayName.Text.Trim(); 
            
            try
            {
                App.Sdk
                    .GetAccount()
                    .UpdateUserDetails(new UpdateUserDetailsData(displayName));
                
                MessageBox.Show("Display name successfully changed!", "Information", MessageBoxButton.OK, MessageBoxImage.Information);
            }
            catch (Exception exception)
            {
                MessageBox.Show("Failed to change display name.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
                return;
            }
            
            Close();
        }
    }
}