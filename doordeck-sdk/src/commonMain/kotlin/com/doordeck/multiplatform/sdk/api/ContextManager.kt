package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.storage.SecureStorage
import kotlin.js.JsExport

@JsExport
interface ContextManager {

    fun setAuthToken(token: String)
    fun setOperationContext(userId: String, certificateChain: List<String>, privateKey: ByteArray)
    fun setOperationContextJson(data: String)
    fun setFusionAuthToken(token: String)
    fun setSecureStorageImpl(secureStorage: SecureStorage)
    fun loadContext()
    fun storeContext()
    fun clearContext()
}