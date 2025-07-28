package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.crypto.CryptoManager.toCertificate
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.toPublicKey
import com.doordeck.multiplatform.sdk.model.common.TwoFactorMethod
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.toUuid
import java.security.PublicKey
import java.security.cert.X509Certificate
import java.util.UUID

data class TokenResponse(
    val authToken: String,
    val refreshToken: String
)

data class UserDetailsResponse(
    val email: String,
    val displayName: String? = null,
    val emailVerified: Boolean,
    val publicKey: PublicKey
)

data class RegisterEphemeralKeyResponse(
    val certificateChain: List<X509Certificate>,
    val userId: UUID
)

data class RegisterEphemeralKeyWithSecondaryAuthenticationResponse(
    val method: TwoFactorMethod
)

internal fun BasicTokenResponse.toTokenResponse(): TokenResponse = TokenResponse(
    authToken = authToken,
    refreshToken = refreshToken
)

internal fun BasicUserDetailsResponse.toUserDetailsResponse(): UserDetailsResponse = UserDetailsResponse(
    email = email,
    displayName = displayName,
    emailVerified = emailVerified,
    publicKey = publicKey.decodeBase64ToByteArray().toPublicKey()
)

internal fun BasicRegisterEphemeralKeyResponse.toRegisterEphemeralKeyResponse(): RegisterEphemeralKeyResponse = RegisterEphemeralKeyResponse(
    certificateChain = certificateChain.map { it.toCertificate() },
    userId = userId.toUuid()
)

internal fun BasicRegisterEphemeralKeyWithSecondaryAuthenticationResponse.toRegisterEphemeralKeyWithSecondaryAuthentication(): RegisterEphemeralKeyWithSecondaryAuthenticationResponse = RegisterEphemeralKeyWithSecondaryAuthenticationResponse(
    method = method
)