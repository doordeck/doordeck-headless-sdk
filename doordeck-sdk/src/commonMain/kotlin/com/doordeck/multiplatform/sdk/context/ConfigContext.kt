package com.doordeck.multiplatform.sdk.context

import com.doordeck.multiplatform.sdk.Constants.DEFAULT_FUSION_HOST
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import kotlin.concurrent.Volatile
import kotlin.jvm.JvmSynthetic

/**
 * This object holds SDK configuration that is not related to the user session.
 * The configuration in this class is stored in memory and is not persisted anywhere.
 */
internal object ConfigContext {

    /**
     * In-memory variable for the API environment. This variable is not changed upon context cleanup;
     * it retains whatever value is set during the initialization process, or defaults to `ApiEnvironment.PROD` if not set.
     */
    @Volatile
    private var API_ENVIRONMENT = ApiEnvironment.PROD

    /**
     * In-memory variable for the Fusion host. This variable is not changed upon context cleanup;
     * it retains whatever value is set during the initialization process, or defaults to `DEFAULT_FUSION_HOST` if not set.
     */
    @Volatile
    private var FUSION_HOST = DEFAULT_FUSION_HOST

    /**
     * Sets the API environment on which the SDK will operate.
     *
     * It can be changed anytime, although it is recommended to set it up through the `SdkConfig` builder.
     */
    @JvmSynthetic
    internal fun setApiEnvironment(apiEnvironment: ApiEnvironment) {
        API_ENVIRONMENT = apiEnvironment
    }

    /**
     * Retrieves the current API environment.
     */
    @JvmSynthetic
    internal fun getApiEnvironment(): ApiEnvironment {
        return API_ENVIRONMENT
    }

    /**
     * Sets the fusion host.
     *
     * It can be changed anytime, although it is recommended to set it up through the SdkConfig builder.
     */
    @JvmSynthetic
    internal fun setFusionHost(host: String) {
        FUSION_HOST = host
    }

    /**
     * Retrieves the fusion host.
     */
    @JvmSynthetic
    internal fun getFusionHost(): String {
        return FUSION_HOST
    }

    /**
     * Restore the default values from the SDK configuration.
     */
    @JvmSynthetic
    internal fun reset() {
        API_ENVIRONMENT = ApiEnvironment.PROD
        FUSION_HOST = DEFAULT_FUSION_HOST
    }
}