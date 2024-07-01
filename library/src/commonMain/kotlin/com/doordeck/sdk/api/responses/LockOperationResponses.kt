package com.doordeck.sdk.api.responses

import com.doordeck.sdk.api.model.AuditEvent
import com.doordeck.sdk.api.model.UserRole
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
class LockResponse(
    val id: String,
    val name: String,
    val colour: String? = null,
    val start: String? = null,
    val end: String? = null,
    val role: UserRole,
    val settings: LockSettingsResponse,
    val state: LockStateResponse,
    val favourite: Boolean,
    val unlockTime: Double? = null,
    val unlockForever: Boolean? = null
)

@JsExport
@Serializable
class LockSettingsResponse(
    val unlockTime: Double,
    val permittedAddresses: Array<String>,
    val defaultName: String,
    val usageRequirements: UsageRequirementsResponse? = null,
    val unlockBetweenWindow: UnlockBetweenSettingResponse? = null,
    val tiles: Array<String>,
    val hidden: Boolean
)

@JsExport
@Serializable
class UsageRequirementsResponse(
    val time: Array<TimeRequirementResponse>? = null,
    val location: LocationRequirementResponse? = null
)

@JsExport
@Serializable
class TimeRequirementResponse(
    val start: String,
    val end: String,
    val timezone: String,
    val days: Array<String>
)

@JsExport
@Serializable
class LocationRequirementResponse(
    val latitude: Double,
    val longitude: Double,
    val enabled: Boolean? = null,
    val radius: Int? = null,
    val accuracy: Int? = null
)

@JsExport
@Serializable
class UnlockBetweenSettingResponse(
    val start: String,
    val end: String,
    val timezone: String,
    val days: Array<String>,
    val exceptions: Array<String>? = null
)

@JsExport
@Serializable
class LockStateResponse(
    val locked: Boolean,
    val connected: Boolean,
    val delay: String? = null // Duration
)

@JsExport
@Serializable
class UserPublicKeyResponse(
    val id: String,
    val publicKey: String
)

@JsExport
@Serializable
class ShareableLockResponse(
    val id: String,
    val name: String
)

@JsExport
@Serializable
class UserLockResponse(
    val userId: String,
    val email: String,
    val publicKey: String,
    val displayName: String? = null,
    val orphan: Boolean,
    val foreign: Boolean,
    val role: UserRole,
    val start: Double? = null,
    val end: Double? = null
)

@JsExport
@Serializable
class LockUserResponse(
    val userId: String,
    val email: String,
    val publicKey: String,
    val displayName: String? = null,
    val orphan: Boolean,
    val foreign: Boolean,
    val start: Double? = null,
    val end: Double? = null,
    val devices: Array<LockUserDetailsResponse>
)

@JsExport
@Serializable
class LockUserDetailsResponse(
    val deviceId: String,
    val role: UserRole,
    val start: Double? = null,
    val end: Double? = null
)

@JsExport
@Serializable
class UserAuditResponse(
    val deviceId: String,
    val timestamp: Double,
    val type: AuditEvent,
    val issuer: UserAuditIssuerResponse,
    val subject: UserAuditSubjectResponse? = null,
    val rejected: Boolean
)

@JsExport
@Serializable
class UserAuditIssuerResponse(
    val userId: String
)

@JsExport
@Serializable
class UserAuditSubjectResponse(
    val userId: String,
    val email: String
)

@JsExport
@Serializable
class LockAuditTrailResponse(
    val timestamp: Double,
    val type: AuditEvent,
    val user: String? = null,
    val email: String? = null,
    val displayName: String? = null,
    val message: String? = null
)