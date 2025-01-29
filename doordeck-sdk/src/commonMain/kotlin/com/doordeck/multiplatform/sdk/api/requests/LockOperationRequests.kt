package com.doordeck.multiplatform.sdk.api.requests

import com.doordeck.multiplatform.sdk.api.model.UserRole
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OperationHeaderRequest(
    val alg: String = "EdDSA",
    val x5c: List<String>,
    val type: String = "JWT"
)

@Serializable
data class OperationBodyRequest(
    val iss: String,
    val sub: String,
    val nbf: Long,
    val iat: Long,
    val exp: Long,
    val jti: String? = null,
    val operation: OperationRequest
)

@Serializable
data class BaseOperationRequest(
    val userId: String,
    val userCertificateChain: List<String>,
    val userPrivateKey: ByteArray,
    val lockId: String,
    val notBefore: Int,
    val issuedAt: Int,
    val expiresAt: Int,
    val jti: String
)

@Serializable
@SerialName("MUTATE_LOCK")
data class LockOperationRequest(
    val locked: Boolean
): OperationRequest

@Serializable
@SerialName("ADD_USER")
data class ShareLockOperationRequest(
    val user: String,
    val publicKey: String,
    val role: UserRole? = null,
    val start: Long? = null,
    val end: Long? = null
): OperationRequest

@Serializable
@SerialName("BATCH_ADD_USER")
data class BatchShareLockOperationRequest(
    val users: List<ShareLockOperationRequest>
): OperationRequest

@Serializable
@SerialName("REMOVE_USER")
data class RevokeAccessToALockOperationRequest(
    val users: List<String>
): OperationRequest

@Serializable
@SerialName("MUTATE_SETTING")
data class UpdateSecureSettingsOperationRequest(
    val unlockDuration: Int? = null,
    val unlockBetween: UnlockBetweenSettingRequest? = null
): OperationRequest

@Serializable
data class UnlockBetweenSettingRequest(
    val start: String, // Local time, (HH:mm)
    val end: String, // Local time, (HH:mm)
    val timezone: String,
    val days: List<String>,
    val exceptions: List<String>? = null
)

@Serializable
sealed interface OperationRequest

@Serializable
data class UserPublicKeyRequest(
    val email: String? = null,
    val telephone: String? = null,
    val localKey: String? = null,
    val foreignKey: String? = null,
    val identity: String? = null
)

@Serializable
data class BatchUserPublicKeyRequest(
    val email: List<String>? = null,
    val telephone: List<String>? = null,
    val localKey: List<String>? = null,
    val foreignKey: List<String>? = null
)

@Serializable
sealed interface UpdateLockPropertiesRequest

@Serializable
data class UpdateLockNameRequest(
    val name: String?
): UpdateLockPropertiesRequest

@Serializable
data class UpdateLockFavouriteRequest(
    val favourite: Boolean?
): UpdateLockPropertiesRequest

@Serializable
data class UpdateLockColourRequest(
    val colour: String?
): UpdateLockPropertiesRequest

@Serializable
data class UpdateLockSettingRequest(
    val settings: LockSettingsRequest?
): UpdateLockPropertiesRequest

@Serializable
sealed interface LockSettingsRequest

@Serializable
data class LockSettingsDefaultNameRequest(
    val defaultName: String?
): LockSettingsRequest

@Serializable
data class LockSettingsPermittedAddressesRequest(
    val permittedAddresses: List<String>
): LockSettingsRequest

@Serializable
data class LockSettingsHiddenRequest(
    val hidden: Boolean
): LockSettingsRequest

@Serializable
sealed interface UsageRequirementRequest

@Serializable
data class UpdateLockSettingUsageRequirementRequest(
    val usageRequirements: UsageRequirementRequest?
): LockSettingsRequest

@Serializable
data class UpdateLockSettingTimeUsageRequirementRequest(
    val time: List<TimeRequirementRequest>
): UsageRequirementRequest

@Serializable
data class UpdateLockSettingLocationUsageRequirementRequest(
    val location: LocationRequirementRequest?
): UsageRequirementRequest

@Serializable
data class TimeRequirementRequest(
    val start: String, // Local time, (HH:mm)
    val end: String, // Local time, (HH:mm)
    val timezone: String,
    val days: List<String>
)

@Serializable
data class LocationRequirementRequest(
    val latitude: Double,
    val longitude: Double,
    val enabled: Boolean? = null,
    val radius: Int? = null,
    val accuracy: Int? = null
)