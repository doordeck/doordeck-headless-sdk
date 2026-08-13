package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.TestConstants.FUSION_INTEGRATIONS
import com.doordeck.multiplatform.sdk.TestConstants.TEST_EXPIRED_CERTIFICATE
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_LOCK_NAME
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_EMAIL
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.TestConstants.TEST_SUPPLEMENTARY_SECOND_USER_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_SUPPLEMENTARY_SECOND_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.TestConstants.TEST_SUPPLEMENTARY_USER_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_SUPPLEMENTARY_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.TestConstants.TEST_VALID_CERTIFICATE
import com.doordeck.multiplatform.sdk.model.data.FusionOperations
import com.doordeck.multiplatform.sdk.model.responses.toLockControllerResponse
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray

internal actual val PLATFORM_MAIN_USER_ID: String = when (platformType) {
    PlatformType.JS_NODE -> "47614e60-969c-11f1-80c4-0f8537fbb53f"
    PlatformType.JS_BROWSER -> "58634a50-969d-11f1-80c4-0f8537fbb53f"
    else -> ""
}
internal actual val PLATFORM_MAIN_LOCK_ID: String = when (platformType) {
    PlatformType.JS_NODE -> "ad396380-969c-11f1-80c4-0f8537fbb53f" // JS_NODE Demo Lock
    PlatformType.JS_BROWSER -> "bc573620-969d-11f1-80c4-0f8537fbb53f" // JS_BROWSER Demo Lock
    else -> ""
}

internal object PlatformTestConstants {

    val PLATFORM_TEST_MAIN_USER_PRIVATE_KEY by lazy { TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray() }
    val PLATFORM_TEST_MAIN_USER_PUBLIC_KEY = TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray()
    val PLATFORM_TEST_MAIN_LOCK_NAME = TEST_MAIN_LOCK_NAME
    val PLATFORM_TEST_MAIN_USER_EMAIL = TEST_MAIN_USER_EMAIL
    val PLATFORM_TEST_MAIN_USER_ID = PLATFORM_MAIN_USER_ID
    val PLATFORM_TEST_SUPPLEMENTARY_USER_ID = TEST_SUPPLEMENTARY_USER_ID
    val PLATFORM_TEST_SUPPLEMENTARY_USER_PUBLIC_KEY = TEST_SUPPLEMENTARY_USER_PUBLIC_KEY.decodeBase64ToByteArray()
    val PLATFORM_TEST_MAIN_TILE_ID = when (platformType) {
        PlatformType.JS_NODE -> "4b4aa116-1792-4c68-b848-f5cd66bf58eb"
        PlatformType.JS_BROWSER -> "df199a97-41c0-46a2-a2ed-da47069cd994"
        else -> ""
    }
    val PLATFORM_TEST_MAIN_LOCK_ID = PLATFORM_MAIN_LOCK_ID
    val PLATFORM_TEST_MAIN_SITE_ID = when (platformType) {
        PlatformType.JS_NODE -> "7b7bd8c0-969a-11f1-a47a-dba4cb2c41d0" // KMP JS_NODE SITE
        PlatformType.JS_BROWSER -> "ad067e90-969a-11f1-a47a-dba4cb2c41d0" // KMP JS_BROWSER SITE
        else -> ""
    }
    val PLATFORM_TEST_SUPPLEMENTARY_TILE_ID = when (platformType) {
        PlatformType.JS_NODE -> "ba014939-cfaf-45e1-bacc-03d89a2586ed"
        PlatformType.JS_BROWSER -> "78123a60-58e8-4276-8ad4-45b7f91e27bd"
        else -> ""
    }
    val PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID = TEST_SUPPLEMENTARY_SECOND_USER_ID
    val PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_PUBLIC_KEY = TEST_SUPPLEMENTARY_SECOND_USER_PUBLIC_KEY.decodeBase64ToByteArray()
    val PLATFORM_TEST_VALID_CERTIFICATE = TEST_VALID_CERTIFICATE
    val PLATFORM_TEST_EXPIRED_CERTIFICATE = TEST_EXPIRED_CERTIFICATE
    val PLATFORM_FUSION_INTEGRATIONS = FUSION_INTEGRATIONS.map {
        TestController(
            uri = it.uri,
            type = it.type,
            controller = it.controller.toLockControllerResponse()
        )
    }

    internal data class TestController(
        val uri: String,
        val type: String,
        val controller: FusionOperations.LockController
    )
}