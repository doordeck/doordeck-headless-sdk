using System.Windows;

namespace WpfSample.TwoFactorVerify;

public partial class TwoFactorVerify : Window
{
    public TwoFactorVerify()
    {
        InitializeComponent();
    }

    public string TwoFactorCode { get; private set; }

    private void SubmitTwoFactorVerify_Click(object sender, RoutedEventArgs e)
    {
        // Capture the input value
        TwoFactorCode = TwoFactorCodeTextBox.Text;

        // Close the window
        DialogResult = true;
    }
}