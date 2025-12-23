package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.model.common.GrantType
import com.doordeck.multiplatform.sdk.util.emptyJsArray
import com.doordeck.multiplatform.sdk.util.toJsArray
import kotlin.js.collections.JsArray

@JsExport
data class ApplicationResponse(
    val applicationId: String,
    val name: String,
    val lastUpdated: Double? = null,
    val owners: JsArray<String> = emptyJsArray(),
    val corsDomains: JsArray<String> = emptyJsArray(),
    val authDomains: JsArray<String> = emptyJsArray(),
    val logoUrl: String? = null,
    val privacyPolicy: String? = null,
    val mailingAddress: String? = null,
    val companyName: String? = null,
    val supportContact: String? = null,
    val appLink: String? = null,
    val slug: String? = null,
    val emailPreferences: EmailPreferencesResponse,
    val authKeys: Map<String, AuthKeyResponse>,
    val oauth: OauthResponse? = null,
    val isDoordeckApplication: Boolean? = null
)

@JsExport
sealed interface AuthKeyResponse {
    val kid: String
    val use: String
    val alg: String?
    val ops: JsArray<String>?
    val x5u: String?
    val x5t: String?
    val x5t256: String?
    val x5c: JsArray<String>?
    val exp: Long?
    val nbf: Long?
    val iat: Long?
}

@JsExport
data class RsaKeyResponse(
    override val use: String,
    override val kid: String,
    override val alg: String? = null,
    override val ops: JsArray<String>? = null,
    override val x5u: String? = null,
    override val x5t: String? = null,
    override val x5t256: String? = null,
    override val x5c: JsArray<String>? = null,
    override val exp: Long? = null,
    override val nbf: Long? = null,
    override val iat: Long? = null,
    val e: String,
    val n: String
): AuthKeyResponse

@JsExport
data class EcKeyResponse(
    override val use: String,
    override val kid: String,
    override val alg: String? = null,
    override val ops: JsArray<String>? = null,
    override val x5u: String? = null,
    override val x5t: String? = null,
    override val x5t256: String? = null,
    override val x5c: JsArray<String>? = null,
    override val exp: Long? = null,
    override val nbf: Long? = null,
    override val iat: Long? = null,
    val crv: String,
    val x: String,
    val y: String
): AuthKeyResponse

@JsExport
data class Ed25519KeyResponse(
    override val use: String,
    override val kid: String,
    override val alg: String? = null,
    override val ops: JsArray<String>? = null,
    override val x5u: String? = null,
    override val x5t: String? = null,
    override val x5t256: String? = null,
    override val x5c: JsArray<String>? = null,
    override val exp: Long? = null,
    override val nbf: Long? = null,
    override val iat: Long? = null,
    val crv: String,
    val x: String
): AuthKeyResponse

@JsExport
data class EmailPreferencesResponse(
    val senderEmail: String? = null,
    val senderName: String? = null,
    val primaryColour: String,
    val secondaryColour: String,
    val onlySendEssentialEmails: Boolean? = null,
    val callToAction: EmailCallToActionResponse? = null,
)

@JsExport
data class EmailCallToActionResponse(
    val actionTarget: String,
    val headline: String,
    val actionText: String
)

@JsExport
data class OauthResponse(
    val authorizationEndpoint: String,
    val clientId: String,
    val grantType: String
)

@JsExport
data class ApplicationOwnerDetailsResponse(
    val userId: String,
    val email: String,
    val displayName: String? = null,
    val orphan: Boolean,
    val foreign: Boolean
)

@JsExport
data class GetLogoUploadUrlResponse(
    val uploadUrl: String
)

internal fun List<BasicApplicationResponse>.toApplicationResponse(): JsArray<ApplicationResponse> = map {
    it.toApplicationResponse()
}.toJsArray()

internal fun BasicApplicationResponse.toApplicationResponse(): ApplicationResponse = ApplicationResponse(
    applicationId = applicationId,
    name = name,
    lastUpdated = lastUpdated,
    owners = owners.toJsArray(),
    corsDomains = corsDomains.toJsArray(),
    authDomains = authDomains.toJsArray(),
    logoUrl = logoUrl,
    privacyPolicy = privacyPolicy,
    mailingAddress = mailingAddress,
    companyName = companyName,
    supportContact = supportContact,
    appLink = appLink,
    slug = slug,
    emailPreferences = emailPreferences.toEmailPreferencesResponse(),
    authKeys = authKeys.map { it.key to it.value.toAuthKeyResponse() }.toMap(),
    oauth = oauth?.toOauthResponse(),
    isDoordeckApplication = isDoordeckApplication
)

internal fun BasicAuthKeyResponse.toAuthKeyResponse() = when(this) {
    is BasicRsaKeyResponse -> RsaKeyResponse(
        use = use,
        kid = kid,
        alg = alg,
        ops = ops?.toJsArray(),
        x5u = x5u,
        x5t = x5t,
        x5t256 = x5t256,
        x5c = x5c?.toJsArray(),
        exp = exp,
        nbf = nbf,
        iat = iat,
        e = e,
        n = n
    )
    is BasicEcKeyResponse -> EcKeyResponse(
        use = use,
        kid = kid,
        alg = alg,
        ops = ops?.toJsArray(),
        x5u = x5u,
        x5t = x5t,
        x5t256 = x5t256,
        x5c = x5c?.toJsArray(),
        exp = exp,
        nbf = nbf,
        iat = iat,
        crv = crv,
        x = x,
        y = y
    )
    is BasicEd25519KeyResponse -> Ed25519KeyResponse(
        use = use,
        kid = kid,
        alg = alg,
        ops = ops?.toJsArray(),
        x5u = x5u,
        x5t = x5t,
        x5t256 = x5t256,
        x5c = x5c?.toJsArray(),
        exp = exp,
        nbf = nbf,
        iat = iat,
        crv = crv,
        x = x
    )
}

internal fun BasicEmailPreferencesResponse.toEmailPreferencesResponse(): EmailPreferencesResponse = EmailPreferencesResponse(
    senderEmail = senderEmail,
    senderName = senderName,
    primaryColour = primaryColour,
    secondaryColour = secondaryColour,
    onlySendEssentialEmails = onlySendEssentialEmails,
    callToAction = callToAction?.toEmailCallToActionResponse(),
)

internal fun BasicEmailCallToActionResponse.toEmailCallToActionResponse(): EmailCallToActionResponse = EmailCallToActionResponse(
    actionTarget = actionTarget,
    headline = headline,
    actionText = actionText
)

internal fun BasicOauthResponse.toOauthResponse(): OauthResponse = OauthResponse(
    authorizationEndpoint = authorizationEndpoint,
    clientId = clientId,
    grantType = grantType.name
)

internal fun List<BasicApplicationOwnerDetailsResponse>.toApplicationOwnerDetailsResponse(): JsArray<ApplicationOwnerDetailsResponse> = map { owner ->
    ApplicationOwnerDetailsResponse(
        userId = owner.userId,
        email = owner.email,
        displayName = owner.displayName,
        orphan = owner.orphan,
        foreign = owner.foreign
    )
}.toJsArray()

internal fun BasicGetLogoUploadUrlResponse.toGetLogoUploadUrlResponse(): GetLogoUploadUrlResponse = GetLogoUploadUrlResponse(
    uploadUrl = uploadUrl
)