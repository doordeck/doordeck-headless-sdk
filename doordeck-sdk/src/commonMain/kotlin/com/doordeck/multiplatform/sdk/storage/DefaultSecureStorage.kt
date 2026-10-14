package com.doordeck.multiplatform.sdk.storage

import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.logger.SdkLogger
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.storage.StorageKey.API_ENVIRONMENT_KEY
import com.doordeck.multiplatform.sdk.storage.StorageKey.CERTIFICATE_CHAIN_KEY
import com.doordeck.multiplatform.sdk.storage.StorageKey.CLOUD_AUTH_TOKEN_KEY
import com.doordeck.multiplatform.sdk.storage.StorageKey.CLOUD_REFRESH_TOKEN_KEY
import com.doordeck.multiplatform.sdk.storage.StorageKey.FUSION_AUTH_TOKEN_KEY
import com.doordeck.multiplatform.sdk.storage.StorageKey.FUSION_HOST_KEY
import com.doordeck.multiplatform.sdk.storage.StorageKey.PRIVATE_KEY_KEY
import com.doordeck.multiplatform.sdk.storage.StorageKey.PUBLIC_KEY_KEY
import com.doordeck.multiplatform.sdk.storage.StorageKey.STORAGE_VERSION_KEY
import com.doordeck.multiplatform.sdk.storage.StorageKey.USER_EMAIL_KEY
import com.doordeck.multiplatform.sdk.storage.StorageKey.USER_ID_KEY
import com.doordeck.multiplatform.sdk.storage.StorageKey.VERIFIED_KEY_PAIR_KEY
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
        storeValue(STORAGE_VERSION_KEY, version)
    }

    fun getStorageVersion(): Int? {
        return retrieveValue(STORAGE_VERSION_KEY)
    }

    override fun setApiEnvironment(apiEnvironment: ApiEnvironment) {
        storeValue(API_ENVIRONMENT_KEY, apiEnvironment.name)
    }

    override fun getApiEnvironment(): ApiEnvironment? {
        return retrieveValue<String>(API_ENVIRONMENT_KEY)?.let { ApiEnvironment.valueOf(it) }
    }

    override fun addCloudAuthToken(token: String) {
        storeValue(CLOUD_AUTH_TOKEN_KEY, token, true)
    }

    override fun getCloudAuthToken(): String? {
        return retrieveValue(CLOUD_AUTH_TOKEN_KEY, true)
    }

    override fun addCloudRefreshToken(token: String) {
        storeValue(CLOUD_REFRESH_TOKEN_KEY, token, true)
    }

    override fun getCloudRefreshToken(): String? {
        return retrieveValue(CLOUD_REFRESH_TOKEN_KEY, true)
    }

    override fun setFusionHost(host: String) {
        storeValue(FUSION_HOST_KEY, host)
    }

    override fun getFusionHost(): String? {
        return retrieveValue(FUSION_HOST_KEY)
    }

    override fun addFusionAuthToken(token: String) {
        storeValue(FUSION_AUTH_TOKEN_KEY, token, true)
    }

    override fun getFusionAuthToken(): String? {
        return retrieveValue(FUSION_AUTH_TOKEN_KEY, true)
    }

    override fun addPublicKey(publicKey: ByteArray) {
        storeValue(PUBLIC_KEY_KEY, publicKey.encodeByteArrayToBase64(), true)
    }

    override fun getPublicKey(): ByteArray? {
        return retrieveValue<String>(PUBLIC_KEY_KEY, true)?.decodeBase64ToByteArray()
    }

    override fun addPrivateKey(privateKey: ByteArray) {
        storeValue(PRIVATE_KEY_KEY, privateKey.encodeByteArrayToBase64(), true)
    }

    override fun getPrivateKey(): ByteArray? {
        return retrieveValue<String>(PRIVATE_KEY_KEY, true)?.decodeBase64ToByteArray()
    }

    override fun setKeyPairVerified(publicKey: ByteArray?) {
        storeValue(VERIFIED_KEY_PAIR_KEY, publicKey?.encodeByteArrayToBase64(), true)
    }

    override fun getKeyPairVerified(): ByteArray? {
        return retrieveValue<String>(VERIFIED_KEY_PAIR_KEY, true)?.decodeBase64ToByteArray()
    }

    override fun addUserId(userId: String) {
        storeValue(USER_ID_KEY, userId)
    }

    override fun getUserId(): String? {
        return retrieveValue(USER_ID_KEY)
    }

    override fun addUserEmail(email: String) {
        storeValue(USER_EMAIL_KEY, email)
    }

    override fun getUserEmail(): String? {
        return retrieveValue(USER_EMAIL_KEY)
    }

    override fun addCertificateChain(certificateChain: List<String>) {
        storeValue(CERTIFICATE_CHAIN_KEY, certificateChain.certificateChainToString(), true)
    }

    override fun getCertificateChain(): List<String>? {
        return retrieveValue<String>(CERTIFICATE_CHAIN_KEY, true)?.stringToCertificateChain()
    }

    override fun clear() {
        settings.clear()
        SdkLogger.d("Successfully cleared storage")
    }

    private fun migrate() {
        val storedVersion = settings.getIntOrNull(STORAGE_VERSION_KEY.name) ?: 0
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