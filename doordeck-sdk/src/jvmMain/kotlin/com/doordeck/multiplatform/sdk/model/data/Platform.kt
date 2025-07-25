package com.doordeck.multiplatform.sdk.model.data

import com.doordeck.multiplatform.sdk.model.data.Platform.CreateApplication
import com.nimbusds.jose.jwk.ECKey
import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.OctetKeyPair
import com.nimbusds.jose.jwk.RSAKey
import java.net.URI

object Platform {

    data class CreateApplication @JvmOverloads constructor(
        val name: String,
        val companyName: String,
        val mailingAddress: String,
        val privacyPolicy: URI? = null,
        val supportContact: URI? = null,
        val appLink: URI? = null,
        val emailPreferences: EmailPreferences? = null,
        val logoUrl: URI? = null
    ) {
        class Builder {
            private var name: String? = null
            private var companyName: String? = null
            private var mailingAddress: String? = null
            private var privacyPolicy: URI? = null
            private var supportContact: URI? = null
            private var appLink: URI? = null
            private var emailPreferences: EmailPreferences? = null
            private var logoUrl: URI? = null

            fun setName(name: String) = apply { this.name = name }
            fun setCompanyName(companyName: String) = apply { this.companyName = companyName }
            fun setMailingAddress(mailingAddress: String) = apply { this.mailingAddress = mailingAddress }
            fun setPrivacyPolicy(privacyPolicy: URI?) = apply { this.privacyPolicy = privacyPolicy }
            fun setSupportContact(supportContact: URI?) = apply { this.supportContact = supportContact }
            fun setAppLink(appLink: URI?) = apply { this.appLink = appLink }
            fun setEmailPreferences(emailPreferences: EmailPreferences?) = apply { this.emailPreferences = emailPreferences }
            fun setLogoUrl(logoUrl: URI?) = apply { this.logoUrl = logoUrl }

            fun build(): CreateApplication {
                return CreateApplication(
                    name = requireNotNull(name),
                    companyName = requireNotNull(companyName),
                    mailingAddress = requireNotNull(mailingAddress),
                    privacyPolicy = privacyPolicy,
                    supportContact = supportContact,
                    appLink = appLink,
                    emailPreferences = emailPreferences,
                    logoUrl = logoUrl
                )
            }
        }
    }

    data class EmailPreferences @JvmOverloads constructor(
        val senderEmail: String? = null,
        val senderName: String? = null,
        val primaryColour: String? = null,
        val secondaryColour: String? = null,
        val onlySendEssentialEmails: Boolean? = null,
        val callToAction: EmailCallToAction? = null
    ) {
        class Builder {
            private var senderEmail: String? = null
            private var senderName: String? = null
            private var primaryColour: String? = null
            private var secondaryColour: String? = null
            private var onlySendEssentialEmails: Boolean? = null
            private var callToAction: EmailCallToAction? = null

            fun setSenderEmail(senderEmail: String?) = apply { this.senderEmail = senderEmail }
            fun setSenderName(senderName: String?) = apply { this.senderName = senderName }
            fun setPrimaryColour(primaryColour: String?) = apply { this.primaryColour = primaryColour }
            fun setSecondaryColour(secondaryColour: String?) = apply { this.secondaryColour = secondaryColour }
            fun setOnlySendEssentialEmails(onlySendEssentialEmails: Boolean?) = apply { this.onlySendEssentialEmails = onlySendEssentialEmails }
            fun setCallToAction(callToAction: EmailCallToAction?) = apply { this.callToAction = callToAction }

            fun build(): EmailPreferences {
                return EmailPreferences(
                    senderEmail = senderEmail,
                    senderName = senderName,
                    primaryColour = primaryColour,
                    secondaryColour = secondaryColour,
                    onlySendEssentialEmails = onlySendEssentialEmails,
                    callToAction = callToAction
                )
            }
        }
    }

    data class EmailCallToAction(
        val actionTarget: URI,
        val headline: String,
        val actionText: String
    ) {
        class Builder {
            private var actionTarget: URI? = null
            private var headline: String? = null
            private var actionText: String? = null

            fun setActionTarget(actionTarget: URI) = apply { this.actionTarget = actionTarget }
            fun setHeadline(headline: String) = apply { this.headline = headline }
            fun setActionText(actionText: String) = apply { this.actionText = actionText }

            fun build(): EmailCallToAction {
                return EmailCallToAction(
                    actionTarget = requireNotNull(actionTarget),
                    headline = requireNotNull(headline),
                    actionText = requireNotNull(actionText),
                )
            }
        }
    }
}

internal fun CreateApplication.toBasicCreateApplication(): BasicPlatform.BasicCreateApplication {
    return BasicPlatform.BasicCreateApplication(
        name = name,
        companyName = companyName,
        mailingAddress = mailingAddress,
        privacyPolicy = privacyPolicy?.toString(),
        supportContact = supportContact?.toString(),
        appLink = appLink?.toString(),
        emailPreferences = emailPreferences?.toBasicEmailPreferences(),
        logoUrl = logoUrl?.toString()
    )
}

internal fun JWK.toBasicAuthKey(): BasicPlatform.BasicAuthKey {
    return when(this) {
        is ECKey -> BasicPlatform.BasicEcKey(
            use = keyUse.value,
            kid = keyID,
            alg = algorithm.name,
            d = d.toString(),
            crv = curve.name,
            x = x.toString(),
            y = y.toString()
        )
        is RSAKey -> BasicPlatform.BasicRsaKey(
            use = keyUse.value,
            kid = keyID,
            alg = algorithm.name,
            p = firstPrimeFactor.toString(),
            q = secondPrimeFactor.toString(),
            d = privateExponent.toString(),
            e = publicExponent.toString(),
            qi = firstCRTCoefficient.toString(),
            dp = firstFactorCRTExponent.toString(),
            dq = secondFactorCRTExponent.toString(),
            n = modulus.toString()
        )
        is OctetKeyPair -> BasicPlatform.BasicEd25519Key(
            use = keyUse.value,
            kid = keyID,
            alg = algorithm.name,
            d = d.toString(),
            crv = curve.name,
            x = x.toString()
        )
        else -> error("")
    }
}

internal fun Platform.EmailPreferences.toBasicEmailPreferences(): BasicPlatform.BasicEmailPreferences {
    return BasicPlatform.BasicEmailPreferences(
        senderEmail = senderEmail,
        senderName = senderName,
        primaryColour = primaryColour,
        secondaryColour = secondaryColour,
        onlySendEssentialEmails = onlySendEssentialEmails,
        callToAction = callToAction?.toBasicEmailCallToAction()
    )
}

internal fun Platform.EmailCallToAction.toBasicEmailCallToAction(): BasicPlatform.BasicEmailCallToAction {
    return BasicPlatform.BasicEmailCallToAction(
        actionTarget = actionTarget.toString(),
        headline = headline,
        actionText = actionText
    )
}