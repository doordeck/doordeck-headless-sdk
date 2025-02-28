package com.doordeck.multiplatform.sdk.context

import com.doordeck.multiplatform.sdk.model.data.Crypto
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import kotlin.js.JsExport
import kotlin.native.CName

@JsExport
interface ContextManager {

    fun setApiEnvironment(apiEnvironment: ApiEnvironment)

    fun getApiEnvironment(): ApiEnvironment

    @CName("setAuthToken")
    fun setAuthToken(token: String)

    @CName("getAuthToken")
    fun getAuthToken(): String?

    @CName("isAuthTokenAboutToExpire")
    fun isAuthTokenAboutToExpire(): Boolean

    @CName("setRefreshToken")
    fun setRefreshToken(token: String)

    @CName("getRefreshToken")
    fun getRefreshToken(): String?

    @CName("setFusionAuthToken")
    fun setFusionAuthToken(token: String)

    @CName("getFusionAuthToken")
    fun getFusionAuthToken(): String?

    @CName("setUserId")
    fun setUserId(userId: String)

    @CName("getUserId")
    fun getUserId(): String?

    @CName("setUserEmail")
    fun setUserEmail(email: String)

    @CName("getUserEmail")
    fun getUserEmail(): String?

    fun setCertificateChain(certificateChain: List<String>)

    fun getCertificateChain(): List<String>?

    @CName("isCertificateChainAboutToExpire")
    fun isCertificateChainAboutToExpire(): Boolean

    fun setKeyPair(publicKey: ByteArray, privateKey: ByteArray)

    fun getKeyPair(): Crypto.KeyPair?

    @CName("isKeyPairValid")
    fun isKeyPairValid(): Boolean

    fun setOperationContext(userId: String, certificateChain: List<String>, publicKey: ByteArray, privateKey: ByteArray)

    @CName("setOperationContextJson")
    fun setOperationContextJson(data: String)

    @CName("clearContext")
    fun clearContext()
}