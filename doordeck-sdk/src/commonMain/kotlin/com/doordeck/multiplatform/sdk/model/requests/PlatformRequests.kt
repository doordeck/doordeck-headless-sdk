package com.doordeck.multiplatform.sdk.model.requests

import com.doordeck.multiplatform.sdk.model.data.BasicPlatform
import kotlinx.serialization.Serializable

@Serializable
internal data class CreateApplicationRequest(
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
internal sealed interface UpdateApplicationRequest

@Serializable
internal data class UpdateApplicationNameRequest(
    val name: String
): UpdateApplicationRequest

@Serializable
internal data class UpdateApplicationCompanyNameRequest(
    val companyName: String
): UpdateApplicationRequest

@Serializable
internal data class UpdateApplicationMailingAddressRequest(
    val mailingAddress: String
): UpdateApplicationRequest

@Serializable
internal data class UpdateApplicationPrivacyPolicyRequest(
    val privacyPolicy: String
): UpdateApplicationRequest

@Serializable
internal data class UpdateApplicationSupportContactRequest(
    val supportContact: String
): UpdateApplicationRequest

@Serializable
internal data class UpdateApplicationAppLinkRequest(
    val appLink: String
): UpdateApplicationRequest

@Serializable
internal data class UpdateApplicationEmailPreferencesRequest(
    val emailPreferences: EmailPreferencesRequest
): UpdateApplicationRequest

@Serializable
internal data class UpdateApplicationLogoUrlRequest(
    val logoUrl: String
): UpdateApplicationRequest

@Serializable
internal data class EmailPreferencesRequest(
    val senderEmail: String? = null,
    val senderName: String? = null,
    val primaryColour: String? = null,
    val secondaryColour: String? = null,
    val onlySendEssentialEmails: Boolean? = null,
    val callToAction: CallToActionRequest? = null
)

@Serializable
internal data class CallToActionRequest(
    val actionTarget: String,
    val headline: String,
    val actionText: String
)

@Serializable
internal data class AddAuthIssuerRequest(
    val url: String
)

@Serializable
internal data class DeleteAuthIssuerRequest(
    val url: String
)

@Serializable
internal data class AddCorsDomainRequest(
    val url: String
)

@Serializable
internal data class RemoveCorsDomainRequest(
    val url: String
)

@Serializable
internal data class AddApplicationOwnerRequest(
    val userId: String
)

@Serializable
internal data class RemoveApplicationOwnerRequest(
    val userId: String
)

@Serializable
internal data class GetLogoUploadUrlRequest(
    val contentType: String
)

@Serializable
internal sealed interface AddAuthKeyRequest {
    val kid: String
    val kty: String
    val use: String
    val alg: String?
}

@Serializable
internal data class AddRsaKeyRequest(
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
internal data class AddEcKeyRequest(
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
internal data class AddEd25519KeyRequest(
    override val kty: String,
    override val use: String,
    override val kid: String,
    override val alg: String? = null,
    val d: String,
    val crv: String,
    val x: String
): AddAuthKeyRequest

internal fun BasicPlatform.BasicCreateApplication.toCreateApplicationRequest(): CreateApplicationRequest = CreateApplicationRequest(
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

internal fun BasicPlatform.BasicAuthKey.toAddAuthKeyRequest(): AddAuthKeyRequest = when(this) {
    is BasicPlatform.BasicRsaKey -> AddRsaKeyRequest(kty, use, kid, alg, p, q, d, e, qi, dp, dq, n)
    is BasicPlatform.BasicEcKey -> AddEcKeyRequest(kty, use, kid, alg, d, crv, x, y)
    is BasicPlatform.BasicEd25519Key -> AddEd25519KeyRequest(kty, use, kid, alg, d, crv, x)
}