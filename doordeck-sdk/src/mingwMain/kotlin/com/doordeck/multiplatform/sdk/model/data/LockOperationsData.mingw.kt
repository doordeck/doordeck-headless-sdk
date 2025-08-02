package com.doordeck.multiplatform.sdk.model.data

import com.doordeck.multiplatform.sdk.model.common.DayOfWeek
import com.doordeck.multiplatform.sdk.model.common.UserRole
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable
import kotlin.time.Duration.Companion.minutes
import kotlin.uuid.Uuid

@Serializable
internal data class GetSingleLockData(
   val lockId: String
)

@Serializable
internal data class GetLockAuditTrailData(
    val lockId: String,
    val start: Long,
    val end: Long
)

@Serializable
internal data class GetAuditForUserData(
    val userId: String,
    val start: Long,
    val end: Long
)

@Serializable
internal data class GetUsersForLockData(
    val lockId: String
)

@Serializable
internal data class GetLocksForUserData(
    val userId: String,
)

@Serializable
internal data class UpdateLockNameData(
    val lockId: String,
    val name: String? = null
)

@Serializable
internal data class UpdateLockFavouriteData(
    val lockId: String,
    val favourite: Boolean? = null
)

@Serializable
internal data class UpdateLockColourData(
    val lockId: String,
    val colour: String? = null
)

@Serializable
internal data class UpdateLockSettingDefaultNameData(
    val lockId: String,
    val name: String? = null
)

@Serializable
internal data class SetLockSettingPermittedAddressesData(
    val lockId: String,
    val permittedAddresses: List<String>
)

@Serializable
internal data class UpdateLockSettingHiddenData(
    val lockId: String,
    val hidden: Boolean
)

@Serializable
internal data class SetLockSettingTimeRestrictionsData(
    val lockId: String,
    val times: List<TimeRequirementData>
)

@Serializable
internal data class TimeRequirementData(
    val start: String,
    val end: String,
    val timezone: String,
    val days: List<String>
)

@Serializable
internal data class UpdateLockSettingLocationRestrictionsData(
    val lockId: String,
    val location: LocationRequirementData? = null
)

@Serializable
internal data class LocationRequirementData(
    val latitude: Double,
    val longitude: Double,
    val enabled: Boolean,
    val radius: Int,
    val accuracy: Int
)

@Serializable
internal data class GetUserPublicKeyData(
    val userEmail: String,
    val visitor: Boolean = false
)

@Serializable
internal data class GetUserPublicKeyByEmailData(
    val email: String
)

@Serializable
internal data class GetUserPublicKeyByTelephoneData(
    val telephone: String
)

@Serializable
internal data class GetUserPublicKeyByLocalKeyData(
    val localKey: String
)

@Serializable
internal data class GetUserPublicKeyByForeignKeyData(
    val foreignKey: String
)

@Serializable
internal data class GetUserPublicKeyByIdentityData(
    val identity: String
)

@Serializable
internal data class GetUserPublicKeyByEmailsData(
    val emails: List<String>
)

@Serializable
internal data class GetUserPublicKeyByTelephonesData(
    val telephones: List<String>
)

@Serializable
internal data class GetUserPublicKeyByLocalKeysData(
    val localKeys: List<String>
)

@Serializable
internal data class GetUserPublicKeyByForeignKeysData(
    val foreignKeys: List<String>
)

@Serializable
internal data class UnlockOperationData(
    val baseOperation: BaseOperationData,
    val directAccessEndpoints: List<String>? = null
)

@Serializable
internal data class BaseOperationData(
    val userId: String? = null,
    val userCertificateChain: List<String>? = null,
    val userPrivateKey: String? = null,
    val lockId: String,
    val notBefore: Long = Clock.System.now().epochSeconds,
    val issuedAt: Long = Clock.System.now().epochSeconds,
    val expiresAt: Long = (Clock.System.now() + 1.minutes).epochSeconds,
    val jti: String = Uuid.random().toString()
)

@Serializable
internal data class ShareLockData(
    val targetUserId: String,
    val targetUserRole: UserRole,
    val targetUserPublicKey: String,
    val start: Long? = null,
    val end: Long? = null
)

@Serializable
internal data class ShareLockOperationData(
    val baseOperation: BaseOperationData,
    val shareLock: ShareLockData
)

@Serializable
internal data class BatchShareLockOperationData(
    val baseOperation: BaseOperationData,
    val users: List<ShareLockData>
)

@Serializable
internal data class RevokeAccessToLockOperationData(
    val baseOperation: BaseOperationData,
    val users: List<String>
)

@Serializable
internal data class UpdateSecureSettingUnlockDurationData(
    val baseOperation: BaseOperationData,
    val unlockDuration: Int
)

@Serializable
internal data class UnlockBetweenData(
    val start: String,
    val end: String,
    val timezone: String,
    val days: List<DayOfWeek>,
    val exceptions: List<String>? = null
)

@Serializable
internal data class UpdateSecureSettingUnlockBetweenData(
    val baseOperation: BaseOperationData,
    val unlockBetween: UnlockBetweenData? = null
)

internal fun List<TimeRequirementData>.toTimeRequirementList() = map { it.toTimeRequirement() }

internal fun TimeRequirementData.toTimeRequirement() = BasicTimeRequirement(
    start = start,
    end = end,
    timezone = timezone,
    days = days.map { DayOfWeek.valueOf(it) }
)

internal fun LocationRequirementData.toLocationRequirement() = BasicLocationRequirement(
    latitude = latitude,
    longitude = longitude,
    enabled = enabled,
    radius = radius,
    accuracy = accuracy
)

internal fun UnlockOperationData.toUnlockOperation() = BasicUnlockOperation(
    baseOperation = baseOperation.toBaseOperation(),
    directAccessEndpoints = directAccessEndpoints
)

internal fun BaseOperationData.toBaseOperation() = BasicBaseOperation(
    userId = userId,
    userCertificateChain = userCertificateChain,
    userPrivateKey = userPrivateKey?.decodeBase64ToByteArray(),
    lockId = lockId,
    notBefore = notBefore,
    issuedAt = issuedAt,
    expiresAt = expiresAt,
    jti = jti
)

internal fun ShareLockData.toShareLock() = BasicShareLock(
    targetUserId = targetUserId,
    targetUserRole = targetUserRole,
    targetUserPublicKey = targetUserPublicKey.decodeBase64ToByteArray(),
    start = start,
    end = end
)

internal fun ShareLockOperationData.toShareLockOperation() = BasicShareLockOperation(
    baseOperation = baseOperation.toBaseOperation(),
    shareLock = shareLock.toShareLock()
)

internal fun BatchShareLockOperationData.toBatchShareLockOperation() = BasicBatchShareLockOperation(
    baseOperation = baseOperation.toBaseOperation(),
    users = users.map { it.toShareLock() }
)

internal fun RevokeAccessToLockOperationData.toRevokeAccessToLockOperation() = BasicRevokeAccessToLockOperation(
    baseOperation = baseOperation.toBaseOperation(),
    users = users
)

internal fun UpdateSecureSettingUnlockDurationData.toUpdateSecureSettingUnlockDuration() = BasicUpdateSecureSettingUnlockDuration(
    baseOperation = baseOperation.toBaseOperation(),
    unlockDuration = unlockDuration
)

internal fun UnlockBetweenData.toUnlockBetween() = BasicUnlockBetween(
    start = start,
    end = end,
    timezone = timezone,
    days = days,
    exceptions = exceptions
)

internal fun UpdateSecureSettingUnlockBetweenData.toUpdateSecureSettingUnlockBetween() = BasicUpdateSecureSettingUnlockBetween(
    baseOperation = baseOperation.toBaseOperation(),
    unlockBetween = unlockBetween?.toUnlockBetween()
)