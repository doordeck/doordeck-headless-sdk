package com.doordeck.multiplatform.sdk.model.values

import com.doordeck.multiplatform.sdk.crypto.CryptoManager.toPrivateKey
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import java.security.PrivateKey

actual class PrivateKeyValue internal constructor(
    val key: PrivateKey
)

fun PrivateKey.toPrivateKeyValue(): PrivateKeyValue {
    return PrivateKeyValue(this)
}

internal actual fun String.toPrivateKeyValue(): PrivateKeyValue {
    return decodeBase64ToByteArray().toPrivateKey().toPrivateKeyValue()
}

internal actual fun PrivateKeyValue.toPrivateKeyValueString(): String {
    return key.encoded.encodeByteArrayToBase64()
}