package com.doordeck.multiplatform.sdk.model.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

internal data class BasicCreateApplication(
    val name: String,
    val companyName: String,
    val mailingAddress: String,
    val privacyPolicy: String? = null,
    val supportContact: String? = null,
    val appLink: String? = null,
    val emailPreferences: BasicEmailPreferences? = null,
    val logoUrl: String? = null
)

internal data class BasicEmailPreferences(
    val senderEmail: String? = null,
    val senderName: String? = null,
    val primaryColour: String? = null,
    val secondaryColour: String? = null,
    val onlySendEssentialEmails: Boolean? = null,
    val callToAction: BasicEmailCallToAction? = null
)

internal data class BasicEmailCallToAction(
    val actionTarget: String,
    val headline: String,
    val actionText: String
)

internal sealed interface BasicAuthKey {
    val kid: String
    val kty: String
    val use: String
    val alg: String?
}

internal data class BasicRsaKey(
    override val kty: String = "RSA",
    override val use: String,
    override val kid: String,
    override val alg: String? = null,
    val e: String,
    val n: String
): BasicAuthKey

internal data class BasicEcKey(
    override val kty: String = "EC",
    override val use: String,
    override val kid: String,
    override val alg: String? = null,
    val crv: String,
    val x: String,
    val y: String
): BasicAuthKey

internal data class BasicEd25519Key(
    override val kty: String = "OKP",
    override val use: String,
    override val kid: String,
    override val alg: String? = null,
    val crv: String,
    val x: String
): BasicAuthKey

@Serializable
data class ApplicationJwtHeader(val alg: String, val kid: String)

@Serializable
data class ApplicationJwtBody(
    val iss: String,
    val exp: Long,
    val iat: Long,
    val aud: String,
    val sub: String,
    val email: String,
    @SerialName("email_verified")
    val emailVerified: Boolean,
    val name: String,
    val locale: String = "en-gb",
    @SerialName("zoneinfo")
    val zoneInfo: String = "Europe/London"
)
