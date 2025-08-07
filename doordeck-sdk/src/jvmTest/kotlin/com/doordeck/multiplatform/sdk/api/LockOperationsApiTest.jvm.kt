package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.IntegrationTest
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
import com.doordeck.multiplatform.sdk.model.common.UserRole
import com.doordeck.multiplatform.sdk.model.data.LockOperations
import com.doordeck.multiplatform.sdk.randomDouble
import com.doordeck.multiplatform.sdk.randomInt
import com.doordeck.multiplatform.sdk.randomUuid
import com.doordeck.multiplatform.sdk.randomUuidString
import com.doordeck.multiplatform.sdk.util.toInetAddress
import com.doordeck.multiplatform.sdk.util.toLocalTime
import com.doordeck.multiplatform.sdk.util.toLocalTimeString
import com.doordeck.multiplatform.sdk.util.toZoneId
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.net.InetAddress
import java.time.DayOfWeek
import java.util.EnumSet
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes

class LockOperationsApiTest : IntegrationTest() {

    @Test
    fun shouldGetSingleLock() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        val response = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID)

        // Then
        assertEquals(PLATFORM_TEST_MAIN_LOCK_ID, response.id)
    }

    @Test
    fun shouldUpdateLockName() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val updatedLockName = "Doordeck Fusion Test Site - ${randomUuidString()}"

        // When
        LockOperationsApi.updateLockName(PLATFORM_TEST_MAIN_LOCK_ID, updatedLockName)

        // Then
        val lock = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID)
        assertEquals(updatedLockName, lock.name)
    }

    @Test
    fun shouldUpdateLockFavourite() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val updatedFavourite = true

        // When
        LockOperationsApi.updateLockFavourite(PLATFORM_TEST_MAIN_LOCK_ID, updatedFavourite)

        // Then
        val lock = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID)
        assertEquals(updatedFavourite, lock.favourite)
    }

    @Test
    fun shouldUpdateLockSettingDefaultName() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val updatedLockDefaultName = "Doordeck Fusion Test Site - ${randomUuidString()}"

        // When
        LockOperationsApi.updateLockSettingDefaultName(PLATFORM_TEST_MAIN_LOCK_ID, updatedLockDefaultName)

        // Then
        val lock = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID)
        assertEquals(updatedLockDefaultName, lock.settings.defaultName)
    }

    @Test
    fun shouldSetAndRemoveLockSettingPermittedAddresses() = runTest {
        // Given - shouldSetLockSettingPermittedAddresses
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val addedLockPermittedAddresses = listOf("95.19.38.42".toInetAddress())

        // When
        LockOperationsApi.setLockSettingPermittedAddresses(PLATFORM_TEST_MAIN_LOCK_ID, addedLockPermittedAddresses)

        // Then
        var lock = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID)
        assertTrue { lock.settings.permittedAddresses.isNotEmpty() }
        assertContains(addedLockPermittedAddresses, lock.settings.permittedAddresses.first())

        // Given - shouldRemoveLockSettingPermittedAddresses
        val removedLockPermittedAddresses = listOf<InetAddress>()

        // When
        LockOperationsApi.setLockSettingPermittedAddresses(PLATFORM_TEST_MAIN_LOCK_ID, removedLockPermittedAddresses)

        // Then
        lock = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID)
        assertTrue { lock.settings.permittedAddresses.isEmpty() }
    }

    @Test
    fun shouldUpdateLockSettingHidden() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val updatedHidden = false

        // When
        LockOperationsApi.updateLockSettingHidden(PLATFORM_TEST_MAIN_LOCK_ID, updatedHidden)

        // Then
        val lock = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID)
        assertEquals(updatedHidden, lock.settings.hidden)
    }

    @Test
    fun shouldSetAndRemoveLockSettingTimeRestrictions() = runTest {
        // Given - shouldSetLockSettingTimeRestrictions
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val now = Clock.System.now()
        val addedTimeRestriction = LockOperations.TimeRequirement(
            start = (now - 1.minutes).toLocalDateTime(TimeZone.UTC).time,
            end = (now + 5.minutes).toLocalDateTime(TimeZone.UTC).time,
            timezone = TimeZone.UTC.id.toZoneId(),
            days = EnumSet.of(DayOfWeek.entries.random())
        )

        // When
        LockOperationsApi.setLockSettingTimeRestrictions(PLATFORM_TEST_MAIN_LOCK_ID, listOf(addedTimeRestriction))

        // Then
        var lock = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID)
        val actualTime = lock.settings.usageRequirements?.time?.firstOrNull()
        assertNotNull(actualTime)
        assertEquals(addedTimeRestriction.start.toLocalTimeString().toLocalTime(), actualTime.start)
        assertEquals(addedTimeRestriction.end.toLocalTimeString().toLocalTime(), actualTime.end)
        assertEquals(addedTimeRestriction.timezone, actualTime.timezone)
        assertContains(actualTime.days, addedTimeRestriction.days.first())

        // Given - shouldRemoveLockSettingTimeRestrictions
        val removedTimeRestriction = emptyList<LockOperations.TimeRequirement>()

        // When
        LockOperationsApi.setLockSettingTimeRestrictions(PLATFORM_TEST_MAIN_LOCK_ID, removedTimeRestriction)

        // Then
        lock = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID)
        assertEquals(0, lock.settings.usageRequirements?.time?.size)
    }

    @Test
    fun shouldUpdateAndRemoveLockSettingLocationRestrictions() = runTest {
        // Given - shouldUpdateLockSettingLocationRestrictions
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val addedLocationRestriction = LockOperations.LocationRequirement(
            latitude = randomDouble(-90.0, 90.0),
            longitude = randomDouble(-180.0, 180.0),
            enabled = true,
            radius = randomInt(1, 100),
            accuracy = randomInt(1, 100)
        )

        // When
        LockOperationsApi.updateLockSettingLocationRestrictions(
            lockId = PLATFORM_TEST_MAIN_LOCK_ID,
            location = addedLocationRestriction
        )

        // Then
        var lock = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID)
        assertNotNull(lock.settings.usageRequirements?.location)
        assertEquals(addedLocationRestriction.latitude, lock.settings.usageRequirements.location.latitude)
        assertEquals(addedLocationRestriction.longitude, lock.settings.usageRequirements.location.longitude)
        assertEquals(addedLocationRestriction.enabled, lock.settings.usageRequirements.location.enabled)
        assertEquals(addedLocationRestriction.radius, lock.settings.usageRequirements.location.radius)
        assertEquals(addedLocationRestriction.accuracy, lock.settings.usageRequirements.location.accuracy)

        // Given - shouldRemoveLockSettingLocationRestrictions
        val removedLocationRestriction = null

        // When
        LockOperationsApi.updateLockSettingLocationRestrictions(PLATFORM_TEST_MAIN_LOCK_ID, removedLocationRestriction)

        // Then
        lock = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID)
        assertEquals(removedLocationRestriction, lock.settings.usageRequirements?.location)
    }

    @Test
    fun shouldGetUserPublicKey() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        val result = LockOperationsApi.getUserPublicKey(TEST_MAIN_USER_EMAIL, true)

        // Then
        assertEquals(PLATFORM_TEST_MAIN_USER_ID, result.id)
    }

    @Test
    fun shouldGetUserPublicKeyByEmail() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        val result = LockOperationsApi.getUserPublicKeyByEmail(TEST_MAIN_USER_EMAIL)

        // Then
        assertEquals(PLATFORM_TEST_MAIN_USER_ID, result.id)
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKey() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        val result = LockOperationsApi.getUserPublicKeyByLocalKey(TEST_MAIN_USER_ID)

        // Then
        assertEquals(PLATFORM_TEST_MAIN_USER_ID, result.id)
    }

    @Test
    fun shouldGetUserPublicKeyByEmails() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        val result = LockOperationsApi.getUserPublicKeyByEmails(listOf(TEST_MAIN_USER_EMAIL, TEST_SUPPLEMENTARY_USER_EMAIL))

        // Then
        assertTrue { result.isNotEmpty() }
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeys() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        val result = LockOperationsApi.getUserPublicKeyByLocalKeys(listOf(TEST_MAIN_USER_ID, TEST_SUPPLEMENTARY_USER_ID))

        // Then
        assertTrue { result.isNotEmpty() }
    }

    @Test
    fun shouldGetUsersForLock() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        val usersForLock = LockOperationsApi.getUsersForLock(PLATFORM_TEST_MAIN_LOCK_ID)

        // Then
        assertTrue { usersForLock.isNotEmpty() }
        assertTrue { usersForLock.any { it.userId == PLATFORM_TEST_MAIN_USER_ID } }
    }

    @Test
    fun shouldGetLockForUser() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        val locksForUser = LockOperationsApi.getLocksForUser(PLATFORM_TEST_MAIN_USER_ID)

        // Then
        assertTrue { locksForUser.devices.isNotEmpty() }
        assertTrue { locksForUser.devices.any { it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID } }
    }

    @Test
    fun shouldGetPinnedLocks() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        val pinnedLocks = LockOperationsApi.getPinnedLocks()

        // Then
        assertTrue { pinnedLocks.isNotEmpty() }
    }

    @Test
    fun shouldGetShareableLocks() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)

        // When
        val shareableLocks = LockOperationsApi.getShareableLocks()

        // Then
        assertTrue { shareableLocks.isNotEmpty() }
        assertTrue { shareableLocks.any { it.id == PLATFORM_TEST_MAIN_LOCK_ID } }
    }

    @Test
    fun shouldUnlock() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountApi.registerEphemeralKey(
            publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
            privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
        ).certificateChain
        val baseOperation = LockOperations.BaseOperation(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            userPrivateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
            lockId = PLATFORM_TEST_MAIN_LOCK_ID
        )

        // When
        LockOperationsApi.unlock(LockOperations.UnlockOperation(baseOperation = baseOperation))
    }

    @Test
    fun shouldUnlockUsingContext() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountApi.registerEphemeralKey(
            publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
            privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
        ).certificateChain
        ContextManager.setOperationContext(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
            privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
            isKeyPairVerified = true
        )

        // When
        LockOperationsApi.unlock(
            unlockOperation = LockOperations.UnlockOperation(
                baseOperation = LockOperations.BaseOperation(lockId = PLATFORM_TEST_MAIN_LOCK_ID)
            )
        )
    }

    @Test
    fun shouldShareAndRevokeLock() = runTest {
        // Given - shouldShareLock
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountApi.registerEphemeralKey(
            publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
            privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
        ).certificateChain
        val shareBaseOperation = LockOperations.BaseOperation(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            userPrivateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
            lockId = PLATFORM_TEST_MAIN_LOCK_ID
        )

        // When
        LockOperationsApi.shareLock(
            shareLockOperation = LockOperations.ShareLockOperation(
                baseOperation = shareBaseOperation,
                shareLock = LockOperations.ShareLock(
                    targetUserId = PLATFORM_TEST_SUPPLEMENTARY_USER_ID,
                    targetUserRole = UserRole.USER,
                    targetUserPublicKey = PLATFORM_TEST_SUPPLEMENTARY_USER_PUBLIC_KEY
                )
            ))

        // Then
        var locks = LockOperationsApi.getLocksForUser(PLATFORM_TEST_SUPPLEMENTARY_USER_ID)
        assertTrue { locks.devices.any { it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID } }

        // Given - shouldRevokeAccessToLock
        val revokeBaseOperation = LockOperations.BaseOperation(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            userPrivateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
            lockId = PLATFORM_TEST_MAIN_LOCK_ID
        )

        // When
        LockOperationsApi.revokeAccessToLock(
            LockOperations.RevokeAccessToLockOperation(
                baseOperation = revokeBaseOperation,
                users = listOf(PLATFORM_TEST_SUPPLEMENTARY_USER_ID)
            ))

        // Then
        locks = LockOperationsApi.getLocksForUser(PLATFORM_TEST_SUPPLEMENTARY_USER_ID)
        assertFalse { locks.devices.any { it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID } }
    }

    @Test
    fun shouldBatchShareAndRevokeLock() = runTest {
        // Given - shouldShareLockUsingContext
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountApi.registerEphemeralKey(
            publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
            privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
        ).certificateChain
        val shareBaseOperation = LockOperations.BaseOperation(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            userPrivateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
            lockId = PLATFORM_TEST_MAIN_LOCK_ID
        )
        val batchShareLock = listOf(
            LockOperations.ShareLock(
                targetUserId = PLATFORM_TEST_SUPPLEMENTARY_USER_ID,
                targetUserRole = UserRole.USER,
                targetUserPublicKey = PLATFORM_TEST_SUPPLEMENTARY_USER_PUBLIC_KEY
            ),
            LockOperations.ShareLock(
                targetUserId = PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID,
                targetUserRole = UserRole.USER,
                targetUserPublicKey = PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_PUBLIC_KEY
            )
        )

        // When
        LockOperationsApi.batchShareLock(
            batchShareLockOperation = LockOperations.BatchShareLockOperation(
                baseOperation = shareBaseOperation,
                users = batchShareLock
            )
        )

        // Then
        assertTrue {
            LockOperationsApi.getLocksForUser(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID
            }
        }
        assertTrue {
            LockOperationsApi.getLocksForUser(PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID).devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID
            }
        }

        // When
        // Given - shouldRevokeAccessToLock
        val revokeBaseOperation = LockOperations.BaseOperation(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            userPrivateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
            lockId = PLATFORM_TEST_MAIN_LOCK_ID
        )
        LockOperationsApi.revokeAccessToLock(
            revokeAccessToLockOperation = LockOperations.RevokeAccessToLockOperation(
                baseOperation = revokeBaseOperation,
                users = listOf(PLATFORM_TEST_SUPPLEMENTARY_USER_ID, PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID)
            )
        )

        // Then
        assertFalse {
            LockOperationsApi.getLocksForUser(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID }
        }
        assertFalse {
            LockOperationsApi.getLocksForUser(PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID).devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID }
        }
    }

    @Test
    fun shouldShareAndRevokeLockUsingContext() = runTest {
        // Given - shouldShareLockUsingContext
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountApi.registerEphemeralKey(
            publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
            privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
        ).certificateChain
        ContextManager.setOperationContext(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
            privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
            isKeyPairVerified = true
        )
        val shareLock = LockOperations.ShareLock(
            targetUserId = PLATFORM_TEST_SUPPLEMENTARY_USER_ID,
            targetUserRole = UserRole.USER,
            targetUserPublicKey = PLATFORM_TEST_SUPPLEMENTARY_USER_PUBLIC_KEY
        )

        // When
        LockOperationsApi.shareLock(
            shareLockOperation = LockOperations.ShareLockOperation(
                baseOperation = LockOperations.BaseOperation(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                shareLock = shareLock
            )
        )

        // Then
        var locks = LockOperationsApi.getLocksForUser(PLATFORM_TEST_SUPPLEMENTARY_USER_ID)
        assertTrue { locks.devices.any { it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID } }

        // Given - shouldRevokeAccessToLockUsingContext
        // When
        LockOperationsApi.revokeAccessToLock(
            revokeAccessToLockOperation = LockOperations.RevokeAccessToLockOperation(
                baseOperation = LockOperations.BaseOperation(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                users = listOf(PLATFORM_TEST_SUPPLEMENTARY_USER_ID)
            )
        )

        // Then
        locks = LockOperationsApi.getLocksForUser(PLATFORM_TEST_SUPPLEMENTARY_USER_ID)
        assertFalse {
            locks.devices.any { it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID }
        }
    }

    @Test
    fun shouldBatchShareAndRevokeLockUsingContext() = runTest {
        // Given - shouldShareLockUsingContext
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountApi.registerEphemeralKey(
            publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
            privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
        ).certificateChain
        ContextManager.setOperationContext(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
            privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
            isKeyPairVerified = true
        )
        val batchShareLock = listOf(
            LockOperations.ShareLock(
                targetUserId = PLATFORM_TEST_SUPPLEMENTARY_USER_ID,
                targetUserRole = UserRole.USER,
                targetUserPublicKey = PLATFORM_TEST_SUPPLEMENTARY_USER_PUBLIC_KEY
            ),
            LockOperations.ShareLock(
                targetUserId = PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID,
                targetUserRole = UserRole.USER,
                targetUserPublicKey = PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_PUBLIC_KEY
            )
        )

        // When
        LockOperationsApi.batchShareLock(
            batchShareLockOperation = LockOperations.BatchShareLockOperation(
                baseOperation = LockOperations.BaseOperation(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                users = batchShareLock
            )
        )

        // Then
        assertTrue {
            LockOperationsApi.getLocksForUser(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID
            }
        }
        assertTrue {
            LockOperationsApi.getLocksForUser(PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID).devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID
            }
        }

        // Given - shouldRevokeAccessToLockUsingContext
        // When
        LockOperationsApi.revokeAccessToLock(
            revokeAccessToLockOperation = LockOperations.RevokeAccessToLockOperation(
                baseOperation = LockOperations.BaseOperation(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                users = listOf(PLATFORM_TEST_SUPPLEMENTARY_USER_ID, PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID)
            )
        )

        // Then
        assertFalse {
            LockOperationsApi.getLocksForUser(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID }
        }
        assertFalse {
            LockOperationsApi.getLocksForUser(PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID).devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID }
        }
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDuration() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountApi.registerEphemeralKey(
            publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
            privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
        ).certificateChain
        val updatedUnlockDuration = Duration.parse("${randomInt(1, 10)}s")
        val baseOperation = LockOperations.BaseOperation(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            userPrivateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
            lockId = PLATFORM_TEST_MAIN_LOCK_ID
        )

        // When
        LockOperationsApi.updateSecureSettingUnlockDuration(
            updateSecureSettingUnlockDuration = LockOperations.UpdateSecureSettingUnlockDuration(
                baseOperation = baseOperation,
                unlockDuration = updatedUnlockDuration
            )
        )

        // Then
        val lock = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID)
        assertEquals(updatedUnlockDuration, lock.settings.unlockTime)
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDurationUsingContext() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountApi.registerEphemeralKey(
            publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
            privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
        ).certificateChain
        val updatedUnlockDuration = Duration.parse("1s")
        ContextManager.setOperationContext(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
            privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
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
        assertEquals(updatedUnlockDuration, lock.settings.unlockTime)
    }

    @Test
    fun shouldUpdateAndRemoveSecureSettingUnlockBetween() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountApi.registerEphemeralKey(
            publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
            privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
        ).certificateChain
        val now = Clock.System.now()
        val updatedUnlockBetween = LockOperations.UnlockBetween(
            start = (now - 1.minutes).toLocalDateTime(TimeZone.UTC).time,
            end = (now + 5.minutes).toLocalDateTime(TimeZone.UTC).time,
            timezone = TimeZone.UTC.id.toZoneId(),
            days = EnumSet.of(DayOfWeek.entries.random()),
            exceptions = emptyList()
        )
        val addBaseOperation = LockOperations.BaseOperation(
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
        assertEquals(updatedUnlockBetween.start.toLocalTimeString().toLocalTime(), lock.settings.unlockBetweenWindow.start)
        assertEquals(updatedUnlockBetween.end.toLocalTimeString().toLocalTime(), lock.settings.unlockBetweenWindow.end)
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
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountApi.registerEphemeralKey(
            publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
            privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
        ).certificateChain
        val now = Clock.System.now()
        val min = now.minus(5.minutes)
        val max = now.plus(10.minutes)
        val updatedUnlockBetween = LockOperations.UnlockBetween(
            start = min.toLocalDateTime(TimeZone.UTC).time,
            end = max.toLocalDateTime(TimeZone.UTC).time,
            timezone = TimeZone.UTC.id.toZoneId(),
            days = EnumSet.of(DayOfWeek.entries.random()),
            exceptions = emptyList()
        )
        ContextManager.setOperationContext(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
            privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
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
        assertEquals(updatedUnlockBetween.start.toLocalTimeString().toLocalTime(), lock.settings.unlockBetweenWindow.start)
        assertEquals(updatedUnlockBetween.end.toLocalTimeString().toLocalTime(), lock.settings.unlockBetweenWindow.end)
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
    }

    @Test
    fun shouldGetLockAuditTrail() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val now = Clock.System.now()
        val start = now.minus(14.days)
        val end = now

        // When
        val lockAuditTrail = LockOperationsApi.getLockAuditTrail(PLATFORM_TEST_MAIN_LOCK_ID, start, end)

        // Then
        assertTrue { lockAuditTrail.isNotEmpty() }
    }

    @Test
    fun shouldGetAuditForUser() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val now = Clock.System.now()
        val start = now.minus(14.days)
        val end = now

        // When
        val auditForUser = LockOperationsApi.getAuditForUser(PLATFORM_TEST_MAIN_USER_ID, start, end)

        // Then
        assertTrue { auditForUser.isNotEmpty() }
    }

    @Test
    fun shouldThrowExceptionWhenOperationContextIsMissing() = runTest {
        // When
        val revokeAccessToLockUsingContextException = assertFails {
            LockOperationsApi.revokeAccessToLock(
                revokeAccessToLockOperation = LockOperations.RevokeAccessToLockOperation(
                    baseOperation = LockOperations.BaseOperation(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                    users = emptyList()
                )
            )
        }
        val shareLockUsingContextException = assertFails {
            LockOperationsApi.shareLock(
                shareLockOperation = LockOperations.ShareLockOperation(
                    baseOperation = LockOperations.BaseOperation(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                    shareLock = LockOperations.ShareLock(
                        targetUserId = randomUuid(),
                        targetUserRole = UserRole.USER,
                        targetUserPublicKey = CryptoManager.generateKeyPair().public
                    )
                )
            )
        }
        val unlockUsingContextException = assertFails {
            LockOperationsApi.unlock(
                unlockOperation = LockOperations.UnlockOperation(
                    baseOperation = LockOperations.BaseOperation(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                    directAccessEndpoints = emptyList()
                )
            )
        }
        val updateSecureSettingUnlockDurationUsingContextException = assertFails {
            LockOperationsApi.updateSecureSettingUnlockDuration(
                updateSecureSettingUnlockDuration = LockOperations.UpdateSecureSettingUnlockDuration(
                    baseOperation = LockOperations.BaseOperation(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                    unlockDuration = Duration.ZERO
                )
            )
        }
        val updateSecureSettingUnlockBetweenUsingContextException = assertFails {
            LockOperationsApi.updateSecureSettingUnlockBetween(
                updateSecureSettingUnlockBetween = LockOperations.UpdateSecureSettingUnlockBetween(
                    baseOperation = LockOperations.BaseOperation(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                    unlockBetween = LockOperations.UnlockBetween(
                        start = Clock.System.now().toLocalDateTime(TimeZone.UTC).time,
                        end = Clock.System.now().toLocalDateTime(TimeZone.UTC).time,
                        timezone = TimeZone.UTC.id.toZoneId(),
                        days = EnumSet.noneOf(DayOfWeek::class.java),
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