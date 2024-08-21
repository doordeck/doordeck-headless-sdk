package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.LockOperationsResource
import com.doordeck.multiplatform.sdk.api.model.LockOperations
import com.doordeck.multiplatform.sdk.api.responses.LockAuditTrailResponse
import com.doordeck.multiplatform.sdk.api.responses.LockResponse
import com.doordeck.multiplatform.sdk.api.responses.LockUserResponse
import com.doordeck.multiplatform.sdk.api.responses.ShareableLockResponse
import com.doordeck.multiplatform.sdk.api.responses.UserAuditResponse
import com.doordeck.multiplatform.sdk.api.responses.UserLockResponse
import com.doordeck.multiplatform.sdk.api.responses.UserPublicKeyResponse
import com.doordeck.multiplatform.sdk.internal.ContextManagerImpl
import io.ktor.client.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletableFuture

class LockOperationsResourceImpl(
    private val httpClient: HttpClient,
    private val contextManager: ContextManagerImpl,
    private val localUnlock: LocalUnlockClientImpl
) : AbstractLockOperationsClientImpl(httpClient, contextManager, localUnlock), LockOperationsResource {
    
    override suspend fun getSingleLock(lockId: String): LockResponse {
        return getSingleLockRequest(lockId)
    }

    override fun getSingleLockFuture(lockId: String): CompletableFuture<LockResponse> {
        return GlobalScope.future(Dispatchers.IO) { getSingleLockRequest(lockId) }
    }

    override suspend fun getLockAuditTrail(lockId: String, start: Int, end: Int): List<LockAuditTrailResponse> {
        return getLockAuditTrailRequest(lockId, start, end)
    }

    override fun getLockAuditTrailFuture(lockId: String, start: Int, end: Int): CompletableFuture<List<LockAuditTrailResponse>> {
        return GlobalScope.future(Dispatchers.IO) { getLockAuditTrailRequest(lockId, start, end) }
    }

    override suspend fun getAuditForUser(userId: String, start: Int, end: Int): List<UserAuditResponse> {
        return getAuditForUserRequest(userId, start, end)
    }

    override fun getAuditForUserFuture(userId: String, start: Int, end: Int): CompletableFuture<List<UserAuditResponse>> {
        return GlobalScope.future(Dispatchers.IO) { getAuditForUserRequest(userId, start, end) }
    }

    override suspend fun getUsersForLock(lockId: String): List<UserLockResponse> {
        return getUsersForLockRequest(lockId)
    }

    override fun getUsersForLockFuture(lockId: String): CompletableFuture<List<UserLockResponse>> {
        return GlobalScope.future(Dispatchers.IO) { getUsersForLockRequest(lockId) }
    }

    override suspend fun getLocksForUser(userId: String): LockUserResponse {
        return getLocksForUserRequest(userId)
    }

    override fun getLocksForUserFuture(userId: String): CompletableFuture<LockUserResponse> {
        return GlobalScope.future(Dispatchers.IO) { getLocksForUserRequest(userId) }
    }

    override suspend fun updateLockName(lockId: String, name: String?) {
        return updateLockNameRequest(lockId, name)
    }

    override fun updateLockNameFuture(lockId: String, name: String?): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { updateLockNameRequest(lockId, name) }
    }

    override suspend fun updateLockFavourite(lockId: String, favourite: Boolean?) {
        return updateLockFavouriteRequest(lockId, favourite)
    }

    override fun updateLockFavouriteFuture(lockId: String, favourite: Boolean?): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { updateLockFavouriteRequest(lockId, favourite) }
    }

    override suspend fun updateLockColour(lockId: String, colour: String?) {
        return updateLockColourRequest(lockId, colour)
    }

    override fun updateLockColourFuture(lockId: String, colour: String?): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { updateLockColourRequest(lockId, colour) }
    }

    override suspend fun updateLockSettingDefaultName(lockId: String, name: String?) {
        return updateLockSettingDefaultNameRequest(lockId, name)
    }

    override fun updateLockSettingDefaultNameFuture(lockId: String, name: String?): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { updateLockSettingDefaultNameRequest(lockId, name) }
    }

    override suspend fun setLockSettingPermittedAddresses(lockId: String, permittedAddresses: List<String>) {
        return setLockSettingPermittedAddressesRequest(lockId, permittedAddresses)
    }

    override fun setLockSettingPermittedAddressesFuture(lockId: String, permittedAddresses: List<String>): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { setLockSettingPermittedAddressesRequest(lockId, permittedAddresses) }
    }

    override suspend fun updateLockSettingHidden(lockId: String, hidden: Boolean) {
        return updateLockSettingHiddenRequest(lockId, hidden)
    }

    override fun updateLockSettingHiddenFuture(lockId: String, hidden: Boolean): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { updateLockSettingHiddenRequest(lockId, hidden) }
    }

    override suspend fun setLockSettingTimeRestrictions(lockId: String, times: List<LockOperations.TimeRequirement>) {
        return setLockSettingTimeRestrictionsRequest(lockId, times)
    }

    override fun setLockSettingTimeRestrictionsFuture(lockId: String, times: List<LockOperations.TimeRequirement>): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { setLockSettingTimeRestrictionsRequest(lockId, times) }
    }

    override suspend fun updateLockSettingLocationRestrictions(lockId: String, location: LockOperations.LocationRequirement?) {
        return updateLockSettingLocationRestrictionsRequest(lockId, location)
    }

    override fun updateLockSettingLocationRestrictionsFuture(lockId: String, location: LockOperations.LocationRequirement?): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { updateLockSettingLocationRestrictionsRequest(lockId, location) }
    }

    override suspend fun getUserPublicKey(userEmail: String, visitor: Boolean): UserPublicKeyResponse {
        return getUserPublicKeyRequest(userEmail, visitor)
    }

    override suspend fun getUserPublicKeyFuture(userEmail: String, visitor: Boolean): CompletableFuture<UserPublicKeyResponse> {
        return GlobalScope.future(Dispatchers.IO) { getUserPublicKeyRequest(userEmail, visitor) }
    }

    override suspend fun getUserPublicKeyByEmail(email: String): UserPublicKeyResponse {
        return getUserPublicKeyByEmailRequest(email)
    }

    override fun getUserPublicKeyByEmailFuture(email: String): CompletableFuture<UserPublicKeyResponse> {
        return GlobalScope.future(Dispatchers.IO) { getUserPublicKeyByEmailRequest(email) }
    }

    override suspend fun getUserPublicKeyByTelephone(telephone: String): UserPublicKeyResponse {
        return getUserPublicKeyByTelephoneRequest(telephone)
    }

    override fun getUserPublicKeyByTelephoneFuture(telephone: String): CompletableFuture<UserPublicKeyResponse> {
        return GlobalScope.future(Dispatchers.IO) { getUserPublicKeyByTelephoneRequest(telephone) }
    }

    override suspend fun getUserPublicKeyByLocalKey(localKey: String): UserPublicKeyResponse {
        return getUserPublicKeyByLocalKeyRequest(localKey)
    }

    override fun getUserPublicKeyByLocalKeyFuture(localKey: String): CompletableFuture<UserPublicKeyResponse> {
        return GlobalScope.future(Dispatchers.IO) { getUserPublicKeyByLocalKeyRequest(localKey) }
    }

    override suspend fun getUserPublicKeyByForeignKey(foreignKey: String): UserPublicKeyResponse {
        return getUserPublicKeyByForeignKeyRequest(foreignKey)
    }

    override fun getUserPublicKeyByForeignKeyFuture(foreignKey: String): CompletableFuture<UserPublicKeyResponse> {
        return GlobalScope.future(Dispatchers.IO) { getUserPublicKeyByForeignKeyRequest(foreignKey) }
    }

    override suspend fun getUserPublicKeyByIdentity(identity: String): UserPublicKeyResponse {
        return getUserPublicKeyByIdentityRequest(identity)
    }

    override fun getUserPublicKeyByIdentityFuture(identity: String): CompletableFuture<UserPublicKeyResponse> {
        return GlobalScope.future(Dispatchers.IO) { getUserPublicKeyByIdentityRequest(identity) }
    }

    override suspend fun unlockWithContext(lockId: String, directAccessEndpoints: List<String>?) {
        return unlockWithContextRequest(lockId, directAccessEndpoints)
    }

    override fun unlockWithContextFuture(lockId: String, directAccessEndpoints: List<String>?): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { unlockWithContextRequest(lockId, directAccessEndpoints) }
    }

    override suspend fun unlock(unlockOperation: LockOperations.UnlockOperation) {
        return unlockRequest(unlockOperation)
    }

    override fun unlockFuture(unlockOperation: LockOperations.UnlockOperation): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { unlockRequest(unlockOperation) }
    }

    override suspend fun shareLockWithContext(lockId: String, shareLock: LockOperations.ShareLock) {
        return shareLockWithContextRequest(lockId, shareLock)
    }

    override fun shareLockWithContextFuture(lockId: String, shareLock: LockOperations.ShareLock): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { shareLockWithContextRequest(lockId, shareLock) }
    }

    override suspend fun shareLock(shareLockOperation: LockOperations.ShareLockOperation) {
        return shareLockRequest(shareLockOperation)
    }

    override fun shareLockFuture(shareLockOperation: LockOperations.ShareLockOperation): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { shareLockRequest(shareLockOperation) }
    }

    override suspend fun revokeAccessToLockWithContext(lockId: String, users: List<String>) {
        return revokeAccessToLockWithContextRequest(lockId, users)
    }

    override fun revokeAccessToLockWithContextFuture(lockId: String, users: List<String>): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { revokeAccessToLockWithContextRequest(lockId, users) }
    }

    override suspend fun revokeAccessToLock(revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation) {
        return revokeAccessToLock(revokeAccessToLockOperation)
    }

    override fun revokeAccessToLockFuture(revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { revokeAccessToLock(revokeAccessToLockOperation) }
    }

    override suspend fun updateSecureSettingUnlockDurationWithContext(lockId: String, unlockDuration: Int) {
        return updateSecureSettingUnlockDurationWithContextRequest(lockId, unlockDuration)
    }

    override fun updateSecureSettingUnlockDurationWithContextFuture(lockId: String, unlockDuration: Int): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { updateSecureSettingUnlockDurationWithContextRequest(lockId, unlockDuration) }
    }

    override suspend fun updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration) {
        return updateSecureSettingUnlockDurationRequest(updateSecureSettingUnlockDuration)
    }

    override fun updateSecureSettingUnlockDurationFuture(updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { updateSecureSettingUnlockDurationRequest(updateSecureSettingUnlockDuration) }
    }

    override suspend fun uploadSecureSettingUnlockBetweenWithContext(lockId: String, unlockBetween: LockOperations.UnlockBetween?) {
        return uploadSecureSettingUnlockBetweenWithContextRequest(lockId, unlockBetween)
    }

    override fun uploadSecureSettingUnlockBetweenWithContextFuture(lockId: String, unlockBetween: LockOperations.UnlockBetween?): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { uploadSecureSettingUnlockBetweenWithContextRequest(lockId, unlockBetween) }
    }

    override suspend fun uploadSecureSettingUnlockBetween(updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween) {
        return uploadSecureSettingUnlockBetweenRequest(updateSecureSettingUnlockBetween)
    }

    override fun uploadSecureSettingUnlockBetweenFuture(updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { uploadSecureSettingUnlockBetweenRequest(updateSecureSettingUnlockBetween) }
    }

    override suspend fun getPinnedLocks(): List<LockResponse> {
        return getPinnedLocksRequest()
    }

    override fun getPinnedLocksFuture(): CompletableFuture<List<LockResponse>> {
        return GlobalScope.future(Dispatchers.IO) { getPinnedLocksRequest() }
    }

    override suspend fun getShareableLocks(): List<ShareableLockResponse> {
        return getShareableLocksRequest()
    }

    override fun getShareableLocksFuture(): CompletableFuture<List<ShareableLockResponse>> {
        return GlobalScope.future(Dispatchers.IO) { getShareableLocksRequest() }
    }
}