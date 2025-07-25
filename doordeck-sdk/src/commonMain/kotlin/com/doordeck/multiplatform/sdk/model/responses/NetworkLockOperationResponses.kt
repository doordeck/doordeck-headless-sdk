package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.model.common.AuditEvent
import com.doordeck.multiplatform.sdk.model.common.CapabilityStatus
import com.doordeck.multiplatform.sdk.model.common.CapabilityType
import com.doordeck.multiplatform.sdk.model.common.DayOfWeek
import com.doordeck.multiplatform.sdk.model.common.UserRole
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
internal data class NetworkLockResponse(
    val id: String,
    val name: String,
    val colour: String? = null,
    val start: String? = null,
    val end: String? = null,
    val role: UserRole,
    val settings: NetworkLockSettingsResponse,
    val state: NetworkLockStateResponse,
    val favourite: Boolean,
    val unlockTime: Double? = null
)

@JsExport
@Serializable
internal data class NetworkLockSettingsResponse(
    val unlockTime: Double,
    val permittedAddresses: List<String>,
    val defaultName: String,
    val usageRequirements: NetworkUsageRequirementsResponse? = null,
    val unlockBetweenWindow: NetworkUnlockBetweenSettingResponse? = null,
    val tiles: List<String>,
    val hidden: Boolean,
    val directAccessEndpoints: List<String> = emptyList(),
    val capabilities: Map<CapabilityType, CapabilityStatus> = emptyMap()
)

@JsExport
@Serializable
internal data class NetworkUsageRequirementsResponse(
    val time: List<NetworkTimeRequirementResponse>? = null,
    val location: NetworkLocationRequirementResponse? = null
)

@JsExport
@Serializable
internal data class NetworkTimeRequirementResponse(
    val start: String,
    val end: String,
    val timezone: String,
    val days: List<DayOfWeek>
)

@JsExport
@Serializable
internal data class NetworkLocationRequirementResponse(
    val latitude: Double,
    val longitude: Double,
    val enabled: Boolean,
    val radius: Int,
    val accuracy: Int
)

@JsExport
@Serializable
internal data class NetworkUnlockBetweenSettingResponse(
    val start: String,
    val end: String,
    val timezone: String,
    val days: List<DayOfWeek>,
    val exceptions: List<String>? = null
)

@JsExport
@Serializable
internal data class NetworkLockStateResponse(
    val locked: Boolean,
    val connected: Boolean
)

@JsExport
@Serializable
internal data class NetworkUserPublicKeyResponse(
    val id: String,
    val publicKey: String
)

@JsExport
@Serializable
internal data class NetworkBatchUserPublicKeyResponse(
    val id: String,
    val email: String? = null,
    val foreignKey: String? = null,
    val phone: String? = null,
    val publicKey: String
)

@JsExport
@Serializable
internal data class NetworkShareableLockResponse(
    val id: String,
    val name: String
)

@JsExport
@Serializable
internal data class NetworkUserLockResponse(
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
internal data class NetworkLockUserResponse(
    val userId: String,
    val email: String,
    val publicKey: String,
    val displayName: String? = null,
    val orphan: Boolean,
    val foreign: Boolean,
    val start: Double? = null,
    val end: Double? = null,
    val devices: List<NetworkLockUserDetailsResponse>
)

@JsExport
@Serializable
internal data class NetworkLockUserDetailsResponse(
    val deviceId: String,
    val role: UserRole,
    val start: Double? = null,
    val end: Double? = null
)

@JsExport
@Serializable
internal data class NetworkAuditResponse(
    val deviceId: String,
    val timestamp: String,
    val type: AuditEvent,
    val issuer: NetworkAuditIssuerResponse,
    val subject: NetworkAuditSubjectResponse? = null,
    val rejectionReason: String? = null,
    val rejected: Boolean
)

@JsExport
@Serializable
internal data class NetworkAuditIssuerResponse(
    val userId: String,
    val email: String? = null,
    val ip: String? = null
)

@JsExport
@Serializable
internal data class NetworkAuditSubjectResponse(
    val userId: String,
    val email: String,
    val displayName: String? = null
)