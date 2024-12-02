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

## Set refresh token

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.contextManager().setRefreshToken("REFRESH_TOKEN")
```
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.contextManager().setRefreshToken(token: "REFRESH_TOKEN")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
sdk.contextManager().setRefreshToken("REFRESH_TOKEN");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var contextManager = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.contextManager(sdk);
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager.setRefreshToken(contextManager, "REFRESH_TOKEN".toSByte());
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

After setting the [operation context](#set-operation-context), [auth token](#set-auth-token), [refresh token](#set-refresh-token), or [fusion auth token](#set-fusion-auth-token), you can store these values so they persist across sessions.

> [!NOTE] 
> You can override the default secure storage implementation with the [set secure storage implementation](#set-secure-storage-implementation) functionality

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

Loads the previously stored context, including the [operation context](#set-operation-context), [auth token](#set-auth-token), [refresh token](#set-refresh-token), and [fusion auth token](#set-fusion-auth-token).

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

## Set secure storage implementation

This function enables you to provide your own storage implementation.

> [!NOTE]
> If you are looking to override the default secure storage with this option, you should set it before using the [load context](#load-context)

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
class MyOwnSecureImplementation : SecureStorage {
    override fun addCloudAuthToken(token: String) {
        // Your implementation
    }

    override fun getCloudAuthToken(): String? {
        // Your implementation
    }

    override fun addCloudRefreshToken(token: String) {
        // Your implementation
    }

    override fun getCloudRefreshToken(): String? {
        // Your implementation
    }
    
    override fun addFusionAuthToken(token: String) {
        // Your implementation
    }

    override fun getFusionAuthToken(): String? {
        // Your implementation
    }

    override fun addPrivateKey(byteArray: ByteArray) {
        // Your implementation
    }

    override fun getPrivateKey(): ByteArray? {
        // Your implementation
    }

    override fun addUserId(userId: String) {
        // Your implementation
    }

    override fun getUserId(): String? {
        // Your implementation
    }

    override fun addCertificateChain(certificateChain: List<String>) {
        // Your implementation
    }

    override fun getCertificateChain(): List<String>? {
        // Your implementation
    }

    override fun clear() {
        // Your implementation
    }
}

sdk.contextManager().setSecureStorageImpl(MyOwnSecureImplementation())
```
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
class MyOwnSecureImplementation : SecureStorage {
    func addCloudAuthToken(token: String) {
        // Your Implementation
    }

    func getCloudAuthToken() -> String? {
        // Your Implementation
    }
    
    func addCloudRefreshToken(token: String) {
        // Your Implementation
    }
    
    func getCloudRefreshToken() -> String? {
        // Your Implementation
    }
    
    func addFusionAuthToken(token: String) {
        // Your Implementation
    }
    
    func getFusionAuthToken() -> String? {
        // Your Implementation
    }
    
    func addPrivateKey(byteArray: KotlinByteArray) {
        // Your Implementation
    }
    
    func getPrivateKey() -> KotlinByteArray? {
        // Your Implementation
    }
    
    func addUserId(userId: String) {
        // Your Implementation
    }
    
    func getUserId() -> String? {
        // Your Implementation
    }
    
    func addCertificateChain(certificateChain: [String]) {
        // Your Implementation
    }
    
    func getCertificateChain() -> [String]? {
        // Your Implementation
    }
    
    func clear() {
        // Your Implementation
    }
}

sdk.contextManager().setSecureStorageImpl(secureStorage: MyOwnSecureImplementation())
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
class MyOwnSecureImplementation implements com.doordeck.multiplatform.sdk.storage.SecureStorage {
    addCloudAuthToken(token: string): void {
        // Your implementation
    }

    getCloudAuthToken(): Nullable<string> {
        // Your implementation
    }

    addCloudRefreshToken(token: string): void {
        // Your implementation
    }

    getCloudRefreshToken(): Nullable<string> {
        // Your implementation
    }

    addFusionAuthToken(token: string): void {
        // Your implementation
    }

    getFusionAuthToken(): Nullable<string> {
        // Your implementation
    }

    addPrivateKey(byteArray: Int8Array): void {
        // Your implementation
    }

    getPrivateKey(): Nullable<Int8Array> {
        // Your implementation
    }

    addUserId(userId: string): void {
        // Your implementation
    }

    getUserId(): Nullable<string> {
        // Your implementation
    }

    addCertificateChain(certificateChain: kotlin.collections.KtList<string>): void {
        // Your implementation
    }

    getCertificateChain(): Nullable<kotlin.collections.KtList<string>> {
        // Your implementation
    }

    clear(): void {
        // Your implementation
    }
}

sdk.contextManager().setSecureStorageImpl(new MyOwnSecureImplementation());
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
// TODO
```
</details>

:arrow_left: [Back to index](01_INDEX.md)
