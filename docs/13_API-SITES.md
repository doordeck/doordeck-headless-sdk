# Sites resource

### List sites
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.sites().listSites()
````
>:information_source: In Java, you can use the `listSitesAsync` function, which returns a `CompletableFuture<List<SiteResponse>>` instead
</details>

<details><summary>Swift</summary>

````swift
let response = sdk.sites().listSites()
````
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.sites().listSites();
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.sites(sdk);
var response = Utils.fromData<List<SiteResponse>>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.SitesResource.listSitesJson(resource));
````
</details>

### Get locks for a site
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.sites().getLocksForSite("SITE_ID")
````
>:information_source: In Java, you can use the `getLocksForSiteAsync` function, which returns a `CompletableFuture<List<SiteLocksResponse>>` instead
</details>

<details><summary>Swift</summary>

````swift
let response = sdk.sites().getLocksForSite(siteId: "SITE_ID")
````
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.sites().getLocksForSite("SITE_ID");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.sites(sdk);
var data = new GetLocksForSiteData("SITE_ID").toData();
var response = Utils.fromData<List<SiteLocksResponse>>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.SitesResource.getLocksForSiteJson(resource, data));
````
</details>

### Get users for a site
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.sites().getUsersForSite("SITE_ID")
````
>:information_source: In Java, you can use the `getUsersForSiteAsync` function, which returns a `CompletableFuture<List<UserForSiteResponse>>` instead
</details>

<details><summary>Swift</summary>

````swift
let response = sdk.sites().getUsersForSite(siteId: "SITE_ID")
````
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.sites().getUsersForSite("SITE_ID");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.sites(sdk);
var data = new GetUsersForSiteData("SITE_ID").toData();
var response = Utils.fromData<List<UserForSiteResponse>>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.SitesResource.getUsersForSiteJson(resource, data));
````
</details>