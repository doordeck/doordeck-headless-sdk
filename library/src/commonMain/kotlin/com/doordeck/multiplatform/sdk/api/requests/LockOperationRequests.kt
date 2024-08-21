package com.doordeck.multiplatform.sdk.api.requests

import com.doordeck.multiplatform.sdk.api.model.UserRole
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class OperationHeaderRequest(
    val alg: String = "EdDSA",
    val x5c: List<String>,
    val type: String = "JWT"
)

@Serializable
class OperationBodyRequest(
    val iss: String,
    val sub: String,
    val nbf: Int,
    val iat: Int,
    val exp: Int,
    val jti: String? = null,
    val operation: OperationRequest
)

@Serializable
@SerialName("MUTATE_LOCK")
class LockOperationRequest(
    val locked: Boolean
): OperationRequest

@Serializable
@SerialName("ADD_USER")
class ShareLockOperationRequest(
    val user: String,
    val publicKey: String,
    val role: UserRole? = null,
    val start: Int? = null,
    val end: Int? = null
): OperationRequest

@Serializable
@SerialName("REMOVE_USER")
class RevokeAccessToALockOperationRequest(
    val users: List<String>
): OperationRequest

@SerialName("MUTATE_SETTING")
@Serializable
class UpdateSecureSettingsOperationRequest(
    val unlockDuration: Int? = null,
    val unlockBetween: UnlockBetweenSettingRequest? = null
): OperationRequest

@Serializable
class UnlockBetweenSettingRequest(
    val start: String,
    val end: String,
    val timezone: String,
    val days: List<String>,
    val exceptions: List<String>? = null
)

@Serializable
sealed interface OperationRequest

@Serializable
class UserPublicKeyRequest(
    val email: String? = null,
    val telephone: String? = null,
    val localKey: String? = null,
    val foreignKey: String? = null,
    val identity: String? = null
)

@Serializable
sealed interface UpdateLockPropertiesRequest

@Serializable
class UpdateLockNameRequest(
    val name: String?
): UpdateLockPropertiesRequest

@Serializable
class UpdateLockFavouriteRequest(
    val favourite: Boolean?
): UpdateLockPropertiesRequest

@Serializable
class UpdateLockColourRequest(
    val colour: String?
): UpdateLockPropertiesRequest

@Serializable
class UpdateLockSettingRequest(
    val settings: LockSettingsRequest?
): UpdateLockPropertiesRequest

@Serializable
sealed interface LockSettingsRequest

@Serializable
class LockSettingsDefaultNameRequest(
    val defaultName: String?
): LockSettingsRequest

@Serializable
class LockSettingsPermittedAddressesRequest(
    val permittedAddresses: List<String>
): LockSettingsRequest

@Serializable
class LockSettingsHiddenRequest(
    val hidden: Boolean
): LockSettingsRequest

@Serializable
sealed interface UsageRequirementRequest

@Serializable
class UpdateLockSettingUsageRequirementRequest(
    val usageRequirements: UsageRequirementRequest?
): LockSettingsRequest

@Serializable
class UpdateLockSettingTimeUsageRequirementRequest(
    val time: List<TimeRequirementRequest>
): UsageRequirementRequest

@Serializable
class UpdateLockSettingLocationUsageRequirementRequest(
    val location: LocationRequirementRequest?
): UsageRequirementRequest

@Serializable
class TimeRequirementRequest(
    val start: String,
    val end: String,
    val timezone: String,
    val days: List<String>
)

@Serializable
class LocationRequirementRequest(
    val latitude: Double,
    val longitude: Double,
    val enabled: Boolean? = null,
    val radius: Int? = null,
    val accuracy: Int? = null
)