package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_VALID_CERTIFICATE
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.model.common.DayOfWeek
import com.doordeck.multiplatform.sdk.model.common.UserRole
import com.doordeck.multiplatform.sdk.model.data.LockOperations
import com.doordeck.multiplatform.sdk.model.data.LockOperations.ShareLock
import com.doordeck.multiplatform.sdk.model.data.PlatformOperations
import com.doordeck.multiplatform.sdk.util.toJsArray

internal fun randomBaseOperation() = LockOperations.BaseOperation(
    userId = randomNullable { randomUuidString() },
    userCertificateChain = randomNullable { jsArrayOf(PLATFORM_TEST_VALID_CERTIFICATE) },
    userPrivateKey = randomNullable { CryptoManager.generateKeyPair().private },
    lockId = randomUuidString(),
    notBefore = randomInt(),
    issuedAt = randomInt(),
    expiresAt = randomInt(),
    jti = randomUuidString()
)

internal fun randomTimeRequirement() = LockOperations.TimeRequirement(
    start = randomString(),
    end = randomString(),
    timezone = randomString(),
    days = DayOfWeek.entries.shuffled().take(3).map { it.name }.toJsArray()
)

internal fun randomLocationRequirement() = LockOperations.LocationRequirement(
    latitude = randomDouble(-90.0, 90.0),
    longitude = randomDouble(-180.0, 180.0),
    enabled = randomBoolean(),
    radius = randomInt(1, 1000),
    accuracy = randomInt(1, 1000)
)

internal fun randomUnlockBetween() = LockOperations.UnlockBetween(
    start = randomString(),
    end = randomString(),
    timezone = randomString(),
    days = DayOfWeek.entries.shuffled().take(3).map { it.name }.toJsArray(),
    exceptions = (1..3).map { randomString() }.toJsArray()
)

internal fun randomRevokeAccessToLockOperation() = LockOperations.RevokeAccessToLockOperation(
    baseOperation = randomBaseOperation(),
    users = (1..3).map { randomUuidString() }.toJsArray()
)

internal fun randomShareLock() = ShareLock(
    targetUserId = randomUuidString(),
    targetUserRole = UserRole.entries.random(),
    targetUserPublicKey = CryptoManager.generateKeyPair().public,
    start = randomNullable { randomInt() },
    end = randomNullable { randomInt() },
)

internal fun randomBatchShareLockOperation() = LockOperations.BatchShareLockOperation(
    baseOperation = randomBaseOperation(),
    users = (1..3).map { randomShareLock() }.toJsArray()
)

internal fun randomUnlockOperation() = LockOperations.UnlockOperation(
    baseOperation = randomBaseOperation(),
    directAccessEndpoints = (1..3).map { randomUrlString() }.toJsArray()
)

internal fun randomShareLockOperation() = LockOperations.ShareLockOperation(
    baseOperation = randomBaseOperation(),
    shareLock = randomShareLock()
)

internal fun randomUpdateSecureSettingUnlockDuration() = LockOperations.UpdateSecureSettingUnlockDuration(
    baseOperation = randomBaseOperation(),
    unlockDuration = randomInt(1, 10)
)

internal fun randomUpdateSecureSettingUnlockBetween() = LockOperations.UpdateSecureSettingUnlockBetween(
    baseOperation = randomBaseOperation(),
    unlockBetween = randomUnlockBetween()
)

internal fun randomCreateApplication() = PlatformOperations.CreateApplication(
    name = randomString(),
    companyName = randomString(),
    mailingAddress = randomString(),
    privacyPolicy = randomNullable { randomUrlString() },
    supportContact = randomNullable { randomUrlString() },
    appLink = randomNullable { randomUrlString() },
    emailPreferences = randomNullable { randomEmailPreferences() },
    logoUrl = randomNullable { randomUrlString() }
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
    actionTarget = randomUrlString(),
    headline = randomString(),
    actionText = randomString()
)