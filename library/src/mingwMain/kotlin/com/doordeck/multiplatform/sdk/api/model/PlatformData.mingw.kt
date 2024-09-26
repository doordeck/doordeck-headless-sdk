package com.doordeck.multiplatform.sdk.api.model

import kotlinx.serialization.Serializable

@Serializable
class CreateApplicationData(
    val name: String,
    val companyName: String,
    val mailingAddress: String,
    val privacyPolicy: String? = null,
    val supportContact: String? = null,
    val appLink: String? = null,
    val emailPreferences: EmailPreferencesData? = null,
    val logoUrl: String? = null
)

@Serializable
class EmailPreferencesData(
    val senderEmail: String? = null,
    val senderName: String? = null,
    val primaryColour: String? = null,
    val secondaryColour: String? = null,
    val onlySendEssentialEmails: Boolean? = null,
    val callToAction: EmailCallToActionData? = null
)

@Serializable
class EmailCallToActionData(
    val actionTarget: String,
    val headline: String,
    val actionText: String
)

@Serializable
class GetApplicationData(
    val applicationId: String
)

@Serializable
class UpdateApplicationNameData(
    val applicationId: String,
    val name: String
)

@Serializable
class UpdateApplicationCompanyNameData(
    val applicationId: String,
    val companyName: String
)

@Serializable
class UpdateApplicationMailingAddressData(
    val applicationId: String,
    val mailingAddress: String
)

@Serializable
class UpdateApplicationPrivacyPolicyData(
    val applicationId: String,
    val privacyPolicy: String
)

@Serializable
class UpdateApplicationSupportContactData(
    val applicationId: String,
    val supportContact: String
)

@Serializable
class UpdateApplicationAppLinkData(
    val applicationId: String,
    val appLink: String
)

@Serializable
class UpdateApplicationEmailPreferencesData(
    val applicationId: String,
    val emailPreferences: EmailPreferencesData
)

@Serializable
class UpdateApplicationLogoUrlData(
    val applicationId: String,
    val logoUrl: String
)

@Serializable
class DeleteApplicationData(
    val applicationId: String
)

@Serializable
class GetLogoUploadUrlData(
    val applicationId: String,
    val contentType: String
)

@Serializable
class AddAuthKeyData(
    val applicationId: String,
    val key: AuthKeyData
)

@Serializable
sealed interface AuthKeyData {
    val kid: String
    val kty: String
    val use: String
    val alg: String?
}

@Serializable
class RsaKeyData(
    override val kty: String = "RSA",
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
): AuthKeyData

@Serializable
class EcKeyData(
    override val kty: String = "EC",
    override val use: String,
    override val kid: String,
    override val alg: String? = null,
    val d: String,
    val crv: String,
    val x: String,
    val y: String
): AuthKeyData

@Serializable
class Ed25519KeyData(
    override val kty: String = "OKP",
    override val use: String,
    override val kid: String,
    override val alg: String? = null,
    val d: String,
    val crv: String,
    val x: String
): AuthKeyData

@Serializable
class AddAuthIssuerData(
    val applicationId: String,
    val url: String
)

@Serializable
class DeleteAuthIssuerData(
    val applicationId: String,
    val url: String
)

@Serializable
class AddCorsDomainData(
    val applicationId: String,
    val url: String
)

@Serializable
class RemoveCorsDomainData(
    val applicationId: String,
    val url: String
)

@Serializable
class AddApplicationOwnerData(
    val applicationId: String,
    val userId: String
)

@Serializable
class RemoveApplicationOwnerData(
    val applicationId: String,
    val userId: String
)

@Serializable
class GetApplicationOwnersDetailsData(
    val applicationId: String
)

internal fun CreateApplicationData.toCreateApplication() = Platform.CreateApplication(
    name = name,
    companyName = companyName,
    mailingAddress = mailingAddress,
    privacyPolicy = privacyPolicy,
    supportContact = supportContact,
    appLink = appLink,
    emailPreferences = emailPreferences?.toEmailPreferences(),
    logoUrl = logoUrl
)

internal fun EmailPreferencesData.toEmailPreferences() = Platform.EmailPreferences(
    senderEmail = senderEmail,
    senderName = senderName,
    primaryColour = primaryColour,
    secondaryColour = secondaryColour,
    onlySendEssentialEmails = onlySendEssentialEmails,
    callToAction = callToAction?.toEmailCallToAction()
)

internal fun EmailCallToActionData.toEmailCallToAction() = Platform.EmailCallToAction(
    actionTarget = actionTarget,
    headline = headline,
    actionText = actionText
)

internal fun AuthKeyData.toAuthKey() = when(this) {
    is RsaKeyData -> Platform.RsaKey(kty, use, kid, alg, p, q, d, e, qi, dp, dq, n)
    is EcKeyData -> Platform.EcKey(kty, use, kid, alg, d, crv, x, y)
    is Ed25519KeyData -> Platform.Ed25519Key(kty, use, kid, alg, d, crv, x)
}