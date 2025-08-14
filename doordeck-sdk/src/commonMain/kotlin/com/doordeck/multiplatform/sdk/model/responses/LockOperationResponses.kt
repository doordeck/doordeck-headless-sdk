package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.model.common.AuditEvent
import com.doordeck.multiplatform.sdk.model.common.CapabilityStatus
import com.doordeck.multiplatform.sdk.model.common.CapabilityType
import com.doordeck.multiplatform.sdk.model.common.DayOfWeek
import com.doordeck.multiplatform.sdk.model.common.UserRole
import kotlinx.serialization.Serializable

@Serializable
internal data class BasicLockResponse(
    val id: String,
    val name: String,
    val colour: String? = null,
    val start: String? = null,
    val end: String? = null,
    val role: UserRole,
    val settings: BasicLockSettingsResponse,
    val state: BasicLockStateResponse,
    val favourite: Boolean
)

@Serializable
internal data class BasicLockSettingsResponse(
    val unlockTime: Double,
    val permittedAddresses: List<String>,
    val defaultName: String,
    val usageRequirements: BasicUsageRequirementsResponse? = null,
    val unlockBetweenWindow: BasicUnlockBetweenSettingResponse? = null,
    val tiles: List<String>,
    val hidden: Boolean,
    val directAccessEndpoints: List<String> = emptyList(),
    val capabilities: Map<CapabilityType, CapabilityStatus> = emptyMap()
)

@Serializable
internal data class BasicUsageRequirementsResponse(
    val time: List<BasicTimeRequirementResponse> = emptyList(),
    val location: BasicLocationRequirementResponse? = null
)

@Serializable
internal data class BasicTimeRequirementResponse(
    val start: String,
    val end: String,
    val timezone: String,
    val days: Set<DayOfWeek>
)

@Serializable
internal data class BasicLocationRequirementResponse(
    val latitude: Double,
    val longitude: Double,
    val enabled: Boolean,
    val radius: Int,
    val accuracy: Int
)

@Serializable
internal data class BasicUnlockBetweenSettingResponse(
    val start: String,
    val end: String,
    val timezone: String,
    val days: Set<DayOfWeek>,
    val exceptions: List<String> = emptyList()
)

@Serializable
internal data class BasicLockStateResponse(
    val connected: Boolean? = null
)

@Serializable
internal data class BasicUserPublicKeyResponse(
    val id: String,
    val publicKey: String
)

@Serializable
internal data class BasicBatchUserPublicKeyResponse(
    val id: String,
    val email: String? = null,
    val foreignKey: String? = null,
    val phone: String? = null,
    val publicKey: String
)

@Serializable
internal data class BasicShareableLockResponse(
    val id: String,
    val name: String
)

@Serializable
internal data class BasicUserLockResponse(
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
internal data class BasicLockUserResponse(
    val userId: String,
    val email: String,
    val publicKey: String,
    val displayName: String? = null,
    val orphan: Boolean,
    val foreign: Boolean,
    val start: Double? = null,
    val end: Double? = null,
    val devices: List<BasicLockUserDetailsResponse>
)

@Serializable
internal data class BasicLockUserDetailsResponse(
    val deviceId: String,
    val role: UserRole,
    val start: Double? = null,
    val end: Double? = null
)

@Serializable
internal data class BasicAuditResponse(
    val deviceId: String,
    val timestamp: String,
    val type: AuditEvent,
    val issuer: BasicAuditUserResponse,
    val subject: BasicAuditUserResponse? = null,
    val rejectionReason: String? = null,
    val rejected: Boolean
)

@Serializable
internal data class BasicAuditUserResponse(
    val userId: String,
    val email: String? = null,
    val displayName: String? = null,
    val ip: String? = null
)