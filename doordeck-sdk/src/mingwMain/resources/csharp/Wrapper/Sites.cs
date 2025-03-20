using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

public class Sites : IResource
{
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_SitesApi _sites;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._SitesApi_e__Struct _sitesApi;

    private unsafe Doordeck_Headless_Sdk_ExportedSymbols* _symbols;

    unsafe void IResource.Initialize(Doordeck_Headless_Sdk_ExportedSymbols* symbols,
        Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck sdk)
    {
        _symbols = symbols;
        _sites = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.sites_(sdk);
        _sitesApi = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.SitesApi;
    }

    unsafe void IResource.Release()
    {
        _symbols->DisposeStablePointer(_sites.pinned);
    }

    public unsafe Task<List<SiteResponse>> ListSites()
    {
        return Process<List<SiteResponse>>(null, _sitesApi.listSites_, null);
    }

    public unsafe Task<List<SiteLocksResponse>> GetLocksForSite(SiteIdData data)
    {
        return Process<List<SiteLocksResponse>>(_sitesApi.getLocksForSite_, null, data);
    }

    public unsafe Task<List<UserForSiteResponse>> GetUsersForSite(SiteIdData data)
    {
        return Process<List<UserForSiteResponse>>(_sitesApi.getUsersForSite_, null, data);
    }

    private unsafe Task<TResponse> Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_SitesApi,
            sbyte*, void*, void> processWithData,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_SitesApi,
            void*, void> processWithoutData,
        object? data
    )
    {
        var tcs = new TaskCompletionSource<TResponse>();
        var sData = data != null ? data.ToData() : null;
        try
        {
            var holder = new CallbackHolder<TResponse>(null, tcs);
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

        return tcs.Task;
    }
}