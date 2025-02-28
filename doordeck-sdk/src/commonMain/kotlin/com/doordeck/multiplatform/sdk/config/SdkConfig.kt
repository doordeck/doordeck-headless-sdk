package com.doordeck.multiplatform.sdk.config

import com.doordeck.multiplatform.sdk.ApplicationContext
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.storage.SecureStorage
import com.doordeck.multiplatform.sdk.storage.createSecureStorage
import kotlin.js.JsExport

@JsExport
class SdkConfig private constructor(
    val apiEnvironment: ApiEnvironment,
    val cloudAuthToken: String?,
    val cloudRefreshToken: String?,
    val applicationContext: ApplicationContext?,
    val secureStorage: SecureStorage
) {
    class Builder {
        private var apiEnvironment: ApiEnvironment = ApiEnvironment.PROD
        private var cloudAuthToken: String? = null
        private var cloudRefreshToken: String? = null
        private var applicationContext: ApplicationContext? = null
        private var secureStorage: SecureStorage? = null

        fun setApiEnvironment(apiEnvironment: ApiEnvironment): Builder = apply { this.apiEnvironment = apiEnvironment }

        fun setCloudAuthToken(cloudAuthToken: String?): Builder = apply { this.cloudAuthToken = cloudAuthToken }

        fun setCloudRefreshToken(cloudRefreshToken: String?): Builder = apply { this.cloudRefreshToken = cloudRefreshToken }

        @JsExport.Ignore
        fun setApplicationContext(context: ApplicationContext): Builder = apply { this.applicationContext = context }

        fun setSecureStorageOverride(secureStorage: SecureStorage): Builder = apply { this.secureStorage = secureStorage }

        fun build(): SdkConfig {
            val secureStorage = secureStorage ?: createSecureStorage(applicationContext)
            return SdkConfig(
                apiEnvironment = apiEnvironment,
                cloudAuthToken = cloudAuthToken,
                cloudRefreshToken = cloudRefreshToken,
                applicationContext = applicationContext,
                secureStorage = secureStorage
            )
        }
    }
}