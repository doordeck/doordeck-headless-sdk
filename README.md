# SDK Documentation

* [Build the project](#build-the-project)
* [Add the SDK into your project](#add-the-sdk-into-your-project)
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
* [Fusion resource](#fusion-resource:API-FUSION.md)
    * [Login](#login-1)
    * [Get integration type](#get-integration-type)
    * [Get integration configuration](#get-integration-configuration)
    * [Enable door](#enable-door)
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
    * [Unlock with context](#unlock-with-context)
    * [Share lock](#share-lock)
    * [Share lock with context](#share-lock-with-context)
    * [Revoke access to lock](#revoke-access-to-lock)
    * [Revoke access to lock with context](#revoke-access-to-lock-with-context)
    * [Update secure setting unlock duration](#update-secure-setting-unlock-duration)
    * [Update secure setting unlock duration with context](#update-secure-setting-unlock-duration-with-context)
    * [Update secure setting unlock between](#update-secure-setting-unlock-between)
    * [Update secure setting unlock between with context](#update-secure-setting-unlock-between-with-context)
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

# Build the project
To build the project, you need to specify the Android SDK directory. To do this, create a `local.properties` file in the root project directory with the following configuration: `sdk.dir=ANDROID_SDK_DIR`

> [!NOTE]  
> It’s normal to see `Unresolved references`. For example, if you are using Windows, you won’t be able to resolve macOS-specific imports

# Add the SDK into your project
<details><summary>JVM</summary>

````kotlin
implementation("doordeck-headless-sdk:library-jvm:0.22.0")
````
>:information_source: The JVM package requires at least Java SDK 1.8
</details>

<details><summary>Android</summary>

````kotlin
implementation("doordeck-headless-sdk:library-android:0.22.0")
````
>:information_source: The Android package requires at least Android SDK 21
</details>

<details><summary>Swift</summary>

````swift
// TODO
````
>:information_source: The iOS package requires at least iOS version 15
</details>

<details><summary>JS</summary>

````js
// TODO
````
</details>

<details><summary>C#</summary>

````csharp
// TODO
````
</details>

# Initialize the SDK
The SDK should be initialized as the first step. The simplest way to do this is by providing the `ApiEnvironment` and the auth token

It can also be initialized without an auth token, but you will need to manually [set an auth token through the context manager](#set-auth-token) to use most of the SDK functionalities

<details><summary>JVM</summary>

````kotlin
val sdk = KDoordeckFactory.initialize(ApiEnvironment.PROD, "AUTH_TOKEN")
````
</details>

<details><summary>Android</summary>

In Android, the SDK requires you to pass the android application context

````kotlin
val applicationContext = ApplicationContext(context)
val sdk = KDoordeckFactory.initialize(applicationContext, ApiEnvironment.PROD, "AUTH_TOKEN")
````
</details>

<details><summary>Swift</summary>

````swift
let sdk = KDoordeckFactory().initialize(apiEnvironment: .prod, token: "AUTH_TOKEN")
````
</details>

<details><summary>JS</summary>

````js
const apiEnvironment = doordeck.com.doordeck.multiplatform.sdk.api.model.ApiEnvironment;
const sdk = doordeck.com.doordeck.multiplatform.sdk.KDoordeckFactory.initializeWithAuthToken(apiEnvironment.PROD, "AUTH_TOKEN");
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

<details><summary>Swift</summary>

````swift
let keyPair = Crypto().generateKeyPair()
````
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

<details><summary>Swift</summary>

````swift
sdk.contextManager().setOperationContext(userId: "USER_ID", certificateChain: USER_CERTIFICATE_CHAIN_LIST, privateKey: PRIVATE_KEY)
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

<details><summary>Swift</summary>

````swift
sdk.contextManager().setAuthToken(token: "AUTH_TOKEN")
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

<details><summary>Swift</summary>

````swift
sdk.contextManager().setFusionAuthToken(token: "FUSION_AUTH_TOKEN")
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

<details><summary>Swift</summary>

````swift
sdk.contextManager().storeContext()
````

>:information_source: In the JVM, the context is stored using `keychain`
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

<details><summary>Swift</summary>

````swift
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

<details><summary>Swift</summary>

````swift
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

<details><summary>Swift</summary>

````swift
let response = sdk.accountless().login(email: "EMAIL", password: "PASSWORD")
````
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

<details><summary>Swift</summary>

````swift
let response = sdk.accountless().registration(email: "EMAIL", password: "PASSWORD", displayName: "DISPLAY_NAME", force: false)
````
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

<details><summary>Swift</summary>

````swift
sdk.accountless().verifyEmail(code: "CODE")
````
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
> When used, the [context manager](#context-manager) is restarted, so for any further usage, you will need to load or provide a new context

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
To register a new ephemeral key, you will need to [generate a new key pair](#generate-a-key-pair)
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
To register a new ephemeral key with secondary authentication, you will need to [generate a new key pair](#generate-a-key-pair). After the registration you will need to [verify the ephemeral key registration](#verify-ephemeral-key-registration)
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

# Helper resource
This function facilitates the upload of a logo into your application in a single call

### Upload platform logo
<details><summary>JVM & Android</summary>

````kotlin
sdk.helper().uploadPlatformLogo("APPLICATION_ID", "CONTENT_TYPE", IMAGE_BYTES)
````
>:information_source: In Java, you can use the `uploadPlatformLogoAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
sdk.helper().uploadPlatformLogo(applicationId: "APPLICATION_ID", contentType: "CONTENT_TYPE", image: IMAGE_BYTES)
````
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.helper().uploadPlatformLogo("APPLICATION_ID", "CONTENT_TYPE", IMAGE_BYTES);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.helper(sdk);
var data = new UploadPlatformLogoData("APPLICATION_ID", "CONTENT_TYPE", "BASE64_IMAGE").toData();
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

<details><summary>Swift</summary>

````swift
let response = sdk.lockOperations().getSingleLock(lockId: "LOCK_ID")
````
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

<details><summary>Swift</summary>

````swift
let response = sdk.lockOperations().getLockAuditTrail(lockId: "LOCK_ID", start: START_EPOCH, end: END_EPOCH)
````
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

<details><summary>Swift</summary>

````swift
let response = sdk.lockOperations().getAuditForUser(userId: "USER_ID", start: START_EPOCH, end: END_EPOCH)
````
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

<details><summary>Swift</summary>

````swift
let response = sdk.lockOperations().getUsersForLock(lockId: "LOCK_ID")
````
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

<details><summary>Swift</summary>

````swift
let response = sdk.lockOperations().getLocksForUser(lockId: "LOCK_ID")
````
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

<details><summary>Swift</summary>

````swift
sdk.lockOperations().updateLockName(lockId: "LOCK_ID", name: "LOCK_NAME")
````
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

<details><summary>Swift</summary>

````swift
sdk.lockOperations().updateLockFavourite(lockId: "LOCK_ID", favourite: true)
````
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

<details><summary>Swift</summary>

````swift
sdk.lockOperations().updateLockColour(lockId: "LOCK_ID", color: "COLOR")
````
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

<details><summary>Swift</summary>

````swift
sdk.lockOperations().updateLockSettingDefaultName(lockId: "LOCK_ID", name: "LOCK_NAME")
````
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
sdk.lockOperations().setLockSettingPermittedAddresses("LOCK_ID", listOf("PERMITTED_ADDRESS"))
````
>:information_source: In Java, you can use the `setLockSettingPermittedAddressesAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
sdk.lockOperations().setLockSettingPermittedAddresses(lockId: "LOCK_ID", permittedAddresses: ["PERMITTED_ADDRESS"])
````
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

<details><summary>Swift</summary>

````swift
sdk.lockOperations().updateLockSettingHidden(lockId: "LOCK_ID", hidden: true)
````
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
val timeRequirements = listOf(LockOperations.TimeRequirement("START_HH_MM", "END_HH_MM", "TIMEZONE", listOf("MONDAY")))
sdk.lockOperations().setLockSettingTimeRestrictions("LOCK_ID", timeRequirements)
````
>:information_source: In Java, you can use the `setLockSettingTimeRestrictionsAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
let timeRequirements = [LockOperations.TimeRequirement(start: "START_HH_MM", end: "END_HH_MM", timezone: "TIMEZONE", days: ["MONDAY"])]
sdk.lockOperations().setLockSettingTimeRestrictions(lockId: "LOCK_ID", times: locationRequirement)
````
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

<details><summary>Swift</summary>

````swift
let locationRequirement = LockOperations.LocationRequirement(latitude: LATITUDE, longitude: LONGITUDE, enabled: true, radius: 100, accuracy: 100)
sdk.lockOperations().updateLockSettingLocationRestrictions(lockId: "LOCK_ID", location: locationRequirement)
````
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

<details><summary>Swift</summary>

````swift
let response = sdk.lockOperations().getUserPublicKey(userEmail: "USER_EMAIL", visitor: false)
````
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

<details><summary>Swift</summary>

````swift
let response = sdk.lockOperations().getUserPublicKeyByEmail(email: "USER_EMAIL")
````
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

<details><summary>Swift</summary>

````swift
let response = sdk.lockOperations().getUserPublicKeyByTelephone(telephone: "USER_TELEPHONE")
````
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

<details><summary>Swift</summary>

````swift
let response = sdk.lockOperations().getUserPublicKeyByLocalKey(localKey: "USER_LOCAL_KEY")
````
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

<details><summary>Swift</summary>

````swift
let response = sdk.lockOperations().getUserPublicKeyByForeignKey(foreignKey: "USER_FOREIGN_KEY")
````
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

<details><summary>Swift</summary>

````swift
let response = sdk.lockOperations().getUserPublicKeyByIdentity(identity: "USER_IDENTITY")
````
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
val baseOperation = LockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID")
val unlockOperation = LockOperations.UnlockOperation(baseOperation)
sdk.lockOperations().unlock(unlockOperation)
````
>:information_source: In Java, you can use the `unlockAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
let baseOperation = LockOperations.BaseOperation(userId: "USER_ID", userCertificateChain: USER_CERTIFICATE_CHAIN_LIST, userPrivateKey: PRIVATE_KEY, lockId: "LOCK_ID", notBefore: NOT_BEFORE, issuedAt: ISSUED_AT, expiresAt: EXPIRES_AT, jti: UUID)
let unlockOperation = LockOperations.UnlockOperation(baseOperation: baseOperation, directAccessEndpoints: null)
sdk.lockOperations().unlock(unlockOperation: shareLockOperation)
````
</details>

<details><summary>JS</summary>

````js
const lockOperations = doordeck.com.doordeck.multiplatform.sdk.api.model.LockOperations;
const baseOperation = new lockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID", NOT_BEFORE, ISSUED_AT, EXPIRES_AT, "UUID");
const unlockOperation = new lockOperations.UnlockOperation(baseOperation, null);
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().unlock(unlockOperation);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var baseOperationData = new BaseOperationData("USER_ID", USER_CERTIFICATE_CHAIN_LIST, "BASE64_PRIVATE_KEY", "LOCK_ID");
var data = new UnlockOperationData(baseOperationData).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.unlockJson(resource, data);
````
</details>

### Unlock with context
> [!IMPORTANT]  
> This functionality requires you to have previously [set the operation context](#set-operation-context).
<details><summary>JVM & Android</summary>

````kotlin
sdk.lockOperations().unlockWithContext("LOCK_ID")
````
>:information_source: In Java, you can use the `unlockWithContextAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
sdk.lockOperations().unlockWithContext(lockId: "LOCK_ID")
````
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().unlockWithContext("LOCK_ID", null);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new UnlockWithContextData("LOCK_ID").toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.unlockWithContextJson(resource, data);
````
</details>

### Share lock
<details><summary>JVM & Android</summary>

````kotlin
val baseOperation = LockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID")
val shareLock = LockOperations.ShareLock("TARGET_USER_ID", TARGET_USER_ROLE, TARGET_PUBLIC_KEY)
val shareLockOperation = LockOperations.ShareLockOperation(baseOperation, shareLock)
sdk.lockOperations().shareLock(shareLockOperation)
````
>:information_source: In Java, you can use the `shareLockAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
let baseOperation = LockOperations.BaseOperation(userId: "USER_ID", userCertificateChain: USER_CERTIFICATE_CHAIN_LIST, userPrivateKey: PRIVATE_KEY, lockId: "LOCK_ID", notBefore: NOT_BEFORE, issuedAt: ISSUED_AT, expiresAt: EXPIRES_AT, jti: UUID)
let shareLock = LockOperations.ShareLock(targetUserId: "TARGET_USER_ID", targetUserRole: TARGET_USER_ROLE, targetUserPublicKey: TARGET_PUBLIC_KEY, start: null, end: null)
let shareLockOperation = LockOperations.ShareLockOperation(baseOperation: baseOperation, shareLock: shareLock)
sdk.lockOperations().shareLock(shareLockOperation: shareLockOperation)
````
</details>

<details><summary>JS</summary>

````js
const lockOperations = doordeck.com.doordeck.multiplatform.sdk.api.model.LockOperations;
const baseOperation = new lockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID", NOT_BEFORE, ISSUED_AT, EXPIRES_AT, "UUID");
const userRole = doordeck.com.doordeck.multiplatform.sdk.api.model.UserRole;
const shareLock = new lockOperations.ShareLock("TARGET_USER_ID", userRole.USER, TARGET_PUBLIC_KEY, null, null);
const shareLockOperation = new lockOperations.shareLockOperation(baseOperation, shareLock);
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().shareLock(shareLockOperation);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var baseOperationData = new BaseOperationData("USER_ID", USER_CERTIFICATE_CHAIN_LIST, "BASE64_PRIVATE_KEY", "LOCK_ID");
var shareLockData = new ShareLockData("TARGET_USER_ID", TARGET_USER_ROLE, "BASE64_TARGET_PUBLIC_KEY");
var data = new ShareLockOperationData(baseOperationData, shareLockData).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.shareLockJson(resource, data);
````
</details>

### Share lock with context
> [!IMPORTANT]  
> This functionality requires you to have previously [set the operation context](#set-operation-context).
<details><summary>JVM & Android</summary>

````kotlin
val shareLock = LockOperations.ShareLock("TARGET_USER_ID", TARGET_USER_ROLE, TARGET_PUBLIC_KEY)
sdk.lockOperations().shareLockWithContext("LOCK_ID", shareLock)
````
>:information_source: In Java, you can use the `shareLockWithContextAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
let shareLock = LockOperations.ShareLock(targetUserId: "TARGET_USER_ID", targetUserRole: TARGET_USER_ROLE, targetUserPublicKey: TARGET_PUBLIC_KEY, start: null, end: null)
sdk.lockOperations().shareLockWithContext(lockId: "LOCK_ID", shareLock: shareLock)
````
</details>

<details><summary>JS</summary>

````js
const lockOperations = doordeck.com.doordeck.multiplatform.sdk.api.model.LockOperations;
const userRole = doordeck.com.doordeck.multiplatform.sdk.api.model.UserRole;
const shareLock = new lockOperations.ShareLock("TARGET_USER_ID", userRole.USER, TARGET_PUBLIC_KEY, null, null);
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().shareLockWithContext("LOCK_ID", shareLock);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var shareLockData = new ShareLockData("TARGET_USER_ID", TARGET_USER_ROLE, "BASE64_TARGET_PUBLIC_KEY");
var data = new ShareLockWithContextData("LOCK_ID", shareLockData).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.shareLockWithContextJson(resource, data);
````
</details>

### Revoke access to lock
<details><summary>JVM & Android</summary>

````kotlin
val baseOperation = LockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID")
val revokeAccessToLockOperation = LockOperations.RevokeAccessToLockOperation(baseOperation, listOf("USER_ID"))
sdk.lockOperations().revokeAccessToLock(revokeAccessToLockOperation)
````
>:information_source: In Java, you can use the `revokeAccessToLockAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
let baseOperation = LockOperations.BaseOperation(userId: "USER_ID", userCertificateChain: USER_CERTIFICATE_CHAIN_LIST, userPrivateKey: PRIVATE_KEY, lockId: "LOCK_ID", notBefore: NOT_BEFORE, issuedAt: ISSUED_AT, expiresAt: EXPIRES_AT, jti: UUID)
let revokeAccessToLockOperation = LockOperations.RevokeAccessToLockOperation(baseOperation: baseOperation, users: ["USER_ID"])
sdk.lockOperations().revokeAccessToLock(revokeAccessToLockOperation: revokeAccessToLockOperation)
````
</details>

<details><summary>JS</summary>

````js
const ktList = doordeck.kotlin.collections.KtList;
const userList = ktList.fromJsArray(["USER_ID"]);
const lockOperations = doordeck.com.doordeck.multiplatform.sdk.api.model.LockOperations;
const baseOperation = new lockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID", NOT_BEFORE, ISSUED_AT, EXPIRES_AT, "UUID");
const revokeAccessToLockOperation = new lockOperations.RevokeAccessToLockOperation(baseOperation, userList);
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().revokeAccessToLock(revokeAccessToLockOperation);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
List<string> userList = ["USER_ID"];
var baseOperationData = new BaseOperationData("USER_ID", USER_CERTIFICATE_CHAIN_LIST, "BASE64_PRIVATE_KEY", "LOCK_ID");
var data = new RevokeAccessToLockOperationData(baseOperationData, userList).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.revokeAccessToLockJson(resource, data);
````
</details>

### Revoke access to lock with context
> [!IMPORTANT]  
> This functionality requires you to have previously [set the operation context](#set-operation-context).
<details><summary>JVM & Android</summary>

````kotlin
sdk.lockOperations().revokeAccessToLockWithContext("LOCK_ID", listOf("USER_ID"))
````
>:information_source: In Java, you can use the `revokeAccessToLockWithContextAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
sdk.lockOperations().revokeAccessToLockWithContext(lockId: "LOCK_ID", users: ["USER_ID"])
````
</details>

<details><summary>JS</summary>

````js
const ktList = doordeck.kotlin.collections.KtList;
const userList = ktList.fromJsArray(["USER_ID"]);
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().revokeAccessToLockWithContext("LOCK_ID", userList);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
List<string> userList = ["USER_ID"];
var data = new RevokeAccessToLockWithContextData("LOCK_ID", userList).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.revokeAccessToLockWithContextJson(resource, data);
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

<details><summary>Swift</summary>

````swift
let baseOperation = LockOperations.BaseOperation(userId: "USER_ID", userCertificateChain: USER_CERTIFICATE_CHAIN_LIST, userPrivateKey: PRIVATE_KEY, lockId: "LOCK_ID", notBefore: NOT_BEFORE, issuedAt: ISSUED_AT, expiresAt: EXPIRES_AT, jti: UUID)
let updateSecureSettingUnlockDuration = LockOperations.UpdateSecureSettingUnlockDuration(baseOperation: baseOperation, unlockDuration: UNLOCK_DURATION)
sdk.lockOperations().updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration: updateSecureSettingUnlockDuration)
````
</details>

<details><summary>JS</summary>

````js
const lockOperations = doordeck.com.doordeck.multiplatform.sdk.api.model.LockOperations;
const baseOperation = new lockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID", NOT_BEFORE, ISSUED_AT, EXPIRES_AT, "UUID");
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

### Update secure setting unlock duration with context
> [!IMPORTANT]  
> This functionality requires you to have previously [set the operation context](#set-operation-context).
<details><summary>JVM & Android</summary>

````kotlin
sdk.lockOperations().updateSecureSettingUnlockDurationWithContext("LOCK_ID", UNLOCK_DURATION)
````
>:information_source: In Java, you can use the `updateSecureSettingUnlockDurationWithContextAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
sdk.lockOperations().updateSecureSettingUnlockDurationWithContext(lockId: "LOCK_ID", unlockDuration: UNLOCK_DURATION)
````
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().updateSecureSettingUnlockDurationWithContext("LOCK_ID", UNLOCK_DURATION);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new UpdateSecureSettingUnlockDurationWithContextData("LOCK_ID", UNLOCK_DURATION).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.updateSecureSettingUnlockDurationWithContextJson(resource, data);
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

<details><summary>Swift</summary>

````swift
let baseOperation = LockOperations.BaseOperation(userId: "USER_ID", userCertificateChain: USER_CERTIFICATE_CHAIN_LIST, userPrivateKey: PRIVATE_KEY, lockId: "LOCK_ID", notBefore: NOT_BEFORE, issuedAt: ISSUED_AT, expiresAt: EXPIRES_AT, jti: UUID)
let unlockBetween = LockOperations.UnlockBetween(start: "START_HH_MM", end: "END_HH_MM", timezone: "TIMEZONE", days: ["MONDAY"], exceptions: ["FRIDAY"])
let updateSecureSettingUnlockBetween = LockOperations.UpdateSecureSettingUnlockBetween(baseOperation: baseOperation, unlockBetween: unlockBetween)
sdk.lockOperations().updateSecureSettingUnlockBetween(updateSecureSettingUnlockBetween: updateSecureSettingUnlockBetween)
````
</details>

<details><summary>JS</summary>

````js
const lockOperations = doordeck.com.doordeck.multiplatform.sdk.api.model.LockOperations;
const baseOperation = new lockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID", NOT_BEFORE, ISSUED_AT, EXPIRES_AT, "UUID");
const unlockBetween = new lockOperations.UnlockBetween("START_HH_MM", "END_HH_MM", "TIMEZONE", DAYS_LIST, EXCEPTIONS_LIST);
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

### Update secure setting unlock between with context
> [!IMPORTANT]  
> This functionality requires you to have previously [set the operation context](#set-operation-context).
<details><summary>JVM & Android</summary>

````kotlin
val unlockBetween = LockOperations.UnlockBetween("START_HH_MM", "END_HH_MM", "TIMEZONE", DAYS_LIST)
sdk.lockOperations().updateSecureSettingUnlockBetweenWithContext("LOCK_ID", unlockBetween)
````
>:information_source: In Java, you can use the `updateSecureSettingUnlockBetweenWithContextAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
let unlockBetween = LockOperations.UnlockBetween(start: "START_HH_MM", end: "END_HH_MM", timezone: "TIMEZONE", days: ["MONDAY"], exceptions: ["FRIDAY"])
sdk.lockOperations().updateSecureSettingUnlockBetweenWithContext(lockId: "LOCK_ID", unlockBetween: unlockBetween)
````
</details>

<details><summary>JS</summary>

````js
const lockOperations = doordeck.com.doordeck.multiplatform.sdk.api.model.LockOperations;
const unlockBetween = new lockOperations.UnlockBetween("START_HH_MM", "END_HH_MM", "TIMEZONE", DAYS_LIST, EXCEPTIONS_LIST);
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().updateSecureSettingUnlockBetweenWithContext("LOCK_ID", unlockBetween);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var unlockBetweenData = new UnlockBetweenData("START_HH_MM", "END_HH_MM", "TIMEZONE", DAYS_LIST);
var data = new UpdateSecureSettingUnlockBetweenWithContextData("LOCK_ID", unlockBetweenData).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.updateSecureSettingUnlockBetweenWithContextJson(resource, data);
````
</details>

### Get pinned locks
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.lockOperations().getPinnedLocks()
````
>:information_source: In Java, you can use the `getPinnedLocksAsync` function, which returns a `CompletableFuture<List<LockResponse>>` instead
</details>

<details><summary>Swift</summary>

````swift
let response = sdk.lockOperations().getPinnedLocks()
````
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

<details><summary>Swift</summary>

````swift
let response = sdk.lockOperations().getShareableLocks()
````
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

<details><summary>Swift</summary>

````swift
let application = Platform.CreateApplication(name: "APPLICATION_NAME", companyName: "COMPANY_NAME", mailingAddress: "COMPANY@MAIL.COM", privacyPolicy: null, supportContact: null, appLink: null, emailPreferences: null, logoUrl: null)
sdk.platform().createApplication(application: application)
````
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

<details><summary>Swift</summary>

````swift
let response = sdk.platform().listApplications()
````
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

<details><summary>Swift</summary>

````swift
let response = sdk.platform().getApplication(applicationId: "APPLICATION_ID")
````
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

<details><summary>Swift</summary>

````swift
sdk.platform().updateApplicationName(applicationId: "APPLICATION_ID", name: "APPLICATION_NAME")
````
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

<details><summary>Swift</summary>

````swift
sdk.platform().updateApplicationCompanyName(applicationId: "APPLICATION_ID", companyName: "APPLICATION_COMPANY_NAME")
````
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

<details><summary>Swift</summary>

````swift
sdk.platform().updateApplicationMailingAddress(applicationId: "APPLICATION_ID", mailingAddress: "COMPANY@MAIL.COM")
````
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

<details><summary>Swift</summary>

````swift
sdk.platform().updateApplicationPrivacyPolicy(applicationId: "APPLICATION_ID", privacyPolicy: "PRIVACY_POLICY")
````
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

<details><summary>Swift</summary>

````swift
sdk.platform().updateApplicationSupportContact(applicationId: "APPLICATION_ID", supportContact: "SUPPORT_CONTACT_URL")
````
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

<details><summary>Swift</summary>

````swift
sdk.platform().updateApplicationAppLink(applicationId: "APPLICATION_ID", appLink: "APP_LINK")
````
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

<details><summary>Swift</summary>

````swift
let emailPreferences = Platform.EmailPreferences(senderEmail: "SENDER_EMAIL", senderName: "SENDER_NAME", primaryColour: "PRIMARY_COLOR", secondaryColour: "SECONDARY_COLOR", onlySendEssentialEmails: false, callToAction: null)
sdk.platform().updateApplicationEmailPreferences(applicationId: "APPLICATION_ID", emailPreferences: emailPreferences)
````
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

<details><summary>Swift</summary>

````swift
sdk.platform().updateApplicationLogoUrl(applicationId: "APPLICATION_ID", logoUrl: "LOGO_URL")
````
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
> [!CAUTION]
> This operation is executed instantly and is irreversible
<details><summary>JVM & Android</summary>

````kotlin
sdk.platform().deleteApplication("APPLICATION_ID")
````
>:information_source: In Java, you can use the `deleteApplicationAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
sdk.platform().deleteApplication(applicationId: "APPLICATION_ID")
````
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

<details><summary>Swift</summary>

````swift
let response = sdk.platform().getLogoUploadUrl(applicationId: "APPLICATION_ID", contentType: "CONTENT_TYPE")
````
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

<details><summary>Swift</summary>

````swift
let key = Platform.Ed25519Key(kty: "OKP", user: "sig", kid: "90a983fd-9077-41f9-840c-7220581017f5", alg: "EdDSA", d: "zVfpB5Nfj4SzYayFpTu4Qm1JaUmk6-FBbFUX3k1qqwc", crv: "Ed25519", x: "0ufELXg9OUjkAZUs5aGdgVbz664erh8t9cTvFBHicrc")
sdk.platform().addAuthKey(applicationId: "APPLICATION_ID", key: key)
````
</details>

<details><summary>JS</summary>

````js
const platform = doordeck.com.doordeck.multiplatform.sdk.api.model.Platform;
const key = new platform.Ed25519Key("OKP", "sig", "90a983fd-9077-41f9-840c-7220581017f5", "EdDSA", "zVfpB5Nfj4SzYayFpTu4Qm1JaUmk6-FBbFUX3k1qqwc", "Ed25519", "0ufELXg9OUjkAZUs5aGdgVbz664erh8t9cTvFBHicrc");
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

<details><summary>Swift</summary>

````swift
sdk.platform().addAuthIssuer(applicationId: "APPLICATION_ID", url: "URL")
````
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

<details><summary>Swift</summary>

````swift
sdk.platform().deleteAuthIssuer(applicationId: "APPLICATION_ID", url: "URL")
````
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

<details><summary>Swift</summary>

````swift
sdk.platform().addCorsDomain(applicationId: "APPLICATION_ID", url: "URL")
````
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

<details><summary>Swift</summary>

````swift
sdk.platform().removeCorsDomain(applicationId: "APPLICATION_ID", url: "URL")
````
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

<details><summary>Swift</summary>

````swift
sdk.platform().addApplicationOwner(applicationId: "APPLICATION_ID", userId: "OWNER_ID")
````
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

<details><summary>Swift</summary>

````swift
sdk.platform().removeApplicationOwner(applicationId: "APPLICATION_ID", userId: "OWNER_ID")
````
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

<details><summary>Swift</summary>

````swift
let response = sdk.platform().getApplicationOwnersDetails(applicationId: "APPLICATION_ID")
````
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

<details><summary>Swift</summary>

````swift
let response = sdk.sites().listSites()
````
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

<details><summary>Swift</summary>

````swift
let response = sdk.sites().getLocksForSite(siteId: "SITE_ID")
````
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

<details><summary>Swift</summary>

````swift
let response = sdk.sites().getUsersForSite(siteId: "SITE_ID")
````
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

<details><summary>Swift</summary>

````swift
let response = sdk.tiles().getLocksBelongingToTile(tileId: "TILE_ID")
````
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
sdk.tiles().associateMultipleLocks("TILE_ID", "SITE_ID", listOf("LOCK_ID"))
````
>:information_source: In Java, you can use the `associateMultipleLocksAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
sdk.tiles().associateMultipleLocks(tileId: "TILE_ID", siteId: "SITE_ID", lockIds: ["LOCK_ID"]))
````
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