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

:arrow_left: [Back to index](01_INDEX.md)