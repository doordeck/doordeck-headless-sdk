# Lock operations resource

### Get single lock
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.lockOperations().getSingleLock("LOCK_ID")
````
>:information_source: In Java, you can use the `getSingleLockAsync` function, which returns a `CompletableFuture<LockResponse>` instead
</details>

<details><summary>Swift</summary>

````swift
let response = sdk.lockOperations().getSingleLock(lockId: "LOCK_ID")
````
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getSingleLock("LOCK_ID");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new GetSingleLockData("LOCK_ID").toData();
var response = Utils.fromData<LockResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getSingleLockJson(resource, data));
````
</details>

### Get lock audit trail
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.lockOperations().getLockAuditTrail("LOCK_ID", START_EPOCH, END_EPOCH)
````
>:information_source: In Java, you can use the `getLockAuditTrailAsync` function, which returns a `CompletableFuture<List<LockAuditTrailResponse>>` instead
</details>

<details><summary>Swift</summary>

````swift
let response = sdk.lockOperations().getLockAuditTrail(lockId: "LOCK_ID", start: START_EPOCH, end: END_EPOCH)
````
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getLockAuditTrail("LOCK_ID", START_EPOCH, END_EPOCH);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new GetLockAuditTrailData("LOCK_ID", START_EPOCH, END_EPOCH).toData();
var response = Utils.fromData<List<LockAuditTrailResponse>>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getLockAuditTrailJson(resource, data));
````
</details>

### Get audit for user
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.lockOperations().getAuditForUser("USER_ID", START_EPOCH, END_EPOCH)
````
>:information_source: In Java, you can use the `getAuditForUserAsync` function, which returns a `CompletableFuture<List<UserAuditResponse>>` instead
</details>

<details><summary>Swift</summary>

````swift
let response = sdk.lockOperations().getAuditForUser(userId: "USER_ID", start: START_EPOCH, end: END_EPOCH)
````
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getAuditForUser("USER_ID", START_EPOCH, END_EPOCH);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new GetAuditForUserData("USER_ID", START_EPOCH, END_EPOCH).toData();
var response = Utils.fromData<List<UserAuditResponse>>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getAuditForUserJson(resource, data));
````
</details>

### Get users for lock
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.lockOperations().getUsersForLock("LOCK_ID")
````
>:information_source: In Java, you can use the `getUsersForLockAsync` function, which returns a `CompletableFuture<List<UserLockResponse>>` instead
</details>

<details><summary>Swift</summary>

````swift
let response = sdk.lockOperations().getUsersForLock(lockId: "LOCK_ID")
````
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getUsersForLock("LOCK_ID");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new GetUsersForLockData("LOCK_ID").toData();
var response = Utils.fromData<List<UserLockResponse>>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getUsersForLockJson(resource, data));
````
</details>

### Get locks for user
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.lockOperations().getLocksForUser("LOCK_ID")
````
>:information_source: In Java, you can use the `getLocksForUserAsync` function, which returns a `CompletableFuture<LockUserResponse>` instead
</details>

<details><summary>Swift</summary>

````swift
let response = sdk.lockOperations().getLocksForUser(lockId: "LOCK_ID")
````
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getLocksForUser("LOCK_ID");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new GetLocksForUserData("LOCK_ID").toData();
var response = Utils.fromData<LockUserResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getLocksForUserJson(resource, data));
````
</details>

### Update lock name
<details><summary>JVM & Android</summary>

````kotlin
sdk.lockOperations().updateLockName("LOCK_ID", "LOCK_NAME")
````
>:information_source: In Java, you can use the `updateLockNameAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
sdk.lockOperations().updateLockName(lockId: "LOCK_ID", name: "LOCK_NAME")
````
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().updateLockName("LOCK_ID", "LOCK_NAME");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new UpdateLockNameData("LOCK_ID", "LOCK_NAME").toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.updateLockNameJson(resource, data);
````
</details>

### Update lock favourite
<details><summary>JVM & Android</summary>

````kotlin
sdk.lockOperations().updateLockFavourite("LOCK_ID", true)
````
>:information_source: In Java, you can use the `updateLockFavouriteAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
sdk.lockOperations().updateLockFavourite(lockId: "LOCK_ID", favourite: true)
````
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().updateLockFavourite("LOCK_ID", true);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new UpdateLockFavouriteData("LOCK_ID", true).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.updateLockFavouriteJson(resource, data);
````
</details>

### Update lock color
<details><summary>JVM & Android</summary>

````kotlin
sdk.lockOperations().updateLockColour("LOCK_ID", "COLOR")
````
>:information_source: In Java, you can use the `updateLockColourAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
sdk.lockOperations().updateLockColour(lockId: "LOCK_ID", color: "COLOR")
````
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().updateLockColour("LOCK_ID", "COLOR");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new UpdateLockColourData("LOCK_ID", "COLOR").toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.updateLockColourJson(resource, data);
````
</details>

### Update lock setting default name
<details><summary>JVM & Android</summary>

````kotlin
sdk.lockOperations().updateLockSettingDefaultName("LOCK_ID", "LOCK_NAME")
````
>:information_source: In Java, you can use the `updateLockSettingDefaultNameAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
sdk.lockOperations().updateLockSettingDefaultName(lockId: "LOCK_ID", name: "LOCK_NAME")
````
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().updateLockSettingDefaultName("LOCK_ID", "LOCK_NAME");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new UpdateLockSettingDefaultNameData("LOCK_ID", "LOCK_NAME").toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.updateLockSettingDefaultNameJson(resource, data);
````
</details>

### Set lock setting permitted addresses
<details><summary>JVM & Android</summary>

````kotlin
sdk.lockOperations().setLockSettingPermittedAddresses("LOCK_ID", listOf("PERMITTED_ADDRESS"))
````
>:information_source: In Java, you can use the `setLockSettingPermittedAddressesAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
sdk.lockOperations().setLockSettingPermittedAddresses(lockId: "LOCK_ID", permittedAddresses: ["PERMITTED_ADDRESS"])
````
</details>

<details><summary>JS</summary>

````js
const ktList = doordeck.kotlin.collections.KtList;
const permittedAddresses = ktList.fromJsArray(["PERMITTED_ADDRESS"]);
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().setLockSettingPermittedAddresses("LOCK_ID", permittedAddresses);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
List<string> permittedAddresses = ["PERMITTED_ADDRESS"];
var data = new SetLockSettingPermittedAddressesData("LOCK_ID", permittedAddresses).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.setLockSettingPermittedAddressesJson(resource, data);
````
</details>

### Update lock setting hidden
<details><summary>JVM & Android</summary>

````kotlin
sdk.lockOperations().updateLockSettingHidden("LOCK_ID", true)
````
>:information_source: In Java, you can use the `updateLockSettingHiddenAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
sdk.lockOperations().updateLockSettingHidden(lockId: "LOCK_ID", hidden: true)
````
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().updateLockSettingHidden("LOCK_ID", true);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new UpdateLockSettingHiddenData("LOCK_ID", true).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.updateLockSettingHiddenJson(resource, data);
````
</details>

### Set lock setting time restrictions
<details><summary>JVM & Android</summary>

````kotlin
val timeRequirements = listOf(LockOperations.TimeRequirement("START_HH_MM", "END_HH_MM", "TIMEZONE", listOf("MONDAY")))
sdk.lockOperations().setLockSettingTimeRestrictions("LOCK_ID", timeRequirements)
````
>:information_source: In Java, you can use the `setLockSettingTimeRestrictionsAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
let timeRequirements = [LockOperations.TimeRequirement(start: "START_HH_MM", end: "END_HH_MM", timezone: "TIMEZONE", days: ["MONDAY"])]
sdk.lockOperations().setLockSettingTimeRestrictions(lockId: "LOCK_ID", times: locationRequirement)
````
</details>

<details><summary>JS</summary>

````js
const ktList = doordeck.kotlin.collections.KtList;
const lockOperations = doordeck.com.doordeck.multiplatform.sdk.api.model.LockOperations;
const timeRequirements = ktList.fromJsArray([new lockOperations.TimeRequirement("START_HH_MM", "END_HH_MM", "TIMEZONE", ktList.fromJsArray(["MONDAY"]))]);
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().setLockSettingTimeRestrictions("LOCK_ID", timeRequirements);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
List<string> days = ["MONDAY"];
List<TimeRequirementData> timeRequirementsData = [new TimeRequirementData("START_HH_MM", "END_HH_MM", "TIMEZONE", days)];
var data = new SetLockSettingTimeRestrictionsData("LOCK_ID", timeRequirementsData).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.setLockSettingTimeRestrictionsJson(resource, data);
````
</details>


### Update lock setting location restrictions
<details><summary>JVM & Android</summary>

````kotlin
val locationRequirement = LockOperations.LocationRequirement(LATITUDE, LONGITUDE, true, 100)
sdk.lockOperations().updateLockSettingLocationRestrictions("LOCK_ID", locationRequirement)
````
>:information_source: In Java, you can use the `updateLockSettingLocationRestrictionsAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
let locationRequirement = LockOperations.LocationRequirement(latitude: LATITUDE, longitude: LONGITUDE, enabled: true, radius: 100, accuracy: 100)
sdk.lockOperations().updateLockSettingLocationRestrictions(lockId: "LOCK_ID", location: locationRequirement)
````
</details>

<details><summary>JS</summary>

````js
const lockOperations = doordeck.com.doordeck.multiplatform.sdk.api.model.LockOperations;
const locationRequirement = new lockOperations.LocationRequirement(LATITUDE, LONGITUDE, ENABLED, RADIUS, ACCURACY);
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().updateLockSettingLocationRestrictions("LOCK_ID", locationRequirement);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var locationRequirementData = new LocationRequirementData(LATITUDE, LONGITUDE, true, 100);
var data = new UpdateLockSettingLocationRestrictionsData("LOCK_ID", locationRequirementData).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.updateLockSettingLocationRestrictionsJson(resource, data);
````
</details>


### Get a Doordeck user's public key
> [!IMPORTANT]
> This function is only available to users with Doordeck issued auth tokens
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.lockOperations().getUserPublicKey("USER_EMAIL", false)
````
>:information_source: In Java, you can use the `getUserPublicKeyAsync` function, which returns a `CompletableFuture<UserPublicKeyResponse>` instead
</details>

<details><summary>Swift</summary>

````swift
let response = sdk.lockOperations().getUserPublicKey(userEmail: "USER_EMAIL", visitor: false)
````
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getUserPublicKey("USER_EMAIL", false);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new GetUserPublicKeyData("USER_EMAIL", false).toData();
var response = Utils.fromData<UserPublicKeyResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getUserPublicKeyJson(resource, data));
````
</details>

### Get user public key by email
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.lockOperations().getUserPublicKeyByEmail("USER_EMAIL")
````
>:information_source: In Java, you can use the `getUserPublicKeyByEmailAsync` function, which returns a `CompletableFuture<UserPublicKeyResponse>` instead
</details>

<details><summary>Swift</summary>

````swift
let response = sdk.lockOperations().getUserPublicKeyByEmail(email: "USER_EMAIL")
````
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getUserPublicKeyByEmail("USER_EMAIL");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new GetUserPublicKeyByEmailData("USER_EMAIL").toData();
var response = Utils.fromData<UserPublicKeyResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getUserPublicKeyByEmailJson(resource, data));
````
</details>

### Get user public key by telephone
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.lockOperations().getUserPublicKeyByTelephone("USER_TELEPHONE")
````
>:information_source: In Java, you can use the `getUserPublicKeyByTelephoneAsync` function, which returns a `CompletableFuture<UserPublicKeyResponse>` instead
</details>

<details><summary>Swift</summary>

````swift
let response = sdk.lockOperations().getUserPublicKeyByTelephone(telephone: "USER_TELEPHONE")
````
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getUserPublicKeyByTelephone("USER_TELEPHONE");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new GetUserPublicKeyByTelephoneData("USER_TELEPHONE").toData();
var response = Utils.fromData<UserPublicKeyResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getUserPublicKeyByTelephoneJson(resource, data));
````
</details>

### Get user public key by local key
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.lockOperations().getUserPublicKeyByLocalKey("USER_LOCAL_KEY")
````
>:information_source: In Java, you can use the `getUserPublicKeyByLocalKeyAsync` function, which returns a `CompletableFuture<UserPublicKeyResponse>` instead
</details>

<details><summary>Swift</summary>

````swift
let response = sdk.lockOperations().getUserPublicKeyByLocalKey(localKey: "USER_LOCAL_KEY")
````
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getUserPublicKeyByLocalKey("USER_LOCAL_KEY");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new GetUserPublicKeyByLocalKeyData("USER_LOCAL_KEY").toData();
var response = Utils.fromData<UserPublicKeyResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getUserPublicKeyByLocalKeyJson(resource, data));
````
</details>

### Get user public key by foreign key
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.lockOperations().getUserPublicKeyByForeignKey("USER_FOREIGN_KEY")
````
>:information_source: In Java, you can use the `getUserPublicKeyByForeignKeyAsync` function, which returns a `CompletableFuture<UserPublicKeyResponse>` instead
</details>

<details><summary>Swift</summary>

````swift
let response = sdk.lockOperations().getUserPublicKeyByForeignKey(foreignKey: "USER_FOREIGN_KEY")
````
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getUserPublicKeyByForeignKey("USER_FOREIGN_KEY");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new GetUserPublicKeyByForeignKeyData("USER_FOREIGN_KEY").toData();
var response = Utils.fromData<UserPublicKeyResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getUserPublicKeyByForeignKeyJson(resource, data));
````
</details>

### Get user public key by identity
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.lockOperations().getUserPublicKeyByIdentity("USER_IDENTITY")
````
>:information_source: In Java, you can use the `getUserPublicKeyByIdentityAsync` function, which returns a `CompletableFuture<UserPublicKeyResponse>` instead
</details>

<details><summary>Swift</summary>

````swift
let response = sdk.lockOperations().getUserPublicKeyByIdentity(identity: "USER_IDENTITY")
````
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getUserPublicKeyByIdentity("USER_IDENTITY");
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new GetUserPublicKeyByIdentityData("USER_IDENTITY").toData();
var response = Utils.fromData<UserPublicKeyResponse>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getUserPublicKeyByIdentityJson(resource, data));
````
</details>

### Unlock
<details><summary>JVM & Android</summary>

````kotlin
val baseOperation = LockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID")
val unlockOperation = LockOperations.UnlockOperation(baseOperation)
sdk.lockOperations().unlock(unlockOperation)
````
>:information_source: In Java, you can use the `unlockAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
let baseOperation = LockOperations.BaseOperation(userId: "USER_ID", userCertificateChain: USER_CERTIFICATE_CHAIN_LIST, userPrivateKey: PRIVATE_KEY, lockId: "LOCK_ID", notBefore: NOT_BEFORE, issuedAt: ISSUED_AT, expiresAt: EXPIRES_AT, jti: UUID)
let unlockOperation = LockOperations.UnlockOperation(baseOperation: baseOperation, directAccessEndpoints: null)
sdk.lockOperations().unlock(unlockOperation: shareLockOperation)
````
</details>

<details><summary>JS</summary>

````js
const lockOperations = doordeck.com.doordeck.multiplatform.sdk.api.model.LockOperations;
const baseOperation = new lockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID", NOT_BEFORE, ISSUED_AT, EXPIRES_AT, "UUID");
const unlockOperation = new lockOperations.UnlockOperation(baseOperation, null);
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().unlock(unlockOperation);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var baseOperationData = new BaseOperationData("USER_ID", USER_CERTIFICATE_CHAIN_LIST, "BASE64_PRIVATE_KEY", "LOCK_ID");
var data = new UnlockOperationData(baseOperationData).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.unlockJson(resource, data);
````
</details>

### Unlock with context
> [!IMPORTANT]  
> This functionality requires you to have previously [set the operation context](#set-operation-context).
<details><summary>JVM & Android</summary>

````kotlin
sdk.lockOperations().unlockWithContext("LOCK_ID")
````
>:information_source: In Java, you can use the `unlockWithContextAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
sdk.lockOperations().unlockWithContext(lockId: "LOCK_ID")
````
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().unlockWithContext("LOCK_ID", null);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new UnlockWithContextData("LOCK_ID").toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.unlockWithContextJson(resource, data);
````
</details>

### Share lock
<details><summary>JVM & Android</summary>

````kotlin
val baseOperation = LockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID")
val shareLock = LockOperations.ShareLock("TARGET_USER_ID", TARGET_USER_ROLE, TARGET_PUBLIC_KEY)
val shareLockOperation = LockOperations.ShareLockOperation(baseOperation, shareLock)
sdk.lockOperations().shareLock(shareLockOperation)
````
>:information_source: In Java, you can use the `shareLockAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
let baseOperation = LockOperations.BaseOperation(userId: "USER_ID", userCertificateChain: USER_CERTIFICATE_CHAIN_LIST, userPrivateKey: PRIVATE_KEY, lockId: "LOCK_ID", notBefore: NOT_BEFORE, issuedAt: ISSUED_AT, expiresAt: EXPIRES_AT, jti: UUID)
let shareLock = LockOperations.ShareLock(targetUserId: "TARGET_USER_ID", targetUserRole: TARGET_USER_ROLE, targetUserPublicKey: TARGET_PUBLIC_KEY, start: null, end: null)
let shareLockOperation = LockOperations.ShareLockOperation(baseOperation: baseOperation, shareLock: shareLock)
sdk.lockOperations().shareLock(shareLockOperation: shareLockOperation)
````
</details>

<details><summary>JS</summary>

````js
const lockOperations = doordeck.com.doordeck.multiplatform.sdk.api.model.LockOperations;
const baseOperation = new lockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID", NOT_BEFORE, ISSUED_AT, EXPIRES_AT, "UUID");
const userRole = doordeck.com.doordeck.multiplatform.sdk.api.model.UserRole;
const shareLock = new lockOperations.ShareLock("TARGET_USER_ID", userRole.USER, TARGET_PUBLIC_KEY, null, null);
const shareLockOperation = new lockOperations.shareLockOperation(baseOperation, shareLock);
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().shareLock(shareLockOperation);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var baseOperationData = new BaseOperationData("USER_ID", USER_CERTIFICATE_CHAIN_LIST, "BASE64_PRIVATE_KEY", "LOCK_ID");
var shareLockData = new ShareLockData("TARGET_USER_ID", TARGET_USER_ROLE, "BASE64_TARGET_PUBLIC_KEY");
var data = new ShareLockOperationData(baseOperationData, shareLockData).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.shareLockJson(resource, data);
````
</details>

### Share lock with context
> [!IMPORTANT]  
> This functionality requires you to have previously [set the operation context](#set-operation-context).
<details><summary>JVM & Android</summary>

````kotlin
val shareLock = LockOperations.ShareLock("TARGET_USER_ID", TARGET_USER_ROLE, TARGET_PUBLIC_KEY)
sdk.lockOperations().shareLockWithContext("LOCK_ID", shareLock)
````
>:information_source: In Java, you can use the `shareLockWithContextAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
let shareLock = LockOperations.ShareLock(targetUserId: "TARGET_USER_ID", targetUserRole: TARGET_USER_ROLE, targetUserPublicKey: TARGET_PUBLIC_KEY, start: null, end: null)
sdk.lockOperations().shareLockWithContext(lockId: "LOCK_ID", shareLock: shareLock)
````
</details>

<details><summary>JS</summary>

````js
const lockOperations = doordeck.com.doordeck.multiplatform.sdk.api.model.LockOperations;
const userRole = doordeck.com.doordeck.multiplatform.sdk.api.model.UserRole;
const shareLock = new lockOperations.ShareLock("TARGET_USER_ID", userRole.USER, TARGET_PUBLIC_KEY, null, null);
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().shareLockWithContext("LOCK_ID", shareLock);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var shareLockData = new ShareLockData("TARGET_USER_ID", TARGET_USER_ROLE, "BASE64_TARGET_PUBLIC_KEY");
var data = new ShareLockWithContextData("LOCK_ID", shareLockData).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.shareLockWithContextJson(resource, data);
````
</details>

### Revoke access to lock
<details><summary>JVM & Android</summary>

````kotlin
val baseOperation = LockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID")
val revokeAccessToLockOperation = LockOperations.RevokeAccessToLockOperation(baseOperation, listOf("USER_ID"))
sdk.lockOperations().revokeAccessToLock(revokeAccessToLockOperation)
````
>:information_source: In Java, you can use the `revokeAccessToLockAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
let baseOperation = LockOperations.BaseOperation(userId: "USER_ID", userCertificateChain: USER_CERTIFICATE_CHAIN_LIST, userPrivateKey: PRIVATE_KEY, lockId: "LOCK_ID", notBefore: NOT_BEFORE, issuedAt: ISSUED_AT, expiresAt: EXPIRES_AT, jti: UUID)
let revokeAccessToLockOperation = LockOperations.RevokeAccessToLockOperation(baseOperation: baseOperation, users: ["USER_ID"])
sdk.lockOperations().revokeAccessToLock(revokeAccessToLockOperation: revokeAccessToLockOperation)
````
</details>

<details><summary>JS</summary>

````js
const ktList = doordeck.kotlin.collections.KtList;
const userList = ktList.fromJsArray(["USER_ID"]);
const lockOperations = doordeck.com.doordeck.multiplatform.sdk.api.model.LockOperations;
const baseOperation = new lockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID", NOT_BEFORE, ISSUED_AT, EXPIRES_AT, "UUID");
const revokeAccessToLockOperation = new lockOperations.RevokeAccessToLockOperation(baseOperation, userList);
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().revokeAccessToLock(revokeAccessToLockOperation);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
List<string> userList = ["USER_ID"];
var baseOperationData = new BaseOperationData("USER_ID", USER_CERTIFICATE_CHAIN_LIST, "BASE64_PRIVATE_KEY", "LOCK_ID");
var data = new RevokeAccessToLockOperationData(baseOperationData, userList).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.revokeAccessToLockJson(resource, data);
````
</details>

### Revoke access to lock with context
> [!IMPORTANT]  
> This functionality requires you to have previously [set the operation context](#set-operation-context).
<details><summary>JVM & Android</summary>

````kotlin
sdk.lockOperations().revokeAccessToLockWithContext("LOCK_ID", listOf("USER_ID"))
````
>:information_source: In Java, you can use the `revokeAccessToLockWithContextAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
sdk.lockOperations().revokeAccessToLockWithContext(lockId: "LOCK_ID", users: ["USER_ID"])
````
</details>

<details><summary>JS</summary>

````js
const ktList = doordeck.kotlin.collections.KtList;
const userList = ktList.fromJsArray(["USER_ID"]);
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().revokeAccessToLockWithContext("LOCK_ID", userList);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
List<string> userList = ["USER_ID"];
var data = new RevokeAccessToLockWithContextData("LOCK_ID", userList).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.revokeAccessToLockWithContextJson(resource, data);
````
</details>

### Update secure setting unlock duration
<details><summary>JVM & Android</summary>

````kotlin
val baseOperation = LockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID")
val updateSecureSettingUnlockDuration = LockOperations.UpdateSecureSettingUnlockDuration(baseOperation, UNLOCK_DURATION)
sdk.lockOperations().updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration)
````
>:information_source: In Java, you can use the `updateSecureSettingUnlockDurationAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
let baseOperation = LockOperations.BaseOperation(userId: "USER_ID", userCertificateChain: USER_CERTIFICATE_CHAIN_LIST, userPrivateKey: PRIVATE_KEY, lockId: "LOCK_ID", notBefore: NOT_BEFORE, issuedAt: ISSUED_AT, expiresAt: EXPIRES_AT, jti: UUID)
let updateSecureSettingUnlockDuration = LockOperations.UpdateSecureSettingUnlockDuration(baseOperation: baseOperation, unlockDuration: UNLOCK_DURATION)
sdk.lockOperations().updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration: updateSecureSettingUnlockDuration)
````
</details>

<details><summary>JS</summary>

````js
const lockOperations = doordeck.com.doordeck.multiplatform.sdk.api.model.LockOperations;
const baseOperation = new lockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID", NOT_BEFORE, ISSUED_AT, EXPIRES_AT, "UUID");
const updateSecureSettingUnlockDuration = new lockOperations.UpdateSecureSettingUnlockDuration(baseOperation, UNLOCK_DURATION);
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var baseOperationData = new BaseOperationData("USER_ID", USER_CERTIFICATE_CHAIN_LIST, "BASE64_PRIVATE_KEY", "LOCK_ID");
var data = new UpdateSecureSettingUnlockDurationData(baseOperationData, UNLOCK_DURATION).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.updateSecureSettingUnlockDurationJson(resource, data);
````
</details>

### Update secure setting unlock duration with context
> [!IMPORTANT]  
> This functionality requires you to have previously [set the operation context](#set-operation-context).
<details><summary>JVM & Android</summary>

````kotlin
sdk.lockOperations().updateSecureSettingUnlockDurationWithContext("LOCK_ID", UNLOCK_DURATION)
````
>:information_source: In Java, you can use the `updateSecureSettingUnlockDurationWithContextAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
sdk.lockOperations().updateSecureSettingUnlockDurationWithContext(lockId: "LOCK_ID", unlockDuration: UNLOCK_DURATION)
````
</details>

<details><summary>JS</summary>

````js
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().updateSecureSettingUnlockDurationWithContext("LOCK_ID", UNLOCK_DURATION);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var data = new UpdateSecureSettingUnlockDurationWithContextData("LOCK_ID", UNLOCK_DURATION).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.updateSecureSettingUnlockDurationWithContextJson(resource, data);
````
</details>

### Update secure setting unlock between
<details><summary>JVM & Android</summary>

````kotlin
val baseOperation = LockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID")
val unlockBetween = LockOperations.UnlockBetween("START_HH_MM", "END_HH_MM", "TIMEZONE", DAYS_LIST)
val updateSecureSettingUnlockBetween = LockOperations.UpdateSecureSettingUnlockBetween(baseOperation, unlockBetween)
sdk.lockOperations().updateSecureSettingUnlockBetween(updateSecureSettingUnlockBetween)
````
>:information_source: In Java, you can use the `updateSecureSettingUnlockBetweenAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
let baseOperation = LockOperations.BaseOperation(userId: "USER_ID", userCertificateChain: USER_CERTIFICATE_CHAIN_LIST, userPrivateKey: PRIVATE_KEY, lockId: "LOCK_ID", notBefore: NOT_BEFORE, issuedAt: ISSUED_AT, expiresAt: EXPIRES_AT, jti: UUID)
let unlockBetween = LockOperations.UnlockBetween(start: "START_HH_MM", end: "END_HH_MM", timezone: "TIMEZONE", days: ["MONDAY"], exceptions: ["FRIDAY"])
let updateSecureSettingUnlockBetween = LockOperations.UpdateSecureSettingUnlockBetween(baseOperation: baseOperation, unlockBetween: unlockBetween)
sdk.lockOperations().updateSecureSettingUnlockBetween(updateSecureSettingUnlockBetween: updateSecureSettingUnlockBetween)
````
</details>

<details><summary>JS</summary>

````js
const lockOperations = doordeck.com.doordeck.multiplatform.sdk.api.model.LockOperations;
const baseOperation = new lockOperations.BaseOperation("USER_ID", USER_CERTIFICATE_CHAIN_LIST, PRIVATE_KEY, "LOCK_ID", NOT_BEFORE, ISSUED_AT, EXPIRES_AT, "UUID");
const unlockBetween = new lockOperations.UnlockBetween("START_HH_MM", "END_HH_MM", "TIMEZONE", DAYS_LIST, EXCEPTIONS_LIST);
const updateSecureSettingUnlockBetween = new lockOperations.UpdateSecureSettingUnlockBetween(baseOperation, unlockBetween)
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().updateSecureSettingUnlockBetween(updateSecureSettingUnlockBetween);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var baseOperationData = new BaseOperationData("USER_ID", USER_CERTIFICATE_CHAIN_LIST, "BASE64_PRIVATE_KEY", "LOCK_ID");
var unlockBetweenData = new UnlockBetweenData("START_HH_MM", "END_HH_MM", "TIMEZONE", DAYS_LIST);
var data = new UpdateSecureSettingUnlockBetweenData(baseOperationData, unlockBetweenData).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.updateSecureSettingUnlockBetweenJson(resource, data);
````
</details>

### Update secure setting unlock between with context
> [!IMPORTANT]  
> This functionality requires you to have previously [set the operation context](#set-operation-context).
<details><summary>JVM & Android</summary>

````kotlin
val unlockBetween = LockOperations.UnlockBetween("START_HH_MM", "END_HH_MM", "TIMEZONE", DAYS_LIST)
sdk.lockOperations().updateSecureSettingUnlockBetweenWithContext("LOCK_ID", unlockBetween)
````
>:information_source: In Java, you can use the `updateSecureSettingUnlockBetweenWithContextAsync` function, which returns a `CompletableFuture<Void>` instead
</details>

<details><summary>Swift</summary>

````swift
let unlockBetween = LockOperations.UnlockBetween(start: "START_HH_MM", end: "END_HH_MM", timezone: "TIMEZONE", days: ["MONDAY"], exceptions: ["FRIDAY"])
sdk.lockOperations().updateSecureSettingUnlockBetweenWithContext(lockId: "LOCK_ID", unlockBetween: unlockBetween)
````
</details>

<details><summary>JS</summary>

````js
const lockOperations = doordeck.com.doordeck.multiplatform.sdk.api.model.LockOperations;
const unlockBetween = new lockOperations.UnlockBetween("START_HH_MM", "END_HH_MM", "TIMEZONE", DAYS_LIST, EXCEPTIONS_LIST);
await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().updateSecureSettingUnlockBetweenWithContext("LOCK_ID", unlockBetween);
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var unlockBetweenData = new UnlockBetweenData("START_HH_MM", "END_HH_MM", "TIMEZONE", DAYS_LIST);
var data = new UpdateSecureSettingUnlockBetweenWithContextData("LOCK_ID", unlockBetweenData).toData();
symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.updateSecureSettingUnlockBetweenWithContextJson(resource, data);
````
</details>

### Get pinned locks
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.lockOperations().getPinnedLocks()
````
>:information_source: In Java, you can use the `getPinnedLocksAsync` function, which returns a `CompletableFuture<List<LockResponse>>` instead
</details>

<details><summary>Swift</summary>

````swift
let response = sdk.lockOperations().getPinnedLocks()
````
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getPinnedLocks();
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var response = Utils.fromData<List<LockResponse>>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getPinnedLocksJson(resource));
````
</details>

### Get shareable locks
<details><summary>JVM & Android</summary>

````kotlin
val response = sdk.lockOperations().getShareableLocks()
````
>:information_source: In Java, you can use the `getShareableLocksAsync` function, which returns a `CompletableFuture<List<ShareableLockResponse>>` instead
</details>

<details><summary>Swift</summary>

````swift
let response = sdk.lockOperations().getShareableLocks()
````
</details>

<details><summary>JS</summary>

````js
const response = await doordeck.com.doordeck.multiplatform.sdk.api.lockOperations().getShareableLocks();
````
</details>

<details><summary>C#</summary>

````csharp
var resource = symbols->kotlin.root.com.doordeck.multiplatform.sdk.Doordeck.lockOperations(sdk);
var response = Utils.fromData<List<ShareableLockResponse>>(symbols->kotlin.root.com.doordeck.multiplatform.sdk.api.LockOperationsResource.getShareableLocksJson(resource));
````
</details>