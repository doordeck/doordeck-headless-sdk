package com.doordeck.multiplatform.sdk.api.model

import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable
import kotlin.js.JsExport
import kotlin.jvm.JvmOverloads
import kotlin.time.Duration.Companion.minutes
import kotlin.uuid.Uuid

@JsExport
object LockOperations {

    @Serializable
    class TimeRequirement(
        val start: String,
        val end: String,
        val timezone: String,
        val days: List<String>
    )

    @Serializable
    class LocationRequirement @JvmOverloads constructor(
        val latitude: Double,
        val longitude: Double,
        val enabled: Boolean? = null,
        val radius: Int? = null,
        val accuracy: Int? = null
    )

    @Serializable
    class UnlockBetween @JvmOverloads constructor(
        val start: String,
        val end: String,
        val timezone: String,
        val days: List<String>,
        val exceptions: List<String>? = null
    )

    @Serializable
    class UnlockOperation @JvmOverloads constructor(
        val baseOperation: BaseOperation,
        val directAccessEndpoints: List<String>? = null
    ): Operation

    @Serializable
    class ShareLockOperation(
        val baseOperation: BaseOperation,
        val shareLock: ShareLock
    ): Operation

    @Serializable
    class ShareLock @JvmOverloads constructor(
        val targetUserId: String,
        val targetUserRole: UserRole,
        val targetUserPublicKey: ByteArray,
        val start: Int? = null,
        val end: Int? = null
    )

    @Serializable
    class RevokeAccessToLockOperation(
        val baseOperation: BaseOperation,
        val users: List<String>
    ): Operation

    @Serializable
    class UpdateSecureSettingUnlockDuration(
        val baseOperation: BaseOperation,
        val unlockDuration: Int
    ): Operation

    @Serializable
    class UpdateSecureSettingUnlockBetween @JvmOverloads constructor(
        val baseOperation: BaseOperation,
        val unlockBetween: UnlockBetween? = null
    ): Operation

    @Serializable
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

    @Serializable
    sealed interface Operation
}
