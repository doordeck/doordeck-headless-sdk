package com.doordeck.multiplatform.sdk.config

import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.storage.SecureStorage

/**
 * Configuration settings for the SDK.
 */
import kotlin.time.Duration

internal data class BasicSdkConfig(
    val apiEnvironment: ApiEnvironment? = null,
    val cloudAuthToken: String? = null,
    val cloudRefreshToken: String? = null,
    val fusionHost: String?,
    val secureStorage: SecureStorage,
    val debugLogging: Boolean? = null,
    /**
     * Moves the SDK's notion of "now". Only ever non-zero when the host process was launched with
     * `DOORDECK_CLOCK_OFFSET_SECONDS` set, which on Apple platforms means a debugger or an XCUITest
     * harness - never a shipped app.
     */
    val clockOffset: Duration = Duration.ZERO
)