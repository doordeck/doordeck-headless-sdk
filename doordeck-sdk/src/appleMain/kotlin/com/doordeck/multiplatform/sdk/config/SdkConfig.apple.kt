package com.doordeck.multiplatform.sdk.config

import com.doordeck.multiplatform.sdk.ApplicationContext
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.storage.SecureStorage
import com.doordeck.multiplatform.sdk.storage.createSecureStorage
import com.doordeck.multiplatform.sdk.util.toUrlString
import platform.Foundation.NSProcessInfo
import platform.Foundation.NSURLComponents
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Configuration settings for the SDK.
 *
 * This class holds various configuration options for initializing and operating the SDK.
 */
data class SdkConfig(
    val apiEnvironment: ApiEnvironment? = null,
    val cloudAuthToken: String? = null,
    val cloudRefreshToken: String? = null,
    val fusionHost: NSURLComponents?,
    val secureStorage: SecureStorage,
    val debugLogging: Boolean? = null
) {
    /**
     * Builder for constructing [SdkConfig] instances.
     *
     * This builder allows for a fluent API to set the configuration options before building
     * an immutable [SdkConfig] instance.
     */
    class Builder {
        private var apiEnvironment: ApiEnvironment? = null
        private var cloudAuthToken: String? = null
        private var cloudRefreshToken: String? = null
        private var fusionHost: NSURLComponents? = null
        private var secureStorage: SecureStorage? = null
        private var debugLogging: Boolean? = null

        /**
         * Sets the API environment for the SDK.
         */
        fun setApiEnvironment(apiEnvironment: ApiEnvironment?): Builder = apply { this.apiEnvironment = apiEnvironment }

        /**
         * Sets the cloud authentication token.
         */
        fun setCloudAuthToken(cloudAuthToken: String?): Builder = apply { this.cloudAuthToken = cloudAuthToken }

        /**
         * Sets the cloud refresh token.
         */
        fun setCloudRefreshToken(cloudRefreshToken: String?): Builder =
            apply { this.cloudRefreshToken = cloudRefreshToken }

        /**
         * Sets the fusion host i.e: http://localhost:27700
         */
        fun setFusionHost(fusionHost: NSURLComponents?): Builder = apply { this.fusionHost = fusionHost }

        /**
         * Overrides the default secure storage with a custom implementation.
         */
        fun setSecureStorageOverride(secureStorage: SecureStorage?): Builder =
            apply { this.secureStorage = secureStorage }

        /**
         * Enables debug logging. Beware: it may output sensitive information.
         */
        fun setDebugLogging(enabled: Boolean?): Builder = apply { this.debugLogging = enabled }

        /**
         * Builds a new [SdkConfig] instance.
         *
         * If no secure storage override is provided, a default secure storage is created.
         */
        @Throws(Exception::class)
        fun build(): SdkConfig {
            val secureStorage = secureStorage ?: createSecureStorage(ApplicationContext)
            return SdkConfig(
                apiEnvironment = apiEnvironment,
                cloudAuthToken = cloudAuthToken,
                cloudRefreshToken = cloudRefreshToken,
                fusionHost = fusionHost,
                secureStorage = secureStorage,
                debugLogging = debugLogging
            )
        }
    }
}

/**
 * Moves the SDK's notion of "now", read from the process environment rather than exposed as API:
 * only a debugger or an XCUITest harness (`launchEnvironment`) can set a variable on an iOS
 * process, so a shipped app can never be in this state. It exists so that a client's own tests can
 * reach a session in its last hours, or a certificate inside its renewal window, without waiting
 * days or moving the device clock.
 */
private fun configuredClockOffset(): Duration {
    val seconds = NSProcessInfo.processInfo.environment["DOORDECK_CLOCK_OFFSET_SECONDS"] as? String
    return seconds?.toDoubleOrNull()?.seconds ?: Duration.ZERO
}

internal fun SdkConfig.toBasicSdkConfig(): BasicSdkConfig = BasicSdkConfig(
    apiEnvironment = apiEnvironment,
    cloudAuthToken = cloudAuthToken,
    cloudRefreshToken = cloudRefreshToken,
    fusionHost = fusionHost?.toUrlString(),
    secureStorage = secureStorage,
    debugLogging = debugLogging,
    clockOffset = configuredClockOffset()
)