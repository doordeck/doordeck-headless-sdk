package com.doordeck.multiplatform.sdk.api.model

import kotlin.js.JsExport
import kotlin.jvm.JvmOverloads

@JsExport
object Platform {

    class CreateApplication @JvmOverloads constructor(
        val name: String,
        val companyName: String,
        val mailingAddress: String,
        val privacyPolicy: String? = null,
        val supportContact: String? = null,
        val appLink: String? = null,
        val emailPreferences: EmailPreferences? = null,
        val logoUrl: String? = null
    )

    class EmailPreferences @JvmOverloads constructor(
        val senderEmail: String? = null,
        val senderName: String? = null,
        val primaryColour: String? = null,
        val secondaryColour: String? = null,
        val onlySendEssentialEmails: Boolean? = null,
        val callToAction: EmailCallToAction? = null
    )

    class EmailCallToAction(
        val actionTarget: String,
        val headline: String,
        val actionText: String
    )

    sealed interface AuthKey {
        val kid: String
        val kty: String
        val use: String
        val alg: String?
    }

    class RsaKey(
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
    ): AuthKey

    class EcKey(
        override val kty: String,
        override val use: String,
        override val kid: String,
        override val alg: String? = null,
        val d: String,
        val crv: String,
        val x: String,
        val y: String
    ): AuthKey

    class Ed25519Key(
        override val kty: String,
        override val use: String,
        override val kid: String,
        override val alg: String? = null,
        val d: String,
        val crv: String,
        val x: String
    ): AuthKey
}