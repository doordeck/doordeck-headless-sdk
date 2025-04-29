package com.doordeck.multiplatform.sdk.storage

import com.doordeck.multiplatform.sdk.ApplicationContext
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.cstr
import kotlinx.cinterop.invoke
import kotlinx.cinterop.memScoped

internal actual fun createSecureStorage(applicationContext: ApplicationContext?): SecureStorage {
    return DefaultSecureStorage(MemorySettings())
}

internal typealias setStringCp = CPointer<CFunction<(CPointer<ByteVar>) -> Unit>>
internal typealias getStringCp = CPointer<CFunction<() -> CPointer<ByteVar>?>>

internal fun setStringCp.invokeCallback(string: String) = memScoped {
    val cString = string.cstr.ptr
    invoke(cString)
}

fun createMingwSecureStorage(
    setApiEnvironmentCp: setStringCp,
    getApiEnvironmentCp: getStringCp,
    addCloudAuthTokenCp: setStringCp,
    getCloudAuthTokenCp: getStringCp,
    addCloudRefreshTokenCp: setStringCp,
    getCloudRefreshTokenCp: getStringCp,
    setFusionHostCp: setStringCp,
    getFusionHostCp: getStringCp,
    addFusionAuthTokenCp: setStringCp,
    getFusionAuthTokenCp: getStringCp,
    addUserIdCp: setStringCp,
    getUserIdCp: getStringCp,
    addUserEmailCp: setStringCp,
    getUserEmailCp: getStringCp,
    clearCp: CPointer<CFunction<() -> Unit>>
): SecureStorage {
    return CallbackSecureStorage(
        setApiEnvironmentCp = setApiEnvironmentCp,
        getApiEnvironmentCp = getApiEnvironmentCp,
        addCloudAuthTokenCp = addCloudAuthTokenCp,
        getCloudAuthTokenCp = getCloudAuthTokenCp,
        addCloudRefreshTokenCp = addCloudRefreshTokenCp,
        getCloudRefreshTokenCp = getCloudRefreshTokenCp,
        setFusionHostCp = setFusionHostCp,
        getFusionHostCp = getFusionHostCp,
        addFusionAuthTokenCp = addFusionAuthTokenCp,
        getFusionAuthTokenCp = getFusionAuthTokenCp,
        addUserIdCp = addUserIdCp,
        getUserIdCp = getUserIdCp,
        addUserEmailCp = addUserEmailCp,
        getUserEmailCp = getUserEmailCp,
        clearCp = clearCp
    )
}

class CallbackSecureStorage(
    private val setApiEnvironmentCp: setStringCp,
    private val getApiEnvironmentCp: getStringCp,
    private val addCloudAuthTokenCp: setStringCp,
    private val getCloudAuthTokenCp: getStringCp,
    private val addCloudRefreshTokenCp: setStringCp,
    private val getCloudRefreshTokenCp: getStringCp,
    private val setFusionHostCp: setStringCp,
    private val getFusionHostCp: getStringCp,
    private val addFusionAuthTokenCp: setStringCp,
    private val getFusionAuthTokenCp: getStringCp,
    private val addUserIdCp: setStringCp,
    private val getUserIdCp: getStringCp,
    private val addUserEmailCp: setStringCp,
    private val getUserEmailCp: getStringCp,
    private val clearCp: CPointer<CFunction<() -> Unit>>
) : SecureStorage {

    override fun setApiEnvironment(apiEnvironment: ApiEnvironment) {
        setApiEnvironmentCp.invokeCallback(apiEnvironment.name)
    }

    override fun getApiEnvironment(): ApiEnvironment? {
        val apiEnvironment = getApiEnvironmentCp()?.toString()
        return apiEnvironment?.let { ApiEnvironment.valueOf(it)  }
    }

    override fun addCloudAuthToken(token: String) {
        addCloudAuthTokenCp.invokeCallback(token)
    }

    override fun getCloudAuthToken(): String? {
        return getCloudAuthTokenCp()?.toString()
    }

    override fun addCloudRefreshToken(token: String) {
        addCloudRefreshTokenCp.invokeCallback(token)
    }

    override fun getCloudRefreshToken(): String? {
        return getCloudRefreshTokenCp()?.toString()
    }

    override fun setFusionHost(host: String) {
        setFusionHostCp.invokeCallback(host)
    }

    override fun getFusionHost(): String? {
        return getFusionHostCp()?.toString()
    }

    override fun addFusionAuthToken(token: String) {
        addFusionAuthTokenCp.invokeCallback(token)
    }

    override fun getFusionAuthToken(): String? {
        return getFusionAuthTokenCp()?.toString()
    }

    override fun addPublicKey(byteArray: ByteArray) {
    }

    override fun getPublicKey(): ByteArray? {
        return null
    }

    override fun addPrivateKey(byteArray: ByteArray) {
    }

    override fun getPrivateKey(): ByteArray? {
        return null
    }

    override fun setKeyPairVerified(verified: Boolean) {
    }

    override fun getKeyPairVerified(): Boolean? {
        return null
    }

    override fun addUserId(userId: String) {
        addUserIdCp.invokeCallback(userId)
    }

    override fun getUserId(): String? {
        return getUserIdCp()?.toString()
    }

    override fun addUserEmail(email: String) {
        addUserEmailCp.invokeCallback(email)
    }

    override fun getUserEmail(): String? {
        return getUserEmailCp()?.toString()
    }

    override fun addCertificateChain(certificateChain: List<String>) {

    }

    override fun getCertificateChain(): List<String>? {
        return null
    }

    override fun clear() {
       clearCp.invoke()
    }
}