using System.Windows;

namespace WpfSample.ShareLock;

public partial class ShareLock
{
    public ShareLock()
    {
        InitializeComponent();
    }

    public string Email { get; private set; } = string.Empty;
    public bool IsAdmin { get; private set; }

    private void SubmitShareLock_Click(object sender, RoutedEventArgs e)
    {
        // Capture the input values
        Email = ShareEmail.Text;
        IsAdmin = ShareRole.Text.Equals("Administrator", StringComparison.OrdinalIgnoreCase);

        // Close the window
        DialogResult = true;
    }
}