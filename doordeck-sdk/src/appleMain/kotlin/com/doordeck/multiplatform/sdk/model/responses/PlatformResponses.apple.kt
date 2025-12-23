package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.model.common.GrantType
import com.doordeck.multiplatform.sdk.util.toNsDate
import com.doordeck.multiplatform.sdk.util.toNsUrlComponents
import com.doordeck.multiplatform.sdk.util.toNsUuid
import platform.Foundation.NSDate
import platform.Foundation.NSURLComponents
import platform.Foundation.NSUUID

data class ApplicationResponse(
    val applicationId: NSUUID,
    val name: String,
    val lastUpdated: NSDate? = null,
    val owners: List<NSUUID> = emptyList(),
    val corsDomains: List<NSURLComponents> = emptyList(),
    val authDomains: List<NSURLComponents> = emptyList(),
    val logoUrl: NSURLComponents? = null,
    val privacyPolicy: NSURLComponents? = null,
    val mailingAddress: String? = null,
    val companyName: String? = null,
    val supportContact: NSURLComponents? = null,
    val appLink: NSURLComponents? = null,
    val slug: String? = null,
    val emailPreferences: EmailPreferencesResponse,
    val authKeys: Map<String, AuthKeyResponse>,
    val oauth: OauthResponse? = null,
    val isDoordeckApplication: Boolean? = null
)

sealed interface AuthKeyResponse {
    val kid: String
    val use: String
    val alg: String?
    val ops: List<String>?
    val x5u: String?
    val x5t: String?
    val x5t256: String?
    val x5c: List<String>?
    val exp: Long?
    val nbf: Long?
    val iat: Long?
}

data class RsaKeyResponse(
    override val use: String,
    override val kid: String,
    override val alg: String? = null,
    override val ops: List<String>? = null,
    override val x5u: String? = null,
    override val x5t: String? = null,
    override val x5t256: String? = null,
    override val x5c: List<String>? = null,
    override val exp: Long? = null,
    override val nbf: Long? = null,
    override val iat: Long? = null,
    val e: String,
    val n: String
) : AuthKeyResponse

data class EcKeyResponse(
    override val use: String,
    override val kid: String,
    override val alg: String? = null,
    override val ops: List<String>? = null,
    override val x5u: String? = null,
    override val x5t: String? = null,
    override val x5t256: String? = null,
    override val x5c: List<String>? = null,
    override val exp: Long? = null,
    override val nbf: Long? = null,
    override val iat: Long? = null,
    val crv: String,
    val x: String,
    val y: String
) : AuthKeyResponse

data class Ed25519KeyResponse(
    override val use: String,
    override val kid: String,
    override val alg: String? = null,
    override val ops: List<String>? = null,
    override val x5u: String? = null,
    override val x5t: String? = null,
    override val x5t256: String? = null,
    override val x5c: List<String>? = null,
    override val exp: Long? = null,
    override val nbf: Long? = null,
    override val iat: Long? = null,
    val crv: String,
    val x: String
) : AuthKeyResponse

data class EmailPreferencesResponse(
    val senderEmail: String? = null,
    val senderName: String? = null,
    val primaryColour: String,
    val secondaryColour: String,
    val onlySendEssentialEmails: Boolean? = null,
    val callToAction: EmailCallToActionResponse? = null,
)

data class EmailCallToActionResponse(
    val actionTarget: NSURLComponents,
    val headline: String,
    val actionText: String
)

data class OauthResponse(
    val authorizationEndpoint: NSURLComponents,
    val clientId: String,
    val grantType: GrantType
)

data class ApplicationOwnerDetailsResponse(
    val userId: NSUUID,
    val email: String,
    val displayName: String? = null,
    val orphan: Boolean,
    val foreign: Boolean
)

data class GetLogoUploadUrlResponse(
    val uploadUrl: NSURLComponents
)

internal fun List<BasicApplicationResponse>.toApplicationResponse(): List<ApplicationResponse> = map {
    it.toApplicationResponse()
}

internal fun BasicApplicationResponse.toApplicationResponse(): ApplicationResponse = ApplicationResponse(
    applicationId = applicationId.toNsUuid(),
    name = name,
    lastUpdated = lastUpdated?.toNsDate(),
    owners = owners.map { it.toNsUuid() },
    corsDomains = corsDomains.map { it.toNsUrlComponents() },
    authDomains = authDomains.map { it.toNsUrlComponents() },
    logoUrl = logoUrl?.toNsUrlComponents(),
    privacyPolicy = privacyPolicy?.toNsUrlComponents(),
    mailingAddress = mailingAddress,
    companyName = companyName,
    supportContact = supportContact?.toNsUrlComponents(),
    appLink = appLink?.toNsUrlComponents(),
    slug = slug,
    emailPreferences = emailPreferences.toEmailPreferencesResponse(),
    authKeys = authKeys.map { it.key to it.value.toAuthKeyResponse() }.toMap(),
    oauth = oauth?.toOauthResponse(),
    isDoordeckApplication = isDoordeckApplication
)

internal fun BasicAuthKeyResponse.toAuthKeyResponse() = when (this) {
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

internal fun BasicEmailPreferencesResponse.toEmailPreferencesResponse(): EmailPreferencesResponse =
    EmailPreferencesResponse(
        senderEmail = senderEmail,
        senderName = senderName,
        primaryColour = primaryColour,
        secondaryColour = secondaryColour,
        onlySendEssentialEmails = onlySendEssentialEmails,
        callToAction = callToAction?.toEmailCallToActionResponse(),
    )

internal fun BasicEmailCallToActionResponse.toEmailCallToActionResponse(): EmailCallToActionResponse =
    EmailCallToActionResponse(
        actionTarget = actionTarget.toNsUrlComponents(),
        headline = headline,
        actionText = actionText
    )

internal fun BasicOauthResponse.toOauthResponse(): OauthResponse = OauthResponse(
    authorizationEndpoint = authorizationEndpoint.toNsUrlComponents(),
    clientId = clientId,
    grantType = grantType
)

internal fun List<BasicApplicationOwnerDetailsResponse>.toApplicationOwnerDetailsResponse(): List<ApplicationOwnerDetailsResponse> =
    map { owner ->
        ApplicationOwnerDetailsResponse(
            userId = owner.userId.toNsUuid(),
            email = owner.email,
            displayName = owner.displayName,
            orphan = owner.orphan,
            foreign = owner.foreign
        )
    }

internal fun BasicGetLogoUploadUrlResponse.toGetLogoUploadUrlResponse(): GetLogoUploadUrlResponse =
    GetLogoUploadUrlResponse(
        uploadUrl = uploadUrl.toNsUrlComponents()
    )