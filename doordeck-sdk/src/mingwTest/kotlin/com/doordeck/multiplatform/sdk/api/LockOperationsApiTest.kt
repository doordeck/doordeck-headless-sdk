package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.CallbackTest
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_LOCK_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_USER_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_MAIN_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_SUPPLEMENTARY_USER_ID
import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_SUPPLEMENTARY_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.TestConstants.TEST_SUPPLEMENTARY_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_SUPPLEMENTARY_USER_ID
import com.doordeck.multiplatform.sdk.context.ContextManager
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.exceptions.MissingContextFieldException
import com.doordeck.multiplatform.sdk.model.common.DayOfWeek
import com.doordeck.multiplatform.sdk.model.common.UserRole
import com.doordeck.multiplatform.sdk.model.data.BaseOperationData
import com.doordeck.multiplatform.sdk.model.data.BasicLocationRequirement
import com.doordeck.multiplatform.sdk.model.data.BasicTimeRequirement
import com.doordeck.multiplatform.sdk.model.data.BatchShareLockOperationData
import com.doordeck.multiplatform.sdk.model.data.GetAuditForUserData
import com.doordeck.multiplatform.sdk.model.data.GetLockAuditTrailData
import com.doordeck.multiplatform.sdk.model.data.GetLocksForUserData
import com.doordeck.multiplatform.sdk.model.data.GetSingleLockData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyByEmailData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyByEmailsData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyByLocalKeyData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyByLocalKeysData
import com.doordeck.multiplatform.sdk.model.data.GetUserPublicKeyData
import com.doordeck.multiplatform.sdk.model.data.GetUsersForLockData
import com.doordeck.multiplatform.sdk.model.data.LocationRequirementData
import com.doordeck.multiplatform.sdk.model.data.LoginData
import com.doordeck.multiplatform.sdk.model.data.RegisterEphemeralKeyData
import com.doordeck.multiplatform.sdk.model.data.ResultData
import com.doordeck.multiplatform.sdk.model.data.RevokeAccessToLockOperationData
import com.doordeck.multiplatform.sdk.model.data.SetLockSettingPermittedAddressesData
import com.doordeck.multiplatform.sdk.model.data.SetLockSettingTimeRestrictionsData
import com.doordeck.multiplatform.sdk.model.data.ShareLockData
import com.doordeck.multiplatform.sdk.model.data.ShareLockOperationData
import com.doordeck.multiplatform.sdk.model.data.TimeRequirementData
import com.doordeck.multiplatform.sdk.model.data.UnlockBetweenData
import com.doordeck.multiplatform.sdk.model.data.UnlockOperationData
import com.doordeck.multiplatform.sdk.model.data.UpdateLockColourData
import com.doordeck.multiplatform.sdk.model.data.UpdateLockFavouriteData
import com.doordeck.multiplatform.sdk.model.data.UpdateLockNameData
import com.doordeck.multiplatform.sdk.model.data.UpdateLockSettingDefaultNameData
import com.doordeck.multiplatform.sdk.model.data.UpdateLockSettingHiddenData
import com.doordeck.multiplatform.sdk.model.data.UpdateLockSettingLocationRestrictionsData
import com.doordeck.multiplatform.sdk.model.data.UpdateSecureSettingUnlockBetweenData
import com.doordeck.multiplatform.sdk.model.data.UpdateSecureSettingUnlockDurationData
import com.doordeck.multiplatform.sdk.model.responses.BasicAuditResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicBatchUserPublicKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicLockResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicLockUserResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicRegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicShareableLockResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicTokenResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicUserLockResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicUserPublicKeyResponse
import com.doordeck.multiplatform.sdk.randomDouble
import com.doordeck.multiplatform.sdk.randomInt
import com.doordeck.multiplatform.sdk.randomUuid
import com.doordeck.multiplatform.sdk.testCallback
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.cinterop.staticCFunction
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes
import kotlin.uuid.Uuid

class LockOperationsApiTest : CallbackTest() {

    @Test
    fun shouldGetSingleLock() = runTest {
        runBlocking {
            // Given
            callbackApiCall<ResultData<BasicTokenResponse>> {
                AccountlessApi.login(
                    data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // When
            val response = callbackApiCall<ResultData<BasicLockResponse>> {
                LockOperationsApi.getSingleLock(
                    data = GetSingleLockData(PLATFORM_TEST_MAIN_LOCK_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // Then
            assertNotNull(response.success)
            assertNotNull(response.success.result)
            assertEquals(PLATFORM_TEST_MAIN_LOCK_ID, response.success.result.id)
        }
    }

    @Test
    fun shouldUpdateLockName() = runTest {
        runBlocking {
            // Given
            callbackApiCall<ResultData<BasicTokenResponse>> {
                AccountlessApi.login(
                    data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            val updatedLockName = "Doordeck Fusion Test Site - ${Uuid.random()}"

            // When
            callbackApiCall<ResultData<Unit>> {
                LockOperationsApi.updateLockName(
                    data = UpdateLockNameData(PLATFORM_TEST_MAIN_LOCK_ID, updatedLockName).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // Then
            val lockResponse = callbackApiCall<ResultData<BasicLockResponse>> {
                LockOperationsApi.getSingleLock(
                    data = GetSingleLockData(PLATFORM_TEST_MAIN_LOCK_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            assertNotNull(lockResponse.success)
            assertNotNull(lockResponse.success.result)
            assertEquals(updatedLockName, lockResponse.success.result.name)
        }
    }

    @Test
    fun shouldUpdateLockFavourite() = runTest {
        runBlocking {
            // Given
            callbackApiCall<ResultData<BasicTokenResponse>> {
                AccountlessApi.login(
                    data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            val updatedFavourite = true

            // When
            callbackApiCall<ResultData<Unit>> {
                LockOperationsApi.updateLockFavourite(
                    data = UpdateLockFavouriteData(PLATFORM_TEST_MAIN_LOCK_ID, updatedFavourite).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // Then
            val lockResponse = callbackApiCall<ResultData<BasicLockResponse>> {
                LockOperationsApi.getSingleLock(
                    data = GetSingleLockData(PLATFORM_TEST_MAIN_LOCK_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            assertNotNull(lockResponse.success)
            assertNotNull(lockResponse.success.result)
            assertEquals(updatedFavourite, lockResponse.success.result.favourite)
        }
    }

    @Test
    fun shouldUpdateLockColour() = runTest {
        runBlocking {
            // Given
            callbackApiCall<ResultData<BasicTokenResponse>> {
                AccountlessApi.login(
                    data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            val updatedLockColour = "#${randomInt(111111, 999999)}"

            // When
            callbackApiCall<ResultData<Unit>> {
                LockOperationsApi.updateLockColour(
                    data = UpdateLockColourData(PLATFORM_TEST_MAIN_LOCK_ID, updatedLockColour).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // Then
            val lockResponse = callbackApiCall<ResultData<BasicLockResponse>> {
                LockOperationsApi.getSingleLock(
                    data = GetSingleLockData(PLATFORM_TEST_MAIN_LOCK_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            assertNotNull(lockResponse.success)
            assertNotNull(lockResponse.success.result)
            assertEquals(updatedLockColour, lockResponse.success.result.colour)
        }
    }

    @Test
    fun shouldUpdateLockSettingDefaultName() = runTest {
        runBlocking {
            // Given
            callbackApiCall<ResultData<BasicTokenResponse>> {
                AccountlessApi.login(
                    data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            val updatedLockDefaultName = "Doordeck Fusion Test Site - ${Uuid.random()}"

            // When
            callbackApiCall<ResultData<Unit>> {
                LockOperationsApi.updateLockSettingDefaultName(
                    data = UpdateLockSettingDefaultNameData(PLATFORM_TEST_MAIN_LOCK_ID, updatedLockDefaultName).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // Then
            val lockResponse = callbackApiCall<ResultData<BasicLockResponse>> {
                LockOperationsApi.getSingleLock(
                    data = GetSingleLockData(PLATFORM_TEST_MAIN_LOCK_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            assertNotNull(lockResponse.success)
            assertNotNull(lockResponse.success.result)
            assertEquals(updatedLockDefaultName, lockResponse.success.result.settings.defaultName)
        }
    }

    @Test
    fun shouldSetAndRemoveLockSettingPermittedAddresses() = runTest {
        runBlocking {
            // Given - shouldSetLockSettingPermittedAddresses
            callbackApiCall<ResultData<BasicTokenResponse>> {
                AccountlessApi.login(
                    data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            val addedLockPermittedAddresses = listOf("95.19.38.42")

            // When
            callbackApiCall<ResultData<Unit>> {
                LockOperationsApi.setLockSettingPermittedAddresses(
                    data = SetLockSettingPermittedAddressesData(PLATFORM_TEST_MAIN_LOCK_ID, addedLockPermittedAddresses).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // Then
            var lockResponse = callbackApiCall<ResultData<BasicLockResponse>> {
                LockOperationsApi.getSingleLock(
                    data = GetSingleLockData(PLATFORM_TEST_MAIN_LOCK_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            assertNotNull(lockResponse.success)
            assertNotNull(lockResponse.success.result)
            assertTrue { lockResponse.success.result.settings.permittedAddresses.isNotEmpty() }
            assertContains(addedLockPermittedAddresses, lockResponse.success.result.settings.permittedAddresses.first())

            // Given - shouldRemoveLockSettingPermittedAddresses
            val removedLockPermittedAddresses = listOf<String>()

            // When
            callbackApiCall<ResultData<Unit>> {
                LockOperationsApi.setLockSettingPermittedAddresses(
                    data = SetLockSettingPermittedAddressesData(PLATFORM_TEST_MAIN_LOCK_ID, removedLockPermittedAddresses).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // Then
            lockResponse = callbackApiCall<ResultData<BasicLockResponse>> {
                LockOperationsApi.getSingleLock(
                    data = GetSingleLockData(PLATFORM_TEST_MAIN_LOCK_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            assertNotNull(lockResponse.success)
            assertNotNull(lockResponse.success.result)
            assertTrue { lockResponse.success.result.settings.permittedAddresses.isEmpty() }
        }
    }

    @Test
    fun shouldUpdateLockSettingHidden() = runTest {
        runBlocking {
            // Given
            callbackApiCall<ResultData<BasicTokenResponse>> {
                AccountlessApi.login(
                    data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            val updatedHidden = false

            // When
            callbackApiCall<ResultData<Unit>> {
                LockOperationsApi.updateLockSettingHidden(
                    data = UpdateLockSettingHiddenData(PLATFORM_TEST_MAIN_LOCK_ID, updatedHidden).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // Then
            val lockResponse = callbackApiCall<ResultData<BasicLockResponse>> {
                LockOperationsApi.getSingleLock(
                    data = GetSingleLockData(PLATFORM_TEST_MAIN_LOCK_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            assertNotNull(lockResponse.success)
            assertNotNull(lockResponse.success.result)
            assertEquals(updatedHidden, lockResponse.success.result.settings.hidden)
        }
    }

    @Test
    fun shouldSetAndRemoveLockSettingTimeRestrictions() = runTest {
        runBlocking {
            // Given - shouldSetLockSettingTimeRestrictions
            callbackApiCall<ResultData<BasicTokenResponse>> {
                AccountlessApi.login(
                    data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            val now = Clock.System.now()
            val min = (now - 1.minutes).toLocalDateTime(TimeZone.UTC)
            val max = (now + 5.minutes).toLocalDateTime(TimeZone.UTC)
            val addedTimeRestriction = TimeRequirementData(
                start = "${min.hour.toString().padStart(2, '0')}:${min.minute.toString().padStart(2, '0')}",
                end = "${max.hour.toString().padStart(2, '0')}:${max.minute.toString().padStart(2, '0')}",
                timezone = TimeZone.UTC.id,
                days = listOf(DayOfWeek.entries.random())
            )

            // When
            callbackApiCall<ResultData<Unit>> {
                LockOperationsApi.setLockSettingTimeRestrictions(
                    data = SetLockSettingTimeRestrictionsData(PLATFORM_TEST_MAIN_LOCK_ID, listOf(addedTimeRestriction)).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // Then
            var lockResponse = callbackApiCall<ResultData<BasicLockResponse>> {
                LockOperationsApi.getSingleLock(
                    data = GetSingleLockData(PLATFORM_TEST_MAIN_LOCK_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            assertNotNull(lockResponse.success)
            assertNotNull(lockResponse.success.result)
            val actualTime = lockResponse.success.result.settings.usageRequirements?.time?.firstOrNull()
            assertNotNull(actualTime)
            assertEquals(addedTimeRestriction.start, actualTime.start)
            assertEquals(addedTimeRestriction.end, actualTime.end)
            assertEquals(addedTimeRestriction.timezone, actualTime.timezone)
            assertContains(actualTime.days, addedTimeRestriction.days.first())

            // Given - shouldRemoveLockSettingTimeRestrictions
            val removedTimeRestriction = emptyList<TimeRequirementData>()

            // When
            callbackApiCall<ResultData<Unit>> {
                LockOperationsApi.setLockSettingTimeRestrictions(
                    data = SetLockSettingTimeRestrictionsData(PLATFORM_TEST_MAIN_LOCK_ID, removedTimeRestriction).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // Then
            lockResponse = callbackApiCall<ResultData<BasicLockResponse>> {
                LockOperationsApi.getSingleLock(
                    data = GetSingleLockData(PLATFORM_TEST_MAIN_LOCK_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            assertNotNull(lockResponse.success)
            assertNotNull(lockResponse.success.result)
            assertNull(lockResponse.success.result.settings.usageRequirements?.time)
        }
    }

    @Test
    fun shouldUpdateAndRemoveLockSettingLocationRestrictions() = runTest {
        runBlocking {
            // Given - shouldUpdateLockSettingLocationRestrictions
            callbackApiCall<ResultData<BasicTokenResponse>> {
                AccountlessApi.login(
                    data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            val addedLocationRestriction = LocationRequirementData(
                latitude = randomDouble(-90.0, 90.0),
                longitude = randomDouble(-180.0, 180.0),
                enabled = true,
                radius = randomInt(1, 100),
                accuracy = randomInt(1, 100)
            )

            // When
            callbackApiCall<ResultData<Unit>> {
                LockOperationsApi.updateLockSettingLocationRestrictions(
                    data = UpdateLockSettingLocationRestrictionsData(PLATFORM_TEST_MAIN_LOCK_ID, addedLocationRestriction).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // Then
            var lockResponse = callbackApiCall<ResultData<BasicLockResponse>> {
                LockOperationsApi.getSingleLock(
                    data = GetSingleLockData(PLATFORM_TEST_MAIN_LOCK_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            assertNotNull(lockResponse.success)
            assertNotNull(lockResponse.success.result)
            assertNotNull(lockResponse.success.result.settings.usageRequirements?.location)
            assertEquals(addedLocationRestriction.latitude, lockResponse.success.result.settings.usageRequirements.location.latitude)
            assertEquals(addedLocationRestriction.longitude, lockResponse.success.result.settings.usageRequirements.location.longitude)
            assertEquals(addedLocationRestriction.enabled, lockResponse.success.result.settings.usageRequirements.location.enabled)
            assertEquals(addedLocationRestriction.radius, lockResponse.success.result.settings.usageRequirements.location.radius)
            assertEquals(addedLocationRestriction.accuracy, lockResponse.success.result.settings.usageRequirements.location.accuracy)

            // Given - shouldRemoveLockSettingLocationRestrictions
            val removedLocationRestriction = null

            // When
            callbackApiCall<ResultData<Unit>> {
                LockOperationsApi.updateLockSettingLocationRestrictions(
                    data = UpdateLockSettingLocationRestrictionsData(PLATFORM_TEST_MAIN_LOCK_ID, removedLocationRestriction).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // Then
            lockResponse = callbackApiCall<ResultData<BasicLockResponse>> {
                LockOperationsApi.getSingleLock(
                    data = GetSingleLockData(PLATFORM_TEST_MAIN_LOCK_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            assertNotNull(lockResponse.success)
            assertNotNull(lockResponse.success.result)
            assertEquals(removedLocationRestriction, lockResponse.success.result.settings.usageRequirements?.location)
        }
    }

    @Test
    fun shouldGetUserPublicKey() = runTest {
        runBlocking {
            // Given
            callbackApiCall<ResultData<BasicTokenResponse>> {
                AccountlessApi.login(
                    data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // When
            val result = callbackApiCall<ResultData<BasicUserPublicKeyResponse>> {
                LockOperationsApi.getUserPublicKey(
                    data = GetUserPublicKeyData(TEST_MAIN_USER_EMAIL, true).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // Then
            assertNotNull(result.success)
            assertNotNull(result.success.result)
            assertEquals(PLATFORM_TEST_MAIN_USER_ID, result.success.result.id)
        }
    }

    @Test
    fun shouldGetUserPublicKeyByEmail() = runTest {
        runBlocking {
            // Given
            callbackApiCall<ResultData<BasicTokenResponse>> {
                AccountlessApi.login(
                    data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // When
            val result = callbackApiCall<ResultData<BasicUserPublicKeyResponse>> {
                LockOperationsApi.getUserPublicKeyByEmail(
                    data = GetUserPublicKeyByEmailData(TEST_MAIN_USER_EMAIL).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // Then
            assertNotNull(result.success)
            assertNotNull(result.success.result)
            assertEquals(PLATFORM_TEST_MAIN_USER_ID, result.success.result.id)
        }
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKey() = runTest {
        runBlocking {
            // Given
            callbackApiCall<ResultData<BasicTokenResponse>> {
                AccountlessApi.login(
                    data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // When
            val result = callbackApiCall<ResultData<BasicUserPublicKeyResponse>> {
                LockOperationsApi.getUserPublicKeyByLocalKey(
                    data = GetUserPublicKeyByLocalKeyData(TEST_MAIN_USER_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // Then
            assertNotNull(result.success)
            assertNotNull(result.success.result)
            assertEquals(PLATFORM_TEST_MAIN_USER_ID, result.success.result.id)
        }
    }

    @Test
    fun shouldGetUserPublicKeyByEmails() = runTest {
        runBlocking {
            // Given
            callbackApiCall<ResultData<BasicTokenResponse>> {
                AccountlessApi.login(
                    data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // When
            val result = callbackApiCall<ResultData<List<BasicBatchUserPublicKeyResponse>>> {
                LockOperationsApi.getUserPublicKeyByEmails(
                    data = GetUserPublicKeyByEmailsData(listOf(TEST_MAIN_USER_EMAIL, TEST_SUPPLEMENTARY_USER_EMAIL)).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // Then
            assertNotNull(result.success)
            assertNotNull(result.success.result)
            assertTrue { result.success.result.isNotEmpty() }
        }
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeys() = runTest {
        runBlocking {
            // Given
            callbackApiCall<ResultData<BasicTokenResponse>> {
                AccountlessApi.login(
                    data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // When
            val result = callbackApiCall<ResultData<List<BasicBatchUserPublicKeyResponse>>> {
                LockOperationsApi.getUserPublicKeyByLocalKeys(
                    data = GetUserPublicKeyByLocalKeysData(listOf(TEST_MAIN_USER_ID, TEST_SUPPLEMENTARY_USER_ID)).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // Then
            assertNotNull(result.success)
            assertNotNull(result.success.result)
            assertTrue { result.success.result.isNotEmpty() }
        }
    }

    @Test
    fun shouldGetUsersForLock() = runTest {
        runBlocking {
            // Given
            callbackApiCall<ResultData<BasicTokenResponse>> {
                AccountlessApi.login(
                    data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // When
            val usersForLock = callbackApiCall<ResultData<List<BasicUserLockResponse>>> {
                LockOperationsApi.getUsersForLock(
                    data = GetUsersForLockData(PLATFORM_TEST_MAIN_LOCK_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // Then
            assertNotNull(usersForLock.success)
            assertNotNull(usersForLock.success.result)
            assertTrue { usersForLock.success.result.isNotEmpty() }
            assertTrue { usersForLock.success.result.any { it.userId == PLATFORM_TEST_MAIN_USER_ID } }
        }
    }

    @Test
    fun shouldGetLockForUser() = runTest {
        runBlocking {
            // Given
            callbackApiCall<ResultData<BasicTokenResponse>> {
                AccountlessApi.login(
                    data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // When
            val locksForUser = callbackApiCall<ResultData<BasicLockUserResponse>> {
                LockOperationsApi.getLocksForUser(
                    data = GetLocksForUserData(TEST_MAIN_USER_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // Then
            assertNotNull(locksForUser.success)
            assertNotNull(locksForUser.success.result)
            assertTrue { locksForUser.success.result.devices.isNotEmpty() }
            assertTrue { locksForUser.success.result.devices.any { it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID } }
        }
    }

    @Test
    fun shouldGetPinnedLocks() = runTest {
        runBlocking {
            // Given
            callbackApiCall<ResultData<BasicTokenResponse>> {
                AccountlessApi.login(
                    data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // When
            val pinnedLocks = callbackApiCall<ResultData<List<BasicLockResponse>>> {
                LockOperationsApi.getPinnedLocks(
                    callback = staticCFunction(::testCallback)
                )
            }

            // Then
            assertNotNull(pinnedLocks.success)
            assertNotNull(pinnedLocks.success.result)
            assertTrue { pinnedLocks.success.result.isNotEmpty() }
        }
    }

    @Test
    fun shouldGetShareableLocks() = runTest {
        runBlocking {
            // Given
            callbackApiCall<ResultData<BasicTokenResponse>> {
                AccountlessApi.login(
                    data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // When
            val shareableLocks = callbackApiCall<ResultData<List<BasicShareableLockResponse>>> {
                LockOperationsApi.getShareableLocks(
                    callback = staticCFunction(::testCallback)
                )
            }

            // Then
            assertNotNull(shareableLocks.success)
            assertNotNull(shareableLocks.success.result)
            assertTrue { shareableLocks.success.result.isNotEmpty() }
            assertTrue { shareableLocks.success.result.any { it.id == PLATFORM_TEST_MAIN_LOCK_ID } }
        }
    }

    @Test
    fun shouldUnlock() = runTest {
        runBlocking {
            // Given
            callbackApiCall<ResultData<BasicTokenResponse>> {
                AccountlessApi.login(
                    data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            val registerKeyResponse = callbackApiCall<ResultData<BasicRegisterEphemeralKeyResponse>> {
                AccountApi.registerEphemeralKey(
                    data = RegisterEphemeralKeyData(PLATFORM_TEST_MAIN_USER_PUBLIC_KEY, PLATFORM_TEST_MAIN_USER_PRIVATE_KEY).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            assertNotNull(registerKeyResponse.success)
            assertNotNull(registerKeyResponse.success.result)
            val TEST_MAIN_USER_CERTIFICATE_CHAIN = registerKeyResponse.success.result.certificateChain
            val baseOperation = BaseOperationData(
                userId = PLATFORM_TEST_MAIN_USER_ID,
                userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
                userPrivateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
                lockId = PLATFORM_TEST_MAIN_LOCK_ID
            )

            // When
            callbackApiCall<ResultData<List<BasicShareableLockResponse>>> {
                LockOperationsApi.unlock(
                    data = UnlockOperationData(baseOperation).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
        }
    }

    @Test
    fun shouldUnlockUsingContext() = runTest {
        // Given
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        val registerKeyResponse = callbackApiCall<ResultData<BasicRegisterEphemeralKeyResponse>> {
            AccountApi.registerEphemeralKey(
                data = RegisterEphemeralKeyData(PLATFORM_TEST_MAIN_USER_PUBLIC_KEY, PLATFORM_TEST_MAIN_USER_PRIVATE_KEY).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(registerKeyResponse.success)
        assertNotNull(registerKeyResponse.success.result)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = registerKeyResponse.success.result.certificateChain
        ContextManager.setOperationContext(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray(),
            privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
            isKeyPairVerified = true
        )

        // When
        callbackApiCall<ResultData<Unit>> {
            LockOperationsApi.unlock(
                data = UnlockOperationData(
                    baseOperation = BaseOperationData(lockId = PLATFORM_TEST_MAIN_LOCK_ID)
                ).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
    }

    @Test
    fun shouldShareAndRevokeLock() = runTest {
        // Given - shouldShareLock
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        val registerKeyResponse = callbackApiCall<ResultData<BasicRegisterEphemeralKeyResponse>> {
            AccountApi.registerEphemeralKey(
                data = RegisterEphemeralKeyData(PLATFORM_TEST_MAIN_USER_PUBLIC_KEY, PLATFORM_TEST_MAIN_USER_PRIVATE_KEY).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(registerKeyResponse.success)
        assertNotNull(registerKeyResponse.success.result)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = registerKeyResponse.success.result.certificateChain
        val shareBaseOperation = BaseOperationData(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            userPrivateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
            lockId = PLATFORM_TEST_MAIN_LOCK_ID
        )

        // When
        callbackApiCall<ResultData<Unit>> {
            LockOperationsApi.shareLock(
                data = ShareLockOperationData(
                    baseOperation = shareBaseOperation,
                    shareLock = ShareLockData(
                        targetUserId = PLATFORM_TEST_SUPPLEMENTARY_USER_ID,
                        targetUserRole = UserRole.USER,
                        targetUserPublicKey = PLATFORM_TEST_SUPPLEMENTARY_USER_PUBLIC_KEY
                    )
                ).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        callbackApiCall<ResultData<BasicLockUserResponse>> {
            LockOperationsApi.getLocksForUser(
                data = GetLocksForUserData(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        var locksResponse =  callbackApiCall<ResultData<BasicLockUserResponse>> {
            LockOperationsApi.getLocksForUser(
                data = GetLocksForUserData(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(locksResponse.success)
        assertNotNull(locksResponse.success.result)
        assertTrue { locksResponse.success.result.devices.any { it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID } }

        // Given - shouldRevokeAccessToLock
        val revokeBaseOperation = BaseOperationData(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            userPrivateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
            lockId = PLATFORM_TEST_MAIN_LOCK_ID
        )

        // When
        callbackApiCall<ResultData<Unit>> {
            LockOperationsApi.revokeAccessToLock(
                data = RevokeAccessToLockOperationData(
                    baseOperation = revokeBaseOperation,
                    users = listOf(PLATFORM_TEST_SUPPLEMENTARY_USER_ID)
                ).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        locksResponse = callbackApiCall<ResultData<BasicLockUserResponse>> {
            LockOperationsApi.getLocksForUser(
                data = GetLocksForUserData(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(locksResponse.success)
        assertNotNull(locksResponse.success.result)
        assertFalse { locksResponse.success.result.devices.any { it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID } }
    }

    @Test
    fun shouldBatchShareAndRevokeLock() = runTest {
        // Given - shouldShareLockUsingContext
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        val registerKeyResponse = callbackApiCall<ResultData<BasicRegisterEphemeralKeyResponse>> {
            AccountApi.registerEphemeralKey(
                data = RegisterEphemeralKeyData(PLATFORM_TEST_MAIN_USER_PUBLIC_KEY, PLATFORM_TEST_MAIN_USER_PRIVATE_KEY).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(registerKeyResponse.success)
        assertNotNull(registerKeyResponse.success.result)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = registerKeyResponse.success.result.certificateChain
        val shareBaseOperation = BaseOperationData(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            userPrivateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
            lockId = PLATFORM_TEST_MAIN_LOCK_ID
        )
        val batchShareLock = listOf(
            ShareLockData(
                targetUserId = PLATFORM_TEST_SUPPLEMENTARY_USER_ID,
                targetUserRole = UserRole.USER,
                targetUserPublicKey = PLATFORM_TEST_SUPPLEMENTARY_USER_PUBLIC_KEY
            ),
            ShareLockData(
                targetUserId = PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID,
                targetUserRole = UserRole.USER,
                targetUserPublicKey = PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_PUBLIC_KEY
            )
        )

        // When
        callbackApiCall<ResultData<Unit>> {
            LockOperationsApi.batchShareLock(
                data = BatchShareLockOperationData(
                    baseOperation = shareBaseOperation,
                    users = batchShareLock
                ).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        assertTrue {
            val response = callbackApiCall<ResultData<BasicLockUserResponse>> {
                LockOperationsApi.getLocksForUser(
                    data = GetLocksForUserData(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            assertNotNull(response.success)
            assertNotNull(response.success.result)
            response.success.result.devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID
            }
        }
        assertTrue {
            val response = callbackApiCall<ResultData<BasicLockUserResponse>> {
                LockOperationsApi.getLocksForUser(
                    data = GetLocksForUserData(PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            assertNotNull(response.success)
            assertNotNull(response.success.result)
            response.success.result.devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID
            }
        }

        // When
        // Given - shouldRevokeAccessToLock
        val revokeBaseOperation = BaseOperationData(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            userPrivateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
            lockId = PLATFORM_TEST_MAIN_LOCK_ID
        )
        callbackApiCall<ResultData<Unit>> {
            LockOperationsApi.revokeAccessToLock(
                data = RevokeAccessToLockOperationData(
                    baseOperation = revokeBaseOperation,
                    users = listOf(PLATFORM_TEST_SUPPLEMENTARY_USER_ID, PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID)
                ).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        assertFalse {
            val response = callbackApiCall<ResultData<BasicLockUserResponse>> {
                LockOperationsApi.getLocksForUser(
                    data = GetLocksForUserData(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            assertNotNull(response.success)
            assertNotNull(response.success.result)
            response.success.result.devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID
            }
        }
        assertFalse {
            val response = callbackApiCall<ResultData<BasicLockUserResponse>> {
                LockOperationsApi.getLocksForUser(
                    data = GetLocksForUserData(PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            assertNotNull(response.success)
            assertNotNull(response.success.result)
            response.success.result.devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID
            }
        }
    }

    @Test
    fun shouldShareAndRevokeLockUsingContext() = runTest {
        // Given - shouldShareLockUsingContext
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        val registerKeyResponse = callbackApiCall<ResultData<BasicRegisterEphemeralKeyResponse>> {
            AccountApi.registerEphemeralKey(
                data = RegisterEphemeralKeyData(PLATFORM_TEST_MAIN_USER_PUBLIC_KEY, PLATFORM_TEST_MAIN_USER_PRIVATE_KEY).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(registerKeyResponse.success)
        assertNotNull(registerKeyResponse.success.result)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = registerKeyResponse.success.result.certificateChain
        ContextManager.setOperationContext(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray(),
            privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
            isKeyPairVerified = true
        )
        val shareLock = ShareLockData(
            targetUserId = PLATFORM_TEST_SUPPLEMENTARY_USER_ID,
            targetUserRole = UserRole.USER,
            targetUserPublicKey = PLATFORM_TEST_SUPPLEMENTARY_USER_PUBLIC_KEY
        )

        // When
        callbackApiCall<ResultData<Unit>> {
            LockOperationsApi.shareLock(
                data = ShareLockOperationData(
                    baseOperation = BaseOperationData(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                    shareLock = shareLock
                ).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        var locksResponse = callbackApiCall<ResultData<BasicLockUserResponse>> {
            LockOperationsApi.getLocksForUser(
                data = GetLocksForUserData(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(locksResponse.success)
        assertNotNull(locksResponse.success.result)
        assertTrue { locksResponse.success.result.devices.any { it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID } }

        // Given - shouldRevokeAccessToLockUsingContext
        // When
        callbackApiCall<ResultData<Unit>> {
            LockOperationsApi.revokeAccessToLock(
                data = RevokeAccessToLockOperationData(
                    baseOperation = BaseOperationData(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                    users = listOf(PLATFORM_TEST_SUPPLEMENTARY_USER_ID)
                ).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        locksResponse = callbackApiCall<ResultData<BasicLockUserResponse>> {
            LockOperationsApi.getLocksForUser(
                data = GetLocksForUserData(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(locksResponse.success)
        assertNotNull(locksResponse.success.result)
        assertFalse {
            locksResponse.success.result.devices.any { it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID }
        }
    }

    @Test
    fun shouldBatchShareAndRevokeLockUsingContext() = runTest {
        // Given - shouldShareLockUsingContext
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        val registerKeyResponse = callbackApiCall<ResultData<BasicRegisterEphemeralKeyResponse>> {
            AccountApi.registerEphemeralKey(
                data = RegisterEphemeralKeyData(PLATFORM_TEST_MAIN_USER_PUBLIC_KEY, PLATFORM_TEST_MAIN_USER_PRIVATE_KEY).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(registerKeyResponse.success)
        assertNotNull(registerKeyResponse.success.result)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = registerKeyResponse.success.result.certificateChain
        ContextManager.setOperationContext(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray(),
            privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
            isKeyPairVerified = true
        )
        val batchShareLock = listOf(
            ShareLockData(
                targetUserId = PLATFORM_TEST_SUPPLEMENTARY_USER_ID,
                targetUserRole = UserRole.USER,
                targetUserPublicKey = PLATFORM_TEST_SUPPLEMENTARY_USER_PUBLIC_KEY
            ),
            ShareLockData(
                targetUserId = PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID,
                targetUserRole = UserRole.USER,
                targetUserPublicKey = PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_PUBLIC_KEY
            )
        )

        // When
        callbackApiCall<ResultData<Unit>> {
            LockOperationsApi.batchShareLock(
                data = BatchShareLockOperationData(
                    baseOperation = BaseOperationData(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                    users = batchShareLock
                ).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        assertTrue {
            val response = callbackApiCall<ResultData<BasicLockUserResponse>> {
                LockOperationsApi.getLocksForUser(
                    data = GetLocksForUserData(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            assertNotNull(response.success)
            assertNotNull(response.success.result)
            response.success.result.devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID
            }
        }
        assertTrue {
            val response = callbackApiCall<ResultData<BasicLockUserResponse>> {
                LockOperationsApi.getLocksForUser(
                    data = GetLocksForUserData(PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            assertNotNull(response.success)
            assertNotNull(response.success.result)
            response.success.result.devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID
            }
        }

        // Given - shouldRevokeAccessToLockUsingContext
        // When
        callbackApiCall<ResultData<Unit>> {
            LockOperationsApi.revokeAccessToLock(
                data = RevokeAccessToLockOperationData(
                    baseOperation = BaseOperationData(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                    users = listOf(PLATFORM_TEST_SUPPLEMENTARY_USER_ID, PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID)
                ).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        assertFalse {
            val response = callbackApiCall<ResultData<BasicLockUserResponse>> {
                LockOperationsApi.getLocksForUser(
                    data = GetLocksForUserData(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            assertNotNull(response.success)
            assertNotNull(response.success.result)
            response.success.result.devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID
            }
        }
        assertFalse {
            val response = callbackApiCall<ResultData<BasicLockUserResponse>> {
                LockOperationsApi.getLocksForUser(
                    data = GetLocksForUserData(PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            assertNotNull(response.success)
            assertNotNull(response.success.result)
            response.success.result.devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID
            }
        }
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDuration() = runTest {
        // Given
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        val registerKeyResponse = callbackApiCall<ResultData<BasicRegisterEphemeralKeyResponse>> {
            AccountApi.registerEphemeralKey(
                data = RegisterEphemeralKeyData(PLATFORM_TEST_MAIN_USER_PUBLIC_KEY, PLATFORM_TEST_MAIN_USER_PRIVATE_KEY).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(registerKeyResponse.success)
        assertNotNull(registerKeyResponse.success.result)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = registerKeyResponse.success.result.certificateChain
        val updatedUnlockDuration = randomInt(1, 10)
        val baseOperation = BaseOperationData(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            userPrivateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
            lockId = PLATFORM_TEST_MAIN_LOCK_ID
        )

        // When
        callbackApiCall<ResultData<Unit>> {
            LockOperationsApi.updateSecureSettingUnlockDuration(
                data = UpdateSecureSettingUnlockDurationData(
                    baseOperation = baseOperation,
                    unlockDuration = updatedUnlockDuration
                ).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        val response = callbackApiCall<ResultData<BasicLockResponse>> {
            LockOperationsApi.getSingleLock(
                data = GetSingleLockData(PLATFORM_TEST_MAIN_LOCK_ID).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }

        // Then
        assertNotNull(response.success)
        assertNotNull(response.success.result)
        assertEquals(updatedUnlockDuration.toDouble(), response.success.result.settings.unlockTime)
    }

    /*@Test
    fun shouldUpdateSecureSettingUnlockDurationUsingContext() = runTest {
        // Given
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        val registerKeyResponse = callbackApiCall<ResultData<BasicRegisterEphemeralKeyResponse>> {
            AccountApi.registerEphemeralKey(
                data = RegisterEphemeralKeyData(PLATFORM_TEST_MAIN_USER_PUBLIC_KEY, PLATFORM_TEST_MAIN_USER_PRIVATE_KEY).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(registerKeyResponse.success)
        assertNotNull(registerKeyResponse.success.result)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = registerKeyResponse.success.result.certificateChain
        val updatedUnlockDuration = 1
        ContextManager.setOperationContext(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray(),
            privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
            isKeyPairVerified = true
        )

        // When
        LockOperationsApi.updateSecureSettingUnlockDuration(
            updateSecureSettingUnlockDuration = LockOperations.UpdateSecureSettingUnlockDuration(
                baseOperation = LockOperations.BaseOperation(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                unlockDuration = updatedUnlockDuration
            )
        )

        // Then
        val lock = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID)
        assertEquals(updatedUnlockDuration.toDouble(), lock.settings.unlockTime)
    }

    @Test
    fun shouldUpdateAndRemoveSecureSettingUnlockBetween() = runTest {
        // Given
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        val registerKeyResponse = callbackApiCall<ResultData<BasicRegisterEphemeralKeyResponse>> {
            AccountApi.registerEphemeralKey(
                data = RegisterEphemeralKeyData(PLATFORM_TEST_MAIN_USER_PUBLIC_KEY, PLATFORM_TEST_MAIN_USER_PRIVATE_KEY).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(registerKeyResponse.success)
        assertNotNull(registerKeyResponse.success.result)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = registerKeyResponse.success.result.certificateChain
        val now = Clock.System.now()
        val min = (now - 1.minutes).toLocalDateTime(TimeZone.UTC)
        val max = (now + 5.minutes).toLocalDateTime(TimeZone.UTC)
        val updatedUnlockBetween = UnlockBetweenData(
            start = "${min.hour.toString().padStart(2, '0')}:${min.minute.toString().padStart(2, '0')}",
            end = "${max.hour.toString().padStart(2, '0')}:${max.minute.toString().padStart(2, '0')}",
            timezone = TimeZone.UTC.id,
            days = listOf(DayOfWeek.entries.random()),
            exceptions = emptyList()
        )
        val addBaseOperation = BaseOperationData(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            userPrivateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
            lockId = PLATFORM_TEST_MAIN_LOCK_ID
        )

        // When
        LockOperationsApi.updateSecureSettingUnlockBetween(
            updateSecureSettingUnlockBetween = LockOperations.UpdateSecureSettingUnlockBetween(
                baseOperation = addBaseOperation,
                unlockBetween = updatedUnlockBetween
            )
        )

        // Then
        var lock = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID)
        assertNotNull(lock.settings.unlockBetweenWindow)
        assertEquals(updatedUnlockBetween.start, lock.settings.unlockBetweenWindow.start)
        assertEquals(updatedUnlockBetween.end, lock.settings.unlockBetweenWindow.end)
        assertEquals(updatedUnlockBetween.timezone, lock.settings.unlockBetweenWindow.timezone)
        assertEquals(updatedUnlockBetween.days, lock.settings.unlockBetweenWindow.days)

        // Given - shouldRemoveSecureSettingUnlockBetween
        val removeBaseOperation = LockOperations.BaseOperation(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            userPrivateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
            lockId = PLATFORM_TEST_MAIN_LOCK_ID
        )

        // When
        LockOperationsApi.updateSecureSettingUnlockBetween(
            updateSecureSettingUnlockBetween = LockOperations.UpdateSecureSettingUnlockBetween(
                baseOperation = removeBaseOperation,
                unlockBetween = null
            )
        )

        // Then
        lock = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID)
        assertNull(lock.settings.unlockBetweenWindow)
    }

    @Test
    fun shouldUpdateAndRemoveSecureSettingUnlockBetweenUsingContext() = runTest {
        // Given
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        val registerKeyResponse = callbackApiCall<ResultData<BasicRegisterEphemeralKeyResponse>> {
            AccountApi.registerEphemeralKey(
                data = RegisterEphemeralKeyData(PLATFORM_TEST_MAIN_USER_PUBLIC_KEY, PLATFORM_TEST_MAIN_USER_PRIVATE_KEY).toJson(),
                callback = staticCFunction(::testCallback)
            )
        }
        assertNotNull(registerKeyResponse.success)
        assertNotNull(registerKeyResponse.success.result)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = registerKeyResponse.success.result.certificateChain
        val now = Clock.System.now()
        val min = now.minus(5.minutes).toLocalDateTime(TimeZone.UTC)
        val max = now.plus(10.minutes).toLocalDateTime(TimeZone.UTC)
        val updatedUnlockBetween = UnlockBetweenData(
            start = "${min.hour.toString().padStart(2, '0')}:${min.minute.toString().padStart(2, '0')}",
            end = "${max.hour.toString().padStart(2, '0')}:${max.minute.toString().padStart(2, '0')}",
            timezone = TimeZone.UTC.id,
            days = listOf(DayOfWeek.entries.random()),
            exceptions = emptyList()
        )
        ContextManager.setOperationContext(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray(),
            privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
            isKeyPairVerified = true
        )

        // When
        LockOperationsApi.updateSecureSettingUnlockBetween(
            updateSecureSettingUnlockBetween = LockOperations.UpdateSecureSettingUnlockBetween(
                baseOperation = LockOperations.BaseOperation(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                unlockBetween = updatedUnlockBetween
            )
        )

        // Then
        var lock = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID)
        assertNotNull(lock.settings.unlockBetweenWindow)
        assertEquals(updatedUnlockBetween.start, lock.settings.unlockBetweenWindow.start)
        assertEquals(updatedUnlockBetween.end, lock.settings.unlockBetweenWindow.end)
        assertEquals(updatedUnlockBetween.timezone, lock.settings.unlockBetweenWindow.timezone)
        assertEquals(updatedUnlockBetween.days, lock.settings.unlockBetweenWindow.days)

        // Given
        LockOperationsApi.updateSecureSettingUnlockBetween(
            updateSecureSettingUnlockBetween = LockOperations.UpdateSecureSettingUnlockBetween(
                baseOperation = LockOperations.BaseOperation(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                unlockBetween = null
            )
        )

        // Then
        lock = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID)
        assertNull(lock.settings.unlockBetweenWindow)
    }*/

    @Test
    fun shouldGetLockAuditTrail() = runTest {
        runBlocking {
            // Given
            callbackApiCall<ResultData<BasicTokenResponse>> {
                AccountlessApi.login(
                    data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            val now = Clock.System.now()
            val start = now.minus(14.days).epochSeconds
            val end = now.epochSeconds

            // When
            val lockAuditTrail = callbackApiCall<ResultData<List<BasicAuditResponse>>> {
                LockOperationsApi.getLockAuditTrail(
                    data = GetLockAuditTrailData(PLATFORM_TEST_MAIN_LOCK_ID, start, end).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // Then
            assertNotNull(lockAuditTrail.success)
            assertNotNull(lockAuditTrail.success.result)
            assertTrue { lockAuditTrail.success.result.isNotEmpty() }
        }
    }

    @Test
    fun shouldGetAuditForUser() = runTest {
        runBlocking {
            // Given
            callbackApiCall<ResultData<BasicTokenResponse>> {
                AccountlessApi.login(
                    data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }
            val now = Clock.System.now()
            val start = now.minus(14.days).epochSeconds
            val end = now.epochSeconds

            // When
            val auditForUser = callbackApiCall<ResultData<List<BasicAuditResponse>>> {
                LockOperationsApi.getAuditForUser(
                    data = GetAuditForUserData(PLATFORM_TEST_MAIN_USER_ID, start, end).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // Then
            assertNotNull(auditForUser.success)
            assertNotNull(auditForUser.success.result)
            assertTrue { auditForUser.success.result.isNotEmpty() }
        }
    }

    @Test
    fun shouldThrowExceptionWhenOperationContextIsMissing() = runTest {
        runBlocking {
            // When
            val revokeAccessToLockUsingContextException = callbackApiCall<ResultData<Unit>> {
                LockOperationsApi.revokeAccessToLock(
                    data = RevokeAccessToLockOperationData(
                        baseOperation = BaseOperationData(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                        users = emptyList()
                    ).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            val shareLockUsingContextException = callbackApiCall<ResultData<Unit>> {
                LockOperationsApi.shareLock(
                    data = ShareLockOperationData(
                        baseOperation = BaseOperationData(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                        shareLock = ShareLockData(
                            targetUserId = randomUuid(),
                            targetUserRole = UserRole.USER,
                            targetUserPublicKey = CryptoManager.generateRawKeyPair().public.encodeByteArrayToBase64()
                        )
                    ).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            val unlockUsingContextException = callbackApiCall<ResultData<Unit>> {
                LockOperationsApi.unlock(
                    data = UnlockOperationData(
                        baseOperation = BaseOperationData(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                        directAccessEndpoints = emptyList()
                    ).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            val updateSecureSettingUnlockDurationUsingContextException = callbackApiCall<ResultData<Unit>> {
                LockOperationsApi.updateSecureSettingUnlockDuration(
                    data = UpdateSecureSettingUnlockDurationData(
                        baseOperation = BaseOperationData(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                        unlockDuration = 0
                    ).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            val updateSecureSettingUnlockBetweenUsingContextException = callbackApiCall<ResultData<Unit>> {
                LockOperationsApi.updateSecureSettingUnlockBetween(
                    data = UpdateSecureSettingUnlockBetweenData(
                        baseOperation = BaseOperationData(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                        unlockBetween = UnlockBetweenData(
                            start = "",
                            end = "",
                            timezone = TimeZone.UTC.id,
                            days = emptyList(),
                            exceptions = emptyList()
                        )
                    ).toJson(),
                    callback = staticCFunction(::testCallback)
                )
            }

            // Then
            assertNotNull(revokeAccessToLockUsingContextException.failure)
            assertContains(revokeAccessToLockUsingContextException.failure.exceptionType, MissingContextFieldException::class.simpleName!!)
            assertContains("User ID is missing", revokeAccessToLockUsingContextException.failure.exceptionMessage)
            assertNotNull(shareLockUsingContextException.failure)
            assertContains(shareLockUsingContextException.failure.exceptionType, MissingContextFieldException::class.simpleName!!)
            assertContains("User ID is missing", shareLockUsingContextException.failure.exceptionMessage)
            assertNotNull(unlockUsingContextException.failure)
            assertContains(unlockUsingContextException.failure.exceptionType, MissingContextFieldException::class.simpleName!!)
            assertContains("User ID is missing", unlockUsingContextException.failure.exceptionMessage)
            assertNotNull(updateSecureSettingUnlockDurationUsingContextException.failure)
            assertContains(updateSecureSettingUnlockDurationUsingContextException.failure.exceptionType, MissingContextFieldException::class.simpleName!!)
            assertContains("User ID is missing", updateSecureSettingUnlockDurationUsingContextException.failure.exceptionMessage)
            assertNotNull(updateSecureSettingUnlockBetweenUsingContextException.failure)
            assertContains(updateSecureSettingUnlockBetweenUsingContextException.failure.exceptionType, MissingContextFieldException::class.simpleName!!)
            assertContains("User ID is missing", updateSecureSettingUnlockBetweenUsingContextException.failure.exceptionMessage)
        }
    }
}