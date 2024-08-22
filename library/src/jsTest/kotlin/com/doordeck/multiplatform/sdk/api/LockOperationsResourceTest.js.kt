package com.doordeck.multiplatform.sdk.api

import com.benasher44.uuid.uuid4
import com.doordeck.multiplatform.sdk.IntegrationTest
import com.doordeck.multiplatform.sdk.MissingOperationContextException
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
import com.doordeck.multiplatform.sdk.util.Crypto.certificateChainArrayToString
import com.doordeck.multiplatform.sdk.util.Crypto.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Crypto.stringToCertificateChainArray
import kotlinx.coroutines.await
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

/*class LockOperationsResourceTest : IntegrationTest() {

    @Test
    fun shouldGetSingleLock() = runTest {
        // Given
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)

        // When
        val response = LOCK_OPERATIONS_RESOURCE.getSingleLock(TEST_MAIN_LOCK_ID).await()

        // Then
        assertEquals(TEST_MAIN_LOCK_ID, response.id)
    }

    @Test
    fun shouldUpdateLockName() = runTest {
        // Given
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val updatedLockName = "Demo ${uuid4()} Lock"

        // When
        LOCK_OPERATIONS_RESOURCE.updateLockName(TEST_MAIN_LOCK_ID, updatedLockName).await()

        // Then
        val lock = LOCK_OPERATIONS_RESOURCE.getSingleLock(TEST_MAIN_LOCK_ID).await()
        assertEquals(updatedLockName, lock.name)
    }

    @Test
    fun shouldUpdateLockFavourite() = runTest {
        // Given
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val updatedFavourite = true

        // When
        LOCK_OPERATIONS_RESOURCE.updateLockFavourite(TEST_MAIN_LOCK_ID, updatedFavourite).await()

        // Then
        val lock = LOCK_OPERATIONS_RESOURCE.getSingleLock(TEST_MAIN_LOCK_ID).await()
        assertEquals(updatedFavourite, lock.favourite)
    }

    @Test
    fun shouldUpdateLockColour() = runTest {
        // Given
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val updatedLockColour = "#${Random.nextInt(111111, 999999)}"

        // When
        LOCK_OPERATIONS_RESOURCE.updateLockColour(TEST_MAIN_LOCK_ID, updatedLockColour).await()

        // Then
        val lock = LOCK_OPERATIONS_RESOURCE.getSingleLock(TEST_MAIN_LOCK_ID).await()
        assertEquals(updatedLockColour, lock.colour)
    }

    @Test
    fun shouldUpdateLockSettingDefaultName() = runTest {
        // Given
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val updatedLockDefaultName = "Demo ${uuid4()} Lock"

        // When
        LOCK_OPERATIONS_RESOURCE.updateLockSettingDefaultName(TEST_MAIN_LOCK_ID, updatedLockDefaultName).await()

        // Then
        val lock = LOCK_OPERATIONS_RESOURCE.getSingleLock(TEST_MAIN_LOCK_ID).await()
        assertEquals(updatedLockDefaultName, lock.settings.defaultName)
    }

    @Test
    fun shouldSetAndRemoveLockSettingPermittedAddresses() = runTest {
        // Given - shouldSetLockSettingPermittedAddresses
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val addedLockPermittedAddresses = arrayOf("95.19.38.42")

        // When
        LOCK_OPERATIONS_RESOURCE.setLockSettingPermittedAddresses(TEST_MAIN_LOCK_ID, addedLockPermittedAddresses).await()

        // Then
        var lock = LOCK_OPERATIONS_RESOURCE.getSingleLock(TEST_MAIN_LOCK_ID).await()
        assertTrue { lock.settings.permittedAddresses.isNotEmpty() }
        assertContains(addedLockPermittedAddresses, lock.settings.permittedAddresses.first())

        // Given - shouldRemoveLockSettingPermittedAddresses
        val removedLockPermittedAddresses = emptyArray<String>()

        // When
        LOCK_OPERATIONS_RESOURCE.setLockSettingPermittedAddresses(TEST_MAIN_LOCK_ID, removedLockPermittedAddresses).await()

        // Then
        lock = LOCK_OPERATIONS_RESOURCE.getSingleLock(TEST_MAIN_LOCK_ID).await()
        assertTrue { lock.settings.permittedAddresses.isEmpty() }
    }

    @Test
    fun shouldUpdateLockSettingHidden() = runTest {
        // Given
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val updatedHidden = false

        // When
        LOCK_OPERATIONS_RESOURCE.updateLockSettingHidden(TEST_MAIN_LOCK_ID, updatedHidden).await()

        // Then
        val lock = LOCK_OPERATIONS_RESOURCE.getSingleLock(TEST_MAIN_LOCK_ID).await()
        assertEquals(updatedHidden, lock.settings.hidden)
    }

    @Test
    fun shouldSetAndRemoveLockSettingTimeRestrictions() = runTest {
        // Given - shouldSetLockSettingTimeRestrictions
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val timezone = TimeZone.UTC
        val now = Clock.System.now()
        val min = now.minus(1.minutes).toLocalDateTime(timezone)
        val max = now.plus(5.minutes).toLocalDateTime(timezone)
        val addedTimeRestriction = LockOperations.TimeRequirement(
            start = "${min.hour.toString().padStart(2, '0')}:${min.minute.toString().padStart(2, '0')}",
            end = "${max.hour.toString().padStart(2, '0')}:${max.minute.toString().padStart(2, '0')}",
            timezone = timezone.id,
            days = arrayOf(min.dayOfWeek.name)
        )

        // When
        LOCK_OPERATIONS_RESOURCE.setLockSettingTimeRestrictions(TEST_MAIN_LOCK_ID, arrayOf(addedTimeRestriction)).await()

        // Then
        var lock = LOCK_OPERATIONS_RESOURCE.getSingleLock(TEST_MAIN_LOCK_ID).await()
        val actualTime = lock.settings.usageRequirements?.time?.firstOrNull()
        assertNotNull(actualTime)
        assertEquals(addedTimeRestriction.start, actualTime.start)
        assertEquals(addedTimeRestriction.end, actualTime.end)
        assertEquals(addedTimeRestriction.timezone, actualTime.timezone)
        assertContains(actualTime.days, addedTimeRestriction.days.first())

        // Given - shouldRemoveLockSettingTimeRestrictions
        val removedTimeRestriction = emptyArray<LockOperations.TimeRequirement>()

        // When
        LOCK_OPERATIONS_RESOURCE.setLockSettingTimeRestrictions(TEST_MAIN_LOCK_ID, removedTimeRestriction).await()

        // Then
        lock = LOCK_OPERATIONS_RESOURCE.getSingleLock(TEST_MAIN_LOCK_ID).await()
        assertNull(lock.settings.usageRequirements?.time)
    }

    @Test
    fun shouldUpdateAndRemoveLockSettingLocationRestrictions() = runTest {
        // Given - shouldUpdateLockSettingLocationRestrictions
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val addedLocationRestriction = LockOperations.LocationRequirement(
            latitude = Random.nextDouble(-90.0, 90.0),
            longitude = Random.nextDouble(-180.0, 180.0),
            enabled = true,
            radius = Random.nextInt(1, 100),
            accuracy = Random.nextInt(1, 100)
        )

        // When
        LOCK_OPERATIONS_RESOURCE.updateLockSettingLocationRestrictions(TEST_MAIN_LOCK_ID, addedLocationRestriction).await()

        // Then
        var lock = LOCK_OPERATIONS_RESOURCE.getSingleLock(TEST_MAIN_LOCK_ID).await()
        assertNotNull(lock.settings.usageRequirements?.location)
        assertEquals(addedLocationRestriction.latitude, lock.settings.usageRequirements?.location?.latitude)
        assertEquals(addedLocationRestriction.longitude, lock.settings.usageRequirements?.location?.longitude)
        assertEquals(addedLocationRestriction.enabled, lock.settings.usageRequirements?.location?.enabled)
        assertEquals(addedLocationRestriction.radius, lock.settings.usageRequirements?.location?.radius)
        assertEquals(addedLocationRestriction.accuracy, lock.settings.usageRequirements?.location?.accuracy)

        // Given - shouldRemoveLockSettingLocationRestrictions
        val removedLocationRestriction = null

        // When
        LOCK_OPERATIONS_RESOURCE.updateLockSettingLocationRestrictions(TEST_MAIN_LOCK_ID, removedLocationRestriction).await()

        // Then
        lock = LOCK_OPERATIONS_RESOURCE.getSingleLock(TEST_MAIN_LOCK_ID).await()
        assertEquals(removedLocationRestriction, lock.settings.usageRequirements?.location)
    }

    @Test
    fun shouldGetUserPublicKey() = runTest {
        // Given
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)

        // When
        val result = LOCK_OPERATIONS_RESOURCE.getUserPublicKey(TEST_MAIN_USER_EMAIL, true).await()

        // Then
        assertTrue { result.publicKey.isNotEmpty() }
    }

    @Test
    fun shouldGetUserPublicKeyByEmail() = runTest {
        // Given
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)

        // When
        val result = LOCK_OPERATIONS_RESOURCE.getUserPublicKeyByEmail(TEST_MAIN_USER_EMAIL).await()

        // Then
        assertTrue { result.publicKey.isNotEmpty() }
    }

    @Test
    fun shouldGetUsersForLock() = runTest {
        // Given
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)

        // When
        val usersForLock = LOCK_OPERATIONS_RESOURCE.getUsersForLock(TEST_MAIN_LOCK_ID).await()

        // Then
        assertTrue { usersForLock.isNotEmpty() }
    }

    @Test
    fun shouldGetLockForUser() = runTest {
        // Given
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)

        // When
        val locksForUser = LOCK_OPERATIONS_RESOURCE.getLocksForUser(TEST_MAIN_USER_ID).await()

        // Then
        assertTrue { locksForUser.devices.isNotEmpty() }
    }

    @Test
    fun shouldGetPinnedLocks() = runTest {
        // Given
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)

        // When
        val pinnedLocks = LOCK_OPERATIONS_RESOURCE.getPinnedLocks().await()

        // Then
        assertTrue { pinnedLocks.isNotEmpty() }
    }

    @Test
    fun shouldGetShareableLocks() = runTest {
        // Given
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)

        // When
        val shareableLocks = LOCK_OPERATIONS_RESOURCE.getShareableLocks().await()

        // Then
        assertTrue { shareableLocks.isNotEmpty() }
    }

    @Test
    fun shouldUnlock() = runTest {
        // Given
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = ACCOUNT_RESOURCE.registerEphemeralKey(TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray()).await()
            .certificateChain
            .certificateChainArrayToString()

        val baseOperation = LockOperations.BaseOperation(
            userId = TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChainArray(),
            userPrivateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
            lockId = TEST_MAIN_LOCK_ID
        )

        // When
        LOCK_OPERATIONS_RESOURCE.unlock(LockOperations.UnlockOperation(baseOperation = baseOperation)).await()

        // Then
        assertTrue { true }
    }

    @Test
    fun shouldUnlockWithContext() = runTest {
        // Given
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = ACCOUNT_RESOURCE.registerEphemeralKey(TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray()).await()
            .certificateChain
            .certificateChainArrayToString()
        CONTEXT_MANAGER.setOperationContext(
            userId = TEST_MAIN_USER_ID,
            certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChainArray(),
            privateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray()
        )

        // When
        LOCK_OPERATIONS_RESOURCE.unlockWithContext(TEST_MAIN_LOCK_ID).await()

        // Then
        assertTrue { true }
    }

    @Test
    fun shouldShareAndRevokeLock() = runTest {
        // Given - shouldShareLock
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = ACCOUNT_RESOURCE.registerEphemeralKey(TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray()).await()
            .certificateChain
            .certificateChainArrayToString()
        val shareBaseOperation = LockOperations.BaseOperation(
            userId = TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChainArray(),
            userPrivateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
            lockId = TEST_MAIN_LOCK_ID
        )

        // When
        LOCK_OPERATIONS_RESOURCE.shareLock(LockOperations.ShareLockOperation(
            baseOperation = shareBaseOperation,
            shareLock = LockOperations.ShareLock(
                targetUserId = TEST_SUPPLEMENTARY_USER_ID,
                targetUserRole = UserRole.USER,
                targetUserPublicKey = TEST_SUPPLEMENTARY_USER_PUBLIC_KEY.decodeBase64ToByteArray()
            )
        )).await()

        // Then
        var locks = LOCK_OPERATIONS_RESOURCE.getLocksForUser(TEST_SUPPLEMENTARY_USER_ID).await()
        assertTrue { locks.devices.isNotEmpty() }

        // Given - shouldRevokeAccessToLock
        val revokeBaseOperation = LockOperations.BaseOperation(
            userId = TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChainArray(),
            userPrivateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
            lockId = TEST_MAIN_LOCK_ID
        )

        // When
        LOCK_OPERATIONS_RESOURCE.revokeAccessToLock(LockOperations.RevokeAccessToLockOperation(
            baseOperation = revokeBaseOperation,
            users = arrayOf(TEST_SUPPLEMENTARY_USER_ID)
        )).await()

        // Then
        locks = LOCK_OPERATIONS_RESOURCE.getLocksForUser(TEST_SUPPLEMENTARY_USER_ID).await()
        assertTrue { locks.devices.isEmpty() }
    }

    @Test
    fun shouldShareAndRevokeLockWithContext() = runTest {
        // Given - shouldShareLockWithContext
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = ACCOUNT_RESOURCE.registerEphemeralKey(TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray()).await()
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
        LOCK_OPERATIONS_RESOURCE.shareLockWithContext(
            lockId = TEST_MAIN_LOCK_ID,
            shareLock = shareLock
        ).await()

        // Then
        var locks = LOCK_OPERATIONS_RESOURCE.getLocksForUser(TEST_SUPPLEMENTARY_USER_ID).await()
        assertTrue { locks.devices.isNotEmpty() }

        // Given - shouldRevokeAccessToLockWithContext
        // When
        LOCK_OPERATIONS_RESOURCE.revokeAccessToLockWithContext(
            lockId = TEST_MAIN_LOCK_ID,
            users = arrayOf(TEST_SUPPLEMENTARY_USER_ID)
        ).await()

        // Then
        locks = LOCK_OPERATIONS_RESOURCE.getLocksForUser(TEST_SUPPLEMENTARY_USER_ID).await()
        assertTrue { locks.devices.isEmpty() }
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDuration() = runTest {
        // Given
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = ACCOUNT_RESOURCE.registerEphemeralKey(TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray()).await()
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
        LOCK_OPERATIONS_RESOURCE.updateSecureSettingUnlockDuration(LockOperations.UpdateSecureSettingUnlockDuration(
            baseOperation = baseOperation,
            unlockDuration = updatedUnlockDuration
        )).await()

        // Then
        val lock = LOCK_OPERATIONS_RESOURCE.getSingleLock(TEST_MAIN_LOCK_ID).await()
        assertEquals(updatedUnlockDuration.toDouble(), lock.settings.unlockTime)
    }

    @Test
    fun shouldUpdateSecureSettingUnlockDurationWithContext() = runTest {
        // Given
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = ACCOUNT_RESOURCE.registerEphemeralKey(TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray()).await()
            .certificateChain
            .certificateChainArrayToString()
        val updatedUnlockDuration = 1
        CONTEXT_MANAGER.setOperationContext(
            userId = TEST_MAIN_USER_ID,
            certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChainArray(),
            privateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray()
        )

        // When
        LOCK_OPERATIONS_RESOURCE.updateSecureSettingUnlockDurationWithContext(
            lockId = TEST_MAIN_LOCK_ID,
            unlockDuration = updatedUnlockDuration
        ).await()

        // Then
        val lock = LOCK_OPERATIONS_RESOURCE.getSingleLock(TEST_MAIN_LOCK_ID).await()
        assertEquals(updatedUnlockDuration.toDouble(), lock.settings.unlockTime)
    }

    @Test
    fun shouldUpdateAndRemoveSecureSettingUnlockBetween() = runTest {
        // Given
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = ACCOUNT_RESOURCE.registerEphemeralKey(TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray()).await()
            .certificateChain
            .certificateChainArrayToString()
        val timezone = TimeZone.UTC
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
        val addBaseOperation = LockOperations.BaseOperation(
            userId = TEST_MAIN_USER_ID,
            userCertificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChainArray(),
            userPrivateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray(),
            lockId = TEST_MAIN_LOCK_ID
        )

        // When
        LOCK_OPERATIONS_RESOURCE.updateSecureSettingUnlockBetween(LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = addBaseOperation,
            unlockBetween = updatedUnlockBetween
        )).await()

        // Then
        var lock = LOCK_OPERATIONS_RESOURCE.getSingleLock(TEST_MAIN_LOCK_ID).await()
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
        LOCK_OPERATIONS_RESOURCE.updateSecureSettingUnlockBetween(LockOperations.UpdateSecureSettingUnlockBetween(
            baseOperation = removeBaseOperation,
            unlockBetween = null
        )).await()

        // Then
        lock = LOCK_OPERATIONS_RESOURCE.getSingleLock(TEST_MAIN_LOCK_ID).await()
        assertNull(lock.settings.unlockBetweenWindow)
    }

    @Test
    fun shouldUpdateAndRemoveSecureSettingUnlockBetweenWithContext() = runTest {
        // Given
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val TEST_MAIN_USER_CERTIFICATE_CHAIN = ACCOUNT_RESOURCE.registerEphemeralKey(TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray()).await()
            .certificateChain
            .certificateChainArrayToString()
        val timezone = TimeZone.UTC
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
            certificateChain = TEST_MAIN_USER_CERTIFICATE_CHAIN.stringToCertificateChainArray(),
            privateKey = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray()
        )

        // When
        LOCK_OPERATIONS_RESOURCE.updateSecureSettingUnlockBetweenWithContext(
            lockId = TEST_MAIN_LOCK_ID,
            unlockBetween = updatedUnlockBetween
        ).await()

        // Then
        var lock = LOCK_OPERATIONS_RESOURCE.getSingleLock(TEST_MAIN_LOCK_ID).await()
        assertNotNull(lock.settings.unlockBetweenWindow)
        assertEquals(updatedUnlockBetween.start, lock.settings.unlockBetweenWindow?.start)
        assertEquals(updatedUnlockBetween.end, lock.settings.unlockBetweenWindow?.end)
        assertEquals(updatedUnlockBetween.timezone, lock.settings.unlockBetweenWindow?.timezone)
        assertContains(lock.settings.unlockBetweenWindow!!.days, min.dayOfWeek.name)

        // Given
        LOCK_OPERATIONS_RESOURCE.updateSecureSettingUnlockBetweenWithContext(
            lockId = TEST_MAIN_LOCK_ID,
            unlockBetween = null
        ).await()

        // Then
        lock = LOCK_OPERATIONS_RESOURCE.getSingleLock(TEST_MAIN_LOCK_ID).await()
        assertNull(lock.settings.unlockBetweenWindow)
    }

    @Test
    fun shouldGetLockAuditTrail() = runTest {
        // Given
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val now = Clock.System.now()
        val start = now.minus(7.days).epochSeconds.toInt()
        val end = now.epochSeconds.toInt()

        // When
        val lockAuditTrail = LOCK_OPERATIONS_RESOURCE.getLockAuditTrail(TEST_MAIN_LOCK_ID, start, end).await()

        // Then
        assertTrue { lockAuditTrail.isNotEmpty() }
    }

    @Test
    fun shouldGetAuditForUser() = runTest {
        // Given
        val login = ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).await()
        CONTEXT_MANAGER.setAuthToken(login.authToken)
        val now = Clock.System.now()
        val start = now.minus(7.days).epochSeconds.toInt()
        val end = now.epochSeconds.toInt()

        // When
        val auditForUser = LOCK_OPERATIONS_RESOURCE.getAuditForUser(TEST_MAIN_USER_ID, start, end).await()

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
            LOCK_OPERATIONS_RESOURCE.revokeAccessToLockWithContext("", emptyArray()).await()
        }
        val shareLockWithContextException = assertFails {
            LOCK_OPERATIONS_RESOURCE.shareLockWithContext("", LockOperations.ShareLock(
                targetUserId = "",
                targetUserRole = UserRole.USER,
                targetUserPublicKey = byteArrayOf()
            )).await()
        }
        val unlockWithContextException = assertFails {
            LOCK_OPERATIONS_RESOURCE.unlockWithContext("").await()
        }
        val updateSecureSettingUnlockDurationWithContextException = assertFails {
            LOCK_OPERATIONS_RESOURCE.updateSecureSettingUnlockDurationWithContext("", 0).await()
        }
        val updateSecureSettingUnlockBetweenWithContextException = assertFails {
            LOCK_OPERATIONS_RESOURCE.updateSecureSettingUnlockBetweenWithContext(
                lockId = TEST_MAIN_LOCK_ID,
                unlockBetween = LockOperations.UnlockBetween(
                    start = "",
                    end = "",
                    timezone = "",
                    days = emptyArray(),
                    exceptions = emptyArray()
                )
            ).await()
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
}*/