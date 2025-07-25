package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.model.common.AuditEvent
import com.doordeck.multiplatform.sdk.model.common.CapabilityStatus
import com.doordeck.multiplatform.sdk.model.common.CapabilityType
import com.doordeck.multiplatform.sdk.model.common.DayOfWeek
import com.doordeck.multiplatform.sdk.model.common.UserRole

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

data class UsageRequirementsResponse(
    val time: List<TimeRequirementResponse>? = null,
    val location: LocationRequirementResponse? = null
)

data class TimeRequirementResponse(
    val start: String,
    val end: String,
    val timezone: String,
    val days: List<DayOfWeek>
)

data class LocationRequirementResponse(
    val latitude: Double,
    val longitude: Double,
    val enabled: Boolean,
    val radius: Int,
    val accuracy: Int
)

data class UnlockBetweenSettingResponse(
    val start: String,
    val end: String,
    val timezone: String,
    val days: List<DayOfWeek>,
    val exceptions: List<String>? = null
)

data class LockStateResponse(
    val locked: Boolean,
    val connected: Boolean
)

data class UserPublicKeyResponse(
    val id: String,
    val publicKey: String
)

data class BatchUserPublicKeyResponse(
    val id: String,
    val email: String? = null,
    val foreignKey: String? = null,
    val phone: String? = null,
    val publicKey: String
)

data class ShareableLockResponse(
    val id: String,
    val name: String
)

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

data class LockUserDetailsResponse(
    val deviceId: String,
    val role: UserRole,
    val start: Double? = null,
    val end: Double? = null
)

data class AuditResponse(
    val deviceId: String,
    val timestamp: Double,
    val type: AuditEvent,
    val issuer: AuditIssuerResponse,
    val subject: AuditSubjectResponse? = null,
    val rejectionReason: String? = null,
    val rejected: Boolean
)

data class AuditIssuerResponse(
    val userId: String,
    val email: String? = null,
    val ip: String? = null
)

data class AuditSubjectResponse(
    val userId: String,
    val email: String,
    val displayName: String? = null
)

internal fun List<NetworkLockResponse>.toLockResponse(): List<LockResponse> = map { lock ->
    lock.toLockResponse()
}

internal fun NetworkLockResponse.toLockResponse(): LockResponse = LockResponse(
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

internal fun NetworkLockSettingsResponse.toLockSettingsResponse(): LockSettingsResponse = LockSettingsResponse(
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

internal fun NetworkUsageRequirementsResponse.toUsageRequirementsResponse(): UsageRequirementsResponse = UsageRequirementsResponse(
    time = time?.map { it.toTimeRequirementResponse() },
    location = location?.toLocationRequirementResponse()
)

internal fun NetworkTimeRequirementResponse.toTimeRequirementResponse(): TimeRequirementResponse = TimeRequirementResponse(
    start = start,
    end = end,
    timezone = timezone,
    days = days
)

internal fun NetworkLocationRequirementResponse.toLocationRequirementResponse(): LocationRequirementResponse = LocationRequirementResponse(
    latitude = latitude,
    longitude = longitude,
    enabled = enabled,
    radius = radius,
    accuracy = accuracy
)

internal fun NetworkUnlockBetweenSettingResponse.toUnlockBetweenSettingResponse(): UnlockBetweenSettingResponse = UnlockBetweenSettingResponse(
    start = start,
    end = end,
    timezone = timezone,
    days = days,
    exceptions = exceptions
)

internal fun NetworkLockStateResponse.toLockStateResponse(): LockStateResponse = LockStateResponse(
    locked = locked,
    connected = connected,
)

internal fun NetworkUserPublicKeyResponse.toUserPublicKeyResponse(): UserPublicKeyResponse = UserPublicKeyResponse(
    id = id,
    publicKey = publicKey
)

internal fun List<NetworkBatchUserPublicKeyResponse>.toBatchUserPublicKeyResponse(): List<BatchUserPublicKeyResponse> = map { user ->
    BatchUserPublicKeyResponse(
        id = user.id,
        email = user.email,
        foreignKey = user.foreignKey,
        phone = user.phone,
        publicKey = user.publicKey
    )
}

internal fun List<NetworkShareableLockResponse>.toShareableLockResponse(): List<ShareableLockResponse> = map { lock ->
    ShareableLockResponse(
        id = lock.id,
        name = lock.name
    )
}

internal fun List<NetworkUserLockResponse>.toUserLockResponse(): List<UserLockResponse> = map { user ->
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

internal fun NetworkLockUserResponse.toLockUserResponse(): LockUserResponse = LockUserResponse(
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

internal fun NetworkLockUserDetailsResponse.toLockUserDetailsResponse(): LockUserDetailsResponse = LockUserDetailsResponse(
    deviceId = deviceId,
    role = role,
    start = start,
    end = end
)

internal fun List<NetworkAuditResponse>.toAuditResponse(): List<AuditResponse> = map { audit ->
    AuditResponse(
        deviceId = audit.deviceId,
        timestamp = audit.timestamp.toDouble(),
        type = audit.type,
        issuer = audit.issuer.toAuditIssuerResponse(),
        subject = audit.subject?.toAuditSubjectResponse(),
        rejectionReason = audit.rejectionReason,
        rejected = audit.rejected
    )
}

internal fun NetworkAuditIssuerResponse.toAuditIssuerResponse(): AuditIssuerResponse = AuditIssuerResponse(
    userId = userId,
    email = email,
    ip = ip
)

internal fun NetworkAuditSubjectResponse.toAuditSubjectResponse(): AuditSubjectResponse = AuditSubjectResponse(
    userId = userId,
    email = email,
    displayName = displayName,
)