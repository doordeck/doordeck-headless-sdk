using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

public class Tiles : IResource
{
    private unsafe Doordeck_Headless_Sdk_ExportedSymbols* _symbols;
    private Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_TilesApi _tiles;

    private Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._TilesApi_e__Struct _tilesApi;

    unsafe void IResource.Initialize(Doordeck_Headless_Sdk_ExportedSymbols* symbols,
        Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_Doordeck sdk)
    {
        _symbols = symbols;
        _tiles = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.tiles_(sdk);
        _tilesApi = _symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.TilesApi;
    }

    unsafe void IResource.Release()
    {
        _symbols->DisposeStablePointer(_tiles.pinned);
    }

    public unsafe Task<TileLocksResponse> GetLocksBelongingToTile(GetLocksBelongingToTileData data)
    {
        return Process<TileLocksResponse>(_tilesApi.getLocksBelongingToTile_, null, data);
    }

    public unsafe Task<object> AssociateMultipleLocks(AssociateMultipleLocksData data)
    {
        return Process<object>(_tilesApi.associateMultipleLocks_, null, data);
    }

    private unsafe Task<TResponse> Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_TilesApi,
            sbyte*, void*, void> processWithData,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_TilesApi,
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
                processWithData(_tiles, sData, callbackPointer.ToPointer());
            }
            else
            {
                processWithoutData(_tiles, callbackPointer.ToPointer());
            }
        }
        finally
        {
            if (data != null) Marshal.FreeHGlobal((IntPtr)sData);
        }

        return tcs.Task;
    }
}