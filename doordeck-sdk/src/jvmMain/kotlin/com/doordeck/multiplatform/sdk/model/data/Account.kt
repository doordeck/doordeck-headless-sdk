package com.doordeck.multiplatform.sdk.model.data

import com.doordeck.multiplatform.sdk.crypto.CryptoManager.toCertificate
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.toPublicKey
import com.doordeck.multiplatform.sdk.model.common.TwoFactorMethod
import com.doordeck.multiplatform.sdk.model.responses.RegisterEphemeralKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.RegisterEphemeralKeyWithSecondaryAuthenticationResponse
import com.doordeck.multiplatform.sdk.model.responses.TokenResponse
import com.doordeck.multiplatform.sdk.model.responses.UserDetailsResponse
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.toUUID
import java.security.PublicKey
import java.security.cert.X509Certificate
import java.util.UUID

data class Token(
    val authToken: String,
    val refreshToken: String
)

data class UserDetails(
    val email: String,
    val displayName: String? = null,
    val emailVerified: Boolean,
    val publicKey: PublicKey
)

data class RegisterEphemeralKey(
    val certificateChain: List<X509Certificate>,
    val userId: UUID
)

data class RegisterEphemeralKeyWithSecondaryAuthentication(
    val method: TwoFactorMethod
)

internal fun TokenResponse.toToken(): Token = Token(
    authToken = authToken,
    refreshToken = refreshToken
)

internal fun UserDetailsResponse.toUserDetails(): UserDetails = UserDetails(
    email = email,
    displayName = displayName,
    emailVerified = emailVerified,
    publicKey = publicKey.decodeBase64ToByteArray().toPublicKey()
)

internal fun RegisterEphemeralKeyResponse.toRegisterEphemeralKey(): RegisterEphemeralKey = RegisterEphemeralKey(
    certificateChain = certificateChain.map { it.toCertificate() },
    userId = userId.toUUID()
)

internal fun RegisterEphemeralKeyWithSecondaryAuthenticationResponse.toRegisterEphemeralKeyWithSecondaryAuthentication(): RegisterEphemeralKeyWithSecondaryAuthentication = RegisterEphemeralKeyWithSecondaryAuthentication(
    method = method
)