package com.doordeck.multiplatform.sdk.data

import com.doordeck.multiplatform.sdk.model.data.LockOperations
import com.doordeck.multiplatform.sdk.randomBaseOperation
import com.doordeck.multiplatform.sdk.randomBatchShareLockOperation
import com.doordeck.multiplatform.sdk.randomLocationRequirement
import com.doordeck.multiplatform.sdk.randomRevokeAccessToLockOperation
import com.doordeck.multiplatform.sdk.randomShareLock
import com.doordeck.multiplatform.sdk.randomShareLockOperation
import com.doordeck.multiplatform.sdk.randomTimeRequirement
import com.doordeck.multiplatform.sdk.randomUnlockBetween
import com.doordeck.multiplatform.sdk.randomUnlockOperation
import com.doordeck.multiplatform.sdk.randomUpdateSecureSettingUnlockDuration
import com.doordeck.multiplatform.sdk.randomUpdateSecureSettingUnlockBetween
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class LockOperationsTest {

    @Test
    fun shouldBuildTimeRequirement() = runTest {
        // Given
        val timeRequirement = randomTimeRequirement()

        // When
        val result = LockOperations.TimeRequirement.Builder()
            .setStart(timeRequirement.start)
            .setEnd(timeRequirement.end)
            .setTimezone(timeRequirement.timezone)
            .setDays(timeRequirement.days)
            .build()

        // Then
        assertEquals(timeRequirement, result)
    }

    @Test
    fun shouldBuildLocationRequirement() = runTest {
        // Given
        val locationRequirement = randomLocationRequirement()

        // When
        val result = LockOperations.LocationRequirement.Builder()
            .setLatitude(locationRequirement.latitude)
            .setLongitude(locationRequirement.longitude)
            .setEnabled(locationRequirement.enabled)
            .setRadius(locationRequirement.radius)
            .setAccuracy(locationRequirement.accuracy)
            .build()

        // Then
        assertEquals(locationRequirement, result)
    }

    @Test
    fun shouldBuildUnlockBetween() = runTest {
        // Given
        val unlockBetween = randomUnlockBetween()

        // When
        val result = LockOperations.UnlockBetween.Builder()
            .setStart(unlockBetween.start)
            .setEnd(unlockBetween.end)
            .setTimezone(unlockBetween.timezone)
            .setDays(unlockBetween.days)
            .setExceptions(unlockBetween.exceptions)
            .build()

        // Then
        assertEquals(unlockBetween, result)
    }

    @Test
    fun shouldBuildUnlockOperation() = runTest {
        // Given
        val unlockOperation = randomUnlockOperation()

        // When
        val result = LockOperations.UnlockOperation.Builder()
            .setBaseOperation(unlockOperation.baseOperation)
            .setDirectAccessEndpoints(unlockOperation.directAccessEndpoints)
            .build()

        // Then
        assertEquals(unlockOperation, result)
    }

    @Test
    fun shouldBuildShareLockOperation() = runTest {
        // Given
        val shareLockShareLockOperation = randomShareLockOperation()

        // When
        val result = LockOperations.ShareLockOperation.Builder()
            .setBaseOperation(shareLockShareLockOperation.baseOperation)
            .setShareLock(shareLockShareLockOperation.shareLock)
            .build()

        // When
        assertEquals(shareLockShareLockOperation, result)
    }

    @Test
    fun shouldBuildBatchShareLockOperation() = runTest {
        // Given
        val batchShareLockOperation = randomBatchShareLockOperation()

        // When
        val result = LockOperations.BatchShareLockOperation.Builder()
            .setBaseOperation(batchShareLockOperation.baseOperation)
            .setUsers(batchShareLockOperation.users)
            .build()

        // Then
        assertEquals(batchShareLockOperation, result)
    }

    @Test
    fun shouldBuildShareLock() = runTest {
        // Given
        val shareLock = randomShareLock()

        // When
        val result = LockOperations.ShareLock.Builder()
            .setTargetUserId(shareLock.targetUserId)
            .setTargetUserRole(shareLock.targetUserRole)
            .setTargetUserPublicKey(shareLock.targetUserPublicKey)
            .setStart(shareLock.start)
            .setEnd(shareLock.end)
            .build()

        // Then
        assertEquals(shareLock, result)
    }

    @Test
    fun shouldBuildRevokeAccessToLockOperation() = runTest {
        // Given
        val revokeAccessToLockOperation = randomRevokeAccessToLockOperation()

        // When
        val result = LockOperations.RevokeAccessToLockOperation.Builder()
            .setBaseOperation(revokeAccessToLockOperation.baseOperation)
            .setUsers(revokeAccessToLockOperation.users)
            .build()

        // Then
        assertEquals(revokeAccessToLockOperation, result)
    }

    @Test
    fun shouldBuildUpdateSecureSettingUnlockDuration() = runTest {
        // Given
        val updateSecureSettingUnlockDuration = randomUpdateSecureSettingUnlockDuration()

        // Then
        val result = LockOperations.UpdateSecureSettingUnlockDuration.Builder()
            .setBaseOperation(updateSecureSettingUnlockDuration.baseOperation)
            .setUnlockDuration(updateSecureSettingUnlockDuration.unlockDuration)
            .build()

        // Then
        assertEquals(updateSecureSettingUnlockDuration, result)
    }

    @Test
    fun shouldBuildUpdateSecureSettingUnlockBetween() = runTest {
        // Given
        val updateSecureSettingUnlockBetween = randomUpdateSecureSettingUnlockBetween()

        // Then
        val result = LockOperations.UpdateSecureSettingUnlockBetween.Builder()
            .setBaseOperation(updateSecureSettingUnlockBetween.baseOperation)
            .setUnlockBetween(updateSecureSettingUnlockBetween.unlockBetween)
            .build()

        // Then
        assertEquals(updateSecureSettingUnlockBetween, result)
    }

    @Test
    fun shouldBuildBaseOperation() = runTest {
        // Given
        val baseOperation = randomBaseOperation()

        // When
        val result = LockOperations.BaseOperation.Builder()
            .setUserId(baseOperation.userId)
            .setUserCertificateChain(baseOperation.userCertificateChain)
            .setUserPrivateKey(baseOperation.userPrivateKey)
            .setLockId(baseOperation.lockId)
            .setNotBefore(baseOperation.notBefore)
            .setIssuedAt(baseOperation.issuedAt)
            .setExpiresAt(baseOperation.expiresAt)
            .setJti(baseOperation.jti)
            .build()

        // Then
        assertEquals(baseOperation, result)
    }
}