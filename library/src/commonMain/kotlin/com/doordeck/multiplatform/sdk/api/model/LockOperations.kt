package com.doordeck.multiplatform.sdk.api.model

import kotlinx.datetime.Clock
import kotlin.js.JsExport
import kotlin.jvm.JvmOverloads
import kotlin.time.Duration.Companion.minutes
import kotlin.uuid.Uuid

@JsExport
object LockOperations {

    class TimeRequirement(
        val start: String, // HH:mm
        val end: String, // HH:mm
        val timezone: String,
        val days: List<String>
    )

    class LocationRequirement @JvmOverloads constructor(
        val latitude: Double,
        val longitude: Double,
        val enabled: Boolean? = null,
        val radius: Int? = null,
        val accuracy: Int? = null
    )

    class UnlockBetween @JvmOverloads constructor(
        val start: String, // HH:mm
        val end: String, // HH:mm
        val timezone: String,
        val days: List<String>,
        val exceptions: List<String>? = null
    )

    class UnlockOperation @JvmOverloads constructor(
        val baseOperation: BaseOperation,
        val directAccessEndpoints: List<String>? = null
    ): Operation

    class ShareLockOperation(
        val baseOperation: BaseOperation,
        val shareLock: ShareLock
    ): Operation

    class ShareLock @JvmOverloads constructor(
        val targetUserId: String,
        val targetUserRole: UserRole,
        val targetUserPublicKey: ByteArray,
        val start: Int? = null,
        val end: Int? = null
    )

    class RevokeAccessToLockOperation(
        val baseOperation: BaseOperation,
        val users: List<String>
    ): Operation

    class UpdateSecureSettingUnlockDuration(
        val baseOperation: BaseOperation,
        val unlockDuration: Int
    ): Operation

    class UpdateSecureSettingUnlockBetween @JvmOverloads constructor(
        val baseOperation: BaseOperation,
        val unlockBetween: UnlockBetween? = null
    ): Operation

    class BaseOperation @JvmOverloads constructor(
        val userId: String,
        val userCertificateChain: List<String>,
        val userPrivateKey: ByteArray,
        val lockId: String,
        val notBefore: Int = Clock.System.now().epochSeconds.toInt(),
        val issuedAt: Int = Clock.System.now().epochSeconds.toInt(),
        val expiresAt: Int = (Clock.System.now() + 1.minutes).epochSeconds.toInt(),
        val jti: String = Uuid.random().toString()
    )

    sealed interface Operation
}
