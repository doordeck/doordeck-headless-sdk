package com.doordeck.multiplatform.sdk.storage

import com.doordeck.multiplatform.sdk.ApplicationContext
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import kotlin.js.JsExport

/**
 * Interface for secure storage operations.
 */
@JsExport
interface SecureStorage {

    /**
     * Stores the API environment on which the SDK will operate.
     *
     * @param apiEnvironment The api environment to be stored.
     */
    fun setApiEnvironment(apiEnvironment: ApiEnvironment)

    /**
     * Retrieves the API environment
     */
    fun getApiEnvironment(): ApiEnvironment?

    /**
     * Stores the cloud authentication token.
     *
     * @param token The cloud authentication token to be stored.
     */
    fun addCloudAuthToken(token: String)

    /**
     * Retrieves the cloud authentication token.
     *
     * @return The stored cloud authentication token, or null if not found.
     */
    fun getCloudAuthToken(): String?

    /**
     * Stores the cloud refresh token.
     *
     * @param token The cloud refresh token to be stored.
     */
    fun addCloudRefreshToken(token: String)

    /**
     * Retrieves the cloud refresh token.
     *
     * @return The stored cloud refresh token, or null if not found.
     */
    fun getCloudRefreshToken(): String?

    /**
     * Stores the fusion host.
     *
     * @param host The fusion host to be stored.
     */
    fun setFusionHost(host: String)

    /**
     * Retrieves the fusion host.
     *
     * @return The stored fusion host, or null if not found.
     */
    fun getFusionHost(): String?

    /**
     * Stores the Fusion authentication token.
     *
     * @param token The Fusion authentication token to be stored.
     */
    fun addFusionAuthToken(token: String)

    /**
     * Retrieves the Fusion authentication token.
     *
     * @return The stored Fusion authentication token, or null if not found.
     */
    fun getFusionAuthToken(): String?

    /**
     * Stores the public key.
     *
     * @param publicKey The public key as a byte array.
     */
    fun addPublicKey(publicKey: ByteArray)

    /**
     * Retrieves the public key.
     *
     * @return The stored public key as a byte array, or null if not found.
     */
    fun getPublicKey(): ByteArray?

    /**
     * Stores the private key.
     *
     * @param privateKey The private key as a byte array.
     */
    fun addPrivateKey(privateKey: ByteArray)

    /**
     * Retrieves the private key.
     *
     * @return The stored private key as a byte array, or null if not found.
     */
    fun getPrivateKey(): ByteArray?

    /**
     * Stores the public key that has been verified via two-factor authentication.
     *
     * @param publicKey The key pair verification status
     */
    fun setKeyPairVerified(publicKey: ByteArray?)

    /**
     * Retrieves the verified public key and compares it against the stored public key.
     * Returns true if they match exactly, or false if either value is null or the keys don't match.
     *
     * @return The stored key pair verification status a boolean, or null if not found.
     */
    fun getKeyPairVerified(): ByteArray?

    /**
     * Stores the user ID.
     *
     * @param userId The user ID to be stored.
     */
    fun addUserId(userId: String)

    /**
     * Retrieves the user ID.
     *
     * @return The stored user ID, or null if not found.
     */
    fun getUserId(): String?

    /**
     * Stores the user email.
     *
     * @param email The user email to be stored.
     */
    fun addUserEmail(email: String)

    /**
     * Retrieves the user email.
     *
     * @return The stored user email, or null if not found.
     */
    fun getUserEmail(): String?

    /**
     * Stores the certificate chain.
     *
     * @param certificateChain The certificate chain as a list of strings.
     */
    fun addCertificateChain(certificateChain: List<String>)

    /**
     * Retrieves the certificate chain.
     *
     * @return The stored certificate chain as a list of strings, or null if not found.
     */
    fun getCertificateChain(): List<String>?

    /**
     * Clears all stored data.
     */
    fun clear()
}

internal expect fun createSecureStorage(applicationContext: ApplicationContext? = null): SecureStorage