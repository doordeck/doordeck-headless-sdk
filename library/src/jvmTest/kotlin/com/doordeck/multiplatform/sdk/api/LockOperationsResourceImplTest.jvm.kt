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
import kotlinx.coroutines.future.await
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
    fun getSingleLockFuture() = runTest {
        lockOperations.getSingleLockFuture(DEFAULT_LOCK_ID).await()
    }

    @Test
    fun getLockAuditTrail() = runTest {
        lockOperations.getLockAuditTrail(DEFAULT_LOCK_ID, 0, 0)
    }

    @Test
    fun getLockAuditTrailFuture() = runTest {
        lockOperations.getLockAuditTrailFuture(DEFAULT_LOCK_ID, 0, 0).await()
    }

    @Test
    fun getAuditForUser() = runTest {
        lockOperations.getAuditForUser(DEFAULT_USER_ID, 0, 0)
    }

    @Test
    fun getAuditForUserFuture() = runTest {
        lockOperations.getAuditForUserFuture(DEFAULT_USER_ID, 0, 0).await()
    }

    @Test
    fun getUsersForLock() = runTest {
        lockOperations.getUsersForLock(DEFAULT_LOCK_ID)
    }

    @Test
    fun getUsersForLockFuture() = runTest {
        lockOperations.getUsersForLockFuture(DEFAULT_LOCK_ID).await()
    }


    @Test
    fun getLocksForUser() = runTest {
        lockOperations.getLocksForUser(DEFAULT_USER_ID)
    }

    @Test
    fun getLocksForUserFuture() = runTest {
        lockOperations.getLocksForUserFuture(DEFAULT_USER_ID).await()
    }

    @Test
    fun updateLockName() = runTest {
        lockOperations.updateLockName(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun updateLockNameFuture() = runTest {
        lockOperations.updateLockNameFuture(DEFAULT_LOCK_ID, "").await()
    }

    @Test
    fun updateLockFavourite() = runTest {
        lockOperations.updateLockFavourite(DEFAULT_LOCK_ID, false)
    }

    @Test
    fun updateLockFavouriteFuture() = runTest {
        lockOperations.updateLockFavouriteFuture(DEFAULT_LOCK_ID, false).await()
    }

    @Test
    fun updateLockColour() = runTest {
        lockOperations.updateLockColour(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun updateLockColourFuture() = runTest {
        lockOperations.updateLockColourFuture(DEFAULT_LOCK_ID, "").await()
    }

    @Test
    fun updateLockSettingDefaultName() = runTest {
        lockOperations.updateLockSettingDefaultName(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun updateLockSettingDefaultNameFuture() = runTest {
        lockOperations.updateLockSettingDefaultNameFuture(DEFAULT_LOCK_ID, "").await()
    }

    @Test
    fun setLockSettingPermittedAddresses() = runTest {
        lockOperations.setLockSettingPermittedAddresses(DEFAULT_LOCK_ID, listOf("1.1.1.1"))
    }

    @Test
    fun setLockSettingPermittedAddressesFuture() = runTest {
        lockOperations.setLockSettingPermittedAddressesFuture(DEFAULT_LOCK_ID, listOf("1.1.1.1")).await()
    }

    @Test
    fun updateLockSettingHidden() = runTest {
        lockOperations.updateLockSettingHidden(DEFAULT_LOCK_ID, true)
    }

    @Test
    fun updateLockSettingHiddenFuture() = runTest {
        lockOperations.updateLockSettingHiddenFuture(DEFAULT_LOCK_ID, true).await()
    }

    @Test
    fun setLockSettingTimeRestrictions() = runTest {
        lockOperations.setLockSettingTimeRestrictions(DEFAULT_LOCK_ID, emptyList())
    }

    @Test
    fun setLockSettingTimeRestrictionsFuture() = runTest {
        lockOperations.setLockSettingTimeRestrictionsFuture(DEFAULT_LOCK_ID, emptyList()).await()
    }

    @Test
    fun updateLockSettingLocationRestrictions() = runTest {
        lockOperations.updateLockSettingLocationRestrictions(DEFAULT_LOCK_ID, null)
    }

    @Test
    fun updateLockSettingLocationRestrictionsFuture() = runTest {
        lockOperations.updateLockSettingLocationRestrictionsFuture(DEFAULT_LOCK_ID, null).await()
    }

    @Test
    fun getUserPublicKey() = runTest {
        lockOperations.getUserPublicKey(DEFAULT_USER_EMAIL)
    }

    @Test
    fun getUserPublicKeyFuture() = runTest {
        lockOperations.getUserPublicKeyFuture(DEFAULT_USER_EMAIL).await()
    }

    @Test
    fun getUserPublicKeyByEmail() = runTest {
        lockOperations.getUserPublicKeyByEmail("")
    }

    @Test
    fun getUserPublicKeyByEmailFuture() = runTest {
        lockOperations.getUserPublicKeyByEmailFuture("").await()
    }

    @Test
    fun getUserPublicKeyByTelephone() = runTest {
        lockOperations.getUserPublicKeyByTelephone("")
    }

    @Test
    fun getUserPublicKeyByTelephoneFuture() = runTest {
        lockOperations.getUserPublicKeyByTelephoneFuture("").await()
    }

    @Test
    fun getUserPublicKeyByLocalKey() = runTest {
        lockOperations.getUserPublicKeyByLocalKey("")
    }

    @Test
    fun getUserPublicKeyByLocalKeyFuture() = runTest {
        lockOperations.getUserPublicKeyByLocalKeyFuture("").await()
    }

    @Test
    fun getUserPublicKeyByForeignKey() = runTest {
        lockOperations.getUserPublicKeyByForeignKey("")
    }

    @Test
    fun getUserPublicKeyByForeignKeyFuture() = runTest {
        lockOperations.getUserPublicKeyByForeignKeyFuture("").await()
    }

    @Test
    fun getUserPublicKeyByIdentity() = runTest {
        lockOperations.getUserPublicKeyByIdentity("")
    }

    @Test
    fun getUserPublicKeyByIdentityFuture() = runTest {
        lockOperations.getUserPublicKeyByIdentityFuture("").await()
    }

    @Test
    fun unlockWithContext() = runTest {
        lockOperations.unlockWithContext(DEFAULT_LOCK_ID)
    }

    @Test
    fun unlockWithContextFuture() = runTest {
        lockOperations.unlockWithContextFuture(DEFAULT_LOCK_ID).await()
    }

    @Test
    fun unlock() = runTest {
        lockOperations.unlock(LockOperations.UnlockOperation(LockOperations.BaseOperation("userId", emptyArray(), byteArrayOf(), DEFAULT_LOCK_ID)))
    }

    @Test
    fun unlockFuture() = runTest {
        lockOperations.unlockFuture(LockOperations.UnlockOperation(LockOperations.BaseOperation("userId", emptyArray(), byteArrayOf(), DEFAULT_LOCK_ID))).await()
    }

    @Test
    fun shareLockWithContext() = runTest {
        lockOperations.shareLockWithContext(DEFAULT_LOCK_ID, LockOperations.ShareLock("", UserRole.USER, byteArrayOf()))
    }

    @Test
    fun shareLockWithContextFuture() = runTest {
        lockOperations.shareLockWithContextFuture(DEFAULT_LOCK_ID, LockOperations.ShareLock("", UserRole.USER, byteArrayOf())).await()
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
    fun shareLockFuture() = runTest {
        lockOperations.shareLockFuture(
            LockOperations.ShareLockOperation(
                baseOperation = LockOperations.BaseOperation("", emptyArray(), byteArrayOf(), DEFAULT_LOCK_ID),
                shareLock = LockOperations.ShareLock("", UserRole.USER, byteArrayOf())
            )).await()
    }

    @Test
    fun revokeAccessToLockWithContext() = runTest {
        lockOperations.revokeAccessToLockWithContext(DEFAULT_LOCK_ID, emptyList())
    }

    @Test
    fun revokeAccessToLockWithContextFuture() = runTest {
        lockOperations.revokeAccessToLockWithContextFuture(DEFAULT_LOCK_ID, emptyList()).await()
    }

    @Test
    fun revokeAccessToLock() = runTest {
        lockOperations.revokeAccessToLock(LockOperations.RevokeAccessToLockOperation(
            baseOperation = LockOperations.BaseOperation("", emptyArray(), byteArrayOf(), DEFAULT_LOCK_ID),
            users = emptyArray()
        ))
    }

    @Test
    fun revokeAccessToLockFuture() = runTest {
        lockOperations.revokeAccessToLockFuture(LockOperations.RevokeAccessToLockOperation(
            baseOperation = LockOperations.BaseOperation("", emptyArray(), byteArrayOf(), DEFAULT_LOCK_ID),
            users = emptyArray()
        )).await()
    }

    @Test
    fun updateSecureSettingUnlockDurationWithContext() = runTest {
        lockOperations.updateSecureSettingUnlockDurationWithContext(DEFAULT_LOCK_ID, 0)
    }

    @Test
    fun updateSecureSettingUnlockDurationWithContextFuture() = runTest {
        lockOperations.updateSecureSettingUnlockDurationWithContextFuture(DEFAULT_LOCK_ID, 0).await()
    }

    @Test
    fun updateSecureSettingUnlockDuration() = runTest {
        lockOperations.updateSecureSettingUnlockDuration(LockOperations.UpdateSecureSettingUnlockDuration(
            baseOperation = LockOperations.BaseOperation("", emptyArray(), byteArrayOf(), DEFAULT_LOCK_ID),
            unlockDuration = 0
        ))
    }

    @Test
    fun updateSecureSettingUnlockDurationFuture() = runTest {
        lockOperations.updateSecureSettingUnlockDurationFuture(LockOperations.UpdateSecureSettingUnlockDuration(
            baseOperation = LockOperations.BaseOperation("", emptyArray(), byteArrayOf(), DEFAULT_LOCK_ID),
            unlockDuration = 0
        )).await()
    }

    @Test
    fun updateSecureSettingUnlockBetweenWithContext() = runTest {
        lockOperations.updateSecureSettingUnlockBetweenWithContext(DEFAULT_LOCK_ID, null)
    }

    @Test
    fun updateSecureSettingUnlockBetweenWithContextFuture() = runTest {
        lockOperations.updateSecureSettingUnlockBetweenWithContextFuture(DEFAULT_LOCK_ID, null).await()
    }

    @Test
    fun updateSecureSettingUnlockBetween() = runTest {
        lockOperations.updateSecureSettingUnlockBetween(LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = LockOperations.BaseOperation("", emptyArray(), byteArrayOf(), DEFAULT_LOCK_ID),
            unlockBetween = null
        ))
    }

    @Test
    fun updateSecureSettingUnlockBetweenFuture() = runTest {
        lockOperations.updateSecureSettingUnlockBetweenFuture(LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = LockOperations.BaseOperation("", emptyArray(), byteArrayOf(), DEFAULT_LOCK_ID),
            unlockBetween = null
        )).await()
    }

    @Test
    fun getPinnedLocks() = runTest {
        lockOperations.getPinnedLocks()
    }

    @Test
    fun getPinnedLocksFuture() = runTest {
        lockOperations.getPinnedLocksFuture().await()
    }

    @Test
    fun getShareableLocks() = runTest {
        lockOperations.getShareableLocks()
    }

    @Test
    fun getShareableLocksFuture() = runTest {
        lockOperations.getShareableLocksFuture().await()
    }
}