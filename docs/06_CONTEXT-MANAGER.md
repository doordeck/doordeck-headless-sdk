# Context manager

The **Context Manager** simplifies the usage of complex SDK functions by reducing the number of parameters required when setting the operation context, authentication tokens, and other session data.

## Set operation context

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.contextManager().setOperationContext("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PUBLIC_KEY, PRIVATE_KEY)
```
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.contextManager().setOperationContext(userId: "USER_ID", certificateChain: USER_CERTIFICATE_CHAIN_LIST, publicKey: PUBLIC_KEY, privateKey: PRIVATE_KEY)
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
sdk.contextManager().setOperationContext("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PUBLIC_KEY, PRIVATE_KEY);
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var data = new OperationContextData("USER_ID", USER_CERTIFICATE_CHAIN_LIST_AS_STRING, "BASE64_PUBLIC_KEY", "BASE64_PRIVATE_KEY");
sdk.GetContextManager().SetOperationContext(data);
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
data = OperationContextData("USER_ID", USER_CERTIFICATE_CHAIN_LIST_AS_STRING, "BASE64_PUBLIC_KEY", "BASE64_PRIVATE_KEY")
sdk.contextManager.set_operation_context(data)
```
</details>


## Is certificate chain about to expire

Checks if the current certificate chain from the context is about to expire within the next 7 days.

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val result = sdk.contextManager().isCertificateChainAboutToExpire()
```
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let result = sdk.contextManager().isCertificateChainAboutToExpire()
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const result = sdk.contextManager().isCertificateChainAboutToExpire();
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var result = sdk.GetContextManager().IsCertificateChainAboutToExpire();
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
result = sdk.contextManager.is_certificate_chain_about_to_expire()
```
</details>

## Is key pair valid

Checks if the current key pair from the context is valid.

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val result = sdk.contextManager().isKeyPairValid()
```
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let result = sdk.contextManager().isKeyPairValid()
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const result = sdk.contextManager().isKeyPairValid();
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var result = sdk.GetContextManager().IsKeyPairValid();
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
result = sdk.contextManager.is_key_pair_valid()
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
sdk.GetContextManager().SetAuthToken("AUTH_TOKEN");
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
sdk.contextManager.set_auth_token("AUTH_TOKEN")
```
</details>

## Is auth token about to expire

Checks if the current [auth token](#set-auth-token) from the context is about to expire within the next 24 hours

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val result = sdk.contextManager().isAuthTokenAboutToExpire()
```
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let result = sdk.contextManager().isAuthTokenAboutToExpire()
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const result = sdk.contextManager().isAuthTokenAboutToExpire();
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var result = sdk.GetContextManager().IsAuthTokenAboutToExpire();
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
result = sdk.contextManager.is_auth_token_about_to_expire()
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
sdk.GetContextManager().SetRefreshToken("REFRESH_TOKEN");
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
result = sdk.contextManager.set_refresh_token("REFRESH_TOKEN")
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
sdk.GetContextManager().SetFusionAuthToken("FUSION_AUTH_TOKEN");
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
result = sdk.contextManager.set_fusion_auth_token("FUSION_AUTH_TOKEN")
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
sdk.GetContextManager().StoreContext();
```

💡 **Note:** In C#, the context is stored using `windows registry`.
</details>

### Python
<details>
<summary>Show Details</summary>

```python
sdk.contextManager.store_context()
```

💡 **Note:** In Python, the context is stored using `windows registry`.
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
sdk.GetContextManager().LoadContext();
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
sdk.contextManager.load_context()
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
sdk.GetContextManager().ClearContext();
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
sdk.contextManager.clear_context()
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

### Python
<details>
<summary>Show Details</summary>

```csharp
// TODO
```
</details>

:arrow_left: [Back to index](01_INDEX.md)
