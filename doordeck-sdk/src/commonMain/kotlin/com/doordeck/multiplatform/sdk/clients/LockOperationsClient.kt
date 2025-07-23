package com.doordeck.multiplatform.sdk.clients

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.cache.CapabilityCache
import com.doordeck.multiplatform.sdk.context.Context
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.signWithPrivateKey
import com.doordeck.multiplatform.sdk.exceptions.BatchShareFailedException
import com.doordeck.multiplatform.sdk.exceptions.MissingContextFieldException
import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.model.common.CapabilityType
import com.doordeck.multiplatform.sdk.model.data.LockOperations
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
import com.doordeck.multiplatform.sdk.model.values.toPlatformLocalTimeString
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

/**
 * Internal implementation of the lock operations API client.
 * Handles all network requests related to lock management and operations.
 */
internal object LockOperationsClient {
    /**
     * Retrieves a single lock by its ID.
     *
     * @param lockId The unique identifier of the lock.
     * @return [LockResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-single-lock">API Doc</a>
     */
    suspend fun getSingleLockRequest(lockId: String): LockResponse {
        return CloudHttpClient.client.get(Paths.getSingleLockPath(lockId)) {
            addRequestHeaders(contentType = null, apiVersion = ApiVersion.VERSION_3)
        }.body()
    }

    /**
     * Retrieves all log events associated with a particular lock.
     *
     * @param lockId The lock's unique identifier.
     * @param start Start of the date range (epoch timestamp).
     * @param end End of date range (epoch timestamp).
     * @return List of [AuditResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-lock-audit-trail-v2">API Doc</a>
     */
    suspend fun getLockAuditTrailRequest(lockId: String, start: Long, end: Long): List<AuditResponse> {
        return CloudHttpClient.client.get(Paths.getLockAuditTrailPath(lockId)) {
            addRequestHeaders(contentType = null, apiVersion = ApiVersion.VERSION_2)
            parameter(Params.START, start)
            parameter(Params.END, end)
        }.body()
    }

    /**
     * Retrieves all log events associated with a particular user.
     *
     * @param userId The user's unique identifier.
     * @param start Start of the date range (epoch timestamp).
     * @param end End of date range (epoch timestamp).
     * @return List of [AuditResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-audit-for-a-user">API Doc</a>
     */
    suspend fun getAuditForUserRequest(userId: String, start: Long, end: Long): List<AuditResponse> {
        return CloudHttpClient.client.get(Paths.getAuditForUserPath(userId)) {
            addRequestHeaders(contentType = null, apiVersion = ApiVersion.VERSION_2)
            parameter(Params.START, start)
            parameter(Params.END, end)
        }.body()
    }

    /**
     * Retrieves all users associated with a particular lock.
     *
     * @param lockId The lock's unique identifier.
     * @return List of [UserLockResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-users-for-a-lock">API Doc</a>
     */
    suspend fun getUsersForLockRequest(lockId: String): List<UserLockResponse> {
        return CloudHttpClient.client.get(Paths.getUsersForLockPath(lockId)).body()
    }

    /**
     * Retrieves basic user information, including the user's public key and all locks for this particular user.
     * The list will contain only the locks where the current user is an administrator.
     *
     * @param userId The user's unique identifier.
     * @return [LockUserResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-for-a-user">API Doc</a>
     */
    suspend fun getLocksForUserRequest(userId: String): LockUserResponse {
        return CloudHttpClient.client.get(Paths.getLocksForUserPath(userId)).body()
    }

    /**
     * Updates the user's alias for the lock.
     *
     * @param lockId The lock's unique identifier.
     * @param name The new alias for the lock (use `null` to remove the existing alias).
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    suspend fun updateLockNameRequest(lockId: String, name: String?) {
        updateLockProperties(lockId, UpdateLockNameRequest(name))
    }

    /**
     * Updates the lock's favorite flag for the current user.
     *
     * @param lockId The lock's unique identifier.
     * @param favourite `true` to mark the lock as a favorite, `false` or `null` to remove the favorite status.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    suspend fun updateLockFavouriteRequest(lockId: String, favourite: Boolean?) {
        updateLockProperties(lockId, UpdateLockFavouriteRequest(favourite))
    }

    /**
     * Updates the lock's display colour.
     *
     * @param lockId The lock's unique identifier.
     * @param colour Hex representation of the colour (e.g., "#FF5733"), use `null` to remove the colour.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    suspend fun updateLockColourRequest(lockId: String, colour: String?) {
        updateLockProperties(lockId, UpdateLockColourRequest(colour))
    }

    /**
     * Updates the lock's default name (visible to all users without a custom alias).
     *
     * @param lockId The lock's unique identifier.
     * @param name The new lock's name, use `null` to remove the default name.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    suspend fun updateLockSettingDefaultNameRequest(lockId: String, name: String?) {
        updateLockProperties(lockId, UpdateLockSettingRequest(LockSettingsDefaultNameRequest(name)))
    }

    /**
     * Updates the list of permitted public IPs allowed to control this lock.
     *
     * @param lockId The lock's unique identifier.
     * @param permittedAddresses The full list of allowed public IP addresses (replaces any existing list).
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    suspend fun setLockSettingPermittedAddressesRequest(lockId: String, permittedAddresses: List<String>) {
        updateLockProperties(
            lockId = lockId,
            request = UpdateLockSettingRequest(LockSettingsPermittedAddressesRequest(permittedAddresses))
        )
    }

    /**
     * Sets whether the device should be hidden from the favorites list.
     *
     * @param lockId The lock's unique identifier.
     * @param hidden `true` to hide from favorites, `false` to make it visible.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    suspend fun updateLockSettingHiddenRequest(lockId: String, hidden: Boolean) {
        updateLockProperties(lockId, UpdateLockSettingRequest(LockSettingsHiddenRequest(hidden)))
    }

    /**
     * Configures time-based usage restrictions for the lock.
     *
     * @param lockId The lock's unique identifier.
     * @param times List of allowed time windows (replaces existing restrictions).
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    suspend fun setLockSettingTimeRestrictionsRequest(lockId: String, times: List<LockOperations.TimeRequirement>) {
        updateLockProperties(
            lockId = lockId,
            request = UpdateLockSettingRequest(
                settings = UpdateLockSettingUsageRequirementRequest(
                    usageRequirements = UpdateLockSettingTimeUsageRequirementRequest(
                        time = times.map {
                            TimeRequirementRequest(
                                start = it.start.toPlatformLocalTimeString(),
                                end = it.end.toPlatformLocalTimeString(),
                                timezone = it.timezone,
                                days = it.days
                            )
                        }
                    )
                )
            )
        )
    }

    /**
     * Configures geofence-based access restrictions for the lock.
     *
     * @param lockId The lock's unique identifier.
     * @param location The geofence configuration (use `null` to remove existing restrictions).
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    suspend fun updateLockSettingLocationRestrictionsRequest(
        lockId: String,
        location: LockOperations.LocationRequirement?
    ) {
        updateLockProperties(
            lockId = lockId,
            request = UpdateLockSettingRequest(
                settings = UpdateLockSettingUsageRequirementRequest(
                    usageRequirements = UpdateLockSettingLocationUsageRequirementRequest(
                        location = location?.let {
                            LocationRequirementRequest(
                                latitude = it.latitude,
                                longitude = it.longitude,
                                enabled = it.enabled,
                                radius = it.radius,
                                accuracy = it.accuracy
                            )
                        }
                    )
                )
            )
        )
    }

    /**
     * Handles the request to update a property of an existing lock.
     *
     * @param lockId The lock's unique identifier.
     * @param request The specific [UpdateLockPropertiesRequest] request to be handled.
     * @throws SdkException if an unexpected error occurs while processing the request.
     */
    private suspend fun updateLockProperties(lockId: String, request: UpdateLockPropertiesRequest) {
        CloudHttpClient.client.put(Paths.getUpdateLockPropertiesPath(lockId)) {
            addRequestHeaders()
            setBody(request)
        }
    }

    /**
     * Retrieves the user's public key along with their ID. If the user is not found, one is created for the specified email.
     *
     * @param userEmail The user's email address.
     * @param visitor Defaults to `false`, set to `true` to direct the visitor to a purely web based experience.
     * @return [UserPublicKeyResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
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
     * Retrieves a user's public key using their email address.
     *
     * @param email The user's email address.
     * @return [UserPublicKeyResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    suspend fun getUserPublicKeyByEmailRequest(email: String): UserPublicKeyResponse =
        getUserPublicKey(UserPublicKeyRequest(email = email))

    /**
     * Retrieves a user's public key using their telephone number.
     *
     * @param telephone The user's telephone number.
     * @return [UserPublicKeyResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    suspend fun getUserPublicKeyByTelephoneRequest(telephone: String): UserPublicKeyResponse =
        getUserPublicKey(UserPublicKeyRequest(telephone = telephone))

    /**
     * Retrieves a user's public key using their local key.
     *
     * @param localKey The user's local key.
     * @return [UserPublicKeyResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    suspend fun getUserPublicKeyByLocalKeyRequest(localKey: String): UserPublicKeyResponse =
        getUserPublicKey(UserPublicKeyRequest(localKey = localKey))

    /**
     * Retrieves a user's public key using their third-party application's identifier for a user.
     *
     * @param foreignKey The user's third-party application's identifier.
     * @return [UserPublicKeyResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    suspend fun getUserPublicKeyByForeignKeyRequest(foreignKey: String): UserPublicKeyResponse =
        getUserPublicKey(UserPublicKeyRequest(foreignKey = foreignKey))

    /**
     * Retrieves a user's public key using their encrypted OpenID token of user.
     *
     * @param identity The user's encrypted OpenID token of user.
     * @return [UserPublicKeyResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    suspend fun getUserPublicKeyByIdentityRequest(identity: String): UserPublicKeyResponse =
        getUserPublicKey(UserPublicKeyRequest(identity = identity))

    /**
     * Handles the public key request of an existing user.
     *
     * @param request The specific [UserPublicKeyRequest] request to be handled.
     * @throws SdkException if an unexpected error occurs while processing the request.
     */
    private suspend fun getUserPublicKey(request: UserPublicKeyRequest): UserPublicKeyResponse {
        return CloudHttpClient.client.post(Paths.getUserPublicKeyPath()) {
            addRequestHeaders()
            setBody(request)
        }.body()
    }

    /**
     * Retrieves public keys for up to 25 users by their email addresses.
     *
     * @param emails List of user email addresses (max 25 entries).
     * @return List of [BatchUserPublicKeyResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v2">API Doc</a>
     */
    suspend fun getUserPublicKeyByEmailsRequest(emails: List<String>): List<BatchUserPublicKeyResponse> =
        batchGetUserPublicKey(BatchUserPublicKeyRequest(email = emails))

    /**
     * Retrieves public keys for up to 25 users by their telephone numbers.
     *
     * @param telephones List of user email addresses (max 25 entries).
     * @return List of [BatchUserPublicKeyResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v2">API Doc</a>
     */
    suspend fun getUserPublicKeyByTelephonesRequest(telephones: List<String>): List<BatchUserPublicKeyResponse> =
        batchGetUserPublicKey(BatchUserPublicKeyRequest(telephone = telephones))

    /**
     * Retrieves public keys for up to 25 users by their local keys.
     *
     * @param localKeys List of user local keys (max 25 entries).
     * @return List of [BatchUserPublicKeyResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v2">API Doc</a>
     */
    suspend fun getUserPublicKeyByLocalKeysRequest(localKeys: List<String>): List<BatchUserPublicKeyResponse> =
        batchGetUserPublicKey(BatchUserPublicKeyRequest(localKey = localKeys))

    /**
     * Retrieves public keys for up to 25 users by their third-party application's identifier for a user.
     *
     * @param foreignKeys List of user third-party application's identifiers (max 25 entries).
     * @return List of [BatchUserPublicKeyResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v2">API Doc</a>
     */
    suspend fun getUserPublicKeyByForeignKeysRequest(foreignKeys: List<String>): List<BatchUserPublicKeyResponse> =
        batchGetUserPublicKey(BatchUserPublicKeyRequest(foreignKey = foreignKeys))

    /**
     * Handles the batch public key request of existing users.
     *
     * @param request The specific [BatchUserPublicKeyRequest] request to be handled.
     * @return List of [BatchUserPublicKeyResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     */
    private suspend fun batchGetUserPublicKey(request: BatchUserPublicKeyRequest): List<BatchUserPublicKeyResponse> {
        return CloudHttpClient.client.post(Paths.getUserPublicKeyPath()) {
            addRequestHeaders(apiVersion = ApiVersion.VERSION_2)
            setBody(request)
        }.body()
    }

    /**
     * Unlocks a device.
     *
     * @param unlockOperation The specific [LockOperations.UnlockOperation] to be handled.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#unlock">API Doc</a>
     */
    suspend fun unlockRequest(unlockOperation: LockOperations.UnlockOperation) {
        val operationRequest = LockOperationRequest(locked = false)
        val baseOperationRequest = unlockOperation.baseOperation.toBaseOperationRequestUsingContext()
        performOperation(baseOperationRequest, operationRequest, unlockOperation.directAccessEndpoints)
    }

    /**
     * Shares access to a device with another user.
     *
     * @param shareLockOperation The specific [LockOperations.ShareLockOperation] to be handled.
     * @throws SdkException if an unexpected error occurs while processing the request.
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
     * Shares device access with multiple users in a single batch operation.
     * Falls back to sequential sharing if batch operations aren't supported by the lock.
     *
     * @param batchShareLockOperation The specific [LockOperations.BatchShareLockOperation] to be handled.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#batch-share-a-lock-v2">API Doc</a>
     */
    suspend fun batchShareLockRequest(batchShareLockOperation: LockOperations.BatchShareLockOperation) {
        /** Verify whether the operation device currently supports the batch sharing operation **/
        val isSupported =
            CapabilityCache.isSupported(batchShareLockOperation.baseOperation.lockId, CapabilityType.BATCH_SHARING_25)
                ?: getSingleLockRequest(batchShareLockOperation.baseOperation.lockId).also {
                    CapabilityCache.put(batchShareLockOperation.baseOperation.lockId, it.settings.capabilities)
                }.settings.capabilities.containsKey(CapabilityType.BATCH_SHARING_25)

        /** If the device does not support the batch sharing operation, we will call the single-user sharing operation for each user individually **/
        if (!isSupported) {
            val failedOperations = batchShareLockOperation.users.mapNotNull { shareLock ->
                try {
                    shareLockRequest(
                        shareLockOperation = LockOperations.ShareLockOperation(
                            baseOperation = batchShareLockOperation.baseOperation.copy(jti = Uuid.random().toString()),
                            shareLock = shareLock
                        )
                    )
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
     * Revokes a user's access permissions to a specific lock.
     *
     * @param revokeAccessToLockOperation The specific [LockOperations.RevokeAccessToLockOperation] to be handled.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#revoke-access-to-a-lock">API Doc</a>
     */
    suspend fun revokeAccessToLockRequest(revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation) {
        val operationRequest = RevokeAccessToALockOperationRequest(users = revokeAccessToLockOperation.users)
        val baseOperationRequest = revokeAccessToLockOperation.baseOperation.toBaseOperationRequestUsingContext()
        performOperation(baseOperationRequest, operationRequest)
    }

    /**
     * Updates the unlock duration setting (how long lock stays unlocked).
     *
     * @param updateSecureSettingUnlockDuration The specific [LockOperations.UpdateSecureSettingUnlockDuration] to be handled.
     * @throws SdkException if an unexpected error occurs while processing the request.
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
     * Updates the unlock between definition.
     *
     * @param updateSecureSettingUnlockBetween The specific [LockOperations.UpdateSecureSettingUnlockBetween] to be handled.
     * @throws SdkException if an unexpected error occurs while processing the request.
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

    /**
     * Handles a secure operation by constructing and signing a JWT request,
     * then dispatching it to both cloud and (optionally) direct access endpoints.
     *
     * @throws SdkException if an unexpected error occurs while processing the request.
     */
    private suspend fun performOperation(
        baseOperationRequest: BaseOperationRequest, operationRequest: OperationRequest,
        directAccessEndpoints: List<String>? = null
    ) {
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
        val signatureB64 =
            "$headerB64.$bodyB64".signWithPrivateKey(baseOperationRequest.userPrivateKey).encodeByteArrayToBase64()
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
     * Retrieves all pinned locks for the current user.
     *
     * @return List of [LockResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-pinned-locks">API Doc</a>
     */
    suspend fun getPinnedLocksRequest(): List<LockResponse> {
        return CloudHttpClient.client.get(Paths.getPinnedLocksPath()).body()
    }

    /**
     * Retrieves all locks where the current user has administrator privileges.
     * @return List of [ShareableLockResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-shareable-locks">API Doc</a>
     */
    suspend fun getShareableLocksRequest(): List<ShareableLockResponse> {
        return CloudHttpClient.client.get(Paths.getShareableLocksPath()).body()
    }

    /**
     * Converts a [LockOperations.BaseOperation] to a [BaseOperationRequest].
     * Validates that required fields (userId, userCertificateChain, userPrivateKey) are available
     * either in the input or the [ContextManagerImpl].
     *
     * @return [BaseOperationRequest].
     * @throws MissingContextFieldException if any required field (userId, userCertificateChain,
     *         or userPrivateKey) is missing from both the input and the [ContextManagerImpl].
     */
    private fun LockOperations.BaseOperation.toBaseOperationRequestUsingContext(): BaseOperationRequest {
        val userId = userId
            ?: Context.getUserId()
            ?: throw MissingContextFieldException("User ID is missing")
        val userCertificateChain = userCertificateChain
            ?: Context.getCertificateChain()
            ?: throw MissingContextFieldException("Certificate chain is missing")
        val userPrivateKey = userPrivateKey
            ?: Context.getPrivateKey()
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