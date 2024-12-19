# Account resource

## Request a new refresh token

> [!IMPORTANT]  
> This function is only available to users with Doordeck issued auth tokens.

> [!NOTE]  
> When used successfully, the auth token and refresh token from the response are added to the [context manager](06_CONTEXT-MANAGER.md).

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
unsafe
{
    var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.account(sdk);
    var data = new RefreshTokenData("REFRESH_TOKEN").ToData();
    var response = Utils.FromData<TokenResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource.refreshTokenJson(resource, data));
}
```
</details>

## Logout

> [!IMPORTANT]  
> When used, the [context manager](06_CONTEXT-MANAGER.md#context-manager) is restarted, so for any further usage, you will need to load or provide a new context.

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
unsafe
{
    var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.account(sdk);
    symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource.logout(resource);
}
```
</details>

## Register ephemeral key
To register a new ephemeral key, you will need to [generate a new key pair](05_CRYPTO.md#generate-a-key-pair).

> [!NOTE]  
> When used successfully, the user ID, and user certificate chain from the response are added to the [context manager](06_CONTEXT-MANAGER.md).

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
unsafe
{
    var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.account(sdk);
    var data = new RegisterEphemeralKeyData("BASE64_PUBLIC_KEY").ToData();
    var response = Utils.FromData<RegisterEphemeralKeyResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource.registerEphemeralKeyJson(resource, data));
}
```
</details>

## Register ephemeral key with secondary authentication
To register a new ephemeral key with secondary authentication, you will need to [generate a new key pair](05_CRYPTO.md#generate-a-key-pair). After the registration, you will need to [verify the ephemeral key registration](#verify-ephemeral-key-registration).

> [!NOTE]  
> When used successfully, the user ID, and user certificate chain from the response are added to the [context manager](06_CONTEXT-MANAGER.md).

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
const twoFactorMethod = doordeck.com.doordeck.multiplatform.sdk.api.model.TwoFactorMethod;
const response = await doordeck.com.doordeck.multiplatform.sdk.api.account().registerEphemeralKeyWithSecondaryAuthentication(PUBLIC_KEY, twoFactorMethod.EMAIL);
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
unsafe
{
    var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.account(sdk);
    var data = new RegisterEphemeralKeyWithSecondaryAuthenticationData("BASE64_PUBLIC_KEY").ToData();
    var response = Utils.FromData<RegisterEphemeralKeyWithSecondaryAuthenticationResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource.registerEphemeralKeyWithSecondaryAuthenticationJson(resource, data));
}
```
</details>

## Verify ephemeral key registration

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
unsafe
{
    var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.account(sdk);
    var data = new VerifyEphemeralKeyRegistrationData("CODE", "BASE64_PRIVATE_KEY").ToData();
    var response = Utils.FromData<RegisterEphemeralKeyResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource.verifyEphemeralKeyRegistrationJson(resource, data));

}
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
unsafe
{
    var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.account(sdk);
    symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource.reverifyEmail(resource);
}
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
unsafe
{
    var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.account(sdk);
    var data = new ChangePasswordData("OLD_PASSWORD", "NEW_PASSWORD").ToData();
    symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource.changePasswordJson(resource, data);
}
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
unsafe
{
    var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.account(sdk);
    var response = Utils.FromData<UserDetailsResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource.getUserDetailsJson(resource));
}
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
unsafe
{
    var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.account(sdk);
    var data = new UpdateUserDetailsData("DISPLAY_NAME").ToData();
    symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource.updateUserDetailsJson(resource, data);
}
```
</details>

## Delete account

> [!CAUTION]  
> This operation is executed instantly and is irreversible.

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
unsafe
{
    var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.account(sdk);
    symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource.deleteAccount(resource);
}
```
</details>

:arrow_left: [Back to index](01_INDEX.md)
