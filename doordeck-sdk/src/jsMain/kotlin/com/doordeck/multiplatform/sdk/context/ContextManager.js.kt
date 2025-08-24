package com.doordeck.multiplatform.sdk.context

import com.doordeck.multiplatform.sdk.model.common.ContextState
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.model.data.Crypto
import com.doordeck.multiplatform.sdk.util.toJsArray
import kotlin.js.collections.JsArray
import kotlin.js.collections.toList

@JsExport
actual object ContextManager {

    fun setApiEnvironment(apiEnvironment: ApiEnvironment) = Context.setApiEnvironment(apiEnvironment)

    fun getApiEnvironment(): ApiEnvironment = Context.getApiEnvironment()

    fun setCloudAuthToken(token: String) = Context.setCloudAuthToken(token)

    fun getCloudAuthToken(): String? = Context.getCloudAuthToken()

    fun isCloudAuthTokenInvalidOrExpired(): Boolean = Context.isCloudAuthTokenInvalidOrExpired()

    fun setCloudRefreshToken(token: String) = Context.setCloudRefreshToken(token)

    fun getCloudRefreshToken(): String? = Context.getCloudRefreshToken()

    fun setFusionHost(host: String) = Context.setFusionHost(host)

    fun getFusionHost(): String = Context.getFusionHost()

    fun setFusionAuthToken(token: String) = Context.setFusionAuthToken(token)

    fun getFusionAuthToken(): String? = Context.getFusionAuthToken()

    fun setUserId(userId: String) = Context.setUserId(userId)

    fun getUserId(): String? = Context.getUserId()

    fun setUserEmail(email: String) = Context.setUserEmail(email)

    fun getUserEmail(): String? = Context.getUserEmail()

    fun setCertificateChain(certificateChain: JsArray<String>) = Context.setCertificateChain(certificateChain.toList())

    fun getCertificateChain(): JsArray<String>? = Context.getCertificateChain()?.toJsArray()

    fun isCertificateChainInvalidOrExpired(): Boolean = Context.isCertificateChainInvalidOrExpired()

    fun setKeyPair(publicKey: ByteArray, privateKey: ByteArray) = Context.setKeyPair(publicKey, privateKey)

    fun getKeyPair(): Crypto.KeyPair? = Context.getKeyPair()

    fun setKeyPairVerified(publicKey: ByteArray?) = Context.setKeyPairVerified(publicKey)

    fun isKeyPairVerified(): Boolean = Context.isKeyPairVerified()

    fun isKeyPairValid(): Boolean = Context.isKeyPairValid()

    fun setOperationContext(
        userId: String,
        certificateChain: JsArray<String>,
        publicKey: ByteArray,
        privateKey: ByteArray,
        isKeyPairVerified: Boolean
    ) = Context.setOperationContext(
        userId = userId,
        certificateChain = certificateChain.toList(),
        publicKey = publicKey,
        privateKey = privateKey,
        isKeyPairVerified = isKeyPairVerified
    )

    fun getContextState(): ContextState = Context.getContextState()

    fun clearContext() = Context.clearContext()
}

@JsExport
actual fun contextManager(): ContextManager = ContextManager