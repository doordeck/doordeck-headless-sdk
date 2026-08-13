package com.doordeck.multiplatform.sdk

import com.doordeck.multiplatform.sdk.TestConstants.FUSION_INTEGRATIONS
import com.doordeck.multiplatform.sdk.TestConstants.TEST_EXPIRED_CERTIFICATE
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

internal object PlatformTestConstants {

    val PLATFORM_TEST_MAIN_USER_PRIVATE_KEY by lazy { TEST_MAIN_USER_PRIVATE_KEY.decodeBase64ToByteArray().toPrivateKey() }
    val PLATFORM_TEST_MAIN_USER_PUBLIC_KEY = TEST_MAIN_USER_PUBLIC_KEY.decodeBase64ToByteArray().toPublicKey()
    val PLATFORM_TEST_MAIN_LOCK_NAME = "KMP $platformType LOCK"
    val PLATFORM_TEST_MAIN_USER_EMAIL = "training+$platformType@doordeck.com"
    val PLATFORM_TEST_MAIN_USER_ID = "c88119c0-9675-11f1-80c4-0f8537fbb53f".toUuid()
    val PLATFORM_TEST_SUPPLEMENTARY_USER_ID = TEST_SUPPLEMENTARY_USER_ID.toUuid()
    val PLATFORM_TEST_SUPPLEMENTARY_USER_PUBLIC_KEY = TEST_SUPPLEMENTARY_USER_PUBLIC_KEY.toRsaPublicKey()
    val PLATFORM_TEST_MAIN_TILE_ID = "adfa3108-3128-4ea4-93cb-cfb1c9d39495".toUuid()
    val PLATFORM_TEST_MAIN_LOCK_ID = "64567110-9676-11f1-80c4-0f8537fbb53f".toUuid() // JVM Demo Lock
    val PLATFORM_TEST_MAIN_SITE_ID = "033d47c0-966f-11f1-99f0-59bbf00598b4".toUuid() // KMP JVM SITE
    val PLATFORM_TEST_SUPPLEMENTARY_TILE_ID = "adad4a9f-22d5-4816-a8cd-3748b45fd4d5".toUuid()
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