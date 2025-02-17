package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.model.common.AuditEvent
import com.doordeck.multiplatform.sdk.model.common.CapabilityStatus
import com.doordeck.multiplatform.sdk.model.common.CapabilityType
import com.doordeck.multiplatform.sdk.model.common.UserRole
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
data class LockResponse(
    val id: String,
    val name: String,
    val colour: String? = null,
    val start: String? = null,
    val end: String? = null,
    val role: UserRole,
    val settings: LockSettingsResponse,
    val state: LockStateResponse,
    val favourite: Boolean,
    val unlockTime: Double? = null
)

@JsExport
@Serializable
data class LockSettingsResponse(
    val unlockTime: Double,
    val permittedAddresses: List<String>,
    val defaultName: String,
    val usageRequirements: UsageRequirementsResponse? = null,
    val unlockBetweenWindow: UnlockBetweenSettingResponse? = null,
    val tiles: List<String>,
    val hidden: Boolean,
    val directAccessEndpoints: List<String> = emptyList(),
    val capabilities: Map<CapabilityType, CapabilityStatus> = emptyMap()
)

@JsExport
@Serializable
data class UsageRequirementsResponse(
    val time: List<TimeRequirementResponse>? = null,
    val location: LocationRequirementResponse? = null
)

@JsExport
@Serializable
data class TimeRequirementResponse(
    val start: String,
    val end: String,
    val timezone: String,
    val days: List<String>
)

@JsExport
@Serializable
data class LocationRequirementResponse(
    val latitude: Double,
    val longitude: Double,
    val enabled: Boolean? = null,
    val radius: Int? = null,
    val accuracy: Int? = null
)

@JsExport
@Serializable
data class UnlockBetweenSettingResponse(
    val start: String,
    val end: String,
    val timezone: String,
    val days: List<String>,
    val exceptions: List<String>? = null
)

@JsExport
@Serializable
data class LockStateResponse(
    val locked: Boolean,
    val connected: Boolean
)

@JsExport
@Serializable
data class UserPublicKeyResponse(
    val id: String,
    val publicKey: String
)

@JsExport
@Serializable
data class BatchUserPublicKeyResponse(
    val id: String,
    val email: String? = null,
    val foreignKey: String? = null,
    val phone: String? = null,
    val publicKey: String
)

@JsExport
@Serializable
data class ShareableLockResponse(
    val id: String,
    val name: String
)

@JsExport
@Serializable
data class UserLockResponse(
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
data class LockUserResponse(
    val userId: String,
    val email: String,
    val publicKey: String,
    val displayName: String? = null,
    val orphan: Boolean,
    val foreign: Boolean,
    val start: Double? = null,
    val end: Double? = null,
    val devices: List<LockUserDetailsResponse>
)

@JsExport
@Serializable
data class LockUserDetailsResponse(
    val deviceId: String,
    val role: UserRole,
    val start: Double? = null,
    val end: Double? = null
)

@JsExport
@Serializable
data class AuditResponse(
    val deviceId: String,
    val timestamp: Double,
    val type: AuditEvent,
    val issuer: AuditIssuerResponse,
    val subject: AuditSubjectResponse? = null,
    val rejectionReason: String? = null,
    val rejected: Boolean
)

@JsExport
@Serializable
data class AuditIssuerResponse(
    val userId: String,
    val email: String? = null,
    val ip: String? = null
)

@JsExport
@Serializable
data class AuditSubjectResponse(
    val userId: String,
    val email: String,
    val displayName: String? = null
)