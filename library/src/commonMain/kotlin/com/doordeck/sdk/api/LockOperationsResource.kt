package com.doordeck.sdk.api

import com.doordeck.sdk.api.model.LockOperations
import com.doordeck.sdk.api.responses.LockAuditTrail
import com.doordeck.sdk.api.responses.LockResponse
import com.doordeck.sdk.api.responses.LockUserResponse
import com.doordeck.sdk.api.responses.ShareableLockResponse
import com.doordeck.sdk.api.responses.UserAuditResponse
import com.doordeck.sdk.api.responses.UserLockResponse
import com.doordeck.sdk.api.responses.UserPublicKeyResponse
import com.doordeck.sdk.internal.api.DoordeckOnly
import kotlin.js.JsExport

@JsExport
interface LockOperationsResource {

    /**
     * Get a single lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-single-lock">API Doc</a>
     */
    fun getSingleLock(lockId: String): LockResponse

    /**
     * Get lock audit trail
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-lock-audit-trail-v2">API Doc</a>
     */
    fun getLockAuditTrail(lockId: String, start: Int, end: Int): Array<LockAuditTrail>

    /**
     * Get audit for a user
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-audit-for-a-user">API Doc</a>
     */
    fun getAuditForUser(lockId: String, start: Int, end: Int): Array<UserAuditResponse>

    /**
     * Get users for a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-users-for-a-lock">API Doc</a>
     */
    fun getUsersForLock(lockId: String): Array<UserLockResponse>

    /**
     * Get locks for a user
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-for-a-user">API Doc</a>
     */
    fun getLocksForUser(userId: String): LockUserResponse

    /**
     * Update lock properties - Name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun updateLockName(lockId: String, name: String? = null)

    /**
     * Update lock properties - Favourite
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun updateLockFavourite(lockId: String, favourite: Boolean? = null)

    /**
     * Update lock properties - Colour
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun updateLockColour(lockId: String, colour: String? = null)

    /**
     * Update lock properties - Settings - Default name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun updateLockSettingDefaultName(lockId: String, name: String? = null)

    /**
     * Update lock properties - Settings - Permitted addresses
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun updateLockSettingPermittedAddresses(lockId: String, permittedAddress: Array<String>? = null)

    /**
     * Update lock properties - Settings - Hidden
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun updateLockSettingHidden(lockId: String, hidden: Boolean? = null)

    /**
     * Update lock properties - Settings - Usage requirements - Time
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun updateLockSettingTimeRestrictions(lockId: String, time: LockOperations.TimeRequirement? = null)

    /**
     * Update lock properties - Settings - Usage requirements - Location
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun updateLockSettingLocationRestrictions(lockId: String, location: LockOperations.LocationRequirement? = null)

    /**
     * Get user’s public key
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-doordeck-user-s-public-key">API Doc</a>
     */
    @DoordeckOnly
    fun getUserPublicKey(userEmail: String, visitor: Boolean = false): UserPublicKeyResponse

    /**
     * Get a user’s public key by email
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-user-s-public-key">API Doc</a>
     */
    fun getUserPublicKeyByEmail(email: String): UserPublicKeyResponse

    /**
     * Get a user’s public key by telephone
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-user-s-public-key">API Doc</a>
     */
    fun getUserPublicKeyByTelephone(telephone: String): UserPublicKeyResponse

    /**
     * Get a user’s public key by local key
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-user-s-public-key">API Doc</a>
     */
    fun getUserPublicKeyByLocalKey(localKey: String): UserPublicKeyResponse

    /**
     * Get a user’s public key by foreign key
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-user-s-public-key">API Doc</a>
     */
    fun getUserPublicKeyByForeignKey(foreignKey: String): UserPublicKeyResponse

    /**
     * Get a user’s public key
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-user-s-public-key">API Doc</a>
     */
    fun getUserPublicKeyByIdentity(identity: String): UserPublicKeyResponse

    /**
     * Unlock
     *
     * @see <a href="https://developer.doordeck.com/docs/#unlock">API Doc</a>
     */
    fun unlock(unlockOperation: LockOperations.UnlockOperation)

    /**
     * Share a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#share-a-lock">API Doc</a>
     */
    fun shareLock(shareLockOperation: LockOperations.ShareLockOperation)

    /**
     * Revoke access to a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#revoke-access-to-a-lock">API Doc</a>
     */
    fun revokeAccessToLock(revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation)

    /**
     * Update secure settings - Unlock duration
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-secure-settings">API Doc</a>
     */
    fun updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration)

    /**
     * Update secure settings - Unlock between
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-secure-settings">API Doc</a>
     */
    fun uploadSecureSettingUnlockBetween(updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween)

    /**
     * Get pinned locks
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-pinned-locks">API Doc</a>
     */
    fun getPinnedLocks(): Array<LockResponse>

    /**
     * Get shareable locks
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-shareable-locks">API Doc</a>
     */
    fun getShareableLocks(): Array<ShareableLockResponse>
}