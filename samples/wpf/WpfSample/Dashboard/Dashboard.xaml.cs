using System.Collections.ObjectModel;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;
using MaterialDesignThemes.Wpf;
using WpfSample.Login;

namespace WpfSample.Dashboard;

public partial class Dashboard
{
    private LockResponse? _selectedLock;

    public Dashboard()
    {
        InitializeComponent();
        Load();
    }

    public ObservableCollection<SiteResponse> Sites { get; } = [];
    public ObservableCollection<LockResponse> Locks { get; } = [];
    private ObservableCollection<UserLockResponse> LockUsers { get; } = [];
    private ObservableCollection<UserLockResponse> LockAdmins { get; } = [];
    public ObservableCollection<AuditResponse> Audits { get; } = [];

    private void Load()
    {
        DataContext = this;

        var auditEnd = DateTimeOffset.Now.AddDays(1);
        var auditStart = DateTimeOffset.Now.Subtract(TimeSpan.FromDays(7));
        StartDatePicker.SelectedDate = auditStart.DateTime;
        EndDatePicker.SelectedDate = auditEnd.DateTime;

        var adminsViewSource = (CollectionViewSource)Resources["AdminsView"]!;
        adminsViewSource.Source = LockAdmins;

        var usersViewSource = (CollectionViewSource)Resources["UsersView"]!;
        usersViewSource.Source = LockUsers;

        LoadSites();
    }

    private async void LoadLockUsers(Guid lockId)
    {
        try
        {
            // Clear users
            LockUsers.Clear();
            LockAdmins.Clear();

            // Load users
            var users = await App.Sdk
                .GetLockOperations()
                .GetUsersForLock(lockId);
            
            users.ForEach(user =>
            {
                if (user.Role == UserRole.USER)
                    LockUsers.Add(user);
                else
                    LockAdmins.Add(user);
            });
        }
        catch (Exception exception)
        {
            Console.Error.WriteLine("Failed to get users for lock: {0}", exception.Message);
        }
    }

    private async void LoadLockAudit(Guid lockId)
    {
        try
        {
            if (StartDatePicker.SelectedDate == null || EndDatePicker.SelectedDate == null) return;

            // Clear audits
            Audits.Clear();

            // Load audit
            var lockAudit = await App.Sdk
                .GetLockOperations()
                .GetLockAuditTrail(lockId,
                    StartDatePicker.SelectedDate.Value,
                    EndDatePicker.SelectedDate.Value);
            
            lockAudit.ForEach(audit => Audits.Add(audit));
        }
        catch (Exception exception)
        {
            Console.Error.WriteLine("Failed to get lock audit for lock: {0}", exception.Message);
        }
    }

    private async void LoadLocksForSite(Guid siteId)
    {
        try
        {
            // Clear locks
            Locks.Clear();

            // Load locks
            var siteLocks = await App.Sdk
                .GetSites()
                .GetLocksForSite(siteId);
        
            siteLocks.ForEach(device => Locks.Add(device));
        }
        catch (Exception exception)
        {
            Console.Error.WriteLine("Failed to get locks for site: {0}", exception.Message);
        }
    }

    private async void LoadSites()
    {
        try
        {
            // Clear sites
            Sites.Clear();

            // Load sites
            var sites = await App.Sdk
                .GetSites()
                .ListSites();
        
            sites.ForEach(site => Sites.Add(site));
        }
        catch (Exception exception)
        {
            Console.Error.WriteLine("Failed to list sites: {0}", exception.Message);
        }
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

    private async void DeleteUser_Click(object sender, RoutedEventArgs e)
    {
        try
        {
            if (sender is not PackIcon { DataContext: UserLockResponse lockUser } ||
                _selectedLock == null) return;
        
            var result = MessageBox.Show(
                "Are you sure you want to remove this user from the lock?",
                "Confirmation",
                MessageBoxButton.YesNo,
                MessageBoxImage.Question);

            if (result != MessageBoxResult.Yes) return;
        
            await App.Sdk
                .GetLockOperations()
                .RevokeAccessToLock(new RevokeAccessToLockOperation(new BaseOperation(_selectedLock.Id),
                    [lockUser.UserId]));

            MessageBox.Show("User successfully removed", "Information", MessageBoxButton.OK,
                MessageBoxImage.Information);

            // Reload users and audit
            LoadLockUsers(_selectedLock.Id);
            LoadLockAudit(_selectedLock.Id);
        }
        catch (Exception exception)
        {
            Console.Error.WriteLine("Failed to revoke lock: {0}", exception.Message);
        }
    }

    private async void Unlock_Click(object sender, RoutedEventArgs e)
    {
        try
        {
            if (_selectedLock == null) return;
            
            // Perform unlock
            await App.Sdk
                .GetLockOperations()
                .Unlock(new UnlockOperation(new BaseOperation(_selectedLock.Id)));
            
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

    private async void Share_Click(object sender, RoutedEventArgs e)
    {
        try
        {
            if (_selectedLock == null) return;

            var shareLockWindow = new ShareLock.ShareLock();
            if (shareLockWindow.ShowDialog() == false) return;
            
            var publicKey = await App.Sdk
                .GetLockOperations()
                .GetUserPublicKey(shareLockWindow.Email);

            await App.Sdk
                .GetLockOperations()
                .ShareLock(new ShareLockOperation(new BaseOperation(_selectedLock.Id),
                    new Doordeck.Headless.Sdk.Model.ShareLock(publicKey.Id, shareLockWindow.IsAdmin ? UserRole.ADMIN : UserRole.USER,
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
        if (sender is not ListBox { SelectedItem: SiteResponse site }) return;
        
        _selectedLock = null;
        Locks.Clear();
        LockUsers.Clear();
        LockAdmins.Clear();
        Audits.Clear();

        LoadLocksForSite(site.Id);
    }

    private void Lock_SelectionChanged(object sender, SelectionChangedEventArgs e)
    {
        if (sender is not ListBox { SelectedItem: LockResponse siteLock }) return;
        
        _selectedLock = siteLock;

        // Reload users and audit
        LoadLockUsers(siteLock.Id);
        LoadLockAudit(siteLock.Id);
    }

    private void DatePicker_SelectedDateChanged(object sender, SelectionChangedEventArgs e)
    {
        if (!StartDatePicker.SelectedDate.HasValue || !EndDatePicker.SelectedDate.HasValue ||
            _selectedLock == null) return;
        
        var startDate = StartDatePicker.SelectedDate.Value;
        var endDate = EndDatePicker.SelectedDate.Value;

        if (startDate < endDate)
            LoadLockAudit(_selectedLock.Id);
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

    private async void Logout_Click(object sender, RoutedEventArgs e)
    {
        try
        {
            // Reset everything
            Sites.Clear();
            Locks.Clear();
            LockUsers.Clear();
            LockAdmins.Clear();
            Audits.Clear();
            _selectedLock = null;

            // Logout
            await App.Sdk
                .GetAccount()
                .Logout();

            // Redirect to log in
            var loginWindow = new LoginWindow();
            loginWindow.Show();

            // Close dashboard
            Close();
        }
        catch (Exception exception)
        {
            Console.Error.WriteLine("Failed to log out: {0}", exception.Message);
        }
    }
}