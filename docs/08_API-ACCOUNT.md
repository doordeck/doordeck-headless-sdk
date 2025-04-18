# Account resource

## Request a new refresh token

> [!IMPORTANT]  
> This function is only available to users with Doordeck issued auth tokens.

> [!NOTE]  
> When used successfully, the cloud auth token and cloud refresh token from the response are added to the [context manager](06_CONTEXT-MANAGER.md) and automatically stored in [secure storage](04_INITIALIZE.md#secure-storage).

> [!NOTE]  
> This function can be used with the [refresh token](06_CONTEXT-MANAGER.md#set-refresh-token) value from the context. To use the value from the context, you should pass null as the function parameter

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.account().refreshToken("REFRESH_TOKEN")
```

💡 **Note:** In Java, use the `refreshTokenAsync` function, which returns a `CompletableFuture<TokenResponse>` instead.
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.account().refreshToken(refreshToken: "REFRESH_TOKEN")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.account().refreshToken("REFRESH_TOKEN");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var response = await sdk.GetAccount().RefreshToken("REFRESH_TOKEN");
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
response = await sdk.account.refresh_token("REFRESH_TOKEN")
```
</details>

## Logout

> [!IMPORTANT]
> When used, the [context manager](06_CONTEXT-MANAGER.md#context-manager) restarts, and the values from the [secure storage](04_INITIALIZE.md#secure-storage) are automatically deleted.

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.account().logout()
```

💡 **Note:** In Java, use the `logoutAsync` function, which returns a `CompletableFuture<Void>` instead.
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.account().logout()
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
await doordeck.com.doordeck.multiplatform.sdk.api.account().logout();
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
await sdk.GetAccount().Logout();
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
await sdk.account.logout()
```
</details>

## Register ephemeral key
To register a new ephemeral key, you will need to [generate a new key pair](05_CRYPTO.md#generate-a-key-pair).

> [!NOTE]  
> When used successfully, the user ID and user certificate chain from the response are added to the [context manager](06_CONTEXT-MANAGER.md) and automatically stored in [secure storage](04_INITIALIZE.md#secure-storage).

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.account().registerEphemeralKey(PUBLIC_KEY)
```

💡 **Note:** In Java, use the `registerEphemeralKeyAsync` function, which returns a `CompletableFuture<RegisterEphemeralKeyResponse>` instead.
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.account().registerEphemeralKey(publicKey: PUBLIC_KEY)
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.account().registerEphemeralKey(PUBLIC_KEY);
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var response = await sdk.GetAccount().RegisterEphemeralKey("BASE64_PUBLIC_KEY");
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
response = await sdk.account.register_ephemeral_key("BASE64_PUBLIC_KEY")
```
</details>

## Register ephemeral key with secondary authentication
To register a new ephemeral key with secondary authentication, you will need to [generate a new key pair](05_CRYPTO.md#generate-a-key-pair). After the registration, you will need to [verify the ephemeral key registration](#verify-ephemeral-key-registration).

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.account().registerEphemeralKeyWithSecondaryAuthentication(PUBLIC_KEY)
```

💡 **Note:** In Java, use the `registerEphemeralKeyWithSecondaryAuthenticationAsync` function, which returns a `CompletableFuture<RegisterEphemeralKeyWithSecondaryAuthenticationResponse>` instead.
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.account().registerEphemeralKeyWithSecondaryAuthentication(publicKey: PUBLIC_KEY, method: null)
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const twoFactorMethod = doordeck.com.doordeck.multiplatform.sdk.model.common.TwoFactorMethod;
const response = await doordeck.com.doordeck.multiplatform.sdk.api.account().registerEphemeralKeyWithSecondaryAuthentication(PUBLIC_KEY, twoFactorMethod.EMAIL);
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var response = await sdk.GetAccount().RegisterEphemeralKeyWithSecondaryAuthentication("BASE64_PUBLIC_KEY");
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
response = await sdk.account.register_ephemeral_key_with_secondary_authentication("BASE64_PUBLIC_KEY")
```
</details>

## Verify ephemeral key registration

> [!NOTE]  
> When used successfully, the user ID and user certificate chain from the response are added to the [context manager](06_CONTEXT-MANAGER.md) and automatically stored in [secure storage](04_INITIALIZE.md#secure-storage).

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.account().verifyEphemeralKeyRegistration("CODE", PRIVATE_KEY)
```

💡 **Note:** In Java, use the `verifyEphemeralKeyRegistrationAsync` function, which returns a `CompletableFuture<RegisterEphemeralKeyResponse>` instead.
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.account().verifyEphemeralKeyRegistration(code: "CODE", privateKey: PRIVATE_KEY)
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.account().verifyEphemeralKeyRegistration("CODE", PRIVATE_KEY);
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var response = await sdk.GetAccount().VerifyEphemeralKeyRegistration("CODE", "BASE64_PRIVATE_KEY");
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
response = await sdk.account.verify_ephemeral_key_registration("CODE", "BASE64_PRIVATE_KEY")
```
</details>

## Re-verify email

> [!IMPORTANT]  
> This function is only available to users with Doordeck issued auth tokens.

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.account().reverifyEmail()
```

💡 **Note:** In Java, use the `reverifyEmailAsync` function, which returns a `CompletableFuture<Unit>` instead.
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.account().reverifyEmail()
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
await doordeck.com.doordeck.multiplatform.sdk.api.account().reverifyEmail();
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
await sdk.GetAccount().ReverifyEmail();
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
await sdk.account.reverify_email()
```
</details>

## Change password

> [!IMPORTANT]  
> This function is only available to users with Doordeck issued auth tokens.

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.account().changePassword("OLD_PASSWORD", "NEW_PASSWORD")
```

💡 **Note:** In Java, use the `changePasswordAsync` function, which returns a `CompletableFuture<Unit>` instead.
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.account().changePassword(oldPassword: "OLD_PASSWORD", newPassword: "NEW_PASSWORD")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
await doordeck.com.doordeck.multiplatform.sdk.api.account().changePassword("OLD_PASSWORD", "NEW_PASSWORD");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
await sdk.GetAccount().ChangePassword("OLD_PASSWORD", "NEW_PASSWORD");
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
await sdk.account.change_password("OLD_PASSWORD", "NEW_PASSWORD")
```
</details>

## Get user details

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.account().getUserDetails()
```

💡 **Note:** In Java, use the `getUserDetailsAsync` function, which returns a `CompletableFuture<UserDetailsResponse>` instead.
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.account().getUserDetails()
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.account().getUserDetails();
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var response = await sdk.GetAccount().GetUserDetails();
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
response = await sdk.account.get_user_details()
```
</details>

## Update user details

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.account().updateUserDetails("DISPLAY_NAME")
```

💡 **Note:** In Java, use the `updateUserDetailsAsync` function, which returns a `CompletableFuture<Void>` instead.
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.account().updateUserDetails(displayName: "DISPLAY_NAME")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
await doordeck.com.doordeck.multiplatform.sdk.api.account().updateUserDetails("DISPLAY_NAME");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
await sdk.GetAccount().UpdateUserDetails("DISPLAY_NAME");
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
await sdk.account.update_user_details("DISPLAY_NAME")
```
</details>

## Delete account

> [!CAUTION]  
> This operation is executed instantly and is irreversible.

> [!IMPORTANT]
> When used, the [context manager](06_CONTEXT-MANAGER.md#context-manager) restarts, and the values from the [secure storage](04_INITIALIZE.md#secure-storage) are automatically deleted.

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.account().deleteAccount()
```

💡 **Note:** In Java, use the `deleteAccountAsync` function, which returns a `CompletableFuture<Void>` instead.
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.account().deleteAccount()
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
await doordeck.com.doordeck.multiplatform.sdk.api.account().deleteAccount();
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
await sdk.GetAccount().DeleteAccount();
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
await sdk.account.delete_account()
```
</details>

:arrow_left: [Back to index](01_INDEX.md)
