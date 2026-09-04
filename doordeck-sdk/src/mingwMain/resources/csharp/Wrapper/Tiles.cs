using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;

using TilesApi = Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_TilesApi;

public class Tiles(
    Doordeck_Headless_Sdk_kref_com_doordeck_multiplatform_sdk_api_TilesApi tiles): AbstractWrapper
{
    public unsafe Task<TileLocksResponse> GetLocksBelongingToTile(Guid tileId) =>
        Process<TilesApi, TileLocksResponse>(tiles, &Methods.getLocksBelongingToTile, new { tileId });

    public unsafe Task<object> AssociateMultipleLocks(Guid tileId, Guid siteId, List<Guid> lockIds) =>
        Process<TilesApi, object>(tiles, &Methods.associateMultipleLocks, new { tileId, siteId, lockIds });
}