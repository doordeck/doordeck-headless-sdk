package com.doordeck.multiplatform.sdk.context

import com.doordeck.multiplatform.sdk.model.common.ContextState
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.model.data.Crypto

@JsExport
actual object ContextManager {

    fun setApiEnvironment(apiEnvironment: ApiEnvironment) {
        Context.setApiEnvironment(apiEnvironment)
    }

    fun getApiEnvironment(): ApiEnvironment {
        return Context.getApiEnvironment()
    }

    fun setCloudAuthToken(token: String) {
        Context.setCloudAuthToken(token)
    }

    fun getCloudAuthToken(): String? {
        return Context.getCloudAuthToken()
    }

    fun isCloudAuthTokenInvalidOrExpired(): Boolean {
        return Context.isCloudAuthTokenInvalidOrExpired()
    }

    fun setCloudRefreshToken(token: String) {
        Context.setCloudRefreshToken(token)
    }

    fun getCloudRefreshToken(): String? {
        return Context.getCloudRefreshToken()
    }

    fun setFusionHost(host: String) {
        Context.setFusionHost(host)
    }

    fun getFusionHost(): String {
        return Context.getFusionHost()
    }

    fun setFusionAuthToken(token: String) {
        Context.setFusionAuthToken(token)
    }

    fun getFusionAuthToken(): String? {
        return Context.getFusionAuthToken()
    }

    fun setUserId(userId: String) {
        Context.setUserId(userId)
    }

    fun getUserId(): String? {
        return Context.getUserId()
    }

    fun setUserEmail(email: String) {
        Context.setUserEmail(email)
    }

    fun getUserEmail(): String? {
        return Context.getUserEmail()
    }

    fun setCertificateChain(certificateChain: List<String>) {
        Context.setCertificateChain(certificateChain)
    }

    fun getCertificateChain(): List<String>? {
        return Context.getCertificateChain()
    }

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

    fun isKeyPairVerified(): Boolean {
        return Context.isKeyPairVerified()
    }

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

    fun getContextState(): ContextState {
        return Context.getContextState()
    }

    fun clearContext() {
        Context.clearContext()
    }
}

@JsExport
actual fun contextManager(): ContextManager = ContextManager