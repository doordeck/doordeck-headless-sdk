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
import com.doordeck.multiplatform.sdk.TestCallback
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.TestConstants.TEST_SUPPLEMENTARY_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_SUPPLEMENTARY_USER_ID
import com.doordeck.multiplatform.sdk.callbackApiCall
import com.doordeck.multiplatform.sdk.context.ContextManager
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.exceptions.MissingContextFieldException
import com.doordeck.multiplatform.sdk.model.common.DayOfWeek
import com.doordeck.multiplatform.sdk.model.common.UserRole
import com.doordeck.multiplatform.sdk.model.data.BaseOperationData
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
import com.doordeck.multiplatform.sdk.model.data.OperationContextData
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
import com.doordeck.multiplatform.sdk.randomUuidString
import com.doordeck.multiplatform.sdk.unwrap
import com.doordeck.multiplatform.sdk.unwrapFailure
import com.doordeck.multiplatform.sdk.util.Utils.certificateChainToString
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.toJson
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes

class LockOperationsApiTest : CallbackTest() {

    @Test
    fun shouldGetSingleLock() = runTest {
        // Given
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // When
        val response = callbackApiCall<ResultData<BasicLockResponse>> {
            LockOperationsApi.getSingleLock(
                data = GetSingleLockData(PLATFORM_TEST_MAIN_LOCK_ID).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // Then
        assertEquals(PLATFORM_TEST_MAIN_LOCK_ID, response.id)
    }

    @Test
    fun shouldUpdateLockName() = runTest {
        // Given
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        val updatedLockName = "Doordeck Fusion Test Site - ${randomUuidString()}"

        // When
        callbackApiCall<ResultData<Unit>> {
            LockOperationsApi.updateLockName(
                data = UpdateLockNameData(PLATFORM_TEST_MAIN_LOCK_ID, updatedLockName).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // Then
        val lockResponse = callbackApiCall<ResultData<BasicLockResponse>> {
            LockOperationsApi.getSingleLock(
                data = GetSingleLockData(PLATFORM_TEST_MAIN_LOCK_ID).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        assertEquals(updatedLockName, lockResponse.name)
    }

    @Test
    fun shouldUpdateLockFavourite() = runTest {
        // Given
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        val updatedFavourite = true

        // When
        callbackApiCall<ResultData<Unit>> {
            LockOperationsApi.updateLockFavourite(
                data = UpdateLockFavouriteData(PLATFORM_TEST_MAIN_LOCK_ID, updatedFavourite).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // Then
        val lockResponse = callbackApiCall<ResultData<BasicLockResponse>> {
            LockOperationsApi.getSingleLock(
                data = GetSingleLockData(PLATFORM_TEST_MAIN_LOCK_ID).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        assertEquals(updatedFavourite, lockResponse.favourite)
    }

    @Test
    fun shouldUpdateLockSettingDefaultName() = runTest {
        // Given
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        val updatedLockDefaultName = "Doordeck Fusion Test Site - ${randomUuidString()}"

        // When
        callbackApiCall<ResultData<Unit>> {
            LockOperationsApi.updateLockSettingDefaultName(
                data = UpdateLockSettingDefaultNameData(PLATFORM_TEST_MAIN_LOCK_ID, updatedLockDefaultName).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // Then
        val lockResponse = callbackApiCall<ResultData<BasicLockResponse>> {
            LockOperationsApi.getSingleLock(
                data = GetSingleLockData(PLATFORM_TEST_MAIN_LOCK_ID).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        assertEquals(updatedLockDefaultName, lockResponse.settings.defaultName)
    }

    @Test
    fun shouldSetAndRemoveLockSettingPermittedAddresses() = runTest {
        // Given - shouldSetLockSettingPermittedAddresses
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        val addedLockPermittedAddresses = listOf("95.19.38.42")

        // When
        callbackApiCall<ResultData<Unit>> {
            LockOperationsApi.setLockSettingPermittedAddresses(
                data = SetLockSettingPermittedAddressesData(
                    PLATFORM_TEST_MAIN_LOCK_ID,
                    addedLockPermittedAddresses
                ).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // Then
        var lockResponse = callbackApiCall<ResultData<BasicLockResponse>> {
            LockOperationsApi.getSingleLock(
                data = GetSingleLockData(PLATFORM_TEST_MAIN_LOCK_ID).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        assertTrue { lockResponse.settings.permittedAddresses.isNotEmpty() }
        assertContains(addedLockPermittedAddresses, lockResponse.settings.permittedAddresses.first())

        // Given - shouldRemoveLockSettingPermittedAddresses
        val removedLockPermittedAddresses = listOf<String>()

        // When
        callbackApiCall<ResultData<Unit>> {
            LockOperationsApi.setLockSettingPermittedAddresses(
                data = SetLockSettingPermittedAddressesData(
                    PLATFORM_TEST_MAIN_LOCK_ID,
                    removedLockPermittedAddresses
                ).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // Then
        lockResponse = callbackApiCall<ResultData<BasicLockResponse>> {
            LockOperationsApi.getSingleLock(
                data = GetSingleLockData(PLATFORM_TEST_MAIN_LOCK_ID).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        assertTrue { lockResponse.settings.permittedAddresses.isEmpty() }
    }

    @Test
    fun shouldUpdateLockSettingHidden() = runTest {
        // Given
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        val updatedHidden = false

        // When
        callbackApiCall<ResultData<Unit>> {
            LockOperationsApi.updateLockSettingHidden(
                data = UpdateLockSettingHiddenData(PLATFORM_TEST_MAIN_LOCK_ID, updatedHidden).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // Then
        val lockResponse = callbackApiCall<ResultData<BasicLockResponse>> {
            LockOperationsApi.getSingleLock(
                data = GetSingleLockData(PLATFORM_TEST_MAIN_LOCK_ID).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        assertEquals(updatedHidden, lockResponse.settings.hidden)
    }

    @Test
    fun shouldSetAndRemoveLockSettingTimeRestrictions() = runTest {
        // Given - shouldSetLockSettingTimeRestrictions
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        val now = Clock.System.now()
        val min = (now - 1.minutes).toLocalDateTime(TimeZone.UTC)
        val max = (now + 5.minutes).toLocalDateTime(TimeZone.UTC)
        val addedTimeRestriction = TimeRequirementData(
            start = "${min.hour.toString().padStart(2, '0')}:${min.minute.toString().padStart(2, '0')}",
            end = "${max.hour.toString().padStart(2, '0')}:${max.minute.toString().padStart(2, '0')}",
            timezone = TimeZone.UTC.id,
            days = setOf(DayOfWeek.entries.random())
        )

        // When
        callbackApiCall<ResultData<Unit>> {
            LockOperationsApi.setLockSettingTimeRestrictions(
                data = SetLockSettingTimeRestrictionsData(
                    PLATFORM_TEST_MAIN_LOCK_ID,
                    listOf(addedTimeRestriction)
                ).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // Then
        var lockResponse = callbackApiCall<ResultData<BasicLockResponse>> {
            LockOperationsApi.getSingleLock(
                data = GetSingleLockData(PLATFORM_TEST_MAIN_LOCK_ID).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        val actualTime = lockResponse.settings.usageRequirements?.time?.firstOrNull()
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
                callback = TestCallback
            )
        }.unwrap()

        // Then
        lockResponse = callbackApiCall<ResultData<BasicLockResponse>> {
            LockOperationsApi.getSingleLock(
                data = GetSingleLockData(PLATFORM_TEST_MAIN_LOCK_ID).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        assertEquals(0, lockResponse.settings.usageRequirements?.time?.size)
    }

    @Test
    fun shouldUpdateAndRemoveLockSettingLocationRestrictions() = runTest {
        // Given - shouldUpdateLockSettingLocationRestrictions
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()
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
                data = UpdateLockSettingLocationRestrictionsData(
                    PLATFORM_TEST_MAIN_LOCK_ID,
                    addedLocationRestriction
                ).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // Then
        var lockResponse = callbackApiCall<ResultData<BasicLockResponse>> {
            LockOperationsApi.getSingleLock(
                data = GetSingleLockData(PLATFORM_TEST_MAIN_LOCK_ID).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        assertNotNull(lockResponse.settings.usageRequirements?.location)
        assertEquals(
            addedLocationRestriction.latitude,
            lockResponse.settings.usageRequirements.location.latitude
        )
        assertEquals(
            addedLocationRestriction.longitude,
            lockResponse.settings.usageRequirements.location.longitude
        )
        assertEquals(
            addedLocationRestriction.enabled,
            lockResponse.settings.usageRequirements.location.enabled
        )
        assertEquals(
            addedLocationRestriction.radius,
            lockResponse.settings.usageRequirements.location.radius
        )
        assertEquals(
            addedLocationRestriction.accuracy,
            lockResponse.settings.usageRequirements.location.accuracy
        )

        // Given - shouldRemoveLockSettingLocationRestrictions
        val removedLocationRestriction = null

        // When
        callbackApiCall<ResultData<Unit>> {
            LockOperationsApi.updateLockSettingLocationRestrictions(
                data = UpdateLockSettingLocationRestrictionsData(
                    PLATFORM_TEST_MAIN_LOCK_ID,
                    removedLocationRestriction
                ).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // Then
        lockResponse = callbackApiCall<ResultData<BasicLockResponse>> {
            LockOperationsApi.getSingleLock(
                data = GetSingleLockData(PLATFORM_TEST_MAIN_LOCK_ID).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        assertEquals(removedLocationRestriction, lockResponse.settings.usageRequirements?.location)
    }

    @Test
    fun shouldGetUserPublicKey() = runTest {
        // Given
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // When
        val result = callbackApiCall<ResultData<BasicUserPublicKeyResponse>> {
            LockOperationsApi.getUserPublicKey(
                data = GetUserPublicKeyData(TEST_MAIN_USER_EMAIL, true).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // Then
        assertEquals(PLATFORM_TEST_MAIN_USER_ID, result.id)
    }

    @Test
    fun shouldGetUserPublicKeyByEmail() = runTest {
        // Given
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // When
        val result = callbackApiCall<ResultData<BasicUserPublicKeyResponse>> {
            LockOperationsApi.getUserPublicKeyByEmail(
                data = GetUserPublicKeyByEmailData(TEST_MAIN_USER_EMAIL).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // Then
        assertEquals(PLATFORM_TEST_MAIN_USER_ID, result.id)
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKey() = runTest {
        // Given
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // When
        val result = callbackApiCall<ResultData<BasicUserPublicKeyResponse>> {
            LockOperationsApi.getUserPublicKeyByLocalKey(
                data = GetUserPublicKeyByLocalKeyData(TEST_MAIN_USER_ID).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // Then
        assertEquals(PLATFORM_TEST_MAIN_USER_ID, result.id)
    }

    @Test
    fun shouldGetUserPublicKeyByEmails() = runTest {
        // Given
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // When
        val result = callbackApiCall<ResultData<List<BasicBatchUserPublicKeyResponse>>> {
            LockOperationsApi.getUserPublicKeyByEmails(
                data = GetUserPublicKeyByEmailsData(
                    listOf(
                        TEST_MAIN_USER_EMAIL,
                        TEST_SUPPLEMENTARY_USER_EMAIL
                    )
                ).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // Then
        assertTrue { result.isNotEmpty() }
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeys() = runTest {
        // Given
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // When
        val result = callbackApiCall<ResultData<List<BasicBatchUserPublicKeyResponse>>> {
            LockOperationsApi.getUserPublicKeyByLocalKeys(
                data = GetUserPublicKeyByLocalKeysData(listOf(TEST_MAIN_USER_ID, TEST_SUPPLEMENTARY_USER_ID)).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // Then
        assertTrue { result.isNotEmpty() }
    }

    @Test
    fun shouldGetUsersForLock() = runTest {
        // Given
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // When
        val usersForLock = callbackApiCall<ResultData<List<BasicUserLockResponse>>> {
            LockOperationsApi.getUsersForLock(
                data = GetUsersForLockData(PLATFORM_TEST_MAIN_LOCK_ID).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // Then
        assertTrue { usersForLock.isNotEmpty() }
        assertTrue { usersForLock.any { it.userId == PLATFORM_TEST_MAIN_USER_ID } }
    }

    @Test
    fun shouldGetLockForUser() = runTest {
        // Given
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // When
        val locksForUser = callbackApiCall<ResultData<BasicLockUserResponse>> {
            LockOperationsApi.getLocksForUser(
                data = GetLocksForUserData(TEST_MAIN_USER_ID).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // Then
        assertTrue { locksForUser.devices.isNotEmpty() }
        assertTrue { locksForUser.devices.any { it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID } }
    }

    @Test
    fun shouldGetPinnedLocks() = runTest {
        // Given
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // When
        val pinnedLocks = callbackApiCall<ResultData<List<BasicLockResponse>>> {
            LockOperationsApi.getPinnedLocks(TestCallback)
        }.unwrap()

        // Then
        assertTrue { pinnedLocks.isNotEmpty() }
    }

    @Test
    fun shouldGetShareableLocks() = runTest {
        // Given
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // When
        val shareableLocks = callbackApiCall<ResultData<List<BasicShareableLockResponse>>> {
            LockOperationsApi.getShareableLocks(TestCallback)
        }.unwrap()

        // Then
        assertTrue { shareableLocks.isNotEmpty() }
        assertTrue { shareableLocks.any { it.id == PLATFORM_TEST_MAIN_LOCK_ID } }
    }

    @Test
    fun shouldUnlock() = runTest {
        // Given
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        val registerKeyResponse = callbackApiCall<ResultData<BasicRegisterEphemeralKeyResponse>> {
            AccountApi.registerEphemeralKey(
                data = RegisterEphemeralKeyData(
                    PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
                    PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
                ).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = registerKeyResponse.certificateChain
        val baseOperation = BaseOperationData(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            userPrivateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
            lockId = PLATFORM_TEST_MAIN_LOCK_ID
        )

        // When
        callbackApiCall<ResultData<Unit>> {
            LockOperationsApi.unlock(
                data = UnlockOperationData(baseOperation).toJson(),
                callback = TestCallback
            )
        }.unwrap()
    }

    @Test
    fun shouldUnlockUsingContext() = runTest {
        // Given
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        val registerKeyResponse = callbackApiCall<ResultData<BasicRegisterEphemeralKeyResponse>> {
            AccountApi.registerEphemeralKey(
                data = RegisterEphemeralKeyData(PLATFORM_TEST_MAIN_USER_PUBLIC_KEY, PLATFORM_TEST_MAIN_USER_PRIVATE_KEY).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = registerKeyResponse.certificateChain
        ContextManager.setOperationContext(
            OperationContextData(
                userId = PLATFORM_TEST_MAIN_USER_ID,
                certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.certificateChainToString(),
                publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
                privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
                isKeyPairVerified = true
            ).toJson()
        )

        // When
        callbackApiCall<ResultData<Unit>> {
            LockOperationsApi.unlock(
                data = UnlockOperationData(
                    baseOperation = BaseOperationData(lockId = PLATFORM_TEST_MAIN_LOCK_ID)
                ).toJson(),
                callback = TestCallback
            )
        }.unwrap()
    }

    @Test
    fun shouldShareAndRevokeLock() = runTest {
        // Given - shouldShareLock
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        val registerKeyResponse = callbackApiCall<ResultData<BasicRegisterEphemeralKeyResponse>> {
            AccountApi.registerEphemeralKey(
                data = RegisterEphemeralKeyData(PLATFORM_TEST_MAIN_USER_PUBLIC_KEY, PLATFORM_TEST_MAIN_USER_PRIVATE_KEY).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = registerKeyResponse.certificateChain
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
                callback = TestCallback
            )
        }.unwrap()

        // Then
        var locksResponse =  callbackApiCall<ResultData<BasicLockUserResponse>> {
            LockOperationsApi.getLocksForUser(
                data = GetLocksForUserData(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        assertTrue { locksResponse.devices.any { it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID } }

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
                callback = TestCallback
            )
        }.unwrap()

        // Then
        locksResponse = callbackApiCall<ResultData<BasicLockUserResponse>> {
            LockOperationsApi.getLocksForUser(
                data = GetLocksForUserData(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        assertFalse { locksResponse.devices.any { it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID } }
    }

    @Test
    fun shouldBatchShareAndRevokeLock() = runTest {
        // Given - shouldShareLockUsingContext
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        val registerKeyResponse = callbackApiCall<ResultData<BasicRegisterEphemeralKeyResponse>> {
            AccountApi.registerEphemeralKey(
                data = RegisterEphemeralKeyData(PLATFORM_TEST_MAIN_USER_PUBLIC_KEY, PLATFORM_TEST_MAIN_USER_PRIVATE_KEY).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = registerKeyResponse.certificateChain
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
                callback = TestCallback
            )
        }.unwrap()

        // Then
        assertTrue {
            val response = callbackApiCall<ResultData<BasicLockUserResponse>> {
                LockOperationsApi.getLocksForUser(
                    data = GetLocksForUserData(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).toJson(),
                    callback = TestCallback
                )
            }.unwrap()
            response.devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID
            }
        }
        assertTrue {
            val response = callbackApiCall<ResultData<BasicLockUserResponse>> {
                LockOperationsApi.getLocksForUser(
                    data = GetLocksForUserData(PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID).toJson(),
                    callback = TestCallback
                )
            }.unwrap()
            response.devices.any {
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
                callback = TestCallback
            )
        }.unwrap()

        // Then
        assertFalse {
            val response = callbackApiCall<ResultData<BasicLockUserResponse>> {
                LockOperationsApi.getLocksForUser(
                    data = GetLocksForUserData(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).toJson(),
                    callback = TestCallback
                )
            }.unwrap()
            response.devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID
            }
        }
        assertFalse {
            val response = callbackApiCall<ResultData<BasicLockUserResponse>> {
                LockOperationsApi.getLocksForUser(
                    data = GetLocksForUserData(PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID).toJson(),
                    callback = TestCallback
                )
            }.unwrap()
            response.devices.any {
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
                callback = TestCallback
            )
        }.unwrap()
        val registerKeyResponse = callbackApiCall<ResultData<BasicRegisterEphemeralKeyResponse>> {
            AccountApi.registerEphemeralKey(
                data = RegisterEphemeralKeyData(PLATFORM_TEST_MAIN_USER_PUBLIC_KEY, PLATFORM_TEST_MAIN_USER_PRIVATE_KEY).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = registerKeyResponse.certificateChain
        ContextManager.setOperationContext(
            OperationContextData(
                userId = PLATFORM_TEST_MAIN_USER_ID,
                certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.certificateChainToString(),
                publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
                privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
                isKeyPairVerified = true
            ).toJson()
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
                callback = TestCallback
            )
        }.unwrap()

        // Then
        var locksResponse = callbackApiCall<ResultData<BasicLockUserResponse>> {
            LockOperationsApi.getLocksForUser(
                data = GetLocksForUserData(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        assertTrue { locksResponse.devices.any { it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID } }

        // Given - shouldRevokeAccessToLockUsingContext
        // When
        callbackApiCall<ResultData<Unit>> {
            LockOperationsApi.revokeAccessToLock(
                data = RevokeAccessToLockOperationData(
                    baseOperation = BaseOperationData(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                    users = listOf(PLATFORM_TEST_SUPPLEMENTARY_USER_ID)
                ).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // Then
        locksResponse = callbackApiCall<ResultData<BasicLockUserResponse>> {
            LockOperationsApi.getLocksForUser(
                data = GetLocksForUserData(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        assertFalse {
            locksResponse.devices.any { it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID }
        }
    }

    @Test
    fun shouldBatchShareAndRevokeLockUsingContext() = runTest {
        // Given - shouldShareLockUsingContext
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        val registerKeyResponse = callbackApiCall<ResultData<BasicRegisterEphemeralKeyResponse>> {
            AccountApi.registerEphemeralKey(
                data = RegisterEphemeralKeyData(PLATFORM_TEST_MAIN_USER_PUBLIC_KEY, PLATFORM_TEST_MAIN_USER_PRIVATE_KEY).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = registerKeyResponse.certificateChain
        ContextManager.setOperationContext(
            OperationContextData(
                userId = PLATFORM_TEST_MAIN_USER_ID,
                certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.certificateChainToString(),
                publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
                privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
                isKeyPairVerified = true
            ).toJson()
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
                callback = TestCallback
            )
        }.unwrap()

        // Then
        assertTrue {
            val response = callbackApiCall<ResultData<BasicLockUserResponse>> {
                LockOperationsApi.getLocksForUser(
                    data = GetLocksForUserData(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).toJson(),
                    callback = TestCallback
                )
            }.unwrap()
            response.devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID
            }
        }
        assertTrue {
            val response = callbackApiCall<ResultData<BasicLockUserResponse>> {
                LockOperationsApi.getLocksForUser(
                    data = GetLocksForUserData(PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID).toJson(),
                    callback = TestCallback
                )
            }.unwrap()
            response.devices.any {
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
                callback = TestCallback
            )
        }.unwrap()

        // Then
        assertFalse {
            val response = callbackApiCall<ResultData<BasicLockUserResponse>> {
                LockOperationsApi.getLocksForUser(
                    data = GetLocksForUserData(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).toJson(),
                    callback = TestCallback
                )
            }.unwrap()
            response.devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID
            }
        }
        assertFalse {
            val response = callbackApiCall<ResultData<BasicLockUserResponse>> {
                LockOperationsApi.getLocksForUser(
                    data = GetLocksForUserData(PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID).toJson(),
                    callback = TestCallback
                )
            }.unwrap()
            response.devices.any {
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
                callback = TestCallback
            )
        }.unwrap()
        val registerKeyResponse = callbackApiCall<ResultData<BasicRegisterEphemeralKeyResponse>> {
            AccountApi.registerEphemeralKey(
                data = RegisterEphemeralKeyData(PLATFORM_TEST_MAIN_USER_PUBLIC_KEY, PLATFORM_TEST_MAIN_USER_PRIVATE_KEY).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = registerKeyResponse.certificateChain
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
                callback = TestCallback
            )
        }.unwrap()

        // Then
        val response = callbackApiCall<ResultData<BasicLockResponse>> {
            LockOperationsApi.getSingleLock(
                data = GetSingleLockData(PLATFORM_TEST_MAIN_LOCK_ID).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        assertEquals(updatedUnlockDuration.toDouble(), response.settings.unlockTime)
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDurationUsingContext() = runTest {
        // Given
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        val registerKeyResponse = callbackApiCall<ResultData<BasicRegisterEphemeralKeyResponse>> {
            AccountApi.registerEphemeralKey(
                data = RegisterEphemeralKeyData(PLATFORM_TEST_MAIN_USER_PUBLIC_KEY, PLATFORM_TEST_MAIN_USER_PRIVATE_KEY).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = registerKeyResponse.certificateChain
        val updatedUnlockDuration = 1
        ContextManager.setOperationContext(
            OperationContextData(
                userId = PLATFORM_TEST_MAIN_USER_ID,
                certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.certificateChainToString(),
                publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
                privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
                isKeyPairVerified = true
            ).toJson()
        )

        // When
        callbackApiCall<ResultData<Unit>> {
            LockOperationsApi.updateSecureSettingUnlockDuration(
                data = UpdateSecureSettingUnlockDurationData(
                    baseOperation = BaseOperationData(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                    unlockDuration = updatedUnlockDuration
                ).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // Then
        val response = callbackApiCall<ResultData<BasicLockResponse>> {
            LockOperationsApi.getSingleLock(
                data = GetSingleLockData(PLATFORM_TEST_MAIN_LOCK_ID).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        assertEquals(updatedUnlockDuration.toDouble(), response.settings.unlockTime)
    }

    @Test
    fun shouldUpdateAndRemoveSecureSettingUnlockBetween() = runTest {
        // Given
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        val registerKeyResponse = callbackApiCall<ResultData<BasicRegisterEphemeralKeyResponse>> {
            AccountApi.registerEphemeralKey(
                data = RegisterEphemeralKeyData(PLATFORM_TEST_MAIN_USER_PUBLIC_KEY, PLATFORM_TEST_MAIN_USER_PRIVATE_KEY).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = registerKeyResponse.certificateChain
        val now = Clock.System.now()
        val min = (now - 1.minutes).toLocalDateTime(TimeZone.UTC)
        val max = (now + 5.minutes).toLocalDateTime(TimeZone.UTC)
        val updatedUnlockBetween = UnlockBetweenData(
            start = "${min.hour.toString().padStart(2, '0')}:${min.minute.toString().padStart(2, '0')}",
            end = "${max.hour.toString().padStart(2, '0')}:${max.minute.toString().padStart(2, '0')}",
            timezone = TimeZone.UTC.id,
            days = setOf(DayOfWeek.entries.random()),
            exceptions = emptyList()
        )
        val addBaseOperation = BaseOperationData(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            userPrivateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
            lockId = PLATFORM_TEST_MAIN_LOCK_ID
        )

        // When
        callbackApiCall<ResultData<Unit>> {
            LockOperationsApi.updateSecureSettingUnlockBetween(
                data = UpdateSecureSettingUnlockBetweenData(
                    baseOperation = addBaseOperation,
                    unlockBetween = updatedUnlockBetween
                ).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // Then
        var lockResponse = callbackApiCall<ResultData<BasicLockResponse>> {
            LockOperationsApi.getSingleLock(
                data = GetSingleLockData(PLATFORM_TEST_MAIN_LOCK_ID).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        assertNotNull(lockResponse.settings.unlockBetweenWindow)
        assertEquals(updatedUnlockBetween.start, lockResponse.settings.unlockBetweenWindow.start)
        assertEquals(updatedUnlockBetween.end, lockResponse.settings.unlockBetweenWindow.end)
        assertEquals(updatedUnlockBetween.timezone, lockResponse.settings.unlockBetweenWindow.timezone)
        assertEquals(updatedUnlockBetween.days, lockResponse.settings.unlockBetweenWindow.days)
        assertEquals(updatedUnlockBetween.exceptions, lockResponse.settings.unlockBetweenWindow.exceptions)

        // Given - shouldRemoveSecureSettingUnlockBetween
        val removeBaseOperation = BaseOperationData(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            userPrivateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
            lockId = PLATFORM_TEST_MAIN_LOCK_ID
        )

        // When
        callbackApiCall<ResultData<Unit>> {
            LockOperationsApi.updateSecureSettingUnlockBetween(
                data = UpdateSecureSettingUnlockBetweenData(
                    baseOperation = removeBaseOperation,
                    unlockBetween = null
                ).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // Then
        lockResponse = callbackApiCall<ResultData<BasicLockResponse>> {
            LockOperationsApi.getSingleLock(
                data = GetSingleLockData(PLATFORM_TEST_MAIN_LOCK_ID).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        assertNull(lockResponse.settings.unlockBetweenWindow)
    }

    @Test
    fun shouldUpdateAndRemoveSecureSettingUnlockBetweenUsingContext() = runTest {
        // Given
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        val registerKeyResponse = callbackApiCall<ResultData<BasicRegisterEphemeralKeyResponse>> {
            AccountApi.registerEphemeralKey(
                data = RegisterEphemeralKeyData(PLATFORM_TEST_MAIN_USER_PUBLIC_KEY, PLATFORM_TEST_MAIN_USER_PRIVATE_KEY).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = registerKeyResponse.certificateChain
        val now = Clock.System.now()
        val min = now.minus(5.minutes).toLocalDateTime(TimeZone.UTC)
        val max = now.plus(10.minutes).toLocalDateTime(TimeZone.UTC)
        val updatedUnlockBetween = UnlockBetweenData(
            start = "${min.hour.toString().padStart(2, '0')}:${min.minute.toString().padStart(2, '0')}",
            end = "${max.hour.toString().padStart(2, '0')}:${max.minute.toString().padStart(2, '0')}",
            timezone = TimeZone.UTC.id,
            days = setOf(DayOfWeek.entries.random()),
            exceptions = emptyList()
        )
        ContextManager.setOperationContext(
            OperationContextData(
                userId = PLATFORM_TEST_MAIN_USER_ID,
                certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.certificateChainToString(),
                publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
                privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
                isKeyPairVerified = true
            ).toJson()
        )

        // When
        callbackApiCall<ResultData<Unit>> {
            LockOperationsApi.updateSecureSettingUnlockBetween(
                data = UpdateSecureSettingUnlockBetweenData(
                    baseOperation = BaseOperationData(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                    unlockBetween = updatedUnlockBetween
                ).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // Then
        var lockResponse = callbackApiCall<ResultData<BasicLockResponse>> {
            LockOperationsApi.getSingleLock(
                data = GetSingleLockData(PLATFORM_TEST_MAIN_LOCK_ID).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        assertNotNull(lockResponse.settings.unlockBetweenWindow)
        assertEquals(updatedUnlockBetween.start, lockResponse.settings.unlockBetweenWindow.start)
        assertEquals(updatedUnlockBetween.end, lockResponse.settings.unlockBetweenWindow.end)
        assertEquals(updatedUnlockBetween.timezone, lockResponse.settings.unlockBetweenWindow.timezone)
        assertEquals(updatedUnlockBetween.days, lockResponse.settings.unlockBetweenWindow.days)
        assertEquals(updatedUnlockBetween.exceptions, lockResponse.settings.unlockBetweenWindow.exceptions)

        // Given
        callbackApiCall<ResultData<Unit>> {
            LockOperationsApi.updateSecureSettingUnlockBetween(
                data = UpdateSecureSettingUnlockBetweenData(
                    baseOperation = BaseOperationData(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                    unlockBetween = null
                ).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // Then
        lockResponse = callbackApiCall<ResultData<BasicLockResponse>> {
            LockOperationsApi.getSingleLock(
                data = GetSingleLockData(PLATFORM_TEST_MAIN_LOCK_ID).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        assertNull(lockResponse.settings.unlockBetweenWindow)
    }

    @Test
    fun shouldGetLockAuditTrail() = runTest {
        // Given
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        val now = Clock.System.now()
        val start = now.minus(14.days).epochSeconds
        val end = now.epochSeconds

        // When
        val lockAuditTrail = callbackApiCall<ResultData<List<BasicAuditResponse>>> {
            LockOperationsApi.getLockAuditTrail(
                data = GetLockAuditTrailData(PLATFORM_TEST_MAIN_LOCK_ID, start, end).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // Then
        assertTrue { lockAuditTrail.isNotEmpty() }
    }

    @Test
    fun shouldGetAuditForUser() = runTest {
        // Given
        callbackApiCall<ResultData<BasicTokenResponse>> {
            AccountlessApi.login(
                data = LoginData(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).toJson(),
                callback = TestCallback
            )
        }.unwrap()
        val now = Clock.System.now()
        val start = now.minus(14.days).epochSeconds
        val end = now.epochSeconds

        // When
        val auditForUser = callbackApiCall<ResultData<List<BasicAuditResponse>>> {
            LockOperationsApi.getAuditForUser(
                data = GetAuditForUserData(PLATFORM_TEST_MAIN_USER_ID, start, end).toJson(),
                callback = TestCallback
            )
        }.unwrap()

        // Then
        assertTrue { auditForUser.isNotEmpty() }
    }

    @Test
    fun shouldThrowExceptionWhenOperationContextIsMissing() = runTest {
        // When
        val revokeAccessToLockUsingContextException = callbackApiCall<ResultData<Unit>> {
            LockOperationsApi.revokeAccessToLock(
                data = RevokeAccessToLockOperationData(
                    baseOperation = BaseOperationData(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                    users = emptyList()
                ).toJson(),
                callback = TestCallback
            )
        }.unwrapFailure()

        val shareLockUsingContextException = callbackApiCall<ResultData<Unit>> {
            LockOperationsApi.shareLock(
                data = ShareLockOperationData(
                    baseOperation = BaseOperationData(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                    shareLock = ShareLockData(
                        targetUserId = randomUuidString(),
                        targetUserRole = UserRole.USER,
                        targetUserPublicKey = CryptoManager.generateRawKeyPair().public.encodeByteArrayToBase64()
                    )
                ).toJson(),
                callback = TestCallback
            )
        }.unwrapFailure()

        val unlockUsingContextException = callbackApiCall<ResultData<Unit>> {
            LockOperationsApi.unlock(
                data = UnlockOperationData(
                    baseOperation = BaseOperationData(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                    directAccessEndpoints = emptyList()
                ).toJson(),
                callback = TestCallback
            )
        }.unwrapFailure()

        val updateSecureSettingUnlockDurationUsingContextException = callbackApiCall<ResultData<Unit>> {
            LockOperationsApi.updateSecureSettingUnlockDuration(
                data = UpdateSecureSettingUnlockDurationData(
                    baseOperation = BaseOperationData(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                    unlockDuration = 0
                ).toJson(),
                callback = TestCallback
            )
        }.unwrapFailure()

        val updateSecureSettingUnlockBetweenUsingContextException = callbackApiCall<ResultData<Unit>> {
            LockOperationsApi.updateSecureSettingUnlockBetween(
                data = UpdateSecureSettingUnlockBetweenData(
                    baseOperation = BaseOperationData(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                    unlockBetween = UnlockBetweenData(
                        start = "",
                        end = "",
                        timezone = TimeZone.UTC.id,
                        days = emptySet(),
                        exceptions = emptyList()
                    )
                ).toJson(),
                callback = TestCallback
            )
        }.unwrapFailure()

        // Then
        assertContains(
            revokeAccessToLockUsingContextException.exceptionType,
            MissingContextFieldException::class.simpleName!!
        )
        assertContains("User ID is missing", revokeAccessToLockUsingContextException.exceptionMessage)
        assertContains(
            shareLockUsingContextException.exceptionType,
            MissingContextFieldException::class.simpleName!!
        )
        assertContains("User ID is missing", shareLockUsingContextException.exceptionMessage)
        assertNotNull(unlockUsingContextException)
        assertContains(
            unlockUsingContextException.exceptionType,
            MissingContextFieldException::class.simpleName!!
        )
        assertContains("User ID is missing", unlockUsingContextException.exceptionMessage)
        assertContains(
            updateSecureSettingUnlockDurationUsingContextException.exceptionType,
            MissingContextFieldException::class.simpleName!!
        )
        assertContains(
            "User ID is missing",
            updateSecureSettingUnlockDurationUsingContextException.exceptionMessage
        )
        assertContains(
            updateSecureSettingUnlockBetweenUsingContextException.exceptionType,
            MissingContextFieldException::class.simpleName!!
        )
        assertContains(
            "User ID is missing",
            updateSecureSettingUnlockBetweenUsingContextException.exceptionMessage
        )
    }
}