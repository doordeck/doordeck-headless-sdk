package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.ApplicationContext
import com.doordeck.multiplatform.sdk.api.model.ApiEnvironment
import com.doordeck.multiplatform.sdk.storage.SecureStorage
import kotlin.js.JsExport

@JsExport
interface ContextManager {

    fun setApiEnvironment(apiEnvironment: ApiEnvironment)
    @JsExport.Ignore
    fun setApplicationContext(applicationContext: ApplicationContext)
    fun setAuthToken(token: String)
    fun isAuthTokenAboutToExpire(): Boolean
    fun setRefreshToken(token: String)
    fun setFusionAuthToken(token: String)
    fun setUserId(userId: String)
    fun setCertificateChain(certificateChain: List<String>)
    fun isCertificateChainAboutToExpire(): Boolean
    fun setKeyPair(publicKey: ByteArray, privateKey: ByteArray)
    fun setOperationContext(userId: String, certificateChain: List<String>, publicKey: ByteArray, privateKey: ByteArray)
    fun setOperationContextJson(data: String)
    fun setSecureStorageImpl(secureStorage: SecureStorage)
    fun loadContext()
    fun storeContext()
    fun clearContext()
}