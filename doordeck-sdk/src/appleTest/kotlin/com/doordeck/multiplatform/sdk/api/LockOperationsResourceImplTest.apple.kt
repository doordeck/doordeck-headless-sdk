package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_LOCK_ID
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_USER_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.api.model.LockOperations
import com.doordeck.multiplatform.sdk.api.model.UserRole
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.LocalUnlockClient
import com.doordeck.multiplatform.sdk.internal.api.LockOperationsClient
import com.doordeck.multiplatform.sdk.internal.api.LockOperationsResourceImpl
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class LockOperationsResourceImplTest {

    private val contextManager = ContextManagerImpl()
    private val localUnlock = LocalUnlockClient(TEST_HTTP_CLIENT)
    private val lockOperations = LockOperationsResourceImpl(LockOperationsClient( TEST_HTTP_CLIENT, contextManager, localUnlock))

    init {
        contextManager.setOperationContext("", emptyList(), TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray())
    }

    @Test
    fun shouldGetSingleLock() = runTest {
        lockOperations.getSingleLock(DEFAULT_LOCK_ID)
    }

    @Test
    fun shouldGetLockAuditTrail() = runTest {
        lockOperations.getLockAuditTrail(DEFAULT_LOCK_ID, 0, 0)
    }

    @Test
    fun shouldGetAuditForUser() = runTest {
        lockOperations.getAuditForUser(DEFAULT_USER_ID, 0, 0)
    }

    @Test
    fun shouldGetUsersForLock() = runTest {
        lockOperations.getUsersForLock(DEFAULT_LOCK_ID)
    }

    @Test
    fun shouldGetLocksForUser() = runTest {
        lockOperations.getLocksForUser(DEFAULT_USER_ID)
    }

    @Test
    fun shouldUpdateLockName() = runTest {
        lockOperations.updateLockName(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun shouldUpdateLockFavourite() = runTest {
        lockOperations.updateLockFavourite(DEFAULT_LOCK_ID, false)
    }

    @Test
    fun shouldUpdateLockColour() = runTest {
        lockOperations.updateLockColour(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun shouldUpdateLockSettingDefaultName() = runTest {
        lockOperations.updateLockSettingDefaultName(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun shouldSetLockSettingPermittedAddresses() = runTest {
        lockOperations.setLockSettingPermittedAddresses(DEFAULT_LOCK_ID, listOf("1.1.1.1"))
    }

    @Test
    fun shouldUpdateLockSettingHidden() = runTest {
        lockOperations.updateLockSettingHidden(DEFAULT_LOCK_ID, true)
    }

    @Test
    fun shouldSetLockSettingTimeRestrictions() = runTest {
        lockOperations.setLockSettingTimeRestrictions(DEFAULT_LOCK_ID, emptyList())
    }

    @Test
    fun shouldUpdateLockSettingLocationRestrictions() = runTest {
        lockOperations.updateLockSettingLocationRestrictions(DEFAULT_LOCK_ID, null)
    }

    @Test
    fun shouldGetUserPublicKey() = runTest {
        lockOperations.getUserPublicKey(DEFAULT_USER_EMAIL)
    }

    @Test
    fun shouldGetUserPublicKeyByEmail() = runTest {
        lockOperations.getUserPublicKeyByEmail("")
    }

    @Test
    fun shouldGetUserPublicKeyByTelephone() = runTest {
        lockOperations.getUserPublicKeyByTelephone("")
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKey() = runTest {
        lockOperations.getUserPublicKeyByLocalKey("")
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKey() = runTest {
        lockOperations.getUserPublicKeyByForeignKey("")
    }

    @Test
    fun shouldGetUserPublicKeyByIdentity() = runTest {
        lockOperations.getUserPublicKeyByIdentity("")
    }

    @Test
    fun shouldGetUserPublicKeyByEmails() = runTest {
        lockOperations.getUserPublicKeyByEmails(listOf("", ""))
    }

    @Test
    fun shouldGetUserPublicKeyByTelephones() = runTest {
        lockOperations.getUserPublicKeyByTelephones(listOf("", ""))
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeys() = runTest {
        lockOperations.getUserPublicKeyByLocalKeys(listOf("", ""))
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKeys() = runTest {
        lockOperations.getUserPublicKeyByForeignKeys(listOf("", ""))
    }

    @Test
    fun shouldUnlockWithContext() = runTest {
        lockOperations.unlockWithContext(DEFAULT_LOCK_ID)
    }

    @Test
    fun shouldUnlock() = runTest {
        lockOperations.unlock(LockOperations.UnlockOperation(LockOperations.BaseOperation("userId", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID)))
    }

    @Test
    fun shouldShareLockWithContext() = runTest {
        lockOperations.shareLockWithContext(DEFAULT_LOCK_ID, LockOperations.ShareLock("", UserRole.USER, byteArrayOf()))
    }

    @Test
    fun shouldShareLock() = runTest {
        lockOperations.shareLock(
            LockOperations.ShareLockOperation(
                baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
                shareLock = LockOperations.ShareLock("", UserRole.USER, byteArrayOf())
        ))
    }

    @Test
    fun shouldRevokeAccessToLockWithContext() = runTest {
        lockOperations.revokeAccessToLockWithContext(DEFAULT_LOCK_ID, emptyList())
    }

    @Test
    fun shouldRevokeAccessToLock() = runTest {
        lockOperations.revokeAccessToLock(LockOperations.RevokeAccessToLockOperation(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            users = emptyList()
        ))
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDurationWithContext() = runTest {
        lockOperations.updateSecureSettingUnlockDurationWithContext(DEFAULT_LOCK_ID, 0)
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDuration() = runTest {
        lockOperations.updateSecureSettingUnlockDuration(LockOperations.UpdateSecureSettingUnlockDuration(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            unlockDuration = 0
        ))
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetweenWithContext() = runTest {
        lockOperations.updateSecureSettingUnlockBetweenWithContext(DEFAULT_LOCK_ID, null)
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetween() = runTest {
        lockOperations.updateSecureSettingUnlockBetween(LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            unlockBetween = null
        ))
    }

    @Test
    fun shouldGetPinnedLocks() = runTest {
        lockOperations.getPinnedLocks()
    }

    @Test
    fun shouldGetShareableLocks() = runTest {
        lockOperations.getShareableLocks()
    }
}