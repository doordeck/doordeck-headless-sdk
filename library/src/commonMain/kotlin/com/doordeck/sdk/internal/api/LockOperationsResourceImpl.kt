package com.doordeck.sdk.internal.api

import com.doordeck.sdk.api.LockOperationsResource
import com.doordeck.sdk.api.model.LockOperations
import com.doordeck.sdk.api.requests.LocationRequirementRequest
import com.doordeck.sdk.api.requests.LockOperationRequest
import com.doordeck.sdk.api.requests.LockSettingsDefaultNameRequest
import com.doordeck.sdk.api.requests.LockSettingsHiddenRequest
import com.doordeck.sdk.api.requests.LockSettingsPermittedAddressesRequest
import com.doordeck.sdk.api.requests.OperationBodyRequest
import com.doordeck.sdk.api.requests.OperationHeaderRequest
import com.doordeck.sdk.api.requests.OperationRequest
import com.doordeck.sdk.api.requests.RevokeAccessToALockOperationRequest
import com.doordeck.sdk.api.requests.ShareLockOperationRequest
import com.doordeck.sdk.api.requests.TimeRequirementRequest
import com.doordeck.sdk.api.requests.UnlockBetweenSettingRequest
import com.doordeck.sdk.api.requests.UpdateLockColourRequest
import com.doordeck.sdk.api.requests.UpdateLockFavouriteRequest
import com.doordeck.sdk.api.requests.UpdateLockNameRequest
import com.doordeck.sdk.api.requests.UpdateLockPropertiesRequest
import com.doordeck.sdk.api.requests.UpdateLockSettingLocationUsageRequirementRequest
import com.doordeck.sdk.api.requests.UpdateLockSettingRequest
import com.doordeck.sdk.api.requests.UpdateLockSettingTimeUsageRequirementRequest
import com.doordeck.sdk.api.requests.UpdateLockSettingUsageRequirementRequest
import com.doordeck.sdk.api.requests.UpdateSecureSettingsOperationRequest
import com.doordeck.sdk.api.requests.UserPublicKeyRequest
import com.doordeck.sdk.api.responses.LockAuditTrailResponse
import com.doordeck.sdk.api.responses.LockResponse
import com.doordeck.sdk.api.responses.LockUserResponse
import com.doordeck.sdk.api.responses.ShareableLockResponse
import com.doordeck.sdk.api.responses.UserAuditResponse
import com.doordeck.sdk.api.responses.UserLockResponse
import com.doordeck.sdk.api.responses.UserPublicKeyResponse
import com.doordeck.sdk.internal.api.Params.END
import com.doordeck.sdk.internal.api.Params.START
import com.doordeck.sdk.internal.api.Params.VISITOR
import com.doordeck.sdk.util.Crypto.encodeByteArrayToBase64
import com.doordeck.sdk.util.Crypto.signWithPrivateKey
import com.doordeck.sdk.util.addRequestHeaders
import com.doordeck.sdk.util.toJson
import io.ktor.client.*
import io.ktor.client.request.*

class LockOperationsResourceImpl(
    private val httpClient: HttpClient
) : AbstractResourceImpl(), LockOperationsResource {

    override fun getSingleLock(lockId: String): LockResponse {
        return httpClient.get(Paths.getSingleLockPath(lockId)){
            addRequestHeaders(headers = emptyMap(), apiVersion = ApiVersion.VERSION_3)
        }
    }

    override fun getLockAuditTrail(lockId: String, start: Int, end: Int): Array<LockAuditTrailResponse> {
        return httpClient.get(Paths.getLockAuditTrailPath(lockId)) {
            addRequestHeaders(headers = emptyMap(), apiVersion = ApiVersion.VERSION_2)
            parameter(START, start)
            parameter(END, end)
        }
    }

    override fun getAuditForUser(lockId: String, start: Int, end: Int): Array<UserAuditResponse> {
        return httpClient.get(Paths.getAuditForUserPath(lockId)) {
            addRequestHeaders(headers = emptyMap(), apiVersion = ApiVersion.VERSION_2)
            parameter(START, start)
            parameter(END, start)
        }
    }

    override fun getUsersForLock(lockId: String): Array<UserLockResponse> {
        return httpClient.get(Paths.getUsersForLockPath(lockId))
    }

    override fun getLocksForUser(userId: String): LockUserResponse {
        return httpClient.get(Paths.getLocksForUserPath(userId))
    }

    override fun updateLockName(lockId: String, name: String?) {
        updateLockProperties(lockId, UpdateLockNameRequest(name))
    }

    override fun updateLockFavourite(lockId: String, favourite: Boolean?) {
        updateLockProperties(lockId, UpdateLockFavouriteRequest(favourite))
    }

    override fun updateLockColour(lockId: String, colour: String?) {
        updateLockProperties(lockId, UpdateLockColourRequest(colour))
    }

    override fun updateLockSettingDefaultName(lockId: String, name: String?) {
        updateLockProperties(lockId, UpdateLockSettingRequest(LockSettingsDefaultNameRequest(name)))
    }

    override fun updateLockSettingPermittedAddresses(lockId: String, permittedAddresses: Array<String>) {
        updateLockProperties(lockId, UpdateLockSettingRequest(LockSettingsPermittedAddressesRequest(permittedAddresses)))
    }

    override fun updateLockSettingHidden(lockId: String, hidden: Boolean) {
        updateLockProperties(lockId, UpdateLockSettingRequest(LockSettingsHiddenRequest(hidden)))
    }

    override fun updateLockSettingTimeRestrictions(lockId: String, times: Array<LockOperations.TimeRequirement>) {
        updateLockProperties(lockId, UpdateLockSettingRequest(
            UpdateLockSettingUsageRequirementRequest(UpdateLockSettingTimeUsageRequirementRequest(
                time = times.map { TimeRequirementRequest(it.start, it.end, it.timezone, it.days) }.toTypedArray()
            ))
        ))
    }

    override fun updateLockSettingLocationRestrictions(lockId: String, location: LockOperations.LocationRequirement?) {
        updateLockProperties(lockId, UpdateLockSettingRequest(
            UpdateLockSettingUsageRequirementRequest(UpdateLockSettingLocationUsageRequirementRequest(
                location?.let { LocationRequirementRequest(it.latitude, it.longitude, it.enabled, it.radius, it.accuracy) }
            ))
        ))
    }

    private fun updateLockProperties(lockId: String, request: UpdateLockPropertiesRequest) {
        httpClient.putEmpty(Paths.getUpdateLockPropertiesPath(lockId)) {
            addRequestHeaders()
            setBody(request)
        }
    }

    override fun getUserPublicKey(userEmail: String, visitor: Boolean): UserPublicKeyResponse {
        return httpClient.post(Paths.getUserPublicKeyPath(userEmail)) {
            addRequestHeaders()
            parameter(VISITOR, visitor)
        }
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

    private fun getUserPublicKey(request: UserPublicKeyRequest): UserPublicKeyResponse {
        return httpClient.post(Paths.getUserPublicKeyPath()) {
            addRequestHeaders()
            setBody(request)
        }
    }

    override fun unlock(unlockOperation: LockOperations.UnlockOperation) {
        val operationRequest = LockOperationRequest(locked = false)
        performOperation(unlockOperation.baseOperation, operationRequest)
    }

    override fun shareLock(shareLockOperation: LockOperations.ShareLockOperation) {
        val operationRequest = ShareLockOperationRequest(
            user = shareLockOperation.targetUserId,
            publicKey = shareLockOperation.targetUserPublicKey.encodeByteArrayToBase64(),
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

    override fun updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration) {
        val operationRequest = UpdateSecureSettingsOperationRequest(
            unlockDuration = updateSecureSettingUnlockDuration.unlockDuration
        )
        performOperation(updateSecureSettingUnlockDuration.baseOperation, operationRequest)
    }

    override fun uploadSecureSettingUnlockBetween(updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween) {
        val operationRequest = UpdateSecureSettingsOperationRequest(
            unlockBetween = updateSecureSettingUnlockBetween.unlockBetween?.let {
                UnlockBetweenSettingRequest(
                    start = it.start,
                    end = it.end,
                    timezone = it.timezone,
                    days = it.days,
                    exceptions = it.exceptions
                )
            }
        )
        performOperation(updateSecureSettingUnlockBetween.baseOperation, operationRequest)
    }

    private fun performOperation(baseOperation: LockOperations.BaseOperation, operationRequest: OperationRequest) {
        val operationHeader = OperationHeaderRequest(x5c = baseOperation.userCertificateChain)
        val operationBody = OperationBodyRequest(
            iss = baseOperation.userId,
            sub = baseOperation.lockId,
            nbf = baseOperation.notBefore,
            iat = baseOperation.issuedAt,
            exp = baseOperation.expiresAt,
            jti = baseOperation.jti,
            operation = operationRequest
        )
        val headerB64 = operationHeader.toJson().encodeToByteArray().encodeByteArrayToBase64()
        val bodyB64 = operationBody.toJson().encodeToByteArray().encodeByteArrayToBase64()
        val signatureB64 = "$headerB64.$bodyB64".signWithPrivateKey(baseOperation.userPrivateKey).encodeByteArrayToBase64()
        val body = "$headerB64.$bodyB64.$signatureB64"
        httpClient.postEmpty(Paths.getOperationPath(baseOperation.lockId)) {
            addRequestHeaders(true)
            setBody(body)
        }
    }

    override fun getPinnedLocks(): Array<LockResponse> {
        return httpClient.get(Paths.getPinnedLocksPath())
    }

    override fun getShareableLocks(): Array<ShareableLockResponse> {
        return httpClient.get(Paths.getShareableLocksPath())
    }
}