package com.doordeck.sdk.api.responses

import com.doordeck.sdk.api.model.AuditEvent
import com.doordeck.sdk.api.model.UserRole
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
class LockResponse(
    val id: String,
    val name: String,
    val colour: String? = null,
    val start: String? = null,
    val end: String? = null,
    val role: UserRole,
    val settings: LockSettingsResponse,
    val state: LockStateResponse,
    val favourite: Boolean,
    val unlockTime: String, // Duration
    val unlockForever: Boolean
)

@JsExport
@Serializable
class LockSettingsResponse(
    val txBeaconRssi: Int,
    val rxBeaconRssi: Int,
    val unlockTime: String, // Duration
    val proximityUnlock: Boolean,
    val permittedAddresses: Array<String>,
    val defaultName: String,
    //val usageRequirements: Array // TODO
    val delay: String, // Duration
    //unlockBetweenWindow
    val tiles: Array<String>,
    val hidden: Boolean
)

@JsExport
@Serializable
class LockStateResponse(
    val locked: Boolean,
    val connected: Boolean,
    val delay: String? = null // Duration
)

@JsExport
@Serializable
class UserPublicKeyResponse(
    val id: String,
    val publicKey: String
)

@JsExport
@Serializable
class ShareableLockResponse(
    val id: String,
    val name: String
)

@JsExport
@Serializable
class UserLockResponse(
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
@Serializable
class LockUserResponse(
    val userId: String,
    val email: String,
    val publicKey: String,
    val displayName: String? = null,
    val orphan: Boolean,
    val foreign: Boolean,
    val role: UserRole,
    val start: Double? = null,
    val end: Double? = null,
    val devices: Array<LockUserDetailsResponse>
)

// FIXME Make better names

@JsExport
@Serializable
class LockUserDetailsResponse(
    val deviceId: String,
    val role: UserRole,
    val start: Double? = null,
    val end: Double? = null
)

@JsExport
@Serializable
class UserAuditResponse(
    val deviceId: String,
    val timestamp: Int,
    val type: AuditEvent,
    val issuer: UserAuditIssuerResponse,
    val subject: UserAuditSubjectResponse,
    val rejected: Boolean
)

@JsExport
@Serializable
class UserAuditIssuerResponse(
    val userId: String
)

@JsExport
@Serializable
class UserAuditSubjectResponse(
    val userId: String,
    val email: String
)

@JsExport
@Serializable
class LockAuditTrail(
    val timestamp: Int,
    val type: AuditEvent,
    val user: String? = null,
    val email: String? = null,
    val displayName: String? = null,
    val message: String
)