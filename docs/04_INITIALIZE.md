# Initialize the SDK
To initialize the SDK, you need to provide an [SdkConfig](04_INITIALIZE.md#sdk-config-builder) with your desired configuration.

## SDK config builder
The minimum viable `SdkConfig` is `SdkConfig.Builder().build()`, although the following configurations are also available:

|                   Configuration                   | Method                     |        Default         |
|:-------------------------------------------------:|:---------------------------|:----------------------:|
|                  API environment                  | `setApiEnvironment`        |       Production       |
|                 Cloud auth token                  | `setCloudAuthToken`        |           -            |
|                Cloud refresh token                | `setCloudRefreshToken`     |           -            |
|                    Fusion host                    | `setFusionHost`            | http://localhost:27700 |
| [Secure storage](04_INITIALIZE.md#secure-storage) | `setSecureStorageOverride` |           -            |

> [!NOTE]  
> If you initialize the SDK without a cloud auth token, you will need to either provide one manually through the [context manager](06_CONTEXT-MANAGER.md#set-cloud-auth-token) or call the [login](07_API-ACCOUNTLESS.md#login) function to access most SDK functionalities.

## Secure storage

By default, the SDK stores the context information on its own, as shown in the following table:

| Platform | Storage                      |
|:--------:|------------------------------|
| Android  | `EncryptedSharedPreferences` |
|   JVM    | `Memory`                     |
|   iOS    | `Keychain`                   |
|  macOS   | `Keychain`                   |
|    JS    | `LocalStorage`               |
| Windows  | `Memory`                     |

> [!NOTE]
> To override the default secure storage, you must implement the `SecureStorage` interface and pass the class through the `setSecureStorageOverride` function from `SdkConfig` builder.

## Initialize

### JVM
<details>
<summary>Show Details</summary>

```kotlin
val sdk = KDoordeckFactory.initialize(SdkConfig.Builder().setCloudAuthToken("AUTH_TOKEN").build())
```
</details>

### Android
<details>
<summary>Show Details</summary>

```kotlin
val sdk = KDoordeckFactory.initialize(SdkConfig.Builder().setCloudAuthToken("AUTH_TOKEN").build())
```
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let sdk = KDoordeckFactory().initialize(sdkConfig: SdkConfig.Builder().setCloudAuthToken(cloudAuthToken: "AUTH_TOKEN").build())
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
import doordeck from '@doordeck/doordeck-headless-sdk';
const sdk = doordeck.com.doordeck.multiplatform.sdk.KDoordeckFactory.initialize(new SdkConfig.Builder().setCloudAuthToken("AUTH_TOKEN").build());
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var sdk = new DoordeckSdk(cloudAuthToken: "AUTH_TOKEN");
```

⚠️ **Note:** You should also call ``sdk.Release();`` at the end of your application’s lifecycle to release the SDK resources.
</details>

### Python
<details>
<summary>Show Details</summary>

```python
sdk = doordeck_headless_sdk.InitializeSdk("AUTH_TOKEN")
```
</details>

:arrow_left: [Back to index](01_INDEX.md)
