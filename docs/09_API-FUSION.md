# Fusion resource

## Login

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.fusion().login("EMAIL", "PASSWORD")
```

💡 **Note:** In Java, use the `fusionAsync` function, which returns a `CompletableFuture<Void>` instead.
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.fusion().login(email: "EMAIL", password: "PASSWORD")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.fusion().login("EMAIL", "PASSWORD");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.fusion(sdk);
var data = new FusionLoginData("EMAIL", "PASSWORD").toData();
var response = Utils.fromData<FusionLoginResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.FusionResource.loginJson(resource, data));
```
</details>

## Get integration type

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.fusion().getIntegrationType()
```

💡 **Note:** In Java, use the `getIntegrationTypeAsync` function, which returns a `CompletableFuture<IntegrationTypeResponse>` instead.
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.fusion().getIntegrationType()
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.fusion().getIntegrationType();
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.fusion(sdk);
var response = Utils.fromData<IntegrationTypeResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.FusionResource.getIntegrationTypeJson(resource));
```
</details>

## Get integration configuration

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.fusion().getIntegrationConfiguration("TYPE")
```

💡 **Note:** In Java, use the `getIntegrationConfigurationAsync` function, which returns a `CompletableFuture<List<IntegrationConfigurationResponse>>` instead.
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.fusion().getIntegrationConfiguration(type: "TYPE")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.fusion().getIntegrationConfiguration("TYPE");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.fusion(sdk);
var data = new GetIntegrationConfigurationData("TYPE");
var response = Utils.fromData<List<IntegrationConfigurationResponse>>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.FusionResource.getIntegrationConfigurationJson(resource, data));
```
</details>

## Enable door

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val controller = Fusion.DemoController()
sdk.fusion().enableDoor("NAME", "SITE_ID", controller)
```

💡 **Note:** In Java, use the `enableDoorAsync` function, which returns a `CompletableFuture<Void>` instead.
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let controller = Fusion.DemoController()
sdk.fusion().enableDoor(name: "NAME", siteId: "SITE_ID", controller: controller)
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const fusion = doordeck.com.doordeck.multiplatform.sdk.api.model.Fusion;
const controller = new fusion.DemoController(8080);
await doordeck.com.doordeck.multiplatform.sdk.api.fusion().enableDoor("NAME", "SITE_ID", controller);
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.fusion(sdk);
var controllerData = new DemoControllerData(8080);
var data = new EnableDoorData("NAME", "SITE_ID", controllerData).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.FusionResource.enableDoor(resource, data);
```
</details>

## Delete door

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.fusion().deleteDoor("DEVICE_ID")
```

💡 **Note:** In Java, use the `deleteDoorAsync` function, which returns a `CompletableFuture<Void>` instead.
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.fusion().deleteDoor(deviceId: "DEVICE_ID")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
await doordeck.com.doordeck.multiplatform.sdk.api.fusion().deleteDoor("DEVICE_ID");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.fusion(sdk);
var data = new DeleteDoorData("DEVICE_ID");
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.FusionResource.deleteDoorJson(resource, data);
```
</details>

## Get door status

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.fusion().getDoorStatus("DEVICE_ID")
```

💡 **Note:** In Java, use the `getDoorStatusAsync` function, which returns a `CompletableFuture<DoorStateResponse>` instead.
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.fusion().getDoorStatus(deviceId: "DEVICE_ID")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.fusion().getDoorStatus("DEVICE_ID");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.fusion(sdk);
var data = new GetDoorStatusData("DEVICE_ID");
var response = Utils.fromData<DoorStateResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.FusionResource.getDoorStatusJson(resource, data));
```
</details>

## Start door

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.fusion().startDoor("DEVICE_ID")
```

💡 **Note:** In Java, use the `startDoorAsync` function, which returns a `CompletableFuture<Void>` instead.
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.fusion().startDoor(deviceId: "DEVICE_ID")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
await doordeck.com.doordeck.multiplatform.sdk.api.fusion().startDoor("DEVICE_ID");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.fusion(sdk);
var data = new StartDoorData("DEVICE_ID");
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.FusionResource.startDoorJson(resource, data);
```
</details>

## Stop door

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.fusion().stopDoor("DEVICE_ID")
```

💡 **Note:** In Java, use the `stopDoorAsync` function, which returns a `CompletableFuture<Void>` instead.
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.fusion().stopDoor(deviceId: "DEVICE_ID")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
await doordeck.com.doordeck.multiplatform.sdk.api.fusion().stopDoor("DEVICE_ID");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.fusion(sdk);
var data = new StopDoorData("DEVICE_ID");
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.FusionResource.stopDoorJson(resource, data);
```
</details>

:arrow_left: [Back to index](01_INDEX.md)
