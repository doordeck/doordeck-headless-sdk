package com.doordeck.multiplatform.sdk.context

import com.doordeck.multiplatform.sdk.Constants.DEFAULT_FUSION_HOST
import com.doordeck.multiplatform.sdk.cache.CapabilityCache
import com.doordeck.multiplatform.sdk.clients.AccountClient
import com.doordeck.multiplatform.sdk.crypto.CryptoManager
import com.doordeck.multiplatform.sdk.logger.SdkLogger
import com.doordeck.multiplatform.sdk.model.common.ContextState
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.model.data.Crypto
import com.doordeck.multiplatform.sdk.storage.DefaultSecureStorage
import com.doordeck.multiplatform.sdk.storage.MemorySettings
import com.doordeck.multiplatform.sdk.storage.SecureStorage
import com.doordeck.multiplatform.sdk.util.JwtUtils.getJwtSubject
import com.doordeck.multiplatform.sdk.util.JwtUtils.isJwtTokenInvalidOrExpired
import com.doordeck.multiplatform.sdk.util.getCertificateUserId
import com.doordeck.multiplatform.sdk.util.KeyPairUtils
import kotlin.jvm.JvmSynthetic

internal object Context {

    private var secureStorage: SecureStorage = DefaultSecureStorage(MemorySettings())

    @JvmSynthetic
    internal fun setDebugLogging(enabled: Boolean) {
        SdkLogger.enableDebugLogging(enabled)
    }

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
     * Sets the cloud authentication token, the provided value will be automatically stored in secure storage.
     *
     * It can be changed anytime, although it is recommended to set it up through the `SdkConfig` builder.
     */
    @JvmSynthetic
    internal fun setCloudAuthToken(token: String) {
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
     * Checks whether the cloud authentication token is invalid (e.g., null, malformed) or
     * expired (considering a minimum lifetime of [com.doordeck.multiplatform.sdk.util.MIN_TOKEN_LIFETIME_DAYS]).
     *
     * @param checkServerInvalidation Whether it should verify with the backend if the token has been invalidated (by performing a network request)
     * @return true if the token is null, malformed, expired, or invalidated (when checkServerInvalidation is true). Otherwise, returns false.
     */
    @JvmSynthetic
    internal suspend fun isCloudAuthTokenInvalidOrExpired(checkServerInvalidation: Boolean): Boolean {
        val token = getCloudAuthToken() ?: return true
        if (token.isJwtTokenInvalidOrExpired()) {
            return true
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
     * Sets the cloud refresh token, the provided value will be automatically stored in secure storage.
     *
     * It can be changed anytime, although it is recommended to set it up through the `SdkConfig` builder.
     */
    @JvmSynthetic
    internal fun setCloudRefreshToken(token: String) {
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
     * Hands the context over to the user [authToken] was just issued to, discarding what the
     * previous one left behind when the two are not the same person.
     *
     * A device that has been through a verification once holds a key pair, the mark saying that
     * pair was verified, a certificate chain and a user id. None of that belongs to the next person
     * to sign in, and leaving it in place is what let a second user in with no verification at all:
     * [getContextState] asks whether a verified, unexpired chain exists, never whose it is.
     *
     * Only what describes the installation rather than the user is carried across. The tokens are
     * written by the caller the moment this returns, so clearing them here costs nothing.
     */
    @JvmSynthetic
    internal fun startSessionFor(email: String, authToken: String) {
        if (isSomebodyElses(email, authToken)) {
            val apiEnvironment = getApiEnvironment()
            val fusionHost = getFusionHost()
            reset()
            setApiEnvironment(apiEnvironment)
            setFusionHost(fusionHost)
        }
        setUserEmail(email)
    }

    /**
     * Whether what the context is holding belongs to somebody other than the user this token was
     * issued to.
     *
     * The certificate chain is the part that grants access, and it says whose it is: its first
     * certificate is issued by the user's master certificate, whose subject is that user's id,
     * and the token that just came back says which user the session is for. A chain issued to
     * anybody else is not ours — and neither is one we cannot read, since an unreadable chain is
     * no use to anybody. Asking the chain rather than the email also heals a device that is
     * already carrying the wrong one.
     *
     * The email answers for the rest of the material: registering leaves a verified key pair and
     * no chain at all, and then there is nothing to compare.
     */
    private fun isSomebodyElses(email: String, authToken: String): Boolean {
        if (getUserEmail() != email) {
            return true
        }
        val certificate = getCertificateChain()?.firstOrNull() ?: return false
        return certificate.getCertificateUserId() != authToken.getJwtSubject()
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

    /**
     * Refreshes the cloud auth tokens if they are no longer valid.
     *
     * The refresh is done by the Ktor auth plugin, which intercepts outgoing requests; this function
     * just issues a throwaway authenticated request to trigger it. Failures are ignored, so a
     * successful return does not guarantee valid tokens.
     *
     * Only meant to be called during SDK initialization.
     */
    @JvmSynthetic
    internal suspend fun attemptToRefreshAuthTokens() {
        if (getCloudAuthToken() != null && getCloudRefreshToken() != null) {
            runCatching {
                AccountClient.getUserDetailsRequest()
            }
        }
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