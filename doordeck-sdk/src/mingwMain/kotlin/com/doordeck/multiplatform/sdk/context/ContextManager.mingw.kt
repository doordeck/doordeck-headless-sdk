package com.doordeck.multiplatform.sdk.context

import com.doordeck.multiplatform.sdk.CStringCallback
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.model.data.EncodedKeyPair
import com.doordeck.multiplatform.sdk.model.data.OperationContextData
import com.doordeck.multiplatform.sdk.util.Utils.certificateChainToString
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.Utils.stringToCertificateChain
import com.doordeck.multiplatform.sdk.util.handleCallback
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.toJson

actual object ContextManager {

    @CName("setApiEnvironment")
    fun setApiEnvironment(apiEnvironment: String) =
        Context.setApiEnvironment(ApiEnvironment.valueOf(apiEnvironment))

    @CName("getApiEnvironment")
    fun getApiEnvironment(): String = Context.getApiEnvironment().name

    @CName("setCloudAuthToken")
    fun setCloudAuthToken(token: String) = Context.setCloudAuthToken(token)

    @CName("getCloudAuthToken")
    fun getCloudAuthToken(): String? = Context.getCloudAuthToken()

    @CName("isCloudAuthTokenInvalidOrExpired")
    fun isCloudAuthTokenInvalidOrExpired(callback: CStringCallback) = callback.handleCallback {
        Context.isCloudAuthTokenInvalidOrExpired()
    }

    @CName("setCloudRefreshToken")
    fun setCloudRefreshToken(token: String) = Context.setCloudRefreshToken(token)

    @CName("getCloudRefreshToken")
    fun getCloudRefreshToken(): String? = Context.getCloudRefreshToken()

    @CName("setFusionHost")
    fun setFusionHost(host: String) = Context.setFusionHost(host)

    @CName("getFusionHost")
    fun getFusionHost(): String = Context.getFusionHost()

    @CName("setFusionAuthToken")
    fun setFusionAuthToken(token: String) = Context.setFusionAuthToken(token)

    @CName("getFusionAuthToken")
    fun getFusionAuthToken(): String? = Context.getFusionAuthToken()

    @CName("setUserId")
    fun setUserId(userId: String) = Context.setUserId(userId)

    @CName("getUserId")
    fun getUserId(): String? = Context.getUserId()

    @CName("setUserEmail")
    fun setUserEmail(email: String) = Context.setUserEmail(email)

    @CName("getUserEmail")
    fun getUserEmail(): String? = Context.getUserEmail()

    @CName("setCertificateChain")
    fun setCertificateChain(certificateChain: String) =
        Context.setCertificateChain(certificateChain.stringToCertificateChain())

    @CName("getCertificateChain")
    fun getCertificateChain(): String? = Context.getCertificateChain()?.certificateChainToString()

    @CName("isCertificateChainInvalidOrExpired")
    fun isCertificateChainInvalidOrExpired(): Boolean = Context.isCertificateChainInvalidOrExpired()

    @CName("setKeyPair")
    fun setKeyPair(publicKey: String, privateKey: String) = Context.setKeyPair(
        publicKey = publicKey.decodeBase64ToByteArray(),
        privateKey = privateKey.decodeBase64ToByteArray()
    )

    @CName("getKeyPair")
    fun getKeyPair(): String? = Context.getKeyPair()?.let {
        EncodedKeyPair(
            publicKey = it.public.encodeByteArrayToBase64(),
            privateKey = it.private.encodeByteArrayToBase64()
        )
    }?.toJson()

    @CName("setKeyPairVerified")
    fun setKeyPairVerified(publicKey: String?) = Context.setKeyPairVerified(publicKey?.decodeBase64ToByteArray())

    @CName("isKeyPairVerified")
    fun isKeyPairVerified(): Boolean = Context.isKeyPairVerified()

    @CName("isKeyPairValid")
    fun isKeyPairValid(): Boolean = Context.isKeyPairValid()

    /**
     * Sets all necessary fields to perform secure operations in JSON format, the provided values will be automatically stored in secure storage.
     */
    @CName("setOperationContext")
    fun setOperationContext(data: String) {
        val operationContextData = data.fromJson<OperationContextData>()
        Context.setOperationContext(
            userId = operationContextData.userId,
            certificateChain = operationContextData.certificateChain.stringToCertificateChain(),
            publicKey = operationContextData.publicKey.decodeBase64ToByteArray(),
            privateKey = operationContextData.privateKey.decodeBase64ToByteArray(),
            isKeyPairVerified = operationContextData.isKeyPairVerified
        )
    }

    @CName("getContextState")
    fun getContextState(callback: CStringCallback) = callback.handleCallback {
        Context.getContextState()
    }

    @CName("clearContext")
    fun clearContext() = Context.clearContext()
}

actual fun contextManager(): ContextManager = ContextManager