package com.doordeck.sdk.api.requests

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
class OperationHeaderRequest(
    val alg: String = "EdDSA",
    val x5c: Array<String>,
    val type: String = "JWT"
)

@JsExport
@Serializable
class LockOperationBodyRequest(
    val iss: String,
    val sub: String,
    val nbf: String,
    val iat: String,
    val exp: String,
    val jti: String? = null,
    val operation: OperationRequest
): OperationBodyRequest()

@JsExport
@Serializable
class LockOperationRequest(
    val type: String = "MUTATE_LOCK",
    val locked: Boolean
): OperationRequest()

@JsExport
@Serializable
sealed class OperationBodyRequest

@JsExport
@Serializable
sealed class OperationRequest

@JsExport
@Serializable
class UserPublicKeyRequest(
    val email: String? = null,
    val telephone: String? = null,
    val localKey: String? = null,
    val foreignKey: String? = null,
    val identity: String? = null
)