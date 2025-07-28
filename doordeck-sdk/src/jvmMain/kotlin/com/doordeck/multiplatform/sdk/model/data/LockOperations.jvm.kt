package com.doordeck.multiplatform.sdk.model.data

import com.doordeck.multiplatform.sdk.model.common.DayOfWeek
import com.doordeck.multiplatform.sdk.model.common.UserRole
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.toLocalDateString
import com.doordeck.multiplatform.sdk.util.toLocalTimeString
import com.doordeck.multiplatform.sdk.util.toSeconds
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import java.net.URI
import java.security.PrivateKey
import java.security.PublicKey
import java.security.cert.X509Certificate
import java.time.ZoneId
import java.util.UUID
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

object LockOperations {

    data class TimeRequirement(
        val start: LocalTime,
        val end: LocalTime,
        val timezone: ZoneId,
        val days: List<DayOfWeek>
    ) {
        class Builder {
            private var start: LocalTime? = null
            private var end: LocalTime? = null
            private var timezone: ZoneId? = null
            private var days: List<DayOfWeek>? = null

            fun setStart(start: LocalTime): Builder = apply { this.start = start }
            fun setEnd(end: LocalTime): Builder = apply { this.end = end }
            fun setTimezone(timezone: ZoneId) = apply { this.timezone = timezone }
            fun setDays(days: List<DayOfWeek>) = apply { this.days = days }

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

    data class LocationRequirement @JvmOverloads constructor(
        val latitude: Double,
        val longitude: Double,
        val enabled: Boolean = false,
        val radius: Int = 100,
        val accuracy: Int = 200
    ) {
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

    data class UnlockBetween @JvmOverloads constructor(
        val start: LocalTime,
        val end: LocalTime,
        val timezone: ZoneId,
        val days: List<DayOfWeek>,
        val exceptions: List<LocalDate>? = null
    ) {
        class Builder {
            private var start: LocalTime? = null
            private var end: LocalTime? = null
            private var timezone: ZoneId? = null
            private var days: List<DayOfWeek>? = null
            private var exceptions: List<LocalDate>? = null

            fun setStart(start: LocalTime): Builder = apply { this.start = start }
            fun setEnd(end: LocalTime): Builder = apply { this.end = end }
            fun setTimezone(timezone: ZoneId): Builder = apply { this.timezone = timezone }
            fun setDays(days: List<DayOfWeek>): Builder = apply { this.days = days }
            fun setExceptions(exceptions: List<LocalDate>?): Builder = apply { this.exceptions = exceptions }

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

    data class UnlockOperation @JvmOverloads constructor(
        val baseOperation: BaseOperation,
        val directAccessEndpoints: List<URI>? = null
    ): Operation {
        class Builder {
            private var baseOperation: BaseOperation? = null
            private var directAccessEndpoints: List<URI>? = null

            fun setBaseOperation(baseOperation: BaseOperation): Builder = apply { this.baseOperation = baseOperation }
            fun setDirectAccessEndpoints(directAccessEndpoints: List<URI>?): Builder = apply { this.directAccessEndpoints = directAccessEndpoints }

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

    data class ShareLock @JvmOverloads constructor(
        val targetUserId: UUID,
        val targetUserRole: UserRole,
        val targetUserPublicKey: PublicKey,
        val start: Instant? = null,
        val end: Instant? = null
    ) {
        class Builder {
            private var targetUserId: UUID? = null
            private var targetUserRole: UserRole? = null
            private var targetUserPublicKey: PublicKey? = null
            private var start: Instant? = null
            private var end: Instant? = null

            fun setTargetUserId(targetUserId: UUID): Builder = apply { this.targetUserId = targetUserId }
            fun setTargetUserRole(targetUserRole: UserRole): Builder = apply {this.targetUserRole = targetUserRole }
            fun setTargetUserPublicKey(targetUserPublicKey: PublicKey): Builder = apply { this.targetUserPublicKey = targetUserPublicKey }
            fun setStart(start: Instant?): Builder = apply { this.start = start }
            fun setEnd(end: Instant?): Builder = apply { this.end = end }

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
        val users: List<UUID>
    ): Operation {
        class Builder {
            private var baseOperation: BaseOperation? = null
            private var users: List<UUID>? = null

            fun setBaseOperation(baseOperation: BaseOperation): Builder = apply { this.baseOperation = baseOperation }
            fun setUsers(users: List<UUID>): Builder = apply { this.users = users }

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
        val unlockDuration: Duration
    ): Operation {
        class Builder {
            private var baseOperation: BaseOperation? = null
            private var unlockDuration: Duration? = null

            fun setBaseOperation(baseOperation: BaseOperation): Builder = apply { this.baseOperation = baseOperation }
            fun setUnlockDuration(unlockDuration: Duration): Builder = apply { this.unlockDuration = unlockDuration }

            fun build(): UpdateSecureSettingUnlockDuration {
                return UpdateSecureSettingUnlockDuration(
                    baseOperation = requireNotNull(baseOperation),
                    unlockDuration = requireNotNull(unlockDuration)
                )
            }
        }
    }

    data class UpdateSecureSettingUnlockBetween @JvmOverloads constructor(
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

    data class BaseOperation @JvmOverloads constructor(
        val userId: UUID? = null,
        val userCertificateChain: List<X509Certificate>? = null,
        val userPrivateKey: PrivateKey? = null,
        val lockId: UUID,
        val notBefore: Instant = Clock.System.now(),
        val issuedAt: Instant = Clock.System.now(),
        val expiresAt: Instant = Clock.System.now().plus(1.minutes),
        val jti: UUID = UUID.randomUUID()
    ) {
        class Builder {
            private var userId: UUID? = null
            private var userCertificateChain: List<X509Certificate>? = null
            private var userPrivateKey: PrivateKey? = null
            private var lockId: UUID? = null
            private var notBefore: Instant = Clock.System.now()
            private var issuedAt: Instant = Clock.System.now()
            private var expiresAt: Instant = Clock.System.now().plus(1.minutes)
            private var jti: UUID = UUID.randomUUID()

            fun setUserId(userId: UUID?): Builder = apply { this.userId = userId }
            fun setUserCertificateChain(userCertificateChain: List<X509Certificate>?): Builder = apply { this.userCertificateChain = userCertificateChain }
            fun setUserPrivateKey(userPrivateKey: PrivateKey?): Builder = apply { this.userPrivateKey = userPrivateKey }
            fun setLockId(lockId: UUID): Builder = apply { this.lockId = lockId }
            fun setNotBefore(notBefore: Instant): Builder = apply { this.notBefore = notBefore }
            fun setIssuedAt(issuedAt: Instant): Builder = apply { this.issuedAt = issuedAt }
            fun setExpiresAt(expiresAt: Instant): Builder = apply { this.expiresAt = expiresAt }
            fun setJti(jti: UUID): Builder = apply { this.jti = jti }

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
        start = requirement.start.toLocalTimeString(),
        end = requirement.end.toLocalTimeString(),
        timezone = requirement.timezone.id,
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
        start = start.toLocalTimeString(),
        end = end.toLocalTimeString(),
        timezone = timezone.id,
        days = days,
        exceptions = exceptions?.map { it.toLocalDateString() }
    )
}

internal fun LockOperations.UnlockOperation.toBasicUnlockOperation(): BasicUnlockOperation {
    return BasicUnlockOperation(
        baseOperation = baseOperation.toBasicBaseOperation(),
        directAccessEndpoints = directAccessEndpoints?.map { it.toString() }
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
        targetUserId = targetUserId.toString(),
        targetUserRole = targetUserRole,
        targetUserPublicKey = targetUserPublicKey.encoded,
        start = start?.epochSeconds,
        end = end?.epochSeconds
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
        users = users.map { it.toString() }
    )
}

internal fun LockOperations.UpdateSecureSettingUnlockDuration.toBasicUpdateSecureSettingUnlockDuration(): BasicUpdateSecureSettingUnlockDuration {
    return BasicUpdateSecureSettingUnlockDuration(
        baseOperation = baseOperation.toBasicBaseOperation(),
        unlockDuration = unlockDuration.toSeconds()
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
        userId = userId?.toString(),
        userCertificateChain = userCertificateChain?.map { it.encoded.encodeByteArrayToBase64() },
        userPrivateKey = userPrivateKey?.encoded,
        lockId = lockId.toString(),
        notBefore = notBefore.epochSeconds,
        issuedAt = issuedAt.epochSeconds,
        expiresAt = expiresAt.epochSeconds,
        jti = jti.toString()
    )
}