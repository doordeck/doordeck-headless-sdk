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
import kotlinx.coroutines.await
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
        lockOperations.getSingleLock(DEFAULT_LOCK_ID).await()
    }

    @Test
    fun getLockAuditTrail() = runTest {
        lockOperations.getLockAuditTrail(DEFAULT_LOCK_ID, 0, 0).await()
    }

    @Test
    fun getAuditForUser() = runTest {
        lockOperations.getAuditForUser(DEFAULT_USER_ID, 0, 0).await()
    }

    @Test
    fun getUsersForLock() = runTest {
        lockOperations.getUsersForLock(DEFAULT_LOCK_ID).await()
    }

    @Test
    fun getLocksForUser() = runTest {
        lockOperations.getLocksForUser(DEFAULT_USER_ID).await()
    }

    @Test
    fun updateLockName() = runTest {
        lockOperations.updateLockName(DEFAULT_LOCK_ID, "").await()
    }

    @Test
    fun updateLockFavourite() = runTest {
        lockOperations.updateLockFavourite(DEFAULT_LOCK_ID, false).await()
    }

    @Test
    fun updateLockColour() = runTest {
        lockOperations.updateLockColour(DEFAULT_LOCK_ID, "").await()
    }

    @Test
    fun updateLockSettingDefaultName() = runTest {
        lockOperations.updateLockSettingDefaultName(DEFAULT_LOCK_ID, "").await()
    }

    @Test
    fun setLockSettingPermittedAddresses() = runTest {
        lockOperations.setLockSettingPermittedAddresses(DEFAULT_LOCK_ID, arrayOf("1.1.1.1")).await()
    }

    @Test
    fun updateLockSettingHidden() = runTest {
        lockOperations.updateLockSettingHidden(DEFAULT_LOCK_ID, true).await()
    }

    @Test
    fun setLockSettingTimeRestrictions() = runTest {
        lockOperations.setLockSettingTimeRestrictions(DEFAULT_LOCK_ID, emptyArray()).await()
    }

    @Test
    fun updateLockSettingLocationRestrictions() = runTest {
        lockOperations.updateLockSettingLocationRestrictions(DEFAULT_LOCK_ID, null).await()
    }

    @Test
    fun getUserPublicKey() = runTest {
        lockOperations.getUserPublicKey(DEFAULT_USER_EMAIL).await()
    }

    @Test
    fun getUserPublicKeyByEmail() = runTest {
        lockOperations.getUserPublicKeyByEmail("").await()
    }

    @Test
    fun getUserPublicKeyByTelephone() = runTest {
        lockOperations.getUserPublicKeyByTelephone("").await()
    }

    @Test
    fun getUserPublicKeyByLocalKey() = runTest {
        lockOperations.getUserPublicKeyByLocalKey("").await()
    }

    @Test
    fun getUserPublicKeyByForeignKey() = runTest {
        lockOperations.getUserPublicKeyByForeignKey("").await()
    }

    @Test
    fun getUserPublicKeyByIdentity() = runTest {
        lockOperations.getUserPublicKeyByIdentity("").await()
    }

    @Test
    fun unlockWithContext() = runTest {
        lockOperations.unlockWithContext(DEFAULT_LOCK_ID).await()
    }

    @Test
    fun unlock() = runTest {
        lockOperations.unlock(LockOperations.UnlockOperation(LockOperations.BaseOperation("userId", emptyArray(), byteArrayOf(), DEFAULT_LOCK_ID))).await()
    }

    @Test
    fun shareLockWithContext() = runTest {
        lockOperations.shareLockWithContext(DEFAULT_LOCK_ID, LockOperations.ShareLock("", UserRole.USER, byteArrayOf())).await()
    }

    @Test
    fun shareLock() = runTest {
        lockOperations.shareLock(
            LockOperations.ShareLockOperation(
                baseOperation = LockOperations.BaseOperation("", emptyArray(), byteArrayOf(), DEFAULT_LOCK_ID),
                shareLock = LockOperations.ShareLock("", UserRole.USER, byteArrayOf())
        )).await()
    }

    @Test
    fun revokeAccessToLockWithContext() = runTest {
        lockOperations.revokeAccessToLockWithContext(DEFAULT_LOCK_ID, emptyArray()).await()
    }

    @Test
    fun revokeAccessToLock() = runTest {
        lockOperations.revokeAccessToLock(LockOperations.RevokeAccessToLockOperation(
            baseOperation = LockOperations.BaseOperation("", emptyArray(), byteArrayOf(), DEFAULT_LOCK_ID),
            users = emptyArray()
        )).await()
    }

    @Test
    fun updateSecureSettingUnlockDurationWithContext() = runTest {
        lockOperations.updateSecureSettingUnlockDurationWithContext(DEFAULT_LOCK_ID, 0).await()
    }

    @Test
    fun updateSecureSettingUnlockDuration() = runTest {
        lockOperations.updateSecureSettingUnlockDuration(LockOperations.UpdateSecureSettingUnlockDuration(
            baseOperation = LockOperations.BaseOperation("", emptyArray(), byteArrayOf(), DEFAULT_LOCK_ID),
            unlockDuration = 0
        )).await()
    }

    @Test
    fun updateSecureSettingUnlockBetweenWithContext() = runTest {
        lockOperations.updateSecureSettingUnlockBetweenWithContext(DEFAULT_LOCK_ID, null).await()
    }

    @Test
    fun updateSecureSettingUnlockBetween() = runTest {
        lockOperations.updateSecureSettingUnlockBetween(LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = LockOperations.BaseOperation("", emptyArray(), byteArrayOf(), DEFAULT_LOCK_ID),
            unlockBetween = null
        )).await()
    }

    @Test
    fun getPinnedLocks() = runTest {
        lockOperations.getPinnedLocks().await()
    }

    @Test
    fun getShareableLocks() = runTest {
        lockOperations.getShareableLocks().await()
    }
}