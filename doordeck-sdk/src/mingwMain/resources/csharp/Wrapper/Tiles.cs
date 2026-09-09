using Doordeck.Headless.Sdk.Model.Responses;

namespace Doordeck.Headless.Sdk.Wrapper;


public class Tiles
{
    public Task<TileLocksResponse> GetLocksBelongingToTile(Guid tileId) =>
        Dispatcher.Call<TileLocksResponse>("tiles.getLocksBelongingToTile", new { tileId });

    public Task<object> AssociateMultipleLocks(Guid tileId, Guid siteId, List<Guid> lockIds) =>
        Dispatcher.Call<object>("tiles.associateMultipleLocks", new { tileId, siteId, lockIds });
}