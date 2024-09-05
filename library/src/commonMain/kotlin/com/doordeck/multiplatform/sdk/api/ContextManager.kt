package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.storage.SecureStorage
import kotlin.js.JsExport

@JsExport
interface ContextManager {

    fun setAuthToken(token: String)
    fun setOperationContext(userId: String, certificateChain: Array<String>, privateKey: ByteArray)
    fun setFusionAuthToken(token: String)
    fun setSecureStorageImpl(secureStorage: SecureStorage)
    fun loadContext()
    fun storeContext()
}