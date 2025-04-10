# Sites resource

## List Sites

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.sites().listSites()
```

💡 **Note:** In Java, use the `listSitesAsync` function, which returns a `CompletableFuture<List<SiteResponse>>` instead.
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.sites().listSites()
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.sites().listSites();
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var response = await sdk.GetSites().ListSites();
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
response = await sdk.sites.list_sites()
```
</details>

## Get locks for a site

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.sites().getLocksForSite("SITE_ID")
```

💡 **Note:** In Java, use the `getLocksForSiteAsync` function, which returns a `CompletableFuture<List<SiteLocksResponse>>` instead.
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.sites().getLocksForSite(siteId: "SITE_ID")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.sites().getLocksForSite("SITE_ID");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var response = await sdk.GetSites().GetLocksForSite("SITE_ID");
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
response = await sdk.sites.get_locks_for_site("SITE_ID")
```
</details>

## Get users for a site

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.sites().getUsersForSite("SITE_ID")
```

💡 **Note:** In Java, use the `getUsersForSiteAsync` function, which returns a `CompletableFuture<List<UserForSiteResponse>>` instead.
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.sites().getUsersForSite(siteId: "SITE_ID")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.sites().getUsersForSite("SITE_ID");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var response = await sdk.GetSites().GetUsersForSite("SITE_ID");
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
response = await sdk.sites.get_users_for_site("SITE_ID")
```
</details>

:arrow_left: [Back to index](01_INDEX.md)
