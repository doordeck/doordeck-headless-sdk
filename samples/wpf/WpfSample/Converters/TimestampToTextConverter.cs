using System.Globalization;
using System.Windows.Data;

namespace WpfSample.Converters;

public class TimestampToTextConverter : IValueConverter
{
    public object Convert(object? value, Type targetType, object? parameter, CultureInfo culture)
    {
        return value is not double timestamp ? 
            string.Empty : 
            DateTimeOffset.FromUnixTimeSeconds((long)timestamp).UtcDateTime.ToString("R");
    }

    public object ConvertBack(object? value, Type targetType, object? parameter, CultureInfo culture)
    {
        throw new NotImplementedException();
    }
}