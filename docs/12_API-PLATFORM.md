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
var data = new CreateApplicationData("APPLICATION_NAME", "COMPANY_NAME", "COMPANY@MAIL.COM");
sdk.GetPlatform().CreateApplication(data);
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
data = doordeck_headless_sdk.CreateApplicationData("APPLICATION_NAME", "COMPANY_NAME", "COMPANY@MAIL.COM")
sdk.platform.create_application(data)
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
var response = sdk.GetPlatform().ListApplications();
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
response = sdk.platform.list_applications()
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
var data = new GetApplicationData("APPLICATION_ID");
var response = sdk.GetPlatform().GetApplication(data);
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
data = doordeck_headless_sdk.GetApplicationData("APPLICATION_ID")
response = sdk.platform.get_application(data)
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
var data = new UpdateApplicationNameData("APPLICATION_ID", "APPLICATION_NAME");
sdk.GetPlatform().UpdateApplicationName(data);
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
data = doordeck_headless_sdk.UpdateApplicationNameData("APPLICATION_ID", "APPLICATION_NAME")
sdk.platform.update_application_name(data)
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
var data = new UpdateApplicationCompanyNameData("APPLICATION_ID", "APPLICATION_COMPANY_NAME");
sdk.GetPlatform().UpdateApplicationCompanyName(data);
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
data = doordeck_headless_sdk.UpdateApplicationCompanyNameData("APPLICATION_ID", "APPLICATION_COMPANY_NAME")
sdk.platform.update_application_company_name(data)
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
var data = new UpdateApplicationMailingAddressData("APPLICATION_ID", "COMPANY@MAIL.COM");
sdk.GetPlatform().UpdateApplicationMailingAddress(data);
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
data = doordeck_headless_sdk.UpdateApplicationMailingAddressData("APPLICATION_ID", "COMPANY@MAIL.COM")
sdk.platform.update_application_mailing_address(data)
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
var data = new UpdateApplicationPrivacyPolicyData("APPLICATION_ID", "PRIVACY_POLICY");
sdk.GetPlatform().UpdateApplicationPrivacyPolicy(data);
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
data = doordeck_headless_sdk.UpdateApplicationPrivacyPolicyData("APPLICATION_ID", "PRIVACY_POLICY")
sdk.platform.update_application_privacy_policy(data)
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
var data = new UpdateApplicationSupportContactData("APPLICATION_ID", "SUPPORT_CONTACT_URL");
sdk.GetPlatform().UpdateApplicationSupportContact(data);
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
data = doordeck_headless_sdk.UpdateApplicationSupportContactData("APPLICATION_ID", "SUPPORT_CONTACT_URL")
sdk.platform.update_application_support_contact(data)
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
var data = new UpdateApplicationAppLinkData("APPLICATION_ID", "APP_LINK");
sdk.GetPlatform().UpdateApplicationAppLink(data);
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
data = doordeck_headless_sdk.UpdateApplicationAppLinkData("APPLICATION_ID", "APP_LINK")
sdk.platform.update_application_app_link(data)
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
var emailPreferencesData = new EmailPreferencesData("SENDER_EMAIL", "SENDER_NAME", "PRIMARY_COLOR", "SECONDARY_COLOR", false);
var data = new UpdateApplicationEmailPreferencesData("APPLICATION_ID", emailPreferencesData);
sdk.GetPlatform().UpdateApplicationEmailPreferences(data);
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
emailPreferencesData = doordeck_headless_sdk.EmailPreferencesData("SENDER_EMAIL", "SENDER_NAME", "PRIMARY_COLOR", "SECONDARY_COLOR", False)
data = doordeck_headless_sdk.UpdateApplicationEmailPreferencesData("APPLICATION_ID", emailPreferencesData)
sdk.platform.update_application_email_preferences(data)
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
var data = new UpdateApplicationLogoUrlData("APPLICATION_ID", "LOGO_URL");
sdk.GetPlatform().UpdateApplicationLogoUrl(data);
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
data = doordeck_headless_sdk.UpdateApplicationLogoUrlData("APPLICATION_ID", "LOGO_URL")
sdk.platform.update_application_logo_url(data)
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
var data = new DeleteApplicationData("APPLICATION_ID");
sdk.GetPlatform().DeleteApplication(data);
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
data = doordeck_headless_sdk.DeleteApplicationData("APPLICATION_ID")
sdk.platform.delete_application(data)
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
var data = new GetLogoUploadUrlData("APPLICATION_ID", "CONTENT_TYPE");
var response = sdk.GetPlatform().GetLogoUploadUrl(data);
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
data = doordeck_headless_sdk.GetLogoUploadUrlData("APPLICATION_ID", "CONTENT_TYPE")
response = sdk.platform.get_logo_upload_url(data)
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
var keyData = new Ed25519KeyData("sig", "90a983fd-9077-41f9-840c-7220581017f5", "EdDSA", "zVfpB5Nfj4SzYayFpTu4Qm1JaUmk6-FBbFUX3k1qqwc", "Ed25519", "0ufELXg9OUjkAZUs5aGdgVbz664erh8t9cTvFBHicrc");
var data = new AddAuthKeyData("APPLICATION_ID", keyData);
sdk.GetPlatform().AddAuthKey(data);
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
keyData = doordeck_headless_sdk.Ed25519KeyData("sig", "90a983fd-9077-41f9-840c-7220581017f5", "EdDSA", "zVfpB5Nfj4SzYayFpTu4Qm1JaUmk6-FBbFUX3k1qqwc", "Ed25519", "0ufELXg9OUjkAZUs5aGdgVbz664erh8t9cTvFBHicrc")
data = doordeck_headless_sdk.AddAuthKeyData("APPLICATION_ID", keyData)
sdk.platform.add_auth_key(data)
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
var data = new AddAuthIssuerData("APPLICATION_ID", "URL");
sdk.GetPlatform().AddAuthIssuer(data);
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
data = doordeck_headless_sdk.AddAuthIssuerData("APPLICATION_ID", "URL")
sdk.platform.add_auth_issuer(data)
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
var data = new DeleteAuthIssuerData("APPLICATION_ID", "URL");
sdk.GetPlatform().DeleteAuthIssuer(data);
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
data = doordeck_headless_sdk.DeleteAuthIssuerData("APPLICATION_ID", "URL")
sdk.platform.delete_auth_issuer(data)
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
var data = new AddCorsDomainData("APPLICATION_ID", "URL");
sdk.GetPlatform().AddCorsDomain(data);
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
data = doordeck_headless_sdk.AddCorsDomainData("APPLICATION_ID", "URL")
sdk.platform.add_cors_domain(data)
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
var data = new RemoveCorsDomainData("APPLICATION_ID", "URL");
sdk.GetPlatform().RemoveCorsDomain(data);
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
data = doordeck_headless_sdk.RemoveCorsDomainData("APPLICATION_ID", "URL")
sdk.platform.remove_cors_domain(data)
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
var data = new AddApplicationOwnerData("APPLICATION_ID", "OWNER_ID");
sdk.GetPlatform().AddApplicationOwner(data);
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
data = doordeck_headless_sdk.AddApplicationOwnerData("APPLICATION_ID", "OWNER_ID")
sdk.platform.add_application_owner(data)
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
var data = new RemoveApplicationOwnerData("APPLICATION_ID", "OWNER_ID");
sdk.GetPlatform().RemoveApplicationOwner(data);
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
data = doordeck_headless_sdk.RemoveApplicationOwnerData("APPLICATION_ID", "OWNER_ID")
sdk.platform.remove_application_owner(data)
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
var data = new GetApplicationOwnersDetailsData("APPLICATION_ID");
var response = sdk.GetPlatform().GetApplicationOwnersDetails(data);
```
</details>

### Python
<details>
<summary>Show Details</summary>

```python
data = doordeck_headless_sdk.GetApplicationOwnersDetailsData("APPLICATION_ID")
response = sdk.platform.get_application_owners_details(data)
```
</details>

:arrow_left: [Back to index](01_INDEX.md)