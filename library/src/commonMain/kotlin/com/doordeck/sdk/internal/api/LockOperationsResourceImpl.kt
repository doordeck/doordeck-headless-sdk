package com.doordeck.sdk.internal.api

import com.doordeck.sdk.api.LockOperationsResource
import com.doordeck.sdk.api.model.LockOperations
import com.doordeck.sdk.api.requests.LocationRequirementRequest
import com.doordeck.sdk.api.requests.LockOperationRequest
import com.doordeck.sdk.api.requests.LockSettingsRequest
import com.doordeck.sdk.api.requests.OperationBodyRequest
import com.doordeck.sdk.api.requests.OperationHeaderRequest
import com.doordeck.sdk.api.requests.OperationRequest
import com.doordeck.sdk.api.requests.PairWithNewLockRequest
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


    // FIXME i think we should remove this one, I'm trying to discourage people using it as its quite expensive, I'd prefer people use "get devices for site" style operations
    // FIXME another note, some APIs like this one, return a streaming JSON response, which might be way too hard to bother supporting on the SDK, but might be worth a brief look into
    override fun getAllLocks(): Array<LockResponse> = runBlocking {
        httpClient.get(Paths.getAllLocksPath()).body()
    }

    // FIXME this one should use version 3
    override fun getSingleLock(lockId: String): LockResponse = runBlocking {
        httpClient.get(Paths.getASingleLockPath(lockId)).body()
    }

    // FIXME we should absolutely use Instant from https://github.com/Kotlin/kotlinx-datetime
    // FIXME this is a better candidate for streaming API responses since it can lots of data
    // also if start/end are not provided, the server just returns the last week of data, which is confusing, so i agree its better to have mandatory start/end
    override fun getLockAuditTrail(lockId: String, start: Int, end: Int): Array<LockAuditTrail> = runBlocking {
        httpClient.get(Paths.getLockAuditTrailPath(lockId)) {
            addRequestHeaders(headers = emptyMap(), apiVersion = ApiVersion.VERSION_2)
            parameter(START, start)
            parameter(END, end)
        }.body()
    }

    override fun getAuditForAUser(lockId: String, start: Int, end: Int): Array<UserAuditResponse> = runBlocking {
        httpClient.get(Paths.getAuditForAUserPath(lockId)) {
            parameter(START, start)
            parameter(END, start)
        }.body()
    }

    // FIXME might be worth considering if we should have a value class for lockId, otherwise it *could* maybe potentially be abused
    override fun getUsersForALock(lockId: String): Array<UserLockResponse> = runBlocking {
        httpClient.get(Paths.getUsersForALockPath(lockId)).body()
    }

    override fun getLocksForAUser(userId: String): LockUserResponse = runBlocking {
        httpClient.get(Paths.getLocksForAUserPath(userId)).body()
    }

    override fun updateLockProperties(lockId: String, lockProperties: LockOperations.LockProperties) {
        runBlocking {
            httpClient.put(Paths.getUpdateLockPropertiesPath(lockId)) {
                addRequestHeaders()
                setBody(UpdateLockPropertiesRequest( // TODO how do they get serialized? null tends to mean 'unset' the setting, where as 'absent' means don't change the setting
                    name = lockProperties.name, // TODO should we hide this one? talk with @Marwan, it allows personal aliases to a lock which only you see but is fairly unclear as settings go
                    favourite = lockProperties.favourite,
                    colour = lockProperties.colour,
                    settings = lockProperties.settings?.let { settings ->
                        LockSettingsRequest(
                            defaultName = settings.defaultName,
                            permittedAddress = settings.permittedAddress,
                            delay = settings.delay,
                            // FIXME hidden = setting.hidden,
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

    // FIXME remove this one, its super legacy, I think Ive even deleted the endpoint for it so i should probably update the docs too
    override fun pairWithNewLock(key: String, name: String) {
        runBlocking {
            httpClient.post(Paths.getPairWithNewLockPath()) {
                addRequestHeaders()
                setBody(PairWithNewLockRequest(key, name))
            }
        }
    }

    // TODO annotate with DoordeckOnly and remove the word Doordeck from the method name
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

    // FIXME lets remove this one, lock is only supported on a few integrations, mostly we just support momentary unlock
    override fun lock(lockOperation: LockOperations.LockOperation) {
        val operationRequest = LockOperationRequest(locked = true)
        performOperation(lockOperation.baseOperation, operationRequest)
    }

    override fun unlock(unlockOperation: LockOperations.UnlockOperation): Unit = runBlocking {
        val operationRequest = LockOperationRequest(locked = false)
        performOperation(unlockOperation.baseOperation, operationRequest)
    }

    // FIXME just call shareLock, not sure I like the `A` in the middle
    override fun shareALock(shareALockOperation: LockOperations.ShareALockOperation): Unit = runBlocking {
        val operationRequest = ShareLockOperationRequest(
            user = shareALockOperation.targetUserId,
            publicKey = shareALockOperation.targetUserPublicKey.encodeKeyToBase64(),
            role = shareALockOperation.targetUserRole,
            start = shareALockOperation.start,
            end = shareALockOperation.end
        )
        performOperation(shareALockOperation.baseOperation, operationRequest)
    }

    override fun revokeAccessToALock(revokeAccessToALockOperation: LockOperations.RevokeAccessToALockOperation) {
        val operationRequest = RevokeAccessToALockOperationRequest(users = revokeAccessToALockOperation.users)
        performOperation(revokeAccessToALockOperation.baseOperation, operationRequest)
    }

    // TODO clarify, this one sets everything to null? i think it might be unsafe to allow that
    override fun removeSecureSettings(removeSecureSettingsOperation: LockOperations.RemoveSecureSettingsOperation) {
        val operationRequest = UpdateSecureSettingsOperationRequest()
        performOperation(removeSecureSettingsOperation.baseOperation, operationRequest)
    }

    // TODO same question as earlier, null = delete setting, absent = preseve current setting so how we handle that is important here, i know its clunky
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