using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

public unsafe class Sites : IResource
{
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_SitesApi _sites;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._SitesApi_e__Struct _sitesApi;

    private Doordeck_Headless_Sdk_ExportedSymbols* _symbols;

    void IResource.Initialize(Doordeck_Headless_Sdk_ExportedSymbols* symbols,
        Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck sdk)
    {
        _symbols = symbols;
        _sites = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.sites_(sdk);
        _sitesApi = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.SitesApi;
    }

    void IResource.Release()
    {
        _symbols->DisposeStablePointer(_sites.pinned);
    }

    public void ListSites(Action<List<SiteResponse>> action)
    {
        Process(null, _sitesApi.listSites_, action, null);
    }

    public void GetLocksForSite(SiteIdData data, Action<List<SiteLocksResponse>> action)
    {
        Process(_sitesApi.getLocksForSite_, null, action, data);
    }

    public void GetUsersForSite(SiteIdData data, Action<List<UserForSiteResponse>> action)
    {
        Process(_sitesApi.getUsersForSite_, null, action, data);
    }

    private void Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_SitesApi,
            sbyte*, void*, void> processWithData,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_SitesApi,
            void*, void> processWithoutData,
        Action<TResponse> userCallback,
        object? data
    )
    {
        var sData = data != null ? data.ToData() : null;
        try
        {
            var holder = new CallbackHolder<TResponse>(userCallback);
            IResource.CallbackDelegate callbackDelegate = holder.Callback;
            var callbackPointer = Marshal.GetFunctionPointerForDelegate(callbackDelegate);
            if (data != null)
            {
                processWithData(_sites, sData, callbackPointer.ToPointer());
            }
            else
            {
                processWithoutData(_sites, callbackPointer.ToPointer());
            }
        }
        finally
        {
            if (data != null) Marshal.FreeHGlobal((IntPtr)sData);
        }
    }
}