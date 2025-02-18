using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

public unsafe class Platform : IResource
{
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_PlatformApi _platform;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._PlatformApi_e__Struct _platformApi;

    private Doordeck_Headless_Sdk_ExportedSymbols* _symbols;

    public void Initialize(Doordeck_Headless_Sdk_ExportedSymbols* symbols,
        Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck sdk)
    {
        _symbols = symbols;
        _platform = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.platform_(sdk);
        _platformApi = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformApi;
    }

    public void Release()
    {
        _symbols->DisposeStablePointer(_platform.pinned);
    }

    public void CreateApplication(CreateApplicationData data)
    {
        Process<object>(
            _platformApi.createApplicationJson_,
            null,
            data
        );
    }

    public List<ApplicationResponse> ListApplications()
    {
        return Process<List<ApplicationResponse>>(
            null,
            _platformApi.listApplicationsJson_,
            null
        );
    }

    public ApplicationResponse GetApplication(GetApplicationData data)
    {
        return Process<ApplicationResponse>(
            _platformApi.getApplicationJson_,
            null,
            data
        );
    }

    public void UpdateApplicationName(UpdateApplicationNameData data)
    {
        Process<object>(
            _platformApi.updateApplicationNameJson_,
            null,
            data
        );
    }

    public void UpdateApplicationCompanyName(UpdateApplicationCompanyNameData data)
    {
        Process<object>(
            _platformApi.updateApplicationCompanyNameJson_,
            null,
            data
        );
    }

    public void UpdateApplicationMailingAddress(UpdateApplicationMailingAddressData data)
    {
        Process<object>(
            _platformApi.updateApplicationMailingAddressJson_,
            null,
            data
        );
    }

    public void UpdateApplicationPrivacyPolicy(UpdateApplicationPrivacyPolicyData data)
    {
        Process<object>(
            _platformApi.updateApplicationPrivacyPolicyJson_,
            null,
            data
        );
    }

    public void UpdateApplicationSupportContact(UpdateApplicationSupportContactData data)
    {
        Process<object>(
            _platformApi.updateApplicationSupportContactJson_,
            null,
            data
        );
    }

    public void UpdateApplicationAppLink(UpdateApplicationAppLinkData data)
    {
        Process<object>(
            _platformApi.updateApplicationAppLinkJson_,
            null,
            data
        );
    }

    public void UpdateApplicationEmailPreferences(UpdateApplicationEmailPreferencesData data)
    {
        Process<object>(
            _platformApi.updateApplicationEmailPreferencesJson_,
            null,
            data
        );
    }

    public void UpdateApplicationLogoUrl(UpdateApplicationLogoUrlData data)
    {
        Process<object>(
            _platformApi.updateApplicationLogoUrlJson_,
            null,
            data
        );
    }

    public void DeleteApplication(DeleteApplicationData data)
    {
        Process<object>(
            _platformApi.deleteApplicationJson_,
            null,
            data
        );
    }

    public GetLogoUploadUrlResponse GetLogoUploadUrl(GetLogoUploadUrlData data)
    {
        return Process<GetLogoUploadUrlResponse>(
            _platformApi.getLogoUploadUrlJson_,
            null,
            data
        );
    }

    public void AddAuthKey(AddAuthKeyData data)
    {
        Process<object>(
            _platformApi.addAuthKeyJson_,
            null,
            data
        );
    }

    public void AddAuthIssuer(AddAuthIssuerData data)
    {
        Process<object>(
            _platformApi.addAuthIssuerJson_,
            null,
            data
        );
    }

    public void DeleteAuthIssuer(DeleteAuthIssuerData data)
    {
        Process<object>(
            _platformApi.deleteAuthIssuerJson_,
            null,
            data
        );
    }

    public void AddCorsDomain(AddCorsDomainData data)
    {
        Process<object>(
            _platformApi.addCorsDomainJson_,
            null,
            data
        );
    }

    public void RemoveCorsDomain(RemoveCorsDomainData data)
    {
        Process<object>(
            _platformApi.removeCorsDomainJson_,
            null,
            data
        );
    }

    public void AddApplicationOwner(AddApplicationOwnerData data)
    {
        Process<object>(
            _platformApi.addApplicationOwnerJson_,
            null,
            data
        );
    }

    public void RemoveApplicationOwner(RemoveApplicationOwnerData data)
    {
        Process<object>(
            _platformApi.removeApplicationOwnerJson_,
            null,
            data
        );
    }

    public List<ApplicationOwnerDetailsResponse> GetApplicationOwnersDetails(GetApplicationOwnersDetailsData data)
    {
        return Process<List<ApplicationOwnerDetailsResponse>>(
            _platformApi.getApplicationOwnersDetailsJson_,
            null,
            data
        );
    }

    private TResponse Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_PlatformApi,
            sbyte*, sbyte*> processDataWithResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_PlatformApi,
            sbyte*> processWithoutDataWithResponse,
        object? data
    )
    {
        var sData = data != null ? data.ToData() : null;
        sbyte* result = null;
        try
        {
            var hasData = data != null;
            result = hasData ? processDataWithResponse(_platform, sData) :
                processWithoutDataWithResponse(_platform);

            var resultData = result != null
                ? Utils.Utils.FromData<ResultData<TResponse>>(result)
                : default!;

            resultData.HandleException();

            return resultData.Success!.Result ?? default!;
        }
        finally
        {
            if (data != null) Marshal.FreeHGlobal((IntPtr)sData);

            if (result != null) _symbols->DisposeString(result);
        }
    }
}