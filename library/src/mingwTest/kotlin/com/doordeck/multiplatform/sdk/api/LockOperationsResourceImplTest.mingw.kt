package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.TEST_HTTP_CLIENT
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_LOCK_ID
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.DEFAULT_USER_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.api.model.BaseOperationData
import com.doordeck.multiplatform.sdk.api.model.GetAuditForUserData
import com.doordeck.multiplatform.sdk.api.model.GetLockAuditTrailData
import com.doordeck.multiplatform.sdk.api.model.GetLocksForUserData
import com.doordeck.multiplatform.sdk.api.model.GetSingleLockData
import com.doordeck.multiplatform.sdk.api.model.GetUserPublicKeyByEmailData
import com.doordeck.multiplatform.sdk.api.model.GetUserPublicKeyByForeignKeyData
import com.doordeck.multiplatform.sdk.api.model.GetUserPublicKeyByIdentityData
import com.doordeck.multiplatform.sdk.api.model.GetUserPublicKeyByLocalKeyData
import com.doordeck.multiplatform.sdk.api.model.GetUserPublicKeyByTelephoneData
import com.doordeck.multiplatform.sdk.api.model.GetUserPublicKeyData
import com.doordeck.multiplatform.sdk.api.model.GetUsersForLockData
import com.doordeck.multiplatform.sdk.api.model.LockOperations
import com.doordeck.multiplatform.sdk.api.model.RevokeAccessToLockOperationData
import com.doordeck.multiplatform.sdk.api.model.RevokeAccessToLockWithContextData
import com.doordeck.multiplatform.sdk.api.model.SetLockSettingPermittedAddressesData
import com.doordeck.multiplatform.sdk.api.model.SetLockSettingTimeRestrictionsData
import com.doordeck.multiplatform.sdk.api.model.ShareLockData
import com.doordeck.multiplatform.sdk.api.model.ShareLockOperationData
import com.doordeck.multiplatform.sdk.api.model.ShareLockWithContextData
import com.doordeck.multiplatform.sdk.api.model.UnlockOperationData
import com.doordeck.multiplatform.sdk.api.model.UnlockWithContextData
import com.doordeck.multiplatform.sdk.api.model.UpdateLockColourData
import com.doordeck.multiplatform.sdk.api.model.UpdateLockFavouriteData
import com.doordeck.multiplatform.sdk.api.model.UpdateLockNameData
import com.doordeck.multiplatform.sdk.api.model.UpdateLockSettingDefaultNameData
import com.doordeck.multiplatform.sdk.api.model.UpdateLockSettingHiddenData
import com.doordeck.multiplatform.sdk.api.model.UpdateLockSettingLocationRestrictionsData
import com.doordeck.multiplatform.sdk.api.model.UpdateSecureSettingUnlockBetweenData
import com.doordeck.multiplatform.sdk.api.model.UpdateSecureSettingUnlockBetweenWithContextData
import com.doordeck.multiplatform.sdk.api.model.UpdateSecureSettingUnlockDurationData
import com.doordeck.multiplatform.sdk.api.model.UpdateSecureSettingUnlockDurationWithContextData
import com.doordeck.multiplatform.sdk.api.model.UserRole
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.LocalUnlockClient
import com.doordeck.multiplatform.sdk.internal.api.LockOperationsResourceImpl
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.toJson
import com.ionspin.kotlin.crypto.LibsodiumInitializer
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class LockOperationsResourceImplTest {

    private val contextManager = ContextManagerImpl()
    private val localUnlock = LocalUnlockClient(TEST_HTTP_CLIENT)
    private val lockOperations = LockOperationsResourceImpl(TEST_HTTP_CLIENT, contextManager, localUnlock)

    init {
        LibsodiumInitializer.initializeWithCallback {  }
        contextManager.setOperationContext("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray())
    }

    @Test
    fun shouldGetSingleLock() = runTest {
        lockOperations.getSingleLock(DEFAULT_LOCK_ID)
    }

    @Test
    fun shouldGetSingleLockJson() = runTest {
        lockOperations.getSingleLockJson(GetSingleLockData(DEFAULT_LOCK_ID).toJson())
    }

    @Test
    fun shouldGetLockAuditTrail() = runTest {
        lockOperations.getLockAuditTrail(DEFAULT_LOCK_ID, 0, 0)
    }

    @Test
    fun shouldGetLockAuditTrailJson() = runTest {
        lockOperations.getLockAuditTrailJson(GetLockAuditTrailData(DEFAULT_LOCK_ID, 0, 0).toJson())
    }

    @Test
    fun shouldGetAuditForUser() = runTest {
        lockOperations.getAuditForUser(DEFAULT_USER_ID, 0, 0)
    }

    @Test
    fun shouldGetAuditForUserJson() = runTest {
        lockOperations.getAuditForUserJson(GetAuditForUserData(DEFAULT_USER_ID, 0, 0).toJson())
    }

    @Test
    fun shouldGetUsersForLock() = runTest {
        lockOperations.getUsersForLock(DEFAULT_LOCK_ID)
    }

    @Test
    fun shouldGetUsersForLockJson() = runTest {
        lockOperations.getUsersForLockJson(GetUsersForLockData(DEFAULT_LOCK_ID).toJson())
    }

    @Test
    fun shouldGetLocksForUser() = runTest {
        lockOperations.getLocksForUser(DEFAULT_USER_ID)
    }

    @Test
    fun shouldGetLocksForUserJson() = runTest {
        lockOperations.getLocksForUserJson(GetLocksForUserData(DEFAULT_USER_ID).toJson())
    }

    @Test
    fun shouldUpdateLockName() = runTest {
        lockOperations.updateLockName(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun shouldUpdateLockNameJson() = runTest {
        lockOperations.updateLockNameJson(UpdateLockNameData(DEFAULT_LOCK_ID, "").toJson())
    }

    @Test
    fun shouldUpdateLockFavourite() = runTest {
        lockOperations.updateLockFavourite(DEFAULT_LOCK_ID, false)
    }

    @Test
    fun shouldUpdateLockFavouriteJson() = runTest {
        lockOperations.updateLockFavouriteJson(UpdateLockFavouriteData(DEFAULT_LOCK_ID, false).toJson())
    }

    @Test
    fun shouldUpdateLockColour() = runTest {
        lockOperations.updateLockColour(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun shouldUpdateLockColourJson() = runTest {
        lockOperations.updateLockColourJson(UpdateLockColourData(DEFAULT_LOCK_ID, "").toJson())
    }

    @Test
    fun shouldUpdateLockSettingDefaultName() = runTest {
        lockOperations.updateLockSettingDefaultName(DEFAULT_LOCK_ID, "")
    }

    @Test
    fun shouldUpdateLockSettingDefaultNameJson() = runTest {
        lockOperations.updateLockSettingDefaultNameJson(UpdateLockSettingDefaultNameData(DEFAULT_LOCK_ID, "").toJson())
    }

    @Test
    fun shouldSetLockSettingPermittedAddresses() = runTest {
        lockOperations.setLockSettingPermittedAddresses(DEFAULT_LOCK_ID, listOf("1.1.1.1"))
    }

    @Test
    fun shouldSetLockSettingPermittedAddressesJson() = runTest {
        lockOperations.setLockSettingPermittedAddressesJson(SetLockSettingPermittedAddressesData(DEFAULT_LOCK_ID, listOf("1.1.1.1")).toJson())
    }

    @Test
    fun shouldUpdateLockSettingHidden() = runTest {
        lockOperations.updateLockSettingHidden(DEFAULT_LOCK_ID, true)
    }

    @Test
    fun shouldUpdateLockSettingHiddenJson() = runTest {
        lockOperations.updateLockSettingHiddenJson(UpdateLockSettingHiddenData(DEFAULT_LOCK_ID, true).toJson())
    }

    @Test
    fun shouldSetLockSettingTimeRestrictions() = runTest {
        lockOperations.setLockSettingTimeRestrictions(DEFAULT_LOCK_ID, emptyList())
    }

    @Test
    fun shouldSetLockSettingTimeRestrictionsJson() = runTest {
        lockOperations.setLockSettingTimeRestrictionsJson(SetLockSettingTimeRestrictionsData(DEFAULT_LOCK_ID, emptyList()).toJson())
    }

    @Test
    fun shouldUpdateLockSettingLocationRestrictions() = runTest {
        lockOperations.updateLockSettingLocationRestrictions(DEFAULT_LOCK_ID, null)
    }

    @Test
    fun shouldUpdateLockSettingLocationRestrictionsJson() = runTest {
        lockOperations.updateLockSettingLocationRestrictionsJson(UpdateLockSettingLocationRestrictionsData(DEFAULT_LOCK_ID, null).toJson())
    }

    @Test
    fun shouldGetUserPublicKey() = runTest {
        lockOperations.getUserPublicKey(DEFAULT_USER_EMAIL)
    }

    @Test
    fun shouldGetUserPublicKeyJson() = runTest {
        lockOperations.getUserPublicKeyJson(GetUserPublicKeyData(DEFAULT_USER_EMAIL).toJson())
    }

    @Test
    fun shouldGetUserPublicKeyByEmail() = runTest {
        lockOperations.getUserPublicKeyByEmail("")
    }

    @Test
    fun shouldGetUserPublicKeyByEmailJson() = runTest {
        lockOperations.getUserPublicKeyByEmailJson(GetUserPublicKeyByEmailData("").toJson())
    }

    @Test
    fun shouldGetUserPublicKeyByTelephone() = runTest {
        lockOperations.getUserPublicKeyByTelephone("")
    }

    @Test
    fun shouldGetUserPublicKeyByTelephoneJson() = runTest {
        lockOperations.getUserPublicKeyByTelephoneJson(GetUserPublicKeyByTelephoneData("").toJson())
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKey() = runTest {
        lockOperations.getUserPublicKeyByLocalKey("")
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeyJson() = runTest {
        lockOperations.getUserPublicKeyByLocalKeyJson(GetUserPublicKeyByLocalKeyData("").toJson())
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKey() = runTest {
        lockOperations.getUserPublicKeyByForeignKey("")
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKeyJson() = runTest {
        lockOperations.getUserPublicKeyByForeignKeyJson(GetUserPublicKeyByForeignKeyData("").toJson())
    }

    @Test
    fun shouldGetUserPublicKeyByIdentity() = runTest {
        lockOperations.getUserPublicKeyByIdentity("")
    }

    @Test
    fun shouldGetUserPublicKeyByIdentityJson() = runTest {
        lockOperations.getUserPublicKeyByIdentityJson(GetUserPublicKeyByIdentityData("").toJson())
    }

    @Test
    fun shouldUnlockWithContext() = runTest {
        lockOperations.unlockWithContext(DEFAULT_LOCK_ID)
    }

    @Test
    fun shouldUnlockWithContextJson() = runTest {
        lockOperations.unlockWithContextJson(UnlockWithContextData(DEFAULT_LOCK_ID).toJson())
    }

    @Test
    fun shouldUnlock() = runTest {
        lockOperations.unlock(LockOperations.UnlockOperation(LockOperations.BaseOperation("userId", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID)))
    }

    @Test
    fun shouldUnlockJson() = runTest {
        lockOperations.unlockJson(UnlockOperationData(BaseOperationData("userId", emptyList(), TEST_MAIN_USER_PRIVATE_KEY, DEFAULT_LOCK_ID, 0, 0, 0, "")).toJson())
    }

    @Test
    fun shouldShareLockWithContext() = runTest {
        lockOperations.shareLockWithContext(DEFAULT_LOCK_ID, LockOperations.ShareLock("", UserRole.USER, byteArrayOf()))
    }

    @Test
    fun shouldShareLockWithContextJson() = runTest {
        lockOperations.shareLockWithContextJson(ShareLockWithContextData(DEFAULT_LOCK_ID, ShareLockData("", UserRole.USER, byteArrayOf().encodeByteArrayToBase64())).toJson())
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
    fun shouldShareLockJson() = runTest {
        lockOperations.shareLockJson(ShareLockOperationData(
            baseOperation = BaseOperationData("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY, DEFAULT_LOCK_ID, 0, 0, 0, ""),
            shareLock = ShareLockData("", UserRole.USER, byteArrayOf().encodeByteArrayToBase64())
        ).toJson())
    }

    @Test
    fun shouldRevokeAccessToLockWithContext() = runTest {
        lockOperations.revokeAccessToLockWithContext(DEFAULT_LOCK_ID, emptyList())
    }

    @Test
    fun shouldRevokeAccessToLockWithContextJson() = runTest {
        lockOperations.revokeAccessToLockWithContextJson(RevokeAccessToLockWithContextData(DEFAULT_LOCK_ID, emptyList()).toJson())
    }

    @Test
    fun shouldRevokeAccessToLock() = runTest {
        lockOperations.revokeAccessToLock(LockOperations.RevokeAccessToLockOperation(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            users = emptyList()
        ))
    }

    @Test
    fun shouldRevokeAccessToLockJson() = runTest {
        lockOperations.revokeAccessToLockJson(RevokeAccessToLockOperationData(
            baseOperation = BaseOperationData("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY, DEFAULT_LOCK_ID, 0, 0, 0, ""),
            users = emptyList()
        ).toJson())
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDurationWithContext() = runTest {
        lockOperations.updateSecureSettingUnlockDurationWithContext(DEFAULT_LOCK_ID, 0)
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDurationWithContextJson() = runTest {
        lockOperations.updateSecureSettingUnlockDurationWithContextJson(UpdateSecureSettingUnlockDurationWithContextData(DEFAULT_LOCK_ID, 0).toJson())
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDuration() = runTest {
        lockOperations.updateSecureSettingUnlockDuration(
            LockOperations.UpdateSecureSettingUnlockDuration(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            unlockDuration = 0
        ))
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDurationJson() = runTest {
        lockOperations.updateSecureSettingUnlockDurationJson(UpdateSecureSettingUnlockDurationData(
            baseOperation = BaseOperationData("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY, DEFAULT_LOCK_ID, 0, 0 , 0, ""),
            unlockDuration = 0
        ).toJson())
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetweenWithContext() = runTest {
        lockOperations.updateSecureSettingUnlockBetweenWithContext(DEFAULT_LOCK_ID, null)
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetweenWithContextJson() = runTest {
        lockOperations.updateSecureSettingUnlockBetweenWithContextJson(UpdateSecureSettingUnlockBetweenWithContextData(DEFAULT_LOCK_ID, null).toJson())
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetween() = runTest {
        lockOperations.updateSecureSettingUnlockBetween(
            LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = LockOperations.BaseOperation("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(), DEFAULT_LOCK_ID),
            unlockBetween = null
        ))
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetweenJson() = runTest {
        lockOperations.updateSecureSettingUnlockBetweenJson(UpdateSecureSettingUnlockBetweenData(
            baseOperation = BaseOperationData("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY, DEFAULT_LOCK_ID, 0, 0, 0, ""),
            unlockBetween = null
        ).toJson())
    }

    @Test
    fun shouldGetPinnedLocks() = runTest {
        lockOperations.getPinnedLocks()
    }

    @Test
    fun shouldGetPinnedLocksJson() = runTest {
        lockOperations.getPinnedLocksJson()
    }

    @Test
    fun shouldGetShareableLocks() = runTest {
        lockOperations.getShareableLocks()
    }

    @Test
    fun shouldGetShareableLocksJson() = runTest {
        lockOperations.getShareableLocksJson()
    }
}