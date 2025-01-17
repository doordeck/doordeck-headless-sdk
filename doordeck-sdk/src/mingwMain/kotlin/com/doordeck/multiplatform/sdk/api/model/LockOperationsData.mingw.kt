package com.doordeck.multiplatform.sdk.api.model

import com.doordeck.multiplatform.sdk.api.model.LockOperations.BaseOperation
import com.doordeck.multiplatform.sdk.api.model.LockOperations.ShareLock
import com.doordeck.multiplatform.sdk.api.model.LockOperations.UnlockBetween
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import kotlinx.datetime.Clock
import kotlinx.serialization.Serializable
import kotlin.time.Duration.Companion.minutes
import kotlin.uuid.Uuid

@Serializable
class GetSingleLockData(
   val lockId: String
)

@Serializable
class GetLockAuditTrailData(
    val lockId: String,
    val start: Int,
    val end: Int
)

@Serializable
class GetAuditForUserData(
    val userId: String,
    val start: Int,
    val end: Int
)

@Serializable
class GetUsersForLockData(
    val lockId: String
)

@Serializable
class GetLocksForUserData(
    val userId: String,
)

@Serializable
class UpdateLockNameData(
    val lockId: String,
    val name: String? = null
)

@Serializable
class UpdateLockFavouriteData(
    val lockId: String,
    val favourite: Boolean? = null
)

@Serializable
class UpdateLockColourData(
    val lockId: String,
    val colour: String? = null
)

@Serializable
class UpdateLockSettingDefaultNameData(
    val lockId: String,
    val name: String? = null
)

@Serializable
class SetLockSettingPermittedAddressesData(
    val lockId: String,
    val permittedAddresses: List<String>
)

@Serializable
class UpdateLockSettingHiddenData(
    val lockId: String,
    val hidden: Boolean
)

@Serializable
class SetLockSettingTimeRestrictionsData(
    val lockId: String,
    val times: List<TimeRequirementData>
)

@Serializable
class TimeRequirementData(
    val start: String,
    val end: String,
    val timezone: String,
    val days: List<String>
)

@Serializable
class UpdateLockSettingLocationRestrictionsData(
    val lockId: String,
    val location: LocationRequirementData? = null
)

@Serializable
class LocationRequirementData(
    val latitude: Double,
    val longitude: Double,
    val enabled: Boolean? = null,
    val radius: Int? = null,
    val accuracy: Int? = null
)

@Serializable
class GetUserPublicKeyData(
    val userEmail: String,
    val visitor: Boolean = false
)

@Serializable
class GetUserPublicKeyByEmailData(
    val email: String
)

@Serializable
class GetUserPublicKeyByTelephoneData(
    val telephone: String
)

@Serializable
class GetUserPublicKeyByLocalKeyData(
    val localKey: String
)

@Serializable
class GetUserPublicKeyByForeignKeyData(
    val foreignKey: String
)

@Serializable
class GetUserPublicKeyByIdentityData(
    val identity: String
)

@Serializable
class GetUserPublicKeyByEmailsData(
    val emails: List<String>
)

@Serializable
class GetUserPublicKeyByTelephonesData(
    val telephones: List<String>
)

@Serializable
class GetUserPublicKeyByLocalKeysData(
    val localKeys: List<String>
)

@Serializable
class GetUserPublicKeyByForeignKeysData(
    val foreignKeys: List<String>
)

@Serializable
class UnlockOperationData(
    val baseOperation: BaseOperationData,
    val directAccessEndpoints: List<String>? = null
)

@Serializable
class BaseOperationData(
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
class ShareLockData(
    val targetUserId: String,
    val targetUserRole: UserRole,
    val targetUserPublicKey: String,
    val start: Int? = null,
    val end: Int? = null
)

@Serializable
class ShareLockOperationData(
    val baseOperation: BaseOperationData,
    val shareLock: ShareLockData
)

@Serializable
class BatchShareLockOperationData(
    val baseOperation: BaseOperationData,
    val users: List<ShareLockData>
)

@Serializable
class RevokeAccessToLockOperationData(
    val baseOperation: BaseOperationData,
    val users: List<String>
)

@Serializable
class UpdateSecureSettingUnlockDurationData(
    val baseOperation: BaseOperationData,
    val unlockDuration: Int
)

@Serializable
class UnlockBetweenData(
    val start: String,
    val end: String,
    val timezone: String,
    val days: List<String>,
    val exceptions: List<String>? = null
)

@Serializable
class UpdateSecureSettingUnlockBetweenData(
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