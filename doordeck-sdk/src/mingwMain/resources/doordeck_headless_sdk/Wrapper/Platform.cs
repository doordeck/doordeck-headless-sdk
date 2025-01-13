using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

public unsafe class Platform : IResource
{
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_PlatformResource _platform;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._PlatformResource_e__Struct _platformResource;

    private Doordeck_Headless_Sdk_ExportedSymbols* _symbols;

    public void Initialize(Doordeck_Headless_Sdk_ExportedSymbols* symbols,
        Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck sdk)
    {
        _symbols = symbols;
        _platform = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.platform(sdk);
        _platformResource = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource;
    }

    public void Release()
    {
        _symbols->DisposeStablePointer(_platform.pinned);
    }

    public void CreateApplication(CreateApplicationData data)
    {
        Process<object>(
            _platformResource.createApplicationJson,
            null,
            data
        );
    }

    public List<ApplicationResponse> ListApplications()
    {
        return Process<List<ApplicationResponse>>(
            null,
            _platformResource.listApplicationsJson,
            null
        );
    }

    public ApplicationResponse GetApplication(GetApplicationData data)
    {
        return Process<ApplicationResponse>(
            _platformResource.getApplicationJson,
            null,
            data
        );
    }

    public void UpdateApplicationName(UpdateApplicationNameData data)
    {
        Process<object>(
            _platformResource.updateApplicationNameJson,
            null,
            data
        );
    }

    public void UpdateApplicationCompanyName(UpdateApplicationCompanyNameData data)
    {
        Process<object>(
            _platformResource.updateApplicationCompanyNameJson,
            null,
            data
        );
    }

    public void UpdateApplicationMailingAddress(UpdateApplicationMailingAddressData data)
    {
        Process<object>(
            _platformResource.updateApplicationMailingAddressJson,
            null,
            data
        );
    }

    public void UpdateApplicationPrivacyPolicy(UpdateApplicationPrivacyPolicyData data)
    {
        Process<object>(
            _platformResource.updateApplicationPrivacyPolicyJson,
            null,
            data
        );
    }

    public void UpdateApplicationSupportContact(UpdateApplicationSupportContactData data)
    {
        Process<object>(
            _platformResource.updateApplicationSupportContactJson,
            null,
            data
        );
    }

    public void UpdateApplicationAppLink(UpdateApplicationAppLinkData data)
    {
        Process<object>(
            _platformResource.updateApplicationAppLinkJson,
            null,
            data
        );
    }

    public void UpdateApplicationEmailPreferences(UpdateApplicationEmailPreferencesData data)
    {
        Process<object>(
            _platformResource.updateApplicationEmailPreferencesJson,
            null,
            data
        );
    }

    public void UpdateApplicationLogoUrl(UpdateApplicationLogoUrlData data)
    {
        Process<object>(
            _platformResource.updateApplicationLogoUrlJson,
            null,
            data
        );
    }

    public void DeleteApplication(DeleteApplicationData data)
    {
        Process<object>(
            _platformResource.deleteApplicationJson,
            null,
            data
        );
    }

    public GetLogoUploadUrlResponse GetLogoUploadUrl(GetLogoUploadUrlData data)
    {
        return Process<GetLogoUploadUrlResponse>(
            _platformResource.getLogoUploadUrlJson,
            null,
            data
        );
    }

    public void AddAuthKey(AddAuthKeyData data)
    {
        Process<object>(
            _platformResource.addAuthKeyJson,
            null,
            data
        );
    }

    public void AddAuthIssuer(AddAuthIssuerData data)
    {
        Process<object>(
            _platformResource.addAuthIssuerJson,
            null,
            data
        );
    }

    public void DeleteAuthIssuer(DeleteAuthIssuerData data)
    {
        Process<object>(
            _platformResource.deleteAuthIssuerJson,
            null,
            data
        );
    }

    public void AddCorsDomain(AddCorsDomainData data)
    {
        Process<object>(
            _platformResource.addCorsDomainJson,
            null,
            data
        );
    }

    public void RemoveCorsDomain(RemoveCorsDomainData data)
    {
        Process<object>(
            _platformResource.removeCorsDomainJson,
            null,
            data
        );
    }

    public void AddApplicationOwner(AddApplicationOwnerData data)
    {
        Process<object>(
            _platformResource.addApplicationOwnerJson,
            null,
            data
        );
    }

    public void RemoveApplicationOwner(RemoveApplicationOwnerData data)
    {
        Process<object>(
            _platformResource.removeApplicationOwnerJson,
            null,
            data
        );
    }

    public List<ApplicationOwnerDetailsResponse> GetApplicationOwnersDetails(GetApplicationOwnersDetailsData data)
    {
        return Process<List<ApplicationOwnerDetailsResponse>>(
            _platformResource.getApplicationOwnersDetailsJson,
            null,
            data
        );
    }

    private TResponse Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_PlatformResource,
            sbyte*, sbyte*> processDataWithResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_PlatformResource,
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