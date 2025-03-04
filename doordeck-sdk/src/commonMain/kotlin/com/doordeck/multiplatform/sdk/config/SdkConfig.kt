package com.doordeck.multiplatform.sdk.config

import com.doordeck.multiplatform.sdk.ApplicationContext
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.storage.SecureStorage
import com.doordeck.multiplatform.sdk.storage.createSecureStorage
import kotlin.js.JsExport

/**
 * Configuration settings for the SDK.
 *
 * This class holds various configuration options for initializing and operating the SDK.
 */
@JsExport
class SdkConfig private constructor(
    val apiEnvironment: ApiEnvironment?,
    val cloudAuthToken: String?,
    val cloudRefreshToken: String?,
    val secureStorage: SecureStorage
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
        private var applicationContext: ApplicationContext? = null
        private var secureStorage: SecureStorage? = null

        /**
         * Sets the API environment for the SDK.
         */
        fun setApiEnvironment(apiEnvironment: ApiEnvironment): Builder = apply { this.apiEnvironment = apiEnvironment }

        /**
         * Sets the cloud authentication token.
         */
        fun setCloudAuthToken(cloudAuthToken: String): Builder = apply { this.cloudAuthToken = cloudAuthToken }

        /**
         * Sets the cloud refresh token.
         */
        fun setCloudRefreshToken(cloudRefreshToken: String): Builder = apply { this.cloudRefreshToken = cloudRefreshToken }

        /**
         * Sets the Android application context. This should only be provided on Android.
         */
        @JsExport.Ignore
        fun setApplicationContext(context: ApplicationContext): Builder = apply { this.applicationContext = context }

        /**
         * Overrides the default secure storage with a custom implementation.
         */
        fun setSecureStorageOverride(secureStorage: SecureStorage): Builder = apply { this.secureStorage = secureStorage }

        /**
         * Builds a new [SdkConfig] instance.
         *
         * If no secure storage override is provided, a default secure storage is created.
         */
        fun build(): SdkConfig {
            val secureStorage = secureStorage ?: createSecureStorage(applicationContext)
            return SdkConfig(
                apiEnvironment = apiEnvironment,
                cloudAuthToken = cloudAuthToken,
                cloudRefreshToken = cloudRefreshToken,
                secureStorage = secureStorage
            )
        }
    }
}