# Accountless resource

## Login

> [!NOTE]  
> When used successfully, the user email, auth token and refresh token from the response are added to the [context manager](06_CONTEXT-MANAGER.md).

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
var data = new LoginData("EMAIL", "PASSWORD");
sdk.GetAccountless().Login(data);
```
</details>

## Register a new user

After registration, you will need to [verify the email](#verify-email)

> [!NOTE]  
> When used successfully, the user email, auth token and refresh token from the response are added to the [context manager](06_CONTEXT-MANAGER.md).

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.accountless().registration("EMAIL", "PASSWORD", "DISPLAY_NAME", false, PUBLIC_KEY)
```

💡 **Note:**  In Java, use the `registrationAsync` function, which returns a `CompletableFuture<TokenResponse>` instead.
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.accountless().registration(email: "EMAIL", password: "PASSWORD", displayName: "DISPLAY_NAME", force: false, publicKey: PUBLIC_KEY)
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.accountless().registration("EMAIL", "PASSWORD", "DISPLAY_NAME", false, PUBLIC_KEY);
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var data = new RegistrationData("EMAIL", "PASSWORD", "DISPLAY_NAME", false, "BASE64_PUBLIC_KEY");
sdk.GetAccountless().Registration(data);
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
var data = new VerifyEmailData("CODE");
sdk.GetAccountless().VerifyEmail(data);
```
</details>

## Password reset

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.accountless().passwordReset("EMAIL")
```

💡 **Note:**  In Java, use the `passwordResetAsync` function, which returns a `CompletableFuture<Void>` instead.
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.accountless().passwordReset(email: "EMAIL")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
await doordeck.com.doordeck.multiplatform.sdk.api.accountless().passwordReset("EMAIL");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var data = new PasswordResetData("EMAIL");
sdk.GetAccountless().PasswordReset(data);
```
</details>

## Password reset verify

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.accountless().passwordResetVerify("USERID", "TOKEN", "EMAIL")
```

💡 **Note:**  In Java, use the `passwordResetVerify` function, which returns a `CompletableFuture<Void>` instead.
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.accountless().passwordResetVerify(userId: "USERID", token: "TOKEN", email: "EMAIL")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
await doordeck.com.doordeck.multiplatform.sdk.api.accountless().passwordResetVerify("USERID", "TOKEN", "EMAIL");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var data = new PasswordResetVerifyData("USERID", "TOKEN", "EMAIL");
sdk.GetAccountless().PasswordResetVerify(data);
```
</details>

:arrow_left: [Back to index](01_INDEX.md)
