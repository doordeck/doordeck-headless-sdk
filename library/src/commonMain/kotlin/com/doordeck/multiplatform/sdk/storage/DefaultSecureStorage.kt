package com.doordeck.multiplatform.sdk.storage

import com.doordeck.multiplatform.sdk.util.Crypto.certificateChainToString
import com.doordeck.multiplatform.sdk.util.Crypto.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Crypto.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.Crypto.stringToCertificateChain
import com.russhwolf.settings.Settings

class DefaultSecureStorage(private val settings: Settings) : SecureStorage {

    /**
     * Storage Keys
     */
    private val CLOUD_AUTH_TOKEN_KEY = "CLOUD_AUTH_TOKEN_KEY"
    private val FUSION_AUTH_TOKEN_KEY = "FUSION_AUTH_TOKEN_KEY"
    private val PRIVATE_KEY_KEY = "PRIVATE_KEY_KEY"
    private val USER_ID_KEY = "USER_ID_KEY"
    private val CERTIFICATE_CHAIN_KEY = "CERTIFICATE_CHAIN_KEY"

    override fun addCloudAuthToken(token: String) {
        return settings.putString(CLOUD_AUTH_TOKEN_KEY, token)
    }

    override fun getCloudAuthToken(): String? {
        return settings.getStringOrNull(CLOUD_AUTH_TOKEN_KEY)
    }

    override fun addFusionAuthToken(token: String) {
        return settings.putString(FUSION_AUTH_TOKEN_KEY, token)
    }

    override fun getFusionAuthToken(): String? {
        return settings.getStringOrNull(FUSION_AUTH_TOKEN_KEY)
    }

    override fun addPrivateKey(byteArray: ByteArray) {
        return settings.putString(PRIVATE_KEY_KEY, byteArray.encodeByteArrayToBase64())
    }

    override fun getPrivateKey(): ByteArray? {
        return settings.getStringOrNull(PRIVATE_KEY_KEY)?.decodeBase64ToByteArray()
    }

    override fun addUserId(userId: String) {
        settings.putString(USER_ID_KEY, userId)
    }

    override fun getUserId(): String? {
        return settings.getStringOrNull(USER_ID_KEY)
    }

    override fun addCertificateChain(certificateChain: Array<String>) {
        return settings.putString(CERTIFICATE_CHAIN_KEY, certificateChain.certificateChainToString())
    }

    override fun getCertificateChain(): Array<String>? {
        return settings.getStringOrNull(CERTIFICATE_CHAIN_KEY)?.stringToCertificateChain()
    }
}