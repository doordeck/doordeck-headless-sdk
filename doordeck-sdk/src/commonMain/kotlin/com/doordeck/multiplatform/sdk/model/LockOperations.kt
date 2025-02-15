package com.doordeck.multiplatform.sdk.model

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