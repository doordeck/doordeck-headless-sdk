package com.doordeck.multiplatform.sdk.api.responses

import com.doordeck.multiplatform.sdk.api.model.Crypto
import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
class AssistedLoginResponse(
    val tokenResponse: TokenResponse,
    val keyPair: Crypto.KeyPair
)