package com.doordeck.multiplatform.sdk.model.requests

import com.doordeck.multiplatform.sdk.model.common.DayOfWeek
import com.doordeck.multiplatform.sdk.model.common.UserRole
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class OperationHeaderRequest(
    val alg: String = "EdDSA",
    val x5c: List<String>,
    val type: String = "JWT"
)

@Serializable
internal data class OperationBodyRequest(
    val iss: String,
    val sub: String,
    val nbf: Long,
    val iat: Long,
    val exp: Long,
    val jti: String? = null,
    val operation: OperationRequest
)

@Serializable
internal data class BaseOperationRequest(
    val userId: String,
    val userCertificateChain: List<String>,
    val userPrivateKey: ByteArray,
    val lockId: String,
    val notBefore: Long,
    val issuedAt: Long,
    val expiresAt: Long,
    val jti: String
)

@Serializable
@SerialName("MUTATE_LOCK")
internal data class LockOperationRequest(
    val locked: Boolean
): OperationRequest

@Serializable
@SerialName("ADD_USER")
internal data class ShareLockOperationRequest(
    val user: String,
    val publicKey: String,
    val role: UserRole? = null,
    val start: Long? = null,
    val end: Long? = null
): OperationRequest

@Serializable
@SerialName("BATCH_ADD_USER")
internal data class BatchShareLockOperationRequest(
    val users: List<ShareLockOperationRequest>
): OperationRequest

@Serializable
@SerialName("REMOVE_USER")
internal data class RevokeAccessToALockOperationRequest(
    val users: List<String>
): OperationRequest

@Serializable
@SerialName("MUTATE_SETTING")
internal data class UpdateSecureSettingsOperationRequest(
    val unlockDuration: Int? = null,
    val unlockBetween: UnlockBetweenSettingRequest? = null
): OperationRequest

@Serializable
internal data class UnlockBetweenSettingRequest(
    val start: String, // Local time, (HH:mm)
    val end: String, // Local time, (HH:mm)
    val timezone: String,
    val days: List<DayOfWeek>,
    val exceptions: List<String>? = null
)

@Serializable
internal sealed interface OperationRequest

@Serializable
internal data class UserPublicKeyRequest(
    val email: String? = null,
    val telephone: String? = null,
    val localKey: String? = null,
    val foreignKey: String? = null,
    val identity: String? = null
)

@Serializable
internal data class BatchUserPublicKeyRequest(
    val email: List<String>? = null,
    val telephone: List<String>? = null,
    val localKey: List<String>? = null,
    val foreignKey: List<String>? = null
)

@Serializable
internal sealed interface UpdateLockPropertiesRequest

@Serializable
internal data class UpdateLockNameRequest(
    val name: String?
): UpdateLockPropertiesRequest

@Serializable
internal data class UpdateLockFavouriteRequest(
    val favourite: Boolean?
): UpdateLockPropertiesRequest

@Serializable
internal data class UpdateLockColourRequest(
    val colour: String?
): UpdateLockPropertiesRequest

@Serializable
internal data class UpdateLockSettingRequest(
    val settings: LockSettingsRequest?
): UpdateLockPropertiesRequest

@Serializable
internal sealed interface LockSettingsRequest

@Serializable
internal data class LockSettingsDefaultNameRequest(
    val defaultName: String?
): LockSettingsRequest

@Serializable
internal data class LockSettingsPermittedAddressesRequest(
    val permittedAddresses: List<String>
): LockSettingsRequest

@Serializable
internal data class LockSettingsHiddenRequest(
    val hidden: Boolean
): LockSettingsRequest

@Serializable
internal sealed interface UsageRequirementRequest

@Serializable
internal data class UpdateLockSettingUsageRequirementRequest(
    val usageRequirements: UsageRequirementRequest?
): LockSettingsRequest

@Serializable
internal data class UpdateLockSettingTimeUsageRequirementRequest(
    val time: List<TimeRequirementRequest>
): UsageRequirementRequest

@Serializable
internal data class UpdateLockSettingLocationUsageRequirementRequest(
    val location: LocationRequirementRequest?
): UsageRequirementRequest

@Serializable
internal data class TimeRequirementRequest(
    val start: String, // Local time, (HH:mm)
    val end: String, // Local time, (HH:mm)
    val timezone: String,
    val days: List<DayOfWeek>
)

@Serializable
internal data class LocationRequirementRequest(
    val latitude: Double,
    val longitude: Double,
    val enabled: Boolean,
    val radius: Int,
    val accuracy: Int
)