package com.doordeck.multiplatform.sdk.model.data

import com.doordeck.multiplatform.sdk.model.common.GrantType
import com.doordeck.multiplatform.sdk.model.responses.BasicApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicApplicationResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicAuthKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicEcKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicEd25519KeyResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicEmailCallToActionResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicEmailPreferencesResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicGetLogoUploadUrlResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicOauthResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicRsaKeyResponse

data class Application(
    val applicationId: String,
    val name: String,
    val lastUpdated: Double? = null,
    val owners: List<String>? = null,
    val corsDomains: List<String>? = null,
    val authDomains: List<String>? = null,
    val logoUrl: String? = null,
    val privacyPolicy: String? = null,
    val mailingAddress: String? = null,
    val companyName: String? = null,
    val supportContact: String? = null,
    val appLink: String? = null,
    val slug: String? = null,
    val emailPreferences: EmailPreferences,
    val authKeys: Map<String, AuthKey>,
    val oauth: Oauth? = null,
    val isDoordeckApplication: Boolean? = null
)

sealed interface AuthKey {
    val kid: String
    val use: String
    val alg: String?
    val ops: List<String>?
    val x5u: String?
    val x5t: String?
    val x5t256: String?
    val x5c: List<String>?
    val exp: Int?
    val nbf: Int?
    val iat: Int?
}

data class RsaKey(
    override val use: String,
    override val kid: String,
    override val alg: String? = null,
    override val ops: List<String>? = null,
    override val x5u: String? = null,
    override val x5t: String? = null,
    override val x5t256: String? = null,
    override val x5c: List<String>? = null,
    override val exp: Int? = null,
    override val nbf: Int? = null,
    override val iat: Int? = null,
    val e: String,
    val n: String
): AuthKey

data class EcKey(
    override val use: String,
    override val kid: String,
    override val alg: String? = null,
    override val ops: List<String>? = null,
    override val x5u: String? = null,
    override val x5t: String? = null,
    override val x5t256: String? = null,
    override val x5c: List<String>? = null,
    override val exp: Int? = null,
    override val nbf: Int? = null,
    override val iat: Int? = null,
    val crv: String,
    val x: String,
    val y: String
): AuthKey

data class Ed25519Key(
    override val use: String,
    override val kid: String,
    override val alg: String? = null,
    override val ops: List<String>? = null,
    override val x5u: String? = null,
    override val x5t: String? = null,
    override val x5t256: String? = null,
    override val x5c: List<String>? = null,
    override val exp: Int? = null,
    override val nbf: Int? = null,
    override val iat: Int? = null,
    val d: String? = null,
    val crv: String,
    val x: String
): AuthKey

data class EmailPreferences(
    val senderEmail: String? = null,
    val senderName: String? = null,
    val primaryColour: String,
    val secondaryColour: String,
    val onlySendEssentialEmails: Boolean? = null,
    val callToAction: EmailCallToAction? = null,
)

data class EmailCallToAction(
    val actionTarget: String,
    val headline: String,
    val actionText: String
)

data class Oauth(
    val authorizationEndpoint: String,
    val clientId: String,
    val grantType: GrantType
)

data class ApplicationOwnerDetails(
    val userId: String,
    val email: String,
    val displayName: String? = null,
    val orphan: Boolean,
    val foreign: Boolean
)

data class GetLogoUploadUrl(
    val uploadUrl: String
)

internal fun List<BasicApplicationResponse>.toApplication(): List<Application> = map {
    it.toApplication()
}

internal fun BasicApplicationResponse.toApplication(): Application = Application(
    applicationId = applicationId,
    name = name,
    lastUpdated = lastUpdated,
    owners = owners,
    corsDomains = corsDomains,
    authDomains = authDomains,
    logoUrl = logoUrl,
    privacyPolicy = privacyPolicy,
    mailingAddress = mailingAddress,
    companyName = companyName,
    supportContact = supportContact,
    appLink = appLink,
    slug = slug,
    emailPreferences = emailPreferences.toEmailPreferences(),
    authKeys = authKeys.map { it.key to it.value.toAuthKey() }.toMap(),
    oauth = oauth?.toOauth(),
    isDoordeckApplication = isDoordeckApplication
)

internal fun BasicAuthKeyResponse.toAuthKey() = when(this) {
    is BasicRsaKeyResponse -> RsaKey(use, kid, alg, ops, x5u, x5t, x5t256, x5c, exp, nbf, iat, e, n)
    is BasicEcKeyResponse -> EcKey(use, kid, alg, ops, x5u, x5t, x5t256, x5c, exp, nbf, iat, crv, x, y)
    is BasicEd25519KeyResponse -> Ed25519Key(use, kid, alg, ops, x5u, x5t, x5t256, x5c, exp, nbf, iat, d, crv, x)
}

internal fun BasicEmailPreferencesResponse.toEmailPreferences(): EmailPreferences = EmailPreferences(
    senderEmail = senderEmail,
    senderName = senderName,
    primaryColour = primaryColour,
    secondaryColour = secondaryColour,
    onlySendEssentialEmails = onlySendEssentialEmails,
    callToAction = callToAction?.toEmailCallToAction(),
)

internal fun BasicEmailCallToActionResponse.toEmailCallToAction(): EmailCallToAction = EmailCallToAction(
    actionTarget = actionTarget,
    headline = headline,
    actionText = actionText
)

internal fun BasicOauthResponse.toOauth(): Oauth = Oauth(
    authorizationEndpoint = authorizationEndpoint,
    clientId = clientId,
    grantType = grantType
)

internal fun List<BasicApplicationOwnerDetailsResponse>.toApplicationOwnerDetails(): List<ApplicationOwnerDetails> = map { owner ->
    ApplicationOwnerDetails(
        userId = owner.userId,
        email = owner.email,
        displayName = owner.displayName,
        orphan = owner.orphan,
        foreign = owner.foreign
    )
}

internal fun BasicGetLogoUploadUrlResponse.toGetLogoUploadUrl(): GetLogoUploadUrl = GetLogoUploadUrl(
    uploadUrl = uploadUrl
)