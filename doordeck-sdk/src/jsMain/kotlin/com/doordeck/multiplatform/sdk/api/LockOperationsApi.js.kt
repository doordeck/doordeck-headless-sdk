package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.annotations.DoordeckOnly
import com.doordeck.multiplatform.sdk.clients.LockOperationsClient
import com.doordeck.multiplatform.sdk.model.data.LockOperations
import com.doordeck.multiplatform.sdk.model.responses.AuditResponse
import com.doordeck.multiplatform.sdk.model.responses.BatchUserPublicKeyResponse
import com.doordeck.multiplatform.sdk.model.responses.LockResponse
import com.doordeck.multiplatform.sdk.model.responses.LockUserResponse
import com.doordeck.multiplatform.sdk.model.responses.ShareableLockResponse
import com.doordeck.multiplatform.sdk.model.responses.UserLockResponse
import com.doordeck.multiplatform.sdk.model.responses.UserPublicKeyResponse
import com.doordeck.multiplatform.sdk.util.promise
import kotlin.js.Promise

@JsExport
actual object LockOperationsApi {
    /**
     * Get a single lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-single-lock">API Doc</a>
     */
    fun getSingleLock(lockId: String): Promise<LockResponse> {
        return promise { LockOperationsClient.getSingleLockRequest(lockId) }
    }

    /**
     * Get lock audit trail
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-lock-audit-trail-v2">API Doc</a>
     */
    fun getLockAuditTrail(lockId: String, start: Int, end: Int): Promise<List<AuditResponse>> {
        return promise { LockOperationsClient.getLockAuditTrailRequest(lockId, start, end) }
    }

    /**
     * Get audit for a user
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-audit-for-a-user">API Doc</a>
     */
    fun getAuditForUser(userId: String, start: Int, end: Int): Promise<List<AuditResponse>> {
        return promise { LockOperationsClient.getAuditForUserRequest(userId, start, end) }
    }

    /**
     * Get users for a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-users-for-a-lock">API Doc</a>
     */
    fun getUsersForLock(lockId: String): Promise<List<UserLockResponse>> {
        return promise { LockOperationsClient.getUsersForLockRequest(lockId) }
    }

    /**
     * Get locks for a user
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-for-a-user">API Doc</a>
     */
    fun getLocksForUser(userId: String): Promise<LockUserResponse> {
        return promise { LockOperationsClient.getLocksForUserRequest(userId) }
    }

    /**
     * Update lock properties - Name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun updateLockName(lockId: String, name: String? = null): Promise<dynamic> {
        return promise { LockOperationsClient.updateLockNameRequest(lockId, name) }
    }

    /**
     * Update lock properties - Favourite
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun updateLockFavourite(lockId: String, favourite: Boolean? = null): Promise<dynamic> {
        return promise { LockOperationsClient.updateLockFavouriteRequest(lockId, favourite) }
    }

    /**
     * Update lock properties - Colour
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun updateLockColour(lockId: String, colour: String? = null): Promise<dynamic> {
        return promise { LockOperationsClient.updateLockColourRequest(lockId, colour) }
    }

    /**
     * Update lock properties - Settings - Default name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun updateLockSettingDefaultName(lockId: String, name: String? = null): Promise<dynamic> {
        return promise { LockOperationsClient.updateLockSettingDefaultNameRequest(lockId, name) }
    }

    /**
     * Set lock properties - Settings - Permitted addresses
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun setLockSettingPermittedAddresses(lockId: String, permittedAddresses: List<String>): Promise<dynamic> {
        return promise { LockOperationsClient.setLockSettingPermittedAddressesRequest(lockId, permittedAddresses) }
    }

    /**
     * Update lock properties - Settings - Hidden
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun updateLockSettingHidden(lockId: String, hidden: Boolean): Promise<dynamic> {
        return promise { LockOperationsClient.updateLockSettingHiddenRequest(lockId, hidden) }
    }

    /**
     * Set lock properties - Settings - Usage requirements - Time
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun setLockSettingTimeRestrictions(lockId: String, times: List<LockOperations.TimeRequirement>): Promise<dynamic> {
        return promise { LockOperationsClient.setLockSettingTimeRestrictionsRequest(lockId, times) }
    }

    /**
     * Update lock properties - Settings - Usage requirements - Location
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun updateLockSettingLocationRestrictions(lockId: String, location: LockOperations.LocationRequirement? = null): Promise<dynamic> {
        return promise { LockOperationsClient.updateLockSettingLocationRestrictionsRequest(lockId, location) }
    }

    /**
     * Get user’s public key
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-doordeck-user-s-public-key">API Doc</a>
     */
    @DoordeckOnly
    fun getUserPublicKey(userEmail: String, visitor: Boolean = false): Promise<UserPublicKeyResponse> {
        return promise { LockOperationsClient.getUserPublicKeyRequest(userEmail, visitor) }
    }

    /**
     * Get a user’s public key by email
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    fun getUserPublicKeyByEmail(email: String): Promise<UserPublicKeyResponse> {
        return promise { LockOperationsClient.getUserPublicKeyByEmailRequest(email) }
    }

    /**
     * Get a user’s public key by telephone
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    fun getUserPublicKeyByTelephone(telephone: String): Promise<UserPublicKeyResponse> {
        return promise { LockOperationsClient.getUserPublicKeyByTelephoneRequest(telephone) }
    }

    /**
     * Get a user’s public key by local key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    fun getUserPublicKeyByLocalKey(localKey: String): Promise<UserPublicKeyResponse> {
        return promise { LockOperationsClient.getUserPublicKeyByLocalKeyRequest(localKey) }
    }

    /**
     * Get a user’s public key by foreign key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    fun getUserPublicKeyByForeignKey(foreignKey: String): Promise<UserPublicKeyResponse> {
        return promise { LockOperationsClient.getUserPublicKeyByForeignKeyRequest(foreignKey) }
    }

    /**
     * Get a user’s public key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    fun getUserPublicKeyByIdentity(identity: String): Promise<UserPublicKeyResponse> {
        return promise { LockOperationsClient.getUserPublicKeyByIdentityRequest(identity) }
    }

    /**
     * Get a user’s public key by email
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v2">API Doc</a>
     */
    fun getUserPublicKeyByEmails(emails: List<String>): Promise<List<BatchUserPublicKeyResponse>> {
        return promise { LockOperationsClient.getUserPublicKeyByEmailsRequest(emails) }
    }

    /**
     * Get a user’s public key by telephone
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v2">API Doc</a>
     */
    fun getUserPublicKeyByTelephones(telephones: List<String>): Promise<List<BatchUserPublicKeyResponse>> {
        return promise { LockOperationsClient.getUserPublicKeyByTelephonesRequest(telephones) }
    }

    /**
     * Get a user’s public key by local key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v2">API Doc</a>
     */
    fun getUserPublicKeyByLocalKeys(localKeys: List<String>): Promise<List<BatchUserPublicKeyResponse>> {
        return promise { LockOperationsClient.getUserPublicKeyByLocalKeysRequest(localKeys) }
    }

    /**
     * Get a user’s public key by foreign key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v2">API Doc</a>
     */
    fun getUserPublicKeyByForeignKeys(foreignKeys: List<String>): Promise<List<BatchUserPublicKeyResponse>> {
        return promise { LockOperationsClient.getUserPublicKeyByForeignKeysRequest(foreignKeys) }
    }

    /**
     * Unlock
     *
     * @see <a href="https://developer.doordeck.com/docs/#unlock">API Doc</a>
     */
    fun unlock(unlockOperation: LockOperations.UnlockOperation): Promise<dynamic> {
        return promise { LockOperationsClient.unlockRequest(unlockOperation) }
    }

    /**
     * Share a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#share-a-lock">API Doc</a>
     */
    fun shareLock(shareLockOperation: LockOperations.ShareLockOperation): Promise<dynamic> {
        return promise { LockOperationsClient.shareLockRequest(shareLockOperation) }
    }

    /**
     * Batch share a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#batch-share-a-lock-v2">API Doc</a>
     */
    fun batchShareLock(batchShareLockOperation: LockOperations.BatchShareLockOperation): Promise<dynamic> {
        return promise { LockOperationsClient.batchShareLockRequest(batchShareLockOperation) }
    }

    /**
     * Revoke access to a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#revoke-access-to-a-lock">API Doc</a>
     */
    fun revokeAccessToLock(revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation): Promise<dynamic> {
        return promise { LockOperationsClient.revokeAccessToLockRequest(revokeAccessToLockOperation) }
    }

    /**
     * Update secure settings - Unlock duration
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-secure-settings">API Doc</a>
     */
    fun updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration): Promise<dynamic> {
        return promise { LockOperationsClient.updateSecureSettingUnlockDurationRequest(updateSecureSettingUnlockDuration) }
    }

    /**
     * Update secure settings - Unlock between
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-secure-settings">API Doc</a>
     */
    fun updateSecureSettingUnlockBetween(updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween): Promise<dynamic> {
        return promise { LockOperationsClient.updateSecureSettingUnlockBetweenRequest(updateSecureSettingUnlockBetween) }
    }

    /**
     * Get pinned locks
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-pinned-locks">API Doc</a>
     */
    fun getPinnedLocks(): Promise<List<LockResponse>> {
        return promise { LockOperationsClient.getPinnedLocksRequest() }
    }

    /**
     * Get shareable locks
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-shareable-locks">API Doc</a>
     */
    fun getShareableLocks(): Promise<List<ShareableLockResponse>> {
        return promise { LockOperationsClient.getShareableLocksRequest() }
    }
}

private val lockOperations = LockOperationsApi

@JsExport
actual fun lockOperations(): LockOperationsApi = lockOperations