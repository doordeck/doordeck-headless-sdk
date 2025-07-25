package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.LockOperationsClient
import com.doordeck.multiplatform.sdk.model.data.BasicLockOperations
import com.doordeck.multiplatform.sdk.model.responses.NetworkAuditResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkBatchUserPublicKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkLockResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkLockUserResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkShareableLockResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkUserLockResponse
import com.doordeck.multiplatform.sdk.model.responses.NetworkUserPublicKeyResponse

/**
 * Platform-specific implementations of lock-related API calls.
 */
actual object LockOperationsApi {
    /**
     * @see LockOperationsClient.getSingleLockRequest
     */
    @Throws(Exception::class)
    suspend fun getSingleLock(lockId: String): NetworkLockResponse {
        return LockOperationsClient.getSingleLockRequest(lockId)
    }

    /**
     * @see LockOperationsClient.getLockAuditTrailRequest
     */
    @Throws(Exception::class)
    suspend fun getLockAuditTrail(lockId: String, start: Long, end: Long): List<NetworkAuditResponse> {
        return LockOperationsClient.getLockAuditTrailRequest(lockId, start, end)
    }

    /**
     * @see LockOperationsClient.getAuditForUserRequest
     */
    @Throws(Exception::class)
    suspend fun getAuditForUser(userId: String, start: Long, end: Long): List<NetworkAuditResponse> {
        return LockOperationsClient.getAuditForUserRequest(userId, start, end)
    }

    /**
     * @see LockOperationsClient.getUsersForLockRequest
     */
    @Throws(Exception::class)
    suspend fun getUsersForLock(lockId: String): List<NetworkUserLockResponse> {
        return LockOperationsClient.getUsersForLockRequest(lockId)
    }

    /**
     * @see LockOperationsClient.getLocksForUserRequest
     */
    @Throws(Exception::class)
    suspend fun getLocksForUser(userId: String): NetworkLockUserResponse {
        return LockOperationsClient.getLocksForUserRequest(userId)
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
    suspend fun setLockSettingTimeRestrictions(lockId: String, times: List<BasicLockOperations.BasicTimeRequirement>) {
        return LockOperationsClient.setLockSettingTimeRestrictionsRequest(lockId, times)
    }

    /**
     * @see LockOperationsClient.updateLockSettingLocationRestrictionsRequest
     */
    @Throws(Exception::class)
    suspend fun updateLockSettingLocationRestrictions(lockId: String, location: BasicLockOperations.BasicLocationRequirement? = null) {
        return LockOperationsClient.updateLockSettingLocationRestrictionsRequest(lockId, location)
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyRequest
     */
    @DoordeckOnly
    @Throws(Exception::class)
    suspend fun getUserPublicKey(userEmail: String, visitor: Boolean = false): NetworkUserPublicKeyResponse {
        return LockOperationsClient.getUserPublicKeyRequest(userEmail, visitor)
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByEmailRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByEmail(email: String): NetworkUserPublicKeyResponse {
        return LockOperationsClient.getUserPublicKeyByEmailRequest(email)
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByTelephoneRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByTelephone(telephone: String): NetworkUserPublicKeyResponse {
        return LockOperationsClient.getUserPublicKeyByTelephoneRequest(telephone)
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByLocalKeyRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByLocalKey(localKey: String): NetworkUserPublicKeyResponse {
        return LockOperationsClient.getUserPublicKeyByLocalKeyRequest(localKey)
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByForeignKeyRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByForeignKey(foreignKey: String): NetworkUserPublicKeyResponse {
        return LockOperationsClient.getUserPublicKeyByForeignKeyRequest(foreignKey)
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByIdentityRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByIdentity(identity: String): NetworkUserPublicKeyResponse {
        return LockOperationsClient.getUserPublicKeyByIdentityRequest(identity)
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByEmailsRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByEmails(emails: List<String>): List<NetworkBatchUserPublicKeyResponse> {
        return LockOperationsClient.getUserPublicKeyByEmailsRequest(emails)
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByTelephonesRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByTelephones(telephones: List<String>): List<NetworkBatchUserPublicKeyResponse> {
        return LockOperationsClient.getUserPublicKeyByTelephonesRequest(telephones)
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByLocalKeysRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByLocalKeys(localKeys: List<String>): List<NetworkBatchUserPublicKeyResponse> {
        return LockOperationsClient.getUserPublicKeyByLocalKeysRequest(localKeys)
    }

    /**
     * @see LockOperationsClient.getUserPublicKeyByForeignKeysRequest
     */
    @Throws(Exception::class)
    suspend fun getUserPublicKeyByForeignKeys(foreignKeys: List<String>): List<NetworkBatchUserPublicKeyResponse> {
        return LockOperationsClient.getUserPublicKeyByForeignKeysRequest(foreignKeys)
    }

    /**
     * @see LockOperationsClient.unlockRequest
     */
    @Throws(Exception::class)
    suspend fun unlock(unlockOperation: BasicLockOperations.BasicUnlockOperation) {
        return LockOperationsClient.unlockRequest(unlockOperation)
    }

    /**
     * @see LockOperationsClient.shareLockRequest
     */
    @Throws(Exception::class)
    suspend fun shareLock(shareLockOperation: BasicLockOperations.BasicShareLockOperation) {
        return LockOperationsClient.shareLockRequest(shareLockOperation)
    }

    /**
     * @see LockOperationsClient.batchShareLockRequest
     */
    @Throws(Exception::class)
    suspend fun batchShareLock(batchShareLockOperation: BasicLockOperations.BasicBatchShareLockOperation) {
        return LockOperationsClient.batchShareLockRequest(batchShareLockOperation)
    }

    /**
     * @see LockOperationsClient.revokeAccessToLockRequest
     */
    @Throws(Exception::class)
    suspend fun revokeAccessToLock(revokeAccessToLockOperation: BasicLockOperations.BasicRevokeAccessToLockOperation) {
        return LockOperationsClient.revokeAccessToLockRequest(revokeAccessToLockOperation)
    }

    /**
     * @see LockOperationsClient.updateSecureSettingUnlockDurationRequest
     */
    @Throws(Exception::class)
    suspend fun updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration: BasicLockOperations.BasicUpdateSecureSettingUnlockDuration) {
        return LockOperationsClient.updateSecureSettingUnlockDurationRequest(updateSecureSettingUnlockDuration)
    }

    /**
     * @see LockOperationsClient.updateSecureSettingUnlockBetweenRequest
     */
    @Throws(Exception::class)
    suspend fun updateSecureSettingUnlockBetween(updateSecureSettingUnlockBetween: BasicLockOperations.BasicUpdateSecureSettingUnlockBetween) {
        return LockOperationsClient.updateSecureSettingUnlockBetweenRequest(updateSecureSettingUnlockBetween)
    }

    /**
     * @see LockOperationsClient.getPinnedLocksRequest
     */
    @Throws(Exception::class)
    suspend fun getPinnedLocks(): List<NetworkLockResponse> {
        return LockOperationsClient.getPinnedLocksRequest()
    }

    /**
     * @see LockOperationsClient.getShareableLocksRequest
     */
    @Throws(Exception::class)
    suspend fun getShareableLocks(): List<NetworkShareableLockResponse> {
        return LockOperationsClient.getShareableLocksRequest()
    }
}

/**
 * Defines the platform-specific implementation of [LockOperationsApi]
 */
actual fun lockOperations(): LockOperationsApi = LockOperationsApi