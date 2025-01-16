using System.Windows.Data;

namespace WpfSample.Converters;

public class TimestampToTextConverter : IValueConverter
{
    public object Convert(object value, Type targetType, object parameter, System.Globalization.CultureInfo culture)
    {
        if (value is Double timestamp)
        {
            return DateTimeOffset.FromUnixTimeSeconds((long)timestamp).UtcDateTime.ToString("R");
        }
        return string.Empty;
    }

    public object ConvertBack(object value, Type targetType, object parameter, System.Globalization.CultureInfo culture)
    {
        throw new NotImplementedException();
    }
}