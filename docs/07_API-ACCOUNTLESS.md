# Accountless resource

## Login

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.accountless().login("EMAIL", "PASSWORD")
```

💡 **Note:**  In Java, use the `loginAsync` function, which returns a `CompletableFuture<TokenResponse>` instead.
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.accountless().login(email: "EMAIL", password: "PASSWORD")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.accountless().login("EMAIL", "PASSWORD");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.accountless(sdk);
var data = new LoginData("EMAIL", "PASSWORD").toData();
var response = Utils.fromData<TokenResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountlessResource.loginJson(resource, data));
```
</details>

## Register a new user

After registration, you will need to [verify the email](#verify-email).

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.accountless().registration("EMAIL", "PASSWORD", "DISPLAY_NAME", false)
```

💡 **Note:**  In Java, use the `registrationAsync` function, which returns a `CompletableFuture<TokenResponse>` instead.
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.accountless().registration(email: "EMAIL", password: "PASSWORD", displayName: "DISPLAY_NAME", force: false)
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.accountless().registration("EMAIL", "PASSWORD", "DISPLAY_NAME", false);
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.accountless(sdk);
var data = new RegistrationData("EMAIL", "PASSWORD", "DISPLAY_NAME", false).toData();
var response = Utils.fromData<TokenResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountlessResource.registrationJson(resource, data));
```
</details>

## Verify email

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.accountless().verifyEmail("CODE")
```

💡 **Note:**  In Java, use the `verifyEmailAsync` function, which returns a `CompletableFuture<Void>` instead.
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.accountless().verifyEmail(code: "CODE")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
await doordeck.com.doordeck.multiplatform.sdk.api.accountless().verifyEmail("CODE");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.accountless(sdk);
var data = new VerifyEmailData("CODE").toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.AccountlessResource.verifyEmailJson(resource, data);
```
</details>

:arrow_left: [Back to index](01_INDEX.md)
