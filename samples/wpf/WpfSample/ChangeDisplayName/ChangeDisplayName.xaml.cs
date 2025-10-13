using System.Windows;

namespace WpfSample.ChangeDisplayName;

public partial class ChangeDisplayName
{
    public ChangeDisplayName()
    {
        InitializeComponent();
    }

    private async void ChangeDisplayName_Click(object sender, RoutedEventArgs e)
    {
        try
        {
            var newDisplayName = NewDisplayName.Text.Trim();
            
            await App.Sdk
                .GetAccount()
                .UpdateUserDetails(newDisplayName);

            MessageBox.Show("Display name successfully changed!", "Information", MessageBoxButton.OK,
                MessageBoxImage.Information);
            
            Close();
        }
        catch
        {
            MessageBox.Show("Failed to change display name.", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
        }
    }
}