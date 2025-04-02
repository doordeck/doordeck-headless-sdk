# Context manager

The **Context Manager** simplifies the usage of complex SDK functions by reducing the number of parameters required when setting the operation context, authentication tokens, and other session data.

> [!NOTE]  
> All the values that are provided to the context manager are automatically stored in [secure storage](04_INITIALIZE.md#secure-storage).

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
sdk.GetContextManager().SetOperationContext("USER_ID", USER_CERTIFICATE_CHAIN_LIST_AS_STRING, "BASE64_PUBLIC_KEY", "BASE64_PRIVATE_KEY");
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
sdk.contextManager.set_operation_context("USER_ID", USER_CERTIFICATE_CHAIN_LIST_AS_STRING, "BASE64_PUBLIC_KEY", "BASE64_PRIVATE_KEY")
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

## Set cloud auth token

If the SDK was initialized without an authentication token, you can provide or update the token using this function.

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.contextManager().setCloudAuthToken("AUTH_TOKEN")
```
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.contextManager().setCloudAuthToken(token: "AUTH_TOKEN")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
sdk.contextManager().setCloudAuthToken("AUTH_TOKEN");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
sdk.GetContextManager().SetCloudAuthToken("AUTH_TOKEN");
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
sdk.contextManager.set_cloud_auth_token("AUTH_TOKEN")
```
</details>

## Get cloud auth token

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val token = sdk.contextManager().getCloudAuthToken()
```
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let token = sdk.contextManager().getCloudAuthToken()
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const token = sdk.contextManager().getCloudAuthToken();
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var token = sdk.GetContextManager().GetCloudAuthToken();
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
token = sdk.contextManager.get_cloud_auth_token()
```
</details>

## Is cloud auth token about to expire

Checks if the current [cloud auth token](#set-cloud-auth-token) from the context is about to expire within the next 24 hours

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val result = sdk.contextManager().isCloudAuthTokenAboutToExpire()
```
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let result = sdk.contextManager().isCloudAuthTokenAboutToExpire()
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const result = sdk.contextManager().isCloudAuthTokenAboutToExpire();
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var result = sdk.GetContextManager().IsCloudAuthTokenAboutToExpire();
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
result = sdk.contextManager.is_cloud_auth_token_about_to_expire()
```
</details>

## Set cloud refresh token

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.contextManager().setCloudRefreshToken("REFRESH_TOKEN")
```
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.contextManager().setCloudRefreshToken(token: "REFRESH_TOKEN")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
sdk.contextManager().setCloudRefreshToken("REFRESH_TOKEN");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
sdk.GetContextManager().SetCloudRefreshToken("REFRESH_TOKEN");
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
result = sdk.contextManager.set_cloud_refresh_token("REFRESH_TOKEN")
```
</details>

## Get cloud refresh token

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val token = sdk.contextManager().getCloudRefreshToken()
```
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let token = sdk.contextManager().getCloudRefreshToken()
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const token = sdk.contextManager().getCloudRefreshToken();
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var token = sdk.GetContextManager().GetCloudRefreshToken();
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
token = sdk.contextManager.get_cloud_refresh_token()
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

## Get fusion auth token

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val token = sdk.contextManager().getFusionAuthToken()
```
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let token = sdk.contextManager().getFusionAuthToken()
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const token = sdk.contextManager().getFusionAuthToken();
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var token = sdk.GetContextManager().GetFusionAuthToken();
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
token = sdk.contextManager.get_fusion_auth_token()
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

:arrow_left: [Back to index](01_INDEX.md)
