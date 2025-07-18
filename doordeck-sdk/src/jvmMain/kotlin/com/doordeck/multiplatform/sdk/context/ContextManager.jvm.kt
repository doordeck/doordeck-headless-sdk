package com.doordeck.multiplatform.sdk.context

import com.doordeck.multiplatform.sdk.crypto.CryptoManager.toCertificate
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.toPrivateKey
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.toPublicKey
import com.doordeck.multiplatform.sdk.model.common.ContextState
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.toUUID
import java.security.KeyPair
import java.security.PrivateKey
import java.security.PublicKey
import java.security.cert.X509Certificate
import java.util.UUID

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

    fun setUserId(userId: UUID) {
        Context.setUserId(userId.toString())
    }

    fun getUserId(): UUID? {
        return Context.getUserId()?.toUUID()
    }

    fun setUserEmail(email: String) {
        Context.setUserEmail(email)
    }

    fun getUserEmail(): String? {
        return Context.getUserEmail()
    }

    fun setCertificateChain(certificateChain: List<X509Certificate>) {
        Context.setCertificateChain(certificateChain.map { it.encoded.encodeByteArrayToBase64() })
    }

    fun getCertificateChain(): List<X509Certificate>? {
        return Context.getCertificateChain()?.map { it.toCertificate() }
    }

    fun isCertificateChainInvalidOrExpired(): Boolean {
        return Context.isCertificateChainInvalidOrExpired()
    }

    fun setKeyPair(publicKey: PublicKey, privateKey: PrivateKey) {
        Context.setKeyPair(publicKey.encoded, privateKey.encoded)
    }

    fun getKeyPair(): KeyPair? {
        return Context.getKeyPair()?.let {
            KeyPair(it.public.toPublicKey(), it.private.toPrivateKey())
        }
    }

    fun setKeyPairVerified(publicKey: PublicKey?) {
        Context.setKeyPairVerified(publicKey?.encoded)
    }

    fun isKeyPairVerified(): Boolean {
        return Context.isKeyPairVerified()
    }

    fun isKeyPairValid(): Boolean {
        return Context.isKeyPairValid()
    }

    fun setOperationContext(userId: UUID, certificateChain: List<X509Certificate>, publicKey: PublicKey,
                            privateKey: PrivateKey, isKeyPairVerified: Boolean) {
        Context.setOperationContext(
            userId = userId.toString(),
            certificateChain = certificateChain.map { it.encoded.encodeByteArrayToBase64() },
            publicKey = privateKey.encoded,
            privateKey = publicKey.encoded,
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

actual fun contextManager(): ContextManager = ContextManager