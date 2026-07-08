package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.util.epochSecondToInstant
import java.time.Instant

data class AssistedLoginResponse(
    val requiresVerification: Boolean,
    val requiresRetry: Boolean
)

data class AssistedRegisterEphemeralKeyResponse(
    val requiresVerification: Boolean,
    val requiresRetry: Boolean
)

data class ServerTimeResponse(
    val now: Instant
)

@JvmSynthetic
internal fun BasicAssistedLoginResponse.toAssistedLoginResponse(): AssistedLoginResponse = AssistedLoginResponse(
    requiresVerification = requiresVerification,
    requiresRetry = requiresRetry
)

@JvmSynthetic
internal fun BasicAssistedRegisterEphemeralKeyResponse.toAssistedRegisterEphemeralKeyResponse(): AssistedRegisterEphemeralKeyResponse = AssistedRegisterEphemeralKeyResponse(
    requiresVerification = requiresVerification,
    requiresRetry = requiresRetry
)

@JvmSynthetic
internal fun BasicServerTimeResponse.toServerTimeResponse(): ServerTimeResponse = ServerTimeResponse(
    now.epochSecondToInstant()
)