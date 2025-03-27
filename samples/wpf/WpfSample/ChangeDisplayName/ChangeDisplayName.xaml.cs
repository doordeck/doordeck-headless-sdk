using System.Windows;
using Doordeck.Headless.Sdk.Model;

namespace WpfSample.ChangeDisplayName;

public partial class ChangeDisplayName : Window
{
    public ChangeDisplayName()
    {
        InitializeComponent();
    }

    private async void ChangeDisplayName_Click(object sender, RoutedEventArgs e)
    {
        // Capture the input value
        var newDisplayName = NewDisplayName.Text.Trim();

        try
        {
            await App.Sdk
                .GetAccount()
                .UpdateUserDetails(new UpdateUserDetailsData(newDisplayName));

            MessageBox.Show("Display name successfully changed!", "Information", MessageBoxButton.OK,
                MessageBoxImage.Information);
        }
        catch
        {
            MessageBox.Show("Failed to change display name.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
            return;
        }

        Close();
    }
}