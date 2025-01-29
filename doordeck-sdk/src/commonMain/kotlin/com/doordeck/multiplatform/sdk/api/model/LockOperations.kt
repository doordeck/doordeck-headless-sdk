package com.doordeck.multiplatform.sdk.api.model

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
    )

    data class LocationRequirement @JvmOverloads constructor(
        val latitude: Double,
        val longitude: Double,
        val enabled: Boolean? = null,
        val radius: Int? = null,
        val accuracy: Int? = null
    )

    data class UnlockBetween @JvmOverloads constructor(
        val start: String, // HH:mm
        val end: String, // HH:mm
        val timezone: String,
        val days: List<String>,
        val exceptions: List<String>? = null
    )

    data class UnlockOperation @JvmOverloads constructor(
        val baseOperation: BaseOperation,
        val directAccessEndpoints: List<String>? = null
    ): Operation

    data class ShareLockOperation(
        val baseOperation: BaseOperation,
        val shareLock: ShareLock
    ): Operation

    data class ShareLock @JvmOverloads constructor(
        val targetUserId: String,
        val targetUserRole: UserRole,
        val targetUserPublicKey: ByteArray,
        val start: Int? = null,
        val end: Int? = null
    )

    data class BatchShareLockOperation(
        val baseOperation: BaseOperation,
        val users: List<ShareLock>
    ): Operation

    data class RevokeAccessToLockOperation(
        val baseOperation: BaseOperation,
        val users: List<String>
    ): Operation

    data class UpdateSecureSettingUnlockDuration(
        val baseOperation: BaseOperation,
        val unlockDuration: Int
    ): Operation

    data class UpdateSecureSettingUnlockBetween @JvmOverloads constructor(
        val baseOperation: BaseOperation,
        val unlockBetween: UnlockBetween? = null
    ): Operation

    data class BaseOperation @JvmOverloads constructor(
        val userId: String? = null,
        val userCertificateChain: List<String>? = null,
        val userPrivateKey: ByteArray? = null,
        val lockId: String,
        val notBefore: Int = Clock.System.now().epochSeconds.toInt(),
        val issuedAt: Int = Clock.System.now().epochSeconds.toInt(),
        val expiresAt: Int = (Clock.System.now() + 1.minutes).epochSeconds.toInt(),
        val jti: String = Uuid.random().toString()
    )

    sealed interface Operation
}

/**
 * Creates a new instance of `LockOperations.BaseOperation` with a newly generated JTI (JSON Token Identifier).
 *
 * The new instance retains all the properties of the original operation except for the JTI,
 * which is replaced with a randomly generated UUID.
 *
 * @receiver The original `LockOperations.BaseOperation` instance.
 * @return A new `LockOperations.BaseOperation` instance with the same properties as the receiver but with a new JTI.
 */
internal fun LockOperations.BaseOperation.withNewJti() = LockOperations.BaseOperation(
    userId = userId,
    userCertificateChain = userCertificateChain,
    userPrivateKey = userPrivateKey,
    lockId = lockId,
    notBefore = notBefore,
    issuedAt = issuedAt,
    expiresAt = expiresAt,
    jti = Uuid.random().toString()
)