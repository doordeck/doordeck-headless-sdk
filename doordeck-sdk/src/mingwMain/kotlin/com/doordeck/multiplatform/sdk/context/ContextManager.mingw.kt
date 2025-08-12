package com.doordeck.multiplatform.sdk.context

import com.doordeck.multiplatform.sdk.model.common.ContextState
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.model.data.Crypto
import com.doordeck.multiplatform.sdk.model.data.OperationContextData
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Utils.stringToCertificateChain
import com.doordeck.multiplatform.sdk.util.fromJson

actual object ContextManager {

    fun setApiEnvironment(apiEnvironment: ApiEnvironment) {
        Context.setApiEnvironment(apiEnvironment)
    }

    @CName("getApiEnvironment")
    fun getApiEnvironment(): ApiEnvironment {
        return Context.getApiEnvironment()
    }

    @CName("setCloudAuthToken")
    fun setCloudAuthToken(token: String) {
        Context.setCloudAuthToken(token)
    }

    @CName("getCloudAuthToken")
    fun getCloudAuthToken(): String? {
        return Context.getCloudAuthToken()
    }

    @CName("isCloudAuthTokenInvalidOrExpired")
    fun isCloudAuthTokenInvalidOrExpired(): Boolean {
        return Context.isCloudAuthTokenInvalidOrExpired()
    }

    @CName("setCloudRefreshToken")
    fun setCloudRefreshToken(token: String) {
        Context.setCloudRefreshToken(token)
    }

    @CName("getCloudRefreshToken")
    fun getCloudRefreshToken(): String? {
        return Context.getCloudRefreshToken()
    }

    @CName("setFusionHost")
    fun setFusionHost(host: String) {
        Context.setFusionHost(host)
    }

    @CName("getFusionHost")
    fun getFusionHost(): String {
        return Context.getFusionHost()
    }

    @CName("setFusionAuthToken")
    fun setFusionAuthToken(token: String) {
        Context.setFusionAuthToken(token)
    }

    @CName("getFusionAuthToken")
    fun getFusionAuthToken(): String? {
        return Context.getFusionAuthToken()
    }

    @CName("setUserId")
    fun setUserId(userId: String) {
        Context.setUserId(userId)
    }

    @CName("getUserId")
    fun getUserId(): String? {
        return Context.getUserId()
    }

    @CName("setUserEmail")
    fun setUserEmail(email: String) {
        Context.setUserEmail(email)
    }

    @CName("getUserEmail")
    fun getUserEmail(): String? {
        return Context.getUserEmail()
    }

    fun setCertificateChain(certificateChain: List<String>) {
        Context.setCertificateChain(certificateChain)
    }

    fun getCertificateChain(): List<String>? {
        return Context.getCertificateChain()
    }

    @CName("isCertificateChainInvalidOrExpired")
    fun isCertificateChainInvalidOrExpired(): Boolean {
        return Context.isCertificateChainInvalidOrExpired()
    }

    fun setKeyPair(publicKey: ByteArray, privateKey: ByteArray) {
        Context.setKeyPair(publicKey, privateKey)
    }

    fun getKeyPair(): Crypto.KeyPair? {
        return Context.getKeyPair()
    }

    fun setKeyPairVerified(publicKey: ByteArray?) {
        Context.setKeyPairVerified(publicKey)
    }

    @CName("isKeyPairVerified")
    fun isKeyPairVerified(): Boolean {
        return Context.isKeyPairVerified()
    }

    @CName("isKeyPairValid")
    fun isKeyPairValid(): Boolean {
        return Context.isKeyPairValid()
    }

    fun setOperationContext(userId: String, certificateChain: List<String>, publicKey: ByteArray,
                            privateKey: ByteArray, isKeyPairVerified: Boolean) {
        Context.setOperationContext(
            userId = userId,
            certificateChain = certificateChain,
            publicKey = publicKey,
            privateKey = privateKey,
            isKeyPairVerified = isKeyPairVerified
        )
    }

    /**
     * Sets all necessary fields to perform secure operations in JSON format, the provided values will be automatically stored in secure storage.
     */
    @CName("setOperationContextJson")
    fun setOperationContextJson(data: String) {
        val operationContextData = data.fromJson<OperationContextData>()
        setOperationContext(
            userId = operationContextData.userId,
            certificateChain = operationContextData.userCertificateChain.stringToCertificateChain(),
            publicKey = operationContextData.userPublicKey.decodeBase64ToByteArray(),
            privateKey = operationContextData.userPrivateKey.decodeBase64ToByteArray(),
            isKeyPairVerified = operationContextData.isKeyPairVerified
        )
    }

    @CName("getContextState")
    fun getContextState(): ContextState {
        return Context.getContextState()
    }

    @CName("clearContext")
    fun clearContext() {
        Context.clearContext()
    }
}

actual fun contextManager(): ContextManager = ContextManager