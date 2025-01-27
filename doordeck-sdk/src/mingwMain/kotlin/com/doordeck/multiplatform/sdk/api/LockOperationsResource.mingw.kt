package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.api.model.LockOperations
import com.doordeck.multiplatform.sdk.api.responses.AuditResponse
import com.doordeck.multiplatform.sdk.api.responses.BatchUserPublicKeyResponse
import com.doordeck.multiplatform.sdk.api.responses.LockResponse
import com.doordeck.multiplatform.sdk.api.responses.LockUserResponse
import com.doordeck.multiplatform.sdk.api.responses.ShareableLockResponse
import com.doordeck.multiplatform.sdk.api.responses.UserLockResponse
import com.doordeck.multiplatform.sdk.api.responses.UserPublicKeyResponse
import com.doordeck.multiplatform.sdk.internal.api.DoordeckOnly
import com.doordeck.multiplatform.sdk.internal.api.LockOperationsResourceImpl

actual interface LockOperationsResource {
    /**
     * Get a single lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-single-lock">API Doc</a>
     */
    fun getSingleLock(lockId: String): LockResponse

    @CName("getSingleLockJson")
    fun getSingleLockJson(data: String): String

    /**
     * Get lock audit trail
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-lock-audit-trail-v2">API Doc</a>
     */
    fun getLockAuditTrail(lockId: String, start: Int, end: Int): List<AuditResponse>

    @CName("getLockAuditTrailJson")
    fun getLockAuditTrailJson(data: String): String

    /**
     * Get audit for a user
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-audit-for-a-user">API Doc</a>
     */
    fun getAuditForUser(userId: String, start: Int, end: Int): List<AuditResponse>

    @CName("getAuditForUserJson")
    fun getAuditForUserJson(data: String): String

    /**
     * Get users for a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-users-for-a-lock">API Doc</a>
     */
    fun getUsersForLock(lockId: String): List<UserLockResponse>

    @CName("getUsersForLockJson")
    fun getUsersForLockJson(data: String): String

    /**
     * Get locks for a user
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-for-a-user">API Doc</a>
     */
    fun getLocksForUser(userId: String): LockUserResponse

    @CName("getLocksForUserJson")
    fun getLocksForUserJson(data: String): String

    /**
     * Update lock properties - Name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun updateLockName(lockId: String, name: String? = null)

    @CName("updateLockNameJson")
    fun updateLockNameJson(data: String): String

    /**
     * Update lock properties - Favourite
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun updateLockFavourite(lockId: String, favourite: Boolean? = null)

    @CName("updateLockFavouriteJson")
    fun updateLockFavouriteJson(data: String): String

    /**
     * Update lock properties - Colour
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun updateLockColour(lockId: String, colour: String? = null)

    @CName("updateLockColourJson")
    fun updateLockColourJson(data: String): String

    /**
     * Update lock properties - Settings - Default name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun updateLockSettingDefaultName(lockId: String, name: String? = null)

    @CName("updateLockSettingDefaultNameJson")
    fun updateLockSettingDefaultNameJson(data: String): String

    /**
     * Set lock properties - Settings - Permitted addresses
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun setLockSettingPermittedAddresses(lockId: String, permittedAddresses: List<String>)

    @CName("setLockSettingPermittedAddressesJson")
    fun setLockSettingPermittedAddressesJson(data: String): String

    /**
     * Update lock properties - Settings - Hidden
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun updateLockSettingHidden(lockId: String, hidden: Boolean)

    @CName("updateLockSettingHiddenJson")
    fun updateLockSettingHiddenJson(data: String): String

    /**
     * Set lock properties - Settings - Usage requirements - Time
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun setLockSettingTimeRestrictions(lockId: String, times: List<LockOperations.TimeRequirement>)

    @CName("setLockSettingTimeRestrictionsJson")
    fun setLockSettingTimeRestrictionsJson(data: String): String

    /**
     * Update lock properties - Settings - Usage requirements - Location
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun updateLockSettingLocationRestrictions(lockId: String, location: LockOperations.LocationRequirement? = null)

    @CName("updateLockSettingLocationRestrictionsJson")
    fun updateLockSettingLocationRestrictionsJson(data: String): String

    /**
     * Get user’s public key
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-doordeck-user-s-public-key">API Doc</a>
     */
    @DoordeckOnly
    fun getUserPublicKey(userEmail: String, visitor: Boolean = false): UserPublicKeyResponse

    @DoordeckOnly
    @CName("getUserPublicKeyJson")
    fun getUserPublicKeyJson(data: String): String

    /**
     * Get a user’s public key by email
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    fun getUserPublicKeyByEmail(email: String): UserPublicKeyResponse

    @CName("getUserPublicKeyByEmailJson")
    fun getUserPublicKeyByEmailJson(data: String): String

    /**
     * Get a user’s public key by telephone
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    fun getUserPublicKeyByTelephone(telephone: String): UserPublicKeyResponse

    @CName("getUserPublicKeyByTelephoneJson")
    fun getUserPublicKeyByTelephoneJson(data: String): String

    /**
     * Get a user’s public key by local key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    fun getUserPublicKeyByLocalKey(localKey: String): UserPublicKeyResponse

    @CName("getUserPublicKeyByLocalKeyJson")
    fun getUserPublicKeyByLocalKeyJson(data: String): String

    /**
     * Get a user’s public key by foreign key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    fun getUserPublicKeyByForeignKey(foreignKey: String): UserPublicKeyResponse

    @CName("getUserPublicKeyByForeignKeyJson")
    fun getUserPublicKeyByForeignKeyJson(data: String): String

    /**
     * Get a user’s public key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    fun getUserPublicKeyByIdentity(identity: String): UserPublicKeyResponse

    @CName("getUserPublicKeyByIdentityJson")
    fun getUserPublicKeyByIdentityJson(data: String): String

    /**
     * Get a user’s public key by email
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v2">API Doc</a>
     */
    fun getUserPublicKeyByEmails(emails: List<String>): List<BatchUserPublicKeyResponse>

    @CName("getUserPublicKeyByEmailsJson")
    fun getUserPublicKeyByEmailsJson(data: String): String

    /**
     * Get a user’s public key by telephone
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v2">API Doc</a>
     */
    fun getUserPublicKeyByTelephones(telephones: List<String>): List<BatchUserPublicKeyResponse>

    @CName("getUserPublicKeyByTelephonesJson")
    fun getUserPublicKeyByTelephonesJson(data: String): String

    /**
     * Get a user’s public key by local key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v2">API Doc</a>
     */
    fun getUserPublicKeyByLocalKeys(localKeys: List<String>): List<BatchUserPublicKeyResponse>

    @CName("getUserPublicKeyByLocalKeysJson")
    fun getUserPublicKeyByLocalKeysJson(data: String): String

    /**
     * Get a user’s public key by foreign key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v2">API Doc</a>
     */
    fun getUserPublicKeyByForeignKeys(foreignKeys: List<String>): List<BatchUserPublicKeyResponse>

    @CName("getUserPublicKeyByForeignKeysJson")
    fun getUserPublicKeyByForeignKeysJson(data: String): String

    /**
     * Unlock
     *
     * @see <a href="https://developer.doordeck.com/docs/#unlock">API Doc</a>
     */
    fun unlock(unlockOperation: LockOperations.UnlockOperation)

    @CName("unlockJson")
    fun unlockJson(data: String): String

    /**
     * Share a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#share-a-lock">API Doc</a>
     */
    fun shareLock(shareLockOperation: LockOperations.ShareLockOperation)

    @CName("shareLockJson")
    fun shareLockJson(data: String): String

    /**
     * Batch share a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#batch-share-a-lock-v2">API Doc</a>
     */
    fun batchShareLock(batchShareLockOperation: LockOperations.BatchShareLockOperation)

    @CName("batchShareLockJson")
    fun batchShareLockJson(data: String): String

    /**
     * Revoke access to a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#revoke-access-to-a-lock">API Doc</a>
     */
    fun revokeAccessToLock(revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation)

    @CName("revokeAccessToLockJson")
    fun revokeAccessToLockJson(data: String): String

    /**
     * Update secure settings - Unlock duration
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-secure-settings">API Doc</a>
     */
    fun updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration)

    @CName("updateSecureSettingUnlockDurationJson")
    fun updateSecureSettingUnlockDurationJson(data: String): String

    /**
     * Update secure settings - Unlock between
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-secure-settings">API Doc</a>
     */
    fun updateSecureSettingUnlockBetween(updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween)

    @CName("updateSecureSettingUnlockBetweenJson")
    fun updateSecureSettingUnlockBetweenJson(data: String): String

    /**
     * Get pinned locks
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-pinned-locks">API Doc</a>
     */
    fun getPinnedLocks(): List<LockResponse>

    @CName("getPinnedLocksJson")
    fun getPinnedLocksJson(): String

    /**
     * Get shareable locks
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-shareable-locks">API Doc</a>
     */
    fun getShareableLocks(): List<ShareableLockResponse>

    @CName("getShareableLocksJson")
    fun getShareableLocksJson(): String
}

actual fun lockOperations(): LockOperationsResource = LockOperationsResourceImpl