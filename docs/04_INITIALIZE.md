# Initialize the SDK
The SDK should be initialized as the first step. The simplest way to do this is by providing the `ApiEnvironment` and the auth token

It can also be initialized without an auth token, but you will need to manually [set an auth token through the context manager](06_CONTEXT-MANAGER.md#set-auth-token) to use most of the SDK functionalities

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
import doordeck from '@doordeck/doordeck-headless-sdk';
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

:arrow_left: [Back to index](01_INDEX.md)