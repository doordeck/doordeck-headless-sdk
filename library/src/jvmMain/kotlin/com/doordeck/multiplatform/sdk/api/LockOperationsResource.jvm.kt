package com.doordeck.multiplatform.sdk.api

import com.doordeck.multiplatform.sdk.MissingOperationContextException
import com.doordeck.multiplatform.sdk.api.model.LockOperations
import com.doordeck.multiplatform.sdk.api.responses.LockAuditTrailResponse
import com.doordeck.multiplatform.sdk.api.responses.LockResponse
import com.doordeck.multiplatform.sdk.api.responses.LockUserResponse
import com.doordeck.multiplatform.sdk.api.responses.ShareableLockResponse
import com.doordeck.multiplatform.sdk.api.responses.UserAuditResponse
import com.doordeck.multiplatform.sdk.api.responses.UserLockResponse
import com.doordeck.multiplatform.sdk.api.responses.UserPublicKeyResponse
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import com.doordeck.multiplatform.sdk.internal.api.DoordeckOnly
import com.doordeck.multiplatform.sdk.internal.api.LocalUnlockClientImpl
import com.doordeck.multiplatform.sdk.internal.api.LockOperationsResourceImpl
import io.ktor.client.HttpClient
import org.koin.core.qualifier.named
import org.koin.mp.KoinPlatform.getKoin
import java.util.concurrent.CompletableFuture

actual interface LockOperationsResource {
    /**
     * Get a single lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-single-lock">API Doc</a>
     */
    suspend fun getSingleLock(lockId: String): LockResponse

    fun getSingleLockFuture(lockId: String): CompletableFuture<LockResponse>

    /**
     * Get lock audit trail
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-lock-audit-trail-v2">API Doc</a>
     */
    suspend fun getLockAuditTrail(lockId: String, start: Int, end: Int): List<LockAuditTrailResponse>

    fun getLockAuditTrailFuture(lockId: String, start: Int, end: Int): CompletableFuture<List<LockAuditTrailResponse>>

    /**
     * Get audit for a user
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-audit-for-a-user">API Doc</a>
     */
    suspend fun getAuditForUser(userId: String, start: Int, end: Int): List<UserAuditResponse>

    fun getAuditForUserFuture(userId: String, start: Int, end: Int): CompletableFuture<List<UserAuditResponse>>

    /**
     * Get users for a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-users-for-a-lock">API Doc</a>
     */
    suspend fun getUsersForLock(lockId: String): List<UserLockResponse>

    fun getUsersForLockFuture(lockId: String): CompletableFuture<List<UserLockResponse>>

    /**
     * Get locks for a user
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-locks-for-a-user">API Doc</a>
     */
    suspend fun getLocksForUser(userId: String): LockUserResponse

    fun getLocksForUserFuture(userId: String): CompletableFuture<LockUserResponse>

    /**
     * Update lock properties - Name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    suspend fun updateLockName(lockId: String, name: String? = null)

    fun updateLockNameFuture(lockId: String, name: String? = null): CompletableFuture<Unit>

    /**
     * Update lock properties - Favourite
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    suspend fun updateLockFavourite(lockId: String, favourite: Boolean? = null)

    fun updateLockFavouriteFuture(lockId: String, favourite: Boolean? = null): CompletableFuture<Unit>

    /**
     * Update lock properties - Colour
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    suspend fun updateLockColour(lockId: String, colour: String? = null)

    fun updateLockColourFuture(lockId: String, colour: String? = null): CompletableFuture<Unit>

    /**
     * Update lock properties - Settings - Default name
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    suspend fun updateLockSettingDefaultName(lockId: String, name: String? = null)

    fun updateLockSettingDefaultNameFuture(lockId: String, name: String? = null): CompletableFuture<Unit>

    /**
     * Set lock properties - Settings - Permitted addresses
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    suspend fun setLockSettingPermittedAddresses(lockId: String, permittedAddresses: List<String>)

    fun setLockSettingPermittedAddressesFuture(lockId: String, permittedAddresses: List<String>): CompletableFuture<Unit>

    /**
     * Update lock properties - Settings - Hidden
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    suspend fun updateLockSettingHidden(lockId: String, hidden: Boolean)

    fun updateLockSettingHiddenFuture(lockId: String, hidden: Boolean): CompletableFuture<Unit>

    /**
     * Set lock properties - Settings - Usage requirements - Time
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    suspend fun setLockSettingTimeRestrictions(lockId: String, times: List<LockOperations.TimeRequirement>)

    fun setLockSettingTimeRestrictionsFuture(lockId: String, times: List<LockOperations.TimeRequirement>): CompletableFuture<Unit>

    /**
     * Update lock properties - Settings - Usage requirements - Location
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-lock-properties">API Doc</a>
     */
    suspend fun updateLockSettingLocationRestrictions(lockId: String, location: LockOperations.LocationRequirement? = null)

    fun updateLockSettingLocationRestrictionsFuture(lockId: String, location: LockOperations.LocationRequirement? = null): CompletableFuture<Unit>

    /**
     * Get user’s public key
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-doordeck-user-s-public-key">API Doc</a>
     */
    @DoordeckOnly
    suspend fun getUserPublicKey(userEmail: String, visitor: Boolean = false): UserPublicKeyResponse

    @DoordeckOnly
    suspend fun getUserPublicKeyFuture(userEmail: String, visitor: Boolean = false): CompletableFuture<UserPublicKeyResponse>

    /**
     * Get a user’s public key by email
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-user-s-public-key">API Doc</a>
     */
    suspend fun getUserPublicKeyByEmail(email: String): UserPublicKeyResponse

    fun getUserPublicKeyByEmailFuture(email: String): CompletableFuture<UserPublicKeyResponse>

    /**
     * Get a user’s public key by telephone
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-user-s-public-key">API Doc</a>
     */
    suspend fun getUserPublicKeyByTelephone(telephone: String): UserPublicKeyResponse

    fun getUserPublicKeyByTelephoneFuture(telephone: String): CompletableFuture<UserPublicKeyResponse>

    /**
     * Get a user’s public key by local key
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-user-s-public-key">API Doc</a>
     */
    suspend fun getUserPublicKeyByLocalKey(localKey: String): UserPublicKeyResponse

    fun getUserPublicKeyByLocalKeyFuture(localKey: String): CompletableFuture<UserPublicKeyResponse>

    /**
     * Get a user’s public key by foreign key
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-user-s-public-key">API Doc</a>
     */
    suspend fun getUserPublicKeyByForeignKey(foreignKey: String): UserPublicKeyResponse

    fun getUserPublicKeyByForeignKeyFuture(foreignKey: String): CompletableFuture<UserPublicKeyResponse>

    /**
     * Get a user’s public key
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-a-user-s-public-key">API Doc</a>
     */
    suspend fun getUserPublicKeyByIdentity(identity: String): UserPublicKeyResponse

    fun getUserPublicKeyByIdentityFuture(identity: String): CompletableFuture<UserPublicKeyResponse>

    /**
     * Unlock
     * @throws MissingOperationContextException if the operation context has not been set
     *
     * @see <a href="https://developer.doordeck.com/docs/#unlock">API Doc</a>
     */
    suspend fun unlockWithContext(lockId: String, directAccessEndpoints: List<String>? = null)

    fun unlockWithContextFuture(lockId: String, directAccessEndpoints: List<String>? = null): CompletableFuture<Unit>

    /**
     * Unlock
     *
     * @see <a href="https://developer.doordeck.com/docs/#unlock">API Doc</a>
     */
    suspend fun unlock(unlockOperation: LockOperations.UnlockOperation)

    fun unlockFuture(unlockOperation: LockOperations.UnlockOperation): CompletableFuture<Unit>

    /**
     * Share a lock
     * @throws MissingOperationContextException if the operation context has not been set
     *
     * @see <a href="https://developer.doordeck.com/docs/#share-a-lock">API Doc</a>
     */
    suspend fun shareLockWithContext(lockId: String, shareLock: LockOperations.ShareLock)

    fun shareLockWithContextFuture(lockId: String, shareLock: LockOperations.ShareLock): CompletableFuture<Unit>

    /**
     * Share a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#share-a-lock">API Doc</a>
     */
    suspend fun shareLock(shareLockOperation: LockOperations.ShareLockOperation)

    fun shareLockFuture(shareLockOperation: LockOperations.ShareLockOperation): CompletableFuture<Unit>

    /**
     * Revoke access to a lock
     * @throws MissingOperationContextException if the operation context has not been set
     *
     * @see <a href="https://developer.doordeck.com/docs/#revoke-access-to-a-lock">API Doc</a>
     */
    suspend fun revokeAccessToLockWithContext(lockId: String, users: List<String>)

    fun revokeAccessToLockWithContextFuture(lockId: String, users: List<String>): CompletableFuture<Unit>

    /**
     * Revoke access to a lock
     *
     * @see <a href="https://developer.doordeck.com/docs/#revoke-access-to-a-lock">API Doc</a>
     */
    suspend fun revokeAccessToLock(revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation)

    fun revokeAccessToLockFuture(revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation): CompletableFuture<Unit>

    /**
     * Update secure settings - Unlock duration
     * @throws MissingOperationContextException if the operation context has not been set
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-secure-settings">API Doc</a>
     */
    suspend fun updateSecureSettingUnlockDurationWithContext(lockId: String, unlockDuration: Int)

    fun updateSecureSettingUnlockDurationWithContextFuture(lockId: String, unlockDuration: Int): CompletableFuture<Unit>

    /**
     * Update secure settings - Unlock duration
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-secure-settings">API Doc</a>
     */
    suspend fun updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration)

    fun updateSecureSettingUnlockDurationFuture(updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration): CompletableFuture<Unit>

    /**
     * Update secure settings - Unlock between
     * @throws MissingOperationContextException if the operation context has not been set
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-secure-settings">API Doc</a>
     */
    suspend fun updateSecureSettingUnlockBetweenWithContext(lockId: String, unlockBetween: LockOperations.UnlockBetween?)

    fun updateSecureSettingUnlockBetweenWithContextFuture(lockId: String, unlockBetween: LockOperations.UnlockBetween?): CompletableFuture<Unit>

    /**
     * Update secure settings - Unlock between
     *
     * @see <a href="https://developer.doordeck.com/docs/#update-secure-settings">API Doc</a>
     */
    suspend fun updateSecureSettingUnlockBetween(updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween)

    fun updateSecureSettingUnlockBetweenFuture(updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween): CompletableFuture<Unit>

    /**
     * Get pinned locks
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-pinned-locks">API Doc</a>
     */
    suspend fun getPinnedLocks(): List<LockResponse>

    fun getPinnedLocksFuture(): CompletableFuture<List<LockResponse>>

    /**
     * Get shareable locks
     *
     * @see <a href="https://developer.doordeck.com/docs/#get-shareable-locks">API Doc</a>
     */
    suspend fun getShareableLocks(): List<ShareableLockResponse>

    fun getShareableLocksFuture(): CompletableFuture<List<ShareableLockResponse>>
}

actual fun lockOperations(): LockOperationsResource = LockOperationsResourceImpl(
    httpClient = getKoin().get<HttpClient>(named("cloudHttpClient")),
    contextManager = getKoin().get<ContextManagerImpl>(),
    localUnlock = getKoin().get<LocalUnlockClientImpl>()
)