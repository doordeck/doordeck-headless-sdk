package com.doordeck.multiplatform.sdk.storage

import com.doordeck.multiplatform.sdk.ApplicationContext
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.util.Utils.certificateChainToString
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.Utils.stringToCertificateChain
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.cstr
import kotlinx.cinterop.invoke
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.toKString

internal actual fun createSecureStorage(applicationContext: ApplicationContext?): SecureStorage {
    return DefaultSecureStorage(MemorySettings())
}

internal typealias setStringCallback = CPointer<CFunction<(CPointer<ByteVar>) -> Unit>>
internal typealias getStringCallback = CPointer<CFunction<() -> CPointer<ByteVar>?>>
internal typealias emptyCallback = CPointer<CFunction<() -> Unit>>

internal fun setStringCallback.invokeStringCallback(string: String) = memScoped {
    val cString = string.cstr.ptr
    invoke(cString)
}

@CName("createMingwSecureStorage")
fun createMingwSecureStorage(
    setStorageVersionCp: setStringCallback,
    getStringCallbackCp: getStringCallback,
    setApiEnvironmentCp: setStringCallback,
    getApiEnvironmentCp: getStringCallback,
    addCloudAuthTokenCp: setStringCallback,
    getCloudAuthTokenCp: getStringCallback,
    addCloudRefreshTokenCp: setStringCallback,
    getCloudRefreshTokenCp: getStringCallback,
    setFusionHostCp: setStringCallback,
    getFusionHostCp: getStringCallback,
    addFusionAuthTokenCp: setStringCallback,
    getFusionAuthTokenCp: getStringCallback,
    addPublicKeyCp: setStringCallback,
    getPublicKeyCp: getStringCallback,
    addPrivateKeyCp: setStringCallback,
    getPrivateKeyCp: getStringCallback,
    setKeyPairVerifiedCp: setStringCallback,
    getKeyPairVerifiedCp: getStringCallback,
    removeVerifiedKeyPairCp: emptyCallback,
    addUserIdCp: setStringCallback,
    getUserIdCp: getStringCallback,
    addUserEmailCp: setStringCallback,
    getUserEmailCp: getStringCallback,
    addCertificateChainCp: setStringCallback,
    getCertificateChainCp: getStringCallback,
    clearCp: emptyCallback,
    migrateCp: emptyCallback
): SecureStorage {
    return MingwSecureStorage(
        setStorageVersionCp = setStorageVersionCp,
        getStorageVersionCp = getStringCallbackCp,
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
        addPublicKeyCp = addPublicKeyCp,
        getPublicKeyCp = getPublicKeyCp,
        addPrivateKeyCp = addPrivateKeyCp,
        getPrivateKeyCp = getPrivateKeyCp,
        setKeyPairVerifiedCp = setKeyPairVerifiedCp,
        getKeyPairVerifiedCp = getKeyPairVerifiedCp,
        removeVerifiedKeyPairCp = removeVerifiedKeyPairCp,
        addUserIdCp = addUserIdCp,
        getUserIdCp = getUserIdCp,
        addUserEmailCp = addUserEmailCp,
        getUserEmailCp = getUserEmailCp,
        addCertificateChainCp = addCertificateChainCp,
        getCertificateChainCp = getCertificateChainCp,
        clearCp = clearCp,
        migrateCp = migrateCp
    )
}

class MingwSecureStorage(
    private val setStorageVersionCp: setStringCallback,
    private val getStorageVersionCp: getStringCallback,
    private val setApiEnvironmentCp: setStringCallback,
    private val getApiEnvironmentCp: getStringCallback,
    private val addCloudAuthTokenCp: setStringCallback,
    private val getCloudAuthTokenCp: getStringCallback,
    private val addCloudRefreshTokenCp: setStringCallback,
    private val getCloudRefreshTokenCp: getStringCallback,
    private val setFusionHostCp: setStringCallback,
    private val getFusionHostCp: getStringCallback,
    private val addFusionAuthTokenCp: setStringCallback,
    private val getFusionAuthTokenCp: getStringCallback,
    private val addPublicKeyCp: setStringCallback,
    private val getPublicKeyCp: getStringCallback,
    private val addPrivateKeyCp: setStringCallback,
    private val getPrivateKeyCp: getStringCallback,
    private val setKeyPairVerifiedCp: setStringCallback,
    private val getKeyPairVerifiedCp: getStringCallback,
    private val removeVerifiedKeyPairCp: emptyCallback,
    private val addUserIdCp: setStringCallback,
    private val getUserIdCp: getStringCallback,
    private val addUserEmailCp: setStringCallback,
    private val getUserEmailCp: getStringCallback,
    private val addCertificateChainCp: setStringCallback,
    private val getCertificateChainCp: getStringCallback,
    private val clearCp: emptyCallback,
    private val migrateCp: emptyCallback
) : SecureStorage {
    override fun setStorageVersion(version: Int) {
        setStorageVersionCp.invokeStringCallback(version.toString())
    }

    override fun getStorageVersion(): Int? {
        return getStorageVersionCp()?.toKString()?.toInt()
    }

    override fun setApiEnvironment(apiEnvironment: ApiEnvironment) {
        setApiEnvironmentCp.invokeStringCallback(apiEnvironment.name)
    }

    override fun getApiEnvironment(): ApiEnvironment? {
        return getApiEnvironmentCp()?.toKString()?.let {
            ApiEnvironment.valueOf(it)
        }
    }

    override fun addCloudAuthToken(token: String) {
        addCloudAuthTokenCp.invokeStringCallback(token)
    }

    override fun getCloudAuthToken(): String? {
        return getCloudAuthTokenCp()?.toKString()
    }

    override fun addCloudRefreshToken(token: String) {
        addCloudRefreshTokenCp.invokeStringCallback(token)
    }

    override fun getCloudRefreshToken(): String? {
        return getCloudRefreshTokenCp()?.toKString()
    }

    override fun setFusionHost(host: String) {
        setFusionHostCp.invokeStringCallback(host)
    }

    override fun getFusionHost(): String? {
        return getFusionHostCp()?.toKString()
    }

    override fun addFusionAuthToken(token: String) {
        addFusionAuthTokenCp.invokeStringCallback(token)
    }

    override fun getFusionAuthToken(): String? {
        return getFusionAuthTokenCp()?.toKString()
    }

    override fun addPublicKey(publicKey: ByteArray) {
        addPublicKeyCp.invokeStringCallback(publicKey.encodeByteArrayToBase64())
    }

    override fun getPublicKey(): ByteArray? {
        return getPublicKeyCp()?.toKString()?.decodeBase64ToByteArray()
    }

    override fun addPrivateKey(privateKey: ByteArray) {
        addPrivateKeyCp.invokeStringCallback(privateKey.encodeByteArrayToBase64())
    }

    override fun getPrivateKey(): ByteArray? {
        return getPrivateKeyCp()?.toKString()?.decodeBase64ToByteArray()
    }

    override fun setKeyPairVerified(publicKey: ByteArray) {
        setKeyPairVerifiedCp.invokeStringCallback(publicKey.encodeByteArrayToBase64())
    }

    override fun getKeyPairVerified(): ByteArray? {
        return getKeyPairVerifiedCp()?.toKString()?.decodeBase64ToByteArray()
    }

    override fun removeVerifiedKeyPair() {
        return removeVerifiedKeyPairCp.invoke()
    }

    override fun addUserId(userId: String) {
        addUserIdCp.invokeStringCallback(userId)
    }

    override fun getUserId(): String? {
        return getUserIdCp()?.toKString()
    }

    override fun addUserEmail(email: String) {
        addUserEmailCp.invokeStringCallback(email)
    }

    override fun getUserEmail(): String? {
        return getUserEmailCp()?.toKString()
    }

    override fun addCertificateChain(certificateChain: List<String>) {
        addCertificateChainCp.invokeStringCallback(certificateChain.certificateChainToString())
    }

    override fun getCertificateChain(): List<String>? {
        return getCertificateChainCp()?.toKString()?.stringToCertificateChain()
    }

    override fun clear() {
       clearCp.invoke()
    }

    override fun migrate() {
        migrateCp.invoke()
    }
}