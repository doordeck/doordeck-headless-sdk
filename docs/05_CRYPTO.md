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
var keyPair = sdk.GetCryptoManager().GenerateEncodedKeyPair();
```
</details>

### Python
<details>
<summary>Show Details</summary>

```csharp
keyPair = sdk.cryptoManager.generate_key_pair()
```
</details>

:arrow_left: [Back to index](01_INDEX.md)
