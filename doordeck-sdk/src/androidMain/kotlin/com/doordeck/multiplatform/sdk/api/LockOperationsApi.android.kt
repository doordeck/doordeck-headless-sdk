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
import com.doordeck.multiplatform.sdk.model.responses.Audit
import com.doordeck.multiplatform.sdk.model.responses.BatchUserPublicKey
import com.doordeck.multiplatform.sdk.model.responses.LockResponse
import com.doordeck.multiplatform.sdk.model.responses.LockUser
import com.doordeck.multiplatform.sdk.model.responses.ShareableLock
import com.doordeck.multiplatform.sdk.model.responses.UserLock
import com.doordeck.multiplatform.sdk.model.responses.UserPublicKey
import com.doordeck.multiplatform.sdk.model.responses.toAudit
import com.doordeck.multiplatform.sdk.model.responses.toBatchUserPublicKey
import com.doordeck.multiplatform.sdk.model.responses.toLock
import com.doordeck.multiplatform.sdk.model.responses.toLockUser
import com.doordeck.multiplatform.sdk.model.responses.toShareableLock
import com.doordeck.multiplatform.sdk.model.responses.toUserLock
import com.doordeck.multiplatform.sdk.model.responses.toUserPublicKey
import com.doordeck.multiplatform.sdk.util.completableFuture
import java.util.concurrent.CompletableFuture

/**
 * Platform-specific implementations of lock-related API calls.
 */
actual object LockOperationsApi {
    /**
     * @see LockOperationsClient.getSingleLockRequest
     */
    suspend fun getSingleLock(lockId: String): LockResponse {
        return LockOperationsClient.getSingleLockRequest(lockId)
            .toLock()
    }

    /**
     * Async variant of [LockOperationsApi.getSingleLock] returning [CompletableFuture].
     */
    fun getSingleLockAsync(lockId: String): CompletableFuture<LockResponse> {
        return completableFuture { getSingleLock(lockId) }
    }

    /**
     * @see LockOperationsClient.getLockAuditTrailRequest
     */
    suspend fun getLockAuditTrail(lockId: String, start: Long, end: Long): List<Audit> {
        return LockOperationsClient.getLockAuditTrailRequest(lockId, start, end)
            .toAudit()
    }

    /**
     * Async variant of [LockOperationsApi.getLockAuditTrail] returning [CompletableFuture].
     */
    fun getLockAuditTrailAsync(lockId: String, start: Long, end: Long): CompletableFuture<List<Audit>> {
        return completableFuture { getLockAuditTrail(lockId, start, end) }
    }

    /**
     * @see LockOperationsClient.getAuditForUserRequest
     */
    suspend fun getAuditForUser(userId: String, start: Long, end: Long): List<Audit> {
        return LockOperationsClient.getAuditForUserRequest(userId, start, end)
            .toAudit()
    }

    /**
     * Async variant of [LockOperationsApi.getAuditForUser] returning [CompletableFuture].
     */
    fun getAuditForUserAsync(userId: String, start: Long, end: Long): CompletableFuture<List<Audit>> {
        return completableFuture { getAuditForUser(userId, start, end) }
    }

    /**
     * @see LockOperationsClient.getUsersForLockRequest
     */
    suspend fun getUsersForLock(lockId: String): List<UserLock> {
        return LockOperationsClient.getUsersForLockRequest(lockId)
            .toUserLock()
    }

    /**
     * Async variant of [LockOperationsApi.getUsersForLock] returning [CompletableFuture].
     */
    fun getUsersForLockAsync(lockId: String): CompletableFuture<List<UserLock>> {
        return completableFuture { getUsersForLock(lockId) }
    }

    /**
     * @see LockOperationsClient.getLocksForUserRequest
     */
    suspend fun getLocksForUser(userId: String): LockUser {
        return LockOperationsClient.getLocksForUserRequest(userId)
            .toLockUser()
    }

    /**
     * Async variant of [LockOperationsApi.getLocksForUser] returning [CompletableFuture].
     */
    fun getLocksForUserAsync(userId: String): CompletableFuture<LockUser> {
        return completableFuture { getLocksForUser(userId) }
    }

    /**
     * @see LockOperationsClient.updateLockNameRequest
     */
    suspend fun updateLockName(lockId: String, name: String? = null) {
        return LockOperationsClient.updateLockNameRequest(lockId, name)
    }

    /**
     * Async variant of [LockOperationsApi.updateLockName] returning [CompletableFuture].
     */
    fun updateLockNameAsync(lockId: String, name: String? = null): CompletableFuture<Unit> {
        return completableFuture { updateLockName(lockId, name) }
    }

    /**
     * @see LockOperationsClient.updateLockFavouriteRequest
     */
    suspend fun updateLockFavourite(lockId: String, favourite: Boolean? = null) {
        return LockOperationsClient.updateLockFavouriteRequest(lockId, favourite)
    }

    /**
     * Async variant of [LockOperationsApi.updateLockFavourite] returning [CompletableFuture].
     */
    fun updateLockFavouriteAsync(lockId: String, favourite: Boolean? = null): CompletableFuture<Unit> {
        return completableFuture { updateLockFavourite(lockId, favourite) }
    }

    /**
     * @see LockOperationsClient.updateLockColourRequest
     */
    suspend fun updateLockColour(lockId: String, colour: String? = null) {
        return LockOperationsClient.updateLockColourRequest(lockId, colour)
    }

    /**
     * Async variant of [LockOperationsApi.updateLockColour] returning [CompletableFuture].
     */
    fun updateLockColourAsync(lockId: String, colour: String? = null): CompletableFuture<Unit> {
        return completableFuture { updateLockColour(lockId, colour) }
    }

    /**
     * @see LockOperationsClient.updateLockSettingDefaultNameRequest
     */
    suspend fun updateLockSettingDefaultName(lockId: String, name: String? = null) {
        return LockOperationsClient.updateLockSettingDefaultNameRequest(lockId, name)
    }

    /**
     * Async variant of [LockOperationsApi.updateLockSettingDefaultName] returning [CompletableFuture].
     */
    fun updateLockSettingDefaultNameAsync(lockId: String, name: String? = null): CompletableFuture<Unit> {
        return completableFuture { updateLockSettingDefaultName(lockId, name) }
    }

    /**
     * @see LockOperationsClient.setLockSettingPermittedAddressesRequest
     */
    suspend fun setLockSettingPermittedAddresses(lockId: String, permittedAddresses: List<String>) {
        return LockOperationsClient.setLockSettingPermittedAddressesRequest(lockId, permittedAddresses)
    }

    /**
     * Async variant of [LockOperationsApi.setLockSettingPermittedAddresses] returning [CompletableFuture].
     */
    fun setLockSettingPermittedAddressesAsync(lockId: String, permittedAddresses: List<String>): CompletableFuture<Unit> {
        return completableFuture { setLockSettingPermittedAddresses(lockId, permittedAddresses) }
    }

    /**
     * @see LockOperationsClient.updateLockSettingHiddenRequest
     */
    suspend fun updateLockSettingHidden(lockId: String, hidden: Boolean) {
        return LockOperationsClient.updateLockSettingHiddenRequest(lockId, hidden)
    }

    /**
     * Async variant of [LockOperationsApi.updateLockSettingHidden] returning [CompletableFuture].
     */
    fun updateLockSettingHiddenAsync(lockId: String, hidden: Boolean): CompletableFuture<Unit> {
        return completableFuture { updateLockSettingHidden(lockId, hidden) }
    }

    /**
     * @see LockOperationsClient.setLockSettingTimeRestrictionsRequest
     */
    suspend fun setLockSettingTimeRestrictions(lockId: String, times: List<LockOperations.TimeRequirement>) {
        return LockOperationsClient.setLockSettingTimeRestrictionsRequest(lockId, times.toBasicTimeRequirement())
    }

    /**
     * Async variant of [LockOperationsApi.setLockSettingTimeRestrictions] returning [CompletableFuture].
     */
    fun setLockSettingTimeRestrictionsAsync(lockId: String, times: List<LockOperations.TimeRequirement>): CompletableFuture<Unit> {
        return completableFuture { setLockSettingTimeRestrictions(lockId, times) }
    }

    /**
     * @see LockOperationsClient.updateLockSettingLocationRestrictionsRequest
     */
    suspend fun updateLockSettingLocationRestrictions(lockId: String, location: LockOperations.LocationRequirement? = null) {
        return LockOperationsClient.updateLockSettingLocationRestrictionsRequest(lockId, location?.toBasicLocationRequirement())
    }

    /**
     * Async variant of [LockOperationsApi.updateLockSettingLocationRestrictions] returning [CompletableFuture].
     */
    fun updateLockSettingLocationRestrictionsAsync(lockId: String, location: LockOperations.LocationRequirement? = null): CompletableFuture<Unit> {
        return completableFuture { updateLockSettingLocationRestrictions(lockId, location) }
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyRequest
     */
    @DoordeckOnly
    suspend fun getUserPublicKey(userEmail: String, visitor: Boolean = false): UserPublicKey {
        return LockOperationsClient.getUserPublicKeyRequest(userEmail, visitor)
            .toUserPublicKey()
    }

    /**
     * Async variant of [LockOperationsApi.getUserPublicKey] returning [CompletableFuture].
     */
    @DoordeckOnly
    suspend fun getUserPublicKeyAsync(userEmail: String, visitor: Boolean = false): CompletableFuture<UserPublicKey> {
        return completableFuture { getUserPublicKey(userEmail, visitor) }
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByEmailRequest
     */
    suspend fun getUserPublicKeyByEmail(email: String): UserPublicKey {
        return LockOperationsClient.getUserPublicKeyByEmailRequest(email)
            .toUserPublicKey()
    }

    /**
     * Async variant of [LockOperationsApi.getUserPublicKeyByEmail] returning [CompletableFuture].
     */
    fun getUserPublicKeyByEmailAsync(email: String): CompletableFuture<UserPublicKey> {
        return completableFuture { getUserPublicKeyByEmail(email) }
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByTelephoneRequest
     */
    suspend fun getUserPublicKeyByTelephone(telephone: String): UserPublicKey {
        return LockOperationsClient.getUserPublicKeyByTelephoneRequest(telephone)
            .toUserPublicKey()
    }

    /**
     * Async variant of [LockOperationsApi.getUserPublicKeyByTelephone] returning [CompletableFuture].
     */
    fun getUserPublicKeyByTelephoneAsync(telephone: String): CompletableFuture<UserPublicKey> {
        return completableFuture { getUserPublicKeyByTelephone(telephone) }
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByLocalKeyRequest
     */
    suspend fun getUserPublicKeyByLocalKey(localKey: String): UserPublicKey {
        return LockOperationsClient.getUserPublicKeyByLocalKeyRequest(localKey)
            .toUserPublicKey()
    }

    /**
     * Async variant of [LockOperationsApi.getUserPublicKeyByLocalKey] returning [CompletableFuture].
     */
    fun getUserPublicKeyByLocalKeyAsync(localKey: String): CompletableFuture<UserPublicKey> {
        return completableFuture { getUserPublicKeyByLocalKey(localKey) }
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByForeignKeyRequest
     */
    suspend fun getUserPublicKeyByForeignKey(foreignKey: String): UserPublicKey {
        return LockOperationsClient.getUserPublicKeyByForeignKeyRequest(foreignKey)
            .toUserPublicKey()
    }

    /**
     * Async variant of [LockOperationsApi.getUserPublicKeyByForeignKey] returning [CompletableFuture].
     */
    fun getUserPublicKeyByForeignKeyAsync(foreignKey: String): CompletableFuture<UserPublicKey> {
        return completableFuture { getUserPublicKeyByForeignKey(foreignKey) }
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByIdentityRequest
     */
    suspend fun getUserPublicKeyByIdentity(identity: String): UserPublicKey {
        return LockOperationsClient.getUserPublicKeyByIdentityRequest(identity)
            .toUserPublicKey()
    }

    /**
     * Async variant of [LockOperationsApi.getUserPublicKeyByIdentity] returning [CompletableFuture].
     */
    fun getUserPublicKeyByIdentityAsync(identity: String): CompletableFuture<UserPublicKey> {
        return completableFuture { getUserPublicKeyByIdentity(identity) }
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByEmailsRequest
     */
    suspend fun getUserPublicKeyByEmails(emails: List<String>): List<BatchUserPublicKey> {
        return LockOperationsClient.getUserPublicKeyByEmailsRequest(emails)
            .toBatchUserPublicKey()
    }

    /**
     * Async variant of [LockOperationsApi.getUserPublicKeyByEmails] returning [CompletableFuture].
     */
    fun getUserPublicKeyByEmailsAsync(emails: List<String>): CompletableFuture<List<BatchUserPublicKey>> {
        return completableFuture { getUserPublicKeyByEmails(emails) }
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByTelephonesRequest
     */
    suspend fun getUserPublicKeyByTelephones(telephones: List<String>): List<BatchUserPublicKey> {
        return LockOperationsClient.getUserPublicKeyByTelephonesRequest(telephones)
            .toBatchUserPublicKey()
    }

    /**
     * Async variant of [LockOperationsApi.getUserPublicKeyByTelephones] returning [CompletableFuture].
     */
    fun getUserPublicKeyByTelephonesAsync(telephones: List<String>): CompletableFuture<List<BatchUserPublicKey>> {
        return completableFuture { getUserPublicKeyByTelephones(telephones) }
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByLocalKeysRequest
     */
    suspend fun getUserPublicKeyByLocalKeys(localKeys: List<String>): List<BatchUserPublicKey> {
        return LockOperationsClient.getUserPublicKeyByLocalKeysRequest(localKeys)
            .toBatchUserPublicKey()
    }

    /**
     * Async variant of [LockOperationsApi.getUserPublicKeyByLocalKeys] returning [CompletableFuture].
     */
    fun getUserPublicKeyByLocalKeysAsync(localKeys: List<String>): CompletableFuture<List<BatchUserPublicKey>> {
        return completableFuture { getUserPublicKeyByLocalKeys(localKeys) }
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByForeignKeysRequest
     */
    suspend fun getUserPublicKeyByForeignKeys(foreignKeys: List<String>): List<BatchUserPublicKey> {
        return LockOperationsClient.getUserPublicKeyByForeignKeysRequest(foreignKeys)
            .toBatchUserPublicKey()
    }

    /**
     * Async variant of [LockOperationsApi.getUserPublicKeyByForeignKeys] returning [CompletableFuture].
     */
    fun getUserPublicKeyByForeignKeysAsync(foreignKeys: List<String>): CompletableFuture<List<BatchUserPublicKey>> {
        return completableFuture { getUserPublicKeyByForeignKeys(foreignKeys) }
    }

    /**
     * @see LockOperationsClient.unlockRequest
     */
    suspend fun unlock(unlockOperation: LockOperations.UnlockOperation) {
        return LockOperationsClient.unlockRequest(unlockOperation.toBasicUnlockOperation())
    }

    /**
     * Async variant of [LockOperationsApi.unlock] returning [CompletableFuture].
     */
    fun unlockAsync(unlockOperation: LockOperations.UnlockOperation): CompletableFuture<Unit> {
        return completableFuture { unlock(unlockOperation) }
    }

    /**
     * @see LockOperationsClient.shareLockRequest
     */
    suspend fun shareLock(shareLockOperation: LockOperations.ShareLockOperation) {
        return LockOperationsClient.shareLockRequest(shareLockOperation.toBasicShareLockOperation())
    }

    /**
     * Async variant of [LockOperationsApi.shareLock] returning [CompletableFuture].
     */
    fun shareLockAsync(shareLockOperation: LockOperations.ShareLockOperation): CompletableFuture<Unit> {
        return completableFuture { shareLock(shareLockOperation) }
    }

    /**
     * @see LockOperationsClient.batchShareLockRequest
     */
    suspend fun batchShareLock(batchShareLockOperation: LockOperations.BatchShareLockOperation) {
        return LockOperationsClient.batchShareLockRequest(batchShareLockOperation.toBasicBatchShareLockOperation())
    }

    /**
     * Async variant of [LockOperationsApi.batchShareLock] returning [CompletableFuture].
     */
    fun batchShareLockAsync(batchShareLockOperation: LockOperations.BatchShareLockOperation): CompletableFuture<Unit> {
        return completableFuture { batchShareLock(batchShareLockOperation) }
    }

    /**
     * @see LockOperationsClient.revokeAccessToLockRequest
     */
    suspend fun revokeAccessToLock(revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation) {
        return LockOperationsClient.revokeAccessToLockRequest(revokeAccessToLockOperation.toBasicRevokeAccessToLockOperation())
    }

    /**
     * Async variant of [LockOperationsApi.revokeAccessToLock] returning [CompletableFuture].
     */
    fun revokeAccessToLockAsync(revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation): CompletableFuture<Unit> {
        return completableFuture { revokeAccessToLock(revokeAccessToLockOperation) }
    }

    /**
     * @see LockOperationsClient.updateSecureSettingUnlockDurationRequest
     */
    suspend fun updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration) {
        return LockOperationsClient.updateSecureSettingUnlockDurationRequest(updateSecureSettingUnlockDuration.toBasicUpdateSecureSettingUnlockDuration())
    }

    /**
     * Async variant of [LockOperationsApi.updateSecureSettingUnlockDuration] returning [CompletableFuture].
     */
    fun updateSecureSettingUnlockDurationAsync(updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration): CompletableFuture<Unit> {
        return completableFuture {
            updateSecureSettingUnlockDuration(
                updateSecureSettingUnlockDuration
            )
        }
    }

    /**
     * @see LockOperationsClient.updateSecureSettingUnlockBetweenRequest
     */
    suspend fun updateSecureSettingUnlockBetween(updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween) {
        return LockOperationsClient.updateSecureSettingUnlockBetweenRequest(updateSecureSettingUnlockBetween.toBasicUpdateSecureSettingUnlockBetween())
    }

    /**
     * Async variant of [LockOperationsApi.updateSecureSettingUnlockBetween] returning [CompletableFuture].
     */
    fun updateSecureSettingUnlockBetweenAsync(updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween): CompletableFuture<Unit> {
        return completableFuture {
            updateSecureSettingUnlockBetween(
                updateSecureSettingUnlockBetween
            )
        }
    }

    /**
     * @see LockOperationsClient.getPinnedLocksRequest
     */
    suspend fun getPinnedLocks(): List<LockResponse> {
        return LockOperationsClient.getPinnedLocksRequest()
            .toLock()
    }

    /**
     * Async variant of [LockOperationsApi.getPinnedLocks] returning [CompletableFuture].
     */
    fun getPinnedLocksAsync(): CompletableFuture<List<LockResponse>> {
        return completableFuture { getPinnedLocks() }
    }

    /**
     * @see LockOperationsClient.getShareableLocksRequest
     */
    suspend fun getShareableLocks(): List<ShareableLock> {
        return LockOperationsClient.getShareableLocksRequest()
            .toShareableLock()
    }

    /**
     * Async variant of [LockOperationsApi.getShareableLocks] returning [CompletableFuture].
     */
    fun getShareableLocksAsync(): CompletableFuture<List<ShareableLock>> {
        return completableFuture { getShareableLocks() }
    }
}

/**
 * Defines the platform-specific implementation of [LockOperationsApi]
 */
actual fun lockOperations(): LockOperationsApi = LockOperationsApi