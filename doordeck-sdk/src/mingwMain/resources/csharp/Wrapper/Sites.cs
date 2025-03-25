using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

public class Sites(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_SitesApi sites,
    Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._SitesApi_e__Struct sitesApi) : IResource
{

    public unsafe Task<List<SiteResponse>> ListSites()
    {
        return Process<List<SiteResponse>>(null, sitesApi.listSites_, null);
    }

    public unsafe Task<List<SiteLocksResponse>> GetLocksForSite(SiteIdData data)
    {
        return Process<List<SiteLocksResponse>>(sitesApi.getLocksForSite_, null, data);
    }

    public unsafe Task<List<UserForSiteResponse>> GetUsersForSite(SiteIdData data)
    {
        return Process<List<UserForSiteResponse>>(sitesApi.getUsersForSite_, null, data);
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
            var holder = new CallbackHolder<TResponse>(tcs);
            IResource.CallbackDelegate callbackDelegate = holder.Callback;
            var callbackPointer = Marshal.GetFunctionPointerForDelegate(callbackDelegate);
            if (data != null)
            {
                processWithData(sites, sData, callbackPointer.ToPointer());
            }
            else
            {
                processWithoutData(sites, callbackPointer.ToPointer());
            }
        }
        finally
        {
            if (data != null) Marshal.FreeHGlobal((IntPtr)sData);
        }

        return tcs.Task;
    }
}