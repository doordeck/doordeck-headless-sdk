using System.Windows;

namespace WpfSample.TwoFactorVerify;

public partial class TwoFactorVerify : Window
{
    public string TwoFactorCode { get; private set; }
    
    public TwoFactorVerify()
    {
        InitializeComponent();
    }
    
    private void SubmitTwoFactorVerify_Click(object sender, RoutedEventArgs e)
    {
        // Capture the input value
        TwoFactorCode = TwoFactorCodeTextBox.Text;

        // Close the window
        DialogResult = true;
    }
}