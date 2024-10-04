# Tiles resource

### Get locks belonging to a tile
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.tiles().getLocksBelongingToTile("TILE_ID")
````
>:information_source: In Java, you can use the `getLocksBelongingToTileAsync` function, which returns a `CompletableFuture<TileLocksResponse>` instead
</details>

<details><summary>Swift</summary>

````swift
let response = sdk.tiles().getLocksBelongingToTile(tileId: "TILE_ID")
````
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.tiles().getLocksBelongingToTile("TILE_ID");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.tiles(sdk);
var data = new GetLocksBelongingToTileData("TILE_ID").toData();
var response = Utils.fromData<TileLocksResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.TilesResource.getLocksBelongingToTileJson(resource, data));
````
</details>

### Associate multiple locks
<details><summary>JVM & Android</summary>

````kotlin
sdk.tiles().associateMultipleLocks("TILE_ID", "SITE_ID", listOf("LOCK_ID"))
````
>:information_source: In Java, you can use the `associateMultipleLocksAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
sdk.tiles().associateMultipleLocks(tileId: "TILE_ID", siteId: "SITE_ID", lockIds: ["LOCK_ID"]))
````
</details>

<details><summary>JS</summary>

````js
const ktList = doordeck.kotlin.collections.KtList;
const lockIdList = ktList.fromJsArray(["LOCK_ID"]);
await doordeck.com.doordeck.multiplatform.sdk.api.tiles().associateMultipleLocks("TILE_ID", "SITE_ID", lockIdList);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.tiles(sdk);
List<string> lockIdList = ["LOCK_ID"];
var data = new AssociateMultipleLocksData("TILE_ID", "SITE_ID", lockIdList).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.TilesResource.associateMultipleLocksJson(resource, data);
````
</details>

:arrow_left: [Back to index](01_INDEX.md)