package com.doordeck.multiplatform.sdk.model.data

import com.doordeck.multiplatform.sdk.model.common.DayOfWeek
import com.doordeck.multiplatform.sdk.model.common.UserRole
import com.doordeck.multiplatform.sdk.util.validateAccuracy
import com.doordeck.multiplatform.sdk.util.validateLatitude
import com.doordeck.multiplatform.sdk.util.validateLongitude
import com.doordeck.multiplatform.sdk.util.validateRadius
import kotlin.time.Clock
import kotlin.js.collections.JsArray
import kotlin.js.collections.JsSet
import kotlin.js.collections.toList
import kotlin.js.collections.toSet
import kotlin.time.Duration.Companion.minutes
import kotlin.uuid.Uuid

@JsExport
object LockOperations {

    data class TimeRequirement(
        val start: String, // HH:mm
        val end: String, // HH:mm
        val timezone: String,
        val days: JsSet<DayOfWeek>
    ) {
        class Builder {
            private var start: String? = null
            private var end: String? = null
            private var timezone: String? = null
            private var days: JsSet<DayOfWeek>? = null

            fun setStart(start: String): Builder = apply { this.start = start }
            fun setEnd(end: String): Builder = apply { this.end = end }
            fun setTimezone(timezone: String) = apply { this.timezone = timezone }
            fun setDays(days: JsSet<DayOfWeek>) = apply { this.days = days }

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
        val start: String, // HH:mm
        val end: String, // HH:mm
        val timezone: String,
        val days: JsSet<DayOfWeek>,
        val exceptions: JsArray<String>? = null
    ) {
        class Builder {
            private var start: String? = null
            private var end: String? = null
            private var timezone: String? = null
            private var days: JsSet<DayOfWeek>? = null
            private var exceptions: JsArray<String>? = null

            fun setStart(start: String): Builder = apply { this.start = start }
            fun setEnd(end: String): Builder = apply { this.end = end }
            fun setTimezone(timezone: String): Builder = apply { this.timezone = timezone }
            fun setDays(days: JsSet<DayOfWeek>): Builder = apply { this.days = days }
            fun setExceptions(exceptions: JsArray<String>?): Builder = apply { this.exceptions = exceptions }

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
        val directAccessEndpoints: JsArray<String>? = null
    ): Operation {
        class Builder {
            private var baseOperation: BaseOperation? = null
            private var directAccessEndpoints: JsArray<String>? = null

            fun setBaseOperation(baseOperation: BaseOperation): Builder = apply { this.baseOperation = baseOperation }
            fun setDirectAccessEndpoints(directAccessEndpoints: JsArray<String>?): Builder = apply { this.directAccessEndpoints = directAccessEndpoints }

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
        val targetUserId: String,
        val targetUserRole: UserRole,
        val targetUserPublicKey: ByteArray,
        val start: Int? = null,
        val end: Int? = null
    ) {
        class Builder {
            private var targetUserId: String? = null
            private var targetUserRole: UserRole? = null
            private var targetUserPublicKey: ByteArray? = null
            private var start: Int? = null
            private var end: Int? = null

            fun setTargetUserId(targetUserId: String): Builder = apply { this.targetUserId = targetUserId }
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
        val users: JsArray<ShareLock>
    ): Operation {
        class Builder {
            private var baseOperation: BaseOperation? = null
            private var users: JsArray<ShareLock>? = null

            fun setBaseOperation(baseOperation: BaseOperation): Builder = apply { this.baseOperation = baseOperation }
            fun setUsers(users: JsArray<ShareLock>): Builder = apply { this.users = users }

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
        val users: JsArray<String>
    ): Operation {
        class Builder {
            private var baseOperation: BaseOperation? = null
            private var users: JsArray<String>? = null

            fun setBaseOperation(baseOperation: BaseOperation): Builder = apply { this.baseOperation = baseOperation }
            fun setUsers(users: JsArray<String>): Builder = apply { this.users = users }

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
        val userId: String? = null,
        val userCertificateChain: JsArray<String>? = null,
        val userPrivateKey: ByteArray? = null,
        val lockId: String,
        val notBefore: Int = Clock.System.now().epochSeconds.toInt(),
        val issuedAt: Int = Clock.System.now().epochSeconds.toInt(),
        val expiresAt: Int = (Clock.System.now() + 1.minutes).epochSeconds.toInt(),
        val jti: String = Uuid.random().toString()
    ) {
        class Builder {
            private var userId: String? = null
            private var userCertificateChain: JsArray<String>? = null
            private var userPrivateKey: ByteArray? = null
            private var lockId: String? = null
            private var notBefore: Int = Clock.System.now().epochSeconds.toInt()
            private var issuedAt: Int = Clock.System.now().epochSeconds.toInt()
            private var expiresAt: Int = (Clock.System.now() + 1.minutes).epochSeconds.toInt()
            private var jti: String = Uuid.random().toString()

            fun setUserId(userId: String?): Builder = apply { this.userId = userId }
            fun setUserCertificateChain(userCertificateChain: JsArray<String>?): Builder = apply { this.userCertificateChain = userCertificateChain }
            fun setUserPrivateKey(userPrivateKey: ByteArray?): Builder = apply { this.userPrivateKey = userPrivateKey }
            fun setLockId(lockId: String): Builder = apply { this.lockId = lockId }
            fun setNotBefore(notBefore: Int): Builder = apply { this.notBefore = notBefore }
            fun setIssuedAt(issuedAt: Int): Builder = apply { this.issuedAt = issuedAt }
            fun setExpiresAt(expiresAt: Int): Builder = apply { this.expiresAt = expiresAt }
            fun setJti(jti: String): Builder = apply { this.jti = jti }

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

internal fun JsArray<LockOperations.TimeRequirement>.toBasicTimeRequirement(): List<BasicTimeRequirement> = toList().map { requirement ->
    BasicTimeRequirement(
        start = requirement.start,
        end = requirement.end,
        timezone = requirement.timezone,
        days = requirement.days.toSet()
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
        start = start,
        end = end,
        timezone = timezone,
        days = days.toSet(),
        exceptions = exceptions?.toList()
    )
}

internal fun LockOperations.UnlockOperation.toBasicUnlockOperation(): BasicUnlockOperation {
    return BasicUnlockOperation(
        baseOperation = baseOperation.toBasicBaseOperation(),
        directAccessEndpoints = directAccessEndpoints?.toList()
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
        targetUserId = targetUserId,
        targetUserRole = targetUserRole,
        targetUserPublicKey = targetUserPublicKey,
        start = start?.toLong(),
        end = end?.toLong()
    )
}

internal fun LockOperations.BatchShareLockOperation.toBasicBatchShareLockOperation(): BasicBatchShareLockOperation {
    return BasicBatchShareLockOperation(
        baseOperation = baseOperation.toBasicBaseOperation(),
        users = users.toList().map { it.toBasicShareLock() }
    )
}

internal fun LockOperations.RevokeAccessToLockOperation.toBasicRevokeAccessToLockOperation(): BasicRevokeAccessToLockOperation {
    return BasicRevokeAccessToLockOperation(
        baseOperation = baseOperation.toBasicBaseOperation(),
        users = users.toList()
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
        userId = userId,
        userCertificateChain = userCertificateChain?.toList(),
        userPrivateKey = userPrivateKey,
        lockId = lockId,
        notBefore = notBefore.toLong(),
        issuedAt = issuedAt.toLong(),
        expiresAt = expiresAt.toLong(),
        jti = jti
    )
}