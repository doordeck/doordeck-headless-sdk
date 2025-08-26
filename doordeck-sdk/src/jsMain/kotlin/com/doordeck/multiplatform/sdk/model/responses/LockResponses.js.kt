package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.model.common.AuditEvent
import com.doordeck.multiplatform.sdk.model.common.CapabilityStatus
import com.doordeck.multiplatform.sdk.model.common.CapabilityType
import com.doordeck.multiplatform.sdk.model.common.UserRole
import com.doordeck.multiplatform.sdk.util.emptyJsArray
import com.doordeck.multiplatform.sdk.util.toJsArray
import kotlin.js.collections.JsArray

@JsExport
data class LockResponse(
    val id: String,
    val name: String,
    val start: String? = null,
    val end: String? = null,
    val role: UserRole,
    val settings: LockSettingsResponse,
    val state: LockStateResponse,
    val favourite: Boolean
)

@JsExport
data class LockSettingsResponse(
    val unlockTime: Double,
    val permittedAddresses: JsArray<String>,
    val defaultName: String,
    val usageRequirements: UsageRequirementsResponse? = null,
    val unlockBetweenWindow: UnlockBetweenSettingResponse? = null,
    val tiles: JsArray<String>,
    val hidden: Boolean,
    val directAccessEndpoints: JsArray<String> = emptyJsArray(),
    val capabilities: Map<CapabilityType, CapabilityStatus> = emptyMap()
)

@JsExport
data class UsageRequirementsResponse(
    val time: JsArray<TimeRequirementResponse> = emptyJsArray(),
    val location: LocationRequirementResponse? = null
)

@JsExport
data class TimeRequirementResponse(
    val start: String,
    val end: String,
    val timezone: String,
    val days: JsArray<String>
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
    val days: JsArray<String>,
    val exceptions: JsArray<String> = emptyJsArray()
)

@JsExport
data class LockStateResponse(
    val connected: Boolean? = null
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
    val devices: JsArray<LockUserDetailsResponse>
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

internal fun List<BasicLockResponse>.toLockResponse(): JsArray<LockResponse> = map { lock ->
    lock.toLockResponse()
}.toJsArray()

internal fun BasicLockResponse.toLockResponse(): LockResponse = LockResponse(
    id = id,
    name = name,
    start = start,
    end = end,
    role = role,
    settings = settings.toLockSettingsResponse(),
    state = state.toLockStateResponse(),
    favourite = favourite
)

internal fun BasicLockSettingsResponse.toLockSettingsResponse(): LockSettingsResponse = LockSettingsResponse(
    unlockTime = unlockTime,
    permittedAddresses = permittedAddresses.toJsArray(),
    defaultName = defaultName,
    usageRequirements = usageRequirements?.toUsageRequirementsResponse(),
    unlockBetweenWindow = unlockBetweenWindow?.toUnlockBetweenSettingResponse(),
    tiles = tiles.toJsArray(),
    hidden = hidden,
    directAccessEndpoints = directAccessEndpoints.toJsArray(),
    capabilities = capabilities
)

internal fun BasicUsageRequirementsResponse.toUsageRequirementsResponse(): UsageRequirementsResponse = UsageRequirementsResponse(
    time = time.map { it.toTimeRequirementResponse() }.toJsArray(),
    location = location?.toLocationRequirementResponse()
)

internal fun BasicTimeRequirementResponse.toTimeRequirementResponse(): TimeRequirementResponse = TimeRequirementResponse(
    start = start,
    end = end,
    timezone = timezone,
    days = days.toList().map { it.name }.toJsArray()
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
    days = days.toList().map { it.name }.toJsArray(),
    exceptions = exceptions.toJsArray()
)

internal fun BasicLockStateResponse.toLockStateResponse(): LockStateResponse = LockStateResponse(
    connected = connected
)

internal fun BasicUserPublicKeyResponse.toUserPublicKeyResponse(): UserPublicKeyResponse = UserPublicKeyResponse(
    id = id,
    publicKey = publicKey
)

internal fun List<BasicBatchUserPublicKeyResponse>.toBatchUserPublicKeyResponse(): JsArray<BatchUserPublicKeyResponse> = map { user ->
    BatchUserPublicKeyResponse(
        id = user.id,
        email = user.email,
        foreignKey = user.foreignKey,
        phone = user.phone,
        publicKey = user.publicKey
    )
}.toJsArray()

internal fun List<BasicShareableLockResponse>.toShareableLockResponse(): JsArray<ShareableLockResponse> = map { lock ->
    ShareableLockResponse(
        id = lock.id,
        name = lock.name
    )
}.toJsArray()

internal fun List<BasicUserLockResponse>.toUserLockResponse(): JsArray<UserLockResponse> = map { user ->
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
}.toJsArray()

internal fun BasicLockUserResponse.toLockUserResponse(): LockUserResponse = LockUserResponse(
    userId = userId,
    email = email,
    publicKey = publicKey,
    displayName = displayName,
    orphan = orphan,
    foreign = foreign,
    start = start,
    end = end,
    devices = devices.map { it.toLockUserDetailsResponse() }.toJsArray()
)

internal fun BasicLockUserDetailsResponse.toLockUserDetailsResponse(): LockUserDetailsResponse = LockUserDetailsResponse(
    deviceId = deviceId,
    role = role,
    start = start,
    end = end
)

internal fun List<BasicAuditResponse>.toAuditResponse(): JsArray<AuditResponse> = map { audit ->
    AuditResponse(
        deviceId = audit.deviceId,
        timestamp = audit.timestamp.toDouble(),
        type = audit.type,
        issuer = audit.issuer.toAuditUserResponse(),
        subject = audit.subject?.toAuditUserResponse(),
        rejectionReason = audit.rejectionReason,
        rejected = audit.rejected
    )
}.toJsArray()

internal fun BasicAuditUserResponse.toAuditUserResponse(): AuditUserResponse = AuditUserResponse(
    userId = userId,
    email = email,
    displayName = displayName,
    ip = ip
)