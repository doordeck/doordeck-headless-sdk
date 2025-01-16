using System.Windows.Data;
using Doordeck.Headless.Sdk.Model;
using Microsoft.VisualBasic.CompilerServices;

namespace WpfSample.Converters;

public class AuditEventToTextConverter : IValueConverter
{
    public object Convert(object value, Type targetType, object parameter, System.Globalization.CultureInfo culture)
    {
        if (value is not AuditEvent type) return string.Empty;
        var lowerCase = type.ToString().ToLower().Replace("_", " ");
        return char.ToUpper(lowerCase[0]) + lowerCase[1..] + " by ";
    }

    public object ConvertBack(object value, Type targetType, object parameter, System.Globalization.CultureInfo culture)
    {
        throw new NotImplementedException();
    }
}