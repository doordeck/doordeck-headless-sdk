package com.doordeck.multiplatform.sdk.storage

import com.doordeck.multiplatform.sdk.ApplicationContext
import kotlin.js.JsExport

@JsExport
interface SecureStorage {
    fun addCloudAuthToken(token: String)
    fun getCloudAuthToken(): String?

    fun addCloudRefreshToken(token: String)
    fun getCloudRefreshToken(): String?

    fun addFusionAuthToken(token: String)
    fun getFusionAuthToken(): String?

    fun addPublicKey(byteArray: ByteArray)
    fun getPublicKey(): ByteArray?

    fun addPrivateKey(byteArray: ByteArray)
    fun getPrivateKey(): ByteArray?

    fun addUserId(userId: String)
    fun getUserId(): String?

    fun addUserEmail(email: String)
    fun getUserEmail(): String?

    fun addCertificateChain(certificateChain: List<String>)
    fun getCertificateChain(): List<String>?

    fun clear()
}

expect fun createSecureStorage(applicationContext: ApplicationContext? = null): SecureStorage