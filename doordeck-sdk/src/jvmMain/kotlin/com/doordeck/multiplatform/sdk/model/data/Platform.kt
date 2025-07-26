package com.doordeck.multiplatform.sdk.model.data

import com.doordeck.multiplatform.sdk.model.common.GrantType
import com.doordeck.multiplatform.sdk.model.responses.ApplicationOwnerDetailsResponse
import com.doordeck.multiplatform.sdk.model.responses.ApplicationResponse
import com.doordeck.multiplatform.sdk.model.responses.AuthKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.EmailCallToActionResponse
import com.doordeck.multiplatform.sdk.model.responses.EmailPreferencesResponse
import com.doordeck.multiplatform.sdk.model.responses.GetLogoUploadUrlResponse
import com.doordeck.multiplatform.sdk.model.responses.OauthResponse
import com.doordeck.multiplatform.sdk.util.toInstant
import com.doordeck.multiplatform.sdk.util.toJson
import com.doordeck.multiplatform.sdk.util.toUUID
import com.doordeck.multiplatform.sdk.util.toUri
import com.doordeck.multiplatform.sdk.util.toUrl
import com.nimbusds.jose.jwk.JWK
import java.net.URI
import java.net.URL
import java.util.UUID
import kotlin.time.Instant

data class Application(
    val applicationId: UUID,
    val name: String,
    val lastUpdated: Instant? = null,
    val owners: List<UUID>? = null,
    val corsDomains: List<URI>? = null,
    val authDomains: List<URI>? = null,
    val logoUrl: URI? = null,
    val privacyPolicy: URI? = null,
    val mailingAddress: String? = null,
    val companyName: String? = null,
    val supportContact: URI? = null,
    val appLink: URI? = null,
    val slug: String? = null,
    val emailPreferences: EmailPreferences,
    val authKeys: Map<String, JWK>,
    val oauth: Oauth? = null,
    val isDoordeckApplication: Boolean? = null
)

// TODO Uhm..!
internal fun AuthKeyResponse.toAuthKey(): JWK = JWK.parse(toJson())

data class EmailPreferences(
    val senderEmail: String? = null,
    val senderName: String? = null,
    val primaryColour: String,
    val secondaryColour: String,
    val onlySendEssentialEmails: Boolean? = null,
    val callToAction: EmailCallToAction? = null,
)

data class EmailCallToAction(
    val actionTarget: URI,
    val headline: String,
    val actionText: String
)

data class Oauth(
    val authorizationEndpoint: URI,
    val clientId: String,
    val grantType: GrantType
)

data class ApplicationOwnerDetails(
    val userId: UUID,
    val email: String,
    val displayName: String? = null,
    val orphan: Boolean,
    val foreign: Boolean
)

data class GetLogoUploadUrl(
    val uploadUrl: URL
)

internal fun List<ApplicationResponse>.toApplication(): List<Application> = map {
    it.toApplication()
}

internal fun ApplicationResponse.toApplication(): Application = Application(
    applicationId = applicationId.toUUID(),
    name = name,
    lastUpdated = lastUpdated?.toInstant(),
    owners = owners?.map { it.toUUID() },
    corsDomains = corsDomains?.map { it.toUri() },
    authDomains = authDomains?.map { it.toUri() },
    logoUrl = logoUrl?.toUri(),
    privacyPolicy = privacyPolicy?.toUri(),
    mailingAddress = mailingAddress,
    companyName = companyName,
    supportContact = supportContact?.toUri(),
    appLink = appLink?.toUri(),
    slug = slug,
    emailPreferences = emailPreferences.toEmailPreferences(),
    authKeys = authKeys.map { it.key to it.value.toAuthKey() }.toMap(),
    oauth = oauth?.toOauth(),
    isDoordeckApplication = isDoordeckApplication
)

internal fun EmailPreferencesResponse.toEmailPreferences(): EmailPreferences = EmailPreferences(
    senderEmail = senderEmail,
    senderName = senderName,
    primaryColour = primaryColour,
    secondaryColour = secondaryColour,
    onlySendEssentialEmails = onlySendEssentialEmails,
    callToAction = callToAction?.toEmailCallToAction(),
)

internal fun EmailCallToActionResponse.toEmailCallToAction(): EmailCallToAction = EmailCallToAction(
    actionTarget = actionTarget.toUri(),
    headline = headline,
    actionText = actionText
)

internal fun OauthResponse.toOauth(): Oauth = Oauth(
    authorizationEndpoint = authorizationEndpoint.toUri(),
    clientId = clientId,
    grantType = grantType
)

internal fun List<ApplicationOwnerDetailsResponse>.toApplicationOwnerDetails(): List<ApplicationOwnerDetails> = map { owner ->
    ApplicationOwnerDetails(
        userId = owner.userId.toUUID(),
        email = owner.email,
        displayName = owner.displayName,
        orphan = owner.orphan,
        foreign = owner.foreign
    )
}

internal fun GetLogoUploadUrlResponse.toGetLogoUploadUrl(): GetLogoUploadUrl = GetLogoUploadUrl(
    uploadUrl = uploadUrl.toUrl()
)