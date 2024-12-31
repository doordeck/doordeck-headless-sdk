package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MockTest
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_LOCK_ID
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_USER_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.api.model.BaseOperationData
import com.doordeck.multiplatform.sdk.api.model.BatchShareLockOperationData
import com.doordeck.multiplatform.sdk.api.model.GetAuditForUserData
import com.doordeck.multiplatform.sdk.api.model.GetLockAuditTrailData
import com.doordeck.multiplatform.sdk.api.model.GetLocksForUserData
import com.doordeck.multiplatform.sdk.api.model.GetSingleLockData
import com.doordeck.multiplatform.sdk.api.model.GetUserPublicKeyByEmailData
import com.doordeck.multiplatform.sdk.api.model.GetUserPublicKeyByEmailsData
import com.doordeck.multiplatform.sdk.api.model.GetUserPublicKeyByForeignKeyData
import com.doordeck.multiplatform.sdk.api.model.GetUserPublicKeyByForeignKeysData
import com.doordeck.multiplatform.sdk.api.model.GetUserPublicKeyByIdentityData
import com.doordeck.multiplatform.sdk.api.model.GetUserPublicKeyByLocalKeyData
import com.doordeck.multiplatform.sdk.api.model.GetUserPublicKeyByLocalKeysData
import com.doordeck.multiplatform.sdk.api.model.GetUserPublicKeyByTelephoneData
import com.doordeck.multiplatform.sdk.api.model.GetUserPublicKeyByTelephonesData
import com.doordeck.multiplatform.sdk.api.model.GetUserPublicKeyData
import com.doordeck.multiplatform.sdk.api.model.GetUsersForLockData
import com.doordeck.multiplatform.sdk.api.model.LockOperations
import com.doordeck.multiplatform.sdk.api.model.RevokeAccessToLockOperationData
import com.doordeck.multiplatform.sdk.api.model.SetLockSettingPermittedAddressesData
import com.doordeck.multiplatform.sdk.api.model.SetLockSettingTimeRestrictionsData
import com.doordeck.multiplatform.sdk.api.model.ShareLockData
import com.doordeck.multiplatform.sdk.api.model.ShareLockOperationData
import com.doordeck.multiplatform.sdk.api.model.UnlockOperationData
import com.doordeck.multiplatform.sdk.api.model.UpdateLockColourData
import com.doordeck.multiplatform.sdk.api.model.UpdateLockFavouriteData
import com.doordeck.multiplatform.sdk.api.model.UpdateLockNameData
import com.doordeck.multiplatform.sdk.api.model.UpdateLockSettingDefaultNameData
import com.doordeck.multiplatform.sdk.api.model.UpdateLockSettingHiddenData
import com.doordeck.multiplatform.sdk.api.model.UpdateLockSettingLocationRestrictionsData
import com.doordeck.multiplatform.sdk.api.model.UpdateSecureSettingUnlockBetweenData
import com.doordeck.multiplatform.sdk.api.model.UpdateSecureSettingUnlockDurationData
import com.doordeck.multiplatform.sdk.api.model.UserRole
import com.doordeck.multiplatform.sdk.internal.api.LockOperationsResourceImpl
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class LockOperationsResourceImplTest : MockTest() {

    @Test
    fun shouldGetSingleLock() = runTest {
        LockOperationsResourceImpl.getSingleLock(DEFAULT_LOCK_ID)
    }

    @Test
    fun shouldGetSingleLockJson() = runTest {
        LockOperationsResourceImpl.getSingleLockJson(GetSingleLockData(DEFAULT_LOCK_ID).toJson())
    }

    @Test
    fun shouldGetLockAuditTrail() = runTest {
        LockOperationsResourceImpl.getLockAuditTrail(DEFAULT_LOCK_ID, 0, 0)
    }

    @Test
    fun shouldGetLockAuditTrailJson() = runTest {
        LockOperationsResourceImpl.getLockAuditTrailJson(GetLockAuditTrailData(DEFAULT_LOCK_ID, 0, 0).toJson())
    }

    @Test
    fun shouldGetAuditForUser() = runTest {
        LockOperationsResourceImpl.getAuditForUser(DEFAULT_USER_ID, 0, 0)
    }

    @Test
    fun shouldGetAuditForUserJson() = runTest {
        LockOperationsResourceImpl.getAuditForUserJson(GetAuditForUserData(DEFAULT_USER_ID, 0, 0).toJson())
    }

    @Test
    fun shouldGetUsersForLock() = runTest {
        LockOperationsResourceImpl.getUsersForLock(DEFAULT_LOCK_ID)
    }

    @Test
    fun shouldGetUsersForLockJson() = runTest {
        LockOperationsResourceImpl.getUsersForLockJson(GetUsersForLockData(DEFAULT_LOCK_ID).toJson())
    }

    @Test
    fun shouldGetLocksForUser() = runTest {
        LockOperationsResourceImpl.getLocksForUser(DEFAULT_USER_ID)
    }

    @Test
    fun shouldGetLocksForUserJson() = runTest {
        LockOperationsResourceImpl.getLocksForUserJson(GetLocksForUserData(DEFAULT_USER_ID).toJson())
    }

    @Test
    fun shouldUpdateLockName() = runTest {
        LockOperationsResourceImpl.updateLockName(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun shouldUpdateLockNameJson() = runTest {
        LockOperationsResourceImpl.updateLockNameJson(UpdateLockNameData(DEFAULT_LOCK_ID, "").toJson())
    }

    @Test
    fun shouldUpdateLockFavourite() = runTest {
        LockOperationsResourceImpl.updateLockFavourite(DEFAULT_LOCK_ID, false)
    }

    @Test
    fun shouldUpdateLockFavouriteJson() = runTest {
        LockOperationsResourceImpl.updateLockFavouriteJson(UpdateLockFavouriteData(DEFAULT_LOCK_ID, false).toJson())
    }

    @Test
    fun shouldUpdateLockColour() = runTest {
        LockOperationsResourceImpl.updateLockColour(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun shouldUpdateLockColourJson() = runTest {
        LockOperationsResourceImpl.updateLockColourJson(UpdateLockColourData(DEFAULT_LOCK_ID, "").toJson())
    }

    @Test
    fun shouldUpdateLockSettingDefaultName() = runTest {
        LockOperationsResourceImpl.updateLockSettingDefaultName(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun shouldUpdateLockSettingDefaultNameJson() = runTest {
        LockOperationsResourceImpl.updateLockSettingDefaultNameJson(UpdateLockSettingDefaultNameData(DEFAULT_LOCK_ID, "").toJson())
    }

    @Test
    fun shouldSetLockSettingPermittedAddresses() = runTest {
        LockOperationsResourceImpl.setLockSettingPermittedAddresses(DEFAULT_LOCK_ID, listOf("1.1.1.1"))
    }

    @Test
    fun shouldSetLockSettingPermittedAddressesJson() = runTest {
        LockOperationsResourceImpl.setLockSettingPermittedAddressesJson(SetLockSettingPermittedAddressesData(DEFAULT_LOCK_ID, listOf("1.1.1.1")).toJson())
    }

    @Test
    fun shouldUpdateLockSettingHidden() = runTest {
        LockOperationsResourceImpl.updateLockSettingHidden(DEFAULT_LOCK_ID, true)
    }

    @Test
    fun shouldUpdateLockSettingHiddenJson() = runTest {
        LockOperationsResourceImpl.updateLockSettingHiddenJson(UpdateLockSettingHiddenData(DEFAULT_LOCK_ID, true).toJson())
    }

    @Test
    fun shouldSetLockSettingTimeRestrictions() = runTest {
        LockOperationsResourceImpl.setLockSettingTimeRestrictions(DEFAULT_LOCK_ID, emptyList())
    }

    @Test
    fun shouldSetLockSettingTimeRestrictionsJson() = runTest {
        LockOperationsResourceImpl.setLockSettingTimeRestrictionsJson(SetLockSettingTimeRestrictionsData(DEFAULT_LOCK_ID, emptyList()).toJson())
    }

    @Test
    fun shouldUpdateLockSettingLocationRestrictions() = runTest {
        LockOperationsResourceImpl.updateLockSettingLocationRestrictions(DEFAULT_LOCK_ID, null)
    }

    @Test
    fun shouldUpdateLockSettingLocationRestrictionsJson() = runTest {
        LockOperationsResourceImpl.updateLockSettingLocationRestrictionsJson(UpdateLockSettingLocationRestrictionsData(DEFAULT_LOCK_ID, null).toJson())
    }

    @Test
    fun shouldGetUserPublicKey() = runTest {
        LockOperationsResourceImpl.getUserPublicKey(DEFAULT_USER_EMAIL)
    }

    @Test
    fun shouldGetUserPublicKeyJson() = runTest {
        LockOperationsResourceImpl.getUserPublicKeyJson(GetUserPublicKeyData(DEFAULT_USER_EMAIL).toJson())
    }

    @Test
    fun shouldGetUserPublicKeyByEmail() = runTest {
        LockOperationsResourceImpl.getUserPublicKeyByEmail("")
    }

    @Test
    fun shouldGetUserPublicKeyByEmailJson() = runTest {
        LockOperationsResourceImpl.getUserPublicKeyByEmailJson(GetUserPublicKeyByEmailData("").toJson())
    }

    @Test
    fun shouldGetUserPublicKeyByTelephone() = runTest {
        LockOperationsResourceImpl.getUserPublicKeyByTelephone("")
    }

    @Test
    fun shouldGetUserPublicKeyByTelephoneJson() = runTest {
        LockOperationsResourceImpl.getUserPublicKeyByTelephoneJson(GetUserPublicKeyByTelephoneData("").toJson())
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKey() = runTest {
        LockOperationsResourceImpl.getUserPublicKeyByLocalKey("")
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeyJson() = runTest {
        LockOperationsResourceImpl.getUserPublicKeyByLocalKeyJson(GetUserPublicKeyByLocalKeyData("").toJson())
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKey() = runTest {
        LockOperationsResourceImpl.getUserPublicKeyByForeignKey("")
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKeyJson() = runTest {
        LockOperationsResourceImpl.getUserPublicKeyByForeignKeyJson(GetUserPublicKeyByForeignKeyData("").toJson())
    }

    @Test
    fun shouldGetUserPublicKeyByIdentity() = runTest {
        LockOperationsResourceImpl.getUserPublicKeyByIdentity("")
    }

    @Test
    fun shouldGetUserPublicKeyByIdentityJson() = runTest {
        LockOperationsResourceImpl.getUserPublicKeyByIdentityJson(GetUserPublicKeyByIdentityData("").toJson())
    }

    @Test
    fun shouldGetUserPublicKeyByEmails() = runTest {
        LockOperationsResourceImpl.getUserPublicKeyByEmails(listOf("", ""))
    }

    @Test
    fun shouldGetUserPublicKeyByEmailsJson() = runTest {
        LockOperationsResourceImpl.getUserPublicKeyByEmailsJson(GetUserPublicKeyByEmailsData(listOf("", "")).toJson())
    }

    @Test
    fun shouldGetUserPublicKeyByTelephones() = runTest {
        LockOperationsResourceImpl.getUserPublicKeyByTelephones(listOf("", ""))
    }

    @Test
    fun shouldGetUserPublicKeyByTelephonesJson() = runTest {
        LockOperationsResourceImpl.getUserPublicKeyByTelephonesJson(GetUserPublicKeyByTelephonesData(listOf("", "")).toJson())
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeys() = runTest {
        LockOperationsResourceImpl.getUserPublicKeyByLocalKeys(listOf("", ""))
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeysJson() = runTest {
        LockOperationsResourceImpl.getUserPublicKeyByLocalKeysJson(GetUserPublicKeyByLocalKeysData(listOf("", "")).toJson())
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKeys() = runTest {
        LockOperationsResourceImpl.getUserPublicKeyByForeignKeys(listOf("", ""))
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKeysJson() = runTest {
        LockOperationsResourceImpl.getUserPublicKeyByForeignKeysJson(GetUserPublicKeyByForeignKeysData(listOf("", "")).toJson())
    }

    @Test
    fun shouldUnlockUsingContext() = runTest {
        LockOperationsResourceImpl.unlock(LockOperations.UnlockOperation(LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID)))
    }

    @Test
    fun shouldUnlockUsingContextJson() = runTest {
        LockOperationsResourceImpl.unlockJson(UnlockOperationData(BaseOperationData(lockId = DEFAULT_LOCK_ID)).toJson())
    }

    @Test
    fun shouldUnlock() = runTest {
        LockOperationsResourceImpl.unlock(LockOperations.UnlockOperation(LockOperations.BaseOperation("userId", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID)))
    }

    @Test
    fun shouldUnlockJson() = runTest {
        LockOperationsResourceImpl.unlockJson(UnlockOperationData(BaseOperationData("userId", emptyList(), TEST_MAIN_USER_PRIVATE_KEY, DEFAULT_LOCK_ID, 0, 0, 0, "")).toJson())
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
    fun shouldShareLockUsingContextJson() = runTest {
        LockOperationsResourceImpl.shareLockJson(ShareLockOperationData(
            baseOperation = BaseOperationData(lockId = DEFAULT_LOCK_ID),
            shareLock = ShareLockData("", UserRole.USER, byteArrayOf().encodeByteArrayToBase64())
        ).toJson())
    }

    @Test
    fun shouldBatchShareLockUsingContext() = runTest {
        LockOperationsResourceImpl.batchShareLock(
            LockOperations.BatchShareLockOperation(
                baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
                users = listOf(LockOperations.ShareLock("", UserRole.USER, byteArrayOf()))
            ))
    }

    @Test
    fun shouldBatchShareLockUsingContextJson() = runTest {
        LockOperationsResourceImpl.batchShareLockJson(BatchShareLockOperationData(
            baseOperation = BaseOperationData(lockId = DEFAULT_LOCK_ID),
            users = listOf(ShareLockData("", UserRole.USER, byteArrayOf().encodeByteArrayToBase64()))
        ).toJson())
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
    fun shouldShareLockJson() = runTest {
        LockOperationsResourceImpl.shareLockJson(ShareLockOperationData(
            baseOperation = BaseOperationData("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY, DEFAULT_LOCK_ID, 0, 0, 0, ""),
            shareLock = ShareLockData("", UserRole.USER, byteArrayOf().encodeByteArrayToBase64())
        ).toJson())
    }

    @Test
    fun shouldBatchShareLock() = runTest {
        LockOperationsResourceImpl.batchShareLock(
            LockOperations.BatchShareLockOperation(
                baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
                users = listOf(LockOperations.ShareLock("", UserRole.USER, byteArrayOf()))
            ))
    }

    @Test
    fun shouldBatchShareLockJson() = runTest {
        LockOperationsResourceImpl.batchShareLockJson(BatchShareLockOperationData(
            baseOperation = BaseOperationData("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY, DEFAULT_LOCK_ID, 0, 0, 0, ""),
            users = listOf(ShareLockData("", UserRole.USER, byteArrayOf().encodeByteArrayToBase64()))
        ).toJson())
    }

    @Test
    fun shouldRevokeAccessToLockUsingContext() = runTest {
        LockOperationsResourceImpl.revokeAccessToLock(LockOperations.RevokeAccessToLockOperation(
            baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
            users = emptyList()
        ))
    }

    @Test
    fun shouldRevokeAccessToLockUsingContextJson() = runTest {
        LockOperationsResourceImpl.revokeAccessToLockJson(RevokeAccessToLockOperationData(
            baseOperation = BaseOperationData(lockId = DEFAULT_LOCK_ID),
            users = emptyList()
        ).toJson())
    }

    @Test
    fun shouldRevokeAccessToLock() = runTest {
        LockOperationsResourceImpl.revokeAccessToLock(LockOperations.RevokeAccessToLockOperation(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            users = emptyList()
        ))
    }

    @Test
    fun shouldRevokeAccessToLockJson() = runTest {
        LockOperationsResourceImpl.revokeAccessToLockJson(RevokeAccessToLockOperationData(
            baseOperation = BaseOperationData("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY, DEFAULT_LOCK_ID, 0, 0, 0, ""),
            users = emptyList()
        ).toJson())
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDurationUsingContext() = runTest {
        LockOperationsResourceImpl.updateSecureSettingUnlockDuration(
            LockOperations.UpdateSecureSettingUnlockDuration(
                baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
                unlockDuration = 0
            ))
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDurationUsingContextJson() = runTest {
        LockOperationsResourceImpl.updateSecureSettingUnlockDurationJson(UpdateSecureSettingUnlockDurationData(
            baseOperation = BaseOperationData(lockId = DEFAULT_LOCK_ID),
            unlockDuration = 0
        ).toJson())
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDuration() = runTest {
        LockOperationsResourceImpl.updateSecureSettingUnlockDuration(
            LockOperations.UpdateSecureSettingUnlockDuration(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            unlockDuration = 0
        ))
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDurationJson() = runTest {
        LockOperationsResourceImpl.updateSecureSettingUnlockDurationJson(UpdateSecureSettingUnlockDurationData(
            baseOperation = BaseOperationData("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY, DEFAULT_LOCK_ID, 0, 0 , 0, ""),
            unlockDuration = 0
        ).toJson())
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetweenUsingContext() = runTest {
        LockOperationsResourceImpl.updateSecureSettingUnlockBetween(
            LockOperations.UpdateSecureSettingUnlockBetween(
                baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
                unlockBetween = null
            ))
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetweenUsingContextJson() = runTest {
        LockOperationsResourceImpl.updateSecureSettingUnlockBetweenJson(UpdateSecureSettingUnlockBetweenData(
            baseOperation = BaseOperationData(lockId = DEFAULT_LOCK_ID),
            unlockBetween = null
        ).toJson())
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetween() = runTest {
        LockOperationsResourceImpl.updateSecureSettingUnlockBetween(
            LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            unlockBetween = null
        ))
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetweenJson() = runTest {
        LockOperationsResourceImpl.updateSecureSettingUnlockBetweenJson(UpdateSecureSettingUnlockBetweenData(
            baseOperation = BaseOperationData("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY, DEFAULT_LOCK_ID, 0, 0, 0, ""),
            unlockBetween = null
        ).toJson())
    }

    @Test
    fun shouldGetPinnedLocks() = runTest {
        LockOperationsResourceImpl.getPinnedLocks()
    }

    @Test
    fun shouldGetPinnedLocksJson() = runTest {
        LockOperationsResourceImpl.getPinnedLocksJson()
    }

    @Test
    fun shouldGetShareableLocks() = runTest {
        LockOperationsResourceImpl.getShareableLocks()
    }

    @Test
    fun shouldGetShareableLocksJson() = runTest {
        LockOperationsResourceImpl.getShareableLocksJson()
    }
}