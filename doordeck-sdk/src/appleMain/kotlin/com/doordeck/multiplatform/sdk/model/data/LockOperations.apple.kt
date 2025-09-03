package com.doordeck.multiplatform.sdk.model.data

import com.doordeck.multiplatform.sdk.model.common.DayOfWeek
import com.doordeck.multiplatform.sdk.model.common.UserRole
import com.doordeck.multiplatform.sdk.util.totoLocalTimeString
import com.doordeck.multiplatform.sdk.util.validateAccuracy
import com.doordeck.multiplatform.sdk.util.validateLatitude
import com.doordeck.multiplatform.sdk.util.validateLongitude
import com.doordeck.multiplatform.sdk.util.validateRadius
import platform.Foundation.NSDate
import platform.Foundation.NSTimeZone
import platform.Foundation.NSUUID
import kotlin.time.Clock
import kotlin.time.Duration.Companion.minutes

object LockOperations {

    data class TimeRequirement(
        val start: NSDate, // HH:mm
        val end: NSDate, // HH:mm
        val timezone: NSTimeZone,
        val days: Set<DayOfWeek>
    ) {
        class Builder {
            private var start: NSDate? = null
            private var end: NSDate? = null
            private var timezone: NSTimeZone? = null
            private var days: Set<DayOfWeek>? = null

            fun setStart(start: NSDate): Builder = apply { this.start = start }
            fun setEnd(end: NSDate): Builder = apply { this.end = end }
            fun setTimezone(timezone: NSTimeZone) = apply { this.timezone = timezone }
            fun setDays(days: Set<DayOfWeek>) = apply { this.days = days }

            fun build(): TimeRequirement {
                return TimeRequirement(
                    start = requireNotNull(start),
                    end = requireNotNull(end),
                    timezone = requireNotNull(timezone),
                    days = requireNotNull(days)
                )
            }
        }
    }

    data class LocationRequirement(
        val latitude: Double,
        val longitude: Double,
        val enabled: Boolean = false,
        val radius: Int = 100,
        val accuracy: Int = 200
    ) {
        init {
            latitude.validateLatitude()
            longitude.validateLongitude()
            radius.validateRadius()
            accuracy.validateAccuracy()
        }

        class Builder {
            private var latitude: Double? = null
            private var longitude: Double? = null
            private var enabled: Boolean = false
            private var radius: Int = 100
            private var accuracy: Int = 200

            fun setLatitude(latitude: Double): Builder = apply { this.latitude = latitude }
            fun setLongitude(longitude: Double): Builder = apply { this.longitude = longitude }
            fun setEnabled(enabled: Boolean): Builder = apply { this.enabled = enabled }
            fun setRadius(radius: Int): Builder = apply { this.radius = radius }
            fun setAccuracy(accuracy: Int): Builder = apply { this.accuracy = accuracy }

            fun build(): LocationRequirement {
                return LocationRequirement(
                    latitude = requireNotNull(latitude),
                    longitude = requireNotNull(longitude),
                    enabled = enabled,
                    radius = radius,
                    accuracy = accuracy
                )
            }
        }
    }

    data class UnlockBetween(
        val start: NSDate, // HH:mm
        val end: NSDate, // HH:mm
        val timezone: NSTimeZone,
        val days: Set<DayOfWeek>,
        val exceptions: List<String>? = null
    ) {
        class Builder {
            private var start: NSDate? = null
            private var end: NSDate? = null
            private var timezone: NSTimeZone? = null
            private var days: Set<DayOfWeek>? = null
            private var exceptions: List<String>? = null

            fun setStart(start: NSDate): Builder = apply { this.start = start }
            fun setEnd(end: NSDate): Builder = apply { this.end = end }
            fun setTimezone(timezone: NSTimeZone): Builder = apply { this.timezone = timezone }
            fun setDays(days: Set<DayOfWeek>): Builder = apply { this.days = days }
            fun setExceptions(exceptions: List<String>?): Builder = apply { this.exceptions = exceptions }

            fun build(): UnlockBetween {
                return UnlockBetween(
                    start = requireNotNull(start),
                    end = requireNotNull(end),
                    timezone = requireNotNull(timezone),
                    days = requireNotNull(days),
                    exceptions = exceptions
                )
            }
        }
    }

    data class UnlockOperation(
        val baseOperation: BaseOperation,
        val directAccessEndpoints: List<String>? = null
    ): Operation {
        class Builder {
            private var baseOperation: BaseOperation? = null
            private var directAccessEndpoints: List<String>? = null

            fun setBaseOperation(baseOperation: BaseOperation): Builder = apply { this.baseOperation = baseOperation }
            fun setDirectAccessEndpoints(directAccessEndpoints: List<String>?): Builder = apply { this.directAccessEndpoints = directAccessEndpoints }

            fun build(): UnlockOperation {
                return UnlockOperation(
                    baseOperation = requireNotNull(baseOperation),
                    directAccessEndpoints = directAccessEndpoints
                )
            }
        }
    }

    data class ShareLockOperation(
        val baseOperation: BaseOperation,
        val shareLock: ShareLock
    ): Operation {
        class Builder {
            private var baseOperation: BaseOperation? = null
            private var shareLock: ShareLock? = null

            fun setBaseOperation(baseOperation: BaseOperation): Builder = apply { this.baseOperation = baseOperation }
            fun setShareLock(shareLock: ShareLock): Builder = apply { this.shareLock = shareLock }

            fun build(): ShareLockOperation {
                return ShareLockOperation(
                    baseOperation = requireNotNull(baseOperation),
                    shareLock = requireNotNull(shareLock)
                )
            }
        }
    }

    data class ShareLock(
        val targetUserId: NSUUID,
        val targetUserRole: UserRole,
        val targetUserPublicKey: ByteArray,
        val start: Int? = null,
        val end: Int? = null
    ) {
        class Builder {
            private var targetUserId: NSUUID? = null
            private var targetUserRole: UserRole? = null
            private var targetUserPublicKey: ByteArray? = null
            private var start: Int? = null
            private var end: Int? = null

            fun setTargetUserId(targetUserId: NSUUID): Builder = apply { this.targetUserId = targetUserId }
            fun setTargetUserRole(targetUserRole: UserRole): Builder = apply {this.targetUserRole = targetUserRole }
            fun setTargetUserPublicKey(targetUserPublicKey: ByteArray): Builder = apply { this.targetUserPublicKey = targetUserPublicKey }
            fun setStart(start: Int?): Builder = apply { this.start = start }
            fun setEnd(end: Int?): Builder = apply { this.end = end }

            fun build(): ShareLock {
                return ShareLock(
                    targetUserId = requireNotNull(targetUserId),
                    targetUserRole = requireNotNull(targetUserRole),
                    targetUserPublicKey = requireNotNull(targetUserPublicKey),
                    start = start,
                    end = end
                )
            }
        }
    }

    data class BatchShareLockOperation(
        val baseOperation: BaseOperation,
        val users: List<ShareLock>
    ): Operation {
        class Builder {
            private var baseOperation: BaseOperation? = null
            private var users: List<ShareLock>? = null

            fun setBaseOperation(baseOperation: BaseOperation): Builder = apply { this.baseOperation = baseOperation }
            fun setUsers(users: List<ShareLock>): Builder = apply { this.users = users }

            fun build(): BatchShareLockOperation {
                return BatchShareLockOperation(
                    baseOperation = requireNotNull(baseOperation),
                    users = requireNotNull(users)
                )
            }
        }
    }

    data class RevokeAccessToLockOperation(
        val baseOperation: BaseOperation,
        val users: List<NSUUID>
    ): Operation {
        class Builder {
            private var baseOperation: BaseOperation? = null
            private var users: List<NSUUID>? = null

            fun setBaseOperation(baseOperation: BaseOperation): Builder = apply { this.baseOperation = baseOperation }
            fun setUsers(users: List<NSUUID>): Builder = apply { this.users = users }

            fun build(): RevokeAccessToLockOperation {
                return RevokeAccessToLockOperation(
                    baseOperation = requireNotNull(baseOperation),
                    users = requireNotNull(users)
                )
            }
        }
    }

    data class UpdateSecureSettingUnlockDuration(
        val baseOperation: BaseOperation,
        val unlockDuration: Int
    ): Operation {
        class Builder {
            private var baseOperation: BaseOperation? = null
            private var unlockDuration: Int? = null

            fun setBaseOperation(baseOperation: BaseOperation): Builder = apply { this.baseOperation = baseOperation }
            fun setUnlockDuration(unlockDuration: Int): Builder = apply { this.unlockDuration = unlockDuration }

            fun build(): UpdateSecureSettingUnlockDuration {
                return UpdateSecureSettingUnlockDuration(
                    baseOperation = requireNotNull(baseOperation),
                    unlockDuration = requireNotNull(unlockDuration)
                )
            }
        }
    }

    data class UpdateSecureSettingUnlockBetween(
        val baseOperation: BaseOperation,
        val unlockBetween: UnlockBetween? = null
    ): Operation {
        class Builder {
            private var baseOperation: BaseOperation? = null
            private var unlockBetween: UnlockBetween? = null

            fun setBaseOperation(baseOperation: BaseOperation): Builder = apply { this.baseOperation = baseOperation }
            fun setUnlockBetween(unlockBetween: UnlockBetween?): Builder = apply { this.unlockBetween = unlockBetween }

            fun build(): UpdateSecureSettingUnlockBetween {
                return UpdateSecureSettingUnlockBetween(
                    baseOperation = requireNotNull(baseOperation),
                    unlockBetween = unlockBetween
                )
            }
        }
    }

    data class BaseOperation(
        val userId: NSUUID? = null,
        val userCertificateChain: List<String>? = null,
        val userPrivateKey: ByteArray? = null,
        val lockId: NSUUID,
        val notBefore: Long = Clock.System.now().epochSeconds,
        val issuedAt: Long = Clock.System.now().epochSeconds,
        val expiresAt: Long = (Clock.System.now() + 1.minutes).epochSeconds,
        val jti: NSUUID = NSUUID.UUID()
    ) {
        class Builder {
            private var userId: NSUUID? = null
            private var userCertificateChain: List<String>? = null
            private var userPrivateKey: ByteArray? = null
            private var lockId: NSUUID? = null
            private var notBefore: Long = Clock.System.now().epochSeconds
            private var issuedAt: Long = Clock.System.now().epochSeconds
            private var expiresAt: Long = (Clock.System.now() + 1.minutes).epochSeconds
            private var jti: NSUUID = NSUUID.UUID()

            fun setUserId(userId: NSUUID?): Builder = apply { this.userId = userId }
            fun setUserCertificateChain(userCertificateChain: List<String>?): Builder = apply { this.userCertificateChain = userCertificateChain }
            fun setUserPrivateKey(userPrivateKey: ByteArray?): Builder = apply { this.userPrivateKey = userPrivateKey }
            fun setLockId(lockId: NSUUID): Builder = apply { this.lockId = lockId }
            fun setNotBefore(notBefore: Long): Builder = apply { this.notBefore = notBefore }
            fun setIssuedAt(issuedAt: Long): Builder = apply { this.issuedAt = issuedAt }
            fun setExpiresAt(expiresAt: Long): Builder = apply { this.expiresAt = expiresAt }
            fun setJti(jti: NSUUID): Builder = apply { this.jti = jti }

            fun build(): BaseOperation {
                return BaseOperation(
                    userId = userId,
                    userCertificateChain = userCertificateChain,
                    userPrivateKey = userPrivateKey,
                    lockId = requireNotNull(lockId),
                    notBefore = notBefore,
                    issuedAt = issuedAt,
                    expiresAt = expiresAt,
                    jti = jti
                )
            }
        }
    }

    sealed interface Operation
}

internal fun List<LockOperations.TimeRequirement>.toBasicTimeRequirement(): List<BasicTimeRequirement> = map { requirement ->
    BasicTimeRequirement(
        start = requirement.start.totoLocalTimeString(),
        end = requirement.end.totoLocalTimeString(),
        timezone = requirement.timezone.name,
        days = requirement.days
    )
}

internal fun LockOperations.LocationRequirement.toBasicLocationRequirement(): BasicLocationRequirement {
    return BasicLocationRequirement(
        latitude = latitude,
        longitude = longitude,
        enabled = enabled,
        radius = radius,
        accuracy = accuracy
    )
}

internal fun LockOperations.UnlockBetween.toBasicUnlockBetween(): BasicUnlockBetween {
    return BasicUnlockBetween(
        start = start.totoLocalTimeString(),
        end = end.totoLocalTimeString(),
        timezone = timezone.name,
        days = days,
        exceptions = exceptions
    )
}

internal fun LockOperations.UnlockOperation.toBasicUnlockOperation(): BasicUnlockOperation {
    return BasicUnlockOperation(
        baseOperation = baseOperation.toBasicBaseOperation(),
        directAccessEndpoints = directAccessEndpoints
    )
}

internal fun LockOperations.ShareLockOperation.toBasicShareLockOperation(): BasicShareLockOperation {
    return BasicShareLockOperation(
        baseOperation = baseOperation.toBasicBaseOperation(),
        shareLock = shareLock.toBasicShareLock()
    )
}

internal fun LockOperations.ShareLock.toBasicShareLock(): BasicShareLock {
    return BasicShareLock(
        targetUserId = targetUserId.UUIDString,
        targetUserRole = targetUserRole,
        targetUserPublicKey = targetUserPublicKey,
        start = start?.toLong(),
        end = end?.toLong()
    )
}

internal fun LockOperations.BatchShareLockOperation.toBasicBatchShareLockOperation(): BasicBatchShareLockOperation {
    return BasicBatchShareLockOperation(
        baseOperation = baseOperation.toBasicBaseOperation(),
        users = users.map { it.toBasicShareLock() }
    )
}

internal fun LockOperations.RevokeAccessToLockOperation.toBasicRevokeAccessToLockOperation(): BasicRevokeAccessToLockOperation {
    return BasicRevokeAccessToLockOperation(
        baseOperation = baseOperation.toBasicBaseOperation(),
        users = users.map { it.UUIDString }
    )
}

internal fun LockOperations.UpdateSecureSettingUnlockDuration.toBasicUpdateSecureSettingUnlockDuration(): BasicUpdateSecureSettingUnlockDuration {
    return BasicUpdateSecureSettingUnlockDuration(
        baseOperation = baseOperation.toBasicBaseOperation(),
        unlockDuration = unlockDuration
    )
}

internal fun LockOperations.UpdateSecureSettingUnlockBetween.toBasicUpdateSecureSettingUnlockBetween(): BasicUpdateSecureSettingUnlockBetween {
    return BasicUpdateSecureSettingUnlockBetween(
        baseOperation = baseOperation.toBasicBaseOperation(),
        unlockBetween = unlockBetween?.toBasicUnlockBetween()
    )
}

internal fun LockOperations.BaseOperation.toBasicBaseOperation(): BasicBaseOperation {
    return BasicBaseOperation(
        userId = userId?.UUIDString,
        userCertificateChain = userCertificateChain,
        userPrivateKey = userPrivateKey,
        lockId = lockId.UUIDString,
        notBefore = notBefore,
        issuedAt = issuedAt,
        expiresAt = expiresAt,
        jti = jti.UUIDString
    )
}