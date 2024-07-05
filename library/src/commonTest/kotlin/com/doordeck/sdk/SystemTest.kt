package com.doordeck.sdk

import com.doordeck.sdk.api.model.ApiEnvironment
import com.doordeck.sdk.internal.api.AccountResourceImpl
import com.doordeck.sdk.internal.api.LockOperationsResourceImpl
import com.doordeck.sdk.internal.api.PlatformResourceImpl
import com.doordeck.sdk.internal.api.SitesResourceImpl
import com.doordeck.sdk.internal.api.TilesResourceImpl
import com.doordeck.sdk.util.Crypto.certificateChainToString
import com.doordeck.sdk.util.Crypto.decodeBase64ToByteArray
import com.doordeck.sdk.util.Jwt

open class SystemTest {

    val TEST_AUTH_TOKEN = getEnvironmentVariable("TEST_AUTH_TOKEN")
        ?: ""
    val TEST_MAIN_USER_ID by lazy { Jwt.getSub(TEST_AUTH_TOKEN)!! }
    val TEST_MAIN_USER_PRIVATE_KEY = getEnvironmentVariable("TEST_MAIN_USER_PRIVATE_KEY")
        ?: ""

    // Http client
    val HTTP_CLIENT  by lazy { createHttpClient(ApiEnvironment.DEV, TEST_AUTH_TOKEN, null) }

    // Resources
    val ACCOUNT_RESOURCE by lazy { AccountResourceImpl(HTTP_CLIENT) }
    val LOCK_OPERATIONS_RESOURCE by lazy { LockOperationsResourceImpl(HTTP_CLIENT) }
    val PLATFORM_RESOURCE by lazy { PlatformResourceImpl(HTTP_CLIENT) }
    val SITES_RESOURCE by lazy { SitesResourceImpl(HTTP_CLIENT) }
    val TILES_RESOURCE by lazy { TilesResourceImpl(HTTP_CLIENT) }

    private val MAIN_USER_DETAILS by lazy { ACCOUNT_RESOURCE.getUserDetails() }
    val TEST_MAIN_USER_EMAIL by lazy { MAIN_USER_DETAILS.email }
    val TEST_MAIN_USER_CERTIFICATE_CHAIN by lazy {
        ACCOUNT_RESOURCE.registerEphemeralKey(MAIN_USER_DETAILS.publicKey.decodeBase64ToByteArray())
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