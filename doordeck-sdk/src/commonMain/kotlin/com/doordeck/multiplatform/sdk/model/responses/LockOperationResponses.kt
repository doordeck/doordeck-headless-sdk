@file:UseSerializers(
    PlatformIdSerializer::class,
    PlatformPublicKeySerializer::class,
    PlatformInstantSerializer::class,
    PlatformDurationSerializer::class
)

package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.model.common.AuditEvent
import com.doordeck.multiplatform.sdk.model.common.CapabilityStatus
import com.doordeck.multiplatform.sdk.model.common.CapabilityType
import com.doordeck.multiplatform.sdk.model.common.UserRole
import com.doordeck.multiplatform.sdk.model.values.PlatformDuration
import com.doordeck.multiplatform.sdk.model.values.PlatformDurationSerializer
import com.doordeck.multiplatform.sdk.model.values.PlatformId
import com.doordeck.multiplatform.sdk.model.values.PlatformIdSerializer
import com.doordeck.multiplatform.sdk.model.values.PlatformInstant
import com.doordeck.multiplatform.sdk.model.values.PlatformInstantSerializer
import com.doordeck.multiplatform.sdk.model.values.PlatformPublicKey
import com.doordeck.multiplatform.sdk.model.values.PlatformPublicKeySerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlin.js.JsExport

@JsExport
@Serializable
data class LockResponse(
    val id: PlatformId,
    val name: String,
    val colour: String? = null,
    val start: PlatformInstant? = null,
    val end: PlatformInstant? = null,
    val role: UserRole,
    val settings: LockSettingsResponse,
    val state: LockStateResponse,
    val favourite: Boolean,
    val unlockTime: PlatformDuration? = null
)

@JsExport
@Serializable
data class LockSettingsResponse(
    val unlockTime: PlatformDuration,
    val permittedAddresses: List<String>, // InetAddress
    val defaultName: String,
    val usageRequirements: UsageRequirementsResponse? = null,
    val unlockBetweenWindow: UnlockBetweenSettingResponse? = null,
    val tiles: List<PlatformId>,
    val hidden: Boolean,
    val directAccessEndpoints: List<String> = emptyList(), // URIs
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
    val start: String, // LocalTime
    val end: String, // LocalTime
    val timezone: String, // ZoneId
    val days: List<String> // DayOfWeek
)

@JsExport
@Serializable
data class LocationRequirementResponse(
    val latitude: Double,
    val longitude: Double,
    val enabled: Boolean,
    val radius: Int,
    val accuracy: Int
)

@JsExport
@Serializable
data class UnlockBetweenSettingResponse(
    val start: String, // LocalTime HH:mm
    val end: String, // LocalTime HH:mm
    val timezone: String, // ZoneId
    val days: List<String>, // DayOfWeek
    val exceptions: List<String>? = null // Local date yyyy-MM-dd
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
    val id: PlatformId,
    val publicKey: PlatformPublicKey
)

@JsExport
@Serializable
data class BatchUserPublicKeyResponse(
    val id: PlatformId,
    val email: String? = null,
    val foreignKey: String? = null,
    val phone: String? = null,
    val publicKey: PlatformPublicKey
)

@JsExport
@Serializable
data class ShareableLockResponse(
    val id: PlatformId,
    val name: String
)

@JsExport
@Serializable
data class UserLockResponse(
    val userId: PlatformId,
    val email: String,
    val publicKey: PlatformPublicKey,
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
    val userId: PlatformId,
    val email: String,
    val publicKey: PlatformPublicKey,
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
    val deviceId: PlatformId,
    val role: UserRole,
    val start: Double? = null,
    val end: Double? = null
)

@JsExport
@Serializable
data class AuditResponse(
    val deviceId: PlatformId,
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
    val userId: PlatformId,
    val email: String? = null,
    val ip: String? = null
)

@JsExport
@Serializable
data class AuditSubjectResponse(
    val userId: PlatformId,
    val email: String,
    val displayName: String? = null
)