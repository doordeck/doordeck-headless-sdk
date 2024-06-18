package com.doordeck.sdk.api.requests

import com.doordeck.sdk.api.model.UserRole
import kotlinx.serialization.Serializable

@Serializable
class OperationHeaderRequest(
    val alg: String = "EdDSA",
    val x5c: Array<String>,
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
class LockOperationRequest(
    val type: String = "MUTATE_LOCK",
    val locked: Boolean
): OperationRequest

@Serializable
class ShareLockOperationRequest(
    val type: String = "ADD_USER",
    val user: String,
    val publicKey: String,
    val role: UserRole? = null,
    val start: Int? = null,
    val end: Int? = null
): OperationRequest

@Serializable
class RevokeAccessToALockOperationRequest(
    val type: String = "REMOVE_USER",
    val users: Array<String>
): OperationRequest

@Serializable
class UpdateSecureSettingsOperationRequest(
    val type: String = "MUTATE_SETTING",
    val unlockDuration: Int? = null,
    val unlockBetween: UnlockBetweenSettingRequest? = null
): OperationRequest

@Serializable
class UnlockBetweenSettingRequest(
    val start: String,
    val end: String,
    val timezone: String,
    val days: String,
    val exceptions: Array<String>? = null
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
    val permittedAddresses: Array<String>?
): LockSettingsRequest

@Serializable
class LockSettingsHiddenRequest(
    val hidden: Boolean?
): LockSettingsRequest

@Serializable
sealed interface UsageRequirementRequest

@Serializable
class UpdateLockSettingUsageRequirementRequest(
    val usageRequirements: UsageRequirementRequest?
): LockSettingsRequest

@Serializable
class UpdateLockSettingTimeUsageRequirementRequest(
    val time: TimeRequirementRequest?
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
    val days: Array<String>
)

@Serializable
class LocationRequirementRequest(
    val latitude: Double,
    val longitude: Double,
    val enabled: Boolean? = null,
    val radius: Int? = null,
    val accuracy: Int? = null
)