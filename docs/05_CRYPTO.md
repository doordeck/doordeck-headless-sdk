# Crypto
### Generate a key pair
<details><summary>JVM & Android</summary>

````kotlin
val keyPair = sdk.crypto().generateKeyPair()
````
</details>

<details><summary>Swift</summary>

````swift
let keyPair = sdk.crypto().generateKeyPair()
````
</details>

<details><summary>JS</summary>

````js
const crypto = doordeck.com.doordeck.multiplatform.sdk.crypto.crypto()
const keyPair = crypto.generateKeyPair();
````
</details>

<details><summary>C#</summary>

````csharp
var crypto = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.crypto(sdk);
var keyPair = Utils.fromData<EncodedKeyPair>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.crypto.CryptoManager.generateEncodedKeyPair(crypto));
````
</details>

:arrow_left: [Back to index](01_INDEX.md)