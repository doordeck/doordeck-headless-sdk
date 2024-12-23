package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.ApplicationContext
import com.doordeck.multiplatform.sdk.api.model.ApiEnvironment
import com.doordeck.multiplatform.sdk.api.model.Crypto
import com.doordeck.multiplatform.sdk.storage.SecureStorage
import kotlin.js.JsExport

@JsExport
interface ContextManager {

    fun setApiEnvironment(apiEnvironment: ApiEnvironment)
    fun getApiEnvironment(): ApiEnvironment
    @JsExport.Ignore
    fun setApplicationContext(applicationContext: ApplicationContext)
    fun setAuthToken(token: String)
    fun getAuthToken(): String?
    fun isAuthTokenAboutToExpire(): Boolean
    fun setRefreshToken(token: String)
    fun getRefreshToken(): String?
    fun setFusionAuthToken(token: String)
    fun getFusionAuthToken(): String?
    fun setUserId(userId: String)
    fun getUserId(): String?
    fun setUserEmail(email: String)
    fun getUserEmail(): String?
    fun setCertificateChain(certificateChain: List<String>)
    fun getCertificateChain(): List<String>?
    fun isCertificateChainAboutToExpire(): Boolean
    fun setKeyPair(publicKey: ByteArray, privateKey: ByteArray)
    fun getKeyPair(): Crypto.KeyPair?
    fun isKeyPairValid(): Boolean
    fun setOperationContext(userId: String, certificateChain: List<String>, publicKey: ByteArray, privateKey: ByteArray)
    fun setOperationContextJson(data: String)
    fun setSecureStorageImpl(secureStorage: SecureStorage)
    fun loadContext()
    fun storeContext()
    fun clearContext()
}