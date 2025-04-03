using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;

public class Tiles(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_TilesApi tiles,
    Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._TilesApi_e__Struct tilesApi): AbstractWrapper
{
    public unsafe Task<TileLocksResponse> GetLocksBelongingToTile(string tileId) =>
        Process<TileLocksResponse>(tilesApi.getLocksBelongingToTile_, null, new { tileId });

    public unsafe Task<object> AssociateMultipleLocks(string tileId, string siteId, List<string> lockIds) =>
        Process<object>(tilesApi.associateMultipleLocks_, null, new { tileId, siteId, lockIds });

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