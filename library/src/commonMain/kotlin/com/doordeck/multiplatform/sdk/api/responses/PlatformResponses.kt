package com.doordeck.multiplatform.sdk.api.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonClassDiscriminator
import kotlin.js.JsExport

@JsExport
@Serializable
class ApplicationResponse(
    val applicationId: String,
    val name: String,
    val lastUpdated: Double? = null,
    val owners: Array<String>? = null,
    val corsDomains: Array<String>? = null,
    val authDomains: Array<String>? = null,
    val logoUrl: String? = null,
    val privacyPolicy: String? = null,
    val mailingAddress: String? = null,
    val companyName: String? = null,
    val supportContact: String? = null,
    val appLink: String? = null,
    val slug: String? = null,
    val emailPreferences: EmailPreferencesResponse,
    val authKeys: Container<AuthKeyResponse>,
    val oauth: OauthResponse? = null,
    val isDoordeckApplication: Boolean? = null
)

@JsExport
@Serializable
@JsonClassDiscriminator("kty")
sealed interface AuthKeyResponse {
    val kid: String
    val use: String
    val alg: String?
    val ops: Array<String>?
    val x5u: String?
    val x5t: String?
    val x5t256: String?
    val x5c: Array<String>?
    val exp: Int?
    val nbf: Int?
    val iat: Int?
}

@JsExport
@Serializable
@SerialName("RSA")
class RsaKeyResponse(
    override val use: String,
    override val kid: String,
    override val alg: String? = null,
    @SerialName("key_ops")
    override val ops: Array<String>? = null,
    override val x5u: String? = null,
    override val x5t: String? = null,
    @SerialName("x5t#S256")
    override val x5t256: String? = null,
    override val x5c: Array<String>? = null,
    override val exp: Int? = null,
    override val nbf: Int? = null,
    override val iat: Int? = null,
    val e: String,
    val n: String
): AuthKeyResponse

@JsExport
@Serializable
@SerialName("EC")
class EcKeyResponse(
    override val use: String,
    override val kid: String,
    override val alg: String? = null,
    @SerialName("key_ops")
    override val ops: Array<String>? = null,
    override val x5u: String? = null,
    override val x5t: String? = null,
    @SerialName("x5t#S256")
    override val x5t256: String? = null,
    override val x5c: Array<String>? = null,
    override val exp: Int? = null,
    override val nbf: Int? = null,
    override val iat: Int? = null,
    val crv: String,
    val x: String,
    val y: String
): AuthKeyResponse

@JsExport
@Serializable
@SerialName("OKP")
class Ed25519KeyResponse(
    override val use: String,
    override val kid: String,
    override val alg: String? = null,
    @SerialName("key_ops")
    override val ops: Array<String>? = null,
    override val x5u: String? = null,
    override val x5t: String? = null,
    @SerialName("x5t#S256")
    override val x5t256: String? = null,
    override val x5c: Array<String>? = null,
    override val exp: Int? = null,
    override val nbf: Int? = null,
    override val iat: Int? = null,
    val d: String? = null,
    val crv: String,
    val x: String
): AuthKeyResponse

@JsExport
@Serializable
class EmailPreferencesResponse(
    val senderEmail: String? = null,
    val senderName: String? = null,
    val primaryColour: String,
    val secondaryColour: String,
    val onlySendEssentialEmails: Boolean? = null,
    val callToAction: EmailCallToActionResponse? = null,
)

@JsExport
@Serializable
class EmailCallToActionResponse(
    val actionTarget: String,
    val headline: String,
    val actionText: String
)

@JsExport
@Serializable
class OauthResponse(
    val authorizationEndpoint: String,
    val clientId: String,
    val grantType: String
)

@JsExport
@Serializable
class ApplicationOwnerDetailsResponse(
    val userId: String,
    val email: String,
    val displayName: String? = null,
    val orphan: Boolean,
    val foreign: Boolean
)

@JsExport
@Serializable
class GetLogoUploadUrlResponse(
    val uploadUrl: String
)