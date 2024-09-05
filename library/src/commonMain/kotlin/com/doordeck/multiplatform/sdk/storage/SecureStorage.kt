package com.doordeck.multiplatform.sdk.storage

import com.doordeck.multiplatform.sdk.ApplicationContext
import kotlin.js.JsExport

@JsExport
interface SecureStorage {
    fun addCloudAuthToken(token: String)
    fun getCloudAuthToken(): String?

    fun addFusionAuthToken(token: String)
    fun getFusionAuthToken(): String?

    fun addPrivateKey(byteArray: ByteArray)
    fun getPrivateKey(): ByteArray?

    fun addUserId(userId: String)
    fun getUserId(): String?

    fun addCertificateChain(certificateChain: Array<String>)
    fun getCertificateChain(): Array<String>?

    fun clear()
}

expect fun createSecureStorage(applicationContext: ApplicationContext? = null): SecureStorage