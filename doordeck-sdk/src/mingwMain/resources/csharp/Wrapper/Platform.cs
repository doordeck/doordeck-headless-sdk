using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

public class Platform(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_PlatformApi platform,
    Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._PlatformApi_e__Struct platformApi) : IResource
{
    public unsafe Task<object> CreateApplication(CreateApplicationData data)
    {
        return Process<object>(platformApi.createApplication_, null, data);
    }

    public unsafe Task<List<ApplicationResponse>> ListApplications()
    {
        return Process<List<ApplicationResponse>>(null, platformApi.listApplications_, null);
    }

    public unsafe Task<ApplicationResponse> GetApplication(ApplicationIdData data)
    {
        return Process<ApplicationResponse>(platformApi.getApplication_, null, data);
    }

    public unsafe Task<object> UpdateApplicationName(UpdateApplicationNameData data)
    {
        return Process<object>(platformApi.updateApplicationName_, null, data);
    }

    public unsafe Task<object> UpdateApplicationCompanyName(UpdateApplicationCompanyNameData data)
    {
        return Process<object>(platformApi.updateApplicationCompanyName_, null, data);
    }

    public unsafe Task<object> UpdateApplicationMailingAddress(UpdateApplicationMailingAddressData data)
    {
        return Process<object>(platformApi.updateApplicationMailingAddress_, null, data);
    }

    public unsafe Task<object> UpdateApplicationPrivacyPolicy(UpdateApplicationPrivacyPolicyData data)
    {
        return Process<object>(platformApi.updateApplicationPrivacyPolicy_, null, data);
    }

    public unsafe Task<object> UpdateApplicationSupportContact(UpdateApplicationSupportContactData data)
    {
        return Process<object>(platformApi.updateApplicationSupportContact_, null, data);
    }

    public unsafe Task<object> UpdateApplicationAppLink(UpdateApplicationAppLinkData data)
    {
        return Process<object>(platformApi.updateApplicationAppLink_, null, data);
    }

    public unsafe Task<object> UpdateApplicationEmailPreferences(UpdateApplicationEmailPreferencesData data)
    {
        return Process<object>(platformApi.updateApplicationEmailPreferences_, null, data);
    }

    public unsafe Task<object> UpdateApplicationLogoUrl(UpdateApplicationLogoUrlData data)
    {
        return Process<object>(platformApi.updateApplicationLogoUrl_, null, data);
    }

    public unsafe Task<object> DeleteApplication(ApplicationIdData data)
    {
        return Process<object>(platformApi.deleteApplication_, null, data);
    }

    public unsafe Task<GetLogoUploadUrlResponse> GetLogoUploadUrl(GetLogoUploadUrlData data)
    {
        return Process<GetLogoUploadUrlResponse>(platformApi.getLogoUploadUrl_, null, data);
    }

    public unsafe Task<object> AddAuthKey(AddAuthKeyData data)
    {
        return Process<object>(platformApi.addAuthKey_, null, data);
    }

    public unsafe Task<object> AddAuthIssuer(AuthIssuerData data)
    {
        return Process<object>(platformApi.addAuthIssuer_, null, data);
    }

    public unsafe Task<object> DeleteAuthIssuer(AuthIssuerData data)
    {
        return Process<object>(platformApi.deleteAuthIssuer_, null, data);
    }

    public unsafe Task<object> AddCorsDomain(CorsDomainData data)
    {
        return Process<object>(platformApi.addCorsDomain_, null, data);
    }

    public unsafe Task<object> RemoveCorsDomain(CorsDomainData data)
    {
        return Process<object>(platformApi.removeCorsDomain_, null, data);
    }

    public unsafe Task<object> AddApplicationOwner(ApplicationOwnerData data)
    {
        return Process<object>(platformApi.addApplicationOwner_, null, data);
    }

    public unsafe Task<object> RemoveApplicationOwner(ApplicationOwnerData data)
    {
        return Process<object>(platformApi.removeApplicationOwner_, null, data);
    }

    public unsafe Task<List<ApplicationOwnerDetailsResponse>> GetApplicationOwnersDetails(ApplicationOwnerData data)
    {
        return Process<List<ApplicationOwnerDetailsResponse>>(platformApi.getApplicationOwnersDetails_, null, data);
    }

    private unsafe Task<TResponse> Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_PlatformApi,
            sbyte*, void*, void> processWithData,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_PlatformApi,
            void*, void> processWithoutData,
        object? data
    )
    {
        var tcs = new TaskCompletionSource<TResponse>();
        var sData = data != null ? data.ToData() : null;
        try
        {
            var holder = new CallbackHolder<TResponse>(tcs);
            IResource.CallbackDelegate callbackDelegate = holder.Callback;
            var callbackPointer = Marshal.GetFunctionPointerForDelegate(callbackDelegate);
            if (data != null)
            {
                processWithData(platform, sData, callbackPointer.ToPointer());
            }
            else
            {
                processWithoutData(platform, callbackPointer.ToPointer());
            }
        }
        finally
        {
            if (data != null) Marshal.FreeHGlobal((IntPtr)sData);
        }

        return tcs.Task;
    }
}