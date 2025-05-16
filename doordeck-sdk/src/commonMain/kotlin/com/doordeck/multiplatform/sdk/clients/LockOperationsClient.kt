package com.doordeck.multiplatform.sdk.clients

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.cache.CapabilityCache
import com.doordeck.multiplatform.sdk.context.ContextManagerImpl
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.signWithPrivateKey
import com.doordeck.multiplatform.sdk.exceptions.BatchShareFailedException
import com.doordeck.multiplatform.sdk.exceptions.MissingContextFieldException
import com.doordeck.multiplatform.sdk.model.data.LockOperations
import com.doordeck.multiplatform.sdk.model.common.CapabilityType
import com.doordeck.multiplatform.sdk.model.network.ApiVersion
import com.doordeck.multiplatform.sdk.model.network.Params
import com.doordeck.multiplatform.sdk.model.network.Paths
import com.doordeck.multiplatform.sdk.model.requests.BaseOperationRequest
import com.doordeck.multiplatform.sdk.model.requests.BatchShareLockOperationRequest
import com.doordeck.multiplatform.sdk.model.requests.BatchUserPublicKeyRequest
import com.doordeck.multiplatform.sdk.model.requests.LocationRequirementRequest
import com.doordeck.multiplatform.sdk.model.requests.LockOperationRequest
import com.doordeck.multiplatform.sdk.model.requests.LockSettingsDefaultNameRequest
import com.doordeck.multiplatform.sdk.model.requests.LockSettingsHiddenRequest
import com.doordeck.multiplatform.sdk.model.requests.LockSettingsPermittedAddressesRequest
import com.doordeck.multiplatform.sdk.model.requests.OperationBodyRequest
import com.doordeck.multiplatform.sdk.model.requests.OperationHeaderRequest
import com.doordeck.multiplatform.sdk.model.requests.OperationRequest
import com.doordeck.multiplatform.sdk.model.requests.RevokeAccessToALockOperationRequest
import com.doordeck.multiplatform.sdk.model.requests.ShareLockOperationRequest
import com.doordeck.multiplatform.sdk.model.requests.TimeRequirementRequest
import com.doordeck.multiplatform.sdk.model.requests.UnlockBetweenSettingRequest
import com.doordeck.multiplatform.sdk.model.requests.UpdateLockColourRequest
import com.doordeck.multiplatform.sdk.model.requests.UpdateLockFavouriteRequest
import com.doordeck.multiplatform.sdk.model.requests.UpdateLockNameRequest
import com.doordeck.multiplatform.sdk.model.requests.UpdateLockPropertiesRequest
import com.doordeck.multiplatform.sdk.model.requests.UpdateLockSettingLocationUsageRequirementRequest
import com.doordeck.multiplatform.sdk.model.requests.UpdateLockSettingRequest
import com.doordeck.multiplatform.sdk.model.requests.UpdateLockSettingTimeUsageRequirementRequest
import com.doordeck.multiplatform.sdk.model.requests.UpdateLockSettingUsageRequirementRequest
import com.doordeck.multiplatform.sdk.model.requests.UpdateSecureSettingsOperationRequest
import com.doordeck.multiplatform.sdk.model.requests.UserPublicKeyRequest
import com.doordeck.multiplatform.sdk.model.responses.AuditResponse
import com.doordeck.multiplatform.sdk.model.responses.BatchUserPublicKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.LockResponse
import com.doordeck.multiplatform.sdk.model.responses.LockUserResponse
import com.doordeck.multiplatform.sdk.model.responses.ShareableLockResponse
import com.doordeck.multiplatform.sdk.model.responses.UserLockResponse
import com.doordeck.multiplatform.sdk.model.responses.UserPublicKeyResponse
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.addRequestHeaders
import com.doordeck.multiplatform.sdk.util.toJson
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import kotlin.uuid.Uuid

internal object LockOperationsClient {

    /**
     * Get a single lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-single-lock">API Doc</a>
     */
    suspend fun getSingleLockRequest(lockId: String): LockResponse {
        return CloudHttpClient.client.get(Paths.getSingleLockPath(lockId)) {
            addRequestHeaders(contentType = null, apiVersion = ApiVersion.VERSION_3)
        }.body()
    }

    /**
     * Get lock audit trail
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-lock-audit-trail-v2">API Doc</a>
     */
    suspend fun getLockAuditTrailRequest(lockId: String, start: Int, end: Int): List<AuditResponse> {
        return CloudHttpClient.client.get(Paths.getLockAuditTrailPath(lockId)) {
            addRequestHeaders(contentType = null, apiVersion = ApiVersion.VERSION_2)
            parameter(Params.START, start)
            parameter(Params.END, end)
        }.body()
    }

    /**
     * Get audit for a user
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-audit-for-a-user">API Doc</a>
     */
    suspend fun getAuditForUserRequest(userId: String, start: Int, end: Int): List<AuditResponse> {
        return CloudHttpClient.client.get(Paths.getAuditForUserPath(userId)) {
            addRequestHeaders(contentType = null, apiVersion = ApiVersion.VERSION_2)
            parameter(Params.START, start)
            parameter(Params.END, end)
        }.body()
    }

    /**
     * Get users for a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-users-for-a-lock">API Doc</a>
     */
    suspend fun getUsersForLockRequest(lockId: String): List<UserLockResponse> {
        return CloudHttpClient.client.get(Paths.getUsersForLockPath(lockId)).body()
    }

    /**
     * Get locks for a user
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-for-a-user">API Doc</a>
     */
    suspend fun getLocksForUserRequest(userId: String): LockUserResponse {
        return CloudHttpClient.client.get(Paths.getLocksForUserPath(userId)).body()
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
                time = times.map { TimeRequirementRequest(it.start, it.end, it.timezone, it.days) }
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
        CloudHttpClient.client.put(Paths.getUpdateLockPropertiesPath(lockId)) {
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
        return CloudHttpClient.client.post(Paths.getUserPublicKeyPath(userEmail)) {
            addRequestHeaders()
            parameter(Params.VISITOR, visitor)
        }.body()
    }

    /**
     * Get a user’s public key by email
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    suspend fun getUserPublicKeyByEmailRequest(email: String): UserPublicKeyResponse =
        getUserPublicKey(UserPublicKeyRequest(email = email))

    /**
     * Get a user’s public key by telephone
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    suspend fun getUserPublicKeyByTelephoneRequest(telephone: String): UserPublicKeyResponse =
        getUserPublicKey(UserPublicKeyRequest(telephone = telephone))

    /**
     * Get a user’s public key by local key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    suspend fun getUserPublicKeyByLocalKeyRequest(localKey: String): UserPublicKeyResponse =
        getUserPublicKey(UserPublicKeyRequest(localKey = localKey))

    /**
     * Get a user’s public key by foreign key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    suspend fun getUserPublicKeyByForeignKeyRequest(foreignKey: String): UserPublicKeyResponse =
        getUserPublicKey(UserPublicKeyRequest(foreignKey = foreignKey))

    /**
     * Get a user’s public key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    suspend fun getUserPublicKeyByIdentityRequest(identity: String): UserPublicKeyResponse =
        getUserPublicKey(UserPublicKeyRequest(identity = identity))

    private suspend fun getUserPublicKey(request: UserPublicKeyRequest): UserPublicKeyResponse {
        return CloudHttpClient.client.post(Paths.getUserPublicKeyPath()) {
            addRequestHeaders()
            setBody(request)
        }.body()
    }

    /**
     * Get a user’s public key by email
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v2">API Doc</a>
     */
    suspend fun getUserPublicKeyByEmailsRequest(emails: List<String>): List<BatchUserPublicKeyResponse> =
        batchGetUserPublicKey(BatchUserPublicKeyRequest(email = emails))

    /**
     * Get a user’s public key by telephone
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v2">API Doc</a>
     */
    suspend fun getUserPublicKeyByTelephonesRequest(telephones: List<String>): List<BatchUserPublicKeyResponse> =
        batchGetUserPublicKey(BatchUserPublicKeyRequest(telephone = telephones))

    /**
     * Get a user’s public key by local key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v2">API Doc</a>
     */
    suspend fun getUserPublicKeyByLocalKeysRequest(localKeys: List<String>): List<BatchUserPublicKeyResponse> =
        batchGetUserPublicKey(BatchUserPublicKeyRequest(localKey = localKeys))

    /**
     * Get a user’s public key by foreign key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v2">API Doc</a>
     */
    suspend fun getUserPublicKeyByForeignKeysRequest(foreignKeys: List<String>): List<BatchUserPublicKeyResponse> =
        batchGetUserPublicKey(BatchUserPublicKeyRequest(foreignKey = foreignKeys))

    private suspend fun batchGetUserPublicKey(request: BatchUserPublicKeyRequest): List<BatchUserPublicKeyResponse> {
        return CloudHttpClient.client.post(Paths.getUserPublicKeyPath()) {
            addRequestHeaders(apiVersion = ApiVersion.VERSION_2)
            setBody(request)
        }.body()
    }

    /**
     * Unlock
     *
     * @see <a href="https://developer.doordeck.com/docs/#unlock">API Doc</a>
     */
    suspend fun unlockRequest(unlockOperation: LockOperations.UnlockOperation) {
        val operationRequest = LockOperationRequest(locked = false)
        val baseOperationRequest = unlockOperation.baseOperation.toBaseOperationRequestUsingContext()
        performOperation(baseOperationRequest, operationRequest, unlockOperation.directAccessEndpoints)
    }

    /**
     * Share a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#share-a-lock">API Doc</a>
     */
    suspend fun shareLockRequest(shareLockOperation: LockOperations.ShareLockOperation) {
        val operationRequest = ShareLockOperationRequest(
            user = shareLockOperation.shareLock.targetUserId,
            publicKey = shareLockOperation.shareLock.targetUserPublicKey.encodeByteArrayToBase64(),
            role = shareLockOperation.shareLock.targetUserRole,
            start = shareLockOperation.shareLock.start?.toLong(),
            end = shareLockOperation.shareLock.end?.toLong()
        )
        val baseOperationRequest = shareLockOperation.baseOperation.toBaseOperationRequestUsingContext()
        performOperation(baseOperationRequest, operationRequest)
    }

    /**
     * Batch share a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#batch-share-a-lock-v2">API Doc</a>
     */
    suspend fun batchShareLockRequest(batchShareLockOperation: LockOperations.BatchShareLockOperation) {
        /**
         * Verify whether the operation device currently supports the batch sharing operation
         */
        val isSupported = CapabilityCache.isSupported(batchShareLockOperation.baseOperation.lockId, CapabilityType.BATCH_SHARING_25)
            ?: getSingleLockRequest(batchShareLockOperation.baseOperation.lockId).also {
                CapabilityCache.put(batchShareLockOperation.baseOperation.lockId, it.settings.capabilities)
            }.settings.capabilities.containsKey(CapabilityType.BATCH_SHARING_25)

        /**
         * If the device does not support the batch sharing operation, we will call the single-user sharing operation for each user individually
         */
        if (!isSupported) {
            val failedOperations = batchShareLockOperation.users.mapNotNull { shareLock ->
                try {
                    shareLockRequest(
                        LockOperations.ShareLockOperation(
                        baseOperation = batchShareLockOperation.baseOperation.copy(jti = Uuid.random().toString()),
                        shareLock = shareLock
                    ))
                    null
                } catch (_: Exception) {
                    shareLock
                }
            }

            if (failedOperations.isNotEmpty()) {
                throw BatchShareFailedException("Batch share failed", failedOperations.map { it.targetUserId })
            }
            return
        }

        val operationRequest = BatchShareLockOperationRequest(
            users = batchShareLockOperation.users.map {
                ShareLockOperationRequest(
                    user = it.targetUserId,
                    publicKey = it.targetUserPublicKey.encodeByteArrayToBase64(),
                    role = it.targetUserRole,
                    start = it.start?.toLong(),
                    end = it.end?.toLong()
                )
            }
        )
        val baseOperationRequest = batchShareLockOperation.baseOperation.toBaseOperationRequestUsingContext()
        performOperation(baseOperationRequest, operationRequest)
    }

    /**
     * Revoke access to a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#revoke-access-to-a-lock">API Doc</a>
     */
    suspend fun revokeAccessToLockRequest(revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation) {
        val operationRequest = RevokeAccessToALockOperationRequest(users = revokeAccessToLockOperation.users)
        val baseOperationRequest = revokeAccessToLockOperation.baseOperation.toBaseOperationRequestUsingContext()
        performOperation(baseOperationRequest, operationRequest)
    }

    /**
     * Update secure settings - Unlock duration
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-secure-settings">API Doc</a>
     */
    suspend fun updateSecureSettingUnlockDurationRequest(updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration) {
        val operationRequest = UpdateSecureSettingsOperationRequest(
            unlockDuration = updateSecureSettingUnlockDuration.unlockDuration
        )
        val baseOperationRequest = updateSecureSettingUnlockDuration.baseOperation.toBaseOperationRequestUsingContext()
        performOperation(baseOperationRequest, operationRequest)
    }

    /**
     * Update secure settings - Unlock between
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-secure-settings">API Doc</a>
     */
    suspend fun updateSecureSettingUnlockBetweenRequest(updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween) {
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
        val baseOperationRequest = updateSecureSettingUnlockBetween.baseOperation.toBaseOperationRequestUsingContext()
        performOperation(baseOperationRequest, operationRequest)
    }

    private suspend fun performOperation(baseOperationRequest: BaseOperationRequest, operationRequest: OperationRequest,
                                         directAccessEndpoints: List<String>? = null) {
        val operationHeader = OperationHeaderRequest(x5c = baseOperationRequest.userCertificateChain)
        val operationBody = OperationBodyRequest(
            iss = baseOperationRequest.userId,
            sub = baseOperationRequest.lockId,
            nbf = baseOperationRequest.notBefore.toLong(),
            iat = baseOperationRequest.issuedAt.toLong(),
            exp = baseOperationRequest.expiresAt.toLong(),
            jti = baseOperationRequest.jti,
            operation = operationRequest
        )
        val headerB64 = operationHeader.toJson().encodeToByteArray().encodeByteArrayToBase64()
        val bodyB64 = operationBody.toJson().encodeToByteArray().encodeByteArrayToBase64()
        val signatureB64 = "$headerB64.$bodyB64".signWithPrivateKey(baseOperationRequest.userPrivateKey).encodeByteArrayToBase64()
        val body = "$headerB64.$bodyB64.$signatureB64"

        // Launch the calls to the direct access endpoints
        if (operationRequest is LockOperationRequest && !directAccessEndpoints.isNullOrEmpty()) {
            LocalUnlockClient.unlock(directAccessEndpoints, body)
        }

        CloudHttpClient.client.post(Paths.getOperationPath(baseOperationRequest.lockId)) {
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
        return CloudHttpClient.client.get(Paths.getPinnedLocksPath()).body()
    }

    /**
     * Get shareable locks
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-shareable-locks">API Doc</a>
     */
    suspend fun getShareableLocksRequest(): List<ShareableLockResponse> {
        return CloudHttpClient.client.get(Paths.getShareableLocksPath()).body()
    }

    private fun LockOperations.BaseOperation.toBaseOperationRequestUsingContext(): BaseOperationRequest {
        val userId = userId
            ?: ContextManagerImpl.getUserId()
            ?: throw MissingContextFieldException("User ID is missing")
        val userCertificateChain = userCertificateChain
            ?: ContextManagerImpl.getCertificateChain()
            ?: throw MissingContextFieldException("Certificate chain is missing")
        val userPrivateKey = userPrivateKey
            ?: ContextManagerImpl.getPrivateKey()
            ?: throw MissingContextFieldException("Private key is missing")
        return BaseOperationRequest(
            userId = userId,
            userCertificateChain = userCertificateChain,
            userPrivateKey = userPrivateKey,
            lockId = lockId,
            notBefore = notBefore,
            issuedAt = issuedAt,
            expiresAt = expiresAt,
            jti = jti
        )
    }
}