package com.doordeck.multiplatform.sdk.internal.api

import com.doordeck.multiplatform.sdk.api.LockOperationsResource
import com.doordeck.multiplatform.sdk.api.model.LockOperations
import com.doordeck.multiplatform.sdk.api.responses.AuditResponse
import com.doordeck.multiplatform.sdk.api.responses.BatchUserPublicKeyResponse
import com.doordeck.multiplatform.sdk.api.responses.LockResponse
import com.doordeck.multiplatform.sdk.api.responses.LockUserResponse
import com.doordeck.multiplatform.sdk.api.responses.ShareableLockResponse
import com.doordeck.multiplatform.sdk.api.responses.UserLockResponse
import com.doordeck.multiplatform.sdk.api.responses.UserPublicKeyResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.future
import java.util.concurrent.CompletableFuture

internal class LockOperationsResourceImpl(
    private val lockOperationsClient: LockOperationsClient
) : LockOperationsResource {
    
    override suspend fun getSingleLock(lockId: String): LockResponse {
        return lockOperationsClient.getSingleLockRequest(lockId)
    }

    override fun getSingleLockAsync(lockId: String): CompletableFuture<LockResponse> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.getSingleLockRequest(lockId) }
    }

    override suspend fun getLockAuditTrail(lockId: String, start: Int, end: Int): List<AuditResponse> {
        return lockOperationsClient.getLockAuditTrailRequest(lockId, start, end)
    }

    override fun getLockAuditTrailAsync(lockId: String, start: Int, end: Int): CompletableFuture<List<AuditResponse>> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.getLockAuditTrailRequest(lockId, start, end) }
    }

    override suspend fun getAuditForUser(userId: String, start: Int, end: Int): List<AuditResponse> {
        return lockOperationsClient.getAuditForUserRequest(userId, start, end)
    }

    override fun getAuditForUserAsync(userId: String, start: Int, end: Int): CompletableFuture<List<AuditResponse>> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.getAuditForUserRequest(userId, start, end) }
    }

    override suspend fun getUsersForLock(lockId: String): List<UserLockResponse> {
        return lockOperationsClient.getUsersForLockRequest(lockId)
    }

    override fun getUsersForLockAsync(lockId: String): CompletableFuture<List<UserLockResponse>> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.getUsersForLockRequest(lockId) }
    }

    override suspend fun getLocksForUser(userId: String): LockUserResponse {
        return lockOperationsClient.getLocksForUserRequest(userId)
    }

    override fun getLocksForUserAsync(userId: String): CompletableFuture<LockUserResponse> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.getLocksForUserRequest(userId) }
    }

    override suspend fun updateLockName(lockId: String, name: String?) {
        return lockOperationsClient.updateLockNameRequest(lockId, name)
    }

    override fun updateLockNameAsync(lockId: String, name: String?): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.updateLockNameRequest(lockId, name) }
    }

    override suspend fun updateLockFavourite(lockId: String, favourite: Boolean?) {
        return lockOperationsClient.updateLockFavouriteRequest(lockId, favourite)
    }

    override fun updateLockFavouriteAsync(lockId: String, favourite: Boolean?): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.updateLockFavouriteRequest(lockId, favourite) }
    }

    override suspend fun updateLockColour(lockId: String, colour: String?) {
        return lockOperationsClient.updateLockColourRequest(lockId, colour)
    }

    override fun updateLockColourAsync(lockId: String, colour: String?): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.updateLockColourRequest(lockId, colour) }
    }

    override suspend fun updateLockSettingDefaultName(lockId: String, name: String?) {
        return lockOperationsClient.updateLockSettingDefaultNameRequest(lockId, name)
    }

    override fun updateLockSettingDefaultNameAsync(lockId: String, name: String?): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.updateLockSettingDefaultNameRequest(lockId, name) }
    }

    override suspend fun setLockSettingPermittedAddresses(lockId: String, permittedAddresses: List<String>) {
        return lockOperationsClient.setLockSettingPermittedAddressesRequest(lockId, permittedAddresses)
    }

    override fun setLockSettingPermittedAddressesAsync(lockId: String, permittedAddresses: List<String>): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.setLockSettingPermittedAddressesRequest(lockId, permittedAddresses) }
    }

    override suspend fun updateLockSettingHidden(lockId: String, hidden: Boolean) {
        return lockOperationsClient.updateLockSettingHiddenRequest(lockId, hidden)
    }

    override fun updateLockSettingHiddenAsync(lockId: String, hidden: Boolean): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.updateLockSettingHiddenRequest(lockId, hidden) }
    }

    override suspend fun setLockSettingTimeRestrictions(lockId: String, times: List<LockOperations.TimeRequirement>) {
        return lockOperationsClient.setLockSettingTimeRestrictionsRequest(lockId, times)
    }

    override fun setLockSettingTimeRestrictionsAsync(lockId: String, times: List<LockOperations.TimeRequirement>): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.setLockSettingTimeRestrictionsRequest(lockId, times) }
    }

    override suspend fun updateLockSettingLocationRestrictions(lockId: String, location: LockOperations.LocationRequirement?) {
        return lockOperationsClient.updateLockSettingLocationRestrictionsRequest(lockId, location)
    }

    override fun updateLockSettingLocationRestrictionsAsync(lockId: String, location: LockOperations.LocationRequirement?): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.updateLockSettingLocationRestrictionsRequest(lockId, location) }
    }

    override suspend fun getUserPublicKey(userEmail: String, visitor: Boolean): UserPublicKeyResponse {
        return lockOperationsClient.getUserPublicKeyRequest(userEmail, visitor)
    }

    override suspend fun getUserPublicKeyAsync(userEmail: String, visitor: Boolean): CompletableFuture<UserPublicKeyResponse> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.getUserPublicKeyRequest(userEmail, visitor) }
    }

    override suspend fun getUserPublicKeyByEmail(email: String): UserPublicKeyResponse {
        return lockOperationsClient.getUserPublicKeyByEmailRequest(email)
    }

    override fun getUserPublicKeyByEmailAsync(email: String): CompletableFuture<UserPublicKeyResponse> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.getUserPublicKeyByEmailRequest(email) }
    }

    override suspend fun getUserPublicKeyByTelephone(telephone: String): UserPublicKeyResponse {
        return lockOperationsClient.getUserPublicKeyByTelephoneRequest(telephone)
    }

    override fun getUserPublicKeyByTelephoneAsync(telephone: String): CompletableFuture<UserPublicKeyResponse> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.getUserPublicKeyByTelephoneRequest(telephone) }
    }

    override suspend fun getUserPublicKeyByLocalKey(localKey: String): UserPublicKeyResponse {
        return lockOperationsClient.getUserPublicKeyByLocalKeyRequest(localKey)
    }

    override fun getUserPublicKeyByLocalKeyAsync(localKey: String): CompletableFuture<UserPublicKeyResponse> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.getUserPublicKeyByLocalKeyRequest(localKey) }
    }

    override suspend fun getUserPublicKeyByForeignKey(foreignKey: String): UserPublicKeyResponse {
        return lockOperationsClient.getUserPublicKeyByForeignKeyRequest(foreignKey)
    }

    override fun getUserPublicKeyByForeignKeyAsync(foreignKey: String): CompletableFuture<UserPublicKeyResponse> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.getUserPublicKeyByForeignKeyRequest(foreignKey) }
    }

    override suspend fun getUserPublicKeyByIdentity(identity: String): UserPublicKeyResponse {
        return lockOperationsClient.getUserPublicKeyByIdentityRequest(identity)
    }

    override fun getUserPublicKeyByIdentityAsync(identity: String): CompletableFuture<UserPublicKeyResponse> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.getUserPublicKeyByIdentityRequest(identity) }
    }

    override suspend fun getUserPublicKeyByEmails(emails: List<String>): List<BatchUserPublicKeyResponse> {
        return lockOperationsClient.getUserPublicKeyByEmailsRequest(emails)
    }

    override fun getUserPublicKeyByEmailsAsync(emails: List<String>): CompletableFuture<List<BatchUserPublicKeyResponse>> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.getUserPublicKeyByEmailsRequest(emails) }
    }

    override suspend fun getUserPublicKeyByTelephones(telephones: List<String>): List<BatchUserPublicKeyResponse> {
        return lockOperationsClient.getUserPublicKeyByTelephonesRequest(telephones)
    }

    override fun getUserPublicKeyByTelephonesAsync(telephones: List<String>): CompletableFuture<List<BatchUserPublicKeyResponse>> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.getUserPublicKeyByTelephonesRequest(telephones) }
    }

    override suspend fun getUserPublicKeyByLocalKeys(localKeys: List<String>): List<BatchUserPublicKeyResponse> {
        return lockOperationsClient.getUserPublicKeyByLocalKeysRequest(localKeys)
    }

    override fun getUserPublicKeyByLocalKeysAsync(localKeys: List<String>): CompletableFuture<List<BatchUserPublicKeyResponse>> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.getUserPublicKeyByLocalKeysRequest(localKeys) }
    }

    override suspend fun getUserPublicKeyByForeignKeys(foreignKeys: List<String>): List<BatchUserPublicKeyResponse> {
        return lockOperationsClient.getUserPublicKeyByForeignKeysRequest(foreignKeys)
    }

    override fun getUserPublicKeyByForeignKeysAsync(foreignKeys: List<String>): CompletableFuture<List<BatchUserPublicKeyResponse>> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.getUserPublicKeyByForeignKeysRequest(foreignKeys) }
    }

    override suspend fun unlockWithContext(lockId: String, directAccessEndpoints: List<String>?) {
        return lockOperationsClient.unlockWithContextRequest(lockId, directAccessEndpoints)
    }

    override fun unlockWithContextAsync(lockId: String, directAccessEndpoints: List<String>?): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.unlockWithContextRequest(lockId, directAccessEndpoints) }
    }

    override suspend fun unlock(unlockOperation: LockOperations.UnlockOperation) {
        return lockOperationsClient.unlockRequest(unlockOperation)
    }

    override fun unlockAsync(unlockOperation: LockOperations.UnlockOperation): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.unlockRequest(unlockOperation) }
    }

    override suspend fun shareLockWithContext(lockId: String, shareLock: LockOperations.ShareLock) {
        return lockOperationsClient.shareLockWithContextRequest(lockId, shareLock)
    }

    override fun shareLockWithContextAsync(lockId: String, shareLock: LockOperations.ShareLock): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.shareLockWithContextRequest(lockId, shareLock) }
    }

    override suspend fun shareLock(shareLockOperation: LockOperations.ShareLockOperation) {
        return lockOperationsClient.shareLockRequest(shareLockOperation)
    }

    override fun shareLockAsync(shareLockOperation: LockOperations.ShareLockOperation): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.shareLockRequest(shareLockOperation) }
    }

    override suspend fun revokeAccessToLockWithContext(lockId: String, users: List<String>) {
        return lockOperationsClient.revokeAccessToLockWithContextRequest(lockId, users)
    }

    override fun revokeAccessToLockWithContextAsync(lockId: String, users: List<String>): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.revokeAccessToLockWithContextRequest(lockId, users) }
    }

    override suspend fun revokeAccessToLock(revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation) {
        return lockOperationsClient.revokeAccessToLockRequest(revokeAccessToLockOperation)
    }

    override fun revokeAccessToLockAsync(revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.revokeAccessToLockRequest(revokeAccessToLockOperation) }
    }

    override suspend fun updateSecureSettingUnlockDurationWithContext(lockId: String, unlockDuration: Int) {
        return lockOperationsClient.updateSecureSettingUnlockDurationWithContextRequest(lockId, unlockDuration)
    }

    override fun updateSecureSettingUnlockDurationWithContextAsync(lockId: String, unlockDuration: Int): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.updateSecureSettingUnlockDurationWithContextRequest(lockId, unlockDuration) }
    }

    override suspend fun updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration) {
        return lockOperationsClient.updateSecureSettingUnlockDurationRequest(updateSecureSettingUnlockDuration)
    }

    override fun updateSecureSettingUnlockDurationAsync(updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.updateSecureSettingUnlockDurationRequest(updateSecureSettingUnlockDuration) }
    }

    override suspend fun updateSecureSettingUnlockBetweenWithContext(lockId: String, unlockBetween: LockOperations.UnlockBetween?) {
        return lockOperationsClient.updateSecureSettingUnlockBetweenWithContextRequest(lockId, unlockBetween)
    }

    override fun updateSecureSettingUnlockBetweenWithContextAsync(lockId: String, unlockBetween: LockOperations.UnlockBetween?): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.updateSecureSettingUnlockBetweenWithContextRequest(lockId, unlockBetween) }
    }

    override suspend fun updateSecureSettingUnlockBetween(updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween) {
        return lockOperationsClient.updateSecureSettingUnlockBetweenRequest(updateSecureSettingUnlockBetween)
    }

    override fun updateSecureSettingUnlockBetweenAsync(updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween): CompletableFuture<Unit> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.updateSecureSettingUnlockBetweenRequest(updateSecureSettingUnlockBetween) }
    }

    override suspend fun getPinnedLocks(): List<LockResponse> {
        return lockOperationsClient.getPinnedLocksRequest()
    }

    override fun getPinnedLocksAsync(): CompletableFuture<List<LockResponse>> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.getPinnedLocksRequest() }
    }

    override suspend fun getShareableLocks(): List<ShareableLockResponse> {
        return lockOperationsClient.getShareableLocksRequest()
    }

    override fun getShareableLocksAsync(): CompletableFuture<List<ShareableLockResponse>> {
        return GlobalScope.future(Dispatchers.IO) { lockOperationsClient.getShareableLocksRequest() }
    }
}