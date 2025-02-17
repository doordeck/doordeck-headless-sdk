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
import com.doordeck.multiplatform.sdk.model.data.BaseOperationData
import com.doordeck.multiplatform.sdk.model.data.BatchShareLockOperationData
import com.doordeck.multiplatform.sdk.model.data.GetAuditForUserData
import com.doordeck.multiplatform.sdk.model.data.GetLockAuditTrailData
import com.doordeck.multiplatform.sdk.model.data.GetLocksForUserData
import com.doordeck.multiplatform.sdk.model.data.GetSingleLockData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyByEmailData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyByEmailsData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyByForeignKeyData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyByForeignKeysData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyByIdentityData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyByLocalKeyData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyByLocalKeysData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyByTelephoneData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyByTelephonesData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyData
import com.doordeck.multiplatform.sdk.model.data.GetUsersForLockData
import com.doordeck.multiplatform.sdk.model.data.LockOperations
import com.doordeck.multiplatform.sdk.model.data.RevokeAccessToLockOperationData
import com.doordeck.multiplatform.sdk.model.data.SetLockSettingPermittedAddressesData
import com.doordeck.multiplatform.sdk.model.data.SetLockSettingTimeRestrictionsData
import com.doordeck.multiplatform.sdk.model.data.ShareLockData
import com.doordeck.multiplatform.sdk.model.data.ShareLockOperationData
import com.doordeck.multiplatform.sdk.model.data.UnlockOperationData
import com.doordeck.multiplatform.sdk.model.data.UpdateLockColourData
import com.doordeck.multiplatform.sdk.model.data.UpdateLockFavouriteData
import com.doordeck.multiplatform.sdk.model.data.UpdateLockNameData
import com.doordeck.multiplatform.sdk.model.data.UpdateLockSettingDefaultNameData
import com.doordeck.multiplatform.sdk.model.data.UpdateLockSettingHiddenData
import com.doordeck.multiplatform.sdk.model.data.UpdateLockSettingLocationRestrictionsData
import com.doordeck.multiplatform.sdk.model.data.UpdateSecureSettingUnlockBetweenData
import com.doordeck.multiplatform.sdk.model.data.UpdateSecureSettingUnlockDurationData
import com.doordeck.multiplatform.sdk.model.common.CapabilityStatus
import com.doordeck.multiplatform.sdk.model.common.CapabilityType
import com.doordeck.multiplatform.sdk.model.common.UserRole
import com.doordeck.multiplatform.sdk.toResultDataJson
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.toJson
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
    fun shouldGetSingleLockJson() = runTest {
        val response = LockOperationsApi.getSingleLockJson(GetSingleLockData(DEFAULT_LOCK_ID).toJson())
        assertEquals(LOCK_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetLockAuditTrail() = runTest {
        val response = LockOperationsApi.getLockAuditTrail(DEFAULT_LOCK_ID, 0, 0)
        assertEquals(AUDIT_RESPONSE, response)
    }

    @Test
    fun shouldGetLockAuditTrailJson() = runTest {
        val response = LockOperationsApi.getLockAuditTrailJson(GetLockAuditTrailData(DEFAULT_LOCK_ID, 0, 0).toJson())
        assertEquals(AUDIT_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetAuditForUser() = runTest {
        val response = LockOperationsApi.getAuditForUser(DEFAULT_USER_ID, 0, 0)
        assertEquals(AUDIT_RESPONSE, response)
    }

    @Test
    fun shouldGetAuditForUserJson() = runTest {
        val response = LockOperationsApi.getAuditForUserJson(GetAuditForUserData(DEFAULT_USER_ID, 0, 0).toJson())
        assertEquals(AUDIT_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetUsersForLock() = runTest {
        val response = LockOperationsApi.getUsersForLock(DEFAULT_LOCK_ID)
        assertEquals(USER_LOCK_RESPONSE, response)
    }

    @Test
    fun shouldGetUsersForLockJson() = runTest {
        val response = LockOperationsApi.getUsersForLockJson(GetUsersForLockData(DEFAULT_LOCK_ID).toJson())
        assertEquals(USER_LOCK_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetLocksForUser() = runTest {
        val response = LockOperationsApi.getLocksForUser(DEFAULT_USER_ID)
        assertEquals(LOCK_USER_RESPONSE, response)
    }

    @Test
    fun shouldGetLocksForUserJson() = runTest {
        val response = LockOperationsApi.getLocksForUserJson(GetLocksForUserData(DEFAULT_USER_ID).toJson())
        assertEquals(LOCK_USER_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldUpdateLockName() = runTest {
        LockOperationsApi.updateLockName(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun shouldUpdateLockNameJson() = runTest {
        LockOperationsApi.updateLockNameJson(UpdateLockNameData(DEFAULT_LOCK_ID, "").toJson())
    }

    @Test
    fun shouldUpdateLockFavourite() = runTest {
        LockOperationsApi.updateLockFavourite(DEFAULT_LOCK_ID, false)
    }

    @Test
    fun shouldUpdateLockFavouriteJson() = runTest {
        LockOperationsApi.updateLockFavouriteJson(UpdateLockFavouriteData(DEFAULT_LOCK_ID, false).toJson())
    }

    @Test
    fun shouldUpdateLockColour() = runTest {
        LockOperationsApi.updateLockColour(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun shouldUpdateLockColourJson() = runTest {
        LockOperationsApi.updateLockColourJson(UpdateLockColourData(DEFAULT_LOCK_ID, "").toJson())
    }

    @Test
    fun shouldUpdateLockSettingDefaultName() = runTest {
        LockOperationsApi.updateLockSettingDefaultName(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun shouldUpdateLockSettingDefaultNameJson() = runTest {
        LockOperationsApi.updateLockSettingDefaultNameJson(UpdateLockSettingDefaultNameData(DEFAULT_LOCK_ID, "").toJson())
    }

    @Test
    fun shouldSetLockSettingPermittedAddresses() = runTest {
        LockOperationsApi.setLockSettingPermittedAddresses(DEFAULT_LOCK_ID, listOf("1.1.1.1"))
    }

    @Test
    fun shouldSetLockSettingPermittedAddressesJson() = runTest {
        LockOperationsApi.setLockSettingPermittedAddressesJson(SetLockSettingPermittedAddressesData(DEFAULT_LOCK_ID, listOf("1.1.1.1")).toJson())
    }

    @Test
    fun shouldUpdateLockSettingHidden() = runTest {
        LockOperationsApi.updateLockSettingHidden(DEFAULT_LOCK_ID, true)
    }

    @Test
    fun shouldUpdateLockSettingHiddenJson() = runTest {
        LockOperationsApi.updateLockSettingHiddenJson(UpdateLockSettingHiddenData(DEFAULT_LOCK_ID, true).toJson())
    }

    @Test
    fun shouldSetLockSettingTimeRestrictions() = runTest {
        LockOperationsApi.setLockSettingTimeRestrictions(DEFAULT_LOCK_ID, emptyList())
    }

    @Test
    fun shouldSetLockSettingTimeRestrictionsJson() = runTest {
        LockOperationsApi.setLockSettingTimeRestrictionsJson(SetLockSettingTimeRestrictionsData(DEFAULT_LOCK_ID, emptyList()).toJson())
    }

    @Test
    fun shouldUpdateLockSettingLocationRestrictions() = runTest {
        LockOperationsApi.updateLockSettingLocationRestrictions(DEFAULT_LOCK_ID, null)
    }

    @Test
    fun shouldUpdateLockSettingLocationRestrictionsJson() = runTest {
        LockOperationsApi.updateLockSettingLocationRestrictionsJson(UpdateLockSettingLocationRestrictionsData(DEFAULT_LOCK_ID, null).toJson())
    }

    @Test
    fun shouldGetUserPublicKey() = runTest {
        val response = LockOperationsApi.getUserPublicKey(DEFAULT_USER_EMAIL)
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyJson() = runTest {
        val response = LockOperationsApi.getUserPublicKeyJson(GetUserPublicKeyData(DEFAULT_USER_EMAIL).toJson())
        assertEquals(USER_PUBLIC_KEY_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetUserPublicKeyByEmail() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByEmail("")
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByEmailJson() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByEmailJson(GetUserPublicKeyByEmailData("").toJson())
        assertEquals(USER_PUBLIC_KEY_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetUserPublicKeyByTelephone() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByTelephone("")
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByTelephoneJson() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByTelephoneJson(GetUserPublicKeyByTelephoneData("").toJson())
        assertEquals(USER_PUBLIC_KEY_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKey() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByLocalKey("")
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeyJson() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByLocalKeyJson(GetUserPublicKeyByLocalKeyData("").toJson())
        assertEquals(USER_PUBLIC_KEY_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKey() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByForeignKey("")
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKeyJson() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByForeignKeyJson(GetUserPublicKeyByForeignKeyData("").toJson())
        assertEquals(USER_PUBLIC_KEY_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetUserPublicKeyByIdentity() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByIdentity("")
        assertEquals(USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByIdentityJson() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByIdentityJson(GetUserPublicKeyByIdentityData("").toJson())
        assertEquals(USER_PUBLIC_KEY_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetUserPublicKeyByEmails() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByEmails(listOf("", ""))
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByEmailsJson() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByEmailsJson(GetUserPublicKeyByEmailsData(listOf("", "")).toJson())
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetUserPublicKeyByTelephones() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByTelephones(listOf("", ""))
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByTelephonesJson() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByTelephonesJson(GetUserPublicKeyByTelephonesData(listOf("", "")).toJson())
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeys() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByLocalKeys(listOf("", ""))
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeysJson() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByLocalKeysJson(GetUserPublicKeyByLocalKeysData(listOf("", "")).toJson())
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKeys() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByForeignKeys(listOf("", ""))
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE, response)
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKeysJson() = runTest {
        val response = LockOperationsApi.getUserPublicKeyByForeignKeysJson(GetUserPublicKeyByForeignKeysData(listOf("", "")).toJson())
        assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldUnlockUsingContext() = runTest {
        LockOperationsApi.unlock(LockOperations.UnlockOperation(LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID)))
    }

    @Test
    fun shouldUnlockUsingContextJson() = runTest {
        LockOperationsApi.unlockJson(UnlockOperationData(BaseOperationData(lockId = DEFAULT_LOCK_ID)).toJson())
    }

    @Test
    fun shouldUnlock() = runTest {
        LockOperationsApi.unlock(LockOperations.UnlockOperation(LockOperations.BaseOperation("userId", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID)))
    }

    @Test
    fun shouldUnlockJson() = runTest {
        LockOperationsApi.unlockJson(UnlockOperationData(BaseOperationData("userId", emptyList(), TEST_MAIN_USER_PRIVATE_KEY, DEFAULT_LOCK_ID, 0, 0, 0, "")).toJson())
    }

    @Test
    fun shouldShareLockUsingContext() = runTest {
        LockOperationsApi.shareLock(
            LockOperations.ShareLockOperation(
                baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
                shareLock = LockOperations.ShareLock("", UserRole.USER, byteArrayOf())
            ))
    }

    @Test
    fun shouldShareLockUsingContextJson() = runTest {
        LockOperationsApi.shareLockJson(
            ShareLockOperationData(
            baseOperation = BaseOperationData(lockId = DEFAULT_LOCK_ID),
            shareLock = ShareLockData("", UserRole.USER, byteArrayOf().encodeByteArrayToBase64())
        ).toJson())
    }

    @Test
    fun shouldBatchShareLockUsingContext() = runTest {
        CapabilityCache.put(DEFAULT_LOCK_ID, mapOf(CapabilityType.BATCH_SHARING_25 to CapabilityStatus.SUPPORTED))
        LockOperationsApi.batchShareLock(
            LockOperations.BatchShareLockOperation(
                baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
                users = listOf(LockOperations.ShareLock("", UserRole.USER, byteArrayOf()))
            ))
    }

    @Test
    fun shouldBatchShareLockUsingContextJson() = runTest {
        CapabilityCache.put(DEFAULT_LOCK_ID, mapOf(CapabilityType.BATCH_SHARING_25 to CapabilityStatus.SUPPORTED))
        LockOperationsApi.batchShareLockJson(
            BatchShareLockOperationData(
            baseOperation = BaseOperationData(lockId = DEFAULT_LOCK_ID),
            users = listOf(ShareLockData("", UserRole.USER, byteArrayOf().encodeByteArrayToBase64()))
        ).toJson())
    }

    @Test
    fun shouldShareLock() = runTest {
        LockOperationsApi.shareLock(
            LockOperations.ShareLockOperation(
                baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
                shareLock = LockOperations.ShareLock("", UserRole.USER, byteArrayOf())
            ))
    }

    @Test
    fun shouldShareLockJson() = runTest {
        LockOperationsApi.shareLockJson(
            ShareLockOperationData(
            baseOperation = BaseOperationData("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY, DEFAULT_LOCK_ID, 0, 0, 0, ""),
            shareLock = ShareLockData("", UserRole.USER, byteArrayOf().encodeByteArrayToBase64())
        ).toJson())
    }

    @Test
    fun shouldBatchShareLock() = runTest {
        CapabilityCache.put(DEFAULT_LOCK_ID, mapOf(CapabilityType.BATCH_SHARING_25 to CapabilityStatus.SUPPORTED))
        LockOperationsApi.batchShareLock(
            LockOperations.BatchShareLockOperation(
                baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
                users = listOf(LockOperations.ShareLock("", UserRole.USER, byteArrayOf()))
            ))
    }

    @Test
    fun shouldBatchShareLockJson() = runTest {
        CapabilityCache.put(DEFAULT_LOCK_ID, mapOf(CapabilityType.BATCH_SHARING_25 to CapabilityStatus.SUPPORTED))
        LockOperationsApi.batchShareLockJson(
            BatchShareLockOperationData(
            baseOperation = BaseOperationData("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY, DEFAULT_LOCK_ID, 0, 0, 0, ""),
            users = listOf(ShareLockData("", UserRole.USER, byteArrayOf().encodeByteArrayToBase64()))
        ).toJson())
    }

    @Test
    fun shouldRevokeAccessToLockUsingContext() = runTest {
        LockOperationsApi.revokeAccessToLock(
            LockOperations.RevokeAccessToLockOperation(
            baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
            users = emptyList()
        ))
    }

    @Test
    fun shouldRevokeAccessToLockUsingContextJson() = runTest {
        LockOperationsApi.revokeAccessToLockJson(
            RevokeAccessToLockOperationData(
            baseOperation = BaseOperationData(lockId = DEFAULT_LOCK_ID),
            users = emptyList()
        ).toJson())
    }

    @Test
    fun shouldRevokeAccessToLock() = runTest {
        LockOperationsApi.revokeAccessToLock(
            LockOperations.RevokeAccessToLockOperation(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            users = emptyList()
        ))
    }

    @Test
    fun shouldRevokeAccessToLockJson() = runTest {
        LockOperationsApi.revokeAccessToLockJson(
            RevokeAccessToLockOperationData(
            baseOperation = BaseOperationData("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY, DEFAULT_LOCK_ID, 0, 0, 0, ""),
            users = emptyList()
        ).toJson())
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDurationUsingContext() = runTest {
        LockOperationsApi.updateSecureSettingUnlockDuration(
            LockOperations.UpdateSecureSettingUnlockDuration(
                baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
                unlockDuration = 0
            ))
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDurationUsingContextJson() = runTest {
        LockOperationsApi.updateSecureSettingUnlockDurationJson(
            UpdateSecureSettingUnlockDurationData(
            baseOperation = BaseOperationData(lockId = DEFAULT_LOCK_ID),
            unlockDuration = 0
        ).toJson())
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDuration() = runTest {
        LockOperationsApi.updateSecureSettingUnlockDuration(
            LockOperations.UpdateSecureSettingUnlockDuration(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            unlockDuration = 0
        ))
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDurationJson() = runTest {
        LockOperationsApi.updateSecureSettingUnlockDurationJson(
            UpdateSecureSettingUnlockDurationData(
            baseOperation = BaseOperationData("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY, DEFAULT_LOCK_ID, 0, 0 , 0, ""),
            unlockDuration = 0
        ).toJson())
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetweenUsingContext() = runTest {
        LockOperationsApi.updateSecureSettingUnlockBetween(
            LockOperations.UpdateSecureSettingUnlockBetween(
                baseOperation = LockOperations.BaseOperation(lockId = DEFAULT_LOCK_ID),
                unlockBetween = null
            ))
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetweenUsingContextJson() = runTest {
        LockOperationsApi.updateSecureSettingUnlockBetweenJson(
            UpdateSecureSettingUnlockBetweenData(
            baseOperation = BaseOperationData(lockId = DEFAULT_LOCK_ID),
            unlockBetween = null
        ).toJson())
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetween() = runTest {
        LockOperationsApi.updateSecureSettingUnlockBetween(
            LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            unlockBetween = null
        ))
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetweenJson() = runTest {
        LockOperationsApi.updateSecureSettingUnlockBetweenJson(
            UpdateSecureSettingUnlockBetweenData(
            baseOperation = BaseOperationData("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY, DEFAULT_LOCK_ID, 0, 0, 0, ""),
            unlockBetween = null
        ).toJson())
    }

    @Test
    fun shouldGetPinnedLocks() = runTest {
        val response = LockOperationsApi.getPinnedLocks()
        assertEquals(PINNED_LOCKS_RESPONSE, response)
    }

    @Test
    fun shouldGetPinnedLocksJson() = runTest {
        val response = LockOperationsApi.getPinnedLocksJson()
        assertEquals(PINNED_LOCKS_RESPONSE.toResultDataJson(), response)
    }

    @Test
    fun shouldGetShareableLocks() = runTest {
        val response = LockOperationsApi.getShareableLocks()
        assertEquals(SHAREABLE_LOCKS_RESPONSE, response)
    }

    @Test
    fun shouldGetShareableLocksJson() = runTest {
        val response = LockOperationsApi.getShareableLocksJson()
        assertEquals(SHAREABLE_LOCKS_RESPONSE.toResultDataJson(), response)
    }
}