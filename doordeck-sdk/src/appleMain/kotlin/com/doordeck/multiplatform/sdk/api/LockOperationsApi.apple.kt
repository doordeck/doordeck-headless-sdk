package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.LockOperationsClient
import com.doordeck.multiplatform.sdk.model.data.Audit
import com.doordeck.multiplatform.sdk.model.data.BatchUserPublicKey
import com.doordeck.multiplatform.sdk.model.data.Lock
import com.doordeck.multiplatform.sdk.model.data.LockOperations
import com.doordeck.multiplatform.sdk.model.data.LockUser
import com.doordeck.multiplatform.sdk.model.data.ShareableLock
import com.doordeck.multiplatform.sdk.model.data.UserLock
import com.doordeck.multiplatform.sdk.model.data.UserPublicKey
import com.doordeck.multiplatform.sdk.model.data.toAudit
import com.doordeck.multiplatform.sdk.model.data.toBasicBatchShareLockOperation
import com.doordeck.multiplatform.sdk.model.data.toBasicLocationRequirement
import com.doordeck.multiplatform.sdk.model.data.toBasicRevokeAccessToLockOperation
import com.doordeck.multiplatform.sdk.model.data.toBasicShareLockOperation
import com.doordeck.multiplatform.sdk.model.data.toBasicTimeRequirement
import com.doordeck.multiplatform.sdk.model.data.toBasicUnlockOperation
import com.doordeck.multiplatform.sdk.model.data.toBasicUpdateSecureSettingUnlockBetween
import com.doordeck.multiplatform.sdk.model.data.toBasicUpdateSecureSettingUnlockDuration
import com.doordeck.multiplatform.sdk.model.data.toBatchUserPublicKey
import com.doordeck.multiplatform.sdk.model.data.toLock
import com.doordeck.multiplatform.sdk.model.data.toLockUser
import com.doordeck.multiplatform.sdk.model.data.toShareableLock
import com.doordeck.multiplatform.sdk.model.data.toUserLock
import com.doordeck.multiplatform.sdk.model.data.toUserPublicKey

/**
 * Platform-specific implementations of lock-related API calls.
 */
actual object LockOperationsApi {
    /**
     * @see LockOperationsClient.getSingleLockRequest
     */
    @Throws(Exception::class)
    suspend fun getSingleLock(lockId: String): Lock {
        return LockOperationsClient.getSingleLockRequest(lockId)
            .toLock()
    }

    /**
     * @see LockOperationsClient.getLockAuditTrailRequest
     */
    @Throws(Exception::class)
    suspend fun getLockAuditTrail(lockId: String, start: Long, end: Long): List<Audit> {
        return LockOperationsClient.getLockAuditTrailRequest(lockId, start, end)
            .toAudit()
    }

    /**
     * @see LockOperationsClient.getAuditForUserRequest
     */
    @Throws(Exception::class)
    suspend fun getAuditForUser(userId: String, start: Long, end: Long): List<Audit> {
        return LockOperationsClient.getAuditForUserRequest(userId, start, end)
            .toAudit()
    }

    /**
     * @see LockOperationsClient.getUsersForLockRequest
     */
    @Throws(Exception::class)
    suspend fun getUsersForLock(lockId: String): List<UserLock> {
        return LockOperationsClient.getUsersForLockRequest(lockId)
            .toUserLock()
    }

    /**
     * @see LockOperationsClient.getLocksForUserRequest
     */
    @Throws(Exception::class)
    suspend fun getLocksForUser(userId: String): LockUser {
        return LockOperationsClient.getLocksForUserRequest(userId)
            .toLockUser()
    }

    /**
     * @see LockOperationsClient.updateLockNameRequest
     */
    @Throws(Exception::class)
    suspend fun updateLockName(lockId: String, name: String? = null) {
        return LockOperationsClient.updateLockNameRequest(lockId, name)
    }

    /**
     * @see LockOperationsClient.updateLockFavouriteRequest
     */
    @Throws(Exception::class)
    suspend fun updateLockFavourite(lockId: String, favourite: Boolean? = null) {
        return LockOperationsClient.updateLockFavouriteRequest(lockId, favourite)
    }

    /**
     * @see LockOperationsClient.updateLockColourRequest
     */
    @Throws(Exception::class)
    suspend fun updateLockColour(lockId: String, colour: String? = null) {
        return LockOperationsClient.updateLockColourRequest(lockId, colour)
    }

    /**
     * @see LockOperationsClient.updateLockSettingDefaultNameRequest
     */
    @Throws(Exception::class)
    suspend fun updateLockSettingDefaultName(lockId: String, name: String? = null) {
        return LockOperationsClient.updateLockSettingDefaultNameRequest(lockId, name)
    }

    /**
     * @see LockOperationsClient.setLockSettingPermittedAddressesRequest
     */
    @Throws(Exception::class)
    suspend fun setLockSettingPermittedAddresses(lockId: String, permittedAddresses: List<String>) {
        return LockOperationsClient.setLockSettingPermittedAddressesRequest(lockId, permittedAddresses)
    }

    /**
     * @see LockOperationsClient.updateLockSettingHiddenRequest
     */
    @Throws(Exception::class)
    suspend fun updateLockSettingHidden(lockId: String, hidden: Boolean) {
        return LockOperationsClient.updateLockSettingHiddenRequest(lockId, hidden)
    }

    /**
     * @see LockOperationsClient.setLockSettingTimeRestrictionsRequest
     */
    @Throws(Exception::class)
    suspend fun setLockSettingTimeRestrictions(lockId: String, times: List<LockOperations.TimeRequirement>) {
        return LockOperationsClient.setLockSettingTimeRestrictionsRequest(lockId, times.toBasicTimeRequirement())
    }

    /**
     * @see LockOperationsClient.updateLockSettingLocationRestrictionsRequest
     */
    @Throws(Exception::class)
    suspend fun updateLockSettingLocationRestrictions(lockId: String, location: LockOperations.LocationRequirement? = null) {
        return LockOperationsClient.updateLockSettingLocationRestrictionsRequest(lockId, location?.toBasicLocationRequirement())
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun getUserPublicKey(userEmail: String, visitor: Boolean = false): UserPublicKey {
        return LockOperationsClient.getUserPublicKeyRequest(userEmail, visitor)
            .toUserPublicKey()
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByEmailRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByEmail(email: String): UserPublicKey {
        return LockOperationsClient.getUserPublicKeyByEmailRequest(email)
            .toUserPublicKey()
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByTelephoneRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByTelephone(telephone: String): UserPublicKey {
        return LockOperationsClient.getUserPublicKeyByTelephoneRequest(telephone)
            .toUserPublicKey()
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByLocalKeyRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByLocalKey(localKey: String): UserPublicKey {
        return LockOperationsClient.getUserPublicKeyByLocalKeyRequest(localKey)
            .toUserPublicKey()
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByForeignKeyRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByForeignKey(foreignKey: String): UserPublicKey {
        return LockOperationsClient.getUserPublicKeyByForeignKeyRequest(foreignKey)
            .toUserPublicKey()
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByIdentityRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByIdentity(identity: String): UserPublicKey {
        return LockOperationsClient.getUserPublicKeyByIdentityRequest(identity)
            .toUserPublicKey()
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByEmailsRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByEmails(emails: List<String>): List<BatchUserPublicKey> {
        return LockOperationsClient.getUserPublicKeyByEmailsRequest(emails)
            .toBatchUserPublicKey()
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByTelephonesRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByTelephones(telephones: List<String>): List<BatchUserPublicKey> {
        return LockOperationsClient.getUserPublicKeyByTelephonesRequest(telephones)
            .toBatchUserPublicKey()
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByLocalKeysRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByLocalKeys(localKeys: List<String>): List<BatchUserPublicKey> {
        return LockOperationsClient.getUserPublicKeyByLocalKeysRequest(localKeys)
            .toBatchUserPublicKey()
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByForeignKeysRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByForeignKeys(foreignKeys: List<String>): List<BatchUserPublicKey> {
        return LockOperationsClient.getUserPublicKeyByForeignKeysRequest(foreignKeys)
            .toBatchUserPublicKey()
    }

    /**
     * @see LockOperationsClient.unlockRequest
     */
    @Throws(Exception::class)
    suspend fun unlock(unlockOperation: LockOperations.UnlockOperation) {
        return LockOperationsClient.unlockRequest(unlockOperation.toBasicUnlockOperation())
    }

    /**
     * @see LockOperationsClient.shareLockRequest
     */
    @Throws(Exception::class)
    suspend fun shareLock(shareLockOperation: LockOperations.ShareLockOperation) {
        return LockOperationsClient.shareLockRequest(shareLockOperation.toBasicShareLockOperation())
    }

    /**
     * @see LockOperationsClient.batchShareLockRequest
     */
    @Throws(Exception::class)
    suspend fun batchShareLock(batchShareLockOperation: LockOperations.BatchShareLockOperation) {
        return LockOperationsClient.batchShareLockRequest(batchShareLockOperation.toBasicBatchShareLockOperation())
    }

    /**
     * @see LockOperationsClient.revokeAccessToLockRequest
     */
    @Throws(Exception::class)
    suspend fun revokeAccessToLock(revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation) {
        return LockOperationsClient.revokeAccessToLockRequest(revokeAccessToLockOperation.toBasicRevokeAccessToLockOperation())
    }

    /**
     * @see LockOperationsClient.updateSecureSettingUnlockDurationRequest
     */
    @Throws(Exception::class)
    suspend fun updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration) {
        return LockOperationsClient.updateSecureSettingUnlockDurationRequest(updateSecureSettingUnlockDuration.toBasicUpdateSecureSettingUnlockDuration())
    }

    /**
     * @see LockOperationsClient.updateSecureSettingUnlockBetweenRequest
     */
    @Throws(Exception::class)
    suspend fun updateSecureSettingUnlockBetween(updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween) {
        return LockOperationsClient.updateSecureSettingUnlockBetweenRequest(updateSecureSettingUnlockBetween.toBasicUpdateSecureSettingUnlockBetween())
    }

    /**
     * @see LockOperationsClient.getPinnedLocksRequest
     */
    @Throws(Exception::class)
    suspend fun getPinnedLocks(): List<Lock> {
        return LockOperationsClient.getPinnedLocksRequest()
            .toLock()
    }

    /**
     * @see LockOperationsClient.getShareableLocksRequest
     */
    @Throws(Exception::class)
    suspend fun getShareableLocks(): List<ShareableLock> {
        return LockOperationsClient.getShareableLocksRequest()
            .toShareableLock()
    }
}

/**
 * Defines the platform-specific implementation of [LockOperationsApi]
 */
actual fun lockOperations(): LockOperationsApi = LockOperationsApi