package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.LockOperationsClient
import com.doordeck.multiplatform.sdk.model.data.LockOperations
import com.doordeck.multiplatform.sdk.model.data.toBasicBatchShareLockOperation
import com.doordeck.multiplatform.sdk.model.data.toBasicLocationRequirement
import com.doordeck.multiplatform.sdk.model.data.toBasicRevokeAccessToLockOperation
import com.doordeck.multiplatform.sdk.model.data.toBasicShareLockOperation
import com.doordeck.multiplatform.sdk.model.data.toBasicTimeRequirement
import com.doordeck.multiplatform.sdk.model.data.toBasicUnlockOperation
import com.doordeck.multiplatform.sdk.model.data.toBasicUpdateSecureSettingUnlockBetween
import com.doordeck.multiplatform.sdk.model.data.toBasicUpdateSecureSettingUnlockDuration
import com.doordeck.multiplatform.sdk.model.responses.AuditResponse
import com.doordeck.multiplatform.sdk.model.responses.BatchUserPublicKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.LockResponse
import com.doordeck.multiplatform.sdk.model.responses.LockUserResponse
import com.doordeck.multiplatform.sdk.model.responses.ShareableLockResponse
import com.doordeck.multiplatform.sdk.model.responses.UserLockResponse
import com.doordeck.multiplatform.sdk.model.responses.UserPublicKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.toAuditResponse
import com.doordeck.multiplatform.sdk.model.responses.toBatchUserPublicKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.toLockResponse
import com.doordeck.multiplatform.sdk.model.responses.toLockUserResponse
import com.doordeck.multiplatform.sdk.model.responses.toShareableLockResponse
import com.doordeck.multiplatform.sdk.model.responses.toUserLockResponse
import com.doordeck.multiplatform.sdk.model.responses.toUserPublicKeyResponse
import com.doordeck.multiplatform.sdk.util.completableFuture
import kotlinx.datetime.Instant
import java.net.InetAddress
import java.util.UUID
import java.util.concurrent.CompletableFuture

/**
 * Platform-specific implementations of lock-related API calls.
 */
actual object LockOperationsApi {
    /**
     * @see LockOperationsClient.getSingleLockRequest
     */
    suspend fun getSingleLock(lockId: UUID): LockResponse = LockOperationsClient
        .getSingleLockRequest(lockId.toString())
        .toLockResponse()

    /**
     * Async variant of [LockOperationsApi.getSingleLock] returning [CompletableFuture].
     */
    fun getSingleLockAsync(lockId: UUID): CompletableFuture<LockResponse> = completableFuture {
        getSingleLock(lockId)
    }

    /**
     * @see LockOperationsClient.getLockAuditTrailRequest
     */
    suspend fun getLockAuditTrail(
        lockId: UUID,
        start: Instant,
        end: Instant
    ): List<AuditResponse> = LockOperationsClient
        .getLockAuditTrailRequest(
            lockId = lockId.toString(),
            start = start.epochSeconds,
            end = end.epochSeconds
        )
        .toAuditResponse()

    /**
     * Async variant of [LockOperationsApi.getLockAuditTrail] returning [CompletableFuture].
     */
    fun getLockAuditTrailAsync(
        lockId: UUID,
        start: Instant,
        end: Instant
    ): CompletableFuture<List<AuditResponse>> = completableFuture {
        getLockAuditTrail(
            lockId = lockId,
            start = start,
            end = end
        )
    }

    /**
     * @see LockOperationsClient.getAuditForUserRequest
     */
    suspend fun getAuditForUser(userId: UUID, start: Instant, end: Instant): List<AuditResponse> = LockOperationsClient
        .getAuditForUserRequest(
            userId = userId.toString(),
            start = start.epochSeconds,
            end = end.epochSeconds
        )
        .toAuditResponse()

    /**
     * Async variant of [LockOperationsApi.getAuditForUser] returning [CompletableFuture].
     */
    fun getAuditForUserAsync(
        userId: UUID,
        start: Instant,
        end: Instant
    ): CompletableFuture<List<AuditResponse>> = completableFuture {
        getAuditForUser(
            userId = userId,
            start = start,
            end = end
        )
    }

    /**
     * @see LockOperationsClient.getUsersForLockRequest
     */
    suspend fun getUsersForLock(lockId: UUID): List<UserLockResponse> = LockOperationsClient
        .getUsersForLockRequest(lockId.toString())
        .toUserLockResponse()

    /**
     * Async variant of [LockOperationsApi.getUsersForLock] returning [CompletableFuture].
     */
    fun getUsersForLockAsync(lockId: UUID): CompletableFuture<List<UserLockResponse>> = completableFuture {
        getUsersForLock(lockId)
    }

    /**
     * @see LockOperationsClient.getLocksForUserRequest
     */
    suspend fun getLocksForUser(userId: UUID): LockUserResponse = LockOperationsClient
        .getLocksForUserRequest(userId.toString())
        .toLockUserResponse()

    /**
     * Async variant of [LockOperationsApi.getLocksForUser] returning [CompletableFuture].
     */
    fun getLocksForUserAsync(userId: UUID): CompletableFuture<LockUserResponse> = completableFuture {
        getLocksForUser(userId)
    }

    /**
     * @see LockOperationsClient.updateLockNameRequest
     */
    suspend fun updateLockName(lockId: UUID, name: String? = null) = LockOperationsClient
        .updateLockNameRequest(
            lockId = lockId.toString(),
            name = name
        )

    /**
     * Async variant of [LockOperationsApi.updateLockName] returning [CompletableFuture].
     */
    fun updateLockNameAsync(lockId: UUID, name: String? = null): CompletableFuture<Unit> = completableFuture {
        updateLockName(
            lockId = lockId,
            name = name
        )
    }

    /**
     * @see LockOperationsClient.updateLockFavouriteRequest
     */
    suspend fun updateLockFavourite(lockId: UUID, favourite: Boolean) = LockOperationsClient
        .updateLockFavouriteRequest(
            lockId = lockId.toString(),
            favourite = favourite
        )

    /**
     * Async variant of [LockOperationsApi.updateLockFavourite] returning [CompletableFuture].
     */
    fun updateLockFavouriteAsync(lockId: UUID, favourite: Boolean): CompletableFuture<Unit> = completableFuture {
        updateLockFavourite(
            lockId = lockId,
            favourite = favourite
        )
    }

    /**
     * @see LockOperationsClient.updateLockSettingDefaultNameRequest
     */
    suspend fun updateLockSettingDefaultName(lockId: UUID, name: String? = null) = LockOperationsClient
        .updateLockSettingDefaultNameRequest(
            lockId = lockId.toString(),
            name = name
        )

    /**
     * Async variant of [LockOperationsApi.updateLockSettingDefaultName] returning [CompletableFuture].
     */
    fun updateLockSettingDefaultNameAsync(
        lockId: UUID,
        name: String? = null
    ): CompletableFuture<Unit> = completableFuture {
        updateLockSettingDefaultName(
            lockId = lockId,
            name = name
        )
    }

    /**
     * @see LockOperationsClient.setLockSettingPermittedAddressesRequest
     */
    suspend fun setLockSettingPermittedAddresses(
        lockId: UUID,
        permittedAddresses: List<InetAddress>
    ) = LockOperationsClient
        .setLockSettingPermittedAddressesRequest(
            lockId = lockId.toString(),
            permittedAddresses = permittedAddresses.map { it.hostAddress })

    /**
     * Async variant of [LockOperationsApi.setLockSettingPermittedAddresses] returning [CompletableFuture].
     */
    fun setLockSettingPermittedAddressesAsync(
        lockId: UUID,
        permittedAddresses: List<InetAddress>
    ): CompletableFuture<Unit> = completableFuture {
        setLockSettingPermittedAddresses(
            lockId = lockId,
            permittedAddresses = permittedAddresses
        )
    }

    /**
     * @see LockOperationsClient.updateLockSettingHiddenRequest
     */
    suspend fun updateLockSettingHidden(lockId: UUID, hidden: Boolean) = LockOperationsClient
        .updateLockSettingHiddenRequest(
            lockId = lockId.toString(),
            hidden = hidden
        )

    /**
     * Async variant of [LockOperationsApi.updateLockSettingHidden] returning [CompletableFuture].
     */
    fun updateLockSettingHiddenAsync(lockId: UUID, hidden: Boolean): CompletableFuture<Unit> = completableFuture {
        updateLockSettingHidden(lockId, hidden)
    }

    /**
     * @see LockOperationsClient.setLockSettingTimeRestrictionsRequest
     */
    suspend fun setLockSettingTimeRestrictions(
        lockId: UUID,
        times: List<LockOperations.TimeRequirement>
    ) = LockOperationsClient
        .setLockSettingTimeRestrictionsRequest(
            lockId = lockId.toString(),
            times = times.toBasicTimeRequirement()
        )

    /**
     * Async variant of [LockOperationsApi.setLockSettingTimeRestrictions] returning [CompletableFuture].
     */
    fun setLockSettingTimeRestrictionsAsync(
        lockId: UUID,
        times: List<LockOperations.TimeRequirement>
    ): CompletableFuture<Unit> = completableFuture {
        setLockSettingTimeRestrictions(
            lockId = lockId,
            times = times
        )
    }

    /**
     * @see LockOperationsClient.updateLockSettingLocationRestrictionsRequest
     */
    suspend fun updateLockSettingLocationRestrictions(
        lockId: UUID,
        location: LockOperations.LocationRequirement? = null
    ) = LockOperationsClient
        .updateLockSettingLocationRestrictionsRequest(
            lockId = lockId.toString(),
            location = location?.toBasicLocationRequirement()
        )

    /**
     * Async variant of [LockOperationsApi.updateLockSettingLocationRestrictions] returning [CompletableFuture].
     */
    fun updateLockSettingLocationRestrictionsAsync(
        lockId: UUID,
        location: LockOperations.LocationRequirement? = null
    ): CompletableFuture<Unit> = completableFuture {
        updateLockSettingLocationRestrictions(
            lockId = lockId,
            location = location
        )
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyRequest
     */
    @DoordeckOnly
    suspend fun getUserPublicKey(
        userEmail: String,
        visitor: Boolean = false
    ): UserPublicKeyResponse = LockOperationsClient
        .getUserPublicKeyRequest(
            userEmail = userEmail,
            visitor = visitor
        )
        .toUserPublicKeyResponse()

    /**
     * Async variant of [LockOperationsApi.getUserPublicKey] returning [CompletableFuture].
     */
    @DoordeckOnly
    suspend fun getUserPublicKeyAsync(
        userEmail: String,
        visitor: Boolean = false
    ): CompletableFuture<UserPublicKeyResponse> = completableFuture {
        getUserPublicKey(userEmail, visitor)
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByEmailRequest
     */
    suspend fun getUserPublicKeyByEmail(email: String): UserPublicKeyResponse = LockOperationsClient
        .getUserPublicKeyByEmailRequest(email)
        .toUserPublicKeyResponse()

    /**
     * Async variant of [LockOperationsApi.getUserPublicKeyByEmail] returning [CompletableFuture].
     */
    fun getUserPublicKeyByEmailAsync(email: String): CompletableFuture<UserPublicKeyResponse> = completableFuture {
        getUserPublicKeyByEmail(email)
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByTelephoneRequest
     */
    suspend fun getUserPublicKeyByTelephone(telephone: String): UserPublicKeyResponse = LockOperationsClient
        .getUserPublicKeyByTelephoneRequest(telephone)
        .toUserPublicKeyResponse()

    /**
     * Async variant of [LockOperationsApi.getUserPublicKeyByTelephone] returning [CompletableFuture].
     */
    fun getUserPublicKeyByTelephoneAsync(
        telephone: String
    ): CompletableFuture<UserPublicKeyResponse> = completableFuture {
        getUserPublicKeyByTelephone(telephone)
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByLocalKeyRequest
     */
    suspend fun getUserPublicKeyByLocalKey(localKey: String): UserPublicKeyResponse = LockOperationsClient
        .getUserPublicKeyByLocalKeyRequest(localKey)
        .toUserPublicKeyResponse()

    /**
     * Async variant of [LockOperationsApi.getUserPublicKeyByLocalKey] returning [CompletableFuture].
     */
    fun getUserPublicKeyByLocalKeyAsync(
        localKey: String
    ): CompletableFuture<UserPublicKeyResponse> = completableFuture {
        getUserPublicKeyByLocalKey(localKey)
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByForeignKeyRequest
     */
    suspend fun getUserPublicKeyByForeignKey(foreignKey: String): UserPublicKeyResponse = LockOperationsClient
        .getUserPublicKeyByForeignKeyRequest(foreignKey)
        .toUserPublicKeyResponse()

    /**
     * Async variant of [LockOperationsApi.getUserPublicKeyByForeignKey] returning [CompletableFuture].
     */
    fun getUserPublicKeyByForeignKeyAsync(
        foreignKey: String
    ): CompletableFuture<UserPublicKeyResponse> = completableFuture {
        getUserPublicKeyByForeignKey(foreignKey)
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByIdentityRequest
     */
    suspend fun getUserPublicKeyByIdentity(identity: String): UserPublicKeyResponse = LockOperationsClient
        .getUserPublicKeyByIdentityRequest(identity)
        .toUserPublicKeyResponse()

    /**
     * Async variant of [LockOperationsApi.getUserPublicKeyByIdentity] returning [CompletableFuture].
     */
    fun getUserPublicKeyByIdentityAsync(
        identity: String
    ): CompletableFuture<UserPublicKeyResponse> = completableFuture {
        getUserPublicKeyByIdentity(identity)
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByEmailsRequest
     */
    suspend fun getUserPublicKeyByEmails(emails: List<String>): List<BatchUserPublicKeyResponse> = LockOperationsClient
        .getUserPublicKeyByEmailsRequest(emails)
        .toBatchUserPublicKeyResponse()

    /**
     * Async variant of [LockOperationsApi.getUserPublicKeyByEmails] returning [CompletableFuture].
     */
    fun getUserPublicKeyByEmailsAsync(
        emails: List<String>
    ): CompletableFuture<List<BatchUserPublicKeyResponse>> = completableFuture {
        getUserPublicKeyByEmails(emails)
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByTelephonesRequest
     */
    suspend fun getUserPublicKeyByTelephones(
        telephones: List<String>
    ): List<BatchUserPublicKeyResponse> = LockOperationsClient
        .getUserPublicKeyByTelephonesRequest(telephones)
        .toBatchUserPublicKeyResponse()

    /**
     * Async variant of [LockOperationsApi.getUserPublicKeyByTelephones] returning [CompletableFuture].
     */
    fun getUserPublicKeyByTelephonesAsync(
        telephones: List<String>
    ): CompletableFuture<List<BatchUserPublicKeyResponse>> = completableFuture {
        getUserPublicKeyByTelephones(telephones)
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByLocalKeysRequest
     */
    suspend fun getUserPublicKeyByLocalKeys(
        localKeys: List<String>
    ): List<BatchUserPublicKeyResponse> = LockOperationsClient
        .getUserPublicKeyByLocalKeysRequest(localKeys)
        .toBatchUserPublicKeyResponse()

    /**
     * Async variant of [LockOperationsApi.getUserPublicKeyByLocalKeys] returning [CompletableFuture].
     */
    fun getUserPublicKeyByLocalKeysAsync(
        localKeys: List<String>
    ): CompletableFuture<List<BatchUserPublicKeyResponse>> = completableFuture {
        getUserPublicKeyByLocalKeys(localKeys)
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByForeignKeysRequest
     */
    suspend fun getUserPublicKeyByForeignKeys(
        foreignKeys: List<String>
    ): List<BatchUserPublicKeyResponse> = LockOperationsClient
        .getUserPublicKeyByForeignKeysRequest(foreignKeys)
        .toBatchUserPublicKeyResponse()

    /**
     * Async variant of [LockOperationsApi.getUserPublicKeyByForeignKeys] returning [CompletableFuture].
     */
    fun getUserPublicKeyByForeignKeysAsync(
        foreignKeys: List<String>
    ): CompletableFuture<List<BatchUserPublicKeyResponse>> = completableFuture {
        getUserPublicKeyByForeignKeys(foreignKeys)
    }

    /**
     * @see LockOperationsClient.unlockRequest
     */
    suspend fun unlock(unlockOperation: LockOperations.UnlockOperation) = LockOperationsClient
        .unlockRequest(unlockOperation.toBasicUnlockOperation())

    /**
     * Async variant of [LockOperationsApi.unlock] returning [CompletableFuture].
     */
    fun unlockAsync(unlockOperation: LockOperations.UnlockOperation): CompletableFuture<Unit> = completableFuture {
        unlock(unlockOperation)
    }

    /**
     * @see LockOperationsClient.shareLockRequest
     */
    suspend fun shareLock(shareLockOperation: LockOperations.ShareLockOperation) = LockOperationsClient
        .shareLockRequest(shareLockOperation.toBasicShareLockOperation())

    /**
     * Async variant of [LockOperationsApi.shareLock] returning [CompletableFuture].
     */
    fun shareLockAsync(
        shareLockOperation: LockOperations.ShareLockOperation
    ): CompletableFuture<Unit> = completableFuture {
        shareLock(shareLockOperation)
    }

    /**
     * @see LockOperationsClient.batchShareLockRequest
     */
    suspend fun batchShareLock(batchShareLockOperation: LockOperations.BatchShareLockOperation) = LockOperationsClient
        .batchShareLockRequest(batchShareLockOperation.toBasicBatchShareLockOperation())

    /**
     * Async variant of [LockOperationsApi.batchShareLock] returning [CompletableFuture].
     */
    fun batchShareLockAsync(
        batchShareLockOperation: LockOperations.BatchShareLockOperation
    ): CompletableFuture<Unit> = completableFuture {
        batchShareLock(batchShareLockOperation)
    }

    /**
     * @see LockOperationsClient.revokeAccessToLockRequest
     */
    suspend fun revokeAccessToLock(
        revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation
    ) = LockOperationsClient
        .revokeAccessToLockRequest(
            revokeAccessToLockOperation = revokeAccessToLockOperation
                .toBasicRevokeAccessToLockOperation()
        )

    /**
     * Async variant of [LockOperationsApi.revokeAccessToLock] returning [CompletableFuture].
     */
    fun revokeAccessToLockAsync(
        revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation
    ): CompletableFuture<Unit> = completableFuture {
        revokeAccessToLock(revokeAccessToLockOperation)
    }

    /**
     * @see LockOperationsClient.updateSecureSettingUnlockDurationRequest
     */
    suspend fun updateSecureSettingUnlockDuration(
        updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration
    ) = LockOperationsClient
        .updateSecureSettingUnlockDurationRequest(
            updateSecureSettingUnlockDuration = updateSecureSettingUnlockDuration
                .toBasicUpdateSecureSettingUnlockDuration()
        )

    /**
     * Async variant of [LockOperationsApi.updateSecureSettingUnlockDuration] returning [CompletableFuture].
     */
    fun updateSecureSettingUnlockDurationAsync(
        updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration
    ): CompletableFuture<Unit> = completableFuture {
        updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration)
    }

    /**
     * @see LockOperationsClient.updateSecureSettingUnlockBetweenRequest
     */
    suspend fun updateSecureSettingUnlockBetween(
        updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween
    ) = LockOperationsClient
        .updateSecureSettingUnlockBetweenRequest(
            updateSecureSettingUnlockBetween = updateSecureSettingUnlockBetween
                .toBasicUpdateSecureSettingUnlockBetween()
        )

    /**
     * Async variant of [LockOperationsApi.updateSecureSettingUnlockBetween] returning [CompletableFuture].
     */
    fun updateSecureSettingUnlockBetweenAsync(
        updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween
    ): CompletableFuture<Unit> = completableFuture {
        updateSecureSettingUnlockBetween(updateSecureSettingUnlockBetween)
    }

    /**
     * @see LockOperationsClient.getPinnedLocksRequest
     */
    suspend fun getPinnedLocks(): List<LockResponse> = LockOperationsClient
        .getPinnedLocksRequest()
        .toLockResponse()

    /**
     * Async variant of [LockOperationsApi.getPinnedLocks] returning [CompletableFuture].
     */
    fun getPinnedLocksAsync(): CompletableFuture<List<LockResponse>> = completableFuture {
        getPinnedLocks()
    }

    /**
     * @see LockOperationsClient.getShareableLocksRequest
     */
    suspend fun getShareableLocks(): List<ShareableLockResponse> = LockOperationsClient
        .getShareableLocksRequest()
        .toShareableLockResponse()

    /**
     * Async variant of [LockOperationsApi.getShareableLocks] returning [CompletableFuture].
     */
    fun getShareableLocksAsync(): CompletableFuture<List<ShareableLockResponse>> = completableFuture {
        getShareableLocks()
    }
}

/**
 * Defines the platform-specific implementation of [LockOperationsApi]
 */
actual fun lockOperations(): LockOperationsApi = LockOperationsApi