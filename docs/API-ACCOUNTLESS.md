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