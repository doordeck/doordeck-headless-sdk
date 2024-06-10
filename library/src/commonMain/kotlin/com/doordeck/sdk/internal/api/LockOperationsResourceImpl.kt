package com.doordeck.sdk.internal.api

import com.doordeck.sdk.api.LockOperationsResource
import com.doordeck.sdk.api.model.LockOperations
import com.doordeck.sdk.api.requests.LocationRequirementRequest
import com.doordeck.sdk.api.requests.LockOperationRequest
import com.doordeck.sdk.api.requests.LockSettingsRequest
import com.doordeck.sdk.api.requests.OperationBodyRequest
import com.doordeck.sdk.api.requests.OperationHeaderRequest
import com.doordeck.sdk.api.requests.OperationRequest
import com.doordeck.sdk.api.requests.RevokeAccessToALockOperationRequest
import com.doordeck.sdk.api.requests.ShareLockOperationRequest
import com.doordeck.sdk.api.requests.TimeRequirementRequest
import com.doordeck.sdk.api.requests.UnlockBetweenSettingRequest
import com.doordeck.sdk.api.requests.UpdateLockPropertiesRequest
import com.doordeck.sdk.api.requests.UpdateSecureSettingsOperationRequest
import com.doordeck.sdk.api.requests.UsageRequirementsRequest
import com.doordeck.sdk.api.requests.UserPublicKeyRequest
import com.doordeck.sdk.api.responses.LockAuditTrail
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

class LockOperationsResourceImpl(
    private val httpClient: HttpClient
) : LockOperationsResource {

    override fun getAllLocks(): Array<LockResponse> = runBlocking {
        httpClient.get(Paths.getAllLocksPath()).body()
    }

    override fun getSingleLock(lockId: String): LockResponse = runBlocking {
        httpClient.get(Paths.getSingleLockPath(lockId)){
            addRequestHeaders(headers = emptyMap(), apiVersion = ApiVersion.VERSION_3)
        }.body()
    }

    override fun getLockAuditTrail(lockId: String, start: Int, end: Int): Array<LockAuditTrail> = runBlocking {
        httpClient.get(Paths.getLockAuditTrailPath(lockId)) {
            addRequestHeaders(headers = emptyMap(), apiVersion = ApiVersion.VERSION_2)
            parameter(START, start)
            parameter(END, end)
        }.body()
    }

    override fun getAuditForUser(lockId: String, start: Int, end: Int): Array<UserAuditResponse> = runBlocking {
        httpClient.get(Paths.getAuditForUserPath(lockId)) {
            parameter(START, start)
            parameter(END, start)
        }.body()
    }

    override fun getUsersForLock(lockId: String): Array<UserLockResponse> = runBlocking {
        httpClient.get(Paths.getUsersForLockPath(lockId)).body()
    }

    override fun getLocksForUser(userId: String): LockUserResponse = runBlocking {
        httpClient.get(Paths.getLocksForUserPath(userId)).body()
    }

    override fun updateLockProperties(lockId: String, lockProperties: LockOperations.LockProperties) {
        runBlocking {
            httpClient.put(Paths.getUpdateLockPropertiesPath(lockId)) {
                addRequestHeaders()
                setBody(UpdateLockPropertiesRequest(
                    name = lockProperties.name,
                    favourite = lockProperties.favourite,
                    colour = lockProperties.colour,
                    settings = lockProperties.settings?.let { settings ->
                        LockSettingsRequest(
                            defaultName = settings.defaultName,
                            permittedAddress = settings.permittedAddress,
                            delay = settings.delay,
                            usageRequirements = settings.usageRequirements?.let { usageRequirements ->
                                UsageRequirementsRequest(
                                    time = usageRequirements.time?.let { time ->
                                        TimeRequirementRequest(
                                            start = time.start,
                                            end = time.end,
                                            timezone = time.timezone,
                                            days = time.days
                                        )
                                    },
                                    location = usageRequirements.location?.let { location ->
                                        LocationRequirementRequest(
                                            latitude = location.latitude,
                                            longitude = location.longitude,
                                            enabled = location.enabled,
                                            radius = location.radius,
                                            accuracy = location.accuracy
                                        )
                                    }
                                )
                            }
                        )
                    }
                ))
            }
        }
    }

    override fun getUserPublicKey(userEmail: String, visitor: Boolean): UserPublicKeyResponse = runBlocking {
        httpClient.post(Paths.getUserPublicKeyPath(userEmail)) {
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

    override fun lock(lockOperation: LockOperations.LockOperation) {
        val operationRequest = LockOperationRequest(locked = true)
        performOperation(lockOperation.baseOperation, operationRequest)
    }

    override fun unlock(unlockOperation: LockOperations.UnlockOperation): Unit = runBlocking {
        val operationRequest = LockOperationRequest(locked = false)
        performOperation(unlockOperation.baseOperation, operationRequest)
    }

    override fun shareLock(shareLockOperation: LockOperations.ShareLockOperation): Unit = runBlocking {
        val operationRequest = ShareLockOperationRequest(
            user = shareLockOperation.targetUserId,
            publicKey = shareLockOperation.targetUserPublicKey.encodeKeyToBase64(),
            role = shareLockOperation.targetUserRole,
            start = shareLockOperation.start,
            end = shareLockOperation.end
        )
        performOperation(shareLockOperation.baseOperation, operationRequest)
    }

    override fun revokeAccessToLock(revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation) {
        val operationRequest = RevokeAccessToALockOperationRequest(users = revokeAccessToLockOperation.users)
        performOperation(revokeAccessToLockOperation.baseOperation, operationRequest)
    }

    override fun removeSecureSettings(removeSecureSettingsOperation: LockOperations.RemoveSecureSettingsOperation) {
        val operationRequest = UpdateSecureSettingsOperationRequest()
        performOperation(removeSecureSettingsOperation.baseOperation, operationRequest)
    }

    override fun updateSecureSettings(updateSecureSettingsOperation: LockOperations.UpdateSecureSettingsOperation) {
        val operationRequest = UpdateSecureSettingsOperationRequest(
            unlockDuration = updateSecureSettingsOperation.unlockDuration,
            unlockBetween = updateSecureSettingsOperation.unlockBetween?.let {
                UnlockBetweenSettingRequest(
                    start = it.start,
                    end = it.end,
                    timezone = it.timezone,
                    days = it.days,
                    exceptions = it.exceptions
                )
            }
        )
        performOperation(updateSecureSettingsOperation.baseOperation, operationRequest)
    }

    private fun performOperation(baseOperation: LockOperations.BaseOperation, operationRequest: OperationRequest): Unit = runBlocking {
        val operationHeader = OperationHeaderRequest(x5c = baseOperation.userCertificateChain)
        val operationBody = OperationBodyRequest(
            iss = baseOperation.userId,
            sub = baseOperation.lockId,
            nbf = baseOperation.notBefore,
            iat = baseOperation.issuedAt,
            exp = baseOperation.expiresAt,
            jti = baseOperation.trackId,
            operation = operationRequest
        )
        val headerB64 = operationHeader.toJson().encodeToByteArray().encodeToBase64UrlString()
        val bodyB64 = operationBody.toJson().encodeToByteArray().encodeToBase64UrlString()
        val signatureB64 = "$headerB64.$bodyB64".signWithPrivateKey(baseOperation.userPrivateKey).encodeToBase64UrlString()
        val body = "$headerB64.$bodyB64.$signatureB64"
        httpClient.post(Paths.getOperationPath(baseOperation.lockId)) {
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