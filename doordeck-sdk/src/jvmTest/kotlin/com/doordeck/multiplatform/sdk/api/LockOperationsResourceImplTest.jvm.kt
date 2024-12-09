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
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class LockOperationsResourceImplTest {

    private val contextManager = ContextManagerImpl()
    private val localUnlock = LocalUnlockClient(TEST_HTTP_CLIENT)
    private val lockOperations = LockOperationsResourceImpl(LockOperationsClient(TEST_HTTP_CLIENT, contextManager, localUnlock))

    init {
        contextManager.setOperationContext("", emptyList(), TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray())
    }

    @Test
    fun shouldGetSingleLock() = runTest {
        lockOperations.getSingleLock(DEFAULT_LOCK_ID)
    }

    @Test
    fun shouldGetSingleLockAsync() = runTest {
        lockOperations.getSingleLockAsync(DEFAULT_LOCK_ID).await()
    }

    @Test
    fun shouldGetLockAuditTrail() = runTest {
        lockOperations.getLockAuditTrail(DEFAULT_LOCK_ID, 0, 0)
    }

    @Test
    fun shouldGetLockAuditTrailAsync() = runTest {
        lockOperations.getLockAuditTrailAsync(DEFAULT_LOCK_ID, 0, 0).await()
    }

    @Test
    fun shouldGetAuditForUser() = runTest {
        lockOperations.getAuditForUser(DEFAULT_USER_ID, 0, 0)
    }

    @Test
    fun shouldGetAuditForUserAsync() = runTest {
        lockOperations.getAuditForUserAsync(DEFAULT_USER_ID, 0, 0).await()
    }

    @Test
    fun shouldGetUsersForLock() = runTest {
        lockOperations.getUsersForLock(DEFAULT_LOCK_ID)
    }

    @Test
    fun shouldGetUsersForLockAsync() = runTest {
        lockOperations.getUsersForLockAsync(DEFAULT_LOCK_ID).await()
    }

    @Test
    fun shouldGetLocksForUser() = runTest {
        lockOperations.getLocksForUser(DEFAULT_USER_ID)
    }

    @Test
    fun shouldGetLocksForUserAsync() = runTest {
        lockOperations.getLocksForUserAsync(DEFAULT_USER_ID).await()
    }

    @Test
    fun shouldUpdateLockName() = runTest {
        lockOperations.updateLockName(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun shouldUpdateLockNameAsync() = runTest {
        lockOperations.updateLockNameAsync(DEFAULT_LOCK_ID, "").await()
    }

    @Test
    fun shouldUpdateLockFavourite() = runTest {
        lockOperations.updateLockFavourite(DEFAULT_LOCK_ID, false)
    }

    @Test
    fun shouldUpdateLockFavouriteAsync() = runTest {
        lockOperations.updateLockFavouriteAsync(DEFAULT_LOCK_ID, false).await()
    }

    @Test
    fun shouldUpdateLockColour() = runTest {
        lockOperations.updateLockColour(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun shouldUpdateLockColourAsync() = runTest {
        lockOperations.updateLockColourAsync(DEFAULT_LOCK_ID, "").await()
    }

    @Test
    fun shouldUpdateLockSettingDefaultName() = runTest {
        lockOperations.updateLockSettingDefaultName(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun shouldUpdateLockSettingDefaultNameAsync() = runTest {
        lockOperations.updateLockSettingDefaultNameAsync(DEFAULT_LOCK_ID, "").await()
    }

    @Test
    fun shouldSetLockSettingPermittedAddresses() = runTest {
        lockOperations.setLockSettingPermittedAddresses(DEFAULT_LOCK_ID, listOf("1.1.1.1"))
    }

    @Test
    fun shouldSetLockSettingPermittedAddressesAsync() = runTest {
        lockOperations.setLockSettingPermittedAddressesAsync(DEFAULT_LOCK_ID, listOf("1.1.1.1")).await()
    }

    @Test
    fun shouldUpdateLockSettingHidden() = runTest {
        lockOperations.updateLockSettingHidden(DEFAULT_LOCK_ID, true)
    }

    @Test
    fun shouldUpdateLockSettingHiddenAsync() = runTest {
        lockOperations.updateLockSettingHiddenAsync(DEFAULT_LOCK_ID, true).await()
    }

    @Test
    fun shouldSetLockSettingTimeRestrictions() = runTest {
        lockOperations.setLockSettingTimeRestrictions(DEFAULT_LOCK_ID, emptyList())
    }

    @Test
    fun shouldSetLockSettingTimeRestrictionsAsync() = runTest {
        lockOperations.setLockSettingTimeRestrictionsAsync(DEFAULT_LOCK_ID, emptyList()).await()
    }

    @Test
    fun shouldUpdateLockSettingLocationRestrictions() = runTest {
        lockOperations.updateLockSettingLocationRestrictions(DEFAULT_LOCK_ID, null)
    }

    @Test
    fun shouldUpdateLockSettingLocationRestrictionsAsync() = runTest {
        lockOperations.updateLockSettingLocationRestrictionsAsync(DEFAULT_LOCK_ID, null).await()
    }

    @Test
    fun shouldGetUserPublicKey() = runTest {
        lockOperations.getUserPublicKey(DEFAULT_USER_EMAIL)
    }

    @Test
    fun shouldGetUserPublicKeyAsync() = runTest {
        lockOperations.getUserPublicKeyAsync(DEFAULT_USER_EMAIL).await()
    }

    @Test
    fun shouldGetUserPublicKeyByEmail() = runTest {
        lockOperations.getUserPublicKeyByEmail("")
    }

    @Test
    fun shouldGetUserPublicKeyByEmailAsync() = runTest {
        lockOperations.getUserPublicKeyByEmailAsync("").await()
    }

    @Test
    fun shouldGetUserPublicKeyByTelephone() = runTest {
        lockOperations.getUserPublicKeyByTelephone("")
    }

    @Test
    fun shouldGetUserPublicKeyByTelephoneAsync() = runTest {
        lockOperations.getUserPublicKeyByTelephoneAsync("").await()
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKey() = runTest {
        lockOperations.getUserPublicKeyByLocalKey("")
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeyAsync() = runTest {
        lockOperations.getUserPublicKeyByLocalKeyAsync("").await()
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKey() = runTest {
        lockOperations.getUserPublicKeyByForeignKey("")
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKeyAsync() = runTest {
        lockOperations.getUserPublicKeyByForeignKeyAsync("").await()
    }

    @Test
    fun shouldGetUserPublicKeyByIdentity() = runTest {
        lockOperations.getUserPublicKeyByIdentity("")
    }

    @Test
    fun shouldGetUserPublicKeyByIdentityAsync() = runTest {
        lockOperations.getUserPublicKeyByIdentityAsync("").await()
    }
    
    @Test
    fun shouldGetUserPublicKeyByEmails() = runTest {
        lockOperations.getUserPublicKeyByEmails(listOf("", ""))
    }

    @Test
    fun shouldGetUserPublicKeyByEmailsAsync() = runTest {
        lockOperations.getUserPublicKeyByEmailsAsync(listOf("", "")).await()
    }

    @Test
    fun shouldGetUserPublicKeyByTelephones() = runTest {
        lockOperations.getUserPublicKeyByTelephones(listOf("", ""))
    }

    @Test
    fun shouldGetUserPublicKeyByTelephonesAsync() = runTest {
        lockOperations.getUserPublicKeyByTelephonesAsync(listOf("", "")).await()
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeys() = runTest {
        lockOperations.getUserPublicKeyByLocalKeys(listOf("", ""))
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeysAsync() = runTest {
        lockOperations.getUserPublicKeyByLocalKeysAsync(listOf("", "")).await()
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKeys() = runTest {
        lockOperations.getUserPublicKeyByForeignKeys(listOf("", ""))
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKeysAsync() = runTest {
        lockOperations.getUserPublicKeyByForeignKeysAsync(listOf("", "")).await()
    }

    @Test
    fun shouldUnlockUsingContext() = runTest {
        lockOperations.unlock(LockOperations.UnlockOperation(LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID)))
    }

    @Test
    fun shouldUnlockUsingContextAsync() = runTest {
        lockOperations.unlockAsync(LockOperations.UnlockOperation(LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID))).await()
    }

    @Test
    fun shouldUnlock() = runTest {
        lockOperations.unlock(LockOperations.UnlockOperation(LockOperations.BaseOperation("userId", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID)))
    }

    @Test
    fun shouldUnlockAsync() = runTest {
        lockOperations.unlockAsync(LockOperations.UnlockOperation(LockOperations.BaseOperation("userId", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID))).await()
    }

    @Test
    fun shouldShareLockUsingContext() = runTest {
        lockOperations.shareLock(
            LockOperations.ShareLockOperation(
                baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
                shareLock = LockOperations.ShareLock("", UserRole.USER, byteArrayOf())
            ))
    }

    @Test
    fun shouldShareLockUsingContextAsync() = runTest {
        lockOperations.shareLockAsync(
            LockOperations.ShareLockOperation(
                baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
                shareLock = LockOperations.ShareLock("", UserRole.USER, byteArrayOf())
            )).await()
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
    fun shouldShareLockAsync() = runTest {
        lockOperations.shareLockAsync(
            LockOperations.ShareLockOperation(
                baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
                shareLock = LockOperations.ShareLock("", UserRole.USER, byteArrayOf())
            )).await()
    }

    @Test
    fun shouldRevokeAccessToLockUsingContext() = runTest {
        lockOperations.revokeAccessToLock(LockOperations.RevokeAccessToLockOperation(
            baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
            users = emptyList()
        ))
    }

    @Test
    fun shouldRevokeAccessToLockUsingContextAsync() = runTest {
        lockOperations.revokeAccessToLockAsync(LockOperations.RevokeAccessToLockOperation(
            baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
            users = emptyList()
        )).await()
    }

    @Test
    fun shouldRevokeAccessToLock() = runTest {
        lockOperations.revokeAccessToLock(LockOperations.RevokeAccessToLockOperation(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            users = emptyList()
        ))
    }

    @Test
    fun shouldRevokeAccessToLockAsync() = runTest {
        lockOperations.revokeAccessToLockAsync(LockOperations.RevokeAccessToLockOperation(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            users = emptyList()
        )).await()
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDurationUsingContext() = runTest {
        lockOperations.updateSecureSettingUnlockDuration(LockOperations.UpdateSecureSettingUnlockDuration(
            baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
            unlockDuration = 0
        ))
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDurationUsingContextAsync() = runTest {
        lockOperations.updateSecureSettingUnlockDurationAsync(LockOperations.UpdateSecureSettingUnlockDuration(
            baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
            unlockDuration = 0
        )).await()
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDuration() = runTest {
        lockOperations.updateSecureSettingUnlockDuration(LockOperations.UpdateSecureSettingUnlockDuration(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            unlockDuration = 0
        ))
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDurationAsync() = runTest {
        lockOperations.updateSecureSettingUnlockDurationAsync(LockOperations.UpdateSecureSettingUnlockDuration(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            unlockDuration = 0
        )).await()
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetweenUsingContext() = runTest {
        lockOperations.updateSecureSettingUnlockBetween(LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
            unlockBetween = null
        ))
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetweenUsingContextAsync() = runTest {
        lockOperations.updateSecureSettingUnlockBetweenAsync(LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
            unlockBetween = null
        )).await()
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetween() = runTest {
        lockOperations.updateSecureSettingUnlockBetween(LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            unlockBetween = null
        ))
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetweenAsync() = runTest {
        lockOperations.updateSecureSettingUnlockBetweenAsync(LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            unlockBetween = null
        )).await()
    }

    @Test
    fun shouldGetPinnedLocks() = runTest {
        lockOperations.getPinnedLocks()
    }

    @Test
    fun shouldGetPinnedLocksAsync() = runTest {
        lockOperations.getPinnedLocksAsync().await()
    }

    @Test
    fun shouldGetShareableLocks() = runTest {
        lockOperations.getShareableLocks()
    }

    @Test
    fun shouldGetShareableLocksAsync() = runTest {
        lockOperations.getShareableLocksAsync().await()
    }
}