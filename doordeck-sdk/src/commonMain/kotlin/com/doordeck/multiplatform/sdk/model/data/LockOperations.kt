package com.doordeck.multiplatform.sdk.model.data

import com.doordeck.multiplatform.sdk.model.common.UserRole
import kotlinx.datetime.Clock
import kotlin.js.JsExport
import kotlin.jvm.JvmOverloads
import kotlin.time.Duration.Companion.minutes
import kotlin.uuid.Uuid

@JsExport
object LockOperations {

    data class TimeRequirement(
        val start: String, // HH:mm
        val end: String, // HH:mm
        val timezone: String,
        val days: List<String>
    ) {
        class Builder {
            private var start: String? = null
            private var end: String? = null
            private var timezone: String? = null
            private var days: List<String>? = null

            fun setStart(start: String): Builder = apply { this.start = start }
            fun setEnd(end: String): Builder = apply { this.end = end }
            fun setTimezone(timezone: String) = apply { this.timezone = timezone }
            fun setDays(days: List<String>) = apply { this.days = days }

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
        val enabled: Boolean? = null,
        val radius: Int = 20,
        val accuracy: Int = 20
    ) {
        class Builder {
            private var latitude: Double? = null
            private var longitude: Double? = null
            private var enabled: Boolean? = null
            private var radius: Int = 20
            private var accuracy: Int = 20

            fun setLatitude(latitude: Double): Builder = apply { this.latitude = latitude }
            fun setLongitude(longitude: Double): Builder = apply { this.longitude = longitude }
            fun setEnabled(enabled: Boolean?): Builder = apply { this.enabled = enabled }
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
        val start: String, // HH:mm
        val end: String, // HH:mm
        val timezone: String,
        val days: List<String>,
        val exceptions: List<String>? = null
    ) {
        class Builder {
            private var start: String? = null
            private var end: String? = null
            private var timezone: String? = null
            private var days: List<String>? = null
            private var exceptions: List<String>? = null

            fun setStart(start: String): Builder = apply { this.start = start }
            fun setEnd(end: String): Builder = apply { this.end = end }
            fun setTimezone(timezone: String): Builder = apply { this.timezone = timezone }
            fun setDays(days: List<String>): Builder = apply { this.days = days }
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

    data class UnlockOperation @JvmOverloads constructor(
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

    data class ShareLock @JvmOverloads constructor(
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
        val users: List<String>
    ): Operation {
        class Builder {
            private var baseOperation: BaseOperation? = null
            private var users: List<String>? = null

            fun setBaseOperation(baseOperation: BaseOperation): Builder = apply { this.baseOperation = baseOperation }
            fun setUsers(users: List<String>): Builder = apply { this.users = users }

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
        val userId: String? = null,
        val userCertificateChain: List<String>? = null,
        val userPrivateKey: ByteArray? = null,
        val lockId: String,
        val notBefore: Int = Clock.System.now().epochSeconds.toInt(),
        val issuedAt: Int = Clock.System.now().epochSeconds.toInt(),
        val expiresAt: Int = (Clock.System.now() + 1.minutes).epochSeconds.toInt(),
        val jti: String = Uuid.random().toString()
    ) {
        class Builder {
            private var userId: String? = null
            private var userCertificateChain: List<String>? = null
            private var userPrivateKey: ByteArray? = null
            private var lockId: String? = null
            private var notBefore: Int = Clock.System.now().epochSeconds.toInt()
            private var issuedAt: Int = Clock.System.now().epochSeconds.toInt()
            private var expiresAt: Int = (Clock.System.now() + 1.minutes).epochSeconds.toInt()
            private var jti: String = Uuid.random().toString()

            fun setUserId(userId: String?): Builder = apply { this.userId = userId }
            fun setUserCertificateChain(userCertificateChain: List<String>?): Builder = apply { this.userCertificateChain = userCertificateChain }
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