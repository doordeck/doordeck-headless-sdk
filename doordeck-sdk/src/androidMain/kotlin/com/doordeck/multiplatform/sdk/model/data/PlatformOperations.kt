package com.doordeck.multiplatform.sdk.model.data

import com.doordeck.multiplatform.sdk.model.data.PlatformOperations.CreateApplication

object PlatformOperations {

    data class CreateApplication(
        val name: String,
        val companyName: String,
        val mailingAddress: String,
        val privacyPolicy: String? = null,
        val supportContact: String? = null,
        val appLink: String? = null,
        val emailPreferences: EmailPreferences? = null,
        val logoUrl: String? = null
    ) {
        class Builder {
            private var name: String? = null
            private var companyName: String? = null
            private var mailingAddress: String? = null
            private var privacyPolicy: String? = null
            private var supportContact: String? = null
            private var appLink: String? = null
            private var emailPreferences: EmailPreferences? = null
            private var logoUrl: String? = null

            fun setName(name: String) = apply { this.name = name }
            fun setCompanyName(companyName: String) = apply { this.companyName = companyName }
            fun setMailingAddress(mailingAddress: String) = apply { this.mailingAddress = mailingAddress }
            fun setPrivacyPolicy(privacyPolicy: String?) = apply { this.privacyPolicy = privacyPolicy }
            fun setSupportContact(supportContact: String?) = apply { this.supportContact = supportContact }
            fun setAppLink(appLink: String?) = apply { this.appLink = appLink }
            fun setEmailPreferences(emailPreferences: EmailPreferences?) = apply { this.emailPreferences = emailPreferences }
            fun setLogoUrl(logoUrl: String?) = apply { this.logoUrl = logoUrl }

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

    data class EmailPreferences(
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
        val actionTarget: String,
        val headline: String,
        val actionText: String
    ) {
        class Builder {
            private var actionTarget: String? = null
            private var headline: String? = null
            private var actionText: String? = null

            fun setActionTarget(actionTarget: String) = apply { this.actionTarget = actionTarget }
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

    sealed interface AuthKey {
        val kid: String
        val kty: String
        val use: String
        val alg: String?
    }

    data class RsaKey(
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
    ): AuthKey {
        class Builder {
            private var kty: String = "RSA"
            private var use: String? = null
            private var kid: String? = null
            private var alg: String? = null
            private var p: String? = null
            private var q: String? = null
            private var d: String? = null
            private var e: String? = null
            private var qi: String? = null
            private var dp: String? = null
            private var dq: String? = null
            private var n: String? = null

            fun setKty(kty: String) = apply { this.kty = kty }
            fun setUse(use: String) = apply { this.use = use }
            fun setKid(kid: String) = apply { this.kid = kid }
            fun setAlg(alg: String?) = apply { this.alg = alg }
            fun setP(p: String) = apply { this.p = p }
            fun setQ(q: String) = apply { this.q = q }
            fun setD(d: String) = apply { this.d = d }
            fun setE(e: String) = apply { this.e = e }
            fun setQi(qi: String) = apply { this.qi = qi }
            fun setDp(dp: String) = apply { this.dp = dp }
            fun setDq(dq: String) = apply { this.dq = dq }
            fun setN(n: String) = apply { this.n = n }

            fun build(): RsaKey {
                return RsaKey(
                    kty = kty,
                    use = requireNotNull(use),
                    kid = requireNotNull(kid),
                    alg = alg,
                    p = requireNotNull(p),
                    q = requireNotNull(q),
                    d = requireNotNull(d),
                    e = requireNotNull(e),
                    qi = requireNotNull(qi),
                    dp = requireNotNull(dp),
                    dq = requireNotNull(dq),
                    n = requireNotNull(n)
                )
            }
        }
    }

    data class EcKey(
        override val kty: String = "EC",
        override val use: String,
        override val kid: String,
        override val alg: String? = null,
        val d: String,
        val crv: String,
        val x: String,
        val y: String
    ): AuthKey {
        class Builder {
            private var kty: String = "EC"
            private var use: String? = null
            private var kid: String? = null
            private var alg: String? = null
            private var d: String? = null
            private var crv: String? = null
            private var x: String? = null
            private var y: String? = null

            fun setKty(kty: String) = apply { this.kty = kty }
            fun setUse(use: String) = apply { this.use = use }
            fun setKid(kid: String) = apply { this.kid = kid }
            fun setAlg(alg: String?) = apply { this.alg = alg }
            fun setD(d: String) = apply { this.d = d }
            fun setCrv(crv: String) = apply { this.crv = crv }
            fun setX(x: String) = apply { this.x = x }
            fun setY(y: String) = apply { this.y = y }

            fun build(): EcKey {
                return EcKey(
                    kty = kty,
                    use = requireNotNull(use),
                    kid = requireNotNull(kid),
                    alg = alg,
                    d = requireNotNull(d),
                    crv = requireNotNull(crv),
                    x = requireNotNull(x),
                    y = requireNotNull(y)
                )
            }
        }
    }

    data class Ed25519Key(
        override val kty: String = "OKP",
        override val use: String,
        override val kid: String,
        override val alg: String? = null,
        val d: String,
        val crv: String,
        val x: String
    ): AuthKey {
        class Builder {
            private var kty: String = "OKP"
            private var use: String? = null
            private var kid: String? = null
            private var alg: String? = null
            private var d: String? = null
            private var crv: String? = null
            private var x: String? = null

            fun setKty(kty: String) = apply { this.kty = kty }
            fun setUse(use: String) = apply { this.use = use }
            fun setKid(kid: String) = apply { this.kid = kid }
            fun setAlg(alg: String?) = apply { this.alg = alg }
            fun setD(d: String) = apply { this.d = d }
            fun setCrv(crv: String) = apply { this.crv = crv }
            fun setX(x: String) = apply { this.x = x }

            fun build(): Ed25519Key {
                return Ed25519Key(
                    kty = kty,
                    use = requireNotNull(use),
                    kid = requireNotNull(kid),
                    alg = alg,
                    d = requireNotNull(d),
                    crv = requireNotNull(crv),
                    x = requireNotNull(x)
                )
            }
        }
    }
}

internal fun CreateApplication.toBasicCreateApplication(): BasicCreateApplication {
    return BasicCreateApplication(
        name = name,
        companyName = companyName,
        mailingAddress = mailingAddress,
        privacyPolicy = privacyPolicy,
        supportContact = supportContact,
        appLink = appLink,
        emailPreferences = emailPreferences?.toBasicEmailPreferences(),
        logoUrl = logoUrl
    )
}

internal fun PlatformOperations.AuthKey.toBasicAuthKey () = when(this) {
    is PlatformOperations.RsaKey -> BasicRsaKey(kty, use, kid, alg, p, q, d, e, qi, dp, dq, n)
    is PlatformOperations.EcKey -> BasicEcKey(kty, use, kid, alg, d, crv, x, y)
    is PlatformOperations.Ed25519Key -> BasicEd25519Key(kty, use, kid, alg, d, crv, x)
}

internal fun PlatformOperations.EmailPreferences.toBasicEmailPreferences(): BasicEmailPreferences {
    return BasicEmailPreferences(
        senderEmail = senderEmail,
        senderName = senderName,
        primaryColour = primaryColour,
        secondaryColour = secondaryColour,
        onlySendEssentialEmails = onlySendEssentialEmails,
        callToAction = callToAction?.toBasicEmailCallToAction()
    )
}

internal fun PlatformOperations.EmailCallToAction.toBasicEmailCallToAction(): BasicEmailCallToAction {
    return BasicEmailCallToAction(
        actionTarget = actionTarget,
        headline = headline,
        actionText = actionText
    )
}