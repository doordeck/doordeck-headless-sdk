package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.api.model.ApiEnvironment

object TestConstants {
    val TEST_MAIN_USER_PASSWORD = getEnvironmentVariable("TEST_MAIN_USER_PASSWORD")
        ?: ""
    val TEST_MAIN_USER_PRIVATE_KEY = getEnvironmentVariable("TEST_MAIN_USER_PRIVATE_KEY")
        ?: ""


    val TEST_ENVIRONMENT = ApiEnvironment.DEV
    val TEST_MAIN_USER_EMAIL = "training@doordeck.com"
    val TEST_MAIN_USER_ID = "05cf8ff0-1285-11e9-9f69-170748b9fca8"
    val TEST_MAIN_USER_PUBLIC_KEY = "mu05vzawHt27GfLUe9JmvYNYCaB+uarf/U+StgMxiC0="
    val TEST_SUPPLEMENTARY_USER_ID = "16ea0fe0-c505-11ed-a6aa-b9ad18da3daf"
    val TEST_SUPPLEMENTARY_USER_PUBLIC_KEY =
        "MIICIjANBgkqhkiG9w0BAQEFAAOCAg8AMIICCgKCAgEAmoQmPVxa/O0+tVYNTA1DEr7Ah5H+zBwpat+jd3S7L4cqmMh+cHbB3w8L6x6VDvglBWXxzo8UFum6+KJaGQd4JVZAlou2YidhkK6/C5Zy4A4U1i3QGPdvFiJQas026PGla86J17ClKH3rNRE4Sa3DRONXu1piri6XtKeT1mfOqroTncl3hMQz7rwQA9BmkeAHwDpJ3cvaT8zGdZOTwNZNLcfyoP2gBV9fM6Qmp5QOM64SMHPQq7mob46My88ZaGvGY5uyEOgPm2ITiuaKvf9lIeWSJ8GD03BKadOKeEeCDDHZOiiougRc4rDBqVPZYZUapmJuEgRdkjnb7DIt7PCyB3Q5A5elqAci9saPrFkVjwwQ3sjHBYOkBEkULUzB5UJL6evYQcHdviT3XifJDx5iNjuhUa6Ifd8RI9T8thDDGBbGXXrfxlz/ywhSboHcorWcB4QpKaKWWSmjTsctwbYmPZ25x687/Q2R/EsomklFLn+mfMPVt1PHyQP5/7sOi9SCxl1m5f+FnLBILCCi/2ADAD7nSG3GmIHznDFZEXg+K2hDEuh7cvdr67Ae5c57Wp+82C8oAE0f1RlRC30c1pqTb55wFzffwhElYpcMNf6T0kaGuD8aQxfMo/DrqCaoJiMS/cAr4rmN7/81l86k7I981fbs6+CiHYfRp8KtjZeuXOECAwEAAQ=="
    val TEST_MAIN_TILE_ID = "00a17f9f-9319-4712-87d7-ff21a4369809"
    val TEST_MAIN_LOCK_ID = "ad8fb900-4def-11e8-9370-170748b9fca8"
    val TEST_MAIN_SITE_ID = "7659e430-4a28-11e8-bf0b-bffab372a82e"//""35ce3ec0-c8b8-11ed-ba1c-4fac5a13d98d"
}