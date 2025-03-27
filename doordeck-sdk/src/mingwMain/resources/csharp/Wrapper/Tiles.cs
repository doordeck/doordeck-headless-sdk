using Doordeck.Headless.Sdk.Model;
using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;

public class Tiles(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_TilesApi tiles,
    Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._TilesApi_e__Struct tilesApi): AbstractWrapper
{
    public unsafe Task<TileLocksResponse> GetLocksBelongingToTile(GetLocksBelongingToTileData data) =>
        Process<TileLocksResponse>(tilesApi.getLocksBelongingToTile_, null, data);

    public unsafe Task<object> AssociateMultipleLocks(AssociateMultipleLocksData data) =>
        Process<object>(tilesApi.associateMultipleLocks_, null, data);

    private unsafe Task<TResponse> Process<TResponse>(
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_TilesApi,
            sbyte*, void*, void> processWithData,
        delegate* unmanaged[Cdecl]<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_TilesApi,
            void*, void> processWithoutData,
        object? data) =>
        ProcessCommon<Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_TilesApi, TResponse>(
            tiles,
            data,
            processWithData,
            processWithoutData);
}