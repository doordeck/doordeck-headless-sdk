package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.api.model.ApiEnvironment
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountResourceImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountlessResourceImpl
import com.doordeck.multiplatform.sdk.internal.api.FusionResourceImpl
import com.doordeck.multiplatform.sdk.internal.api.LockOperationsResourceImpl
import com.doordeck.multiplatform.sdk.internal.api.PlatformResourceImpl
import com.doordeck.multiplatform.sdk.internal.api.SitesResourceImpl
import com.doordeck.multiplatform.sdk.internal.api.TilesResourceImpl
import com.doordeck.multiplatform.sdk.util.Crypto.certificateChainToString
import com.doordeck.multiplatform.sdk.util.Crypto.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Jwt.getUserIdFromToken

open class SystemTest {

    val TEST_ENVIRONMENT = ApiEnvironment.DEV

    val TEST_MAIN_USER_EMAIL by lazy {
        getEnvironmentVariable("TEST_MAIN_USER_EMAIL") ?: ""
    }
    val TEST_MAIN_USER_PASSWORD by lazy {
        getEnvironmentVariable("TEST_MAIN_USER_PASSWORD") ?: ""
    }
    val ACCOUNTLESS_RESOURCE by lazy {
        AccountlessResourceImpl(createCloudHttpClient(TEST_ENVIRONMENT, ContextManagerImpl()))
    }
    val TEST_AUTH_TOKEN by lazy {
        ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).authToken
    }
    val TEST_MAIN_USER_ID by lazy {
        TEST_AUTH_TOKEN.getUserIdFromToken()!!
    }
    val TEST_MAIN_USER_PRIVATE_KEY by lazy {
        getEnvironmentVariable("TEST_MAIN_USER_PRIVATE_KEY") ?: ""
    }
    val TEST_MAIN_USER_PUBLIC_KEY by lazy {
        getEnvironmentVariable("TEST_MAIN_USER_PUBLIC_KEY") ?: ""
    }

    // Context manager
    val CONTEXT_MANAGER by lazy {
        ContextManagerImpl(TEST_AUTH_TOKEN)
    }

    // Http client
    private val CLOUD_HTTP_CLIENT  by lazy {
        createCloudHttpClient(TEST_ENVIRONMENT, CONTEXT_MANAGER)
    }

    // Resources
    val ACCOUNT_RESOURCE by lazy {
        AccountResourceImpl(CLOUD_HTTP_CLIENT, CONTEXT_MANAGER)
    }
    val LOCK_OPERATIONS_RESOURCE by lazy {
        LockOperationsResourceImpl(CLOUD_HTTP_CLIENT, CONTEXT_MANAGER)
    }
    val PLATFORM_RESOURCE by lazy {
        PlatformResourceImpl(CLOUD_HTTP_CLIENT)
    }
    val SITES_RESOURCE by lazy {
        SitesResourceImpl(CLOUD_HTTP_CLIENT)
    }
    val TILES_RESOURCE by lazy {
        TilesResourceImpl(CLOUD_HTTP_CLIENT)
    }

    val TEST_MAIN_USER_CERTIFICATE_CHAIN by lazy {
        ACCOUNT_RESOURCE.registerEphemeralKey(TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray())
            .certificateChain
            .certificateChainToString()
    }
    val TEST_SUPPLEMENTARY_USER_ID by lazy {
        getEnvironmentVariable("TEST_SUPPLEMENTARY_USER_ID") ?: ""
    }
    val TEST_SUPPLEMENTARY_USER_PUBLIC_KEY by lazy {
        getEnvironmentVariable("TEST_SUPPLEMENTARY_USER_PUBLIC_KEY") ?: ""
    }
    val TEST_MAIN_TILE_ID = "00a17f9f-9319-4712-87d7-ff21a4369809"
    val TEST_MAIN_LOCK_ID = "ad8fb900-4def-11e8-9370-170748b9fca8"
    val TEST_MAIN_SITE_ID = "35ce3ec0-c8b8-11ed-ba1c-4fac5a13d98d"
}