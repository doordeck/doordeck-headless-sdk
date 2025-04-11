package com.doordeck.multiplatform.sdk.storage

import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.util.Utils.certificateChainToString
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.Utils.stringToCertificateChain
import com.russhwolf.settings.Settings

internal class DefaultSecureStorage(private val settings: Settings) : SecureStorage {

    /**
     * Storage Keys
     */
    private val API_ENVIRONMENT = "API_ENVIRONMENT"
    private val CLOUD_AUTH_TOKEN_KEY = "CLOUD_AUTH_TOKEN_KEY"
    private val CLOUD_REFRESH_TOKEN_KEY = "CLOUD_REFRESH_TOKEN_KEY"
    private val FUSION_HOST = "FUSION_HOST"
    private val FUSION_AUTH_TOKEN_KEY = "FUSION_AUTH_TOKEN_KEY"
    private val PUBLIC_KEY_KEY = "PUBLIC_KEY_KEY"
    private val PRIVATE_KEY_KEY = "PRIVATE_KEY_KEY"
    private val KEY_PAIR_VERIFIED = "KEY_PAIR_VERIFIED"
    private val USER_ID_KEY = "USER_ID_KEY"
    private val USER_EMAIL_KEY = "USER_EMAIL_KEY"
    private val CERTIFICATE_CHAIN_KEY = "CERTIFICATE_CHAIN_KEY"

    override fun setApiEnvironment(apiEnvironment: ApiEnvironment) {
        settings.putString(API_ENVIRONMENT, apiEnvironment.name)
    }

    override fun getApiEnvironment(): ApiEnvironment? {
        return settings.getStringOrNull(API_ENVIRONMENT)?.let { ApiEnvironment.valueOf(it) }
    }

    override fun addCloudAuthToken(token: String) {
        settings.putString(CLOUD_AUTH_TOKEN_KEY, token)
    }

    override fun getCloudAuthToken(): String? {
        return settings.getStringOrNull(CLOUD_AUTH_TOKEN_KEY)
    }

    override fun addCloudRefreshToken(token: String) {
        settings.putString(CLOUD_REFRESH_TOKEN_KEY, token)
    }

    override fun getCloudRefreshToken(): String? {
        return settings.getStringOrNull(CLOUD_REFRESH_TOKEN_KEY)
    }

    override fun setFusionHost(host: String) {
        settings.putString(FUSION_HOST, host)
    }

    override fun getFusionHost(): String? {
        return settings.getStringOrNull(FUSION_HOST)
    }

    override fun addFusionAuthToken(token: String) {
        settings.putString(FUSION_AUTH_TOKEN_KEY, token)
    }

    override fun getFusionAuthToken(): String? {
        return settings.getStringOrNull(FUSION_AUTH_TOKEN_KEY)
    }

    override fun addPublicKey(byteArray: ByteArray) {
        settings.putString(PUBLIC_KEY_KEY, byteArray.encodeByteArrayToBase64())
    }

    override fun getPublicKey(): ByteArray? {
        return settings.getStringOrNull(PUBLIC_KEY_KEY)?.decodeBase64ToByteArray()
    }

    override fun addPrivateKey(byteArray: ByteArray) {
        settings.putString(PRIVATE_KEY_KEY, byteArray.encodeByteArrayToBase64())
    }

    override fun getPrivateKey(): ByteArray? {
        return settings.getStringOrNull(PRIVATE_KEY_KEY)?.decodeBase64ToByteArray()
    }

    override fun setKeyPairVerified(verified: Boolean) {
        settings.putBoolean(KEY_PAIR_VERIFIED, verified)
    }

    override fun getKeyPairVerified(): Boolean? {
        return settings.getBooleanOrNull(KEY_PAIR_VERIFIED)
    }

    override fun addUserId(userId: String) {
        settings.putString(USER_ID_KEY, userId)
    }

    override fun getUserId(): String? {
        return settings.getStringOrNull(USER_ID_KEY)
    }

    override fun addUserEmail(email: String) {
        settings.putString(USER_EMAIL_KEY, email)
    }

    override fun getUserEmail(): String? {
        return settings.getStringOrNull(USER_EMAIL_KEY)
    }

    override fun addCertificateChain(certificateChain: List<String>) {
        settings.putString(CERTIFICATE_CHAIN_KEY, certificateChain.certificateChainToString())
    }

    override fun getCertificateChain(): List<String>? {
        return settings.getStringOrNull(CERTIFICATE_CHAIN_KEY)?.stringToCertificateChain()
    }

    override fun clear() {
        settings.clear()
    }
}