package com.doordeck.multiplatform.sdk.model.common

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

@JsExport
@Serializable
enum class ContextState {
    /**
     * Logging in or providing valid authentication tokens in the context is required.
     */
    CLOUD_TOKEN_IS_INVALID_OR_EXPIRED,

    /**
     * Registering an ephemeral key or providing a valid key pair in the context is required.
     */
    KEY_PAIR_IS_INVALID,

    /**
     * Registering an ephemeral key with two-factor authentication or providing a verified public key in the context is required.
     */
    KEY_PAIR_IS_NOT_VERIFIED,

    /**
     * Registering an ephemeral key (to receive a fresh certificate chain from the backend)
     * or providing a valid existing certificate chain in the context is required.
     */
    CERTIFICATE_CHAIN_IS_INVALID_OR_EXPIRED,

    /**
     * Everything is ready - nothing further is required.
     */
    READY
}