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
import com.doordeck.multiplatform.sdk.model.LockOperations
import com.doordeck.multiplatform.sdk.model.common.CapabilityStatus
import com.doordeck.multiplatform.sdk.model.common.CapabilityType
import com.doordeck.multiplatform.sdk.model.common.UserRole
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class LockOperationsApiTest : MockTest() {

    @Test
    fun shouldGetSingleLock() = runTest {
        val response = LockOperationsApi.getSingleLock(DEFAULT_LOCK_ID).await()
        assertEquals(LOCK_RESPONSE, response)
    }

    @Test
    fun shouldGetLockAuditTrail() = runTest {
        val response = LockOperationsApi.getLockAuditTrail(DEFAULT_LOCK_ID, 0, 0).await()
        assertEquals(AUDIT_RESPONSE, response)
    }

    @Test
    fun shouldGetAuditForUser() = runTest {
        val response = LockOperationsApi.getAuditForUser(DEFAULT_USER_ID, 0, 0).await()
        assertEquals(AUDIT_RESPONSE, response)
    }

    @Test
    fun shouldGetUsersForLock() = runTest {
        val response = LockOperationsApi.getUsersForLock(DEFAULT_LOCK_ID).await()
        assertEquals(USER_LOCK_RESPONSE, response)
    }

    @Test
    fun shouldGetLocksForUser() = runTest {
        val response = LockOperationsApi.getLocksForUser(DEFAULT_USER_ID).await()
        assertEquals(LOCK_USER_RESPONSE, response)
    }

    @Test
    fun shouldUpdateLockName() = runTest {
        LockOperationsApi.updateLockName(DEFAULT_LOCK_ID, "").await()
    }

    @Test
    fun shouldUpdateLockFavourite() = runTest {
        LockOperationsApi.updateLockFavourite(DEFAULT_LOCK_ID, false).await()
    }

    @Test
    fun shouldUpdateLockColour() = runTest {
        LockOperationsApi.updateLockColour(DEFAULT_LOCK_ID, "").await()
    }

    @Test
    fun shouldUpdateLockSettingDefaultName() = runTest {
        LockOperationsApi.updateLockSettingDefaultName(DEFAULT_LOCK_ID, "").await()
    }

    @Test
    fun shouldSetLockSettingPermittedAddresses() = runTest {
        LockOperationsApi.setLockSettingPermittedAddresses(DEFAULT_LOCK_ID, listOf("1.1.1.1")).await()
    }

    @Test
    fun shouldUpdateLockSettingHidden() = runTest {
        LockOperationsApi.updateLockSettingHidden(DEFAULT_LOCK_ID, true).await()
    }

    @Test
    fun shouldSetLockSettingTimeRestrictions() = runTest {
        LockOperationsApi.setLockSettingTimeRestrictions(DEFAULT_LOCK_ID, emptyList()).await()
    }

    @Test
    fun shouldUpdateLockSettingLocationRestrictions() = runTest {
        LockOperationsApi.updateLockSettingLocationRestrictions(DEFAULT_LOCK_ID, null).await()
    }

    @Test
    fun shouldGetUserPublicKey() = runTest {
        val response = LockOperationsApi.getUserPublicKey(DEFAULT_USER_EMAIL).await()
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByEmail() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByEmail("").await()
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByTelephone() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByTelephone("").await()
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKey() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByLocalKey("").await()
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKey() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByForeignKey("").await()
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByIdentity() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByIdentity("").await()
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByEmails() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByEmails(listOf("", "")).await()
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByTelephones() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByTelephones(listOf("", "")).await()
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeys() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByLocalKeys(listOf("", "")).await()
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKeys() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByForeignKeys(listOf("", "")).await()
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldUnlockUsingContext() = runTest {
        LockOperationsApi.unlock(LockOperations.UnlockOperation(LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID))).await()
    }

    @Test
    fun shouldUnlock() = runTest {
        LockOperationsApi.unlock(LockOperations.UnlockOperation(LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID))).await()
    }

    @Test
    fun shouldShareLockUsingContext() = runTest {
        LockOperationsApi.shareLock(
            LockOperations.ShareLockOperation(
                baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
                shareLock = LockOperations.ShareLock("", UserRole.USER, byteArrayOf())
            )).await()
    }

    @Test
    fun shouldBatchShareLockUsingContext() = runTest {
        CapabilityCache.put(DEFAULT_LOCK_ID, mapOf(CapabilityType.BATCH_SHARING_25 to CapabilityStatus.SUPPORTED))
        LockOperationsApi.batchShareLock(
            LockOperations.BatchShareLockOperation(
                baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
                users = listOf(LockOperations.ShareLock("", UserRole.USER, byteArrayOf()))
            )
        ).await()
    }

    @Test
    fun shouldShareLock() = runTest {
        LockOperationsApi.shareLock(
            LockOperations.ShareLockOperation(
                baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
                shareLock = LockOperations.ShareLock("", UserRole.USER, byteArrayOf())
        )).await()
    }

    @Test
    fun shouldBatchShareLock() = runTest {
        CapabilityCache.put(DEFAULT_LOCK_ID, mapOf(CapabilityType.BATCH_SHARING_25 to CapabilityStatus.SUPPORTED))
        LockOperationsApi.batchShareLock(
            LockOperations.BatchShareLockOperation(
                baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
                users = listOf(LockOperations.ShareLock("", UserRole.USER, byteArrayOf()))
            )
        ).await()
    }

    @Test
    fun shouldRevokeAccessToLockUsingContext() = runTest {
        LockOperationsApi.revokeAccessToLock(
            LockOperations.RevokeAccessToLockOperation(
            baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
            users = emptyList()
        )).await()
    }

    @Test
    fun shouldRevokeAccessToLock() = runTest {
        LockOperationsApi.revokeAccessToLock(
            LockOperations.RevokeAccessToLockOperation(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            users = emptyList()
        )).await()
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDurationUsingContext() = runTest {
        LockOperationsApi.updateSecureSettingUnlockDuration(
            LockOperations.UpdateSecureSettingUnlockDuration(
            baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
            unlockDuration = 0
        )).await()
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDuration() = runTest {
        LockOperationsApi.updateSecureSettingUnlockDuration(
            LockOperations.UpdateSecureSettingUnlockDuration(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            unlockDuration = 0
        )).await()
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetweenUsingContext() = runTest {
        LockOperationsApi.updateSecureSettingUnlockBetween(
            LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
            unlockBetween = null
        )).await()
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetween() = runTest {
        LockOperationsApi.updateSecureSettingUnlockBetween(
            LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            unlockBetween = null
        )).await()
    }

    @Test
    fun shouldGetPinnedLocks() = runTest {
        val response = LockOperationsApi.getPinnedLocks().await()
        assertEquals(PINNED_LOCKS_RESPONSE, response)
    }

    @Test
    fun shouldGetShareableLocks() = runTest {
        val response = LockOperationsApi.getShareableLocks().await()
        assertEquals(SHAREABLE_LOCKS_RESPONSE, response)
    }
}