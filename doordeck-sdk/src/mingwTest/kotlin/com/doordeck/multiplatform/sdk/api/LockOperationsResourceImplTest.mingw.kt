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
import com.doordeck.multiplatform.sdk.api.model.BaseOperationData
import com.doordeck.multiplatform.sdk.api.model.BatchShareLockOperationData
import com.doordeck.multiplatform.sdk.api.model.CapabilityStatus
import com.doordeck.multiplatform.sdk.api.model.CapabilityType
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
import com.doordeck.multiplatform.sdk.cache.CapabilityCache
import com.doordeck.multiplatform.sdk.internal.api.LockOperationsResourceImpl
import com.doordeck.multiplatform.sdk.toResultDataJson
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class LockOperationsResourceImplTest : MockTest() {

    @Test
    fun shouldGetSingleLock() = runTest {
        val response = LockOperationsResourceImpl.getSingleLock(DEFAULT_LOCK_ID)
        assertEquals(LOCK_RESPONSE, response)
    }

    @Test
    fun shouldGetSingleLockJson() = runTest {
        val response = LockOperationsResourceImpl.getSingleLockJson(GetSingleLockData(DEFAULT_LOCK_ID).toJson())
        assertEquals(LOCK_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetLockAuditTrail() = runTest {
        val response = LockOperationsResourceImpl.getLockAuditTrail(DEFAULT_LOCK_ID, 0, 0)
        assertEquals(AUDIT_RESPONSE, response)
    }

    @Test
    fun shouldGetLockAuditTrailJson() = runTest {
        val response = LockOperationsResourceImpl.getLockAuditTrailJson(GetLockAuditTrailData(DEFAULT_LOCK_ID, 0, 0).toJson())
        assertEquals(AUDIT_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetAuditForUser() = runTest {
        val response = LockOperationsResourceImpl.getAuditForUser(DEFAULT_USER_ID, 0, 0)
        assertEquals(AUDIT_RESPONSE, response)
    }

    @Test
    fun shouldGetAuditForUserJson() = runTest {
        val response = LockOperationsResourceImpl.getAuditForUserJson(GetAuditForUserData(DEFAULT_USER_ID, 0, 0).toJson())
        assertEquals(AUDIT_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetUsersForLock() = runTest {
        val response = LockOperationsResourceImpl.getUsersForLock(DEFAULT_LOCK_ID)
        assertEquals(USER_LOCK_RESPONSE, response)
    }

    @Test
    fun shouldGetUsersForLockJson() = runTest {
        val response = LockOperationsResourceImpl.getUsersForLockJson(GetUsersForLockData(DEFAULT_LOCK_ID).toJson())
        assertEquals(USER_LOCK_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetLocksForUser() = runTest {
        val response = LockOperationsResourceImpl.getLocksForUser(DEFAULT_USER_ID)
        assertEquals(LOCK_USER_RESPONSE, response)
    }

    @Test
    fun shouldGetLocksForUserJson() = runTest {
        val response = LockOperationsResourceImpl.getLocksForUserJson(GetLocksForUserData(DEFAULT_USER_ID).toJson())
        assertEquals(LOCK_USER_RESPONSE.toResultDataJson(), response)
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
        val response = LockOperationsResourceImpl.getUserPublicKey(DEFAULT_USER_EMAIL)
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyJson() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyJson(GetUserPublicKeyData(DEFAULT_USER_EMAIL).toJson())
        assertEquals(USER_PUBLIC_KEY_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetUserPublicKeyByEmail() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByEmail("")
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByEmailJson() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByEmailJson(GetUserPublicKeyByEmailData("").toJson())
        assertEquals(USER_PUBLIC_KEY_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetUserPublicKeyByTelephone() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByTelephone("")
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByTelephoneJson() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByTelephoneJson(GetUserPublicKeyByTelephoneData("").toJson())
        assertEquals(USER_PUBLIC_KEY_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKey() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByLocalKey("")
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeyJson() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByLocalKeyJson(GetUserPublicKeyByLocalKeyData("").toJson())
        assertEquals(USER_PUBLIC_KEY_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKey() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByForeignKey("")
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKeyJson() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByForeignKeyJson(GetUserPublicKeyByForeignKeyData("").toJson())
        assertEquals(USER_PUBLIC_KEY_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetUserPublicKeyByIdentity() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByIdentity("")
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByIdentityJson() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByIdentityJson(GetUserPublicKeyByIdentityData("").toJson())
        assertEquals(USER_PUBLIC_KEY_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetUserPublicKeyByEmails() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByEmails(listOf("", ""))
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByEmailsJson() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByEmailsJson(GetUserPublicKeyByEmailsData(listOf("", "")).toJson())
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetUserPublicKeyByTelephones() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByTelephones(listOf("", ""))
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByTelephonesJson() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByTelephonesJson(GetUserPublicKeyByTelephonesData(listOf("", "")).toJson())
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeys() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByLocalKeys(listOf("", ""))
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeysJson() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByLocalKeysJson(GetUserPublicKeyByLocalKeysData(listOf("", "")).toJson())
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKeys() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByForeignKeys(listOf("", ""))
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKeysJson() = runTest {
        val response = LockOperationsResourceImpl.getUserPublicKeyByForeignKeysJson(GetUserPublicKeyByForeignKeysData(listOf("", "")).toJson())
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE.toResultDataJson(), response)
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
        CapabilityCache.put(DEFAULT_LOCK_ID, mapOf(CapabilityType.BATCH_SHARING_25 to CapabilityStatus.SUPPORTED))
        LockOperationsResourceImpl.batchShareLock(
            LockOperations.BatchShareLockOperation(
                baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
                users = listOf(LockOperations.ShareLock("", UserRole.USER, byteArrayOf()))
            ))
    }

    @Test
    fun shouldBatchShareLockUsingContextJson() = runTest {
        CapabilityCache.put(DEFAULT_LOCK_ID, mapOf(CapabilityType.BATCH_SHARING_25 to CapabilityStatus.SUPPORTED))
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
        CapabilityCache.put(DEFAULT_LOCK_ID, mapOf(CapabilityType.BATCH_SHARING_25 to CapabilityStatus.SUPPORTED))
        LockOperationsResourceImpl.batchShareLock(
            LockOperations.BatchShareLockOperation(
                baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
                users = listOf(LockOperations.ShareLock("", UserRole.USER, byteArrayOf()))
            ))
    }

    @Test
    fun shouldBatchShareLockJson() = runTest {
        CapabilityCache.put(DEFAULT_LOCK_ID, mapOf(CapabilityType.BATCH_SHARING_25 to CapabilityStatus.SUPPORTED))
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
        val response = LockOperationsResourceImpl.getPinnedLocks()
        assertEquals(PINNED_LOCKS_RESPONSE, response)
    }

    @Test
    fun shouldGetPinnedLocksJson() = runTest {
        val response = LockOperationsResourceImpl.getPinnedLocksJson()
        assertEquals(PINNED_LOCKS_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetShareableLocks() = runTest {
        val response = LockOperationsResourceImpl.getShareableLocks()
        assertEquals(SHAREABLE_LOCKS_RESPONSE, response)
    }

    @Test
    fun shouldGetShareableLocksJson() = runTest {
        val response = LockOperationsResourceImpl.getShareableLocksJson()
        assertEquals(SHAREABLE_LOCKS_RESPONSE.toResultDataJson(), response)
    }
}