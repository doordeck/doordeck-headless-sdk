using System.Runtime.InteropServices;
using Doordeck.Headless.Sdk.Callback;
using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;
using Doordeck.Headless.Sdk.Utils;

namespace Doordeck.Headless.Sdk.Wrapper;

public class Tiles(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_TilesApi tiles,
    Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._TilesApi_e__Struct tilesApi)
{
    public unsafe Task<TileLocksResponse> GetLocksBelongingToTile(GetLocksBelongingToTileData data)
    {
        return Process<TileLocksResponse>(tilesApi.getLocksBelongingToTile_, null, data);
    }

    public unsafe Task<object> AssociateMultipleLocks(AssociateMultipleLocksData data)
    {
        return Process<object>(tilesApi.associateMultipleLocks_, null, data);
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
            var callbackPointer = Marshal.GetFunctionPointerForDelegate(holder.CallbackDelegate);
            if (data != null)
            {
                processWithData(tiles, sData, callbackPointer.ToPointer());
            }
            else
            {
                processWithoutData(tiles, callbackPointer.ToPointer());
            }
        }
        finally
        {
            if (data != null) Marshal.FreeHGlobal((IntPtr)sData);
        }

        return tcs.Task;
    }
}