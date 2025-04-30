package com.doordeck.multiplatform.sdk.storage

import com.doordeck.multiplatform.sdk.ApplicationContext
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.util.Utils.certificateChainToString
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.Utils.stringToCertificateChain
import kotlinx.cinterop.BooleanVar
import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.alloc
import kotlinx.cinterop.cstr
import kotlinx.cinterop.invoke
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.pointed
import kotlinx.cinterop.ptr
import kotlinx.cinterop.value

internal actual fun createSecureStorage(applicationContext: ApplicationContext?): SecureStorage {
    return DefaultSecureStorage(MemorySettings())
}

internal typealias setStringCallback = CPointer<CFunction<(CPointer<ByteVar>) -> Unit>>
internal typealias getStringCallback = CPointer<CFunction<() -> CPointer<ByteVar>?>>
internal typealias setBooleanCallback = CPointer<CFunction<(CPointer<BooleanVar>) -> Unit>>
internal typealias getBooleanCallback = CPointer<CFunction<() -> CPointer<BooleanVar>?>>
internal typealias emptyCallback = CPointer<CFunction<() -> Unit>>

internal fun setStringCallback.invokeStringCallback(string: String) = memScoped {
    val cString = string.cstr.ptr
    invoke(cString)
}

internal fun setBooleanCallback.invokeBooleanCallback(boolean: Boolean) = memScoped {
    val booleanVar = alloc<BooleanVar>().apply {
        value = boolean
    }
    invoke(booleanVar.ptr)
}

fun createMingwSecureStorage(
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
    setKeyPairVerifiedCp: setBooleanCallback,
    getKeyPairVerifiedCp: getBooleanCallback,
    addUserIdCp: setStringCallback,
    getUserIdCp: getStringCallback,
    addUserEmailCp: setStringCallback,
    getUserEmailCp: getStringCallback,
    addCertificateChainCp: setStringCallback,
    getCertificateChainCp: getStringCallback,
    clearCp: emptyCallback
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
        addPublicKeyCp = addPublicKeyCp,
        getPublicKeyCp = getPublicKeyCp,
        addPrivateKeyCp = addPrivateKeyCp,
        getPrivateKeyCp = getPrivateKeyCp,
        setKeyPairVerifiedCp = setKeyPairVerifiedCp,
        getKeyPairVerifiedCp = getKeyPairVerifiedCp,
        addUserIdCp = addUserIdCp,
        getUserIdCp = getUserIdCp,
        addUserEmailCp = addUserEmailCp,
        getUserEmailCp = getUserEmailCp,
        addCertificateChainCp = addCertificateChainCp,
        getCertificateChainCp = getCertificateChainCp,
        clearCp = clearCp
    )
}

class CallbackSecureStorage(
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
    private val setKeyPairVerifiedCp: setBooleanCallback,
    private val getKeyPairVerifiedCp: getBooleanCallback,
    private val addUserIdCp: setStringCallback,
    private val getUserIdCp: getStringCallback,
    private val addUserEmailCp: setStringCallback,
    private val getUserEmailCp: getStringCallback,
    private val addCertificateChainCp: setStringCallback,
    private val getCertificateChainCp: getStringCallback,
    private val clearCp: emptyCallback
) : SecureStorage {

    override fun setApiEnvironment(apiEnvironment: ApiEnvironment) {
        setApiEnvironmentCp.invokeStringCallback(apiEnvironment.name)
    }

    override fun getApiEnvironment(): ApiEnvironment? {
        return getApiEnvironmentCp()?.toString()?.let {
            ApiEnvironment.valueOf(it)
        }
    }

    override fun addCloudAuthToken(token: String) {
        addCloudAuthTokenCp.invokeStringCallback(token)
    }

    override fun getCloudAuthToken(): String? {
        return getCloudAuthTokenCp()?.toString()
    }

    override fun addCloudRefreshToken(token: String) {
        addCloudRefreshTokenCp.invokeStringCallback(token)
    }

    override fun getCloudRefreshToken(): String? {
        return getCloudRefreshTokenCp()?.toString()
    }

    override fun setFusionHost(host: String) {
        setFusionHostCp.invokeStringCallback(host)
    }

    override fun getFusionHost(): String? {
        return getFusionHostCp()?.toString()
    }

    override fun addFusionAuthToken(token: String) {
        addFusionAuthTokenCp.invokeStringCallback(token)
    }

    override fun getFusionAuthToken(): String? {
        return getFusionAuthTokenCp()?.toString()
    }

    override fun addPublicKey(byteArray: ByteArray) {
        addPublicKeyCp.invokeStringCallback(byteArray.encodeByteArrayToBase64())
    }

    override fun getPublicKey(): ByteArray? {
        return getPublicKeyCp()?.toString()?.decodeBase64ToByteArray()
    }

    override fun addPrivateKey(byteArray: ByteArray) {
        addPrivateKeyCp.invokeStringCallback(byteArray.encodeByteArrayToBase64())
    }

    override fun getPrivateKey(): ByteArray? {
        return getPrivateKeyCp()?.toString()?.decodeBase64ToByteArray()
    }

    override fun setKeyPairVerified(verified: Boolean) {
        setKeyPairVerifiedCp.invokeBooleanCallback(verified)
    }

    override fun getKeyPairVerified(): Boolean? {
        return getKeyPairVerifiedCp()?.pointed?.value
    }

    override fun addUserId(userId: String) {
        addUserIdCp.invokeStringCallback(userId)
    }

    override fun getUserId(): String? {
        return getUserIdCp()?.toString()
    }

    override fun addUserEmail(email: String) {
        addUserEmailCp.invokeStringCallback(email)
    }

    override fun getUserEmail(): String? {
        return getUserEmailCp()?.toString()
    }

    override fun addCertificateChain(certificateChain: List<String>) {
        addCertificateChainCp.invokeStringCallback(certificateChain.certificateChainToString())
    }

    override fun getCertificateChain(): List<String>? {
        return getCertificateChainCp()?.toString()?.stringToCertificateChain()
    }

    override fun clear() {
       clearCp.invoke()
    }
}