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
class UpdateLockPropertiesRequest(
    val name: String? = null,
    val favourite: Boolean? = null,
    val colour: String? = null,
    val settings: LockSettingsRequest? = null
)

@Serializable
class LockSettingsRequest(
    val defaultName: String? = null,
    val permittedAddress: Array<String>? = null,
    val delay: Int? = null,
    val usageRequirements: UsageRequirementsRequest? = null
)

@Serializable
class UsageRequirementsRequest(
    val time: TimeRequirementRequest? = null,
    val location: LocationRequirementRequest? = null
)

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