# Tiles resource

## Get Locks Belonging to a Tile

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.tiles().getLocksBelongingToTile("TILE_ID")
```

💡 **Note:** In Java, use the `getLocksBelongingToTileAsync` function, which returns a `CompletableFuture<TileLocksResponse>` instead.
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.tiles().getLocksBelongingToTile(tileId: "TILE_ID")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.tiles().getLocksBelongingToTile("TILE_ID");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var data = new GetLocksBelongingToTileData("TILE_ID");
var response = await sdk.GetTiles().GetLocksBelongingToTile(data);
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
data = doordeck_headless_sdk.GetLocksBelongingToTileData("TILE_ID")
response = sdk.tiles.get_locks_belonging_to_tile(data)
```
</details>

## Associate Multiple Locks

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.tiles().associateMultipleLocks("TILE_ID", "SITE_ID", listOf("LOCK_ID"))
```

💡 **Note:** In Java, use the `associateMultipleLocksAsync` function, which returns a `CompletableFuture<Void>` instead.
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.tiles().associateMultipleLocks(tileId: "TILE_ID", siteId: "SITE_ID", lockIds: ["LOCK_ID"]))
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const ktList = doordeck.kotlin.collections.KtList;
await doordeck.com.doordeck.multiplatform.sdk.api.tiles().associateMultipleLocks("TILE_ID", "SITE_ID", ktList.fromJsArray(["LOCK_ID"]));
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var data = new AssociateMultipleLocksData("TILE_ID", "SITE_ID", ["LOCK_ID"]);
await sdk.GetTiles().AssociateMultipleLocks(data);
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
data = doordeck_headless_sdk.AssociateMultipleLocksData("TILE_ID", "SITE_ID", ["LOCK_ID"])
sdk.tiles.associate_multiple_locks(data)
```
</details>

:arrow_left: [Back to index](01_INDEX.md)
