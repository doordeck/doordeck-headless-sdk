using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

public unsafe class Sites : IResource
{
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_SitesResource _sites;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._SitesResource_e__Struct _sitesResource;

    private Doordeck_Headless_Sdk_ExportedSymbols* _symbols;

    public void Initialize(Doordeck_Headless_Sdk_ExportedSymbols* symbols,
        Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck sdk)
    {
        _symbols = symbols;
        _sites = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.sites_(sdk);
        _sitesResource = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.SitesResource;
    }

    public void Release()
    {
        _symbols->DisposeStablePointer(_sites.pinned);
    }

    public List<SiteResponse> ListSites()
    {
        return Process<List<SiteResponse>>(
            null,
            _sitesResource.listSitesJson_,
            null
        );
    }

    public List<SiteLocksResponse> GetLocksForSite(GetLocksForSiteData data)
    {
        return Process<List<SiteLocksResponse>>(
            _sitesResource.getLocksForSiteJson_,
            null,
            data
        );
    }

    public List<UserForSiteResponse> GetUsersForSite(GetUsersForSiteData data)
    {
        return Process<List<UserForSiteResponse>>(
            _sitesResource.getUsersForSiteJson_,
            null,
            data
        );
    }

    private TResponse Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_SitesResource,
            sbyte*, sbyte*> processDataWithResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_SitesResource,
            sbyte*> processWithoutDataWithResponse,
        object? data
    )
    {
        var sData = data != null ? data.ToData() : null;
        sbyte* result = null;
        try
        {
            var hasData = data != null;
            result = hasData ? processDataWithResponse(_sites, sData) :
                processWithoutDataWithResponse(_sites);

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