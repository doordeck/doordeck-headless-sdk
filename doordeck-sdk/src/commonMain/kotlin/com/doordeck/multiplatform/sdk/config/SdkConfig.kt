package com.doordeck.multiplatform.sdk.config

import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.storage.SecureStorage

/**
 * Configuration settings for the SDK.
 */
internal data class BasicSdkConfig(
    val apiEnvironment: ApiEnvironment,
    val cloudAuthToken: String? = null,
    val cloudRefreshToken: String? = null,
    val fusionHost: String? = null,
    val secureStorage: SecureStorage,
    val debugLogging: Boolean? = null
)