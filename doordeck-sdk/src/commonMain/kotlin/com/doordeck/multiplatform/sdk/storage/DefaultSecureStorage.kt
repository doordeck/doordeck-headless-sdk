package com.doordeck.multiplatform.sdk.storage

import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.logger.SdkLogger
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.storage.migrations.CURRENT_STORAGE_VERSION
import com.doordeck.multiplatform.sdk.storage.migrations.Migrations.migrations
import com.doordeck.multiplatform.sdk.util.Utils.certificateChainToString
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.Utils.stringToCertificateChain
import com.doordeck.multiplatform.sdk.util.mask
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
    private val keyPairVerifiedKey = "VERIFIED_KEY_PAIR_KEY"
    private val userIdKey = "USER_ID_KEY"
    private val userEmailKey = "USER_EMAIL_KEY"
    private val certificateChainKey = "CERTIFICATE_CHAIN_KEY"
    private val storageVersionKey = "STORAGE_VERSION_KEY"

    init {
        migrate()
    }

    fun setStorageVersion(version: Int) {
        storeIntValue(storageVersionKey, version)
    }

    fun getStorageVersion(): Int? {
        return retrieveIntValue(storageVersionKey)
    }

    override fun setApiEnvironment(apiEnvironment: ApiEnvironment) {
        storeStringValue(apiEnvironmentKey, apiEnvironment.name)
    }

    override fun getApiEnvironment(): ApiEnvironment? {
        return retrieveStringValue(apiEnvironmentKey)?.let { ApiEnvironment.valueOf(it) }
    }

    override fun addCloudAuthToken(token: String) {
        storeStringValue(cloudAuthTokenKey, token, true)
    }

    override fun getCloudAuthToken(): String? {
        return retrieveStringValue(cloudAuthTokenKey, true)
    }

    override fun addCloudRefreshToken(token: String) {
        storeStringValue(cloudRefreshTokenKey, token, true)
    }

    override fun getCloudRefreshToken(): String? {
        return retrieveStringValue(cloudRefreshTokenKey, true)
    }

    override fun setFusionHost(host: String) {
        storeStringValue(fusionHostKey, host)
    }

    override fun getFusionHost(): String? {
        return retrieveStringValue(fusionHostKey)
    }

    override fun addFusionAuthToken(token: String) {
        storeStringValue(fusionAuthTokenKey, token, true)
    }

    override fun getFusionAuthToken(): String? {
        return retrieveStringValue(fusionAuthTokenKey, true)
    }

    override fun addPublicKey(publicKey: ByteArray) {
        storeStringValue(publicKeyKey, publicKey.encodeByteArrayToBase64(), true)
    }

    override fun getPublicKey(): ByteArray? {
        return retrieveStringValue(publicKeyKey, true)?.decodeBase64ToByteArray()
    }

    override fun addPrivateKey(privateKey: ByteArray) {
        storeStringValue(privateKeyKey, privateKey.encodeByteArrayToBase64(), true)
    }

    override fun getPrivateKey(): ByteArray? {
        return retrieveStringValue(privateKeyKey, true)?.decodeBase64ToByteArray()
    }

    override fun setKeyPairVerified(publicKey: ByteArray?) {
        storeStringValue(keyPairVerifiedKey, publicKey?.encodeByteArrayToBase64(), true)
    }

    override fun getKeyPairVerified(): ByteArray? {
        return retrieveStringValue(keyPairVerifiedKey, true)?.decodeBase64ToByteArray()
    }

    override fun addUserId(userId: String) {
        storeStringValue(userIdKey, userId)
    }

    override fun getUserId(): String? {
        return retrieveStringValue(userIdKey)
    }

    override fun addUserEmail(email: String) {
        storeStringValue(userEmailKey, email)
    }

    override fun getUserEmail(): String? {
        return retrieveStringValue(userEmailKey)
    }

    override fun addCertificateChain(certificateChain: List<String>) {
        storeStringValue(certificateChainKey, certificateChain.certificateChainToString(), true)
    }

    override fun getCertificateChain(): List<String>? {
        return retrieveStringValue(certificateChainKey, true)?.stringToCertificateChain()
    }

    override fun clear() {
        settings.clear()
        SdkLogger.d("Successfully cleared storage")
    }

    private fun migrate() {
        val storedVersion = settings.getIntOrNull(storageVersionKey) ?: 0
        if (storedVersion < CURRENT_STORAGE_VERSION) {
            try {
                migrations
                    .sortedBy { it.fromVersion }
                    .filter { it.fromVersion >= storedVersion }
                    .forEach { migration ->
                        migration.migrate(settings)
                        setStorageVersion(migration.toVersion)
                    }
            } catch (exception: Exception) {
                throw SdkException("Failed to perform storage migrations", exception)
            }
        }
    }

    private fun storeStringValue(key: String, value: String?, maskValue: Boolean = false) {
        if (value == null) {
            settings.remove(key)
            SdkLogger.d { "Removed key $key" }
        } else {
            settings.putString(key, value)
            SdkLogger.d("Stored value: ${if (maskValue) value.mask() else value} for key: $key")
        }
    }

    private fun retrieveStringValue(key: String, maskValue: Boolean = false): String? {
        val value = settings.getStringOrNull(key)
        SdkLogger.d("Retrieved value: ${if (maskValue) value?.mask() else value} for key: $key")
        return value
    }

    @Suppress("SameParameterValue")
    private fun storeIntValue(key: String, value: Int) {
        settings.putInt(key, value)
        SdkLogger.d("Stored value: $value for key: $key")
    }

    @Suppress("SameParameterValue")
    private fun retrieveIntValue(key: String): Int? {
        val value = settings.getIntOrNull(key)
        SdkLogger.d("Retrieved value: $value for key: $key")
        return value
    }
}