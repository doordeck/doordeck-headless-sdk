package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.model.common.AuditEvent
import com.doordeck.multiplatform.sdk.model.common.CapabilityStatus
import com.doordeck.multiplatform.sdk.model.common.CapabilityType
import com.doordeck.multiplatform.sdk.model.common.DayOfWeek
import com.doordeck.multiplatform.sdk.model.common.UserRole
import kotlinx.serialization.Serializable

@Serializable
internal data class LockResponse(
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

@Serializable
internal data class LockSettingsResponse(
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

@Serializable
internal data class UsageRequirementsResponse(
    val time: List<TimeRequirementResponse>? = null,
    val location: LocationRequirementResponse? = null
)

@Serializable
internal data class TimeRequirementResponse(
    val start: String,
    val end: String,
    val timezone: String,
    val days: List<DayOfWeek>
)

@Serializable
internal data class LocationRequirementResponse(
    val latitude: Double,
    val longitude: Double,
    val enabled: Boolean,
    val radius: Int,
    val accuracy: Int
)

@Serializable
internal data class UnlockBetweenSettingResponse(
    val start: String,
    val end: String,
    val timezone: String,
    val days: List<DayOfWeek>,
    val exceptions: List<String>? = null
)

@Serializable
internal data class LockStateResponse(
    val locked: Boolean,
    val connected: Boolean
)

@Serializable
internal data class UserPublicKeyResponse(
    val id: String,
    val publicKey: String
)

@Serializable
internal data class BatchUserPublicKeyResponse(
    val id: String,
    val email: String? = null,
    val foreignKey: String? = null,
    val phone: String? = null,
    val publicKey: String
)

@Serializable
internal data class ShareableLockResponse(
    val id: String,
    val name: String
)

@Serializable
internal data class UserLockResponse(
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

@Serializable
internal data class LockUserResponse(
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

@Serializable
internal data class LockUserDetailsResponse(
    val deviceId: String,
    val role: UserRole,
    val start: Double? = null,
    val end: Double? = null
)

@Serializable
internal data class AuditResponse(
    val deviceId: String,
    val timestamp: String,
    val type: AuditEvent,
    val issuer: AuditIssuerResponse,
    val subject: AuditSubjectResponse? = null,
    val rejectionReason: String? = null,
    val rejected: Boolean
)

@Serializable
internal data class AuditIssuerResponse(
    val userId: String,
    val email: String? = null,
    val ip: String? = null
)

@Serializable
internal data class AuditSubjectResponse(
    val userId: String,
    val email: String,
    val displayName: String? = null
)