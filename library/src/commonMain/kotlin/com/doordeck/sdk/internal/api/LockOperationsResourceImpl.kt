package com.doordeck.sdk.internal.api

import com.doordeck.sdk.api.LockOperationsResource
import com.doordeck.sdk.api.model.UserRole
import com.doordeck.sdk.api.requests.LockOperationRequest
import com.doordeck.sdk.api.requests.OperationBodyRequest
import com.doordeck.sdk.api.requests.OperationHeaderRequest
import com.doordeck.sdk.api.requests.OperationRequest
import com.doordeck.sdk.api.requests.PairWithNewLockRequest
import com.doordeck.sdk.api.requests.RevokeAccessToALockOperationRequest
import com.doordeck.sdk.api.requests.ShareLockOperationRequest
import com.doordeck.sdk.api.requests.UpdateSecureSettingsOperationRequest
import com.doordeck.sdk.api.requests.UserPublicKeyRequest
import com.doordeck.sdk.api.responses.LockResponse
import com.doordeck.sdk.api.responses.LockUserResponse
import com.doordeck.sdk.api.responses.ShareableLockResponse
import com.doordeck.sdk.api.responses.UserAuditResponse
import com.doordeck.sdk.api.responses.UserLockResponse
import com.doordeck.sdk.api.responses.UserPublicKeyResponse
import com.doordeck.sdk.internal.api.Params.END
import com.doordeck.sdk.internal.api.Params.START
import com.doordeck.sdk.internal.api.Params.VISITOR
import com.doordeck.sdk.runBlocking
import com.doordeck.sdk.util.Crypto.encodeKeyToBase64
import com.doordeck.sdk.util.Crypto.signWithPrivateKey
import com.doordeck.sdk.util.addRequestHeaders
import com.doordeck.sdk.util.encodeToBase64UrlString
import com.doordeck.sdk.util.toJson
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.minutes

class LockOperationsResourceImpl(
    private val httpClient: HttpClient
) : LockOperationsResource {

    override fun getAllLocks(): Array<LockResponse> = runBlocking {
        httpClient.get(Paths.getAllLocksPath()).body()
    }

    override fun getSingleLock(lockId: String): LockResponse = runBlocking {
        httpClient.get(Paths.getASingleLockPath(lockId)).body()
    }

    override fun getAuditForAUser(lockId: String, start: Int, end: Int): Array<UserAuditResponse> = runBlocking {
        httpClient.get(Paths.getAuditForAUserPath(lockId)) {
            parameter(START, start)
            parameter(END, start)
        }.body()
    }

    override fun getUsersForALock(lockId: String): Array<UserLockResponse> = runBlocking {
        httpClient.get(Paths.getUsersForALockPath(lockId)).body()
    }

    override fun getLocksForAUser(userId: String): LockUserResponse = runBlocking {
        httpClient.get(Paths.getLocksForAUserPath(userId)).body()
    }

    override fun updateLockProperties(lockId: String) {
        TODO("Not yet implemented")
    }

    override fun pairWithNewLock(key: String, name: String) {
        runBlocking {
            httpClient.post(Paths.getPairWithNewLockPath()) {
                addRequestHeaders()
                setBody(PairWithNewLockRequest(key, name))
            }
        }
    }

    override fun getADoordeckUserPublicKey(userEmail: String, visitor: Boolean): UserPublicKeyResponse = runBlocking {
        httpClient.post(Paths.getADoordeckUserPublickKeyPath(userEmail)) {
            addRequestHeaders()
            parameter(VISITOR, visitor)
        }.body()
    }

    override fun getUserPublicKeyByEmail(email: String): UserPublicKeyResponse =
        getUserPublicKey(UserPublicKeyRequest(email = email))

    override fun getUserPublicKeyByTelephone(telephone: String): UserPublicKeyResponse =
        getUserPublicKey(UserPublicKeyRequest(telephone = telephone))

    override fun getUserPublicKeyByLocalKey(localKey: String): UserPublicKeyResponse =
        getUserPublicKey(UserPublicKeyRequest(localKey = localKey))

    override fun getUserPublicKeyByForeignKey(foreignKey: String): UserPublicKeyResponse =
        getUserPublicKey(UserPublicKeyRequest(foreignKey = foreignKey))

    override fun getUserPublicKeyByIdentity(identity: String): UserPublicKeyResponse =
        getUserPublicKey(UserPublicKeyRequest(identity = identity))

    private fun getUserPublicKey(request: UserPublicKeyRequest): UserPublicKeyResponse = runBlocking {
        httpClient.post(Paths.getUserPublicKeyPath()) {
            addRequestHeaders()
            setBody(request)
        }.body()
    }

    override fun lock(userId: String, x5c: Array<String>, lockId: String, privateKey: ByteArray, trackId: String?) {
        val operationRequest = LockOperationRequest(locked = true)
        performOperation(userId, x5c, lockId, operationRequest, privateKey, trackId)
    }

    override fun unlock(userId: String, x5c: Array<String>, lockId: String, privateKey: ByteArray,
                        trackId: String?): Unit = runBlocking {
        val operationRequest = LockOperationRequest(locked = false)
        performOperation(userId, x5c, lockId, operationRequest, privateKey, trackId)
    }

    override fun shareALock(userId: String, x5c: Array<String>, lockId: String, targetUserId: String,
                            targetUserRole: UserRole, targetUserPublicKey: ByteArray, privateKey: ByteArray,
                            start: Int?, end: Int?, trackId: String?): Unit = runBlocking {
        val operationRequest = ShareLockOperationRequest(
            user = targetUserId,
            publicKey = targetUserPublicKey.encodeKeyToBase64(),
            role = targetUserRole,
            start = start,
            end = end
        )
        performOperation(userId, x5c, lockId, operationRequest, privateKey, trackId)
    }

    override fun revokeAccessToALock(userId: String, x5c: Array<String>, lockId: String, users: Array<String>,
                                     privateKey: ByteArray, trackId: String?) {
        val operationRequest = RevokeAccessToALockOperationRequest(users = users)
        performOperation(userId, x5c, lockId, operationRequest, privateKey, trackId)
    }

    override fun removeSecureSettings(userId: String, x5c: Array<String>, lockId: String,
                                      privateKey: ByteArray, trackId: String?) {
        val operationRequest = UpdateSecureSettingsOperationRequest()
        performOperation(userId, x5c, lockId, operationRequest, privateKey, trackId)
    }

    override fun updateSecureSettings(lockId: String) {
        val operationRequest = UpdateSecureSettingsOperationRequest()
        TODO("Not yet implemented")
    }

    private fun performOperation(userId: String, x5c: Array<String>, lockId: String, operationRequest: OperationRequest,
                                 privateKey: ByteArray, trackId: String?): Unit = runBlocking {
        val operationHeader = OperationHeaderRequest(x5c = x5c)
        val operationBody = OperationBodyRequest(
            iss = userId,
            sub = lockId,
            nbf = Clock.System.now().epochSeconds.toInt(), // TODO Move to func param
            iat = Clock.System.now().epochSeconds.toInt(), // TODO Move to func param
            exp = (Clock.System.now() + 1.minutes).epochSeconds.toInt(), // TODO Move to func param
            jti = trackId,
            operation = operationRequest
        )
        val headerB64 = operationHeader.toJson().encodeToByteArray().encodeToBase64UrlString()
        val bodyB64 = operationBody.toJson().encodeToByteArray().encodeToBase64UrlString()
        val signatureB64 = "$headerB64.$bodyB64".signWithPrivateKey(privateKey).encodeToBase64UrlString()
        val body = "$headerB64.$bodyB64.$signatureB64"
        httpClient.post(Paths.getOperationPath(lockId)) {
            addRequestHeaders(true)
            setBody(body)
        }.body()
    }

    override fun getPinnedLocks(): Array<LockResponse> = runBlocking {
        httpClient.get(Paths.getPinnedLocksPath()).body()
    }

    override fun getShareableLocks(): Array<ShareableLockResponse> = runBlocking {
        httpClient.get(Paths.getShareableLocksPath()).body()
    }
}