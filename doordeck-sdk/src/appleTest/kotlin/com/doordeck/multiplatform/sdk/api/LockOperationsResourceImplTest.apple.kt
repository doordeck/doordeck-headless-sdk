package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.HttpClient
import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_LOCK_ID
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_USER_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_ENVIRONMENT
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.api.model.LockOperations
import com.doordeck.multiplatform.sdk.api.model.UserRole
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.LockOperationsResourceImpl
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class LockOperationsResourceImplTest {

    init {
        ContextManagerImpl.setApiEnvironment(TEST_ENVIRONMENT)
        ContextManagerImpl.setOperationContext("", emptyList(), TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray())
        CloudHttpClient.overrideClient(TEST_HTTP_CLIENT)
        HttpClient.overrideClient(TEST_HTTP_CLIENT)
    }

    @Test
    fun shouldGetSingleLock() = runTest {
        LockOperationsResourceImpl.getSingleLock(DEFAULT_LOCK_ID)
    }

    @Test
    fun shouldGetLockAuditTrail() = runTest {
        LockOperationsResourceImpl.getLockAuditTrail(DEFAULT_LOCK_ID, 0, 0)
    }

    @Test
    fun shouldGetAuditForUser() = runTest {
        LockOperationsResourceImpl.getAuditForUser(DEFAULT_USER_ID, 0, 0)
    }

    @Test
    fun shouldGetUsersForLock() = runTest {
        LockOperationsResourceImpl.getUsersForLock(DEFAULT_LOCK_ID)
    }

    @Test
    fun shouldGetLocksForUser() = runTest {
        LockOperationsResourceImpl.getLocksForUser(DEFAULT_USER_ID)
    }

    @Test
    fun shouldUpdateLockName() = runTest {
        LockOperationsResourceImpl.updateLockName(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun shouldUpdateLockFavourite() = runTest {
        LockOperationsResourceImpl.updateLockFavourite(DEFAULT_LOCK_ID, false)
    }

    @Test
    fun shouldUpdateLockColour() = runTest {
        LockOperationsResourceImpl.updateLockColour(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun shouldUpdateLockSettingDefaultName() = runTest {
        LockOperationsResourceImpl.updateLockSettingDefaultName(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun shouldSetLockSettingPermittedAddresses() = runTest {
        LockOperationsResourceImpl.setLockSettingPermittedAddresses(DEFAULT_LOCK_ID, listOf("1.1.1.1"))
    }

    @Test
    fun shouldUpdateLockSettingHidden() = runTest {
        LockOperationsResourceImpl.updateLockSettingHidden(DEFAULT_LOCK_ID, true)
    }

    @Test
    fun shouldSetLockSettingTimeRestrictions() = runTest {
        LockOperationsResourceImpl.setLockSettingTimeRestrictions(DEFAULT_LOCK_ID, emptyList())
    }

    @Test
    fun shouldUpdateLockSettingLocationRestrictions() = runTest {
        LockOperationsResourceImpl.updateLockSettingLocationRestrictions(DEFAULT_LOCK_ID, null)
    }

    @Test
    fun shouldGetUserPublicKey() = runTest {
        LockOperationsResourceImpl.getUserPublicKey(DEFAULT_USER_EMAIL)
    }

    @Test
    fun shouldGetUserPublicKeyByEmail() = runTest {
        LockOperationsResourceImpl.getUserPublicKeyByEmail("")
    }

    @Test
    fun shouldGetUserPublicKeyByTelephone() = runTest {
        LockOperationsResourceImpl.getUserPublicKeyByTelephone("")
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKey() = runTest {
        LockOperationsResourceImpl.getUserPublicKeyByLocalKey("")
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKey() = runTest {
        LockOperationsResourceImpl.getUserPublicKeyByForeignKey("")
    }

    @Test
    fun shouldGetUserPublicKeyByIdentity() = runTest {
        LockOperationsResourceImpl.getUserPublicKeyByIdentity("")
    }

    @Test
    fun shouldGetUserPublicKeyByEmails() = runTest {
        LockOperationsResourceImpl.getUserPublicKeyByEmails(listOf("", ""))
    }

    @Test
    fun shouldGetUserPublicKeyByTelephones() = runTest {
        LockOperationsResourceImpl.getUserPublicKeyByTelephones(listOf("", ""))
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeys() = runTest {
        LockOperationsResourceImpl.getUserPublicKeyByLocalKeys(listOf("", ""))
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKeys() = runTest {
        LockOperationsResourceImpl.getUserPublicKeyByForeignKeys(listOf("", ""))
    }

    @Test
    fun shouldUnlockUsingContext() = runTest {
        LockOperationsResourceImpl.unlock(LockOperations.UnlockOperation(LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID)))
    }

    @Test
    fun shouldUnlock() = runTest {
        LockOperationsResourceImpl.unlock(LockOperations.UnlockOperation(LockOperations.BaseOperation("userId", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID)))
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
    fun shouldShareLock() = runTest {
        LockOperationsResourceImpl.shareLock(
            LockOperations.ShareLockOperation(
                baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
                shareLock = LockOperations.ShareLock("", UserRole.USER, byteArrayOf())
        ))
    }

    @Test
    fun shouldRevokeAccessToLockUsingContext() = runTest {
        LockOperationsResourceImpl.revokeAccessToLock(LockOperations.RevokeAccessToLockOperation(
            baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
            users = emptyList()
        ))
    }

    @Test
    fun shouldRevokeAccessToLock() = runTest {
        LockOperationsResourceImpl.revokeAccessToLock(LockOperations.RevokeAccessToLockOperation(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            users = emptyList()
        ))
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDurationUsingContext() = runTest {
        LockOperationsResourceImpl.updateSecureSettingUnlockDuration(LockOperations.UpdateSecureSettingUnlockDuration(
            baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
            unlockDuration = 0
        ))
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDuration() = runTest {
        LockOperationsResourceImpl.updateSecureSettingUnlockDuration(LockOperations.UpdateSecureSettingUnlockDuration(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            unlockDuration = 0
        ))
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetweenUsingContext() = runTest {
        LockOperationsResourceImpl.updateSecureSettingUnlockBetween(LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = LockOperations.BaseOperation(lockId =DEFAULT_LOCK_ID),
            unlockBetween = null
        ))
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetween() = runTest {
        LockOperationsResourceImpl.updateSecureSettingUnlockBetween(LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            unlockBetween = null
        ))
    }

    @Test
    fun shouldGetPinnedLocks() = runTest {
        LockOperationsResourceImpl.getPinnedLocks()
    }

    @Test
    fun shouldGetShareableLocks() = runTest {
        LockOperationsResourceImpl.getShareableLocks()
    }
}