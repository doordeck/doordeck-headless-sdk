package com.doordeck.multiplatform.sdk.api

import com.benasher44.uuid.uuid4
import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.MissingOperationContextException
import com.doordeck.multiplatform.sdk.PlatformType
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_LOCK_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.TestConstants.TEST_SUPPLEMENTARY_USER_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_SUPPLEMENTARY_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.api.model.LockOperations
import com.doordeck.multiplatform.sdk.api.model.UserRole
import com.doordeck.multiplatform.sdk.getPlatform
import com.doordeck.multiplatform.sdk.util.Crypto.certificateChainArrayToString
import com.doordeck.multiplatform.sdk.util.Crypto.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Crypto.stringToCertificateChainArray
import com.ionspin.kotlin.crypto.LibsodiumInitializer
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes

class LockOperationsClientTest : IntegrationTest() {

    init {
        if (getPlatform() != PlatformType.ANDROID) {
            LibsodiumInitializer.initializeWithCallback {  }
        }
    }

    @Test
    fun shouldGetSingleLock() = runTest {
        // Given
        val login = ACCOUNTLESS_CLIENT.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        CONTEXT_MANAGER.setAuthToken(login.authToken)

        // When
        val response = LOCK_OPERATIONS_CLIENT.getSingleLockRequest(TEST_MAIN_LOCK_ID)

        // Then
        assertEquals(TEST_MAIN_LOCK_ID, response.id)
    }

    @Test
    fun shouldUpdateLockName() = runTest {
        // Given
        val login = ACCOUNTLESS_CLIENT.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val updatedLockName = "Demo ${uuid4()} Lock"

        // When
        LOCK_OPERATIONS_CLIENT.updateLockNameRequest(TEST_MAIN_LOCK_ID, updatedLockName)

        // Then
        val lock = LOCK_OPERATIONS_CLIENT.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        assertEquals(updatedLockName, lock.name)
    }

    @Test
    fun shouldUpdateLockFavourite() = runTest {
        // Given
        val login = ACCOUNTLESS_CLIENT.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val updatedFavourite = true

        // When
        LOCK_OPERATIONS_CLIENT.updateLockFavouriteRequest(TEST_MAIN_LOCK_ID, updatedFavourite)

        // Then
        val lock = LOCK_OPERATIONS_CLIENT.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        assertEquals(updatedFavourite, lock.favourite)
    }

    @Test
    fun shouldUpdateLockColour() = runTest {
        // Given
        val login = ACCOUNTLESS_CLIENT.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val updatedLockColour = "#${Random.nextInt(111111, 999999)}"

        // When
        LOCK_OPERATIONS_CLIENT.updateLockColourRequest(TEST_MAIN_LOCK_ID, updatedLockColour)

        // Then
        val lock = LOCK_OPERATIONS_CLIENT.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        assertEquals(updatedLockColour, lock.colour)
    }

    @Test
    fun shouldUpdateLockSettingDefaultName() = runTest {
        // Given
        val login = ACCOUNTLESS_CLIENT.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val updatedLockDefaultName = "Demo ${uuid4()} Lock"

        // When
        LOCK_OPERATIONS_CLIENT.updateLockSettingDefaultNameRequest(TEST_MAIN_LOCK_ID, updatedLockDefaultName)

        // Then
        val lock = LOCK_OPERATIONS_CLIENT.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        assertEquals(updatedLockDefaultName, lock.settings.defaultName)
    }

    @Test
    fun shouldSetAndRemoveLockSettingPermittedAddresses() = runTest {
        // Given - shouldSetLockSettingPermittedAddresses
        val login = ACCOUNTLESS_CLIENT.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val addedLockPermittedAddresses = listOf("95.19.38.42")

        // When
        LOCK_OPERATIONS_CLIENT.setLockSettingPermittedAddressesRequest(TEST_MAIN_LOCK_ID, addedLockPermittedAddresses)

        // Then
        var lock = LOCK_OPERATIONS_CLIENT.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        assertTrue { lock.settings.permittedAddresses.isNotEmpty() }
        assertContains(addedLockPermittedAddresses, lock.settings.permittedAddresses.first())

        // Given - shouldRemoveLockSettingPermittedAddresses
        val removedLockPermittedAddresses = listOf<String>()

        // When
        LOCK_OPERATIONS_CLIENT.setLockSettingPermittedAddressesRequest(TEST_MAIN_LOCK_ID, removedLockPermittedAddresses)

        // Then
        lock = LOCK_OPERATIONS_CLIENT.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        assertTrue { lock.settings.permittedAddresses.isEmpty() }
    }

    @Test
    fun shouldUpdateLockSettingHidden() = runTest {
        // Given
        val login = ACCOUNTLESS_CLIENT.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val updatedHidden = false

        // When
        LOCK_OPERATIONS_CLIENT.updateLockSettingHiddenRequest(TEST_MAIN_LOCK_ID, updatedHidden)

        // Then
        val lock = LOCK_OPERATIONS_CLIENT.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        assertEquals(updatedHidden, lock.settings.hidden)
    }

    @Test
    fun shouldSetAndRemoveLockSettingTimeRestrictions() = runTest {
        // Given - shouldSetLockSettingTimeRestrictions
        val login = ACCOUNTLESS_CLIENT.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val now = Clock.System.now()
        val min = now.minus(1.minutes).toLocalDateTime(TimeZone.UTC)
        val max = now.plus(5.minutes).toLocalDateTime(TimeZone.UTC)
        val addedTimeRestriction = LockOperations.TimeRequirement(
            start = "${min.hour.toString().padStart(2, '0')}:${min.minute.toString().padStart(2, '0')}",
            end = "${max.hour.toString().padStart(2, '0')}:${max.minute.toString().padStart(2, '0')}",
            timezone = TimeZone.UTC.id,
            days = arrayOf(min.dayOfWeek.name)
        )

        // When
        LOCK_OPERATIONS_CLIENT.setLockSettingTimeRestrictionsRequest(TEST_MAIN_LOCK_ID, listOf(addedTimeRestriction))

        // Then
        var lock = LOCK_OPERATIONS_CLIENT.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        val actualTime = lock.settings.usageRequirements?.time?.firstOrNull()
        assertNotNull(actualTime)
        assertEquals(addedTimeRestriction.start, actualTime.start)
        assertEquals(addedTimeRestriction.end, actualTime.end)
        assertEquals(addedTimeRestriction.timezone, actualTime.timezone)
        assertContains(actualTime.days, addedTimeRestriction.days.first())

        // Given - shouldRemoveLockSettingTimeRestrictions
        val removedTimeRestriction = emptyList<LockOperations.TimeRequirement>()

        // When
        LOCK_OPERATIONS_CLIENT.setLockSettingTimeRestrictionsRequest(TEST_MAIN_LOCK_ID, removedTimeRestriction)

        // Then
        lock = LOCK_OPERATIONS_CLIENT.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        assertNull(lock.settings.usageRequirements?.time)
    }

    @Test
    fun shouldUpdateAndRemoveLockSettingLocationRestrictions() = runTest {
        // Given - shouldUpdateLockSettingLocationRestrictions
        val login = ACCOUNTLESS_CLIENT.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val addedLocationRestriction = LockOperations.LocationRequirement(
            latitude = Random.nextDouble(-90.0, 90.0),
            longitude = Random.nextDouble(-180.0, 180.0),
            enabled = true,
            radius = Random.nextInt(1, 100),
            accuracy = Random.nextInt(1, 100)
        )

        // When
        LOCK_OPERATIONS_CLIENT.updateLockSettingLocationRestrictionsRequest(TEST_MAIN_LOCK_ID, addedLocationRestriction)

        // Then
        var lock = LOCK_OPERATIONS_CLIENT.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        assertNotNull(lock.settings.usageRequirements?.location)
        assertEquals(addedLocationRestriction.latitude, lock.settings.usageRequirements?.location?.latitude)
        assertEquals(addedLocationRestriction.longitude, lock.settings.usageRequirements?.location?.longitude)
        assertEquals(addedLocationRestriction.enabled, lock.settings.usageRequirements?.location?.enabled)
        assertEquals(addedLocationRestriction.radius, lock.settings.usageRequirements?.location?.radius)
        assertEquals(addedLocationRestriction.accuracy, lock.settings.usageRequirements?.location?.accuracy)

        // Given - shouldRemoveLockSettingLocationRestrictions
        val removedLocationRestriction = null

        // When
        LOCK_OPERATIONS_CLIENT.updateLockSettingLocationRestrictionsRequest(TEST_MAIN_LOCK_ID, removedLocationRestriction)

        // Then
        lock = LOCK_OPERATIONS_CLIENT.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        assertEquals(removedLocationRestriction, lock.settings.usageRequirements?.location)
    }

    @Test
    fun shouldGetUserPublicKey() = runTest {
        // Given
        val login = ACCOUNTLESS_CLIENT.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        CONTEXT_MANAGER.setAuthToken(login.authToken)

        // When
        val result = LOCK_OPERATIONS_CLIENT.getUserPublicKeyRequest(TEST_MAIN_USER_EMAIL, true)

        // Then
        assertTrue { result.publicKey.isNotEmpty() }
    }

    @Test
    fun shouldGetUserPublicKeyByEmail() = runTest {
        // Given
        val login = ACCOUNTLESS_CLIENT.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        CONTEXT_MANAGER.setAuthToken(login.authToken)

        // When
        val result = LOCK_OPERATIONS_CLIENT.getUserPublicKeyByEmailRequest(TEST_MAIN_USER_EMAIL)

        // Then
        assertTrue { result.publicKey.isNotEmpty() }
    }

    @Test
    fun shouldGetUsersForLock() = runTest {
        // Given
        val login = ACCOUNTLESS_CLIENT.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        CONTEXT_MANAGER.setAuthToken(login.authToken)

        // When
        val usersForLock = LOCK_OPERATIONS_CLIENT.getUsersForLockRequest(TEST_MAIN_LOCK_ID)

        // Then
        assertTrue { usersForLock.isNotEmpty() }
    }

    @Test
    fun shouldGetLockForUser() = runTest {
        // Given
        val login = ACCOUNTLESS_CLIENT.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        CONTEXT_MANAGER.setAuthToken(login.authToken)

        // When
        val locksForUser = LOCK_OPERATIONS_CLIENT.getLocksForUserRequest(TEST_MAIN_USER_ID)

        // Then
        assertTrue { locksForUser.devices.isNotEmpty() }
    }

    @Test
    fun shouldGetPinnedLocks() = runTest {
        // Given
        val login = ACCOUNTLESS_CLIENT.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        CONTEXT_MANAGER.setAuthToken(login.authToken)

        // When
        val pinnedLocks = LOCK_OPERATIONS_CLIENT.getPinnedLocksRequest()

        // Then
        assertTrue { pinnedLocks.isNotEmpty() }
    }

    @Test
    fun shouldGetShareableLocks() = runTest {
        // Given
        val login = ACCOUNTLESS_CLIENT.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        CONTEXT_MANAGER.setAuthToken(login.authToken)

        // When
        val shareableLocks = LOCK_OPERATIONS_CLIENT.getShareableLocksRequest()

        // Then
        assertTrue { shareableLocks.isNotEmpty() }
    }

    @Test
    fun shouldUnlock() = runTest {
        if (getPlatform() == PlatformType.ANDROID) return@runTest
        // Given
        val login = ACCOUNTLESS_CLIENT.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = ACCOUNT_CLIENT.registerEphemeralKeyRequest(TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray())
            .certificateChain
            .certificateChainArrayToString()
        val baseOperation = LockOperations.BaseOperation(
            userId = TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChainArray(),
            userPrivateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
            lockId = TEST_MAIN_LOCK_ID
        )

        // When
        LOCK_OPERATIONS_CLIENT.unlockRequest(LockOperations.UnlockOperation(baseOperation = baseOperation))
    }

    @Test
    fun shouldUnlockWithContext() = runTest {
        if (getPlatform() == PlatformType.ANDROID) return@runTest
        // Given
        val login = ACCOUNTLESS_CLIENT.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = ACCOUNT_CLIENT.registerEphemeralKeyRequest(TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray())
            .certificateChain
            .certificateChainArrayToString()
        CONTEXT_MANAGER.setOperationContext(
            userId = TEST_MAIN_USER_ID,
            certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChainArray(),
            privateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray()
        )

        // When
        LOCK_OPERATIONS_CLIENT.unlockWithContextRequest(TEST_MAIN_LOCK_ID, emptyList())
    }

    @Test
    fun shouldShareAndRevokeLock() = runTest {
        if (getPlatform() == PlatformType.ANDROID) return@runTest
        // Given - shouldShareLock
        val login = ACCOUNTLESS_CLIENT.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = ACCOUNT_CLIENT.registerEphemeralKeyRequest(TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray())
            .certificateChain
            .certificateChainArrayToString()
        val shareBaseOperation = LockOperations.BaseOperation(
            userId = TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChainArray(),
            userPrivateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
            lockId = TEST_MAIN_LOCK_ID
        )

        // When
        LOCK_OPERATIONS_CLIENT.shareLockRequest(LockOperations.ShareLockOperation(
            baseOperation = shareBaseOperation,
            shareLock = LockOperations.ShareLock(
                targetUserId = TEST_SUPPLEMENTARY_USER_ID,
                targetUserRole = UserRole.USER,
                targetUserPublicKey = TEST_SUPPLEMENTARY_USER_PUBLIC_KEY.decodeBase64ToByteArray()
            )
        ))

        // Then
        var locks = LOCK_OPERATIONS_CLIENT.getLocksForUserRequest(TEST_SUPPLEMENTARY_USER_ID)
        assertTrue { locks.devices.any { it.deviceId == TEST_MAIN_LOCK_ID } }

        // Given - shouldRevokeAccessToLock
        val revokeBaseOperation = LockOperations.BaseOperation(
            userId = TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChainArray(),
            userPrivateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
            lockId = TEST_MAIN_LOCK_ID
        )

        // When
        LOCK_OPERATIONS_CLIENT.revokeAccessToLockRequest(LockOperations.RevokeAccessToLockOperation(
            baseOperation = revokeBaseOperation,
            users = arrayOf(TEST_SUPPLEMENTARY_USER_ID)
        ))

        // Then
        locks = LOCK_OPERATIONS_CLIENT.getLocksForUserRequest(TEST_SUPPLEMENTARY_USER_ID)
        assertFalse { locks.devices.any { it.deviceId == TEST_MAIN_LOCK_ID } }
    }

    @Test
    fun shouldShareAndRevokeLockWithContext() = runTest {
        if (getPlatform() == PlatformType.ANDROID) return@runTest
        // Given - shouldShareLockWithContext
        val login = ACCOUNTLESS_CLIENT.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = ACCOUNT_CLIENT.registerEphemeralKeyRequest(TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray())
            .certificateChain
            .certificateChainArrayToString()
        CONTEXT_MANAGER.setOperationContext(
            userId = TEST_MAIN_USER_ID,
            certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChainArray(),
            privateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray()
        )
        val shareLock = LockOperations.ShareLock(
            targetUserId = TEST_SUPPLEMENTARY_USER_ID,
            targetUserRole = UserRole.USER,
            targetUserPublicKey = TEST_SUPPLEMENTARY_USER_PUBLIC_KEY.decodeBase64ToByteArray()
        )

        // When
        LOCK_OPERATIONS_CLIENT.shareLockWithContextRequest(
            lockId = TEST_MAIN_LOCK_ID,
            shareLock = shareLock
        )

        // Then
        var locks = LOCK_OPERATIONS_CLIENT.getLocksForUserRequest(TEST_SUPPLEMENTARY_USER_ID)
        assertTrue { locks.devices.any { it.deviceId == TEST_MAIN_LOCK_ID } }

        // Given - shouldRevokeAccessToLockWithContext
        // When
        LOCK_OPERATIONS_CLIENT.revokeAccessToLockWithContextRequest(
            lockId = TEST_MAIN_LOCK_ID,
            users = listOf(TEST_SUPPLEMENTARY_USER_ID)
        )

        // Then
        locks = LOCK_OPERATIONS_CLIENT.getLocksForUserRequest(TEST_SUPPLEMENTARY_USER_ID)
        assertFalse { locks.devices.any { it.deviceId == TEST_MAIN_LOCK_ID } }
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDuration() = runTest {
        if (getPlatform() == PlatformType.ANDROID) return@runTest
        // Given
        val login = ACCOUNTLESS_CLIENT.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = ACCOUNT_CLIENT.registerEphemeralKeyRequest(TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray())
            .certificateChain
            .certificateChainArrayToString()
        val updatedUnlockDuration = Random.nextInt(30, 60)
        val baseOperation = LockOperations.BaseOperation(
            userId = TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChainArray(),
            userPrivateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
            lockId = TEST_MAIN_LOCK_ID
        )

        // When
        LOCK_OPERATIONS_CLIENT.updateSecureSettingUnlockDurationRequest(LockOperations.UpdateSecureSettingUnlockDuration(
            baseOperation = baseOperation,
            unlockDuration = updatedUnlockDuration
        ))

        // Then
        val lock = LOCK_OPERATIONS_CLIENT.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        assertEquals(updatedUnlockDuration.toDouble(), lock.settings.unlockTime)
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDurationWithContext() = runTest {
        if (getPlatform() == PlatformType.ANDROID) return@runTest
        // Given
        val login = ACCOUNTLESS_CLIENT.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = ACCOUNT_CLIENT.registerEphemeralKeyRequest(TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray())
            .certificateChain
            .certificateChainArrayToString()
        val updatedUnlockDuration = 1
        CONTEXT_MANAGER.setOperationContext(
            userId = TEST_MAIN_USER_ID,
            certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChainArray(),
            privateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray()
        )

        // When
        LOCK_OPERATIONS_CLIENT.updateSecureSettingUnlockDurationWithContextRequest(
            lockId = TEST_MAIN_LOCK_ID,
            unlockDuration = updatedUnlockDuration
        )

        // Then
        val lock = LOCK_OPERATIONS_CLIENT.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        assertEquals(updatedUnlockDuration.toDouble(), lock.settings.unlockTime)
    }

    @Test
    fun shouldUpdateAndRemoveSecureSettingUnlockBetween() = runTest {
        if (getPlatform() == PlatformType.ANDROID) return@runTest
        // Given
        val login = ACCOUNTLESS_CLIENT.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = ACCOUNT_CLIENT.registerEphemeralKeyRequest(TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray())
            .certificateChain
            .certificateChainArrayToString()
        val now = Clock.System.now()
        val min = now.minus(1.minutes).toLocalDateTime(TimeZone.UTC)
        val max = now.plus(5.minutes).toLocalDateTime(TimeZone.UTC)
        val updatedUnlockBetween = LockOperations.UnlockBetween(
            start = "${min.hour.toString().padStart(2, '0')}:${min.minute.toString().padStart(2, '0')}",
            end = "${max.hour.toString().padStart(2, '0')}:${max.minute.toString().padStart(2, '0')}",
            timezone = TimeZone.UTC.id,
            days = arrayOf(min.dayOfWeek.name),
            exceptions = emptyArray()
        )
        val addBaseOperation = LockOperations.BaseOperation(
            userId = TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChainArray(),
            userPrivateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
            lockId = TEST_MAIN_LOCK_ID
        )

        // When
        LOCK_OPERATIONS_CLIENT.updateSecureSettingUnlockBetweenRequest(LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = addBaseOperation,
            unlockBetween = updatedUnlockBetween
        ))

        // Then
        var lock = LOCK_OPERATIONS_CLIENT.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        assertNotNull(lock.settings.unlockBetweenWindow)
        assertEquals(updatedUnlockBetween.start, lock.settings.unlockBetweenWindow?.start)
        assertEquals(updatedUnlockBetween.end, lock.settings.unlockBetweenWindow?.end)
        assertEquals(updatedUnlockBetween.timezone, lock.settings.unlockBetweenWindow?.timezone)
        assertContains(lock.settings.unlockBetweenWindow!!.days, min.dayOfWeek.name)

        // Given - shouldRemoveSecureSettingUnlockBetween
        val removeBaseOperation = LockOperations.BaseOperation(
            userId = TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChainArray(),
            userPrivateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
            lockId = TEST_MAIN_LOCK_ID
        )

        // When
        LOCK_OPERATIONS_CLIENT.updateSecureSettingUnlockBetweenRequest(LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = removeBaseOperation,
            unlockBetween = null
        ))

        // Then
        lock = LOCK_OPERATIONS_CLIENT.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        assertNull(lock.settings.unlockBetweenWindow)
    }

    @Test
    fun shouldUpdateAndRemoveSecureSettingUnlockBetweenWithContext() = runTest {
        if (getPlatform() == PlatformType.ANDROID) return@runTest
        // Given
        val login = ACCOUNTLESS_CLIENT.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = ACCOUNT_CLIENT.registerEphemeralKeyRequest(TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray())
            .certificateChain
            .certificateChainArrayToString()
        val now = Clock.System.now()
        val min = now.minus(5.minutes).toLocalDateTime(TimeZone.UTC)
        val max = now.plus(10.minutes).toLocalDateTime(TimeZone.UTC)
        val updatedUnlockBetween = LockOperations.UnlockBetween(
            start = "${min.hour.toString().padStart(2, '0')}:${min.minute.toString().padStart(2, '0')}",
            end = "${max.hour.toString().padStart(2, '0')}:${max.minute.toString().padStart(2, '0')}",
            timezone = TimeZone.UTC.id,
            days = arrayOf(min.dayOfWeek.name),
            exceptions = emptyArray()
        )
        CONTEXT_MANAGER.setOperationContext(
            userId = TEST_MAIN_USER_ID,
            certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChainArray(),
            privateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray()
        )

        // When
        LOCK_OPERATIONS_CLIENT.updateSecureSettingUnlockBetweenWithContextRequest(
            lockId = TEST_MAIN_LOCK_ID,
            unlockBetween = updatedUnlockBetween
        )

        // Then
        var lock = LOCK_OPERATIONS_CLIENT.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        assertNotNull(lock.settings.unlockBetweenWindow)
        assertEquals(updatedUnlockBetween.start, lock.settings.unlockBetweenWindow?.start)
        assertEquals(updatedUnlockBetween.end, lock.settings.unlockBetweenWindow?.end)
        assertEquals(updatedUnlockBetween.timezone, lock.settings.unlockBetweenWindow?.timezone)
        assertContains(lock.settings.unlockBetweenWindow!!.days, min.dayOfWeek.name)

        // Given
        LOCK_OPERATIONS_CLIENT.updateSecureSettingUnlockBetweenWithContextRequest(
            lockId = TEST_MAIN_LOCK_ID,
            unlockBetween = null
        )

        // Then
        lock = LOCK_OPERATIONS_CLIENT.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        assertNull(lock.settings.unlockBetweenWindow)
    }

    @Test
    fun shouldGetLockAuditTrail() = runTest {
        // Given
        val login = ACCOUNTLESS_CLIENT.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val now = Clock.System.now()
        val start = now.minus(7.days).epochSeconds.toInt()
        val end = now.epochSeconds.toInt()

        // When
        val lockAuditTrail = LOCK_OPERATIONS_CLIENT.getLockAuditTrailRequest(TEST_MAIN_LOCK_ID, start, end)

        // Then
        assertTrue { lockAuditTrail.isNotEmpty() }
    }

    @Test
    fun shouldGetAuditForUser() = runTest {
        // Given
        val login = ACCOUNTLESS_CLIENT.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val now = Clock.System.now()
        val start = now.minus(7.days).epochSeconds.toInt()
        val end = now.epochSeconds.toInt()

        // When
        val auditForUser = LOCK_OPERATIONS_CLIENT.getAuditForUserRequest(TEST_MAIN_USER_ID, start, end)

        // Then
        assertTrue { auditForUser.isNotEmpty() }
    }

    @Test
    fun shouldThrowExceptionWhenOperationContextIsMissing() = runTest {
        // Given
        CONTEXT_MANAGER.resetOperationContext()
        val exceptionMessage = "The operation context is missing"

        // When
        val revokeAccessToLockWithContextException = assertFails {
            LOCK_OPERATIONS_CLIENT.revokeAccessToLockWithContextRequest("", listOf())
        }
        val shareLockWithContextException = assertFails {
            LOCK_OPERATIONS_CLIENT.shareLockWithContextRequest("", LockOperations.ShareLock(
                targetUserId = "",
                targetUserRole = UserRole.USER,
                targetUserPublicKey = byteArrayOf()
            ))
        }
        val unlockWithContextException = assertFails {
            LOCK_OPERATIONS_CLIENT.unlockWithContextRequest("", emptyList())
        }
        val updateSecureSettingUnlockDurationWithContextException = assertFails {
            LOCK_OPERATIONS_CLIENT.updateSecureSettingUnlockDurationWithContextRequest("", 0)
        }
        val updateSecureSettingUnlockBetweenWithContextException = assertFails {
            LOCK_OPERATIONS_CLIENT.updateSecureSettingUnlockBetweenWithContextRequest(
                lockId = TEST_MAIN_LOCK_ID,
                unlockBetween = LockOperations.UnlockBetween(
                    start = "",
                    end = "",
                    timezone = "",
                    days = emptyArray(),
                    exceptions = emptyArray()
                )
            )
        }

        // Then
        assertTrue { revokeAccessToLockWithContextException is MissingOperationContextException }
        assertEquals(exceptionMessage, revokeAccessToLockWithContextException.message)
        assertTrue { shareLockWithContextException is MissingOperationContextException }
        assertEquals(exceptionMessage, shareLockWithContextException.message)
        assertTrue { unlockWithContextException is MissingOperationContextException }
        assertEquals(exceptionMessage, unlockWithContextException.message)
        assertTrue { updateSecureSettingUnlockDurationWithContextException is MissingOperationContextException }
        assertEquals(exceptionMessage, updateSecureSettingUnlockDurationWithContextException.message)
        assertTrue { updateSecureSettingUnlockBetweenWithContextException is MissingOperationContextException }
        assertEquals(exceptionMessage, updateSecureSettingUnlockBetweenWithContextException.message)
    }
}