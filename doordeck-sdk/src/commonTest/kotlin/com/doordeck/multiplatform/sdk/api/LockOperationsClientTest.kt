package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.MissingContextFieldException
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_LOCK_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PASSWORD
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.TestConstants.TEST_SUPPLEMENTARY_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_SUPPLEMENTARY_USER_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_SUPPLEMENTARY_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.api.model.LockOperations
import com.doordeck.multiplatform.sdk.api.model.UserRole
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountClient
import com.doordeck.multiplatform.sdk.internal.api.AccountlessClient
import com.doordeck.multiplatform.sdk.internal.api.LockOperationsClient
import com.doordeck.multiplatform.sdk.util.Utils.certificateChainToString
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Utils.stringToCertificateChain
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
import kotlin.uuid.Uuid

class LockOperationsClientTest : IntegrationTest() {

    @Test
    fun shouldGetSingleLock() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        val response = LockOperationsClient.getSingleLockRequest(TEST_MAIN_LOCK_ID)

        // Then
        assertEquals(TEST_MAIN_LOCK_ID, response.id)
    }

    @Test
    fun shouldUpdateLockName() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val updatedLockName = "Demo ${Uuid.random()} Lock"

        // When
        LockOperationsClient.updateLockNameRequest(TEST_MAIN_LOCK_ID, updatedLockName)

        // Then
        val lock = LockOperationsClient.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        assertEquals(updatedLockName, lock.name)
    }

    @Test
    fun shouldUpdateLockFavourite() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val updatedFavourite = true

        // When
        LockOperationsClient.updateLockFavouriteRequest(TEST_MAIN_LOCK_ID, updatedFavourite)

        // Then
        val lock = LockOperationsClient.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        assertEquals(updatedFavourite, lock.favourite)
    }

    @Test
    fun shouldUpdateLockColour() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val updatedLockColour = "#${Random.nextInt(111111, 999999)}"

        // When
        LockOperationsClient.updateLockColourRequest(TEST_MAIN_LOCK_ID, updatedLockColour)

        // Then
        val lock = LockOperationsClient.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        assertEquals(updatedLockColour, lock.colour)
    }

    @Test
    fun shouldUpdateLockSettingDefaultName() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val updatedLockDefaultName = "Demo ${Uuid.random()} Lock"

        // When
        LockOperationsClient.updateLockSettingDefaultNameRequest(TEST_MAIN_LOCK_ID, updatedLockDefaultName)

        // Then
        val lock = LockOperationsClient.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        assertEquals(updatedLockDefaultName, lock.settings.defaultName)
    }

    @Test
    fun shouldSetAndRemoveLockSettingPermittedAddresses() = runTest {
        // Given - shouldSetLockSettingPermittedAddresses
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val addedLockPermittedAddresses = listOf("95.19.38.42")

        // When
        LockOperationsClient.setLockSettingPermittedAddressesRequest(TEST_MAIN_LOCK_ID, addedLockPermittedAddresses)

        // Then
        var lock = LockOperationsClient.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        assertTrue { lock.settings.permittedAddresses.isNotEmpty() }
        assertContains(addedLockPermittedAddresses, lock.settings.permittedAddresses.first())

        // Given - shouldRemoveLockSettingPermittedAddresses
        val removedLockPermittedAddresses = listOf<String>()

        // When
        LockOperationsClient.setLockSettingPermittedAddressesRequest(TEST_MAIN_LOCK_ID, removedLockPermittedAddresses)

        // Then
        lock = LockOperationsClient.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        assertTrue { lock.settings.permittedAddresses.isEmpty() }
    }

    @Test
    fun shouldUpdateLockSettingHidden() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val updatedHidden = false

        // When
        LockOperationsClient.updateLockSettingHiddenRequest(TEST_MAIN_LOCK_ID, updatedHidden)

        // Then
        val lock = LockOperationsClient.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        assertEquals(updatedHidden, lock.settings.hidden)
    }

    @Test
    fun shouldSetAndRemoveLockSettingTimeRestrictions() = runTest {
        // Given - shouldSetLockSettingTimeRestrictions
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val now = Clock.System.now()
        val min = now.minus(1.minutes).toLocalDateTime(TimeZone.UTC)
        val max = now.plus(5.minutes).toLocalDateTime(TimeZone.UTC)
        val addedTimeRestriction = LockOperations.TimeRequirement(
            start = "${min.hour.toString().padStart(2, '0')}:${min.minute.toString().padStart(2, '0')}",
            end = "${max.hour.toString().padStart(2, '0')}:${max.minute.toString().padStart(2, '0')}",
            timezone = TimeZone.UTC.id,
            days = listOf(min.dayOfWeek.name)
        )

        // When
        LockOperationsClient.setLockSettingTimeRestrictionsRequest(TEST_MAIN_LOCK_ID, listOf(addedTimeRestriction))

        // Then
        var lock = LockOperationsClient.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        val actualTime = lock.settings.usageRequirements?.time?.firstOrNull()
        assertNotNull(actualTime)
        assertEquals(addedTimeRestriction.start, actualTime.start)
        assertEquals(addedTimeRestriction.end, actualTime.end)
        assertEquals(addedTimeRestriction.timezone, actualTime.timezone)
        assertContains(actualTime.days, addedTimeRestriction.days.first())

        // Given - shouldRemoveLockSettingTimeRestrictions
        val removedTimeRestriction = emptyList<LockOperations.TimeRequirement>()

        // When
        LockOperationsClient.setLockSettingTimeRestrictionsRequest(TEST_MAIN_LOCK_ID, removedTimeRestriction)

        // Then
        lock = LockOperationsClient.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        assertNull(lock.settings.usageRequirements?.time)
    }

    @Test
    fun shouldUpdateAndRemoveLockSettingLocationRestrictions() = runTest {
        // Given - shouldUpdateLockSettingLocationRestrictions
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val addedLocationRestriction = LockOperations.LocationRequirement(
            latitude = Random.nextDouble(-90.0, 90.0),
            longitude = Random.nextDouble(-180.0, 180.0),
            enabled = true,
            radius = Random.nextInt(1, 100),
            accuracy = Random.nextInt(1, 100)
        )

        // When
        LockOperationsClient.updateLockSettingLocationRestrictionsRequest(TEST_MAIN_LOCK_ID, addedLocationRestriction)

        // Then
        var lock = LockOperationsClient.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        assertNotNull(lock.settings.usageRequirements?.location)
        assertEquals(addedLocationRestriction.latitude, lock.settings.usageRequirements?.location?.latitude)
        assertEquals(addedLocationRestriction.longitude, lock.settings.usageRequirements?.location?.longitude)
        assertEquals(addedLocationRestriction.enabled, lock.settings.usageRequirements?.location?.enabled)
        assertEquals(addedLocationRestriction.radius, lock.settings.usageRequirements?.location?.radius)
        assertEquals(addedLocationRestriction.accuracy, lock.settings.usageRequirements?.location?.accuracy)

        // Given - shouldRemoveLockSettingLocationRestrictions
        val removedLocationRestriction = null

        // When
        LockOperationsClient.updateLockSettingLocationRestrictionsRequest(TEST_MAIN_LOCK_ID, removedLocationRestriction)

        // Then
        lock = LockOperationsClient.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        assertEquals(removedLocationRestriction, lock.settings.usageRequirements?.location)
    }

    @Test
    fun shouldGetUserPublicKey() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        val result = LockOperationsClient.getUserPublicKeyRequest(TEST_MAIN_USER_EMAIL, true)

        // Then
        assertTrue { result.publicKey.isNotEmpty() }
    }

    @Test
    fun shouldGetUserPublicKeyByEmail() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        val result = LockOperationsClient.getUserPublicKeyByEmailRequest(TEST_MAIN_USER_EMAIL)

        // Then
        assertTrue { result.publicKey.isNotEmpty() }
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKey() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        val result = LockOperationsClient.getUserPublicKeyByLocalKeyRequest(TEST_MAIN_USER_ID)

        // Then
        assertTrue { result.publicKey.isNotEmpty() }
    }

    @Test
    fun shouldGetUserPublicKeyByEmails() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        val result = LockOperationsClient.getUserPublicKeyByEmailsRequest(listOf(TEST_MAIN_USER_EMAIL, TEST_SUPPLEMENTARY_USER_EMAIL))

        // Then
        assertTrue { result.isNotEmpty() }
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeys() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        val result = LockOperationsClient.getUserPublicKeyByLocalKeysRequest(listOf(TEST_MAIN_USER_ID, TEST_SUPPLEMENTARY_USER_ID))

        // Then
        assertTrue { result.isNotEmpty() }
    }

    @Test
    fun shouldGetUsersForLock() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        val usersForLock = LockOperationsClient.getUsersForLockRequest(TEST_MAIN_LOCK_ID)

        // Then
        assertTrue { usersForLock.isNotEmpty() }
    }

    @Test
    fun shouldGetLockForUser() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        val locksForUser = LockOperationsClient.getLocksForUserRequest(TEST_MAIN_USER_ID)

        // Then
        assertTrue { locksForUser.devices.isNotEmpty() }
    }

    @Test
    fun shouldGetPinnedLocks() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        val pinnedLocks = LockOperationsClient.getPinnedLocksRequest()

        // Then
        assertTrue { pinnedLocks.isNotEmpty() }
    }

    @Test
    fun shouldGetShareableLocks() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        val shareableLocks = LockOperationsClient.getShareableLocksRequest()

        // Then
        assertTrue { shareableLocks.isNotEmpty() }
    }

    @Test
    fun shouldUnlock() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountClient.registerEphemeralKeyRequest(TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray())
            .certificateChain
            .certificateChainToString()
        val baseOperation = LockOperations.BaseOperation(
            userId = TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChain(),
            userPrivateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
            lockId = TEST_MAIN_LOCK_ID
        )

        // When
        LockOperationsClient.unlockRequest(LockOperations.UnlockOperation(baseOperation = baseOperation))
    }

    @Test
    fun shouldUnlockUsingContext() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountClient.registerEphemeralKeyRequest(TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray())
            .certificateChain
            .certificateChainToString()
        ContextManagerImpl.setOperationContext(
            userId = TEST_MAIN_USER_ID,
            certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChain(),
            publicKey = TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray(),
            privateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray()
        )

        // When
        LockOperationsClient.unlockRequest(LockOperations.UnlockOperation(baseOperation = LockOperations.BaseOperation(lockId = TEST_MAIN_LOCK_ID)))
    }

    @Test
    fun shouldShareAndRevokeLock() = runTest {
        // Given - shouldShareLock
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountClient.registerEphemeralKeyRequest(TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray())
            .certificateChain
            .certificateChainToString()
        val shareBaseOperation = LockOperations.BaseOperation(
            userId = TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChain(),
            userPrivateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
            lockId = TEST_MAIN_LOCK_ID
        )

        // When
        LockOperationsClient.shareLockRequest(LockOperations.ShareLockOperation(
            baseOperation = shareBaseOperation,
            shareLock = LockOperations.ShareLock(
                targetUserId = TEST_SUPPLEMENTARY_USER_ID,
                targetUserRole = UserRole.USER,
                targetUserPublicKey = TEST_SUPPLEMENTARY_USER_PUBLIC_KEY.decodeBase64ToByteArray()
            )
        ))

        // Then
        var locks = LockOperationsClient.getLocksForUserRequest(TEST_SUPPLEMENTARY_USER_ID)
        assertTrue { locks.devices.any { it.deviceId == TEST_MAIN_LOCK_ID } }

        // Given - shouldRevokeAccessToLock
        val revokeBaseOperation = LockOperations.BaseOperation(
            userId = TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChain(),
            userPrivateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
            lockId = TEST_MAIN_LOCK_ID
        )

        // When
        LockOperationsClient.revokeAccessToLockRequest(LockOperations.RevokeAccessToLockOperation(
            baseOperation = revokeBaseOperation,
            users = listOf(TEST_SUPPLEMENTARY_USER_ID)
        ))

        // Then
        locks = LockOperationsClient.getLocksForUserRequest(TEST_SUPPLEMENTARY_USER_ID)
        assertFalse { locks.devices.any { it.deviceId == TEST_MAIN_LOCK_ID } }
    }

    @Test
    fun shouldShareAndRevokeLockUsingContext() = runTest {
        // Given - shouldShareLockUsingContext
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountClient.registerEphemeralKeyRequest(TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray())
            .certificateChain
            .certificateChainToString()
        ContextManagerImpl.setOperationContext(
            userId = TEST_MAIN_USER_ID,
            certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChain(),
            publicKey = TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray(),
            privateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray()
        )
        val shareLock = LockOperations.ShareLock(
            targetUserId = TEST_SUPPLEMENTARY_USER_ID,
            targetUserRole = UserRole.USER,
            targetUserPublicKey = TEST_SUPPLEMENTARY_USER_PUBLIC_KEY.decodeBase64ToByteArray()
        )

        // When
        LockOperationsClient.shareLockRequest(LockOperations.ShareLockOperation(
            baseOperation = LockOperations.BaseOperation(lockId = TEST_MAIN_LOCK_ID),
            shareLock = shareLock
        ))

        // Then
        var locks = LockOperationsClient.getLocksForUserRequest(TEST_SUPPLEMENTARY_USER_ID)
        assertTrue { locks.devices.any { it.deviceId == TEST_MAIN_LOCK_ID } }

        // Given - shouldRevokeAccessToLockUsingContext
        // When
        LockOperationsClient.revokeAccessToLockRequest(LockOperations.RevokeAccessToLockOperation(
            baseOperation = LockOperations.BaseOperation(lockId = TEST_MAIN_LOCK_ID),
            users = listOf(TEST_SUPPLEMENTARY_USER_ID)
        ))

        // Then
        locks = LockOperationsClient.getLocksForUserRequest(TEST_SUPPLEMENTARY_USER_ID)
        assertFalse { locks.devices.any { it.deviceId == TEST_MAIN_LOCK_ID } }
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDuration() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountClient.registerEphemeralKeyRequest(TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray())
            .certificateChain
            .certificateChainToString()
        val updatedUnlockDuration = Random.nextInt(30, 60)
        val baseOperation = LockOperations.BaseOperation(
            userId = TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChain(),
            userPrivateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
            lockId = TEST_MAIN_LOCK_ID
        )

        // When
        LockOperationsClient.updateSecureSettingUnlockDurationRequest(LockOperations.UpdateSecureSettingUnlockDuration(
            baseOperation = baseOperation,
            unlockDuration = updatedUnlockDuration
        ))

        // Then
        val lock = LockOperationsClient.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        assertEquals(updatedUnlockDuration.toDouble(), lock.settings.unlockTime)
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDurationUsingContext() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountClient.registerEphemeralKeyRequest(TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray())
            .certificateChain
            .certificateChainToString()
        val updatedUnlockDuration = 1
        ContextManagerImpl.setOperationContext(
            userId = TEST_MAIN_USER_ID,
            certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChain(),
            publicKey = TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray(),
            privateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray()
        )

        // When
        LockOperationsClient.updateSecureSettingUnlockDurationRequest(
            LockOperations.UpdateSecureSettingUnlockDuration(
                baseOperation = LockOperations.BaseOperation(lockId = TEST_MAIN_LOCK_ID),
                unlockDuration = updatedUnlockDuration
            )
        )

        // Then
        val lock = LockOperationsClient.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        assertEquals(updatedUnlockDuration.toDouble(), lock.settings.unlockTime)
    }

    @Test
    fun shouldUpdateAndRemoveSecureSettingUnlockBetween() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountClient.registerEphemeralKeyRequest(TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray())
            .certificateChain
            .certificateChainToString()
        val now = Clock.System.now()
        val min = now.minus(1.minutes).toLocalDateTime(TimeZone.UTC)
        val max = now.plus(5.minutes).toLocalDateTime(TimeZone.UTC)
        val updatedUnlockBetween = LockOperations.UnlockBetween(
            start = "${min.hour.toString().padStart(2, '0')}:${min.minute.toString().padStart(2, '0')}",
            end = "${max.hour.toString().padStart(2, '0')}:${max.minute.toString().padStart(2, '0')}",
            timezone = TimeZone.UTC.id,
            days = listOf(min.dayOfWeek.name),
            exceptions = emptyList()
        )
        val addBaseOperation = LockOperations.BaseOperation(
            userId = TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChain(),
            userPrivateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
            lockId = TEST_MAIN_LOCK_ID
        )

        // When
        LockOperationsClient.updateSecureSettingUnlockBetweenRequest(LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = addBaseOperation,
            unlockBetween = updatedUnlockBetween
        ))

        // Then
        var lock = LockOperationsClient.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        assertNotNull(lock.settings.unlockBetweenWindow)
        assertEquals(updatedUnlockBetween.start, lock.settings.unlockBetweenWindow?.start)
        assertEquals(updatedUnlockBetween.end, lock.settings.unlockBetweenWindow?.end)
        assertEquals(updatedUnlockBetween.timezone, lock.settings.unlockBetweenWindow?.timezone)
        assertContains(lock.settings.unlockBetweenWindow!!.days, min.dayOfWeek.name)

        // Given - shouldRemoveSecureSettingUnlockBetween
        val removeBaseOperation = LockOperations.BaseOperation(
            userId = TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChain(),
            userPrivateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
            lockId = TEST_MAIN_LOCK_ID
        )

        // When
        LockOperationsClient.updateSecureSettingUnlockBetweenRequest(LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = removeBaseOperation,
            unlockBetween = null
        ))

        // Then
        lock = LockOperationsClient.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        assertNull(lock.settings.unlockBetweenWindow)
    }

    @Test
    fun shouldUpdateAndRemoveSecureSettingUnlockBetweenUsingContext() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountClient.registerEphemeralKeyRequest(TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray())
            .certificateChain
            .certificateChainToString()
        val now = Clock.System.now()
        val min = now.minus(5.minutes).toLocalDateTime(TimeZone.UTC)
        val max = now.plus(10.minutes).toLocalDateTime(TimeZone.UTC)
        val updatedUnlockBetween = LockOperations.UnlockBetween(
            start = "${min.hour.toString().padStart(2, '0')}:${min.minute.toString().padStart(2, '0')}",
            end = "${max.hour.toString().padStart(2, '0')}:${max.minute.toString().padStart(2, '0')}",
            timezone = TimeZone.UTC.id,
            days = listOf(min.dayOfWeek.name),
            exceptions = emptyList()
        )
        ContextManagerImpl.setOperationContext(
            userId = TEST_MAIN_USER_ID,
            certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChain(),
            publicKey = TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray(),
            privateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray()
        )

        // When
        LockOperationsClient.updateSecureSettingUnlockBetweenRequest(LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = LockOperations.BaseOperation(lockId = TEST_MAIN_LOCK_ID),
            unlockBetween = updatedUnlockBetween
        ))

        // Then
        var lock = LockOperationsClient.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        assertNotNull(lock.settings.unlockBetweenWindow)
        assertEquals(updatedUnlockBetween.start, lock.settings.unlockBetweenWindow?.start)
        assertEquals(updatedUnlockBetween.end, lock.settings.unlockBetweenWindow?.end)
        assertEquals(updatedUnlockBetween.timezone, lock.settings.unlockBetweenWindow?.timezone)
        assertContains(lock.settings.unlockBetweenWindow!!.days, min.dayOfWeek.name)

        // Given
        LockOperationsClient.updateSecureSettingUnlockBetweenRequest(LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = LockOperations.BaseOperation(lockId = TEST_MAIN_LOCK_ID),
            unlockBetween = null
        ))

        // Then
        lock = LockOperationsClient.getSingleLockRequest(TEST_MAIN_LOCK_ID)
        assertNull(lock.settings.unlockBetweenWindow)
    }

    @Test
    fun shouldGetLockAuditTrail() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val now = Clock.System.now()
        val start = now.minus(14.days).epochSeconds.toInt()
        val end = now.epochSeconds.toInt()

        // When
        val lockAuditTrail = LockOperationsClient.getLockAuditTrailRequest(TEST_MAIN_LOCK_ID, start, end)

        // Then
        assertTrue { lockAuditTrail.isNotEmpty() }
    }

    @Test
    fun shouldGetAuditForUser() = runTest {
        // Given
        AccountlessClient.loginRequest(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val now = Clock.System.now()
        val start = now.minus(14.days).epochSeconds.toInt()
        val end = now.epochSeconds.toInt()

        // When
        val auditForUser = LockOperationsClient.getAuditForUserRequest(TEST_MAIN_USER_ID, start, end)

        // Then
        assertTrue { auditForUser.isNotEmpty() }
    }

    @Test
    fun shouldThrowExceptionWhenOperationContextIsMissing() = runTest {
        // Given
        ContextManagerImpl.resetOperationContext()

        // When
        val revokeAccessToLockUsingContextException = assertFails {
            LockOperationsClient.revokeAccessToLockRequest(LockOperations.RevokeAccessToLockOperation(LockOperations.BaseOperation(lockId = TEST_MAIN_LOCK_ID), emptyList()))
        }
        val shareLockUsingContextException = assertFails {
            LockOperationsClient.shareLockRequest(LockOperations.ShareLockOperation(
                baseOperation = LockOperations.BaseOperation(lockId = TEST_MAIN_LOCK_ID),
                shareLock = LockOperations.ShareLock(
                    targetUserId = "",
                    targetUserRole = UserRole.USER,
                    targetUserPublicKey = byteArrayOf()
                )
            ))
        }
        val unlockUsingContextException = assertFails {
            LockOperationsClient.unlockRequest(LockOperations.UnlockOperation(LockOperations.BaseOperation(lockId = TEST_MAIN_LOCK_ID), emptyList()))
        }
        val updateSecureSettingUnlockDurationUsingContextException = assertFails {
            LockOperationsClient.updateSecureSettingUnlockDurationRequest(LockOperations.UpdateSecureSettingUnlockDuration(
                baseOperation = LockOperations.BaseOperation(lockId = TEST_MAIN_LOCK_ID),
                unlockDuration = 0
            ))
        }
        val updateSecureSettingUnlockBetweenUsingContextException = assertFails {
            LockOperationsClient.updateSecureSettingUnlockBetweenRequest(
                updateSecureSettingUnlockBetween = LockOperations.UpdateSecureSettingUnlockBetween(
                    baseOperation = LockOperations.BaseOperation(lockId = TEST_MAIN_LOCK_ID),
                    unlockBetween = LockOperations.UnlockBetween(
                        start = "",
                        end = "",
                        timezone = "",
                        days = emptyList(),
                        exceptions = emptyList()
                    )
                )
            )
        }

        // Then
        assertTrue { revokeAccessToLockUsingContextException is MissingContextFieldException }
        assertEquals("User ID is missing", revokeAccessToLockUsingContextException.message)
        assertTrue { shareLockUsingContextException is MissingContextFieldException }
        assertEquals("User ID is missing", shareLockUsingContextException.message)
        assertTrue { unlockUsingContextException is MissingContextFieldException }
        assertEquals("User ID is missing", unlockUsingContextException.message)
        assertTrue { updateSecureSettingUnlockDurationUsingContextException is MissingContextFieldException }
        assertEquals("User ID is missing", updateSecureSettingUnlockDurationUsingContextException.message)
        assertTrue { updateSecureSettingUnlockBetweenUsingContextException is MissingContextFieldException }
        assertEquals("User ID is missing", updateSecureSettingUnlockBetweenUsingContextException.message)
    }
}