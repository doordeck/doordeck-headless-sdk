package com.doordeck.sdk.api.requests

import kotlinx.serialization.Serializable

@Serializable
class CreateApplicationRequest(
    val name: String,
    val companyName: String,
    val mailingAddress: String,
    val privacyPolicy: String? = null,
    val supportContact: String? = null,
    val appLink: String? = null,
    val emailPreferences: EmailPreferencesRequest? = null,
    val logoUrl: String? = null
)

@Serializable
class EmailPreferencesRequest(
    val senderEmail: String? = null,
    val senderName: String? = null,
    val primaryColour: String? = null,
    val secondaryColour: String? = null,
    val callToAction: CallToActionRequest? = null
)

@Serializable
class CallToActionRequest(
    val actionTarget: String,
    val headline: String,
    val actionTest: String
)

@Serializable
class AddAuthIssuerRequest(
    val url: String
)

@Serializable
class DeleteAuthIssuerRequest(
    val url: String
)

@Serializable
class AddCorsDomainRequest(
    val url: String
)

@Serializable
class RemoveCorsDomainRequest(
    val url: String
)

@Serializable
class AddApplicationOwnerRequest(
    val userId: String
)

@Serializable
class RemoveApplicationOwnerRequest(
    val userId: String
)

@Serializable
class GetLogoUploadUrlRequest(
    val contentType: String
)