package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.LockOperationsResource
import com.doordeck.multiplatform.sdk.api.model.LockOperations
import com.doordeck.multiplatform.sdk.api.requests.LocationRequirementRequest
import com.doordeck.multiplatform.sdk.api.requests.LockOperationRequest
import com.doordeck.multiplatform.sdk.api.requests.LockSettingsDefaultNameRequest
import com.doordeck.multiplatform.sdk.api.requests.LockSettingsHiddenRequest
import com.doordeck.multiplatform.sdk.api.requests.LockSettingsPermittedAddressesRequest
import com.doordeck.multiplatform.sdk.api.requests.OperationBodyRequest
import com.doordeck.multiplatform.sdk.api.requests.OperationHeaderRequest
import com.doordeck.multiplatform.sdk.api.requests.OperationRequest
import com.doordeck.multiplatform.sdk.api.requests.RevokeAccessToALockOperationRequest
import com.doordeck.multiplatform.sdk.api.requests.ShareLockOperationRequest
import com.doordeck.multiplatform.sdk.api.requests.TimeRequirementRequest
import com.doordeck.multiplatform.sdk.api.requests.UnlockBetweenSettingRequest
import com.doordeck.multiplatform.sdk.api.requests.UpdateLockColourRequest
import com.doordeck.multiplatform.sdk.api.requests.UpdateLockFavouriteRequest
import com.doordeck.multiplatform.sdk.api.requests.UpdateLockNameRequest
import com.doordeck.multiplatform.sdk.api.requests.UpdateLockPropertiesRequest
import com.doordeck.multiplatform.sdk.api.requests.UpdateLockSettingLocationUsageRequirementRequest
import com.doordeck.multiplatform.sdk.api.requests.UpdateLockSettingRequest
import com.doordeck.multiplatform.sdk.api.requests.UpdateLockSettingTimeUsageRequirementRequest
import com.doordeck.multiplatform.sdk.api.requests.UpdateLockSettingUsageRequirementRequest
import com.doordeck.multiplatform.sdk.api.requests.UpdateSecureSettingsOperationRequest
import com.doordeck.multiplatform.sdk.api.requests.UserPublicKeyRequest
import com.doordeck.multiplatform.sdk.api.responses.LockAuditTrailResponse
import com.doordeck.multiplatform.sdk.api.responses.LockResponse
import com.doordeck.multiplatform.sdk.api.responses.LockUserResponse
import com.doordeck.multiplatform.sdk.api.responses.ShareableLockResponse
import com.doordeck.multiplatform.sdk.api.responses.UserAuditResponse
import com.doordeck.multiplatform.sdk.api.responses.UserLockResponse
import com.doordeck.multiplatform.sdk.api.responses.UserPublicKeyResponse
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.Params.END
import com.doordeck.multiplatform.sdk.internal.api.Params.START
import com.doordeck.multiplatform.sdk.internal.api.Params.VISITOR
import com.doordeck.multiplatform.sdk.util.Crypto.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.Crypto.signWithPrivateKey
import com.doordeck.multiplatform.sdk.util.addRequestHeaders
import com.doordeck.multiplatform.sdk.util.toJson
import io.ktor.client.*
import io.ktor.client.request.*

class LockOperationsResourceImpl(
    private val httpClient: HttpClient,
    private val contextManager: ContextManagerImpl
) : AbstractResourceImpl(), LockOperationsResource {

    override fun getSingleLock(lockId: String): LockResponse {
        return httpClient.get(Paths.getSingleLockPath(lockId)){
            addRequestHeaders(contentType = null, apiVersion = ApiVersion.VERSION_3)
        }
    }

    override fun getLockAuditTrail(lockId: String, start: Int, end: Int): Array<LockAuditTrailResponse> {
        return httpClient.get(Paths.getLockAuditTrailPath(lockId)) {
            addRequestHeaders(contentType = null, apiVersion = ApiVersion.VERSION_2)
            parameter(START, start)
            parameter(END, end)
        }
    }

    override fun getAuditForUser(userId: String, start: Int, end: Int): Array<UserAuditResponse> {
        return httpClient.get(Paths.getAuditForUserPath(userId)) {
            addRequestHeaders(contentType = null, apiVersion = ApiVersion.VERSION_2)
            parameter(START, start)
            parameter(END, end)
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

    override fun setLockSettingPermittedAddresses(lockId: String, permittedAddresses: Array<String>) {
        updateLockProperties(lockId, UpdateLockSettingRequest(LockSettingsPermittedAddressesRequest(permittedAddresses)))
    }

    override fun updateLockSettingHidden(lockId: String, hidden: Boolean) {
        updateLockProperties(lockId, UpdateLockSettingRequest(LockSettingsHiddenRequest(hidden)))
    }

    override fun setLockSettingTimeRestrictions(lockId: String, times: Array<LockOperations.TimeRequirement>) {
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

    override fun unlockWithContext(lockId: String) {
        val operationContext = contextManager.getOperationContext()
        val baseOperation = LockOperations.BaseOperation(
            userId = operationContext.userId,
            userCertificateChain = operationContext.userCertificateChain,
            userPrivateKey = operationContext.userPrivateKey,
            lockId = lockId
        )
        unlock(baseOperation)
    }

    override fun unlock(unlockOperation: LockOperations.UnlockOperation) {
        unlock(unlockOperation.baseOperation)
    }

    private fun unlock(baseOperation: LockOperations.BaseOperation) {
        val operationRequest = LockOperationRequest(locked = false)
        performOperation(baseOperation, operationRequest)
    }

    override fun shareLockWithContext(lockId: String, shareLock: LockOperations.ShareLock) {
        val operationContext = contextManager.getOperationContext()
        val baseOperation = LockOperations.BaseOperation(
            userId = operationContext.userId,
            userCertificateChain = operationContext.userCertificateChain,
            userPrivateKey = operationContext.userPrivateKey,
            lockId = lockId
        )
        shareLock(baseOperation, shareLock)
    }

    override fun shareLock(shareLockOperation: LockOperations.ShareLockOperation) {
        shareLock(shareLockOperation.baseOperation, shareLockOperation.shareLock)
    }

    private fun shareLock(baseOperation: LockOperations.BaseOperation, shareLock: LockOperations.ShareLock) {
        val operationRequest = ShareLockOperationRequest(
            user = shareLock.targetUserId,
            publicKey = shareLock.targetUserPublicKey.encodeByteArrayToBase64(),
            role = shareLock.targetUserRole,
            start = shareLock.start,
            end = shareLock.end
        )
        performOperation(baseOperation, operationRequest)
    }

    override fun revokeAccessToLockWithContext(lockId: String, users: Array<String>) {
        val operationContext = contextManager.getOperationContext()
        val baseOperation = LockOperations.BaseOperation(
            userId = operationContext.userId,
            userCertificateChain = operationContext.userCertificateChain,
            userPrivateKey = operationContext.userPrivateKey,
            lockId = lockId
        )
        revokeAccessToLock(baseOperation, users)
    }

    override fun revokeAccessToLock(revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation) {
        revokeAccessToLock(revokeAccessToLockOperation.baseOperation, revokeAccessToLockOperation.users)
    }

    private fun revokeAccessToLock(baseOperation: LockOperations.BaseOperation, users: Array<String>) {
        val operationRequest = RevokeAccessToALockOperationRequest(users = users)
        performOperation(baseOperation, operationRequest)
    }

    override fun updateSecureSettingUnlockDurationWithContext(lockId: String, unlockDuration: Int) {
        val operationContext = contextManager.getOperationContext()
        val baseOperation = LockOperations.BaseOperation(
            userId = operationContext.userId,
            userCertificateChain = operationContext.userCertificateChain,
            userPrivateKey = operationContext.userPrivateKey,
            lockId = lockId
        )
        updateSecureSettingUnlockDuration(baseOperation, unlockDuration)
    }

    override fun updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration) {
        updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration.baseOperation, updateSecureSettingUnlockDuration.unlockDuration)
    }

    private fun updateSecureSettingUnlockDuration(baseOperation: LockOperations.BaseOperation, unlockDuration: Int) {
        val operationRequest = UpdateSecureSettingsOperationRequest(
            unlockDuration = unlockDuration
        )
        performOperation(baseOperation, operationRequest)
    }

    override fun uploadSecureSettingUnlockBetweenWithContext(lockId: String, unlockBetween: LockOperations.UnlockBetween?) {
        val operationContext = contextManager.getOperationContext()
        val baseOperation = LockOperations.BaseOperation(
            userId = operationContext.userId,
            userCertificateChain = operationContext.userCertificateChain,
            userPrivateKey = operationContext.userPrivateKey,
            lockId = lockId
        )
        uploadSecureSettingUnlockBetween(baseOperation, unlockBetween)
    }

    override fun uploadSecureSettingUnlockBetween(updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween) {
        uploadSecureSettingUnlockBetween(updateSecureSettingUnlockBetween.baseOperation, updateSecureSettingUnlockBetween.unlockBetween)
    }

    private fun uploadSecureSettingUnlockBetween(baseOperation: LockOperations.BaseOperation, unlockBetween: LockOperations.UnlockBetween?) {
        val operationRequest = UpdateSecureSettingsOperationRequest(
            unlockBetween = unlockBetween?.let {
                UnlockBetweenSettingRequest(
                    start = it.start,
                    end = it.end,
                    timezone = it.timezone,
                    days = it.days,
                    exceptions = it.exceptions
                )
            }
        )
        performOperation(baseOperation, operationRequest)
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