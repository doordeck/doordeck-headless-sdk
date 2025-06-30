package com.doordeck.multiplatform.sdk.context

import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.model.data.Crypto
import kotlin.js.JsExport
import kotlin.native.CName

@JsExport
interface ContextManager {

    /**
     * Sets the API environment on which the SDK will operate, the provided value will be automatically stored in secure storage.
     *
     * It can be changed anytime, although it is recommended to set it up through the `SdkConfig` builder.
     */
    fun setApiEnvironment(apiEnvironment: ApiEnvironment)

    /**
     * Retrieves the current API environment.
     */
    @CName("getApiEnvironment")
    fun getApiEnvironment(): ApiEnvironment

    /**
     * Sets the cloud authentication token, the provided value will be automatically stored in secure storage.
     *
     * It can be changed anytime, although it is recommended to set it up through the `SdkConfig` builder.
     */
    @CName("setCloudAuthToken")
    fun setCloudAuthToken(token: String)

    /**
     * Retrieves the cloud authentication token.
     */
    @CName("getCloudAuthToken")
    fun getCloudAuthToken(): String?

    /**
     * Checks whether the cloud authentication token is invalid (e.g., null, malformed) or expired.
     * (we consider it expired if it will expire within the next [com.doordeck.multiplatform.sdk.util.MIN_TOKEN_LIFETIME_DAYS] days).
     */
    @CName("isCloudAuthTokenInvalidOrExpired")
    fun isCloudAuthTokenInvalidOrExpired(): Boolean

    /**
     * Sets the cloud refresh token, the provided value will be automatically stored in secure storage.
     *
     * It can be changed anytime, although it is recommended to set it up through the `SdkConfig` builder.
     */
    @CName("setCloudRefreshToken")
    fun setCloudRefreshToken(token: String)

    /**
     * Retrieves the cloud refresh token.
     */
    @CName("getCloudRefreshToken")
    fun getCloudRefreshToken(): String?

    /**
     * Sets the fusion host, the provided value will be automatically stored in secure storage.
     *
     * It can be changed anytime, although it is recommended to set it up through the SdkConfig builder.
     */
    @CName("setFusionHost")
    fun setFusionHost(host: String)

    /**
     * Retrieves the fusion host.
     */
    @CName("getFusionHost")
    fun getFusionHost(): String

    /**
     * Sets the Fusion authentication token, the provided value will be automatically stored in secure storage.
     */
    @CName("setFusionAuthToken")
    fun setFusionAuthToken(token: String)

    /**
     * Retrieves the Fusion authentication token.
     */
    @CName("getFusionAuthToken")
    fun getFusionAuthToken(): String?

    /**
     * Sets the user ID associated with the context, the provided value will be automatically stored in secure storage.
     */
    @CName("setUserId")
    fun setUserId(userId: String)

    /**
     * Retrieves the user identifier.
     */
    @CName("getUserId")
    fun getUserId(): String?

    /**
     * Sets the user email associated with the context, the provided value will be automatically stored in secure storage.
     */
    @CName("setUserEmail")
    fun setUserEmail(email: String)

    /**
     * Retrieves the user email.
     */
    @CName("getUserEmail")
    fun getUserEmail(): String?

    /**
     * Sets the certificate chain for the context, the provided value will be automatically stored in secure storage.
     */
    fun setCertificateChain(certificateChain: List<String>)

    /**
     * Retrieves the certificate chain.
     */
    fun getCertificateChain(): List<String>?

    /**
     * Checks whether the certificate chain is invalid (e.g., null, malformed) or expired.
     * (we consider it expired if it will expire within the next [com.doordeck.multiplatform.sdk.crypto.MIN_CERTIFICATE_LIFETIME_DAYS] days).
     */
    @CName("isCertificateChainInvalidOrExpired")
    fun isCertificateChainInvalidOrExpired(): Boolean

    /**
     * Sets the key pair for the context, the provided values will be automatically stored in secure storage.
     */
    fun setKeyPair(publicKey: ByteArray, privateKey: ByteArray)

    /**
     * Retrieves the key pair.
     */
    fun getKeyPair(): Crypto.KeyPair?

    /**
     * Sets the key pair verification status, the provided values will be automatically stored in secure storage.
     */
    fun setKeyPairVerified(publicKey: ByteArray?)

    /**
     * Retrieves the key pair verification status.
     */
    @CName("isKeyPairVerified")
    fun isKeyPairVerified(): Boolean

    /**
     * Checks whether the current key pair is not null and valid by signing a small piece of text and verifying it.
     */
    @CName("isKeyPairValid")
    fun isKeyPairValid(): Boolean

    /**
     * Sets all necessary fields to perform secure operations, the provided values will be automatically stored in secure storage.
     */
    fun setOperationContext(userId: String, certificateChain: List<String>, publicKey: ByteArray, privateKey: ByteArray, isKeyPairVerified: Boolean = true)

    /**
     * Sets all necessary fields to perform secure operations in JSON format, the provided values will be automatically stored in secure storage.
     */
    @CName("setOperationContextJson")
    fun setOperationContextJson(data: String)

    /**
     * Clears all the values stored in secure storage.
     */
    @CName("clearContext")
    fun clearContext()
}