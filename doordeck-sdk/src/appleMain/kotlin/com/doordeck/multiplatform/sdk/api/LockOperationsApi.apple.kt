package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.LockOperationsClient
import com.doordeck.multiplatform.sdk.model.responses.AuditResponse
import com.doordeck.multiplatform.sdk.model.responses.BatchUserPublicKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.LockResponse
import com.doordeck.multiplatform.sdk.model.data.LockOperations
import com.doordeck.multiplatform.sdk.model.responses.LockUserResponse
import com.doordeck.multiplatform.sdk.model.responses.ShareableLockResponse
import com.doordeck.multiplatform.sdk.model.responses.UserLockResponse
import com.doordeck.multiplatform.sdk.model.responses.UserPublicKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.toAuditResponse
import com.doordeck.multiplatform.sdk.model.data.toBasicBatchShareLockOperation
import com.doordeck.multiplatform.sdk.model.data.toBasicLocationRequirement
import com.doordeck.multiplatform.sdk.model.data.toBasicRevokeAccessToLockOperation
import com.doordeck.multiplatform.sdk.model.data.toBasicShareLockOperation
import com.doordeck.multiplatform.sdk.model.data.toBasicTimeRequirement
import com.doordeck.multiplatform.sdk.model.data.toBasicUnlockOperation
import com.doordeck.multiplatform.sdk.model.data.toBasicUpdateSecureSettingUnlockBetween
import com.doordeck.multiplatform.sdk.model.data.toBasicUpdateSecureSettingUnlockDuration
import com.doordeck.multiplatform.sdk.model.responses.toBatchUserPublicKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.toLockResponse
import com.doordeck.multiplatform.sdk.model.responses.toLockUserResponse
import com.doordeck.multiplatform.sdk.model.responses.toShareableLockResponse
import com.doordeck.multiplatform.sdk.model.responses.toUserLockResponse
import com.doordeck.multiplatform.sdk.model.responses.toUserPublicKeyResponse

/**
 * Platform-specific implementations of lock-related API calls.
 */
actual object LockOperationsApi {
    /**
     * @see LockOperationsClient.getSingleLockRequest
     */
    @Throws(Exception::class)
    suspend fun getSingleLock(lockId: String): LockResponse {
        return LockOperationsClient.getSingleLockRequest(lockId)
            .toLockResponse()
    }

    /**
     * @see LockOperationsClient.getLockAuditTrailRequest
     */
    @Throws(Exception::class)
    suspend fun getLockAuditTrail(lockId: String, start: Long, end: Long): List<AuditResponse> {
        return LockOperationsClient.getLockAuditTrailRequest(lockId, start, end)
            .toAuditResponse()
    }

    /**
     * @see LockOperationsClient.getAuditForUserRequest
     */
    @Throws(Exception::class)
    suspend fun getAuditForUser(userId: String, start: Long, end: Long): List<AuditResponse> {
        return LockOperationsClient.getAuditForUserRequest(userId, start, end)
            .toAuditResponse()
    }

    /**
     * @see LockOperationsClient.getUsersForLockRequest
     */
    @Throws(Exception::class)
    suspend fun getUsersForLock(lockId: String): List<UserLockResponse> {
        return LockOperationsClient.getUsersForLockRequest(lockId)
            .toUserLockResponse()
    }

    /**
     * @see LockOperationsClient.getLocksForUserRequest
     */
    @Throws(Exception::class)
    suspend fun getLocksForUser(userId: String): LockUserResponse {
        return LockOperationsClient.getLocksForUserRequest(userId)
            .toLockUserResponse()
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
    suspend fun updateLockFavourite(lockId: String, favourite: Boolean) {
        return LockOperationsClient.updateLockFavouriteRequest(lockId, favourite)
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
    suspend fun getUserPublicKey(userEmail: String, visitor: Boolean = false): UserPublicKeyResponse {
        return LockOperationsClient.getUserPublicKeyRequest(userEmail, visitor)
            .toUserPublicKeyResponse()
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByEmailRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByEmail(email: String): UserPublicKeyResponse {
        return LockOperationsClient.getUserPublicKeyByEmailRequest(email)
            .toUserPublicKeyResponse()
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByTelephoneRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByTelephone(telephone: String): UserPublicKeyResponse {
        return LockOperationsClient.getUserPublicKeyByTelephoneRequest(telephone)
            .toUserPublicKeyResponse()
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByLocalKeyRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByLocalKey(localKey: String): UserPublicKeyResponse {
        return LockOperationsClient.getUserPublicKeyByLocalKeyRequest(localKey)
            .toUserPublicKeyResponse()
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByForeignKeyRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByForeignKey(foreignKey: String): UserPublicKeyResponse {
        return LockOperationsClient.getUserPublicKeyByForeignKeyRequest(foreignKey)
            .toUserPublicKeyResponse()
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByIdentityRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByIdentity(identity: String): UserPublicKeyResponse {
        return LockOperationsClient.getUserPublicKeyByIdentityRequest(identity)
            .toUserPublicKeyResponse()
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByEmailsRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByEmails(emails: List<String>): List<BatchUserPublicKeyResponse> {
        return LockOperationsClient.getUserPublicKeyByEmailsRequest(emails)
            .toBatchUserPublicKeyResponse()
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByTelephonesRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByTelephones(telephones: List<String>): List<BatchUserPublicKeyResponse> {
        return LockOperationsClient.getUserPublicKeyByTelephonesRequest(telephones)
            .toBatchUserPublicKeyResponse()
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByLocalKeysRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByLocalKeys(localKeys: List<String>): List<BatchUserPublicKeyResponse> {
        return LockOperationsClient.getUserPublicKeyByLocalKeysRequest(localKeys)
            .toBatchUserPublicKeyResponse()
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByForeignKeysRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByForeignKeys(foreignKeys: List<String>): List<BatchUserPublicKeyResponse> {
        return LockOperationsClient.getUserPublicKeyByForeignKeysRequest(foreignKeys)
            .toBatchUserPublicKeyResponse()
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
    suspend fun getPinnedLocks(): List<LockResponse> {
        return LockOperationsClient.getPinnedLocksRequest()
            .toLockResponse()
    }

    /**
     * @see LockOperationsClient.getShareableLocksRequest
     */
    @Throws(Exception::class)
    suspend fun getShareableLocks(): List<ShareableLockResponse> {
        return LockOperationsClient.getShareableLocksRequest()
            .toShareableLockResponse()
    }
}

/**
 * Defines the platform-specific implementation of [LockOperationsApi]
 */
actual fun lockOperations(): LockOperationsApi = LockOperationsApi