package com.doordeck.multiplatform.sdk.context

import com.doordeck.multiplatform.sdk.model.data.Crypto
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import kotlin.js.JsExport
import kotlin.native.CName

@JsExport
interface ContextManager {

    /**
     * Sets the API environment on which the SDK will operate.
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
     * Checks whether the cloud authentication token is null or will expire within the next 24 hours.
     */
    @CName("isCloudAuthTokenAboutToExpire")
    fun isCloudAuthTokenAboutToExpire(): Boolean

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
     * Checks whether the certificate chain is null or will expire within the next 7 days.
     */
    @CName("isCertificateChainAboutToExpire")
    fun isCertificateChainAboutToExpire(): Boolean

    /**
     * Sets the key pair for the context, the provided values will be automatically stored in secure storage.
     */
    fun setKeyPair(publicKey: ByteArray, privateKey: ByteArray)

    /**
     * Retrieves the key pair.
     */
    fun getKeyPair(): Crypto.KeyPair?

    /**
     * Checks whether the current key pair is not null and valid by signing a small piece of text and verifying it.
     */
    @CName("isKeyPairValid")
    fun isKeyPairValid(): Boolean

    /**
     * Sets all necessary fields to perform secure operations, the provided values will be automatically stored in secure storage.
     */
    fun setOperationContext(userId: String, certificateChain: List<String>, publicKey: ByteArray, privateKey: ByteArray)

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