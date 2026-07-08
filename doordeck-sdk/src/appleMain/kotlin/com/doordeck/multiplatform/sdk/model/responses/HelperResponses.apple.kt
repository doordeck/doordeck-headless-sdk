package com.doordeck.multiplatform.sdk.model.responses

import com.doordeck.multiplatform.sdk.util.epochSecondToNsDate
import platform.Foundation.NSDate

data class AssistedLoginResponse(
    val requiresVerification: Boolean,
    val requiresRetry: Boolean
)

data class AssistedRegisterEphemeralKeyResponse(
    val requiresVerification: Boolean,
    val requiresRetry: Boolean
)

data class ServerTimeResponse(
    val now: NSDate
)

internal fun BasicAssistedLoginResponse.toAssistedLoginResponse(): AssistedLoginResponse = AssistedLoginResponse(
    requiresVerification = requiresVerification,
    requiresRetry = requiresRetry
)

internal fun BasicAssistedRegisterEphemeralKeyResponse.toAssistedRegisterEphemeralKeyResponse(): AssistedRegisterEphemeralKeyResponse =
    AssistedRegisterEphemeralKeyResponse(
        requiresVerification = requiresVerification,
        requiresRetry = requiresRetry
    )

internal fun BasicServerTimeResponse.toServerTimeResponse(): ServerTimeResponse = ServerTimeResponse(
    now = now.epochSecondToNsDate()
)