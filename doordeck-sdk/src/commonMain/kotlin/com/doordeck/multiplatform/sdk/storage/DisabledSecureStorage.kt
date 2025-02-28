package com.doordeck.multiplatform.sdk.storage

/**
 * Disabled secure storage implementation
 */
class DisabledSecureStorage : SecureStorage {

    override fun addCloudAuthToken(token: String) { }

    override fun getCloudAuthToken(): String? { return null }

    override fun addCloudRefreshToken(token: String) { }

    override fun getCloudRefreshToken(): String? { return null }

    override fun addFusionAuthToken(token: String) { }

    override fun getFusionAuthToken(): String? { return null }

    override fun addPublicKey(byteArray: ByteArray) { }

    override fun getPublicKey(): ByteArray? { return null }

    override fun addPrivateKey(byteArray: ByteArray) { }

    override fun getPrivateKey(): ByteArray? { return null }

    override fun addUserId(userId: String) { }

    override fun getUserId(): String? { return null }

    override fun addUserEmail(email: String) { }

    override fun getUserEmail(): String? { return null }

    override fun addCertificateChain(certificateChain: List<String>) { }

    override fun getCertificateChain(): List<String>? { return null }

    override fun clear() { }
}