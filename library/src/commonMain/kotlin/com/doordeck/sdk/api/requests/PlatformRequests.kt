package com.doordeck.sdk.api.requests

import com.doordeck.sdk.api.model.Platform
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
sealed interface UpdateApplicationRequest

@Serializable
class UpdateApplicationNameRequest(
    val name: String
): UpdateApplicationRequest

@Serializable
class UpdateApplicationCompanyNameRequest(
    val companyName: String
): UpdateApplicationRequest

@Serializable
class UpdateApplicationMailingAddressRequest(
    val mailingAddress: String
): UpdateApplicationRequest

@Serializable
class UpdateApplicationPrivacyPolicyRequest(
    val privacyPolicy: String
): UpdateApplicationRequest

@Serializable
class UpdateApplicationSupportContactRequest(
    val supportContact: String
): UpdateApplicationRequest

@Serializable
class UpdateApplicationAppLinkRequest(
    val appLink: String
): UpdateApplicationRequest

@Serializable
class UpdateApplicationEmailPreferencesRequest(
    val emailPreferences: EmailPreferencesRequest
): UpdateApplicationRequest

@Serializable
class UpdateApplicationLogoUrlRequest(
    val logoUrl: String
): UpdateApplicationRequest

@Serializable
class EmailPreferencesRequest(
    val senderEmail: String? = null,
    val senderName: String? = null,
    val primaryColour: String? = null,
    val secondaryColour: String? = null,
    val onlySendEssentialEmails: Boolean? = null,
    val callToAction: CallToActionRequest? = null
)

@Serializable
class CallToActionRequest(
    val actionTarget: String,
    val headline: String,
    val actionText: String
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

@Serializable
sealed interface AddAuthKeyRequest {
    val kid: String
    val kty: String
    val use: String
    val alg: String?
}

@Serializable
class AddRsaKeyRequest(
    override val kty: String,
    override val use: String,
    override val kid: String,
    override val alg: String? = null,
    val p: String,
    val q: String,
    val d: String,
    val e: String,
    val qi: String,
    val dp: String,
    val dq: String,
    val n: String
): AddAuthKeyRequest

@Serializable
class AddEcKeyRequest(
    override val kty: String,
    override val use: String,
    override val kid: String,
    override val alg: String? = null,
    val d: String,
    val crv: String,
    val x: String,
    val y: String
): AddAuthKeyRequest

@Serializable
class AddEd25519KeyRequest(
    override val kty: String,
    override val use: String,
    override val kid: String,
    override val alg: String? = null,
    val d: String,
    val crv: String,
    val x: String
): AddAuthKeyRequest

internal fun Platform.CreateApplication.toCreateApplicationRequest(): CreateApplicationRequest = CreateApplicationRequest(
    name = name,
    companyName = companyName,
    mailingAddress = mailingAddress,
    privacyPolicy = privacyPolicy,
    supportContact = supportContact,
    appLink = appLink,
    emailPreferences = emailPreferences?.let { emailPreference ->
        EmailPreferencesRequest(
            senderEmail = emailPreference.senderEmail,
            senderName = emailPreference.senderName,
            primaryColour = emailPreference.primaryColour,
            secondaryColour = emailPreference.secondaryColour,
            onlySendEssentialEmails = emailPreference.onlySendEssentialEmails,
            callToAction = emailPreference.callToAction?.let { callToAction ->
                CallToActionRequest(
                    actionTarget = callToAction.actionTarget,
                    headline = callToAction.headline,
                    actionText = callToAction.actionText
                )
            }
        )
    },
    logoUrl = logoUrl
)

internal fun Platform.AuthKey.toAddAuthKeyRequest(): AddAuthKeyRequest = when(this) {
    is Platform.RsaKey -> AddRsaKeyRequest(kty, use, kid, alg, p, q, d, e, qi, dp, dq, n)
    is Platform.EcKey -> AddEcKeyRequest(kty, use, kid, alg, d, crv, x, y)
    is Platform.Ed25519Key -> AddEd25519KeyRequest(kty, use, kid, alg, d, crv, x)
}