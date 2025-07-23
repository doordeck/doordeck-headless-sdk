@file:UseSerializers(
    IdValueSerializer::class, PublicKeyValueSerializer::class, InstantValueSerializer::class,
    DurationValueSerializer::class, LocalTimeValueSerializer::class, LocalDateValueSerializer::class
)

package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.model.common.AuditEvent
import com.doordeck.multiplatform.sdk.model.common.CapabilityStatus
import com.doordeck.multiplatform.sdk.model.common.CapabilityType
import com.doordeck.multiplatform.sdk.model.common.DayOfWeek
import com.doordeck.multiplatform.sdk.model.common.UserRole
import com.doordeck.multiplatform.sdk.model.values.DurationValue
import com.doordeck.multiplatform.sdk.model.values.DurationValueSerializer
import com.doordeck.multiplatform.sdk.model.values.IdValue
import com.doordeck.multiplatform.sdk.model.values.IdValueSerializer
import com.doordeck.multiplatform.sdk.model.values.InstantValue
import com.doordeck.multiplatform.sdk.model.values.InstantValueSerializer
import com.doordeck.multiplatform.sdk.model.values.LocalDateValue
import com.doordeck.multiplatform.sdk.model.values.LocalDateValueSerializer
import com.doordeck.multiplatform.sdk.model.values.LocalTimeValue
import com.doordeck.multiplatform.sdk.model.values.LocalTimeValueSerializer
import com.doordeck.multiplatform.sdk.model.values.PublicKeyValue
import com.doordeck.multiplatform.sdk.model.values.PublicKeyValueSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlin.js.JsExport

@JsExport
@Serializable
data class LockResponse(
    val id: IdValue,
    val name: String,
    val colour: String? = null,
    val start: InstantValue? = null,
    val end: InstantValue? = null,
    val role: UserRole,
    val settings: LockSettingsResponse,
    val state: LockStateResponse,
    val favourite: Boolean,
    val unlockTime: DurationValue? = null
)

@JsExport
@Serializable
data class LockSettingsResponse(
    val unlockTime: DurationValue,
    val permittedAddresses: List<String>,
    val defaultName: String,
    val usageRequirements: UsageRequirementsResponse? = null,
    val unlockBetweenWindow: UnlockBetweenSettingResponse? = null,
    val tiles: List<IdValue>,
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
    val start: LocalTimeValue,
    val end: LocalTimeValue,
    val timezone: String,
    val days: List<DayOfWeek>
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
    val start: LocalTimeValue,
    val end: LocalTimeValue,
    val timezone: String,
    val days: List<DayOfWeek>,
    val exceptions: List<LocalDateValue>? = null
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
    val id: IdValue,
    val publicKey: PublicKeyValue
)

@JsExport
@Serializable
data class BatchUserPublicKeyResponse(
    val id: IdValue,
    val email: String? = null,
    val foreignKey: String? = null,
    val phone: String? = null,
    val publicKey: PublicKeyValue
)

@JsExport
@Serializable
data class ShareableLockResponse(
    val id: IdValue,
    val name: String
)

@JsExport
@Serializable
data class UserLockResponse(
    val userId: IdValue,
    val email: String,
    val publicKey: PublicKeyValue,
    val displayName: String? = null,
    val orphan: Boolean,
    val foreign: Boolean,
    val role: UserRole,
    val start: InstantValue? = null,
    val end: InstantValue? = null
)

@JsExport
@Serializable
data class LockUserResponse(
    val userId: IdValue,
    val email: String,
    val publicKey: PublicKeyValue,
    val displayName: String? = null,
    val orphan: Boolean,
    val foreign: Boolean,
    val start: InstantValue? = null,
    val end: InstantValue? = null,
    val devices: List<LockUserDetailsResponse>
)

@JsExport
@Serializable
data class LockUserDetailsResponse(
    val deviceId: IdValue,
    val role: UserRole,
    val start: InstantValue? = null,
    val end: InstantValue? = null
)

@JsExport
@Serializable
data class AuditResponse(
    val deviceId: IdValue,
    val timestamp: InstantValue,
    val type: AuditEvent,
    val issuer: AuditIssuerResponse,
    val subject: AuditSubjectResponse? = null,
    val rejectionReason: String? = null,
    val rejected: Boolean
)

@JsExport
@Serializable
data class AuditIssuerResponse(
    val userId: IdValue,
    val email: String? = null,
    val ip: String? = null
)

@JsExport
@Serializable
data class AuditSubjectResponse(
    val userId: IdValue,
    val email: String,
    val displayName: String? = null
)