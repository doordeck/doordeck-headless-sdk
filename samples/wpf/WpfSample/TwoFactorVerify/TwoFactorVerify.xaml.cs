using System.Windows;

namespace WpfSample.TwoFactorVerify;

public partial class TwoFactorVerify : Window
{
    public string TwoFactorCode { get; private set; }
    
    public TwoFactorVerify()
    {
        InitializeComponent();
    }
    
    private void SubmitTwoFactorVerifyButton_Click(object sender, RoutedEventArgs e)
    {
        // Capture the input code
        TwoFactorCode = TwoFactorCodeTextBox.Text;

        // Close the window
        DialogResult = true;
    }
}