package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.model.common.GrantType
import com.doordeck.multiplatform.sdk.util.toInstant
import com.doordeck.multiplatform.sdk.util.toJson
import com.doordeck.multiplatform.sdk.util.toUri
import com.doordeck.multiplatform.sdk.util.toUrl
import com.doordeck.multiplatform.sdk.util.toUuid
import com.nimbusds.jose.jwk.JWK
import java.net.URI
import java.net.URL
import java.time.Instant
import java.util.UUID

data class ApplicationResponse(
    val applicationId: UUID,
    val name: String,
    val lastUpdated: Instant? = null,
    val owners: List<UUID> = emptyList(),
    val corsDomains: List<URI> = emptyList(),
    val authDomains: List<URI> = emptyList(),
    val logoUrl: URI? = null,
    val privacyPolicy: URI? = null,
    val mailingAddress: String? = null,
    val companyName: String? = null,
    val supportContact: URI? = null,
    val appLink: URI? = null,
    val slug: String? = null,
    val emailPreferences: EmailPreferencesResponse,
    val authKeys: Map<String, JWK>,
    val oauth: OauthResponseResponse? = null,
    val isDoordeckApplication: Boolean? = null
)

@JvmSynthetic
internal fun BasicAuthKeyResponse.toAuthKey(): JWK = JWK.parse(toJson())

data class EmailPreferencesResponse(
    val senderEmail: String? = null,
    val senderName: String? = null,
    val primaryColour: String,
    val secondaryColour: String,
    val onlySendEssentialEmails: Boolean? = null,
    val callToAction: EmailCallToActionResponse? = null,
)

data class EmailCallToActionResponse(
    val actionTarget: URI,
    val headline: String,
    val actionText: String
)

data class OauthResponseResponse(
    val authorizationEndpoint: URI,
    val clientId: String,
    val grantType: GrantType
)

data class ApplicationOwnerDetailsResponse(
    val userId: UUID,
    val email: String,
    val displayName: String? = null,
    val orphan: Boolean,
    val foreign: Boolean
)

data class GetLogoUploadUrlResponse(
    val uploadUrl: URL
)

@JvmSynthetic
internal fun List<BasicApplicationResponse>.toApplicationResponse(): List<ApplicationResponse> = map {
    it.toApplicationResponse()
}

@JvmSynthetic
internal fun BasicApplicationResponse.toApplicationResponse(): ApplicationResponse = ApplicationResponse(
    applicationId = applicationId.toUuid(),
    name = name,
    lastUpdated = lastUpdated?.toInstant(),
    owners = owners.map { it.toUuid() },
    corsDomains = corsDomains.map { it.toUri() },
    authDomains = authDomains.map { it.toUri() },
    logoUrl = logoUrl?.toUri(),
    privacyPolicy = privacyPolicy?.toUri(),
    mailingAddress = mailingAddress,
    companyName = companyName,
    supportContact = supportContact?.toUri(),
    appLink = appLink?.toUri(),
    slug = slug,
    emailPreferences = emailPreferences.toEmailPreferencesResponse(),
    authKeys = authKeys.map { it.key to it.value.toAuthKey() }.toMap(),
    oauth = oauth?.toOauthResponse(),
    isDoordeckApplication = isDoordeckApplication
)

@JvmSynthetic
internal fun BasicEmailPreferencesResponse.toEmailPreferencesResponse(): EmailPreferencesResponse = EmailPreferencesResponse(
    senderEmail = senderEmail,
    senderName = senderName,
    primaryColour = primaryColour,
    secondaryColour = secondaryColour,
    onlySendEssentialEmails = onlySendEssentialEmails,
    callToAction = callToAction?.toEmailCallToActionResponse(),
)

@JvmSynthetic
internal fun BasicEmailCallToActionResponse.toEmailCallToActionResponse(): EmailCallToActionResponse = EmailCallToActionResponse(
    actionTarget = actionTarget.toUri(),
    headline = headline,
    actionText = actionText
)

@JvmSynthetic
internal fun BasicOauthResponse.toOauthResponse(): OauthResponseResponse = OauthResponseResponse(
    authorizationEndpoint = authorizationEndpoint.toUri(),
    clientId = clientId,
    grantType = grantType
)

@JvmSynthetic
internal fun List<BasicApplicationOwnerDetailsResponse>.toApplicationOwnerDetailsResponse(): List<ApplicationOwnerDetailsResponse> = map { owner ->
    ApplicationOwnerDetailsResponse(
        userId = owner.userId.toUuid(),
        email = owner.email,
        displayName = owner.displayName,
        orphan = owner.orphan,
        foreign = owner.foreign
    )
}

@JvmSynthetic
internal fun BasicGetLogoUploadUrlResponse.toGetLogoUploadUrlResponse(): GetLogoUploadUrlResponse = GetLogoUploadUrlResponse(
    uploadUrl = uploadUrl.toUrl()
)