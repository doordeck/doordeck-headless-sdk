package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.api.model.ApiEnvironment
import com.doordeck.multiplatform.sdk.api.model.Fusion

internal object TestConstants {
    val TEST_ENVIRONMENT = ApiEnvironment.DEV
    val TEST_MAIN_USER_PASSWORD = getEnvironmentVariable("TEST_MAIN_USER_PASSWORD")
        ?: ""
    val TEST_MAIN_USER_PRIVATE_KEY = getEnvironmentVariable("TEST_MAIN_USER_PRIVATE_KEY")
        ?: ""
    const val TEST_MAIN_USER_EMAIL = "training@doordeck.com"
    const val TEST_MAIN_USER_ID = "05cf8ff0-1285-11e9-9f69-170748b9fca8"
    const val TEST_MAIN_USER_PUBLIC_KEY = "mu05vzawHt27GfLUe9JmvYNYCaB+uarf/U+StgMxiC0="
    const val TEST_SUPPLEMENTARY_USER_EMAIL = "training+tests@doordeck.com"
    const val TEST_SUPPLEMENTARY_USER_ID = "22c75ac0-c7ab-11ef-b34b-75fe33822921"
    const val TEST_SUPPLEMENTARY_USER_PUBLIC_KEY =
        "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlwQVOr+frEg3hRpP6OcHrwuwChwwYxca4sXmUxh3byqL0ZKgUhoNKtTl+06HiFQvqWSgeSy3fYvN/XGtG4IMG0/iY11IyqDC0zgWnWxq/cVbTizne67MSTHrtfqVnx92HrD3zIFepTxW+aIhruHyNIv+OFbsUGxd5pUHwdltMHQ1TiIZF49I/XtNEt9hb6jUxwqlxUP4FhlwsRtL5g7fIZt3BFN1m3W5XOo8ITBwPVI2nH8h+dLaIe0b9u1QCdTzL10qOxWYzoGV0drkV0mPt9p0z4sCWJwvjAwp+g9fkuNcw7ZCA7Nidzkyh+4QRuUWtwhERs7OxyihSqf1nODG+QIDAQAB"
    const val TEST_MAIN_TILE_ID = "00a17f9f-9319-4712-87d7-ff21a4369809"
    const val TEST_MAIN_LOCK_ID = "ad8fb900-4def-11e8-9370-170748b9fca8"
    const val TEST_MAIN_SITE_ID = "7659e430-4a28-11e8-bf0b-bffab372a82e"

    val FUSION_INTEGRATIONS: Map<String, TestController> = mapOf(
        "192.168.202.54:27700" to TestController("demo", Fusion.DemoController(1)),
        "192.168.202.26:27700" to TestController("paxton10", Fusion.Paxton10Controller("", "", "", 1)),
        "192.168.202.58:27700" to TestController("amag", Fusion.AmagController("", "", 1, "")),
        "192.168.202.19:27700" to TestController("gallagher", Fusion.GallagherController("", "", "")),
        "192.168.202.56:27700" to TestController("genetec", Fusion.GenetecController("", "", "", "")),
        "192.168.202.39:27700" to TestController("lenel", Fusion.LenelController("", "", "", "", "", "")),
        "192.168.202.31:27700" to TestController("net2", Fusion.PaxtonNet2Controller("", "", 0)),
        "192.168.202.18:27700" to TestController("integra-v2", Fusion.IntegraV2Controller("", "", 1, 1, 1)),
        //"192.168.202.16:27700" to TestController("", ), WORK IN PROGRESS
        "192.168.202.52:27700" to TestController("tdsi-gardis", Fusion.TdsiGardisController("", "", "", 1)),
        "192.168.202.61:27700" to TestController("tdsi-exgarde", Fusion.TdsiExgardeController("", "", "", 1)),
        "192.168.202.62:27700" to TestController("zkteco-zkbio-cvsecurity", Fusion.ZktecoController("", "", "", Fusion.ZktecoEntityType.DOOR)),
        "192.168.202.63:27700" to TestController("alpeta", Fusion.AlpetaController("", "", 1, ""))
    )

    data class TestController(
        val type: String,
        val controller: Fusion.LockController,
        val enabled: Boolean = false
    )

    // Default values used on the Mock http client
    const val DEFAULT_TILE_ID = "TILE_ID"
    const val DEFAULT_SITE_ID = "SITE_ID"
    const val DEFAULT_APPLICATION_ID = "APPLICATION_ID"
    const val DEFAULT_DEVICE_ID = "DEVICE_ID"
    const val DEFAULT_LOCK_ID = "LOCK_ID"
    const val DEFAULT_USER_ID = "USER_ID"
    const val DEFAULT_USER_EMAIL = "USER_EMAIL"
    const val DEFAULT_UPLOAD_URL_PATH = "/upload"
    const val DEFAULT_UPLOAD_URL = "https://cute-upload-url.com$DEFAULT_UPLOAD_URL_PATH"
}