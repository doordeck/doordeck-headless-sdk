package com.doordeck.multiplatform.sdk.storage

import co.touchlab.kermit.Logger
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.util.Utils.certificateChainToString
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.Utils.stringToCertificateChain
import com.russhwolf.settings.Settings

internal class DefaultSecureStorage(
    private val settings: Settings
) : SecureStorage {

    /**
     * Storage Keys
     */
    private val apiEnvironmentKey = "API_ENVIRONMENT_KEY"
    private val cloudAuthTokenKey = "CLOUD_AUTH_TOKEN_KEY"
    private val cloudRefreshTokenKey = "CLOUD_REFRESH_TOKEN_KEY"
    private val fusionHostKey = "FUSION_HOST_KEY"
    private val fusionAuthTokenKey = "FUSION_AUTH_TOKEN_KEY"
    private val publicKeyKey = "PUBLIC_KEY_KEY"
    private val privateKeyKey = "PRIVATE_KEY_KEY"
    private val keyPairVerifiedKey = "KEY_PAIR_VERIFIED_KEY"
    private val userIdKey = "USER_ID_KEY"
    private val userEmailKey = "USER_EMAIL_KEY"
    private val certificateChainKey = "CERTIFICATE_CHAIN_KEY"

    override fun setApiEnvironment(apiEnvironment: ApiEnvironment) {
        storeValue(apiEnvironmentKey, apiEnvironment.name)
    }

    override fun getApiEnvironment(): ApiEnvironment? {
        return retrieveValue<String?>(apiEnvironmentKey)?.let { ApiEnvironment.valueOf(it) }
    }

    override fun addCloudAuthToken(token: String) {
        storeValue(cloudAuthTokenKey, token)
    }

    override fun getCloudAuthToken(): String? {
        return retrieveValue(cloudAuthTokenKey)
    }

    override fun addCloudRefreshToken(token: String) {
        storeValue(cloudRefreshTokenKey, token)
    }

    override fun getCloudRefreshToken(): String? {
        return retrieveValue(cloudRefreshTokenKey)
    }

    override fun setFusionHost(host: String) {
        storeValue(fusionHostKey, host)
    }

    override fun getFusionHost(): String? {
        return retrieveValue(fusionHostKey)
    }

    override fun addFusionAuthToken(token: String) {
        storeValue(fusionAuthTokenKey, token)
    }

    override fun getFusionAuthToken(): String? {
        return retrieveValue(fusionAuthTokenKey)
    }

    override fun addPublicKey(byteArray: ByteArray) {
        storeValue(publicKeyKey, byteArray.encodeByteArrayToBase64())
    }

    override fun getPublicKey(): ByteArray? {
        return retrieveValue<String?>(publicKeyKey)?.decodeBase64ToByteArray()
    }

    override fun addPrivateKey(byteArray: ByteArray) {
        storeValue(privateKeyKey, byteArray.encodeByteArrayToBase64())
    }

    override fun getPrivateKey(): ByteArray? {
        return retrieveValue<String?>(privateKeyKey)?.decodeBase64ToByteArray()
    }

    override fun setKeyPairVerified(verified: Boolean) {
        storeValue(keyPairVerifiedKey, verified)
    }

    override fun getKeyPairVerified(): Boolean? {
        return retrieveValue<Boolean?>(keyPairVerifiedKey)
    }

    override fun addUserId(userId: String) {
        storeValue(userIdKey, userId)
    }

    override fun getUserId(): String? {
        return retrieveValue(userIdKey)
    }

    override fun addUserEmail(email: String) {
        storeValue(userEmailKey, email)
    }

    override fun getUserEmail(): String? {
        return retrieveValue(userEmailKey)
    }

    override fun addCertificateChain(certificateChain: List<String>) {
        storeValue(certificateChainKey, certificateChain.certificateChainToString())
    }

    override fun getCertificateChain(): List<String>? {
        return retrieveValue<String?>(certificateChainKey)?.stringToCertificateChain()
    }

    override fun clear() {
        settings.clear()
        Logger.d("Successfully cleared storage")
    }

    private fun storeValue(key: String, value: Any) {
        when (value) {
            is String -> settings.putString(key, value)
            is Boolean -> settings.putBoolean(key, value)
            else -> {
                Logger.e("Unhandled value type for key: $key")
                return
            }
        }
        Logger.d("Successfully stored value: $value for key: $key")
    }

    private inline fun <reified T> retrieveValue(key: String): T? {
        val value = when (T::class) {
            String::class -> settings.getStringOrNull(key)
            Boolean::class -> settings.getBooleanOrNull(key)
            else -> {
                Logger.e("Unhandled value type for key: $key")
                return null
            }
        }
        Logger.d("Successfully retrieved value: $value for key: $key")
        return value as T
    }
}