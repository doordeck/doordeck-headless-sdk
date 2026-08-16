package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.TestConstants.FUSION_INTEGRATIONS
import com.doordeck.multiplatform.sdk.TestConstants.TEST_EXPIRED_CERTIFICATE
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_APPLICATION_NAME
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_FUSION_DOOR_NAME
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
import com.doordeck.multiplatform.sdk.util.toNsUrlComponents
import com.doordeck.multiplatform.sdk.util.toNsUuid
import platform.Foundation.NSURLComponents

internal actual val PLATFORM_MAIN_USER_ID: String = when (platformType) {
    PlatformType.APPLE_MAC -> "f22f5cc0-96a0-11f1-80c4-0f8537fbb53f"
    PlatformType.APPLE_WATCH -> "18b0ff70-96a1-11f1-80c4-0f8537fbb53f"
    PlatformType.APPLE_IOS -> "40b179f0-96a1-11f1-80c4-0f8537fbb53f"
    else -> ""
}
internal actual val PLATFORM_MAIN_LOCK_ID: String = when (platformType) {
    PlatformType.APPLE_MAC -> "420d6b00-96a2-11f1-80c4-0f8537fbb53f" // APPLE_MAC Demo Lock
    PlatformType.APPLE_WATCH -> "cabb58e0-96a2-11f1-80c4-0f8537fbb53f" // APPLE_WATCH Demo Lock
    PlatformType.APPLE_IOS -> "7d4dea90-96a3-11f1-80c4-0f8537fbb53f" // APPLE_IOS Demo Lock
    else -> ""
}

internal object PlatformTestConstants {

    val PLATFORM_TEST_MAIN_USER_PRIVATE_KEY by lazy { TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray() }
    val PLATFORM_TEST_MAIN_USER_PUBLIC_KEY = TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray()
    val PLATFORM_TEST_MAIN_APPLICATION_NAME = TEST_MAIN_APPLICATION_NAME
    val PLATFORM_TEST_MAIN_FUSION_DOOR_NAME = TEST_MAIN_FUSION_DOOR_NAME
    val PLATFORM_TEST_MAIN_LOCK_NAME = TEST_MAIN_LOCK_NAME
    val PLATFORM_TEST_MAIN_USER_EMAIL = TEST_MAIN_USER_EMAIL
    val PLATFORM_TEST_MAIN_USER_ID = PLATFORM_MAIN_USER_ID.toNsUuid()
    val PLATFORM_TEST_SUPPLEMENTARY_USER_ID = TEST_SUPPLEMENTARY_USER_ID.toNsUuid()
    val PLATFORM_TEST_SUPPLEMENTARY_USER_PUBLIC_KEY = TEST_SUPPLEMENTARY_USER_PUBLIC_KEY.decodeBase64ToByteArray()
    val PLATFORM_TEST_MAIN_TILE_ID = when(platformType) {
        PlatformType.APPLE_MAC -> "eb86c471-1f34-441b-99de-1afddf6f32c5"
        PlatformType.APPLE_WATCH -> "26d8c620-9fa1-4201-93ce-239057d430e7"
        PlatformType.APPLE_IOS -> "a830a74b-0154-4da3-a23a-92b20b817bcd"
        else -> ""
    }.toNsUuid()
    val PLATFORM_TEST_MAIN_LOCK_ID = PLATFORM_MAIN_LOCK_ID.toNsUuid()
    val PLATFORM_TEST_MAIN_SITE_ID = when(platformType) {
        PlatformType.APPLE_MAC -> "26068010-96a0-11f1-a47a-dba4cb2c41d0" // KMP APPLE_MAC SITE
        PlatformType.APPLE_WATCH -> "7cfa2ca0-96a0-11f1-99f0-59bbf00598b4" // KMP APPLE_WATCH SITE
        PlatformType.APPLE_IOS -> "93e22080-96a0-11f1-a47a-dba4cb2c41d0" // KMP APPLE_IOS SITE
        else -> ""
    }.toNsUuid()
    val PLATFORM_TEST_SUPPLEMENTARY_TILE_ID = when(platformType) {
        PlatformType.APPLE_MAC -> "43289f22-feb0-4c5a-87e2-216eab36ad7f"
        PlatformType.APPLE_WATCH -> "4704ea50-3417-41ed-a88d-dd2f2912f37f"
        PlatformType.APPLE_IOS -> "a713f790-a738-48a2-b5e6-47347ca29e30"
        else -> ""
    }.toNsUuid()
    val PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID = TEST_SUPPLEMENTARY_SECOND_USER_ID.toNsUuid()
    val PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_PUBLIC_KEY = TEST_SUPPLEMENTARY_SECOND_USER_PUBLIC_KEY.decodeBase64ToByteArray()
    val PLATFORM_TEST_VALID_CERTIFICATE = TEST_VALID_CERTIFICATE
    val PLATFORM_TEST_EXPIRED_CERTIFICATE = TEST_EXPIRED_CERTIFICATE
    val PLATFORM_FUSION_INTEGRATIONS = FUSION_INTEGRATIONS.map {
        TestController(
            uri = it.uri.toNsUrlComponents(),
            type = it.type,
            controller = it.controller.toLockControllerResponse()
        )
    }

    data class TestController(
        val uri: NSURLComponents,
        val type: String,
        val controller: FusionOperations.LockController
    )
}