# Context manager

The **Context Manager** simplifies the usage of complex SDK functions by reducing the number of parameters required when setting the operation context, authentication tokens, and other session data.

## Set operation context

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.contextManager().setOperationContext("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY)
```
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.contextManager().setOperationContext(userId: "USER_ID", certificateChain: USER_CERTIFICATE_CHAIN_LIST, privateKey: PRIVATE_KEY)
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
sdk.contextManager().setOperationContext("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY);
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var contextManager = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.contextManager(sdk);
var data = new OperationContextData("USER_ID", USER_CERTIFICATE_CHAIN_LIST, "BASE64_PRIVATE_KEY").toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager.setOperationContextJson(contextManager, data);
```
</details>

## Set auth token

If the SDK was initialized without an authentication token, you can provide or update the token using this function.

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.contextManager().setAuthToken("AUTH_TOKEN")
```
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.contextManager().setAuthToken(token: "AUTH_TOKEN")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
sdk.contextManager().setAuthToken("AUTH_TOKEN");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var contextManager = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.contextManager(sdk);
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager.setAuthToken(contextManager, "AUTH_TOKEN".toSByte());
```
</details>

## Set fusion auth token

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.contextManager().setFusionAuthToken("FUSION_AUTH_TOKEN")
```
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.contextManager().setFusionAuthToken(token: "FUSION_AUTH_TOKEN")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
sdk.contextManager().setFusionAuthToken("FUSION_AUTH_TOKEN");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var contextManager = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.contextManager(sdk);
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager.setFusionAuthToken(contextManager, "FUSION_AUTH_TOKEN".toSByte());
```
</details>

## Store context

After setting the [operation context](#set-operation-context), [auth token](#set-auth-token), or [fusion auth token](#set-fusion-auth-token), you can store these values so they persist across sessions.

### JVM
<details>
<summary>Show Details</summary>

```kotlin
sdk.contextManager().storeContext()
```

💡 **Note:** In JVM, the context is stored using `properties`.
</details>

### Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.contextManager().storeContext()
```

💡 **Note:** In Android, the context is stored using `shared preference settings`.
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.contextManager().storeContext()
```

💡 **Note:** In Swift, the context is stored using `keychain`.
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
sdk.contextManager().storeContext();
```

💡 **Note:** In JavaScript, the context is stored using `local storage`.
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var contextManager = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.contextManager(sdk);
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager.storeContext(contextManager);
```

💡 **Note:** In C#, the context is stored using `windows registry`.
</details>

## Load context

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.contextManager().loadContext()
```
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.contextManager().loadContext()
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
sdk.contextManager().loadContext();
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var contextManager = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.contextManager(sdk);
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager.loadContext(contextManager);
```
</details>

## Clear context

This function removes all stored context fields from the system but does not clear them from memory.

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.contextManager().clearContext()
```
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.contextManager().clearContext()
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
sdk.contextManager().clearContext();
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var contextManager = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.contextManager(sdk);
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager.clearContext(contextManager);
```
</details>

:arrow_left: [Back to index](01_INDEX.md)
