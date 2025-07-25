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
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class LockOperationsApiTest : MockTest() {

    @Test
    fun shouldGetSingleLock() = runTest {
        val response = LockOperationsApi.getSingleLock(DEFAULT_LOCK_ID)
        assertEquals(LOCK_RESPONSE, response)
    }

    @Test
    fun shouldGetLockAuditTrail() = runTest {
        val response = LockOperationsApi.getLockAuditTrail(DEFAULT_LOCK_ID, 0, 0)
        assertEquals(AUDIT_RESPONSE, response)
    }

    @Test
    fun shouldGetAuditForUser() = runTest {
        val response = LockOperationsApi.getAuditForUser(DEFAULT_USER_ID, 0, 0)
        assertEquals(AUDIT_RESPONSE, response)
    }

    @Test
    fun shouldGetUsersForLock() = runTest {
        val response = LockOperationsApi.getUsersForLock(DEFAULT_LOCK_ID)
        assertEquals(USER_LOCK_RESPONSE, response)
    }

    @Test
    fun shouldGetLocksForUser() = runTest {
        val response = LockOperationsApi.getLocksForUser(DEFAULT_USER_ID)
        assertEquals(LOCK_USER_RESPONSE, response)
    }

    @Test
    fun shouldUpdateLockName() = runTest {
        LockOperationsApi.updateLockName(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun shouldUpdateLockFavourite() = runTest {
        LockOperationsApi.updateLockFavourite(DEFAULT_LOCK_ID, false)
    }

    @Test
    fun shouldUpdateLockColour() = runTest {
        LockOperationsApi.updateLockColour(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun shouldUpdateLockSettingDefaultName() = runTest {
        LockOperationsApi.updateLockSettingDefaultName(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun shouldSetLockSettingPermittedAddresses() = runTest {
        LockOperationsApi.setLockSettingPermittedAddresses(DEFAULT_LOCK_ID, listOf("1.1.1.1"))
    }

    @Test
    fun shouldUpdateLockSettingHidden() = runTest {
        LockOperationsApi.updateLockSettingHidden(DEFAULT_LOCK_ID, true)
    }

    @Test
    fun shouldSetLockSettingTimeRestrictions() = runTest {
        LockOperationsApi.setLockSettingTimeRestrictions(DEFAULT_LOCK_ID, emptyList())
    }

    @Test
    fun shouldUpdateLockSettingLocationRestrictions() = runTest {
        LockOperationsApi.updateLockSettingLocationRestrictions(DEFAULT_LOCK_ID, null)
    }

    @Test
    fun shouldGetUserPublicKey() = runTest {
        val response = LockOperationsApi.getUserPublicKey(DEFAULT_USER_EMAIL)
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByEmail() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByEmail("")
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByTelephone() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByTelephone("")
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKey() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByLocalKey("")
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKey() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByForeignKey("")
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByIdentity() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByIdentity("")
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByEmails() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByEmails(listOf("", ""))
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByTelephones() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByTelephones(listOf("", ""))
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeys() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByLocalKeys(listOf("", ""))
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKeys() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByForeignKeys(listOf("", ""))
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldUnlockUsingContext() = runTest {
        LockOperationsApi.unlock(BasicLockOperations.BasicUnlockOperation(BasicLockOperations.BasicBaseOperation(lockId = DEFAULT_LOCK_ID)))
    }

    @Test
    fun shouldUnlock() = runTest {
        LockOperationsApi.unlock(BasicLockOperations.BasicUnlockOperation(BasicLockOperations.BasicBaseOperation("userId", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID)))
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
    fun shouldShareLock() = runTest {
        LockOperationsApi.shareLock(
            BasicLockOperations.BasicShareLockOperation(
                baseOperation = BasicLockOperations.BasicBaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
                shareLock = BasicLockOperations.BasicShareLock("", UserRole.USER, byteArrayOf())
        ))
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
    fun shouldRevokeAccessToLockUsingContext() = runTest {
        LockOperationsApi.revokeAccessToLock(
            BasicLockOperations.BasicRevokeAccessToLockOperation(
            baseOperation = BasicLockOperations.BasicBaseOperation(lockId = DEFAULT_LOCK_ID),
            users = emptyList()
        ))
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
    fun shouldUpdateSecureSettingUnlockDurationUsingContext() = runTest {
        LockOperationsApi.updateSecureSettingUnlockDuration(
            BasicLockOperations.BasicUpdateSecureSettingUnlockDuration(
            baseOperation = BasicLockOperations.BasicBaseOperation(lockId = DEFAULT_LOCK_ID),
            unlockDuration = 0
        ))
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
    fun shouldUpdateSecureSettingUnlockBetweenUsingContext() = runTest {
        LockOperationsApi.updateSecureSettingUnlockBetween(
            BasicLockOperations.BasicUpdateSecureSettingUnlockBetween(
            baseOperation = BasicLockOperations.BasicBaseOperation(lockId =DEFAULT_LOCK_ID),
            unlockBetween = null
        ))
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
    fun shouldGetPinnedLocks() = runTest {
        val response = LockOperationsApi.getPinnedLocks()
        assertEquals(PINNED_LOCKS_RESPONSE, response)
    }

    @Test
    fun shouldGetShareableLocks() = runTest {
        val response = LockOperationsApi.getShareableLocks()
        assertEquals(SHAREABLE_LOCKS_RESPONSE, response)
    }
}