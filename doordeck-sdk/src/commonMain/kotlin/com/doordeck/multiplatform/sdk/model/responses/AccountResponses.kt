@file:UseSerializers(PlatformIdSerializer::class, PlatformPublicKeySerializer::class, PlatformCertificateSerializer::class)

package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.model.common.TwoFactorMethod
import com.doordeck.multiplatform.sdk.model.values.PlatformCertificate
import com.doordeck.multiplatform.sdk.model.values.PlatformCertificateSerializer
import com.doordeck.multiplatform.sdk.model.values.PlatformId
import com.doordeck.multiplatform.sdk.model.values.PlatformIdSerializer
import com.doordeck.multiplatform.sdk.model.values.PlatformPublicKey
import com.doordeck.multiplatform.sdk.model.values.PlatformPublicKeySerializer
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
    val publicKey: PlatformPublicKey
)

@JsExport
@Serializable
data class RegisterEphemeralKeyResponse(
    val certificateChain: List<PlatformCertificate>,
    val userId: PlatformId
)

@JsExport
@Serializable
data class RegisterEphemeralKeyWithSecondaryAuthenticationResponse(
    val method: TwoFactorMethod
)