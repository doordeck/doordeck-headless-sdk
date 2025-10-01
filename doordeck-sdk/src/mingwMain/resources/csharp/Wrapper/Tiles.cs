using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;

using TilesApi = Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_TilesApi;

public class Tiles(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_TilesApi tiles,
    Doordeck_Headless_Sdk_ExportedSymbols._kotlin_e__Struct._root_e__Struct._com_e__Struct._doordeck_e__Struct.
        _multiplatform_e__Struct._sdk_e__Struct._api_e__Struct._TilesApi_e__Struct tilesApi): AbstractWrapper
{
    public unsafe Task<TileLocksResponse> GetLocksBelongingToTile(Guid tileId) =>
        Process<TilesApi, TileLocksResponse>(tiles, tilesApi.getLocksBelongingToTile_, new { tileId });

    public unsafe Task<object> AssociateMultipleLocks(Guid tileId, Guid siteId, List<Guid> lockIds) =>
        Process<TilesApi, object>(tiles, tilesApi.associateMultipleLocks_, new { tileId, siteId, lockIds });
}