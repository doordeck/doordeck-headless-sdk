package com.doordeck.multiplatform.sdk.context

import com.doordeck.multiplatform.sdk.model.common.ContextState
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.model.data.Crypto
import com.doordeck.multiplatform.sdk.util.toNsUrl
import com.doordeck.multiplatform.sdk.util.toUrlString
import platform.Foundation.NSURL

actual object ContextManager {

    fun setApiEnvironment(apiEnvironment: ApiEnvironment) = Context.setApiEnvironment(apiEnvironment)

    fun getApiEnvironment(): ApiEnvironment = Context.getApiEnvironment()

    fun setCloudAuthToken(token: String) = Context.setCloudAuthToken(token)

    fun getCloudAuthToken(): String? = Context.getCloudAuthToken()

    fun isCloudAuthTokenInvalidOrExpired(): Boolean = Context.isCloudAuthTokenInvalidOrExpired()

    fun setCloudRefreshToken(token: String) = Context.setCloudRefreshToken(token)

    fun getCloudRefreshToken(): String? = Context.getCloudRefreshToken()

    fun setFusionHost(host: NSURL) = Context.setFusionHost(host.toUrlString())

    fun getFusionHost(): NSURL = Context.getFusionHost().toNsUrl()

    fun setFusionAuthToken(token: String) = Context.setFusionAuthToken(token)

    fun getFusionAuthToken(): String? = Context.getFusionAuthToken()

    fun setUserId(userId: String) = Context.setUserId(userId)

    fun getUserId(): String? = Context.getUserId()

    fun setUserEmail(email: String) = Context.setUserEmail(email)

    fun getUserEmail(): String? = Context.getUserEmail()

    fun setCertificateChain(certificateChain: List<String>) = Context.setCertificateChain(certificateChain)

    fun getCertificateChain(): List<String>? = Context.getCertificateChain()

    fun isCertificateChainInvalidOrExpired(): Boolean = Context.isCertificateChainInvalidOrExpired()

    fun setKeyPair(publicKey: ByteArray, privateKey: ByteArray) = Context.setKeyPair(publicKey, privateKey)

    fun getKeyPair(): Crypto.KeyPair? = Context.getKeyPair()

    fun setKeyPairVerified(publicKey: ByteArray?) = Context.setKeyPairVerified(publicKey)

    fun isKeyPairVerified(): Boolean = Context.isKeyPairVerified()

    fun isKeyPairValid(): Boolean = Context.isKeyPairValid()

    fun setOperationContext(
        userId: String,
        certificateChain: List<String>,
        publicKey: ByteArray,
        privateKey: ByteArray,
        isKeyPairVerified: Boolean
    ) = Context.setOperationContext(
        userId = userId,
        certificateChain = certificateChain,
        publicKey = publicKey,
        privateKey = privateKey,
        isKeyPairVerified = isKeyPairVerified
    )

    fun getContextState(): ContextState = Context.getContextState()

    fun clearContext() = Context.clearContext()
}

actual fun contextManager(): ContextManager = ContextManager