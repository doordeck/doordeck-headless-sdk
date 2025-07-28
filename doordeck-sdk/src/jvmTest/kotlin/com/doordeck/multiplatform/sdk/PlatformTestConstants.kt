package com.doordeck.multiplatform.sdk

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
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.toPrivateKey
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.toPublicKey
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.toUuid

internal object PlatformTestConstants {

    val PLATFORM_TEST_MAIN_USER_PRIVATE_KEY by lazy { TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray().toPrivateKey() }
    val PLATFORM_TEST_MAIN_USER_PUBLIC_KEY by lazy { TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray().toPublicKey() }
    val PLATFORM_TEST_MAIN_USER_ID by lazy { TEST_MAIN_USER_ID.toUuid() }
    val PLATFORM_TEST_SUPPLEMENTARY_USER_ID by lazy { TEST_SUPPLEMENTARY_USER_ID.toUuid() }
    val PLATFORM_TEST_SUPPLEMENTARY_USER_PUBLIC_KEY by lazy { TEST_SUPPLEMENTARY_USER_PUBLIC_KEY.decodeBase64ToByteArray().toPublicKey() }
    val PLATFORM_TEST_MAIN_TILE_ID by lazy { TEST_MAIN_TILE_ID.toUuid() }
    val PLATFORM_TEST_MAIN_LOCK_ID by lazy { TEST_MAIN_LOCK_ID.toUuid() }
    val PLATFORM_TEST_MAIN_SITE_ID by lazy { TEST_MAIN_SITE_ID.toUuid() }
    val PLATFORM_TEST_SUPPLEMENTARY_TILE_ID by lazy { TEST_SUPPLEMENTARY_TILE_ID.toUuid() }
    val PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID by lazy { TEST_SUPPLEMENTARY_SECOND_USER_ID.toUuid() }
    val PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_PUBLIC_KEY by lazy { TEST_SUPPLEMENTARY_SECOND_USER_PUBLIC_KEY.decodeBase64ToByteArray().toPublicKey() }
}