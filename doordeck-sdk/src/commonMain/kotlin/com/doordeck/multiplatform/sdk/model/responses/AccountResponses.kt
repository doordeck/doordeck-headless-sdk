@file:UseSerializers(IdValueSerializer::class, PublicKeyValueSerializer::class, CertificateValueSerializer::class)

package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.model.common.TwoFactorMethod
import com.doordeck.multiplatform.sdk.model.values.CertificateValue
import com.doordeck.multiplatform.sdk.model.values.CertificateValueSerializer
import com.doordeck.multiplatform.sdk.model.values.IdValue
import com.doordeck.multiplatform.sdk.model.values.IdValueSerializer
import com.doordeck.multiplatform.sdk.model.values.PublicKeyValue
import com.doordeck.multiplatform.sdk.model.values.PublicKeyValueSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlin.js.JsExport

@JsExport
@Serializable
data class TokenResponse(
    val authToken: String,
    val refreshToken: String
)

@JsExport
@Serializable
data class UserDetailsResponse(
    val email: String,
    val displayName: String? = null,
    val emailVerified: Boolean,
    val publicKey: PublicKeyValue
)

@JsExport
@Serializable
data class RegisterEphemeralKeyResponse(
    val certificateChain: List<CertificateValue>,
    val userId: IdValue
)

@JsExport
@Serializable
data class RegisterEphemeralKeyWithSecondaryAuthenticationResponse(
    val method: TwoFactorMethod
)