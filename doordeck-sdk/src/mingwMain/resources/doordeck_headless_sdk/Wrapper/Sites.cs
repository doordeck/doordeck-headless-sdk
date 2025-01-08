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
        _sites = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.sites(sdk);
        _sitesResource = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.SitesResource;
    }

    public void Release()
    {
        _symbols->DisposeStablePointer(_sites.pinned);
    }

    public List<SiteResponse> ListSites()
    {
        return ProcessSitesResource<List<SiteResponse>>(
            null,
            null,
            _sitesResource.listSitesJson,
            null
        );
    }

    public List<SiteLocksResponse> GetLocksForSite(GetLocksForSiteData data)
    {
        return ProcessSitesResource<List<SiteLocksResponse>>(
            _sitesResource.getLocksForSiteJson,
            null,
            null,
            data
        );
    }

    public List<UserForSiteResponse> GetUsersForSite(GetUsersForSiteData data)
    {
        return ProcessSitesResource<List<UserForSiteResponse>>(
            _sitesResource.getUsersForSiteJson,
            null,
            null,
            data
        );
    }

    private TResponse ProcessSitesResource<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_SitesResource,
            sbyte*, sbyte*> withDataAndWithResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_SitesResource,
            sbyte*, void> withDataAndWithoutResponse,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_SitesResource,
            sbyte*> withoutDataAndWithResponse,
        object? data
    )
    {
        var sData = data != null ? data.ToData() : null;
        sbyte* result = null;
        try
        {
            var withResponse = typeof(TResponse) != typeof(object);
            var withData = data != null;

            if (withData && withResponse)
                result = withDataAndWithResponse(_sites, sData);
            else if (withData && !withResponse)
                withDataAndWithoutResponse(_sites, sData);
            else if (!withData && withResponse)
                result = withoutDataAndWithResponse(_sites);
            return result != null ? Utils.Utils.FromData<TResponse>(result)! : default!;
        }
        finally
        {
            if (data != null) Marshal.FreeHGlobal((IntPtr)sData);

            if (result != null) _symbols->DisposeString(result);
        }
    }
}