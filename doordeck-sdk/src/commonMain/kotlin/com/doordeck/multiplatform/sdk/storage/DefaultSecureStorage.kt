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
        storeValue(StorageKey.STORAGE_VERSION_KEY, version)
    }

    fun getStorageVersion(): Int? {
        return retrieveValue(StorageKey.STORAGE_VERSION_KEY)
    }

    override fun setApiEnvironment(apiEnvironment: ApiEnvironment) {
        storeValue(StorageKey.API_ENVIRONMENT_KEY, apiEnvironment.name)
    }

    override fun getApiEnvironment(): ApiEnvironment? {
        return retrieveValue<String>(StorageKey.API_ENVIRONMENT_KEY)?.let { ApiEnvironment.valueOf(it) }
    }

    override fun addCloudAuthToken(token: String) {
        storeValue(StorageKey.CLOUD_AUTH_TOKEN_KEY, token, true)
    }

    override fun getCloudAuthToken(): String? {
        return retrieveValue(StorageKey.CLOUD_AUTH_TOKEN_KEY, true)
    }

    override fun addCloudRefreshToken(token: String) {
        storeValue(StorageKey.CLOUD_REFRESH_TOKEN_KEY, token, true)
    }

    override fun getCloudRefreshToken(): String? {
        return retrieveValue(StorageKey.CLOUD_REFRESH_TOKEN_KEY, true)
    }

    override fun setFusionHost(host: String) {
        storeValue(StorageKey.FUSION_HOST_KEY, host)
    }

    override fun getFusionHost(): String? {
        return retrieveValue(StorageKey.FUSION_HOST_KEY)
    }

    override fun addFusionAuthToken(token: String) {
        storeValue(StorageKey.FUSION_AUTH_TOKEN_KEY, token, true)
    }

    override fun getFusionAuthToken(): String? {
        return retrieveValue(StorageKey.FUSION_AUTH_TOKEN_KEY, true)
    }

    override fun addPublicKey(publicKey: ByteArray) {
        storeValue(StorageKey.PUBLIC_KEY_KEY, publicKey.encodeByteArrayToBase64(), true)
    }

    override fun getPublicKey(): ByteArray? {
        return retrieveValue<String>(StorageKey.PUBLIC_KEY_KEY, true)?.decodeBase64ToByteArray()
    }

    override fun addPrivateKey(privateKey: ByteArray) {
        storeValue(StorageKey.PRIVATE_KEY_KEY, privateKey.encodeByteArrayToBase64(), true)
    }

    override fun getPrivateKey(): ByteArray? {
        return retrieveValue<String>(StorageKey.PRIVATE_KEY_KEY, true)?.decodeBase64ToByteArray()
    }

    override fun setKeyPairVerified(publicKey: ByteArray?) {
        storeValue(StorageKey.VERIFIED_KEY_PAIR_KEY, publicKey?.encodeByteArrayToBase64(), true)
    }

    override fun getKeyPairVerified(): ByteArray? {
        return retrieveValue<String>(StorageKey.VERIFIED_KEY_PAIR_KEY, true)?.decodeBase64ToByteArray()
    }

    override fun addUserId(userId: String) {
        storeValue(StorageKey.USER_ID_KEY, userId)
    }

    override fun getUserId(): String? {
        return retrieveValue(StorageKey.USER_ID_KEY)
    }

    override fun addUserEmail(email: String) {
        storeValue(StorageKey.USER_EMAIL_KEY, email)
    }

    override fun getUserEmail(): String? {
        return retrieveValue(StorageKey.USER_EMAIL_KEY)
    }

    override fun addCertificateChain(certificateChain: List<String>) {
        storeValue(StorageKey.CERTIFICATE_CHAIN_KEY, certificateChain.certificateChainToString(), true)
    }

    override fun getCertificateChain(): List<String>? {
        return retrieveValue<String>(StorageKey.CERTIFICATE_CHAIN_KEY, true)?.stringToCertificateChain()
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

    private inline fun <reified T : Any> storeValue(key: StorageKey, value: T?, maskValue: Boolean = false) {
        if (value == null) {
            settings.remove(key.name)
            SdkLogger.d { "Removed stored value for key: $key" }
            return
        }
        when (value) {
            is String -> settings.putString(key.name, value)
            is Int -> settings.putInt(key.name, value)
            else -> throw SdkException("Unsupported type ${T::class} for key $key")
        }
        SdkLogger.d { "Stored value: ${if (maskValue) value.toString().mask() else value} for key: $key" }
    }

    private inline fun <reified T : Any> retrieveValue(key: StorageKey, maskValue: Boolean = false): T? {
        val value: Any? = when (T::class) {
            String::class -> settings.getStringOrNull(key.name)
            Int::class -> settings.getIntOrNull(key.name)
            else -> throw SdkException("Unsupported type ${T::class} for key $key")
        }
        SdkLogger.d { "Retrieved value: ${if (maskValue) value?.toString()?.mask() else value} for key: $key" }
        return value as T?
    }
}