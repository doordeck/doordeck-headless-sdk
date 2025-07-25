package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.AUDIT_RESPONSE
import com.doordeck.multiplatform.sdk.BATCH_USER_PUBLIC_KEY_RESPONSE
import com.doordeck.multiplatform.sdk.LOCK_RESPONSE
import com.doordeck.multiplatform.sdk.LOCK_USER_RESPONSE
import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.PINNED_LOCKS_RESPONSE
import com.doordeck.multiplatform.sdk.SHAREABLE_LOCKS_RESPONSE
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_LOCK_ID
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_USER_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.USER_LOCK_RESPONSE
import com.doordeck.multiplatform.sdk.USER_PUBLIC_KEY_RESPONSE
import com.doordeck.multiplatform.sdk.cache.CapabilityCache
import com.doordeck.multiplatform.sdk.model.data.BasicLockOperations
import com.doordeck.multiplatform.sdk.model.common.CapabilityStatus
import com.doordeck.multiplatform.sdk.model.common.CapabilityType
import com.doordeck.multiplatform.sdk.model.common.UserRole
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.toUUID
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Clock
import kotlin.time.toJavaInstant

class LockOperationsApiTest : MockTest() {

    @Test
    fun shouldGetSingleLock() = runTest {
        val response = LockOperationsApi.getSingleLock(DEFAULT_LOCK_ID.toUUID())
        assertEquals(LOCK_RESPONSE, response)
    }

    @Test
    fun shouldGetSingleLockAsync() = runTest {
        val response = LockOperationsApi.getSingleLockAsync(DEFAULT_LOCK_ID.toUUID()).await()
        assertEquals(LOCK_RESPONSE, response)
    }

    @Test
    fun shouldGetLockAuditTrail() = runTest {
        val response = LockOperationsApi.getLockAuditTrail(DEFAULT_LOCK_ID.toUUID(), Clock.System.now(), Clock.System.now())
        assertEquals(AUDIT_RESPONSE, response)
    }

    @Test
    fun shouldGetLockAuditTrailAsync() = runTest {
        val response = LockOperationsApi.getLockAuditTrailAsync(DEFAULT_LOCK_ID.toUUID(), Clock.System.now().toJavaInstant(), Clock.System.now().toJavaInstant()).await()
        assertEquals(AUDIT_RESPONSE, response)
    }

    @Test
    fun shouldGetAuditForUser() = runTest {
        val response = LockOperationsApi.getAuditForUser(DEFAULT_USER_ID.toUUID(), Clock.System.now(), Clock.System.now())
        assertEquals(AUDIT_RESPONSE, response)
    }

    @Test
    fun shouldGetAuditForUserAsync() = runTest {
        val response = LockOperationsApi.getAuditForUserAsync(DEFAULT_USER_ID.toUUID(), Clock.System.now().toJavaInstant(), Clock.System.now().toJavaInstant()).await()
        assertEquals(AUDIT_RESPONSE, response)
    }

    @Test
    fun shouldGetUsersForLock() = runTest {
        val response = LockOperationsApi.getUsersForLock(DEFAULT_LOCK_ID.toUUID())
        assertEquals(USER_LOCK_RESPONSE, response)
    }

    @Test
    fun shouldGetUsersForLockAsync() = runTest {
        val response = LockOperationsApi.getUsersForLockAsync(DEFAULT_LOCK_ID.toUUID()).await()
        assertEquals(USER_LOCK_RESPONSE, response)
    }

    @Test
    fun shouldGetLocksForUser() = runTest {
        val response = LockOperationsApi.getLocksForUser(DEFAULT_USER_ID.toUUID())
        assertEquals(LOCK_USER_RESPONSE, response)
    }

    @Test
    fun shouldGetLocksForUserAsync() = runTest {
        val response = LockOperationsApi.getLocksForUserAsync(DEFAULT_USER_ID.toUUID()).await()
        assertEquals(LOCK_USER_RESPONSE, response)
    }

    @Test
    fun shouldUpdateLockName() = runTest {
        LockOperationsApi.updateLockName(DEFAULT_LOCK_ID.toUUID(), "")
    }

    @Test
    fun shouldUpdateLockNameAsync() = runTest {
        LockOperationsApi.updateLockNameAsync(DEFAULT_LOCK_ID.toUUID(), "").await()
    }

    @Test
    fun shouldUpdateLockFavourite() = runTest {
        LockOperationsApi.updateLockFavourite(DEFAULT_LOCK_ID.toUUID(), false)
    }

    @Test
    fun shouldUpdateLockFavouriteAsync() = runTest {
        LockOperationsApi.updateLockFavouriteAsync(DEFAULT_LOCK_ID.toUUID(), false).await()
    }

    @Test
    fun shouldUpdateLockColour() = runTest {
        LockOperationsApi.updateLockColour(DEFAULT_LOCK_ID.toUUID(), "")
    }

    @Test
    fun shouldUpdateLockColourAsync() = runTest {
        LockOperationsApi.updateLockColourAsync(DEFAULT_LOCK_ID.toUUID(), "").await()
    }

    @Test
    fun shouldUpdateLockSettingDefaultName() = runTest {
        LockOperationsApi.updateLockSettingDefaultName(DEFAULT_LOCK_ID.toUUID(), "")
    }

    @Test
    fun shouldUpdateLockSettingDefaultNameAsync() = runTest {
        LockOperationsApi.updateLockSettingDefaultNameAsync(DEFAULT_LOCK_ID.toUUID(), "").await()
    }

    @Test
    fun shouldSetLockSettingPermittedAddresses() = runTest {
        LockOperationsApi.setLockSettingPermittedAddresses(DEFAULT_LOCK_ID.toUUID(), listOf("1.1.1.1"))
    }

    @Test
    fun shouldSetLockSettingPermittedAddressesAsync() = runTest {
        LockOperationsApi.setLockSettingPermittedAddressesAsync(DEFAULT_LOCK_ID.toUUID(), listOf("1.1.1.1")).await()
    }

    @Test
    fun shouldUpdateLockSettingHidden() = runTest {
        LockOperationsApi.updateLockSettingHidden(DEFAULT_LOCK_ID.toUUID(), true)
    }

    @Test
    fun shouldUpdateLockSettingHiddenAsync() = runTest {
        LockOperationsApi.updateLockSettingHiddenAsync(DEFAULT_LOCK_ID.toUUID(), true).await()
    }

    @Test
    fun shouldSetLockSettingTimeRestrictions() = runTest {
        LockOperationsApi.setLockSettingTimeRestrictions(DEFAULT_LOCK_ID.toUUID(), emptyList())
    }

    @Test
    fun shouldSetLockSettingTimeRestrictionsAsync() = runTest {
        LockOperationsApi.setLockSettingTimeRestrictionsAsync(DEFAULT_LOCK_ID.toUUID(), emptyList()).await()
    }

    @Test
    fun shouldUpdateLockSettingLocationRestrictions() = runTest {
        LockOperationsApi.updateLockSettingLocationRestrictions(DEFAULT_LOCK_ID.toUUID(), null)
    }

    @Test
    fun shouldUpdateLockSettingLocationRestrictionsAsync() = runTest {
        LockOperationsApi.updateLockSettingLocationRestrictionsAsync(DEFAULT_LOCK_ID.toUUID(), null).await()
    }

    @Test
    fun shouldGetUserPublicKey() = runTest {
        val response = LockOperationsApi.getUserPublicKey(DEFAULT_USER_EMAIL)
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyAsync() = runTest {
        val response = LockOperationsApi.getUserPublicKeyAsync(DEFAULT_USER_EMAIL).await()
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByEmail() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByEmail("")
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByEmailAsync() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByEmailAsync("").await()
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByTelephone() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByTelephone("")
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByTelephoneAsync() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByTelephoneAsync("").await()
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKey() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByLocalKey("")
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeyAsync() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByLocalKeyAsync("").await()
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKey() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByForeignKey("")
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKeyAsync() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByForeignKeyAsync("").await()
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByIdentity() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByIdentity("")
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByIdentityAsync() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByIdentityAsync("").await()
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }
    
    @Test
    fun shouldGetUserPublicKeyByEmails() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByEmails(listOf("", ""))
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByEmailsAsync() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByEmailsAsync(listOf("", "")).await()
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByTelephones() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByTelephones(listOf("", ""))
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByTelephonesAsync() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByTelephonesAsync(listOf("", "")).await()
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeys() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByLocalKeys(listOf("", ""))
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeysAsync() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByLocalKeysAsync(listOf("", "")).await()
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKeys() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByForeignKeys(listOf("", ""))
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKeysAsync() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByForeignKeysAsync(listOf("", "")).await()
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldUnlockUsingContext() = runTest {
        LockOperationsApi.unlock(BasicLockOperations.BasicUnlockOperation(BasicLockOperations.BasicBaseOperation(lockId = DEFAULT_LOCK_ID)))
    }

    @Test
    fun shouldUnlockUsingContextAsync() = runTest {
        LockOperationsApi.unlockAsync(BasicLockOperations.BasicUnlockOperation(BasicLockOperations.BasicBaseOperation(lockId = DEFAULT_LOCK_ID))).await()
    }

    @Test
    fun shouldUnlock() = runTest {
        LockOperationsApi.unlock(BasicLockOperations.BasicUnlockOperation(BasicLockOperations.BasicBaseOperation("userId", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID)))
    }

    @Test
    fun shouldUnlockAsync() = runTest {
        LockOperationsApi.unlockAsync(BasicLockOperations.BasicUnlockOperation(BasicLockOperations.BasicBaseOperation("userId", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID))).await()
    }

    @Test
    fun shouldShareLockUsingContext() = runTest {
        LockOperationsApi.shareLock(
            BasicLockOperations.BasicShareLockOperation(
                baseOperation = BasicLockOperations.BasicBaseOperation(lockId = DEFAULT_LOCK_ID),
                shareLock = BasicLockOperations.BasicShareLock("", UserRole.USER, byteArrayOf())
            ))
    }

    @Test
    fun shouldShareLockUsingContextAsync() = runTest {
        LockOperationsApi.shareLockAsync(
            BasicLockOperations.BasicShareLockOperation(
                baseOperation = BasicLockOperations.BasicBaseOperation(lockId = DEFAULT_LOCK_ID),
                shareLock = BasicLockOperations.BasicShareLock("", UserRole.USER, byteArrayOf())
            )).await()
    }

    @Test
    fun shouldBatchShareLockUsingContext() = runTest {
        CapabilityCache.put(DEFAULT_LOCK_ID, mapOf(CapabilityType.BATCH_SHARING_25 to CapabilityStatus.SUPPORTED))
        LockOperationsApi.batchShareLock(
            BasicLockOperations.BasicBatchShareLockOperation(
                baseOperation = BasicLockOperations.BasicBaseOperation(lockId = DEFAULT_LOCK_ID),
                users = listOf(BasicLockOperations.BasicShareLock("", UserRole.USER, byteArrayOf()))
            )
        )
    }

    @Test
    fun shouldBatchShareLockUsingContextAsync() = runTest {
        CapabilityCache.put(DEFAULT_LOCK_ID, mapOf(CapabilityType.BATCH_SHARING_25 to CapabilityStatus.SUPPORTED))
        LockOperationsApi.batchShareLockAsync(
            BasicLockOperations.BasicBatchShareLockOperation(
                baseOperation = BasicLockOperations.BasicBaseOperation(lockId = DEFAULT_LOCK_ID),
                users = listOf(BasicLockOperations.BasicShareLock("", UserRole.USER, byteArrayOf()))
            )
        ).await()
    }

    @Test
    fun shouldShareLock() = runTest {
        LockOperationsApi.shareLock(
            BasicLockOperations.BasicShareLockOperation(
                baseOperation = BasicLockOperations.BasicBaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
                shareLock = BasicLockOperations.BasicShareLock("", UserRole.USER, byteArrayOf())
        ))
    }

    @Test
    fun shouldShareLockAsync() = runTest {
        LockOperationsApi.shareLockAsync(
            BasicLockOperations.BasicShareLockOperation(
                baseOperation = BasicLockOperations.BasicBaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
                shareLock = BasicLockOperations.BasicShareLock("", UserRole.USER, byteArrayOf())
            )).await()
    }

    @Test
    fun shouldBatchShareLock() = runTest {
        CapabilityCache.put(DEFAULT_LOCK_ID, mapOf(CapabilityType.BATCH_SHARING_25 to CapabilityStatus.SUPPORTED))
        LockOperationsApi.batchShareLock(
            BasicLockOperations.BasicBatchShareLockOperation(
                baseOperation = BasicLockOperations.BasicBaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
                users = listOf(BasicLockOperations.BasicShareLock("", UserRole.USER, byteArrayOf()))
            )
        )
    }

    @Test
    fun shouldBatchShareLockAsync() = runTest {
        CapabilityCache.put(DEFAULT_LOCK_ID, mapOf(CapabilityType.BATCH_SHARING_25 to CapabilityStatus.SUPPORTED))
        LockOperationsApi.batchShareLockAsync(
            BasicLockOperations.BasicBatchShareLockOperation(
                baseOperation = BasicLockOperations.BasicBaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
                users = listOf(BasicLockOperations.BasicShareLock("", UserRole.USER, byteArrayOf()))
            )
        ).await()
    }

    @Test
    fun shouldRevokeAccessToLockUsingContext() = runTest {
        LockOperationsApi.revokeAccessToLock(
            BasicLockOperations.BasicRevokeAccessToLockOperation(
            baseOperation = BasicLockOperations.BasicBaseOperation(lockId = DEFAULT_LOCK_ID),
            users = emptyList()
        ))
    }

    @Test
    fun shouldRevokeAccessToLockUsingContextAsync() = runTest {
        LockOperationsApi.revokeAccessToLockAsync(
            BasicLockOperations.BasicRevokeAccessToLockOperation(
            baseOperation = BasicLockOperations.BasicBaseOperation(lockId = DEFAULT_LOCK_ID),
            users = emptyList()
        )).await()
    }

    @Test
    fun shouldRevokeAccessToLock() = runTest {
        LockOperationsApi.revokeAccessToLock(
            BasicLockOperations.BasicRevokeAccessToLockOperation(
            baseOperation = BasicLockOperations.BasicBaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            users = emptyList()
        ))
    }

    @Test
    fun shouldRevokeAccessToLockAsync() = runTest {
        LockOperationsApi.revokeAccessToLockAsync(
            BasicLockOperations.BasicRevokeAccessToLockOperation(
            baseOperation = BasicLockOperations.BasicBaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            users = emptyList()
        )).await()
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDurationUsingContext() = runTest {
        LockOperationsApi.updateSecureSettingUnlockDuration(
            BasicLockOperations.BasicUpdateSecureSettingUnlockDuration(
            baseOperation = BasicLockOperations.BasicBaseOperation(lockId = DEFAULT_LOCK_ID),
            unlockDuration = 0
        ))
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDurationUsingContextAsync() = runTest {
        LockOperationsApi.updateSecureSettingUnlockDurationAsync(
            BasicLockOperations.BasicUpdateSecureSettingUnlockDuration(
            baseOperation = BasicLockOperations.BasicBaseOperation(lockId = DEFAULT_LOCK_ID),
            unlockDuration = 0
        )).await()
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDuration() = runTest {
        LockOperationsApi.updateSecureSettingUnlockDuration(
            BasicLockOperations.BasicUpdateSecureSettingUnlockDuration(
            baseOperation = BasicLockOperations.BasicBaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            unlockDuration = 0
        ))
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDurationAsync() = runTest {
        LockOperationsApi.updateSecureSettingUnlockDurationAsync(
            BasicLockOperations.BasicUpdateSecureSettingUnlockDuration(
            baseOperation = BasicLockOperations.BasicBaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            unlockDuration = 0
        )).await()
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetweenUsingContext() = runTest {
        LockOperationsApi.updateSecureSettingUnlockBetween(
            BasicLockOperations.BasicUpdateSecureSettingUnlockBetween(
            baseOperation = BasicLockOperations.BasicBaseOperation(lockId = DEFAULT_LOCK_ID),
            unlockBetween = null
        ))
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetweenUsingContextAsync() = runTest {
        LockOperationsApi.updateSecureSettingUnlockBetweenAsync(
            BasicLockOperations.BasicUpdateSecureSettingUnlockBetween(
            baseOperation = BasicLockOperations.BasicBaseOperation(lockId = DEFAULT_LOCK_ID),
            unlockBetween = null
        )).await()
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetween() = runTest {
        LockOperationsApi.updateSecureSettingUnlockBetween(
            BasicLockOperations.BasicUpdateSecureSettingUnlockBetween(
            baseOperation = BasicLockOperations.BasicBaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            unlockBetween = null
        ))
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetweenAsync() = runTest {
        LockOperationsApi.updateSecureSettingUnlockBetweenAsync(
            BasicLockOperations.BasicUpdateSecureSettingUnlockBetween(
            baseOperation = BasicLockOperations.BasicBaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            unlockBetween = null
        )).await()
    }

    @Test
    fun shouldGetPinnedLocks() = runTest {
        val response = LockOperationsApi.getPinnedLocks()
        assertEquals(PINNED_LOCKS_RESPONSE, response)
    }

    @Test
    fun shouldGetPinnedLocksAsync() = runTest {
        val response = LockOperationsApi.getPinnedLocksAsync().await()
        assertEquals(PINNED_LOCKS_RESPONSE, response)
    }

    @Test
    fun shouldGetShareableLocks() = runTest {
        val response = LockOperationsApi.getShareableLocks()
        assertEquals(SHAREABLE_LOCKS_RESPONSE, response)
    }

    @Test
    fun shouldGetShareableLocksAsync() = runTest {
        val response = LockOperationsApi.getShareableLocksAsync().await()
        assertEquals(SHAREABLE_LOCKS_RESPONSE, response)
    }
}