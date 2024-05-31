package com.doordeck.sdk.internal.api

import com.benasher44.uuid.uuid4
import com.doordeck.sdk.api.LockOperationsResource
import com.doordeck.sdk.api.requests.LockOperationBodyRequest
import com.doordeck.sdk.api.requests.LockOperationRequest
import com.doordeck.sdk.api.requests.OperationHeaderRequest
import com.doordeck.sdk.api.requests.UserPublicKeyRequest
import com.doordeck.sdk.api.responses.LockResponse
import com.doordeck.sdk.api.responses.LockUserResponse
import com.doordeck.sdk.api.responses.ShareableLockResponse
import com.doordeck.sdk.api.responses.UserLockResponse
import com.doordeck.sdk.api.responses.UserPublicKeyResponse
import com.doordeck.sdk.runBlocking
import com.doordeck.sdk.util.addRequestHeaders
import com.doordeck.sdk.util.encodeToBase64UrlString
import com.doordeck.sdk.util.signWithPrivateKey
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

    override fun getAuditForAUser(lockId: String, start: Int, end: Int) {
        TODO("Not yet implemented")
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

    override fun pairWithNewLock() {
        TODO("Not yet implemented")
    }

    override fun getADoordeckUserPublicKey(userEmail: String) {
        TODO("Not yet implemented")
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

    override fun unlock(x5c: Array<String>, lockId: String, locked: Boolean, privateKey: ByteArray, ): Unit = runBlocking {
        val operationHeader =  OperationHeaderRequest(x5c = x5c)
        val lockOperation = LockOperationRequest(locked = locked)
        val operationBody = LockOperationBodyRequest(
            iss = "",// TODO UserId extracted from the token?
            sub = lockId,
            nbf = Clock.System.now().epochSeconds.toString(),
            iat = Clock.System.now().epochSeconds.toString(),
            exp = (Clock.System.now() + 1.minutes).toString(),
            jti = uuid4().toString(),
            operation = lockOperation
        )
        val headerB64 = operationHeader.toJson().encodeToByteArray().encodeToBase64UrlString()
        val bodyB64 = operationBody.toJson().encodeToByteArray().encodeToBase64UrlString()
        val signatureB64 = "$headerB64.$bodyB64".signWithPrivateKey(privateKey).encodeToBase64UrlString()
        val body = "$headerB64.$bodyB64.$signatureB64"
        httpClient.post(Paths.getUnlockPath(operationBody.sub)) {
            addRequestHeaders() // TODO content-type should be application/jwt
            setBody(body)
        }.body()
    }

    override fun shareALock(lockId: String) {
        TODO("Not yet implemented")
    }

    override fun revokeAccessToALock(lockId: String) {
        TODO("Not yet implemented")
    }

    override fun updateSecureSettings(lockId: String) {
        TODO("Not yet implemented")
    }

    override fun getPinnedLocks(): Array<LockResponse> = runBlocking {
        httpClient.get(Paths.getPinnedLocksPath()).body()
    }

    override fun getShareableLocks(): Array<ShareableLockResponse> = runBlocking {
        httpClient.get(Paths.getShareableLocksPath()).body()
    }
}