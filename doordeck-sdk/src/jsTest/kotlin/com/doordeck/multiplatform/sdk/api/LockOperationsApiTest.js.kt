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
import com.doordeck.multiplatform.sdk.model.common.DayOfWeek
import com.doordeck.multiplatform.sdk.model.common.UserRole
import com.doordeck.multiplatform.sdk.model.data.LockOperations
import com.doordeck.multiplatform.sdk.randomDouble
import com.doordeck.multiplatform.sdk.randomInt
import com.doordeck.multiplatform.sdk.randomUuidString
import kotlinx.coroutines.await
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes

class LockOperationsApiTest : IntegrationTest() {

    @Test
    fun shouldGetSingleLock() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // When
        val response = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID).await()

        // Then
        assertEquals(PLATFORM_TEST_MAIN_LOCK_ID, response.id)
    }

    @Test
    fun shouldUpdateLockName() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val updatedLockName = "Doordeck Fusion Test Site - ${randomUuidString()}"

        // When
        LockOperationsApi.updateLockName(PLATFORM_TEST_MAIN_LOCK_ID, updatedLockName).await()

        // Then
        val lock = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID).await()
        assertEquals(updatedLockName, lock.name)
    }

    @Test
    fun shouldUpdateLockFavourite() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val updatedFavourite = true

        // When
        LockOperationsApi.updateLockFavourite(PLATFORM_TEST_MAIN_LOCK_ID, updatedFavourite).await()

        // Then
        val lock = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID).await()
        assertEquals(updatedFavourite, lock.favourite)
    }

    @Test
    fun shouldUpdateLockSettingDefaultName() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val updatedLockDefaultName = "Doordeck Fusion Test Site - ${randomUuidString()}"

        // When
        LockOperationsApi.updateLockSettingDefaultName(PLATFORM_TEST_MAIN_LOCK_ID, updatedLockDefaultName).await()

        // Then
        val lock = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID).await()
        assertEquals(updatedLockDefaultName, lock.settings.defaultName)
    }

    @Test
    fun shouldSetAndRemoveLockSettingPermittedAddresses() = runTest {
        // Given - shouldSetLockSettingPermittedAddresses
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val addedLockPermittedAddresses = listOf("95.19.38.42")

        // When
        LockOperationsApi.setLockSettingPermittedAddresses(PLATFORM_TEST_MAIN_LOCK_ID, addedLockPermittedAddresses).await()

        // Then
        var lock = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID).await()
        assertTrue { lock.settings.permittedAddresses.isNotEmpty() }
        assertContains(addedLockPermittedAddresses, lock.settings.permittedAddresses.first())

        // Given - shouldRemoveLockSettingPermittedAddresses
        val removedLockPermittedAddresses = listOf<String>()

        // When
        LockOperationsApi.setLockSettingPermittedAddresses(PLATFORM_TEST_MAIN_LOCK_ID, removedLockPermittedAddresses).await()

        // Then
        lock = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID).await()
        assertTrue { lock.settings.permittedAddresses.isEmpty() }
    }

    @Test
    fun shouldUpdateLockSettingHidden() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val updatedHidden = false

        // When
        LockOperationsApi.updateLockSettingHidden(PLATFORM_TEST_MAIN_LOCK_ID, updatedHidden).await()

        // Then
        val lock = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID).await()
        assertEquals(updatedHidden, lock.settings.hidden)
    }

    @Test
    fun shouldSetAndRemoveLockSettingTimeRestrictions() = runTest {
        // Given - shouldSetLockSettingTimeRestrictions
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val now = Clock.System.now()
        val min = (now - 1.minutes).toLocalDateTime(TimeZone.UTC)
        val max = (now + 5.minutes).toLocalDateTime(TimeZone.UTC)
        val addedTimeRestriction = LockOperations.TimeRequirement(
            start = "${min.hour.toString().padStart(2, '0')}:${min.minute.toString().padStart(2, '0')}",
            end = "${max.hour.toString().padStart(2, '0')}:${max.minute.toString().padStart(2, '0')}",
            timezone = TimeZone.UTC.id,
            days = setOf(DayOfWeek.entries.random())
        )

        // When
        LockOperationsApi.setLockSettingTimeRestrictions(PLATFORM_TEST_MAIN_LOCK_ID, listOf(addedTimeRestriction)).await()

        // Then
        var lock = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID).await()
        val actualTime = lock.settings.usageRequirements?.time?.firstOrNull()
        assertNotNull(actualTime)
        assertEquals(addedTimeRestriction.start, actualTime.start)
        assertEquals(addedTimeRestriction.end, actualTime.end)
        assertEquals(addedTimeRestriction.timezone, actualTime.timezone)
        assertContains(actualTime.days, addedTimeRestriction.days.first())

        // Given - shouldRemoveLockSettingTimeRestrictions
        val removedTimeRestriction = emptyList<LockOperations.TimeRequirement>()

        // When
        LockOperationsApi.setLockSettingTimeRestrictions(PLATFORM_TEST_MAIN_LOCK_ID, removedTimeRestriction).await()

        // Then
        lock = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID).await()
        assertEquals(0, lock.settings.usageRequirements?.time?.size)
    }

    @Test
    fun shouldUpdateAndRemoveLockSettingLocationRestrictions() = runTest {
        // Given - shouldUpdateLockSettingLocationRestrictions
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
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
        ).await()

        // Then
        var lock = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID).await()
        assertNotNull(lock.settings.usageRequirements?.location)
        assertEquals(addedLocationRestriction.latitude, lock.settings.usageRequirements.location.latitude)
        assertEquals(addedLocationRestriction.longitude, lock.settings.usageRequirements.location.longitude)
        assertEquals(addedLocationRestriction.enabled, lock.settings.usageRequirements.location.enabled)
        assertEquals(addedLocationRestriction.radius, lock.settings.usageRequirements.location.radius)
        assertEquals(addedLocationRestriction.accuracy, lock.settings.usageRequirements.location.accuracy)

        // Given - shouldRemoveLockSettingLocationRestrictions
        val removedLocationRestriction = null

        // When
        LockOperationsApi.updateLockSettingLocationRestrictions(PLATFORM_TEST_MAIN_LOCK_ID, removedLocationRestriction).await()

        // Then
        lock = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID).await()
        assertEquals(removedLocationRestriction, lock.settings.usageRequirements?.location)
    }

    @Test
    fun shouldGetUserPublicKey() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // When
        val result = LockOperationsApi.getUserPublicKey(TEST_MAIN_USER_EMAIL, true).await()

        // Then
        assertEquals(PLATFORM_TEST_MAIN_USER_ID, result.id)
    }

    @Test
    fun shouldGetUserPublicKeyByEmail() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // When
        val result = LockOperationsApi.getUserPublicKeyByEmail(TEST_MAIN_USER_EMAIL).await()

        // Then
        assertEquals(PLATFORM_TEST_MAIN_USER_ID, result.id)
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKey() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // When
        val result = LockOperationsApi.getUserPublicKeyByLocalKey(TEST_MAIN_USER_ID).await()

        // Then
        assertEquals(PLATFORM_TEST_MAIN_USER_ID, result.id)
    }

    @Test
    fun shouldGetUserPublicKeyByEmails() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // When
        val result = LockOperationsApi.getUserPublicKeyByEmails(listOf(TEST_MAIN_USER_EMAIL, TEST_SUPPLEMENTARY_USER_EMAIL)).await()

        // Then
        assertTrue { result.isNotEmpty() }
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeys() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // When
        val result = LockOperationsApi.getUserPublicKeyByLocalKeys(listOf(TEST_MAIN_USER_ID, TEST_SUPPLEMENTARY_USER_ID)).await()

        // Then
        assertTrue { result.isNotEmpty() }
    }

    @Test
    fun shouldGetUsersForLock() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // When
        val usersForLock = LockOperationsApi.getUsersForLock(PLATFORM_TEST_MAIN_LOCK_ID).await()

        // Then
        assertTrue { usersForLock.isNotEmpty() }
        assertTrue { usersForLock.any { it.userId == PLATFORM_TEST_MAIN_USER_ID } }
    }

    @Test
    fun shouldGetLockForUser() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // When
        val locksForUser = LockOperationsApi.getLocksForUser(PLATFORM_TEST_MAIN_USER_ID).await()

        // Then
        assertTrue { locksForUser.devices.isNotEmpty() }
        assertTrue { locksForUser.devices.any { it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID } }
    }

    @Test
    fun shouldGetPinnedLocks() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // When
        val pinnedLocks = LockOperationsApi.getPinnedLocks().await()

        // Then
        assertTrue { pinnedLocks.isNotEmpty() }
    }

    @Test
    fun shouldGetShareableLocks() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // When
        val shareableLocks = LockOperationsApi.getShareableLocks().await()

        // Then
        assertTrue { shareableLocks.isNotEmpty() }
        assertTrue { shareableLocks.any { it.id == PLATFORM_TEST_MAIN_LOCK_ID } }
    }

    @Test
    fun shouldUnlock() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountApi.registerEphemeralKey(
            publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
            privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
        ).await().certificateChain
        val baseOperation = LockOperations.BaseOperation(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            userPrivateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
            lockId = PLATFORM_TEST_MAIN_LOCK_ID
        )

        // When
        LockOperationsApi.unlock(LockOperations.UnlockOperation(baseOperation = baseOperation)).await()
    }

    @Test
    fun shouldUnlockUsingContext() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountApi.registerEphemeralKey(
            publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
            privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
        ).await().certificateChain
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
        ).await()
    }

    @Test
    fun shouldShareAndRevokeLock() = runTest {
        // Given - shouldShareLock
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountApi.registerEphemeralKey(
            publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
            privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
        ).await().certificateChain
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
            )
        ).await()

        // Then
        var locks = LockOperationsApi.getLocksForUser(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).await()
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
            revokeAccessToLockOperation = LockOperations.RevokeAccessToLockOperation(
                baseOperation = revokeBaseOperation,
                users = listOf(PLATFORM_TEST_SUPPLEMENTARY_USER_ID)
            )
        ).await()

        // Then
        locks = LockOperationsApi.getLocksForUser(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).await()
        assertFalse { locks.devices.any { it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID } }
    }

    @Test
    fun shouldBatchShareAndRevokeLock() = runTest {
        // Given - shouldShareLockUsingContext
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountApi.registerEphemeralKey(
            publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
            privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
        ).await().certificateChain
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
        ).await()

        // Then
        assertTrue {
            LockOperationsApi.getLocksForUser(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).await().devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID
            }
        }
        assertTrue {
            LockOperationsApi.getLocksForUser(PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID).await().devices.any {
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
        ).await()

        // Then
        assertFalse {
            LockOperationsApi.getLocksForUser(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).await().devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID }
        }
        assertFalse {
            LockOperationsApi.getLocksForUser(PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID).await().devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID }
        }
    }

    @Test
    fun shouldShareAndRevokeLockUsingContext() = runTest {
        // Given - shouldShareLockUsingContext
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountApi.registerEphemeralKey(
            publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
            privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
        ).await().certificateChain
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
        ).await()

        // Then
        var locks = LockOperationsApi.getLocksForUser(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).await()
        assertTrue { locks.devices.any { it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID } }

        // Given - shouldRevokeAccessToLockUsingContext
        // When
        LockOperationsApi.revokeAccessToLock(
            revokeAccessToLockOperation = LockOperations.RevokeAccessToLockOperation(
                baseOperation = LockOperations.BaseOperation(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                users = listOf(PLATFORM_TEST_SUPPLEMENTARY_USER_ID)
            )
        ).await()

        // Then
        locks = LockOperationsApi.getLocksForUser(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).await()
        assertFalse {
            locks.devices.any { it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID }
        }
    }

    @Test
    fun shouldBatchShareAndRevokeLockUsingContext() = runTest {
        // Given - shouldShareLockUsingContext
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountApi.registerEphemeralKey(
            publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
            privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
        ).await().certificateChain
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
        ).await()

        // Then
        assertTrue {
            LockOperationsApi.getLocksForUser(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).await().devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID
            }
        }
        assertTrue {
            LockOperationsApi.getLocksForUser(PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID).await().devices.any {
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
        ).await()

        // Then
        assertFalse {
            LockOperationsApi.getLocksForUser(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).await().devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID }
        }
        assertFalse {
            LockOperationsApi.getLocksForUser(PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID).await().devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID }
        }
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDuration() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountApi.registerEphemeralKey(
            publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
            privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
        ).await().certificateChain
        val updatedUnlockDuration = randomInt(1, 10)
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
        ).await()

        // Then
        val lock = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID).await()
        assertEquals(updatedUnlockDuration.toDouble(), lock.settings.unlockTime)
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDurationUsingContext() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountApi.registerEphemeralKey(
            publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
            privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
        ).await().certificateChain
        val updatedUnlockDuration = 1
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
        ).await()

        // Then
        val lock = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID).await()
        assertEquals(updatedUnlockDuration.toDouble(), lock.settings.unlockTime)
    }

    @Test
    fun shouldUpdateAndRemoveSecureSettingUnlockBetween() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountApi.registerEphemeralKey(
            publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
            privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
        ).await().certificateChain
        val now = Clock.System.now()
        val min = (now - 1.minutes).toLocalDateTime(TimeZone.UTC)
        val max = (now + 5.minutes).toLocalDateTime(TimeZone.UTC)
        val updatedUnlockBetween = LockOperations.UnlockBetween(
            start = "${min.hour.toString().padStart(2, '0')}:${min.minute.toString().padStart(2, '0')}",
            end = "${max.hour.toString().padStart(2, '0')}:${max.minute.toString().padStart(2, '0')}",
            timezone = TimeZone.UTC.id,
            days = setOf(DayOfWeek.entries.random()),
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
        ).await()

        // Then
        var lock = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID).await()
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
        ).await()

        // Then
        lock = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID).await()
        assertNull(lock.settings.unlockBetweenWindow)
    }

    @Test
    fun shouldUpdateAndRemoveSecureSettingUnlockBetweenUsingContext() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountApi.registerEphemeralKey(
            publicKey = PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
            privateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
        ).await().certificateChain
        val now = Clock.System.now()
        val min = now.minus(5.minutes).toLocalDateTime(TimeZone.UTC)
        val max = now.plus(10.minutes).toLocalDateTime(TimeZone.UTC)
        val updatedUnlockBetween = LockOperations.UnlockBetween(
            start = "${min.hour.toString().padStart(2, '0')}:${min.minute.toString().padStart(2, '0')}",
            end = "${max.hour.toString().padStart(2, '0')}:${max.minute.toString().padStart(2, '0')}",
            timezone = TimeZone.UTC.id,
            days = setOf(DayOfWeek.entries.random()),
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
        ).await()

        // Then
        var lock = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID).await()
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
        ).await()

        // Then
        lock = LockOperationsApi.getSingleLock(PLATFORM_TEST_MAIN_LOCK_ID).await()
        assertNull(lock.settings.unlockBetweenWindow)
    }

    @Test
    fun shouldGetLockAuditTrail() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val now = Clock.System.now()
        val start = now.minus(14.days).epochSeconds.toInt()
        val end = now.epochSeconds.toInt()

        // When
        val lockAuditTrail = LockOperationsApi.getLockAuditTrail(PLATFORM_TEST_MAIN_LOCK_ID, start, end).await()

        // Then
        assertTrue { lockAuditTrail.isNotEmpty() }
    }

    @Test
    fun shouldGetAuditForUser() = runTest {
        // Given
        AccountlessApi.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val now = Clock.System.now()
        val start = now.minus(14.days).epochSeconds.toInt()
        val end = now.epochSeconds.toInt()

        // When
        val auditForUser = LockOperationsApi.getAuditForUser(PLATFORM_TEST_MAIN_USER_ID, start, end).await()

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
            ).await()
        }
        val shareLockUsingContextException = assertFails {
            LockOperationsApi.shareLock(
                shareLockOperation = LockOperations.ShareLockOperation(
                    baseOperation = LockOperations.BaseOperation(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                    shareLock = LockOperations.ShareLock(
                        targetUserId = randomUuidString(),
                        targetUserRole = UserRole.USER,
                        targetUserPublicKey = CryptoManager.generateKeyPair().public
                    )
                )
            ).await()
        }
        val unlockUsingContextException = assertFails {
            LockOperationsApi.unlock(
                unlockOperation = LockOperations.UnlockOperation(
                    baseOperation = LockOperations.BaseOperation(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                    directAccessEndpoints = emptyList()
                )
            ).await()
        }
        val updateSecureSettingUnlockDurationUsingContextException = assertFails {
            LockOperationsApi.updateSecureSettingUnlockDuration(
                updateSecureSettingUnlockDuration = LockOperations.UpdateSecureSettingUnlockDuration(
                    baseOperation = LockOperations.BaseOperation(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                    unlockDuration = 0
                )
            ).await()
        }
        val updateSecureSettingUnlockBetweenUsingContextException = assertFails {
            LockOperationsApi.updateSecureSettingUnlockBetween(
                updateSecureSettingUnlockBetween = LockOperations.UpdateSecureSettingUnlockBetween(
                    baseOperation = LockOperations.BaseOperation(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                    unlockBetween = LockOperations.UnlockBetween(
                        start = "",
                        end = "",
                        timezone = TimeZone.UTC.id,
                        days = emptySet(),
                        exceptions = emptyList()
                    )
                )
            ).await()
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