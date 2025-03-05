using System.Collections.ObjectModel;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;
using MaterialDesignThemes.Wpf;
using WpfSample.Login;

namespace WpfSample.Dashboard;

public partial class Dashboard : Window
{
    private SiteLocksResponse? _selectedLock;
    private SiteResponse? _selectedSite;

    public Dashboard()
    {
        InitializeComponent();
        Load();
    }

    public ObservableCollection<SiteResponse> Sites { get; } = [];
    public ObservableCollection<SiteLocksResponse> Locks { get; } = [];
    private ObservableCollection<UserLockResponse> LockUsers { get; } = [];
    private ObservableCollection<UserLockResponse> LockAdmins { get; } = [];
    public ObservableCollection<AuditResponse> Audits { get; } = [];

    private void Load()
    {
        DataContext = this;

        var auditEnd = DateTimeOffset.Now;
        var auditStart = DateTimeOffset.Now.Subtract(TimeSpan.FromDays(7));
        StartDatePicker.SelectedDate = auditStart.DateTime;
        EndDatePicker.SelectedDate = auditEnd.DateTime;

        var adminsViewSource = (CollectionViewSource)Resources["AdminsView"]!;
        adminsViewSource.Source = LockAdmins;

        var usersViewSource = (CollectionViewSource)Resources["UsersView"]!;
        usersViewSource.Source = LockUsers;

        LoadSites();
    }

    private void LoadLockUsers(string lockId)
    {
        // Clear users
        LockUsers.Clear();
        LockAdmins.Clear();

        // Load users
        App.Sdk
            .GetLockOperations()
            .GetUsersForLock(new GetUsersForLockData(lockId))
            .ForEach(user =>
            {
                if (user.Role == UserRole.USER)
                    LockUsers.Add(user);
                else
                    LockAdmins.Add(user);
            });
    }

    private void LoadLockAudit(string lockId)
    {
        if (StartDatePicker.SelectedDate == null || EndDatePicker.SelectedDate == null) return;

        // Clear audits
        Audits.Clear();

        // Load audit
        App.Sdk
            .GetLockOperations()
            .GetLockAuditTrail(new GetLockAuditTrailData(lockId,
                (int)((DateTimeOffset)StartDatePicker.SelectedDate.Value).ToUnixTimeSeconds(),
                (int)((DateTimeOffset)EndDatePicker.SelectedDate.Value).ToUnixTimeSeconds()))
            .ForEach(audit => Audits.Add(audit));
    }

    private void LoadLocksForSite(string siteId)
    {
        // Clear locks
        Locks.Clear();

        // Load locks
        App.Sdk
            .GetSites()
            .GetLocksForSite(new GetLocksForSiteData(siteId))
            .ForEach(device => Locks.Add(device));
    }

    private void LoadSites()
    {
        // Clear sites
        Sites.Clear();

        // Load sites
        App.Sdk
            .GetSites()
            .ListSites()
            .ForEach(site => Sites.Add(site));
    }

    private void SearchUsers_TextChanged(object sender, TextChangedEventArgs e)
    {
        var searchTerm = ((TextBox)sender).Text.ToLower();
        var adminsView = (ListCollectionView)((CollectionViewSource)Resources["AdminsView"]!).View;
        var usersView = (ListCollectionView)((CollectionViewSource)Resources["UsersView"]!).View;

        adminsView.Filter = item =>
        {
            var user = item as UserLockResponse;
            return user != null && user.Email.Contains(searchTerm, StringComparison.CurrentCultureIgnoreCase);
        };
        usersView.Filter = item =>
        {
            var user = item as UserLockResponse;
            return user != null && user.Email.Contains(searchTerm, StringComparison.CurrentCultureIgnoreCase);
        };
    }

    private void DeleteUser_Click(object sender, RoutedEventArgs e)
    {
        if (sender is PackIcon button && button.DataContext is UserLockResponse lockUser && _selectedLock != null)
        {
            var result = MessageBox.Show(
                "Are you sure you want to remove this user from the lock?",
                "Confirmation",
                MessageBoxButton.YesNo,
                MessageBoxImage.Question);

            if (result == MessageBoxResult.Yes)
            {
                App.Sdk
                    .GetLockOperations()
                    .RevokeAccessToLock(new RevokeAccessToLockOperationData(new BaseOperationData(_selectedLock.Id),
                        [lockUser.UserId]));

                MessageBox.Show("User successfully removed", "Information", MessageBoxButton.OK,
                    MessageBoxImage.Information);

                // Reload users and audit
                LoadLockUsers(_selectedLock.Id);
                LoadLockAudit(_selectedLock.Id);
            }
        }
    }

    private void Unlock_Click(object sender, RoutedEventArgs e)
    {
        if (_selectedLock == null) return;

        try
        {
            // Perform unlock
            App.Sdk
                .GetLockOperations()
                .Unlock(new UnlockOperationData(new BaseOperationData(_selectedLock.Id)));
            // Display success message
            MessageBox.Show("Lock successfully unlocked!", "Information", MessageBoxButton.OK,
                MessageBoxImage.Information);
            // Refresh audit list
            LoadLockAudit(_selectedLock.Id);
        }
        catch
        {
            MessageBox.Show("Failed to unlock", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
        }
    }

    private void Share_Click(object sender, RoutedEventArgs e)
    {
        if (_selectedLock == null) return;

        var shareLockWindow = new ShareLock.ShareLock();
        if (shareLockWindow.ShowDialog() == false) return;

        try
        {
            var publicKey = App.Sdk
                .GetLockOperations()
                .GetUserPublicKey(new GetUserPublicKeyData(shareLockWindow.Email));

            App.Sdk
                .GetLockOperations()
                .ShareLock(new ShareLockOperationData(new BaseOperationData(_selectedLock.Id),
                    new ShareLockData(publicKey.Id, shareLockWindow.IsAdmin ? UserRole.ADMIN : UserRole.USER,
                        publicKey.PublicKey)));

            MessageBox.Show("User successfully added", "Information", MessageBoxButton.OK, MessageBoxImage.Information);

            LoadLockUsers(_selectedLock.Id);
            LoadLockAudit(_selectedLock.Id);
        }
        catch
        {
            MessageBox.Show("Failed to share lock", "Error", MessageBoxButton.OK, MessageBoxImage.Error);
        }
    }

    private void Site_SelectionChanged(object sender, SelectionChangedEventArgs e)
    {
        if (sender is ListBox listBox && listBox.SelectedItem is SiteResponse site)
        {
            _selectedLock = null;
            Locks.Clear();
            LockUsers.Clear();
            LockAdmins.Clear();
            Audits.Clear();

            _selectedSite = site;

            LoadLocksForSite(site.Id);
        }
    }

    private void Lock_SelectionChanged(object sender, SelectionChangedEventArgs e)
    {
        if (sender is ListBox listBox && listBox.SelectedItem is SiteLocksResponse siteLock)
        {
            _selectedLock = siteLock;

            // Reload users and audit
            LoadLockUsers(siteLock.Id);
            LoadLockAudit(siteLock.Id);
        }
    }

    private void DatePicker_SelectedDateChanged(object sender, SelectionChangedEventArgs e)
    {
        if (StartDatePicker.SelectedDate.HasValue && EndDatePicker.SelectedDate.HasValue && _selectedLock != null)
        {
            // Capture the input values
            var startDate = StartDatePicker.SelectedDate.Value;
            var endDate = EndDatePicker.SelectedDate.Value;

            if (startDate < endDate)
                LoadLockAudit(_selectedLock.Id);
        }
    }

    private void ChangePassword_Click(object sender, RoutedEventArgs e)
    {
        var passwordChangeWindow = new ChangePassword.ChangePassword();
        passwordChangeWindow.ShowDialog();
    }

    private void ChangeDisplayName_Click(object sender, RoutedEventArgs e)
    {
        var changeDisplayNameWindow = new ChangeDisplayName.ChangeDisplayName();
        changeDisplayNameWindow.ShowDialog();
    }

    private void Logout_Click(object sender, RoutedEventArgs e)
    {
        // Reset everything
        Sites.Clear();
        Locks.Clear();
        LockUsers.Clear();
        LockAdmins.Clear();
        Audits.Clear();
        _selectedLock = null;
        _selectedSite = null;

        // Logout
        App.Sdk
            .GetAccount()
            .Logout();

        // Redirect to log in
        var loginWindow = new LoginWindow();
        loginWindow.Show();

        // Close dashboard
        Close();
    }
}