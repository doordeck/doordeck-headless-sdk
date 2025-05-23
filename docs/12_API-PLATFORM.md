# Platform resource

> [!IMPORTANT]
> All of the platform functions are only available to users with Doordeck issued auth tokens

## Create application

### JVM
<details>
<summary>Show Details</summary>

```kotlin
val application = Platform.CreateApplication("APPLICATION_NAME", "COMPANY_NAME", "COMPANY@MAIL.COM")
sdk.platform().createApplication(application)
```
💡 **Note:** In Java, use the `createApplicationAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let application = Platform.CreateApplication(name: "APPLICATION_NAME", companyName: "COMPANY_NAME", mailingAddress: "COMPANY@MAIL.COM", privacyPolicy: null, supportContact: null, appLink: null, emailPreferences: null, logoUrl: null)
sdk.platform().createApplication(application: application)
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const platform = doordeck.com.doordeck.multiplatform.sdk.model.data.Platform;
const application = new platform.CreateApplication("APPLICATION_NAME", "COMPANY_NAME", "COMPANY@MAIL.COM", null, null, null, null, null);
await doordeck.com.doordeck.multiplatform.sdk.api.platform().createApplication(application);
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var data = new CreateApplication("APPLICATION_NAME", "COMPANY_NAME", "COMPANY@MAIL.COM");
await sdk.GetPlatform().CreateApplication(data);
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
data = doordeck_headless_sdk.CreateApplication("APPLICATION_NAME", "COMPANY_NAME", "COMPANY@MAIL.COM")
await sdk.platform.create_application(data)
```
</details>

## List applications

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.platform().listApplications()
```
💡 **Note:** In Java, use the `listApplicationsAsync` function, which returns a `CompletableFuture<List<ApplicationResponse>>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.platform().listApplications()
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.platform().listApplications();
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var response = await sdk.GetPlatform().ListApplications();
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
response = await sdk.platform.list_applications()
```
</details>

## Get application

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.platform().getApplication("APPLICATION_ID")
```
💡 **Note:** In Java, use the `getApplicationAsync` function, which returns a `CompletableFuture<ApplicationResponse>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.platform().getApplication(applicationId: "APPLICATION_ID")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.platform().getApplication("APPLICATION_ID");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var response = await sdk.GetPlatform().GetApplication("APPLICATION_ID");
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
response = await sdk.platform.get_application("APPLICATION_ID")
```
</details>

## Update application name

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.platform().updateApplicationName("APPLICATION_ID", "APPLICATION_NAME")
```
💡 **Note:** In Java, use the `updateApplicationNameAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.platform().updateApplicationName(applicationId: "APPLICATION_ID", name: "APPLICATION_NAME")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
await doordeck.com.doordeck.multiplatform.sdk.api.platform().updateApplicationName("APPLICATION_ID", "APPLICATION_NAME");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
await sdk.GetPlatform().UpdateApplicationName("APPLICATION_ID", "APPLICATION_NAME");
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
await sdk.platform.update_application_name("APPLICATION_ID", "APPLICATION_NAME")
```
</details>

## Update application company name

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.platform().updateApplicationCompanyName("APPLICATION_ID", "APPLICATION_COMPANY_NAME")
```
💡 **Note:** In Java, use the `updateApplicationCompanyNameAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.platform().updateApplicationCompanyName(applicationId: "APPLICATION_ID", companyName: "APPLICATION_COMPANY_NAME")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
await doordeck.com.doordeck.multiplatform.sdk.api.platform().updateApplicationCompanyName("APPLICATION_ID", "APPLICATION_COMPANY_NAME");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
await sdk.GetPlatform().UpdateApplicationCompanyName("APPLICATION_ID", "APPLICATION_COMPANY_NAME");
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
await sdk.platform.update_application_company_name("APPLICATION_ID", "APPLICATION_COMPANY_NAME")
```
</details>

## Update application mailing address

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.platform().updateApplicationMailingAddress("APPLICATION_ID", "COMPANY@MAIL.COM")
```
💡 **Note:** In Java, use the `updateApplicationMailingAddressAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.platform().updateApplicationMailingAddress(applicationId: "APPLICATION_ID", mailingAddress: "COMPANY@MAIL.COM")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
await doordeck.com.doordeck.multiplatform.sdk.api.platform().updateApplicationMailingAddress("APPLICATION_ID", "COMPANY@MAIL.COM");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
await sdk.GetPlatform().UpdateApplicationMailingAddress("APPLICATION_ID", "COMPANY@MAIL.COM");
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
await sdk.platform.update_application_mailing_address("APPLICATION_ID", "COMPANY@MAIL.COM")
```
</details>

## Update application privacy policy

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.platform().updateApplicationPrivacyPolicy("APPLICATION_ID", "PRIVACY_POLICY")
```
💡 **Note:** In Java, use the `updateApplicationPrivacyPolicyAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.platform().updateApplicationPrivacyPolicy(applicationId: "APPLICATION_ID", privacyPolicy: "PRIVACY_POLICY")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
await doordeck.com.doordeck.multiplatform.sdk.api.platform().updateApplicationPrivacyPolicy("APPLICATION_ID", "PRIVACY_POLICY");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
await sdk.GetPlatform().UpdateApplicationPrivacyPolicy("APPLICATION_ID", "PRIVACY_POLICY");
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
await sdk.platform.update_application_privacy_policy("APPLICATION_ID", "PRIVACY_POLICY")
```
</details>

## Update application support contact

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.platform().updateApplicationSupportContact("APPLICATION_ID", "SUPPORT_CONTACT_URL")
```
💡 **Note:** In Java, use the `updateApplicationSupportContactAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.platform().updateApplicationSupportContact(applicationId: "APPLICATION_ID", supportContact: "SUPPORT_CONTACT_URL")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
await doordeck.com.doordeck.multiplatform.sdk.api.platform().updateApplicationSupportContact("APPLICATION_ID", "SUPPORT_CONTACT_URL");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
await sdk.GetPlatform().UpdateApplicationSupportContact("APPLICATION_ID", "SUPPORT_CONTACT_URL");
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
await sdk.platform.update_application_support_contact("APPLICATION_ID", "SUPPORT_CONTACT_URL")
```
</details>

## Update application app link

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.platform().updateApplicationAppLink("APPLICATION_ID", "APP_LINK")
```
💡 **Note:** In Java, use the `updateApplicationAppLinkAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.platform().updateApplicationAppLink(applicationId: "APPLICATION_ID", appLink: "APP_LINK")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
await doordeck.com.doordeck.multiplatform.sdk.api.platform().updateApplicationAppLink("APPLICATION_ID", "APP_LINK");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
await sdk.GetPlatform().UpdateApplicationAppLink("APPLICATION_ID", "APP_LINK");
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
await sdk.platform.update_application_app_link("APPLICATION_ID", "APP_LINK")
```
</details>

## Update application email preferences

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val emailPreferences = Platform.EmailPreferences("SENDER_EMAIL", "SENDER_NAME", "PRIMARY_COLOR", "SECONDARY_COLOR", false, null)
sdk.platform().updateApplicationEmailPreferences("APPLICATION_ID", emailPreferences)
```
💡 **Note:** In Java, use the `updateApplicationEmailPreferencesAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let emailPreferences = Platform.EmailPreferences(senderEmail: "SENDER_EMAIL", senderName: "SENDER_NAME", primaryColour: "PRIMARY_COLOR", secondaryColour: "SECONDARY_COLOR", onlySendEssentialEmails: false, callToAction: null)
sdk.platform().updateApplicationEmailPreferences(applicationId: "APPLICATION_ID", emailPreferences: emailPreferences)
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const platform = doordeck.com.doordeck.multiplatform.sdk.model.data.Platform;
const emailPreferences = new platform.EmailPreferences("SENDER_EMAIL", "SENDER_NAME", "PRIMARY_COLOR", "SECONDARY_COLOR", false, null);
await doordeck.com.doordeck.multiplatform.sdk.api.platform().updateApplicationEmailPreferences("APPLICATION_ID", emailPreferences);
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var emailPreferences = new EmailPreferences("SENDER_EMAIL", "SENDER_NAME", "PRIMARY_COLOR", "SECONDARY_COLOR", false);
await sdk.GetPlatform().UpdateApplicationEmailPreferences("APPLICATION_ID", emailPreferences);
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
emailPreferences = doordeck_headless_sdk.EmailPreferences("SENDER_EMAIL", "SENDER_NAME", "PRIMARY_COLOR", "SECONDARY_COLOR", False)
await sdk.platform.update_application_email_preferences("APPLICATION_ID", emailPreferences)
```
</details>

## Update application log url

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.platform().updateApplicationLogoUrl("APPLICATION_ID", "LOGO_URL")
```
💡 **Note:** In Java, use the `updateApplicationLogoUrlAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.platform().updateApplicationLogoUrl(applicationId: "APPLICATION_ID", logoUrl: "LOGO_URL")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
await doordeck.com.doordeck.multiplatform.sdk.api.platform().updateApplicationLogoUrl("APPLICATION_ID", "LOGO_URL");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
await sdk.GetPlatform().UpdateApplicationLogoUrl("APPLICATION_ID", "LOGO_URL");
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
await sdk.platform.update_application_logo_url("APPLICATION_ID", "LOGO_URL")
```
</details>

## Delete application

> [!CAUTION]
> This operation is executed instantly and is irreversible

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.platform().deleteApplication("APPLICATION_ID")
```
💡 **Note:** In Java, use the `deleteApplicationAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.platform().deleteApplication(applicationId: "APPLICATION_ID")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
await doordeck.com.doordeck.multiplatform.sdk.api.platform().deleteApplication("APPLICATION_ID");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
await sdk.GetPlatform().DeleteApplication("APPLICATION_ID");
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
await sdk.platform.delete_application("APPLICATION_ID")
```
</details>

## Get logo upload url

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.platform().getLogoUploadUrl("APPLICATION_ID", "CONTENT_TYPE")
```
💡 **Note:** In Java, use the `getLogoUploadUrlAsync` function, which returns a `CompletableFuture<GetLogoUploadUrlResponse>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.platform().getLogoUploadUrl(applicationId: "APPLICATION_ID", contentType: "CONTENT_TYPE")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.platform().getLogoUploadUrl("APPLICATION_ID", "CONTENT_TYPE");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var response = await sdk.GetPlatform().GetLogoUploadUrl("APPLICATION_ID", "CONTENT_TYPE");
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
response = await sdk.platform.get_logo_upload_url("APPLICATION_ID", "CONTENT_TYPE")
```
</details>

## Add auth key

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val key = Platform.Ed25519Key("OKP", "sig", "90a983fd-9077-41f9-840c-7220581017f5", "EdDSA", "zVfpB5Nfj4SzYayFpTu4Qm1JaUmk6-FBbFUX3k1qqwc", "Ed25519", "0ufELXg9OUjkAZUs5aGdgVbz664erh8t9cTvFBHicrc")
sdk.platform().addAuthKey("APPLICATION_ID", key)
```
💡 **Note:** In Java, use the `addAuthKeyAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let key = Platform.Ed25519Key(kty: "OKP", user: "sig", kid: "90a983fd-9077-41f9-840c-7220581017f5", alg: "EdDSA", d: "zVfpB5Nfj4SzYayFpTu4Qm1JaUmk6-FBbFUX3k1qqwc", crv: "Ed25519", x: "0ufELXg9OUjkAZUs5aGdgVbz664erh8t9cTvFBHicrc")
sdk.platform().addAuthKey(applicationId: "APPLICATION_ID", key: key)
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const platform = doordeck.com.doordeck.multiplatform.sdk.model.data.Platform;
const key = new platform.Ed25519Key("OKP", "sig", "90a983fd-9077-41f9-840c-7220581017f5", "EdDSA", "zVfpB5Nfj4SzYayFpTu4Qm1JaUmk6-FBbFUX3k1qqwc", "Ed25519", "0ufELXg9OUjkAZUs5aGdgVbz664erh8t9cTvFBHicrc");
await doordeck.com.doordeck.multiplatform.sdk.api.platform().addAuthKey("APPLICATION_ID", key);
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var key = new Ed25519Key("sig", "90a983fd-9077-41f9-840c-7220581017f5", "EdDSA", "zVfpB5Nfj4SzYayFpTu4Qm1JaUmk6-FBbFUX3k1qqwc", "Ed25519", "0ufELXg9OUjkAZUs5aGdgVbz664erh8t9cTvFBHicrc");
await sdk.GetPlatform().AddAuthKey("APPLICATION_ID", key);
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
key = doordeck_headless_sdk.Ed25519Key("sig", "90a983fd-9077-41f9-840c-7220581017f5", "EdDSA", "zVfpB5Nfj4SzYayFpTu4Qm1JaUmk6-FBbFUX3k1qqwc", "Ed25519", "0ufELXg9OUjkAZUs5aGdgVbz664erh8t9cTvFBHicrc")
await sdk.platform.add_auth_key("APPLICATION_ID", key)
```
</details>

## Add auth issuer

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.platform().addAuthIssuer("APPLICATION_ID", "URL")
```
💡 **Note:** In Java, use the `addAuthIssuerAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.platform().addAuthIssuer(applicationId: "APPLICATION_ID", url: "URL")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
await doordeck.com.doordeck.multiplatform.sdk.api.platform().addAuthIssuer("APPLICATION_ID", "URL");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
await sdk.GetPlatform().AddAuthIssuer("APPLICATION_ID", "URL");
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
await sdk.platform.add_auth_issuer("APPLICATION_ID", "URL")
```
</details>

## Delete auth issuer

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.platform().deleteAuthIssuer("APPLICATION_ID", "URL")
```
💡 **Note:** In Java, use the `deleteAuthIssuerAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.platform().deleteAuthIssuer(applicationId: "APPLICATION_ID", url: "URL")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
await doordeck.com.doordeck.multiplatform.sdk.api.platform().deleteAuthIssuer("APPLICATION_ID", "URL");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
await sdk.GetPlatform().DeleteAuthIssuer("APPLICATION_ID", "URL");
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
await sdk.platform.delete_auth_issuer("APPLICATION_ID", "URL")
```
</details>

## Add CORS domain

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.platform().addCorsDomain("APPLICATION_ID", "URL")
```
💡 **Note:** In Java, use the `addCorsDomainAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.platform().addCorsDomain(applicationId: "APPLICATION_ID", url: "URL")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
await doordeck.com.doordeck.multiplatform.sdk.api.platform().addCorsDomain("APPLICATION_ID", "URL");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
await sdk.GetPlatform().AddCorsDomain("APPLICATION_ID", "URL");
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
await sdk.platform.add_cors_domain("APPLICATION_ID", "URL")
```
</details>

## Remove CORS domain

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.platform().removeCorsDomain("APPLICATION_ID", "URL")
```
💡 **Note:** In Java, use the `removeCorsDomainAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.platform().removeCorsDomain(applicationId: "APPLICATION_ID", url: "URL")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
await doordeck.com.doordeck.multiplatform.sdk.api.platform().removeCorsDomain("APPLICATION_ID", "URL");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
await sdk.GetPlatform().RemoveCorsDomain("APPLICATION_ID", "URL");
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
await sdk.platform.remove_cors_domain("APPLICATION_ID", "URL")
```
</details>

## Add application owner

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.platform().addApplicationOwner("APPLICATION_ID", "OWNER_ID")
```
💡 **Note:** In Java, use the `addApplicationOwnerAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.platform().addApplicationOwner(applicationId: "APPLICATION_ID", userId: "OWNER_ID")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
await doordeck.com.doordeck.multiplatform.sdk.api.platform().addApplicationOwner("APPLICATION_ID", "OWNER_ID");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
await sdk.GetPlatform().AddApplicationOwner("APPLICATION_ID", "OWNER_ID");
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
await sdk.platform.add_application_owner("APPLICATION_ID", "OWNER_ID")
```
</details>

## Remove application owner

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.platform().removeApplicationOwner("APPLICATION_ID", "OWNER_ID")
```
💡 **Note:** In Java, use the `removeApplicationOwnerAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.platform().removeApplicationOwner(applicationId: "APPLICATION_ID", userId: "OWNER_ID")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
await doordeck.com.doordeck.multiplatform.sdk.api.platform().removeApplicationOwner("APPLICATION_ID", "OWNER_ID");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
await sdk.GetPlatform().RemoveApplicationOwner("APPLICATION_ID", "OWNER_ID");
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
await sdk.platform.remove_application_owner("APPLICATION_ID", "OWNER_ID")
```
</details>

## Get application owners details

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.platform().getApplicationOwnersDetails("APPLICATION_ID")
```
💡 **Note:** In Java, use the `getApplicationOwnersDetailsAsync` function, which returns a `CompletableFuture<List<ApplicationOwnerDetailsResponse>>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.platform().getApplicationOwnersDetails(applicationId: "APPLICATION_ID")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.platform().getApplicationOwnersDetails("APPLICATION_ID");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var response = await sdk.GetPlatform().GetApplicationOwnersDetails("APPLICATION_ID");
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
response = await sdk.platform.get_application_owners_details("APPLICATION_ID")
```
</details>

:arrow_left: [Back to index](01_INDEX.md)