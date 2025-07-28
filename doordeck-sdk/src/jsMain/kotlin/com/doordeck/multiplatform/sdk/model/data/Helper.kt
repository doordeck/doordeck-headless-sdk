package com.doordeck.multiplatform.sdk.model.data

import com.doordeck.multiplatform.sdk.model.responses.BasicAssistedLoginResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicAssistedRegisterEphemeralKeyResponse

@JsExport
data class AssistedLogin(
    val requiresVerification: Boolean,
    val requiresRetry: Boolean
)

@JsExport
data class AssistedRegisterEphemeralKey(
    val requiresVerification: Boolean,
    val requiresRetry: Boolean
)

internal fun BasicAssistedLoginResponse.toAssistedLogin(): AssistedLogin = AssistedLogin(
    requiresVerification = requiresVerification,
    requiresRetry = requiresRetry
)

internal fun BasicAssistedRegisterEphemeralKeyResponse.toAssistedRegisterEphemeralKey(): AssistedRegisterEphemeralKey = AssistedRegisterEphemeralKey(
    requiresVerification = requiresVerification,
    requiresRetry = requiresRetry
)