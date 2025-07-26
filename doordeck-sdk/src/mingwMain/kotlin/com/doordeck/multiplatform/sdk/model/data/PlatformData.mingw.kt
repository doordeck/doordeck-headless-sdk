package com.doordeck.multiplatform.sdk.model.data

import kotlinx.serialization.Serializable

@Serializable
data class CreateApplicationData(
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
data class EmailPreferencesData(
    val senderEmail: String? = null,
    val senderName: String? = null,
    val primaryColour: String? = null,
    val secondaryColour: String? = null,
    val onlySendEssentialEmails: Boolean? = null,
    val callToAction: EmailCallToActionData? = null
)

@Serializable
data class EmailCallToActionData(
    val actionTarget: String,
    val headline: String,
    val actionText: String
)

@Serializable
data class ApplicationIdData(
    val applicationId: String
)

@Serializable
data class UpdateApplicationNameData(
    val applicationId: String,
    val name: String
)

@Serializable
data class UpdateApplicationCompanyNameData(
    val applicationId: String,
    val companyName: String
)

@Serializable
data class UpdateApplicationMailingAddressData(
    val applicationId: String,
    val mailingAddress: String
)

@Serializable
data class UpdateApplicationPrivacyPolicyData(
    val applicationId: String,
    val privacyPolicy: String
)

@Serializable
data class UpdateApplicationSupportContactData(
    val applicationId: String,
    val supportContact: String
)

@Serializable
data class UpdateApplicationAppLinkData(
    val applicationId: String,
    val appLink: String
)

@Serializable
data class UpdateApplicationEmailPreferencesData(
    val applicationId: String,
    val emailPreferences: EmailPreferencesData
)

@Serializable
data class UpdateApplicationLogoUrlData(
    val applicationId: String,
    val logoUrl: String
)

@Serializable
data class GetLogoUploadUrlData(
    val applicationId: String,
    val contentType: String
)

@Serializable
data class AddAuthKeyData(
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
data class RsaKeyData(
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
data class EcKeyData(
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
data class Ed25519KeyData(
    override val kty: String = "OKP",
    override val use: String,
    override val kid: String,
    override val alg: String? = null,
    val d: String,
    val crv: String,
    val x: String
): AuthKeyData

@Serializable
data class AuthIssuerData(
    val applicationId: String,
    val url: String
)

@Serializable
data class CorsDomainData(
    val applicationId: String,
    val url: String
)

@Serializable
data class ApplicationOwnerData(
    val applicationId: String,
    val userId: String
)

internal fun CreateApplicationData.toCreateApplication() = BasicCreateApplication(
    name = name,
    companyName = companyName,
    mailingAddress = mailingAddress,
    privacyPolicy = privacyPolicy,
    supportContact = supportContact,
    appLink = appLink,
    emailPreferences = emailPreferences?.toEmailPreferences(),
    logoUrl = logoUrl
)

internal fun EmailPreferencesData.toEmailPreferences() = BasicEmailPreferences(
    senderEmail = senderEmail,
    senderName = senderName,
    primaryColour = primaryColour,
    secondaryColour = secondaryColour,
    onlySendEssentialEmails = onlySendEssentialEmails,
    callToAction = callToAction?.toEmailCallToAction()
)

internal fun EmailCallToActionData.toEmailCallToAction() = BasicEmailCallToAction(
    actionTarget = actionTarget,
    headline = headline,
    actionText = actionText
)

internal fun AuthKeyData.toAuthKey() = when(this) {
    is RsaKeyData -> BasicRsaKey(kty, use, kid, alg, p, q, d, e, qi, dp, dq, n)
    is EcKeyData -> BasicEcKey(kty, use, kid, alg, d, crv, x, y)
    is Ed25519KeyData -> BasicEd25519Key(kty, use, kid, alg, d, crv, x)
}