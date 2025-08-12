package com.doordeck.multiplatform.sdk.model.data

import kotlinx.serialization.Serializable

@Serializable
internal data class CreateApplicationData(
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
internal data class EmailPreferencesData(
    val senderEmail: String? = null,
    val senderName: String? = null,
    val primaryColour: String? = null,
    val secondaryColour: String? = null,
    val onlySendEssentialEmails: Boolean? = null,
    val callToAction: EmailCallToActionData? = null
)

@Serializable
internal data class EmailCallToActionData(
    val actionTarget: String,
    val headline: String,
    val actionText: String
)

@Serializable
internal data class ApplicationIdData(
    val applicationId: String
)

@Serializable
internal data class UpdateApplicationNameData(
    val applicationId: String,
    val name: String
)

@Serializable
internal data class UpdateApplicationCompanyNameData(
    val applicationId: String,
    val companyName: String
)

@Serializable
internal data class UpdateApplicationMailingAddressData(
    val applicationId: String,
    val mailingAddress: String
)

@Serializable
internal data class UpdateApplicationPrivacyPolicyData(
    val applicationId: String,
    val privacyPolicy: String
)

@Serializable
internal data class UpdateApplicationSupportContactData(
    val applicationId: String,
    val supportContact: String
)

@Serializable
internal data class UpdateApplicationAppLinkData(
    val applicationId: String,
    val appLink: String
)

@Serializable
internal data class UpdateApplicationEmailPreferencesData(
    val applicationId: String,
    val emailPreferences: EmailPreferencesData
)

@Serializable
internal data class UpdateApplicationLogoUrlData(
    val applicationId: String,
    val logoUrl: String
)

@Serializable
internal data class GetLogoUploadUrlData(
    val applicationId: String,
    val contentType: String
)

@Serializable
internal data class AddAuthKeyData(
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
internal data class RsaKeyData(
    override val kty: String = "RSA",
    override val use: String,
    override val kid: String,
    override val alg: String? = null,
    val e: String,
    val n: String
): AuthKeyData

@Serializable
internal data class EcKeyData(
    override val kty: String = "EC",
    override val use: String,
    override val kid: String,
    override val alg: String? = null,
    val crv: String,
    val x: String,
    val y: String
): AuthKeyData

@Serializable
internal data class Ed25519KeyData(
    override val kty: String = "OKP",
    override val use: String,
    override val kid: String,
    override val alg: String? = null,
    val crv: String,
    val x: String
): AuthKeyData

@Serializable
internal data class AuthIssuerData(
    val applicationId: String,
    val url: String
)

@Serializable
internal data class CorsDomainData(
    val applicationId: String,
    val url: String
)

@Serializable
internal data class ApplicationOwnerData(
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
    is RsaKeyData -> BasicRsaKey(
        kty = kty,
        use = use,
        kid = kid,
        alg = alg,
        e = e,
        n = n
    )
    is EcKeyData -> BasicEcKey(
        kty = kty,
        use = use,
        kid = kid,
        alg = alg,
        crv = crv,
        x = x,
        y = y
    )
    is Ed25519KeyData -> BasicEd25519Key(
        kty = kty,
        use = use,
        kid = kid,
        alg = alg,
        crv = crv,
        x = x
    )
}