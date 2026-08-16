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
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.toCertificate
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.toPrivateKey
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.toPublicKey
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.toRsaPublicKey
import com.doordeck.multiplatform.sdk.model.data.FusionOperations
import com.doordeck.multiplatform.sdk.model.responses.toLockControllerResponse
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.toUri
import com.doordeck.multiplatform.sdk.util.toUuid
import java.net.URI

internal actual val PLATFORM_MAIN_USER_ID: String = "516d1150-9692-11f1-80c4-0f8537fbb53f"
internal actual val PLATFORM_MAIN_LOCK_ID: String = "fcb4eaf0-9693-11f1-80c4-0f8537fbb53f" // ANDROID Demo Lock

internal object PlatformTestConstants {

    val PLATFORM_TEST_MAIN_USER_PRIVATE_KEY by lazy { TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray().toPrivateKey() }
    val PLATFORM_TEST_MAIN_USER_PUBLIC_KEY = TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray().toPublicKey()
    val PLATFORM_TEST_MAIN_APPLICATION_NAME = TEST_MAIN_APPLICATION_NAME
    val PLATFORM_TEST_MAIN_FUSION_DOOR_NAME = TEST_MAIN_FUSION_DOOR_NAME
    val PLATFORM_TEST_MAIN_LOCK_NAME = TEST_MAIN_LOCK_NAME
    val PLATFORM_TEST_MAIN_USER_EMAIL = TEST_MAIN_USER_EMAIL
    val PLATFORM_TEST_MAIN_USER_ID = PLATFORM_MAIN_USER_ID.toUuid()
    val PLATFORM_TEST_SUPPLEMENTARY_USER_ID = TEST_SUPPLEMENTARY_USER_ID.toUuid()
    val PLATFORM_TEST_SUPPLEMENTARY_USER_PUBLIC_KEY = TEST_SUPPLEMENTARY_USER_PUBLIC_KEY.toRsaPublicKey()
    val PLATFORM_TEST_MAIN_TILE_ID = "70284036-5155-4e86-94b3-125a5a8b66e7".toUuid()
    val PLATFORM_TEST_MAIN_LOCK_ID = PLATFORM_MAIN_LOCK_ID.toUuid()
    val PLATFORM_TEST_MAIN_SITE_ID = "752d5c00-9690-11f1-a47a-dba4cb2c41d0".toUuid() // KMP ANDROID SITE
    val PLATFORM_TEST_SUPPLEMENTARY_TILE_ID = "49c5df50-4933-49be-9985-5478199c43ca".toUuid()
    val PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_ID = TEST_SUPPLEMENTARY_SECOND_USER_ID.toUuid()
    val PLATFORM_TEST_SUPPLEMENTARY_SECOND_USER_PUBLIC_KEY = TEST_SUPPLEMENTARY_SECOND_USER_PUBLIC_KEY.toRsaPublicKey()
    val PLATFORM_TEST_VALID_CERTIFICATE = TEST_VALID_CERTIFICATE.toCertificate()
    val PLATFORM_TEST_EXPIRED_CERTIFICATE = TEST_EXPIRED_CERTIFICATE.toCertificate()
    val PLATFORM_FUSION_INTEGRATIONS = FUSION_INTEGRATIONS.map {
        TestController(
            uri = it.uri.toUri(),
            type = it.type,
            controller = it.controller.toLockControllerResponse()
        )
    }

    internal data class TestController(
        val uri: URI,
        val type: String,
        val controller: FusionOperations.LockController
    )
}