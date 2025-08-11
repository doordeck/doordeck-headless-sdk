package com.doordeck.multiplatform.sdk.context

import com.doordeck.multiplatform.sdk.crypto.CryptoManager.toCertificate
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.toPrivateKey
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.toPublicKey
import com.doordeck.multiplatform.sdk.model.common.ContextState
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.toUri
import com.doordeck.multiplatform.sdk.util.toUuid
import java.net.URI
import java.security.KeyPair
import java.security.PublicKey
import java.security.cert.X509Certificate
import java.util.UUID

actual object ContextManager {

    fun setApiEnvironment(apiEnvironment: ApiEnvironment) = Context.setApiEnvironment(apiEnvironment)

    fun getApiEnvironment(): ApiEnvironment = Context.getApiEnvironment()

    fun setCloudAuthToken(token: String) = Context.setCloudAuthToken(token)

    fun getCloudAuthToken(): String? = Context.getCloudAuthToken()

    fun isCloudAuthTokenInvalidOrExpired(): Boolean = Context.isCloudAuthTokenInvalidOrExpired()

    fun setCloudRefreshToken(token: String) = Context.setCloudRefreshToken(token)

    fun getCloudRefreshToken(): String? = Context.getCloudRefreshToken()

    fun setFusionHost(host: URI) = Context.setFusionHost(host.toString())

    fun getFusionHost(): URI = Context.getFusionHost().toUri()

    fun setFusionAuthToken(token: String) = Context.setFusionAuthToken(token)

    fun getFusionAuthToken(): String? = Context.getFusionAuthToken()

    fun setUserId(userId: UUID) = Context.setUserId(userId.toString())

    fun getUserId(): UUID? = Context.getUserId()?.toUuid()

    fun setUserEmail(email: String) = Context.setUserEmail(email)

    fun getUserEmail(): String? = Context.getUserEmail()

    fun setCertificateChain(certificateChain: List<X509Certificate>) = Context.setCertificateChain(
        certificateChain = certificateChain.map {
            it.encoded.encodeByteArrayToBase64()
        }
    )

    fun getCertificateChain(): List<X509Certificate>? = Context.getCertificateChain()?.map {
        it.toCertificate()
    }

    fun isCertificateChainInvalidOrExpired(): Boolean = Context.isCertificateChainInvalidOrExpired()

    fun setKeyPair(keyPair: KeyPair) = Context.setKeyPair(
        publicKey = keyPair.public.encoded,
        privateKey = keyPair.private.encoded
    )

    fun getKeyPair(): KeyPair? = Context.getKeyPair()?.let {
        KeyPair(it.public.toPublicKey(), it.private.toPrivateKey())
    }

    fun setKeyPairVerified(publicKey: PublicKey?) = Context.setKeyPairVerified(publicKey?.encoded)

    fun isKeyPairVerified(): Boolean = Context.isKeyPairVerified()

    fun isKeyPairValid(): Boolean = Context.isKeyPairValid()

    fun setOperationContext(
        userId: UUID,
        certificateChain: List<X509Certificate>,
        keyPair: KeyPair,
        isKeyPairVerified: Boolean
    ) = Context.setOperationContext(
        userId = userId.toString(),
        certificateChain = certificateChain.map {
            it.encoded.encodeByteArrayToBase64()
        },
        publicKey = keyPair.public.encoded,
        privateKey = keyPair.private.encoded,
        isKeyPairVerified = isKeyPairVerified
    )

    fun getContextState(): ContextState = Context.getContextState()

    fun clearContext() = Context.clearContext()
}

actual fun contextManager(): ContextManager = ContextManager