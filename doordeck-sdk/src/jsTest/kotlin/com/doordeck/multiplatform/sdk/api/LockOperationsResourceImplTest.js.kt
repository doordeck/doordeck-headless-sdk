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
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class LockOperationsResourceImplTest : MockTest() {

    @Test
    fun shouldGetSingleLock() = runTest {
        val response = LockOperationsResourceImpl.getSingleLock(DEFAULT_LOCK_ID).await()
        assertEquals(LOCK_RESPONSE, response)
    }

    @Test
    fun shouldGetLockAuditTrail() = runTest {
        val response = LockOperationsResourceImpl.getLockAuditTrail(DEFAULT_LOCK_ID, 0, 0).await()
        assertEquals(AUDIT_RESPONSE, response)
    }

    @Test
    fun shouldGetAuditForUser() = runTest {
        val response = LockOperationsResourceImpl.getAuditForUser(DEFAULT_USER_ID, 0, 0).await()
        assertEquals(AUDIT_RESPONSE, response)
    }

    @Test
    fun shouldGetUsersForLock() = runTest {
        val response = LockOperationsResourceImpl.getUsersForLock(DEFAULT_LOCK_ID).await()
        assertEquals(USER_LOCK_RESPONSE, response)
    }

    @Test
    fun shouldGetLocksForUser() = runTest {
        val response = LockOperationsResourceImpl.getLocksForUser(DEFAULT_USER_ID).await()
        assertEquals(LOCK_USER_RESPONSE, response)
    }

    @Test
    fun shouldUpdateLockName() = runTest {
        LockOperationsResourceImpl.updateLockName(DEFAULT_LOCK_ID, "").await()
    }

    @Test
    fun shouldUpdateLockFavourite() = runTest {
        LockOperationsResourceImpl.updateLockFavourite(DEFAULT_LOCK_ID, false).await()
    }

    @Test
    fun shouldUpdateLockColour() = runTest {
        LockOperationsResourceImpl.updateLockColour(DEFAULT_LOCK_ID, "").await()
    }

    @Test
    fun shouldUpdateLockSettingDefaultName() = runTest {
        LockOperationsResourceImpl.updateLockSettingDefaultName(DEFAULT_LOCK_ID, "").await()
    }

    @Test
    fun shouldSetLockSettingPermittedAddresses() = runTest {
        LockOperationsResourceImpl.setLockSettingPermittedAddresses(DEFAULT_LOCK_ID, listOf("1.1.1.1")).await()
    }

    @Test
    fun shouldUpdateLockSettingHidden() = runTest {
        LockOperationsResourceImpl.updateLockSettingHidden(DEFAULT_LOCK_ID, true).await()
    }

    @Test
    fun shouldSetLockSettingTimeRestrictions() = runTest {
        LockOperationsResourceImpl.setLockSettingTimeRestrictions(DEFAULT_LOCK_ID, emptyList()).await()
    }

    @Test
    fun shouldUpdateLockSettingLocationRestrictions() = runTest {
        LockOperationsResourceImpl.updateLockSettingLocationRestrictions(DEFAULT_LOCK_ID, null).await()
    }

    @Test
    fun shouldGetUserPublicKey() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKey(DEFAULT_USER_EMAIL).await()
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByEmail() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByEmail("").await()
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByTelephone() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByTelephone("").await()
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKey() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByLocalKey("").await()
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKey() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByForeignKey("").await()
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByIdentity() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByIdentity("").await()
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByEmails() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByEmails(listOf("", "")).await()
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByTelephones() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByTelephones(listOf("", "")).await()
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeys() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByLocalKeys(listOf("", "")).await()
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKeys() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByForeignKeys(listOf("", "")).await()
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldUnlockUsingContext() = runTest {
        LockOperationsResourceImpl.unlock(LockOperations.UnlockOperation(LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID))).await()
    }

    @Test
    fun shouldUnlock() = runTest {
        LockOperationsResourceImpl.unlock(LockOperations.UnlockOperation(LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID))).await()
    }

    @Test
    fun shouldShareLockUsingContext() = runTest {
        LockOperationsResourceImpl.shareLock(
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
        ).await()
    }

    @Test
    fun shouldShareLock() = runTest {
        LockOperationsResourceImpl.shareLock(
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
        ).await()
    }

    @Test
    fun shouldRevokeAccessToLockUsingContext() = runTest {
        LockOperationsResourceImpl.revokeAccessToLock(LockOperations.RevokeAccessToLockOperation(
            baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
            users = emptyList()
        )).await()
    }

    @Test
    fun shouldRevokeAccessToLock() = runTest {
        LockOperationsResourceImpl.revokeAccessToLock(LockOperations.RevokeAccessToLockOperation(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            users = emptyList()
        )).await()
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDurationUsingContext() = runTest {
        LockOperationsResourceImpl.updateSecureSettingUnlockDuration(LockOperations.UpdateSecureSettingUnlockDuration(
            baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
            unlockDuration = 0
        )).await()
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDuration() = runTest {
        LockOperationsResourceImpl.updateSecureSettingUnlockDuration(LockOperations.UpdateSecureSettingUnlockDuration(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            unlockDuration = 0
        )).await()
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetweenUsingContext() = runTest {
        LockOperationsResourceImpl.updateSecureSettingUnlockBetween(LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
            unlockBetween = null
        )).await()
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetween() = runTest {
        LockOperationsResourceImpl.updateSecureSettingUnlockBetween(LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            unlockBetween = null
        )).await()
    }

    @Test
    fun shouldGetPinnedLocks() = runTest {
        val response = LockOperationsResourceImpl.getPinnedLocks().await()
        assertEquals(PINNED_LOCKS_RESPONSE, response)
    }

    @Test
    fun shouldGetShareableLocks() = runTest {
        val response = LockOperationsResourceImpl.getShareableLocks().await()
        assertEquals(SHAREABLE_LOCKS_RESPONSE, response)
    }
}