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
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Ignore
import kotlin.test.Test

class LockOperationsResourceImplTest {

    private val contextManager = ContextManagerImpl()
    private val localUnlock = LocalUnlockClient(TEST_HTTP_CLIENT)
    private val lockOperations = LockOperationsResourceImpl(TEST_HTTP_CLIENT, contextManager, localUnlock)

    init {
        contextManager.setOperationContext("", emptyArray(), byteArrayOf())
    }

    @Test
    fun shouldGetSingleLock() = runTest {
        lockOperations.getSingleLock(DEFAULT_LOCK_ID)
    }

    @Test
    fun shouldGetSingleLockFuture() = runTest {
        lockOperations.getSingleLockFuture(DEFAULT_LOCK_ID).await()
    }

    @Test
    fun shouldGetLockAuditTrail() = runTest {
        lockOperations.getLockAuditTrail(DEFAULT_LOCK_ID, 0, 0)
    }

    @Test
    fun shouldGetLockAuditTrailFuture() = runTest {
        lockOperations.getLockAuditTrailFuture(DEFAULT_LOCK_ID, 0, 0).await()
    }

    @Test
    fun shouldGetAuditForUser() = runTest {
        lockOperations.getAuditForUser(DEFAULT_USER_ID, 0, 0)
    }

    @Test
    fun shouldGetAuditForUserFuture() = runTest {
        lockOperations.getAuditForUserFuture(DEFAULT_USER_ID, 0, 0).await()
    }

    @Test
    fun shouldGetUsersForLock() = runTest {
        lockOperations.getUsersForLock(DEFAULT_LOCK_ID)
    }

    @Test
    fun shouldGetUsersForLockFuture() = runTest {
        lockOperations.getUsersForLockFuture(DEFAULT_LOCK_ID).await()
    }


    @Test
    fun shouldGetLocksForUser() = runTest {
        lockOperations.getLocksForUser(DEFAULT_USER_ID)
    }

    @Test
    fun shouldGetLocksForUserFuture() = runTest {
        lockOperations.getLocksForUserFuture(DEFAULT_USER_ID).await()
    }

    @Test
    fun shouldUpdateLockName() = runTest {
        lockOperations.updateLockName(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun shouldUpdateLockNameFuture() = runTest {
        lockOperations.updateLockNameFuture(DEFAULT_LOCK_ID, "").await()
    }

    @Test
    fun shouldUpdateLockFavourite() = runTest {
        lockOperations.updateLockFavourite(DEFAULT_LOCK_ID, false)
    }

    @Test
    fun shouldUpdateLockFavouriteFuture() = runTest {
        lockOperations.updateLockFavouriteFuture(DEFAULT_LOCK_ID, false).await()
    }

    @Test
    fun shouldUpdateLockColour() = runTest {
        lockOperations.updateLockColour(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun shouldUpdateLockColourFuture() = runTest {
        lockOperations.updateLockColourFuture(DEFAULT_LOCK_ID, "").await()
    }

    @Test
    fun shouldUpdateLockSettingDefaultName() = runTest {
        lockOperations.updateLockSettingDefaultName(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun shouldUpdateLockSettingDefaultNameFuture() = runTest {
        lockOperations.updateLockSettingDefaultNameFuture(DEFAULT_LOCK_ID, "").await()
    }

    @Test
    fun shouldSetLockSettingPermittedAddresses() = runTest {
        lockOperations.setLockSettingPermittedAddresses(DEFAULT_LOCK_ID, listOf("1.1.1.1"))
    }

    @Test
    fun shouldSetLockSettingPermittedAddressesFuture() = runTest {
        lockOperations.setLockSettingPermittedAddressesFuture(DEFAULT_LOCK_ID, listOf("1.1.1.1")).await()
    }

    @Test
    fun shouldUpdateLockSettingHidden() = runTest {
        lockOperations.updateLockSettingHidden(DEFAULT_LOCK_ID, true)
    }

    @Test
    fun shouldUpdateLockSettingHiddenFuture() = runTest {
        lockOperations.updateLockSettingHiddenFuture(DEFAULT_LOCK_ID, true).await()
    }

    @Test
    fun shouldSetLockSettingTimeRestrictions() = runTest {
        lockOperations.setLockSettingTimeRestrictions(DEFAULT_LOCK_ID, emptyList())
    }

    @Test
    fun shouldSetLockSettingTimeRestrictionsFuture() = runTest {
        lockOperations.setLockSettingTimeRestrictionsFuture(DEFAULT_LOCK_ID, emptyList()).await()
    }

    @Test
    fun shouldUpdateLockSettingLocationRestrictions() = runTest {
        lockOperations.updateLockSettingLocationRestrictions(DEFAULT_LOCK_ID, null)
    }

    @Test
    fun shouldUpdateLockSettingLocationRestrictionsFuture() = runTest {
        lockOperations.updateLockSettingLocationRestrictionsFuture(DEFAULT_LOCK_ID, null).await()
    }

    @Test
    fun shouldGetUserPublicKey() = runTest {
        lockOperations.getUserPublicKey(DEFAULT_USER_EMAIL)
    }

    @Test
    fun shouldGetUserPublicKeyFuture() = runTest {
        lockOperations.getUserPublicKeyFuture(DEFAULT_USER_EMAIL).await()
    }

    @Test
    fun shouldGetUserPublicKeyByEmail() = runTest {
        lockOperations.getUserPublicKeyByEmail("")
    }

    @Test
    fun shouldGetUserPublicKeyByEmailFuture() = runTest {
        lockOperations.getUserPublicKeyByEmailFuture("").await()
    }

    @Test
    fun shouldGetUserPublicKeyByTelephone() = runTest {
        lockOperations.getUserPublicKeyByTelephone("")
    }

    @Test
    fun shouldGetUserPublicKeyByTelephoneFuture() = runTest {
        lockOperations.getUserPublicKeyByTelephoneFuture("").await()
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKey() = runTest {
        lockOperations.getUserPublicKeyByLocalKey("")
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeyFuture() = runTest {
        lockOperations.getUserPublicKeyByLocalKeyFuture("").await()
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKey() = runTest {
        lockOperations.getUserPublicKeyByForeignKey("")
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKeyFuture() = runTest {
        lockOperations.getUserPublicKeyByForeignKeyFuture("").await()
    }

    @Test
    fun shouldGetUserPublicKeyByIdentity() = runTest {
        lockOperations.getUserPublicKeyByIdentity("")
    }

    @Test
    fun shouldGetUserPublicKeyByIdentityFuture() = runTest {
        lockOperations.getUserPublicKeyByIdentityFuture("").await()
    }

    @Ignore("Libsodium does not work with the android tests")
    @Test
    fun shouldUnlockWithContext() = runTest {
        lockOperations.unlockWithContext(DEFAULT_LOCK_ID)
    }

    @Ignore("Libsodium does not work with the android tests")
    @Test
    fun shouldUnlockWithContextFuture() = runTest {
        lockOperations.unlockWithContextFuture(DEFAULT_LOCK_ID).await()
    }

    @Ignore("Libsodium does not work with the android tests")
    @Test
    fun shouldUnlock() = runTest {
        lockOperations.unlock(LockOperations.UnlockOperation(LockOperations.BaseOperation("userId", emptyArray(), byteArrayOf(), DEFAULT_LOCK_ID)))
    }

    @Ignore("Libsodium does not work with the android tests")
    @Test
    fun shouldUnlockFuture() = runTest {
        lockOperations.unlockFuture(LockOperations.UnlockOperation(LockOperations.BaseOperation("userId", emptyArray(), byteArrayOf(), DEFAULT_LOCK_ID))).await()
    }

    @Ignore("Libsodium does not work with the android tests")
    @Test
    fun shouldShareLockWithContext() = runTest {
        lockOperations.shareLockWithContext(DEFAULT_LOCK_ID, LockOperations.ShareLock("", UserRole.USER, byteArrayOf()))
    }

    @Ignore("Libsodium does not work with the android tests")
    @Test
    fun shouldShareLockWithContextFuture() = runTest {
        lockOperations.shareLockWithContextFuture(DEFAULT_LOCK_ID, LockOperations.ShareLock("", UserRole.USER, byteArrayOf())).await()
    }

    @Ignore("Libsodium does not work with the android tests")
    @Test
    fun shouldShareLock() = runTest {
        lockOperations.shareLock(
            LockOperations.ShareLockOperation(
                baseOperation = LockOperations.BaseOperation("", emptyArray(), byteArrayOf(), DEFAULT_LOCK_ID),
                shareLock = LockOperations.ShareLock("", UserRole.USER, byteArrayOf())
        ))
    }

    @Ignore("Libsodium does not work with the android tests")
    @Test
    fun shouldShareLockFuture() = runTest {
        lockOperations.shareLockFuture(
            LockOperations.ShareLockOperation(
                baseOperation = LockOperations.BaseOperation("", emptyArray(), byteArrayOf(), DEFAULT_LOCK_ID),
                shareLock = LockOperations.ShareLock("", UserRole.USER, byteArrayOf())
            )).await()
    }

    @Ignore("Libsodium does not work with the android tests")
    @Test
    fun shouldRevokeAccessToLockWithContext() = runTest {
        lockOperations.revokeAccessToLockWithContext(DEFAULT_LOCK_ID, emptyList())
    }

    @Ignore("Libsodium does not work with the android tests")
    @Test
    fun shouldRevokeAccessToLockWithContextFuture() = runTest {
        lockOperations.revokeAccessToLockWithContextFuture(DEFAULT_LOCK_ID, emptyList()).await()
    }

    @Ignore("Libsodium does not work with the android tests")
    @Test
    fun shouldRevokeAccessToLock() = runTest {
        lockOperations.revokeAccessToLock(LockOperations.RevokeAccessToLockOperation(
            baseOperation = LockOperations.BaseOperation("", emptyArray(), byteArrayOf(), DEFAULT_LOCK_ID),
            users = emptyArray()
        ))
    }

    @Ignore("Libsodium does not work with the android tests")
    @Test
    fun shouldRevokeAccessToLockFuture() = runTest {
        lockOperations.revokeAccessToLockFuture(LockOperations.RevokeAccessToLockOperation(
            baseOperation = LockOperations.BaseOperation("", emptyArray(), byteArrayOf(), DEFAULT_LOCK_ID),
            users = emptyArray()
        )).await()
    }

    @Ignore("Libsodium does not work with the android tests")
    @Test
    fun shouldUpdateSecureSettingUnlockDurationWithContext() = runTest {
        lockOperations.updateSecureSettingUnlockDurationWithContext(DEFAULT_LOCK_ID, 0)
    }

    @Ignore("Libsodium does not work with the android tests")
    @Test
    fun shouldUpdateSecureSettingUnlockDurationWithContextFuture() = runTest {
        lockOperations.updateSecureSettingUnlockDurationWithContextFuture(DEFAULT_LOCK_ID, 0).await()
    }

    @Ignore("Libsodium does not work with the android tests")
    @Test
    fun shouldUpdateSecureSettingUnlockDuration() = runTest {
        lockOperations.updateSecureSettingUnlockDuration(LockOperations.UpdateSecureSettingUnlockDuration(
            baseOperation = LockOperations.BaseOperation("", emptyArray(), byteArrayOf(), DEFAULT_LOCK_ID),
            unlockDuration = 0
        ))
    }

    @Ignore("Libsodium does not work with the android tests")
    @Test
    fun shouldUpdateSecureSettingUnlockDurationFuture() = runTest {
        lockOperations.updateSecureSettingUnlockDurationFuture(LockOperations.UpdateSecureSettingUnlockDuration(
            baseOperation = LockOperations.BaseOperation("", emptyArray(), byteArrayOf(), DEFAULT_LOCK_ID),
            unlockDuration = 0
        )).await()
    }

    @Ignore("Libsodium does not work with the android tests")
    @Test
    fun shouldUpdateSecureSettingUnlockBetweenWithContext() = runTest {
        lockOperations.updateSecureSettingUnlockBetweenWithContext(DEFAULT_LOCK_ID, null)
    }

    @Ignore("Libsodium does not work with the android tests")
    @Test
    fun shouldUpdateSecureSettingUnlockBetweenWithContextFuture() = runTest {
        lockOperations.updateSecureSettingUnlockBetweenWithContextFuture(DEFAULT_LOCK_ID, null).await()
    }

    @Ignore("Libsodium does not work with the android tests")
    @Test
    fun shouldUpdateSecureSettingUnlockBetween() = runTest {
        lockOperations.updateSecureSettingUnlockBetween(LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = LockOperations.BaseOperation("", emptyArray(), byteArrayOf(), DEFAULT_LOCK_ID),
            unlockBetween = null
        ))
    }

    @Ignore("Libsodium does not work with the android tests")
    @Test
    fun shouldUpdateSecureSettingUnlockBetweenFuture() = runTest {
        lockOperations.updateSecureSettingUnlockBetweenFuture(LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = LockOperations.BaseOperation("", emptyArray(), byteArrayOf(), DEFAULT_LOCK_ID),
            unlockBetween = null
        )).await()
    }

    @Test
    fun shouldGetPinnedLocks() = runTest {
        lockOperations.getPinnedLocks()
    }

    @Test
    fun shouldGetPinnedLocksFuture() = runTest {
        lockOperations.getPinnedLocksFuture().await()
    }

    @Test
    fun shouldGetShareableLocks() = runTest {
        lockOperations.getShareableLocks()
    }

    @Test
    fun shouldGetShareableLocksFuture() = runTest {
        lockOperations.getShareableLocksFuture().await()
    }
}