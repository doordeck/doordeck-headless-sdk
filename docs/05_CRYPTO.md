# Crypto

## Generate a key pair

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val keyPair = sdk.crypto().generateKeyPair()
```
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let keyPair = sdk.crypto().generateKeyPair()
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const crypto = doordeck.com.doordeck.multiplatform.sdk.crypto.crypto()
const keyPair = crypto.generateKeyPair();
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var crypto = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.crypto(sdk);
var keyPair = Utils.FromData<EncodedKeyPair>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.crypto.CryptoManager.generateEncodedKeyPair(crypto));
```
</details>

:arrow_left: [Back to index](01_INDEX.md)
