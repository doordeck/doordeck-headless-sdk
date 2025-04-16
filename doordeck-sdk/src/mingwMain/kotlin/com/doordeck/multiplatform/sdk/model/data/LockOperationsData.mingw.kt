package com.doordeck.multiplatform.sdk.model.data

import com.doordeck.multiplatform.sdk.model.data.LockOperations.BaseOperation
import com.doordeck.multiplatform.sdk.model.data.LockOperations.ShareLock
import com.doordeck.multiplatform.sdk.model.data.LockOperations.UnlockBetween
import com.doordeck.multiplatform.sdk.model.common.UserRole
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable
import kotlin.time.Duration.Companion.minutes
import kotlin.uuid.Uuid

@Serializable
data class GetSingleLockData(
   val lockId: String
)

@Serializable
data class GetLockAuditTrailData(
    val lockId: String,
    val start: Int,
    val end: Int
)

@Serializable
data class GetAuditForUserData(
    val userId: String,
    val start: Int,
    val end: Int
)

@Serializable
data class GetUsersForLockData(
    val lockId: String
)

@Serializable
data class GetLocksForUserData(
    val userId: String,
)

@Serializable
data class UpdateLockNameData(
    val lockId: String,
    val name: String? = null
)

@Serializable
data class UpdateLockFavouriteData(
    val lockId: String,
    val favourite: Boolean? = null
)

@Serializable
data class UpdateLockColourData(
    val lockId: String,
    val colour: String? = null
)

@Serializable
data class UpdateLockSettingDefaultNameData(
    val lockId: String,
    val name: String? = null
)

@Serializable
data class SetLockSettingPermittedAddressesData(
    val lockId: String,
    val permittedAddresses: List<String>
)

@Serializable
data class UpdateLockSettingHiddenData(
    val lockId: String,
    val hidden: Boolean
)

@Serializable
data class SetLockSettingTimeRestrictionsData(
    val lockId: String,
    val times: List<TimeRequirementData>
)

@Serializable
data class TimeRequirementData(
    val start: String,
    val end: String,
    val timezone: String,
    val days: List<String>
)

@Serializable
data class UpdateLockSettingLocationRestrictionsData(
    val lockId: String,
    val location: LocationRequirementData? = null
)

@Serializable
data class LocationRequirementData(
    val latitude: Double,
    val longitude: Double,
    val enabled: Boolean,
    val radius: Int,
    val accuracy: Int
)

@Serializable
data class GetUserPublicKeyData(
    val userEmail: String,
    val visitor: Boolean = false
)

@Serializable
data class GetUserPublicKeyByEmailData(
    val email: String
)

@Serializable
data class GetUserPublicKeyByTelephoneData(
    val telephone: String
)

@Serializable
data class GetUserPublicKeyByLocalKeyData(
    val localKey: String
)

@Serializable
data class GetUserPublicKeyByForeignKeyData(
    val foreignKey: String
)

@Serializable
data class GetUserPublicKeyByIdentityData(
    val identity: String
)

@Serializable
data class GetUserPublicKeyByEmailsData(
    val emails: List<String>
)

@Serializable
data class GetUserPublicKeyByTelephonesData(
    val telephones: List<String>
)

@Serializable
data class GetUserPublicKeyByLocalKeysData(
    val localKeys: List<String>
)

@Serializable
data class GetUserPublicKeyByForeignKeysData(
    val foreignKeys: List<String>
)

@Serializable
data class UnlockOperationData(
    val baseOperation: BaseOperationData,
    val directAccessEndpoints: List<String>? = null
)

@Serializable
data class BaseOperationData(
    val userId: String? = null,
    val userCertificateChain: List<String>? = null,
    val userPrivateKey: String? = null,
    val lockId: String,
    val notBefore: Int = Clock.System.now().epochSeconds.toInt(),
    val issuedAt: Int = Clock.System.now().epochSeconds.toInt(),
    val expiresAt: Int = (Clock.System.now() + 1.minutes).epochSeconds.toInt(),
    val jti: String = Uuid.random().toString()
)

@Serializable
data class ShareLockData(
    val targetUserId: String,
    val targetUserRole: UserRole,
    val targetUserPublicKey: String,
    val start: Int? = null,
    val end: Int? = null
)

@Serializable
data class ShareLockOperationData(
    val baseOperation: BaseOperationData,
    val shareLock: ShareLockData
)

@Serializable
data class BatchShareLockOperationData(
    val baseOperation: BaseOperationData,
    val users: List<ShareLockData>
)

@Serializable
data class RevokeAccessToLockOperationData(
    val baseOperation: BaseOperationData,
    val users: List<String>
)

@Serializable
data class UpdateSecureSettingUnlockDurationData(
    val baseOperation: BaseOperationData,
    val unlockDuration: Int
)

@Serializable
data class UnlockBetweenData(
    val start: String,
    val end: String,
    val timezone: String,
    val days: List<String>,
    val exceptions: List<String>? = null
)

@Serializable
data class UpdateSecureSettingUnlockBetweenData(
    val baseOperation: BaseOperationData,
    val unlockBetween: UnlockBetweenData? = null
)

internal fun List<TimeRequirementData>.toTimeRequirementList() = map { it.toTimeRequirement() }

internal fun TimeRequirementData.toTimeRequirement() = LockOperations.TimeRequirement(
    start = start,
    end = end,
    timezone = timezone,
    days = days
)

internal fun LocationRequirementData.toLocationRequirement() = LockOperations.LocationRequirement(
    latitude = latitude,
    longitude = longitude,
    enabled = enabled,
    radius = radius,
    accuracy = accuracy
)

internal fun UnlockOperationData.toUnlockOperation() = LockOperations.UnlockOperation(
    baseOperation = baseOperation.toBaseOperation(),
    directAccessEndpoints = directAccessEndpoints
)

internal fun BaseOperationData.toBaseOperation() = BaseOperation(
    userId = userId,
    userCertificateChain = userCertificateChain,
    userPrivateKey = userPrivateKey?.decodeBase64ToByteArray(),
    lockId = lockId,
    notBefore = notBefore,
    issuedAt = issuedAt,
    expiresAt = expiresAt,
    jti = jti
)

internal fun ShareLockData.toShareLock() = ShareLock(
    targetUserId = targetUserId,
    targetUserRole = targetUserRole,
    targetUserPublicKey = targetUserPublicKey.decodeBase64ToByteArray(),
    start = start,
    end = end
)

internal fun ShareLockOperationData.toShareLockOperation() = LockOperations.ShareLockOperation(
    baseOperation = baseOperation.toBaseOperation(),
    shareLock = shareLock.toShareLock()
)

internal fun BatchShareLockOperationData.toBatchShareLockOperation() = LockOperations.BatchShareLockOperation(
    baseOperation = baseOperation.toBaseOperation(),
    users = users.map { it.toShareLock() }
)

internal fun RevokeAccessToLockOperationData.toRevokeAccessToLockOperation() = LockOperations.RevokeAccessToLockOperation(
    baseOperation = baseOperation.toBaseOperation(),
    users = users
)

internal fun UpdateSecureSettingUnlockDurationData.toUpdateSecureSettingUnlockDuration() = LockOperations.UpdateSecureSettingUnlockDuration(
    baseOperation = baseOperation.toBaseOperation(),
    unlockDuration = unlockDuration
)

internal fun UnlockBetweenData.toUnlockBetween() = UnlockBetween(
    start = start,
    end = end,
    timezone = timezone,
    days = days,
    exceptions = exceptions
)

internal fun UpdateSecureSettingUnlockBetweenData.toUpdateSecureSettingUnlockBetween() = LockOperations.UpdateSecureSettingUnlockBetween(
    baseOperation = baseOperation.toBaseOperation(),
    unlockBetween = unlockBetween?.toUnlockBetween()
)