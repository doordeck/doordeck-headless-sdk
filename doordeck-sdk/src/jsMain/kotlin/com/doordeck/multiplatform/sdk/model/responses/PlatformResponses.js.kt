package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.model.common.GrantType

@JsExport
data class ApplicationResponse(
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
    val ops: List<String>?
    val x5u: String?
    val x5t: String?
    val x5t256: String?
    val x5c: List<String>?
    val exp: Int?
    val nbf: Int?
    val iat: Int?
}

@JsExport
data class RsaKeyResponse(
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
): AuthKeyResponse

@JsExport
data class EcKeyResponse(
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
): AuthKeyResponse

@JsExport
data class Ed25519KeyResponse(
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
    val grantType: GrantType
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

internal fun List<BasicApplicationResponse>.toApplicationResponse(): List<ApplicationResponse> = map {
    it.toApplicationResponse()
}

internal fun BasicApplicationResponse.toApplicationResponse(): ApplicationResponse = ApplicationResponse(
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
        ops = ops,
        x5u = x5u,
        x5t = x5t,
        x5t256 = x5t256,
        x5c = x5c,
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
        ops = ops,
        x5u = x5u,
        x5t = x5t,
        x5t256 = x5t256,
        x5c = x5c,
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
        ops = ops,
        x5u = x5u,
        x5t = x5t,
        x5t256 = x5t256,
        x5c = x5c,
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
    grantType = grantType
)

internal fun List<BasicApplicationOwnerDetailsResponse>.toApplicationOwnerDetailsResponse(): List<ApplicationOwnerDetailsResponse> = map { owner ->
    ApplicationOwnerDetailsResponse(
        userId = owner.userId,
        email = owner.email,
        displayName = owner.displayName,
        orphan = owner.orphan,
        foreign = owner.foreign
    )
}

internal fun BasicGetLogoUploadUrlResponse.toGetLogoUploadUrlResponse(): GetLogoUploadUrlResponse = GetLogoUploadUrlResponse(
    uploadUrl = uploadUrl
)