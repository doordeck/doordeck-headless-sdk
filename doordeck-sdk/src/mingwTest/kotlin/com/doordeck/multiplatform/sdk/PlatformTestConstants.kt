package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.TestConstants.FUSION_INTEGRATIONS
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
import com.doordeck.multiplatform.sdk.model.data.BasicLockController
import kotlinx.serialization.Serializable
import kotlin.getValue

internal object PlatformTestConstants {

    val PLATFORM_TEST_MAIN_USER_PRIVATE_KEY by lazy { TEST_MAIN_USER_PRIVATE_KEY }
    val PLATFORM_TEST_MAIN_USER_PUBLIC_KEY by lazy { TEST_MAIN_USER_PUBLIC_KEY }
    val PLATFORM_TEST_MAIN_USER_ID by lazy { TEST_MAIN_USER_ID }
    val PLATFORM_TEST_SUPPLEMENTARY_USER_ID by lazy { TEST_SUPPLEMENTARY_USER_ID }
    val PLATFORM_TEST_SUPPLEMENTARY_USER_PUBLIC_KEY by lazy { TEST_SUPPLEMENTARY_USER_PUBLIC_KEY }
    val PLATFORM_TEST_MAIN_TILE_ID by lazy { TEST_MAIN_TILE_ID }
    val PLATFORM_TEST_MAIN_LOCK_ID by lazy { TEST_MAIN_LOCK_ID }
    val PLATFORM_TEST_MAIN_SITE_ID by lazy { TEST_MAIN_SITE_ID }
    val PLATFORM_TEST_SUPPLEMENTARY_TILE_ID by lazy { TEST_SUPPLEMENTARY_TILE_ID }
    val PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID by lazy { TEST_SUPPLEMENTARY_SECOND_USER_ID }
    val PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_PUBLIC_KEY by lazy { TEST_SUPPLEMENTARY_SECOND_USER_PUBLIC_KEY }
    val PLATFORM_FUSION_INTEGRATIONS by lazy { FUSION_INTEGRATIONS }

    @Serializable
    data class TestController(
        val type: String,
        val controller: BasicLockController
    )
}