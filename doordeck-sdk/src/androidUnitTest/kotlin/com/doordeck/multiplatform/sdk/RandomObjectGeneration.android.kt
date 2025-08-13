package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_VALID_CERTIFICATE
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.model.common.UserRole
import com.doordeck.multiplatform.sdk.model.data.LockOperations
import com.doordeck.multiplatform.sdk.model.data.LockOperations.ShareLock
import com.doordeck.multiplatform.sdk.model.data.PlatformOperations
import com.doordeck.multiplatform.sdk.util.now
import com.doordeck.multiplatform.sdk.util.toEnumSet
import com.doordeck.multiplatform.sdk.util.toUuid
import java.net.URI
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.util.UUID

internal fun randomUuid(): UUID = randomUuidString().toUuid()

internal fun randomUri(): URI = URI.create(randomUrlString())

internal fun randomBaseOperation() = LockOperations.BaseOperation(
    userId = randomNullable { randomUuid() },
    userCertificateChain = randomNullable { listOf(PLATFORM_TEST_VALID_CERTIFICATE) },
    userPrivateKey = randomNullable { CryptoManager.generateKeyPair().private },
    lockId = randomUuid(),
    notBefore = now(),
    issuedAt = now(),
    expiresAt = now(),
    jti = randomUuid()
)

internal fun randomTimeRequirement() = LockOperations.TimeRequirement(
    start = LocalTime.now(),
    end = LocalTime.now(),
    timezone = ZoneId.of("UTC"),
    days = java.time.DayOfWeek.entries.shuffled().take(3).toEnumSet()
)

internal fun randomLocationRequirement() = LockOperations.LocationRequirement(
    latitude = randomDouble(-90.0, 90.0),
    longitude = randomDouble(-180.0, 180.0),
    enabled = randomBoolean(),
    radius = randomInt(1, 1000),
    accuracy = randomInt(1, 1000)
)

internal fun randomUnlockBetween() = LockOperations.UnlockBetween(
    start = LocalTime.now(),
    end = LocalTime.now(),
    timezone = ZoneId.of("UTC"),
    days = java.time.DayOfWeek.entries.shuffled().take(3).toEnumSet(),
    exceptions = (1..3).map { LocalDate.now().plusDays(it.toLong()) }
)

internal fun randomRevokeAccessToLockOperation() = LockOperations.RevokeAccessToLockOperation(
    baseOperation = randomBaseOperation(),
    users = (1..3).map { randomUuid() }
)

internal fun randomShareLock() = ShareLock(
    targetUserId = randomUuid(),
    targetUserRole = UserRole.entries.random(),
    targetUserPublicKey = CryptoManager.generateKeyPair().public,
    start = randomNullable { now() },
    end = randomNullable { now() }
)

internal fun randomBatchShareLockOperation() = LockOperations.BatchShareLockOperation(
    baseOperation = randomBaseOperation(),
    users = (1..3).map { randomShareLock() }
)

internal fun randomUnlockOperation() = LockOperations.UnlockOperation(
    baseOperation = randomBaseOperation(),
    directAccessEndpoints = (1..3).map { randomUri() }
)

internal fun randomShareLockOperation() = LockOperations.ShareLockOperation(
    baseOperation = randomBaseOperation(),
    shareLock = randomShareLock()
)

internal fun randomUpdateSecureSettingUnlockDuration() = LockOperations.UpdateSecureSettingUnlockDuration(
    baseOperation = randomBaseOperation(),
    unlockDuration = Duration.ofMinutes(randomLong(1, 10))
)

internal fun randomUpdateSecureSettingUnlockBetween() = LockOperations.UpdateSecureSettingUnlockBetween(
    baseOperation = randomBaseOperation(),
    unlockBetween = randomUnlockBetween()
)

internal fun randomCreateApplication() = PlatformOperations.CreateApplication(
    name = randomString(),
    companyName = randomString(),
    mailingAddress = randomString(),
    privacyPolicy = randomNullable { randomUri() },
    supportContact = randomNullable { randomUri() },
    appLink = randomNullable { randomUri() },
    emailPreferences = randomNullable { randomEmailPreferences() },
    logoUrl = randomNullable { randomUri() }
)

internal fun randomEmailPreferences() = PlatformOperations.EmailPreferences(
    senderEmail = randomNullable { randomString() },
    senderName = randomNullable { randomString() },
    primaryColour = randomNullable { randomString() },
    secondaryColour = randomNullable { randomString() },
    onlySendEssentialEmails = randomNullable { randomBoolean() },
    callToAction = randomNullable { randomEmailCallToAction() }
)

internal fun randomEmailCallToAction() = PlatformOperations.EmailCallToAction(
    actionTarget = randomUri(),
    headline = randomString(),
    actionText = randomString()
)