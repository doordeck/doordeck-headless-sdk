package com.doordeck.multiplatform.sdk.clients

import com.doordeck.multiplatform.sdk.CloudHttpClient
import com.doordeck.multiplatform.sdk.cache.CapabilityCache
import com.doordeck.multiplatform.sdk.context.Context
import com.doordeck.multiplatform.sdk.crypto.CryptoManager.signWithPrivateKey
import com.doordeck.multiplatform.sdk.exceptions.BatchShareFailedException
import com.doordeck.multiplatform.sdk.exceptions.MissingContextFieldException
import com.doordeck.multiplatform.sdk.exceptions.SdkException
import com.doordeck.multiplatform.sdk.model.common.CapabilityType
import com.doordeck.multiplatform.sdk.model.data.BasicBaseOperation
import com.doordeck.multiplatform.sdk.model.data.BasicBatchShareLockOperation
import com.doordeck.multiplatform.sdk.model.data.BasicLocationRequirement
import com.doordeck.multiplatform.sdk.model.data.BasicRevokeAccessToLockOperation
import com.doordeck.multiplatform.sdk.model.data.BasicShareLockOperation
import com.doordeck.multiplatform.sdk.model.data.BasicTimeRequirement
import com.doordeck.multiplatform.sdk.model.data.BasicUnlockOperation
import com.doordeck.multiplatform.sdk.model.data.BasicUpdateSecureSettingUnlockBetween
import com.doordeck.multiplatform.sdk.model.data.BasicUpdateSecureSettingUnlockDuration
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
import com.doordeck.multiplatform.sdk.model.requests.UpdateLockFavouriteRequest
import com.doordeck.multiplatform.sdk.model.requests.UpdateLockNameRequest
import com.doordeck.multiplatform.sdk.model.requests.UpdateLockPropertiesRequest
import com.doordeck.multiplatform.sdk.model.requests.UpdateLockSettingLocationUsageRequirementRequest
import com.doordeck.multiplatform.sdk.model.requests.UpdateLockSettingRequest
import com.doordeck.multiplatform.sdk.model.requests.UpdateLockSettingTimeUsageRequirementRequest
import com.doordeck.multiplatform.sdk.model.requests.UpdateLockSettingUsageRequirementRequest
import com.doordeck.multiplatform.sdk.model.requests.UpdateSecureSettingsOperationRequest
import com.doordeck.multiplatform.sdk.model.requests.UserPublicKeyRequest
import com.doordeck.multiplatform.sdk.model.responses.BasicAuditResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicBatchUserPublicKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicLockResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicLockUserResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicShareableLockResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicUserLockResponse
import com.doordeck.multiplatform.sdk.model.responses.BasicUserPublicKeyResponse
import com.doordeck.multiplatform.sdk.util.Utils.encodeByteArrayToBase64
import com.doordeck.multiplatform.sdk.util.addRequestHeaders
import com.doordeck.multiplatform.sdk.util.toJson
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import kotlin.jvm.JvmSynthetic
import kotlin.time.Clock.System.now
import kotlin.time.Duration.Companion.days
import kotlin.uuid.Uuid

/**
 * Internal implementation of the lock operations API client.
 * Handles all  requests related to lock management and operations.
 */
internal object LockOperationsClient {
    /**
     * Retrieves a single lock by its ID.
     *
     * @param lockId The unique identifier of the lock.
     * @return [BasicLockResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/lock-operations/get-a-single-lock">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun getSingleLockRequest(lockId: String): BasicLockResponse {
        return CloudHttpClient.client.get(Paths.getSingleLockPath(lockId)) {
            addRequestHeaders(contentType = null, apiVersion = ApiVersion.VERSION_3)
        }.body()
    }

    /**
     * Retrieves all log events associated with a particular lock.
     *
     * @param lockId The lock's unique identifier.
     * @param start Start of the date range (epoch timestamp) or null to use the last week.
     * @param end End of date range (epoch timestamp) or null to use the last week.
     * @return List of [BasicAuditResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/lock-operations/get-lock-audit-trail-v2">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun getLockAuditTrailRequest(
        lockId: String,
        start: Long? = now().minus(7.days).epochSeconds,
        end: Long? = now().epochSeconds
    ): List<BasicAuditResponse> {
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
     * @param start Start of the date range (epoch timestamp) or null to use the last week.
     * @param end End of date range (epoch timestamp) or null to use the last week.
     * @return List of [BasicAuditResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/lock-operations/get-audit-for-a-user">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun getAuditForUserRequest(
        userId: String,
        start: Long? = now().minus(7.days).epochSeconds,
        end: Long? = now().epochSeconds
    ): List<BasicAuditResponse> {
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
     * @return List of [BasicUserLockResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/lock-operations/get-users-for-a-lock">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun getUsersForLockRequest(lockId: String): List<BasicUserLockResponse> {
        return CloudHttpClient.client.get(Paths.getUsersForLockPath(lockId)).body()
    }

    /**
     * Retrieves basic user information, including the user's public key and all locks for this particular user.
     * The list will contain only the locks where the current user is an administrator.
     *
     * @param userId The user's unique identifier.
     * @return [BasicLockUserResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/lock-operations/get-locks-for-a-user">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun getLocksForUserRequest(userId: String): BasicLockUserResponse {
        return CloudHttpClient.client.get(Paths.getLocksForUserPath(userId)).body()
    }

    /**
     * Updates the user's alias for the lock.
     *
     * @param lockId The lock's unique identifier.
     * @param name The new alias for the lock (use `null` to remove the existing alias).
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/lock-operations/update-lock-properties">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun updateLockNameRequest(lockId: String, name: String?) {
        updateLockProperties(lockId, UpdateLockNameRequest(name))
    }

    /**
     * Updates the lock's favorite flag for the current user.
     *
     * @param lockId The lock's unique identifier.
     * @param favourite `true` to mark the lock as a favorite, `false` to remove the favorite status.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/lock-operations/update-lock-properties">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun updateLockFavouriteRequest(lockId: String, favourite: Boolean) {
        updateLockProperties(lockId, UpdateLockFavouriteRequest(favourite))
    }

    /**
     * Updates the lock's default name (visible to all users without a custom alias).
     *
     * @param lockId The lock's unique identifier.
     * @param name The new lock's default name.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/lock-operations/update-lock-properties">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun updateLockSettingDefaultNameRequest(lockId: String, name: String) {
        updateLockProperties(lockId, UpdateLockSettingRequest(LockSettingsDefaultNameRequest(name)))
    }

    /**
     * Updates the list of permitted public IPs allowed to control this lock.
     *
     * @param lockId The lock's unique identifier.
     * @param permittedAddresses The full list of allowed public IP addresses (replaces any existing list).
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/lock-operations/update-lock-properties">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun setLockSettingPermittedAddressesRequest(lockId: String, permittedAddresses: List<String>) {
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
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/lock-operations/update-lock-properties">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun updateLockSettingHiddenRequest(lockId: String, hidden: Boolean) {
        updateLockProperties(lockId, UpdateLockSettingRequest(LockSettingsHiddenRequest(hidden)))
    }

    /**
     * Configures time-based usage restrictions for the lock.
     *
     * @param lockId The lock's unique identifier.
     * @param times List of allowed time windows (replaces existing restrictions).
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/lock-operations/update-lock-properties">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun setLockSettingTimeRestrictionsRequest(lockId: String, times: List<BasicTimeRequirement>) {
        updateLockProperties(
            lockId = lockId,
            request = UpdateLockSettingRequest(
                settings = UpdateLockSettingUsageRequirementRequest(
                    usageRequirements = UpdateLockSettingTimeUsageRequirementRequest(
                        time = times.map {
                            TimeRequirementRequest(
                                start = it.start,
                                end = it.end,
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
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/lock-operations/update-lock-properties">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun updateLockSettingLocationRestrictionsRequest(
        lockId: String,
        location: BasicLocationRequirement?
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
     * @return [BasicUserPublicKeyResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/lock-operations/get-a-doordeck-user-public-key">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun getUserPublicKeyRequest(userEmail: String, visitor: Boolean): BasicUserPublicKeyResponse {
        return CloudHttpClient.client.post(Paths.getUserPublicKeyPath(userEmail)) {
            addRequestHeaders()
            parameter(Params.VISITOR, visitor)
        }.body()
    }

    /**
     * Retrieves a user's public key using their email address.
     *
     * @param email The user's email address.
     * @return [BasicUserPublicKeyResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/lock-operations/lookup-user-public-key-v1">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun getUserPublicKeyByEmailRequest(email: String): BasicUserPublicKeyResponse =
        getUserPublicKey(UserPublicKeyRequest(email = email))

    /**
     * Retrieves a user's public key using their telephone number.
     *
     * @param telephone The user's telephone number.
     * @return [BasicUserPublicKeyResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/lock-operations/lookup-user-public-key-v1">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun getUserPublicKeyByTelephoneRequest(telephone: String): BasicUserPublicKeyResponse =
        getUserPublicKey(UserPublicKeyRequest(telephone = telephone))

    /**
     * Retrieves a user's public key using their local key.
     *
     * @param localKey The user's local key.
     * @return [BasicUserPublicKeyResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/lock-operations/lookup-user-public-key-v1">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun getUserPublicKeyByLocalKeyRequest(localKey: String): BasicUserPublicKeyResponse =
        getUserPublicKey(UserPublicKeyRequest(localKey = localKey))

    /**
     * Retrieves a user's public key using their third-party application's identifier for a user.
     *
     * @param foreignKey The user's third-party application's identifier.
     * @return [BasicUserPublicKeyResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/lock-operations/lookup-user-public-key-v1">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun getUserPublicKeyByForeignKeyRequest(foreignKey: String): BasicUserPublicKeyResponse =
        getUserPublicKey(UserPublicKeyRequest(foreignKey = foreignKey))

    /**
     * Retrieves a user's public key using their encrypted OpenID token of user.
     *
     * @param identity The user's encrypted OpenID token of user.
     * @return [BasicUserPublicKeyResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/lock-operations/lookup-user-public-key-v1">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun getUserPublicKeyByIdentityRequest(identity: String): BasicUserPublicKeyResponse =
        getUserPublicKey(UserPublicKeyRequest(identity = identity))

    /**
     * Handles the public key request of an existing user.
     *
     * @param request The specific [UserPublicKeyRequest] request to be handled.
     * @throws SdkException if an unexpected error occurs while processing the request.
     */
    private suspend fun getUserPublicKey(request: UserPublicKeyRequest): BasicUserPublicKeyResponse {
        return CloudHttpClient.client.post(Paths.getUserPublicKeyPath()) {
            addRequestHeaders()
            setBody(request)
        }.body()
    }

    /**
     * Retrieves public keys for up to 25 users by their email addresses.
     *
     * @param emails List of user email addresses (max 25 entries).
     * @return List of [BasicBatchUserPublicKeyResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/lock-operations/lookup-user-public-key-v2">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun getUserPublicKeyByEmailsRequest(emails: List<String>): List<BasicBatchUserPublicKeyResponse> =
        batchGetUserPublicKey(BatchUserPublicKeyRequest(email = emails))

    /**
     * Retrieves public keys for up to 25 users by their telephone numbers.
     *
     * @param telephones List of user email addresses (max 25 entries).
     * @return List of [BasicBatchUserPublicKeyResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/lock-operations/lookup-user-public-key-v2">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun getUserPublicKeyByTelephonesRequest(telephones: List<String>): List<BasicBatchUserPublicKeyResponse> =
        batchGetUserPublicKey(BatchUserPublicKeyRequest(telephone = telephones))

    /**
     * Retrieves public keys for up to 25 users by their local keys.
     *
     * @param localKeys List of user local keys (max 25 entries).
     * @return List of [BasicBatchUserPublicKeyResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/lock-operations/lookup-user-public-key-v2">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun getUserPublicKeyByLocalKeysRequest(localKeys: List<String>): List<BasicBatchUserPublicKeyResponse> =
        batchGetUserPublicKey(BatchUserPublicKeyRequest(localKey = localKeys))

    /**
     * Retrieves public keys for up to 25 users by their third-party application's identifier for a user.
     *
     * @param foreignKeys List of user third-party application's identifiers (max 25 entries).
     * @return List of [BasicBatchUserPublicKeyResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/lock-operations/lookup-user-public-key-v2">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun getUserPublicKeyByForeignKeysRequest(foreignKeys: List<String>): List<BasicBatchUserPublicKeyResponse> =
        batchGetUserPublicKey(BatchUserPublicKeyRequest(foreignKey = foreignKeys))

    /**
     * Handles the batch public key request of existing users.
     *
     * @param request The specific [BatchUserPublicKeyRequest] request to be handled.
     * @return List of [BasicBatchUserPublicKeyResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     */
    private suspend fun batchGetUserPublicKey(request: BatchUserPublicKeyRequest): List<BasicBatchUserPublicKeyResponse> {
        return CloudHttpClient.client.post(Paths.getUserPublicKeyPath()) {
            addRequestHeaders(apiVersion = ApiVersion.VERSION_2)
            setBody(request)
        }.body()
    }

    /**
     * Unlocks a device.
     *
     * @param unlockOperation The specific [BasicUnlockOperation] to be handled.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/lock-operations/unlock/">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun unlockRequest(unlockOperation: BasicUnlockOperation) {
        val operationRequest = LockOperationRequest(locked = false)
        val baseOperationRequest = unlockOperation.baseOperation.toBaseOperationRequestUsingContext()
        performOperation(baseOperationRequest, operationRequest, unlockOperation.directAccessEndpoints)
    }

    /**
     * Shares access to a device with another user.
     *
     * @param shareLockOperation The specific [BasicShareLockOperation] to be handled.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/lock-operations/share-a-lock-v1">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun shareLockRequest(shareLockOperation: BasicShareLockOperation) {
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
     * @param batchShareLockOperation The specific [BasicBatchShareLockOperation] to be handled.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/lock-operations/batch-share-a-lock-v2">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun batchShareLockRequest(batchShareLockOperation: BasicBatchShareLockOperation) {
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
                        shareLockOperation = BasicShareLockOperation(
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
     * @param revokeAccessToLockOperation The specific [BasicRevokeAccessToLockOperation] to be handled.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/lock-operations/revoke-access-to-a-lock">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun revokeAccessToLockRequest(revokeAccessToLockOperation: BasicRevokeAccessToLockOperation) {
        val operationRequest = RevokeAccessToALockOperationRequest(users = revokeAccessToLockOperation.users)
        val baseOperationRequest = revokeAccessToLockOperation.baseOperation.toBaseOperationRequestUsingContext()
        performOperation(baseOperationRequest, operationRequest)
    }

    /**
     * Updates the unlock duration setting (how long lock stays unlocked).
     *
     * @param updateSecureSettingUnlockDuration The specific [BasicUpdateSecureSettingUnlockDuration] to be handled.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/lock-operations/update-secure-settings">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun updateSecureSettingUnlockDurationRequest(updateSecureSettingUnlockDuration: BasicUpdateSecureSettingUnlockDuration) {
        val operationRequest = UpdateSecureSettingsOperationRequest(
            unlockDuration = updateSecureSettingUnlockDuration.unlockDuration
        )
        val baseOperationRequest = updateSecureSettingUnlockDuration.baseOperation.toBaseOperationRequestUsingContext()
        performOperation(baseOperationRequest, operationRequest)
    }

    /**
     * Updates the unlock between definition.
     *
     * @param updateSecureSettingUnlockBetween The specific [BasicUpdateSecureSettingUnlockBetween] to be handled.
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/lock-operations/update-secure-settings">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun updateSecureSettingUnlockBetweenRequest(updateSecureSettingUnlockBetween: BasicUpdateSecureSettingUnlockBetween) {
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
     * @return List of [BasicLockResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/lock-operations/get-pinned-locks">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun getPinnedLocksRequest(): List<BasicLockResponse> {
        return CloudHttpClient.client.get(Paths.getPinnedLocksPath()).body()
    }

    /**
     * Retrieves all locks where the current user has administrator privileges.
     * @return List of [BasicShareableLockResponse].
     * @throws SdkException if an unexpected error occurs while processing the request.
     *
     * @see <a href="https://portal.sentryinteractive.com/docs/cloud-api/lock-operations/get-shareable-locks">API Doc</a>
     */
    @JvmSynthetic
    internal suspend fun getShareableLocksRequest(): List<BasicShareableLockResponse> {
        return CloudHttpClient.client.get(Paths.getShareableLocksPath()).body()
    }

    /**
     * Converts a [BasicBaseOperation] to a [BaseOperationRequest].
     * Validates that required fields (userId, userCertificateChain, userPrivateKey) are available
     * either in the input or the [Context].
     *
     * @return [BaseOperationRequest].
     * @throws MissingContextFieldException if any required field (userId, userCertificateChain,
     *         or userPrivateKey) is missing from both the input and the [Context].
     */
    private fun BasicBaseOperation.toBaseOperationRequestUsingContext(): BaseOperationRequest {
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