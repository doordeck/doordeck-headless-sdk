package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.crypto.CryptoManager.toRsaPublicKey
import com.doordeck.multiplatform.sdk.model.common.AuditEvent
import com.doordeck.multiplatform.sdk.model.common.CapabilityStatus
import com.doordeck.multiplatform.sdk.model.common.CapabilityType
import com.doordeck.multiplatform.sdk.model.common.UserRole
import com.doordeck.multiplatform.sdk.util.secondsToDuration
import com.doordeck.multiplatform.sdk.util.toEnumSet
import com.doordeck.multiplatform.sdk.util.toInetAddress
import com.doordeck.multiplatform.sdk.util.toInstant
import com.doordeck.multiplatform.sdk.util.toLocalDate
import com.doordeck.multiplatform.sdk.util.toLocalTime
import com.doordeck.multiplatform.sdk.util.toUri
import com.doordeck.multiplatform.sdk.util.toUuid
import com.doordeck.multiplatform.sdk.util.toZoneId
import java.net.InetAddress
import java.net.URI
import java.security.PublicKey
import java.time.DayOfWeek
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.util.EnumSet
import java.util.UUID

data class LockResponse(
    val id: UUID,
    val name: String,
    val start: Instant? = null,
    val end: Instant? = null,
    val role: UserRole,
    val settings: LockSettingsResponse,
    val state: LockStateResponse,
    val favourite: Boolean
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
    val time: List<TimeRequirementResponse> = emptyList(),
    val location: LocationRequirementResponse? = null
)

data class TimeRequirementResponse(
    val start: LocalTime,
    val end: LocalTime,
    val timezone: ZoneId,
    val days: EnumSet<DayOfWeek>
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
    val days: EnumSet<DayOfWeek>,
    val exceptions: List<LocalDate> = emptyList()
)

data class LockStateResponse(
    val connected: Boolean? = null
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
    val issuer: AuditUserResponse,
    val subject: AuditUserResponse? = null,
    val rejectionReason: String? = null,
    val rejected: Boolean
)

data class AuditUserResponse(
    val userId: UUID,
    val email: String? = null,
    val displayName: String? = null,
    val ip: InetAddress? = null
)

@JvmSynthetic
internal fun List<BasicLockResponse>.toLockResponse(): List<LockResponse> = map { lock ->
    lock.toLockResponse()
}

@JvmSynthetic
internal fun BasicLockResponse.toLockResponse(): LockResponse = LockResponse(
    id = id.toUuid(),
    name = name,
    start = start?.toInstant(),
    end = end?.toInstant(),
    role = role,
    settings = settings.toLockSettingsResponse(),
    state = state.toLockStateResponse(),
    favourite = favourite
)

@JvmSynthetic
internal fun BasicLockSettingsResponse.toLockSettingsResponse(): LockSettingsResponse = LockSettingsResponse(
    unlockTime = unlockTime.toInt().secondsToDuration(),
    permittedAddresses = permittedAddresses.map { it.toInetAddress() },
    defaultName = defaultName,
    usageRequirements = usageRequirements?.toUsageRequirementsResponse(),
    unlockBetweenWindow = unlockBetweenWindow?.toUnlockBetweenSettingResponse(),
    tiles = tiles.map { it.toUuid() },
    hidden = hidden,
    directAccessEndpoints = directAccessEndpoints.map { it.toUri() },
    capabilities = capabilities
)

@JvmSynthetic
internal fun BasicUsageRequirementsResponse.toUsageRequirementsResponse(): UsageRequirementsResponse = UsageRequirementsResponse(
    time = time.map { it.toTimeRequirementResponse() },
    location = location?.toLocationRequirementResponse()
)

@JvmSynthetic
internal fun BasicTimeRequirementResponse.toTimeRequirementResponse(): TimeRequirementResponse = TimeRequirementResponse(
    start = start.toLocalTime(),
    end = end.toLocalTime(),
    timezone = timezone.toZoneId(),
    days = days.map { DayOfWeek.valueOf(it.name) }.toEnumSet()
)

@JvmSynthetic
internal fun BasicLocationRequirementResponse.toLocationRequirementResponse(): LocationRequirementResponse = LocationRequirementResponse(
    latitude = latitude,
    longitude = longitude,
    enabled = enabled,
    radius = radius,
    accuracy = accuracy
)

@JvmSynthetic
internal fun BasicUnlockBetweenSettingResponse.toUnlockBetweenSettingResponse(): UnlockBetweenSettingResponse = UnlockBetweenSettingResponse(
    start = start.toLocalTime(),
    end = end.toLocalTime(),
    timezone = timezone.toZoneId(),
    days = days.map { DayOfWeek.valueOf(it.name) }.toEnumSet(),
    exceptions = exceptions.map { it.toLocalDate() }
)

@JvmSynthetic
internal fun BasicLockStateResponse.toLockStateResponse(): LockStateResponse = LockStateResponse(
    connected = connected
)

@JvmSynthetic
internal fun BasicUserPublicKeyResponse.toUserPublicKeyResponse(): UserPublicKeyResponse = UserPublicKeyResponse(
    id = id.toUuid(),
    publicKey = publicKey.toRsaPublicKey()
)

@JvmSynthetic
internal fun List<BasicBatchUserPublicKeyResponse>.toBatchUserPublicKeyResponse(): List<BatchUserPublicKeyResponse> = map { user ->
    BatchUserPublicKeyResponse(
        id = user.id.toUuid(),
        email = user.email,
        foreignKey = user.foreignKey,
        phone = user.phone,
        publicKey = user.publicKey.toRsaPublicKey(),
    )
}

@JvmSynthetic
internal fun List<BasicShareableLockResponse>.toShareableLockResponse(): List<ShareableLockResponse> = map { lock ->
    ShareableLockResponse(
        id = lock.id.toUuid(),
        name = lock.name
    )
}

@JvmSynthetic
internal fun List<BasicUserLockResponse>.toUserLockResponse(): List<UserLockResponse> = map { user ->
    UserLockResponse(
        userId = user.userId.toUuid(),
        email = user.email,
        publicKey = user.publicKey.toRsaPublicKey(),
        displayName = user.displayName,
        orphan = user.orphan,
        foreign = user.foreign,
        role = user.role,
        start = user.start?.toInstant(),
        end = user.end?.toInstant()
    )
}

@JvmSynthetic
internal fun BasicLockUserResponse.toLockUserResponse(): LockUserResponse = LockUserResponse(
    userId = userId.toUuid(),
    email = email,
    publicKey = publicKey.toRsaPublicKey(),
    displayName = displayName,
    orphan = orphan,
    foreign = foreign,
    start = start?.toInstant(),
    end = end?.toInstant(),
    devices = devices.map { it.toLockUserDetailsResponse() }
)

@JvmSynthetic
internal fun BasicLockUserDetailsResponse.toLockUserDetailsResponse(): LockUserDetailsResponse = LockUserDetailsResponse(
    deviceId = deviceId.toUuid(),
    role = role,
    start = start?.toInstant(),
    end = end?.toInstant()
)

@JvmSynthetic
internal fun List<BasicAuditResponse>.toAuditResponse(): List<AuditResponse> = map { audit ->
    AuditResponse(
        deviceId = audit.deviceId.toUuid(),
        timestamp = audit.timestamp.toInstant(),
        type = audit.type,
        issuer = audit.issuer.toAuditUserResponse(),
        subject = audit.subject?.toAuditUserResponse(),
        rejectionReason = audit.rejectionReason,
        rejected = audit.rejected
    )
}

@JvmSynthetic
internal fun BasicAuditUserResponse.toAuditUserResponse(): AuditUserResponse = AuditUserResponse(
    userId = userId.toUuid(),
    email = email,
    displayName = displayName,
    ip = ip?.toInetAddress()
)