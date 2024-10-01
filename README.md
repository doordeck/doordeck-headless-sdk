# SDK Documentation

* Add the SDK into your project
* [Initialize the SDK](#initialize-the-sdk)
* [Crypto](#crypto)
  * [Generate a key pair](#generate-a-key-pair)
* [Context manager](#context-manager)
  * [Set operation context](#set-operation-context)
  * [Set auth token](#set-auth-token)
  * [Set fusion auth token](#set-fusion-auth-token)
  * [Store context](#store-context)
  * [Load context](#load-context)
  * [Clear context](#clear-context)
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
* [Lock operations resource](#lock-operations-resource)
  * [Get single lock](#get-single-lock)
  * [Get lock audit trail](#get-lock-audit-trail)
  * [Get audit for user](#get-audit-for-user)
  * [Get users for lock](#get-users-for-lock)
  * [Get locks for user](#get-locks-for-user)
  * [Update lock name](#update-lock-name)
  * [Update lock favourite](#update-lock-favourite)
  * [Update lock color](#update-lock-color)
  * [Update lock setting default name](#update-lock-setting-default-name)
  * [Set lock setting permitted addresses](#set-lock-setting-permitted-addresses)
  * [Update lock setting hidden](#update-lock-setting-hidden)
  * [Set lock setting time restrictions](#set-lock-setting-time-restrictions)
  * [Update lock setting location restrictions](#update-lock-setting-location-restrictions)
  * [Get a Doordeck user's public key](#get-a-doordeck-users-public-key)
  * [Get user public key by email](#get-user-public-key-by-email)
  * [Get user public key by telephone](#get-user-public-key-by-telephone)
  * [Get user public key by local key](#get-user-public-key-by-local-key)
  * [Get user public key by foreign key](#get-user-public-key-by-foreign-key)
  * [Get user public key by identity](#get-user-public-key-by-identity)
  * [Unlock](#unlock)
  * [Share lock](#share-lock)
  * [Revoke access to lock](#revoke-access-to-lock)
  * [Update secure setting unlock duration](#update-secure-setting-unlock-duration)
  * [Update secure setting unlock between](#update-secure-setting-unlock-between)
  * [Get pinned locks](#get-pinned-locks)
  * [Get shareable locks](#get-shareable-locks)
* [Platform resource](#platform-resource)
  * [Create application](#create-application)
  * [List applications](#list-applications)
  * [Get application](#get-application)
  * [Update application name](#update-application-name)
  * [Update application company name](#update-application-company-name)
  * [Update application mailing address](#update-application-mailing-address)
  * [Update application privacy policy](#update-application-privacy-policy)
  * [Update application support contact](#update-application-support-contact)
  * [Update application app link](#update-application-app-link)
  * [Update application email preferences](#update-application-email-preferences)
  * [Update application logo url](#update-application-log-url)
  * [Delete application](#delete-application)
  * [Get logo upload url](#get-logo-upload-url)
  * [Add auth key](#add-auth-key)
  * [Add auth issuer](#add-auth-issuer)
  * [Delete auth issuer](#delete-auth-issuer)
  * [Add CORS domain](#add-cors-domain)
  * [Remove CORS domain](#remove-cors-domain)
  * [Add application owner](#add-application-owner)
  * [Remove application owner](#remove-application-owner)
  * [Get application owners details](#get-application-owners-details)
* [Sites resource](#sites-resource)
  * [List sites](#list-sites)
  * [Get locks for a site](#get-locks-for-a-site)
  * [Get users for a site](#get-users-for-a-site)
* [Tiles resource](#tiles-resource)
  * [Get locks belonging to a tile](#get-locks-belonging-to-a-tile)
  * [Associate multiple locks](#associate-multiple-locks)
* Samples


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
doordeck_sdk_ExportedSymbols* symbols = Methods.doordeck_sdk_symbols();
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
var data = new OperationContextData("USER_ID", USER_CERTIFICATE_CHAIN_LIST, "BASE64_PRIVATE_KEY").toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager.setOperationContextJson(contextManager, data);
````
</details>

### Set auth token
If you have initialized the SDK without providing an authentication token, you can provide one or update the existing token using this function
<details><summary>JVM & Android</summary>

````kotlin
sdk.contextManager().setAuthToken("AUTH_TOKEN")
````
</details>

<details><summary>JS</summary>

````js
sdk.contextManager().setAuthToken("AUTH_TOKEN");
````
</details>

<details><summary>C#</summary>

````csharp
var contextManager = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.contextManager(sdk);
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager.setAuthToken(contextManager, "AUTH_TOKEN".toSByte());
````
</details>

### Set fusion auth token
<details><summary>JVM & Android</summary>

````kotlin
sdk.contextManager().setFusionAuthToken("FUSION_AUTH_TOKEN")
````
</details>

<details><summary>JS</summary>

````js
sdk.contextManager().setFusionAuthToken("FUSION_AUTH_TOKEN");
````
</details>

<details><summary>C#</summary>

````csharp
var contextManager = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.contextManager(sdk);
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager.setFusionAuthToken(contextManager, "FUSION_AUTH_TOKEN".toSByte());
````
</details>

### Store context
If you have previously set the [operation context](#set-operation-context), [auth token](#set-auth-token) or the [fusion auth token](#set-fusion-auth-token), you can store those context fields in the system so they are persisted between sessions.
<details><summary>JVM</summary>

````kotlin
sdk.contextManager().storeContext()
````
>:information_source: In the JVM, the context is stored using `properties`
</details>

<details><summary>Android</summary>

````kotlin
sdk.contextManager().storeContext()
````
>:information_source: In Android, the context is stored using `shared preference settings`
</details>

<details><summary>JS</summary>

````js
sdk.contextManager().storeContext();
````
>:information_source: In JS, the context is stored using `local storage`
</details>

<details><summary>C#</summary>

````csharp
var contextManager = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.contextManager(sdk);
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager.storeContext(contextManager);
````
>:information_source: In C#, the context is stored using `windows registry`
</details>

### Load context
<details><summary>JVM & Android</summary>

````kotlin
sdk.contextManager().loadContext()
````
</details>

<details><summary>JS</summary>

````js
sdk.contextManager().loadContext();
````
</details>

<details><summary>C#</summary>

````csharp
var contextManager = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.contextManager(sdk);
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager.loadContext(contextManager);
````
</details>

### Clear context
Removes all the stored fields from the system; however, this function does not clear the fields from memory
<details><summary>JVM & Android</summary>

````kotlin
sdk.contextManager().clearContext()
````
</details>

<details><summary>JS</summary>

````js
sdk.contextManager().clearContext();
````
</details>

<details><summary>C#</summary>

````csharp
var contextManager = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.contextManager(sdk);
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.ContextManager.clearContext(contextManager);
````
</details>

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
> [!IMPORTANT]
> When used, the [context manager](#context-manager) is restarted, so for any further usage, you will need to load or provide a new context

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


# Lock operations resource

### Get single lock
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.lockOperations().getSingleLock("LOCK_ID")
````
>:information_source: In Java, you can use the `getSingleLockAsync` function, which returns a `CompletableFuture<LockResponse>` instead
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getSingleLock("LOCK_ID");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new GetSingleLockData("LOCK_ID").toData();
var response = Utils.fromData<LockResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getSingleLockJson(resource, data));
````
</details>

### Get lock audit trail
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.lockOperations().getLockAuditTrail("LOCK_ID", START_EPOCH, END_EPOCH)
````
>:information_source: In Java, you can use the `getLockAuditTrailAsync` function, which returns a `CompletableFuture<List<LockAuditTrailResponse>>` instead
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getLockAuditTrail("LOCK_ID", START_EPOCH, END_EPOCH);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new GetLockAuditTrailData("LOCK_ID", START_EPOCH, END_EPOCH).toData();
var response = Utils.fromData<List<LockAuditTrailResponse>>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getLockAuditTrailJson(resource, data));
````
</details>

### Get audit for user
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.lockOperations().getAuditForUser("USER_ID", START_EPOCH, END_EPOCH)
````
>:information_source: In Java, you can use the `getAuditForUserAsync` function, which returns a `CompletableFuture<List<UserAuditResponse>>` instead
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getAuditForUser("USER_ID", START_EPOCH, END_EPOCH);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new GetAuditForUserData("USER_ID", START_EPOCH, END_EPOCH).toData();
var response = Utils.fromData<List<UserAuditResponse>>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getAuditForUserJson(resource, data));
````
</details>

### Get users for lock
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.lockOperations().getUsersForLock("LOCK_ID")
````
>:information_source: In Java, you can use the `getUsersForLockAsync` function, which returns a `CompletableFuture<List<UserLockResponse>>` instead
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getUsersForLock("LOCK_ID");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new GetUsersForLockData("LOCK_ID").toData();
var response = Utils.fromData<List<UserLockResponse>>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getUsersForLockJson(resource, data));
````
</details>

### Get locks for user
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.lockOperations().getLocksForUser("LOCK_ID")
````
>:information_source: In Java, you can use the `getLocksForUserAsync` function, which returns a `CompletableFuture<LockUserResponse>` instead
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getLocksForUser("LOCK_ID");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new GetLocksForUserData("LOCK_ID").toData();
var response = Utils.fromData<LockUserResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getLocksForUserJson(resource, data));
````
</details>

### Update lock name
<details><summary>JVM & Android</summary>

````kotlin
sdk.lockOperations().updateLockName("LOCK_ID", "LOCK_NAME")
````
>:information_source: In Java, you can use the `updateLockNameAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().updateLockName("LOCK_ID", "LOCK_NAME");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new UpdateLockNameData("LOCK_ID", "LOCK_NAME").toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.updateLockNameJson(resource, data);
````
</details>

### Update lock favourite
<details><summary>JVM & Android</summary>

````kotlin
sdk.lockOperations().updateLockFavourite("LOCK_ID", true)
````
>:information_source: In Java, you can use the `updateLockFavouriteAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().updateLockFavourite("LOCK_ID", true);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new UpdateLockFavouriteData("LOCK_ID", true).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.updateLockFavouriteJson(resource, data);
````
</details>

### Update lock color
<details><summary>JVM & Android</summary>

````kotlin
sdk.lockOperations().updateLockColour("LOCK_ID", "COLOR")
````
>:information_source: In Java, you can use the `updateLockColourAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().updateLockColour("LOCK_ID", "COLOR");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new UpdateLockColourData("LOCK_ID", "COLOR").toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.updateLockColourJson(resource, data);
````
</details>

### Update lock setting default name
<details><summary>JVM & Android</summary>

````kotlin
sdk.lockOperations().updateLockSettingDefaultName("LOCK_ID", "LOCK_NAME")
````
>:information_source: In Java, you can use the `updateLockSettingDefaultNameAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().updateLockSettingDefaultName("LOCK_ID", "LOCK_NAME");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new UpdateLockSettingDefaultNameData("LOCK_ID", "LOCK_NAME").toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.updateLockSettingDefaultNameJson(resource, data);
````
</details>

### Set lock setting permitted addresses
<details><summary>JVM & Android</summary>

````kotlin
sdk.lockOperations().setLockSettingPermittedAddresses("LOCK_ID", PERMITTED_ADDRESSES_LIST)
````
>:information_source: In Java, you can use the `setLockSettingPermittedAddressesAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
const ktList = doordeck.kotlin.collections.KtList;
const permittedAddresses = ktList.fromJsArray(["PERMITTED_ADDRESS"]);
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().setLockSettingPermittedAddresses("LOCK_ID", permittedAddresses);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
List<string> permittedAddresses = ["PERMITTED_ADDRESS"];
var data = new SetLockSettingPermittedAddressesData("LOCK_ID", permittedAddresses).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.setLockSettingPermittedAddressesJson(resource, data);
````
</details>

### Update lock setting hidden
<details><summary>JVM & Android</summary>

````kotlin
sdk.lockOperations().updateLockSettingHidden("LOCK_ID", true)
````
>:information_source: In Java, you can use the `updateLockSettingHiddenAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().updateLockSettingHidden("LOCK_ID", true);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new UpdateLockSettingHiddenData("LOCK_ID", true).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.updateLockSettingHiddenJson(resource, data);
````
</details>

### Set lock setting time restrictions
<details><summary>JVM & Android</summary>

````kotlin
val timeRequirements = listOf(LockOperations.TimeRequirement("START_HH_MM", "END_HH_MM", "TIMEZONE", DAYS_LIST))
sdk.lockOperations().setLockSettingTimeRestrictions("LOCK_ID", timeRequirements)
````
>:information_source: In Java, you can use the `setLockSettingTimeRestrictionsAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
const ktList = doordeck.kotlin.collections.KtList;
const lockOperations = doordeck.com.doordeck.multiplatform.sdk.api.model.LockOperations;
const timeRequirements = ktList.fromJsArray([new lockOperations.TimeRequirement("START_HH_MM", "END_HH_MM", "TIMEZONE", ktList.fromJsArray(["MONDAY"]))]);
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().setLockSettingTimeRestrictions("LOCK_ID", timeRequirements);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
List<string> days = ["MONDAY"];
List<TimeRequirementData> timeRequirementsData = [new TimeRequirementData("START_HH_MM", "END_HH_MM", "TIMEZONE", days)];
var data = new SetLockSettingTimeRestrictionsData("LOCK_ID", timeRequirementsData).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.setLockSettingTimeRestrictionsJson(resource, data);
````
</details>


### Update lock setting location restrictions
<details><summary>JVM & Android</summary>

````kotlin
val locationRequirement = LockOperations.LocationRequirement(LATITUDE, LONGITUDE, true, 100)
sdk.lockOperations().updateLockSettingLocationRestrictions("LOCK_ID", locationRequirement)
````
>:information_source: In Java, you can use the `updateLockSettingLocationRestrictionsAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
const lockOperations = doordeck.com.doordeck.multiplatform.sdk.api.model.LockOperations;
const locationRequirement = new lockOperations.LocationRequirement(LATITUDE, LONGITUDE, ENABLED, RADIUS, ACCURACY);
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().updateLockSettingLocationRestrictions("LOCK_ID", locationRequirement);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var locationRequirementData = new LocationRequirementData(LATITUDE, LONGITUDE, true, 100);
var data = new UpdateLockSettingLocationRestrictionsData("LOCK_ID", locationRequirementData).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.updateLockSettingLocationRestrictionsJson(resource, data);
````
</details>


### Get a Doordeck user's public key
> [!IMPORTANT]
> This function is only available to users with Doordeck issued auth tokens
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.lockOperations().getUserPublicKey("USER_EMAIL", false)
````
>:information_source: In Java, you can use the `getUserPublicKeyAsync` function, which returns a `CompletableFuture<UserPublicKeyResponse>` instead
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getUserPublicKey("USER_EMAIL", false);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new GetUserPublicKeyData("USER_EMAIL", false).toData();
var response = Utils.fromData<UserPublicKeyResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getUserPublicKeyJson(resource, data));
````
</details>

### Get user public key by email
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.lockOperations().getUserPublicKeyByEmail("USER_EMAIL")
````
>:information_source: In Java, you can use the `getUserPublicKeyByEmailAsync` function, which returns a `CompletableFuture<UserPublicKeyResponse>` instead
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getUserPublicKeyByEmail("USER_EMAIL");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new GetUserPublicKeyByEmailData("USER_EMAIL").toData();
var response = Utils.fromData<UserPublicKeyResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getUserPublicKeyByEmailJson(resource, data));
````
</details>

### Get user public key by telephone
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.lockOperations().getUserPublicKeyByTelephone("USER_TELEPHONE")
````
>:information_source: In Java, you can use the `getUserPublicKeyByTelephoneAsync` function, which returns a `CompletableFuture<UserPublicKeyResponse>` instead
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getUserPublicKeyByTelephone("USER_TELEPHONE");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new GetUserPublicKeyByTelephoneData("USER_TELEPHONE").toData();
var response = Utils.fromData<UserPublicKeyResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getUserPublicKeyByTelephoneJson(resource, data));
````
</details>

### Get user public key by local key
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.lockOperations().getUserPublicKeyByLocalKey("USER_LOCAL_KEY")
````
>:information_source: In Java, you can use the `getUserPublicKeyByLocalKeyAsync` function, which returns a `CompletableFuture<UserPublicKeyResponse>` instead
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getUserPublicKeyByLocalKey("USER_LOCAL_KEY");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new GetUserPublicKeyByLocalKeyData("USER_LOCAL_KEY").toData();
var response = Utils.fromData<UserPublicKeyResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getUserPublicKeyByLocalKeyJson(resource, data));
````
</details>

### Get user public key by foreign key
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.lockOperations().getUserPublicKeyByForeignKey("USER_FOREIGN_KEY")
````
>:information_source: In Java, you can use the `getUserPublicKeyByForeignKeyAsync` function, which returns a `CompletableFuture<UserPublicKeyResponse>` instead
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getUserPublicKeyByForeignKey("USER_FOREIGN_KEY");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new GetUserPublicKeyByForeignKeyData("USER_FOREIGN_KEY").toData();
var response = Utils.fromData<UserPublicKeyResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getUserPublicKeyByForeignKeyJson(resource, data));
````
</details>

### Get user public key by identity
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.lockOperations().getUserPublicKeyByIdentity("USER_IDENTITY")
````
>:information_source: In Java, you can use the `getUserPublicKeyByIdentityAsync` function, which returns a `CompletableFuture<UserPublicKeyResponse>` instead
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getUserPublicKeyByIdentity("USER_IDENTITY");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new GetUserPublicKeyByIdentityData("USER_IDENTITY").toData();
var response = Utils.fromData<UserPublicKeyResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getUserPublicKeyByIdentityJson(resource, data));
````
</details>

### Unlock
<details><summary>JVM & Android</summary>

````kotlin
val unlockOperation = LockOperations.UnlockOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID")
val response = sdk.lockOperations().unlock(unlockOperation)
````
>:information_source: In Java, you can use the `unlockAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
const lockOperations = doordeck.com.doordeck.multiplatform.sdk.api.model.LockOperations;
const baseOperation = new lockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST,
        PRIVATE_KEY, "LOCK_ID", NOT_BEFORE, ISSUED_AT, EXPIRES_AT, "UUID");
const unlockOperation = new lockOperations.UnlockOperation(baseOperation, null);
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().unlock(unlockOperation);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var baseOperationData = new BaseOperationData("USER_ID", USER_CERTIFICATE_CHAIN_LIST, "BASE64_PRIVATE_KEY", LOCK_ID);
var data = new UnlockOperationData(baseOperationData).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.unlockJson(resource, data);
````
</details>

### Share lock
<details><summary>JVM & Android</summary>

````kotlin
val shareLockOperation = LockOperations.ShareLock("TARGET_USER_ID", TARGET_USER_ROLE, TARGET_PUBLIC_KEY)
sdk.lockOperations().shareLock("LOCK_ID", shareLockOperation)
````
>:information_source: In Java, you can use the `shareLockAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
const lockOperations = doordeck.com.doordeck.multiplatform.sdk.api.model.LockOperations;
const userRole = doordeck.com.doordeck.multiplatform.sdk.api.model.UserRole;
const shareLockOperation = new lockOperations.ShareLock("TARGET_USER_ID", userRole.USER, TARGET_PUBLIC_KEY, null, null);
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().shareLock("LOCK_ID", shareLockOperation);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new ShareLockData("TARGET_USER_ID", TARGET_USER_ROLE, "BASE64_TARGET_PUBLIC_KEY").toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.shareLockJson(resource, data);
````
</details>

### Revoke access to lock
<details><summary>JVM & Android</summary>

````kotlin
val baseOperation = LockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID")
val revokeAccessToLockOperation = LockOperations.RevokeAccessToLockOperation(baseOperation, USER_LIST)
sdk.lockOperations().revokeAccessToLock(revokeAccessToLockOperation)
````
>:information_source: In Java, you can use the `revokeAccessToLockAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
const ktList = doordeck.kotlin.collections.KtList;
const userList = ktList.fromJsArray(["USER_ID"]);
const lockOperations = doordeck.com.doordeck.multiplatform.sdk.api.model.LockOperations;
const baseOperation = new lockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST,
        PRIVATE_KEY, "LOCK_ID", NOT_BEFORE, ISSUED_AT, EXPIRES_AT, "UUID");
const revokeAccessToLockOperation = new lockOperations.RevokeAccessToLockOperation(baseOperation, userList);
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().revokeAccessToLock(revokeAccessToLockOperation);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
List<string> userList = ["USER_ID"];
var baseOperationData = new BaseOperationData("USER_ID", USER_CERTIFICATE_CHAIN_LIST, "BASE64_PRIVATE_KEY", LOCK_ID);
var data = new RevokeAccessToLockOperationData(baseOperationData, userList).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.revokeAccessToLockJson(resource, data);
````
</details>

### Update secure setting unlock duration
<details><summary>JVM & Android</summary>

````kotlin
val baseOperation = LockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID")
val updateSecureSettingUnlockDuration = LockOperations.UpdateSecureSettingUnlockDuration(baseOperation, UNLOCK_DURATION)
sdk.lockOperations().updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration)
````
>:information_source: In Java, you can use the `updateSecureSettingUnlockDurationAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
const lockOperations = doordeck.com.doordeck.multiplatform.sdk.api.model.LockOperations;
const baseOperation = new lockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST,
        PRIVATE_KEY, "LOCK_ID", NOT_BEFORE, ISSUED_AT, EXPIRES_AT, "UUID");
const updateSecureSettingUnlockDuration = new lockOperations.UpdateSecureSettingUnlockDuration(baseOperation, UNLOCK_DURATION);
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var baseOperationData = new BaseOperationData("USER_ID", USER_CERTIFICATE_CHAIN_LIST, "BASE64_PRIVATE_KEY", "LOCK_ID");
var data = new UpdateSecureSettingUnlockDurationData(baseOperationData, UNLOCK_DURATION).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.updateSecureSettingUnlockDurationJson(resource, data);
````
</details>


### Update secure setting unlock between
<details><summary>JVM & Android</summary>

````kotlin
val baseOperation = LockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID")
val unlockBetween = LockOperations.UnlockBetween("START_HH_MM", "END_HH_MM", "TIMEZONE", DAYS_LIST)
val updateSecureSettingUnlockBetween = LockOperations.UpdateSecureSettingUnlockBetween(baseOperation, unlockBetween)
sdk.lockOperations().updateSecureSettingUnlockBetween(updateSecureSettingUnlockBetween)
````
>:information_source: In Java, you can use the `updateSecureSettingUnlockBetweenAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
const lockOperations = doordeck.com.doordeck.multiplatform.sdk.api.model.LockOperations;
const baseOperation = new lockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST,
        PRIVATE_KEY, "LOCK_ID", NOT_BEFORE, ISSUED_AT, EXPIRES_AT, "UUID");
const unlockBetween = lockOperations.UnlockBetween("START_HH_MM", "END_HH_MM", "TIMEZONE", DAYS_LIST, EXCEPTIONS_LIST);
const updateSecureSettingUnlockBetween = new lockOperations.UpdateSecureSettingUnlockBetween(baseOperation, unlockBetween)
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().updateSecureSettingUnlockBetween(updateSecureSettingUnlockBetween);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var baseOperationData = new BaseOperationData("USER_ID", USER_CERTIFICATE_CHAIN_LIST, "BASE64_PRIVATE_KEY", "LOCK_ID");
var unlockBetweenData = new UnlockBetweenData("START_HH_MM", "END_HH_MM", "TIMEZONE", DAYS_LIST);
var data = new UpdateSecureSettingUnlockBetweenData(baseOperationData, unlockBetweenData).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.updateSecureSettingUnlockBetweenJson(resource, data);
````
</details>

### Get pinned locks
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.lockOperations().getPinnedLocks()
````
>:information_source: In Java, you can use the `getPinnedLocksAsync` function, which returns a `CompletableFuture<List<LockResponse>>` instead
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getPinnedLocks();
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var response = Utils.fromData<List<LockResponse>>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getPinnedLocksJson(resource));
````
</details>

### Get shareable locks
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.lockOperations().getShareableLocks()
````
>:information_source: In Java, you can use the `getShareableLocksAsync` function, which returns a `CompletableFuture<List<ShareableLockResponse>>` instead
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getShareableLocks();
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var response = Utils.fromData<List<ShareableLockResponse>>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getShareableLocksJson(resource));
````
</details>


# Platform resource
> [!IMPORTANT]
> All of the platform functions are only available to users with Doordeck issued auth tokens

### Create application
<details><summary>JVM & Android</summary>

````kotlin
val application = Platform.CreateApplication("APPLICATION_NAME", "COMPANY_NAME", "COMPANY@MAIL.COM")
sdk.platform().createApplication(application)
````
>:information_source: In Java, you can use the `createApplicationAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
const platform = doordeck.com.doordeck.multiplatform.sdk.api.model.Platform;
const application = new platform.CreateApplication("APPLICATION_NAME", "COMPANY_NAME", "COMPANY@MAIL.COM", null, null, null, null, null);
await doordeck.com.doordeck.multiplatform.sdk.api.platform().createApplication(application);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.platform(sdk);
var data = new CreateApplicationData("APPLICATION_NAME", "COMPANY_NAME", "COMPANY@MAIL.COM").toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.createApplicationJson(resource, data);
````
</details>

### List applications
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.platform().listApplications()
````
>:information_source: In Java, you can use the `listApplicationsAsync` function, which returns a `CompletableFuture<List<ApplicationResponse>>` instead
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.platform().listApplications();
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.platform(sdk);
var response = Utils.fromData<List<ApplicationResponse>>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.listApplicationsJson(resource));
````
</details>

### Get application
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.platform().getApplication("APPLICATION_ID")
````
>:information_source: In Java, you can use the `getApplicationAsync` function, which returns a `CompletableFuture<ApplicationResponse>` instead
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.platform().getApplication("APPLICATION_ID");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.platform(sdk);
var data = new GetApplicationData("APPLICATION_ID").toData();
var response = Utils.fromData<ApplicationResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.getApplicationJson(resource, data));
````
</details>

### Update application name
<details><summary>JVM & Android</summary>

````kotlin
sdk.platform().updateApplicationName("APPLICATION_ID", "APPLICATION_NAME")
````
>:information_source: In Java, you can use the `updateApplicationNameAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.platform().updateApplicationName("APPLICATION_ID", "APPLICATION_NAME");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.platform(sdk);
var data = new UpdateApplicationNameData("APPLICATION_ID","APPLICATION_NAME").toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.updateApplicationNameJson(resource, data);
````
</details>

### Update application company name
<details><summary>JVM & Android</summary>

````kotlin
sdk.platform().updateApplicationCompanyName("APPLICATION_ID", "APPLICATION_COMPANY_NAME")
````
>:information_source: In Java, you can use the `updateApplicationCompanyNameAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.platform().updateApplicationCompanyName("APPLICATION_ID", "APPLICATION_COMPANY_NAME");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.platform(sdk);
var data = new UpdateApplicationCompanyNameData("APPLICATION_ID", "APPLICATION_COMPANY_NAME").toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.updateApplicationCompanyNameJson(resource, data);
````
</details>

### Update application mailing address
<details><summary>JVM & Android</summary>

````kotlin
sdk.platform().updateApplicationMailingAddress("APPLICATION_ID", "COMPANY@MAIL.COM")
````
>:information_source: In Java, you can use the `updateApplicationMailingAddressAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.platform().updateApplicationMailingAddress("APPLICATION_ID", "COMPANY@MAIL.COM");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.platform(sdk);
var data = new UpdateApplicationMailingAddressData("APPLICATION_ID", "COMPANY@MAIL.COM").toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.updateApplicationMailingAddressJson(resource, data);
````
</details>

### Update application privacy policy
<details><summary>JVM & Android</summary>

````kotlin
sdk.platform().updateApplicationPrivacyPolicy("APPLICATION_ID", "PRIVACY_POLICY")
````
>:information_source: In Java, you can use the `updateApplicationPrivacyPolicyAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.platform().updateApplicationPrivacyPolicy("APPLICATION_ID", "PRIVACY_POLICY");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.platform(sdk);
var data = new UpdateApplicationPrivacyPolicyData("APPLICATION_ID", "PRIVACY_POLICY").toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.updateApplicationPrivacyPolicyJson(resource, data);
````
</details>

### Update application support contact
<details><summary>JVM & Android</summary>

````kotlin
sdk.platform().updateApplicationSupportContact("APPLICATION_ID", "SUPPORT_CONTACT_URL")
````
>:information_source: In Java, you can use the `updateApplicationSupportContactAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.platform().updateApplicationSupportContact("APPLICATION_ID", "SUPPORT_CONTACT_URL");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.platform(sdk);
var data = new UpdateApplicationSupportContactData("APPLICATION_ID", "SUPPORT_CONTACT_URL").toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.updateApplicationSupportContactJson(resource, data);
````
</details>

### Update application app link
<details><summary>JVM & Android</summary>

````kotlin
sdk.platform().updateApplicationAppLink("APPLICATION_ID", "APP_LINK")
````
>:information_source: In Java, you can use the `updateApplicationAppLinkAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.platform().updateApplicationAppLink("APPLICATION_ID", "APP_LINK");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.platform(sdk);
var data = new UpdateApplicationAppLinkData("APPLICATION_ID", "APP_LINK").toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.updateApplicationAppLinkJson(resource, data);
````
</details>

### Update application email preferences
<details><summary>JVM & Android</summary>

````kotlin
val emailPreferences = Platform.EmailPreferences("SENDER_EMAIL", "SENDER_NAME", "PRIMARY_COLOR", "SECONDARY_COLOR", false, null)
sdk.platform().updateApplicationEmailPreferences("APPLICATION_ID", emailPreferences)
````
>:information_source: In Java, you can use the `updateApplicationEmailPreferencesAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
const platform = doordeck.com.doordeck.multiplatform.sdk.api.model.Platform;
const emailPreferences = new platform.EmailPreferences("SENDER_EMAIL", "SENDER_NAME", "PRIMARY_COLOR", "SECONDARY_COLOR", false, null);
await doordeck.com.doordeck.multiplatform.sdk.api.platform().updateApplicationEmailPreferences("APPLICATION_ID", emailPreferences);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.platform(sdk);
var emailPreferencesData = new EmailPreferencesData("SENDER_EMAIL", "SENDER_NAME", "PRIMARY_COLOR", "SECONDARY_COLOR", false);
var data = new UpdateApplicationEmailPreferencesData("APPLICATION_ID", emailPreferencesData).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.updateApplicationEmailPreferencesJson(resource, data);
````
</details>

### Update application log url
<details><summary>JVM & Android</summary>

````kotlin
sdk.platform().updateApplicationLogoUrl("APPLICATION_ID", "LOGO_URL")
````
>:information_source: In Java, you can use the `updateApplicationLogoUrlAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.platform().updateApplicationLogoUrl("APPLICATION_ID", "LOGO_URL");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.platform(sdk);
var data = new UpdateApplicationLogoUrlData("APPLICATION_ID", "LOGO_URL").toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.updateApplicationLogoUrlJson(resource, data);
````
</details>

### Delete application
<details><summary>JVM & Android</summary>

````kotlin
sdk.platform().deleteApplication("APPLICATION_ID")
````
>:information_source: In Java, you can use the `deleteApplicationAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.platform().deleteApplication("APPLICATION_ID");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.platform(sdk);
var data = new DeleteApplicationData("APPLICATION_ID").toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.deleteApplicationJson(resource, data);
````
</details>

### Get logo upload url
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.platform().getLogoUploadUrl("APPLICATION_ID", "CONTENT_TYPE")
````
>:information_source: In Java, you can use the `getLogoUploadUrlAsync` function, which returns a `CompletableFuture<GetLogoUploadUrlResponse>` instead
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.platform().getLogoUploadUrl("APPLICATION_ID", "CONTENT_TYPE");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.platform(sdk);
var data = new GetLogoUploadUrlData("APPLICATION_ID", "CONTENT_TYPE").toData();
var response = Utils.fromData<GetLogoUploadUrlResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.getLogoUploadUrlJson(resource, data));
````
</details>

### Add auth key
<details><summary>JVM & Android</summary>

````kotlin
val key = Platform.Ed25519Key("OKP", "sig", "90a983fd-9077-41f9-840c-7220581017f5", "EdDSA", "zVfpB5Nfj4SzYayFpTu4Qm1JaUmk6-FBbFUX3k1qqwc", "Ed25519", "0ufELXg9OUjkAZUs5aGdgVbz664erh8t9cTvFBHicrc")
sdk.platform().addAuthKey("APPLICATION_ID", key)
````
>:information_source: In Java, you can use the `addAuthKeyAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
const platform = doordeck.com.doordeck.multiplatform.sdk.api.model.Platform;
const key = platform.Ed25519Key("OKP", "sig", "90a983fd-9077-41f9-840c-7220581017f5", "EdDSA", "zVfpB5Nfj4SzYayFpTu4Qm1JaUmk6-FBbFUX3k1qqwc", "Ed25519", "0ufELXg9OUjkAZUs5aGdgVbz664erh8t9cTvFBHicrc");
await doordeck.com.doordeck.multiplatform.sdk.api.platform().addAuthKey("APPLICATION_ID", key);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.platform(sdk);
var keyData = new Ed25519KeyData("sig", "90a983fd-9077-41f9-840c-7220581017f5", "EdDSA", "zVfpB5Nfj4SzYayFpTu4Qm1JaUmk6-FBbFUX3k1qqwc", "Ed25519", "0ufELXg9OUjkAZUs5aGdgVbz664erh8t9cTvFBHicrc");
var data = new AddAuthKeyData("APPLICATION_ID", keyData).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.addAuthKeyJson(resource, data);
````
</details>


### Add auth issuer
<details><summary>JVM & Android</summary>

````kotlin
sdk.platform().addAuthIssuer("APPLICATION_ID", "URL")
````
>:information_source: In Java, you can use the `addAuthIssuerAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.platform().addAuthIssuer("APPLICATION_ID", "URL");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.platform(sdk);
var data = new AddAuthIssuerData("APPLICATION_ID", "URL").toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.addAuthIssuerJson(resource, data);
````
</details>

### Delete auth issuer
<details><summary>JVM & Android</summary>

````kotlin
sdk.platform().deleteAuthIssuer("APPLICATION_ID", "URL")
````
>:information_source: In Java, you can use the `deleteAuthIssuerAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.platform().deleteAuthIssuer("APPLICATION_ID", "URL");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.platform(sdk);
var data = new DeleteAuthIssuerData("APPLICATION_ID", "URL").toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.deleteAuthIssuerJson(resource, data);
````
</details>

### Add CORS domain
<details><summary>JVM & Android</summary>

````kotlin
sdk.platform().addCorsDomain("APPLICATION_ID", "URL")
````
>:information_source: In Java, you can use the `addCorsDomainAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.platform().addCorsDomain("APPLICATION_ID", "URL");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.platform(sdk);
var data = new AddCorsDomainData("APPLICATION_ID", "URL").toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.addCorsDomainJson(resource, data);
````
</details>

### Remove CORS domain
<details><summary>JVM & Android</summary>

````kotlin
sdk.platform().removeCorsDomain("APPLICATION_ID", "URL")
````
>:information_source: In Java, you can use the `removeCorsDomainAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.platform().removeCorsDomain("APPLICATION_ID", "URL");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.platform(sdk);
var data = new RemoveCorsDomainData("APPLICATION_ID", "URL").toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.removeCorsDomainJson(resource, data);
````
</details>

### Add application owner
<details><summary>JVM & Android</summary>

````kotlin
sdk.platform().addApplicationOwner("APPLICATION_ID", "OWNER_ID")
````
>:information_source: In Java, you can use the `addApplicationOwnerAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.platform().addApplicationOwner("APPLICATION_ID", "OWNER_ID");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.platform(sdk);
var data = new AddApplicationOwnerData("APPLICATION_ID", "OWNER_ID").toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.addApplicationOwnerJson(resource, data);
````
</details>

### Remove application owner
<details><summary>JVM & Android</summary>

````kotlin
sdk.platform().removeApplicationOwner("APPLICATION_ID", "OWNER_ID")
````
>:information_source: In Java, you can use the `removeApplicationOwnerAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.platform().removeApplicationOwner("APPLICATION_ID", "OWNER_ID");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.platform(sdk);
var data = new RemoveApplicationOwnerData("APPLICATION_ID", "OWNER_ID").toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.removeApplicationOwnerJson(resource, data);
````
</details>

### Get application owners details
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.platform().getApplicationOwnersDetails("APPLICATION_ID")
````
>:information_source: In Java, you can use the `getApplicationOwnersDetailsAsync` function, which returns a `CompletableFuture<List<ApplicationOwnerDetailsResponse>>` instead
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.platform().getApplicationOwnersDetails("APPLICATION_ID");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.platform(sdk);
var data = new GetApplicationOwnersDetailsData("APPLICATION_ID").toData();
var response = Utils.fromData<List<ApplicationOwnerDetailsResponse>>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.PlatformResource.getApplicationOwnersDetailsJson(resource, data));
````
</details>


# Sites resource

### List sites
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.sites().listSites()
````
>:information_source: In Java, you can use the `listSitesAsync` function, which returns a `CompletableFuture<List<SiteResponse>>` instead
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.sites().listSites();
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.sites(sdk);
var response = Utils.fromData<List<SiteResponse>>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.SitesResource.listSitesJson(resource));
````
</details>

### Get locks for a site
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.sites().getLocksForSite("SITE_ID")
````
>:information_source: In Java, you can use the `getLocksForSiteAsync` function, which returns a `CompletableFuture<List<SiteLocksResponse>>` instead
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.sites().getLocksForSite("SITE_ID");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.sites(sdk);
var data = new GetLocksForSiteData("SITE_ID").toData();
var response = Utils.fromData<List<SiteLocksResponse>>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.SitesResource.getLocksForSiteJson(resource, data));
````
</details>

### Get users for a site
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.sites().getUsersForSite("SITE_ID")
````
>:information_source: In Java, you can use the `getUsersForSiteAsync` function, which returns a `CompletableFuture<List<UserForSiteResponse>>` instead
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.sites().getUsersForSite("SITE_ID");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.sites(sdk);
var data = new GetUsersForSiteData("SITE_ID").toData();
var response = Utils.fromData<List<UserForSiteResponse>>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.SitesResource.getUsersForSiteJson(resource, data));
````
</details>


# Tiles resource

### Get locks belonging to a tile
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.tiles().getLocksBelongingToTile("TILE_ID")
````
>:information_source: In Java, you can use the `getLocksBelongingToTileAsync` function, which returns a `CompletableFuture<TileLocksResponse>` instead
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.tiles().getLocksBelongingToTile("TILE_ID");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.tiles(sdk);
var data = new GetLocksBelongingToTileData("TILE_ID").toData();
var response = Utils.fromData<TileLocksResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.TilesResource.getLocksBelongingToTileJson(resource, data));
````
</details>

### Associate multiple locks
<details><summary>JVM & Android</summary>

````kotlin
sdk.tiles().associateMultipleLocks("TILE_ID", "SITE_ID", LOCK_ID_LIST)
````
>:information_source: In Java, you can use the `associateMultipleLocksAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>JS</summary>

````js
const ktList = doordeck.kotlin.collections.KtList;
const lockIdList = ktList.fromJsArray(["LOCK_ID"]);
await doordeck.com.doordeck.multiplatform.sdk.api.tiles().associateMultipleLocks("TILE_ID", "SITE_ID", lockIdList);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.tiles(sdk);
List<string> lockIdList = ["LOCK_ID"];
var data = new AssociateMultipleLocksData("TILE_ID", "SITE_ID", lockIdList).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.TilesResource.associateMultipleLocksJson(resource, data);
````
</details>