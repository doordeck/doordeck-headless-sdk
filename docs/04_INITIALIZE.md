# Initialize the SDK

To get started with the SDK, the first step is to initialize it by providing the `ApiEnvironment` and an authentication token.

You can also initialize the SDK without an auth token, but in this case, you'll need to manually [set an auth token through the context manager](06_CONTEXT-MANAGER.md#set-auth-token) to access most SDK functionalities.

### JVM
<details>
<summary>Show Details</summary>

```kotlin
val sdk = KDoordeckFactory.initialize(ApiEnvironment.PROD, "AUTH_TOKEN")
```
</details>

### Android
<details>
<summary>Show Details</summary>

In Android, you need to pass the Android application context to initialize the SDK:

```kotlin
val applicationContext = ApplicationContext(context)
val sdk = KDoordeckFactory.initialize(applicationContext, ApiEnvironment.PROD, "AUTH_TOKEN")
```
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let sdk = KDoordeckFactory().initialize(apiEnvironment: .prod, token: "AUTH_TOKEN")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
import doordeck from '@doordeck/doordeck-headless-sdk';
const apiEnvironment = doordeck.com.doordeck.multiplatform.sdk.api.model.ApiEnvironment;
const sdk = doordeck.com.doordeck.multiplatform.sdk.KDoordeckFactory.initializeWithAuthToken(apiEnvironment.PROD, "AUTH_TOKEN");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var sdk = new DoordeckSdk(ApiEnvironment.PROD, "AUTH_TOKEN")
sdk.Initialize();
```
</details>

:arrow_left: [Back to index](01_INDEX.md)
