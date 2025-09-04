package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.model.common.AuditEvent
import com.doordeck.multiplatform.sdk.model.common.CapabilityStatus
import com.doordeck.multiplatform.sdk.model.common.CapabilityType
import com.doordeck.multiplatform.sdk.model.common.DayOfWeek
import com.doordeck.multiplatform.sdk.model.common.UserRole
import com.doordeck.multiplatform.sdk.util.toNsDate
import com.doordeck.multiplatform.sdk.util.toNsTimeZone
import com.doordeck.multiplatform.sdk.util.toNsUuid
import platform.Foundation.NSDate
import platform.Foundation.NSDateComponents
import platform.Foundation.NSTimeInterval
import platform.Foundation.NSTimeZone
import platform.Foundation.NSUUID

data class LockResponse(
    val id: NSUUID,
    val name: String,
    val start: NSDate? = null,
    val end: NSDate? = null,
    val role: UserRole,
    val settings: LockSettingsResponse,
    val state: LockStateResponse,
    val favourite: Boolean
)

data class LockSettingsResponse(
    val unlockTime: NSTimeInterval,
    val permittedAddresses: List<String>,
    val defaultName: String,
    val usageRequirements: UsageRequirementsResponse? = null,
    val unlockBetweenWindow: UnlockBetweenSettingResponse? = null,
    val tiles: List<NSUUID>,
    val hidden: Boolean,
    val directAccessEndpoints: List<String> = emptyList(),
    val capabilities: Map<CapabilityType, CapabilityStatus> = emptyMap()
)

data class UsageRequirementsResponse(
    val time: List<TimeRequirementResponse> = emptyList(),
    val location: LocationRequirementResponse? = null
)

data class TimeRequirementResponse(
    val start: NSDateComponents,
    val end: NSDateComponents,
    val timezone: NSTimeZone,
    val days: Set<DayOfWeek>
)

data class LocationRequirementResponse(
    val latitude: Double,
    val longitude: Double,
    val enabled: Boolean,
    val radius: Int,
    val accuracy: Int
)

data class UnlockBetweenSettingResponse(
    val start: NSDateComponents,
    val end: NSDateComponents,
    val timezone: NSTimeZone,
    val days: Set<DayOfWeek>,
    val exceptions: List<NSDateComponents> = emptyList()
)

data class LockStateResponse(
    val connected: Boolean? = null
)

data class UserPublicKeyResponse(
    val id: NSUUID,
    val publicKey: String
)

data class BatchUserPublicKeyResponse(
    val id: NSUUID,
    val email: String? = null,
    val foreignKey: String? = null,
    val phone: String? = null,
    val publicKey: String
)

data class ShareableLockResponse(
    val id: NSUUID,
    val name: String
)

data class UserLockResponse(
    val userId: NSUUID,
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
    val userId: NSUUID,
    val email: String,
    val publicKey: String,
    val displayName: String? = null,
    val orphan: Boolean,
    val foreign: Boolean,
    val start: NSDate? = null,
    val end: NSDate? = null,
    val devices: List<LockUserDetailsResponse>
)

data class LockUserDetailsResponse(
    val deviceId: NSUUID,
    val role: UserRole,
    val start: NSDate? = null,
    val end: NSDate? = null
)

data class AuditResponse(
    val deviceId: NSUUID,
    val timestamp: NSDate,
    val type: AuditEvent,
    val issuer: AuditUserResponse,
    val subject: AuditUserResponse? = null,
    val rejectionReason: String? = null,
    val rejected: Boolean
)

data class AuditUserResponse(
    val userId: NSUUID,
    val email: String? = null,
    val displayName: String? = null,
    val ip: String? = null
)

internal fun List<BasicLockResponse>.toLockResponse(): List<LockResponse> = map { lock ->
    lock.toLockResponse()
}

internal fun BasicLockResponse.toLockResponse(): LockResponse = LockResponse(
    id = id.toNsUuid(),
    name = name,
    start = start?.toNsDate(),
    end = end?.toNsDate(),
    role = role,
    settings = settings.toLockSettingsResponse(),
    state = state.toLockStateResponse(),
    favourite = favourite
)

internal fun BasicLockSettingsResponse.toLockSettingsResponse(): LockSettingsResponse = LockSettingsResponse(
    unlockTime = unlockTime,
    permittedAddresses = permittedAddresses,
    defaultName = defaultName,
    usageRequirements = usageRequirements?.toUsageRequirementsResponse(),
    unlockBetweenWindow = unlockBetweenWindow?.toUnlockBetweenSettingResponse(),
    tiles = tiles.map { it.toNsUuid() },
    hidden = hidden,
    directAccessEndpoints = directAccessEndpoints,
    capabilities = capabilities
)

internal fun BasicUsageRequirementsResponse.toUsageRequirementsResponse(): UsageRequirementsResponse = UsageRequirementsResponse(
    time = time.map { it.toTimeRequirementResponse() },
    location = location?.toLocationRequirementResponse()
)

internal fun BasicTimeRequirementResponse.toTimeRequirementResponse(): TimeRequirementResponse = TimeRequirementResponse(
    start = start,
    end = end,
    timezone = timezone.toNsTimeZone(),
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
    timezone = timezone.toNsTimeZone(),
    days = days,
    exceptions = exceptions
)

internal fun BasicLockStateResponse.toLockStateResponse(): LockStateResponse = LockStateResponse(
    connected = connected
)

internal fun BasicUserPublicKeyResponse.toUserPublicKeyResponse(): UserPublicKeyResponse = UserPublicKeyResponse(
    id = id.toNsUuid(),
    publicKey = publicKey
)

internal fun List<BasicBatchUserPublicKeyResponse>.toBatchUserPublicKeyResponse(): List<BatchUserPublicKeyResponse> = map { user ->
    BatchUserPublicKeyResponse(
        id = user.id.toNsUuid(),
        email = user.email,
        foreignKey = user.foreignKey,
        phone = user.phone,
        publicKey = user.publicKey
    )
}

internal fun List<BasicShareableLockResponse>.toShareableLockResponse(): List<ShareableLockResponse> = map { lock ->
    ShareableLockResponse(
        id = lock.id.toNsUuid(),
        name = lock.name
    )
}

internal fun List<BasicUserLockResponse>.toUserLockResponse(): List<UserLockResponse> = map { user ->
    UserLockResponse(
        userId = user.userId.toNsUuid(),
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
    userId = userId.toNsUuid(),
    email = email,
    publicKey = publicKey,
    displayName = displayName,
    orphan = orphan,
    foreign = foreign,
    start = start?.toNsDate(),
    end = end?.toNsDate(),
    devices = devices.map { it.toLockUserDetailsResponse() }
)

internal fun BasicLockUserDetailsResponse.toLockUserDetailsResponse(): LockUserDetailsResponse = LockUserDetailsResponse(
    deviceId = deviceId.toNsUuid(),
    role = role,
    start = start?.toNsDate(),
    end = end?.toNsDate()
)

internal fun List<BasicAuditResponse>.toAuditResponse(): List<AuditResponse> = map { audit ->
    AuditResponse(
        deviceId = audit.deviceId.toNsUuid(),
        timestamp = audit.timestamp.toNsDate(),
        type = audit.type,
        issuer = audit.issuer.toAuditUserResponse(),
        subject = audit.subject?.toAuditUserResponse(),
        rejectionReason = audit.rejectionReason,
        rejected = audit.rejected
    )
}

internal fun BasicAuditUserResponse.toAuditUserResponse(): AuditUserResponse = AuditUserResponse(
    userId = userId.toNsUuid(),
    email = email,
    displayName = displayName,
    ip = ip
)