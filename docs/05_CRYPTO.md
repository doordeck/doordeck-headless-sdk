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

:arrow_left: [Back to index](01_INDEX.md)