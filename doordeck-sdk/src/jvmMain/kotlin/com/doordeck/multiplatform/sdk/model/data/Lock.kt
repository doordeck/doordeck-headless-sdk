package com.doordeck.multiplatform.sdk.model.data

import com.doordeck.multiplatform.sdk.crypto.CryptoManager.toPublicKey
import com.doordeck.multiplatform.sdk.model.common.AuditEvent
import com.doordeck.multiplatform.sdk.model.common.CapabilityStatus
import com.doordeck.multiplatform.sdk.model.common.CapabilityType
import com.doordeck.multiplatform.sdk.model.common.DayOfWeek
import com.doordeck.multiplatform.sdk.model.common.UserRole
import com.doordeck.multiplatform.sdk.model.responses.AuditIssuerResponse
import com.doordeck.multiplatform.sdk.model.responses.AuditResponse
import com.doordeck.multiplatform.sdk.model.responses.AuditSubjectResponse
import com.doordeck.multiplatform.sdk.model.responses.BatchUserPublicKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.LocationRequirementResponse
import com.doordeck.multiplatform.sdk.model.responses.LockResponse
import com.doordeck.multiplatform.sdk.model.responses.LockSettingsResponse
import com.doordeck.multiplatform.sdk.model.responses.LockStateResponse
import com.doordeck.multiplatform.sdk.model.responses.LockUserDetailsResponse
import com.doordeck.multiplatform.sdk.model.responses.LockUserResponse
import com.doordeck.multiplatform.sdk.model.responses.ShareableLockResponse
import com.doordeck.multiplatform.sdk.model.responses.TimeRequirementResponse
import com.doordeck.multiplatform.sdk.model.responses.UnlockBetweenSettingResponse
import com.doordeck.multiplatform.sdk.model.responses.UsageRequirementsResponse
import com.doordeck.multiplatform.sdk.model.responses.UserLockResponse
import com.doordeck.multiplatform.sdk.model.responses.UserPublicKeyResponse
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

data class Lock(
    val id: UUID,
    val name: String,
    val colour: String? = null,
    val start: Instant? = null,
    val end: Instant? = null,
    val role: UserRole,
    val settings: LockSettings,
    val state: LockState,
    val favourite: Boolean,
    val unlockTime: Duration? = null
)

data class LockSettings(
    val unlockTime: Duration,
    val permittedAddresses: List<InetAddress>,
    val defaultName: String,
    val usageRequirements: UsageRequirements? = null,
    val unlockBetweenWindow: UnlockBetweenSetting? = null,
    val tiles: List<UUID>,
    val hidden: Boolean,
    val directAccessEndpoints: List<URI> = emptyList(),
    val capabilities: Map<CapabilityType, CapabilityStatus> = emptyMap()
)

data class UsageRequirements(
    val time: List<TimeRequirement>? = null,
    val location: LocationRequirement? = null
)

data class TimeRequirement(
    val start: LocalTime,
    val end: LocalTime,
    val timezone: ZoneId,
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
    val start: LocalTime,
    val end: LocalTime,
    val timezone: ZoneId,
    val days: List<DayOfWeek>,
    val exceptions: List<LocalDate>? = null
)

data class LockState(
    val locked: Boolean,
    val connected: Boolean
)

data class UserPublicKey(
    val id: UUID,
    val publicKey: PublicKey
)

data class BatchUserPublicKey(
    val id: UUID,
    val email: String? = null,
    val foreignKey: String? = null,
    val phone: String? = null,
    val publicKey: PublicKey
)

data class ShareableLock(
    val id: UUID,
    val name: String
)

data class UserLock(
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

data class LockUser(
    val userId: UUID,
    val email: String,
    val publicKey: PublicKey,
    val displayName: String? = null,
    val orphan: Boolean,
    val foreign: Boolean,
    val start: Instant? = null,
    val end: Instant? = null,
    val devices: List<LockUserDetails>
)

data class LockUserDetails(
    val deviceId: UUID,
    val role: UserRole,
    val start: Instant? = null,
    val end: Instant? = null
)

data class Audit(
    val deviceId: UUID,
    val timestamp: Instant,
    val type: AuditEvent,
    val issuer: AuditIssuer,
    val subject: AuditSubject? = null,
    val rejectionReason: String? = null,
    val rejected: Boolean
)

data class AuditIssuer(
    val userId: UUID,
    val email: String? = null,
    val ip: InetAddress? = null
)

data class AuditSubject(
    val userId: UUID,
    val email: String,
    val displayName: String? = null
)

internal fun List<LockResponse>.toLock(): List<Lock> = map { lock ->
    lock.toLock()
}

internal fun LockResponse.toLock(): Lock = Lock(
    id = id.toUUID(),
    name = name,
    colour = colour,
    start = start?.toInstant(),
    end = end?.toInstant(),
    role = role,
    settings = settings.toLockSettings(),
    state = state.toLockState(),
    favourite = favourite,
    unlockTime = unlockTime?.toDuration()
)

internal fun LockSettingsResponse.toLockSettings(): LockSettings = LockSettings(
    unlockTime = unlockTime.toDuration(),
    permittedAddresses = permittedAddresses.map { it.toInetAddress() },
    defaultName = defaultName,
    usageRequirements = usageRequirements?.toUsageRequirements(),
    unlockBetweenWindow = unlockBetweenWindow?.toUnlockBetweenSetting(),
    tiles = tiles.map { it.toUUID() },
    hidden = hidden,
    directAccessEndpoints = directAccessEndpoints.map { it.toUri() },
    capabilities = capabilities
)

internal fun UsageRequirementsResponse.toUsageRequirements(): UsageRequirements = UsageRequirements(
    time = time?.map { it.toTimeRequirement() },
    location = location?.toLocationRequirement()
)

internal fun TimeRequirementResponse.toTimeRequirement(): TimeRequirement = TimeRequirement(
    start = start.toLocalTime(),
    end = end.toLocalTime(),
    timezone = timezone.toZoneId(),
    days = days
)

internal fun LocationRequirementResponse.toLocationRequirement(): LocationRequirement = LocationRequirement(
    latitude = latitude,
    longitude = longitude,
    enabled = enabled,
    radius = radius,
    accuracy = accuracy
)

internal fun UnlockBetweenSettingResponse.toUnlockBetweenSetting(): UnlockBetweenSetting = UnlockBetweenSetting(
    start = start.toLocalTime(),
    end = end.toLocalTime(),
    timezone = timezone.toZoneId(),
    days = days,
    exceptions = exceptions?.map { it.toLocalDate() }
)

internal fun LockStateResponse.toLockState(): LockState = LockState(
    locked = locked,
    connected = connected,
)

internal fun UserPublicKeyResponse.toUserPublicKey(): UserPublicKey = UserPublicKey(
    id = id.toUUID(),
    publicKey = publicKey.decodeBase64ToByteArray().toPublicKey()
)

internal fun List<BatchUserPublicKeyResponse>.toBatchUserPublicKey(): List<BatchUserPublicKey> = map { user ->
    BatchUserPublicKey(
        id = user.id.toUUID(),
        email = user.email,
        foreignKey = user.foreignKey,
        phone = user.phone,
        publicKey = user.publicKey.decodeBase64ToByteArray().toPublicKey(),
    )
}

internal fun List<ShareableLockResponse>.toShareableLock(): List<ShareableLock> = map { lock ->
    ShareableLock(
        id = lock.id.toUUID(),
        name = lock.name
    )
}

internal fun List<UserLockResponse>.toUserLock(): List<UserLock> = map { user ->
    UserLock(
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

internal fun LockUserResponse.toLockUser(): LockUser = LockUser(
    userId = userId.toUUID(),
    email = email,
    publicKey = publicKey.decodeBase64ToByteArray().toPublicKey(),
    displayName = displayName,
    orphan = orphan,
    foreign = foreign,
    start = start?.toInstant(),
    end = end?.toInstant(),
    devices = devices.map { it.toLockUserDetails() }
)

internal fun LockUserDetailsResponse.toLockUserDetails(): LockUserDetails = LockUserDetails(
    deviceId = deviceId.toUUID(),
    role = role,
    start = start?.toInstant(),
    end = end?.toInstant()
)

internal fun List<AuditResponse>.toAudit(): List<Audit> = map { audit ->
    Audit(
        deviceId = audit.deviceId.toUUID(),
        timestamp = audit.timestamp.toInstant(),
        type = audit.type,
        issuer = audit.issuer.toAuditIssuer(),
        subject = audit.subject?.toAuditSubject(),
        rejectionReason = audit.rejectionReason,
        rejected = audit.rejected
    )
}

internal fun AuditIssuerResponse.toAuditIssuer(): AuditIssuer = AuditIssuer(
    userId = userId.toUUID(),
    email = email,
    ip = ip?.toInetAddress()
)

internal fun AuditSubjectResponse.toAuditSubject(): AuditSubject = AuditSubject(
    userId = userId.toUUID(),
    email = email,
    displayName = displayName,
)