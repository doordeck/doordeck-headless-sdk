package com.doordeck.sdk.api.model

import kotlinx.datetime.Clock
import kotlin.js.JsExport
import kotlin.time.Duration.Companion.minutes

@JsExport
object LockOperations {

    // FIXME why are these not data classes?
    class LockProperties(
        val name: String? = null,
        val favourite: Boolean? = null,
        val colour: String? = null,
        val settings: LockSettings? = null
    )

    class LockSettings(
        val defaultName: String? = null,
        val permittedAddress: Array<String>? = null,
        val delay: Int? = null,
        val usageRequirements: UsageRequirements? = null
    )

    class UsageRequirements(
        val time: TimeRequirement? = null,
        val location: LocationRequirement? = null
    )

    class TimeRequirement(
        val start: String,
        val end: String,
        val timezone: String,
        val days: Array<String>
    )

    class LocationRequirement(
        val latitude: Double,
        val longitude: Double,
        val enabled: Boolean? = null,
        val radius: Int? = null,
        val accuracy: Int? = null
    )

    class UnlockBetween(
        val start: String,
        val end: String,
        val timezone: String,
        val days: String,
        val exceptions: Array<String>? = null
    )

    class LockOperation(
        override val baseOperation: BaseOperation,
    ): Operation(baseOperation)

    class UnlockOperation(
        override val baseOperation: BaseOperation
    ): Operation(baseOperation)

    class ShareALockOperation(
        override val baseOperation: BaseOperation,
        val targetUserId: String,
        val targetUserRole: UserRole,
        val targetUserPublicKey: ByteArray,
        val start: Int? = null,
        val end: Int? = null
    ): Operation(baseOperation)

    class RevokeAccessToALockOperation(
        override val baseOperation: BaseOperation,
        val users: Array<String>
    ): Operation(baseOperation)

    class UpdateSecureSettingsOperation(
        override val baseOperation: BaseOperation,
        val unlockDuration: Int? = null,
        val unlockBetween: UnlockBetween? = null
    ): Operation(baseOperation)

    class RemoveSecureSettingsOperation(
        override val baseOperation: BaseOperation
    ): Operation(baseOperation)

    class BaseOperation(
        val userId: String,
        val userCertificateChain: Array<String>,
        val userPrivateKey: ByteArray,
        val lockId: String,
        val notBefore: Int = Clock.System.now().epochSeconds.toInt(),
        val issuedAt: Int = Clock.System.now().epochSeconds.toInt(),
        val expiresAt: Int = (Clock.System.now() + 1.minutes).epochSeconds.toInt(),
        val trackId: String? = null // FIXME whats trackId?
    )

    abstract class Operation(
        open val baseOperation: BaseOperation
    )
}
