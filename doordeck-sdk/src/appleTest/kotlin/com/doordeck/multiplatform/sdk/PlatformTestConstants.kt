package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.TestConstants.FUSION_INTEGRATIONS
import com.doordeck.multiplatform.sdk.TestConstants.TEST_EXPIRED_CERTIFICATE
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_LOCK_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_SITE_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_TILE_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PRIVATE_KEY
import com.doordeck.multiplatform.sdk.TestConstants.TEST_MAIN_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.TestConstants.TEST_SUPPLEMENTARY_SECOND_USER_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_SUPPLEMENTARY_SECOND_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.TestConstants.TEST_SUPPLEMENTARY_TILE_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_SUPPLEMENTARY_USER_ID
import com.doordeck.multiplatform.sdk.TestConstants.TEST_SUPPLEMENTARY_USER_PUBLIC_KEY
import com.doordeck.multiplatform.sdk.TestConstants.TEST_VALID_CERTIFICATE
import com.doordeck.multiplatform.sdk.model.data.FusionOperations
import com.doordeck.multiplatform.sdk.model.responses.toLockControllerResponse
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.toNsUrlComponents
import com.doordeck.multiplatform.sdk.util.toNsUuid
import kotlinx.serialization.Serializable

internal object PlatformTestConstants {

    val PLATFORM_TEST_MAIN_USER_PRIVATE_KEY = TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray()
    val PLATFORM_TEST_MAIN_USER_PUBLIC_KEY = TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray()
    val PLATFORM_TEST_MAIN_USER_ID = TEST_MAIN_USER_ID.toNsUuid()
    val PLATFORM_TEST_SUPPLEMENTARY_USER_ID = TEST_SUPPLEMENTARY_USER_ID.toNsUuid()
    val PLATFORM_TEST_SUPPLEMENTARY_USER_PUBLIC_KEY = TEST_SUPPLEMENTARY_USER_PUBLIC_KEY.decodeBase64ToByteArray()
    val PLATFORM_TEST_MAIN_TILE_ID = TEST_MAIN_TILE_ID.toNsUuid()
    val PLATFORM_TEST_MAIN_LOCK_ID = TEST_MAIN_LOCK_ID.toNsUuid()
    val PLATFORM_TEST_MAIN_SITE_ID = TEST_MAIN_SITE_ID.toNsUuid()
    val PLATFORM_TEST_SUPPLEMENTARY_TILE_ID = TEST_SUPPLEMENTARY_TILE_ID.toNsUuid()
    val PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID = TEST_SUPPLEMENTARY_SECOND_USER_ID.toNsUuid()
    val PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_PUBLIC_KEY = TEST_SUPPLEMENTARY_SECOND_USER_PUBLIC_KEY.decodeBase64ToByteArray()
    val PLATFORM_TEST_VALID_CERTIFICATE = TEST_VALID_CERTIFICATE
    val PLATFORM_TEST_EXPIRED_CERTIFICATE = TEST_EXPIRED_CERTIFICATE
    val PLATFORM_FUSION_INTEGRATIONS = FUSION_INTEGRATIONS.map {
        it.key.toNsUrlComponents() to TestController(
            type = it.value.type,
            controller = it.value.controller.toLockControllerResponse()
        )
    }.toMap()

    @Serializable
    data class TestController(
        val type: String,
        val controller: FusionOperations.LockController
    )
}