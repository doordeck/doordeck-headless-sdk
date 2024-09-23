package com.doordeck.multiplatform.sdk.api.model

import kotlinx.datetime.Clock
import kotlin.js.JsExport
import kotlin.jvm.JvmOverloads
import kotlin.time.Duration.Companion.minutes
import kotlin.uuid.Uuid

@JsExport
object LockOperations {

    class TimeRequirement(
        val start: String,
        val end: String,
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
        val start: String,
        val end: String,
        val timezone: String,
        val days: List<String>,
        val exceptions: List<String>? = null
    )

    class UnlockOperation @JvmOverloads constructor(
        override val baseOperation: BaseOperation,
        val directAccessEndpoints: List<String>? = null
    ): Operation(baseOperation)

    class ShareLockOperation(
        override val baseOperation: BaseOperation,
        val shareLock: ShareLock
    ): Operation(baseOperation)

    class ShareLock @JvmOverloads constructor(
        val targetUserId: String,
        val targetUserRole: UserRole,
        val targetUserPublicKey: ByteArray,
        val start: Int? = null,
        val end: Int? = null
    )

    class RevokeAccessToLockOperation(
        override val baseOperation: BaseOperation,
        val users: List<String>
    ): Operation(baseOperation)

    class UpdateSecureSettingUnlockDuration(
        override val baseOperation: BaseOperation,
        val unlockDuration: Int
    ): Operation(baseOperation)

    class UpdateSecureSettingUnlockBetween @JvmOverloads constructor(
        override val baseOperation: BaseOperation,
        val unlockBetween: UnlockBetween? = null
    ): Operation(baseOperation)

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

    abstract class Operation(
        open val baseOperation: BaseOperation
    )
}
