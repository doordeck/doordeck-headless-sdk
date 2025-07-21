package com.doordeck.multiplatform.sdk.model.values

import com.doordeck.multiplatform.sdk.crypto.CryptoManager.toPublicKey
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import java.security.PublicKey

actual class PlatformPublicKey internal constructor(
    val key: PublicKey
)

internal actual fun String.toPlatformPublicKey(): PlatformPublicKey {
    return PlatformPublicKey(decodeBase64ToByteArray().toPublicKey())
}

internal actual fun PlatformPublicKey.toPlatformPublicKeyString(): String {
   return key.encoded.encodeByteArrayToBase64()
}