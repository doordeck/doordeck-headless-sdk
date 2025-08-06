package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.model.common.AuditEvent
import com.doordeck.multiplatform.sdk.model.common.CapabilityStatus
import com.doordeck.multiplatform.sdk.model.common.CapabilityType
import com.doordeck.multiplatform.sdk.model.common.DayOfWeek
import com.doordeck.multiplatform.sdk.model.common.UserRole

@JsExport
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
data class UsageRequirementsResponse(
    val time: List<TimeRequirementResponse>? = null,
    val location: LocationRequirementResponse? = null
)

@JsExport
data class TimeRequirementResponse(
    val start: String,
    val end: String,
    val timezone: String,
    val days: List<DayOfWeek>
)

@JsExport
data class LocationRequirementResponse(
    val latitude: Double,
    val longitude: Double,
    val enabled: Boolean,
    val radius: Int,
    val accuracy: Int
)

@JsExport
data class UnlockBetweenSettingResponse(
    val start: String,
    val end: String,
    val timezone: String,
    val days: List<DayOfWeek>,
    val exceptions: List<String>? = null
)

@JsExport
data class LockStateResponse(
    val connected: Boolean
)

@JsExport
data class UserPublicKeyResponse(
    val id: String,
    val publicKey: String
)

@JsExport
data class BatchUserPublicKeyResponse(
    val id: String,
    val email: String? = null,
    val foreignKey: String? = null,
    val phone: String? = null,
    val publicKey: String
)

@JsExport
data class ShareableLockResponse(
    val id: String,
    val name: String
)

@JsExport
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
data class LockUserDetailsResponse(
    val deviceId: String,
    val role: UserRole,
    val start: Double? = null,
    val end: Double? = null
)

@JsExport
data class AuditResponse(
    val deviceId: String,
    val timestamp: Double,
    val type: AuditEvent,
    val issuer: AuditUserResponse,
    val subject: AuditUserResponse? = null,
    val rejectionReason: String? = null,
    val rejected: Boolean
)

@JsExport
data class AuditUserResponse(
    val userId: String,
    val email: String? = null,
    val displayName: String? = null,
    val ip: String? = null
)

internal fun List<BasicLockResponse>.toLockResponse(): List<LockResponse> = map { lock ->
    lock.toLockResponse()
}

internal fun BasicLockResponse.toLockResponse(): LockResponse = LockResponse(
    id = id,
    name = name,
    colour = colour,
    start = start,
    end = end,
    role = role,
    settings = settings.toLockSettingsResponse(),
    state = state.toLockStateResponse(),
    favourite = favourite,
    unlockTime = unlockTime
)

internal fun BasicLockSettingsResponse.toLockSettingsResponse(): LockSettingsResponse = LockSettingsResponse(
    unlockTime = unlockTime,
    permittedAddresses = permittedAddresses,
    defaultName = defaultName,
    usageRequirements = usageRequirements?.toUsageRequirementsResponse(),
    unlockBetweenWindow = unlockBetweenWindow?.toUnlockBetweenSettingResponse(),
    tiles = tiles,
    hidden = hidden,
    directAccessEndpoints = directAccessEndpoints,
    capabilities = capabilities
)

internal fun BasicUsageRequirementsResponse.toUsageRequirementsResponse(): UsageRequirementsResponse = UsageRequirementsResponse(
    time = time?.map { it.toTimeRequirementResponse() },
    location = location?.toLocationRequirementResponse()
)

internal fun BasicTimeRequirementResponse.toTimeRequirementResponse(): TimeRequirementResponse = TimeRequirementResponse(
    start = start,
    end = end,
    timezone = timezone,
    days = days
)

internal fun BasicLocationRequirementResponse.toLocationRequirementResponse(): LocationRequirementResponse = LocationRequirementResponse(
    latitude = latitude,
    longitude = longitude,
    enabled = enabled,
    radius = radius,
    accuracy = accuracy
)

internal fun BasicUnlockBetweenSettingResponse.toUnlockBetweenSettingResponse(): UnlockBetweenSettingResponse = UnlockBetweenSettingResponse(
    start = start,
    end = end,
    timezone = timezone,
    days = days,
    exceptions = exceptions
)

internal fun BasicLockStateResponse.toLockStateResponse(): LockStateResponse = LockStateResponse(
    connected = connected
)

internal fun BasicUserPublicKeyResponse.toUserPublicKeyResponse(): UserPublicKeyResponse = UserPublicKeyResponse(
    id = id,
    publicKey = publicKey
)

internal fun List<BasicBatchUserPublicKeyResponse>.toBatchUserPublicKeyResponse(): List<BatchUserPublicKeyResponse> = map { user ->
    BatchUserPublicKeyResponse(
        id = user.id,
        email = user.email,
        foreignKey = user.foreignKey,
        phone = user.phone,
        publicKey = user.publicKey
    )
}

internal fun List<BasicShareableLockResponse>.toShareableLockResponse(): List<ShareableLockResponse> = map { lock ->
    ShareableLockResponse(
        id = lock.id,
        name = lock.name
    )
}

internal fun List<BasicUserLockResponse>.toUserLockResponse(): List<UserLockResponse> = map { user ->
    UserLockResponse(
        userId = user.userId,
        email = user.email,
        publicKey = user.publicKey,
        displayName = user.displayName,
        orphan = user.orphan,
        foreign = user.foreign,
        role = user.role,
        start = user.start,
        end = user.end
    )
}

internal fun BasicLockUserResponse.toLockUserResponse(): LockUserResponse = LockUserResponse(
    userId = userId,
    email = email,
    publicKey = publicKey,
    displayName = displayName,
    orphan = orphan,
    foreign = foreign,
    start = start,
    end = end,
    devices = devices.map { it.toLockUserDetailsResponse() }
)

internal fun BasicLockUserDetailsResponse.toLockUserDetailsResponse(): LockUserDetailsResponse = LockUserDetailsResponse(
    deviceId = deviceId,
    role = role,
    start = start,
    end = end
)

internal fun List<BasicAuditResponse>.toAuditResponse(): List<AuditResponse> = map { audit ->
    AuditResponse(
        deviceId = audit.deviceId,
        timestamp = audit.timestamp.toDouble(),
        type = audit.type,
        issuer = audit.issuer.toAuditUserResponse(),
        subject = audit.subject?.toAuditUserResponse(),
        rejectionReason = audit.rejectionReason,
        rejected = audit.rejected
    )
}

internal fun BasicAuditUserResponse.toAuditUserResponse(): AuditUserResponse = AuditUserResponse(
    userId = userId,
    email = email,
    displayName = displayName,
    ip = ip
)