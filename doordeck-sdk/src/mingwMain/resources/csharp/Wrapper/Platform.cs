using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;


public class Platform
{
    public Task<Guid> CreateApplication(CreateApplication data) =>
        Dispatcher.Call<Guid>("platform.createApplication", data);

    public Task<List<ApplicationResponse>> ListApplications() =>
        Dispatcher.Call<List<ApplicationResponse>>("platform.listApplications");

    public Task<ApplicationResponse> GetApplication(Guid applicationId) =>
        Dispatcher.Call<ApplicationResponse>("platform.getApplication", new { applicationId });

    public Task<object> UpdateApplicationName(Guid applicationId, string name) =>
        Dispatcher.Call<object>("platform.updateApplicationName", new { applicationId, name });

    public Task<object> UpdateApplicationCompanyName(Guid applicationId, string companyName) =>
        Dispatcher.Call<object>("platform.updateApplicationCompanyName", new { applicationId, companyName });

    public Task<object> UpdateApplicationMailingAddress(Guid applicationId, string mailingAddress) =>
        Dispatcher.Call<object>("platform.updateApplicationMailingAddress", new { applicationId, mailingAddress });

    public Task<object> UpdateApplicationPrivacyPolicy(Guid applicationId, Uri privacyPolicy) =>
        Dispatcher.Call<object>("platform.updateApplicationPrivacyPolicy", new { applicationId, privacyPolicy });

    public Task<object> UpdateApplicationSupportContact(Guid applicationId, Uri supportContact) =>
        Dispatcher.Call<object>("platform.updateApplicationSupportContact", new { applicationId, supportContact });

    public Task<object> UpdateApplicationAppLink(Guid applicationId, Uri appLink) =>
        Dispatcher.Call<object>("platform.updateApplicationAppLink", new { applicationId, appLink });

    public Task<object> UpdateApplicationEmailPreferences(Guid applicationId, EmailPreferences emailPreferences) =>
        Dispatcher.Call<object>("platform.updateApplicationEmailPreferences", new { applicationId, emailPreferences });

    public Task<object> UpdateApplicationLogoUrl(Guid applicationId, Uri logoUrl) =>
        Dispatcher.Call<object>("platform.updateApplicationLogoUrl", new { applicationId, logoUrl });

    public Task<object> DeleteApplication(Guid applicationId) =>
        Dispatcher.Call<object>("platform.deleteApplication", new { applicationId });

    public Task<GetLogoUploadUrlResponse> GetLogoUploadUrl(Guid applicationId, string contentType) =>
        Dispatcher.Call<GetLogoUploadUrlResponse>("platform.getLogoUploadUrl", new { applicationId, contentType });

    public Task<object> AddAuthKey(Guid applicationId, IAuthKey key) =>
        Dispatcher.Call<object>("platform.addAuthKey", new { applicationId, key });

    public Task<object> AddAuthIssuer(Guid applicationId, Uri url) =>
        Dispatcher.Call<object>("platform.addAuthIssuer", new { applicationId, url });

    public Task<object> DeleteAuthIssuer(Guid applicationId, Uri url) =>
        Dispatcher.Call<object>("platform.deleteAuthIssuer", new { applicationId, url });

    public Task<object> AddCorsDomain(Guid applicationId, Uri url) =>
        Dispatcher.Call<object>("platform.addCorsDomain", new { applicationId, url });

    public Task<object> RemoveCorsDomain(Guid applicationId, Uri url) =>
        Dispatcher.Call<object>("platform.removeCorsDomain", new { applicationId, url });

    public Task<object> AddApplicationOwner(Guid applicationId, Guid userId) =>
        Dispatcher.Call<object>("platform.addApplicationOwner", new { applicationId, userId });

    public Task<object> RemoveApplicationOwner(Guid applicationId, Guid userId) =>
        Dispatcher.Call<object>("platform.removeApplicationOwner", new { applicationId, userId });

    public Task<List<ApplicationOwnerDetailsResponse>> GetApplicationOwnersDetails(Guid applicationId, Guid userId) =>
        Dispatcher.Call<List<ApplicationOwnerDetailsResponse>>("platform.getApplicationOwnersDetails", new { applicationId, userId });

    public Task<List<ApplicationUserResponse>> GetApplicationUsers(Guid applicationId, int pageSize = 100, Guid? lastUserRetrieved = null) =>
        Dispatcher.Call<List<ApplicationUserResponse>>("platform.getApplicationUsers", new { applicationId, pageSize, lastUserRetrieved });
}