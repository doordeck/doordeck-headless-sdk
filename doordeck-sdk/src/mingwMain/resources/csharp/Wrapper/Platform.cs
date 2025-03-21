using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

public class Platform : IResource
{
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_PlatformApi _platform;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._PlatformApi_e__Struct _platformApi;

    private unsafe Doordeck_Headless_Sdk_ExportedSymbols* _symbols;

    unsafe void IResource.Initialize(Doordeck_Headless_Sdk_ExportedSymbols* symbols,
        Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck sdk)
    {
        _symbols = symbols;
        _platform = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.platform_(sdk);
        _platformApi = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformApi;
    }

    unsafe void IResource.Release()
    {
        _symbols->DisposeStablePointer(_platform.pinned);
    }

    public unsafe Task<object> CreateApplication(CreateApplicationData data)
    {
        return Process<object>(_platformApi.createApplication_, null, data);
    }

    public unsafe Task<List<ApplicationResponse>> ListApplications()
    {
        return Process<List<ApplicationResponse>>(null, _platformApi.listApplications_, null);
    }

    public unsafe Task<ApplicationResponse> GetApplication(ApplicationIdData data)
    {
        return Process<ApplicationResponse>(_platformApi.getApplication_, null, data);
    }

    public unsafe Task<object> UpdateApplicationName(UpdateApplicationNameData data)
    {
        return Process<object>(_platformApi.updateApplicationName_, null, data);
    }

    public unsafe Task<object> UpdateApplicationCompanyName(UpdateApplicationCompanyNameData data)
    {
        return Process<object>(_platformApi.updateApplicationCompanyName_, null, data);
    }

    public unsafe Task<object> UpdateApplicationMailingAddress(UpdateApplicationMailingAddressData data)
    {
        return Process<object>(_platformApi.updateApplicationMailingAddress_, null, data);
    }

    public unsafe Task<object> UpdateApplicationPrivacyPolicy(UpdateApplicationPrivacyPolicyData data)
    {
        return Process<object>(_platformApi.updateApplicationPrivacyPolicy_, null, data);
    }

    public unsafe Task<object> UpdateApplicationSupportContact(UpdateApplicationSupportContactData data)
    {
        return Process<object>(_platformApi.updateApplicationSupportContact_, null, data);
    }

    public unsafe Task<object> UpdateApplicationAppLink(UpdateApplicationAppLinkData data)
    {
        return Process<object>(_platformApi.updateApplicationAppLink_, null, data);
    }

    public unsafe Task<object> UpdateApplicationEmailPreferences(UpdateApplicationEmailPreferencesData data)
    {
        return Process<object>(_platformApi.updateApplicationEmailPreferences_, null, data);
    }

    public unsafe Task<object> UpdateApplicationLogoUrl(UpdateApplicationLogoUrlData data)
    {
        return Process<object>(_platformApi.updateApplicationLogoUrl_, null, data);
    }

    public unsafe Task<object> DeleteApplication(ApplicationIdData data)
    {
        return Process<object>(_platformApi.deleteApplication_, null, data);
    }

    public unsafe Task<GetLogoUploadUrlResponse> GetLogoUploadUrl(GetLogoUploadUrlData data)
    {
        return Process<GetLogoUploadUrlResponse>(_platformApi.getLogoUploadUrl_, null, data);
    }

    public unsafe Task<object> AddAuthKey(AddAuthKeyData data)
    {
        return Process<object>(_platformApi.addAuthKey_, null, data);
    }

    public unsafe Task<object> AddAuthIssuer(AuthIssuerData data)
    {
        return Process<object>(_platformApi.addAuthIssuer_, null, data);
    }

    public unsafe Task<object> DeleteAuthIssuer(AuthIssuerData data)
    {
        return Process<object>(_platformApi.deleteAuthIssuer_, null, data);
    }

    public unsafe Task<object> AddCorsDomain(CorsDomainData data)
    {
        return Process<object>(_platformApi.addCorsDomain_, null, data);
    }

    public unsafe Task<object> RemoveCorsDomain(CorsDomainData data)
    {
        return Process<object>(_platformApi.removeCorsDomain_, null, data);
    }

    public unsafe Task<object> AddApplicationOwner(ApplicationOwnerData data)
    {
        return Process<object>(_platformApi.addApplicationOwner_, null, data);
    }

    public unsafe Task<object> RemoveApplicationOwner(ApplicationOwnerData data)
    {
        return Process<object>(_platformApi.removeApplicationOwner_, null, data);
    }

    public unsafe Task<List<ApplicationOwnerDetailsResponse>> GetApplicationOwnersDetails(ApplicationOwnerData data)
    {
        return Process<List<ApplicationOwnerDetailsResponse>>(_platformApi.getApplicationOwnersDetails_, null, data);
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
                processWithData(_platform, sData, callbackPointer.ToPointer());
            }
            else
            {
                processWithoutData(_platform, callbackPointer.ToPointer());
            }
        }
        finally
        {
            if (data != null) Marshal.FreeHGlobal((IntPtr)sData);
        }

        return tcs.Task;
    }
}