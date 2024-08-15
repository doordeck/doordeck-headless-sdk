package com.doordeck.multiplatform.sdk.api

import com.benasher44.uuid.uuid4
import com.doordeck.multiplatform.sdk.MissingOperationContextException
import com.doordeck.multiplatform.sdk.PlatformType
import com.doordeck.multiplatform.sdk.SystemTest
import com.doordeck.multiplatform.sdk.api.model.LockOperations
import com.doordeck.multiplatform.sdk.api.model.UserRole
import com.doordeck.multiplatform.sdk.api.responses.LockResponse
import com.doordeck.multiplatform.sdk.getPlatform
import com.doordeck.multiplatform.sdk.util.Crypto.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Crypto.stringToCertificateChain
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
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes

class LockOperationsResourceTest : SystemTest() {

    @Test
    fun shouldTestLockOperations() = runTest {
        if (getPlatform() == PlatformType.ANDROID) {
            return@runTest
        }
        LibsodiumInitializer.initialize()

        shouldGetSingleLock()
        shouldUpdateLockName()
        shouldUpdateLockSettingHidden()
        shouldUpdateLockFavourite()
        shouldUpdateLockColour()
        shouldUpdateLockSettingDefaultName()
        shouldSetLockSettingPermittedAddresses()
        shouldRemoveLockSettingPermittedAddresses()
        shouldSetLockSettingTimeRestrictions()
        shouldRemoveLockSettingTimeRestrictions()
        shouldUpdateLockSettingLocationRestrictions()
        shouldRemoveLockSettingLocationRestrictions()
        shouldGetUserPublicKey()
        shouldGetUserPublicKeyByEmail()
        shouldGetUsersForLock()
        shouldGetLockForUser()
        shouldGetPinnedLocks()
        shouldGetShareableLocks()
        shouldUnlock()
        shouldUnlockWithContext()
        shouldShareLock()
        shouldRevokeAccessToLock()
        shouldShareLockWithContext()
        shouldRevokeAccessToLockWithContext()
        shouldUpdateSecureSettingUnlockDuration()
        shouldUpdateSecureSettingUnlockDurationWithContext()
        shouldUploadSecureSettingUnlockBetween()
        shouldUploadSecureSettingUnlockBetweenWithContext()
        shouldRemoveSecureSettingUnlockBetween()
        shouldGetLockAuditTrail()
        shouldGetAuditForUser()
        shouldThrowExceptionWhenOperationContextIsMissing()
    }

    private fun shouldGetSingleLock(): LockResponse {
        return LOCK_OPERATIONS_RESOURCE.getSingleLock(TEST_MAIN_LOCK_ID)
    }

    private fun shouldUpdateLockName() {
        // Given
        val updatedLockName = "Demo ${uuid4()} Lock"

        // When
        LOCK_OPERATIONS_RESOURCE.updateLockName(TEST_MAIN_LOCK_ID, updatedLockName)

        // Then
        val lock = shouldGetSingleLock()
        assertEquals(updatedLockName, lock.name)
    }

    private fun shouldUpdateLockFavourite() {
        // Given
        val updatedFavourite = true

        // When
        LOCK_OPERATIONS_RESOURCE.updateLockFavourite(TEST_MAIN_LOCK_ID, updatedFavourite)

        // Then
        val lock = shouldGetSingleLock()
        assertEquals(updatedFavourite, lock.favourite)
    }

    private fun shouldUpdateLockColour() {
        // Given
        val updatedLockColour = "#${Random.nextInt(111111, 999999)}"

        // When
        LOCK_OPERATIONS_RESOURCE.updateLockColour(TEST_MAIN_LOCK_ID, updatedLockColour)

        // Then
        val lock = shouldGetSingleLock()
        assertEquals(updatedLockColour, lock.colour)
    }

    private fun shouldUpdateLockSettingDefaultName() {
        // Given
        val updatedLockDefaultName = "Demo ${uuid4()} Lock"

        // When
        LOCK_OPERATIONS_RESOURCE.updateLockSettingDefaultName(TEST_MAIN_LOCK_ID, updatedLockDefaultName)

        // Then
        val lock = shouldGetSingleLock()
        assertEquals(updatedLockDefaultName, lock.settings.defaultName)
    }

    private fun shouldSetLockSettingPermittedAddresses() {
        // Given
        val updatedLockPermittedAddresses = arrayOf("95.19.38.42")

        // When
        LOCK_OPERATIONS_RESOURCE.setLockSettingPermittedAddresses(TEST_MAIN_LOCK_ID, updatedLockPermittedAddresses)

        // Then
        val lock = shouldGetSingleLock()
        assertTrue { lock.settings.permittedAddresses.isNotEmpty() }
        assertContains(updatedLockPermittedAddresses, lock.settings.permittedAddresses.first())
    }

    private fun shouldRemoveLockSettingPermittedAddresses() {
        // Given
        val updatedLockPermittedAddresses = emptyArray<String>()

        // When
        LOCK_OPERATIONS_RESOURCE.setLockSettingPermittedAddresses(TEST_MAIN_LOCK_ID, updatedLockPermittedAddresses)

        // Then
        val lock = shouldGetSingleLock()
        assertTrue { lock.settings.permittedAddresses.isEmpty() }
    }

    private fun shouldUpdateLockSettingHidden() {
        // Given
        val updatedHidden = false

        // When
        LOCK_OPERATIONS_RESOURCE.updateLockSettingHidden(TEST_MAIN_LOCK_ID, updatedHidden)

        // Then
        val lock = shouldGetSingleLock()
        assertEquals(updatedHidden, lock.settings.hidden)
    }

    private fun shouldSetLockSettingTimeRestrictions() {
        val timezone = TimeZone.currentSystemDefault()
        val now = Clock.System.now()
        val min = now.minus(1.minutes).toLocalDateTime(timezone)
        val max = now.plus(5.minutes).toLocalDateTime(timezone)
        val updatedTimeRestriction = LockOperations.TimeRequirement(
            start = "${min.hour.toString().padStart(2, '0')}:${min.minute.toString().padStart(2, '0')}",
            end = "${max.hour.toString().padStart(2, '0')}:${max.minute.toString().padStart(2, '0')}",
            timezone = timezone.id,
            days = arrayOf(min.dayOfWeek.name)
        )

        // When
        LOCK_OPERATIONS_RESOURCE.setLockSettingTimeRestrictions(TEST_MAIN_LOCK_ID, arrayOf(updatedTimeRestriction))

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
        LOCK_OPERATIONS_RESOURCE.setLockSettingTimeRestrictions(TEST_MAIN_LOCK_ID, updatedTimeRestriction)

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
        LOCK_OPERATIONS_RESOURCE.updateLockSettingLocationRestrictions(TEST_MAIN_LOCK_ID, updatedLocationRestriction)

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
        LOCK_OPERATIONS_RESOURCE.updateLockSettingLocationRestrictions(TEST_MAIN_LOCK_ID, updatedLocationRestriction)

        // Then
        val lock = shouldGetSingleLock()
        assertEquals(updatedLocationRestriction, lock.settings.usageRequirements?.location)
    }

    private fun shouldGetUserPublicKey() {
        LOCK_OPERATIONS_RESOURCE.getUserPublicKey(TEST_MAIN_USER_EMAIL, true)
    }

    private fun shouldGetUserPublicKeyByEmail() {
        LOCK_OPERATIONS_RESOURCE.getUserPublicKeyByEmail(TEST_MAIN_USER_EMAIL)
    }

    private fun shouldGetUsersForLock() {
        // When
        val usersForLock = LOCK_OPERATIONS_RESOURCE.getUsersForLock(TEST_MAIN_LOCK_ID)

        // Then
        assertTrue { usersForLock.isNotEmpty() }
    }

    private fun shouldGetLockForUser() {
        // When
        val locksForUser = LOCK_OPERATIONS_RESOURCE.getLocksForUser(TEST_MAIN_USER_ID)

        // Then
        assertTrue { locksForUser.devices.isNotEmpty() }
    }

    private fun shouldGetPinnedLocks() {
        // When
        val pinnedLocks = LOCK_OPERATIONS_RESOURCE.getPinnedLocks()

        // Then
        assertTrue { pinnedLocks.isNotEmpty() }
    }

    private fun shouldGetShareableLocks() {
        // When
        val shareableLocks = LOCK_OPERATIONS_RESOURCE.getShareableLocks()

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
        LOCK_OPERATIONS_RESOURCE.unlock(LockOperations.UnlockOperation(baseOperation = baseOperation))
    }

    private fun shouldUnlockWithContext() {
        // Given
        CONTEXT_MANAGER.setOperationContext(
            userId = TEST_MAIN_USER_ID,
            certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChain(),
            privateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray()
        )

        // When
        LOCK_OPERATIONS_RESOURCE.unlockWithContext(TEST_MAIN_LOCK_ID)
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
        LOCK_OPERATIONS_RESOURCE.shareLock(LockOperations.ShareLockOperation(
            baseOperation = baseOperation,
            shareLock = LockOperations.ShareLock(
                targetUserId = TEST_SUPPLEMENTARY_USER_ID,
                targetUserRole = UserRole.USER,
                targetUserPublicKey = TEST_SUPPLEMENTARY_USER_PUBLIC_KEY.decodeBase64ToByteArray()
            )
        ))

        // Then
        val locks = LOCK_OPERATIONS_RESOURCE.getLocksForUser(TEST_SUPPLEMENTARY_USER_ID)
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
        LOCK_OPERATIONS_RESOURCE.revokeAccessToLock(LockOperations.RevokeAccessToLockOperation(
            baseOperation = baseOperation,
            users = arrayOf(TEST_SUPPLEMENTARY_USER_ID)
        ))

        // Then
        val locks = LOCK_OPERATIONS_RESOURCE.getLocksForUser(TEST_SUPPLEMENTARY_USER_ID)
        assertTrue { locks.devices.isEmpty() }
    }

    private fun shouldShareLockWithContext() {
        // Given
        CONTEXT_MANAGER.setOperationContext(
            userId = TEST_MAIN_USER_ID,
            certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChain(),
            privateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray()
        )
        val shareLock = LockOperations.ShareLock(
            targetUserId = TEST_SUPPLEMENTARY_USER_ID,
            targetUserRole = UserRole.USER,
            targetUserPublicKey = TEST_SUPPLEMENTARY_USER_PUBLIC_KEY.decodeBase64ToByteArray()
        )

        // When
        LOCK_OPERATIONS_RESOURCE.shareLockWithContext(
            lockId = TEST_MAIN_LOCK_ID,
            shareLock = shareLock
        )

        // Then
        val locks = LOCK_OPERATIONS_RESOURCE.getLocksForUser(TEST_SUPPLEMENTARY_USER_ID)
        assertTrue { locks.devices.isNotEmpty() }
    }

    private fun shouldRevokeAccessToLockWithContext() {
        // Given
        CONTEXT_MANAGER.setOperationContext(
            userId = TEST_MAIN_USER_ID,
            certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChain(),
            privateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray()
        )

        // When
        LOCK_OPERATIONS_RESOURCE.revokeAccessToLockWithContext(
            lockId = TEST_MAIN_LOCK_ID,
            users = arrayOf(TEST_SUPPLEMENTARY_USER_ID)
        )

        // Then
        val locks = LOCK_OPERATIONS_RESOURCE.getLocksForUser(TEST_SUPPLEMENTARY_USER_ID)
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
        LOCK_OPERATIONS_RESOURCE.updateSecureSettingUnlockDuration(LockOperations.UpdateSecureSettingUnlockDuration(
            baseOperation = baseOperation,
            unlockDuration = updatedUnlockDuration
        ))

        // Then
        val lock = shouldGetSingleLock()
        assertEquals(updatedUnlockDuration.toDouble(), lock.settings.unlockTime)
    }

    private fun shouldUpdateSecureSettingUnlockDurationWithContext() {
        // Given
        val updatedUnlockDuration = 1
        CONTEXT_MANAGER.setOperationContext(
            userId = TEST_MAIN_USER_ID,
            certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChain(),
            privateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray()
        )

        // When
        LOCK_OPERATIONS_RESOURCE.updateSecureSettingUnlockDurationWithContext(
            lockId = TEST_MAIN_LOCK_ID,
            unlockDuration = updatedUnlockDuration
        )

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
            start = "${min.hour.toString().padStart(2, '0')}:${min.minute.toString().padStart(2, '0')}",
            end = "${max.hour.toString().padStart(2, '0')}:${max.minute.toString().padStart(2, '0')}",
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
        LOCK_OPERATIONS_RESOURCE.uploadSecureSettingUnlockBetween(LockOperations.UpdateSecureSettingUnlockBetween(
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

    private fun shouldUploadSecureSettingUnlockBetweenWithContext() {
        // Given
        val timezone = TimeZone.currentSystemDefault()
        val now = Clock.System.now()
        val min = now.minus(5.minutes).toLocalDateTime(timezone)
        val max = now.plus(10.minutes).toLocalDateTime(timezone)
        val updatedUnlockBetween = LockOperations.UnlockBetween(
            start = "${min.hour.toString().padStart(2, '0')}:${min.minute.toString().padStart(2, '0')}",
            end = "${max.hour.toString().padStart(2, '0')}:${max.minute.toString().padStart(2, '0')}",
            timezone = timezone.id,
            days = arrayOf(min.dayOfWeek.name),
            exceptions = emptyArray()
        )
        CONTEXT_MANAGER.setOperationContext(
            userId = TEST_MAIN_USER_ID,
            certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChain(),
            privateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray()
        )

        // When
        LOCK_OPERATIONS_RESOURCE.uploadSecureSettingUnlockBetweenWithContext(
            lockId = TEST_MAIN_LOCK_ID,
            unlockBetween = updatedUnlockBetween
        )

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
        LOCK_OPERATIONS_RESOURCE.uploadSecureSettingUnlockBetween(LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = baseOperation,
            unlockBetween = null
        ))

        // Then
        val lock = shouldGetSingleLock()
        assertNull(lock.settings.unlockBetweenWindow)
    }

    private fun shouldGetLockAuditTrail() {
        // Given
        val now = Clock.System.now()
        val start = now.minus(7.days).epochSeconds.toInt()
        val end = now.epochSeconds.toInt()

        // When
        val lockAuditTrail = LOCK_OPERATIONS_RESOURCE.getLockAuditTrail(TEST_MAIN_LOCK_ID, start, end)

        // Then
        assertTrue { lockAuditTrail.isNotEmpty() }
    }

    private fun shouldGetAuditForUser() {
        // Given
        val now = Clock.System.now()
        val start = now.minus(7.days).epochSeconds.toInt()
        val end = now.epochSeconds.toInt()

        // When
        val auditForUser = LOCK_OPERATIONS_RESOURCE.getAuditForUser(TEST_MAIN_USER_ID, start, end)

        // Then
        assertTrue { auditForUser.isNotEmpty() }
    }

    private fun shouldThrowExceptionWhenOperationContextIsMissing() {
        // Given
        CONTEXT_MANAGER.resetOperationContext()
        val exceptionMessage = "The operation context is missing"

        // When
        val revokeAccessToLockWithContextException = assertFails {
            LOCK_OPERATIONS_RESOURCE.revokeAccessToLockWithContext("", emptyArray())
        }
        val shareLockWithContextException = assertFails {
            LOCK_OPERATIONS_RESOURCE.shareLockWithContext("", LockOperations.ShareLock(
                targetUserId = "",
                targetUserRole = UserRole.USER,
                targetUserPublicKey = byteArrayOf()
            ))
        }
        val unlockWithContextException = assertFails {
            LOCK_OPERATIONS_RESOURCE.unlockWithContext("")
        }
        val updateSecureSettingUnlockDurationWithContextException = assertFails {
            LOCK_OPERATIONS_RESOURCE.updateSecureSettingUnlockDurationWithContext("", 0)
        }
        val uploadSecureSettingUnlockBetweenWithContextException = assertFails {
            LOCK_OPERATIONS_RESOURCE.uploadSecureSettingUnlockBetweenWithContext(
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
        assertTrue { uploadSecureSettingUnlockBetweenWithContextException is MissingOperationContextException }
        assertEquals(exceptionMessage, uploadSecureSettingUnlockBetweenWithContextException.message)
    }
}