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

    val TEST_MAIN_USER_EMAIL = "training@doordeck.com"
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
    val TEST_MAIN_USER_PUBLIC_KEY = "mu05vzawHt27GfLUe9JmvYNYCaB+uarf/U+StgMxiC0="

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
    val TEST_SUPPLEMENTARY_USER_ID = "16ea0fe0-c505-11ed-a6aa-b9ad18da3daf"
    val TEST_SUPPLEMENTARY_USER_PUBLIC_KEY =
        "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAmoQmPVxa/O0+tVYNTA1DEr7Ah5H+zBwpat+jd3S7L4cqmMh+cHbB3w8L6x6VDvglBWXxzo8UFum6+KJaGQd4JVZAlou2YidhkK6/C5Zy4A4U1i3QGPdvFiJQas026PGla86J17ClKH3rNRE4Sa3DRONXu1piri6XtKeT1mfOqroTncl3hMQz7rwQA9BmkeAHwDpJ3cvaT8zGdZOTwNZNLcfyoP2gBV9fM6Qmp5QOM64SMHPQq7mob46My88ZaGvGY5uyEOgPm2ITiuaKvf9lIeWSJ8GD03BKadOKeEeCDDHZOiiougRc4rDBqVPZYZUapmJuEgRdkjnb7DIt7PCyB3Q5A5elqAci9saPrFkVjwwQ3sjHBYOkBEkULUzB5UJL6evYQcHdviT3XifJDx5iNjuhUa6Ifd8RI9T8thDDGBbGXXrfxlz/ywhSboHcorWcB4QpKaKWWSmjTsctwbYmPZ25x687/Q2R/EsomklFLn+mfMPVt1PHyQP5/7sOi9SCxl1m5f+FnLBILCCi/2ADAD7nSG3GmIHznDFZEXg+K2hDEuh7cvdr67Ae5c57Wp+82C8oAE0f1RlRC30c1pqTb55wFzffwhElYpcMNf6T0kaGuD8aQxfMo/DrqCaoJiMS/cAr4rmN7/81l86k7I981fbs6+CiHYfRp8KtjZeuXOECAwEAAQ=="
    val TEST_MAIN_TILE_ID = "00a17f9f-9319-4712-87d7-ff21a4369809"
    val TEST_MAIN_LOCK_ID = "ad8fb900-4def-11e8-9370-170748b9fca8"
    val TEST_MAIN_SITE_ID = "35ce3ec0-c8b8-11ed-ba1c-4fac5a13d98d"
}