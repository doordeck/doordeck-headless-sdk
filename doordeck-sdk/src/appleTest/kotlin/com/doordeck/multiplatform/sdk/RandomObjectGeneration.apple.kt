package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.PlatformTestConstants.PLATFORM_TEST_VALID_CERTIFICATE
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.model.common.DayOfWeek
import com.doordeck.multiplatform.sdk.model.common.UserRole
import com.doordeck.multiplatform.sdk.model.data.LockOperations
import com.doordeck.multiplatform.sdk.model.data.LockOperations.ShareLock
import com.doordeck.multiplatform.sdk.model.data.PlatformOperations
import com.doordeck.multiplatform.sdk.util.toNSURLComponents
import com.doordeck.multiplatform.sdk.util.toNsUuid
import platform.Foundation.NSURLComponents
import platform.Foundation.NSUUID

internal fun randomUuid(): NSUUID = randomUuidString().toNsUuid()

internal fun randomUri(): NSURLComponents = randomUrlString().toNSURLComponents()

internal fun randomBaseOperation() = LockOperations.BaseOperation(
    userId = randomNullable { randomUuid() },
    userCertificateChain = randomNullable { listOf(PLATFORM_TEST_VALID_CERTIFICATE) },
    userPrivateKey = randomNullable { CryptoManager.generateKeyPair().private },
    lockId = randomUuid(),
    notBefore = randomLong(),
    issuedAt = randomLong(),
    expiresAt = randomLong(),
    jti = randomUuid()
)

internal fun randomTimeRequirement() = LockOperations.TimeRequirement(
    start = randomString(),
    end = randomString(),
    timezone = randomString(),
    days = DayOfWeek.entries.shuffled().take(3).toSet()
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
    days = DayOfWeek.entries.shuffled().take(3).toSet(),
    exceptions = (1..3).map { randomString() }
)

internal fun randomRevokeAccessToLockOperation() = LockOperations.RevokeAccessToLockOperation(
    baseOperation = randomBaseOperation(),
    users = (1..3).map { randomUuid() }
)

internal fun randomShareLock() = ShareLock(
    targetUserId = randomUuid(),
    targetUserRole = UserRole.entries.random(),
    targetUserPublicKey = CryptoManager.generateKeyPair().public,
    start = randomNullable { randomInt() },
    end = randomNullable { randomInt() },
)

internal fun randomBatchShareLockOperation() = LockOperations.BatchShareLockOperation(
    baseOperation = randomBaseOperation(),
    users = (1..3).map { randomShareLock() }
)

internal fun randomUnlockOperation() = LockOperations.UnlockOperation(
    baseOperation = randomBaseOperation(),
    directAccessEndpoints = (1..3).map { randomUrlString() }
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