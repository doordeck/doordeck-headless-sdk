# Account resource

### Request a new refresh token
> [!IMPORTANT]
> This function is only available to users with Doordeck issued auth tokens
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.account().refreshToken("REFRESH_TOKEN")
````
>:information_source: In Java, you can use the `refreshTokenAsync` function, which returns a `CompletableFuture<TokenResponse>` instead
</details>

<details><summary>Swift</summary>

````swift
let response = sdk.account().refreshToken(refreshToken: "REFRESH_TOKEN")
````
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.account().refreshToken("REFRESH_TOKEN");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.account(sdk);
var data = new RefreshTokenData("REFRESH_TOKEN").toData();
var response = Utils.fromData<TokenResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource.refreshTokenJson(resource, data));
````
</details>

### Logout
> [!IMPORTANT]
> When used, the [context manager](06_CONTEXT-MANAGER.md#context-manager) is restarted, so for any further usage, you will need to load or provide a new context

<details><summary>JVM & Android</summary>

````kotlin
sdk.account().logout()
````
>:information_source: In Java, you can use the `logoutAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
sdk.account().logout()
````
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.account().logout();
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.account(sdk);
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource.logout(resource);
````
</details>

### Register ephemeral key
To register a new ephemeral key, you will need to [generate a new key pair](05_CRYPTO.md#generate-a-key-pair)
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.account().registerEphemeralKey(PUBLIC_KEY)
````
>:information_source: In Java, you can use the `registerEphemeralKeyAsync` function, which returns a `CompletableFuture<RegisterEphemeralKeyResponse>` instead
</details>

<details><summary>Swift</summary>

````swift
let response = sdk.account().registerEphemeralKey(publicKey: PUBLIC_KEY)
````
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.account().registerEphemeralKey(PUBLIC_KEY);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.account(sdk);
var data = new RegisterEphemeralKeyData("BASE64_PUBLIC_KEY").toData();
var response = Utils.fromData<RegisterEphemeralKeyResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource.registerEphemeralKeyJson(resource, data));
````
</details>

### Register ephemeral key with secondary authentication
To register a new ephemeral key with secondary authentication, you will need to [generate a new key pair](05_CRYPTO.md#generate-a-key-pair). After the registration you will need to [verify the ephemeral key registration](#verify-ephemeral-key-registration)
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.account().registerEphemeralKeyWithSecondaryAuthentication(PUBLIC_KEY)
````
>:information_source: In Java, you can use the `registerEphemeralKeyWithSecondaryAuthenticationAsync` function, which returns a `CompletableFuture<RegisterEphemeralKeyWithSecondaryAuthenticationResponse>` instead
</details>

<details><summary>Swift</summary>

````swift
let response = sdk.account().registerEphemeralKeyWithSecondaryAuthentication(publicKey: PUBLIC_KEY, method: null)
````
</details>

<details><summary>JS</summary>

````js
const twoFactorMethod = doordeck.com.doordeck.multiplatform.sdk.api.model.TwoFactorMethod;
const response = await doordeck.com.doordeck.multiplatform.sdk.api.account().registerEphemeralKeyWithSecondaryAuthentication(PUBLIC_KEY, twoFactorMethod.EMAIL);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.account(sdk);
var data = new RegisterEphemeralKeyWithSecondaryAuthenticationData("BASE64_PUBLIC_KEY").toData();
var response = Utils.fromData<RegisterEphemeralKeyWithSecondaryAuthenticationResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource.registerEphemeralKeyWithSecondaryAuthenticationJson(resource, data));
````
</details>

### Verify ephemeral key registration
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.account().verifyEphemeralKeyRegistration("CODE", PRIVATE_KEY)
````
>:information_source: In Java, you can use the `verifyEphemeralKeyRegistrationAsync` function, which returns a `CompletableFuture<RegisterEphemeralKeyResponse>` instead
</details>

<details><summary>Swift</summary>

````swift
let response = sdk.account().verifyEphemeralKeyRegistration(code: "CODE", privateKey: PRIVATE_KEY)
````
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.account().verifyEphemeralKeyRegistration("CODE", PRIVATE_KEY);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.account(sdk);
var data = new VerifyEphemeralKeyRegistrationData("CODE", "BASE64_PRIVATE_KEY").toData();
var response = Utils.fromData<RegisterEphemeralKeyResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource.verifyEphemeralKeyRegistrationJson(resource, data));
````
</details>

### Re-verify email
> [!IMPORTANT]
> This function is only available to users with Doordeck issued auth tokens
<details><summary>JVM & Android</summary>

````kotlin
sdk.account().reverifyEmail()
````
>:information_source: In Java, you can use the `reverifyEmailAsync` function, which returns a `CompletableFuture<Unit>` instead
</details>

<details><summary>Swift</summary>

````swift
sdk.account().reverifyEmail()
````
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.account().reverifyEmail();
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.account(sdk);
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource.reverifyEmail(resource);
````
</details>

### Change password
> [!IMPORTANT]
> This function is only available to users with Doordeck issued auth tokens
<details><summary>JVM & Android</summary>

````kotlin
sdk.account().changePassword("OLD_PASSWORD", "NEW_PASSWORD")
````
>:information_source: In Java, you can use the `changePasswordAsync` function, which returns a `CompletableFuture<Unit>` instead
</details>

<details><summary>Swift</summary>

````swift
sdk.account().changePassword(oldPassword: "OLD_PASSWORD", newPassword: "NEW_PASSWORD")
````
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.account().changePassword("OLD_PASSWORD", "NEW_PASSWORD");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.account(sdk);
var data = new ChangePasswordData("OLD_PASSWORD", "NEW_PASSWORD").toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource.changePasswordJson(resource, data);
````
</details>

### Get the user details
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.account().getUserDetails()
````
>:information_source: In Java, you can use the `getUserDetailsAsync` function, which returns a `CompletableFuture<UserDetailsResponse>` instead
</details>

<details><summary>Swift</summary>

````swift
let response = sdk.account().getUserDetails()
````
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.account().getUserDetails();
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.account(sdk);
var response = Utils.fromData<UserDetailsResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource.getUserDetailsJson(resource));
````
</details>

### Update the user details
<details><summary>JVM & Android</summary>

````kotlin
sdk.account().updateUserDetails("DISPLAY_NAME")
````
>:information_source: In Java, you can use the `updateUserDetailsAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
sdk.account().updateUserDetails(displayName: "DISPLAY_NAME")
````
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.account().updateUserDetails("DISPLAY_NAME");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.account(sdk);
var data = new UpdateUserDetailsData("DISPLAY_NAME").toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource.updateUserDetailsJson(resource, data);
````
</details>

### Delete account
> [!CAUTION]
> This operation is executed instantly and is irreversible
<details><summary>JVM & Android</summary>

````kotlin
sdk.account().deleteAccount()
````
>:information_source: In Java, you can use the `deleteAccountAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
sdk.account().deleteAccount()
````
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.account().deleteAccount();
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.account(sdk);
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource.deleteAccount(resource);
````
</details>