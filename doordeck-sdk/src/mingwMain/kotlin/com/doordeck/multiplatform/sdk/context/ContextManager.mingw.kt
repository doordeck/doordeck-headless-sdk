package com.doordeck.multiplatform.sdk.context

import com.doordeck.multiplatform.sdk.ResultCallback
import com.doordeck.multiplatform.sdk.model.data.ApiEnvironment
import com.doordeck.multiplatform.sdk.model.data.EncodedKeyPair
import com.doordeck.multiplatform.sdk.model.data.OperationContextData
import com.doordeck.multiplatform.sdk.util.Utils.certificateChainToString
import com.doordeck.multiplatform.sdk.util.Utils.decodeBase64ToByteArray
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.Utils.stringToCertificateChain
import com.doordeck.multiplatform.sdk.util.fromJson
import com.doordeck.multiplatform.sdk.util.reply
import com.doordeck.multiplatform.sdk.util.replyAsync
import com.doordeck.multiplatform.sdk.util.toJson

actual object ContextManager {

    fun setApiEnvironment(apiEnvironment: String, requestId: Long = 0, callback: ResultCallback) =
        callback.reply(requestId) { Context.setApiEnvironment(ApiEnvironment.valueOf(apiEnvironment)) }

    fun getApiEnvironment(requestId: Long = 0, callback: ResultCallback) =
        callback.reply(requestId) { Context.getApiEnvironment().name }

    fun setCloudAuthToken(token: String, requestId: Long = 0, callback: ResultCallback) =
        callback.reply(requestId) { Context.setCloudAuthToken(token) }

    fun getCloudAuthToken(requestId: Long = 0, callback: ResultCallback) =
        callback.reply(requestId) { Context.getCloudAuthToken() }

    fun isCloudAuthTokenInvalidOrExpired(checkServerInvalidation: String, requestId: Long = 0, callback: ResultCallback) =
        callback.replyAsync(requestId) { Context.isCloudAuthTokenInvalidOrExpired(checkServerInvalidation.toBoolean()) }

    fun setCloudRefreshToken(token: String, requestId: Long = 0, callback: ResultCallback) =
        callback.reply(requestId) { Context.setCloudRefreshToken(token) }

    fun getCloudRefreshToken(requestId: Long = 0, callback: ResultCallback) =
        callback.reply(requestId) { Context.getCloudRefreshToken() }

    fun setFusionHost(host: String, requestId: Long = 0, callback: ResultCallback) =
        callback.reply(requestId) { Context.setFusionHost(host) }

    fun getFusionHost(requestId: Long = 0, callback: ResultCallback) =
        callback.reply(requestId) { Context.getFusionHost() }

    fun setFusionAuthToken(token: String, requestId: Long = 0, callback: ResultCallback) =
        callback.reply(requestId) { Context.setFusionAuthToken(token) }

    fun getFusionAuthToken(requestId: Long = 0, callback: ResultCallback) =
        callback.reply(requestId) { Context.getFusionAuthToken() }

    fun setUserId(userId: String, requestId: Long = 0, callback: ResultCallback) =
        callback.reply(requestId) { Context.setUserId(userId) }

    fun getUserId(requestId: Long = 0, callback: ResultCallback) =
        callback.reply(requestId) { Context.getUserId() }

    fun setUserEmail(email: String, requestId: Long = 0, callback: ResultCallback) =
        callback.reply(requestId) { Context.setUserEmail(email) }

    fun getUserEmail(requestId: Long = 0, callback: ResultCallback) =
        callback.reply(requestId) { Context.getUserEmail() }

    fun setCertificateChain(certificateChain: String, requestId: Long = 0, callback: ResultCallback) =
        callback.reply(requestId) { Context.setCertificateChain(certificateChain.stringToCertificateChain()) }

    fun getCertificateChain(requestId: Long = 0, callback: ResultCallback) =
        callback.reply(requestId) { Context.getCertificateChain()?.certificateChainToString() }

    fun isCertificateChainInvalidOrExpired(requestId: Long = 0, callback: ResultCallback) =
        callback.reply(requestId) { Context.isCertificateChainInvalidOrExpired() }

    fun setKeyPair(data: String, requestId: Long = 0, callback: ResultCallback) =
        callback.reply(requestId) {
            val keyPair = data.fromJson<EncodedKeyPair>()
            Context.setKeyPair(
                publicKey = keyPair.publicKey.decodeBase64ToByteArray(),
                privateKey = keyPair.privateKey.decodeBase64ToByteArray()
            )
        }

    fun getKeyPair(requestId: Long = 0, callback: ResultCallback) =
        callback.reply(requestId) {
            Context.getKeyPair()?.let {
                EncodedKeyPair(
                    publicKey = it.public.encodeByteArrayToBase64(),
                    privateKey = it.private.encodeByteArrayToBase64()
                )
            }?.toJson()
        }

    fun setKeyPairVerified(publicKey: String?, requestId: Long = 0, callback: ResultCallback) =
        callback.reply(requestId) { Context.setKeyPairVerified(publicKey?.decodeBase64ToByteArray()) }

    fun isKeyPairVerified(requestId: Long = 0, callback: ResultCallback) =
        callback.reply(requestId) { Context.isKeyPairVerified() }

    fun isKeyPairValid(requestId: Long = 0, callback: ResultCallback) =
        callback.reply(requestId) { Context.isKeyPairValid() }

    /**
     * Sets all necessary fields to perform secure operations in JSON format, the provided values will be automatically stored in secure storage.
     */
    fun setOperationContext(data: String, requestId: Long = 0, callback: ResultCallback) =
        callback.reply(requestId) {
            val operationContextData = data.fromJson<OperationContextData>()
            Context.setOperationContext(
                userId = operationContextData.userId,
                certificateChain = operationContextData.certificateChain.stringToCertificateChain(),
                publicKey = operationContextData.publicKey.decodeBase64ToByteArray(),
                privateKey = operationContextData.privateKey.decodeBase64ToByteArray(),
                isKeyPairVerified = operationContextData.isKeyPairVerified
            )
        }

    fun getContextState(checkServerInvalidation: String, requestId: Long = 0, callback: ResultCallback) =
        callback.replyAsync(requestId) { Context.getContextState(checkServerInvalidation.toBoolean()) }

    fun clearContext(requestId: Long = 0, callback: ResultCallback) =
        callback.reply(requestId) { Context.clearContext() }
}

actual fun contextManager(): ContextManager = ContextManager
