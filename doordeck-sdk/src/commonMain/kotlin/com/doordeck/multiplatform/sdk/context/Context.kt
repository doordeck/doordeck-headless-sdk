package com.doordeck.multiplatform.sdk.context

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.Constants.DEFAULT_FUSION_HOST
import com.doordeck.multiplatform.sdk.cache.CapabilityCache
import com.doordeck.multiplatform.sdk.clients.AccountClient
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.model.common.ContextState
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.model.data.Crypto
import com.doordeck.multiplatform.sdk.storage.DefaultSecureStorage
import com.doordeck.multiplatform.sdk.storage.MemorySettings
import com.doordeck.multiplatform.sdk.storage.SecureStorage
import com.doordeck.multiplatform.sdk.util.JwtUtils.isJwtTokenInvalidOrExpired
import com.doordeck.multiplatform.sdk.util.JwtUtils.isJwtTokenInvalidOrPastExpiration
import com.doordeck.multiplatform.sdk.util.KeyPairUtils
import com.doordeck.multiplatform.sdk.util.refreshCloudAuthTokensOrNull
import kotlin.jvm.JvmSynthetic

internal object Context {

    private var secureStorage: SecureStorage = DefaultSecureStorage(MemorySettings())

    /**
     * Sets the API environment on which the SDK will operate, the provided value will be automatically stored in secure storage.
     *
     * It can be changed anytime, although it is recommended to set it up through the `SdkConfig` builder.
     */
    @JvmSynthetic
    internal fun setApiEnvironment(apiEnvironment: ApiEnvironment) {
        secureStorage.setApiEnvironment(apiEnvironment)
    }

    /**
     * Retrieves the current API environment.
     */
    @JvmSynthetic
    internal fun getApiEnvironment(): ApiEnvironment {
        return secureStorage.getApiEnvironment()
            ?: ApiEnvironment.PROD
    }

    /**
     * Sets the cloud authentication token, the provided value will be automatically stored in secure storage,
     * passing `null` removes the existing entry.
     *
     * It can be changed anytime, although it is recommended to set it up through the `SdkConfig` builder.
     */
    @JvmSynthetic
    internal fun setCloudAuthToken(token: String?) {
        secureStorage.addCloudAuthToken(token)
    }

    /**
     * Retrieves the cloud authentication token.
     */
    @JvmSynthetic
    internal fun getCloudAuthToken(): String? {
        return secureStorage.getCloudAuthToken()
    }

    /**
     * Checks whether the cloud authentication token is invalid (e.g., null, malformed) or expired.
     * A token that is null, malformed or about to expire (within
     * [com.doordeck.multiplatform.sdk.util.MIN_TOKEN_LIFETIME_DAYS]) is first refreshed when a usable refresh token
     * is stored. When it cannot be refreshed without user interaction, a token that is not yet past its expiration
     * still counts as valid, since the backend accepts it until then.
     *
     * A network request may be performed regardless of [checkServerInvalidation]: the refresh goes through the
     * backend whenever a refresh token is stored and the SDK is not being released. [checkServerInvalidation] only
     * applies to a token that passes the local checks, including one that could not be refreshed but is not past its
     * expiration, which is then verified with the backend to make sure it has not been invalidated. No request is
     * made for such a token when [checkServerInvalidation] is false.
     *
     * @param checkServerInvalidation Whether a token that passes the local checks should also be verified with the
     *  backend (by performing a network request)
     * @return true if the token is null, malformed, past its expiration, or invalidated (when checkServerInvalidation
     *  is true) and it could not be refreshed. Otherwise, returns false.
     */
    @JvmSynthetic
    internal suspend fun isCloudAuthTokenInvalidOrExpired(checkServerInvalidation: Boolean): Boolean {
        val token = getCloudAuthToken()
        if (token == null || token.isJwtTokenInvalidOrExpired()) {
            val refreshedToken = CloudHttpClient.client
                .refreshCloudAuthTokensOrNull(staleToken = token, alreadyRejectedByBackend = false)
            if (refreshedToken != null) {
                return false
            }
            // Not refreshed: the stored token remains usable until it is past its expiration
            if (token == null || token.isJwtTokenInvalidOrPastExpiration()) {
                return true
            }
        }
        return if (checkServerInvalidation) {
            try {
                AccountClient.getUserDetailsRequest()
                false
            } catch (_: Exception) {
                true
            }
        } else {
            false
        }
    }

    /**
     * Sets the cloud refresh token, the provided value will be automatically stored in secure storage,
     * passing `null` removes the existing entry.
     *
     * It can be changed anytime, although it is recommended to set it up through the `SdkConfig` builder.
     */
    @JvmSynthetic
    internal fun setCloudRefreshToken(token: String?) {
        secureStorage.addCloudRefreshToken(token)
    }

    /**
     * Retrieves the cloud refresh token.
     */
    @JvmSynthetic
    internal fun getCloudRefreshToken(): String? {
        return secureStorage.getCloudRefreshToken()
    }

    /**
     * Sets the fusion host, the provided value will be automatically stored in secure storage.
     *
     * It can be changed anytime, although it is recommended to set it up through the SdkConfig builder.
     */
    @JvmSynthetic
    internal fun setFusionHost(host: String) {
        secureStorage.setFusionHost(host)
    }

    /**
     * Retrieves the fusion host.
     */
    @JvmSynthetic
    internal fun getFusionHost(): String {
        return secureStorage.getFusionHost()
            ?: DEFAULT_FUSION_HOST
    }

    /**
     * Sets the Fusion authentication token, the provided value will be automatically stored in secure storage.
     */
    @JvmSynthetic
    internal fun setFusionAuthToken(token: String) {
        secureStorage.addFusionAuthToken(token)
    }

    /**
     * Retrieves the Fusion authentication token.
     */
    @JvmSynthetic
    internal fun getFusionAuthToken(): String? {
        return secureStorage.getFusionAuthToken()
    }

    /**
     * Sets the user ID associated with the context, the provided value will be automatically stored in secure storage.
     */
    @JvmSynthetic
    internal fun setUserId(userId: String) {
        secureStorage.addUserId(userId)
    }

    /**
     * Retrieves the user identifier.
     */
    @JvmSynthetic
    internal fun getUserId(): String? {
        return secureStorage.getUserId()
    }

    /**
     * Sets the user email associated with the context, the provided value will be automatically stored in secure storage.
     */
    @JvmSynthetic
    internal fun setUserEmail(email: String) {
        secureStorage.addUserEmail(email)
    }

    /**
     * Retrieves the user email.
     */
    @JvmSynthetic
    internal fun getUserEmail(): String? {
        return secureStorage.getUserEmail()
    }

    /**
     * Sets the certificate chain for the context, the provided value will be automatically stored in secure storage.
     */
    @JvmSynthetic
    internal fun setCertificateChain(certificateChain: List<String>) {
        secureStorage.addCertificateChain(certificateChain)
    }

    /**
     * Retrieves the certificate chain.
     */
    @JvmSynthetic
    internal fun getCertificateChain(): List<String>? {
        return secureStorage.getCertificateChain()
    }

    /**
     * Checks whether the certificate chain is invalid (e.g., null, malformed) or expired.
     * (we consider it expired if it will expire within the next [com.doordeck.multiplatform.sdk.crypto.MIN_CERTIFICATE_LIFETIME_DAYS] days).
     */
    @JvmSynthetic
    internal fun isCertificateChainInvalidOrExpired(): Boolean {
        return getCertificateChain()?.firstOrNull()?.let {
            CryptoManager.isCertificateInvalidOrExpired(it)
        } ?: true
    }

    /**
     * Sets the key pair for the context, the provided values will be automatically stored in secure storage.
     */
    @JvmSynthetic
    internal fun setKeyPair(publicKey: ByteArray, privateKey: ByteArray) {
        secureStorage.addPublicKey(publicKey)
        secureStorage.addPrivateKey(privateKey)
    }

    /**
     * Retrieves the key pair.
     */
    @JvmSynthetic
    internal fun getKeyPair(): Crypto.KeyPair? {
        val actualUserPublicKey = getPublicKey()
        val actualUserPrivateKey = getPrivateKey()
        return if (actualUserPublicKey != null && actualUserPrivateKey != null) {
            Crypto.KeyPair(actualUserPrivateKey, actualUserPublicKey)
        } else null
    }

    /**
     * Sets the public key that has been verified via two-factor authentication. The provided value will be automatically stored in secure storage.
     */
    @JvmSynthetic
    internal fun setKeyPairVerified(publicKey: ByteArray?) {
        secureStorage.setKeyPairVerified(publicKey)
    }

    /**
     * Retrieves the key pair verification status.
     */
    @JvmSynthetic
    internal fun isKeyPairVerified(): Boolean {
        val verified = secureStorage.getKeyPairVerified()
        val publicKey = secureStorage.getPublicKey()
        if (verified == null || publicKey == null) {
            return false
        }
        return verified.contentEquals(publicKey)
    }

    @JvmSynthetic
    internal fun getPublicKey(): ByteArray? {
        return secureStorage.getPublicKey()
    }

    @JvmSynthetic
    internal fun getPrivateKey(): ByteArray? {
        return secureStorage.getPrivateKey()
    }

    /**
     * Checks whether the current key pair is not null and valid by signing a small piece of text and verifying it.
     */
    @JvmSynthetic
    internal fun isKeyPairValid(): Boolean {
        val publicKey = getPublicKey()
        val privateKey = getPrivateKey()
        if (publicKey == null || privateKey == null) {
            return false
        }
        return KeyPairUtils.isKeyPairValid(publicKey, privateKey)
    }

    @JvmSynthetic
    internal fun reset() {
        CapabilityCache.reset()
        clearContext()
    }

    /**
     * Sets all necessary fields to perform secure operations, the provided values will be automatically stored in secure storage.
     */
    @JvmSynthetic
    internal fun setOperationContext(userId: String, certificateChain: List<String>, publicKey: ByteArray,
                                     privateKey: ByteArray, isKeyPairVerified: Boolean) {
        setUserId(userId)
        setCertificateChain(certificateChain)
        setKeyPair(publicKey = publicKey, privateKey = privateKey)
        setKeyPairVerified(if (isKeyPairVerified) publicKey else null)
    }

    /**
     * Performs a sequence of checks to determine the [ContextState].
     * The first check to fail determines the returned state.
     * The checks are, in order: [isCloudAuthTokenInvalidOrExpired], [isKeyPairValid],
     * [isKeyPairVerified], and [isCertificateChainInvalidOrExpired].
     *
     * @param checkServerInvalidation Whether it should verify with the backend if the token has been invalidated (by performing a network request)
     * @return A [ContextState] representing the context state.
     */
    @JvmSynthetic
    internal suspend fun getContextState(checkServerInvalidation: Boolean): ContextState {
        if (isCloudAuthTokenInvalidOrExpired(checkServerInvalidation)) { return ContextState.CLOUD_TOKEN_IS_INVALID_OR_EXPIRED }
        if (!isKeyPairValid()) { return ContextState.KEY_PAIR_IS_INVALID }
        if (!isKeyPairVerified()) { return ContextState.KEY_PAIR_IS_NOT_VERIFIED }
        if (isCertificateChainInvalidOrExpired()) { return ContextState.CERTIFICATE_CHAIN_IS_INVALID_OR_EXPIRED }
        return ContextState.READY
    }

    @JvmSynthetic
    internal fun setSecureStorageImpl(secureStorage: SecureStorage) {
        this.secureStorage = secureStorage
    }

    /**
     * Clears all the values stored in secure storage.
     */
    @JvmSynthetic
    internal fun clearContext() {
        secureStorage.clear()
    }
}