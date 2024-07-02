package com.doordeck.sdk.api

import com.benasher44.uuid.uuid4
import com.doordeck.sdk.PlatformType
import com.doordeck.sdk.SystemTest
import com.doordeck.sdk.api.model.ApiEnvironment
import com.doordeck.sdk.api.model.LockOperations
import com.doordeck.sdk.api.model.UserRole
import com.doordeck.sdk.api.responses.LockResponse
import com.doordeck.sdk.createHttpClient
import com.doordeck.sdk.getPlatform
import com.doordeck.sdk.internal.api.LockOperationsResourceImpl
import com.doordeck.sdk.runBlocking
import com.doordeck.sdk.util.Crypto.decodeBase64ToByteArray
import com.doordeck.sdk.util.Crypto.stringToCertificateChain
import com.ionspin.kotlin.crypto.LibsodiumInitializer
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes

class LockOperationsResourceTest : SystemTest() {

    private val resource = LockOperationsResourceImpl(createHttpClient(ApiEnvironment.DEV, TEST_AUTH_TOKEN, null))

    @Test
    fun shouldTestLockOperations() = runBlocking {
        if (getPlatform() == PlatformType.ANDROID) {
            return@runBlocking
        }
        LibsodiumInitializer.initialize()

        shouldGetSingleLock()
        shouldUpdateLockName()
        shouldUpdateLockSettingHidden()
        shouldUpdateLockFavourite()
        shouldUpdateLockColour()
        shouldUpdateLockSettingDefaultName()
        shouldUpdateLockSettingPermittedAddresses()
        shouldRemoveLockSettingPermittedAddresses()
        shouldUpdateLockSettingTimeRestrictions()
        shouldRemoveLockSettingTimeRestrictions()
        shouldUpdateLockSettingLocationRestrictions()
        shouldRemoveLockSettingLocationRestrictions()
        shouldGetUserPublicKey()
        shouldGetUserPublicKeyByEmail()
        shouldGetLockAuditTrail()
        shouldGetAuditForUser()
        shouldGetUsersForLock()
        shouldGetLockForUser()
        shouldGetPinnedLocks()
        shouldGetShareableLocks()
        shouldUnlock()
        shouldShareLock()
        shouldRevokeAccessToLock()
        shouldUpdateSecureSettingUnlockDuration()
        shouldRemoveSecureSettingUnlockDuration()
        shouldUploadSecureSettingUnlockBetween()
        shouldRemoveSecureSettingUnlockBetween()
    }

    private fun shouldGetSingleLock(): LockResponse {
        return resource.getSingleLock(TEST_MAIN_LOCK_ID)
    }

    private fun shouldUpdateLockName() {
        // Given
        val updatedLockName = "Demo ${uuid4()} Lock"

        // When
        resource.updateLockName(TEST_MAIN_LOCK_ID, updatedLockName)

        // Then
        val lock = shouldGetSingleLock()
        assertEquals(updatedLockName, lock.name)
    }

    private fun shouldUpdateLockFavourite() {
        // Given
        val updatedFavourite = true

        // When
        resource.updateLockFavourite(TEST_MAIN_LOCK_ID, updatedFavourite)

        // Then
        val lock = shouldGetSingleLock()
        assertEquals(updatedFavourite, lock.favourite)
    }

    private fun shouldUpdateLockColour() {
        // Given
        val updatedLockColour = "#${Random.nextInt(111111, 999999)}"

        // When
        resource.updateLockColour(TEST_MAIN_LOCK_ID, updatedLockColour)

        // Then
        val lock = shouldGetSingleLock()
        assertEquals(updatedLockColour, lock.colour)
    }

    private fun shouldUpdateLockSettingDefaultName() {
        // Given
        val updatedLockDefaultName = "Demo ${uuid4()} Lock"

        // When
        resource.updateLockSettingDefaultName(TEST_MAIN_LOCK_ID, updatedLockDefaultName)

        // Then
        val lock = shouldGetSingleLock()
        assertEquals(updatedLockDefaultName, lock.settings.defaultName)
    }

    private fun shouldUpdateLockSettingPermittedAddresses() {
        // Given
        val updatedLockPermittedAddresses = arrayOf("95.19.38.42")

        // When
        resource.updateLockSettingPermittedAddresses(TEST_MAIN_LOCK_ID, updatedLockPermittedAddresses)

        // Then
        val lock = shouldGetSingleLock()
        assertTrue { lock.settings.permittedAddresses.isNotEmpty() }
        assertContains(updatedLockPermittedAddresses, lock.settings.permittedAddresses.first())
    }

    private fun shouldRemoveLockSettingPermittedAddresses() {
        // Given
        val updatedLockPermittedAddresses = emptyArray<String>()

        // When
        resource.updateLockSettingPermittedAddresses(TEST_MAIN_LOCK_ID, updatedLockPermittedAddresses)

        // Then
        val lock = shouldGetSingleLock()
        assertTrue { lock.settings.permittedAddresses.isEmpty() }
    }

    private fun shouldUpdateLockSettingHidden() {
        // Given
        val updatedHidden = false

        // When
        resource.updateLockSettingHidden(TEST_MAIN_LOCK_ID, updatedHidden)

        // Then
        val lock = shouldGetSingleLock()
        assertEquals(updatedHidden, lock.settings.hidden)
    }

    private fun shouldUpdateLockSettingTimeRestrictions() {
        val timezone = TimeZone.currentSystemDefault()
        val now = Clock.System.now()
        val min = now.minus(1.minutes).toLocalDateTime(timezone)
        val max = now.plus(5.minutes).toLocalDateTime(timezone)
        val updatedTimeRestriction = LockOperations.TimeRequirement(
            start = "${min.hour}:${min.minute}",
            end = "${max.hour}:${max.minute}",
            timezone = timezone.id,
            days = arrayOf(min.dayOfWeek.name)
        )

        // When
        resource.updateLockSettingTimeRestrictions(TEST_MAIN_LOCK_ID, arrayOf(updatedTimeRestriction))

        // Then
        val lock = shouldGetSingleLock()
        val actualTime = lock.settings.usageRequirements?.time?.firstOrNull()
        assertNotNull(actualTime)
        assertEquals(updatedTimeRestriction.start, actualTime.start)
        assertEquals(updatedTimeRestriction.end, actualTime.end)
        assertEquals(updatedTimeRestriction.timezone, actualTime.timezone)
        assertContains(actualTime.days, updatedTimeRestriction.days.first())
    }

    private fun shouldRemoveLockSettingTimeRestrictions() {
        // Given
        val updatedTimeRestriction = emptyArray<LockOperations.TimeRequirement>()

        // When
        resource.updateLockSettingTimeRestrictions(TEST_MAIN_LOCK_ID, updatedTimeRestriction)

        // Then
        val lock = shouldGetSingleLock()
        assertNull(lock.settings.usageRequirements?.time)
    }

    private fun shouldUpdateLockSettingLocationRestrictions() {
        // Given
        val updatedLocationRestriction = LockOperations.LocationRequirement(
            latitude = Random.nextDouble(-90.0, 90.0),
            longitude = Random.nextDouble(-180.0, 180.0),
            enabled = true,
            radius = Random.nextInt(1, 100),
            accuracy = Random.nextInt(1, 100)
        )

        // When
        resource.updateLockSettingLocationRestrictions(TEST_MAIN_LOCK_ID, updatedLocationRestriction)

        // Then
        val lock = shouldGetSingleLock()
        assertNotNull(lock.settings.usageRequirements?.location)
        assertEquals(updatedLocationRestriction.latitude, lock.settings.usageRequirements?.location?.latitude)
        assertEquals(updatedLocationRestriction.longitude, lock.settings.usageRequirements?.location?.longitude)
        assertEquals(updatedLocationRestriction.enabled, lock.settings.usageRequirements?.location?.enabled)
        assertEquals(updatedLocationRestriction.radius, lock.settings.usageRequirements?.location?.radius)
        assertEquals(updatedLocationRestriction.accuracy, lock.settings.usageRequirements?.location?.accuracy)
    }

    private fun shouldRemoveLockSettingLocationRestrictions() {
        // Given
        val updatedLocationRestriction = null

        // When
        resource.updateLockSettingLocationRestrictions(TEST_MAIN_LOCK_ID, updatedLocationRestriction)

        // Then
        val lock = shouldGetSingleLock()
        assertEquals(updatedLocationRestriction, lock.settings.usageRequirements?.location)
    }

    private fun shouldGetUserPublicKey() {
        resource.getUserPublicKey(TEST_MAIN_USER_EMAIL, true)
    }

    private fun shouldGetUserPublicKeyByEmail() {
        resource.getUserPublicKeyByEmail(TEST_MAIN_USER_EMAIL)
    }

    private fun shouldGetLockAuditTrail() {
        // Given
        val now = Clock.System.now()
        val start = now.minus(7.days).epochSeconds.toInt()
        val end = now.epochSeconds.toInt()

        // When
        val lockAuditTrail = resource.getLockAuditTrail(TEST_MAIN_LOCK_ID, start, end)

        // Then
        assertTrue { lockAuditTrail.isNotEmpty() }
    }

    private fun shouldGetAuditForUser() {
        // Given
        val now = Clock.System.now()
        val start = now.minus(7.days).epochSeconds.toInt()
        val end = now.epochSeconds.toInt()

        // When
        val auditForUser = resource.getAuditForUser(TEST_MAIN_USER_ID, start, end)

        // Then
        assertTrue { auditForUser.isNotEmpty() }
    }

    private fun shouldGetUsersForLock() {
        // When
        val usersForLock = resource.getUsersForLock(TEST_MAIN_LOCK_ID)

        // Then
        assertTrue { usersForLock.isNotEmpty() }
    }

    private fun shouldGetLockForUser() {
        // When
        val locksForUser = resource.getLocksForUser(TEST_MAIN_USER_ID)

        // Then
        assertTrue { locksForUser.devices.isNotEmpty() }
    }

    private fun shouldGetPinnedLocks() {
        // When
        val pinnedLocks = resource.getPinnedLocks()

        // Then
        assertTrue { pinnedLocks.isNotEmpty() }
    }

    private fun shouldGetShareableLocks() {
        // When
        val shareableLocks = resource.getShareableLocks()

        // Then
        assertTrue { shareableLocks.isNotEmpty() }
    }

    private fun shouldUnlock() {
        // Given
        val baseOperation = LockOperations.BaseOperation(
            userId = TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChain(),
            userPrivateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
            lockId = TEST_MAIN_LOCK_ID
        )

        // When
        resource.unlock(LockOperations.UnlockOperation(baseOperation = baseOperation))

        // Then
        // TODO Verify if its locked ?
    }

    private fun shouldShareLock() {
        // Given
        val baseOperation = LockOperations.BaseOperation(
            userId = TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChain(),
            userPrivateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
            lockId = TEST_MAIN_LOCK_ID
        )

        // When
        resource.shareLock(LockOperations.ShareLockOperation(
            baseOperation = baseOperation,
            targetUserId = TEST_SUPPLEMENTARY_USER_ID,
            targetUserRole = UserRole.USER,
            targetUserPublicKey = TEST_SUPPLEMENTARY_USER_PUBLIC_KEY.decodeBase64ToByteArray()
        ))

        // Then
        val locks = resource.getLocksForUser(TEST_SUPPLEMENTARY_USER_ID)
        assertTrue { locks.devices.isNotEmpty() }
    }

    private fun shouldRevokeAccessToLock() {
        // Given
        val baseOperation = LockOperations.BaseOperation(
            userId = TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChain(),
            userPrivateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
            lockId = TEST_MAIN_LOCK_ID
        )

        // When
        resource.revokeAccessToLock(LockOperations.RevokeAccessToLockOperation(
            baseOperation = baseOperation,
            users = arrayOf(TEST_SUPPLEMENTARY_USER_ID)
        ))

        // Then
        val locks = resource.getLocksForUser(TEST_SUPPLEMENTARY_USER_ID)
        assertTrue { locks.devices.isEmpty() }
    }

    private fun shouldUpdateSecureSettingUnlockDuration() {
        // Given
        val updatedUnlockDuration = Random.nextInt(30, 60)
        val baseOperation = LockOperations.BaseOperation(
            userId = TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChain(),
            userPrivateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
            lockId = TEST_MAIN_LOCK_ID
        )

        // When
        resource.updateSecureSettingUnlockDuration(LockOperations.UpdateSecureSettingUnlockDuration(
            baseOperation = baseOperation,
            unlockDuration = updatedUnlockDuration
        ))

        // Then
        val lock = shouldGetSingleLock()
        assertEquals(updatedUnlockDuration.toDouble(), lock.settings.unlockTime)
    }

    private fun shouldRemoveSecureSettingUnlockDuration() {
        // Given
        val updatedUnlockDuration = 1
        val baseOperation = LockOperations.BaseOperation(
            userId = TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChain(),
            userPrivateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
            lockId = TEST_MAIN_LOCK_ID
        )

        // When
        resource.updateSecureSettingUnlockDuration(LockOperations.UpdateSecureSettingUnlockDuration(
            baseOperation = baseOperation,
            unlockDuration = updatedUnlockDuration
        ))

        // Then
        val lock = shouldGetSingleLock()
        assertEquals(updatedUnlockDuration.toDouble(), lock.settings.unlockTime)
    }

    private fun shouldUploadSecureSettingUnlockBetween() {
        // Given
        val timezone = TimeZone.currentSystemDefault()
        val now = Clock.System.now()
        val min = now.minus(1.minutes).toLocalDateTime(timezone)
        val max = now.plus(5.minutes).toLocalDateTime(timezone)
        val updatedUnlockBetween = LockOperations.UnlockBetween(
            start = "${min.hour}:${min.minute}",
            end = "${max.hour}:${max.minute}",
            timezone = timezone.id,
            days = arrayOf(min.dayOfWeek.name),
            exceptions = emptyArray()
        )
        val baseOperation = LockOperations.BaseOperation(
            userId = TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChain(),
            userPrivateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
            lockId = TEST_MAIN_LOCK_ID
        )

        // When
        resource.uploadSecureSettingUnlockBetween(LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = baseOperation,
            unlockBetween = updatedUnlockBetween
        ))

        // Then
        val lock = shouldGetSingleLock()
        assertNotNull(lock.settings.unlockBetweenWindow)
        assertEquals(updatedUnlockBetween.start, lock.settings.unlockBetweenWindow?.start)
        assertEquals(updatedUnlockBetween.end, lock.settings.unlockBetweenWindow?.end)
        assertEquals(updatedUnlockBetween.timezone, lock.settings.unlockBetweenWindow?.timezone)
        assertContains(lock.settings.unlockBetweenWindow!!.days, min.dayOfWeek.name)
    }

    private fun shouldRemoveSecureSettingUnlockBetween() {
        // Given
        val baseOperation = LockOperations.BaseOperation(
            userId = TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChain(),
            userPrivateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
            lockId = TEST_MAIN_LOCK_ID
        )

        // When
        resource.uploadSecureSettingUnlockBetween(LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = baseOperation,
            unlockBetween = null
        ))

        // Then
        val lock = shouldGetSingleLock()
        assertNull(lock.settings.unlockBetweenWindow)
    }
}