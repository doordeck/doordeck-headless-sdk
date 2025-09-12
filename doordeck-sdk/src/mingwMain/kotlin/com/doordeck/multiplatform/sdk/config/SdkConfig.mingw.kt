package com.doordeck.multiplatform.sdk.config

import com.doordeck.multiplatform.sdk.ApplicationContext
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.storage.SecureStorage
import com.doordeck.multiplatform.sdk.storage.createSecureStorage

/**
 * Configuration settings for the SDK.
 *
 * This class holds various configuration options for initializing and operating the SDK.
 */
data class SdkConfig(
    val apiEnvironment: String? = null,
    val cloudAuthToken: String? = null,
    val cloudRefreshToken: String? = null,
    val fusionHost: String?,
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
        private var apiEnvironment: String? = null
        private var cloudAuthToken: String? = null
        private var cloudRefreshToken: String? = null
        private var fusionHost: String? = null
        private var secureStorage: SecureStorage? = null
        private var debugLogging: Boolean? = null

        /**
         * Sets the API environment for the SDK.
         */
        fun setApiEnvironment(apiEnvironment: String?): Builder = apply { this.apiEnvironment = apiEnvironment }

        /**
         * Sets the cloud authentication token.
         */
        fun setCloudAuthToken(cloudAuthToken: String?): Builder = apply { this.cloudAuthToken = cloudAuthToken }

        /**
         * Sets the cloud refresh token.
         */
        fun setCloudRefreshToken(cloudRefreshToken: String?): Builder = apply { this.cloudRefreshToken = cloudRefreshToken }

        /**
         * Sets the fusion host i.e: http://localhost:500
         */
        fun setFusionHost(fusionHost: String?): Builder = apply { this.fusionHost = fusionHost }

        /**
         * Overrides the default secure storage with a custom implementation.
         */
        fun setSecureStorageOverride(secureStorage: SecureStorage?): Builder = apply { this.secureStorage = secureStorage }

        /**
         * Enables debug logging. Beware: it may output sensitive information.
         */
        fun setDebugLogging(enabled: Boolean?): Builder = apply { this.debugLogging = enabled }

        /**
         * Builds a new [SdkConfig] instance.
         *
         * If no secure storage override is provided, a default secure storage is created.
         */
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

internal fun SdkConfig.toBasicSdkConfig(): BasicSdkConfig = BasicSdkConfig(
    apiEnvironment = apiEnvironment?.let { ApiEnvironment.valueOf(it) },
    cloudAuthToken = cloudAuthToken,
    cloudRefreshToken = cloudRefreshToken,
    fusionHost = fusionHost.toString(),
    secureStorage = secureStorage,
    debugLogging = debugLogging
)