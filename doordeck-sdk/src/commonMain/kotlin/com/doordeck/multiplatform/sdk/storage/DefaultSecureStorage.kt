package com.doordeck.multiplatform.sdk.storage

import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.logger.SdkLogger
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
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

    init {
        migrate()
    }

    fun setStorageVersion(version: Int) {
        storeIntValue(StorageKey.STORAGE_VERSION_KEY, version)
    }

    fun getStorageVersion(): Int? {
        return retrieveIntValue(StorageKey.STORAGE_VERSION_KEY)
    }

    override fun setApiEnvironment(apiEnvironment: ApiEnvironment) {
        storeStringValue(StorageKey.API_ENVIRONMENT_KEY, apiEnvironment.name)
    }

    override fun getApiEnvironment(): ApiEnvironment? {
        return retrieveStringValue(StorageKey.API_ENVIRONMENT_KEY)?.let { ApiEnvironment.valueOf(it) }
    }

    override fun addCloudAuthToken(token: String) {
        storeStringValue(StorageKey.CLOUD_AUTH_TOKEN_KEY, token, true)
    }

    override fun getCloudAuthToken(): String? {
        return retrieveStringValue(StorageKey.CLOUD_AUTH_TOKEN_KEY, true)
    }

    override fun addCloudRefreshToken(token: String) {
        storeStringValue(StorageKey.CLOUD_REFRESH_TOKEN_KEY, token, true)
    }

    override fun getCloudRefreshToken(): String? {
        return retrieveStringValue(StorageKey.CLOUD_REFRESH_TOKEN_KEY, true)
    }

    override fun setFusionHost(host: String) {
        storeStringValue(StorageKey.FUSION_HOST_KEY, host)
    }

    override fun getFusionHost(): String? {
        return retrieveStringValue(StorageKey.FUSION_HOST_KEY)
    }

    override fun addFusionAuthToken(token: String) {
        storeStringValue(StorageKey.FUSION_AUTH_TOKEN_KEY, token, true)
    }

    override fun getFusionAuthToken(): String? {
        return retrieveStringValue(StorageKey.FUSION_AUTH_TOKEN_KEY, true)
    }

    override fun addPublicKey(publicKey: ByteArray) {
        storeStringValue(StorageKey.PUBLIC_KEY_KEY, publicKey.encodeByteArrayToBase64(), true)
    }

    override fun getPublicKey(): ByteArray? {
        return retrieveStringValue(StorageKey.PUBLIC_KEY_KEY, true)?.decodeBase64ToByteArray()
    }

    override fun addPrivateKey(privateKey: ByteArray) {
        storeStringValue(StorageKey.PRIVATE_KEY_KEY, privateKey.encodeByteArrayToBase64(), true)
    }

    override fun getPrivateKey(): ByteArray? {
        return retrieveStringValue(StorageKey.PRIVATE_KEY_KEY, true)?.decodeBase64ToByteArray()
    }

    override fun setKeyPairVerified(publicKey: ByteArray?) {
        storeStringValue(StorageKey.VERIFIED_KEY_PAIR_KEY, publicKey?.encodeByteArrayToBase64(), true)
    }

    override fun getKeyPairVerified(): ByteArray? {
        return retrieveStringValue(StorageKey.VERIFIED_KEY_PAIR_KEY, true)?.decodeBase64ToByteArray()
    }

    override fun addUserId(userId: String) {
        storeStringValue(StorageKey.USER_ID_KEY, userId)
    }

    override fun getUserId(): String? {
        return retrieveStringValue(StorageKey.USER_ID_KEY)
    }

    override fun addUserEmail(email: String) {
        storeStringValue(StorageKey.USER_EMAIL_KEY, email)
    }

    override fun getUserEmail(): String? {
        return retrieveStringValue(StorageKey.USER_EMAIL_KEY)
    }

    override fun addCertificateChain(certificateChain: List<String>) {
        storeStringValue(StorageKey.CERTIFICATE_CHAIN_KEY, certificateChain.certificateChainToString(), true)
    }

    override fun getCertificateChain(): List<String>? {
        return retrieveStringValue(StorageKey.CERTIFICATE_CHAIN_KEY, true)?.stringToCertificateChain()
    }

    override fun clear() {
        settings.clear()
        SdkLogger.d("Successfully cleared storage")
    }

    private fun migrate() {
        val storedVersion = settings.getIntOrNull(StorageKey.STORAGE_VERSION_KEY.name) ?: 0
        val maxStorageVersion = migrations.maxOf { it.toVersion }
        if (storedVersion < maxStorageVersion) {
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

    private fun storeStringValue(key: StorageKey, value: String?, maskValue: Boolean = false) {
        if (value == null) {
            settings.remove(key.name)
            SdkLogger.d { "Removed key $key" }
        } else {
            settings.putString(key.name, value)
            SdkLogger.d("Stored value: ${if (maskValue) value.mask() else value} for key: $key")
        }
    }

    private fun retrieveStringValue(key: StorageKey, maskValue: Boolean = false): String? {
        val value = settings.getStringOrNull(key.name)
        SdkLogger.d("Retrieved value: ${if (maskValue) value?.mask() else value} for key: $key")
        return value
    }

    @Suppress("SameParameterValue")
    private fun storeIntValue(key: StorageKey, value: Int) {
        settings.putInt(key.name, value)
        SdkLogger.d("Stored value: $value for key: $key")
    }

    @Suppress("SameParameterValue")
    private fun retrieveIntValue(key: StorageKey): Int? {
        val value = settings.getIntOrNull(key.name)
        SdkLogger.d("Retrieved value: $value for key: $key")
        return value
    }
}