package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_LOCK_ID
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_USER_ID
import com.doordeck.multiplatform.sdk.api.model.LockOperations
import com.doordeck.multiplatform.sdk.api.model.UserRole
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.LocalUnlockClient
import com.doordeck.multiplatform.sdk.internal.api.LockOperationsResourceImpl
import com.ionspin.kotlin.crypto.LibsodiumInitializer
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class LockOperationsResourceImplTest {

    private val contextManager = ContextManagerImpl()
    private val localUnlock = LocalUnlockClient(TEST_HTTP_CLIENT)
    private val lockOperations = LockOperationsResourceImpl(TEST_HTTP_CLIENT, contextManager, localUnlock)

    init {
        LibsodiumInitializer.initializeWithCallback {  }
        contextManager.setOperationContext("", emptyArray(), byteArrayOf())
    }

    @Test
    fun getSingleLock() = runTest {
        lockOperations.getSingleLock(DEFAULT_LOCK_ID)
    }

    @Test
    fun getLockAuditTrail() = runTest {
        lockOperations.getLockAuditTrail(DEFAULT_LOCK_ID, 0, 0)
    }

    @Test
    fun getAuditForUser() = runTest {
        lockOperations.getAuditForUser(DEFAULT_USER_ID, 0, 0)
    }

    @Test
    fun getUsersForLock() = runTest {
        lockOperations.getUsersForLock(DEFAULT_LOCK_ID)
    }


    @Test
    fun getLocksForUser() = runTest {
        lockOperations.getLocksForUser(DEFAULT_USER_ID)
    }

    @Test
    fun updateLockName() = runTest {
        lockOperations.updateLockName(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun updateLockFavourite() = runTest {
        lockOperations.updateLockFavourite(DEFAULT_LOCK_ID, false)
    }

    @Test
    fun updateLockColour() = runTest {
        lockOperations.updateLockColour(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun updateLockSettingDefaultName() = runTest {
        lockOperations.updateLockSettingDefaultName(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun setLockSettingPermittedAddresses() = runTest {
        lockOperations.setLockSettingPermittedAddresses(DEFAULT_LOCK_ID, listOf("1.1.1.1"))
    }

    @Test
    fun updateLockSettingHidden() = runTest {
        lockOperations.updateLockSettingHidden(DEFAULT_LOCK_ID, true)
    }

    @Test
    fun setLockSettingTimeRestrictions() = runTest {
        lockOperations.setLockSettingTimeRestrictions(DEFAULT_LOCK_ID, emptyList())
    }

    @Test
    fun updateLockSettingLocationRestrictions() = runTest {
        lockOperations.updateLockSettingLocationRestrictions(DEFAULT_LOCK_ID, null)
    }

    @Test
    fun getUserPublicKey() = runTest {
        lockOperations.getUserPublicKey(DEFAULT_USER_EMAIL)
    }

    @Test
    fun getUserPublicKeyByEmail() = runTest {
        lockOperations.getUserPublicKeyByEmail("")
    }

    @Test
    fun getUserPublicKeyByTelephone() = runTest {
        lockOperations.getUserPublicKeyByTelephone("")
    }

    @Test
    fun getUserPublicKeyByLocalKey() = runTest {
        lockOperations.getUserPublicKeyByLocalKey("")
    }

    @Test
    fun getUserPublicKeyByForeignKey() = runTest {
        lockOperations.getUserPublicKeyByForeignKey("")
    }

    @Test
    fun getUserPublicKeyByIdentity() = runTest {
        lockOperations.getUserPublicKeyByIdentity("")
    }

    @Test
    fun unlockWithContext() = runTest {
        lockOperations.unlockWithContext(DEFAULT_LOCK_ID)
    }

    @Test
    fun unlock() = runTest {
        lockOperations.unlock(LockOperations.UnlockOperation(LockOperations.BaseOperation("userId", emptyArray(), byteArrayOf(), DEFAULT_LOCK_ID)))
    }

    @Test
    fun shareLockWithContext() = runTest {
        lockOperations.shareLockWithContext(DEFAULT_LOCK_ID, LockOperations.ShareLock("", UserRole.USER, byteArrayOf()))
    }

    @Test
    fun shareLock() = runTest {
        lockOperations.shareLock(
            LockOperations.ShareLockOperation(
                baseOperation = LockOperations.BaseOperation("", emptyArray(), byteArrayOf(), DEFAULT_LOCK_ID),
                shareLock = LockOperations.ShareLock("", UserRole.USER, byteArrayOf())
        ))
    }

    @Test
    fun revokeAccessToLockWithContext() = runTest {
        lockOperations.revokeAccessToLockWithContext(DEFAULT_LOCK_ID, emptyList())
    }

    @Test
    fun revokeAccessToLock() = runTest {
        lockOperations.revokeAccessToLock(LockOperations.RevokeAccessToLockOperation(
            baseOperation = LockOperations.BaseOperation("", emptyArray(), byteArrayOf(), DEFAULT_LOCK_ID),
            users = emptyArray()
        ))
    }

    @Test
    fun updateSecureSettingUnlockDurationWithContext() = runTest {
        lockOperations.updateSecureSettingUnlockDurationWithContext(DEFAULT_LOCK_ID, 0)
    }

    @Test
    fun updateSecureSettingUnlockDuration() = runTest {
        lockOperations.updateSecureSettingUnlockDuration(LockOperations.UpdateSecureSettingUnlockDuration(
            baseOperation = LockOperations.BaseOperation("", emptyArray(), byteArrayOf(), DEFAULT_LOCK_ID),
            unlockDuration = 0
        ))
    }

    @Test
    fun updateSecureSettingUnlockBetweenWithContext() = runTest {
        lockOperations.updateSecureSettingUnlockBetweenWithContext(DEFAULT_LOCK_ID, null)
    }

    @Test
    fun updateSecureSettingUnlockBetween() = runTest {
        lockOperations.updateSecureSettingUnlockBetween(LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = LockOperations.BaseOperation("", emptyArray(), byteArrayOf(), DEFAULT_LOCK_ID),
            unlockBetween = null
        ))
    }

    @Test
    fun getPinnedLocks() = runTest {
        lockOperations.getPinnedLocks()
    }

    @Test
    fun getShareableLocks() = runTest {
        lockOperations.getShareableLocks()
    }
}