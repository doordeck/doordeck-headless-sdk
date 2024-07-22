package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.api.model.ApiEnvironment
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountResourceImpl
import com.doordeck.multiplatform.sdk.internal.api.AccountlessResourceImpl
import com.doordeck.multiplatform.sdk.internal.api.LockOperationsResourceImpl
import com.doordeck.multiplatform.sdk.internal.api.PlatformResourceImpl
import com.doordeck.multiplatform.sdk.internal.api.SitesResourceImpl
import com.doordeck.multiplatform.sdk.internal.api.TilesResourceImpl
import com.doordeck.multiplatform.sdk.util.Crypto.certificateChainToString
import com.doordeck.multiplatform.sdk.util.Crypto.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Jwt.getUserIdFromToken

open class SystemTest {

    val TEST_ENVIRONMENT = ApiEnvironment.DEV

    val TEST_MAIN_USER_EMAIL = getEnvironmentVariable("TEST_MAIN_USER_EMAIL")
        ?: ""
    val TEST_MAIN_USER_PASSWORD = getEnvironmentVariable("TEST_MAIN_USER_PASSWORD")
        ?: ""

    val ACCOUNTLESS_RESOURCE by lazy {
        AccountlessResourceImpl(createCloudHttpClient(TEST_ENVIRONMENT, ContextManagerImpl()))
    }

    val TEST_AUTH_TOKEN by lazy { ACCOUNTLESS_RESOURCE.login(TEST_MAIN_USER_EMAIL, TEST_MAIN_USER_PASSWORD).authToken }
    val TEST_MAIN_USER_ID by lazy { TEST_AUTH_TOKEN.getUserIdFromToken()!! }
    val TEST_MAIN_USER_PRIVATE_KEY = getEnvironmentVariable("TEST_MAIN_USER_PRIVATE_KEY")
        ?: ""
    val TEST_MAIN_USER_PUBLIC_KEY = getEnvironmentVariable("TEST_MAIN_USER_PUBLIC_KEY")
        ?: ""

    // Token manager
    val contextManager = ContextManagerImpl(TEST_AUTH_TOKEN)

    // Http client
    val HTTP_CLIENT  by lazy { createCloudHttpClient(TEST_ENVIRONMENT, contextManager) }

    // Resources
    val ACCOUNT_RESOURCE by lazy { AccountResourceImpl(HTTP_CLIENT, contextManager) }
    val LOCK_OPERATIONS_RESOURCE by lazy { LockOperationsResourceImpl(HTTP_CLIENT, contextManager) }
    val PLATFORM_RESOURCE by lazy { PlatformResourceImpl(HTTP_CLIENT) }
    val SITES_RESOURCE by lazy { SitesResourceImpl(HTTP_CLIENT) }
    val TILES_RESOURCE by lazy { TilesResourceImpl(HTTP_CLIENT) }

    val TEST_MAIN_USER_CERTIFICATE_CHAIN by lazy {
        ACCOUNT_RESOURCE.registerEphemeralKey(TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray())
            .certificateChain
            .certificateChainToString()
    }

    val TEST_SUPPLEMENTARY_USER_ID = getEnvironmentVariable("TEST_SUPPLEMENTARY_USER_ID")
        ?: ""
    val TEST_SUPPLEMENTARY_USER_PUBLIC_KEY = getEnvironmentVariable("TEST_SUPPLEMENTARY_USER_PUBLIC_KEY")
        ?: ""

    val TEST_MAIN_TILE_ID = getEnvironmentVariable("TEST_MAIN_TILE_ID")
        ?: ""
    val TEST_MAIN_LOCK_ID = getEnvironmentVariable("TEST_MAIN_LOCK_ID")
        ?: ""
}