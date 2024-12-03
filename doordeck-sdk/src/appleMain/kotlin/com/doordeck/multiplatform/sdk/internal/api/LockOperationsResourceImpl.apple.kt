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

internal class LockOperationsResourceImpl(
    private val lockOperationsClient: LockOperationsClient
) : LockOperationsResource {

    override suspend fun getSingleLock(lockId: String): LockResponse {
        return lockOperationsClient.getSingleLockRequest(lockId)
    }

    override suspend fun getLockAuditTrail(lockId: String, start: Int, end: Int): List<AuditResponse> {
        return lockOperationsClient.getLockAuditTrailRequest(lockId, start, end)
    }

    override suspend fun getAuditForUser(userId: String, start: Int, end: Int): List<AuditResponse> {
        return lockOperationsClient.getAuditForUserRequest(userId, start, end)
    }

    override suspend fun getUsersForLock(lockId: String): List<UserLockResponse> {
        return lockOperationsClient.getUsersForLockRequest(lockId)
    }

    override suspend fun getLocksForUser(userId: String): LockUserResponse {
        return lockOperationsClient.getLocksForUserRequest(userId)
    }

    override suspend fun updateLockName(lockId: String, name: String?) {
        return lockOperationsClient.updateLockNameRequest(lockId, name)
    }

    override suspend fun updateLockFavourite(lockId: String, favourite: Boolean?) {
        return lockOperationsClient.updateLockFavouriteRequest(lockId, favourite)
    }

    override suspend fun updateLockColour(lockId: String, colour: String?) {
        return lockOperationsClient.updateLockColourRequest(lockId, colour)
    }

    override suspend fun updateLockSettingDefaultName(lockId: String, name: String?) {
        return lockOperationsClient.updateLockSettingDefaultNameRequest(lockId, name)
    }

    override suspend fun setLockSettingPermittedAddresses(lockId: String, permittedAddresses: List<String>) {
        return lockOperationsClient.setLockSettingPermittedAddressesRequest(lockId, permittedAddresses)
    }

    override suspend fun updateLockSettingHidden(lockId: String, hidden: Boolean) {
        return lockOperationsClient.updateLockSettingHiddenRequest(lockId, hidden)
    }

    override suspend fun setLockSettingTimeRestrictions(lockId: String, times: List<LockOperations.TimeRequirement>) {
        return lockOperationsClient.setLockSettingTimeRestrictionsRequest(lockId, times)
    }

    override suspend fun updateLockSettingLocationRestrictions(lockId: String, location: LockOperations.LocationRequirement?) {
        return lockOperationsClient.updateLockSettingLocationRestrictionsRequest(lockId, location)
    }

    override suspend fun getUserPublicKey(userEmail: String, visitor: Boolean): UserPublicKeyResponse {
        return lockOperationsClient.getUserPublicKeyRequest(userEmail, visitor)
    }

    override suspend fun getUserPublicKeyByEmail(email: String): UserPublicKeyResponse {
        return lockOperationsClient.getUserPublicKeyByEmailRequest(email)
    }

    override suspend fun getUserPublicKeyByTelephone(telephone: String): UserPublicKeyResponse {
        return lockOperationsClient.getUserPublicKeyByTelephoneRequest(telephone)
    }

    override suspend fun getUserPublicKeyByLocalKey(localKey: String): UserPublicKeyResponse {
        return lockOperationsClient.getUserPublicKeyByLocalKeyRequest(localKey)
    }

    override suspend fun getUserPublicKeyByForeignKey(foreignKey: String): UserPublicKeyResponse {
        return lockOperationsClient.getUserPublicKeyByForeignKeyRequest(foreignKey)
    }

    override suspend fun getUserPublicKeyByIdentity(identity: String): UserPublicKeyResponse {
        return lockOperationsClient.getUserPublicKeyByIdentityRequest(identity)
    }

    override suspend fun getUserPublicKeyByEmails(emails: List<String>): List<BatchUserPublicKeyResponse> {
        return lockOperationsClient.getUserPublicKeyByEmailsRequest(emails)
    }

    override suspend fun getUserPublicKeyByTelephones(telephones: List<String>): List<BatchUserPublicKeyResponse> {
        return lockOperationsClient.getUserPublicKeyByTelephonesRequest(telephones)
    }

    override suspend fun getUserPublicKeyByLocalKeys(localKeys: List<String>): List<BatchUserPublicKeyResponse> {
        return lockOperationsClient.getUserPublicKeyByLocalKeysRequest(localKeys)
    }

    override suspend fun getUserPublicKeyByForeignKeys(foreignKeys: List<String>): List<BatchUserPublicKeyResponse> {
        return lockOperationsClient.getUserPublicKeyByForeignKeysRequest(foreignKeys)
    }

    override suspend fun unlockWithContext(lockId: String, directAccessEndpoints: List<String>?) {
        return lockOperationsClient.unlockWithContextRequest(lockId, directAccessEndpoints)
    }

    override suspend fun unlock(unlockOperation: LockOperations.UnlockOperation) {
        return lockOperationsClient.unlockRequest(unlockOperation)
    }

    override suspend fun shareLockWithContext(lockId: String, shareLock: LockOperations.ShareLock) {
        return lockOperationsClient.shareLockWithContextRequest(lockId, shareLock)
    }

    override suspend fun shareLock(shareLockOperation: LockOperations.ShareLockOperation) {
        return lockOperationsClient.shareLockRequest(shareLockOperation)
    }

    override suspend fun revokeAccessToLockWithContext(lockId: String, users: List<String>) {
        return lockOperationsClient.revokeAccessToLockWithContextRequest(lockId, users)
    }

    override suspend fun revokeAccessToLock(revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation) {
        return lockOperationsClient.revokeAccessToLockRequest(revokeAccessToLockOperation)
    }

    override suspend fun updateSecureSettingUnlockDurationWithContext(lockId: String, unlockDuration: Int) {
        return lockOperationsClient.updateSecureSettingUnlockDurationWithContextRequest(lockId, unlockDuration)
    }

    override suspend fun updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration) {
        return lockOperationsClient.updateSecureSettingUnlockDurationRequest(updateSecureSettingUnlockDuration)
    }

    override suspend fun updateSecureSettingUnlockBetweenWithContext(lockId: String, unlockBetween: LockOperations.UnlockBetween?) {
        return lockOperationsClient.updateSecureSettingUnlockBetweenWithContextRequest(lockId, unlockBetween)
    }

    override suspend fun updateSecureSettingUnlockBetween(updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween) {
        return lockOperationsClient.updateSecureSettingUnlockBetweenRequest(updateSecureSettingUnlockBetween)
    }

    override suspend fun getPinnedLocks(): List<LockResponse> {
        return lockOperationsClient.getPinnedLocksRequest()
    }

    override suspend fun getShareableLocks(): List<ShareableLockResponse> {
        return lockOperationsClient.getShareableLocksRequest()
    }
}