package com.doordeck.sdk.api.requests

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
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

@JsExport
@Serializable
class EmailPreferencesRequest(
    val senderEmail: String? = null,
    val senderName: String? = null,
    val primaryColour: String? = null,
    val secondaryColour: String? = null,
    val callToAction: CallToActionRequest? = null
)

@JsExport
@Serializable
class CallToActionRequest(
    val actionTarget: String,
    val headline: String,
    val actionTest: String
)