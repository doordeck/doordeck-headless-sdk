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
    val settings: LockSettings,
    val state: LockState,
    val favourite: Boolean,
    val unlockTime: Double? = null
)

data class LockSettings(
    val unlockTime: Double,
    val permittedAddresses: List<String>,
    val defaultName: String,
    val usageRequirements: UsageRequirements? = null,
    val unlockBetweenWindow: UnlockBetweenSetting? = null,
    val tiles: List<String>,
    val hidden: Boolean,
    val directAccessEndpoints: List<String> = emptyList(),
    val capabilities: Map<CapabilityType, CapabilityStatus> = emptyMap()
)

data class UsageRequirements(
    val time: List<TimeRequirement>? = null,
    val location: LocationRequirement? = null
)

data class TimeRequirement(
    val start: String,
    val end: String,
    val timezone: String,
    val days: List<DayOfWeek>
)

data class LocationRequirement(
    val latitude: Double,
    val longitude: Double,
    val enabled: Boolean,
    val radius: Int,
    val accuracy: Int
)

data class UnlockBetweenSetting(
    val start: String,
    val end: String,
    val timezone: String,
    val days: List<DayOfWeek>,
    val exceptions: List<String>? = null
)

data class LockState(
    val locked: Boolean,
    val connected: Boolean
)

data class UserPublicKey(
    val id: String,
    val publicKey: String
)

data class BatchUserPublicKey(
    val id: String,
    val email: String? = null,
    val foreignKey: String? = null,
    val phone: String? = null,
    val publicKey: String
)

data class ShareableLock(
    val id: String,
    val name: String
)

data class UserLock(
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

data class LockUser(
    val userId: String,
    val email: String,
    val publicKey: String,
    val displayName: String? = null,
    val orphan: Boolean,
    val foreign: Boolean,
    val start: Double? = null,
    val end: Double? = null,
    val devices: List<LockUserDetails>
)

data class LockUserDetails(
    val deviceId: String,
    val role: UserRole,
    val start: Double? = null,
    val end: Double? = null
)

data class Audit(
    val deviceId: String,
    val timestamp: Double,
    val type: AuditEvent,
    val issuer: AuditIssuer,
    val subject: AuditSubject? = null,
    val rejectionReason: String? = null,
    val rejected: Boolean
)

data class AuditIssuer(
    val userId: String,
    val email: String? = null,
    val ip: String? = null
)

data class AuditSubject(
    val userId: String,
    val email: String,
    val displayName: String? = null
)

internal fun List<BasicLockResponse>.toLock(): List<LockResponse> = map { lock ->
    lock.toLock()
}

internal fun BasicLockResponse.toLock(): LockResponse = LockResponse(
    id = id,
    name = name,
    colour = colour,
    start = start,
    end = end,
    role = role,
    settings = settings.toLockSettings(),
    state = state.toLockState(),
    favourite = favourite,
    unlockTime = unlockTime
)

internal fun BasicLockSettingsResponse.toLockSettings(): LockSettings = LockSettings(
    unlockTime = unlockTime,
    permittedAddresses = permittedAddresses,
    defaultName = defaultName,
    usageRequirements = usageRequirements?.toUsageRequirements(),
    unlockBetweenWindow = unlockBetweenWindow?.toUnlockBetweenSetting(),
    tiles = tiles,
    hidden = hidden,
    directAccessEndpoints = directAccessEndpoints,
    capabilities = capabilities
)

internal fun BasicUsageRequirementsResponse.toUsageRequirements(): UsageRequirements = UsageRequirements(
    time = time?.map { it.toTimeRequirement() },
    location = location?.toLocationRequirement()
)

internal fun BasicTimeRequirementResponse.toTimeRequirement(): TimeRequirement = TimeRequirement(
    start = start,
    end = end,
    timezone = timezone,
    days = days
)

internal fun BasicLocationRequirementResponse.toLocationRequirement(): LocationRequirement = LocationRequirement(
    latitude = latitude,
    longitude = longitude,
    enabled = enabled,
    radius = radius,
    accuracy = accuracy
)

internal fun BasicUnlockBetweenSettingResponse.toUnlockBetweenSetting(): UnlockBetweenSetting = UnlockBetweenSetting(
    start = start,
    end = end,
    timezone = timezone,
    days = days,
    exceptions = exceptions
)

internal fun BasicLockStateResponse.toLockState(): LockState = LockState(
    locked = locked,
    connected = connected,
)

internal fun BasicUserPublicKeyResponse.toUserPublicKey(): UserPublicKey = UserPublicKey(
    id = id,
    publicKey = publicKey
)

internal fun List<BasicBatchUserPublicKeyResponse>.toBatchUserPublicKey(): List<BatchUserPublicKey> = map { user ->
    BatchUserPublicKey(
        id = user.id,
        email = user.email,
        foreignKey = user.foreignKey,
        phone = user.phone,
        publicKey = user.publicKey
    )
}

internal fun List<BasicShareableLockResponse>.toShareableLock(): List<ShareableLock> = map { lock ->
    ShareableLock(
        id = lock.id,
        name = lock.name
    )
}

internal fun List<BasicUserLockResponse>.toUserLock(): List<UserLock> = map { user ->
    UserLock(
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

internal fun BasicLockUserResponse.toLockUser(): LockUser = LockUser(
    userId = userId,
    email = email,
    publicKey = publicKey,
    displayName = displayName,
    orphan = orphan,
    foreign = foreign,
    start = start,
    end = end,
    devices = devices.map { it.toLockUserDetails() }
)

internal fun BasicLockUserDetailsResponse.toLockUserDetails(): LockUserDetails = LockUserDetails(
    deviceId = deviceId,
    role = role,
    start = start,
    end = end
)

internal fun List<BasicAuditResponse>.toAudit(): List<Audit> = map { audit ->
    Audit(
        deviceId = audit.deviceId,
        timestamp = audit.timestamp.toDouble(),
        type = audit.type,
        issuer = audit.issuer.toAuditIssuer(),
        subject = audit.subject?.toAuditSubject(),
        rejectionReason = audit.rejectionReason,
        rejected = audit.rejected
    )
}

internal fun BasicAuditIssuerResponse.toAuditIssuer(): AuditIssuer = AuditIssuer(
    userId = userId,
    email = email,
    ip = ip
)

internal fun BasicAuditSubjectResponse.toAuditSubject(): AuditSubject = AuditSubject(
    userId = userId,
    email = email,
    displayName = displayName,
)