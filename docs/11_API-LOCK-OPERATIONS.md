# Lock operations resource

## Get single lock

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.lockOperations().getSingleLock("LOCK_ID")
```
💡 **Note:** In Java, use the `getSingleLockAsync` function, which returns a `CompletableFuture<LockResponse>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.lockOperations().getSingleLock(lockId: "LOCK_ID")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getSingleLock("LOCK_ID");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var data = new GetSingleLockData("LOCK_ID");
var response = sdk.GetLockOperations().GetSingleLock(data);
```
</details>

## Get lock audit trail

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.lockOperations().getLockAuditTrail("LOCK_ID", START_EPOCH, END_EPOCH)
```
💡 **Note:** In Java, use the `getLockAuditTrailAsync` function, which returns a `CompletableFuture<List<LockAuditTrailResponse>>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.lockOperations().getLockAuditTrail(lockId: "LOCK_ID", start: START_EPOCH, end: END_EPOCH)
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getLockAuditTrail("LOCK_ID", START_EPOCH, END_EPOCH);
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var data = new GetLockAuditTrailData("LOCK_ID", START_EPOCH, END_EPOCH);
var response = sdk.GetLockOperations().GetLockAuditTrail(data);
```
</details>

## Get audit for user

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.lockOperations().getAuditForUser("USER_ID", START_EPOCH, END_EPOCH)
```
💡 **Note:** In Java, use the `getAuditForUserAsync` function, which returns a `CompletableFuture<List<AuditResponse>>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.lockOperations().getAuditForUser(userId: "USER_ID", start: START_EPOCH, end: END_EPOCH)
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getAuditForUser("USER_ID", START_EPOCH, END_EPOCH);
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var data = new GetAuditForUserData("USER_ID", START_EPOCH, END_EPOCH);
var response = sdk.GetLockOperations().GetAuditForUser(data);
```
</details>

## Get users for lock

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.lockOperations().getUsersForLock("LOCK_ID")
```
💡 **Note:** In Java, use the `getUsersForLockAsync` function, which returns a `CompletableFuture<List<UserLockResponse>>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.lockOperations().getUsersForLock(lockId: "LOCK_ID")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getUsersForLock("LOCK_ID");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var data = new GetUsersForLockData("LOCK_ID");
var response = sdk.GetLockOperations().GetUsersForLock(data);
```
</details>

## Get locks for user

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.lockOperations().getLocksForUser("LOCK_ID")
```
💡 **Note:** In Java, use the `getLocksForUserAsync` function, which returns a `CompletableFuture<LockUserResponse>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.lockOperations().getLocksForUser(lockId: "LOCK_ID")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getLocksForUser("LOCK_ID");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var data = new GetLocksForUserData("LOCK_ID");
var response = sdk.GetLockOperations().GetLocksForUser(data);
```
</details>

## Update lock name

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.lockOperations().updateLockName("LOCK_ID", "LOCK_NAME")
```
💡 **Note:** In Java, use the `updateLockNameAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.lockOperations().updateLockName(lockId: "LOCK_ID", name: "LOCK_NAME")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().updateLockName("LOCK_ID", "LOCK_NAME");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var data = new UpdateLockNameData("LOCK_ID", "LOCK_NAME");
sdk.GetLockOperations().UpdateLockName(data);
```
</details>

## Update lock favourite

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.lockOperations().updateLockFavourite("LOCK_ID", true)
```
💡 **Note:** In Java, use the `updateLockFavouriteAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.lockOperations().updateLockFavourite(lockId: "LOCK_ID", favourite: true)
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().updateLockFavourite("LOCK_ID", true);
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var data = new UpdateLockFavouriteData("LOCK_ID", true);
sdk.GetLockOperations().UpdateLockFavourite(data);
```
</details>

## Update lock color

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.lockOperations().updateLockColour("LOCK_ID", "COLOR")
```
💡 **Note:** In Java, use the `updateLockColourAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.lockOperations().updateLockColour(lockId: "LOCK_ID", color: "COLOR")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().updateLockColour("LOCK_ID", "COLOR");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var data = new UpdateLockColourData("LOCK_ID", "COLOR");
sdk.GetLockOperations().UpdateLockColour(data);
```
</details>

## Update lock setting default name

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.lockOperations().updateLockSettingDefaultName("LOCK_ID", "LOCK_NAME")
```
💡 **Note:** In Java, use the `updateLockSettingDefaultNameAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.lockOperations().updateLockSettingDefaultName(lockId: "LOCK_ID", name: "LOCK_NAME")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().updateLockSettingDefaultName("LOCK_ID", "LOCK_NAME");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var data = new UpdateLockSettingDefaultNameData("LOCK_ID", "LOCK_NAME");
sdk.GetLockOperations().UpdateLockSettingDefaultName(data);
```
</details>

## Set lock setting permitted addresses

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.lockOperations().setLockSettingPermittedAddresses("LOCK_ID", listOf("PERMITTED_ADDRESS"))
```
💡 **Note:** In Java, use the `setLockSettingPermittedAddressesAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.lockOperations().setLockSettingPermittedAddresses(lockId: "LOCK_ID", permittedAddresses: ["PERMITTED_ADDRESS"])
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const ktList = doordeck.kotlin.collections.KtList;
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().setLockSettingPermittedAddresses("LOCK_ID", ktList.fromJsArray(["PERMITTED_ADDRESS"]));
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var data = new SetLockSettingPermittedAddressesData("LOCK_ID", ["PERMITTED_ADDRESS"]);
sdk.GetLockOperations().SetLockSettingPermittedAddresses(data);
```
</details>

## Update lock setting hidden

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
sdk.lockOperations().updateLockSettingHidden("LOCK_ID", true)
```
💡 **Note:** In Java, use the `updateLockSettingHiddenAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
sdk.lockOperations().updateLockSettingHidden(lockId: "LOCK_ID", hidden: true)
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().updateLockSettingHidden("LOCK_ID", true);
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var data = new UpdateLockSettingHiddenData("LOCK_ID", true);
sdk.GetLockOperations().UpdateLockSettingHidden(data);
```
</details>

## Set lock setting time restrictions

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val timeRequirements = listOf(LockOperations.TimeRequirement("START_HH_MM", "END_HH_MM", "TIMEZONE", listOf("MONDAY")))
sdk.lockOperations().setLockSettingTimeRestrictions("LOCK_ID", timeRequirements)
```
💡 **Note:** In Java, use the `setLockSettingTimeRestrictionsAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let timeRequirements = [LockOperations.TimeRequirement(start: "START_HH_MM", end: "END_HH_MM", timezone: "TIMEZONE", days: ["MONDAY"])]
sdk.lockOperations().setLockSettingTimeRestrictions(lockId: "LOCK_ID", times: locationRequirement)
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const ktList = doordeck.kotlin.collections.KtList;
const lockOperations = doordeck.com.doordeck.multiplatform.sdk.api.model.LockOperations;
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().setLockSettingTimeRestrictions("LOCK_ID",
    ktList.fromJsArray([new lockOperations.TimeRequirement("START_HH_MM", "END_HH_MM", "TIMEZONE", ktList.fromJsArray(["MONDAY"]))]));
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
List<TimeRequirementData> timeRequirementsData = [new TimeRequirementData("START_HH_MM", "END_HH_MM", "TIMEZONE", ["MONDAY"])];
var data = new SetLockSettingTimeRestrictionsData("LOCK_ID", timeRequirementsData);
sdk.GetLockOperations().SetLockSettingTimeRestrictions(data);
```
</details>

## Update lock setting location restrictions

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val locationRequirement = LockOperations.LocationRequirement(LATITUDE, LONGITUDE, true, 100)
sdk.lockOperations().updateLockSettingLocationRestrictions("LOCK_ID", locationRequirement)
```
💡 **Note:** In Java, use the `updateLockSettingLocationRestrictionsAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let locationRequirement = LockOperations.LocationRequirement(latitude: LATITUDE, longitude: LONGITUDE, enabled: true, radius: 100, accuracy: 100)
sdk.lockOperations().updateLockSettingLocationRestrictions(lockId: "LOCK_ID", location: locationRequirement)
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const lockOperations = doordeck.com.doordeck.multiplatform.sdk.api.model.LockOperations;
const locationRequirement = new lockOperations.LocationRequirement(LATITUDE, LONGITUDE, ENABLED, RADIUS, ACCURACY);
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().updateLockSettingLocationRestrictions("LOCK_ID", locationRequirement);
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var locationRequirementData = new LocationRequirementData(LATITUDE, LONGITUDE, true, 100);
var data = new UpdateLockSettingLocationRestrictionsData("LOCK_ID", locationRequirementData);
sdk.GetLockOperations().UpdateLockSettingLocationRestrictions(data);
```
</details>

## Get a Doordeck user's public key

> [!IMPORTANT]
> This function is only available to users with Doordeck issued auth tokens

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.lockOperations().getUserPublicKey("USER_EMAIL", false)
```
💡 **Note:** In Java, use the `getUserPublicKeyAsync` function, which returns a `CompletableFuture<UserPublicKeyResponse>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.lockOperations().getUserPublicKey(userEmail: "USER_EMAIL", visitor: false)
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getUserPublicKey("USER_EMAIL", false);
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var data = new GetUserPublicKeyData("USER_EMAIL", false);
var response = sdk.GetLockOperations().GetUserPublicKey(data);
```
</details>

## Lookup user public key by email

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.lockOperations().getUserPublicKeyByEmail("USER_EMAIL")
```
💡 **Note:** In Java, use the `getUserPublicKeyByEmailAsync` function, which returns a `CompletableFuture<UserPublicKeyResponse>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.lockOperations().getUserPublicKeyByEmail(email: "USER_EMAIL")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getUserPublicKeyByEmail("USER_EMAIL");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var data = new GetUserPublicKeyByEmailData("USER_EMAIL");
var response = sdk.GetLockOperations().GetUserPublicKeyByEmail(data);
```
</details>

## Lookup user public key by telephone

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.lockOperations().getUserPublicKeyByTelephone("USER_TELEPHONE")
```
💡 **Note:** In Java, use the `getUserPublicKeyByTelephoneAsync` function, which returns a `CompletableFuture<UserPublicKeyResponse>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.lockOperations().getUserPublicKeyByTelephone(telephone: "USER_TELEPHONE")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getUserPublicKeyByTelephone("USER_TELEPHONE");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var data = new GetUserPublicKeyByTelephoneData("USER_TELEPHONE");
var response = sdk.GetLockOperations().GetUserPublicKeyByTelephone(data);
```
</details>

## Lookup user public key by local key

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.lockOperations().getUserPublicKeyByLocalKey("USER_LOCAL_KEY")
```
💡 **Note:** In Java, use the `getUserPublicKeyByLocalKeyAsync` function, which returns a `CompletableFuture<UserPublicKeyResponse>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.lockOperations().getUserPublicKeyByLocalKey(localKey: "USER_LOCAL_KEY")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getUserPublicKeyByLocalKey("USER_LOCAL_KEY");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var data = new GetUserPublicKeyByLocalKeyData("USER_LOCAL_KEY");
var response = sdk.GetLockOperations().GetUserPublicKeyByLocalKey(data);
```
</details>

## Lookup user public key by foreign key

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.lockOperations().getUserPublicKeyByForeignKey("USER_FOREIGN_KEY")
```
💡 **Note:** In Java, use the `getUserPublicKeyByForeignKeyAsync` function, which returns a `CompletableFuture<UserPublicKeyResponse>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.lockOperations().getUserPublicKeyByForeignKey(foreignKey: "USER_FOREIGN_KEY")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getUserPublicKeyByForeignKey("USER_FOREIGN_KEY");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var data = new GetUserPublicKeyByForeignKeyData("USER_FOREIGN_KEY");
var response = sdk.GetLockOperations().GetUserPublicKeyByForeignKey(data);
```
</details>

## Lookup user public key by identity

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.lockOperations().getUserPublicKeyByIdentity("USER_IDENTITY")
```
💡 **Note:** In Java, use the `getUserPublicKeyByIdentityAsync` function, which returns a `CompletableFuture<UserPublicKeyResponse>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.lockOperations().getUserPublicKeyByIdentity(identity: "USER_IDENTITY")
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getUserPublicKeyByIdentity("USER_IDENTITY");
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var data = new GetUserPublicKeyByIdentityData("USER_IDENTITY");
var response = sdk.GetLockOperations().GetUserPublicKeyByIdentity(data);
```
</details>


## Batch lookup user public key by email

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.lockOperations().getUserPublicKeyByEmails(listOf("USER_EMAIL", "USER_EMAIL"))
```
💡 **Note:** In Java, use the `getUserPublicKeyByEmailsAsync` function, which returns a `CompletableFuture<List<BatchUserPublicKeyResponse>>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.lockOperations().getUserPublicKeyByEmails(emails: ["USER_EMAIL", "USER_EMAIL"])
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const ktList = doordeck.kotlin.collections.KtList;
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getUserPublicKeyByEmails(ktList.fromJsArray(["USER_EMAIL", "USER_EMAIL"]));
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var data = new GetUserPublicKeyByEmailsData(["USER_EMAIL", "USER_EMAIL"]);
var response = sdk.GetLockOperations().GetUserPublicKeyByEmails(data);
```
</details>

## Batch lookup user public key by telephone

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.lockOperations().getUserPublicKeyByTelephones(listOf("USER_TELEPHONE", "USER_TELEPHONE"))
```
💡 **Note:** In Java, use the `getUserPublicKeyByTelephonesAsync` function, which returns a `CompletableFuture<List<BatchUserPublicKeyResponse>>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.lockOperations().getUserPublicKeyByTelephones(telephones: ["USER_TELEPHONE", "USER_TELEPHONE"])
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const ktList = doordeck.kotlin.collections.KtList;
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getUserPublicKeyByTelephones(ktList.fromJsArray(["USER_TELEPHONE", "USER_TELEPHONE"]));
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var data = new GetUserPublicKeyByTelephonesData(["USER_TELEPHONE", "USER_TELEPHONE"]);
var response = sdk.GetLockOperations().GetUserPublicKeyByTelephones(data);
```
</details>

## Batch lookup user public key by local key

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.lockOperations().getUserPublicKeyByLocalKeys(listOf("USER_LOCAL_KEY"))
```
💡 **Note:** In Java, use the `getUserPublicKeyByLocalKeysAsync` function, which returns a `CompletableFuture<List<BatchUserPublicKeyResponse>>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.lockOperations().getUserPublicKeyByLocalKeys(localKeys: ["USER_LOCAL_KEY", "USER_LOCAL_KEY"])
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const ktList = doordeck.kotlin.collections.KtList;
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getUserPublicKeyByLocalKeys(ktList.fromJsArray(["USER_LOCAL_KEY", "USER_LOCAL_KEY"]));
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var data = new GetUserPublicKeyByLocalKeysData(["USER_LOCAL_KEY", "USER_LOCAL_KEY"]);
var response = sdk.GetLockOperations().GetUserPublicKeyByLocalKeys(data);
```
</details>

## Batch lookup user public key by foreign key

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.lockOperations().getUserPublicKeyByForeignKeys(listOf("USER_FOREIGN_KEY", "USER_FOREIGN_KEY"))
```
💡 **Note:** In Java, use the `getUserPublicKeyByForeignKeysAsync` function, which returns a `CompletableFuture<List<BatchUserPublicKeyResponse>>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.lockOperations().getUserPublicKeyByForeignKeys(foreignKeys: ["USER_FOREIGN_KEY", "USER_FOREIGN_KEY"])
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const ktList = doordeck.kotlin.collections.KtList;
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getUserPublicKeyByForeignKey(ktList.fromJsArray(["USER_FOREIGN_KEY", "USER_FOREIGN_KEY"]));
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var data = new GetUserPublicKeyByForeignKeysData(["USER_FOREIGN_KEY", "USER_FOREIGN_KEY"]);
var response = sdk.GetLockOperations().GetUserPublicKeyByForeignKeys(data);
```
</details>

## Unlock

> [!NOTE]
> This function can be used with the [user ID, certificate chain, and private key](06_CONTEXT-MANAGER.md#set-operation-context) values from the context. To use these values from the context, you should set those parameters to null in the ```BaseOperation``` object.

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val baseOperation = LockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID")
val unlockOperation = LockOperations.UnlockOperation(baseOperation)
sdk.lockOperations().unlock(unlockOperation)
```
💡 **Note:** In Java, use the `unlockAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let baseOperation = LockOperations.BaseOperation(userId: "USER_ID", userCertificateChain: USER_CERTIFICATE_CHAIN_LIST, userPrivateKey: PRIVATE_KEY, lockId: "LOCK_ID", notBefore: NOT_BEFORE, issuedAt: ISSUED_AT, expiresAt: EXPIRES_AT, jti: UUID)
let unlockOperation = LockOperations.UnlockOperation(baseOperation: baseOperation, directAccessEndpoints: null)
sdk.lockOperations().unlock(unlockOperation: shareLockOperation)
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const lockOperations = doordeck.com.doordeck.multiplatform.sdk.api.model.LockOperations;
const baseOperation = new lockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID", NOT_BEFORE, ISSUED_AT, EXPIRES_AT, "UUID");
const unlockOperation = new lockOperations.UnlockOperation(baseOperation, null);
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().unlock(unlockOperation);
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var baseOperationData = new BaseOperationData("USER_ID", USER_CERTIFICATE_CHAIN_LIST, "BASE64_PRIVATE_KEY", "LOCK_ID");
var data = new UnlockOperationData(baseOperationData);
sdk.GetLockOperations().Unlock(data);
```
</details>

## Share lock

> [!NOTE]
> This function can be used with the [user ID, certificate chain, and private key](06_CONTEXT-MANAGER.md#set-operation-context) values from the context. To use these values from the context, you should set those parameters to null in the ```BaseOperation``` object.

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val baseOperation = LockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID")
val shareLock = LockOperations.ShareLock("TARGET_USER_ID", TARGET_USER_ROLE, TARGET_PUBLIC_KEY)
val shareLockOperation = LockOperations.ShareLockOperation(baseOperation, shareLock)
sdk.lockOperations().shareLock(shareLockOperation)
```
💡 **Note:** In Java, use the `shareLockAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let baseOperation = LockOperations.BaseOperation(userId: "USER_ID", userCertificateChain: USER_CERTIFICATE_CHAIN_LIST, userPrivateKey: PRIVATE_KEY, lockId: "LOCK_ID", notBefore: NOT_BEFORE, issuedAt: ISSUED_AT, expiresAt: EXPIRES_AT, jti: UUID)
let shareLock = LockOperations.ShareLock(targetUserId: "TARGET_USER_ID", targetUserRole: TARGET_USER_ROLE, targetUserPublicKey: TARGET_PUBLIC_KEY, start: null, end: null)
let shareLockOperation = LockOperations.ShareLockOperation(baseOperation: baseOperation, shareLock: shareLock)
sdk.lockOperations().shareLock(shareLockOperation: shareLockOperation)
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const lockOperations = doordeck.com.doordeck.multiplatform.sdk.api.model.LockOperations;
const baseOperation = new lockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID", NOT_BEFORE, ISSUED_AT, EXPIRES_AT, "UUID");
const userRole = doordeck.com.doordeck.multiplatform.sdk.api.model.UserRole;
const shareLock = new lockOperations.ShareLock("TARGET_USER_ID", userRole.USER, TARGET_PUBLIC_KEY, null, null);
const shareLockOperation = new lockOperations.ShareLockOperation(baseOperation, shareLock);
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().shareLock(shareLockOperation);
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var baseOperationData = new BaseOperationData("USER_ID", USER_CERTIFICATE_CHAIN_LIST, "BASE64_PRIVATE_KEY", "LOCK_ID");
var shareLockData = new ShareLockData("TARGET_USER_ID", TARGET_USER_ROLE, "BASE64_TARGET_PUBLIC_KEY");
var data = new ShareLockOperationData(baseOperationData, shareLockData);
sdk.GetLockOperations().ShareLock(data);
```
</details>

## Batch share lock
> [!NOTE]
> This functionality requires the device to support the batch sharing feature. The function will retrieve the device's capabilities, store them in a cache, and perform the batch operation if supported. Otherwise, it will default to the standard device sharing process.

> [!NOTE]
> This function can be used with the [user ID, certificate chain, and private key](06_CONTEXT-MANAGER.md#set-operation-context) values from the context. To use these values from the context, you should set those parameters to null in the ```BaseOperation``` object.

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val baseOperation = LockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID")
val users = listOf(LockOperations.ShareLock("TARGET_USER_ID", TARGET_USER_ROLE, TARGET_PUBLIC_KEY))
val shareLockOperation = LockOperations.BatchShareLockOperation(baseOperation, users)
sdk.lockOperations().batchShareLock(shareLockOperation)
```
💡 **Note:** In Java, use the `batchShareLockAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let baseOperation = LockOperations.BaseOperation(userId: "USER_ID", userCertificateChain: USER_CERTIFICATE_CHAIN_LIST, userPrivateKey: PRIVATE_KEY, lockId: "LOCK_ID", notBefore: NOT_BEFORE, issuedAt: ISSUED_AT, expiresAt: EXPIRES_AT, jti: UUID)
let users = [LockOperations.ShareLock(targetUserId: "TARGET_USER_ID", targetUserRole: TARGET_USER_ROLE, targetUserPublicKey: TARGET_PUBLIC_KEY, start: null, end: null)]
let shareLockOperation = LockOperations.BatchShareLockOperation(baseOperation: baseOperation, users: users)
sdk.lockOperations().batchShareLock(shareLockOperation: shareLockOperation)
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const ktList = doordeck.kotlin.collections.KtList;
const lockOperations = doordeck.com.doordeck.multiplatform.sdk.api.model.LockOperations;
const baseOperation = new lockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID", NOT_BEFORE, ISSUED_AT, EXPIRES_AT, "UUID");
const userRole = doordeck.com.doordeck.multiplatform.sdk.api.model.UserRole;
const users = [new lockOperations.ShareLock("TARGET_USER_ID", userRole.USER, TARGET_PUBLIC_KEY, null, null)];
const shareLockOperation = new lockOperations.BatchShareLockOperation(baseOperation, ktList.fromJsArray(users));
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().batchShareLock(shareLockOperation);
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var baseOperationData = new BaseOperationData("USER_ID", USER_CERTIFICATE_CHAIN_LIST, "BASE64_PRIVATE_KEY", "LOCK_ID");
var users = [new ShareLockData("TARGET_USER_ID", TARGET_USER_ROLE, "BASE64_TARGET_PUBLIC_KEY")];
var data = new BatchShareLockOperationData(baseOperationData, users);
sdk.GetLockOperations().BatchShareLock(data);
```
</details>

## Revoke access to lock

> [!NOTE]
> This function can be used with the [user ID, certificate chain, and private key](06_CONTEXT-MANAGER.md#set-operation-context) values from the context. To use these values from the context, you should set those parameters to null in the ```BaseOperation``` object.

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val baseOperation = LockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID")
val revokeAccessToLockOperation = LockOperations.RevokeAccessToLockOperation(baseOperation, listOf("USER_ID"))
sdk.lockOperations().revokeAccessToLock(revokeAccessToLockOperation)
```
💡 **Note:** In Java, use the `revokeAccessToLockAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let baseOperation = LockOperations.BaseOperation(userId: "USER_ID", userCertificateChain: USER_CERTIFICATE_CHAIN_LIST, userPrivateKey: PRIVATE_KEY, lockId: "LOCK_ID", notBefore: NOT_BEFORE, issuedAt: ISSUED_AT, expiresAt: EXPIRES_AT, jti: UUID)
let revokeAccessToLockOperation = LockOperations.RevokeAccessToLockOperation(baseOperation: baseOperation, users: ["USER_ID"])
sdk.lockOperations().revokeAccessToLock(revokeAccessToLockOperation: revokeAccessToLockOperation)
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const ktList = doordeck.kotlin.collections.KtList;
const lockOperations = doordeck.com.doordeck.multiplatform.sdk.api.model.LockOperations;
const baseOperation = new lockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID", NOT_BEFORE, ISSUED_AT, EXPIRES_AT, "UUID");
const revokeAccessToLockOperation = new lockOperations.RevokeAccessToLockOperation(baseOperation, ktList.fromJsArray(["USER_ID"]));
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().revokeAccessToLock(revokeAccessToLockOperation);
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var baseOperationData = new BaseOperationData("USER_ID", USER_CERTIFICATE_CHAIN_LIST, "BASE64_PRIVATE_KEY", "LOCK_ID");
var data = new RevokeAccessToLockOperationData(baseOperationData, ["USER_ID"]);
sdk.GetLockOperations().RevokeAccessToLock(data);
```
</details>

## Update secure setting unlock duration

> [!NOTE]
> This function can be used with the [user ID, certificate chain, and private key](06_CONTEXT-MANAGER.md#set-operation-context) values from the context. To use these values from the context, you should set those parameters to null in the ```BaseOperation``` object.

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val baseOperation = LockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID")
val updateSecureSettingUnlockDuration = LockOperations.UpdateSecureSettingUnlockDuration(baseOperation, UNLOCK_DURATION)
sdk.lockOperations().updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration)
```
💡 **Note:** In Java, use the `updateSecureSettingUnlockDurationAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let baseOperation = LockOperations.BaseOperation(userId: "USER_ID", userCertificateChain: USER_CERTIFICATE_CHAIN_LIST, userPrivateKey: PRIVATE_KEY, lockId: "LOCK_ID", notBefore: NOT_BEFORE, issuedAt: ISSUED_AT, expiresAt: EXPIRES_AT, jti: UUID)
let updateSecureSettingUnlockDuration = LockOperations.UpdateSecureSettingUnlockDuration(baseOperation: baseOperation, unlockDuration: UNLOCK_DURATION)
sdk.lockOperations().updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration: updateSecureSettingUnlockDuration)
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const lockOperations = doordeck.com.doordeck.multiplatform.sdk.api.model.LockOperations;
const baseOperation = new lockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID", NOT_BEFORE, ISSUED_AT, EXPIRES_AT, "UUID");
const updateSecureSettingUnlockDuration = new lockOperations.UpdateSecureSettingUnlockDuration(baseOperation, UNLOCK_DURATION);
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration);
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var baseOperationData = new BaseOperationData("USER_ID", USER_CERTIFICATE_CHAIN_LIST, "BASE64_PRIVATE_KEY", "LOCK_ID");
var data = new UpdateSecureSettingUnlockDurationData(baseOperationData, UNLOCK_DURATION)
sdk.GetLockOperations().UpdateSecureSettingUnlockDuration(data);
```
</details>

## Update secure setting unlock between

> [!NOTE]
> This function can be used with the [user ID, certificate chain, and private key](06_CONTEXT-MANAGER.md#set-operation-context) values from the context. To use these values from the context, you should set those parameters to null in the ```BaseOperation``` object.

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val baseOperation = LockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID")
val unlockBetween = LockOperations.UnlockBetween("START_HH_MM", "END_HH_MM", "TIMEZONE", DAYS_LIST)
val updateSecureSettingUnlockBetween = LockOperations.UpdateSecureSettingUnlockBetween(baseOperation, unlockBetween)
sdk.lockOperations().updateSecureSettingUnlockBetween(updateSecureSettingUnlockBetween)
```
💡 **Note:** In Java, use the `updateSecureSettingUnlockBetweenAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let baseOperation = LockOperations.BaseOperation(userId: "USER_ID", userCertificateChain: USER_CERTIFICATE_CHAIN_LIST, userPrivateKey: PRIVATE_KEY, lockId: "LOCK_ID", notBefore: NOT_BEFORE, issuedAt: ISSUED_AT, expiresAt: EXPIRES_AT, jti: UUID)
let unlockBetween = LockOperations.UnlockBetween(start: "START_HH_MM", end: "END_HH_MM", timezone: "TIMEZONE", days: ["MONDAY"], exceptions: ["FRIDAY"])
let updateSecureSettingUnlockBetween = LockOperations.UpdateSecureSettingUnlockBetween(baseOperation: baseOperation, unlockBetween: unlockBetween)
sdk.lockOperations().updateSecureSettingUnlockBetween(updateSecureSettingUnlockBetween: updateSecureSettingUnlockBetween)
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const lockOperations = doordeck.com.doordeck.multiplatform.sdk.api.model.LockOperations;
const baseOperation = new lockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID", NOT_BEFORE, ISSUED_AT, EXPIRES_AT, "UUID");
const unlockBetween = new lockOperations.UnlockBetween("START_HH_MM", "END_HH_MM", "TIMEZONE", DAYS_LIST, EXCEPTIONS_LIST);
const updateSecureSettingUnlockBetween = new lockOperations.UpdateSecureSettingUnlockBetween(baseOperation, unlockBetween)
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().updateSecureSettingUnlockBetween(updateSecureSettingUnlockBetween);
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var baseOperationData = new BaseOperationData("USER_ID", USER_CERTIFICATE_CHAIN_LIST, "BASE64_PRIVATE_KEY", "LOCK_ID");
var unlockBetweenData = new UnlockBetweenData("START_HH_MM", "END_HH_MM", "TIMEZONE", DAYS_LIST);
var data = new UpdateSecureSettingUnlockBetweenData(baseOperationData, unlockBetweenData);
sdk.GetLockOperations().UpdateSecureSettingUnlockBetween(data);
```
</details>

## Get pinned locks

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.lockOperations().getPinnedLocks()
```
💡 **Note:** In Java, use the `getPinnedLocksAsync` function, which returns a `CompletableFuture<List<LockResponse>>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.lockOperations().getPinnedLocks()
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getPinnedLocks();
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var response = sdk.GetLockOperations().GetPinnedLocks();
```
</details>

## Get shareable locks

### JVM & Android
<details>
<summary>Show Details</summary>

```kotlin
val response = sdk.lockOperations().getShareableLocks()
```
💡 **Note:** In Java, use the `getShareableLocksAsync` function, which returns a `CompletableFuture<List<ShareableLockResponse>>` instead
</details>

### Swift
<details>
<summary>Show Details</summary>

```swift
let response = sdk.lockOperations().getShareableLocks()
```
</details>

### JavaScript
<details>
<summary>Show Details</summary>

```js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getShareableLocks();
```
</details>

### C#
<details>
<summary>Show Details</summary>

```csharp
var response = sdk.GetLockOperations().GetShareableLocks();
```
</details>

:arrow_left: [Back to index](01_INDEX.md)