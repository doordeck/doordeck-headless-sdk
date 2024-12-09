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
import com.doordeck.multiplatform.sdk.internal.api.LockOperationsClient
import com.doordeck.multiplatform.sdk.internal.api.LockOperationsResourceImpl
import io.ktor.client.*
import org.koin.mp.KoinPlatform.getKoin
import kotlin.js.Promise

@JsExport
actual interface LockOperationsResource {
    /**
     * Get a single lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-single-lock">API Doc</a>
     */
    fun getSingleLock(lockId: String): Promise<LockResponse>

    /**
     * Get lock audit trail
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-lock-audit-trail-v2">API Doc</a>
     */
    fun getLockAuditTrail(lockId: String, start: Int, end: Int): Promise<List<AuditResponse>>

    /**
     * Get audit for a user
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-audit-for-a-user">API Doc</a>
     */
    fun getAuditForUser(userId: String, start: Int, end: Int): Promise<List<AuditResponse>>

    /**
     * Get users for a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-users-for-a-lock">API Doc</a>
     */
    fun getUsersForLock(lockId: String): Promise<List<UserLockResponse>>

    /**
     * Get locks for a user
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-for-a-user">API Doc</a>
     */
    fun getLocksForUser(userId: String): Promise<LockUserResponse>

    /**
     * Update lock properties - Name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun updateLockName(lockId: String, name: String? = null): Promise<dynamic>

    /**
     * Update lock properties - Favourite
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun updateLockFavourite(lockId: String, favourite: Boolean? = null): Promise<dynamic>

    /**
     * Update lock properties - Colour
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun updateLockColour(lockId: String, colour: String? = null): Promise<dynamic>

    /**
     * Update lock properties - Settings - Default name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun updateLockSettingDefaultName(lockId: String, name: String? = null): Promise<dynamic>

    /**
     * Set lock properties - Settings - Permitted addresses
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun setLockSettingPermittedAddresses(lockId: String, permittedAddresses: List<String>): Promise<dynamic>

    /**
     * Update lock properties - Settings - Hidden
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun updateLockSettingHidden(lockId: String, hidden: Boolean): Promise<dynamic>

    /**
     * Set lock properties - Settings - Usage requirements - Time
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun setLockSettingTimeRestrictions(lockId: String, times: List<LockOperations.TimeRequirement>): Promise<dynamic>

    /**
     * Update lock properties - Settings - Usage requirements - Location
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    fun updateLockSettingLocationRestrictions(lockId: String, location: LockOperations.LocationRequirement? = null): Promise<dynamic>

    /**
     * Get user’s public key
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-doordeck-user-s-public-key">API Doc</a>
     */
    @DoordeckOnly
    fun getUserPublicKey(userEmail: String, visitor: Boolean = false): Promise<UserPublicKeyResponse>

    /**
     * Get a user’s public key by email
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    fun getUserPublicKeyByEmail(email: String): Promise<UserPublicKeyResponse>

    /**
     * Get a user’s public key by telephone
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    fun getUserPublicKeyByTelephone(telephone: String): Promise<UserPublicKeyResponse>

    /**
     * Get a user’s public key by local key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    fun getUserPublicKeyByLocalKey(localKey: String): Promise<UserPublicKeyResponse>

    /**
     * Get a user’s public key by foreign key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    fun getUserPublicKeyByForeignKey(foreignKey: String): Promise<UserPublicKeyResponse>

    /**
     * Get a user’s public key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v1">API Doc</a>
     */
    fun getUserPublicKeyByIdentity(identity: String): Promise<UserPublicKeyResponse>

    /**
     * Get a user’s public key by email
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v2">API Doc</a>
     */
    fun getUserPublicKeyByEmails(emails: List<String>): Promise<List<BatchUserPublicKeyResponse>>

    /**
     * Get a user’s public key by telephone
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v2">API Doc</a>
     */
    fun getUserPublicKeyByTelephones(telephones: List<String>): Promise<List<BatchUserPublicKeyResponse>>

    /**
     * Get a user’s public key by local key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v2">API Doc</a>
     */
    fun getUserPublicKeyByLocalKeys(localKeys: List<String>): Promise<List<BatchUserPublicKeyResponse>>

    /**
     * Get a user’s public key by foreign key
     *
     * @see <a href="https://developer.doordeck.com/docs/#lookup-user-public-key-v2">API Doc</a>
     */
    fun getUserPublicKeyByForeignKeys(foreignKeys: List<String>): Promise<List<BatchUserPublicKeyResponse>>

    /**
     * Unlock
     *
     * @see <a href="https://developer.doordeck.com/docs/#unlock">API Doc</a>
     */
    fun unlock(unlockOperation: LockOperations.UnlockOperation): Promise<dynamic>

    /**
     * Share a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#share-a-lock">API Doc</a>
     */
    fun shareLock(shareLockOperation: LockOperations.ShareLockOperation): Promise<dynamic>

    /**
     * Revoke access to a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#revoke-access-to-a-lock">API Doc</a>
     */
    fun revokeAccessToLock(revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation): Promise<dynamic>

    /**
     * Update secure settings - Unlock duration
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-secure-settings">API Doc</a>
     */
    fun updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration): Promise<dynamic>

    /**
     * Update secure settings - Unlock between
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-secure-settings">API Doc</a>
     */
    fun updateSecureSettingUnlockBetween(updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween): Promise<dynamic>

    /**
     * Get pinned locks
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-pinned-locks">API Doc</a>
     */
    fun getPinnedLocks(): Promise<List<LockResponse>>

    /**
     * Get shareable locks
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-shareable-locks">API Doc</a>
     */
    fun getShareableLocks(): Promise<List<ShareableLockResponse>>
}

private val lockOperations = LockOperationsResourceImpl(getKoin().get<LockOperationsClient>())

@JsExport
actual fun lockOperations(): LockOperationsResource = lockOperations