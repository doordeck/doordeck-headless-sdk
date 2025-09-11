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
import com.doordeck.multiplatform.sdk.randomLong
import com.doordeck.multiplatform.sdk.randomUuid
import com.doordeck.multiplatform.sdk.randomUuidString
import com.doordeck.multiplatform.sdk.util.now
import com.doordeck.multiplatform.sdk.util.toInetAddress
import com.doordeck.multiplatform.sdk.util.toLocalTime
import com.doordeck.multiplatform.sdk.util.toLocalTimeString
import com.doordeck.multiplatform.sdk.util.toZoneId
import kotlinx.coroutines.future.await
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.TimeZone
import java.net.InetAddress
import java.security.KeyPair
import java.time.DayOfWeek
import java.time.Duration
import java.time.LocalTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.EnumSet
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertFails
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class LockOperationsApiAsyncTest : IntegrationTest() {

    @Test
    fun shouldGetSingleLockAsync() = runTest {
        // Given
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // When
        val response = LockOperationsApi.getSingleLockAsync(PLATFORM_TEST_MAIN_LOCK_ID).await()

        // Then
        assertEquals(PLATFORM_TEST_MAIN_LOCK_ID, response.id)
    }

    @Test
    fun shouldUpdateLockNameAsync() = runTest {
        // Given
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val updatedLockName = "Doordeck Fusion Test Site - ${randomUuidString()}"

        // When
        LockOperationsApi.updateLockNameAsync(PLATFORM_TEST_MAIN_LOCK_ID, updatedLockName).await()

        // Then
        val lock = LockOperationsApi.getSingleLockAsync(PLATFORM_TEST_MAIN_LOCK_ID).await()
        assertEquals(updatedLockName, lock.name)
    }

    @Test
    fun shouldUpdateLockFavouriteAsync() = runTest {
        // Given
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val updatedFavourite = true

        // When
        LockOperationsApi.updateLockFavouriteAsync(PLATFORM_TEST_MAIN_LOCK_ID, updatedFavourite).await()

        // Then
        val lock = LockOperationsApi.getSingleLockAsync(PLATFORM_TEST_MAIN_LOCK_ID).await()
        assertEquals(updatedFavourite, lock.favourite)
    }

    @Test
    fun shouldUpdateLockSettingDefaultNameAsync() = runTest {
        // Given
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val updatedLockDefaultName = "Doordeck Fusion Test Site - ${randomUuidString()}"

        // When
        LockOperationsApi.updateLockSettingDefaultNameAsync(PLATFORM_TEST_MAIN_LOCK_ID, updatedLockDefaultName).await()

        // Then
        val lock = LockOperationsApi.getSingleLockAsync(PLATFORM_TEST_MAIN_LOCK_ID).await()
        assertEquals(updatedLockDefaultName, lock.settings.defaultName)
    }

    @Test
    fun shouldSetAndRemoveLockSettingPermittedAddressesAsync() = runTest {
        // Given - shouldSetLockSettingPermittedAddresses
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val addedLockPermittedAddresses = listOf("95.19.38.42".toInetAddress())

        // When
        LockOperationsApi.setLockSettingPermittedAddressesAsync(PLATFORM_TEST_MAIN_LOCK_ID, addedLockPermittedAddresses).await()

        // Then
        var lock = LockOperationsApi.getSingleLockAsync(PLATFORM_TEST_MAIN_LOCK_ID).await()
        assertTrue { lock.settings.permittedAddresses.isNotEmpty() }
        assertContains(addedLockPermittedAddresses, lock.settings.permittedAddresses.first())

        // Given - shouldRemoveLockSettingPermittedAddresses
        val removedLockPermittedAddresses = listOf<InetAddress>()

        // When
        LockOperationsApi.setLockSettingPermittedAddressesAsync(PLATFORM_TEST_MAIN_LOCK_ID, removedLockPermittedAddresses).await()

        // Then
        lock = LockOperationsApi.getSingleLockAsync(PLATFORM_TEST_MAIN_LOCK_ID).await()
        assertTrue { lock.settings.permittedAddresses.isEmpty() }
    }

    @Test
    fun shouldUpdateLockSettingHiddenAsync() = runTest {
        // Given
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val updatedHidden = false

        // When
        LockOperationsApi.updateLockSettingHiddenAsync(PLATFORM_TEST_MAIN_LOCK_ID, updatedHidden).await()

        // Then
        val lock = LockOperationsApi.getSingleLockAsync(PLATFORM_TEST_MAIN_LOCK_ID).await()
        assertEquals(updatedHidden, lock.settings.hidden)
    }

    @Test
    fun shouldSetAndRemoveLockSettingTimeRestrictionsAsync() = runTest {
        // Given - shouldSetLockSettingTimeRestrictions
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val now = now()
        val addedTimeRestriction = LockOperations.TimeRequirement(
            start = LocalTime.ofInstant(now.minus(1, ChronoUnit.MINUTES), ZoneId.of("UTC")),
            end = LocalTime.ofInstant(now.plus(5, ChronoUnit.MINUTES), ZoneId.of("UTC")),
            timezone = TimeZone.UTC.id.toZoneId(),
            days = EnumSet.of(DayOfWeek.entries.random())
        )

        // When
        LockOperationsApi.setLockSettingTimeRestrictionsAsync(PLATFORM_TEST_MAIN_LOCK_ID, listOf(addedTimeRestriction)).await()

        // Then
        var lock = LockOperationsApi.getSingleLockAsync(PLATFORM_TEST_MAIN_LOCK_ID).await()
        val actualTime = lock.settings.usageRequirements?.time?.firstOrNull()
        assertNotNull(actualTime)
        assertEquals(addedTimeRestriction.start.toLocalTimeString().toLocalTime(), actualTime.start)
        assertEquals(addedTimeRestriction.end.toLocalTimeString().toLocalTime(), actualTime.end)
        assertEquals(addedTimeRestriction.timezone, actualTime.timezone)
        assertContains(actualTime.days, addedTimeRestriction.days.first())

        // Given - shouldRemoveLockSettingTimeRestrictions
        val removedTimeRestriction = emptyList<LockOperations.TimeRequirement>()

        // When
        LockOperationsApi.setLockSettingTimeRestrictionsAsync(PLATFORM_TEST_MAIN_LOCK_ID, removedTimeRestriction).await()

        // Then
        lock = LockOperationsApi.getSingleLockAsync(PLATFORM_TEST_MAIN_LOCK_ID).await()
        assertEquals(0, lock.settings.usageRequirements?.time?.size)
    }

    @Test
    fun shouldUpdateAndRemoveLockSettingLocationRestrictionsAsync() = runTest {
        // Given - shouldUpdateLockSettingLocationRestrictions
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val addedLocationRestriction = LockOperations.LocationRequirement(
            latitude = randomDouble(-90.0, 90.0),
            longitude = randomDouble(-180.0, 180.0),
            enabled = true,
            radius = randomInt(1, 100),
            accuracy = randomInt(1, 100)
        )

        // When
        LockOperationsApi.updateLockSettingLocationRestrictionsAsync(
            lockId = PLATFORM_TEST_MAIN_LOCK_ID,
            location = addedLocationRestriction
        ).await()

        // Then
        var lock = LockOperationsApi.getSingleLockAsync(PLATFORM_TEST_MAIN_LOCK_ID).await()
        assertNotNull(lock.settings.usageRequirements?.location)
        assertEquals(addedLocationRestriction.latitude, lock.settings.usageRequirements.location.latitude)
        assertEquals(addedLocationRestriction.longitude, lock.settings.usageRequirements.location.longitude)
        assertEquals(addedLocationRestriction.enabled, lock.settings.usageRequirements.location.enabled)
        assertEquals(addedLocationRestriction.radius, lock.settings.usageRequirements.location.radius)
        assertEquals(addedLocationRestriction.accuracy, lock.settings.usageRequirements.location.accuracy)

        // Given - shouldRemoveLockSettingLocationRestrictions
        val removedLocationRestriction = null

        // When
        LockOperationsApi.updateLockSettingLocationRestrictionsAsync(PLATFORM_TEST_MAIN_LOCK_ID, removedLocationRestriction).await()

        // Then
        lock = LockOperationsApi.getSingleLockAsync(PLATFORM_TEST_MAIN_LOCK_ID).await()
        assertEquals(removedLocationRestriction, lock.settings.usageRequirements?.location)
    }

    @Test
    fun shouldGetUserPublicKeyAsync() = runTest {
        // Given
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // When
        val result = LockOperationsApi.getUserPublicKeyAsync(TEST_MAIN_USER_EMAIL, true).await()

        // Then
        assertEquals(PLATFORM_TEST_MAIN_USER_ID, result.id)
    }

    @Test
    fun shouldGetUserPublicKeyByEmailAsync() = runTest {
        // Given
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // When
        val result = LockOperationsApi.getUserPublicKeyByEmailAsync(TEST_MAIN_USER_EMAIL).await()

        // Then
        assertEquals(PLATFORM_TEST_MAIN_USER_ID, result.id)
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeyAsync() = runTest {
        // Given
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // When
        val result = LockOperationsApi.getUserPublicKeyByLocalKeyAsync(TEST_MAIN_USER_ID).await()

        // Then
        assertEquals(PLATFORM_TEST_MAIN_USER_ID, result.id)
    }

    @Test
    fun shouldGetUserPublicKeyByEmailsAsync() = runTest {
        // Given
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // When
        val result = LockOperationsApi.getUserPublicKeyByEmailsAsync(listOf(TEST_MAIN_USER_EMAIL, TEST_SUPPLEMENTARY_USER_EMAIL)).await()

        // Then
        assertTrue { result.isNotEmpty() }
    }

    @Test
    fun shouldGetUserPublicKeyByLocalKeysAsync() = runTest {
        // Given
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // When
        val result = LockOperationsApi.getUserPublicKeyByLocalKeysAsync(listOf(TEST_MAIN_USER_ID, TEST_SUPPLEMENTARY_USER_ID)).await()

        // Then
        assertTrue { result.isNotEmpty() }
    }

    @Test
    fun shouldGetUsersForLockAsync() = runTest {
        // Given
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // When
        val usersForLock = LockOperationsApi.getUsersForLockAsync(PLATFORM_TEST_MAIN_LOCK_ID).await()

        // Then
        assertTrue { usersForLock.isNotEmpty() }
        assertTrue { usersForLock.any { it.userId == PLATFORM_TEST_MAIN_USER_ID } }
    }

    @Test
    fun shouldGetLockForUserAsync() = runTest {
        // Given
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // When
        val locksForUser = LockOperationsApi.getLocksForUserAsync(PLATFORM_TEST_MAIN_USER_ID).await()

        // Then
        assertTrue { locksForUser.devices.isNotEmpty() }
        assertTrue { locksForUser.devices.any { it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID } }
    }

    @Test
    fun shouldGetPinnedLocksAsync() = runTest {
        // Given
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // When
        val pinnedLocks = LockOperationsApi.getPinnedLocksAsync().await()

        // Then
        assertTrue { pinnedLocks.isNotEmpty() }
    }

    @Test
    fun shouldGetShareableLocksAsync() = runTest {
        // Given
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()

        // When
        val shareableLocks = LockOperationsApi.getShareableLocksAsync().await()

        // Then
        assertTrue { shareableLocks.isNotEmpty() }
        assertTrue { shareableLocks.any { it.id == PLATFORM_TEST_MAIN_LOCK_ID } }
    }

    @Test
    fun shouldUnlockAsync() = runTest {
        // Given
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountApi.registerEphemeralKeyAsync(
            KeyPair(
                PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
                PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
            )
        ).await().certificateChain
        val baseOperation = LockOperations.BaseOperation(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            userPrivateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
            lockId = PLATFORM_TEST_MAIN_LOCK_ID
        )

        // When
        LockOperationsApi.unlockAsync(LockOperations.UnlockOperation(baseOperation = baseOperation)).await()
    }

    @Test
    fun shouldUnlockUsingContextAsync() = runTest {
        // Given
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountApi.registerEphemeralKeyAsync(
            KeyPair(
                PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
                PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
            )
        ).await().certificateChain
        ContextManager.setOperationContext(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            keyPair = KeyPair(
                PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
                PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
            ),
            isKeyPairVerified = true
        )

        // When
        LockOperationsApi.unlockAsync(
            unlockOperation = LockOperations.UnlockOperation(
                baseOperation = LockOperations.BaseOperation(lockId = PLATFORM_TEST_MAIN_LOCK_ID)
            )
        ).await()
    }

    @Test
    fun shouldShareAndRevokeLockAsync() = runTest {
        // Given - shouldShareLock
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountApi.registerEphemeralKeyAsync(
            KeyPair(
                PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
                PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
            )
        ).await().certificateChain
        val shareBaseOperation = LockOperations.BaseOperation(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            userPrivateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
            lockId = PLATFORM_TEST_MAIN_LOCK_ID
        )

        // When
        LockOperationsApi.shareLockAsync(
            shareLockOperation = LockOperations.ShareLockOperation(
                baseOperation = shareBaseOperation,
                shareLock = LockOperations.ShareLock(
                    targetUserId = PLATFORM_TEST_SUPPLEMENTARY_USER_ID,
                    targetUserRole = UserRole.USER,
                    targetUserPublicKey = PLATFORM_TEST_SUPPLEMENTARY_USER_PUBLIC_KEY
                )
            )).await()

        // Then
        var locks = LockOperationsApi.getLocksForUserAsync(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).await()
        assertTrue { locks.devices.any { it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID } }

        // Given - shouldRevokeAccessToLock
        val revokeBaseOperation = LockOperations.BaseOperation(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            userPrivateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
            lockId = PLATFORM_TEST_MAIN_LOCK_ID
        )

        // When
        LockOperationsApi.revokeAccessToLockAsync(
            LockOperations.RevokeAccessToLockOperation(
                baseOperation = revokeBaseOperation,
                users = listOf(PLATFORM_TEST_SUPPLEMENTARY_USER_ID)
            )).await()

        // Then
        locks = LockOperationsApi.getLocksForUserAsync(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).await()
        assertFalse { locks.devices.any { it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID } }
    }

    @Test
    fun shouldBatchShareAndRevokeLockAsync() = runTest {
        // Given - shouldShareLockUsingContext
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountApi.registerEphemeralKeyAsync(
            KeyPair(
                PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
                PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
            )
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
        LockOperationsApi.batchShareLockAsync(
            batchShareLockOperation = LockOperations.BatchShareLockOperation(
                baseOperation = shareBaseOperation,
                users = batchShareLock
            )
        ).await()

        // Then
        assertTrue {
            LockOperationsApi.getLocksForUserAsync(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).await().devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID
            }
        }
        assertTrue {
            LockOperationsApi.getLocksForUserAsync(PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID).await().devices.any {
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
        LockOperationsApi.revokeAccessToLockAsync(
            revokeAccessToLockOperation = LockOperations.RevokeAccessToLockOperation(
                baseOperation = revokeBaseOperation,
                users = listOf(PLATFORM_TEST_SUPPLEMENTARY_USER_ID, PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID)
            )
        ).await()

        // Then
        assertFalse {
            LockOperationsApi.getLocksForUserAsync(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).await().devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID }
        }
        assertFalse {
            LockOperationsApi.getLocksForUserAsync(PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID).await().devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID }
        }
    }

    @Test
    fun shouldShareAndRevokeLockUsingContextAsync() = runTest {
        // Given - shouldShareLockUsingContext
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountApi.registerEphemeralKeyAsync(
            KeyPair(
                PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
                PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
            )
        ).await().certificateChain
        ContextManager.setOperationContext(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            keyPair = KeyPair(
                PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
                PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
            ),
            isKeyPairVerified = true
        )
        val shareLock = LockOperations.ShareLock(
            targetUserId = PLATFORM_TEST_SUPPLEMENTARY_USER_ID,
            targetUserRole = UserRole.USER,
            targetUserPublicKey = PLATFORM_TEST_SUPPLEMENTARY_USER_PUBLIC_KEY
        )

        // When
        LockOperationsApi.shareLockAsync(
            shareLockOperation = LockOperations.ShareLockOperation(
                baseOperation = LockOperations.BaseOperation(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                shareLock = shareLock
            )
        ).await()

        // Then
        var locks = LockOperationsApi.getLocksForUserAsync(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).await()
        assertTrue { locks.devices.any { it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID } }

        // Given - shouldRevokeAccessToLockUsingContext
        // When
        LockOperationsApi.revokeAccessToLockAsync(
            revokeAccessToLockOperation = LockOperations.RevokeAccessToLockOperation(
                baseOperation = LockOperations.BaseOperation(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                users = listOf(PLATFORM_TEST_SUPPLEMENTARY_USER_ID)
            )
        ).await()

        // Then
        locks = LockOperationsApi.getLocksForUserAsync(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).await()
        assertFalse {
            locks.devices.any { it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID }
        }
    }

    @Test
    fun shouldBatchShareAndRevokeLockUsingContextAsync() = runTest {
        // Given - shouldShareLockUsingContext
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountApi.registerEphemeralKeyAsync(
            KeyPair(
                PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
                PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
            )
        ).await().certificateChain
        ContextManager.setOperationContext(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            keyPair = KeyPair(
                PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
                PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
            ),
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
        LockOperationsApi.batchShareLockAsync(
            batchShareLockOperation = LockOperations.BatchShareLockOperation(
                baseOperation = LockOperations.BaseOperation(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                users = batchShareLock
            )
        ).await()

        // Then
        assertTrue {
            LockOperationsApi.getLocksForUserAsync(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).await().devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID
            }
        }
        assertTrue {
            LockOperationsApi.getLocksForUserAsync(PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID).await().devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID
            }
        }

        // Given - shouldRevokeAccessToLockUsingContext
        // When
        LockOperationsApi.revokeAccessToLockAsync(
            revokeAccessToLockOperation = LockOperations.RevokeAccessToLockOperation(
                baseOperation = LockOperations.BaseOperation(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                users = listOf(PLATFORM_TEST_SUPPLEMENTARY_USER_ID, PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID)
            )
        ).await()

        // Then
        assertFalse {
            LockOperationsApi.getLocksForUserAsync(PLATFORM_TEST_SUPPLEMENTARY_USER_ID).await().devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID }
        }
        assertFalse {
            LockOperationsApi.getLocksForUserAsync(PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID).await().devices.any {
                it.deviceId == PLATFORM_TEST_MAIN_LOCK_ID }
        }
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDurationAsync() = runTest {
        // Given
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountApi.registerEphemeralKeyAsync(
            KeyPair(
                PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
                PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
            )
        ).await().certificateChain
        val updatedUnlockDuration = Duration.ofSeconds(randomLong(1, 10))
        val baseOperation = LockOperations.BaseOperation(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            userPrivateKey = PLATFORM_TEST_MAIN_USER_PRIVATE_KEY,
            lockId = PLATFORM_TEST_MAIN_LOCK_ID
        )

        // When
        LockOperationsApi.updateSecureSettingUnlockDurationAsync(
            updateSecureSettingUnlockDuration = LockOperations.UpdateSecureSettingUnlockDuration(
                baseOperation = baseOperation,
                unlockDuration = updatedUnlockDuration
            )
        ).await()

        // Then
        val lock = LockOperationsApi.getSingleLockAsync(PLATFORM_TEST_MAIN_LOCK_ID).await()
        assertEquals(updatedUnlockDuration, lock.settings.unlockTime)
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDurationUsingContextAsync() = runTest {
        // Given
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountApi.registerEphemeralKeyAsync(
            KeyPair(
                PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
                PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
            )
        ).await().certificateChain
        val updatedUnlockDuration = Duration.ofSeconds(1)
        ContextManager.setOperationContext(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            keyPair = KeyPair(
                PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
                PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
            ),
            isKeyPairVerified = true
        )

        // When
        LockOperationsApi.updateSecureSettingUnlockDurationAsync(
            updateSecureSettingUnlockDuration = LockOperations.UpdateSecureSettingUnlockDuration(
                baseOperation = LockOperations.BaseOperation(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                unlockDuration = updatedUnlockDuration
            )
        ).await()

        // Then
        val lock = LockOperationsApi.getSingleLockAsync(PLATFORM_TEST_MAIN_LOCK_ID).await()
        assertEquals(updatedUnlockDuration, lock.settings.unlockTime)
    }

    @Test
    fun shouldUpdateAndRemoveSecureSettingUnlockBetweenAsync() = runTest {
        // Given
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountApi.registerEphemeralKeyAsync(
            KeyPair(
                PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
                PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
            )
        ).await().certificateChain
        val now = now()
        val updatedUnlockBetween = LockOperations.UnlockBetween(
            start = LocalTime.ofInstant(now.minus(1, ChronoUnit.MINUTES), ZoneId.of("UTC")),
            end = LocalTime.ofInstant(now.plus(5, ChronoUnit.MINUTES), ZoneId.of("UTC")),
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
        LockOperationsApi.updateSecureSettingUnlockBetweenAsync(
            updateSecureSettingUnlockBetween = LockOperations.UpdateSecureSettingUnlockBetween(
                baseOperation = addBaseOperation,
                unlockBetween = updatedUnlockBetween
            )
        ).await()

        // Then
        var lock = LockOperationsApi.getSingleLockAsync(PLATFORM_TEST_MAIN_LOCK_ID).await()
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
        LockOperationsApi.updateSecureSettingUnlockBetweenAsync(
            updateSecureSettingUnlockBetween = LockOperations.UpdateSecureSettingUnlockBetween(
                baseOperation = removeBaseOperation,
                unlockBetween = null
            )
        ).await()

        // Then
        lock = LockOperationsApi.getSingleLockAsync(PLATFORM_TEST_MAIN_LOCK_ID).await()
        assertNull(lock.settings.unlockBetweenWindow)
    }

    @Test
    fun shouldUpdateAndRemoveSecureSettingUnlockBetweenUsingContextAsync() = runTest {
        // Given
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = AccountApi.registerEphemeralKeyAsync(
            KeyPair(
                PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
                PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
            )
        ).await().certificateChain
        val now = now()
        val min = now.minus(5, ChronoUnit.MINUTES)
        val max = now.plus(10, ChronoUnit.MINUTES)
        val updatedUnlockBetween = LockOperations.UnlockBetween(
            start = LocalTime.ofInstant(min, ZoneId.of("UTC")),
            end = LocalTime.ofInstant(max, ZoneId.of("UTC")),
            timezone = TimeZone.UTC.id.toZoneId(),
            days = EnumSet.of(DayOfWeek.entries.random()),
            exceptions = emptyList()
        )
        ContextManager.setOperationContext(
            userId = PLATFORM_TEST_MAIN_USER_ID,
            certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN,
            keyPair = KeyPair(
                PLATFORM_TEST_MAIN_USER_PUBLIC_KEY,
                PLATFORM_TEST_MAIN_USER_PRIVATE_KEY
            ),
            isKeyPairVerified = true
        )

        // When
        LockOperationsApi.updateSecureSettingUnlockBetweenAsync(
            updateSecureSettingUnlockBetween = LockOperations.UpdateSecureSettingUnlockBetween(
                baseOperation = LockOperations.BaseOperation(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                unlockBetween = updatedUnlockBetween
            )
        ).await()

        // Then
        var lock = LockOperationsApi.getSingleLockAsync(PLATFORM_TEST_MAIN_LOCK_ID).await()
        assertNotNull(lock.settings.unlockBetweenWindow)
        assertEquals(updatedUnlockBetween.start.toLocalTimeString().toLocalTime(), lock.settings.unlockBetweenWindow.start)
        assertEquals(updatedUnlockBetween.end.toLocalTimeString().toLocalTime(), lock.settings.unlockBetweenWindow.end)
        assertEquals(updatedUnlockBetween.timezone, lock.settings.unlockBetweenWindow.timezone)
        assertEquals(updatedUnlockBetween.days, lock.settings.unlockBetweenWindow.days)

        // Given
        LockOperationsApi.updateSecureSettingUnlockBetweenAsync(
            updateSecureSettingUnlockBetween = LockOperations.UpdateSecureSettingUnlockBetween(
                baseOperation = LockOperations.BaseOperation(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                unlockBetween = null
            )
        ).await()

        // Then
        lock = LockOperationsApi.getSingleLockAsync(PLATFORM_TEST_MAIN_LOCK_ID).await()
        assertNull(lock.settings.unlockBetweenWindow)
    }

    @Test
    fun shouldGetLockAuditTrailAsync() = runTest {
        // Given
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val now = now()
        val start = now.minus(14, ChronoUnit.DAYS)
        val end = now

        // When
        val lockAuditTrail = LockOperationsApi.getLockAuditTrailAsync(PLATFORM_TEST_MAIN_LOCK_ID, start, end).await()

        // Then
        assertTrue { lockAuditTrail.isNotEmpty() }
    }

    @Test
    fun shouldGetAuditForUserAsync() = runTest {
        // Given
        AccountlessApi.loginAsync(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        val now = now()
        val start = now.minus(14, ChronoUnit.DAYS)
        val end = now

        // When
        val auditForUser = LockOperationsApi.getAuditForUserAsync(PLATFORM_TEST_MAIN_USER_ID, start, end).await()

        // Then
        assertTrue { auditForUser.isNotEmpty() }
    }

    @Test
    fun shouldThrowExceptionWhenOperationContextIsMissingAsync() = runTest {
        // When
        val revokeAccessToLockUsingContextException = assertFails {
            LockOperationsApi.revokeAccessToLockAsync(
                revokeAccessToLockOperation = LockOperations.RevokeAccessToLockOperation(
                    baseOperation = LockOperations.BaseOperation(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                    users = emptyList()
                )
            ).await()
        }
        val shareLockUsingContextException = assertFails {
            LockOperationsApi.shareLockAsync(
                shareLockOperation = LockOperations.ShareLockOperation(
                    baseOperation = LockOperations.BaseOperation(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                    shareLock = LockOperations.ShareLock(
                        targetUserId = randomUuid(),
                        targetUserRole = UserRole.USER,
                        targetUserPublicKey = CryptoManager.generateKeyPair().public
                    )
                )
            ).await()
        }
        val unlockUsingContextException = assertFails {
            LockOperationsApi.unlockAsync(
                unlockOperation = LockOperations.UnlockOperation(
                    baseOperation = LockOperations.BaseOperation(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                    directAccessEndpoints = emptyList()
                )
            ).await()
        }
        val updateSecureSettingUnlockDurationUsingContextException = assertFails {
            LockOperationsApi.updateSecureSettingUnlockDurationAsync(
                updateSecureSettingUnlockDuration = LockOperations.UpdateSecureSettingUnlockDuration(
                    baseOperation = LockOperations.BaseOperation(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                    unlockDuration = Duration.ZERO
                )
            ).await()
        }
        val updateSecureSettingUnlockBetweenUsingContextException = assertFails {
            LockOperationsApi.updateSecureSettingUnlockBetweenAsync(
                updateSecureSettingUnlockBetween = LockOperations.UpdateSecureSettingUnlockBetween(
                    baseOperation = LockOperations.BaseOperation(lockId = PLATFORM_TEST_MAIN_LOCK_ID),
                    unlockBetween = LockOperations.UnlockBetween(
                        start = LocalTime.ofInstant(now(), ZoneId.of("UTC")),
                        end = LocalTime.ofInstant(now(), ZoneId.of("UTC")),
                        timezone = TimeZone.UTC.id.toZoneId(),
                        days = EnumSet.noneOf(DayOfWeek::class.java),
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