using System.Globalization;
using System.Windows.Data;

namespace WpfSample.Converters;

public class DateTimeToLocalTimeConverter : IValueConverter
{
    public object Convert(object? value, Type targetType, object? parameter, CultureInfo culture)
    {
        return value is DateTime dt ? dt.ToLocalTime() : string.Empty;
    }

    public object ConvertBack(object? value, Type targetType, object? parameter, CultureInfo culture)
    {
        throw new NotImplementedException();
    }
}