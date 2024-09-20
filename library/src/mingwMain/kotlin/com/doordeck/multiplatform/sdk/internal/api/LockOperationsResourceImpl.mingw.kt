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
import com.doordeck.multiplatform.sdk.util.fromJson
import io.ktor.client.HttpClient
import kotlinx.coroutines.runBlocking

class LockOperationsResourceImpl(
    httpClient: HttpClient,
    contextManager: ContextManagerImpl,
    localUnlock: LocalUnlockClient
) : LockOperationsClient(httpClient, contextManager, localUnlock), LockOperationsResource {

    override fun getSingleLock(lockId: String): LockResponse {
        return runBlocking { getSingleLockRequest(lockId) }
    }

    override fun getLockAuditTrail(lockId: String, start: Int, end: Int): List<LockAuditTrailResponse> {
        return runBlocking { getLockAuditTrailRequest(lockId, start, end) }
    }

    override fun getAuditForUser(userId: String, start: Int, end: Int): List<UserAuditResponse> {
        return runBlocking { getAuditForUserRequest(userId, start, end) }
    }

    override fun getUsersForLock(lockId: String): List<UserLockResponse> {
        return runBlocking { getUsersForLockRequest(lockId) }
    }

    override fun getLocksForUser(userId: String): LockUserResponse {
        return runBlocking { getLocksForUserRequest(userId) }
    }

    override fun updateLockName(lockId: String, name: String?) {
        return runBlocking { updateLockNameRequest(lockId, name) }
    }

    override fun updateLockFavourite(lockId: String, favourite: Boolean?) {
        return runBlocking { updateLockFavouriteRequest(lockId, favourite) }
    }

    override fun updateLockColour(lockId: String, colour: String?) {
        return runBlocking { updateLockColourRequest(lockId, colour) }
    }

    override fun updateLockSettingDefaultName(lockId: String, name: String?) {
        return runBlocking { updateLockSettingDefaultNameRequest(lockId, name) }
    }

    override fun setLockSettingPermittedAddresses(lockId: String, permittedAddresses: List<String>) {
        return runBlocking { setLockSettingPermittedAddressesRequest(lockId, permittedAddresses) }
    }

    override fun updateLockSettingHidden(lockId: String, hidden: Boolean) {
        return runBlocking { updateLockSettingHiddenRequest(lockId, hidden) }
    }

    override fun setLockSettingTimeRestrictions(lockId: String, times: List<LockOperations.TimeRequirement>) {
        return runBlocking { setLockSettingTimeRestrictionsRequest(lockId, times) }
    }

    override fun updateLockSettingLocationRestrictions(lockId: String, location: LockOperations.LocationRequirement?) {
        return runBlocking { updateLockSettingLocationRestrictionsRequest(lockId, location) }
    }

    override fun getUserPublicKey(userEmail: String, visitor: Boolean): UserPublicKeyResponse {
        return runBlocking { getUserPublicKeyRequest(userEmail, visitor) }
    }

    override fun getUserPublicKeyByEmail(email: String): UserPublicKeyResponse {
        return runBlocking { getUserPublicKeyByEmailRequest(email) }
    }

    override fun getUserPublicKeyByTelephone(telephone: String): UserPublicKeyResponse {
        return runBlocking { getUserPublicKeyByTelephoneRequest(telephone) }
    }

    override fun getUserPublicKeyByLocalKey(localKey: String): UserPublicKeyResponse {
        return runBlocking { getUserPublicKeyByLocalKeyRequest(localKey) }
    }

    override fun getUserPublicKeyByForeignKey(foreignKey: String): UserPublicKeyResponse {
        return runBlocking { getUserPublicKeyByForeignKeyRequest(foreignKey) }
    }

    override fun getUserPublicKeyByIdentity(identity: String): UserPublicKeyResponse {
        return runBlocking { getUserPublicKeyByIdentityRequest(identity) }
    }

    override fun unlockWithContext(lockId: String, directAccessEndpoints: List<String>?) {
        return runBlocking { unlockWithContextRequest(lockId, directAccessEndpoints) }
    }

    override fun unlock(unlockOperation: LockOperations.UnlockOperation) {
        return runBlocking { unlockRequest(unlockOperation) }
    }

    override fun unlockSync2(unlockOperation: String) {
        return runBlocking { unlockRequest(unlockOperation.fromJson()) }
    }

    override fun shareLockWithContext(lockId: String, shareLock: LockOperations.ShareLock) {
        return runBlocking { shareLockWithContextRequest(lockId, shareLock) }
    }

    override fun shareLock(shareLockOperation: LockOperations.ShareLockOperation) {
        return runBlocking { shareLockRequest(shareLockOperation) }
    }

    override fun revokeAccessToLockWithContext(lockId: String, users: List<String>) {
        return runBlocking { revokeAccessToLockWithContextRequest(lockId, users) }
    }

    override fun revokeAccessToLock(revokeAccessToLockOperation: LockOperations.RevokeAccessToLockOperation) {
        return runBlocking { revokeAccessToLockRequest(revokeAccessToLockOperation) }
    }

    override fun updateSecureSettingUnlockDurationWithContext(lockId: String, unlockDuration: Int) {
        return runBlocking { updateSecureSettingUnlockDurationWithContextRequest(lockId, unlockDuration) }
    }

    override fun updateSecureSettingUnlockDuration(updateSecureSettingUnlockDuration: LockOperations.UpdateSecureSettingUnlockDuration) {
        return runBlocking { updateSecureSettingUnlockDurationRequest(updateSecureSettingUnlockDuration) }
    }

    override fun updateSecureSettingUnlockBetweenWithContext(lockId: String, unlockBetween: LockOperations.UnlockBetween?) {
        return runBlocking { updateSecureSettingUnlockBetweenWithContextRequest(lockId, unlockBetween) }
    }

    override fun updateSecureSettingUnlockBetween(updateSecureSettingUnlockBetween: LockOperations.UpdateSecureSettingUnlockBetween) {
        return runBlocking { updateSecureSettingUnlockBetweenRequest(updateSecureSettingUnlockBetween) }
    }

    override fun getPinnedLocks(): List<LockResponse> {
        return runBlocking { getPinnedLocksRequest() }
    }

    override fun getShareableLocks(): List<ShareableLockResponse> {
        return runBlocking { getShareableLocksRequest() }
    }
}