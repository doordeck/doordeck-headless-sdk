package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.MissingOperationContextException
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
import com.doordeck.multiplatform.sdk.util.Crypto.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.Crypto.signWithPrivateKey
import com.doordeck.multiplatform.sdk.util.addRequestHeaders
import com.doordeck.multiplatform.sdk.util.toJson
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.setBody

open class LockOperationsClient(
    private val httpClient: HttpClient,
    private val contextManager: ContextManagerImpl,
    private val localUnlockClient: LocalUnlockClient
) : AbstractResourceImpl() {

    /**
     * Get a single lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-single-lock">API Doc</a>
     */
    suspend fun getSingleLockRequest(lockId: String): LockResponse {
        return httpClient.get(Paths.getSingleLockPath(lockId)){
            addRequestHeaders(contentType = null, apiVersion = ApiVersion.VERSION_3)
        }
    }

    /**
     * Get lock audit trail
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-lock-audit-trail-v2">API Doc</a>
     */
    suspend fun getLockAuditTrailRequest(lockId: String, start: Int, end: Int): List<LockAuditTrailResponse> {
        return httpClient.get(Paths.getLockAuditTrailPath(lockId)) {
            addRequestHeaders(contentType = null, apiVersion = ApiVersion.VERSION_2)
            parameter(Params.START, start)
            parameter(Params.END, end)
        }
    }

    /**
     * Get audit for a user
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-audit-for-a-user">API Doc</a>
     */
    suspend fun getAuditForUserRequest(userId: String, start: Int, end: Int): List<UserAuditResponse> {
        return httpClient.get(Paths.getAuditForUserPath(userId)) {
            addRequestHeaders(contentType = null, apiVersion = ApiVersion.VERSION_2)
            parameter(Params.START, start)
            parameter(Params.END, end)
        }
    }

    /**
     * Get users for a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-users-for-a-lock">API Doc</a>
     */
    suspend fun getUsersForLockRequest(lockId: String): List<UserLockResponse> {
        return httpClient.get(Paths.getUsersForLockPath(lockId))
    }

    /**
     * Get locks for a user
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-for-a-user">API Doc</a>
     */
    suspend fun getLocksForUserRequest(userId: String): LockUserResponse {
        return httpClient.get(Paths.getLocksForUserPath(userId))
    }

    /**
     * Update lock properties - Name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    suspend fun updateLockNameRequest(lockId: String, name: String?) {
        updateLockProperties(lockId, UpdateLockNameRequest(name))
    }

    /**
     * Update lock properties - Favourite
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    suspend fun updateLockFavouriteRequest(lockId: String, favourite: Boolean?) {
        updateLockProperties(lockId, UpdateLockFavouriteRequest(favourite))
    }

    /**
     * Update lock properties - Colour
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    suspend fun updateLockColourRequest(lockId: String, colour: String?) {
        updateLockProperties(lockId, UpdateLockColourRequest(colour))
    }

    /**
     * Update lock properties - Settings - Default name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    suspend fun updateLockSettingDefaultNameRequest(lockId: String, name: String?) {
        updateLockProperties(lockId, UpdateLockSettingRequest(LockSettingsDefaultNameRequest(name)))
    }

    /**
     * Set lock properties - Settings - Permitted addresses
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    suspend fun setLockSettingPermittedAddressesRequest(lockId: String, permittedAddresses: List<String>) {
        updateLockProperties(lockId, UpdateLockSettingRequest(LockSettingsPermittedAddressesRequest(permittedAddresses)))
    }

    /**
     * Update lock properties - Settings - Hidden
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    suspend fun updateLockSettingHiddenRequest(lockId: String, hidden: Boolean) {
        updateLockProperties(lockId, UpdateLockSettingRequest(LockSettingsHiddenRequest(hidden)))
    }

    /**
     * Set lock properties - Settings - Usage requirements - Time
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    suspend fun setLockSettingTimeRestrictionsRequest(lockId: String, times: List<LockOperations.TimeRequirement>) {
        updateLockProperties(lockId, UpdateLockSettingRequest(
            UpdateLockSettingUsageRequirementRequest(UpdateLockSettingTimeUsageRequirementRequest(
                time = times.map { TimeRequirementRequest(it.start, it.end, it.timezone, it.days.toList()) }
            ))
        ))
    }

    /**
     * Update lock properties - Settings - Usage requirements - Location
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    suspend fun updateLockSettingLocationRestrictionsRequest(lockId: String, location: LockOperations.LocationRequirement?) {
        updateLockProperties(lockId, UpdateLockSettingRequest(
            UpdateLockSettingUsageRequirementRequest(UpdateLockSettingLocationUsageRequirementRequest(
                location?.let { LocationRequirementRequest(it.latitude, it.longitude, it.enabled, it.radius, it.accuracy) }
            ))
        ))
    }

    private suspend fun updateLockProperties(lockId: String, request: UpdateLockPropertiesRequest) {
        httpClient.put<Unit>(Paths.getUpdateLockPropertiesPath(lockId)) {
            addRequestHeaders()
            setBody(request)
        }
    }

    /**
     * Get user’s public key
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-doordeck-user-s-public-key">API Doc</a>
     */
    @DoordeckOnly
    suspend fun getUserPublicKeyRequest(userEmail: String, visitor: Boolean): UserPublicKeyResponse {
        return httpClient.post(Paths.getUserPublicKeyPath(userEmail)) {
            addRequestHeaders()
            parameter(Params.VISITOR, visitor)
        }
    }

    /**
     * Get a user’s public key by email
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-user-s-public-key">API Doc</a>
     */
    suspend fun getUserPublicKeyByEmailRequest(email: String): UserPublicKeyResponse =
        getUserPublicKey(UserPublicKeyRequest(email = email))

    /**
     * Get a user’s public key by telephone
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-user-s-public-key">API Doc</a>
     */
    suspend fun getUserPublicKeyByTelephoneRequest(telephone: String): UserPublicKeyResponse =
        getUserPublicKey(UserPublicKeyRequest(telephone = telephone))

    /**
     * Get a user’s public key by local key
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-user-s-public-key">API Doc</a>
     */
    suspend fun getUserPublicKeyByLocalKeyRequest(localKey: String): UserPublicKeyResponse =
        getUserPublicKey(UserPublicKeyRequest(localKey = localKey))

    /**
     * Get a user’s public key by foreign key
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-user-s-public-key">API Doc</a>
     */
    suspend fun getUserPublicKeyByForeignKeyRequest(foreignKey: String): UserPublicKeyResponse =
        getUserPublicKey(UserPublicKeyRequest(foreignKey = foreignKey))

    /**
     * Get a user’s public key
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-user-s-public-key">API Doc</a>
     */
    suspend fun getUserPublicKeyByIdentityRequest(identity: String): UserPublicKeyResponse =
        getUserPublicKey(UserPublicKeyRequest(identity = identity))

    private suspend fun getUserPublicKey(request: UserPublicKeyRequest): UserPublicKeyResponse {
        return httpClient.post(Paths.getUserPublicKeyPath()) {
            addRequestHeaders()
            setBody(request)
        }
    }

    /**
     * Unlock
     * @throws MissingOperationContextException if the operation context has not been set
     *
     * @see <a href="https://developer.doordeck.com/docs/#unlock">API Doc</a>
     */
    suspend fun unlockWithContextRequest(lockId: String, directAccessEndpoints: List<String>?) {
        val operationContext = contextManager.getOperationContext()
        val baseOperation = LockOperations.BaseOperation(
            userId = operationContext.userId,
            userCertificateChain = operationContext.userCertificateChain,
            userPrivateKey = operationContext.userPrivateKey,
            lockId = lockId
        )
        unlockRequest(LockOperations.UnlockOperation(baseOperation, directAccessEndpoints?.toTypedArray()))
    }

    /**
     * Unlock
     *
     * @see <a href="https://developer.doordeck.com/docs/#unlock">API Doc</a>
     */
    suspend fun unlockRequest(unlockOperation: LockOperations.UnlockOperation) {
        val operationRequest = LockOperationRequest(locked = false)
        performOperation(unlockOperation.baseOperation, operationRequest, unlockOperation.directAccessEndpoints?.toList())
    }

    /**
     * Share a lock
     * @throws MissingOperationContextException if the operation context has not been set
     *
     * @see <a href="https://developer.doordeck.com/docs/#share-a-lock">API Doc</a>
     */
    suspend fun shareLockWithContextRequest(lockId: String, shareLock: LockOperations.ShareLock) {
        val operationContext = contextManager.getOperationContext()
        val baseOperation = LockOperations.BaseOperation(
            userId = operationContext.userId,
            userCertificateChain = operationContext.userCertificateChain,
            userPrivateKey = operationContext.userPrivateKey,
            lockId = lockId
        )
        shareLock(baseOperation, shareLock)
    }

    /**
     * Share a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#share-a-lock">API Doc</a>
     */
    suspend fun shareLockRequest(shareLockOperation: LockOperations.ShareLockOperation) {
        shareLock(shareLockOperation.baseOperation, shareLockOperation.shareLock)
    }

    private suspend fun shareLock(baseOperation: LockOperations.BaseOperation, shareLock: LockOperations.ShareLock) {
        val operationRequest = ShareLockOperationRequest(
            user = shareLock.targetUserId,
            publicKey = shareLock.targetUserPublicKey.encodeByteArrayToBase64(),
            role = shareLock.targetUserRole,
            start = shareLock.start,
            end = shareLock.end
        )
        performOperation(baseOperation, operationRequest)
    }

    /**
     * Revoke access to a lock
     * @throws MissingOperationContextException if the operation context has not been set
     *
     * @see <a href="https://developer.doordeck.com/docs/#revoke-access-to-a-lock">API Doc</a>
     */
    suspend fun revokeAccessToLockWithContextRequest(lockId: String, users: List<String>) {
        val operationContext = contextManager.getOperationContext()
        val baseOperation = LockOperations.BaseOperation(
            userId = operationContext.userId,
            userCertificateChain = operationContext.userCertificateChain,
            userPrivateKey = operationContext.userPrivateKey,
            lockId = lockId
        )
        revokeAccessToLock(baseOperation, users)
    }

    /**
     * Revoke access to a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#revoke-access-to-a-lock">API Doc</a>
     */
    suspend fun revokeAccessToLockRequest(revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation) {
        revokeAccessToLock(revokeAccessToLockOperation.baseOperation, revokeAccessToLockOperation.users.toList())
    }

    private suspend fun revokeAccessToLock(baseOperation: LockOperations.BaseOperation, users: List<String>) {
        val operationRequest = RevokeAccessToALockOperationRequest(users = users)
        performOperation(baseOperation, operationRequest)
    }

    /**
     * Update secure settings - Unlock duration
     * @throws MissingOperationContextException if the operation context has not been set
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-secure-settings">API Doc</a>
     */
    suspend fun updateSecureSettingUnlockDurationWithContextRequest(lockId: String, unlockDuration: Int) {
        val operationContext = contextManager.getOperationContext()
        val baseOperation = LockOperations.BaseOperation(
            userId = operationContext.userId,
            userCertificateChain = operationContext.userCertificateChain,
            userPrivateKey = operationContext.userPrivateKey,
            lockId = lockId
        )
        updateSecureSettingUnlockDuration(baseOperation, unlockDuration)
    }

    /**
     * Update secure settings - Unlock duration
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-secure-settings">API Doc</a>
     */
    suspend fun updateSecureSettingUnlockDurationRequest(updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration) {
        updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration.baseOperation, updateSecureSettingUnlockDuration.unlockDuration)
    }

    private suspend fun updateSecureSettingUnlockDuration(baseOperation: LockOperations.BaseOperation, unlockDuration: Int) {
        val operationRequest = UpdateSecureSettingsOperationRequest(
            unlockDuration = unlockDuration
        )
        performOperation(baseOperation, operationRequest)
    }

    /**
     * Update secure settings - Unlock between
     * @throws MissingOperationContextException if the operation context has not been set
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-secure-settings">API Doc</a>
     */
    suspend fun updateSecureSettingUnlockBetweenWithContextRequest(lockId: String, unlockBetween: LockOperations.UnlockBetween?) {
        val operationContext = contextManager.getOperationContext()
        val baseOperation = LockOperations.BaseOperation(
            userId = operationContext.userId,
            userCertificateChain = operationContext.userCertificateChain,
            userPrivateKey = operationContext.userPrivateKey,
            lockId = lockId
        )
        updateSecureSettingUnlockBetween(baseOperation, unlockBetween)
    }

    /**
     * Update secure settings - Unlock between
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-secure-settings">API Doc</a>
     */
    suspend fun updateSecureSettingUnlockBetweenRequest(updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween) {
        updateSecureSettingUnlockBetween(updateSecureSettingUnlockBetween.baseOperation, updateSecureSettingUnlockBetween.unlockBetween)
    }

    private suspend fun updateSecureSettingUnlockBetween(baseOperation: LockOperations.BaseOperation, unlockBetween: LockOperations.UnlockBetween?) {
        val operationRequest = UpdateSecureSettingsOperationRequest(
            unlockBetween = unlockBetween?.let {
                UnlockBetweenSettingRequest(
                    start = it.start,
                    end = it.end,
                    timezone = it.timezone,
                    days = it.days.toList(),
                    exceptions = it.exceptions?.toList()
                )
            }
        )
        performOperation(baseOperation, operationRequest)
    }

    private suspend fun performOperation(baseOperation: LockOperations.BaseOperation, operationRequest: OperationRequest,
                                 directAccessEndpoints: List<String>? = null) {
        val operationHeader = OperationHeaderRequest(x5c = baseOperation.userCertificateChain.toList())
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

        // Launch the calls to the direct access endpoints
        if (operationRequest is LockOperationRequest && !directAccessEndpoints.isNullOrEmpty()) {
            localUnlockClient.unlock(directAccessEndpoints, body)
        }

        httpClient.post<Unit>(Paths.getOperationPath(baseOperation.lockId)) {
            addRequestHeaders(true)
            setBody(body)
        }
    }

    /**
     * Get pinned locks
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-pinned-locks">API Doc</a>
     */
    suspend fun getPinnedLocksRequest(): List<LockResponse> {
        return httpClient.get(Paths.getPinnedLocksPath())
    }

    /**
     * Get shareable locks
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-shareable-locks">API Doc</a>
     */
    suspend fun getShareableLocksRequest(): List<ShareableLockResponse> {
        return httpClient.get(Paths.getShareableLocksPath())
    }
}