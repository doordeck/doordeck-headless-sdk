# SDK Documentation

* Add the SDK into your project
* [Initialize the SDK](#initialize-the-sdk)
* [Crypto](#crypto)
    * [Generate a key pair](#generate-a-key-pair)
* [Context manager](#context-manager)
    * [Set operation context](#set-operation-context)
    * Save, load and clear operation context
* [Accountless resource](#accountless-resource)
    * [Login](#login)
    * [Register a new user](#register-a-new-user)
    * [Verify email](#verify-email)
* [Account resource](#account-resource)
    * [Request a new refresh token](#request-a-new-refresh-token)
    * [Logout](#logout)
    * [Register ephemeral key](#register-ephemeral-key)
    * [Register ephemeral key with secondary authentication](#register-ephemeral-key-with-secondary-authentication)
    * [Verify ephemeral key registration](#verify-ephemeral-key-registration)
    * [Re-verify email](#re-verify-email)
    * [Change password](#change-password)
    * [Get the user details](#get-the-user-details)
    * [Update the user details](#get-the-user-details)
    * [Delete account](#delete-account)
* [Fusion resource](#fusion-resource)
    * [Login](#login-1)
    * [Get integration type](#get-integration-type)
    * [Get integration configuration](#get-integration-configuration)
    * Enable door
    * [Delete door](#delete-door)
    * [Get door status](#get-door-status)
    * [Start door](#start-door)
    * [Stop door](#stop-door)
* [Helper resource](#helper-resource)
    * [Upload platform logo](#upload-platform-logo)

# Initialize the SDK
The SDK should be initialized as the first step. The simplest way to do this is by providing the `ApiEnvironment` and the auth token

It can also be initialized without an auth token, but you will need to manually set an auth token through the `context manager` to use most of the SDK functionalities

<details><summary>JVM</summary>

````kotlin
val sdk = KDoordeckFactory.initialize(ApiEnvironment.PROD, token)
````
</details>

<details><summary>Android</summary>

In Android, the SDK requires you to pass the android application context

````kotlin
val applicationContext = ApplicationContext(context)
val sdk = KDoordeckFactory.initialize(applicationContext, ApiEnvironment.PROD, token)
````
</details>

<details><summary>JS</summary>

````js
const apiEnvironment = doordeck.com.doordeck.multiplatform.sdk.api.model.ApiEnvironment;
const sdk = doordeck.com.doordeck.multiplatform.sdk.KDoordeckFactory.initializeWithAuthToken(apiEnvironment.PROD, token);
````
</details>

<details><summary>C#</summary>

````csharp
var apiEnvironment = symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.model.ApiEnvironment.PROD.get();
var factory = symbols->kotlin.root.com.doordeck.multiplatform.sdk.KDoordeckFactory._instance();
var sdk = symbols->kotlin.root.com.doordeck.multiplatform.sdk.KDoordeckFactory.initializeWithAuthToken(factory, apiEnvironment, token.toSByte());
````
</details>

# Crypto
### Generate a key pair
<details><summary>JVM & Android</summary>

````kotlin
val keyPair = Crypto.generateKeyPair()
````
>:information_source: In Java, you should use `Crypto.INSTANCE.generateKeyPair()` instead
</details>

<details><summary>JS</summary>

````js
const crypto = doordeck.com.doordeck.multiplatform.sdk.util.Crypto;
const keyPair = crypto.generateKeyPair();
````
</details>

<details><summary>C#</summary>

````csharp
var crypto = symbols->kotlin.root.com.doordeck.multiplatform.sdk.util.Crypto._instance();
var keyPair = Utils.fromData<EncodedKeyPair>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.util.Crypto.generateKeyPairJson(crypto));
````
</details>

# Context manager

### Set operation context
By setting the operation context, the usage of the most complex functions from the SDK will be simplified, as you will need to use far fewer parameters with them
<details><summary>JVM & Android</summary>

````kotlin
sdk.contextManager().setOperationContext("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY)
````
</details>

<details><summary>JS</summary>

````js
sdk.contextManager().setOperationContext("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY);
````
</details>

<details><summary>C#</summary>

````csharp
var contextManager = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.contextManager(sdk);
var data = new OperationContextData("USER_ID", USER_CERTIFICATE_CHAIN_LIST, BASE64_PRIVATE_KEY).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager.setOperationContextJson(contextManager, data);
````
</details>

### Save, load and clear operation context

# Accountless resource

### Login
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.accountless().login("EMAIL", "PASSWORD")
````
>:information_source: In Java, you can use the `loginAsync` function, which returns a `CompletableFuture<TokenResponse>` instead
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.accountless().login("EMAIL", "PASSWORD");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.accountless(sdk);
var data = new LoginData("EMAIL", "PASSWORD").toData();
var response = Utils.fromData<TokenResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountlessResource.loginJson(resource, data));
````
</details>

### Register a new user
After the registration you will need to [verify the email](#verify-email)
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.accountless().registration("EMAIL", "PASSWORD", "DISPLAY_NAME", false)
````
>:information_source: In Java, you can use the `registrationAsync` function, which returns a `CompletableFuture<TokenResponse>` instead
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.accountless().registration("EMAIL", "PASSWORD", "DISPLAY_NAME", false);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.accountless(sdk);
var data = new RegistrationData("EMAIL", "PASSWORD", "DISPLAY_NAME", false).toData();
var response = Utils.fromData<TokenResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountlessResource.registrationJson(resource, data));
````
</details>

### Verify email
<details><summary>JVM & Android</summary>

````kotlin
sdk.accountless().verifyEmail("CODE")
````
>:information_source: In Java, you can use the `verifyEmailAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.accountless().verifyEmail("CODE");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.accountless(sdk);
var data = new VerifyEmailData("CODE").toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountlessResource.verifyEmailJson(resource, data);
````
</details>


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
<details><summary>JVM & Android</summary>

````kotlin
sdk.account().logout()
````
>:information_source: In Java, you can use the `logoutAsync` function, which returns a `CompletableFuture<Void>` instead
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
To register a new ephemeral key, you will need to [generate a new key pair](#generate-a-key-pair)
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.account().registerEphemeralKey(PUBLIC_KEY)
````
>:information_source: In Java, you can use the `registerEphemeralKeyAsync` function, which returns a `CompletableFuture<RegisterEphemeralKeyResponse>` instead
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.account().registerEphemeralKey(PUBLIC_KEY);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.account(sdk);
var data = new RegisterEphemeralKeyData("BASE64_ENCODED_PUBLIC_KEY").toData();
var response = Utils.fromData<RegisterEphemeralKeyResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource.registerEphemeralKeyJson(resource, data));
````
</details>

### Register ephemeral key with secondary authentication
To register a new ephemeral key with secondary authentication, you will need to [generate a new key pair](#generate-a-key-pair). After the registration you will need to [verify the ephemeral key registration](#verify-ephemeral-key-registration)
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.account().registerEphemeralKeyWithSecondaryAuthentication(PUBLIC_KEY)
````
>:information_source: In Java, you can use the `registerEphemeralKeyWithSecondaryAuthenticationAsync` function, which returns a `CompletableFuture<RegisterEphemeralKeyWithSecondaryAuthenticationResponse>` instead
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
var data = new RegisterEphemeralKeyWithSecondaryAuthenticationData("BASE64_ENCODED_PUBLIC_KEY").toData();
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

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.account().verifyEphemeralKeyRegistration("CODE", PRIVATE_KEY);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.account(sdk);
var data = new VerifyEphemeralKeyRegistrationData("CODE", "BASE64_ENCODED_PRIVATE_KEY").toData();
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

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.account().getUserDetails();
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.account(sdk);
var response Utils.fromData<UserDetailsResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountResource.getUserDetailsJson(resource));
````
</details>

### Update the user details
<details><summary>JVM & Android</summary>

````kotlin
sdk.account().updateUserDetails("DISPLAY_NAME")
````
>:information_source: In Java, you can use the `updateUserDetailsAsync` function, which returns a `CompletableFuture<Void>` instead
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

# Fusion resource

### Login
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.fusion().login("EMAIL", "PASSWORD")
````
>:information_source: In Java, you can use the `fusionAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.fusion().login("EMAIL", "PASSWORD");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.fusion(sdk);
var data = new FusionLoginData("EMAIL", "PASSWORD").toData();
var response = Utils.fromData<FusionLoginResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.FusionResource.loginJson(resource, data));
````
</details>

### Get integration type
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.fusion().getIntegrationType()
````
>:information_source: In Java, you can use the `getIntegrationTypeAsync` function, which returns a `CompletableFuture<IntegrationTypeResponse>` instead
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.fusion().getIntegrationType();
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.fusion(sdk);
var response = Utils.fromData<IntegrationTypeResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.FusionResource.getIntegrationTypeJson(resource));
````
</details>

### Get integration configuration
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.fusion().getIntegrationConfiguration("TYPE")
````
>:information_source: In Java, you can use the `getIntegrationConfigurationAsync` function, which returns a `CompletableFuture<List<IntegrationConfigurationResponse>>` instead
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.fusion().getIntegrationConfiguration("TYPE");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.fusion(sdk);
var data = new GetIntegrationConfigurationData("TYPE");
var response = Utils.fromData<List<IntegrationConfigurationResponse>>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.FusionResource.getIntegrationConfigurationJson(resource, data));
````
</details>

### Enable door
### Delete door
<details><summary>JVM & Android</summary>

````kotlin
sdk.fusion().deleteDoor("DEVICE_ID")
````
>:information_source: In Java, you can use the `deleteDoorAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.fusion().deleteDoor("DEVICE_ID");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.fusion(sdk);
var data = new DeleteDoorData("DEVICE_ID");
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.FusionResource.deleteDoorJson(resource, data);
````
</details>

### Get door status
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.fusion().getDoorStatus("DEVICE_ID")
````
>:information_source: In Java, you can use the `getDoorStatusAsync` function, which returns a `CompletableFuture<DoorStateResponse>` instead
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.fusion().getDoorStatus("DEVICE_ID");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.fusion(sdk);
var data = new GetDoorStatusData("DEVICE_ID");
var response = Utils.fromData<DoorStateResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.FusionResource.getDoorStatusJson(resource, data));
````
</details>

### Start door
<details><summary>JVM & Android</summary>

````kotlin
sdk.fusion().startDoor("DEVICE_ID")
````
>:information_source: In Java, you can use the `startDoorAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.fusion().startDoor("DEVICE_ID");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.fusion(sdk);
var data = new StartDoorData("DEVICE_ID");
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.FusionResource.startDoorJson(resource, data);
````
</details>

### Stop door
<details><summary>JVM & Android</summary>

````kotlin
sdk.fusion().stopDoor("DEVICE_ID")
````
>:information_source: In Java, you can use the `stopDoorAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.fusion().stopDoor("DEVICE_ID");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.fusion(sdk);
var data = new StopDoorData("DEVICE_ID");
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.FusionResource.stopDoorJson(resource, data);
````
</details>

# Helper resource
This function facilitates the upload of a logo into your application in a single call

### Upload platform logo
<details><summary>JVM & Android</summary>

````kotlin
sdk.helper().uploadPlatformLogo("APPLICATION_ID", "CONTENT_TYPE", IMAGE_BYTES)
````
>:information_source: In Java, you can use the `uploadPlatformLogoAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.helper().uploadPlatformLogo("APPLICATION_ID", "CONTENT_TYPE", IMAGE_BYTES);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.helper(sdk);
var data = new UploadPlatformLogoData("APPLICATION_ID", "CONTENT_TYPE", "BASE64_ENCODED_IMAGE").toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.HelperResource.uploadPlatformLogoJson(resource, data);
````
</details>