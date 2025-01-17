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
import com.doordeck.multiplatform.sdk.util.completableFuture
import java.util.concurrent.CompletableFuture

internal object LockOperationsResourceImpl : LockOperationsResource {

    override suspend fun getSingleLock(lockId: String): LockResponse {
        return LockOperationsClient.getSingleLockRequest(lockId)
    }

    override fun getSingleLockAsync(lockId: String): CompletableFuture<LockResponse> {
        return completableFuture { getSingleLock(lockId) }
    }

    override suspend fun getLockAuditTrail(lockId: String, start: Int, end: Int): List<AuditResponse> {
        return LockOperationsClient.getLockAuditTrailRequest(lockId, start, end)
    }

    override fun getLockAuditTrailAsync(lockId: String, start: Int, end: Int): CompletableFuture<List<AuditResponse>> {
        return completableFuture { getLockAuditTrail(lockId, start, end) }
    }

    override suspend fun getAuditForUser(userId: String, start: Int, end: Int): List<AuditResponse> {
        return LockOperationsClient.getAuditForUserRequest(userId, start, end)
    }

    override fun getAuditForUserAsync(userId: String, start: Int, end: Int): CompletableFuture<List<AuditResponse>> {
        return completableFuture { getAuditForUser(userId, start, end) }
    }

    override suspend fun getUsersForLock(lockId: String): List<UserLockResponse> {
        return LockOperationsClient.getUsersForLockRequest(lockId)
    }

    override fun getUsersForLockAsync(lockId: String): CompletableFuture<List<UserLockResponse>> {
        return completableFuture { getUsersForLock(lockId) }
    }

    override suspend fun getLocksForUser(userId: String): LockUserResponse {
        return LockOperationsClient.getLocksForUserRequest(userId)
    }

    override fun getLocksForUserAsync(userId: String): CompletableFuture<LockUserResponse> {
        return completableFuture { getLocksForUser(userId) }
    }

    override suspend fun updateLockName(lockId: String, name: String?) {
        return LockOperationsClient.updateLockNameRequest(lockId, name)
    }

    override fun updateLockNameAsync(lockId: String, name: String?): CompletableFuture<Unit> {
        return completableFuture { updateLockName(lockId, name) }
    }

    override suspend fun updateLockFavourite(lockId: String, favourite: Boolean?) {
        return LockOperationsClient.updateLockFavouriteRequest(lockId, favourite)
    }

    override fun updateLockFavouriteAsync(lockId: String, favourite: Boolean?): CompletableFuture<Unit> {
        return completableFuture { updateLockFavourite(lockId, favourite) }
    }

    override suspend fun updateLockColour(lockId: String, colour: String?) {
        return LockOperationsClient.updateLockColourRequest(lockId, colour)
    }

    override fun updateLockColourAsync(lockId: String, colour: String?): CompletableFuture<Unit> {
        return completableFuture { updateLockColour(lockId, colour) }
    }

    override suspend fun updateLockSettingDefaultName(lockId: String, name: String?) {
        return LockOperationsClient.updateLockSettingDefaultNameRequest(lockId, name)
    }

    override fun updateLockSettingDefaultNameAsync(lockId: String, name: String?): CompletableFuture<Unit> {
        return completableFuture { updateLockSettingDefaultName(lockId, name) }
    }

    override suspend fun setLockSettingPermittedAddresses(lockId: String, permittedAddresses: List<String>) {
        return LockOperationsClient.setLockSettingPermittedAddressesRequest(lockId, permittedAddresses)
    }

    override fun setLockSettingPermittedAddressesAsync(lockId: String, permittedAddresses: List<String>): CompletableFuture<Unit> {
        return completableFuture { setLockSettingPermittedAddresses(lockId, permittedAddresses) }
    }

    override suspend fun updateLockSettingHidden(lockId: String, hidden: Boolean) {
        return LockOperationsClient.updateLockSettingHiddenRequest(lockId, hidden)
    }

    override fun updateLockSettingHiddenAsync(lockId: String, hidden: Boolean): CompletableFuture<Unit> {
        return completableFuture { updateLockSettingHidden(lockId, hidden) }
    }

    override suspend fun setLockSettingTimeRestrictions(lockId: String, times: List<LockOperations.TimeRequirement>) {
        return LockOperationsClient.setLockSettingTimeRestrictionsRequest(lockId, times)
    }

    override fun setLockSettingTimeRestrictionsAsync(lockId: String, times: List<LockOperations.TimeRequirement>): CompletableFuture<Unit> {
        return completableFuture { setLockSettingTimeRestrictions(lockId, times) }
    }

    override suspend fun updateLockSettingLocationRestrictions(lockId: String, location: LockOperations.LocationRequirement?) {
        return LockOperationsClient.updateLockSettingLocationRestrictionsRequest(lockId, location)
    }

    override fun updateLockSettingLocationRestrictionsAsync(lockId: String, location: LockOperations.LocationRequirement?): CompletableFuture<Unit> {
        return completableFuture { updateLockSettingLocationRestrictions(lockId, location) }
    }

    override suspend fun getUserPublicKey(userEmail: String, visitor: Boolean): UserPublicKeyResponse {
        return LockOperationsClient.getUserPublicKeyRequest(userEmail, visitor)
    }

    override suspend fun getUserPublicKeyAsync(userEmail: String, visitor: Boolean): CompletableFuture<UserPublicKeyResponse> {
        return completableFuture { getUserPublicKey(userEmail, visitor) }
    }

    override suspend fun getUserPublicKeyByEmail(email: String): UserPublicKeyResponse {
        return LockOperationsClient.getUserPublicKeyByEmailRequest(email)
    }

    override fun getUserPublicKeyByEmailAsync(email: String): CompletableFuture<UserPublicKeyResponse> {
        return completableFuture { getUserPublicKeyByEmail(email) }
    }

    override suspend fun getUserPublicKeyByTelephone(telephone: String): UserPublicKeyResponse {
        return LockOperationsClient.getUserPublicKeyByTelephoneRequest(telephone)
    }

    override fun getUserPublicKeyByTelephoneAsync(telephone: String): CompletableFuture<UserPublicKeyResponse> {
        return completableFuture { getUserPublicKeyByTelephone(telephone) }
    }

    override suspend fun getUserPublicKeyByLocalKey(localKey: String): UserPublicKeyResponse {
        return LockOperationsClient.getUserPublicKeyByLocalKeyRequest(localKey)
    }

    override fun getUserPublicKeyByLocalKeyAsync(localKey: String): CompletableFuture<UserPublicKeyResponse> {
        return completableFuture { getUserPublicKeyByLocalKey(localKey) }
    }

    override suspend fun getUserPublicKeyByForeignKey(foreignKey: String): UserPublicKeyResponse {
        return LockOperationsClient.getUserPublicKeyByForeignKeyRequest(foreignKey)
    }

    override fun getUserPublicKeyByForeignKeyAsync(foreignKey: String): CompletableFuture<UserPublicKeyResponse> {
        return completableFuture { getUserPublicKeyByForeignKey(foreignKey) }
    }

    override suspend fun getUserPublicKeyByIdentity(identity: String): UserPublicKeyResponse {
        return LockOperationsClient.getUserPublicKeyByIdentityRequest(identity)
    }

    override fun getUserPublicKeyByIdentityAsync(identity: String): CompletableFuture<UserPublicKeyResponse> {
        return completableFuture { getUserPublicKeyByIdentity(identity) }
    }

    override suspend fun getUserPublicKeyByEmails(emails: List<String>): List<BatchUserPublicKeyResponse> {
        return LockOperationsClient.getUserPublicKeyByEmailsRequest(emails)
    }

    override fun getUserPublicKeyByEmailsAsync(emails: List<String>): CompletableFuture<List<BatchUserPublicKeyResponse>> {
        return completableFuture { getUserPublicKeyByEmails(emails) }
    }

    override suspend fun getUserPublicKeyByTelephones(telephones: List<String>): List<BatchUserPublicKeyResponse> {
        return LockOperationsClient.getUserPublicKeyByTelephonesRequest(telephones)
    }

    override fun getUserPublicKeyByTelephonesAsync(telephones: List<String>): CompletableFuture<List<BatchUserPublicKeyResponse>> {
        return completableFuture { getUserPublicKeyByTelephones(telephones) }
    }

    override suspend fun getUserPublicKeyByLocalKeys(localKeys: List<String>): List<BatchUserPublicKeyResponse> {
        return LockOperationsClient.getUserPublicKeyByLocalKeysRequest(localKeys)
    }

    override fun getUserPublicKeyByLocalKeysAsync(localKeys: List<String>): CompletableFuture<List<BatchUserPublicKeyResponse>> {
        return completableFuture { getUserPublicKeyByLocalKeys(localKeys) }
    }

    override suspend fun getUserPublicKeyByForeignKeys(foreignKeys: List<String>): List<BatchUserPublicKeyResponse> {
        return LockOperationsClient.getUserPublicKeyByForeignKeysRequest(foreignKeys)
    }

    override fun getUserPublicKeyByForeignKeysAsync(foreignKeys: List<String>): CompletableFuture<List<BatchUserPublicKeyResponse>> {
        return completableFuture { getUserPublicKeyByForeignKeys(foreignKeys) }
    }

    override suspend fun unlock(unlockOperation: LockOperations.UnlockOperation) {
        return LockOperationsClient.unlockRequest(unlockOperation)
    }

    override fun unlockAsync(unlockOperation: LockOperations.UnlockOperation): CompletableFuture<Unit> {
        return completableFuture { unlock(unlockOperation) }
    }

    override suspend fun shareLock(shareLockOperation: LockOperations.ShareLockOperation) {
        return LockOperationsClient.shareLockRequest(shareLockOperation)
    }

    override fun shareLockAsync(shareLockOperation: LockOperations.ShareLockOperation): CompletableFuture<Unit> {
        return completableFuture { shareLock(shareLockOperation) }
    }

    override suspend fun batchShareLock(batchShareLockOperation: LockOperations.BatchShareLockOperation) {
        return LockOperationsClient.batchShareLockRequest(batchShareLockOperation)
    }

    override fun batchShareLockAsync(batchShareLockOperation: LockOperations.BatchShareLockOperation): CompletableFuture<Unit> {
        return completableFuture { batchShareLock(batchShareLockOperation) }
    }

    override suspend fun revokeAccessToLock(revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation) {
        return LockOperationsClient.revokeAccessToLockRequest(revokeAccessToLockOperation)
    }

    override fun revokeAccessToLockAsync(revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation): CompletableFuture<Unit> {
        return completableFuture { revokeAccessToLock(revokeAccessToLockOperation) }
    }

    override suspend fun updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration) {
        return LockOperationsClient.updateSecureSettingUnlockDurationRequest(updateSecureSettingUnlockDuration)
    }

    override fun updateSecureSettingUnlockDurationAsync(updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration): CompletableFuture<Unit> {
        return completableFuture { updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration) }
    }

    override suspend fun updateSecureSettingUnlockBetween(updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween) {
        return LockOperationsClient.updateSecureSettingUnlockBetweenRequest(updateSecureSettingUnlockBetween)
    }

    override fun updateSecureSettingUnlockBetweenAsync(updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween): CompletableFuture<Unit> {
        return completableFuture { updateSecureSettingUnlockBetween(updateSecureSettingUnlockBetween) }
    }

    override suspend fun getPinnedLocks(): List<LockResponse> {
        return LockOperationsClient.getPinnedLocksRequest()
    }

    override fun getPinnedLocksAsync(): CompletableFuture<List<LockResponse>> {
        return completableFuture { getPinnedLocks() }
    }

    override suspend fun getShareableLocks(): List<ShareableLockResponse> {
        return LockOperationsClient.getShareableLocksRequest()
    }

    override fun getShareableLocksAsync(): CompletableFuture<List<ShareableLockResponse>> {
        return completableFuture { getShareableLocks() }
    }
}