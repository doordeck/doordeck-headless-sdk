package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.crypto.CryptoManager.toPublicKey
import com.doordeck.multiplatform.sdk.model.common.AuditEvent
import com.doordeck.multiplatform.sdk.model.common.CapabilityStatus
import com.doordeck.multiplatform.sdk.model.common.CapabilityType
import com.doordeck.multiplatform.sdk.model.common.DayOfWeek
import com.doordeck.multiplatform.sdk.model.common.UserRole
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.toInetAddress
import com.doordeck.multiplatform.sdk.util.toInstant
import com.doordeck.multiplatform.sdk.util.toUUID
import com.doordeck.multiplatform.sdk.util.toZoneId
import com.doordeck.multiplatform.sdk.util.toDuration
import com.doordeck.multiplatform.sdk.util.toLocalDate
import com.doordeck.multiplatform.sdk.util.toLocalTime
import com.doordeck.multiplatform.sdk.util.toUri
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import java.net.InetAddress
import java.net.URI
import java.security.PublicKey
import java.time.ZoneId
import java.util.UUID
import kotlin.time.Duration
import kotlin.time.Instant

data class LockResponse(
    val id: UUID,
    val name: String,
    val colour: String? = null,
    val start: Instant? = null,
    val end: Instant? = null,
    val role: UserRole,
    val settings: LockSettingsResponse,
    val state: LockStateResponse,
    val favourite: Boolean,
    val unlockTime: Duration? = null
)

data class LockSettingsResponse(
    val unlockTime: Duration,
    val permittedAddresses: List<InetAddress>,
    val defaultName: String,
    val usageRequirements: UsageRequirementsResponse? = null,
    val unlockBetweenWindow: UnlockBetweenSettingResponse? = null,
    val tiles: List<UUID>,
    val hidden: Boolean,
    val directAccessEndpoints: List<URI> = emptyList(),
    val capabilities: Map<CapabilityType, CapabilityStatus> = emptyMap()
)

data class UsageRequirementsResponse(
    val time: List<TimeRequirementResponse>? = null,
    val location: LocationRequirementResponse? = null
)

data class TimeRequirementResponse(
    val start: LocalTime,
    val end: LocalTime,
    val timezone: ZoneId,
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
    val start: LocalTime,
    val end: LocalTime,
    val timezone: ZoneId,
    val days: List<DayOfWeek>,
    val exceptions: List<LocalDate>? = null
)

data class LockStateResponse(
    val locked: Boolean,
    val connected: Boolean
)

data class UserPublicKeyResponse(
    val id: UUID,
    val publicKey: PublicKey
)

data class BatchUserPublicKeyResponse(
    val id: UUID,
    val email: String? = null,
    val foreignKey: String? = null,
    val phone: String? = null,
    val publicKey: PublicKey
)

data class ShareableLockResponse(
    val id: UUID,
    val name: String
)

data class UserLockResponse(
    val userId: UUID,
    val email: String,
    val publicKey: PublicKey,
    val displayName: String? = null,
    val orphan: Boolean,
    val foreign: Boolean,
    val role: UserRole,
    val start: Instant? = null,
    val end: Instant? = null
)

data class LockUserResponse(
    val userId: UUID,
    val email: String,
    val publicKey: PublicKey,
    val displayName: String? = null,
    val orphan: Boolean,
    val foreign: Boolean,
    val start: Instant? = null,
    val end: Instant? = null,
    val devices: List<LockUserDetailsResponse>
)

data class LockUserDetailsResponse(
    val deviceId: UUID,
    val role: UserRole,
    val start: Instant? = null,
    val end: Instant? = null
)

data class AuditResponse(
    val deviceId: UUID,
    val timestamp: Instant,
    val type: AuditEvent,
    val issuer: AuditIssuerResponse,
    val subject: AuditSubjectResponse? = null,
    val rejectionReason: String? = null,
    val rejected: Boolean
)

data class AuditIssuerResponse(
    val userId: UUID,
    val email: String? = null,
    val ip: InetAddress? = null
)

data class AuditSubjectResponse(
    val userId: UUID,
    val email: String,
    val displayName: String? = null
)

internal fun List<NetworkLockResponse>.toLockResponse(): List<LockResponse> = map { lock ->
    lock.toLockResponse()
}

internal fun NetworkLockResponse.toLockResponse(): LockResponse = LockResponse(
    id = id.toUUID(),
    name = name,
    colour = colour,
    start = start?.toInstant(),
    end = end?.toInstant(),
    role = role,
    settings = settings.toLockSettingsResponse(),
    state = state.toLockStateResponse(),
    favourite = favourite,
    unlockTime = unlockTime?.toDuration()
)

internal fun NetworkLockSettingsResponse.toLockSettingsResponse(): LockSettingsResponse = LockSettingsResponse(
    unlockTime = unlockTime.toDuration(),
    permittedAddresses = permittedAddresses.map { it.toInetAddress() },
    defaultName = defaultName,
    usageRequirements = usageRequirements?.toUsageRequirementsResponse(),
    unlockBetweenWindow = unlockBetweenWindow?.toUnlockBetweenSettingResponse(),
    tiles = tiles.map { it.toUUID() },
    hidden = hidden,
    directAccessEndpoints = directAccessEndpoints.map { it.toUri() },
    capabilities = capabilities
)

internal fun NetworkUsageRequirementsResponse.toUsageRequirementsResponse(): UsageRequirementsResponse = UsageRequirementsResponse(
    time = time?.map { it.toTimeRequirementResponse() },
    location = location?.toLocationRequirementResponse()
)

internal fun NetworkTimeRequirementResponse.toTimeRequirementResponse(): TimeRequirementResponse = TimeRequirementResponse(
    start = start.toLocalTime(),
    end = end.toLocalTime(),
    timezone = timezone.toZoneId(),
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
    start = start.toLocalTime(),
    end = end.toLocalTime(),
    timezone = timezone.toZoneId(),
    days = days,
    exceptions = exceptions?.map { it.toLocalDate() }
)

internal fun NetworkLockStateResponse.toLockStateResponse(): LockStateResponse = LockStateResponse(
    locked = locked,
    connected = connected,
)

internal fun NetworkUserPublicKeyResponse.toUserPublicKeyResponse(): UserPublicKeyResponse = UserPublicKeyResponse(
    id = id.toUUID(),
    publicKey = publicKey.decodeBase64ToByteArray().toPublicKey()
)

internal fun List<NetworkBatchUserPublicKeyResponse>.toBatchUserPublicKeyResponse(): List<BatchUserPublicKeyResponse> = map { user ->
    BatchUserPublicKeyResponse(
        id = user.id.toUUID(),
        email = user.email,
        foreignKey = user.foreignKey,
        phone = user.phone,
        publicKey = user.publicKey.decodeBase64ToByteArray().toPublicKey(),
    )
}

internal fun List<NetworkShareableLockResponse>.toShareableLockResponse(): List<ShareableLockResponse> = map { lock ->
    ShareableLockResponse(
        id = lock.id.toUUID(),
        name = lock.name
    )
}

internal fun List<NetworkUserLockResponse>.toUserLockResponse(): List<UserLockResponse> = map { user ->
    UserLockResponse(
        userId = user.userId.toUUID(),
        email = user.email,
        publicKey = user.publicKey.decodeBase64ToByteArray().toPublicKey(),
        displayName = user.displayName,
        orphan = user.orphan,
        foreign = user.foreign,
        role = user.role,
        start = user.start?.toInstant(),
        end = user.end?.toInstant()
    )
}

internal fun NetworkLockUserResponse.toLockUserResponse(): LockUserResponse = LockUserResponse(
    userId = userId.toUUID(),
    email = email,
    publicKey = publicKey.decodeBase64ToByteArray().toPublicKey(),
    displayName = displayName,
    orphan = orphan,
    foreign = foreign,
    start = start?.toInstant(),
    end = end?.toInstant(),
    devices = devices.map { it.toLockUserDetailsResponse() }
)

internal fun NetworkLockUserDetailsResponse.toLockUserDetailsResponse(): LockUserDetailsResponse = LockUserDetailsResponse(
    deviceId = deviceId.toUUID(),
    role = role,
    start = start?.toInstant(),
    end = end?.toInstant()
)

internal fun List<NetworkAuditResponse>.toAuditResponse(): List<AuditResponse> = map { audit ->
    AuditResponse(
        deviceId = audit.deviceId.toUUID(),
        timestamp = audit.timestamp.toInstant(),
        type = audit.type,
        issuer = audit.issuer.toAuditIssuerResponse(),
        subject = audit.subject?.toAuditSubjectResponse(),
        rejectionReason = audit.rejectionReason,
        rejected = audit.rejected
    )
}

internal fun NetworkAuditIssuerResponse.toAuditIssuerResponse(): AuditIssuerResponse = AuditIssuerResponse(
    userId = userId.toUUID(),
    email = email,
    ip = ip?.toInetAddress()
)

internal fun NetworkAuditSubjectResponse.toAuditSubjectResponse(): AuditSubjectResponse = AuditSubjectResponse(
    userId = userId.toUUID(),
    email = email,
    displayName = displayName,
)