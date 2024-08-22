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
import io.ktor.client.HttpClient

class LockOperationsResourceImpl(
    private val httpClient: HttpClient,
    private val contextManager: ContextManagerImpl,
    private val localUnlock: LocalUnlockClientImpl
) : AbstractLockOperationsClientImpl(httpClient, contextManager, localUnlock), LockOperationsResource {

    override suspend fun getSingleLock(lockId: String): LockResponse {
        return getSingleLockRequest(lockId)
    }

    override suspend fun getLockAuditTrail(lockId: String, start: Int, end: Int): List<LockAuditTrailResponse> {
        return getLockAuditTrailRequest(lockId, start, end)
    }

    override suspend fun getAuditForUser(userId: String, start: Int, end: Int): List<UserAuditResponse> {
        return getAuditForUserRequest(userId, start, end)
    }

    override suspend fun getUsersForLock(lockId: String): List<UserLockResponse> {
        return getUsersForLockRequest(lockId)
    }

    override suspend fun getLocksForUser(userId: String): LockUserResponse {
        return getLocksForUserRequest(userId)
    }

    override suspend fun updateLockName(lockId: String, name: String?) {
        return updateLockNameRequest(lockId, name)
    }

    override suspend fun updateLockFavourite(lockId: String, favourite: Boolean?) {
        return updateLockFavouriteRequest(lockId, favourite)
    }

    override suspend fun updateLockColour(lockId: String, colour: String?) {
        return updateLockColourRequest(lockId, colour)
    }

    override suspend fun updateLockSettingDefaultName(lockId: String, name: String?) {
        return updateLockSettingDefaultNameRequest(lockId, name)
    }

    override suspend fun setLockSettingPermittedAddresses(lockId: String, permittedAddresses: List<String>) {
        return setLockSettingPermittedAddressesRequest(lockId, permittedAddresses)
    }

    override suspend fun updateLockSettingHidden(lockId: String, hidden: Boolean) {
        return updateLockSettingHiddenRequest(lockId, hidden)
    }

    override suspend fun setLockSettingTimeRestrictions(lockId: String, times: List<LockOperations.TimeRequirement>) {
        return setLockSettingTimeRestrictionsRequest(lockId, times)
    }

    override suspend fun updateLockSettingLocationRestrictions(lockId: String, location: LockOperations.LocationRequirement?) {
        return updateLockSettingLocationRestrictionsRequest(lockId, location)
    }

    override suspend fun getUserPublicKey(userEmail: String, visitor: Boolean): UserPublicKeyResponse {
        return getUserPublicKeyRequest(userEmail, visitor)
    }

    override suspend fun getUserPublicKeyByEmail(email: String): UserPublicKeyResponse {
        return getUserPublicKeyByEmailRequest(email)
    }

    override suspend fun getUserPublicKeyByTelephone(telephone: String): UserPublicKeyResponse {
        return getUserPublicKeyByTelephoneRequest(telephone)
    }

    override suspend fun getUserPublicKeyByLocalKey(localKey: String): UserPublicKeyResponse {
        return getUserPublicKeyByLocalKeyRequest(localKey)
    }

    override suspend fun getUserPublicKeyByForeignKey(foreignKey: String): UserPublicKeyResponse {
        return getUserPublicKeyByForeignKeyRequest(foreignKey)
    }

    override suspend fun getUserPublicKeyByIdentity(identity: String): UserPublicKeyResponse {
        return getUserPublicKeyByIdentityRequest(identity)
    }

    override suspend fun unlockWithContext(lockId: String, directAccessEndpoints: List<String>?) {
        return unlockWithContextRequest(lockId, directAccessEndpoints)
    }

    override suspend fun unlock(unlockOperation: LockOperations.UnlockOperation) {
        return unlockRequest(unlockOperation)
    }

    override suspend fun shareLockWithContext(lockId: String, shareLock: LockOperations.ShareLock) {
        return shareLockWithContextRequest(lockId, shareLock)
    }

    override suspend fun shareLock(shareLockOperation: LockOperations.ShareLockOperation) {
        return shareLockRequest(shareLockOperation)
    }

    override suspend fun revokeAccessToLockWithContext(lockId: String, users: List<String>) {
        return revokeAccessToLockWithContextRequest(lockId, users)
    }

    override suspend fun revokeAccessToLock(revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation) {
        return revokeAccessToLockRequest(revokeAccessToLockOperation)
    }

    override suspend fun updateSecureSettingUnlockDurationWithContext(lockId: String, unlockDuration: Int) {
        return updateSecureSettingUnlockDurationWithContextRequest(lockId, unlockDuration)
    }

    override suspend fun updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration) {
        return updateSecureSettingUnlockDurationRequest(updateSecureSettingUnlockDuration)
    }

    override suspend fun updateSecureSettingUnlockBetweenWithContext(lockId: String, unlockBetween: LockOperations.UnlockBetween?) {
        return updateSecureSettingUnlockBetweenWithContextRequest(lockId, unlockBetween)
    }

    override suspend fun updateSecureSettingUnlockBetween(updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween) {
        return updateSecureSettingUnlockBetweenRequest(updateSecureSettingUnlockBetween)
    }

    override suspend fun getPinnedLocks(): List<LockResponse> {
        return getPinnedLocksRequest()
    }

    override suspend fun getShareableLocks(): List<ShareableLockResponse> {
        return getShareableLocksRequest()
    }
}