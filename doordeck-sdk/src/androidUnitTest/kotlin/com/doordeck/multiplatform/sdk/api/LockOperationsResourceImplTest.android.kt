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
import com.doordeck.multiplatform.sdk.api.model.CapabilityStatus
import com.doordeck.multiplatform.sdk.api.model.CapabilityType
import com.doordeck.multiplatform.sdk.api.model.LockOperations
import com.doordeck.multiplatform.sdk.api.model.UserRole
import com.doordeck.multiplatform.sdk.cache.CapabilityCache
import com.doordeck.multiplatform.sdk.internal.api.LockOperationsResourceImpl
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class LockOperationsResourceImplTest : MockTest() {

    @Test
    fun shouldGetSingleLock() = runTest {
        val response = LockOperationsResourceImpl.getSingleLock(DEFAULT_LOCK_ID)
        assertEquals(LOCK_RESPONSE, response)
    }

    @Test
    fun shouldGetSingleLockAsync() = runTest {
        val response = LockOperationsResourceImpl.getSingleLockAsync(DEFAULT_LOCK_ID).await()
        assertEquals(LOCK_RESPONSE, response)
    }

    @Test
    fun shouldGetLockAuditTrail() = runTest {
        val response = LockOperationsResourceImpl.getLockAuditTrail(DEFAULT_LOCK_ID, 0, 0)
        assertEquals(AUDIT_RESPONSE, response)
    }

    @Test
    fun shouldGetLockAuditTrailAsync() = runTest {
        val response = LockOperationsResourceImpl.getLockAuditTrailAsync(DEFAULT_LOCK_ID, 0, 0).await()
        assertEquals(AUDIT_RESPONSE, response)
    }

    @Test
    fun shouldGetAuditForUser() = runTest {
        val response = LockOperationsResourceImpl.getAuditForUser(DEFAULT_USER_ID, 0, 0)
        assertEquals(AUDIT_RESPONSE, response)
    }

    @Test
    fun shouldGetAuditForUserAsync() = runTest {
        val response = LockOperationsResourceImpl.getAuditForUserAsync(DEFAULT_USER_ID, 0, 0).await()
        assertEquals(AUDIT_RESPONSE, response)
    }

    @Test
    fun shouldGetUsersForLock() = runTest {
        val response = LockOperationsResourceImpl.getUsersForLock(DEFAULT_LOCK_ID)
        assertEquals(USER_LOCK_RESPONSE, response)
    }

    @Test
    fun shouldGetUsersForLockAsync() = runTest {
        val response = LockOperationsResourceImpl.getUsersForLockAsync(DEFAULT_LOCK_ID).await()
        assertEquals(USER_LOCK_RESPONSE, response)
    }

    @Test
    fun shouldGetLocksForUser() = runTest {
        val response = LockOperationsResourceImpl.getLocksForUser(DEFAULT_USER_ID)
        assertEquals(LOCK_USER_RESPONSE, response)
    }

    @Test
    fun shouldGetLocksForUserAsync() = runTest {
        val response = LockOperationsResourceImpl.getLocksForUserAsync(DEFAULT_USER_ID).await()
        assertEquals(LOCK_USER_RESPONSE, response)
    }

    @Test
    fun shouldUpdateLockName() = runTest {
        LockOperationsResourceImpl.updateLockName(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun shouldUpdateLockNameAsync() = runTest {
        LockOperationsResourceImpl.updateLockNameAsync(DEFAULT_LOCK_ID, "").await()
    }

    @Test
    fun shouldUpdateLockFavourite() = runTest {
        LockOperationsResourceImpl.updateLockFavourite(DEFAULT_LOCK_ID, false)
    }

    @Test
    fun shouldUpdateLockFavouriteAsync() = runTest {
        LockOperationsResourceImpl.updateLockFavouriteAsync(DEFAULT_LOCK_ID, false).await()
    }

    @Test
    fun shouldUpdateLockColour() = runTest {
        LockOperationsResourceImpl.updateLockColour(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun shouldUpdateLockColourAsync() = runTest {
        LockOperationsResourceImpl.updateLockColourAsync(DEFAULT_LOCK_ID, "").await()
    }

    @Test
    fun shouldUpdateLockSettingDefaultName() = runTest {
        LockOperationsResourceImpl.updateLockSettingDefaultName(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun shouldUpdateLockSettingDefaultNameAsync() = runTest {
        LockOperationsResourceImpl.updateLockSettingDefaultNameAsync(DEFAULT_LOCK_ID, "").await()
    }

    @Test
    fun shouldSetLockSettingPermittedAddresses() = runTest {
        LockOperationsResourceImpl.setLockSettingPermittedAddresses(DEFAULT_LOCK_ID, listOf("1.1.1.1"))
    }

    @Test
    fun shouldSetLockSettingPermittedAddressesAsync() = runTest {
        LockOperationsResourceImpl.setLockSettingPermittedAddressesAsync(DEFAULT_LOCK_ID, listOf("1.1.1.1")).await()
    }

    @Test
    fun shouldUpdateLockSettingHidden() = runTest {
        LockOperationsResourceImpl.updateLockSettingHidden(DEFAULT_LOCK_ID, true)
    }

    @Test
    fun shouldUpdateLockSettingHiddenAsync() = runTest {
        LockOperationsResourceImpl.updateLockSettingHiddenAsync(DEFAULT_LOCK_ID, true).await()
    }

    @Test
    fun shouldSetLockSettingTimeRestrictions() = runTest {
        LockOperationsResourceImpl.setLockSettingTimeRestrictions(DEFAULT_LOCK_ID, emptyList())
    }

    @Test
    fun shouldSetLockSettingTimeRestrictionsAsync() = runTest {
        LockOperationsResourceImpl.setLockSettingTimeRestrictionsAsync(DEFAULT_LOCK_ID, emptyList()).await()
    }

    @Test
    fun shouldUpdateLockSettingLocationRestrictions() = runTest {
        LockOperationsResourceImpl.updateLockSettingLocationRestrictions(DEFAULT_LOCK_ID, null)
    }

    @Test
    fun shouldUpdateLockSettingLocationRestrictionsAsync() = runTest {
        LockOperationsResourceImpl.updateLockSettingLocationRestrictionsAsync(DEFAULT_LOCK_ID, null).await()
    }

    @Test
    fun shouldGetUserPublicKey() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKey(DEFAULT_USER_EMAIL)
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyAsync() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyAsync(DEFAULT_USER_EMAIL).await()
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByEmail() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByEmail("")
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByEmailAsync() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByEmailAsync("").await()
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByTelephone() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByTelephone("")
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByTelephoneAsync() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByTelephoneAsync("").await()
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKey() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByLocalKey("")
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeyAsync() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByLocalKeyAsync("").await()
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKey() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByForeignKey("")
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKeyAsync() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByForeignKeyAsync("").await()
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByIdentity() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByIdentity("")
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByIdentityAsync() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByIdentityAsync("").await()
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByEmails() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByEmails(listOf("", ""))
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByEmailsAsync() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByEmailsAsync(listOf("", "")).await()
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByTelephones() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByTelephones(listOf("", ""))
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByTelephonesAsync() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByTelephonesAsync(listOf("", "")).await()
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeys() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByLocalKeys(listOf("", ""))
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeysAsync() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByLocalKeysAsync(listOf("", "")).await()
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKeys() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByForeignKeys(listOf("", ""))
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKeysAsync() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByForeignKeysAsync(listOf("", "")).await()
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldUnlockUsingContext() = runTest {
        LockOperationsResourceImpl.unlock(LockOperations.UnlockOperation(LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID)))
    }

    @Test
    fun shouldUnlockUsingContextAsync() = runTest {
        LockOperationsResourceImpl.unlockAsync(LockOperations.UnlockOperation(LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID))).await()
    }

    @Test
    fun shouldUnlock() = runTest {
        LockOperationsResourceImpl.unlock(LockOperations.UnlockOperation(LockOperations.BaseOperation("userId", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID)))
    }

    @Test
    fun shouldUnlockAsync() = runTest {
        LockOperationsResourceImpl.unlockAsync(LockOperations.UnlockOperation(LockOperations.BaseOperation("userId", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID))).await()
    }

    @Test
    fun shouldShareLockUsingContext() = runTest {
        LockOperationsResourceImpl.shareLock(
            LockOperations.ShareLockOperation(
                baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
                shareLock = LockOperations.ShareLock("", UserRole.USER, byteArrayOf())
            ))
    }

    @Test
    fun shouldShareLockUsingContextAsync() = runTest {
        LockOperationsResourceImpl.shareLockAsync(
            LockOperations.ShareLockOperation(
                baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
                shareLock = LockOperations.ShareLock("", UserRole.USER, byteArrayOf())
            )).await()
    }

    @Test
    fun shouldBatchShareLockUsingContext() = runTest {
        CapabilityCache.put(DEFAULT_LOCK_ID, mapOf(CapabilityType.BATCH_SHARING_25 to CapabilityStatus.SUPPORTED))
        LockOperationsResourceImpl.batchShareLock(
            LockOperations.BatchShareLockOperation(
                baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
                users = listOf(LockOperations.ShareLock("", UserRole.USER, byteArrayOf()))
            )
        )
    }

    @Test
    fun shouldBatchShareLockUsingContextAsync() = runTest {
        CapabilityCache.put(DEFAULT_LOCK_ID, mapOf(CapabilityType.BATCH_SHARING_25 to CapabilityStatus.SUPPORTED))
        LockOperationsResourceImpl.batchShareLockAsync(
            LockOperations.BatchShareLockOperation(
                baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
                users = listOf(LockOperations.ShareLock("", UserRole.USER, byteArrayOf()))
            )
        ).await()
    }

    @Test
    fun shouldShareLock() = runTest {
        LockOperationsResourceImpl.shareLock(
            LockOperations.ShareLockOperation(
                baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
                shareLock = LockOperations.ShareLock("", UserRole.USER, byteArrayOf())
            )
        )
    }

    @Test
    fun shouldShareLockAsync() = runTest {
        LockOperationsResourceImpl.shareLockAsync(
            LockOperations.ShareLockOperation(
                baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
                shareLock = LockOperations.ShareLock("", UserRole.USER, byteArrayOf())
            )).await()
    }

    @Test
    fun shouldBatchShareLock() = runTest {
        CapabilityCache.put(DEFAULT_LOCK_ID, mapOf(CapabilityType.BATCH_SHARING_25 to CapabilityStatus.SUPPORTED))
        LockOperationsResourceImpl.batchShareLock(
            LockOperations.BatchShareLockOperation(
                baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
                users = listOf(LockOperations.ShareLock("", UserRole.USER, byteArrayOf()))
            )
        )
    }

    @Test
    fun shouldBatchShareLockAsync() = runTest {
        CapabilityCache.put(DEFAULT_LOCK_ID, mapOf(CapabilityType.BATCH_SHARING_25 to CapabilityStatus.SUPPORTED))
        LockOperationsResourceImpl.batchShareLockAsync(
            LockOperations.BatchShareLockOperation(
                baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
                users = listOf(LockOperations.ShareLock("", UserRole.USER, byteArrayOf()))
            )
        ).await()
    }

    @Test
    fun shouldRevokeAccessToLockUsingContext() = runTest {
        LockOperationsResourceImpl.revokeAccessToLock(LockOperations.RevokeAccessToLockOperation(
            baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
            users = emptyList()
        ))
    }

    @Test
    fun shouldRevokeAccessToLockUsingContextAsync() = runTest {
        LockOperationsResourceImpl.revokeAccessToLockAsync(LockOperations.RevokeAccessToLockOperation(
            baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
            users = emptyList()
        )).await()
    }

    @Test
    fun shouldRevokeAccessToLock() = runTest {
        LockOperationsResourceImpl.revokeAccessToLock(LockOperations.RevokeAccessToLockOperation(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            users = emptyList()
        ))
    }

    @Test
    fun shouldRevokeAccessToLockAsync() = runTest {
        LockOperationsResourceImpl.revokeAccessToLockAsync(LockOperations.RevokeAccessToLockOperation(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            users = emptyList()
        )).await()
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDurationUsingContext() = runTest {
        LockOperationsResourceImpl.updateSecureSettingUnlockDuration(LockOperations.UpdateSecureSettingUnlockDuration(
            baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
            unlockDuration = 0
        ))
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDurationUsingContextAsync() = runTest {
        LockOperationsResourceImpl.updateSecureSettingUnlockDurationAsync(LockOperations.UpdateSecureSettingUnlockDuration(
            baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
            unlockDuration = 0
        )).await()
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDuration() = runTest {
        LockOperationsResourceImpl.updateSecureSettingUnlockDuration(LockOperations.UpdateSecureSettingUnlockDuration(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            unlockDuration = 0
        ))
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDurationAsync() = runTest {
        LockOperationsResourceImpl.updateSecureSettingUnlockDurationAsync(LockOperations.UpdateSecureSettingUnlockDuration(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            unlockDuration = 0
        )).await()
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetweenUsingContext() = runTest {
        LockOperationsResourceImpl.updateSecureSettingUnlockBetween(LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
            unlockBetween = null
        ))
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetweenUsingContextAsync() = runTest {
        LockOperationsResourceImpl.updateSecureSettingUnlockBetweenAsync(LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
            unlockBetween = null
        )).await()
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetween() = runTest {
        LockOperationsResourceImpl.updateSecureSettingUnlockBetween(LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            unlockBetween = null
        ))
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetweenAsync() = runTest {
        LockOperationsResourceImpl.updateSecureSettingUnlockBetweenAsync(LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            unlockBetween = null
        )).await()
    }

    @Test
    fun shouldGetPinnedLocks() = runTest {
        val response = LockOperationsResourceImpl.getPinnedLocks()
        assertEquals(PINNED_LOCKS_RESPONSE, response)
    }

    @Test
    fun shouldGetPinnedLocksAsync() = runTest {
        val response = LockOperationsResourceImpl.getPinnedLocksAsync().await()
        assertEquals(PINNED_LOCKS_RESPONSE, response)
    }

    @Test
    fun shouldGetShareableLocks() = runTest {
        val response = LockOperationsResourceImpl.getShareableLocks()
        assertEquals(SHAREABLE_LOCKS_RESPONSE, response)
    }

    @Test
    fun shouldGetShareableLocksAsync() = runTest {
        val response = LockOperationsResourceImpl.getShareableLocksAsync().await()
        assertEquals(SHAREABLE_LOCKS_RESPONSE, response)
    }
}