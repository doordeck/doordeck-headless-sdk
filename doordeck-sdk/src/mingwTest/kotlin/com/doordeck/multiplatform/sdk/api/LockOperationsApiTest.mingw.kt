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
import com.doordeck.multiplatform.sdk.UNIT_RESULT_DATA
import com.doordeck.multiplatform.sdk.USER_LOCK_RESPONSE
import com.doordeck.multiplatform.sdk.USER_PUBLIC_KEY_RESPONSE
import com.doordeck.multiplatform.sdk.cache.CapabilityCache
import com.doordeck.multiplatform.sdk.capturedCallback
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
import com.doordeck.multiplatform.sdk.testCallback
import com.doordeck.multiplatform.sdk.toResultDataJson
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.cinterop.staticCFunction
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Duration.Companion.milliseconds

class LockOperationsApiTest : MockTest() {

    @Test
    fun shouldGetSingleLock() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.getSingleLock(
                data = GetSingleLockData(DEFAULT_LOCK_ID).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(LOCK_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldGetLockAuditTrail() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.getLockAuditTrail(
                data = GetLockAuditTrailData(DEFAULT_LOCK_ID, 0, 0).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(AUDIT_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldGetAuditForUser() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.getAuditForUser(
                data = GetAuditForUserData(DEFAULT_USER_ID, 0, 0).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(AUDIT_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldGetUsersForLock() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.getUsersForLock(
                data = GetUsersForLockData(DEFAULT_LOCK_ID).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(USER_LOCK_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldGetLocksForUser() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.getLocksForUser(
                data = GetLocksForUserData(DEFAULT_USER_ID).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(LOCK_USER_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldUpdateLockName() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.updateLockName(
                data = UpdateLockNameData(DEFAULT_LOCK_ID, "").toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldUpdateLockFavourite() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.updateLockFavourite(
                data = UpdateLockFavouriteData(DEFAULT_LOCK_ID, false).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldUpdateLockColour() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.updateLockColour(
                data = UpdateLockColourData(DEFAULT_LOCK_ID, "").toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldUpdateLockSettingDefaultName() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.updateLockSettingDefaultName(
                data = UpdateLockSettingDefaultNameData(DEFAULT_LOCK_ID, "").toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldSetLockSettingPermittedAddresses() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.setLockSettingPermittedAddresses(
                data = SetLockSettingPermittedAddressesData(DEFAULT_LOCK_ID, listOf("1.1.1.1")).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldUpdateLockSettingHidden() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.updateLockSettingHidden(
                data = UpdateLockSettingHiddenData(DEFAULT_LOCK_ID, true).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldSetLockSettingTimeRestrictions() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.setLockSettingTimeRestrictions(
                data = SetLockSettingTimeRestrictionsData(DEFAULT_LOCK_ID, emptyList()).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldUpdateLockSettingLocationRestrictions() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.updateLockSettingLocationRestrictions(
                data = UpdateLockSettingLocationRestrictionsData(DEFAULT_LOCK_ID, null).toJson(),
                callback = callbackPtr
            )
            
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldGetUserPublicKey() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.getUserPublicKey(
                data = GetUserPublicKeyData(DEFAULT_USER_EMAIL).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(USER_PUBLIC_KEY_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldGetUserPublicKeyByEmail() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.getUserPublicKeyByEmail(
                data = GetUserPublicKeyByEmailData("").toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(USER_PUBLIC_KEY_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldGetUserPublicKeyByTelephone() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.getUserPublicKeyByTelephone(
                data = GetUserPublicKeyByTelephoneData("").toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(USER_PUBLIC_KEY_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKey() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.getUserPublicKeyByLocalKey(
                data = GetUserPublicKeyByLocalKeyData("").toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(USER_PUBLIC_KEY_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKey() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.getUserPublicKeyByForeignKey(
                data = GetUserPublicKeyByForeignKeyData("").toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(USER_PUBLIC_KEY_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldGetUserPublicKeyByIdentity() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.getUserPublicKeyByIdentity(
                data = GetUserPublicKeyByIdentityData("").toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(USER_PUBLIC_KEY_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldGetUserPublicKeyByEmails() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.getUserPublicKeyByEmails(
                data = GetUserPublicKeyByEmailsData(listOf("", "")).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldGetUserPublicKeyByTelephones() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.getUserPublicKeyByTelephones(
                data = GetUserPublicKeyByTelephonesData(listOf("", "")).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeys() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.getUserPublicKeyByLocalKeys(
                data = GetUserPublicKeyByLocalKeysData(listOf("", "")).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldGetUserPublicKeyByForeignKeys() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.getUserPublicKeyByForeignKeys(
                data = GetUserPublicKeyByForeignKeysData(listOf("", "")).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(BATCH_USER_PUBLIC_KEY_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldUnlockUsingContext() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.unlock(
                data = UnlockOperationData(BaseOperationData(lockId = DEFAULT_LOCK_ID)).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldUnlock() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.unlock(
                data = UnlockOperationData(BaseOperationData("userId", emptyList(), TEST_MAIN_USER_PRIVATE_KEY, DEFAULT_LOCK_ID, 0, 0, 0, "")).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldShareLockUsingContext() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.shareLock(
                data = ShareLockOperationData(
                    baseOperation = BaseOperationData(lockId = DEFAULT_LOCK_ID),
                    shareLock = ShareLockData("", UserRole.USER, byteArrayOf().encodeByteArrayToBase64())
                ).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldBatchShareLockUsingContext() = runTest {
        runBlocking {
            // Given
            CapabilityCache.put(DEFAULT_LOCK_ID, mapOf(CapabilityType.BATCH_SHARING_25 to CapabilityStatus.SUPPORTED))
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.batchShareLock(
                data = BatchShareLockOperationData(
                    baseOperation = BaseOperationData(lockId = DEFAULT_LOCK_ID),
                    users = listOf(ShareLockData("", UserRole.USER, byteArrayOf().encodeByteArrayToBase64()))
                ).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldShareLock() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.shareLock(
                data = ShareLockOperationData(
                    baseOperation = BaseOperationData("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY, DEFAULT_LOCK_ID, 0, 0, 0, ""),
                    shareLock = ShareLockData("", UserRole.USER, byteArrayOf().encodeByteArrayToBase64())
                ).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldBatchShareLock() = runTest {
        runBlocking {
            // Given
            CapabilityCache.put(DEFAULT_LOCK_ID, mapOf(CapabilityType.BATCH_SHARING_25 to CapabilityStatus.SUPPORTED))
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.batchShareLock(
                data = BatchShareLockOperationData(
                    baseOperation = BaseOperationData("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY, DEFAULT_LOCK_ID, 0, 0, 0, ""),
                    users = listOf(ShareLockData("", UserRole.USER, byteArrayOf().encodeByteArrayToBase64()))
                ).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldRevokeAccessToLockUsingContext() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.revokeAccessToLock(
                data = RevokeAccessToLockOperationData(
                    baseOperation = BaseOperationData(lockId = DEFAULT_LOCK_ID),
                    users = emptyList()
                ).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldRevokeAccessToLock() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.revokeAccessToLock(
                data = RevokeAccessToLockOperationData(
                    baseOperation = BaseOperationData("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY, DEFAULT_LOCK_ID, 0, 0, 0, ""),
                    users = emptyList()
                ).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDurationUsingContext() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.updateSecureSettingUnlockDuration(
                data = UpdateSecureSettingUnlockDurationData(
                    baseOperation = BaseOperationData(lockId = DEFAULT_LOCK_ID),
                    unlockDuration = 0
                ).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDuration() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.updateSecureSettingUnlockDuration(
                data = UpdateSecureSettingUnlockDurationData(
                    baseOperation = BaseOperationData("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY, DEFAULT_LOCK_ID, 0, 0 , 0, ""),
                    unlockDuration = 0
                ).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetweenUsingContext() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.updateSecureSettingUnlockBetween(
                data = UpdateSecureSettingUnlockBetweenData(
                    baseOperation = BaseOperationData(lockId = DEFAULT_LOCK_ID),
                    unlockBetween = null
                ).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldUpdateSecureSettingUnlockBetween() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.updateSecureSettingUnlockBetween(
                data = UpdateSecureSettingUnlockBetweenData(
                    baseOperation = BaseOperationData("", emptyList(), TEST_MAIN_USER_PRIVATE_KEY, DEFAULT_LOCK_ID, 0, 0, 0, ""),
                    unlockBetween = null
                ).toJson(),
                callback = callbackPtr
            )
            delay(10.milliseconds)

            // Then
            assertEquals(UNIT_RESULT_DATA, capturedCallback)
        }
    }

    @Test
    fun shouldGetPinnedLocks() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.getPinnedLocks(callbackPtr)
            delay(10.milliseconds)

            // Then
            assertEquals(PINNED_LOCKS_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }

    @Test
    fun shouldGetShareableLocks() = runTest {
        runBlocking {
            // Given
            val callbackPtr = staticCFunction(::testCallback)

            // When
            LockOperationsApi.getShareableLocks(callbackPtr)
            delay(10.milliseconds)

            // Then
            assertEquals(SHAREABLE_LOCKS_RESPONSE.toResultDataJson(), capturedCallback)
        }
    }
}